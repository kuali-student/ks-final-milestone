package org.kuali.student.psu.util;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 
 * 
 * @since Mar 30, 2010 2:38:19 PM
 */
public class MethodEvent {
	long elapsed;
	long sequence;
	long timestamp;
	Object result;
	ProceedingJoinPoint call;

	public MethodEvent() {
		super();
	}

	public MethodEvent(long sequence, long timestamp, long elapsed, Object result, ProceedingJoinPoint call) {
		super();
		this.sequence = sequence;
		this.timestamp = timestamp;
		this.elapsed = elapsed;
		this.result = result;
		this.call = call;
	}

	public ProceedingJoinPoint getCall() {
		return call;
	}

	public void setCall(ProceedingJoinPoint call) {
		this.call = call;
	}

	public long getSequence() {
		return sequence;
	}

	public void setSequence(long sequence) {
		this.sequence = sequence;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public long getElapsed() {
		return elapsed;
	}

	public void setElapsed(long elapsed) {
		this.elapsed = elapsed;
	}
}
