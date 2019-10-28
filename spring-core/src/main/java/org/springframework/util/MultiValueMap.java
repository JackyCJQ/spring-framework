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

package org.springframework.util;

import java.util.List;
import java.util.Map;

import org.springframework.lang.Nullable;

public interface MultiValueMap<K, V> extends Map<K, List<V>> {

	/**
	 * Return the first value for the given key.
	 */
	@Nullable
	V getFirst(K key);

	/**
	 * Add the given single value to the current list of values for the given key.
	 */
	void add(K key, @Nullable V value);

	/**
	 * Add all the values of the given list to the current list of values for the given key.
	 */
	void addAll(K key, List<? extends V> values);

	/**
	 * Add all the values of the given {@code MultiValueMap} to the current values.
	 */
	void addAll(MultiValueMap<K, V> values);

	/**
	 * Set the given single value under the given key.
	 */
	void set(K key, @Nullable V value);

	/**
	 * Set the given values under.
	 */
	void setAll(Map<K, V> values);

	/**
	 * Returns the first values contained in this {@code MultiValueMap}.
	 */
	Map<K, V> toSingleValueMap();

}
