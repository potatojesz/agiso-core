/* org.agiso.core.context.util.SystemContextUtils (7 gru 2015)
 * 
 * SystemContextUtils.java
 * 
 * Copyright 2015 agiso.org
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.agiso.core.context.util;

import org.agiso.core.beans.util.BeanUtils;
import org.agiso.core.context.ISystemContext;
import org.agiso.core.context.ISystemContext.Scope;

/**
 * 
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public class SystemContextUtils {
	private static ISystemContext systemContext;

//	--------------------------------------------------------------------------
	public static void initialize() {
		systemContext = BeanUtils.getBean(ISystemContext.class);
	}

//	--------------------------------------------------------------------------
	@SuppressWarnings("unchecked")
	public <T> T putAttribute(String key, Object value, Scope scope) {
		return (T)systemContext.putAttribute(scope, key, value);
	}

	public boolean containsAttribute(String key, Scope scope) {
		return systemContext.containsAttribute(scope, key);
	}

	@SuppressWarnings("unchecked")
	public <T> T getAttribute(String key, Scope scope) {
		return (T)systemContext.getAttribute(scope, key);
	}
	@SuppressWarnings("unchecked")
	public <T> T getAttribute(String key, Scope... scopes) {
		for(Scope scope : scopes) {
			if(systemContext.containsAttribute(scope, key)) {
				return (T)systemContext.getAttribute(scope, key);
			}
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public <T> T getAttribute(String key, T def, Scope... scopes) {
		for(Scope scope : scopes) {
			if(systemContext.containsAttribute(scope, key)) {
				return (T)systemContext.getAttribute(scope, key);
			}
		}
		return def;
	}

	@SuppressWarnings("unchecked")
	public <T> T removeAttribute(String key, Scope scope) {
		return (T)systemContext.removeAttribute(scope, key);
	}
}
