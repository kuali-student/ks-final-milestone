package org.apache.torque.mojo;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.kuali.core.db.torque.KualiTorqueSchemaDumpTask;

/**
 * Dump the schema to an XML file
 * 
 * @goal schemadump
 * @phase generate-sources
 */
public class SchemaDumpMojo extends AntTaskMojo {
	private static final String FS = System.getProperty("file.separator");

	/**
	 * Database type (oracle, mysql etc)
	 * 
	 * @parameter expression="${targetDatabase}"
	 */
	private String targetDatabase;

	/**
	 * The encoding to use when creating the schema XML file
	 * 
	 * @parameter expression="${encoding}" default-value= "${project.build.sourceEncoding}"
	 * @since 1.1
	 */
	private String encoding = "";

	/**
	 * The directory where the schema XML file is to be written
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
	 * The name that should end up in the schema XML for this schema dump (ks-core-db, ks-lum-db etc)
	 * 
	 * @parameter expression="${schemaXMLName}"
	 */
	private String schemaXMLName;

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
	 * Creates a new SQLMojo object.
	 */
	public SchemaDumpMojo() {
		super(new KualiTorqueSchemaDumpTask());
	}

	/**
	 * Configure the Ant task
	 */
	protected void configureTask() throws MojoExecutionException {
		super.configureTask();
		KualiTorqueSchemaDumpTask task = (KualiTorqueSchemaDumpTask) super.getAntTask();
		task.setDriver(getDriver());
		task.setUrl(getUrl());
		task.setUsername(getUsername());
		task.setPassword(getPassword());
		task.setSchema(getSchema());
		makeOutputDir();
		task.setEncoding(getEncoding());
		task.setTargetDatabase(getTargetDatabase());
		task.setSchemaXMLName(getSchemaXMLName());
		task.setSchemaXMLFile(new File(getOutputDir() + FS + getSchemaXMLName() + "-schema.xml"));
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

	public String getSchemaXMLName() {
		return schemaXMLName;
	}

	public void setSchemaXMLName(String schemaXMLName) {
		this.schemaXMLName = schemaXMLName;
	}
}
