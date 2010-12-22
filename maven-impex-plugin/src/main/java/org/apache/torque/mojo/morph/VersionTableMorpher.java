package org.apache.torque.mojo.morph;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class VersionTableMorpher extends Morpher {
	private static final Log log = LogFactory.getLog(VersionTableMorpher.class);

	String projectVersion;

	public VersionTableMorpher() {
		this(null, null);
	}

	public VersionTableMorpher(MorphRequest morphRequest, String artifactId) {
		super(morphRequest, artifactId);
	}

	protected String getMorphedContents(String contents) {
		log.debug("contents=" + contents);
		String oldVersion = StringUtils.substringBetween(contents, "VERSION=\"", "\"");
		String searchString = "VERSION=\"" + oldVersion + "\"";
		String replacement = "VERSION=\"" + getProjectVersion() + "\"";
		String s = StringUtils.replace(contents, searchString, replacement);
		return s;
	}

	/**
	 * Return true if we need to morph this file
	 */
	protected boolean isMorphNeeded(String contents) {
		return true;
	}

	public String getProjectVersion() {
		return projectVersion;
	}

	public void setProjectVersion(String projectVersion) {
		this.projectVersion = projectVersion;
	}

}
