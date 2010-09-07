package org.apache.torque.mojo.morph;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class Morpher {
	private static final Log log = LogFactory.getLog(Morpher.class);

	MorphRequest morphRequest;
	String artifactId;

	public Morpher() {
		this(null, null);
	}

	public Morpher(MorphRequest morphRequest, String artifactId) {
		super();
		this.morphRequest = morphRequest;
		this.artifactId = artifactId;
	}

	protected abstract boolean isMorphNeeded(String contents);

	protected abstract String getMorphedContents(String contents);

	public void executeMorph() throws IOException {
		// Read the "old" schema XML data into a string
		String contents = IOUtils.toString(morphRequest.getInputStream(), morphRequest.getEncoding());

		// May not need to morph
		if (isMorphNeeded(contents)) {
			contents = getMorphedContents(contents);
		} else {
			log.debug("Skipping morph on " + morphRequest.getName());
		}

		// Write the schema to the output stream
		IOUtils.write(contents, morphRequest.getOutputStream(), morphRequest.getEncoding());
	}

	public MorphRequest getMorphRequest() {
		return morphRequest;
	}

	public void setMorphRequest(MorphRequest morphRequest) {
		this.morphRequest = morphRequest;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(String schema) {
		this.artifactId = schema;
	}

}
