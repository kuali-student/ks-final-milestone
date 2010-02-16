/*
 * Copyright 2007 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.common.ui.server.gwt.rpc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.SerializationPolicy;

/**
 * This is based on Google's RPC.java source. The improvement here is that
 * invokeAndEncode is broken up into separate invoke() and encode() methods.
 */
public class RpcUtils {

	/**
	 * Invoke the specified method and return the result
	 * 
	 * @param target
	 * @param serviceMethod
	 * @param args
	 * @return
	 * @throws InvocationTargetException
	 */
	public Object invoke(Object target, Method serviceMethod, Object[] args) throws InvocationTargetException {
		if (serviceMethod == null) {
			throw new NullPointerException("serviceMethod");
		}
		try {
			return serviceMethod.invoke(target, args);
		} catch (IllegalAccessException e) {
			SecurityException securityException = new SecurityException(formatIllegalAccessErrorMessage(target, serviceMethod));
			securityException.initCause(e);
			throw securityException;
		} catch (IllegalArgumentException e) {
			SecurityException securityException = new SecurityException(formatIllegalArgumentErrorMessage(target, serviceMethod, args));
			securityException.initCause(e);
			throw securityException;
		} catch (InvocationTargetException e) {
			throw e;
		}
	}

	/**
	 * Serialize the result object
	 * 
	 * @param result
	 * @param target
	 * @param serviceMethod
	 * @param args
	 * @param serializationPolicy
	 * @return
	 * @throws SerializationException
	 */
	public String encodeResponse(Object result, Object target, Method serviceMethod, Object[] args, SerializationPolicy serializationPolicy) throws SerializationException {
		if (serviceMethod == null) {
			throw new NullPointerException("serviceMethod");
		}

		if (serializationPolicy == null) {
			throw new NullPointerException("serializationPolicy");
		}
		return RPC.encodeResponseForSuccess(serviceMethod, result, serializationPolicy);
	}

	protected String getSourceRepresentation(Method method) {
		return method.toString().replace('$', '.');
	}

	protected String formatIllegalAccessErrorMessage(Object target, Method serviceMethod) {
		StringBuffer sb = new StringBuffer();
		sb.append("Blocked attempt to access inaccessible method '");
		sb.append(getSourceRepresentation(serviceMethod));
		sb.append("'");
		if (target != null) {
			sb.append(" on target '");
			sb.append(printTypeName(target.getClass()));
			sb.append("'");
		}
		sb.append("; this is either misconfiguration or a hack attempt");
		return sb.toString();
	}

	protected String formatIllegalArgumentErrorMessage(Object target, Method serviceMethod, Object[] args) {
		StringBuffer sb = new StringBuffer();
		sb.append("Blocked attempt to invoke method '");
		sb.append(getSourceRepresentation(serviceMethod));
		sb.append("'");

		if (target != null) {
			sb.append(" on target '");
			sb.append(printTypeName(target.getClass()));
			sb.append("'");
		}

		sb.append(" with invalid arguments");

		if (args != null && args.length > 0) {
			sb.append(Arrays.asList(args));
		}
		return sb.toString();
	}

	protected String printTypeName(Class<?> type) {
		// Primitives
		if (type.equals(Integer.TYPE)) {
			return "int";
		} else if (type.equals(Long.TYPE)) {
			return "long";
		} else if (type.equals(Short.TYPE)) {
			return "short";
		} else if (type.equals(Byte.TYPE)) {
			return "byte";
		} else if (type.equals(Character.TYPE)) {
			return "char";
		} else if (type.equals(Boolean.TYPE)) {
			return "boolean";
		} else if (type.equals(Float.TYPE)) {
			return "float";
		} else if (type.equals(Double.TYPE)) {
			return "double";
		}

		// Arrays
		if (type.isArray()) {
			Class<?> componentType = type.getComponentType();
			return printTypeName(componentType) + "[]";
		}

		// Everything else
		return type.getName().replace('$', '.');
	}

}
