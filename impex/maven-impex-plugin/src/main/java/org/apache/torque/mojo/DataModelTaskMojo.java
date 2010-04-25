package org.apache.torque.mojo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.types.FileSet;
import org.apache.torque.task.TorqueDataModelTask;

/**
 * The base class for mojos that wrap DataModelTasks
 */
public abstract class DataModelTaskMojo extends TexenTaskMojo {

	/**
	 * The Velocity context property for the target database
	 */
	public static final String TARGET_DATABASE_CONTEXT_PROPERTY = "targetDatabase";

	/**
	 * The path to the directory where the schema XML files are located
	 * 
	 * @parameter expression="${schemaDir}" default-value="${basedir}/src/main/impex/schema"
	 * @required
	 */
	private String schemaDir;

	/**
	 * The schema files from that directory which should be included (ant-style notation).
	 * 
	 * @parameter expression="${schemaDir}" default-value="*schema.xml"
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
	 * @parameter expression="${sqlDbMap}" default-value="${project.build.directory}/impex/sqldbmap.properties"
	 */
	private String sqlDbMap;

	/**
	 * Creates a new DataModelTaskMojo object.
	 */
	DataModelTaskMojo() {
		super(new TorqueDataModelTask());
	}

	/**
	 * Creates a new DataModelTaskMojo object wrapping the passed TorqueDataModelTask.
	 * 
	 * @param torqueDataModelTask
	 *            the DataModelTask to be wrapped by this Mojo.
	 */
	DataModelTaskMojo(TorqueDataModelTask torqueDataModelTask) {
		super(torqueDataModelTask);
	}

	/**
	 * Returns the path to the control template.
	 * 
	 * @return the path to the control template.
	 */
	protected abstract String getControlTemplate();

	protected void addTargetDatabaseToOutputDir() {
		TorqueDataModelTask task = (TorqueDataModelTask) super.getGeneratorTask();
		String newOutputDir = getOutputDir() + "/" + getTargetDatabase();
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
	 * Analyze schema change properties to determine if the mojo should execute
	 */
	public void execute() throws MojoExecutionException {
		super.execute();
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

	protected FileSet getSchemaXMLFileSet() {
		FileSet fileSet = new FileSet();
		fileSet.setDir(new File(getSchemaDir()));
		fileSet.setIncludes(getSchemaIncludes());
		fileSet.setExcludes(getSchemaExcludes());
		return fileSet;
	}

	protected List<File> getFiles(FileSet fileSet) {
		DirectoryScanner directoryScanner = fileSet.getDirectoryScanner(getAntProject());
		List<File> files = new ArrayList<File>();
		String[] fileNames = directoryScanner.getIncludedFiles();
		File srcDir = directoryScanner.getBasedir();
		for (int i = 0; i < fileNames.length; ++i) {
			File file = new File(srcDir, fileNames[i]);
			files.add(file);
		}
		return files;
	}

	protected List<File> getSchemaFiles() {
		FileSet schemaXMLFileSet = getSchemaXMLFileSet();
		return getFiles(schemaXMLFileSet);
	}

	/**
	 * Returns true if any of the schema XML files have changed since the time the report file was generated
	 * 
	 * @return whether the schema has changed.
	 */
	protected boolean isSchemaChanged() {
		File report = new File(super.getOutputDir(), getReportFile());
		if (!report.exists()) {
			return true;
		}

		List<File> schemaFiles = getSchemaFiles();
		for (File schemaFile : schemaFiles) {
			getLog().debug("schemaFile.lastModified=" + schemaFile.lastModified() + " " + schemaFile.getAbsolutePath());
			getLog().debug("report.lastModified=" + report.lastModified() + " " + report.getAbsolutePath());
			if (schemaFile.lastModified() > report.lastModified()) {
				return true;
			}
		}
		return false;
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

}
