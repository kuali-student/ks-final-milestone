package org.kuali.student.common.ui.server.gwt;

import org.kuali.rice.core.config.ConfigContext;

import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import static org.kuali.student.common.ui.server.gwt.Constants.*;

/**
 * Base class that extends RemoteServiceServlet so we can extend the behavior of
 * GWT's RemoteServiceServlet
 */
@SuppressWarnings("serial")
public abstract class BaseRemoteAbstractServiceServlet extends RemoteServiceServlet {
	RPCUtils utils = new RPCUtils();
	boolean httpRequestDebugMode = "true".equalsIgnoreCase(ConfigContext.getCurrentContextConfig().getProperty(HTTP_REQUEST_DEBUG_MODE));

	/**
	 * Extract information about the RPC call, transform it into a human
	 * friendly format and place it on the request so it can be processed later
	 * on
	 */
	@Override
	protected void onAfterRequestDeserialized(RPCRequest rpcRequest) {
		if (httpRequestDebugMode) {
			getThreadLocalRequest().setAttribute(RPC_METHOD_KEY, utils.toString(rpcRequest.getMethod()));
			getThreadLocalRequest().setAttribute(RPC_PARAMETERS_KEY, utils.toXML(rpcRequest.getParameters()));
		}
		super.onAfterRequestDeserialized(rpcRequest);
	}

}
