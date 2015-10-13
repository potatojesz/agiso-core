/* org.agiso.core.i18n.provider.AnnotationMessageProvider (4 gru 2014)
 * 
 * AnnotationMessageProvider.java
 * 
 * Copyright 2014 agiso.org.
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
package org.agiso.core.i18n.provider;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.agiso.core.i18n.annotation.I18n;
import org.agiso.core.i18n.util.I18nUtils;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.MethodAnnotationsScanner;

/**
 * 
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public class AnnotationMessageProvider extends AbstractMessageProvider {
	private Map<String, String> messages = new HashMap<String, String>();

//	--------------------------------------------------------------------------
	public AnnotationMessageProvider(String... packages) {
		for(String packagePrefix : packages) {
			Reflections reflections = new Reflections(packagePrefix, new FieldAnnotationsScanner(), new MethodAnnotationsScanner());
			Set<Field> fields = reflections.getFieldsAnnotatedWith(I18n.class);
			for(Field field : fields) {
				String msg = field.getAnnotation(I18n.class).def();
				if(msg != null && !msg.trim().isEmpty()) {
					messages.put(I18nUtils.getCode(field), msg);
				}
			}
			Set<Method> methods = reflections.getMethodsAnnotatedWith(I18n.class);
			for(Method method : methods) {
				String msg = method.getAnnotation(I18n.class).def();
				if(msg != null && !msg.trim().isEmpty()) {
					messages.put(I18nUtils.getCode(method), msg);
				}
			}
		}
	}

//	--------------------------------------------------------------------------
	@Override
	protected MessageFormat resolveMessageFormat(Locale locale, String code) {
		String msg = getStringOrNull(messages, code);
		if(msg != null) {
			return createMessageFormat(msg, locale);
		}
		return null;
	}

	private String getStringOrNull(Map<String, String> messages, String code) {
		return messages.get(code);
	}
}
