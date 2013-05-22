/* org.agiso.core.lang.util.ObjectUtils (2008-11-05)
 * 
 * ObjectUtils.java
 * 
 * Copyright 2008 agiso.org.
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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.agiso.core.lang.annotation.InEquals;
import org.agiso.core.lang.annotation.InHashCode;
import org.agiso.core.lang.annotation.InToString;
import org.agiso.core.lang.exception.BaseRuntimeException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Klasa dostarczajaca metod narzędziowych pozwalających na generowanie
 * reprezentacji tekstowej obiektu, wyznaczanie jego sumy haszującej oraz
 * porównywanie obiektów.
 * </br>
 * Obiekty analizowane są za pomocą mechanizmu refleksji poprzez bezpośrednią
 * analizę pól klasy. Uwzglęniany jest stan pól prywatnych.
 * 
 * @author <a href="mailto:kkopacz@agiso.org">Karol Kopacz</a>
 * @since 1.0
 */
public final class ObjectUtils {
	private static final String TOSTRING_COLON   = ": ";
	private static final String TOSTRING_HYPHEN  = ", ";
	private static final String TOSTRING_PREFIX  = "[";
	private static final String TOSTRING_SUFFIX  = "]";
	private static final String TOSTRING_EMPTY   = "[null]";
	private static final String TOSTRING_REPEATE = "[...]";

	private static final String TOSTRING_TCBUFF = "_toStringTCBuff";
	private static final String TOSTRING_TCATTR = "_toStringTCAttr";

//	--------------------------------------------------------------------------
	/**
	 * Prywatny konstruktor uniemożliwiający instancjonowanie klasy.
	 */
	private ObjectUtils() {
		// Does nothing
	}

//	--------------------------------------------------------------------------
//	Wsparcie implementacji metod hashCode, equals i toString
//	--------------------------------------------------------------------------
	/**
	 * Generuje sumę haszującą obiektu wykorzystując mechamizmy refleksji.
	 * Podczas jej wyznaczania wykorzystuje opis dostarczany przez adnotację
	 * {@link InHashCode}. Ignoruje pola finalne oraz statyczne.
	 * 
	 * @see HashCodeBuilder#reflectionHashCode(Object, String[])
	 */
	public static int hashCodeBuilder(Object object) {
		Set<String> excludeFields = new HashSet<String>();
		for(Field field: object.getClass().getDeclaredFields()) {
			if(field.isAnnotationPresent(InHashCode.class)) {
				InHashCode inHashCode = field.getAnnotation(InHashCode.class);
				if(inHashCode.ignore()) {
					excludeFields.add(field.getName());
				}
			}
		}
		return HashCodeBuilder.reflectionHashCode(object, excludeFields);
	}

	/**
	 * Porównuje dwa obiekty wykorzystując mechamizmy refleksji. W trakcie
	 * porównywania wykorzystuje opis dostarczany przez adnotację {@link
	 * InEquals}. Ignoruje pola finalne oraz statyczne.
	 * 
	 * @see EqualsBuilder#reflectionEquals(Object, Object, String[])
	 */
	public static boolean equalsBuilder(Object object1, Object object2) {
		Set<String> excludeFields = new HashSet<String>();
		for(Field field: object1.getClass().getDeclaredFields()) {
			if(field.isAnnotationPresent(InEquals.class)) {
				InEquals inEquals = field.getAnnotation(InEquals.class);
				if(inEquals.ignore()) {
					excludeFields.add(field.getName());
				}
			}
		}
		return EqualsBuilder.reflectionEquals(object1, object2, excludeFields);
	}

	/**
	 * Generuje łańcuch opisujący obiekt wykorzystując mechanizmy refleksji.
	 * Podczas generacji łańcucha wykorzystuje opis dostarczany przez adnotację
	 * {@link InToString}. Ignoruje pola finalne oraz statyczne.
	 * 
	 * @param object Obiekt, którego opis ma zostać wygenerowany.
	 * @return Opis obiektu.
	 */
	public static String toStringBuilder(Object object) {
		if(ThreadUtils.hasAttribute(TOSTRING_TCBUFF)) {
			// Nastąpiło rekurencyjne wywołanie metody toStringBuidler(Object).
			// Pobieramy z wątku bufor zawierający generowany łańcuch i zachowujemy
			// go w zmiennej pomocniczej na czas tworzenia reprezentacji podobiektu:
			StringBuffer resultBuffer = (StringBuffer)ThreadUtils.getAttribute(TOSTRING_TCBUFF);

			StringBuffer localBuffer = new StringBuffer();
			ThreadUtils.putAttribute(TOSTRING_TCBUFF, localBuffer);

			toStringObject(object.getClass(), object);

			ThreadUtils.putAttribute(TOSTRING_TCBUFF, resultBuffer);

			localBuffer.append(TOSTRING_SUFFIX);
			return localBuffer.toString();
		} else try {
			// Generujemy reprezentację tekstową obiektu. Tworzymy bufor na tą
			// reprezentację i zbiór gromadzący informacje o obiektach już
			// przetworzonych (w celu uniknięcia cyklicznych wywołań):
			StringBuffer resultBuffer = new StringBuffer();
			ThreadUtils.putAttribute(TOSTRING_TCBUFF, resultBuffer);
			ThreadUtils.putAttribute(TOSTRING_TCATTR, new HashSet<String>());

			toStringObject(object.getClass(), object);

			resultBuffer.append(TOSTRING_SUFFIX);
			return resultBuffer.toString();
		} finally {
			ThreadUtils.removeAttribute(TOSTRING_TCBUFF);
			ThreadUtils.removeAttribute(TOSTRING_TCATTR);
		}
	}

	/**
	 * Metoda generująca reprezentację łańcuchową obiektu. Przegląda wszystkie
	 * pola obiektu i pobiera ich reprezentację łańcuchową. Na tej podstawie
	 * generuje łańcuch wynikowy.
	 * 
	 * @param clazz
	 * @param object
	 */
	private static void toStringObject(Class<?> clazz, Object object) {
		StringBuffer buffer = (StringBuffer)ThreadUtils.getAttribute(TOSTRING_TCBUFF);

		String hexHash = Integer.toHexString(System.identityHashCode(object));
		if(0 == buffer.length()) {
			buffer.append(clazz.getSimpleName()).append('@')
					.append(hexHash).append(TOSTRING_PREFIX);
		}

		@SuppressWarnings("unchecked")
		Set<String> converted = (Set<String>)ThreadUtils.getAttribute(TOSTRING_TCATTR);
		converted.add(object.getClass().getCanonicalName() + "@" + hexHash);

		// Rekurencyjne przeglądanie wszystkich klas nadrzędnych:
		Class<?> superClass = clazz.getSuperclass();
		if(superClass != null) {
			toStringObject(superClass, object);
		}

		String hyphen = "";
		if(TOSTRING_PREFIX.charAt(0) != buffer.charAt(buffer.length() - 1)) {
			hyphen = TOSTRING_HYPHEN;
		}

		for(Field field: clazz.getDeclaredFields()) {
			try {
				field.setAccessible(true);
				if(field.isAnnotationPresent(InToString.class)) {
					InToString inToString = field.getAnnotation(InToString.class);
					if(!inToString.ignore()) {
						String name = inToString.name();
						buffer.append(hyphen)
								.append((name.length() > 0)? name : field.getName())
								.append(TOSTRING_COLON);
						toStringField(field.get(object));
						hyphen = TOSTRING_HYPHEN;
					}
				} else if((field.getModifiers() & (Modifier.STATIC | Modifier.FINAL)) == 0) {
					buffer.append(hyphen).append(field.getName()).append(TOSTRING_COLON);
					toStringField(field.get(object));
					hyphen = TOSTRING_HYPHEN;
				}
			} catch(Exception e) {
				// TODO: Zaimplementować logowanie wyjątku
				e.printStackTrace();
			}
		}
	}

	/**
	 * Generuje reprezentację łańcuchową pola. Sprawdza, czy nie była ona już wcześniej
	 * wyznaczana. Jeśli tak, tworzy wersję skróconą reprezentacji takiego pola.
	 * 
	 * @param object
	 */
	private static void toStringField(Object object) {
		StringBuffer buffer = (StringBuffer)ThreadUtils.getAttribute(TOSTRING_TCBUFF);

		if(object == null) {
			buffer.append(TOSTRING_EMPTY);
		} else {
			Class<?> clazz = object.getClass();
			if(clazz.isPrimitive()) {
				buffer.append(object.toString());
			} else {
				@SuppressWarnings("unchecked")
				Set<String> converted = (Set<String>)ThreadUtils.getAttribute(TOSTRING_TCATTR);
				String hexHash = Integer.toHexString(System.identityHashCode(object));
				if(converted.contains(clazz.getCanonicalName() + "@" + hexHash)) {
					buffer.append(clazz.getSimpleName()).append('@').append(hexHash)
							.append(TOSTRING_REPEATE);
				} else {
					buffer.append(object.toString());
				}
			}
		}
	}

//	--------------------------------------------------------------------------
//	Porównywanie obiektów
//	--------------------------------------------------------------------------
	/**
	 * Porównuje dwa obiekty wykorzystując standardową metodę <code>equals</code>
	 * obiektu przekazanego parametrem wywołania <code>object1</code>. Zwraca
	 * <code>true</code> jeśli oba przekazane do porównania obiekty nie są
	 * określone (mają wartość <code>null</code>).
	 */
	public static boolean equalsChecker(Object object1, Object object2) {
		return null == object1? null == object2 : object1.equals(object2);
	}

	/**
	 * Porównuje dwa obiekty typu {@link Comparable} wykorzystując metodę <code>
	 * comapreTo</code> obiektu przezkazanego prarametrem wywołania <code>object1</code>.
	 * Zwraca <code>true</code> jeśli oba przekazane do porównania obiekty nie są
	 * określone (mają wartość <code>null</code>).
	 */
	@SuppressWarnings("unchecked")
	public static final boolean compareChecker(Comparable<?> object1, Comparable<?> object2) {
		return null == object1? null == object2 : 0 == ((Comparable<Object>)object1).compareTo((Comparable<Object>)object2);
	}

	/**
	 * Porównuje dwa obiekty <code>T</code> typu {@link Comparable} wykorzystując
	 * metdoę {@link Comparable#compareTo(Object)}. Jeśli przekazane obiekty <code>
	 * val?</code> mają wartość <code>null</code>, do do porównania wykorzystuje
	 * odpowiadające im wartości <code>def?</code>.
	 * 
	 * @param <T>
	 * @param val1
	 * @param def1
	 * @param val2
	 * @param def2
	 * @return
	 */
	// TODO: Zweryfikować dokumentację
	public static final <T extends Comparable<T>> int comparator(T val1, T def1, T val2, T def2) {
		return comparator(val1 == null? def1 : val1, val2 == null? def2 : val2);
		// return (val1 == null? def1 : val1).compareTo(val2 == null? def2 : val2);
	}
	// TODO: Uzupełnić dokumentację
	public static final <T extends Comparable<T>> int comparator(T val1, T val2) {
		if(val1 == null) {
			return val2 == null? 0 : -1;
		} else {
			return val2 == null? 1 : val1.compareTo(val2);
		}
	}

//	--------------------------------------------------------------------------
//	Operacje na listach. TODO: Do weryfikacji wykorzystania i przeniesienia
//	--------------------------------------------------------------------------
	public static <E> List<E> subtractList(List<E> minuend, Collection<?> subtrahendList) {
		return subtractList(minuend, null, subtrahendList, null);
	}
	public static <E> List<E> subtractList(List<E> minuend, String mProperty, Collection<?> subtrahendList) {
		return subtractList(minuend, mProperty, subtrahendList, null);
	}
	public static <E> List<E> subtractList(List<E> minuend, Collection<?> subtrahendList, String sProperty) {
		return subtractList(minuend, null, subtrahendList, sProperty);
	}
	public static <E> List<E> subtractList(List<E> minuend, String mProperty, Collection<?> subtrahendList, String sProperty) {
		Object mObject, sObject;
		List<E> result = new ArrayList<E>();

		try {
			for(Object element : subtrahendList) {
				for(int index = minuend.size() - 1; index >= 0; index--) {
					sObject = StringUtils.isEmpty(sProperty)?
							element : PropertyUtils.getProperty(element, sProperty);
					mObject = StringUtils.isEmpty(mProperty)?
							minuend.get(index) : PropertyUtils.getProperty(minuend.get(index), mProperty);

					if(mObject.equals(sObject)) {
						result.add(minuend.remove(index));
					}
				}
			}
		} catch(Exception e) {
			// TODO: Zaimplementować dedykowany wyjątek dla metody QbjectUtils.subtractList()
			throw new BaseRuntimeException(e);
		}

		return result;
	}
}
