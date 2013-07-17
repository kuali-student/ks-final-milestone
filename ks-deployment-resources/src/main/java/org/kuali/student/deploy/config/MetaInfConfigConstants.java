package org.kuali.student.deploy.config;

import java.util.Arrays;
import java.util.List;

import org.kuali.common.util.CollectionUtils;
import org.kuali.common.util.config.ConfigUtils;
import org.kuali.student.deploy.spring.DeployProjectConstants;

public class MetaInfConfigConstants {

	// Shorthand for GroupId + ArtifactId
	private static final String GA = DeployProjectConstants.GROUP_ID + ":" + DeployProjectConstants.ARTIFACT_ID;

	public static final String SQL_METAINF_CONFIG_ID = ConfigUtils.getIdString(GA, org.kuali.common.util.metainf.MetaInfConfigConstants.SQL_CONTEXT_ID);

	public static final List<String> SQL_METAINF_CONFIG_IDS = CollectionUtils.combine(org.kuali.common.util.metainf.MetaInfConfigConstants.SQL_BUILD_CONFIG_IDS,
			Arrays.asList(SQL_METAINF_CONFIG_ID));

}
