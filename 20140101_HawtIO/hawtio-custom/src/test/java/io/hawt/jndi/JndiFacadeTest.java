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
import javax.naming.Context;
import javax.naming.NamingException;
import org.junit.*;
import static org.junit.Assert.*;
/**
 *
 */
public class JndiFacadeTest {
	private JndiFacade jndiFacade;
	private Context context;
	@Before
	public void setUp() throws NamingException {
		Hashtable<String,Object> environment=new Hashtable<String, Object>();
		environment.put(Context.INITIAL_CONTEXT_FACTORY, StubInitialContextFactory.class.getName());
		Map<String,Object> data=new HashMap<String, Object>(3);
		data.put("string", "Xxx");
		data.put("integer", 123);
		Map<String,Object> subData=new HashMap<String, Object>(3);
		subData.put("boolean", true);
		data.put("subcontext", subData);
		environment.put("data", data);
		jndiFacade=new JndiFacade();
		jndiFacade.setEnvironment(environment);
	}
	@Test
	public void testGetData() throws NamingException {
		assertEquals(3, jndiFacade.getData().length);
	}
	@Test
	public void testList() throws NamingException {
		assertEquals(3, jndiFacade.list(null).length);
		assertEquals(3, jndiFacade.list("").length);
		final JndiDTO[] namingDatas = jndiFacade.list("subcontext");
		assertEquals(1, namingDatas.length);
		assertEquals("subcontext/boolean", namingDatas[0].getFullName());
		assertEquals("boolean", namingDatas[0].getSimpleName());
		assertEquals("java.lang.Boolean", namingDatas[0].getTypeNames()[0]);		
	}
	@Test
	public void testLookup() throws NamingException {
		JndiDTO data=jndiFacade.lookup("string");
		assertEquals("string", data.getSimpleName());
		assertEquals("string", data.getFullName());
		assertEquals("java.lang.String", data.getTypeNames()[0]);
		
		data=jndiFacade.lookup("subcontext");
		assertEquals("subcontext", data.getSimpleName());
		assertEquals("subcontext", data.getFullName());
		assertEquals("javax.naming.Context", data.getTypeNames()[0]);
		
		data=jndiFacade.lookup("subcontext/boolean");
		assertEquals("boolean", data.getSimpleName());
		assertEquals("subcontext/boolean", data.getFullName());
		assertEquals("java.lang.Boolean", data.getTypeNames()[0]);
		
		assertNull(jndiFacade.lookup("null"));

		data=jndiFacade.lookup(null);
		assertEquals("", data.getSimpleName());
		assertEquals("", data.getFullName());
		assertEquals("javax.naming.Context", data.getTypeNames()[0]);
	}
}
