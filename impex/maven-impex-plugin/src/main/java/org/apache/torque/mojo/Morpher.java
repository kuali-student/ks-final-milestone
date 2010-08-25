package org.apache.torque.mojo;

import java.io.IOException;

public abstract class Morpher {
	MorphRequest morphRequest;

	public abstract void executeMorph(String encoding) throws IOException;

	public MorphRequest getMorphRequest() {
		return morphRequest;
	}

	public void setMorphRequest(MorphRequest morphRequest) {
		this.morphRequest = morphRequest;
	}

}
