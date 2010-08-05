package org.apache.torque.mojo;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/**
 * A base class for mojos that wrap an Ant Task
 */
public class AntTaskMojo extends AbstractMojo {

	/**
	 * When <code>true</code>, skip the execution.
	 * 
	 * @since 1.0
	 * @parameter default-value="false"
	 */
	private boolean skip;

	/**
	 * Setting this parameter to <code>true</code> will force the execution of this mojo, even if it would get skipped
	 * usually.
	 * 
	 * @parameter expression="${forceAntTaskExecution}" default-value=false
	 * @required
	 */
	private boolean forceMojoExecution;

	/**
	 * The Maven project this plugin runs in.
	 * 
	 * @parameter expression="${project}"
	 * @required
	 * @readonly
	 */
	private MavenProject project;

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
	 * <p>
	 * Determine if the mojo execution should get skipped.
	 * </p>
	 * This is the case if:
	 * <ul>
	 * <li>{@link #skip} is <code>true</code></li>
	 * <li>if the mojo gets executed on a project with packaging type 'pom' and {@link #forceMojoExecution} is
	 * <code>false</code></li>
	 * </ul>
	 * 
	 * @return <code>true</code> if the mojo execution should be skipped.
	 */
	protected boolean skipMojo() {
		if (skip) {
			getLog().info("Skip morpher execution");
			return true;
		}

		if (!forceMojoExecution && project != null && "pom".equals(project.getPackaging())) {
			getLog().info("Skipping execution for project with packaging type 'pom'");
			return true;
		}

		return false;
	}

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
	public void execute() throws MojoExecutionException {
		if (skipMojo()) {
			return;
		}
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

	public MavenProject getProject() {
		return project;
	}

	public void setProject(MavenProject project) {
		this.project = project;
	}
}
