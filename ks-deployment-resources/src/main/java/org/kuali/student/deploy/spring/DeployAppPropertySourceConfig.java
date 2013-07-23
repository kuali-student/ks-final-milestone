package org.kuali.student.deploy.spring;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.deploy.config.DeployConstants;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeployAppPropertySourceConfig extends InitAppDbPropertySourceConfig {

    @Override
    protected List<String> getConfigIds() {
        List<String> configIds = new ArrayList<String>();
        configIds.addAll(super.getBaseConfigIds());
        configIds.addAll(DeployConstants.DEPLOY_CONFIG_IDS);
        configIds.add(super.getAppConfigId());
        return configIds;
    }

}
