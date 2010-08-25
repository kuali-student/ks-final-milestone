package org.apache.torque.mojo;

import org.codehaus.plexus.util.StringUtils;

public class DataMorpher extends Morpher {

	protected String getMorphedContents(String contents) {
		return StringUtils.replace(contents, "\"data.dtd\"", '"' + schema + ".dtd\"");
	}

	/**
	 * Return true if we need to morph this file
	 */
	protected boolean isMorphNeeded(String contents) {
		// Look for the DTD the Maven Impex Plugin uses
		int pos = contents.indexOf(schema + ".dtd");

		if (pos == -1) {
			// It isn't there so we should morph
			return true;
		} else {
			// It is already there, we are good to go
			return false;
		}
	}

}
