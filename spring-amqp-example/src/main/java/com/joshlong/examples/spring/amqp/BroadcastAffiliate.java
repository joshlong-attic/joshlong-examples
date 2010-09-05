package com.joshlong.examples.spring.amqp;

public class BroadcastAffiliate {

	/**
	 * Gets called when a {@link NewsChannel} publishes news that needs to be received and re-broadcast appropriately.
	 *
	 * @param newsStory the news story
	 * @throws Exception   escape-hatch {@link com.joshlong.examples.spring.amqp.NewsStory}
	 */
	public void breakForNews( NewsStory newsStory ) throws Exception {
		
	}
}
