package org.apache.torque.mojo;

import java.io.File;
import java.util.List;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.torque.util.ChangeDetector;
import org.apache.torque.util.SimpleScanner;
import org.kuali.core.db.torque.KualiTorqueDataSQLTask;

/**
 * Generates platform specific SQL from database agnostic XML files. Each SQL file created by this goal contains data
 * for a single table. The database platform to generate SQL for is determined by ${targetDatabase}. See also
 * <code>impex:schemasql</code>
 * 
 * @goal datasql
 * @phase generate-sources
 */
public class DataSqlMojo extends DataModelTaskMojo {
	/**
	 * The directory in which the SQL will be generated.
	 * 
	 * @parameter property="outputDir" expression="${outputDir}" default-value="${project.build.directory}/classes/sql"
	 * @required
	 */
	@SuppressWarnings("unused")
	private String dummy;

	/**
	 * The location where the SQL file will be generated.
	 * 
	 * @parameter property="reportFile" expression="${reportFile}"
	 *            default-value="../../../reports/report.${project.artifactId}-data.sql"
	 */
	@SuppressWarnings("unused")
	private String dummy2;

	/**
	 * The location where the context property file for velocity will be generated.
	 * 
	 * @parameter property="contextPropertiesPath" expression="${contextPropertiesPath}"
	 *            default-value="${project.build.directory}/reports/context.datasql.properties"
	 */
	@SuppressWarnings("unused")
	private String dummy3;

	/**
	 * Only run this mojo if the data or schema has changed since the last run
	 * 
	 * @parameter expression="${runOnlyOnChange}" default-value="true"
	 * @required
	 */
	private boolean runOnlyOnChange;

	/**
	 * The XML file describing the database schema
	 * 
	 * @parameter expression="${schemaXMLFile}" default-value="${basedir}/src/main/impex/${project.artifactId}.xml"
	 * @required
	 */
	private File schemaXMLFile;

	/**
	 * The directory containing data XML files
	 * 
	 * @parameter expression="${dataXMLDir}" default-value="${basedir}/src/main/impex"
	 * @required
	 */
	private File dataXMLDir;

	/**
	 * The default set of files in that directory to include (ant style notation)
	 * 
	 * @parameter expression="${dataXMLIncludes}" default-value="*.xml"
	 * @required
	 */
	private String dataXMLIncludes;

	/**
	 * The default set of files in that directory to exclude (ant style notation)
	 * 
	 * @parameter expression="${dataXMLExcludes}" default-value="${project.artifactId}.xml"
	 */
	private String dataXMLExcludes;

	/**
	 * The DTD for the data XML files
	 * 
	 * @parameter expression="${dataDTD}"
	 *            default-value="${project.build.directory}/generated-impex/${project.artifactId}.dtd"
	 * @required
	 */
	private File dataDTD;

	@Override
	public void executeMojo() throws MojoExecutionException {
		// loadPropertiesToMojo();
		updateConfiguration();
		validateConfiguration();
		generateContextProperties();
		configureTask();
		addTargetDatabaseToOutputDir();
		addTargetDatabaseToReportFile();
		ChangeDetector schema = new ChangeDetector(getCanonicalReportFile(), getSchemaFiles());
		ChangeDetector data = new ChangeDetector(getCanonicalReportFile(), getDataFiles());
		if (!schema.isChanged() && !data.isChanged() && isRunOnlyOnChange()) {
			getLog().info("------------------------------------------------------------------------");
			getLog().info("Data and schema are unchanged.  Skipping generation.");
			getLog().info("------------------------------------------------------------------------");
			return;
		}
		getLog().info("------------------------------------------------------------------------");
		getLog().info("Generating SQL for " + getTargetDatabase() + " from data XML files");
		getLog().info("------------------------------------------------------------------------");
		getAntTask().execute();
	}

	protected List<File> getDataFiles() {
		return new SimpleScanner(getDataXMLDir(), getDataXMLIncludes(), getDataXMLExcludes()).getFiles();
	}

	/**
	 * Returns the context properties for the Texen task.
	 * 
	 * @return The PropertiesConfiguration containing all context properties, not null.
	 */
	protected PropertiesConfiguration getMojoContextProperties() {
		PropertiesConfiguration configuration = new PropertiesConfiguration();
		configuration.addProperty(TARGET_DATABASE_CONTEXT_PROPERTY, super.getTargetDatabase());
		return configuration;
	}

	/**
	 * Configures the Texen task wrapped by this mojo
	 */
	protected void configureTask() throws MojoExecutionException {
		KualiTorqueDataSQLTask task = new KualiTorqueDataSQLTask();
		setAntTask(task);
		super.configureTask();
		task.setDataDTD(getDataDTD());
		task.addFileset(getAntFileSet(getDataXMLDir(), getDataXMLIncludes(), getDataXMLExcludes()));
		task.setXmlFile(getSchemaXMLFile().getAbsolutePath());
		task.setTargetDatabase(getTargetDatabase());
	}

	/**
	 * Returns the path to the control template.
	 * 
	 * @return "sql/load/Control.vm"
	 */
	protected String getControlTemplate() {
		return "sql/load/Control.vm";
	}

	public String getDataXMLIncludes() {
		return dataXMLIncludes;
	}

	public void setDataXMLIncludes(String dataXMLIncludes) {
		this.dataXMLIncludes = dataXMLIncludes;
	}

	public String getDataXMLExcludes() {
		return dataXMLExcludes;
	}

	public void setDataXMLExcludes(String dataXMLExcludes) {
		this.dataXMLExcludes = dataXMLExcludes;
	}

	public File getDataXMLDir() {
		return dataXMLDir;
	}

	public void setDataXMLDir(File dataXMLDir) {
		this.dataXMLDir = dataXMLDir;
	}

	public File getSchemaXMLFile() {
		return schemaXMLFile;
	}

	public void setSchemaXMLFile(File schemaXMLFile) {
		this.schemaXMLFile = schemaXMLFile;
	}

	public boolean isRunOnlyOnChange() {
		return runOnlyOnChange;
	}

	public void setRunOnlyOnChange(boolean runOnlyOnDataChange) {
		this.runOnlyOnChange = runOnlyOnDataChange;
	}

	public File getDataDTD() {
		return dataDTD;
	}

	public void setDataDTD(File dataDTD) {
		this.dataDTD = dataDTD;
	}

}
