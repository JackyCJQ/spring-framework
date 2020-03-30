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

package org.springframework.core.env;

/**
 * 继承了属性解析的接口
 */
public interface Environment extends PropertyResolver {

	/**
	 * 获取激活的配置
	 *
	 * @return
	 */
	String[] getActiveProfiles();

	/**
	 * 获取默认的配置
	 *
	 * @return
	 */
	String[] getDefaultProfiles();

	@Deprecated
	boolean acceptsProfiles(String... profiles);

	/**
	 * 接受配置文件,单个的配置
	 *
	 * @param profiles
	 * @return
	 */
	boolean acceptsProfiles(Profiles profiles);

}
