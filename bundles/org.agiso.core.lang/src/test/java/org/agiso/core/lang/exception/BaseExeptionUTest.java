/* org.agiso.core.lang.exception.BaseExeptionUTest (2010-09-25)
 * 
 * BaseExeptionUTest.java
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
 * Testuje proces wyrzucania wyjątków typu {@link BaseException}.
 *
 * @author <a href="mailto:kkopacz@agiso.org">Karol Kopacz</a>
 * @since 1.0
 */
public class BaseExeptionUTest {
	@Test(expectedExceptions = TestException.class)
	public void testThrow() throws TestException {
		throw new TestException();
	}

	@Test
	public void testCatch() {
		try {
			throw new TestException();
		} catch(BaseException be) {
			assert null != be;
			assert be instanceof IException;
		}
	}

//	--------------------------------------------------------------------------
	public class TestException extends BaseException {
		private static final long serialVersionUID = 1L;
	}
}
