package org.apache.torque.mojo;

import java.io.File;
import java.util.List;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.tools.ant.types.FileSet;
import org.kuali.core.db.torque.KualiTorqueDataSQLTask;

/**
 * Generates SQL from data XML files
 * 
 * @goal datasql
 * @phase generate-sources
 */
public class DataSqlMojo extends DataModelTaskMojo {
	/**
	 * The directory in which the SQL will be generated.
	 * 
	 * @parameter property="outputDir" expression="${outputDir}"
	 *            default-value="${project.build.directory}/generated-sql/impex"
	 * @required
	 */
	@SuppressWarnings("unused")
	private String dummy;

	/**
	 * The location where the SQL file will be generated.
	 * 
	 * @parameter property="reportFile" expression="${reportFile}"
	 *            default-value="../../../impex/report.${project.artifact.artifactId}-data.sql"
	 */
	@SuppressWarnings("unused")
	private String dummy2;

	/**
	 * The location where the context property file for velocity will be generated.
	 * 
	 * @parameter property="contextPropertiesPath" expression="${contextPropertiesPath}"
	 *            default-value="${project.build.directory}/impex/context.datasql.properties"
	 */
	@SuppressWarnings("unused")
	private String dummy3;

	/**
	 * Only run this mojo if the data or schema has changed
	 * 
	 * @parameter expression="${runOnlyOnChange}" default-value="true"
	 * @required
	 */
	private boolean runOnlyOnChange;

	/**
	 * The schema.xml file describing the database
	 * 
	 * @parameter expression="${schemaXMLFile}" default-value="${basedir}/src/main/impex/schema/${project.artifactId}-schema.xml"
	 * @required
	 */
	private File schemaXMLFile;

	/**
	 * The directory containing data XML files
	 * 
	 * @parameter expression="${dataXMLDir}" default-value="${basedir}/src/main/impex/data"
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
	 * @parameter expression="${dataXMLExcludes}"
	 */
	private String dataXMLExcludes;

	/**
	 * The DTD for the data XML files
	 * 
	 * @parameter expression="${dataDTD}" default-value="${project.build.directory}/data/impex"
	 * @required
	 */
	private File dataDTDDir;

	/**
	 * Creates a new SQLMojo object.
	 */
	public DataSqlMojo() {
		super(new KualiTorqueDataSQLTask());
	}

	@Override
	public void execute() throws MojoExecutionException {
		addTargetDatabaseToOutputDir();
		addTargetDatabaseToReportFile();
		if (!isChanged() && isRunOnlyOnChange()) {
			getLog().info("------------------------------------------------------------------------");
			getLog().info("Data and schema are unchanged.  Skipping generation.");
			getLog().info("------------------------------------------------------------------------");
			return;
		}
		getLog().info("------------------------------------------------------------------------");
		getLog().info("Generating SQL for " + getTargetDatabase() + " from data XML files");
		getLog().info("------------------------------------------------------------------------");
		super.execute();
	}

	protected boolean isChanged() {
		if (isDataChanged()) {
			return true;
		}
		if (isSchemaChanged()) {
			return true;
		}
		return false;
	}

	/**
	 * Returns true if any of the data XML files have changed since the time the report file was generated
	 * 
	 * @return whether the data has changed
	 */
	protected boolean isDataChanged() {
		File report = new File(super.getOutputDir(), getReportFile());
		if (!report.exists()) {
			return true;
		}

		FileSet dataXMLFileSet = getDataXMLFileSet();
		List<File> dataXMLFiles = getFiles(dataXMLFileSet);
		for (File dataXMLFile : dataXMLFiles) {
			if (dataXMLFile.lastModified() > report.lastModified()) {
				return true;
			}
		}
		return false;
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
		super.configureTask();
		KualiTorqueDataSQLTask task = (KualiTorqueDataSQLTask) super.getGeneratorTask();
		task.setDataDTDDir(getDataDTDDir());
		task.addFileset(getDataXMLFileSet());
		task.setXmlFile(getSchemaXMLFile().getAbsolutePath());
		task.setTargetDatabase(getTargetDatabase());
	}

	protected FileSet getDataXMLFileSet() {
		FileSet fileset = new FileSet();
		fileset.setDir(getDataXMLDir());
		fileset.setIncludes(getDataXMLIncludes());
		fileset.setExcludes(getDataXMLExcludes());
		return fileset;
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

	public File getDataDTDDir() {
		return dataDTDDir;
	}

	public void setDataDTDDir(File dataDTDDir) {
		this.dataDTDDir = dataDTDDir;
	}

	public boolean isRunOnlyOnChange() {
		return runOnlyOnChange;
	}

	public void setRunOnlyOnChange(boolean runOnlyOnDataChange) {
		this.runOnlyOnChange = runOnlyOnDataChange;
	}

}
