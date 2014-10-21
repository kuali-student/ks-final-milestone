package org.kuali.student.deploy.config;

import java.util.Collections;
import java.util.List;

public class DeployConstants {

    /**
     * The unmodifiable list of configuration ids needed by the process that deploys KS applications
     */
    public static final List<String> DEPLOY_CONFIG_IDS = getDeployConfigIds();

    protected static List<String> getDeployConfigIds() {
        String configId = KSDeploymentResourcesConfig.DEPLOY.getConfigId();
        return Collections.singletonList(configId);
    }

}
