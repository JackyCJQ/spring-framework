/*
 * Copyright 2002-2012 the original author or authors.
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

package org.springframework.core;

import org.springframework.lang.Nullable;

/**
 * 属性配置获取处理器
 */
public interface AttributeAccessor {

	//设置属性
	void setAttribute(String name, @Nullable Object value);

	/**
	 * 获取属性
	 */
	@Nullable
	Object getAttribute(String name);

	/**
	 * 根据name移除掉一个属性
	 */
	@Nullable
	Object removeAttribute(String name);

	/**
	 * 判断是否包含某个属性
	 */
	boolean hasAttribute(String name);

	/**
	 * 获取所有的属性的名字
	 */
	String[] attributeNames();

}
