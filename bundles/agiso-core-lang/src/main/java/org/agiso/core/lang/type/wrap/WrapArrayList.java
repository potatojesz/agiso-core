/* org.agiso.core.lang.type.wrap.WrapArrayList (2010-07-02)
 * 
 * WrapArrayList.java
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Implementacja listy obiektów opakowujących oparta o klasę {@link ArrayList}.
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public class WrapArrayList<W extends IWrap<T>, T> extends ArrayList<W> implements IWrapList<W, T> {
	private static final long serialVersionUID = 1L;

	private IWrapFactory<T> wrapFactory;

//	--------------------------------------------------------------------------
	public WrapArrayList(Collection<? extends T> collection, IWrapFactory<T> wrapFactory) {
		super(collection.size());

		this.wrapFactory = wrapFactory;

		wrapAll(collection);
	}

//	--------------------------------------------------------------------------
	public boolean wrap(T t) {
		@SuppressWarnings("unchecked")
		W wrap = (W)this.wrapFactory.wrap(t);
		return add(wrap);
	};

	public void wrap(int index, T t) {
		@SuppressWarnings("unchecked")
		W wrap = (W)this.wrapFactory.wrap(t);
		add(index, wrap);
	};

	public boolean wrapAll(Collection<? extends T> colection) {
		return wrapAll(size(), colection);
	}

	public boolean wrapAll(int index, Collection<? extends T> colection) {
		List<W> wraps = new ArrayList<W>(colection.size());
		for(T t : colection) {
			@SuppressWarnings("unchecked")
			W wrap = (W)this.wrapFactory.wrap(t);
			wraps.add(wrap);
		}
		return addAll(index, wraps);
	}

//	--------------------------------------------------------------------------
	@Override
	public Object clone() {
		@SuppressWarnings("unchecked")
		WrapArrayList<W, T> clone = (WrapArrayList<W, T>)super.clone();
		clone.wrapFactory = wrapFactory;
		return clone;
	}
}
