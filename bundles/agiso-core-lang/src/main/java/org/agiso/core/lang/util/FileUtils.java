/* org.agiso.core.lang.util.FileUtils (11-02-2014)
 * 
 * FileUtils.java
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public abstract class FileUtils {
	public static final void copyFile(String srcFile, String dstFile) throws IOException {
		copyFile(new File(srcFile), new File(dstFile));
	}

	public static final void copyFile(File srcFile, File dstFile) throws IOException {
		InputStream is = null;
		try {
			is = new FileInputStream(srcFile);

			copyFile(is, dstFile);
		} finally {
			if(is != null) {
				try {
					is.close();
				} catch(Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	public static final void copyFile(InputStream is, File dstFile) throws IOException {
		OutputStream os = null;
		try {
			os = new FileOutputStream(dstFile);

			int len;
			byte[] buf = new byte[1024];
			while((len = is.read(buf)) > 0) {
				os.write(buf, 0, len);
			}
		} finally {
			if(os != null) {
				try {
					os.flush();
					os.close();
				} catch(Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	public static final void copyDir(String srcDir, String dstDir) throws IOException {
		copyFolder(new File(srcDir), new File(dstDir));
	}

	public static final void copyFolder(File srcDir, File dstDir) throws IOException {
		if(srcDir.isDirectory()) {
			if(!dstDir.exists()) {
				dstDir.mkdir();
			}

			String nodes[] = srcDir.list();
			for(String node : nodes) {
				copyFolder(new File(srcDir, node), new File(dstDir, node));
			}
		} else {
			copyFile(srcDir, dstDir);
		}
	}
}
