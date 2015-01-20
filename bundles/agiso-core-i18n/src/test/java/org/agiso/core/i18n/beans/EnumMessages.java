/* org.agiso.core.i18n.beans.EnumMessages (20-01-2015)
 * 
 * EnumMessages.java
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
package org.agiso.core.i18n.beans;

import org.agiso.core.i18n.annotation.I18n;

/**
 * 
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public enum EnumMessages {
	@I18n(def = "Enum message 1")
	M1,

	@I18n(def = "Enum message 2 with param {0}")
	M2
}
