package org.kuali.student.psrg.util;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;

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
	String filename;
	OutputStream out;
	PrettyPrint prettyPrint = new PrettyPrint();

	public Object trackMethod(ProceedingJoinPoint call) throws Throwable {
		// Keep track of the sequence
		long currentSequence = 0;
		synchronized (sequence) {
			if (sequence == 0 && filename != null) {
				out = new FileOutputStream(getFilename());
				IOUtils.write("Sequence,Elapsed,Method\n", out);
			}
			currentSequence = ++sequence;
		}

		// Time the method call
		long start = System.currentTimeMillis();
		Object result = call.proceed();
		long stop = System.currentTimeMillis();
		long elapsed = stop - start;

		// Produce a log message about this method call
		log.info(prettyPrint.getLogMessage(currentSequence, elapsed, call, result));
		if (out != null) {
			String csv = prettyPrint.getCsv(currentSequence, elapsed, call, result);
			IOUtils.write(csv, out);
			out.flush();
		}
		return result;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public PrettyPrint getPrettyPrint() {
		return prettyPrint;
	}

	public void setPrettyPrint(PrettyPrint prettyPrint) {
		this.prettyPrint = prettyPrint;
	}
}
