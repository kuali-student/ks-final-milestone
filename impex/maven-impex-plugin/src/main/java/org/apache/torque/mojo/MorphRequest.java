package org.apache.torque.mojo;

import java.io.File;

public class MorphRequest {
	File oldFile;
	File newFile;

	public File getOldFile() {
		return oldFile;
	}

	public void setOldFile(File oldFile) {
		this.oldFile = oldFile;
	}

	public File getNewFile() {
		return newFile;
	}

	public void setNewFile(File newFile) {
		this.newFile = newFile;
	}
}
