/* org.agiso.core.lang.util.ThreadUtils (2009-01-09)
 * 
 * ThreadUtils.java
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
package org.agiso.core.lang.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.agiso.core.lang.type.Parameter;

/**
 * Klasa dostarczajaca metod narzędziowych związanych z wątkami. Pozwala na:
 * <ul>
 *     <li>wyznaczanie unikatowego identyfikatora wątku,</li>
 *     <li>przechowywanie atrybutów w zasięgu wątku pod określnymi kluczami.</li>
 * </ul>
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public abstract class ThreadUtils {
	private static final AtomicLong uniqueId;
	private static final ThreadLocal<Long> uniqueNum;

	private static final ThreadLocal<Map<String, Object>> localAttributeHolder;

	private static final ThreadLocal<Parameter<Boolean>> hasInheritedAttributes;
	private static final ThreadLocal<Map<String, Object>> inheritableAttributeHolder;

//	--------------------------------------------------------------------------
	static {
		uniqueId = new AtomicLong(0);
		uniqueNum = new ThreadLocal<Long>() {
			@Override
			protected Long initialValue() {
				return uniqueId.getAndIncrement();
			}
		};

		localAttributeHolder = new ThreadLocal<Map<String, Object>>() {
			@Override
			protected Map<String, Object> initialValue() {
				return new HashMap<String, Object>();
			}
		};

		hasInheritedAttributes = new InheritableThreadLocal<Parameter<Boolean>>() {
			@Override
			protected Parameter<Boolean> initialValue() {
				return new Parameter<Boolean>(false);
			}
			@Override
			protected Parameter<Boolean> childValue(Parameter<Boolean> parentValue) {
				return new Parameter<Boolean>(!inheritableAttributeHolder.get().isEmpty());
			}
		};
		inheritableAttributeHolder = new InheritableThreadLocal<Map<String, Object>>() {
			@Override
			protected Map<String, Object> initialValue() {
				return new HashMap<String, Object>();
			}
			@Override
			protected Map<String, Object> childValue(Map<String, Object> parentAttributes) {
				return parentAttributes.isEmpty()? initialValue() : parentAttributes;
			}
		};
	}

//	--------------------------------------------------------------------------
	/**
	 * Zwraca unikatowy identyfikator wątku. Identyfiator generowany jest podczas
	 * pierwszego wywołania metody <code>ThreadUtils.getUniqueThreadId()</code>.
	 * Jeśli dla jakiegoś wątku metoda nie jest wywołana, to nie jest dla niego
	 * tworzony identyfikator.
	 * 
	 * @return
	 */
	public static long getUniqueThreadId() {
		return uniqueNum.get();
	}

	/**
	 * Umieszcza wartość atrybutu pod określonym kluczem w lokalnym zasięgu wątku.
	 * 
	 * @param key Klucz, pod którym atrybut zostanie umieszczony
	 * @param attribute Wartość atrybutu do umieszczenia w lokalnym zasięgu wątku
	 * @return poprzednia wartość atrybutu powiązanego z klluczem, lub <code>null
	 *     </code> jeśli atrybut nie został ustawiony (albo posiadał wartość <code>
	 *     null</code>).
	 */
	public static Object putAttribute(String key, Object attribute) {
		return putAttribute(key, attribute, false);
	}

	/**
	 * Umieszcza wartość atrybutu pod określonym kluczem w zasięgu wątku określonym
	 * parametrem wywołania <code>inheritable</code>.
	 * 
	 * @param key Klucz, pod którym atrybut zostanie umieszczony
	 * @param attribute Wartość atrybutu do umieszczenia w zasięgu wątku
	 * @param inheritable Wartość logiczna określająca, czy atrybut ma być
	 *     dziedziczony przez wąki dzieci, czy nie, jeśli ma wartość:
	 *     <ul>
	 *         <li><code>false</code> - jest atrybutem lokalnym dla bieżącego
	 *             wątku i nie jest widoczny przez jego dzieci,</li>
	 *         <li><code>true</code> - jest atrybutem dziedzicznym bieżącego
	 *             wątku i jest widoczny przez jego dzieci.</li>
	 *     </ul>
	 * @return
	 */
	public static Object putAttribute(String key, Object attribute, boolean inheritable) {
		if(inheritable) {
			Object value = inheritableAttributeHolder.get().put(key, attribute);
			if(value == null) {
				value = localAttributeHolder.get().remove(key);
			} else {
				localAttributeHolder.get().remove(key);
			}
			return value;
		} else {
			Object value = localAttributeHolder.get().put(key, attribute);
			if(value == null) {
				value = inheritableAttributeHolder.get().remove(key);
			} else {
				inheritableAttributeHolder.get().remove(key);
			}
			return value;
		}
	}

	/**
	 * Sprawdza, czy pod określonym kluczem został umieszczony atrybut.
	 * 
	 * @param key Klucz, dla którego sprawdzamy obecność atrybutu
	 * @return <code>true</code> jeśli pod określonym kluczem znajduje się
	 *     atrybut (może on mieć wartość <code>null</code>).
	 */
	public static boolean hasAttribute(String key) {
		if(localAttributeHolder.get().containsKey(key)) {
			return true;
		}
		return inheritableAttributeHolder.get().containsKey(key);
	}

//	TODO
//	public static boolean isInheritedAttribute(String key) {
//		
//	}

	public static boolean hasInheritedAttributes() {
		return hasInheritedAttributes.get().getValue();
	}

	/**
	 * Pobiera wartość atrybutu umieszczonego pod określonym kluczem.
	 * 
	 * @param key Klucz, spod którego atrybut ma zostać odczytany
	 * @return Wartość atrybutu spod określonego klucza lub <code>null</code>
	 *     jeśli atrybutu nie było, bądź miał wartość <code>null</code>
	 */
	public static Object getAttribute(String key) {
		Object value = localAttributeHolder.get().get(key);
		if(value == null) {
			value = inheritableAttributeHolder.get().get(key);
		}
		return value;
	}

	/**
	 * Usuwa wartość atrybutu umieszczonego pod określonym kluczem.
	 * 
	 * @param key Klucz, spod którego atrybut ma zostać usunięty
	 * @return Wartość atrybutu spod określonego klucza lub <code>null</code>
	 *     jeśli atrybutu nie było, bądź miał wartość <code>null</code>
	 */
	public static Object removeAttribute(String key) {
		Object value = localAttributeHolder.get().remove(key);
		if(value == null) {
			value = inheritableAttributeHolder.get().remove(key);
		} else {
			inheritableAttributeHolder.get().remove(key);
		}
		return value;
	}

	/**
	 * Czyści wszystkie atrybuty wątku, zarówno lokalne jak i dziedziczne.
	 * 
	 * TODO: Co się dzieje z atrybutami dziedziczonymi?
	 */
	public static void clearAttributes() {
		localAttributeHolder.get().clear();
		inheritableAttributeHolder.get().clear();
	}
}
