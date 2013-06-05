/* org.agiso.core.logging.slf4j.SLF4JCallerLocationAwareLogger.java (2009-02-10)
 *
 * SLF4JCallerLocationAwareLogger.java.java
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
import org.slf4j.spi.LocationAwareLogger;

/**
 * Implementacja interfejsu {@link Logger} dostarczająca mechanizmów logowania
 * za pośrednictwem loggera SLF4J niezależnego od lokacji klasy określonej jako
 * wywołująca (poprzez parametr <code>caller</code> wywołania konstruktora),
 * tzn. pozwalającego na wyświetlanie poprawnych informacji o lokalizacji loga
 * (nazwa metody, nr linii, etc.).
 * 
 * @author <a href="mailto:kkopacz@agiso.org">Karol Kopacz</a>
 * @since 1.0
 */
class SLF4JCallerLocationAwareLogger<T, E extends I18nId> extends SLF4JLogger<T, LocationAwareLogger, E> {
	private String fqcn = SLF4JLocationAwareLogger.class.getName();

//	--------------------------------------------------------------------------
	SLF4JCallerLocationAwareLogger(LocationAwareLogger logger, Class<?> caller) {
		super(logger);

		this.fqcn = caller.getName();
	}

//	--------------------------------------------------------------------------
	@Override
	public void trace(String message, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.TRACE_INT, String.valueOf(message), args, null);
	}
	@Override
	public void trace(Enum<?> e, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.TRACE_INT, I18nUtils.getMessage(e, args), null, null);
	}
	@Override
	public void trace(E logId, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.TRACE_INT, I18nUtils.getMessage((Enum<?>)logId, args), null, null);
	}
	@Override
	public void trace(MessageId messageId, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.TRACE_INT, I18nUtils.getMessage((Enum<?>)messageId, args), null, null);
	}
	@Override
	public void trace(Throwable t, String message, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.TRACE_INT, String.valueOf(message), args, t);
	}
	@Override
	public void trace(Throwable t, Enum<?> e, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.TRACE_INT, I18nUtils.getMessage(e, args), null, t);
	}
	@Override
	public void trace(Throwable t, E logId, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.TRACE_INT, I18nUtils.getMessage((Enum<?>)logId, args), null, t);
	}
	@Override
	public void trace(Throwable t, MessageId messageId, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.TRACE_INT, I18nUtils.getMessage((Enum<?>)messageId, args), null, t);
	}

	@Override
	public void info(String message, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.INFO_INT, String.valueOf(message), args, null);
	}
	@Override
	public void info(Enum<?> e, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.INFO_INT, I18nUtils.getMessage(e, args), null, null);
	}
	@Override
	public void info(E logId, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.INFO_INT, I18nUtils.getMessage((Enum<?>)logId, args), null, null);
	}
	@Override
	public void info(MessageId messageId, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.INFO_INT, I18nUtils.getMessage((Enum<?>)messageId, args), null, null);
	}
	@Override
	public void info(Throwable t, String message, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.INFO_INT, String.valueOf(message), args, t);
	}
	@Override
	public void info(Throwable t, Enum<?> e, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.INFO_INT, I18nUtils.getMessage(e, args), null, t);
	}
	@Override
	public void info(Throwable t, E logId, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.INFO_INT, I18nUtils.getMessage((Enum<?>)logId, args), null, t);
	}
	@Override
	public void info(Throwable t, MessageId messageId, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.INFO_INT, I18nUtils.getMessage((Enum<?>)messageId, args), null, t);
	}

	@Override
	public void debug(String message, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.DEBUG_INT, String.valueOf(message), args, null);
	}
	@Override
	public void debug(Enum<?> e, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.DEBUG_INT, I18nUtils.getMessage(e, args), null, null);
	}
	@Override
	public void debug(E logId, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.DEBUG_INT, I18nUtils.getMessage((Enum<?>)logId, args), null, null);
	}
	@Override
	public void debug(MessageId messageId, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.DEBUG_INT, I18nUtils.getMessage((Enum<?>)messageId, args), null, null);
	}
	@Override
	public void debug(Throwable t, String message, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.DEBUG_INT, String.valueOf(message), args, t);
	}
	@Override
	public void debug(Throwable t, Enum<?> e, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.DEBUG_INT, I18nUtils.getMessage(e, args), null, t);
	}
	@Override
	public void debug(Throwable t, E logId, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.DEBUG_INT, I18nUtils.getMessage((Enum<?>)logId, args), null, t);
	}
	@Override
	public void debug(Throwable t, MessageId messageId, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.DEBUG_INT, I18nUtils.getMessage((Enum<?>)messageId, args), null, t);
	}

	@Override
	public void warn(String message, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.WARN_INT, String.valueOf(message), args, null);
	}
	@Override
	public void warn(Enum<?> e, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.WARN_INT, I18nUtils.getMessage(e, args), null, null);
	}
	@Override
	public void warn(E logId, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.WARN_INT, I18nUtils.getMessage((Enum<?>)logId, args), null, null);
	}
	@Override
	public void warn(MessageId messageId, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.WARN_INT, I18nUtils.getMessage((Enum<?>)messageId, args), null, null);
	}
	@Override
	public void warn(Throwable t, String message, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.WARN_INT, String.valueOf(message), args, t);
	}
	@Override
	public void warn(Throwable t, Enum<?> e, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.WARN_INT, I18nUtils.getMessage(e, args), null, t);
	}
	@Override
	public void warn(Throwable t, E logId, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.WARN_INT, I18nUtils.getMessage((Enum<?>)logId, args), null, t);
	}
	@Override
	public void warn(Throwable t, MessageId messageId, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.WARN_INT, I18nUtils.getMessage((Enum<?>)messageId, args), null, t);
	}

	@Override
	public void error(String message, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.ERROR_INT, String.valueOf(message), args, null);
	}
	@Override
	public void error(Enum<?> e, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.ERROR_INT, I18nUtils.getMessage(e, args), null, null);
	}
	@Override
	public void error(E logId, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.ERROR_INT, I18nUtils.getMessage((Enum<?>)logId, args), null, null);
	}
	@Override
	public void error(MessageId messageId, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.ERROR_INT, I18nUtils.getMessage((Enum<?>)messageId, args), null, null);
	}
	@Override
	public void error(Throwable t, String message, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.ERROR_INT, String.valueOf(message), args, t);
	}
	@Override
	public void error(Throwable t, Enum<?> e, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.ERROR_INT, I18nUtils.getMessage(e, args), null, t);
	}
	@Override
	public void error(Throwable t, E logId, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.ERROR_INT, I18nUtils.getMessage((Enum<?>)logId, args), null, t);
	}
	@Override
	public void error(Throwable t, MessageId messageId, Object... args) {
		logger.log(null, fqcn, LocationAwareLogger.ERROR_INT, I18nUtils.getMessage((Enum<?>)messageId, args), null, t);
	}
}
