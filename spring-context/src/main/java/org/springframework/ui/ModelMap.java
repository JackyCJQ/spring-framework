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

package org.springframework.ui;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.core.Conventions;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * 扩展了linkedhashMap
 * Implementation of {@link java.util.Map} for use when building model data for use
 * with UI tools.
 */
@SuppressWarnings("serial")
public class ModelMap extends LinkedHashMap<String, Object> {

	public ModelMap() {
	}

	public ModelMap(String attributeName, @Nullable Object attributeValue) {

		addAttribute(attributeName, attributeValue);
	}

	public ModelMap(Object attributeValue) {
		addAttribute(attributeValue);
	}


	/**
	 * Add the supplied attribute under the supplied name.
	 * 在返回这个容器 热不是是否成功的标志
	 */
	public ModelMap addAttribute(String attributeName, @Nullable Object attributeValue) {
		Assert.notNull(attributeName, "Model attribute name must not be null");
		put(attributeName, attributeValue);
		return this;
	}


	public ModelMap addAttribute(Object attributeValue) {
		Assert.notNull(attributeValue, "Model object must not be null");
		if (attributeValue instanceof Collection && ((Collection<?>) attributeValue).isEmpty()) {
			return this;
		}
		//集合中的类型的名字
		//如果是两种类型相同的集合 后者数据会覆盖前者数据
		return addAttribute(Conventions.getVariableName(attributeValue), attributeValue);
	}

	/**
	 * Copy all attributes in the supplied {@code Collection} into this
	 * {@code Map}, using attribute name generation for each element.
	 *
	 * @see #addAttribute(Object)
	 */
	public ModelMap addAllAttributes(@Nullable Collection<?> attributeValues) {
		if (attributeValues != null) {
			for (Object attributeValue : attributeValues) {
				addAttribute(attributeValue);
			}
		}
		return this;
	}

	/**
	 * Copy all attributes in the supplied {@code Map} into this {@code Map}.
	 *
	 * @see #addAttribute(String, Object)
	 */
	public ModelMap addAllAttributes(@Nullable Map<String, ?> attributes) {
		if (attributes != null) {
			putAll(attributes);
		}
		return this;
	}

	/**
	 * Copy all attributes in the supplied {@code Map} into this {@code Map},
	 * with existing objects of the same name taking precedence (i.e. not getting
	 * replaced).
	 */
	public ModelMap mergeAttributes(@Nullable Map<String, ?> attributes) {
		if (attributes != null) {
			attributes.forEach((key, value) -> {
				if (!containsKey(key)) {
					put(key, value);
				}
			});
		}
		return this;
	}

	public boolean containsAttribute(String attributeName) {
		return containsKey(attributeName);
	}

}
