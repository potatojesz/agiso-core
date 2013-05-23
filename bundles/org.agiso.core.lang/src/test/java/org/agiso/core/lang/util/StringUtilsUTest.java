/* org.agiso.core.lang.util.StringUtilsUTest (2013-05-22)
 * 
 * StringUtilsUTest.java
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

import static org.agiso.core.lang.util.StringUtils.*;

import java.util.Date;

import org.testng.annotations.Test;

/**
 * Testuje poprawność działania metod klasy narzędziowej {@link StringUtils}.
 * 
 * @author <a href="mailto:kkopacz@agiso.org">Karol Kopacz</a>
 */
public class StringUtilsUTest {
	@Test
	public void testBlankIfNull() throws Exception {
		Object o1 = null;
		Object o2 = new Date();

		assert "".equals(blankIfNull(o1));
		assert o2.toString().equals(blankIfNull(o2));
	}

	@Test
	public void testAddPending() throws Exception {
		String text = "text";

		assert  "text".equals(addPending(text, 'x', -1));
		assert  "text".equals(addPending(text, 'x',  0));
		assert  "text".equals(addPending(text, 'x',  4));
		assert "xtext".equals(addPending(text, 'x',  5));
	}
}
