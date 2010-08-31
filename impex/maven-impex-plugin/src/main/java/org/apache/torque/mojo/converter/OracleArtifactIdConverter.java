package org.apache.torque.mojo.converter;

public class OracleArtifactIdConverter extends ArtifactIdConverter {

	@Override
	public String getConvertedArtifactId(String artifactId) {
		String s = super.getConvertedArtifactId(artifactId);
		return s.toUpperCase();
	}
}
