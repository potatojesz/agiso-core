/* org.agiso.core.lang.util.ApiUtilsUTest (7 gru 2015)
 * 
 * ApiUtilsUTest.java
 * 
 * Copyright 2015 agiso.org
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

import static org.agiso.core.lang.util.ApiUtils.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.annotations.Test;

/**
 * Testuje poprawność działania metod klasy narzędziowej {@link ApiUtils}.
 *
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public class ApiUtilsUTest {
	@Test
	public void testFlowSet() throws Exception {
		Iterator<?> iterator;

		Set<String> stringSet;
		stringSet = ApiUtils.<String>flowSet().getSet();
		assert stringSet != null;
		assert stringSet.isEmpty();
		assert stringSet instanceof HashSet;

		stringSet = ApiUtils.flowSet(new HashSet<String>()).getSet();
		assert stringSet != null;
		assert stringSet.isEmpty();

		stringSet = ApiUtils.<String>flowSet("A", "B").getSet();
		assert stringSet != null;
		assert stringSet.size() == 2;
		iterator = stringSet.iterator();
		assert iterator.next().equals("A");
		assert iterator.next().equals("B");
		assert !iterator.hasNext();

		Set<Long> longSet;
		longSet = flowSet(new LinkedHashSet<Long>())
						.add(123L)
						.add(234L)
				.getSet();
		assert longSet != null;
		assert longSet.size() == 2;
		iterator = longSet.iterator();
		assert iterator.next().equals(123L);
		assert iterator.next().equals(234L);
		assert !iterator.hasNext();
	}

	@Test
	public void testFlowList() throws Exception {
		Iterator<?> iterator;

		List<String> stringList;
		stringList = ApiUtils.<String>flowList().getList();
		assert stringList != null;
		assert stringList.isEmpty();
		assert stringList instanceof ArrayList;

		stringList = ApiUtils.flowList(new ArrayList<String>()).getList();
		assert stringList != null;
		assert stringList.isEmpty();

		stringList = ApiUtils.<String>flowList("A", "B").getList();
		assert stringList != null;
		assert stringList.size() == 2;
		iterator = stringList.iterator();
		assert iterator.next().equals("A");
		assert iterator.next().equals("B");
		assert !iterator.hasNext();

		List<Long> longList;
		longList = flowList(new ArrayList<Long>())
						.add(123L)
						.add(234L)
				.getList();
		assert longList != null;
		assert longList.size() == 2;
		iterator = longList.iterator();
		assert iterator.next().equals(123L);
		assert iterator.next().equals(234L);
		assert !iterator.hasNext();
	}

	@Test
	public void testFlowMap() throws Exception {
		Iterator<?> iterator;

		Map<String, String> stringMap;
		stringMap = ApiUtils.<String, String>flowMap().getMap();
		assert stringMap != null;
		assert stringMap.isEmpty();
		assert stringMap instanceof HashMap;

		stringMap = ApiUtils.flowMap(new LinkedHashMap<String, String>())
						.put("k1", "v1")
						.put("k2", "v2")
				.getMap();
		assert stringMap != null;
		assert stringMap.size() == 2;
		iterator = stringMap.keySet().iterator();
		assert iterator.next().equals("k1");
		assert iterator.next().equals("k2");
		assert !iterator.hasNext();
		iterator = stringMap.values().iterator();
		assert iterator.next().equals("v1");
		assert iterator.next().equals("v2");
		assert !iterator.hasNext();
	}
}
