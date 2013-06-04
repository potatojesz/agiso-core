/* org.agiso.core.logging.I18nLogger (04-06-2013)
 * 
 * I18nLogger.java
 * 
 * Copyright 2013 agiso.org
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
package org.agiso.core.logging;

import org.agiso.core.i18n.util.I18nUtils.I18nId;

/**
 * Interfejs określający metody wykorzystywane do logowania
 * z wykorzystaniem logów wielojęzykowych (będących elementami
 * wyliczeń zestawów komunikatów {@link LogId}).
 * 
 * @author <a href="mailto:kkopacz@agiso.org">Karol Kopacz</a>
 * @since 1.0
 */
public interface I18nLogger extends LoggerInformer {
	public interface LogId extends I18nId {
	}

//	--------------------------------------------------------------------------
	public void trace(LogId logId, Object... args);
	public void trace(Throwable t, LogId logId, Object... args);

	public void debug(LogId logId, Object... args);
	public void debug(Throwable t, LogId logId, Object... args);

	public void info(LogId logId, Object... args);
	public void info(Throwable t, LogId logId, Object... args);

	public void warn(LogId logId, Object... args);
	public void warn(Throwable t, LogId logId, Object... args);

	public void error(LogId logId, Object... args);
	public void error(Throwable t, LogId logId, Object... args);
}
