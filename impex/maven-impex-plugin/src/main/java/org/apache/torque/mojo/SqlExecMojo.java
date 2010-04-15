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

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.tools.ant.Project;
import org.apache.torque.task.TorqueSQLExec;
import org.apache.torque.task.TorqueSQLExec.OnError;

/**
 * Executes the generated SQL.
 * 
 * @author Raphael Pieroni (rafale_at_codehaus.org)
 * @author <a href="fischer@seitenbau.de">Thomas Fischer</a>
 * 
 * @goal sqlExec
 * @phase generate-sources
 */
public class SqlExecMojo extends AbstractMojo {
	/**
	 * The ant task this mojo is wrapping.
	 */
	private TorqueSQLExec antTask = new TorqueSQLExec();

	/**
	 * The ant project the ant task is running in.
	 */
	private Project antProject = new Project();

	/**
	 * Autocommit flag. Default value is true.
	 * 
	 * @parameter expression="true"
	 */
	private boolean autocommit = true;

	/**
	 * Tells the Mojo what to do if an sql error occurs during execution. Can be either "continue", "stop" or "abort".
	 * 
	 * @parameter expression="continue".
	 * @required
	 */
	private String onError;

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
	private String password = "";

	/**
	 * The SQL Statement delimiter.
	 * 
	 * @parameter expression=";"
	 */
	private String delimiter = ";";

	/**
	 * Whether the delimiter can be anywhere in the sql ("normal") or needs to be in an extra row ("row").
	 * 
	 * @parameter expression="normal"
	 */
	private String delimiterType = "normal";

	/**
	 * The path to the properties file containing the mapping sql file -> target database.
	 * 
	 * @parameter expression="${project.build.directory}/torque/sqldbmap.properties"
	 */
	private String sqlDbMap;

	/**
	 * The source directory where to find the SQL files.
	 * 
	 * @parameter expression="${project.build.directory}/generated-sql/torque"
	 */
	private String srcDir;

	/**
	 * Creates and initializes a SqlExecMojo.
	 */
	public SqlExecMojo() {
		antProject.init();
		antTask.setProject(antProject);
	}

	/**
	 * Returns whether autocommit is turned on.
	 * 
	 * @return true if autocommit is on, false otherwise.
	 */
	public boolean isAutocommit() {
		return autocommit;
	}

	/**
	 * Sets whether autocommit is turned on.
	 * 
	 * @param autocommit
	 *            true to turn autocommit on, false to turn it off.
	 */
	public void setAutocommit(boolean autocommit) {
		this.autocommit = autocommit;
	}

	/**
	 * The delimiter used to separate SQL commands.
	 * 
	 * @return the delimiter used to separate SQL commands.
	 */
	public String getDelimiter() {
		return delimiter;
	}

	/**
	 * Sets the delimiter used to separate SQL commands.
	 * 
	 * @param delimiter
	 *            the delimiter used to separate SQL commands.
	 */
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	/**
	 * Returns whether the delimiter can be anywhere in the sql ("normal") or needs to be in an extra row ("row").
	 * 
	 * @return the delimiter type.
	 */
	public String getDelimiterType() {
		return delimiterType;
	}

	/**
	 * Sets whether the delimiter can be anywhere in the sql ("normal") or needs to be in an extra row ("row").
	 * 
	 * @param delimiterType
	 *            the delimiter type, should either be "normal" or "row".
	 */
	public void setDelimiterType(String delimiterType) {
		this.delimiterType = delimiterType;
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
	 * Returns the path to the mapping SQL Files -> database.
	 * 
	 * @return the path to the mapping SQL Files -> database.
	 */
	public String getSqlDbMap() {
		return sqlDbMap;
	}

	/**
	 * Sets the path to the mapping SQL Files -> database.
	 * 
	 * @param sqlDbMap
	 *            the absolute path to the mapping SQL Files -> database.
	 */
	public void setSqlDbMap(String sqlDbMap) {
		this.sqlDbMap = sqlDbMap;
	}

	/**
	 * Returns whether to procede if an sql error occurs during execution.
	 * 
	 * @return onError what to do in case of an sql error, can be one of "continue", "stop" or "abort".
	 */
	public String getOnError() {
		return onError;
	}

	/**
	 * Tells the task whether to procede if an sql error occurs during execution. Can be either "continue", "stop" or
	 * "abort".
	 * 
	 * @param onError
	 *            what to do in case of an sql error.
	 */
	public void setOnError(String onError) {
		this.onError = onError;
	}

	/**
	 * Returns the path to the directory where the sql files can be found.
	 * 
	 * @return the source directory where to find the SQL files.
	 */
	public String getSrcDir() {
		return srcDir;
	}

	/**
	 * Sets the path to the directory where the sql files can be found.
	 * 
	 * @param srcDir
	 *            the source directory where to find the SQL files.
	 */
	public void setSrcDir(String srcDir) {
		this.srcDir = srcDir;
	}

	/**
	 * Executes the goal of this mojo.
	 * 
	 * @throws MojoExecutionException
	 *             if the execution fails.
	 * 
	 * @see org.apache.maven.plugin.Mojo#execute()
	 */
	public void execute() throws MojoExecutionException {
		configureTask();
		antTask.execute();
	}

	/**
	 * Transfers the settings in this Mojo to the encapsulated ant task.
	 */
	protected void configureTask() {
		antTask.setDelimiter(getDelimiter());
		getLog().debug("Delimiter = " + getDelimiter());
		antTask.setDelimiterType(getDelimiterTypeForAnt());
		getLog().debug("DelimiterType = " + getDelimiterType());
		antTask.setAutocommit(isAutocommit());
		getLog().debug("Autocommit = " + isAutocommit());
		antTask.setDriver(getDriver());
		getLog().debug("Driver = " + getDriver());
		antTask.setUrl(getUrl());
		getLog().debug("Url = " + getUrl());
		antTask.setUserid(getUser());
		getLog().debug("Userid = " + getUser());
		antTask.setPassword(getPassword());
		getLog().debug("Password = " + getPassword());
		antTask.setSqlDbMap(getSqlDbMap());
		getLog().debug("SqlDbMap = " + getSqlDbMap());
		antTask.setSrcDir(getSrcDir());
		getLog().info("executing SQL files in src dir: " + getSrcDir());
		antTask.setOnerror(getOnErrorAction());
		getLog().debug("onErrorAction = " + getOnErrorAction());
	}

	/**
	 * Returns the onError setting as a OnError object.
	 * 
	 * @return the onError setting as a OnError object, never null.
	 */
	private OnError getOnErrorAction() {
		OnError onErrorAction = new OnError();
		onErrorAction.setValue(onError);
		return onErrorAction;
	}

	/**
	 * Returns the delimiter type in a form acceptable by the ant task.
	 * 
	 * @return the delimiter type setting as a DelimiterType object, never null.
	 */
	private TorqueSQLExec.DelimiterType getDelimiterTypeForAnt() {
		TorqueSQLExec.DelimiterType delimiterType = new TorqueSQLExec.DelimiterType();
		delimiterType.setValue(this.delimiterType);
		return delimiterType;
	}

}
