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
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public abstract class DigestUtils {
	private static final Level LOG_LEVEL = Level.FINE;
	private static final Logger logger = Logger.getLogger(DigestUtils.class.getName());

//	--------------------------------------------------------------------------
	/**
	 * @param algorithm
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public static final String countDigest(final String algorithm, final InputStream is) throws Exception {
		final MessageDigest md = MessageDigest.getInstance(algorithm);

		updateDigest(md, is);

		if(logger.isLoggable(LOG_LEVEL)) {
			final String result = HexUtils.toHexString(md.digest());
			if(is instanceof FileNameInputStream) {
				logger.log(LOG_LEVEL, result + ": File " + ((FileNameInputStream)is).path);
			} else if(is instanceof PathNameInputStream) {
				logger.log(LOG_LEVEL, result + ": Path " + ((PathNameInputStream)is).path);
			} else {
				logger.log(LOG_LEVEL, result + ": " + is.getClass().getName()
						+ "@" + Integer.toHexString(System.identityHashCode(is)));
			}
			return result;
		} else {
			return HexUtils.toHexString(md.digest());
		}
	}

	/**
	 * @param md
	 * @param is
	 * @throws Exception
	 */
	public static final void updateDigest(final MessageDigest md, final InputStream is) throws Exception {
		final DigestInputStream dis = new DigestInputStream(new BufferedInputStream(is), md);

		while(dis.read() != -1);
	}

	/**
	 * @param algorithm
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static final String countDigest(final String algorithm, final File file) throws Exception {
		if(!file.exists()) {
			throw new FileNotFoundException(file.getPath());
		}

		final MessageDigest md = MessageDigest.getInstance(algorithm);

		final String basePath = file.getCanonicalPath();
		if(file.isDirectory()) {
			final File[] content = file.listFiles();
			Arrays.sort(content, new Comparator<File>() {
				@Override
				public int compare(File f1, File f2) {
					return f1.getName().compareTo(f2.getName());
				}
			});

			for(File subFile : content) {
				updateDirecotryDigest(basePath, subFile, md);
			}
		} else {
			updateDigest(md, new FileInputStream(file));
			if(logger.isLoggable(LOG_LEVEL)) {
				countDigest(md.getAlgorithm(), new FileNameInputStream(file, file.getName()));
			}
		}

		if(file.isDirectory() && logger.isLoggable(LOG_LEVEL)) {
			final String result = HexUtils.toHexString(md.digest());
			logger.log(LOG_LEVEL, result + ": Dir  "+ file.getPath());
			return result;
		} else {
			return HexUtils.toHexString(md.digest());
		}
	}

//	--------------------------------------------------------------------------
	private static void updateDirecotryDigest(final String path, final File file, MessageDigest md) throws Exception {
		String relativePath = file.getCanonicalPath().substring(path.length());
		relativePath = relativePath.replace('\\', '/');		// normalizacja ścieżki
		if(file.isDirectory()) {
			md.update(relativePath.getBytes(Charset.forName("UTF8")));
			if(logger.isLoggable(LOG_LEVEL)) {
				countDigest(md.getAlgorithm(), new PathNameInputStream(relativePath));
			}

			final File[] content = file.listFiles();
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
			if(logger.isLoggable(LOG_LEVEL)) {
				countDigest(md.getAlgorithm(), new PathNameInputStream(relativePath));
			}

			updateDigest(md, new FileInputStream(file));
			if(logger.isLoggable(LOG_LEVEL)) {
				countDigest(md.getAlgorithm(), new FileNameInputStream(file, relativePath));
			}
		}
	}
}

//	--------------------------------------------------------------------------
/**
 * 
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
class FileNameInputStream extends FileInputStream {
	final String path;

	public FileNameInputStream(File file, String path) throws FileNotFoundException {
		super(file);

		this.path = path;
	}
}

/**
 * 
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
class PathNameInputStream extends ByteArrayInputStream {
	final String path;

	public PathNameInputStream(String path) {
		super(path.getBytes(Charset.forName("UTF8")));

		this.path = path;
	}
}