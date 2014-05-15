/* org.agiso.core.lang.util.ApiUtils (9 kwi 2014)
 * 
 * ApiUtils.java
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

import java.util.Map;
import java.util.Set;

/**
 * 
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public final class ApiUtils {
	public static <T, S extends Set<T>> FlowSet<T, S> flowSet(S set) {
		return new FlowSet<T, S>(set);
	}

	public static <K, V, M extends Map<K, V>> FlowMap<K, V, M> flowMap(M map) {
		return new FlowMap<K, V, M>(map);
	}

//	--------------------------------------------------------------------------
	public static final class FlowSet<T, S extends Set<T>> {
		private final S set;

		public FlowSet(S set) {
			this.set = set;
		}

		public FlowSet<T, S> add(T e) {
			set.add(e);
			return this;
		}

		public S getSet() {
			return set;
		}
	}

	public static final class FlowMap<K, V, M extends Map<K, V>> {
		private final M map;

		public FlowMap(M map) {
			this.map = map;
		}

		public FlowMap<K, V, M> put(K k, V v) {
			map.put(k, v);
			return this;
		}

		public M getMap() {
			return map;
		}
	}
}
