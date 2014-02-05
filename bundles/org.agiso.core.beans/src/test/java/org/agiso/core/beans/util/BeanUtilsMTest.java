/* org.agiso.core.lang.util.BeanUtilsMTest (2010-01-16)
 * 
 * BeanUtilsMTest.java
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
package org.agiso.core.beans.util;

import static org.mockito.Mockito.*;

import org.agiso.core.beans.exception.BeanRetrievalException;
import org.agiso.core.beans.factory.IBeanFactory;
import org.testng.annotations.Test;

/**
 * Testuje poprawność działania klasy {@link BeanUtils}.
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
@Test(sequential = false)
public class BeanUtilsMTest {
	@Test(expectedExceptions = BeanRetrievalException.class)
	public void testNotInitialized() throws Exception {
		BeanUtils.setBeanFactory(null);
		BeanUtils.getBean("");
	}
	@Test(expectedExceptions = BeanRetrievalException.class)
	public void testGetBean() throws Exception {
		IBeanFactory beanFactory = mock(IBeanFactory.class);
		when(beanFactory.getBean("string"))
			.thenReturn("STRING OBJECT");
		when(beanFactory.getBean("missing"))
			.thenThrow(new BeanRetrievalException("missing"));

		BeanUtils.setBeanFactory(beanFactory);
		assert "STRING OBJECT".equals(BeanUtils.getBean("string"));

		try {
			BeanUtils.getBean("missing");
		} catch(Exception e) {
			throw e;
		}
	}
}
