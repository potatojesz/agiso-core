/* org.agiso.core.i18n.support.spring.I18nUtilsMessageSourceProxy (29 paź 2015)
 * 
 * I18nUtilsMessageSourceProxy.java
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

import javax.annotation.PreDestroy;

import org.agiso.core.i18n.provider.ILocaleProvider;
import org.agiso.core.i18n.provider.IMessageProvider;
import org.agiso.core.i18n.util.I18nUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;

/**
 * Pośrednik {@link MessageSource} wykorzystujący mechanizmy I18nUtils.
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public class I18nUtilsMessageSourceProxy implements MessageSource {
	private final MessageSource targetMessageSource;

	private final ILocaleProvider orgLocaleProvider;
	private final IMessageProvider[] orgMessageProviders;

//	--------------------------------------------------------------------------
	public I18nUtilsMessageSourceProxy(MessageSource targetMessageSource) {
		this(targetMessageSource, new LocaleContextHolderLocaleProvider());
	}
	public I18nUtilsMessageSourceProxy(MessageSource targetMessageSource, ILocaleProvider localeProvider) {
		this.targetMessageSource = targetMessageSource;

		this.orgLocaleProvider = I18nUtils.getLocaleProvider();
		I18nUtils.setLocaleProvider(localeProvider);

		this.orgMessageProviders = I18nUtils.getMessageProviders();
		I18nUtils.setMessageProvider(
				new MessageSourceMessageProvider(this.targetMessageSource)
		);
	}

//	--------------------------------------------------------------------------
	@PreDestroy
	public void cleanUp() throws Exception {
		I18nUtils.setLocaleProvider(orgLocaleProvider);
		I18nUtils.setMessageProviders(orgMessageProviders);
	}

//	--------------------------------------------------------------------------
	@Override
	public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
		return targetMessageSource.getMessage(resolvable, locale);
	}
	@Override
	public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
		return targetMessageSource.getMessage(code, args, locale);
	}
	@Override
	public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
		return targetMessageSource.getMessage(code, args, defaultMessage, locale);
	}
}
