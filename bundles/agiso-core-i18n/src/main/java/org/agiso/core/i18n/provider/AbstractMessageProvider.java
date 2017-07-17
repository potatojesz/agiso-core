/* org.agiso.core.i18n.provider.AbstractMessageProvider (09-01-2015)
 * 
 * AbstractMessageProvider.java
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

import org.agiso.core.i18n.util.I18nUtils;

/**
 * 
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public abstract class AbstractMessageProvider extends MessageProviderSupport implements IMessageProvider {
	private boolean useCodeAsDefaultMessage = true;

//	--------------------------------------------------------------------------
	public void setUseCodeAsDefaultMessage(boolean useCodeAsDefaultMessage) {
		this.useCodeAsDefaultMessage = useCodeAsDefaultMessage;
	}
	protected boolean isUseCodeAsDefaultMessage() {
		return this.useCodeAsDefaultMessage;
	}

//	--------------------------------------------------------------------------
	@Override
	public String getMessage(String code, Object... args) {
		return getMessage(I18nUtils.getLocale(), code, args);
	}
	@Override
	public String getMessage(Locale locale, String code, Object... args) {
		final String msg = getMessageIfExists(locale, code, args);
		if(msg != null) {
			return msg;
		}
		throw new RuntimeException("Message code '" + code + "' not found");
	}

	@Override
	public String getMessageIfExists(String code, Object... args) {
		return getMessageIfExists(I18nUtils.getLocale(), code, args);
	}
	@Override
	public String getMessageIfExists(Locale locale, String code, Object... args) {
		final String msg = getMessageInternal(locale, code, args);
		if(msg != null) {
			return msg;
		}
		final String def = getDefaultMessage(code);
		if(def != null) {
			return def;
		}
		return null;
	}

//	--------------------------------------------------------------------------
	protected String getMessageInternal(Locale locale, String code, Object[] args) {
		if(code == null) {
			return null;
		}

		if(locale == null) {
			locale = I18nUtils.getLocale();
		}

		final MessageFormat messageFormat = resolveMessageFormat(locale, code);
		if(messageFormat != null) {
			synchronized(messageFormat) {
				return messageFormat.format(args);
			}
		}

		return null;
	}

	protected String getDefaultMessage(String code) {
		if(isUseCodeAsDefaultMessage()) {
			return code;
		}
		return null;
	}

	protected abstract MessageFormat resolveMessageFormat(Locale locale, String code);
}
