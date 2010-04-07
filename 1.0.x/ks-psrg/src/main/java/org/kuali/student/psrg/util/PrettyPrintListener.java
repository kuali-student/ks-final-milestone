package org.kuali.student.psrg.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;

/**
 * 
 */
public class PrettyPrintListener implements MethodListener {
	protected final Log log = LogFactory.getLog(this.getClass());
	OutputStream out;
	String filename;
	Boolean flushAlways = new Boolean(false);

	protected void handleCSV(MethodEvent event) throws IOException {
		if (out == null) {
			return;
		}
		String csv = getCsv(event);
		synchronized (out) {
			IOUtils.write(csv, out);
			if (flushAlways) {
				out.flush();
			}
		}
	}

	public void init() throws IOException {
		log.info("Initializing");
		initOutputStream();
	}

	protected void initOutputStream() throws IOException {
		if (filename == null) {
			return;
		}
		File file = new File(filename);
		log.info("Logging method calls to " + file.getAbsolutePath());
		out = new BufferedOutputStream(new FileOutputStream(file));
		IOUtils.write("Thread,Timestamp,Sequence,Elapsed,Method\n", out);
	}

	public void destroy() {
		log.info("Shutting down");
		IOUtils.closeQuietly(out);
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
	protected String getLogMessage(MethodEvent event) {
		// Produce a log message about this method call
		StringBuffer sb = new StringBuffer();
		sb.append("Timestamp:" + space(event.getTimestamp(), 4) + " ");
		sb.append(" Sequence:" + space(event.getSequence(), 4) + " ");
		sb.append("  Elapsed:" + space(event.getElapsed(), 4) + " ");
		// Show return type, method name, and argument types
		sb.append(getPrettyPrint(event.getCall()));
		return sb.toString();
	}

	protected String getCsv(MethodEvent event) {
		// Produce a log message about this method call
		StringBuffer sb = new StringBuffer();
		sb.append(Thread.currentThread().getName() + ",");
		sb.append(event.getTimestamp() + ",");
		sb.append(event.getSequence() + ",");
		sb.append(event.getElapsed() + ",");
		// Show return type, method name, and argument types
		sb.append('"' + getPrettyPrint(event.getCall()) + '"');
		sb.append("\n");
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
	protected String getPrettyPrint(ProceedingJoinPoint call) {
		StringBuffer sb = new StringBuffer();
		// The return type of the method
		sb.append(getReturnType(call));
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
	 * Convert the parameter list to String form. If any of the parameters are a String return the
	 * value of the string
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
				sb.append("'");
				sb.append(args[i]);
				sb.append("'");
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
	protected String getShortName(Class<?> c) {
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

	@Override
	public void methodInvoked(MethodEvent event) {
		// Produce a log message about this method call
		log.info(getLogMessage(event));
		try {
			handleCSV(event);
		} catch (IOException e) {
			throw new RuntimeException("Error recording CSV information", e);
		}
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Boolean getFlushAlways() {
		return flushAlways;
	}

	public void setFlushAlways(Boolean flushAlways) {
		this.flushAlways = flushAlways;
	}
}
