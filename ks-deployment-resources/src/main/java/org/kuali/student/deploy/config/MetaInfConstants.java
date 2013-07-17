package org.kuali.student.deploy.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.kuali.common.util.Str;
import org.kuali.common.util.metainf.MetaInfConfigConstants;
import org.kuali.student.deploy.spring.DeployProjectConstants;

public class MetaInfConstants {

	// Shorthand for GroupId + ArtifactId
	private static final String GA = Str.getId(DeployProjectConstants.GROUP_ID, DeployProjectConstants.ARTIFACT_ID);

	// Re-use the context id from kuali-util
	public static final String SQL_METAINF_CONFIG_ID = Str.getId(GA, MetaInfConfigConstants.SQL_CONTEXT_ID);

	// The unmodifiable list of config ids needed to properly package KS SQL
	public static final List<String> SQL_METAINF_CONFIG_IDS = getSqlMetaInfConfigIds();

	protected static List<String> getSqlMetaInfConfigIds() {
		List<String> list = new ArrayList<String>();
		list.addAll(MetaInfConfigConstants.SQL_BUILD_CONFIG_IDS);
		list.add(SQL_METAINF_CONFIG_ID);
		return Collections.unmodifiableList(list);
	}

}
