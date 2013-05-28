/* org.agiso.core.lang.util.ReflectUtilsUTest (2010-01-17)
 *
 * ReflectUtilsUTest.java
 *
 * Copyright 2010 agiso.org.
 */
package org.agiso.core.lang.util;

import org.agiso.core.lang.exception.MethodInvocationException;
import org.testng.annotations.Test;

/**
 * Testuje poprawność działania klasy narzędziowej {@link ReflectUtils}.
 * 
 * @author <a href="mailto:kkopacz@agiso.org">Karol Kopacz</a>
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
