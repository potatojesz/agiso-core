/* org.agiso.core.lang.util.ReflectUtils (2010-01-17)
 *
 * ReflectUtils.java
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
package org.agiso.core.lang.util;

import java.lang.reflect.Method;

import org.agiso.core.lang.exception.MethodInvocationException;

/**
 * Klasa narzędziowa dostarczająca mechanizmów refleksji.
 * 
 * @author <a href="mailto:kkopacz@agiso.org">Karol Kopacz</a>
 * @since 1.0
 */
public class ReflectUtils {
	/**
	 * Prywatny konstruktor uniemożliwiający instancjonowanie klasy.
	 */
	private ReflectUtils() {
		// Does nothing
	}

//	--------------------------------------------------------------------------
	/**
	 * Wywołuje określoną metodę obiektu przekazując do niej dostarczone parametry.
	 * 
	 * @param target Obiekt, którego metoda ma zostać wywołana.
	 * @param method Nazwa metody do wywołania.
	 * @param args Tablica argumentów metody.
	 * @return Obiekt zwracany przez metodę.
	 */
	public static Object invokeMethod(Object target, String method, Object... args) {
		Class<?>[] types = new Class[args.length];
		for(int index = args.length - 1; index >= 0; index--) {
			types[index] = args[index].getClass();
		}
		try {
			return target.getClass().getMethod(method, types).invoke(target, args);
		} catch(Throwable t) {
			throw new MethodInvocationException(t);
		}
	}

//	--------------------------------------------------------------------------
	/**
	 * <pre>
	 * Method currentMethod = new MethodHelper() {
	 *     {@literal @}Override
	 *     public Method getCurrentMethod() {
	 *         return getClass().getEnclosingMethod();
	 *     }
	 * }.getCurrentMethod();
	 * </pre>
	 * 
	 * @author <a href="mailto:kkopacz@agiso.org">Karol Kopacz</a>
	 * @since 1.0
	 */
	public interface MethodHelper {
		public Method getCurrentMethod();
	}
}
