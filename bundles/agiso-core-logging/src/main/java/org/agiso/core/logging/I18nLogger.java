/* org.agiso.core.logging.I18nLogger (2013-06-04)
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
 * @author Karol Kopacz
 * @since 1.0
 */
public interface I18nLogger<E extends I18nId> extends LoggerInformer {
	public void trace(E i18nId, Object... args);
	public void trace(Throwable t, E i18nId, Object... args);

	public void debug(E i18nId, Object... args);
	public void debug(Throwable t, E i18nId, Object... args);

	public void info(E i18nId, Object... args);
	public void info(Throwable t, E i18nId, Object... args);

	public void warn(E i18nId, Object... args);
	public void warn(Throwable t, E i18nId, Object... args);

	public void error(E i18nId, Object... args);
	public void error(Throwable t, E i18nId, Object... args);
}
