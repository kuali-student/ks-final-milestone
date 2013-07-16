package org.kuali.student.deploy.spring.config;

import org.kuali.common.util.metainf.SqlMetaInfConfig;

public class StudentSqlMetaInfConfig extends DeploymentConfigRequest {

	public StudentSqlMetaInfConfig() {
		super(SqlMetaInfConfig.CONTEXT_ID);
	}

}
