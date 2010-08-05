package org.apache.torque.mojo;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.FileConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.texen.ant.TexenTask;

/**
 * The base class for mojos that wrap Texen Ant Tasks
 */
public abstract class TexenTaskMojo extends AntTaskMojo {
	/**
	 * The directory where the generator output is written
	 * 
	 * @required
	 */
	private String outputDir;

	/**
	 * The base path where the templates are read from, if they are not read from the classpath.
	 * 
	 * @parameter expression="${templatePath}" default-value="${basedir}/src/main/impex/templates"
	 */
	private String templatePath;

	/**
	 * Whether the templates should be loaded from the classpath.
	 * 
	 * @parameter expression="${useClasspath}" default-value="true"
	 */
	private boolean useClasspath;

	/**
	 * A map where all user-defined context properties can be set. Overrides all other mojo configuration settings which
	 * are mapped to context properties.
	 * 
	 * @parameter
	 */
	private Map<?, ?> userContextProperties;

	/**
	 * The path to the generated context property file.
	 * 
	 * @required
	 */
	private String contextPropertiesPath;

	/**
	 * Creates a new instance of AbstractTorqueMojo.
	 * 
	 * @throws IllegalArgumentException
	 *             if generatorTask is null.
	 */
	public TexenTaskMojo(TexenTask generatorTask) {
		super(generatorTask);
	}

	/**
	 * Sets the path to Torque's output directory.
	 * 
	 * @param outputDir
	 *            the path to Torque's output directory.
	 */
	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}

	/**
	 * Returns the path to Torque's output directory.
	 * 
	 * @return the path to Torque's output directory. Not null if initialized correctly.
	 */
	public String getOutputDir() {
		return this.outputDir;
	}

	/**
	 * Sets the path to Torque's templates, if the classpath is not used to load the templates.
	 * 
	 * @param templatePath
	 *            the path to Torque's templates.
	 */
	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	/**
	 * Returns the path to Torque's templates, if the classpath is not used to load the templates.
	 * 
	 * @return the path to Torque's templates.
	 */
	public String getTemplatePath() {
		return this.templatePath;
	}

	/**
	 * Sets whether the classpath should be used to locate the templates.
	 * 
	 * @param templatePath
	 *            the path to Torque's templates.
	 */
	public void setUseClasspath(boolean useClasspath) {
		this.useClasspath = useClasspath;
	}

	/**
	 * Returns whether the classpath is used to locate the templates.
	 * 
	 * @return true if the classpath is used to locate the templates, false otherwise
	 */
	public boolean getUseClasspath() {
		return this.useClasspath;
	}

	/**
	 * Sets the path to the generated property file used as Texen's context properties.
	 * 
	 * @param generatedPropertyFilePath
	 *            the path to the generated context properties file.
	 */
	public void setContextPropertiesPath(String contextPropertiesPath) {
		this.contextPropertiesPath = contextPropertiesPath;
	}

	/**
	 * Returns the path to the generated property file used as Texen's context properties.
	 * 
	 * @return the path to the generated context properties file.
	 */
	public String getContextPropertiesPath() {
		return this.contextPropertiesPath;
	}

	/**
	 * Sets the map which defines user-defined context properties. The settings override all other mojo configuration
	 * settings which are mapped to context properties.
	 * 
	 * @param contextProperties
	 *            the user-defined context properties.
	 */
	public void setUserContextProperties(Map<?, ?> userContextProperties) {
		this.userContextProperties = userContextProperties;
	}

	/**
	 * Returns the map which defines user-defined context properties.
	 * 
	 * @return the map containing user-defined context properties, or null if not set.
	 */
	public Map<?, ?> getUserContextProperties() {
		return userContextProperties;
	}

	/**
	 * returns the generator Task for this mojo.
	 * 
	 * @return the generator Task, not null.
	 */
	protected TexenTask getGeneratorTask() {
		return (TexenTask) getAntTask();
	}

	/**
	 * Generates the context properties file for Texen. The file is written to the path contextPropertiesPath.
	 * 
	 * @throws MojoExecutionException
	 *             if an error occurs.
	 */
	protected void generateContextProperties() throws MojoExecutionException {
		try {
			FileConfiguration configuration = getMojoContextProperties();
			if (userContextProperties != null) {
				for (Iterator<?> contextPropertyIt = userContextProperties.entrySet().iterator(); contextPropertyIt.hasNext();) {
					Map.Entry<?, ?> entry = (Map.Entry<?, ?>) contextPropertyIt.next();
					configuration.addProperty(entry.getKey().toString(), entry.getValue().toString());
				}
			}
			configuration.save(contextPropertiesPath);
		} catch (ConfigurationException e) {
			getLog().error("Error writing temporary context properties: " + e.getMessage());
			throw new MojoExecutionException(e.getMessage());
		}
	}

	/**
	 * Configures the Texen task wrapped by this mojo.
	 */
	protected void configureTask() throws MojoExecutionException {
		getGeneratorTask().setContextProperties(contextPropertiesPath);

		getGeneratorTask().setUseClasspath(useClasspath);

		if (templatePath != null) {
			try {
				getGeneratorTask().setTemplatePath(templatePath);
			} catch (Exception e) {
				getLog().error("Error setting template path: " + e.getMessage());
				throw new MojoExecutionException(e.getMessage());
			}
		}

		File outputDirectory = new File(outputDir);
		getLog().debug("generating torque java sources into: " + outputDirectory.getAbsolutePath());
		outputDirectory.mkdirs();
		getGeneratorTask().setOutputDirectory(outputDirectory);
	}

	/**
	 * Executes the wrapped Texen task. Before this is done, the context properties file is generated and the Texen task
	 * is configured.
	 * 
	 * @throws MojoExecutionException
	 *             if an error occurs during execution.
	 * 
	 * @see org.apache.maven.plugin.Mojo#execute()
	 */
	public void execute() throws MojoExecutionException {
		if (skipMojo()) {
			return;
		}
		generateContextProperties();
		super.execute();
	}

	/**
	 * Returns the context properties for the Texen task which are defined in the configuration of the child mojo. This
	 * method needs to be overwritten in subclasses.
	 * 
	 * @return The PropertiesConfiguration containing all context properties, not null.
	 */
	protected abstract PropertiesConfiguration getMojoContextProperties();
}
