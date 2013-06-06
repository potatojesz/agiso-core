/* org.agiso.core.beans.factory.IBeanFactory (2009-02-12)
 *
 * IBeanFactory.java
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
package org.agiso.core.beans.factory;

import org.agiso.core.beans.exception.BeanRetrievalException;

/**
 * Interfejs dla fabryki obiektów wykorzystywanej do ich pobierania z kontekstu
 * aplikacji.
 * 
 * @author <a href="mailto:kkopacz@agiso.org">Karol Kopacz</a>
 * @since 1.0
 */
public interface IBeanFactory {
	/**
	 * Pobiera z kontekstu apilkacji obiekt o wskazanej nazwie.
	 * 
	 * @param name Nazwa obiektu do pobrania.
	 * @return Pobrany obiekt.
	 * @throws BeanRetrievalException jeśli nie udało się pozyskać obiektu.
	 */
	public Object getBean(String name);

	/**
	 * Pobiera z kontekstu aplikacji obiekt o wskazanym typie.
	 * 
	 * @param type Typ obiektu do pobrania.
	 * @return Pobrany obiekt.
	 * @throws BeanRetrievalException jeśli nie udało się pozyskać obiektu.
	 */
	public <T> T getBean(Class<T> type);

	/**
	 * Pobiera z kontekstu aplikacji obiekt o wskazanej nazwie i typie.
	 * 
	 * @param name Nazwa obiektu do pobrania.
	 * @param type Typ obiektu do pobrania.
	 * @return Pobrany obiekt.
	 * @throws BeanRetrievalException jeśli nie udało się pozyskać obiektu.
	 */
	public <T> T getBean(String name, Class<T> type);
}
