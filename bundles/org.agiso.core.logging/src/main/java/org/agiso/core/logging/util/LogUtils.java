/* org.agiso.core.logging.util.LogUtils (2009-02-10)
 *
 * LogUtils.java
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
package org.agiso.core.logging.util;

import org.agiso.core.logging.LoggerInformer;
import org.agiso.core.logging.slf4j.SLF4JLoggerFactory;

/**
 * Klasa narzędziowa pozwalająca na pozyskiwanie loggera typu {@link Logger}
 * wykorzystywanego do wysyłania informacji do logów aplikacji.
 * 
 * @author <a href="mailto:kkopacz@agiso.org">Karol Kopacz</a>
 * @since 1.0
 */
public abstract class LogUtils {
	/**
	 * Zwraca logger dla określonej klasy będący implementacją interfejsu
	 * {@link Logger}.
	 * 
	 * @param <T> Parametr typu klasy loggera.
	 * @param clazz Typ klasy dla której zwracany jest logger.
	 * @return Logger dla określonej klasy.
	 */
	@SuppressWarnings("unchecked")
	public static <T, L extends LoggerInformer> L getLogger(Class<T> clazz) {
		return (L)SLF4JLoggerFactory.getLogger(clazz);
	}

	/**
	 * Zwraca niezależny od lokacji logger dla określonej klasy będący
	 * implementacją interfejsu {@link Logger}.<br/>
	 * Niezależność od lokacji oznacza, że klasa dla której pobierany jest
	 * logger nie będzie uwzględniana podczas wyznaczania informacji
	 * lokalizacyjnej opisującej wystąpienie zdarzenia logowania.<br/>
	 * Loggery niezależne od lokacji mogą być wykorzystywane w klasach
	 * narzędziowych, które odpowiadają np. za wyświetlanie błędów.
	 * 
	 * @param <T> Parametr typu klasy loggera.
	 * @param clazz Typ klasy dla której zwracany jest logger. Jednocześnie
	 *     jest to klasa, która nie będzie uwzględniana w wyznaczaniu lokacji.
	 * @return Logger dla określonej klasy.
	 */
	@SuppressWarnings("unchecked")
	public static <T, L extends LoggerInformer> L getLocationAwareLogger(Class<T> clazz) {
		return (L)SLF4JLoggerFactory.getLogger(clazz, clazz);
	}

	/**
	 * @see #getLocationAwareLogger(Class)
	 * 
	 * @param <T> Parametr typu klasy loggera.
	 * @param clazz Typ klasy dla której zwracany jest logger.
	 * @param caller Klasa, która nie będzie uwzględniana w wyznaczaniu lokacji
	 * @return Logger dla określonej klasy.
	 */
	@SuppressWarnings("unchecked")
	public static <T, L extends LoggerInformer> L getLocationAwareLogger(Class<T> clazz, Class<?> caller) {
		return (L)SLF4JLoggerFactory.getLogger(clazz, caller);
	}
}
