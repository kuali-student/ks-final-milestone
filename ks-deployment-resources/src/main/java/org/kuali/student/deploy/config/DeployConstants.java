package org.kuali.student.deploy.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.kuali.common.jdbc.config.JdbcConfigConstants;

public class DeployConstants {

    /**
     * The unmodifiable list of configuration ids needed by the process that deploys KS applications
     */
    public static final List<String> DEPLOY_CONFIG_IDS = getDeployConfigIds();

    protected static List<String> getDeployConfigIds() {
        List<String> configIds = new ArrayList<String>();
        configIds.addAll(JdbcConfigConstants.DEFAULT_CONFIG_IDS);
        configIds.add(KSDeploymentResourcesConfig.METAINF_SQL.getConfigId());
        return Collections.unmodifiableList(configIds);
    }

}
