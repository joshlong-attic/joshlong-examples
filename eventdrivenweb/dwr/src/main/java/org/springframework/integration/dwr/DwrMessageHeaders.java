package org.springframework.integration.dwr;

/**
 * Constants for the various message headers on inbound {@link org.springframework.integration.Message} messages.
 *
 * @author Josh Long
 * @since 1.0
 */
public class DwrMessageHeaders {

	/**
	 * the prefix common to all message headers
	 */
	public static final String DWR_PREFIX = DwrMessageHeaders.class.getName() ;

	/**
	 * the name of the function to be invoked on the client when a message is pushed down
	 */
	public static final String DWR_SCRIPT_FUNCTION_NAME =  DWR_PREFIX + "CallbackFunctionName";

	/**
	 * the DWR session to which the message should be routed, in specific.
	 *
	 * the session is a client-created value that DWR uses to startup an adapter
	 *
	 */
	public static final String DWR_TARGET_SESSION_ID =  DWR_PREFIX + "TargetDwrSessionId";

}
