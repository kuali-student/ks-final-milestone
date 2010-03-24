package org.kuali.student.psrg.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory; //import org.kuali.rice.core.config.ConfigContext;

import com.google.gwt.user.server.rpc.RPCRequest;

import static org.brisant.gwt.user.server.rpc.listener.StoreAsRequestAttributesListenerImpl.*;

public class HttpRequestListenerImpl implements RequestRecorderFilterListener {
	private static final String SESSION_RECORDER_KEY = "recordedSession";
	private static final String REQUEST_RECORDER_KEY = "recordedRequest";
	private static final Log log = LogFactory.getLog(HttpRequestListenerImpl.class);

	String debugModeProperty = "ks.web.httprequest.debug.mode";
	boolean debugMode = false;// "true".equalsIgnoreCase(ConfigContext.getCurrentContextConfig().getProperty(debugModeProperty));
	RequestUtils utils = new RequestUtils();

	@Override
	public void onBeforeDoFilter(HttpServletFilterContext context) {
		if (!debugMode) {
			return;
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

		// Store our object on the request as an attribute
		context.getRequest().setAttribute(REQUEST_RECORDER_KEY, rr);
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
	public void onAfterDoFilter(HttpServletFilterContext context) {
		if (!debugMode) {
			return;
		}
		RecordedRequest rr = (RecordedRequest) context.getRequest().getAttribute(REQUEST_RECORDER_KEY);
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
		if (rpcRequest == null) {
			return;
		}
		String method = utils.toString(rpcRequest.getMethod());
		String parameters = utils.toXML(rpcRequest.getParameters());

		NameValuesBean methodBean = utils.getNameValuesBean(RPC_REQUEST_METHOD, method);
		NameValuesBean parametersBean = utils.getNameValuesBean(RPC_REQUEST_PARAMETERS, parameters);

		rr.getParameters().add(methodBean);
		if (!parameters.equals("")) {
			rr.getParameters().add(parametersBean);
		}
	}

	protected void handleRpcResponse(HttpServletRequest request, RecordedRequest rr) {
		Object result = (Object) request.getAttribute(RPC_RETURN_OBJECT);
		if (result == null) {
			return;
		}
		/**
		 * XMLEncoder had issues converting complex return types to XML
		 */
		// String xml = utils.toXML(result);
		// NameValuesBean bean = utils.getNameValuesBean(RPC_RESPONSE, xml);
		// rr.getParameters().add(bean);
	}

	protected void handleRpcExceptions(HttpServletRequest request, RecordedRequest rr) {
		handleException(request, rr, RPC_UNEXPECTED_FAILURE);
		handleException(request, rr, RPC_TRAPPED_EXCEPTION);
	}

	protected void handleException(HttpServletRequest request, RecordedRequest rr, String name) {
		Throwable e = (Throwable) request.getAttribute(name);
		if (e == null) {
			return;
		}
		String s = utils.toString(e);
		NameValuesBean bean = utils.getNameValuesBean(name, s);
		rr.getParameters().add(bean);
	}

}
