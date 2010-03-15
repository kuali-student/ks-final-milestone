package org.kuali.student.psrg.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 
 */
public class MethodTrackerAdvice {
	int sequence;
	protected final Log log = LogFactory.getLog(this.getClass());

	List<ProceedingJoinPoint> list = Collections.synchronizedList(new ArrayList<ProceedingJoinPoint>());

	public Object trackMethod(ProceedingJoinPoint call) throws Throwable {
		synchronized (list) {
			sequence++;
			list.add(call);
			System.out.println("Sequence: " + space(sequence, 4) + " " + call.getSignature().toLongString());
		}
		return call.proceed();
	}

	public List<ProceedingJoinPoint> getList() {
		return list;
	}

	protected String space(int number, int padding) {
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
}
