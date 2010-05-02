package org.kuali.student.psu.util;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * This advice tracks method calls. It captures the amount of time each method takes as well as the sequence in which
 * methods are invoked.
 */
public class MethodTrackerAdvice {
	Long sequence = new Long(0);
	MethodListener listener;

	public Object trackMethod(final ProceedingJoinPoint call) throws Throwable {
		// Keep track of the sequence
		long currentSequence;
		synchronized (sequence) {
			currentSequence = ++sequence;
		}

		// Time the method call
		long start = System.currentTimeMillis();
		Object result = call.proceed();
		long elapsed = System.currentTimeMillis() - start;

		// Invoke our listener
		MethodEvent event = new MethodEvent(currentSequence, start, elapsed, result, call);
		listener.methodInvoked(event);

		// Return the result
		return result;
	}

	public MethodListener getListener() {
		return listener;
	}

	public void setListener(MethodListener listener) {
		this.listener = listener;
	}

}
