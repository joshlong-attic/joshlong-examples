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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.keyvalue.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * implementation of counter that works with a Redis store
 */
@Component
public class RedisCounter implements Counter {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Override
	public void decrement(String vName) {
		modifyKey(vName, -1);
	}

	/**
	 * todo hack waiting for Spring Data to trap the issue in the underlying client so that I can use RedisAtomicInteger
	 */
	private void modifyKey(String k, int d) {
		String intValAsString = redisTemplate.opsForValue().get(k);
		if (intValAsString == null) {
			intValAsString = 0 + "";
			redisTemplate.opsForValue().set(k, intValAsString);
		}

		int currentIntVal = Integer.parseInt(intValAsString);
		redisTemplate.opsForValue().set(k, (currentIntVal + d) + "");
	}

	@Override
	public void increment(String vName) {
		modifyKey(vName, +1);
	}

	@Override
	public Integer getCount(String vName) {
		return Integer.parseInt(redisTemplate.opsForValue().get(vName));
	}
}
