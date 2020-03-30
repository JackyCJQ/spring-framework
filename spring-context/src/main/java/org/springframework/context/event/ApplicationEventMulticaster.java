/*
 * Copyright 2002-2017 the original author or authors.
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

package org.springframework.context.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.ResolvableType;
import org.springframework.lang.Nullable;

/**
 * 应用事件发布器
 * Interface to be implemented by objects that can manage a number of
 * {@link ApplicationListener} objects, and publish events to them.
 *
 * <p>An {@link org.springframework.context.ApplicationEventPublisher}, typically
 * a Spring {@link org.springframework.context.ApplicationContext}, can use an
 * ApplicationEventMulticaster as a delegate for actually publishing events.
 *
 */
public interface ApplicationEventMulticaster {

	void addApplicationListener(ApplicationListener<?> listener);

	void addApplicationListenerBean(String listenerBeanName);

	void removeApplicationListener(ApplicationListener<?> listener);

	void removeApplicationListenerBean(String listenerBeanName);

	void removeAllListeners();

	void multicastEvent(ApplicationEvent event);

	void multicastEvent(ApplicationEvent event, @Nullable ResolvableType eventType);

}
