package org.apache.torque.mojo;

import java.io.File;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ChangeDetector {

	private static final Log log = LogFactory.getLog(ChangeDetectorOld.class);
	File controlFile;
	List<File> files;

	public ChangeDetector() {
		this(null, null);
	}

	public ChangeDetector(File controlFile, List<File> files) {
		super();
		this.controlFile = controlFile;
		this.files = files;
	}

	/**
	 * Return true if any file in the list of files has a timestamp newer than the control file or if the control file
	 * does not exist
	 */
	public boolean isChanged() {
		if (!getControlFile().exists()) {
			log.debug("File " + getControlFile().getAbsolutePath() + " does not exist.  Returning true");
			return true;
		}
		long lastModified = getControlFile().lastModified();
		for (File file : files) {
			if (file.lastModified() > lastModified) {
				log.debug("File " + file.getAbsolutePath() + " was modified after " + getControlFile().getAbsolutePath() + " was last modified");
				return true;
			}
		}
		log.debug("No files were modified.");
		return false;
	}

	public File getControlFile() {
		return controlFile;
	}

	public void setControlFile(File controlFile) {
		this.controlFile = controlFile;
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}
}
