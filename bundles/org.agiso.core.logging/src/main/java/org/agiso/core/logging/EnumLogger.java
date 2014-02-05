/* org.agiso.core.logging.EnumLogger (2013-06-04)
 * 
 * EnumLogger.java
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
 * 
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public interface EnumLogger extends LoggerInformer {
	public void trace(Enum<?> e, Object... args);
	public void trace(Throwable t, Enum<?> e, Object... args);

	public void debug(Enum<?> e, Object... args);
	public void debug(Throwable t, Enum<?> e, Object... args);

	public void info(Enum<?> e, Object... args);
	public void info(Throwable t, Enum<?> e, Object... args);

	public void warn(Enum<?> e, Object... args);
	public void warn(Throwable t, Enum<?> e, Object... args);

	public void error(Enum<?> e, Object... args);
	public void error(Throwable t, Enum<?> e, Object... args);
}
