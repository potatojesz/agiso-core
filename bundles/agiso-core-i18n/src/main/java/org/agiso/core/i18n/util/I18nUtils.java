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
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

import org.agiso.core.i18n.annotation.I18n;
import org.agiso.core.i18n.provider.ILocaleProvider;
import org.agiso.core.i18n.provider.IMessageProvider;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import com.google.common.collect.ImmutableMap;

import net.jodah.typetools.TypeResolver;

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

//	--------------------------------------------------------------------------
//	Obsługa LocaleProvider'a i MessageProvider'a
//	--------------------------------------------------------------------------
	private static final Method METHOD_TO_LANGUAGE_TAG;

	private static ILocaleProvider localeProvider;
	private static final ILocaleProvider internalLocaleProivder =
			new ILocaleProvider() {
				@Override
				public Locale getLocale() {
					return Locale.getDefault();
				}
			};

	private static IMessageProvider[] messageProviders;
	private static final IMessageProvider[] internalMessageProviders =
			new IMessageProvider[] {
				new SimpleMessageProvider()
			};

	static {
		Method method_toLanguageTag = null;
		try {
			method_toLanguageTag = Locale.class.getMethod("toLanguageTag");
		} catch(Exception e) {
		}
		METHOD_TO_LANGUAGE_TAG = method_toLanguageTag;

		localeProvider = internalLocaleProivder;
		messageProviders = internalMessageProviders;
	}

//	--------------------------------------------------------------------------
	/**
	 * Zwraca dostarczyciela lokalizacji wykorzystywanego przez klasę
	 * narzędziową, lub <code>null</code> jeśli dostarczyciel nie był ustawiony
	 * i jest wykorzystywany mechanizm wbudowany.
	 */
	public static ILocaleProvider getLocaleProvider() {
		if(localeProvider == internalLocaleProivder) {
			return null;
		} else {
			return localeProvider;
		}
	}
	/**
	 * Ustawia dostarczyciela lokalizacji wykorzystywanego przez klasę narzędziową.
	 * Jeśli metoda nie zostanie wywołana, lokalizacja zwracana będzie zgodnie
	 * z mechanizmem wbudowanym.<br/>
	 * Wywołanie metody z wartością <code>null</code> przywraca mechanizm wbudowany.
	 * 
	 * @param provider Dostarczyciel lokalizacji.
	 */
	public static void setLocaleProvider(ILocaleProvider provider) {
		if(provider == null) {
			localeProvider = internalLocaleProivder;
		} else {
			localeProvider = provider;
		}
	}

	/**
	 * Zwraca tablicę dostarczycieli wiadomości wykorzystywanych przez klasę
	 * narzędziową, lub <code>null</code> jeśli dostarczyciele nie byli ustawieni
	 * i jest wykorzystywany mechanizm wbudowany.
	 */
	public static IMessageProvider[] getMessageProviders() {
		if(messageProviders == internalMessageProviders) {
			return null;
		} else {
			return messageProviders;
		}
	}
	/**
	 * Ustawia dostarczycieli wiadomości wykorzystywanych przez klasę narzędziową.
	 * Jeśli metoda nie zostanie wywołana, zwracane wiadomości będą generowane
	 * zgodnie z mechanizmem wbudowanym (w oparciu o przekazane do wywołań kody i
	 * parametry wiadomości).<br/>
	 * Wywołanie metody z wartością <code>null</code> przywraca mechanizm wbudowany.
	 * 
	 * @param provider Dostarczyciel wiadomości.
	 */
	public static void setMessageProvider(IMessageProvider provider) {
		setMessageProviders(provider);
	}
	public static void setMessageProviders(IMessageProvider... providers) {
		// Jeśli tablica dostarczycieli jest pusta bądź zawiera tylko jeden
		// element o wartości 'null', to przywracamy mechanizm wbudowany:
		if(providers == null || providers.length == 0 || (
				providers.length == 1 && providers[0] == null)) {
			messageProviders = internalMessageProviders;
		} else {
			messageProviders = providers;
		}
	}

//	--------------------------------------------------------------------------
//	Pobieranie lokalizacji i znacznika języka
//	--------------------------------------------------------------------------
	public static Locale getLocale() {
		return localeProvider.getLocale();
	}

	public static String getLocaleLanguageTag() {
		return getLocaleLanguageTag(getLocale());
	}
	public static String getLocaleLanguageTag(Locale locale) {
		if(METHOD_TO_LANGUAGE_TAG != null) try {
			return (String)METHOD_TO_LANGUAGE_TAG.invoke(locale);
		} catch(Exception e) {
		}
		return locale.toString();
	}

//	--------------------------------------------------------------------------
//	Pobieranie tłumaczeń na podstawie kodów, wyliczeń, klas, metod i pól
//	--------------------------------------------------------------------------
	public static String getMessage(String code, Object... args) {
		return getMessage(I18nUtils.getLocale(), code, args);
	}
	public static String getMessage(Locale locale, String code, Object... args) {
		String message = null;
		for(IMessageProvider messageProvider : messageProviders) {
			message = messageProvider.getMessageIfExists(locale, code, args);
			if(message != null) {
				break;
			}
		}
		return message;
	}

	public static String getMessage(Enum<?> e, Object... args) {
		return getMessage(getCode(e), args);
	}
	public static String getMessage(String dscr, Enum<?> e, Object... args) {
		String message = getMessage(getCode(dscr, e), args);
		if(message == null) {
			message = getMessage(getCode(e), args);
		}
		return message;
	}
	public static String getMessage(Locale locale, Enum<?> e, Object... args) {
		return getMessage(locale, getCode(e), args);
	}
	public static String getMessage(Locale locale, String dscr, Enum<?> e, Object... args) {
		String message = getMessage(locale, getCode(dscr, e), args);
		if(message == null) {
			message = getMessage(locale, getCode(dscr, e), args);
		}
		return message;
	}

	public static String getMessage(Class<?> c, Object... args) {
		return getMessage(getCode(c), args);
	}
	public static String getMessage(Locale locale, Class<?> c, Object... args) {
		return getMessage(locale, getCode(c), args);
	}

	public static String getMessage(Method m, Object... args) {
		return getMessage(getCode(m), args);
	}
	public static String getMessage(Locale locale, Method m, Object... args) {
		return getMessage(locale, getCode(m), args);
	}

	public static String getMessage(Field f, Object... args) {
		return getMessage(getCode(f), args);
	}
	public static String getMessage(Locale locale, Field f, Object... args) {
		return getMessage(locale, getCode(f), args);
	}

	public static String getMessage(Class<?> c, String field, Object... args)
			throws IntrospectionException {
		return getMessage(getCode(c, field), args);
	}
	public static String getMessage(Locale locale, Class<?> c, String field, Object... args)
			throws IntrospectionException {
		return getMessage(locale, getCode(c, field), args);
	}

//	--------------------------------------------------------------------------
//	Wyznaczanie kodów I18n wyliczeń, klas, metod i pól
//	--------------------------------------------------------------------------
	private static final char CODE_SEPARATOR = '.';
	private static final char DSCR_SEPARATOR = '#';

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
	public static String getCode(String dscr, Enum<?> e) {
		return getCode(e) + DSCR_SEPARATOR + dscr;
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

	@SuppressWarnings("unchecked")
	public static <T, R extends Serializable> String getCode(Function<T, R> f)
			throws IntrospectionException {
		final Class<?>[] arguments = TypeResolver.resolveRawArguments(Function.class, f.getClass());

		return getCode((Class<T>)arguments[0], f);
	}
	public static <T, R extends Serializable> String getCode(Class<T> c, Function<T, R> f)
			throws IntrospectionException {
		Recorder<T> recorder = RecordingObject.create(c);
		T object = recorder.getObject();

		f.apply(object);

		String name = recorder.getCurrentPropertyName();
		name = uncapitalize(banishGetterSetters(name));

		return getCode(c, name);
	}

//	--------------------------------------------------------------------------
	private static String banishGetterSetters(String name) {
		return name.replaceAll("^(get|set)", "");
	}
	public static String toSnakeCase(String s) {
		return s.replaceAll("([a-z])([A-Z])","$1_$2").toLowerCase();
	}
	public static String uncapitalize(String s) {
		return Character.toLowerCase(s.charAt(0)) + s.substring(1);
	}

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
						// wykorzystane mechanizmy refleksji, to sprawdzamy interfejsy i nadklasę:
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
		return getMessage(I18nUtils.getLocale(), code, args);
	}
	@Override
	public String getMessage(Locale locale, String code, Object... args) {
		return getMessageIfExists(locale, code, args);
	}

	@Override
	public String getMessageIfExists(String code, Object... args) {
		return getMessageIfExists(I18nUtils.getLocale(), code, args);
	}
	@Override
	public String getMessageIfExists(Locale locale, String code, Object... args) {
		if(locale == null) {
			locale = I18nUtils.getLocale();
		}

		if(args == null || args.length == 0) {
			return I18nUtils.getLocaleLanguageTag(locale) + " " + code + "[]";
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

//--------------------------------------------------------------------------
// Na podstawie kodu z projektu https://github.com/benjiman/benjiql
// /src/main/java/uk/co/benjiweber/benjiql/mocking
//--------------------------------------------------------------------------
class Recorder<T> {
	private T t;
	private RecordingObject recorder;

	public Recorder(T t, RecordingObject recorder) {
		this.t = t;
		this.recorder = recorder;
	}

	public String getCurrentPropertyName() {
		return recorder.getCurrentPropertyName();
	}

	public T getObject() {
		return t;
	}
}

class RecordingObject implements MethodInterceptor {
	private static final Map<Class<?>, Object> DEFAULT_VALUES =
			ImmutableMap.<Class<?>,Object>builder()
					.put(String.class, "string")
					.put(Integer.class,0)
					.put(Float.class, 0f)
					.put(Double.class, 0d)
					.put(Long.class, 0L)
					.put(Character.class, 'c')
					.put(Byte.class, (byte)0)
					.put(int.class, 0)
					.put(float.class,0f)
					.put(double.class,0d)
					.put(long.class, 0L)
					.put(char.class, 'c')
					.put(byte.class, (byte)0)
			.build();

	private String currentPropertyName = "";
	private Recorder<?> currentMock = null;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> Recorder<T> create(Class<T> cls) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(cls);
		final RecordingObject recordingObject = new RecordingObject();

		enhancer.setCallback(recordingObject);
		return new Recorder((T) enhancer.create(), recordingObject);
	}

	public Object intercept(Object o, Method method, Object[] os, MethodProxy mp) throws Throwable {
		if(method.getName().equals("getCurrentPropertyName")) {
			return getCurrentPropertyName();
		}
		currentPropertyName =  method.getName();
		try {
			currentMock = create(method.getReturnType());
			return currentMock.getObject();
		} catch (IllegalArgumentException e) {
			return DEFAULT_VALUES.get((method.getReturnType()));
		}
	}

	public String getCurrentPropertyName() {
		return currentPropertyName;
	}
}