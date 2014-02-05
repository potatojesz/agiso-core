/* org.agiso.core.logging.slf4j.SLF4JLocationAwareLogger (2009-02-10)
 * 
 * SLF4JLocationAwareLogger.java
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
 * za pośrednictwem loggera SLF4J niezależnego od lokacji, tzn. pozwalającego
 * na wyświetlanie poprawnych informacji o lokalizacji loga (nazwa metody, nr
 * linii, etc.).
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
class SLF4JLocationAwareLogger<T, E extends I18nId> extends SLF4JLogger<T, LocationAwareLogger, E> {
	private static final String FQCN = SLF4JLocationAwareLogger.class.getName();

//	--------------------------------------------------------------------------
	SLF4JLocationAwareLogger(LocationAwareLogger logger) {
		super(logger);
	}

//	--------------------------------------------------------------------------
	@Override
	public void trace(String message, Object... args) {
		logger.log(null, FQCN, LocationAwareLogger.TRACE_INT, String.valueOf(message), args, null);
	}
	@Override
	public void trace(Enum<?> e, Object... args) {
		logger.log(null, FQCN, LocationAwareLogger.TRACE_INT, I18nUtils.getMessage(e, args), null, null);
	}
	@Override
	public void trace(E i18nId, Object... args) {
		logger.log(null, FQCN, LocationAwareLogger.TRACE_INT, I18nUtils.getMessage((Enum<?>)i18nId, args), null, null);
	}
	@Override
	public void trace(Throwable t, String message, Object... args) {
		logger.log(null, FQCN, LocationAwareLogger.TRACE_INT, String.valueOf(message), args, t);
	}
	@Override
	public void trace(Throwable t, Enum<?> e, Object... args) {
		logger.log(null, FQCN, LocationAwareLogger.TRACE_INT, I18nUtils.getMessage(e, args), null, t);
	}
	@Override
	public void trace(Throwable t, E i18nId, Object... args) {
		logger.log(null, FQCN, LocationAwareLogger.TRACE_INT, I18nUtils.getMessage((Enum<?>)i18nId, args), null, t);
	}

	@Override
	public void info(String message, Object... args) {
		logger.log(null, FQCN, LocationAwareLogger.INFO_INT, String.valueOf(message), args, null);
	}
	@Override
	public void info(Enum<?> e, Object... args) {
		logger.log(null, FQCN, LocationAwareLogger.INFO_INT, I18nUtils.getMessage(e, args), null, null);
	}
	@Override
	public void info(E i18nId, Object... args) {
		logger.log(null, FQCN, LocationAwareLogger.INFO_INT, I18nUtils.getMessage((Enum<?>)i18nId, args), null, null);
	}
	@Override
	public void info(Throwable t, String message, Object... args) {
		logger.log(null, FQCN, LocationAwareLogger.INFO_INT, String.valueOf(message), args, t);
	}
	@Override
	public void info(Throwable t, Enum<?> e, Object... args) {
		logger.log(null, FQCN, LocationAwareLogger.INFO_INT, I18nUtils.getMessage(e, args), null, t);
	}
	@Override
	public void info(Throwable t, E i18nId, Object... args) {
		logger.log(null, FQCN, LocationAwareLogger.INFO_INT, I18nUtils.getMessage((Enum<?>)i18nId, args), null, t);
	}

	@Override
	public void debug(String message, Object... args) {
		logger.log(null, FQCN, LocationAwareLogger.DEBUG_INT, String.valueOf(message), args, null);
	}
	@Override
	public void debug(Enum<?> e, Object... args) {
		logger.log(null, FQCN, LocationAwareLogger.DEBUG_INT, I18nUtils.getMessage(e, args), null, null);
	}
	@Override
	public void debug(E i18nId, Object... args) {
		logger.log(null, FQCN, LocationAwareLogger.DEBUG_INT, I18nUtils.getMessage((Enum<?>)i18nId, args), null, null);
	}
	@Override
	public void debug(Throwable t, String message, Object... args) {
		logger.log(null, FQCN, LocationAwareLogger.DEBUG_INT, String.valueOf(message), args, t);
	}
	@Override
	public void debug(Throwable t, Enum<?> e, Object... args) {
		logger.log(null, FQCN, LocationAwareLogger.DEBUG_INT, I18nUtils.getMessage(e, args), null, t);
	}
	@Override
	public void debug(Throwable t, E i18nId, Object... args) {
		logger.log(null, FQCN, LocationAwareLogger.DEBUG_INT, I18nUtils.getMessage((Enum<?>)i18nId, args), null, t);
	}

	@Override
	public void warn(String message, Object... args) {
		logger.log(null, FQCN, LocationAwareLogger.WARN_INT, String.valueOf(message), args, null);
	}
	@Override
	public void warn(Enum<?> e, Object... args) {
		logger.log(null, FQCN, LocationAwareLogger.WARN_INT, I18nUtils.getMessage(e, args), null, null);
	}
	@Override
	public void warn(E i18nId, Object... args) {
		logger.log(null, FQCN, LocationAwareLogger.WARN_INT, I18nUtils.getMessage((Enum<?>)i18nId, args), null, null);
	}
	@Override
	public void warn(Throwable t, String message, Object... args) {
		logger.log(null, FQCN, LocationAwareLogger.WARN_INT, String.valueOf(message), args, t);
	}
	@Override
	public void warn(Throwable t, Enum<?> e, Object... args) {
		logger.log(null, FQCN, LocationAwareLogger.WARN_INT, I18nUtils.getMessage(e, args), null, t);
	}
	@Override
	public void warn(Throwable t, E i18nId, Object... args) {
		logger.log(null, FQCN, LocationAwareLogger.WARN_INT, I18nUtils.getMessage((Enum<?>)i18nId, args), null, t);
	}

	@Override
	public void error(String message, Object... args) {
		logger.log(null, FQCN, LocationAwareLogger.ERROR_INT, String.valueOf(message), args, null);
	}
	@Override
	public void error(Enum<?> e, Object... args) {
		logger.log(null, FQCN, LocationAwareLogger.ERROR_INT, I18nUtils.getMessage(e, args), null, null);
	}
	@Override
	public void error(E i18nId, Object... args) {
		logger.log(null, FQCN, LocationAwareLogger.ERROR_INT, I18nUtils.getMessage((Enum<?>)i18nId, args), null, null);
	}
	@Override
	public void error(Throwable t, String message, Object... args) {
		logger.log(null, FQCN, LocationAwareLogger.ERROR_INT, String.valueOf(message), args, t);
	}
	@Override
	public void error(Throwable t, Enum<?> e, Object... args) {
		logger.log(null, FQCN, LocationAwareLogger.ERROR_INT, I18nUtils.getMessage(e, args), null, t);
	}
	@Override
	public void error(Throwable t, E i18nId, Object... args) {
		logger.log(null, FQCN, LocationAwareLogger.ERROR_INT, I18nUtils.getMessage((Enum<?>)i18nId, args), null, t);
	}
}
