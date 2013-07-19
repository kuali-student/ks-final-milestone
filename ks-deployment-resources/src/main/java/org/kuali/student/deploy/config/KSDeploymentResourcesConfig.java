package org.kuali.student.deploy.config;

import org.kuali.common.util.config.ConfigUtils;
import org.kuali.common.util.config.KualiUtilConfig;
import org.kuali.common.util.config.ProjectConfig;
import org.kuali.common.util.project.ImmutableProject;
import org.kuali.student.deploy.DeployProjectConstants;

public enum KSDeploymentResourcesConfig implements ProjectConfig {

	METAINF_SQL(KualiUtilConfig.METAINF_SQL.getContextId()), //
	SOURCE_DB_INIT("source-db:initialize"), //
	SOURCE_DB_DUMP("source-db:dump"), //
	SOURCE_DB_SCM("source-db:scm"), //
	INIT_APP_DB("init-app");

	private final ImmutableProject project = DeployProjectConstants.PROJECT;
	private final String contextId;
	private final String configId;

	private KSDeploymentResourcesConfig(String contextId) {
		this.contextId = contextId;
		this.configId = ConfigUtils.getConfigId(this);
	}

	@Override
	public String getGroupId() {
		return project.getGroupId();
	}

	@Override
	public String getArtifactId() {
		return project.getArtifactId();
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
