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

package org.springframework.core;

/**
 * 别名注册管理
 */
public interface AliasRegistry {
	/**
	 * 注册一个别名
	 *
	 * @param name
	 * @param alias
	 */
	void registerAlias(String name, String alias);

	/**
	 * 移除一个别名
	 *
	 * @param alias
	 */
	void removeAlias(String alias);

	/**
	 * 判断 有一个名字是否是别名
	 *
	 * @param name
	 * @return
	 */
	boolean isAlias(String name);

	/**
	 * 获取一个名字对应的所有的别名
	 *
	 * @param name
	 * @return
	 */
	String[] getAliases(String name);

}
