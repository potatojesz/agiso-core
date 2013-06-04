/* org.agiso.core.logging.slf4j.SLF4JLoggerFactory (2009-02-10)
 *
 * SLF4JLoggerFactory.java
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

import org.slf4j.LoggerFactory;
import org.slf4j.spi.LocationAwareLogger;

/**
 * Fabryka pozwalająca na pozyskanie loggera SLF4J.
 * 
 * @author <a href="mailto:kkopacz@agiso.org">Karol Kopacz</a>
 * @since 1.0
 */
public class SLF4JLoggerFactory {
	/**
	 * Pobiera loggera SLF4J dla określonej klasy.
	 */
	public static <T> SLF4JLogger<T, ?> getLogger(Class<T> clazz) {
		org.slf4j.Logger logger = LoggerFactory.getLogger(clazz);
		if(logger instanceof LocationAwareLogger) {
			return new SLF4JLocationAwareLogger<T>((LocationAwareLogger)logger);
		} else {
			return new SLF4JLogger<T, org.slf4j.Logger>(logger);
		}
	}

	/**
	 * Pobiera loggera SLF4J niezależnego od lokalizacji dla określonej klasy.
	 */
	public static <T> SLF4JLogger<T, ?> getLogger(Class<T> clazz, Class<?> caller) {
		org.slf4j.Logger logger = LoggerFactory.getLogger(clazz);
		if(logger instanceof LocationAwareLogger) {
			return new SLF4JCallerLocationAwareLogger<T>((LocationAwareLogger)logger, caller);
		} else {
			return new SLF4JLogger<T, org.slf4j.Logger>(logger);
		}
	}
}
