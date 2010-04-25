package org.apache.torque.mojo;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.kuali.core.db.torque.KualiTorqueDataDumpTask;

/**
 * Reads the content of tables from the database and stores the data in XML files.
 * 
 * @goal datadump
 * @phase generate-sources
 */
public class DataDumpMojo extends AntTaskMojo {

	/**
	 * Database type (oracle, mysql etc)
	 * 
	 * @parameter expression="${targetDatabase}"
	 */
	private String targetDatabase;

	/**
	 * Encoding to use when reading SQL statements from a file.
	 * 
	 * @parameter expression="${encoding}" default-value= "${project.build.sourceEncoding}"
	 * @since 1.1
	 */
	private String encoding = "";

	/**
	 * The format to use for dates/timestamps
	 * 
	 * @parameter expression="${dateFormat}" default-value="yyyyMMddHHmmss"
	 * @required
	 */
	private String dateFormat;

	/**
	 * The directory where data XML files will be written
	 * 
	 * @parameter expression="${outputDir}" default-value="${project.build.directory}/impex/data"
	 * @required
	 */
	private File outputDir;

	/**
	 * The schema containing the tables to dump
	 * 
	 * @parameter expression="${schema}"
	 * @required
	 */
	private String schema;

	/**
	 * The fully qualified class name of the database driver.
	 * 
	 * @parameter expression="${driver}"
	 * @required
	 */
	private String driver;

	/**
	 * The connect URL of the database.
	 * 
	 * @parameter expression="${url}"
	 * @required
	 */
	private String url;

	/**
	 * The user name to connect to the database.
	 * 
	 * @parameter expression="${username}"
	 */
	private String username;

	/**
	 * The password for the database user.
	 * 
	 * @parameter expression="${password}"
	 */
	private String password;

	/**
	 * The name of the xml file to process. Only one xml file can be processed at a time. Overrides the settings
	 * schemaIncludes and schemaExcludes
	 * 
	 * @parameter expression="${schemaXMLFile}"
	 *            default-value="${project.build.directory}/impex/data/${project.artifactId}-schema.xml"
	 * @required
	 */
	private String schemaXMLFile;

	/**
	 * Creates a new SQLMojo object.
	 */
	public DataDumpMojo() {
		super(new KualiTorqueDataDumpTask());
	}

	/**
	 * Configure the Ant task
	 */
	protected void configureTask() throws MojoExecutionException {
		super.configureTask();
		KualiTorqueDataDumpTask task = (KualiTorqueDataDumpTask) super.getAntTask();
		task.setDriver(getDriver());
		task.setUrl(getUrl());
		task.setUsername(getUsername());
		task.setPassword(getPassword());
		task.setSchema(getSchema());
		makeOutputDir();
		task.setOutputDirectory(getOutputDir());
		task.setDateFormat(getDateFormat());
		task.setSchemaXMLFile(getSchemaXMLFile());
		task.setEncoding(getEncoding());
		task.setDatabaseType(getTargetDatabase());
	}

	protected void makeOutputDir() throws MojoExecutionException {
		if (getOutputDir().exists()) {
			return;
		}
		try {
			FileUtils.forceMkdir(getOutputDir());
		} catch (IOException e) {
			throw new MojoExecutionException("Error creating output directory", e);
		}
	}

	/**
	 * Returns the fully qualified class name of the database driver.
	 */
	public String getDriver() {
		return driver;
	}

	/**
	 * Sets the fully qualified class name of the database driver.
	 * 
	 * @param driver
	 *            the fully qualified class name of the database driver.
	 */
	public void setDriver(String driver) {
		this.driver = driver;
	}

	/**
	 * Returns the password of the database user.
	 * 
	 * @return the password of the database user.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password of the database user.
	 * 
	 * @param password
	 *            the password of the database user.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Returns the connect URL to the database.
	 * 
	 * @return the connect URL to the database.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the connect URL to the database.
	 * 
	 * @param url
	 *            the connect URL to the database.
	 */
	public void setUrl(String url) {
		this.url = url;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public File getOutputDir() {
		return outputDir;
	}

	public void setOutputDir(File outputDir) {
		this.outputDir = outputDir;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getTargetDatabase() {
		return targetDatabase;
	}

	public void setTargetDatabase(String targetDatabase) {
		this.targetDatabase = targetDatabase;
	}
}
