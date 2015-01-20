/* org.agiso.core.i18n.beans.BeanImplementation2 (28 gru 2014)
 * 
 * BeanImplementation2.java
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
package org.agiso.core.i18n.beans;

import org.agiso.core.i18n.annotation.I18n;

/**
 * 
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public abstract class BeanImplementation2 extends BeanImplementation1 {
	private String field3;

//	--------------------------------------------------------------------------
	@Override
	public String getField2() {
		return super.getField2();
	}

	@I18n(def = "BeanImplementation2 field3 label")
	public String getField3() {
		return field3;
	}
	public void setField3(String field3) {
		this.field3 = field3;
	}
}
