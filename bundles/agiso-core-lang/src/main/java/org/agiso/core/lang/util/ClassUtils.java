/* org.agiso.core.lang.util.ClassUtils (2013-05-17)
 * 
 * ClassUtils.java
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
package org.agiso.core.lang.util;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public abstract class ClassUtils {
	private static final Logger logger = Logger.getLogger(ClassUtils.class.getName());

//	--------------------------------------------------------------------------
//	Wyszukiwanie metod w klasach
//	--------------------------------------------------------------------------
	/**
	 * Sprawdza, czy wskazana klasa posiada publiczną metodę o określonej sygnaturze.
	 * 
	 * <p>Based on:
	 * org.springframework.util.ClassUtils.hasMethod(Class<?>, String, Class<?>...)
	 * 
	 * @param clazz Klasa do sprawdzenia
	 * @param methodName Nazwa wyszukiwanej metody
	 * @param paramTypes Tablica typów parametrów wywołania metody
	 * @return <code>true</code> jeśli klasa zawiera metodę
	 * @see Class#getMethod
	 */
	public static boolean hasMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
		return (getMethodIfAvailable(clazz, methodName, paramTypes) != null);
	}

	/**
	 * Wyszukuje dla wskazanej klasy publiczną metodę o określonej sygnaturze. Jeśli
	 * metoda nie zostanie naleziona wyrzuca wyjątek {@code IllegalStateException}.
	 * <p>W przypadku gdy nie jest określona tablica parametrów wywołania, zwraca metodę
	 * tylko gdy wynik wyszukiwania jest unikatowy, tj. istnieje tylko jedna publiczna
	 * metoda o wskazanej nazwie.
	 * 
	 * <p>Based on:
	 * org.springframework.util.ClassUtils.getMethod(Class<?>, String, Class<?>...)
	 * 
	 * @param clazz Klasa do sprawdzenia
	 * @param methodName Nazwa wyszukiwanej metody
	 * @param paramTypes Tablica typów parametrów wywołania metody
	 *     (może być {@code null} w celu wyszukania dowolnej metody o wskazanej nazwie)
	 * @return Znaleziona metoda (niegdy {@code null})
	 * @throws IllegalStateException jeśli nie znaleziono metody lub nie jest unikatowa
	 * @see Class#getMethod
	 */
	public static Method getMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) {
		if(clazz == null) {
			throw new NullPointerException("Klasa musi być określona");
		}
		if(methodName == null) {
			throw new NullPointerException("Nazwa metody musi być określona");
		}

		if(paramTypes != null) {
			try {
				return clazz.getMethod(methodName, paramTypes);
			} catch(NoSuchMethodException ex) {
				throw new IllegalStateException("Expected method not found: " + ex);
			}
		} else {
			Set<Method> candidates = new HashSet<Method>(1);
			Method[] methods = clazz.getMethods();
			for(Method method : methods) {
				if(methodName.equals(method.getName())) {
					candidates.add(method);
				}
			}
			if(candidates.size() == 1) {
				return candidates.iterator().next();
			} else if(candidates.isEmpty()) {
				throw new IllegalStateException("Expected method not found: " + clazz + "." + methodName);
			} else {
				throw new IllegalStateException("No unique method found: " + clazz + "." + methodName);
			}
		}
	}

	/**
	 * Wyszukuje dla wskazanej klasy publiczną metodę o określonej sygnaturze. Jeśli
	 * metoda nie zostanie naleziona zwraca {@code null}.
	 * <p>W przypadku gdy nie jest określona tablica parametrów wywołania, zwraca metodę
	 * tylko gdy wynik wyszukiwania jest unikatowy, tj. istnieje tylko jedna publiczna
	 * metoda o wskazanej nazwie.
	 * 
	 * <p>Based on:
	 * org.springframework.util.ClassUtils.getMethodIfAvailable(Class<?>, String, Class<?>...)
	 * 
	 * @param clazz Klasa do sprawdzenia
	 * @param methodName Nazwa wyszukiwanej metody
	 * @param paramTypes Tablica typów parametrów wywołania metody
	 *     (może być {@code null} w celu wyszukania dowolnej metody o wskazanej nazwie)
	 * @return Znaleziona metoda lub @{code null} gdy nie istnieje lub nie jest unikatowa
	 * @see Class#getMethod
	 */
	public static Method getMethodIfAvailable(Class<?> clazz, String methodName, Class<?>... paramTypes) {
		if(clazz == null) {
			throw new NullPointerException("Klasa musi być określona");
		}
		if(methodName == null) {
			throw new NullPointerException("Nazwa metody musi być określona");
		}

		if(paramTypes != null) {
			try {
				return clazz.getMethod(methodName, paramTypes);
			} catch(NoSuchMethodException ex) {
				return null;
			}
		} else {
			Set<Method> candidates = new HashSet<Method>(1);
			Method[] methods = clazz.getMethods();
			for(Method method : methods) {
				if(methodName.equals(method.getName())) {
					candidates.add(method);
				}
			}
			if(candidates.size() == 1) {
				return candidates.iterator().next();
			}
			return null;
		}
	}

//	--------------------------------------------------------------------------
//	Wyszukiwanie lokalizacji biblioteki jar zawierającej klasę
//	Na podstawie: https://dzone.com/articles/locate-jar-classpath-given
//	--------------------------------------------------------------------------
	public static String locate(Class<?> c) /*throws ClassNotFoundException*/ {
		return locate(c, c.getClassLoader());
	}
	public static String locate(Class<?> c, ClassLoader cl) /*throws ClassNotFoundException*/ {
		return locate(c.getName(), cl);
	}
	public static String locate(String name, ClassLoader cl) /*throws ClassNotFoundException*/ {
		final URL location;
		final String classLocation = name.replace('.', '/') + ".class";

		if(cl == null) {
			location = ClassLoader.getSystemResource(classLocation);
		} else {
			location = cl.getResource(classLocation);
		}

		if(location != null) {
			Pattern p = Pattern.compile("^.*:(.*)!.*$");
			Matcher m = p.matcher(location.toString());
			if(m.find()) {
				return m.group(1);
			} else {
				if(logger.isLoggable(Level.WARNING)) {
					logger.warning("Cannot parse location of '" + location + "'. " +
							"Probably not loaded from a Jar");
				}
				// throw new ClassNotFoundException("Cannot parse location of '"
				// 		+ location + "'.  Probably not loaded from a Jar");
				return null;
			}
		} else {
			if(logger.isLoggable(Level.WARNING)) {
				logger.warning("Cannot find class '" + name + " using the " + cl);
			}
			// throw new ClassNotFoundException("Cannot find class '"
			// 		+ name + " using the " + cl);
			return null;
		}
	}
}
