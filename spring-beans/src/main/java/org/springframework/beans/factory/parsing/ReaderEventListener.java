/*
 * Copyright 2002-2007 the original author or authors.
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

package org.springframework.beans.factory.parsing;

import java.util.EventListener;

/**
 * 读事件监听器
 */
public interface ReaderEventListener extends EventListener {

	/**
	 * Notification that the given defaults has been registered.
	 */
	void defaultsRegistered(DefaultsDefinition defaultsDefinition);

	/**
	 * Notification that the given component has been registered.
	 */
	void componentRegistered(ComponentDefinition componentDefinition);

	/**
	 * Notification that the given alias has been registered.
	 */
	void aliasRegistered(AliasDefinition aliasDefinition);

	/**
	 * Notification that the given import has been processed.
	 */
	void importProcessed(ImportDefinition importDefinition);

}
