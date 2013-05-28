/* org.agiso.core.lang.type.wrap.WrapProxyArrayList (2010-07-02)
 *
 * WrapProxyArrayList.java
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
package org.agiso.core.lang.type.wrap;

import java.util.Collection;
import java.util.List;

/**
 * Implementacja listy obiektów opakowujących przekazująca wszystkie operacje
 * do listy obiektów opakowywanych.
 * 
 * @author <a href="mailto:kkopacz@agiso.org">Karol Kopacz</a>
 * @since 1.0
 */
public class WrapProxyArrayList<W extends IWrap<T>, T> extends WrapArrayList<W, T> implements IWrapList<W, T> {
	private static final long serialVersionUID = 1L;

	private List<T> baseList;

//	--------------------------------------------------------------------------
	public WrapProxyArrayList(List<T> baseList, IWrapFactory<T> wrapFactory) {
		super(baseList, wrapFactory);

		this.baseList = baseList;
	}

//	--------------------------------------------------------------------------
	@Override
	public boolean wrap(T t) {
		super.wrap(t);
		return baseList.add(t);
	}

	@Override
	public void wrap(int index, T t) {
		super.wrap(index, t);
		baseList.add(index, t);
	}

	@Override
	public boolean wrapAll(Collection<? extends T> collection) {
		super.wrapAll(collection);
		if(baseList != null && baseList != collection) {
			return baseList.addAll(collection);
		}
		return false;
	}

	@Override
	public boolean wrapAll(int index, Collection<? extends T> collection) {
		super.wrapAll(index, collection);
		if(baseList != null && baseList != collection) {
			return baseList.addAll(index, collection);
		}
		return false;
	}

//	--------------------------------------------------------------------------
	@Override
	public Object clone() {
		@SuppressWarnings("unchecked")
		WrapProxyArrayList<W, T> clone = (WrapProxyArrayList<W, T>)super.clone();
		clone.baseList = baseList;
		return clone;
	}

	@Override
	public boolean add(W o) {
		baseList.add(o.getWrappedObject());
		return super.add(o);
	}

	@Override
	public void add(int index, W element) {
		baseList.add(index, element.getWrappedObject());
		super.add(index, element);
	};

	@Override
	public boolean addAll(Collection<? extends W> c) {
		if(baseList != null) {
			for(W element : c) {
				baseList.add(element.getWrappedObject());
			}
		}
		return super.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends W> c) {
		if(baseList != null) {
			int baseListIndex = index;
			for(W element : c) {
				baseList.add(baseListIndex++, element.getWrappedObject());
			}
		}
		return super.addAll(index, c);
	}

	@Override
	public W set(int index, W element) {
		baseList.set(index, element.getWrappedObject());
		return super.set(index, element);
	};

	@Override
	public W remove(int index) {
		baseList.remove(index);
		return super.remove(index);
	}

	@Override
	public boolean remove(Object o) {
		baseList.remove(o);
		return super.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		@SuppressWarnings("unchecked")
		Collection<W> elements = (Collection<W>)c;
		for(W element : elements) {
			baseList.remove(element.getWrappedObject());
		}
		return super.removeAll(elements);
	}

	@Override
	public void clear() {
		baseList.clear();
		super.clear();
	}
}
