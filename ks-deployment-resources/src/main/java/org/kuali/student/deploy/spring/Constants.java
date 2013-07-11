package org.kuali.student.deploy.spring;

import org.kuali.common.util.ProjectUtils;

public abstract class Constants {

	public static final String GROUP_ID = "org.kuali.student.deployments";
	public static final String GROUP_ID_BASE = "org.kuali.student";
	public static final String ARTIFACT_ID = "ks-deployment-resources";
	public static final String KS_DB_GROUP_ID = "org.kuali.student.db";
	public static final String KS_SOURCE_DB = "ks-source-db";
	public static final String KS_SOURCE_DB_RESOURCE_PREFIX = ProjectUtils.getClassPathPrefix(Constants.GROUP_ID_BASE, Constants.KS_SOURCE_DB);

}
