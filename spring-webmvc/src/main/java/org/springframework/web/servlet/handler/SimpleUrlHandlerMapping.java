/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.web.servlet.handler;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.util.CollectionUtils;

/**
 * <p>Mappings to bean names can be set via the "mappings" property, in a form
 * accepted by the {@code java.util.Properties} class, like as follows:<br>
 * /welcome.html=ticketController
 * /show.html=ticketController
 * The syntax is {@code PATH=HANDLER_BEAN_NAME}.
 * If the path doesn't begin with a slash, one is prepended.
 */
public class SimpleUrlHandlerMapping extends AbstractUrlHandlerMapping {

	//通过一个map来保存对应的映射关系
	private final Map<String, Object> urlMap = new LinkedHashMap<>();

	//可以通过一个properties来添加映射关系
	public void setMappings(Properties mappings) {

		CollectionUtils.mergePropertiesIntoMap(mappings, this.urlMap);
	}

	//通过map来添加
	public void setUrlMap(Map<String, ?> urlMap) {
		this.urlMap.putAll(urlMap);
	}

	public Map<String, ?> getUrlMap() {
		return this.urlMap;
	}

	@Override
	public void initApplicationContext() throws BeansException {
		super.initApplicationContext();
		//注册映射关系
		registerHandlers(this.urlMap);
	}

	protected void registerHandlers(Map<String, Object> urlMap) throws BeansException {
		if (urlMap.isEmpty()) {
			logger.trace("No patterns in " + formatMappingName());
		} else {
			urlMap.forEach((url, handler) -> {
				// 如果没有不是以/开头，默认会添加上
				if (!url.startsWith("/")) {
					url = "/" + url;
				}
				// Remove whitespace from handler bean name.
				if (handler instanceof String) {
					handler = ((String) handler).trim();
				}
				//进行注册
				registerHandler(url, handler);
			});
			//下面就是为了打印日志用的
			if (logger.isDebugEnabled()) {
				List<String> patterns = new ArrayList<>();
				if (getRootHandler() != null) {
					patterns.add("/");
				}
				if (getDefaultHandler() != null) {
					patterns.add("/**");
				}
				patterns.addAll(getHandlerMap().keySet());
				logger.debug("Patterns " + patterns + " in " + formatMappingName());
			}
		}
	}

}
