/* org.agiso.core.lang.exception.IException (2010-01-16)
 * 
 * IException.java
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

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Interfejs dla wszystkich wyjątków czasu wykonania.
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public interface IException {
	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	public String getMessage();

	/* (non-Javadoc)
	 * @see java.lang.Throwable#getLocalizedMessage()
	 */
	public String getLocalizedMessage();

	/* (non-Javadoc)
	 * @see java.lang.Throwable#getCause()
	 */
	public Throwable getCause();

	/* (non-Javadoc)
	 * @see java.lang.Throwable#initCause(java.lang.Throwable)
	 */
	public Throwable initCause(Throwable cause);

	/* (non-Javadoc)
	 * @see java.lang.Throwable#printStackTrace()
	 */
	public void printStackTrace();

	/* (non-Javadoc)
	 * @see java.lang.Throwable#printStackTrace(java.io.PrintStream)
	 */
	public void printStackTrace(PrintStream s);

	/* (non-Javadoc)
	 * @see java.lang.Throwable#printStackTrace(java.io.PrintWriter)
	 */
	public void printStackTrace(PrintWriter s);

	/* (non-Javadoc)
	 * @see java.lang.Throwable#fillInStackTrace()
	 */
	public Throwable fillInStackTrace();

	/* (non-Javadoc)
	 * @see java.lang.Throwable#getStackTrace()
	 */
	public StackTraceElement[] getStackTrace();

	/* (non-Javadoc)
	 * @see java.lang.Throwable#setStackTrace(java.lang.StackTraceElement[])
	 */
	public void setStackTrace(StackTraceElement[] stackTrace);
}
