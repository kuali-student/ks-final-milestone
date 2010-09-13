package org.apache.torque.mojo;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.torque.util.MojoAntBuildListener;
import org.kuali.core.db.torque.FilteredPropertyCopier;

/**
 * A base class for mojos that wrap an Ant Task
 */
public class AntTaskMojo extends PropertiesMojo {

	/**
	 * The ant task to be executed by this mojo.
	 */
	private Task antTask;

	/**
	 * The ant project for the ant task.
	 */
	private Project antProject;

	/**
	 * Configures the Ant task which is wrapped by this mojo.
	 */
	protected void configureTask() throws MojoExecutionException {
		if (getAntTask() == null) {
			throw new IllegalArgumentException("Ant task is null");
		}

		// Attach our task to a project
		setAntProject(getIniatializedAntProject());
		getAntTask().setProject(getAntProject());
		try {
			// Copy configuration from the mojo to the task
			FilteredPropertyCopier copier = new FilteredPropertyCopier();
			copier.addExclude("project");
			copier.copyProperties(getAntTask(), this);
		} catch (Exception e) {
			throw new MojoExecutionException("Error copying properties", e);
		}
	}

	/**
	 * Configure the Ant task and then execute it
	 */
	public void executeMojo() throws MojoExecutionException {
		super.executeMojo();
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
		// Return the initialized ant project
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
