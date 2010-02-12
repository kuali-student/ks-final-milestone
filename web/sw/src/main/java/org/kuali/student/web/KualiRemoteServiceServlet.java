package org.kuali.student.web;

import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

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
		getThreadLocalRequest().setAttribute(RPC_METHOD_KEY, toString(rpcRequest.getMethod()));
		getThreadLocalRequest().setAttribute(RPC_PARAMETERS_KEY, toXML(rpcRequest.getParameters()));
		super.onAfterRequestDeserialized(rpcRequest);
	}

	protected String toXML(Object[] parameters) {
		StringBuffer sb = new StringBuffer();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		XMLEncoder e = new XMLEncoder(out);
		e.writeObject(parameters);
		e.close();
		sb.append(out.toString());
		return sb.toString();
	}

	private static final int LANGUAGE_MODIFIERS = Modifier.PUBLIC | Modifier.PROTECTED | Modifier.PRIVATE | Modifier.ABSTRACT | Modifier.STATIC | Modifier.FINAL | Modifier.SYNCHRONIZED | Modifier.NATIVE;

	protected String toString(Method m) {
		try {
			StringBuffer sb = new StringBuffer();
			int mod = m.getModifiers() & LANGUAGE_MODIFIERS;
			if (mod != 0) {
				sb.append(Modifier.toString(mod) + " ");
			}
			sb.append(getTypeName(m.getReturnType()) + " ");
			sb.append(getTypeName(m.getDeclaringClass()) + ".");
			sb.append(m.getName() + "(");
			Class<?>[] params = m.getParameterTypes(); // avoid clone
			for (int j = 0; j < params.length; j++) {
				sb.append(getTypeName(params[j]));
				if (j < (params.length - 1))
					sb.append(",");
			}
			sb.append(")");
			Class<?>[] exceptions = m.getExceptionTypes(); // avoid clone
			if (exceptions.length > 0) {
				sb.append(" throws ");
				for (int k = 0; k < exceptions.length; k++) {
					sb.append(getGeneric(exceptions[k].getName()));
					if (k < (exceptions.length - 1))
						sb.append(",");
				}
			}
			return sb.toString();
		} catch (Exception e) {
			return "[" + e + "]";
		}
	}

	/*
	 * Utility routine to paper over array type names
	 */
	protected String getTypeName(Class<?> type) {
		if (type.isArray()) {
			try {
				Class<?> cl = type;
				int dimensions = 0;
				while (cl.isArray()) {
					dimensions++;
					cl = cl.getComponentType();
				}
				StringBuffer sb = new StringBuffer();
				sb.append(cl.getName());
				for (int i = 0; i < dimensions; i++) {
					sb.append("[]");
				}
				return getGeneric(sb.toString());
			} catch (Throwable e) { /* FALLTHRU */
			}
		}
		return getGeneric(type.getName());
	}

	protected String getGeneric(String s) {
		int pos = s.lastIndexOf(".");
		if (pos == -1) {
			return s;
		}
		return s.substring(pos + 1);
	}

}
