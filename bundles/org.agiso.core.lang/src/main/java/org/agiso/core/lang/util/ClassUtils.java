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

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 
 * @author <a href="mailto:kkopacz@agiso.org">Karol Kopacz</a>
 * @since 1.0
 */
public abstract class ClassUtils {
	private static final Logger logger = Logger.getLogger(ClassUtils.class.getName());

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
