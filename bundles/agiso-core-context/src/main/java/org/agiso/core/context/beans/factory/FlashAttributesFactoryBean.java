/* org.agiso.core.context.beans.factory.FlashAttributesFactoryBean (10.11.2016)
 * 
 * FlashAttributesFactoryBean.java
 * 
 * Copyright 2016 EXPERT SOLUTIONS Sp. z o. o.
 */
package org.agiso.core.context.beans.factory;

import static org.agiso.core.lang.util.ThreadUtils.*;

import java.util.HashMap;
import java.util.Map;

import org.agiso.core.context.factory.IAttributesFactory;

/**
 * 
 * 
 * @author <a href="mailto:kkopacz@exso.pl">Karol Kopacz</a>
 */
public class FlashAttributesFactoryBean implements IAttributesFactory {
	private static final String FLASH_ATTRIBUTES_MAP = "_flash_attributes_map";

//	--------------------------------------------------------------------------
	@Override
	public Map<String, Object> getAttributesMap() {
		@SuppressWarnings("unchecked")
		Map<String, Object> threadAttributesMap = (Map<String, Object>)getAttribute(FLASH_ATTRIBUTES_MAP);
		if(threadAttributesMap == null) {
			threadAttributesMap = new FlashAttributesMap();
			putAttribute(FLASH_ATTRIBUTES_MAP, threadAttributesMap);
		}
		return threadAttributesMap;
	}

//	--------------------------------------------------------------------------
	private class FlashAttributesMap extends HashMap<String, Object> {
		private static final long serialVersionUID = 1L;

		@Override
		public Object get(Object key) {
			final Object result = super.get(key);
			remove(key);
			return result;
		}
	}
}
