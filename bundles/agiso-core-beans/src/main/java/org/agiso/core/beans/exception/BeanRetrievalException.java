/* org.agiso.core.beans.exception.BeanRetrievalException (2009-02-12)
 * 
 * BeanRetrievalException.java
 * 
 * Copyright 2009 agiso.org.
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
package org.agiso.core.beans.exception;

import org.agiso.core.lang.exception.BaseRuntimeException;

/**
 * Wyjątek pozyskiwania obiektu z kontekstu aplikacji.
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public class BeanRetrievalException extends BaseRuntimeException {
	private static final long serialVersionUID = 1L;

//	-----------------------------------------------------------------
	private String beanName;

//	-----------------------------------------------------------------
	public BeanRetrievalException(String beanName) {
		super();
		this.beanName = beanName;
	}

	public BeanRetrievalException(String beanName, String message) {
		super(message);
		this.beanName = beanName;
	}

	public BeanRetrievalException(String beanName, Throwable cause) {
		super(cause);
		this.beanName = beanName;
	}

	public BeanRetrievalException(String beanName, String message, Throwable cause) {
		super(message, cause);
		this.beanName = beanName;
	}

//	-----------------------------------------------------------------
	/**
	 * @return Nazwa obiektu do pobrania z kontekstu aplikacji.
	 */
	public String getBeanName() {
		return beanName;
	}
}
