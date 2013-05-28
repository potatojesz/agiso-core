/* org.agiso.core.lang.type.ObjectStamp (2010-11-28)
 *
 * ObjectStamp.java
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
package org.agiso.core.lang.type;

import org.agiso.core.lang.util.DateUtils;

/**
 * Klasa wykorzystywana do znacznikowania obiektów (wiązania ich z pewnymi
 * wartościami), z założenia wykorzystywana w następującym scenariuszu:
 * <ol>
 *   <li>pobieramy z repozytorium danych złożony obiekt, który musimy cyklicznie
 *       odświeżać i dla którego repozytorium potrafi w prosty sposób wyznaczyć
 *       sumę kontrolną (znacznik) pozwalający na stwierdzenie, czy obiekt
 *       uległ zmianie,</li>
 *   <li>opakowujemy obiekt instancją klasy {@link ObjectStamp} i wiążemy go
 *       z jego sumą kontrolną,</li>
 *   <li>w razie konieczności odświeżenia obiektu sprawdzamy bieżącą wartość
 *       jego sumy kontrolnej w repozytorium danych i pobieramy aktualną wersję
 *       obiektu tylko wtedy, gdy suma ta różni się od wcześniej zapamiętanej</li>.
 * </ol>
 * 
 * @param <T> Typ znacznikowanego obiektu
 * @param <V> Typ wartości znacznika (np. {@link Long} lub {@link String})
 * 
 * @author <a href="mailto:kkopacz@agiso.org">Karol Kopacz</a>
 * @since 1.0
 */
public class ObjectStamp<T, V> {
	private T object;

	private V stampValue;
	private Long lastStampTime;

//	--------------------------------------------------------------------------
	// TODO: Zweryfikować potrzebę istnienia konstruktora
	public ObjectStamp(T object) {
		this.setObject(object);
	}
	public ObjectStamp(T object, V stampValue) {
		this.setObject(object, stampValue);
	}

//	--------------------------------------------------------------------------
	public T getObject() {
		return object;
	}
	// TODO: Zweryfikować potrzebę istnienia metody
	public void setObject(T object) {
		this.object = object;
		this.lastStampTime = null;
	}
	public void setObject(T object, V stampValue) {
		this.object = object;
		this.stampValue = stampValue;
		this.lastStampTime = DateUtils.getDate().getTime();
	}

	public V getStampValue() {
		return stampValue;
	}

//	--------------------------------------------------------------------------
	public void invalidate() {
		this.lastStampTime = null;
	}
	public void resetStampAge() {
		this.lastStampTime = DateUtils.getDate().getTime();
	}
	public boolean isStampOlderThan(long milis) {
		return (lastStampTime == null || (DateUtils.getDate().getTime() - lastStampTime) > milis);
	}
}
