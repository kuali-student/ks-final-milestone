package org.kuali.student.deploy.spring;

import java.util.ArrayList;
import java.util.List;

import org.kuali.common.deploy.config.KualiDeployConfig;
import org.kuali.common.util.config.KualiUtilConfig;
import org.kuali.student.deploy.config.KSDeploymentResourcesConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeployAppPropertySourceConfig extends InitAppDbPropertySourceConfig {

    protected static final String BASE_CONFIG_ID = KSDeploymentResourcesConfig.DEPLOY.getConfigId();

    @Override
    protected List<String> getConfigIds() {
        List<String> configIds = new ArrayList<String>();
        configIds.addAll(super.getBaseConfigIds());
        configIds.add(KualiUtilConfig.METAINF_MPX.getConfigId());
        configIds.add(KualiDeployConfig.DEFAULT.getConfigId());
        configIds.add(super.getAppConfigId(BASE_CONFIG_ID));
        return configIds;
    }

}
