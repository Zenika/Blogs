/*
 * Copyright 2013 gerald.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.hawt.jndi;

import io.hawt.util.MBeanSupport;
import java.lang.reflect.Proxy;
import java.util.*;
import javax.naming.*;

/**
 * JNDI Facade MX Bean implementation
 */
public class JndiFacade extends MBeanSupport implements JndiFacadeMXBean {
	/**
	 * Context name/path from initial context.
	 * null means initial context
	 */
	private String contextName;
	/**
	 * List of known datatypes
	 */
	private final List<Class> dataTypes=new ArrayList<Class>();
	/**
	 * List of simple types
	 */
	private final Set<Class> simpleTypes=new HashSet<Class>(Arrays.asList(String.class,Integer.class,Long.class,Boolean.class));
	/**
	 * JNDI Environment
	 */
	private Hashtable<String,?> environment;
	/**
	 * Default constructor
	 */
	public JndiFacade() {
		addDataTypes(Context.class, Reference.class);
		addDataTypes(simpleTypes);
		addDataTypes(
				// JDBC
				"javax.sql.DataSource",
				// Mail
				"javax.mail.Session",
				// JTA
				"javax.transaction.UserTransaction",
				"javax.transaction.TransactionManager",
				"javax.transaction.TransactionSynchronizationRegistry",
				// JMS
				"javax.jms.ConnectionFactory",
				"javax.jms.Queue",
				"javax.jms.Topic",
				"javax.jms.Destination",
				// Validation
				"javax.validation.ValidatorFactory"
				);
	}
	/**
	 * Execute something with a JNDI connection.
	 * Inspired by Spring JndiTemplate
	 * @param <T> Return type
	 * @param callback Callback executed within a JNDI context
	 * @return Callback result
	 */
	protected <T> T execute(ContextCallback<T> callback) throws NamingException {
		InitialContext initialContext=null;
		try {
			initialContext= new InitialContext(environment);
			Context context;
			if (contextName==null) {
				context = initialContext;
			} else {
				context=(Context) initialContext.lookup(contextName);
			}
			return callback.doInJndi(context);
		} finally {
			if (initialContext!=null) {
				initialContext.close();
			}
		}
	}
	/**
	 * Callback used by {@link #execute(io.hawt.jndi.JndiFacade.ContextCallback) } method
	 */
	protected interface ContextCallback<T> {
		T doInJndi(Context context) throws NamingException;
	}
	/**
	 * Add types to known data types
	 */
	public final void addDataTypes(Collection<Class>  aDataTypes) {
		dataTypes.addAll(aDataTypes);
	}
	/**
	 * Add types to known data types
	 */
	public final void addDataTypes(Class... aDataTypes) {
		addDataTypes(Arrays.asList(aDataTypes));
	}
	/**
	 * Add types to known data types
	 */
	public final void addDataTypes(String... aDataTypeNames) {
		for(String dataTypeName:aDataTypeNames) {
			try {
				dataTypes.add(Class.forName(dataTypeName));				
			} catch (ClassNotFoundException classNotFoundException) {
			}
		}
	}
	/**
	 * Try to get Class from class name
	 * @param typeName Class name
	 * @return Class or null if not found
	 */
	private Class getType(String typeName) {
		Class type;
		try {
			type= Class.forName(typeName);
		} catch (ClassNotFoundException classNotFoundException) {
			type= null;
		}		
		return type;
	}
	/**
	 * Try to get known type from any type
	 * @param type Any type
	 * @return Known type or null if unknown
	 */
	private List<Class> getDataTypes(Class type) {
		List<Class> result=new ArrayList<Class>(1);
		for(Class dataType:dataTypes) {
			if (dataType.isAssignableFrom(type)) {
				result.add(dataType);
			}
		}
		return result;
	}
	
	private JndiDTO createData(Context context, String fullName, String simpleName, String className, Object value) {
		Class clazz=null;
		List<Class> dataTypes=Collections.emptyList();
		boolean proxy=false;
		if (value!=null) {
			clazz=value.getClass();
		} else if (className!=null) {
			clazz=getType(className);
		}
		if (clazz!=null) {
			dataTypes=getDataTypes(clazz);
			proxy=Proxy.isProxyClass(clazz);
		}
		if (simpleName==null) {
			int slashPos=fullName.lastIndexOf('/');
			simpleName=slashPos<0?fullName:fullName.substring(slashPos+1);
		}
		if (value!=null) {
			for(Class dataType:dataTypes) {
				if (simpleTypes.contains(dataType)) {
					try {
						value = context.lookup(fullName);
					} catch (NamingException namingException) {
					}
					break;
				}
			}
		}
		List<String> dataTypeNames=new ArrayList<String>(dataTypes.size()+1);
		for(Class dataType:dataTypes) {
			dataTypeNames.add(dataType.getName());			
		}		
		if (proxy) {
			dataTypeNames.add("$Proxy");
		}
		String[] dataTypeNamesArray=dataTypeNames.isEmpty()?
				null:
				dataTypeNames.toArray(new String[dataTypeNames.size()]);
		JndiDTO namingData=new JndiDTO(
				fullName,
				simpleName,
				className,
				dataTypeNamesArray,
				value==null?null:value.toString()
		);
		return namingData;
	}
	private String fixName(String name) {
		return name==null?"":name.trim();
	}
	@Override
	public JndiDTO[] list(final String name) throws NamingException {
		return execute(new ContextCallback<JndiDTO[]>() {
			@Override
			public JndiDTO[] doInJndi(Context context) throws NamingException {
				NamingEnumeration<NameClassPair> namingEnum=null;
				try {
					namingEnum=context.list(fixName(name));
					String prefix=name==null||name.isEmpty()?"":name+"/";
					List<JndiDTO> namingDatas=new ArrayList<JndiDTO>();
					while(namingEnum.hasMore()) {
						NameClassPair nameClassPair=namingEnum.next();
						String simpleName=nameClassPair.getName();
						String fullName=prefix+simpleName;
						if (nameClassPair instanceof Binding) {
							Binding binding=(Binding) nameClassPair;
							namingDatas.add(createData(context, fullName, simpleName, nameClassPair.getClassName(), binding.getObject()));
						} else {
							namingDatas.add(createData(context, fullName, simpleName, nameClassPair.getClassName(), null));														
						}
					}
					return namingDatas.toArray(new JndiDTO[namingDatas.size()]);
				} finally {
					if (namingEnum!=null) {
						namingEnum.close();
					}
				}
			}
		});
	}
	@Override
	public JndiDTO lookup(final String name) throws NamingException {
		return execute(new ContextCallback<JndiDTO>() {
			@Override
			public JndiDTO doInJndi(Context context) throws NamingException {
				String fullName=fixName(name);
				String simpleName;
				Object object;
				if (fullName.isEmpty()) {
					object = context;
					simpleName = "";
				} else {
					object=context.lookup(fullName);
					simpleName=null;
				}
				if (object==null) {
					return null;
				}
				return createData(context, fullName, simpleName, object.getClass().getName(), object);
			}
		});
	}
	@Override
	public JndiDTO[] getData() throws NamingException {
		return list("");
	}

	@Override
	protected String getDefaultObjectName() {
		String name;
		if (contextName==null) {
			name="initial";
		} else {
			name=contextName.replaceAll("[:/]+", ".");
			if (name.endsWith(".")) {
				name=name.substring(0, name.length()-1);
			}
		}
		return "hawtio:type=JndiFacade,name="+name;
	}

	/**
	 * Get context name
	 * @return null stands for initial context
	 */
	@Override
	public String getContextName() {
		return contextName;
	}

	public void setContextName(String contextName) {
		this.contextName = contextName;
	}

	public Hashtable<String, ?> getEnvironment() {
		return environment;
	}

	public void setEnvironment(Hashtable<String, ?> environment) {
		this.environment = environment;
	}
	
}
