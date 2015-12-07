/* org.agiso.core.context.ISystemContext (2012-02-15)
 * 
 * ISystemContext.java
 * 
 * Copyright 2012 agiso.org.
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
package org.agiso.core.context;

import java.util.Map;

/**
 * Interfejs kontekstu systemowego dającego dostęp do parametrów
 * .... TODO
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public interface ISystemContext {
	public enum Scope {
		FLASH,
		REQUEST,
		SESSION,
		USER,
		CLIENTIP,
		INSTANCE,
		GLOBAL
	}

//	--------------------------------------------------------------------------
	public Object putString(Scope scope, String key, String value);
	public Object putAttribute(Scope scope, String key, Object value);

	public boolean containsAttribute(Scope scope, String key);

	public String getString(Scope scope, String key);
	public Object getAttribute(Scope scope, String key);

	public String removeString(Scope scope, String key);
	public Object removeAttribute(Scope scope, String key);

	public Map<String, Object> getAttributes(Scope scope);
	public Map<String, Object> getFlashAttributes();
	public Map<String, Object> getRequestAttributes();
	public Map<String, Object> getSessionAttributes();
	public Map<String, Object> getUserAttributes();
	public Map<String, Object> getClientIPAttributes();
	public Map<String, Object> getInstanceAttributes();
	public Map<String, Object> getGlobalAttributes();

	public void clearFlashScope();
	public void clearRequestScope();
	public void clearSessionScope();

//	--------------------------------------------------------------------------
	public Map<String, Object> getFa();
	public Map<String, Object> getRa();
	public Map<String, Object> getSa();
	public Map<String, Object> getUa();
	public Map<String, Object> getCa();
	public Map<String, Object> getIa();
	public Map<String, Object> getGa();
}
