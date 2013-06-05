/* org.agiso.core.lang.classloading.IsoClassLoader (2013-02-26)
 * 
 * IsoClassLoader.java
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
package org.agiso.core.lang.classloading;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.SecureClassLoader;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.agiso.core.lang.classloading.jcl.IsoJarClassLoader;
import org.agiso.core.lang.classloading.jcl.IsoProxyClassLoader;
import org.agiso.core.lang.util.ClassUtils;
import org.xeustechnologies.jcl.JarClassLoader;
import org.xeustechnologies.jcl.ProxyClassLoader;

/**
 * 
 * 
 * @author <a href="mailto:kkopacz@agiso.org">Karol Kopacz</a>
 * @since 1.0
 */
public class IsoClassLoader extends SecureClassLoader {
	private static final Logger logger = Logger.getLogger(IsoClassLoader.class.getName());

//	--------------------------------------------------------------------------
	/**
	 * @param rcl ClassLoader, który bedzie użyty przy wyszukiwaniu plików .jar,
	 *     mających się znaleźć w ścieżce klas.
	 * @param classpath Lista ścieżek dyskowych do plików .jar, które mają być
	 *     włączone do ścieżki klas ClassLoader'a.
	 */
	public IsoClassLoader(ClassLoader rcl, List<String> classpath) {
		this(rcl, classpath, null);
	}
	/**
	 * @param rcl ClassLoader, który bedzie użyty przy wyszukiwaniu plików .jar,
	 *     mających się znaleźć w ścieżce klas oraz przy wyszukiwaniu opcjonalnych
	 *     bibliotek zawierających klasy pomocnicze (np. {@link JarClassLoader}).
	 * @param classpath Lista ścieżek dyskowych do plików .jar, które mają być
	 *     włączone do ścieżki klas ClassLoader'a.
	 * @param libraries Mapa strumieni plików .jar, które mają być włączone do
	 *     ścieżki klas (uwzględniana jedynie gdy <b>rcl</b> wskaże lokalizację
	 *     pliku .jar zawierającego klasę {@link JarClassLoader}.
	 */
	public IsoClassLoader(ClassLoader rcl, List<String> classpath, Map<String, InputStream> libraries) {
		super(createParent(rcl, classpath, libraries));
	}

//	--------------------------------------------------------------------------
	/**
	 * @param rcl
	 * @param classpath
	 * @param iss
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static ClassLoader createParent(ClassLoader rcl, List<String> classpath, Map<String, InputStream> iss) {
		ClassLoader ccl = Thread.currentThread().getContextClassLoader();
		try {
			URL[] urls;
			if(classpath == null || classpath.isEmpty()) {
				urls = new URL[0];
			} else {
				urls = new URL[classpath.size()];

				for(int index = 0, size = classpath.size(); index < size; index++) {
					String entry = classpath.get(index);
					if(entry.startsWith("classpath:")) {
						entry = entry.substring(10);
						urls[index] = rcl.getResource(entry);
					} else {
						urls[index] = new URL(entry);
					}
					if(logger.isLoggable(Level.FINEST)) {
						logger.finest("Common ClasspathResource (" + entry + "): " + urls[index]);
					}
				}
			}

			// Extension Class Loader dostarczający jedynie klas maszyny wirtualnej:
			ClassLoader ecl = ClassLoader.getSystemClassLoader().getParent();
			Thread.currentThread().setContextClassLoader(ecl);

			// Z użyciem ecl tworzymy URLClassLoader'a. Jeśli w zasobach jest biblioteka
			// zawierająca klasę org.xeustechnologies.jcl.JarClassLoader, to klasa ta będzie
			// użyta do obsługi bibliotek jar dostarczanych w formie strumieni (np. z DBMS).
			Class<URLClassLoader> uclc = null;
			try {
				uclc = (Class<URLClassLoader>)ecl.loadClass(URLClassLoader.class.getCanonicalName());
			} catch(ClassNotFoundException cnfe) {
				throw new RuntimeException(cnfe);				// nie powinien wystąpić
			}

			URLClassLoader ucl = null;

			String jclJar = ClassUtils.locate(JarClassLoader.class, rcl);
			String pclJar = ClassUtils.locate(IsoProxyClassLoader.class, rcl);
			if(jclJar != null) {
				ucl = uclc.getConstructor(URL[].class, ClassLoader.class).newInstance(new URL[] {
						new URL("file://" + jclJar),
						new URL("file://" + pclJar)
				}, ecl);

				// JarClassLoader jcl = new JarClassLoader();
				Class<ClassLoader> jclc = (Class<ClassLoader>)ucl.loadClass(IsoJarClassLoader.class.getCanonicalName());
				ClassLoader jcl = jclc.getConstructor(ClassLoader.class).newInstance(ecl);

				// jcl.getLocalLoader().setEnabled(true);				// order = 1	cache
				Object jclcl = jclc.getMethod("getLocalLoader").invoke(jcl);
				jclcl.getClass().getMethod("setEnabled", boolean.class).invoke(jclcl, true);

				// jcl.getCurrentLoader().setEnabled(false);			// order = 2
				jclcl = jclc.getMethod("getCurrentLoader").invoke(jcl);
				jclcl.getClass().getMethod("setEnabled", boolean.class).invoke(jclcl, false);

				// jcl.getParentLoader().setEnabled(false);				// order = 3
				jclcl = jclc.getMethod("getParentLoader").invoke(jcl);
				jclcl.getClass().getMethod("setEnabled", boolean.class).invoke(jclcl, false);

				// jcl.getThreadLoader().setEnabled(false);				// order = 4
				jclcl = jclc.getMethod("getThreadLoader").invoke(jcl);
				jclcl.getClass().getMethod("setEnabled", boolean.class).invoke(jclcl, false);

				if(pclJar != null) {
					// jcl.getSystemLoader().setEnabled(false);			// order = 5
					jclcl = jclc.getMethod("getSystemLoader").invoke(jcl);
					jclcl.getClass().getMethod("setEnabled", boolean.class).invoke(jclcl, false);

					// jcl.addLoader(new IsoProxyClassLoader(scl, 6));	// order = 6	systemowy
					Class<?> pclc = ucl.loadClass(IsoProxyClassLoader.class.getCanonicalName());
					Object pcl = pclc.getConstructor(ClassLoader.class, int.class).newInstance(ecl, 6);
					pclc = ucl.loadClass(ProxyClassLoader.class.getCanonicalName());
					jclc.getMethod("addLoader", pclc).invoke(jcl, pcl);
				} else {	// środowisko uruchomienia testów (brak pliku jar z pcl)
					// jcl.getSystemLoader().setEnabled(true);			// order = 5
					jclcl = jclc.getMethod("getSystemLoader").invoke(jcl);
					jclcl.getClass().getMethod("setEnabled", boolean.class).invoke(jclcl, true);
				}
				if(iss != null && !iss.isEmpty()) {
					Method add = jclc.getMethod("add", InputStream.class);
					for(String key : iss.keySet()) {
						add.invoke(jcl, iss.get(key));
						if(logger.isLoggable(Level.FINEST)) {
							logger.finest("Script ClasspathResoruce: " + key);
						}
					}
				}

				ucl = uclc.getConstructor(URL[].class, ClassLoader.class).newInstance(urls, jcl);
			} else {
				ucl = uclc.getConstructor(URL[].class, ClassLoader.class).newInstance(urls, ecl);
			}

			if(logger.isLoggable(Level.INFO)) {
				logger.info(IsoClassLoader.class.getSimpleName() + " (" + ucl.getParent() + ") created");
			}

			return ucl;
		} catch(Exception e) {
			throw new RuntimeException(e);
		} finally {
			Thread.currentThread().setContextClassLoader(ccl);
		}
	}
}
