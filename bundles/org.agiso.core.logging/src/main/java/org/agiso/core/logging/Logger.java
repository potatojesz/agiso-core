/* org.agiso.core.logging.Logger (04-06-2013)
 * 
 * Logger.java
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

/**
 * Interfejs określający metody wykorzystywane do logowania.
 * 
 * @author <a href="mailto:kkopacz@agiso.org">Karol Kopacz</a>
 * @since 1.0
 */
public interface Logger extends LoggerInformer {
	public void trace(String message, Object... args);
	public void trace(Throwable t, String message, Object... args);

	public void debug(String message, Object... args);
	public void debug(Throwable t, String message, Object... args);

	public void info(String message, Object... args);
	public void info(Throwable t, String message, Object... args);

	public void warn(String message, Object... args);
	public void warn(Throwable t, String message, Object... args);

	public void error(String message, Object... args);
	public void error(Throwable t, String message, Object... args);
}
