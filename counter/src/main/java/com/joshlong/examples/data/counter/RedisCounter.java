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
