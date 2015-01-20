/* org.agiso.core.i18n.provider.ResourceBundleMessageProviderUTest (20-01-2015)
 * 
 * ResourceBundleMessageProviderUTest.java
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

import java.util.ResourceBundle;

import org.agiso.core.i18n.util.I18nUtils.IMessageProvider;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * 
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public class ResourceBundleMessageProviderUTest {
	private IMessageProvider messageProvider;

//	--------------------------------------------------------------------------
	@BeforeClass
	public void beforeClass() throws Exception {
		String bundleName = "org.agiso.core.i18n.provider.ResourceBundleMessageProviderUTest";
		messageProvider = new ResourceBundleMessageProvider(ResourceBundle.getBundle(bundleName));
	}

//	--------------------------------------------------------------------------
	@Test
	public void testAnnotationMessageProvider() throws Exception {
		assert "Resource bundle message 1"
			.equals(messageProvider.getMessage("bundle.key.1"));
		assert "Resource bundle message 2 with param {0}"
			.equals(messageProvider.getMessage("bundle.key.2"));
		assert "Resource bundle message 2 with param value"
			.equals(messageProvider.getMessage("bundle.key.2", "value"));
	}
}
