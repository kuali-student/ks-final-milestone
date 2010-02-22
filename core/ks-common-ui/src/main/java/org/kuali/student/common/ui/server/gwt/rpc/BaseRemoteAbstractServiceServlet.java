/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.common.ui.server.gwt.rpc;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.SerializationException;
import static com.google.gwt.user.server.rpc.RPC.*;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Overrides the processCall method in a way that provides two more RPC hooks.
 * The first hook <code>onBeforeResponseSerialized(Object result)</code> exposes
 * the Java object that is about to be returned before it gets serialized. The
 * second hook <code>doTrappedException(Exception e)</code> allows for exception
 * handling if a known exception is about to get thrown.
 * 
 * The extra hooks are an enhancement asked for by the GWT community at large
 * http://code.google.com/p/google-web-toolkit/issues/detail?id=1291
 * 
 * Also contains a listener that can provide custom behavior as needed. The
 * default listener just stores RPC related objects as request attributes.
 * Injecting a different listener or use AOP to wrap methods on the default
 * listener allows for customizing this behavior.
 */
@SuppressWarnings("serial")
public abstract class BaseRemoteAbstractServiceServlet extends RemoteServiceServlet {

	RpcUtils utils = new RpcUtils();

	@Override
	public String processCall(String payload) throws SerializationException {
		try {
			RPCRequest rpcRequest = decodeRequest(payload, this.getClass(), this);
			onAfterRequestDeserialized(rpcRequest);
			Object result = utils.invoke(this, rpcRequest.getMethod(), rpcRequest.getParameters());
			onBeforeResponseSerialized(result);
			return utils.encodeResponse(result, this, rpcRequest.getMethod(), rpcRequest.getParameters(), rpcRequest.getSerializationPolicy());
		} catch (IncompatibleRemoteServiceException e) {
			log("An IncompatibleRemoteServiceException was thrown while processing this call.", e);
			doTrappedException(e);
			return encodeResponseForFailure(null, e);
		} catch (InvocationTargetException e) {
			log("An InvocationTargetException was thrown while processing this call.", e);
			doTrappedException(e);
			return encodeResponseForFailure(null, e);
		}
	}

	GwtRpcRequestListener gwtRpcRequestListener = new GwtRpcRequestListener() {
		public void onBeforeRequestDeserialized(HttpServletRequest request, String requestPayload) {
			request.setAttribute(RPC_REQUEST_PAYLOAD, requestPayload);
		}

		public void onAfterRequestDeserialized(HttpServletRequest request, RPCRequest rpcRequest) {
			request.setAttribute(RPC_REQUEST, rpcRequest);
		}

		public void onBeforeResponseSerialized(HttpServletRequest request, Object result) {
			request.setAttribute(RPC_RETURN_OBJECT, result);
		}

		public void onAfterResponseSerialized(HttpServletRequest request, String responsePayload) {
			request.setAttribute(RPC_RESPONSE_PAYLOAD, responsePayload);
		}

		public void doUnexpectedFailure(HttpServletRequest request, Throwable e) {
			request.setAttribute(RPC_UNEXPECTED_FAILURE, e);
		}

		public void doTrappedException(HttpServletRequest request, Exception e) {
			request.setAttribute(RPC_TRAPPED_EXCEPTION, e);
		}
	};

	@Override
	protected void onBeforeRequestDeserialized(String requestPayload) {
		gwtRpcRequestListener.onBeforeRequestDeserialized(getThreadLocalRequest(), requestPayload);
		super.onBeforeRequestDeserialized(requestPayload);
	}

	@Override
	protected void onAfterRequestDeserialized(RPCRequest rpcRequest) {
		gwtRpcRequestListener.onAfterRequestDeserialized(getThreadLocalRequest(), rpcRequest);
		super.onAfterRequestDeserialized(rpcRequest);
	}

	protected void onBeforeResponseSerialized(Object result) {
		gwtRpcRequestListener.onBeforeResponseSerialized(getThreadLocalRequest(), result);
	}

	@Override
	protected void onAfterResponseSerialized(String responsePayload) {
		gwtRpcRequestListener.onAfterResponseSerialized(getThreadLocalRequest(), responsePayload);
		super.onAfterResponseSerialized(responsePayload);
	}

	@Override
	protected void doUnexpectedFailure(Throwable e) {
		gwtRpcRequestListener.doUnexpectedFailure(getThreadLocalRequest(), e);
		super.doUnexpectedFailure(e);
	}

	protected void doTrappedException(Exception e) {
		gwtRpcRequestListener.doTrappedException(getThreadLocalRequest(), e);
	}

	public GwtRpcRequestListener getGwtRpcRequestListener() {
		return gwtRpcRequestListener;
	}

	public void setGwtRpcRequestListener(GwtRpcRequestListener listener) {
		this.gwtRpcRequestListener = listener;
	}
}
