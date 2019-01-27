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

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.lang.Nullable;

/**
 * MVC View for a web interaction. Implementations are responsible for rendering
 * content, and exposing the model. A single view exposes multiple model attributes.
 */
public interface View {

	/**
	 * Name of the {@link HttpServletRequest} attribute that contains the response status code.
	 * <p>Note: This attribute is not required to be supported by all View implementations.
	 *
	 * @since 3.0
	 */
	String RESPONSE_STATUS_ATTRIBUTE = View.class.getName() + ".responseStatus";

	/**
	 * Name of the {@link HttpServletRequest} attribute that contains a Map with path variables.
	 * The map consists of String-based URI template variable names as keys and their corresponding
	 * Object-based values -- extracted from segments of the URL and type converted.
	 * <p>Note: This attribute is not required to be supported by all View implementations.
	 *
	 * @since 3.1
	 */
	String PATH_VARIABLES = View.class.getName() + ".pathVariables";

	/**
	 * The {@link org.springframework.http.MediaType} selected during content negotiation,
	 * which may be more specific than the one the View is configured with. For example:
	 * "application/vnd.example-v1+xml" vs "application/*+xml".
	 *
	 * @since 3.2
	 */
	String SELECTED_CONTENT_TYPE = View.class.getName() + ".selectedContentType";


	@Nullable
	default String getContentType() {
		return null;
	}

	//交由具体的子类去执行
	void render(@Nullable Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception;

}
