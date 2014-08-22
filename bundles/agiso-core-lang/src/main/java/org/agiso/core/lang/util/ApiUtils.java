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

import java.util.Collection;
import java.util.List;
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

	public static <T, L extends List<T>> FlowList<T, L> flowList(L list) {
		return new FlowList<T, L>(list);
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

		public FlowSet<T, S> addAll(Collection<? extends T> c) {
			set.addAll(c);
			return this;
		}

		public S getSet() {
			return set;
		}
	}

	public static final class FlowList<T, L extends List<T>> {
		private final L list;

		public FlowList(L list) {
			this.list = list;
		}

		public FlowList<T, L> add(T e) {
			list.add(e);
			return this;
		}

		public FlowList<T, L> addAll(Collection<? extends T> c) {
			list.addAll(c);
			return this;
		}

		public L getList() {
			return list;
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

		public FlowMap<K, V, M> putAll(Map<? extends K, ? extends V> m) {
			map.putAll(m);
			return this;
		}

		public M getMap() {
			return map;
		}
	}
}
