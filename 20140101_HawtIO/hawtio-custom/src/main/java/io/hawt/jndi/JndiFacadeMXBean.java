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

import javax.management.MXBean;
import javax.naming.NamingException;

/**
 * JNDI Facade MX Bean interface
 */
@MXBean
public interface JndiFacadeMXBean {
	/**
	 * List the content of JNDI context 
	 * @param name JNDI context path
	 * @return List of JNDI elements
	 */
	JndiDTO[] list(String name) throws NamingException;

	/**
	 * List the content of JNDI context 
	 * @return List of JNDI elements
	 */
	JndiDTO[] getData() throws NamingException;

	/**
	 * Get the detail of an element 
	 * @param name Element path
	 * @return Detail of element
	 */
	JndiDTO lookup(String name) throws NamingException;

	/**
	 * Get context name
	 * @return null stands for initial context
	 */
	String getContextName();
}
