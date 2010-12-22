package org.apache.torque.mojo.morph;

import org.apache.commons.lang.StringUtils;

public class VersionTableMorpher extends Morpher {

	public VersionTableMorpher() {
		this(null, null);
	}

	public VersionTableMorpher(MorphRequest morphRequest, String artifactId) {
		super(morphRequest, artifactId);
	}

	protected String getMorphedContents(String contents) {
		String version = StringUtils.substringBetween(contents, "VERSION=\"", "\"");
		String searchString = "VERSION=\"" + version + "\"";
		String replacement = "VERSION=\"${project.version}\"";
		String s = StringUtils.replace(contents, searchString, replacement);
		return s;
	}

	/**
	 * Return true if we need to morph this file
	 */
	protected boolean isMorphNeeded(String contents) {
		return true;
	}

}
