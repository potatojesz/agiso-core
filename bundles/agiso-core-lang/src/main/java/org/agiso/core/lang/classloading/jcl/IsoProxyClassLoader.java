/* org.agiso.core.lang.classloading.jcl.IsoProxyClassLoader (2013-02-26)
 * 
 * IsoProxyClassLoader.java
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
package org.agiso.core.lang.classloading.jcl;

import java.io.InputStream;
import java.net.URL;

import org.xeustechnologies.jcl.ProxyClassLoader;

/**
 * 
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public class IsoProxyClassLoader extends ProxyClassLoader {
	private ClassLoader cl;

//	--------------------------------------------------------------------------
	public IsoProxyClassLoader(ClassLoader cl, int order) {
		this.cl = cl;

		this.order = order;
		this.enabled = true;
	}

//	--------------------------------------------------------------------------
	@Override
	public Class<?> loadClass(String className, boolean resolveIt) {
		try {
			return cl.loadClass(className);
		} catch(ClassNotFoundException e) {
			return null;
		}
	}

	@Override
	public InputStream loadResource(String name) {
		return cl.getResourceAsStream(name);
	}

	@Override
	public URL findResource(String name) {
		return null;
	}
}