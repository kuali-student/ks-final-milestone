package org.kuali.student.deploy.config;

import org.kuali.common.util.metainf.SqlMetaInfConfig;

public class StudentSqlMetaInfConfigRequest extends DeploymentConfigRequest {

	public StudentSqlMetaInfConfigRequest() {
		super(SqlMetaInfConfig.CONTEXT_ID);
	}

}
