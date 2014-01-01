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

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

/**
 *
 */
public class StubContext implements Context{
	private final Hashtable<String, Object> environment;
	private final Map<String,Object> map=new HashMap<String, Object>();

	public StubContext(Map<String, Object> environment) {
		this.environment = new Hashtable<String, Object>(environment);
		Map<String,Object> data=(Map<String,Object>)environment.get("data");
		if (data!=null) {
			for(Map.Entry<String,Object> entry:data.entrySet()) {
				if (entry.getValue() instanceof Map) {
					Hashtable<String, Object> newEnvironment=new Hashtable<String, Object>(environment);
					newEnvironment.put("data", entry.getValue());
					map.put(entry.getKey(), new StubContext(newEnvironment));
				} else {
					map.put(entry.getKey(), entry.getValue());
				}
			}
		}
	}
	@Override
	public NamingEnumeration<NameClassPair> list(String name) throws NamingException {
		if (name==null || name.isEmpty()) {
			return new StubNamingEnumeration(map.entrySet().iterator());
		} else {
			return ((Context) lookup(name)).list("");
		}		
	}
	@Override
	public Object lookup(String name) throws NamingException {
		int slashPos=name.indexOf('/');
		if (slashPos<0) {
			return map.get(name);			
		}
		String subContextName=name.substring(0, slashPos);
		Context subContext=(Context) map.get(subContextName);
		if (subContext==null) {
			throw new NamingException(subContextName+" not found");
		}
		return subContext.lookup(name.substring(slashPos+1));
	}
	@Override
	public Object addToEnvironment(String propName, Object propVal) throws NamingException {
		return environment.put(propName, propVal);
	}

	@Override
	public Object removeFromEnvironment(String propName) throws NamingException {
		return environment.remove(propName);
	}

	@Override
	public Hashtable<?, ?> getEnvironment() throws NamingException {
		return environment;
	}
	@Override
	public void close() throws NamingException {
	}


	@Override
	public Object lookup(Name name) throws NamingException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}


	@Override
	public void bind(Name name, Object obj) throws NamingException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void bind(String name, Object obj) throws NamingException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void rebind(Name name, Object obj) throws NamingException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void rebind(String name, Object obj) throws NamingException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void unbind(Name name) throws NamingException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void unbind(String name) throws NamingException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void rename(Name oldName, Name newName) throws NamingException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void rename(String oldName, String newName) throws NamingException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public NamingEnumeration<NameClassPair> list(Name name) throws NamingException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}


	@Override
	public NamingEnumeration<Binding> listBindings(Name name) throws NamingException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public NamingEnumeration<Binding> listBindings(String name) throws NamingException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void destroySubcontext(Name name) throws NamingException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void destroySubcontext(String name) throws NamingException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Context createSubcontext(Name name) throws NamingException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Context createSubcontext(String name) throws NamingException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Object lookupLink(Name name) throws NamingException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Object lookupLink(String name) throws NamingException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public NameParser getNameParser(Name name) throws NamingException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public NameParser getNameParser(String name) throws NamingException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Name composeName(Name name, Name prefix) throws NamingException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public String composeName(String name, String prefix) throws NamingException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public String getNameInNamespace() throws NamingException {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
