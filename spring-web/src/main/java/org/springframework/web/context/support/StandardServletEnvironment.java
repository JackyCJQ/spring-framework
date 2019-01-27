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

package org.springframework.web.context.support;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.PropertySource.StubPropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.jndi.JndiLocatorDelegate;
import org.springframework.jndi.JndiPropertySource;
import org.springframework.lang.Nullable;
import org.springframework.web.context.ConfigurableWebEnvironment;

/**
 * 默认使用StandardServletEnvironment作为运行的环境
 * {@link Environment} implementation to be used by {@code Servlet}-based web
 * applications. All web-related (servlet-based) {@code ApplicationContext} classes
 * initialize an instance by default.
 * <p>
 * <p>Contributes {@code ServletConfig}, {@code ServletContext}, and JNDI-based
 * {@link PropertySource} instances. See {@link #customizePropertySources} method
 * documentation for details.
 *
 * @author Chris Beams
 * @see StandardEnvironment
 * @since 3.1
 */
public class StandardServletEnvironment extends StandardEnvironment implements ConfigurableWebEnvironment {

	//servlet 初始化的参数
	public static final String SERVLET_CONTEXT_PROPERTY_SOURCE_NAME = "servletContextInitParams";
    //servletConfig	初始化的参数
	public static final String SERVLET_CONFIG_PROPERTY_SOURCE_NAME = "servletConfigInitParams";
    //jndi的属性配置
	public static final String JNDI_PROPERTY_SOURCE_NAME = "jndiProperties";


	/**
	 * Customize the set of property sources with those contributed by superclasses as
	 * well as those appropriate for standard servlet-based environments:
	 * <ul>
	 * <li>{@value #SERVLET_CONFIG_PROPERTY_SOURCE_NAME}
	 * <li>{@value #SERVLET_CONTEXT_PROPERTY_SOURCE_NAME}
	 * <li>{@value #JNDI_PROPERTY_SOURCE_NAME}
	 * </ul>
	 * <p>Properties present in {@value #SERVLET_CONFIG_PROPERTY_SOURCE_NAME} will
	 * take precedence over those in {@value #SERVLET_CONTEXT_PROPERTY_SOURCE_NAME}, and
	 * properties found in either of the above take precedence over those found in
	 * {@value #JNDI_PROPERTY_SOURCE_NAME}.
	 * <p>Properties in any of the above will take precedence over system properties and
	 * environment variables contributed by the {@link StandardEnvironment} superclass.
	 * <p>The {@code Servlet}-related property sources are added as
	 * {@link StubPropertySource stubs} at this stage, and will be
	 * {@linkplain #initPropertySources(ServletContext, ServletConfig) fully initialized}
	 * once the actual {@link ServletContext} object becomes available.
	 */
	@Override
	protected void customizePropertySources(MutablePropertySources propertySources) {
		//添加这两个配置的环境
		propertySources.addLast(new StubPropertySource(SERVLET_CONFIG_PROPERTY_SOURCE_NAME));
		propertySources.addLast(new StubPropertySource(SERVLET_CONTEXT_PROPERTY_SOURCE_NAME));
		//如果是jndi环境则添加进jndi的配置
		if (JndiLocatorDelegate.isDefaultJndiEnvironmentAvailable()) {
			propertySources.addLast(new JndiPropertySource(JNDI_PROPERTY_SOURCE_NAME));
		}
		//调用父类在添加系统的配置
		super.customizePropertySources(propertySources);
	}

	//通过servletContext,ServletConfig配置中获取对应的属性配置
	@Override
	public void initPropertySources(@Nullable ServletContext servletContext, @Nullable ServletConfig servletConfig) {
		WebApplicationContextUtils.initServletPropertySources(getPropertySources(), servletContext, servletConfig);
	}

}
