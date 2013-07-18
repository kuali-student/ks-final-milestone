package org.kuali.student.deploy.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.kuali.common.jdbc.config.KualiJdbcConfig;
import org.kuali.common.jdbc.config.KualiSqlConfig;
import org.kuali.common.util.config.KualiUtilConfig;

public class InitSourceDbConstants {

	// The complete list of configId's needed to initialize KS_SOURCE_DB
	public static final List<String> SOURCE_DB_CONFIG_IDS = getSourceDbConfigIds();

	protected static List<String> getSourceDbConfigIds() {
		List<String> ids = new ArrayList<String>();
		ids.add(KualiSqlConfig.DEFAULT.getConfigId());
		ids.add(KualiJdbcConfig.DEFAULT.getConfigId());
		ids.add(KualiUtilConfig.METAINF_SQL.getConfigId());
		ids.add(KSDeploymentResourcesConfig.SOURCE_DB_INIT.getConfigId());
		return Collections.unmodifiableList(ids);
	}

}
