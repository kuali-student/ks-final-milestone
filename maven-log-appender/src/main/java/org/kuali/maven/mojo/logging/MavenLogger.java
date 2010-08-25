package org.kuali.maven.mojo.logging;

import org.apache.commons.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;

public class MavenLogger implements Log {

	public MavenLogger() {
		this(null);
	}

	public MavenLogger(String name) {
		super();
		System.out.println(name);
	}

	private static org.apache.maven.plugin.logging.Log mavenLog = new SystemStreamLog();

	@Override
	public boolean isDebugEnabled() {
		return mavenLog.isDebugEnabled();
	}

	@Override
	public boolean isErrorEnabled() {
		return mavenLog.isErrorEnabled();
	}

	@Override
	public boolean isFatalEnabled() {
		return mavenLog.isErrorEnabled();
	}

	@Override
	public boolean isInfoEnabled() {
		return mavenLog.isInfoEnabled();
	}

	@Override
	public boolean isTraceEnabled() {
		return mavenLog.isDebugEnabled();
	}

	@Override
	public boolean isWarnEnabled() {
		return mavenLog.isWarnEnabled();
	}

	@Override
	public void trace(Object message) {
		mavenLog.debug(message.toString());
	}

	@Override
	public void trace(Object message, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debug(Object message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void debug(Object message, Throwable t) {
		// TODO Auto-generated method stub

	}

	protected String getString(Object message) {
		if (message == null) {
			return null;
		}
		return message.toString();
	}

	@Override
	public void info(Object message) {
		mavenLog.info(getString(message));
	}

	@Override
	public void info(Object message, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void warn(Object message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void warn(Object message, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void error(Object message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void error(Object message, Throwable t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fatal(Object message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fatal(Object message, Throwable t) {
		// TODO Auto-generated method stub

	}
}
