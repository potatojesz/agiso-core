/* org.agiso.core.i18n.support.spring.LocaleContextHolderLocaleProvider (29 paź 2015)
 * 
 * LocaleContextHolderLocaleProvider.java
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
package org.agiso.core.i18n.support.spring;

import java.util.Locale;

import org.agiso.core.i18n.provider.ILocaleProvider;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * {@link ILocaleProvider} dostarczający lokalizację z wykorzystaniem mechanizmów
 * {@link LocaleContextHolder}'a dostarczanego przez framework Spring.
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public class LocaleContextHolderLocaleProvider implements ILocaleProvider {
	@Override
	public Locale getLocale() {
		return LocaleContextHolder.getLocale();
	}
}
