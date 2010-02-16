package org.kuali.student.common.ui.server.gwt.rpc;

import javax.servlet.http.HttpServletRequest;

import com.google.gwt.user.server.rpc.RPCRequest;

/**
 * Default implementation intentionally does nothing
 */
public class DefaultGwtRpcRequestListenerImpl implements GwtRpcRequestListener {

	@Override
	public void doTrappedException(HttpServletRequest request, Exception e) {
	}

	@Override
	public void doUnexpectedFailure(HttpServletRequest request, Throwable e) {
	}

	@Override
	public void onAfterRequestDeserialized(HttpServletRequest request, RPCRequest rpcRequest) {
	}

	@Override
	public void onAfterResponseSerialized(HttpServletRequest request, String responsePayload) {
	}

	@Override
	public void onBeforeRequestDeserialized(HttpServletRequest request, String requestPayload) {
	}

	@Override
	public void onBeforeResponseSerialized(HttpServletRequest request, Object result) {
	}
}
