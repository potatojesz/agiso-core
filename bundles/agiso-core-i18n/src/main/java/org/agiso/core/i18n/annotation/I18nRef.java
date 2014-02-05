/* org.agiso.core.i18n.annotation.I18nRef (12-10-2012)
 * 
 * I18nRef.java
 * 
 * Copyright 2012 agiso.org.
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
package org.agiso.core.i18n.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * TODO: Zaimplementować mechanizm referencji do adnotacji I18n poprzez
 * podawanie pełnej nazwy pakietowej do innego obiektu (wyliczenia) opisanego
 * tą adnotacją.
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
@Documented
@Retention(RUNTIME)
@Target({TYPE, METHOD, FIELD})
/* public */ @interface I18nRef {
	public String value();
}
