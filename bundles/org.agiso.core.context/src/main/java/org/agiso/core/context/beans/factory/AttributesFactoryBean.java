/* org.agiso.core.context.beans.factory.AttributesFactoryBean (2012-02-15)
 * 
 * AttributesFactoryBean.java
 * 
 * Copyright 2012 agiso.org.
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
package org.agiso.core.context.beans.factory;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.agiso.core.context.factory.IAttributesFactory;
import org.agiso.core.logging.Logger;
import org.agiso.core.logging.util.LogUtils;

/**
 * 
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public class AttributesFactoryBean implements IAttributesFactory, Serializable {
	private static final long serialVersionUID = 1L;
	private static final transient Logger logger = LogUtils.getLogger(AttributesFactoryBean.class);

//	--------------------------------------------------------------------------
	private Map<String, Object> attributesMap;

//	--------------------------------------------------------------------------
	public AttributesFactoryBean() {
		logger.debug("Creating " + this.getClass().getSimpleName() + "(" + System.identityHashCode(this) + ")");
	}

//	--------------------------------------------------------------------------
	@PostConstruct
	public void initialize() {
		logger.debug("Initializing " + this.getClass().getSimpleName() + "(" + System.identityHashCode(this) + ")");

		attributesMap = Collections.synchronizedMap(new HashMap<String, Object>());
	}
	@PreDestroy
	public void destroy() {
		logger.debug("Destroing " + this.getClass().getSimpleName() + "(" + System.identityHashCode(this) + ")");

		attributesMap.clear();
	}

//	--------------------------------------------------------------------------
	@Override
	public Map<String, Object> getAttributesMap() {
		return attributesMap;
	}
}
