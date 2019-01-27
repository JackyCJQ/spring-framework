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

package org.springframework.web.servlet;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

/**
 * 执行链，包括interceptors以及对应的handler
 */
public class HandlerExecutionChain {

	private static final Log logger = LogFactory.getLog(HandlerExecutionChain.class);

	//对应的handler
	private final Object handler;

	//所有的interceptors
	@Nullable
	private HandlerInterceptor[] interceptors;
	//此执行链中所有应用的HandlerInterceptor
	@Nullable
	private List<HandlerInterceptor> interceptorList;

	private int interceptorIndex = -1;


	/**
	 * 具体的handler还是外面传入的
	 * Create a new HandlerExecutionChain.
	 *
	 * @param handler the handler object to execute
	 */
	public HandlerExecutionChain(Object handler) {
		this(handler, (HandlerInterceptor[]) null);
	}

	/**
	 * Create a new HandlerExecutionChain.
	 *
	 * @param handler      the handler object to execute
	 * @param interceptors the array of interceptors to apply
	 *                     (in the given order) before the handler itself executes
	 */
	public HandlerExecutionChain(Object handler, @Nullable HandlerInterceptor... interceptors) {
		if (handler instanceof HandlerExecutionChain) {
			HandlerExecutionChain originalChain = (HandlerExecutionChain) handler;
			this.handler = originalChain.getHandler();
			this.interceptorList = new ArrayList<>();
			//把origin中的interceptors融合过来
			CollectionUtils.mergeArrayIntoCollection(originalChain.getInterceptors(), this.interceptorList);
			//在把后面传递过来的在融合进来
			CollectionUtils.mergeArrayIntoCollection(interceptors, this.interceptorList);
		} else {
			this.handler = handler;
			this.interceptors = interceptors;
		}
	}


	/**
	 * Return the handler object to execute.
	 */
	public Object getHandler() {
		return this.handler;
	}

	public void addInterceptor(HandlerInterceptor interceptor) {
		initInterceptorList().add(interceptor);
	}

	public void addInterceptors(HandlerInterceptor... interceptors) {
		//一次添加多个
		if (!ObjectUtils.isEmpty(interceptors)) {
			CollectionUtils.mergeArrayIntoCollection(interceptors, initInterceptorList());
		}
	}

	//初始化拦截器链
	private List<HandlerInterceptor> initInterceptorList() {
		if (this.interceptorList == null) {
			this.interceptorList = new ArrayList<>();
			//如果所有的拦截器不为空，把所有的拦截器 加入到
			if (this.interceptors != null) {
				// An interceptor array specified through the constructor
				CollectionUtils.mergeArrayIntoCollection(this.interceptors, this.interceptorList);
			}
		}
		this.interceptors = null;
		return this.interceptorList;
	}

	/**
	 * Return the array of interceptors to apply (in the given order).
	 *
	 * @return the array of HandlerInterceptors instances (may be {@code null})
	 */
	@Nullable
	public HandlerInterceptor[] getInterceptors() {
		if (this.interceptors == null && this.interceptorList != null) {
			//进行赋值操作
			this.interceptors = this.interceptorList.toArray(new HandlerInterceptor[0]);
		}
		return this.interceptors;
	}


	/**
	 * Apply preHandle methods of registered interceptors.
	 *
	 * @return {@code true} if the execution chain should proceed with the
	 * next interceptor or the handler itself. Else, DispatcherServlet assumes
	 * that this interceptor has already dealt with the response itself.
	 */
	boolean applyPreHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取所有的getInterceptors()
		HandlerInterceptor[] interceptors = getInterceptors();
		//如果存在interceptors
		if (!ObjectUtils.isEmpty(interceptors)) {
			for (int i = 0; i < interceptors.length; i++) {
				HandlerInterceptor interceptor = interceptors[i];
				//进行处理
				if (!interceptor.preHandle(request, response, this.handler)) {
					triggerAfterCompletion(request, response, null);
					return false;
				}
				//标记执行到了哪个拦截器的索引
				this.interceptorIndex = i;
			}
		}
		return true;
	}

	void applyPostHandle(HttpServletRequest request, HttpServletResponse response, @Nullable ModelAndView mv)
			throws Exception {
		HandlerInterceptor[] interceptors = getInterceptors();
		if (!ObjectUtils.isEmpty(interceptors)) {
			//后处理过程 要把执行顺序反过来
			for (int i = interceptors.length - 1; i >= 0; i--) {
				HandlerInterceptor interceptor = interceptors[i];
				interceptor.postHandle(request, response, this.handler, mv);
			}
		}
	}

	/**
	 * Trigger afterCompletion callbacks on the mapped HandlerInterceptors.
	 * Will just invoke afterCompletion for all interceptors whose preHandle invocation
	 * has successfully completed and returned true.
	 */
	void triggerAfterCompletion(HttpServletRequest request, HttpServletResponse response, @Nullable Exception ex)
			throws Exception {

		HandlerInterceptor[] interceptors = getInterceptors();
		if (!ObjectUtils.isEmpty(interceptors)) {
			//执行到哪一个，在返回执行afterCompletion方法
			for (int i = this.interceptorIndex; i >= 0; i--) {
				HandlerInterceptor interceptor = interceptors[i];
				try {
					//在触发每一个执行完之后的
					interceptor.afterCompletion(request, response, this.handler, ex);
				} catch (Throwable ex2) {
					logger.error("HandlerInterceptor.afterCompletion threw exception", ex2);
				}
			}
		}
	}

	/**
	 * Apply afterConcurrentHandlerStarted callback on mapped AsyncHandlerInterceptors.
	 */
	void applyAfterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response) {
		HandlerInterceptor[] interceptors = getInterceptors();
		if (!ObjectUtils.isEmpty(interceptors)) {
			//还是反序
			for (int i = interceptors.length - 1; i >= 0; i--) {
				if (interceptors[i] instanceof AsyncHandlerInterceptor) {
					try {
						//转换为异步执行
						AsyncHandlerInterceptor asyncInterceptor = (AsyncHandlerInterceptor) interceptors[i];
						asyncInterceptor.afterConcurrentHandlingStarted(request, response, this.handler);
					} catch (Throwable ex) {
						logger.error("Interceptor [" + interceptors[i] + "] failed in afterConcurrentHandlingStarted", ex);
					}
				}
			}
		}
	}


	/**
	 * Delegates to the handler and interceptors' {@code toString()}.
	 */
	@Override
	public String toString() {
		Object handler = getHandler();
		StringBuilder sb = new StringBuilder();
		sb.append("HandlerExecutionChain with [").append(handler).append("] and ");
		if (this.interceptorList != null) {
			sb.append(this.interceptorList.size());
		} else if (this.interceptors != null) {
			sb.append(this.interceptors.length);
		} else {
			sb.append(0);
		}
		return sb.append(" interceptors").toString();
	}

}
