package org.kuali.student.web;

import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import static org.kuali.student.web.Constants.*;

public class KualiRemoteServiceServlet extends RemoteServiceServlet {
	private static final long serialVersionUID = 1L;
	RPCUtils utils = new RPCUtils();

	/**
	 * Extract information about the RPC call, transform it into a human
	 * friendly format and place it on the request so ClickTrailFilter can store
	 * it in the session
	 */
	@Override
	protected void onAfterRequestDeserialized(RPCRequest rpcRequest) {
		getThreadLocalRequest().setAttribute(RPC_METHOD_KEY, utils.toString(rpcRequest.getMethod()));
		getThreadLocalRequest().setAttribute(RPC_PARAMETERS_KEY, utils.toXML(rpcRequest.getParameters()));
		super.onAfterRequestDeserialized(rpcRequest);
	}

}
