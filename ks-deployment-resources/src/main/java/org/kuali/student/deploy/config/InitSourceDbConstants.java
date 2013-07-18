package org.kuali.student.deploy.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.kuali.common.jdbc.config.JdbcConfigConstants;
import org.kuali.common.util.config.KualiUtilConfig;

public class InitSourceDbConstants {

	// The complete list of configId's needed to initialize KS_SOURCE_DB
	public static final List<String> SOURCE_DB_CONFIG_IDS = getSourceDbConfigIds();

	protected static List<String> getSourceDbConfigIds() {
		List<String> ids = new ArrayList<String>();

		// We are connecting to a database so we need whatever it is JDBC needs
		ids.addAll(JdbcConfigConstants.DEFAULT_CONFIG_IDS);

		// Re-use properties from org.kuali.common:kuali-util:metainf:sql that helped create the .resources files
		ids.add(KualiUtilConfig.METAINF_SQL.getConfigId());

		// KS specific config for connecting to Amazon RDS
		ids.add(KSDeploymentResourcesConfig.SOURCE_DB_INIT.getConfigId());

		// Make sure the list is read-only
		return Collections.unmodifiableList(ids);
	}

}
