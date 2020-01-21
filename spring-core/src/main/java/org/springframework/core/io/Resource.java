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

package org.springframework.core.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import org.springframework.lang.Nullable;

/**
 * 代表着资源 URL没有实现相对于classpath和servletCOntext等资源的handler，没有提供资源检查，读取等操作。
 * 所以Spring自己进行了定义
 */
public interface Resource extends InputStreamSource {

	boolean exists();

	default boolean isReadable() {
		return exists();
	}

	default boolean isOpen() {
		return false;
	}

	default boolean isFile() {
		return false;
	}

	URL getURL() throws IOException;

	URI getURI() throws IOException;

	File getFile() throws IOException;

	default ReadableByteChannel readableChannel() throws IOException {
		return Channels.newChannel(getInputStream());
	}

	long contentLength() throws IOException;

	long lastModified() throws IOException;

	Resource createRelative(String relativePath) throws IOException;

	@Nullable
	String getFilename();

	String getDescription();

}
