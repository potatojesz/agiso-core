/* org.agiso.core.context.beans.SystemContextBean (2012-02-15)
 * 
 * SystemContextBean.java
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
package org.agiso.core.context.beans;

import java.util.Collections;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.agiso.core.beans.exception.BeanRetrievalException;
import org.agiso.core.beans.util.BeanUtils;
import org.agiso.core.context.ISystemContext;
import org.agiso.core.context.factory.IAttributesFactory;
import org.agiso.core.logging.Logger;
import org.agiso.core.logging.util.LogUtils;

/**
 * 
 * 
 * @author Karol Kopacz
 * @since 1.0
 */
public class SystemContextBean implements ISystemContext {
	private static final long serialVersionUID = 1L;
	private static final transient Logger logger = LogUtils.getLogger(SystemContextBean.class);

//	--------------------------------------------------------------------------
	private String flashAttributesFactoryBeanName;
	public void setFlashAttributesFactoryBeanName(String name) {
		flashAttributesFactoryBeanName = name;
	}
//	private Map<String, Object> flashAttributes;
//	@Autowired(required = false) @Qualifier("flashAttributes")
//	public void setFlashAttributes(Map<String, Object> attributes) {
//		flashAttributes = Collections.synchronizedMap(attributes);
//	}

	private String requestAttributesFactoryBeanName;
	public void setRequestAttributesFactoryBeanName(String name) {
		requestAttributesFactoryBeanName = name;
	}
//	private Map<String, Object> requestAttributes;
//	@Autowired(required = false) @Qualifier("requestAttributes")
//	public void setRequestAttributes(Map<String, Object> attributes) {
//		requestAttributes = Collections.synchronizedMap(attributes);
//	}

	private String sessionAttributesFactoryBeanName;
	public void setSessionAttributesFactoryBeanName(String name) {
		sessionAttributesFactoryBeanName = name;
	}
//	private Map<String, Object> sessionAttributes;
//	@Autowired(required = true)
//	public void setSessionAttributes(ISessionAttributes attributes) {
//		sessionAttributes = Collections.synchronizedMap(attributes);
//	}

	private String userAttributesFactoryBeanName;
	public void setUserAttributesFactoryBeanName(String name) {
		userAttributesFactoryBeanName = name;
	}
//	private Map<String, Object> userAttributes;
//	@Autowired(required = true)
//	public void setUserAttributes(Map<String, Object> attributes) {
//		userAttributes = Collections.synchronizedMap(attributes);
//	}

	private String clientIPAttributesFactoryBeanName;
	public void setClientIPAttributesFactoryBeanName(String name) {
		clientIPAttributesFactoryBeanName = name;
	}
//	private Map<String, Object> clientIPAttributes;
//	@Autowired(required = true)
//	public void setClientIPAttributes(Map<String, Object> attributes) {
//		clientIPAttributes = Collections.synchronizedMap(attributes);
//	}

	private String instanceAttributesFactoryBeanName;
	public void setInstanceAttributesFactoryBeanName(String name) {
		instanceAttributesFactoryBeanName = name;
	}
//	private Map<String, Object> instanceAttributes;
//	@Autowired(required = false) @Qualifier("instanceAttributes")
//	public void setInstanceAttributes(Map<String, Object> attributes) {
//		instanceAttributes = Collections.synchronizedMap(attributes);
//	}

	private String globalAttributesFactoryBeanName;
	public void setGlobalAttributesFactoryBeanName(String name) {
		globalAttributesFactoryBeanName = name;
	}
//	private Map<String, Object> globalAttributes;
//	@Autowired(required = false) @Qualifier("applicationAttributes")
//	public void setApplicationAttributes(Map<String, Object> attributes) {
//		applicationAttributes = Collections.synchronizedMap(attributes);
//	}

//	--------------------------------------------------------------------------
	public SystemContextBean() {
		logger.debug("Creating " + this.getClass().getSimpleName() + "(" + System.identityHashCode(this) + ")");

		flashAttributesFactoryBeanName = "flashAttributes";
		requestAttributesFactoryBeanName = "requestAttributes";
		sessionAttributesFactoryBeanName = "sessionAttributes";
		userAttributesFactoryBeanName = "userAttributes";
		clientIPAttributesFactoryBeanName = "clientIPAttributes";
		instanceAttributesFactoryBeanName = "instanceAttributes";
		globalAttributesFactoryBeanName = "globalAttributes";
	}

//	--------------------------------------------------------------------------
	@PostConstruct
	public void initialize() {
		logger.debug("Initializing " + this.getClass().getSimpleName() + "(" + System.identityHashCode(this) + ")");
	}
	@PreDestroy
	public void destroy() {
		logger.debug("Destroing " + this.getClass().getSimpleName() + "(" + System.identityHashCode(this) + ")");
	}

//	--------------------------------------------------------------------------
	@Override
	public Object putString(Scope scope, String key, String value) {
		return getScopeMap(scope).put(key, value);
	}
	@Override
	public Object putAttribute(Scope scope, String key, Object value) {
		return getScopeMap(scope).put(key, value);
	}

	@Override
	public String getString(Scope scope, String key) {
		Object value = getScopeMap(scope).get(key);
		if(value instanceof String) {
			return (String)value;
		}
		return null;
	}
	@Override
	public Object getAttribute(Scope scope, String key) {
		return getScopeMap(scope).get(key);
	}

	@Override
	public String removeString(Scope scope, String key) {
		Map<String, Object> attributes = getScopeMap(scope);
		Object value = attributes.get(key);
		if(value instanceof String) {
			return (String)attributes.remove(key);
		}
		return null;
	}
	@Override
	public Object removeAttribute(Scope scope, String key) {
		return getScopeMap(scope).remove(key);
	}

	@Override
	public Map<String, Object> getAttributes(Scope scope) {
		return Collections.unmodifiableMap(getScopeMap(scope));
	}
	@Override
	public Map<String, Object> getFlashAttributes() {
		return Collections.unmodifiableMap(getScopeMap(Scope.FLASH));
	}
	@Override
	public Map<String, Object> getRequestAttributes() {
		return Collections.unmodifiableMap(getScopeMap(Scope.REQUEST));
	}
	@Override
	public Map<String, Object> getSessionAttributes() {
		return Collections.unmodifiableMap(getScopeMap(Scope.SESSION));
	}
	@Override
	public Map<String, Object> getUserAttributes() {
		return Collections.unmodifiableMap(getScopeMap(Scope.USER));
	}
	@Override
	public Map<String, Object> getClientIPAttributes() {
		return Collections.unmodifiableMap(getScopeMap(Scope.CLIENTIP));
	}
	@Override
	public Map<String, Object> getInstanceAttributes() {
		return Collections.unmodifiableMap(getScopeMap(Scope.INSTANCE));
	}
	@Override
	public Map<String, Object> getGlobalAttributes() {
		return Collections.unmodifiableMap(getScopeMap(Scope.GLOBAL));
	}

	@Override
	public void clearFlashScope() {
		getScopeMap(Scope.FLASH).clear();
	}
	@Override
	public void clearRequestScope() {
		getScopeMap(Scope.REQUEST).clear();
	}
	@Override
	public void clearSessionScope() {
		getScopeMap(Scope.SESSION).clear();
	}

	@Override
	public Map<String, Object> getFa() {
		return getFlashAttributes();
	}
	@Override
	public Map<String, Object> getRa() {
		return getRequestAttributes();
	}
	@Override
	public Map<String, Object> getSa() {
		return getSessionAttributes();
	}
	@Override
	public Map<String, Object> getUa() {
		return getUserAttributes();
	}
	@Override
	public Map<String, Object> getCa() {
		return getClientIPAttributes();
	}
	@Override
	public Map<String, Object> getIa() {
		return getInstanceAttributes();
	}
	@Override
	public Map<String, Object> getGa() {
		return getGlobalAttributes();
	}

//	--------------------------------------------------------------------------
	private Map<String, Object> getScopeMap(Scope scope) {
		switch(scope) {
			case FLASH:
				return getAttributesMap(flashAttributesFactoryBeanName);
			case REQUEST:
				return getAttributesMap(requestAttributesFactoryBeanName);
			case SESSION:
				return getAttributesMap(sessionAttributesFactoryBeanName);
			case USER:
				return getAttributesMap(userAttributesFactoryBeanName);
			case CLIENTIP:
				return getAttributesMap(clientIPAttributesFactoryBeanName);
			case INSTANCE:
				return getAttributesMap(instanceAttributesFactoryBeanName);
			case GLOBAL:
				return getAttributesMap(globalAttributesFactoryBeanName);
		}
		return null;
	}

	private Map<String, Object> getAttributesMap(String factoryBeanName) {
		Object factory = BeanUtils.getBean(factoryBeanName);
		if(factory == null) {
			throw new BeanRetrievalException("Brak fabryki " + factoryBeanName);
		}
		return ((IAttributesFactory)factory).getAttributesMap();
	}
}
