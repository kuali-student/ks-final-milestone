package org.kuali.student.common.ui.server.gwt;

import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import static org.kuali.student.common.ui.server.gwt.Constants.*;

/**
 * Base class that extends RemoteServiceServlet so KS has a chance to extend the
 * behavior of the baseline RemoteServiceServlet
 */
@SuppressWarnings("serial")
public abstract class BaseRemoteAbstractServiceServlet extends RemoteServiceServlet {
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
