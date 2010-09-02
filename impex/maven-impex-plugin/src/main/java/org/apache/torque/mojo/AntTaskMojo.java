package org.apache.torque.mojo;

import org.apache.maven.plugin.MojoExecutionException;
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
		bootstrap();
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
		antProject.addBuildListener(new MojoAntBuildListener(getLog()));
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
