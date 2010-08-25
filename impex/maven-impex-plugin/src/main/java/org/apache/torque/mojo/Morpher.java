package org.apache.torque.mojo;

import static org.apache.commons.io.FileUtils.readFileToString;
import static org.apache.commons.io.FileUtils.writeStringToFile;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class Morpher {
	private static final Log log = LogFactory.getLog(Morpher.class);

	MorphRequest morphRequest;
	String schema;

	protected abstract boolean isMorphNeeded(String contents);

	protected abstract String getMorphedContents(String contents);

	public void executeMorph(String encoding) throws IOException {
		// Read the "old" schema XML file into a string
		String contents = readFileToString(morphRequest.getOldFile(), encoding);

		// May not need to morph
		if (isMorphNeeded(contents)) {
			contents = getMorphedContents(contents);
		} else {
			log.info("No morphing needed");
		}

		// Write the schema to the file system
		writeStringToFile(morphRequest.getNewFile(), contents, encoding);
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
