package org.apache.torque.mojo;

import org.codehaus.plexus.util.StringUtils;

public class DataMorpher extends Morpher {
	// Ant prologue
	String antPrologue = "<?xml version=\"1.0\"?>";
	// New prologue
	String newPrologue = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

	// The hard coded Ant impex document type
	String antDocType = "<!DOCTYPE dataset SYSTEM \"data.dtd\">";

	public DataMorpher() {
		this(null, null);
	}

	public DataMorpher(MorphRequest morphRequest, String schema) {
		super(morphRequest, schema);
	}

	protected String getNewDocType() {
		return "<!DOCTYPE dataset SYSTEM \"" + getSchema() + ".dtd\">";
	}

	protected String getMorphedContents(String contents) {
		contents = StringUtils.replace(contents, antPrologue, newPrologue);
		return StringUtils.replace(contents, antDocType, getNewDocType());
	}

	/**
	 * Return true if we need to morph this file
	 */
	protected boolean isMorphNeeded(String contents) {
		// Look for the DTD the Maven Impex Plugin uses
		int pos = contents.indexOf(antDocType);

		if (pos == -1) {
			// It isn't there so we should morph
			return true;
		} else {
			// It is already there, we are good to go
			return false;
		}
	}

	public String getAntPrologue() {
		return antPrologue;
	}

	public void setAntPrologue(String antPrologue) {
		this.antPrologue = antPrologue;
	}

	public String getNewPrologue() {
		return newPrologue;
	}

	public void setNewPrologue(String newPrologue) {
		this.newPrologue = newPrologue;
	}

}
