/* org.agiso.core.lang.exception.MethodInvocationException (2010-01-15)
 *
 * MethodInvocationException.java
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
package org.agiso.core.lang.exception;

/**
 * Wyjątek wywołania metody (z wykorzystaniem mechanizmów refleksji).
 * 
 * @author <a href="mailto:kkopacz@agiso.org">Karol Kopacz</a>
 * @since 1.0
 */
public class MethodInvocationException extends BaseRuntimeException {
	private static final long serialVersionUID = 1L;

//	-----------------------------------------------------------------
	public MethodInvocationException() {
		super();
	}

	public MethodInvocationException(String message) {
		super(message);
	}

	public MethodInvocationException(Throwable cause) {
		super(cause);
	}

	public MethodInvocationException(String message, Throwable cause) {
		super(message, cause);
	}
}
