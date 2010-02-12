package org.kuali.student.web;

import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;

import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import static org.kuali.student.web.Constants.*;

public class KualiRemoteServiceServlet extends RemoteServiceServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void onBeforeRequestDeserialized(String serializedRequest) {
		// getThreadLocalRequest().setAttribute(REQUEST_PAYLOAD_KEY,
		// serializedRequest);
		super.onBeforeRequestDeserialized(serializedRequest);
	}

	@Override
	protected void onAfterResponseSerialized(String serializedResponse) {
		// getThreadLocalRequest().setAttribute(RESPONSE_PAYLOAD_KEY,
		// serializedResponse);
		super.onAfterResponseSerialized(serializedResponse);
	}

	@Override
	protected void onAfterRequestDeserialized(RPCRequest rpcRequest) {
		getThreadLocalRequest().setAttribute(REQUEST_PAYLOAD_KEY, toXML(rpcRequest));
		super.onAfterRequestDeserialized(rpcRequest);
	}

	protected String toXML(RPCRequest rpcRequest) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		XMLEncoder e = new XMLEncoder(out);
		e.writeObject(rpcRequest.getMethod());
		e.close();
		e = new XMLEncoder(out);
		e.writeObject(rpcRequest.getParameters());
		e.close();
		return out.toString();
	}
}
