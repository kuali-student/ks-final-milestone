package org.apache.torque.mojo.converter;

import org.apache.commons.lang.StringUtils;

public class ArtifactIdConverter {
	public String getConvertedArtifactId(String artifactId) {
		String s = StringUtils.remove(artifactId, "-db");
		return StringUtils.remove(s, "-");
	}

}