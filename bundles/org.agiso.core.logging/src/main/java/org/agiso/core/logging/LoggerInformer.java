/* org.agiso.core.logging.LoggerInformer (2013-06-04)
 * 
 * LoggerInformer.java
 * 
 * Copyright 2013 agiso.org
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
package org.agiso.core.logging;

/**
 * 
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public interface LoggerInformer {
	// 1. trace (the least serious)
	public boolean isTraceEnabled();

	// 2. debug
	public boolean isDebugEnabled();

	// 3. info
	public boolean isInfoEnabled();

	// 4. warn
	public boolean isWarnEnabled();

	// 5. error
	public boolean isErrorEnabled();

	//TODO: 6. fatal (the most serious)
}
