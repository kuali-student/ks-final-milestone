package org.apache.torque.mojo.converter;

public class OracleArtifactIdConverterImpl extends ArtifactIdConverter {

	@Override
	public String getConvertedArtifactId(String artifactId) {
		String s = super.getConvertedArtifactId(artifactId);
		return s.toUpperCase();
	}
}
