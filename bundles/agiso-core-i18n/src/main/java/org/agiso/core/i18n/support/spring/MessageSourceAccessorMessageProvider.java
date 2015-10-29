/* org.agiso.core.i18n.support.spring.MessageSourceAccessorMessageProvider (29 paź 2015)
 * 
 * MessageSourceAccessorMessageProvider.java
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

import java.io.Serializable;
import java.util.Locale;

import javax.servlet.ServletContext;

import org.agiso.core.i18n.provider.IMessageProvider;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * {@link IMessageProvider} rozwijający wiadomości z wykorzystaniem mechanizmów
 * {@link MessageSourceAccessor}'a dostarczanego przez framework Spring.
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public class MessageSourceAccessorMessageProvider implements IMessageProvider, Serializable {
	private static final long serialVersionUID = 1L;

	private MessageSourceAccessor messageSource;

//	--------------------------------------------------------------------------
	public MessageSourceAccessorMessageProvider(ServletContext servletContext) {
		messageSource = new MessageSourceAccessor(
				WebApplicationContextUtils.getRequiredWebApplicationContext(
						servletContext
				)
		);
	}

//	--------------------------------------------------------------------------
	@Override
	public String getMessage(String code, Object... args) {
		return messageSource.getMessage(code, args);
	}
	@Override
	public String getMessage(Locale locale, String code, Object... args) {
		return messageSource.getMessage(code, args, locale);
	}
	@Override
	public String getMessageIfExists(String code, Object... args) {
		try {
			return messageSource.getMessage(code, args);
		} catch(NoSuchMessageException nsme) {
			return null;
		}
	}
	@Override
	public String getMessageIfExists(Locale locale, String code, Object... args) {
		try {
			return messageSource.getMessage(code, args, locale);
		} catch(NoSuchMessageException nsme) {
			return null;
		}
	}
}
