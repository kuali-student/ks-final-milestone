package org.apache.torque.mojo.converter;

public class MysqlArtifactIdConverterImpl extends ArtifactIdConverter {

	@Override
	public String getConvertedArtifactId(String artifactId) {
		String s = super.getConvertedArtifactId(artifactId);
		return s.toLowerCase();
	}
}
