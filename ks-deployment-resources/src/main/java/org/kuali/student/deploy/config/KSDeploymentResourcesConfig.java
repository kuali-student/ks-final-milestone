package org.kuali.student.deploy.config;

import org.kuali.common.util.config.ConfigUtils;
import org.kuali.common.util.config.KualiUtilConfig;
import org.kuali.common.util.config.ProjectConfig;

public enum KSDeploymentResourcesConfig implements ProjectConfig {

	METAINF_SQL(KualiUtilConfig.METAINF_SQL.getContextId()), //
	SOURCE_DB_INIT("source-db:initialize"), //
	SOURCE_DB_DUMP("source-db:dump"), //
	SOURCE_DB_SCM("source-db:scm"), //
	APP_DB_INIT("app-db:init"),
    DEPLOY("DEPLOY");

	private final String contextId;
	private final String configId;

	private KSDeploymentResourcesConfig(String contextId) {
		this.contextId = contextId;
		this.configId = ConfigUtils.getConfigId(this);
	}

	@Override
	public String getGroupId() {
		return DeployProjectConstants.GROUP_ID;
	}

	@Override
	public String getArtifactId() {
		return DeployProjectConstants.ARTIFACT_ID;
	}

	@Override
	public String getContextId() {
		return contextId;
	}

	@Override
	public String getConfigId() {
		return configId;
	}

}
