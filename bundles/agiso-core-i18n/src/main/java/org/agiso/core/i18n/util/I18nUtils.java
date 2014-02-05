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
	private static IMessageProvider messageProvider;

	private static final IMessageProvider internalMessageProvider = new IMessageProvider() {
		@Override
		public String getMessage(String code, Object... args) {
			if(args == null) {
				return code;
			} else {
				return code + "[" + args + "]";
			}
		}
	};

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
	public static String getCode(Enum<?> e) {
		try {
			Field f = e.getClass().getField(e.name());
			if(f.isAnnotationPresent(I18n.class)) {
				return f.getAnnotation(I18n.class).value();
			// } else if(f.isAnnotationPresent(I18nRef.class)) {
			// 	Object ref = f.getAnnotation(I18nRef.class).value();
			// 	if(((Class<?>)ref).isEnum()) {
			// 		return getCode((Enum<?>)ref);
			// 	} else {
			// 		return getCode((Class<?>)ref);
			// 	}
			}
		} catch(SecurityException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} catch(NoSuchFieldException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return e.getDeclaringClass().getName() + '.' + e.name();
	}

	public static String getCode(Class<?> c) {
		if(c.isAnnotationPresent(I18n.class)) {
			return c.getAnnotation(I18n.class).value();
		// } else if(c.isAnnotationPresent(I18nRef.class)) {
		// 	Object ref = c.getAnnotation(I18nRef.class).value();
		// 	if(((Class<?>)ref).isEnum()) {
		// 		return getCode((Enum<?>)ref);
		// 	} else {
		// 		return getCode((Class<?>)ref);
		// 	}
		}
		return c.getName();
	}

	public static String getCode(Class<?> c, String field) throws IntrospectionException {
		for(PropertyDescriptor pd : Introspector.getBeanInfo(c).getPropertyDescriptors()) {
			if(pd.getName().equals(field)) {
				final Method getter = pd.getReadMethod();
				if(getter != null) {
					I18n i18n = getter.getAnnotation(I18n.class);
					if(i18n != null) {
						return i18n.value();
					}
				}
			}
		}
		return c.getName() + "." + field;
	}

	public static String getMessage(String code, Object... args) {
		return messageProvider.getMessage(code, args);
	}

	public static String getMessage(Enum<?> e, Object... args) {
		return getMessage(getCode(e), args);
	}

	public static String getMessage(Class<?> c, Object... args) {
		return getMessage(getCode(c), args);
	}

	public static String getMessage(Class<?> c, String field, Object... args) throws IntrospectionException {
		return getMessage(getCode(c, field), args);
	}
}
