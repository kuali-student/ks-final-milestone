package org.apache.torque.mojo.morph;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
		// Read the "old" data into a string
		InputStream in = morphRequest.getInputStream();
		String contents = IOUtils.toString(in, morphRequest.getEncoding());
		IOUtils.closeQuietly(in);

		// May not need to morph
		if (isMorphNeeded(contents)) {
			contents = getMorphedContents(contents);
		} else {
			log.debug("Skipping morph on " + morphRequest.getName());
		}

		// Write the morphed data to the output stream
		OutputStream out = morphRequest.getOutputStream();
		IOUtils.write(contents, out, morphRequest.getEncoding());
		IOUtils.closeQuietly(out);
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
