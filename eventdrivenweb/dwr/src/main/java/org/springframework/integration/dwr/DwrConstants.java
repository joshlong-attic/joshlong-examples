package org.springframework.integration.dwr;

/**
 * constants for the various message headers on inbound {@link org.springframework.integration.Message} messages
 *
 * @since 1.0
 * @author Josh Long
 */
public class DwrConstants {

	/**
	 * the prefix common to all message headers
	 */
	public static final String DWR_PREFIX = DwrConstants.class.getName() ;

	/**
	 * the name of the function to be invoked on the client when a message is pushed down
	 */
	public static final String DWR_SCRIPT_FUNCTION_NAME =  DWR_PREFIX + "CallbackFunctionName";

}
