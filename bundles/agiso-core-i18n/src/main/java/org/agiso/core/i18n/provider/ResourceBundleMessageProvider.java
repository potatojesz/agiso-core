/* org.agiso.core.i18n.provider.ResourceBundleMessageProvider (09-01-2015)
 * 
 * ResourceBundleMessageProvider.java
 * 
 * Copyright 2015 agiso.org.
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

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public class ResourceBundleMessageProvider extends AbstractMessageProvider {
	private ResourceBundle resourceBundle;

//	--------------------------------------------------------------------------
	public ResourceBundleMessageProvider(ResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
	}

//	--------------------------------------------------------------------------
	@Override
	protected MessageFormat resolveMessageFormat(String code, Locale locale) {
		String msg = getStringOrNull(resourceBundle, code);
		if(msg != null) {
			return createMessageFormat(msg, locale);
		}
		return null;
	}

	private String getStringOrNull(ResourceBundle resourceBundle, String code) {
		return resourceBundle.getString(code);
	}
}
