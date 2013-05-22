/* org.agiso.core.lang.annotation.InToString (2008-11-05)
 * 
 * InToString.java
 * 
 * Copyright 2008 agiso.org.
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
package org.agiso.core.lang.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.agiso.core.lang.util.ObjectUtils;

/**
 * Steruje generowaniem reprezentacji tekstowej obiektu przez metodę {@link
 * ObjectUtils#toStringBuilder(Object)}. Stosowana do pól obiektu.
 * 
 * @author <a href="mailto:kkopacz@agiso.org">Karol Kopacz</a>
 * @since 1.0
 */
@Documented
@Target(FIELD)
@Retention(RUNTIME)
public @interface InToString {
	/**
	 * Definiuje nazwę pola w reprezentacji tekstowej obiektu (domyślnie pole
	 * będzie posiadało taką nazwę jak w klasie).
	 */
	public String name() default "";

	/**
	 * Określa, czy opisane pole ma być ignorowane podczas tworzenia
	 * reprezentacji testowej. Domyślnie <code>false</code>, co oznacza,
	 * że polejest uwzględniane.
	 */
	public boolean ignore() default false;
}
