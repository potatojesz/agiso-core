/* org.agiso.core.lang.exception.NotImplementedException (07-02-2013)
 * 
 * NotImplementedException.java
 * 
 * Copyright 2013 agiso.org.
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
 * Wyjątek metody wykorzystywany do oznaczania metod, które jeszcze nie
 * zostały zaimplementowane, ale planowane jest ich wypełnienie.
 * 
 * @deprecated W celu oznaczenia metod do implementacji.
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public class NotImplementedException extends BaseRuntimeException {
	private static final long serialVersionUID = 1L;

//	-----------------------------------------------------------------
	private String className;
	private String methodName;

//	-----------------------------------------------------------------
	public NotImplementedException() {
		int depth = 0;
		StackTraceElement[] trace = Thread.currentThread().getStackTrace();
		for(StackTraceElement traceElement : trace) {
			if(traceElement.getClassName().equals(this.getClass().getName())) {
				break;
			}
			depth++;
		}

		StackTraceElement traceElement = trace[depth + 1];

		className = traceElement.getClassName();
		methodName = traceElement.getMethodName();
	}

//	-----------------------------------------------------------------
	public String getClassName() {
		return className;
	}

	public String getMethodName() {
		return methodName;
	}
}
