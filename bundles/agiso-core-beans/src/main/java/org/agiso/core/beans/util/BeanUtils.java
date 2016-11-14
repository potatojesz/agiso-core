/* org.agiso.core.beans.util.BeanUtils (2009-02-12)
 * 
 * BeanUtils.java
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
package org.agiso.core.beans.util;

import static java.lang.System.*;

import org.agiso.core.beans.exception.BeanRetrievalException;
import org.agiso.core.beans.factory.IBeanFactory;

/**
 * Klasa narzędziowa pozwalająca na pozyskiwanie obiektów z kontekstu aplikacji.
 * <br/>
 * Do poprawnego działania klasa wymaga wywołania metody {@link #setBeanFactory(
 * IBeanFactory)}, za pomocą której wskazywana jest instancja interfejsu {@link
 * IBeanFactory} dostarczająca właściwych metod pobierających obiekty z kontekstu.
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public abstract class BeanUtils {
	private static volatile IBeanFactory beanFactory;

//	--------------------------------------------------------------------------
	/**
	 * Ustawia fabrykę obiektów wykorzystywaną do ich pozyskiwania.</br>
	 * 
	 * Fabryka obiektów powinna być ustawiona przed pierwszym użyciem klasy,
	 * chyba że używane są metody getBean z określonym czasem oczekiwania na
	 * ustawienie fabryki obiektów. W takiej sytuacji ustawianie fabryki
	 * i pobieranie bean'a z czasem oczekiwania musi się odbywać w różnych
	 * wątkach.
	 * 
	 * @param beanFactory Fabryka obiektów.
	 */
	public static void setBeanFactory(IBeanFactory beanFactory) {
		BeanUtils.beanFactory = beanFactory;
	}

	public static boolean isInitialized() {
		return BeanUtils.beanFactory != null;
	}

//	--------------------------------------------------------------------------
	/**
	 * Pobiera z kontekstu aplikacji obiekt o wskazanej nazwie.
	 * 
	 * @param name Nazwa jednoznacznie identyfikująca obiekt.
	 * @return Obiekt o wskazanej nazwie lub <code>null</code> jeśli nie znaleziono.
	 * @throws BeanRetrievalException jeśli nie została ustawiona fabryka obiektów
	 *     lub nie udało się pozyskać żądanego obiektu.
	 */
	public static Object getBean(String name) {
		checkBeanFactory(0, name);

		return beanFactory.getBean(name);
	}
	/**
	 * Pobiera z kontekstu aplikacji obiekt o wskazanej nazwie w razie
	 * potrzeby wstrzymując bieżący wątek w oczekiwaniu na inicjalizację fabryki
	 * obiektów (poprzez wywołanie metody {@link #setBeanFactory(IBeanFactory)}).
	 * 
	 * @param name Nazwa jednoznacznie identyfikująca obiekt.
	 * @param waitTime Maksymalny czas oczekiwania na inicjalizację fabryki
	 *     obiektów (w sekundach). Wartość ujemna oznacza czas nieograniczony.
	 * @return Obiekt o wskazanej nazwie lub <code>null</code> jeśli nie znaleziono.
	 * @throws BeanRetrievalException jeśli nie została ustawiona fabryka obiektów
	 *     lub nie udało się pozyskać żądanego obiektu.
	 */
	public static Object getBean(String name, long waitTime) {
		checkBeanFactory(waitTime, name);

		return beanFactory.getBean(name);
	}

	/**
	 * Pobiera z kontekstu aplikacji obiekt o wskazanym typie.
	 * 
	 * @param type Typ obiektu do pobrania z kontekstu aplikacji.
	 * @return Obiekt o wskazanym typie lub <code>null</code> jeśli nie znaleziono.
	 * @throws BeanRetrievalException jeśli nie została ustawiona fabryka obiektów
	 *     lub nie udało się pozyskać żądanego obiektu.
	 */
	public static <T> T getBean(Class<T> type) {
		checkBeanFactory(0, type.getName());

		return beanFactory.getBean(type);
	}
	/**
	 * Pobiera z kontekstu aplikacji obiekt o wskazanym typie w razie
	 * potrzeby wstrzymując bieżący wątek w oczekiwaniu na inicjalizację fabryki
	 * obiektów (poprzez wywołanie metody {@link #setBeanFactory(IBeanFactory)}).
	 * 
	 * @param type Typ obiektu do pobrania z kontekstu aplikacji.
	 * @param waitTime Maksymalny czas oczekiwania na inicjalizację fabryki
	 *     obiektów (w sekundach). Wartość ujemna oznacza czas nieograniczony.
	 * @return Obiekt o wskazanym typie lub <code>null</code> jeśli nie znaleziono.
	 * @throws BeanRetrievalException jeśli nie została ustawiona fabryka obiektów
	 *     lub nie udało się pozyskać żądanego obiektu.
	 */
	public static <T> T getBean(Class<T> type, long waitTime) {
		checkBeanFactory(waitTime, type.getName());

		return beanFactory.getBean(type);
	}

	/**
	 * Pobiera z kontekstu aplikacji obiekt o wskazanej nazwie i typie.
	 * 
	 * @param name Nazwa jednoznacznie identyfikująca obiekt.
	 * @param type Typ obiektu do pobrania z kontekstu aplikacji.
	 * @return Obiekt o wskazanym typie lub <code>null</code> jeśli nie znaleziono.
	 * @throws BeanRetrievalException jeśli nie została ustawiona fabryka obiektów
	 *     lub nie udało się pozyskać żądanego obiektu.
	 */
	public static <T> T getBean(String name, Class<T> type) {
		checkBeanFactory(0, name + " of type " + type.getName());

		return beanFactory.getBean(name, type);
	}
	/**
	 * Pobiera z kontekstu aplikacji obiekt o wskazanej nazwie i typie w razie
	 * potrzeby wstrzymując bieżący wątek w oczekiwaniu na inicjalizację fabryki
	 * obiektów (poprzez wywołanie metody {@link #setBeanFactory(IBeanFactory)}).
	 * 
	 * @param name Nazwa jednoznacznie identyfikująca obiekt.
	 * @param type Typ obiektu do pobrania z kontekstu aplikacji.
	 * @param waitTime Maksymalny czas oczekiwania na inicjalizację fabryki
	 *     obiektów (w sekundach). Wartość ujemna oznacza czas nieograniczony.
	 * @return Obiekt o wskazanym typie lub <code>null</code> jeśli nie znaleziono.
	 * @throws BeanRetrievalException jeśli nie została ustawiona fabryka obiektów
	 *     lub nie udało się pozyskać żądanego obiektu.
	 */
	public static <T> T getBean(String name, Class<T> type, long waitTime) {
		checkBeanFactory(waitTime, name + " of type " + type.getName());

		return beanFactory.getBean(name, type);
	}

//	--------------------------------------------------------------------------
	private static void checkBeanFactory(long waitTime, String bean) {
		if(beanFactory != null) {
			return;
		}

		// Jeśli czas oczekiwania jest określony, to wstrzymujemy bieżący wątek
		// do momentu ustawienia fabryki bean'ów. Dla ujemnej wartości czasu
		// oczekiwania - do skutku. Jeśli wartość jest dodatnia - aż upłynie
		// zdana ilość sekund:
		if(waitTime != 0) {
			long endTime = currentTimeMillis() + (waitTime * 1000);
			while(beanFactory == null && (waitTime < 0 || currentTimeMillis() < endTime)) {
				try {
					Thread.sleep(1000);
				} catch(InterruptedException e) {
				}
			}
		}

		if(beanFactory == null) {
			throw new BeanRetrievalException(bean,
					"Bean utils not initialized (bean factory not set)");
		}
	}
}
