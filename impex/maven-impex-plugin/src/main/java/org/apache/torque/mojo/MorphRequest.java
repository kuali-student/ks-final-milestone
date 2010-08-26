package org.apache.torque.mojo;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 
 * 
 */
public class MorphRequest {

	InputStream oldData;
	OutputStream newData;

	public MorphRequest() {
		this(null, null);
	}

	public MorphRequest(InputStream oldData, OutputStream newData) {
		super();
		this.oldData = oldData;
		this.newData = newData;
	}

	public InputStream getOldData() {
		return oldData;
	}

	public void setOldData(InputStream oldData) {
		this.oldData = oldData;
	}

	public OutputStream getNewData() {
		return newData;
	}

	public void setNewData(OutputStream newData) {
		this.newData = newData;
	}

}
