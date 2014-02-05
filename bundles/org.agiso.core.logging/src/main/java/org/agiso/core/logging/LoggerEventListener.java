/* org.agiso.core.logging.LoggerEventListener (2011-12-23)
 * 
 * LoggerEventListener.java
 * 
 * Copyright 2011 agiso.org.
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
 * Interfejs słuchacza zdarzeń loggera.
 * <br \>
 * Instancje klas implementujących interfejs słuchacza zdarzeń loggera mogą
 * być rejestrowane jako odbiorcy powiadomień o wystąpieniu logów.
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public interface LoggerEventListener {
	public void log(LoggerEvent event);
}
