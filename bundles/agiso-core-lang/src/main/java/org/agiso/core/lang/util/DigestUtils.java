/* org.agiso.core.lang.util.DigestUtils (12-02-2014)
 * 
 * DigestUtils.java
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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Comparator;

/**
 * 
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public abstract class DigestUtils {
	/**
	 * @param algorithm
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public static final String countDigest(String algorithm, InputStream is) throws Exception {
		MessageDigest digest = MessageDigest.getInstance(algorithm);

		updateDigest(digest, is);

		return HexUtils.toHexString(digest.digest());
	}

	/**
	 * @param md
	 * @param is
	 * @throws Exception
	 */
	public static final void updateDigest(MessageDigest md, InputStream is) throws Exception {
		DigestInputStream dis = new DigestInputStream(new BufferedInputStream(is), md);

		while(dis.read() != -1);
	}

	/**
	 * @param algorithm
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static final String countDigest(String algorithm, File file) throws Exception {
		if(!file.exists()) {
			throw new FileNotFoundException(file.getPath());
		}

		MessageDigest digest = MessageDigest.getInstance(algorithm);

		String basePath = file.getCanonicalPath();
		if(file.isDirectory()) {
			File[] content = file.listFiles();
			Arrays.sort(content, new Comparator<File>() {
				@Override
				public int compare(File f1, File f2) {
					return f1.getName().compareTo(f2.getName());
				}
			});

			for(File subFile : content) {
				updateDirecotryDigest(basePath, subFile, digest);
			}
		} else {
			updateDigest(digest, new FileInputStream(file));
		}

		return HexUtils.toHexString(digest.digest());
	}

//	--------------------------------------------------------------------------
	private static void updateDirecotryDigest(String path, File file, MessageDigest md) throws Exception {
		String relativePath = file.getCanonicalPath().substring(path.length());
		relativePath = relativePath.replace('\\', '/');		// normalizacja ścieżki
		if(file.isDirectory()) {
			md.update(relativePath.getBytes(Charset.forName("UTF8")));

			File[] content = file.listFiles();
			Arrays.sort(content, new Comparator<File>() {
				@Override
				public int compare(File f1, File f2) {
					return f1.getName().compareTo(f2.getName());
				}
			});

			for(File subFile : content) {
				updateDirecotryDigest(path, subFile, md);
			}
		} else {
			md.update(relativePath.getBytes(Charset.forName("UTF8")));

			updateDigest(md, new FileInputStream(file));
		}
	}
}
