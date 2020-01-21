/*
 * Copyright 2002-2015 the original author or authors.
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

package org.springframework.beans.factory.config;

import org.springframework.lang.Nullable;

/**
 * 单例bean注册
 */
public interface SingletonBeanRegistry {

	//注册单例的bean
	void registerSingleton(String beanName, Object singletonObject);

	@Nullable
	Object getSingleton(String beanName);

	boolean containsSingleton(String beanName);

	//获取所有单例的bean
	String[] getSingletonNames();

	//获取单例bean的数量
	int getSingletonCount();

	/**
	 * Return the singleton mutex used by this registry (for external collaborators).
	 */
	Object getSingletonMutex();

}
