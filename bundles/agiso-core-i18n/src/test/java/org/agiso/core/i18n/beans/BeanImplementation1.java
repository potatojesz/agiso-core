/* org.agiso.core.i18n.beans.BeanImplementation1 (4 gru 2014)
 * 
 * BeanImplementation1.java
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
public abstract class BeanImplementation1 implements IBeanInterface {
	private String field1;
	private String field2;

//	--------------------------------------------------------------------------
	@I18n(def = "BeanImplementation1 field1 label")
	public String getField1() {
		return field1;
	}
	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String getField2() {
		return field2;
	}
	public void setField2(String field2) {
		this.field2 = field2;
	}
}
