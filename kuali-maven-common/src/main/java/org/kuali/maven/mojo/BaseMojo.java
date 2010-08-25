package org.kuali.maven.mojo;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.kuali.maven.mojo.MavenLogger;

/**
 * Mojo essentials. Contains the "skip" logic that is the de facto standard for maven plugins. Contains a number of
 * maven related properties that are common to most mojos. Also sets up logging so that if libraries called by a mojo
 * issue log statements to Jakarta Commons Logging or Log4j, those log messages are show in maven's output
 */
public abstract class BaseMojo extends AbstractMojo {
	public static final String FS = System.getProperty("file.separator");
	public static final String SKIP_PACKAGING_TYPE = "pom";

	public abstract boolean isSkip();

	public abstract boolean isForceMojoExecution();

	public abstract MavenProject getProject();

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		MavenLogger.startPluginLog(this);
		if (skipMojo()) {
			return;
		}
		executeMojo();
		MavenLogger.endPluginLog(this);
	}

	protected abstract void executeMojo() throws MojoExecutionException, MojoFailureException;

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
		if (isSkip()) {
			getLog().info("Skipping execution");
			return true;
		}

		if (!isForceMojoExecution() && getProject() != null && SKIP_PACKAGING_TYPE.equals(getProject().getPackaging())) {
			getLog().info("Skipping execution for project with packaging type '" + SKIP_PACKAGING_TYPE + "'");
			return true;
		}

		return false;
	}
}
