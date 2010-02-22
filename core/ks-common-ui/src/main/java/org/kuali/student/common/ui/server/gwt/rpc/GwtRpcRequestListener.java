package org.kuali.student.common.ui.server.gwt.rpc;

import javax.servlet.http.HttpServletRequest;

import com.google.gwt.user.server.rpc.RPCRequest;

/**
 * GWT provides hooks into its handling of RPC requests. This is a listener
 * interface for responding to RPC events.
 */
public interface GwtRpcRequestListener {
	/**
	 * This is the name of a method to execute
	 */
	public static final String RPC_REQUEST_METHOD = "rpc.method";

	/**
	 * These are the method parameters
	 */
	public static final String RPC_REQUEST_PARAMETERS = "rpc.params";

	/**
	 * This is a String that represents a serialized object that has come from
	 * the browser
	 */
	public static final String RPC_REQUEST_PAYLOAD = "rpc.requestPayload";

	/**
	 * The serialized object gets decoded into a Method and an Object[] of
	 * parameters
	 */
	public static final String RPC_REQUEST = "rpc.rpcRequest";

	/**
	 * This is the object that will get serialized and returned.
	 */
	public static final String RPC_RETURN_OBJECT = "rpc.returnObject";

	/**
	 * This is the return object after it has been serialized
	 */
	public static final String RPC_RESPONSE_PAYLOAD = "rpc.responsePayload";

	/**
	 * An unexpected exception occurred that we did not catch
	 */
	public static final String RPC_UNEXPECTED_FAILURE = "rpc.unexpected";

	/**
	 * Something happened that we specifically caught
	 */
	public static final String RPC_TRAPPED_EXCEPTION = "rpc.trappedException";

	/**
	 * This event gets fired after GWT has read the bytes associated with this
	 * http request into a string but before it attempts to do anything else.
	 * The string contains a serialized java object.
	 * 
	 * @param request
	 * @param requestPayload
	 */
	public void onBeforeRequestDeserialized(HttpServletRequest request, String requestPayload);

	/**
	 * This event gets fired after GWT has materialized the raw string into an
	 * RPCRequest object. This object contains the Method of a service to invoke
	 * as well as any parameters that will be passed into the method. This event
	 * fires before GWT attempts to invoke the method
	 * 
	 * @param request
	 * @param rpcRequest
	 */
	public void onAfterRequestDeserialized(HttpServletRequest request, RPCRequest rpcRequest);

	/**
	 * This event gets fired after GWT has invoked the service method and
	 * obtained a handle to the result object. This is before it attempts to
	 * serialize the response object.
	 * 
	 * @param request
	 * @param responsePayload
	 */
	public void onBeforeResponseSerialized(HttpServletRequest request, Object result);

	/**
	 * This event gets fired after GWT has serialized the result object.
	 * 
	 * @param request
	 * @param responsePayload
	 */
	public void onAfterResponseSerialized(HttpServletRequest request, String responsePayload);

	/**
	 * This event gets fired if an exception GWT explicitly traps gets thrown
	 * while processing the RPC request
	 * 
	 * @param request
	 * @param e
	 */
	public void doTrappedException(HttpServletRequest request, Exception e);

	/**
	 * This event gets fired if an unexpected exception gets thrown while
	 * processing the RPC request
	 * 
	 * @param request
	 * @param e
	 */
	public void doUnexpectedFailure(HttpServletRequest request, Throwable e);
}
