package org.apache.torque.mojo;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.torque.task.TorqueDataDumpTask;

/**
 * Reads the content of tables from thh database and stores the data in XML files.
 * 
 * @author Raphael Pieroni (rafale_at_codehaus.org)
 * @author <a href="fischer@seitenbau.de">Thomas Fischer</a>
 * 
 * @goal datadump
 * @phase generate-sources
 */
public class DataDumpMojo extends DataModelTaskMojo {
	/** The context property for the name of the project. */
	public static final String PROJECT_CONTEXT_PROPERTY = "project";

	// The following dummies trick the Mojo Description Extractor
	// into setting the correct default values for
	// outputDir, reportFile, contextPropertiesPath, schemaExcludes
	/**
	 * The directory in which the data files will be created.
	 * 
	 * @parameter property="outputDir" expression="${project.build.directory}/data/torque"
	 */
	private String dummy;

	/**
	 * The location where the report file will be generated.
	 * 
	 * @parameter property="reportFile" expression="../../torque/report.${project.artifact.artifactId}.data.generation"
	 */
	private String dummy2;

	/**
	 * The location where the context property file for velocity will be generated.
	 * 
	 * @parameter property="contextPropertiesPath"
	 *            expression="${project.build.directory}/torque/context.data.properties"
	 */
	private String dummy3;

	/**
	 * The schema files which should be excluded in generation (in ant-style notation).
	 * 
	 * @parameter property="schemaExcludes" expression="id-table-schema.xml"
	 */
	private String dummy4;

	/**
	 * The fully qualified class name of the database driver.
	 * 
	 * @parameter
	 * @required
	 */
	private String driver = null;

	/**
	 * The connect URL of the database.
	 * 
	 * @parameter
	 * @required
	 */
	private String url = null;

	/**
	 * The user name to connect to the database.
	 * 
	 * @parameter
	 * @required
	 */
	private String user = null;

	/**
	 * The password for the database user.
	 * 
	 * @parameter
	 */
	private String password = null;

	/**
	 * The name of the project, used as a prefix of the names of the generated files and the name of the datadtd.
	 * 
	 * @parameter expression="torque"
	 */
	private String projectName = null;

	/**
	 * The name of the xml file to process. Only one xml file can be processed at a time. Overrides the settings
	 * schemaIncludes and schemaExcludes
	 * 
	 * @parameter
	 * @required
	 */
	private String xmlFile = null;

	/**
	 * Creates a new SQLMojo object.
	 */
	public DataDumpMojo() {
		super(new TorqueDataDumpTask());
	}

	/**
	 * Returns the path to the control template.
	 * 
	 * @return "data/Control.vm"
	 */
	protected String getControlTemplate() {
		return "data/dump/Control.vm";
	}

	/**
	 * Returns the context properties for the Texen task.
	 * 
	 * @return The PropertiesConfiguration containing all context properties, not null.
	 */
	protected PropertiesConfiguration getMojoContextProperties() {
		PropertiesConfiguration configuration = new PropertiesConfiguration();
		configuration.addProperty(PROJECT_CONTEXT_PROPERTY, projectName);
		return configuration;
	}

	/**
	 * Configures the Texen task which is wrapped by this mojo. In this implementation, the context properties,
	 * useClasspath, the output directory, the control template, the schema Fileset, the target package, the target
	 * database and the xml file are set.
	 * 
	 * @throws MojoExecutionException
	 *             if an error occurs when setting the Tasks properties.
	 * 
	 * @see TexenTaskMojo#configureTask()
	 */
	protected void configureTask() throws MojoExecutionException {
		super.configureTask();

		TorqueDataDumpTask task = (TorqueDataDumpTask) super.getGeneratorTask();

		task.setDatabaseDriver(driver);
		task.setDatabaseUrl(url);
		task.setDatabaseUser(user);
		task.setDatabasePassword(password);
		task.setXmlFile(xmlFile);
	}

	/**
	 * Returns the fully qualified class name of the database driver.
	 * 
	 * @return the fully qualified class name of the database driver.
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
	 * Returns the database user.
	 * 
	 * @return the userId of the database user.
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Sets the database user.
	 * 
	 * @param user
	 *            the userId of the database user.
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * Returns the name of the project, which is used as prefix for the generated table names and the name of the
	 * datadtd.
	 * 
	 * @return the name of the project.
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * Sets the name of the project, which is used as prefix for the generated table names and the name of the datadtd.
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
	public String getXmlFile() {
		return xmlFile;
	}

	/**
	 * Sets the name of the xml file to process.
	 * 
	 * @param project
	 *            the name of the xml file to process.
	 */
	public void setXmlFile(String xmlFile) {
		this.xmlFile = xmlFile;
	}
}
