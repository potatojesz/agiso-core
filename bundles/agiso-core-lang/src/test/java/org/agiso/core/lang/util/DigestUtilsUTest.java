/* org.agiso.core.lang.util.DigestUtilsUTest (19-02-2014)
 * 
 * DigestUtilsUTest.java
 * 
 * Copyright 2014 agiso.org
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
package org.agiso.core.lang.util;

import static org.agiso.core.lang.util.DigestUtils.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import org.testng.annotations.Test;

/**
 * 
 * 
 * @author Karol Kopacz
 */
public class DigestUtilsUTest {
//	static {
//		Logger.getLogger(DigestUtils.class.getName()).setLevel(Level.FINE);
//	}

//	--------------------------------------------------------------------------
	@Test
	public void testCountDigestInputStream() throws Exception {
		InputStream is = new ByteArrayInputStream("Test data".getBytes());
		countDigest("MD5", is);
	}

	@Test
	public void testCountDigestFile() throws Exception {
		File file = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
		countDigest("MD5", file);
	}
}
