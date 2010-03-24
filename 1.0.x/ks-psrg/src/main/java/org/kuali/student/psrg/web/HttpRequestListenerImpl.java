package org.kuali.student.psrg.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kuali.rice.core.config.ConfigContext;

import com.google.gwt.user.server.rpc.RPCRequest;

import static org.brisant.gwt.user.server.rpc.listener.StoreAsRequestAttributesListenerImpl.*;

/**
 * Provides a mechanism for tracking GWT rpc requests
 * 
 * @author Jeff Caddel
 * 
 * @since Mar 24, 2010 2:38:11 PM
 */
public class HttpRequestListenerImpl implements RequestRecorderFilterListener {
	protected final Log log = LogFactory.getLog(HttpRequestListenerImpl.class);

	String sessionRecorderKey = "recordedSession";
	String requestRecorderKey = "recordedRequest";
	String gwtDebugProperty = "ks.gwt.debug";
	boolean debug = "true".equalsIgnoreCase(ConfigContext.getCurrentContextConfig().getProperty(gwtDebugProperty));
	RequestUtils requestUtils = new RequestUtils();

	@Override
	public void onBeforeDoFilter(HttpServletFilterContext context) {
		if (!debug) {
			return;
		}
		HttpSession session = context.getRequest().getSession(true);
		RecordedSession rs = (RecordedSession) session.getAttribute(sessionRecorderKey);
		if (rs == null) {
			log.info("Recording a new HTTP session: " + session.getId());
			rs = new RecordedSession();
			session.setAttribute(sessionRecorderKey, rs);
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
		context.getRequest().setAttribute(requestRecorderKey, rr);
	}

	protected RecordedRequest getRecordedRequest(HttpServletRequest request) {
		// Copy the parameter list
		List<NameValuesBean> parameters = requestUtils.getSortedParameters(request.getParameterMap());

		// Create and populate a RecordedRequest object
		RecordedRequest rr = new RecordedRequest();
		rr.setStartTime(new Date());
		rr.setPath(request.getRequestURL() + "");
		rr.setParameters(parameters);
		return rr;
	}

	@Override
	public void onAfterDoFilter(HttpServletFilterContext context) {
		if (!debug) {
			return;
		}
		RecordedRequest rr = (RecordedRequest) context.getRequest().getAttribute(requestRecorderKey);
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
		String method = requestUtils.toString(rpcRequest.getMethod());
		String parameters = requestUtils.toXML(rpcRequest.getParameters());

		NameValuesBean methodBean = requestUtils.getNameValuesBean(RPC_REQUEST_METHOD, method);
		NameValuesBean parametersBean = requestUtils.getNameValuesBean(RPC_REQUEST_PARAMETERS, parameters);

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
		String s = requestUtils.toString(e);
		NameValuesBean bean = requestUtils.getNameValuesBean(name, s);
		rr.getParameters().add(bean);
	}

	public String getSessionRecorderKey() {
		return sessionRecorderKey;
	}

	public void setSessionRecorderKey(String sessionRecorderKey) {
		this.sessionRecorderKey = sessionRecorderKey;
	}

	public String getRequestRecorderKey() {
		return requestRecorderKey;
	}

	public void setRequestRecorderKey(String requestRecorderKey) {
		this.requestRecorderKey = requestRecorderKey;
	}

	public String getGwtDebugProperty() {
		return gwtDebugProperty;
	}

	public void setGwtDebugProperty(String debugModeProperty) {
		this.gwtDebugProperty = debugModeProperty;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

}
