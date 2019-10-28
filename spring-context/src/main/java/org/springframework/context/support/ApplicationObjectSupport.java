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

package org.springframework.context.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Convenient superclass for application objects that want to be aware of
 * the application context, e.g. for custom lookup of collaborating beans
 * or for context-specific resource access. It saves the application
 * context reference and provides an initialization callback method.
 * Furthermore, it offers numerous convenience methods for message lookup.
 * <p>
 * <p>There is no requirement to subclass this class: It just makes things
 * a little easier if you need access to the context, e.g. for access to
 * file resources or to the message source. Note that many application
 * objects do not need to be aware of the application context at all,
 * as they can receive collaborating beans via bean references.
 * <p>
 * <p>Many framework classes are derived from this class, particularly
 * within the web support.
 */
public abstract class ApplicationObjectSupport implements ApplicationContextAware {

	protected final Log logger = LogFactory.getLog(getClass());

	@Nullable
	private ApplicationContext applicationContext;

	@Nullable
	private MessageSourceAccessor messageSourceAccessor;


	//在启动的时候注入进来
	@Override
	public final void setApplicationContext(@Nullable ApplicationContext context) throws BeansException {
		//如果没有要求注入
		if (context == null && !isContextRequired()) {
			this.applicationContext = null;
			this.messageSourceAccessor = null;
		} else if (this.applicationContext == null) {
			// 要求出入的上下文为ApplicationContext.class的一个实例
			if (!requiredContextClass().isInstance(context)) {
				throw new ApplicationContextException(
						"Invalid application context: needs to be of type [" + requiredContextClass().getName() + "]");
			}
			//如果要求注入spring的application则才进行注入
			this.applicationContext = context;
			this.messageSourceAccessor = new MessageSourceAccessor(context);
			//注入之后开始进行初始化
			initApplicationContext(context);
		} else {
			if (this.applicationContext != context) {
				throw new ApplicationContextException(
						"Cannot reinitialize with different application context: current one is [" +
								this.applicationContext + "], passed-in one is [" + context + "]");
			}
		}
	}

	protected boolean isContextRequired() {
		return false;
	}

	protected Class<?> requiredContextClass() {
		return ApplicationContext.class;
	}

	//初始化容器
	protected void initApplicationContext(ApplicationContext context) throws BeansException {
		initApplicationContext();
	}

	//被子类重写
	protected void initApplicationContext() throws BeansException {
	}

	@Nullable
	public final ApplicationContext getApplicationContext() throws IllegalStateException {
		//如果要求注入 但是为空的时候就报错了
		if (this.applicationContext == null && isContextRequired()) {
			throw new IllegalStateException(
					"ApplicationObjectSupport instance [" + this + "] does not run in an ApplicationContext");
		}
		return this.applicationContext;
	}

	protected final ApplicationContext obtainApplicationContext() {
		ApplicationContext applicationContext = getApplicationContext();
		Assert.state(applicationContext != null, "No ApplicationContext");
		return applicationContext;
	}

	@Nullable
	protected final MessageSourceAccessor getMessageSourceAccessor() throws IllegalStateException {
		//如果要求获取到， 但是为空 则报错
		if (this.messageSourceAccessor == null && isContextRequired()) {
			throw new IllegalStateException(
					"ApplicationObjectSupport instance [" + this + "] does not run in an ApplicationContext");
		}
		return this.messageSourceAccessor;
	}

}
