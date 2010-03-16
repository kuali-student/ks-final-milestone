package org.kuali.student.psrg.util;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;

/**
 * 
 */
public class MethodTrackerAdvice {
	Integer sequence = new Integer(0);
	protected final Log log = LogFactory.getLog(this.getClass());

	public Object trackMethod(ProceedingJoinPoint call) throws Throwable {
		int currentSequence = 0;
		synchronized (sequence) {
			currentSequence = ++sequence;
		}
		long start = System.currentTimeMillis();
		Object result = call.proceed();
		long stop = System.currentTimeMillis();
		long elapsed = stop - start;
		StringBuffer sb = new StringBuffer();
		sb.append("Seq:" + space(currentSequence, 4) + " ");
		sb.append("Time: " + space(elapsed, 4) + " ");
		sb.append(getPrettyPrint(call, result));
		log.info(sb.toString());
		return result;
	}

	protected String space(long number, int padding) {
		String s = number + "";
		int length = padding - s.length();
		if (length < 1) {
			return number + "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append(" ");
		}
		sb.append(number);
		return sb.toString();
	}

	protected String getReturnType(ProceedingJoinPoint call, Object result) throws NoSuchMethodException {
		if (result instanceof String) {
			return '"' + result.toString() + '"';
		} else if (result != null) {
			return getShortName(result.getClass());
		} else {
			Signature sig = call.getSignature();
			Object target = call.getTarget();
			Class<?>[] parameterTypes = getParameterTypes(call.getArgs());
			Method m = target.getClass().getMethod(sig.getName(), parameterTypes);
			Class<?> returnType = m.getReturnType();
			return getShortName(returnType);
		}
	}

	protected String getPrettyPrint(ProceedingJoinPoint call, Object result) throws NoSuchMethodException {
		StringBuffer sb = new StringBuffer();
		sb.append(getReturnType(call, result));
		sb.append(" ");
		sb.append(getShortName(call.getSignature().getDeclaringType()));
		sb.append(".");
		sb.append(call.getSignature().getName());
		sb.append("(");
		sb.append(getParameters(call.getArgs()));
		sb.append(")");
		return sb.toString();
	}

	protected String getParameters(Object[] args) {
		if (args == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			if (i != 0) {
				sb.append(",");
			}
			if (args[i] instanceof String) {
				sb.append('"');
				sb.append(args[i]);
				sb.append('"');
			} else {
				sb.append(getShortName(args[i].getClass()));
			}
		}
		return sb.toString();
	}

	public String getShortName(Class<?> c) {
		String s = c.getName();
		return s.substring(s.lastIndexOf(".") + 1);
	}

	protected Class<?>[] getParameterTypes(Object[] args) {
		if (args == null) {
			return null;
		}
		Class<?>[] parameterTypes = new Class[args.length];
		for (int i = 0; i < args.length; i++) {
			parameterTypes[i] = args[i].getClass();
		}
		return parameterTypes;
	}
}
