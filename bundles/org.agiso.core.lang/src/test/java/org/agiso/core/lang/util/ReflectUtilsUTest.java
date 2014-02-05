/* org.agiso.core.lang.util.ReflectUtilsUTest (2010-01-17)
 * 
 * ReflectUtilsUTest.java
 * 
 * Copyright 2010 agiso.org.
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

import org.agiso.core.lang.exception.MethodInvocationException;
import org.testng.annotations.Test;

/**
 * Testuje poprawność działania klasy narzędziowej {@link ReflectUtils}.
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
@Test(singleThreaded = false)
public class ReflectUtilsUTest {
	@SuppressWarnings("unused")
	private class TestObject {
		private String value;

		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}

//	--------------------------------------------------------------------------
	@Test
	public void testInvokeMethod() throws Exception {
		TestObject object = new TestObject();
		assert null == object.getValue();

		ReflectUtils.invokeMethod(object, "setValue", "abcd");
		assert "abcd".equals(ReflectUtils.invokeMethod(object, "getValue"));
	}

	@Test(expectedExceptions = MethodInvocationException.class)
	public void testInvokeMethodException() throws Exception {
		TestObject object = new TestObject();
		ReflectUtils.invokeMethod(object, "invalidMethod");
	}
}
