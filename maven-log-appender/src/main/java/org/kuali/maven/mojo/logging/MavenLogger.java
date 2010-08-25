package org.kuali.maven.mojo.logging;

import org.apache.commons.logging.Log;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.logging.SystemStreamLog;

public class MavenLogger implements Log {

	private static org.apache.maven.plugin.logging.Log systemStreamLog = new SystemStreamLog();
	private static org.apache.maven.plugin.logging.Log mavenLog = systemStreamLog;

	public MavenLogger() {
		this(null);
	}

	public MavenLogger(String name) {
		super();
	}

	public static void startPluginLog(AbstractMojo mojo) {
		mavenLog = mojo.getLog();
	}

	public static void endPluginLog(AbstractMojo mojo) {
		mavenLog = systemStreamLog;
	}

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
		trace(message, null);
	}

	@Override
	public void trace(Object message, Throwable t) {
		mavenLog.debug(getString(message), t);

	}

	@Override
	public void debug(Object message) {
		mavenLog.debug(getString(message));
	}

	@Override
	public void debug(Object message, Throwable t) {
		// TODO Auto-generated method stub
	}

	@Override
	public void info(Object message) {
		mavenLog.info(getString(message));
	}

	@Override
	public void info(Object message, Throwable t) {
		mavenLog.info(getString(message), t);
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

	/**
	 * Check for null then call toString on the object
	 */
	protected String getString(Object message) {
		if (message == null) {
			return null;
		}
		return message.toString();
	}

}
