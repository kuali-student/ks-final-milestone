package org.apache.torque.mojo;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.tools.ant.types.FileSet;
import org.apache.torque.task.TorqueDataModelTask;
import org.apache.torque.util.JdbcConfigurer;
import org.apache.torque.util.SimpleScanner;
import org.kuali.core.db.torque.PropertyHandlingException;
import org.kuali.db.DatabaseType;

/**
 * The base class for mojos that wrap DataModelTasks
 */
public abstract class DataModelTaskMojo extends TexenTaskMojo {

	/**
	 * The Velocity context property for the target database
	 */
	public static final String TARGET_DATABASE_CONTEXT_PROPERTY = "targetDatabase";

	/**
	 * Database URL.
	 * 
	 * @parameter expression="${url}"
	 */
	String url;

	/**
	 * The suffix of the generated sql files.
	 */
	String suffix = "";

	protected File getCanonicalReportFile() throws MojoExecutionException {
		try {
			String filename = getOutputDir() + FS + getReportFile();
			File file = new File(filename);
			return file.getCanonicalFile();
		} catch (IOException e) {
			throw new MojoExecutionException("Error with report file", e);
		}
	}

	protected FileSet getAntFileSet(File baseDir, String includes, String excludes) {
		FileSet fileSet = new FileSet();
		fileSet.setDir(baseDir);
		fileSet.setIncludes(includes);
		fileSet.setExcludes(excludes);
		return fileSet;
	}

	/**
	 * Validate that some essential configuration items are present
	 */
	protected void updateConfiguration() throws MojoExecutionException {
		try {
			loadPropertiesToMojo();
			new JdbcConfigurer().updateConfiguration(this);
		} catch (PropertyHandlingException e) {
			throw new MojoExecutionException("Error handling properties", e);
		}
	}

	protected String getInvalidTargetDatabaseMessage() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n\n");
		sb.append("Target database of '" + getTargetDatabase() + "' is invalid.\n\n");
		sb.append("Valid values are " + org.springframework.util.StringUtils.arrayToCommaDelimitedString(DatabaseType.values()) + ".\n\n");
		sb.append("Specify targetDatabase in the plugin configuration or as a system property.\n\n");
		sb.append("For example:\n-DtargetDatabase=oracle\n\n.");
		return sb.toString();
	}

	/**
	 * Validate that some essential configuration items are present
	 */
	protected void validateConfiguration() throws MojoExecutionException {
		if (StringUtils.isEmpty(getTargetDatabase())) {
			throw new MojoExecutionException(getInvalidTargetDatabaseMessage());
		}

		try {
			DatabaseType.valueOf(getTargetDatabase().toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new MojoExecutionException(getInvalidTargetDatabaseMessage());
		}
	}

	/**
	 * The path to the directory where the schema XML files are located
	 * 
	 * @parameter expression="${schemaDir}" default-value="${basedir}/src/main/impex"
	 * @required
	 */
	private String schemaDir;

	/**
	 * The schema files from that directory which should be included (ant-style notation).
	 * 
	 * @parameter expression="${schemaIncludes}" default-value="${project.artifactId}.xml"
	 * @required
	 */
	private String schemaIncludes;

	/**
	 * The schema files from that directory which should be excluded (ant-style notation).
	 */
	private String schemaExcludes;

	/**
	 * The database type (e.g. mysql, oracle, ...)
	 * 
	 * @parameter expression="${targetDatabase}"
	 */
	private String targetDatabase;

	/**
	 * The target package for the generated classes.
	 * 
	 * @parameter expression="${targetPackage}" default-value="impex.generated"
	 */
	private String targetPackage;

	/**
	 * The file containing the generation report, relative to <code>outputDir</code>.
	 * 
	 * @required
	 */
	private String reportFile;

	/**
	 * Determines if this task should run only if the schema has changed
	 * 
	 * @parameter expression="${runOnlyOnSchemaChange}" default-value="true"
	 */
	private boolean runOnlyOnSchemaChange;

	/**
	 * The path to the properties file containing the mapping sql file -> target database.
	 * 
	 * @parameter expression="${sqlDbMap}" default-value="${project.build.directory}/reports/sqldbmap.properties"
	 */
	private String sqlDbMap;

	/**
	 * Returns the path to the control template.
	 * 
	 * @return the path to the control template.
	 */
	protected abstract String getControlTemplate();

	protected void addTargetDatabaseToOutputDir() {
		TorqueDataModelTask task = (TorqueDataModelTask) super.getGeneratorTask();
		String newOutputDir = getOutputDir() + FS + getTargetDatabase();
		task.setOutputDirectory(new File(newOutputDir));
		setOutputDir(newOutputDir);
	}

	protected void addTargetDatabaseToReportFile() {
		TorqueDataModelTask task = (TorqueDataModelTask) super.getGeneratorTask();
		String newReportFile = getReportFile() + "." + getTargetDatabase();
		task.setOutputFile(newReportFile);
		setReportFile(newReportFile);
	}

	/**
	 * Configures the Texen task wrapped by this mojo
	 */
	protected void configureTask() throws MojoExecutionException {
		super.configureTask();

		TorqueDataModelTask task = (TorqueDataModelTask) super.getGeneratorTask();
		task.setControlTemplate(getControlTemplate());
		task.setOutputFile(getReportFile());
		task.setTargetDatabase(getTargetDatabase());
		task.setTargetPackage(getTargetPackage());
		if (getSqlDbMap() != null) {
			task.setSqlDbMap(getSqlDbMap());
		}
	}

	protected List<File> getSchemaFiles() {
		return new SimpleScanner(new File(getSchemaDir()), getSchemaIncludes(), getSchemaExcludes()).getFiles();
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

}
