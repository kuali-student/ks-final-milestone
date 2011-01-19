package org.kuali.maven.mojo.s3;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.settings.Settings;

/**
 * Mojo essentials. Contains the "skip" logic that is the de facto standard for maven plugins. Contains a number of
 * maven related properties that are common to most mojos. Also sets up logging so that if libraries called by a mojo
 * issue log statements to Jakarta Commons Logging or Log4j, those log messages are shown in maven's output
 */
public abstract class BaseMojo extends AbstractMojo {

    /**
     * Convenience reference to System.getProperty("file.separator").
     */
    public static final String FS = System.getProperty("file.separator");

    /**
     * Skip packaging if type is "pom".
     */
    public static final String SKIP_PACKAGING_TYPE = "pom";

    /**
     * When <code>true</code>, skip the execution of this mojo.
     *
     * @parameter default-value="false"
     */
    private boolean skip;

    /**
     * Setting this parameter to <code>true</code> will force the execution of this mojo, even if it would get skipped
     * usually.
     *
     * @parameter expression="${forceMojoExecution}" default-value="false"
     * @required
     */
    private boolean forceMojoExecution;

    /**
     * The encoding to use when reading/writing files. If not specified this defaults to the platform specific encoding
     * of whatever machine the build is running on.
     *
     * @parameter expression="${encoding}" default-value="${project.build.sourceEncoding}"
     */
    private String encoding;

    /**
     * The Maven project this plugin runs in.
     *
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;

    /**
     * @parameter expression="${settings}"
     * @required
     * @since 1.0
     * @readonly
     */
    private Settings settings;

    /**
     * @parameter default-value="${session}"
     * @required
     * @readonly
     */
    private MavenSession mavenSession;

    public void execute() throws MojoExecutionException, MojoFailureException {
        if (skipMojo()) {
            return;
        }
        executeMojo();
    }

    /**
     * This method is called after logging has been configured and only if mojo execution should not be skipped.
     *
     * @throws MojoExecutionException
     * @throws MojoFailureException
     */
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
        if (skip) {
            getLog().info("Skipping execution");
            return true;
        }

        if (!forceMojoExecution && project != null && SKIP_PACKAGING_TYPE.equals(project.getPackaging())) {
            getLog().info("Skipping execution for project with packaging type '" + SKIP_PACKAGING_TYPE + "'");
            return true;
        }

        return false;
    }

    /**
     * @return the skip
     */
    public boolean isSkip() {
        return skip;
    }

    /**
     * @param skip
     * the skip to set
     */
    public void setSkip(final boolean skip) {
        this.skip = skip;
    }

    /**
     * @return the forceMojoExecution
     */
    public boolean isForceMojoExecution() {
        return forceMojoExecution;
    }

    /**
     * @param forceMojoExecution
     * the forceMojoExecution to set
     */
    public void setForceMojoExecution(final boolean forceMojoExecution) {
        this.forceMojoExecution = forceMojoExecution;
    }

    /**
     * @return the encoding
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * @param encoding
     * the encoding to set
     */
    public void setEncoding(final String encoding) {
        this.encoding = encoding;
    }

    /**
     * @return the project
     */
    public MavenProject getProject() {
        return project;
    }

    /**
     * @param project
     * the project to set
     */
    public void setProject(final MavenProject project) {
        this.project = project;
    }

    /**
     * @return the settings
     */
    public Settings getSettings() {
        return settings;
    }

    /**
     * @param settings
     * the settings to set
     */
    public void setSettings(final Settings settings) {
        this.settings = settings;
    }

    /**
     * @return the mavenSession
     */
    public MavenSession getMavenSession() {
        return mavenSession;
    }

    /**
     * @param mavenSession
     * the mavenSession to set
     */
    public void setMavenSession(final MavenSession mavenSession) {
        this.mavenSession = mavenSession;
    }

}
