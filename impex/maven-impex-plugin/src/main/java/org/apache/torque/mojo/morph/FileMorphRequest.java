package org.apache.torque.mojo.morph;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileMorphRequest extends MorphRequest {
	File oldFile;
	File newFile;

	public FileMorphRequest(File oldFile, File newFile) {
		super();
		this.oldFile = oldFile;
		this.newFile = newFile;
	}

	public FileMorphRequest() {
		this(null, null);
	}

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

	@Override
	public InputStream getInputStream() {
		try {
			return new FileInputStream(oldFile);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public OutputStream getOutputStream() {
		try {
			return new FileOutputStream(newFile);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

}
