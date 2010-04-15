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

import java.io.File;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.types.FileSet;
import org.apache.torque.task.TorqueDataModelTask;

/**
 * The base class for all mojos wrapping DataModelTasks.
 * 
 * @author Raphael Pieroni (rafale_at_codehaus.org)
 * @author <a href="fischer@seitenbau.de">Thomas Fischer</a>
 */
public abstract class DataModelTaskMojo extends TexenTaskMojo {
	/**
	 * The context property for the database.
	 */
	public static final String TARGET_DATABASE_CONTEXT_PROPERTY = "targetDatabase";

	/**
	 * The path to the directory where the schema files are located in.
	 * 
	 * @parameter expression="${basedir}/src/main/torque/schema"
	 * @required
	 */
	private String schemaDir;

	/**
	 * The schema files which should be included in generation (in ant-style notation).
	 * 
	 * @parameter expression="*schema.xml"
	 * @required
	 */
	private String schemaIncludes;

	/**
	 * The schema files which should be excluded in generation (in ant-style notation).
	 */
	private String schemaExcludes;

	/**
	 * The database type (e.g. mysql, oracle, ...) for the generated persistence classes,
	 * 
	 * @parameter
	 * @required
	 */
	private String targetDatabase;

	/**
	 * The target package for the generated classes.
	 * 
	 * @parameter expression="torque.generated"
	 */
	private String targetPackage;

	/**
	 * The file containing the generation report, relative to <code>outputDir</code>.
	 * 
	 * @required
	 */
	private String reportFile;

	/**
	 * Determines if this task should run only if the schema has changed.
	 * 
	 * @parameter expression="true"
	 */
	private boolean runOnlyOnSchemaChange;

	/**
	 * The path to the properties file containing the mapping sql file -> target database.
	 * 
	 * @parameter expression="${project.build.directory}/torque/sqldbmap.properties"
	 */
	private String sqlDbMap;

	/**
	 * Creates a new TorqueOMMojo object.
	 */
	DataModelTaskMojo() {
		super(new TorqueDataModelTask());
	}

	/**
	 * Creates a new TorqueOMMojo object wrapping the passed TorqueDataModelTask.
	 * 
	 * @param torqueDataModelTask
	 *            the DataModelTask to be wrapped by this Mojo.
	 */
	DataModelTaskMojo(TorqueDataModelTask torqueDataModelTask) {
		super(torqueDataModelTask);
	}

	/**
	 * Configures the Texen task which is wrapped by this mojo. In this implementation, the context properties,
	 * useClasspath, the output directory, the control template, the schema Fileset, the target package and the target
	 * database are set.
	 * 
	 * @throws MojoExecutionException
	 *             if an error occurs when setting the Tasks properties.
	 * 
	 * @see TexenTaskMojo#configureTask()
	 */
	protected void configureTask() throws MojoExecutionException {
		super.configureTask();

		TorqueDataModelTask task = (TorqueDataModelTask) super.getGeneratorTask();

		task.setControlTemplate(getControlTemplate());

		task.setOutputFile(reportFile);

		task.setTargetDatabase(targetDatabase);

		task.setTargetPackage(getTargetPackage());

		if (sqlDbMap != null) {
			task.setSqlDbMap(sqlDbMap);
		}

		{
			FileSet fileSet = new FileSet();
			fileSet.setDir(new File(schemaDir));
			fileSet.setIncludes(schemaIncludes);
			fileSet.setExcludes(schemaExcludes);

			task.addFileset(fileSet);
		}
	}

	/**
	 * Returns whether the schema has changed. This is done by comparing the modification date of all schema files to
	 * the modification date of the report file.
	 * 
	 * @return whether the schema has changed.
	 */
	protected boolean schemaChanged() {
		boolean schemaChanged = true;
		File report = new File(super.getOutputDir(), this.reportFile);
		if (report.exists()) {
			FileSet fileSet = new FileSet();
			fileSet.setDir(new File(schemaDir));
			fileSet.setIncludes(schemaIncludes);
			fileSet.setExcludes(schemaExcludes);

			DirectoryScanner directoryScanner = fileSet.getDirectoryScanner(getAntProject());

			String[] fileNames = directoryScanner.getIncludedFiles();

			/*
			 * File schemaDirectory = new File(schemaDir); FileFilter schemaFileFilter = new FileFilter() { public
			 * boolean accept(File toCheck) { String path = toCheck.getAbsolutePath(); if (schemaExcludes != null &&
			 * !schemaExcludes.trim().equals("") && Pattern.matches(schemaExcludes, path)) { return false; } return
			 * Pattern.matches(schemaIncludes, path); } }; File[] schemaFiles =
			 * schemaDirectory.listFiles(schemaFileFilter);
			 */

			// schema has changed if one of the schema files has a modification
			// Date larger than the report.
			schemaChanged = false;
			for (int i = 0; i < fileNames.length; ++i) {
				File file = new File(fileNames[i]);
				if (file.lastModified() > report.lastModified()) {
					schemaChanged = true;
					break;
				}
			}
		}

		return schemaChanged;
	}

	/**
	 * Returns the directory where the schema files are located.
	 * 
	 * @return the the directory where the schema files are located.
	 */
	public String getSchemaDir() {
		return schemaDir;
	}

	/**
	 * Sets the the directory where the schema files are located.
	 * 
	 * @param schemaDir
	 *            the directory where the schema files are located.
	 */
	public void setSchemaDir(String schemaDir) {
		this.schemaDir = schemaDir;
	}

	/**
	 * Returns the target database (e.g. mysql, oracle, ... ) for the generated files.
	 * 
	 * @return the target database for the generated files.
	 */
	public String getTargetDatabase() {
		return targetDatabase;
	}

	/**
	 * Sets the target database (e.g. mysql, oracle, ... ) for the generated files.
	 * 
	 * @param targetDatabase
	 *            the target database for the generated files.
	 */
	public void setTargetDatabase(String targetDatabase) {
		this.targetDatabase = targetDatabase;
	}

	/**
	 * Returns the target package for the generated classes.
	 * 
	 * @return the target package for the generated classes.
	 */
	public String getTargetPackage() {
		return targetPackage;
	}

	/**
	 * Sets the target package for the generated classes.
	 * 
	 * param targetPackage the target package for the generated classes.
	 */
	public void setTargetPackage(String targetPackage) {
		this.targetPackage = targetPackage;
	}

	/**
	 * Gets the path to the report file. The path is relative to <code>outputDir</code>.
	 * 
	 * @return the path to the report file.
	 */
	public String getReportFile() {
		return reportFile;
	}

	/**
	 * Sets the path to the report file. The path is relative to <code>outputDir</code>.
	 * 
	 * @param reportFile
	 *            the path to the report file.
	 */
	public void setReportFile(String reportFile) {
		this.reportFile = reportFile;
	}

	/**
	 * Returns whether this mojo should be executed only if the schema has changed.
	 * 
	 * @return true if the mojo only runs if the schema has changed, false otherwise.
	 */
	public boolean isRunOnlyOnSchemaChange() {
		return runOnlyOnSchemaChange;
	}

	/**
	 * Sets whether this mojo should be executed only if the schema has changed.
	 * 
	 * @param runOnlyOnSchemaChange
	 *            whether the mojo only should run if the schema has changed.
	 */
	public void setRunOnlyOnSchemaChange(boolean runOnlyOnSchemaChange) {
		this.runOnlyOnSchemaChange = runOnlyOnSchemaChange;
	}

	/**
	 * Runs the generation for the database layout defined in the schema.xml files.
	 * 
	 * @throws MojoExecutionException
	 *             If an error occurs during generation.
	 * 
	 * @see TexenTaskMojo#execute()
	 * @see org.apache.maven.plugin.Mojo#execute()
	 */
	public void execute() throws MojoExecutionException {
		if (!schemaChanged() && isRunOnlyOnSchemaChange()) {
			getLog().info("Schema has not changed; skipping generation");
			return;
		}

		super.execute();
	}

	/**
	 * Returns the schema files which are excluded from generation.
	 * 
	 * @return the pattern for the excluded files.
	 */
	public String getSchemaExcludes() {
		return schemaExcludes;
	}

	/**
	 * Sets the schema files which are excluded from generation.
	 * 
	 * @param schemaExcludes
	 *            the pattern for the excluded files.
	 */
	public void setSchemaExcludes(String schemaExcludes) {
		this.schemaExcludes = schemaExcludes;
	}

	/**
	 * Returns the schema files which are included in generation.
	 * 
	 * @return the pattern for the included files.
	 */
	public String getSchemaIncludes() {
		return schemaIncludes;
	}

	/**
	 * Sets the schema files which are included in generation.
	 * 
	 * @param schemaIncludes
	 *            the pattern for the included files.
	 */
	public void setSchemaIncludes(String schemaIncludes) {
		this.schemaIncludes = schemaIncludes;
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
	 * Returns the path to the control template.
	 * 
	 * @return the path to the control template.
	 */
	protected abstract String getControlTemplate();
}
