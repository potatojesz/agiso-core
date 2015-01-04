/* org.agiso.core.i18n.util.beans.IBeanInterface (4 gru 2014)
 * 
 * IBeanInterface.java
 * 
 * Copyright 2014 agiso.org.
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
package org.agiso.core.i18n.util.beans;

import org.agiso.core.i18n.annotation.I18n;

/**
 * 
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public interface IBeanInterface {
	@I18n(def = "Interface field1 label")
	public String getField1();

	@I18n(def = "Interface field2 label")
	public String getField2();

	@I18n(def = "Interface field3 label")
	public String getField3();

	@I18n(def = "Interface field4 label")
	public String getField4();
}
