package org.apache.torque.mojo;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class Morpher {
	public Morpher() {
		this(null, null);
	}

	public Morpher(MorphRequest morphRequest, String schema) {
		super();
		this.morphRequest = morphRequest;
		this.schema = schema;
	}

	private static final Log log = LogFactory.getLog(Morpher.class);

	MorphRequest morphRequest;
	String schema;

	protected abstract boolean isMorphNeeded(String contents);

	protected abstract String getMorphedContents(String contents);

	public void executeMorph(String encoding) throws IOException {
		// Read the "old" schema XML data into a string
		String contents = IOUtils.toString(morphRequest.getOldData(), encoding);

		// May not need to morph
		if (isMorphNeeded(contents)) {
			contents = getMorphedContents(contents);
		} else {
			log.info("No morphing needed");
		}

		// Write the schema to the output stream
		IOUtils.write(contents, morphRequest.getNewData(), encoding);
	}

	public MorphRequest getMorphRequest() {
		return morphRequest;
	}

	public void setMorphRequest(MorphRequest morphRequest) {
		this.morphRequest = morphRequest;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

}
