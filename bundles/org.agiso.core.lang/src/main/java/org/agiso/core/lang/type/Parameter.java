/* org.agiso.core.lang.type.Parameter (2009-01-09)
 * 
 * Parameter.java
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
package org.agiso.core.lang.type;

import java.io.Serializable;
import java.util.Map;

/**
 * Klasa nienazwanego parametru (który może być nazywany przez powiązanie z
 * kluczem np. w obiekcie {@link Map}) określonego typu, którego wartość może
 * być modyfikowana.
 * 
 * @author <a href="mailto:kkopacz@agiso.org">Karol Kopacz</a>
 * @since 1.0
 */
public class Parameter<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Wartość parametru */
	private T value;

//	--------------------------------------------------------------------------
	/**
	 * Tworzy nowy nienazwany parametr o określonej wartości.
	 * 
	 * @param value Wartość początkowa tworzonego parametru. 
	 */
	public Parameter(T value) {
		this.value = value;
	}

//	--------------------------------------------------------------------------
	/**
	 * Pobiera aktualną wartość parametru.
	 * 
	 * @return Bieżąca wartość parametru.
	 */
	public T getValue() {
		return value;
	}
	/**
	 * Ustawia nową wartość parametru.
	 * 
	 * @param value Nowa wartość parametru.
	 */
	public void setValue(T value) {
		this.value = value;
	}
}
