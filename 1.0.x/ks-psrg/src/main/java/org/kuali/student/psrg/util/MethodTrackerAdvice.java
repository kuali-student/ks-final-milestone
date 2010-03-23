package org.kuali.student.psrg.util;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;

/**
 * This advice produces logging information about method calls. It captures the
 * amount of time each method takes and also tracks the sequence in which
 * methods are called.
 * 
 * @author Jeff Caddel
 */
public class MethodTrackerAdvice {
	Long sequence = new Long(0);
	protected final Log log = LogFactory.getLog(this.getClass());

	public Object trackMethod(ProceedingJoinPoint call) throws Throwable {
		// Keep track of the sequence
		long currentSequence = 0;
		synchronized (sequence) {
			currentSequence = ++sequence;
		}
		// Time the method call
		long start = System.currentTimeMillis();
		Object result = call.proceed();
		long stop = System.currentTimeMillis();
		long elapsed = stop - start;

		// Produce a log message about this method call
		StringBuffer sb = new StringBuffer();
		sb.append("Sequence:" + space(currentSequence, 4) + " ");
		sb.append(" Elapsed:" + space(elapsed, 4) + " ");
		// Show return type, method name, and argument types
		sb.append(getPrettyPrint(call, result));
		log.info(sb.toString());
		return result;
	}

	/**
	 * Produce a log message about this method call
	 * 
	 * @param currentSequence
	 * @param elapsed
	 * @param call
	 * @param result
	 * @return
	 */
	protected String getLogMessage(long currentSequence, long elapsed, ProceedingJoinPoint call, Object result) {
		// Produce a log message about this method call
		StringBuffer sb = new StringBuffer();
		sb.append("Sequence:" + space(currentSequence, 4) + " ");
		sb.append(" Elapsed:" + space(elapsed, 4) + " ");
		// Show return type, method name, and argument types
		sb.append(getPrettyPrint(call, result));
		return sb.toString();
	}

	/**
	 * Space out the number passed in with the padding amount indicated
	 * 
	 * @param number
	 * @param padding
	 * @return
	 */
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

	/**
	 * Get the return type of this method
	 * 
	 * @param call
	 * @param result
	 * @return
	 */
	protected String getReturnType(ProceedingJoinPoint call, Object result) {
		if (result instanceof String) {
			return '"' + result.toString() + '"';
		}
		String returnType = getReturnType(call);
		if (returnType != null) {
			return returnType;
		} else if (result != null) {
			return getShortName(result.getClass());
		} else {
			return null;
		}
	}

	/**
	 * Attempt to obtain the return type via reflection
	 * 
	 * @param call
	 * @return
	 */
	protected String getReturnType(ProceedingJoinPoint call) {
		Signature sig = call.getSignature();
		Object target = call.getTarget();
		Class<?>[] parameterTypes = getParameterTypes(call.getArgs());
		try {
			Method m = target.getClass().getMethod(sig.getName(), parameterTypes);
			Class<?> returnType = m.getReturnType();
			return getShortName(returnType);
		} catch (NoSuchMethodException e) {
			// Ignore this
			return null;
		}
	}

	/**
	 * Produce a formatted string representing this method call
	 * 
	 * @param call
	 * @param result
	 * @return
	 */
	protected String getPrettyPrint(ProceedingJoinPoint call, Object result) {
		StringBuffer sb = new StringBuffer();
		// The return type of the method
		sb.append(getReturnType(call, result));
		sb.append(" ");

		// The class we are calling a method on
		sb.append(getShortName(call.getSignature().getDeclaringType()));
		sb.append(".");

		// The method we are invoking
		sb.append(call.getSignature().getName());
		sb.append("(");

		// Parameters being passed to the method
		sb.append(getParameters(call.getArgs()));
		sb.append(")");
		return sb.toString();
	}

	/**
	 * Convert the parameter list to String form. If any of the parameters are a
	 * String return the value of the string
	 * 
	 * @param args
	 * @return
	 */
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

	/**
	 * Trim the package name off
	 * 
	 * @param c
	 * @return
	 */
	public String getShortName(Class<?> c) {
		String s = c.getName();
		int pos = s.lastIndexOf(".");
		if (pos == -1) {
			return s;
		} else {
			return s.substring(pos + 1);
		}
	}

	/**
	 * Get the Class associated with each arg
	 * 
	 * @param args
	 * @return
	 */
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
