/* org.agiso.core.i18n.provider.AnnotationMessageProviderUTest (20-01-2015)
 * 
 * AnnotationMessageProviderUTest.java
 * 
 * Copyright 2015 agiso.org.
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
package org.agiso.core.i18n.provider;

import static org.agiso.core.i18n.util.I18nUtils.*;

import org.agiso.core.i18n.beans.BeanImplementation1;
import org.agiso.core.i18n.beans.EnumMessages;
import org.agiso.core.i18n.beans.IBeanInterface;
import org.agiso.core.i18n.util.I18nUtils.IMessageProvider;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * 
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public class AnnotationMessageProviderUTest {
	private IMessageProvider messageProvider;

//	--------------------------------------------------------------------------
	@BeforeClass
	public void beforeClass() throws Exception {
		messageProvider = new AnnotationMessageProvider("org.agiso.core.i18n");
	}

//	--------------------------------------------------------------------------
	@Test
	public void testAnnotationMessageProvider() throws Exception {
		assert "Enum message 1"
			.equals(messageProvider.getMessage(getCode(EnumMessages.M1)));
		assert "Enum message 2 with param {0}"
			.equals(messageProvider.getMessage(getCode(EnumMessages.M2)));
		assert "Enum message 2 with param value"
			.equals(messageProvider.getMessage(getCode(EnumMessages.M2), "value"));

		assert "Interface field1 label"
			.equals(messageProvider.getMessage(getCode(IBeanInterface.class, "field1")));
		assert "BeanImplementation1 field1 label"
			.equals(messageProvider.getMessage(getCode(BeanImplementation1.class, "field1")));
		assert "org.agiso.core.i18n.beans.BeanImplementation1.field2"
			.equals(messageProvider.getMessage(getCode(BeanImplementation1.class, "field2")));
	}
}
