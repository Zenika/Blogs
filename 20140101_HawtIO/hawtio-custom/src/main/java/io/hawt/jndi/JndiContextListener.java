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

import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class JndiContextListener implements ServletContextListener {
	private final List<JndiFacade> registeredMBeans=new ArrayList<JndiFacade>(2);
	private final Logger logger=LoggerFactory.getLogger(JndiFacadeMXBean.class);
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		registerContext(null); // Initial context
		registerContext("java:global"); 
		registerContext("java:comp/env"); 
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		for(JndiFacade mBean:registeredMBeans) {
			try {
				mBean.destroy();
			} catch (Exception exception) {
			}
		}
		registeredMBeans.clear();
	}
	
	private void registerContext(String name) {
		try {
			JndiFacade jndiFacade = new JndiFacade();
			jndiFacade.setContextName(name);
			jndiFacade.init();
			registeredMBeans.add(jndiFacade);
		} catch (Exception exception) {
			warn("Couldn't register context "+name, exception);
		}
	}
	public void warn(String message, Exception exc) {
		logger.warn(message, exc);
	}
}
