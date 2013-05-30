/* org.agiso.core.lang.exception.StubImplementationExceptionITest (07-02-2013)
 * 
 * StubImplementationExceptionITest.java
 * 
 * Copyright 2013 agiso.org.
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
 * 
 * 
 * @author <a href="mailto:kkopacz@agiso.org">Karol Kopacz</a>
 */
public class StubImplementationExceptionITest {
	@Test
	public void testStubImplementationException() throws Exception {
		try {
			throw new StubImplementationException();
		} catch(StubImplementationException sie) {
			assert this.getClass().getCanonicalName().equals(sie.getClassName());
			assert "testStubImplementationException".equals(sie.getMethodName());
		}
	}
}
