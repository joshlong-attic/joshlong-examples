/*
 * Copyright 2010-2011 the original author or authors.
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
package com.joshlong.examples.data.counter;

/**
 * Simple class that simply counts a number using Redis as the persistent store
 *
 * @author jlong
 */
public interface Counter {

	/**
	 * decrements the value associated with vName (does not return the client view -
	 * up to the client to reselect that value)
	 */
	void decrement(String vName);

	/**
	 * increments the value associated with vName  (does not return the client view - up to the client to
	 * reselect that value)
	 */
	void increment(String vName);

	/**
	 * returns the actual value at present
	 */
	Integer getCount(String vName);
}
