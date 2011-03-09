package com.joshlong.examples.data.counter;


/**
 * Simple class that simply counts a number using Redis as the persistent store
 * 
 * @author jlong
 *
 */
public interface Counter {
	
	/**
	 * decrements the value associated with {@link vName the key name} (does not return the client view - 
	 * up to the client to reselect that value)
	 */
	void decrement(String vName) ;
	
	/**
	 * increments the value associated with {@link vName the key name} (does not return the client view - up to the client to
	 * reselect that value) 
	 */
	void increment (String vName) ;
	 
	/**
	 * returns the actual value
	 * @param vName
	 * @return
	 */
	Integer getCount( String vName) ;
	 
	 
}
