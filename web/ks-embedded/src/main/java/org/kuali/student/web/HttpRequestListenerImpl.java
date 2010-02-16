package org.kuali.student.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kuali.rice.core.config.ConfigContext;
import org.kuali.student.common.ui.server.gwt.rpc.GwtRpcRequestListener;

import com.google.gwt.user.server.rpc.RPCRequest;

public class HttpRequestListenerImpl implements RequestRecorderFilterListener, GwtRpcRequestListener {
	private static final String RPC_REQUEST_METHOD = "rpc.method";
	private static final String RPC_REQUEST_PARAMETERS = "rpc.params";
	private static final String RPC_REQUEST = "rpc.request";
	private static final String RPC_RESPONSE = "rpc.response";
	private static final String RPC_EXCEPTION_TRAPPED = "rpc.exception.trapped";
	private static final String RPC_EXCEPTION_UNEXPECTED = "rpc.exception.unexpected";
	private static final String RPC_RESPONSE_SERIALIZED = "rpc.response.serialized";
	private static final String RPC_REQUEST_SERIALIZED = "rpc.request.serialized";
	private static final String RPC_RESPONSE_OBJECT = "rpc.response.object";
	private static final String SESSION_RECORDER_KEY = "recordedSession";
	private static final Log log = LogFactory.getLog(HttpRequestListenerImpl.class);

	String debugModeProperty = "ks.web.httprequest.debug.mode";
	boolean debugMode = "true".equalsIgnoreCase(ConfigContext.getCurrentContextConfig().getProperty(debugModeProperty));
	RequestUtils utils = new RequestUtils();

	@Override
	public RecordedRequest onBeforeDoFilter(HttpServletFilterContext context) {
		if (!debugMode) {
			return null;
		}
		HttpSession session = context.getRequest().getSession(true);
		RecordedSession rs = (RecordedSession) session.getAttribute(SESSION_RECORDER_KEY);
		if (rs == null) {
			log.info("Recording a new HTTP session: " + session.getId());
			rs = new RecordedSession();
			session.setAttribute(SESSION_RECORDER_KEY, rs);
		}

		// Get a recorded request object
		RecordedRequest rr = getRecordedRequest(context.getRequest());

		/**
		 * GWT can spew lots of asynchronous requests. Synchronize on the
		 * RecordedSession object to insure that the modification to the list of
		 * recorded requests and the setting of the sequence number is atomic.
		 */
		synchronized (rs) {
			// Add the request to our list
			rs.getRecordedRequests().add(rr);

			// Set the sequence
			rr.setSequence(rs.getRecordedRequests().size());
		}

		// return the recorded request object
		return rr;
	}

	protected RecordedRequest getRecordedRequest(HttpServletRequest request) {
		// Copy the parameter list
		List<NameValuesBean> parameters = utils.getSortedParameters(request.getParameterMap());

		// Create and populate a RecordedRequest object
		RecordedRequest rr = new RecordedRequest();
		rr.setStartTime(new Date());
		rr.setPath(request.getRequestURL() + "");
		rr.setParameters(parameters);
		return rr;
	}

	@Override
	public void onAfterDoFilter(HttpServletFilterContext context, RecordedRequest rr) {
		if (!debugMode) {
			return;
		}
		rr.setFinishTime(new Date());
		handleRPC(context.getRequest(), rr);
	}

	/**
	 * Detect if this is a GWT RPC call. If so, handle the RPC data
	 */
	protected void handleRPC(HttpServletRequest request, RecordedRequest rr) {
		if (request.getAttribute(RPC_REQUEST) == null) {
			return;
		}
		if (rr.getParameters() == null) {
			rr.setParameters(new ArrayList<NameValuesBean>());
		}
		handleRpcRequest(request, rr);
		handleRpcResponse(request, rr);
		handleRpcExceptions(request, rr);
	}

	protected void handleRpcRequest(HttpServletRequest request, RecordedRequest rr) {
		RPCRequest rpcRequest = (RPCRequest) request.getAttribute(RPC_REQUEST);
		String method = utils.toString(rpcRequest.getMethod());
		String parameters = utils.toXML(rpcRequest.getParameters());

		NameValuesBean methodBean = utils.getNameValuesBean(RPC_REQUEST_METHOD, method);
		NameValuesBean parametersBean = utils.getNameValuesBean(RPC_REQUEST_PARAMETERS, parameters);

		rr.getParameters().add(methodBean);
		rr.getParameters().add(parametersBean);
	}

	protected void handleRpcResponse(HttpServletRequest request, RecordedRequest rr) {
		Object object = (Object) request.getAttribute(RPC_RESPONSE_OBJECT);
		String xml = utils.toXML(object);
		NameValuesBean bean = utils.getNameValuesBean(RPC_RESPONSE, xml);
		rr.getParameters().add(bean);
	}

	protected void handleRpcExceptions(HttpServletRequest request, RecordedRequest rr) {
		handleException(request, rr, RPC_EXCEPTION_UNEXPECTED);
		handleException(request, rr, RPC_EXCEPTION_TRAPPED);
	}

	protected void handleException(HttpServletRequest request, RecordedRequest rr, String name) {
		Throwable e = (Throwable) request.getAttribute(name);
		String s = utils.toString(e);
		NameValuesBean bean = utils.getNameValuesBean(name, s);
		rr.getParameters().add(bean);
	}

	@Override
	public void doTrappedException(HttpServletRequest request, Exception e) {
		request.setAttribute(RPC_EXCEPTION_TRAPPED, e);
	}

	@Override
	public void doUnexpectedFailure(HttpServletRequest request, Throwable e) {
		request.setAttribute(RPC_EXCEPTION_UNEXPECTED, e);
	}

	@Override
	public void onAfterRequestDeserialized(HttpServletRequest request, RPCRequest rpcRequest) {
		request.setAttribute(RPC_REQUEST, rpcRequest);
	}

	@Override
	public void onAfterResponseSerialized(HttpServletRequest request, String responsePayload) {
		request.setAttribute(RPC_RESPONSE_SERIALIZED, responsePayload);
	}

	@Override
	public void onBeforeRequestDeserialized(HttpServletRequest request, String requestPayload) {
		request.setAttribute(RPC_REQUEST_SERIALIZED, requestPayload);
	}

	@Override
	public void onBeforeResponseSerialized(HttpServletRequest request, Object result) {
		request.setAttribute(RPC_RESPONSE_OBJECT, result);
	}
}
