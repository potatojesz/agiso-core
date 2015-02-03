/* org.agiso.core.i18n.util.I18nUtils (20-08-2012)
 * 
 * I18nUtils.java
 * 
 * Copyright 2012 agiso.org.
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
package org.agiso.core.i18n.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.agiso.core.i18n.annotation.I18n;
import org.agiso.core.i18n.util.I18nUtils.IMessageProvider;

/**
 * Klasa narzędziowa dostarczająca funkcjonalności związanych z wielojęzykowością
 * łańcuchów znaków (komunikaty, wiadomości, etc...)
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public abstract class I18nUtils {
	/**
	 * Bazowy interfejs wyliczenia zestawu komunikatów wykorzystywany do
	 * znacznikowania tych wyliczeń.
	 */
	public interface I18nId {
	}

	/**
	 * Interfejs dostarczyciela rozwinięć kodów komunikatów na komunikaty.
	 */
	public interface IMessageProvider {
		public String getMessage(String code, Object... args);
	}

//	--------------------------------------------------------------------------
//	Obsługa MessageProvider'a
//	--------------------------------------------------------------------------
	private static IMessageProvider messageProvider;
	private static final IMessageProvider internalMessageProvider =
			new SimpleMessageProvider();

	static {
		messageProvider = internalMessageProvider;
	}

//	--------------------------------------------------------------------------
	/**
	 * Zwraca wykorzystywanego przez klasę narzędziową dostarczyciela wiadomości,
	 * lub <code>null</code> jeśli nie był on ustawiony i jest wykorzystywany
	 * mechanizm wbudowany.
	 * 
	 * @return the messageFactory
	 */
	public static IMessageProvider getMessageProvider() {
		if(messageProvider == internalMessageProvider) {
			return null;
		} else {
			return messageProvider;
		}
	}
	/**
	 * Ustawia dostarczyciela wiadomości wykorzystywanego przez klasę narzędziową.
	 * Jeśli metoda nie zostanie wywołana, zwracane wiadomości będą generowane
	 * zgodnie z mechanizmem wbudowanym (w oparciu o przekazane do wywołań kody i
	 * parametry wiadomości).<br/>
	 * Wywołanie metody z wartością <code>null</code> przywraca mechanizm wbudowany.
	 * 
	 * @param provider Dostarczyciel lokalizacji wiadomości.
	 */
	public static void setMessageProvider(IMessageProvider provider) {
		if(provider != null) {
			messageProvider = provider;
		} else {
			messageProvider = internalMessageProvider;
		}
	}

//	--------------------------------------------------------------------------
//	Pobieranie tłumaczeń na podstawie kodów, wyliczeń, klas, metod i pól
//	--------------------------------------------------------------------------
	public static String getMessage(String code, Object... args) {
		return messageProvider.getMessage(code, args);
	}

	public static String getMessage(Enum<?> e, Object... args) {
		return getMessage(getCode(e), args);
	}

	public static String getMessage(Class<?> c, Object... args) {
		return getMessage(getCode(c), args);
	}

	public static String getMessage(Method m, Object... args) {
		return getMessage(getCode(m), args);
	}

	public static String getMessage(Field f, Object... args) {
		return getMessage(getCode(f), args);
	}

	public static String getMessage(Class<?> c, String field, Object... args)
			throws IntrospectionException {
		return getMessage(getCode(c, field), args);
	}

//	--------------------------------------------------------------------------
//	Wyznaczanie kodów I18n wyliczeń, klas, metod i pól
//	--------------------------------------------------------------------------
	private static final char CODE_SEPARATOR = '.';

	public static String getCode(Enum<?> e) {
		try {
			return getCode(e.getClass().getField(e.name()));
		} catch(SecurityException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} catch(NoSuchFieldException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return e.getDeclaringClass().getName() + CODE_SEPARATOR + e.name();
	}

	public static String getCode(Class<?> c) {
		if(c.isAnnotationPresent(I18n.class)) {
			if(c.getAnnotation(I18n.class).value().length() > 0) {
				return c.getAnnotation(I18n.class).value();
			} else {
				return c.getName();
			}
		}
		// else if(c.isAnnotationPresent(I18nRef.class)) {
		// 	Object ref = c.getAnnotation(I18nRef.class).value();
		// 	if(((Class<?>)ref).isEnum()) {
		// 		return getCode((Enum<?>)ref);
		// 	} else {
		// 		return getCode((Class<?>)ref);
		// 	}
		// }
		return c.getName();
	}

	public static String getCode(Method m) {
		if(m.isAnnotationPresent(I18n.class)) {
			if(m.getAnnotation(I18n.class).value().length() > 0) {
				return m.getAnnotation(I18n.class).value();
			} else {
				return m.getDeclaringClass().getCanonicalName() + CODE_SEPARATOR + findGetterFieldName(m);
			}
		}
		// else if(m.isAnnotationPresent(I18nRef.class)) {
		// 	Object ref = m.getAnnotation(I18nRef.class).value();
		// 	if(((Class<?>)ref).isEnum()) {
		// 		return getCode((Enum<?>)ref);
		// 	} else {
		// 		return getCode((Class<?>)ref);
		// 	}
		// }
		return m.getDeclaringClass().getCanonicalName() + CODE_SEPARATOR + findGetterFieldName(m);
	}

	public static String getCode(Field f) {
		if(f.isAnnotationPresent(I18n.class)) {
			if(f.getAnnotation(I18n.class).value().length() > 0) {
				return f.getAnnotation(I18n.class).value();
			} else {
				return f.getDeclaringClass().getName() + CODE_SEPARATOR + f.getName();
			}
		}
		// else if(f.isAnnotationPresent(I18nRef.class)) {
		// 	Object ref = f.getAnnotation(I18nRef.class).value();
		// 	if(((Class<?>)ref).isEnum()) {
		// 		return getCode((Enum<?>)ref);
		// 	} else {
		// 		return getCode((Class<?>)ref);
		// 	}
		// }
		return f.getDeclaringClass().getName() + CODE_SEPARATOR + f.getName();
	}

	public static String getCode(Class<?> c, String field) throws IntrospectionException {
		final String i18nCode = findGetterFieldCode(c, field, false);
		return i18nCode != null? i18nCode : c.getName() + CODE_SEPARATOR + field;
	}

	public static String findCode(Class<?> c, String field) throws IntrospectionException {
		final String i18nCode = findGetterFieldCode(c, field, true);
		return i18nCode != null? i18nCode : c.getName() + CODE_SEPARATOR + field;
	}

//	--------------------------------------------------------------------------
	private static String findGetterFieldName(Method m) {
		String name = m.getName();
		if(name.length() > 3 && name.startsWith("get") && Character.isUpperCase(name.charAt(3))) {
			if(name.length() == 4) {
				name = String.valueOf(Character.toLowerCase(name.charAt(3)));
			} else {
				name = String.valueOf(Character.toLowerCase(name.charAt(3))) + name.substring(4);
			}
		} else if(name.length() > 2 && name.startsWith("is") && Character.isUpperCase(name.charAt(2))) {
			if(name.length() == 3) {
				name = String.valueOf(Character.toLowerCase(name.charAt(2)));
			} else {
				name = String.valueOf(Character.toLowerCase(name.charAt(2))) + name.substring(3);
			}
		}
		return name;
	}

	private static String findGetterFieldCode(Class<?> c, String field, boolean reflectionCheck)
			throws IntrospectionException {
		for(PropertyDescriptor pd : Introspector.getBeanInfo(c).getPropertyDescriptors()) {
			if(pd.getName().equals(field)) {
				final Method g = pd.getReadMethod();
				if(g != null) {
					// Jeśli jest adnotacja I18n na metodzie odczytującej pole, to pobieranie
					// pobieranie jej klucza (określonego przez 'value') lub klucza domyślnego:
					if(g.isAnnotationPresent(I18n.class)) {
						if(g.getAnnotation(I18n.class).value().length() > 0) {
							return g.getAnnotation(I18n.class).value();
						} else {
							return g.getDeclaringClass().getName() + CODE_SEPARATOR + field;
						}
					} else if(reflectionCheck) {
						// Pole nie jest opisane adnotacją I18n. Jeśli do wyszukania mają być
						// wykorzystane mechanizmy, to sprawdzamy interfejsy i nadklasę:
						for(Class<?> i : c.getInterfaces()) {
							String i18nCode = findGetterFieldCode(i, field, false);
							if(i18nCode != null) {
								return i18nCode;
							}
						}
						Class<?> s = c.getSuperclass();
						if(s != null) {
							return findGetterFieldCode(s, field, true);
						}
					}
				}
			}
		}
		if(reflectionCheck) {
			for(Class<?> i : c.getInterfaces()) {
				String i18nCode = findGetterFieldCode(i, field, false);
				if(i18nCode != null) {
					return i18nCode;
				}
			}
		}

		return null;
	}
}

//	--------------------------------------------------------------------------
/**
 * 
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
class SimpleMessageProvider implements IMessageProvider {
	@Override
	public String getMessage(String code, Object... args) {
		if(args == null || args.length == 0) {
			return code + "[]";
		} else {
			StringBuilder builder = new StringBuilder();
			builder.append(code).append("[0: ");
			builder.append(String.valueOf(args[0]));
			for(int index = 1; index < args.length; index++) {
				builder.append(", ").append(index).append(": ");
				builder.append(String.valueOf(args[index]));
			}
			builder.append("]");
			return builder.toString();
		}
	}
}