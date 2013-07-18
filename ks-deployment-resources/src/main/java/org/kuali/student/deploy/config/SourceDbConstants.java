package org.kuali.student.deploy.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.kuali.common.impex.config.KualiImpexExportConfig;
import org.kuali.common.jdbc.config.KualiJdbcConfig;
import org.kuali.common.jdbc.config.KualiSqlConfig;
import org.kuali.common.util.config.KualiUtilConfig;

public class SourceDbConstants {

	// The complete list of configId's needed to dump KS_SOURCE_DB
	public static final List<String> DUMP_SOURCE_DB_CONFIG_IDS = getDumpSourceDbConfigIds();

	// The complete list of configId's needed to initialize KS_SOURCE_DB
	public static final List<String> INIT_SOURCE_DB_CONFIG_IDS = getInitSourceDbConfigIds();

	protected static List<String> getInitSourceDbConfigIds() {
		List<String> ids = new ArrayList<String>();
		ids.add(KualiSqlConfig.DEFAULT.getConfigId());
		ids.add(KualiJdbcConfig.DEFAULT.getConfigId());
		ids.add(KualiUtilConfig.METAINF_SQL.getConfigId());
		ids.add(KSDeploymentResourcesConfig.SOURCE_DB_INIT.getConfigId());
		return Collections.unmodifiableList(ids);
	}

	protected static List<String> getDumpSourceDbConfigIds() {
		List<String> ids = new ArrayList<String>();
		ids.add(KualiSqlConfig.DEFAULT.getConfigId());
		ids.add(KualiJdbcConfig.DEFAULT.getConfigId());
		ids.add(KualiImpexExportConfig.DUMP_BUILD.getConfigId());
		ids.add(KSDeploymentResourcesConfig.SOURCE_DB_DUMP.getConfigId());
		return Collections.unmodifiableList(ids);
	}

}
