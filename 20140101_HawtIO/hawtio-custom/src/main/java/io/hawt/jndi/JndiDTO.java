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

import java.beans.ConstructorProperties;

/**
 *
 */
public class JndiDTO {
	private final String fullName;
	private final String simpleName;
	private final String className;
	private final String[] typeNames;
	private final String value;
	@ConstructorProperties({"fullName","simpleName","className","typeNames","value"}) 
	public JndiDTO(String fullName,String simpleName, String className, String[] typeNames, String value) {
		this.fullName = fullName;
		this.simpleName = simpleName;
		this.className = className;
		this.typeNames = typeNames;
		this.value = value;
	}

	public String getClassName() {
		return className;
	}

	public String[] getTypeNames() {
		return typeNames;
	}

	public String getFullName() {
		return fullName;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public String getValue() {
		return value;
	}
	
}
