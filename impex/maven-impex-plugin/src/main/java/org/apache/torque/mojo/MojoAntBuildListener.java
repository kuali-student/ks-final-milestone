package org.apache.torque.mojo;

import org.apache.maven.plugin.logging.Log;
import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.Project;

/**
 * This listener issues Ant logging messages to the Maven logging system
 */
public class MojoAntBuildListener implements BuildListener {
	Log mavenLog;

	public MojoAntBuildListener() {
		this(null);
	}

	public MojoAntBuildListener(Log mavenLog) {
		super();
		this.mavenLog = mavenLog;
	}

	// Log the Ant message using Maven's logger
	public void messageLogged(BuildEvent event) {
		String message = event.getMessage();
		int priority = event.getPriority();
		switch (priority) {
		case (Project.MSG_VERBOSE):
		case (Project.MSG_DEBUG):
			mavenLog.debug(message);
			return;
		case (Project.MSG_ERR):
			mavenLog.error(message);
			return;
		case (Project.MSG_WARN):
			mavenLog.warn(message);
			return;
		case (Project.MSG_INFO):
			mavenLog.info(message);
			return;
		default:
			mavenLog.info(message);
			return;
		}
	}

	// Ignore all other events
	public void taskStarted(BuildEvent event) {
	}

	public void taskFinished(BuildEvent event) {
	}

	public void targetStarted(BuildEvent event) {
	}

	public void targetFinished(BuildEvent event) {
	}

	public void buildStarted(BuildEvent event) {
	}

	public void buildFinished(BuildEvent event) {
	}

	public Log getMavenLog() {
		return mavenLog;
	}

	public void setMavenLog(Log mavenLog) {
		this.mavenLog = mavenLog;
	}

}
