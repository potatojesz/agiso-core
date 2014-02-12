/* org.agiso.core.lang.util.HexUtils (12-02-2014)
 * 
 * HexUtils.java
 * 
 * Copyright 2014 agiso.org
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
 * 
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public abstract class HexUtils {
	/**
	 * @param array
	 * @return
	 */
	public static final String toHexString(byte[] array) {
		int length = array.length;
		StringBuilder hexString = new StringBuilder();
		for(int i = 0; i < length; i++) {
			hexString.append(toHexDigit(array[i]));
		}

		return hexString.toString();
	}

	/**
	 * @param b
	 * @return
	 */
	public static final String toHexDigit(byte b) {
		StringBuilder sb = new StringBuilder();
		char c;
		// First nibble
		c = (char)((b >> 4) & 0xf);
		if(c > 9) {
			c = (char)((c - 10) + 'a');
		} else {
			c = (char)(c + '0');
		}
		sb.append(c);
		// Second nibble
		c = (char)(b & 0xf);
		if(c > 9) {
			c = (char)((c - 10) + 'a');
		} else {
			c = (char)(c + '0');
		}
		sb.append(c);
		return sb.toString();
	}
}
