package org.kuali.student.deploy.spring;

import org.kuali.common.util.ProjectConstants;
import org.kuali.common.util.ProjectUtils;

public abstract class DeployProjectConstants {

	public static final String GROUP_ID = ProjectConstants.STUDENT_GROUP_ID;
	public static final String ARTIFACT_ID = "ks-deployment-resources";
	public static final String KS_SOURCE_DB_ARTIFACT_ID = "ks-source-db";

	@Deprecated
	public static final String GROUP_ID_BASE = GROUP_ID;

	@Deprecated
	public static final String KS_SOURCE_DB_RESOURCE_PREFIX = ProjectUtils.getClassPathPrefix(GROUP_ID, KS_SOURCE_DB_ARTIFACT_ID);

	@Deprecated
	public static final String KS_SOURCE_DB_COMMON = KS_SOURCE_DB_RESOURCE_PREFIX + "/common.properties";

}
