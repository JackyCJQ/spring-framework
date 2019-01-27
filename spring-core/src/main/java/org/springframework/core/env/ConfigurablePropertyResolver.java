/*
 * Copyright 2002-2016 the original author or authors.
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

import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.lang.Nullable;

/**
 * 扩展了PropertyResolver，提供了一些其他的配置方法
 */
public interface ConfigurablePropertyResolver extends PropertyResolver {


	ConfigurableConversionService getConversionService();

    //设置转换的service
	void setConversionService(ConfigurableConversionService conversionService);

	/**
	 * 设置前缀
	 * Set the prefix that placeholders replaced by this resolver must begin with.
	 */
	void setPlaceholderPrefix(String placeholderPrefix);

	/**
	 * 设置后缀
	 * Set the suffix that placeholders replaced by this resolver must end with.
	 */
	void setPlaceholderSuffix(String placeholderSuffix);

	/**
	 * 设置属性分割符
	 * Specify the separating character between the placeholders replaced by this
	 * resolver and their associated default value, or {@code null} if no such
	 * special character should be processed as a value separator.
	 */
	void setValueSeparator(@Nullable String valueSeparator);

	/**
	 * 设置是否忽略未解析的属性
	 * Set whether to throw an exception when encountering an unresolvable placeholder
	 * nested within the value of a given property.
	 */
	void setIgnoreUnresolvableNestedPlaceholders(boolean ignoreUnresolvableNestedPlaceholders);

	/**
	 * 设置必须的属性
	 * Specify which properties must be present, to be verified by
	 * {@link #validateRequiredProperties()}.
	 */
	void setRequiredProperties(String... requiredProperties);

	/**
	 * 是否验证必须的属性
	 * Validate that each of the properties specified by
	 * {@link #setRequiredProperties} is present and resolves to a
	 * non-{@code null} value.
	 */
	void validateRequiredProperties() throws MissingRequiredPropertiesException;

}
