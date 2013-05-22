/* org.agiso.core.lang.exception.BaseRuntimeExceptionUTest (2010-09-25)
 * 
 * BaseRuntimeExceptionUTest.java
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
package org.agiso.core.lang.exception;

import org.testng.annotations.Test;

/**
 * Testuje proces wyrzucania wyjątków typu {@link BaseRuntimeException}.
 *
 * @author <a href="mailto:kkopacz@agiso.org">Karol Kopacz</a>
 * @since 1.0
 */
public class BaseRuntimeExceptionUTest {
	@Test(expectedExceptions = BaseRuntimeException.class)
	public void testThrow() {
		throw new BaseRuntimeException();
	}

	@Test
	public void testCatch() {
		try {
			throw new BaseRuntimeException();
		} catch(BaseRuntimeException bre) {
			assert null != bre;
			assert bre instanceof IException;
		}
	}
}
