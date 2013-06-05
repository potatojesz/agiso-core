/* org.agiso.core.logging.slf4j.SLF4JLogger (2009-02-10)
 *
 * SLF4JLogger.java
 *
 * Copyright 2009 agiso.org.
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
package org.agiso.core.logging.slf4j;

import org.agiso.core.i18n.util.I18nUtils;
import org.agiso.core.i18n.util.I18nUtils.I18nId;
import org.agiso.core.logging.EnumLogger;
import org.agiso.core.logging.I18nLogger;
import org.agiso.core.logging.Logger;
import org.agiso.core.logging.MessageLogger;
import org.apache.commons.lang.ArrayUtils;

/**
 * Implementacja interfejsu {@link Logger} dostarczająca mechanizmów logowania
 * za pośrednictwem prostego loggera SLF4J.
 * 
 * @author <a href="mailto:kkopacz@agiso.org">Karol Kopacz</a>
 * @since 1.0
 */
class SLF4JLogger<T, L extends org.slf4j.Logger, E extends I18nId>
		implements Logger, EnumLogger, I18nLogger<E>, MessageLogger {
	protected L logger;

//	--------------------------------------------------------------------------
	SLF4JLogger(L logger) {
		this.logger = logger;
	}

//	--------------------------------------------------------------------------
	@Override
	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}
	@Override
	public void trace(String message, Object... args) {
		if(args == null || args.length == 0) {
			logger.trace(message);
		} else {
			logger.trace(message, args);
		}
	}
	@Override
	public void trace(Enum<?> e, Object... args) {
		logger.trace(I18nUtils.getMessage(e, args));
	}
	@Override
	public void trace(E logId, Object... args) {
		logger.trace(I18nUtils.getMessage((Enum<?>)logId, args));
	}
	@Override
	public void trace(MessageId messageId, Object... args) {
		logger.trace(I18nUtils.getMessage((Enum<?>)messageId, args));
	}
	@Override
	public void trace(Throwable t, String message, Object... args) {
		if(args == null || args.length == 0) {
			logger.trace(message, t);
		} else {
			logger.trace(message, ArrayUtils.add(args, t));
		}
	}
	@Override
	public void trace(Throwable t, Enum<?> e, Object... args) {
		logger.trace(I18nUtils.getMessage(e, args), t);
	}
	@Override
	public void trace(Throwable t, E logId, Object... args) {
		logger.trace(I18nUtils.getMessage((Enum<?>)logId, args), t);
	}
	@Override
	public void trace(Throwable t, MessageId messageId, Object... args) {
		logger.trace(I18nUtils.getMessage((Enum<?>)messageId, args), t);
	}

	@Override
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}
	@Override
	public void debug(String message, Object... args) {
		if(args == null || args.length == 0) {
			logger.debug(message);
		} else {
			logger.debug(message, args);
		}
	}
	@Override
	public void debug(Enum<?> e, Object... args) {
		logger.debug(I18nUtils.getMessage(e, args));
	}
	@Override
	public void debug(E logId, Object... args) {
		logger.debug(I18nUtils.getMessage((Enum<?>)logId, args));
	}
	@Override
	public void debug(MessageId messageId, Object... args) {
		logger.debug(I18nUtils.getMessage((Enum<?>)messageId, args));
	}
	@Override
	public void debug(Throwable t, String message, Object... args) {
		if(args == null || args.length == 0) {
			logger.debug(message, t);
		} else {
			logger.debug(message, ArrayUtils.add(args, t));
		}
	}
	@Override
	public void debug(Throwable t, Enum<?> e, Object... args) {
		logger.debug(I18nUtils.getMessage(e, args), t);
	}
	@Override
	public void debug(Throwable t, E logId, Object... args) {
		logger.debug(I18nUtils.getMessage((Enum<?>)logId, args), t);
	}
	@Override
	public void debug(Throwable t, MessageId messageId, Object... args) {
		logger.debug(I18nUtils.getMessage((Enum<?>)messageId, args), t);
	}

	@Override
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}
	@Override
	public void info(String message, Object... args) {
		if(args == null || args.length == 0) {
			logger.info(message);
		} else {
			logger.info(message, args);
		}
	}
	@Override
	public void info(Enum<?> e, Object... args) {
		logger.info(I18nUtils.getMessage(e, args));
	}
	@Override
	public void info(E logId, Object... args) {
		logger.info(I18nUtils.getMessage((Enum<?>)logId, args));
	}
	@Override
	public void info(MessageId messageId, Object... args) {
		logger.info(I18nUtils.getMessage((Enum<?>)messageId, args));
	}
	@Override
	public void info(Throwable t, String message, Object... args) {
		if(args == null || args.length == 0) {
			logger.info(message, t);
		} else {
			logger.info(message, ArrayUtils.add(args, t));
		}
	}
	@Override
	public void info(Throwable t, Enum<?> e, Object... args) {
		logger.info(I18nUtils.getMessage(e, args), t);
	}
	@Override
	public void info(Throwable t, E logId, Object... args) {
		logger.info(I18nUtils.getMessage((Enum<?>)logId, args), t);
	}
	@Override
	public void info(Throwable t, MessageId messageId, Object... args) {
		logger.info(I18nUtils.getMessage((Enum<?>)messageId, args), t);
	}

	@Override
	public boolean isWarnEnabled() {
		return logger.isWarnEnabled();
	}
	@Override
	public void warn(String message, Object... args) {
		if(args == null || args.length == 0) {
			logger.warn(message);
		} else {
			logger.warn(message, args);
		}
	}
	@Override
	public void warn(Enum<?> e, Object... args) {
		logger.warn(I18nUtils.getMessage(e, args));
	}
	@Override
	public void warn(E logId, Object... args) {
		logger.warn(I18nUtils.getMessage((Enum<?>)logId, args));
	}
	@Override
	public void warn(MessageId messageId, Object... args) {
		logger.warn(I18nUtils.getMessage((Enum<?>)messageId, args));
	}
	@Override
	public void warn(Throwable t, String message, Object... args) {
		if(args == null || args.length == 0) {
			logger.warn(message, t);
		} else {
			logger.warn(message, ArrayUtils.add(args, t));
		}
	}
	@Override
	public void warn(Throwable t, Enum<?> e, Object... args) {
		logger.warn(I18nUtils.getMessage(e, args), t);
	}
	@Override
	public void warn(Throwable t, E logId, Object... args) {
		logger.warn(I18nUtils.getMessage((Enum<?>)logId, args), t);
	}
	@Override
	public void warn(Throwable t, MessageId messageId, Object... args) {
		logger.warn(I18nUtils.getMessage((Enum<?>)messageId, args), t);
	}

	@Override
	public boolean isErrorEnabled() {
		return logger.isErrorEnabled();
	}
	@Override
	public void error(String message, Object... args) {
		if(args == null || args.length == 0) {
			logger.error(message);
		} else {
			logger.error(message, args);
		}
	}
	@Override
	public void error(Enum<?> e, Object... args) {
		logger.error(I18nUtils.getMessage(e, args));
	}
	@Override
	public void error(E logId, Object... args) {
		logger.error(I18nUtils.getMessage((Enum<?>)logId, args));
	}
	@Override
	public void error(MessageId messageId, Object... args) {
		logger.error(I18nUtils.getMessage((Enum<?>)messageId, args));
	}
	@Override
	public void error(Throwable t, String message, Object... args) {
		if(args == null || args.length == 0) {
			logger.error(message, t);
		} else {
			logger.error(message, ArrayUtils.add(args, t));
		}
	}
	@Override
	public void error(Throwable t, Enum<?> e, Object... args) {
		logger.error(I18nUtils.getMessage(e, args), t);
	}
	@Override
	public void error(Throwable t, E logId, Object... args) {
		logger.error(I18nUtils.getMessage((Enum<?>)logId, args), t);
	}
	@Override
	public void error(Throwable t, MessageId messageId, Object... args) {
		logger.error(I18nUtils.getMessage((Enum<?>)messageId, args), t);
	}
}
