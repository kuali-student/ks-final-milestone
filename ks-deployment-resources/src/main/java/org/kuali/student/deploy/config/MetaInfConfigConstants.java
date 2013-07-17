package org.kuali.student.deploy.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.kuali.common.util.config.ConfigUtils;
import org.kuali.student.deploy.spring.DeployProjectConstants;

public class MetaInfConfigConstants {

	// Shorthand for GroupId + ArtifactId
	private static final String GA = DeployProjectConstants.GROUP_ID + ":" + DeployProjectConstants.ARTIFACT_ID;

	// Shorthand for stuff in the kuali-util constants area
	private static final String UTIL_SQL_CONTEXT_ID = org.kuali.common.util.metainf.MetaInfConfigConstants.SQL_CONTEXT_ID;
	private static final List<String> UTIL_SQL_BUILD_CONTEXT_IDS = org.kuali.common.util.metainf.MetaInfConfigConstants.SQL_BUILD_CONFIG_IDS;

	// Re-use the context id from kuali-util
	public static final String SQL_METAINF_CONFIG_ID = ConfigUtils.getIdString(GA, UTIL_SQL_CONTEXT_ID);

	// Produce the unmodifiable list of config ids needed to property package KS SQL
	public static final List<String> SQL_METAINF_CONFIG_IDS = getSqlMetaInfConfigIds();

	protected static List<String> getSqlMetaInfConfigIds() {
		List<String> list = new ArrayList<String>();
		list.addAll(UTIL_SQL_BUILD_CONTEXT_IDS);
		list.add(SQL_METAINF_CONFIG_ID);
		return Collections.unmodifiableList(list);
	}

}
