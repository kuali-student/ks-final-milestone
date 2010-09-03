package org.apache.torque.mojo;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.torque.task.TorqueDataModelTask;
import org.kuali.core.db.torque.Utils;

/**
 * Generates a DTD for the database tables from a schema XML file
 * 
 * @goal datadtd
 * @phase generate-sources
 */
public class DataDtdMojo extends DataModelTaskMojo {
	/** The context property for the name of the project. */
	public static final String PROJECT_CONTEXT_PROPERTY = "project";

	/**
	 * The directory in which the DTD will be generated
	 * 
	 * @parameter property="outputDir" expression="${outputDir}"
	 *            default-value="${project.build.directory}/generated-impex"
	 */
	@SuppressWarnings("unused")
	private String dummy1;

	/**
	 * The location where the report file will be generated, relative to outputDir.
	 * 
	 * @parameter property="reportFile" expression="${reportFile}"
	 *            default-value="../reports/report.${project.artifact.artifactId}.datadtd.generation"
	 */
	@SuppressWarnings("unused")
	private String dummy2;

	/**
	 * The location where the context property file for velocity will be generated.
	 * 
	 * @parameter property="contextPropertiesPath" expression="${contextPropertiesPath}"
	 *            default-value="${project.build.directory}/reports/context.datadtd.properties"
	 */
	@SuppressWarnings("unused")
	private String dummy3;

	/**
	 * The name of the project
	 * 
	 * @parameter expression="${projectName}" default-value="impex"
	 */
	private String projectName;

	/**
	 * The name of the schema.xml file to process
	 * 
	 * @parameter expression="${schemaXMLFile}" default-value="${basedir}/src/main/impex/${project.artifactId}.xml"
	 * @required
	 */
	private String schemaXMLFile;

	/**
	 * Returns the context properties for the Texen task.
	 * 
	 * @return The PropertiesConfiguration containing all context properties, not null.
	 */
	protected PropertiesConfiguration getMojoContextProperties() {
		PropertiesConfiguration configuration = new PropertiesConfiguration();
		configuration.addProperty(PROJECT_CONTEXT_PROPERTY, getProjectName());
		configuration.addProperty("version", getProject().getVersion());
		return configuration;
	}

	/**
	 * Configures the Texen task wrapped by this mojo
	 */
	protected void configureTask() throws MojoExecutionException {
		TorqueDataModelTask task = new TorqueDataModelTask();
		setAntTask(task);
		super.configureTask();
		boolean exists = new Utils().isFileOrResource(getSchemaXMLFile());
		if (!exists) {
			throw new MojoExecutionException("Unable to locate: " + getSchemaXMLFile());
		}
		task.setXmlFile(getSchemaXMLFile());

	}

	/**
	 * Returns the path to the control template.
	 * 
	 * @return "data/Control.vm"
	 */
	protected String getControlTemplate() {
		return "data/Control.vm";
	}

	/**
	 * Returns the name of the project
	 * 
	 * @return the name of the project.
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * Sets the name of the project
	 * 
	 * @param project
	 *            the name of the project.
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * Returns the name of the xml file to process.
	 * 
	 * @return the name of the xml file to process.
	 */
	public String getSchemaXMLFile() {
		return schemaXMLFile;
	}

	/**
	 * Sets the name of the xml file to process.
	 * 
	 * @param project
	 *            the name of the xml file to process.
	 */
	public void setSchemaXMLFile(String xmlFile) {
		this.schemaXMLFile = xmlFile;
	}

	@Override
	public void executeMojo() throws MojoExecutionException {
		getLog().info("-----------------------------------------------------------------");
		getLog().info("Generating database DTD");
		getLog().info("-----------------------------------------------------------------");
		super.executeMojo();
	}
}
