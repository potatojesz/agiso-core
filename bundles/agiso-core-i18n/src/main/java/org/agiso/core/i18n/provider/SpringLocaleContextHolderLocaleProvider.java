/* org.agiso.core.i18n.provider.SpringLocaleContextHolderLocaleProvider (29 paź 2015)
 * 
 * SpringLocaleContextHolderLocaleProvider.java
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

import org.springframework.context.i18n.LocaleContextHolder;

/**
 * {@link ILocaleProvider} dostarczający lokalizację z wykorzystaniem mechanizmów
 * {@link LocaleContextHolder}'a dostarczanego przez framework Spring.
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public class SpringLocaleContextHolderLocaleProvider implements ILocaleProvider {
	@Override
	public Locale getLocale() {
		return LocaleContextHolder.getLocale();
	}
}
