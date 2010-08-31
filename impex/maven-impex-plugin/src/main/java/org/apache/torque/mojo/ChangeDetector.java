package org.apache.torque.mojo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ChangeDetector {
	private static final Log log = LogFactory.getLog(ChangeDetector.class);
	String outputDir;
	String reportFile;
	List<File> schemaFiles = new ArrayList<File>();
	List<File> dataFiles = new ArrayList<File>();

	public ChangeDetector() {
		this(null, null, null, null);
	}

	public ChangeDetector(String outputDir, String reportFile, List<File> schemaFiles, List<File> dataFiles) {
		super();
		this.outputDir = outputDir;
		this.reportFile = reportFile;
		this.schemaFiles = schemaFiles;
		this.dataFiles = dataFiles;
	}

	/**
	 * Return true if any of the data XML files have changed of if the schema XML file has changed
	 */
	public boolean isChanged() {
		if (isDataChanged()) {
			return true;
		}
		if (isSchemaChanged()) {
			return true;
		}
		return false;
	}

	public boolean isDataChanged() {
		File report = new File(getOutputDir(), getReportFile());
		if (!report.exists()) {
			return true;
		}

		for (File dataFile : dataFiles) {
			if (dataFile.lastModified() > report.lastModified()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns true if any of the schema XML files have changed since the time the report file was generated
	 * 
	 * @return whether the schema has changed.
	 */
	public boolean isSchemaChanged() {
		File report = new File(getOutputDir(), getReportFile());
		if (!report.exists()) {
			return true;
		}

		List<File> schemaFiles = getSchemaFiles();
		for (File schemaFile : schemaFiles) {
			log.debug("schemaFile.lastModified=" + schemaFile.lastModified() + " " + schemaFile.getAbsolutePath());
			log.debug("report.lastModified=" + report.lastModified() + " " + report.getAbsolutePath());
			if (schemaFile.lastModified() > report.lastModified()) {
				return true;
			}
		}
		return false;
	}

	public String getOutputDir() {
		return outputDir;
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}

	public String getReportFile() {
		return reportFile;
	}

	public void setReportFile(String reportFile) {
		this.reportFile = reportFile;
	}

	public List<File> getSchemaFiles() {
		return schemaFiles;
	}

	public void setSchemaFiles(List<File> schemaFiles) {
		this.schemaFiles = schemaFiles;
	}

	public List<File> getDataFiles() {
		return dataFiles;
	}

	public void setDataFiles(List<File> dataFiles) {
		this.dataFiles = dataFiles;
	}

}
