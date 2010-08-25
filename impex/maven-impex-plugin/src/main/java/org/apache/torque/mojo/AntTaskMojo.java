package org.apache.torque.mojo;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/**
 * A base class for mojos that wrap an Ant Task
 */
public class AntTaskMojo extends BaseMojo {

	/**
	 * Creates a new instance of AntTaskMojo.
	 */
	public AntTaskMojo(Task antTask) {
		setAntTask(antTask);
		bootstrap();
	}

	/**
	 * The ant task to be executed by this mojo.
	 */
	private Task antTask;

	/**
	 * The ant project for the ant task.
	 */
	private Project antProject;

	/**
	 * Provide a hook for any Ant initialization that needs to be done
	 */
	protected void bootstrap() {
		if (getAntTask() == null) {
			throw new IllegalArgumentException("antTask is null");
		}

		// Attach our task to a project
		setAntProject(getIniatializedAntProject());
		getAntTask().setProject(getAntProject());
	}

	/**
	 * Configures the Ant task which is wrapped by this mojo.
	 */
	protected void configureTask() throws MojoExecutionException {
		// nothing to do by default
	}

	/**
	 * Configure the Ant task and then execute it
	 */
	public void executeMojo() throws MojoExecutionException {
		configureTask();
		getAntTask().execute();
	}

	/**
	 * Return an Ant project that informs Maven about logging events
	 */
	protected Project getIniatializedAntProject() {
		getLog().info("Initializing the Ant Project");
		// Create a new Ant Project
		Project antProject = new Project();
		// initialize it
		antProject.init();
		// Add a listener that gets notified about log messages
		antProject.addBuildListener(new BuildListener() {
			// Log the Ant message using Maven's logger
			public void messageLogged(BuildEvent event) {
				String message = event.getMessage();
				int priority = event.getPriority();
				switch (priority) {
				case (Project.MSG_VERBOSE):
				case (Project.MSG_DEBUG):
					getLog().debug(message);
					return;
				case (Project.MSG_ERR):
					getLog().error(message);
					return;
				case (Project.MSG_WARN):
					getLog().warn(message);
					return;
				case (Project.MSG_INFO):
					getLog().info(message);
					return;
				default:
					getLog().info(message);
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
		});
		return antProject;
	}

	public Project getAntProject() {
		return antProject;
	}

	public void setAntProject(Project antProject) {
		this.antProject = antProject;
	}

	public Task getAntTask() {
		return antTask;
	}

	public void setAntTask(Task antTask) {
		this.antTask = antTask;
	}
}
