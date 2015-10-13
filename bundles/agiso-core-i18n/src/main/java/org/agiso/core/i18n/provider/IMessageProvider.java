/* org.agiso.core.i18n.provider.IMessageProvider (18 lut 2015)
 * 
 * IMessageProvider.java
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
package org.agiso.core.i18n.provider;

import java.util.Locale;

/**
 * Interfejs dostarczyciela rozwinięć kodów komunikatów na komunikaty.
 * 
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public interface IMessageProvider {
	/**
	 * @param code
	 * @param args
	 * @return
	 */
	public String getMessage(String code, Object... args);
	/**
	 * @param locale
	 * @param code
	 * @param args
	 * @return
	 */
	public String getMessage(Locale locale, String code, Object... args);

	/**
	 * @param code
	 * @param args
	 * @return
	 */
	public String getMessageIfExists(String code, Object... args);
	/**
	 * @param locale
	 * @param code
	 * @param args
	 * @return
	 */
	public String getMessageIfExists(Locale locale, String code, Object... args);
}
