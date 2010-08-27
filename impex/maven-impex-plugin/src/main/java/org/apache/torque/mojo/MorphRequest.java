package org.apache.torque.mojo;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 
 * 
 */
public class MorphRequest {

	String name;
	String encoding;
	InputStream inputStream;
	OutputStream outputStream;

	public MorphRequest() {
		this(null, null, null);
	}

	public MorphRequest(String filename, InputStream inputStream, OutputStream outputStream) {
		super();
		this.name = filename;
		this.inputStream = inputStream;
		this.outputStream = outputStream;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream oldData) {
		this.inputStream = oldData;
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(OutputStream newData) {
		this.outputStream = newData;
	}

	public String getName() {
		return name;
	}

	public void setName(String filename) {
		this.name = filename;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

}
