/* org.agiso.core.lang.util.StringUtils (2009-10-16)
 * 
 * StringUtils.java
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

/**
 * Roszerzenie klasy narzędziowej {@link org.apache.commons.lang.StringUtils}.
 * 
 * @author <a href="mailto:kkopacz@agiso.org">Karol Kopacz</a>
 * @since 1.0
 */
public final class StringUtils extends org.apache.commons.lang.StringUtils {
	/**
	 * Prywatny konstruktor uniemożliwiający instancjonowanie klasy.
	 */
	private StringUtils() {
		// Does nothing
	}

//	--------------------------------------------------------------------------
	public static String blankIfNull(Object object) {
		return (object == null)? "" : object.toString();
	}

	public static String addPending(String s, char c, int length) {
		if(length <= s.length()) {
			return s;
		}

		StringBuilder b = new StringBuilder(length);
		for(int index = s.length(); index < length; index++) {
			b.append(c);
		}
		b.append(s);

		return b.toString();
	}

//	--------------------------------------------------------------------------
//	TODO: Przenieść do klasy TextUtils
//	--------------------------------------------------------------------------
}
