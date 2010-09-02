package org.apache.torque.mojo;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.kuali.core.db.torque.KualiTorqueDataDumpTask;

/**
 * Reads the content of tables from the database and exports the data to XML files.
 * 
 * @goal exportdata
 * @phase generate-sources
 */
public class ExportDataMojo extends ExportMojo {

	/**
	 * The format to use for dates/timestamps
	 * 
	 * @parameter expression="${dateFormat}" default-value="yyyyMMddHHmmss z"
	 * @required
	 */
	private String dateFormat;

	/**
	 * The directory where data XML files will be written
	 * 
	 * @parameter expression="${outputDir}" default-value="${basedir}/src/main/impex"
	 * @required
	 */
	private File outputDir;

	/**
	 * Configure the Ant task
	 */
	protected void configureTask() throws MojoExecutionException {
		KualiTorqueDataDumpTask task = new KualiTorqueDataDumpTask();
		setAntTask(task);
		super.configureTask();
		makeOutputDir();
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
}
