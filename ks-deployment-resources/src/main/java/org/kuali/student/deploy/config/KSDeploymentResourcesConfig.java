package org.kuali.student.deploy.config;

import org.kuali.common.util.config.ConfigUtils;
import org.kuali.common.util.config.KualiUtilConfig;
import org.kuali.common.util.config.ProjectConfig;

public enum KSDeploymentResourcesConfig implements ProjectConfig {

    METAINF_SQL(KualiUtilConfig.METAINF_SQL.getContextId()), //
    DB_SOURCE_INIT("db:source:init"), //
    DB_SOURCE_DUMP("db:source:dump"), //
    DB_SOURCE_SCM("db:source:scm"), //
    DB_APP_INIT("db:app:init"), //
    DEPLOY("deploy");

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
