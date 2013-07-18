package org.kuali.student.deploy.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.kuali.common.impex.config.KualiImpexExportConfig;
import org.kuali.common.jdbc.config.KualiJdbcConfig;
import org.kuali.common.jdbc.config.KualiSqlConfig;

public class SourceDbConstants {

	// The complete list of configId's needed to dump KS_SOURCE_DB
	public static final List<String> DUMP_SOURCE_DB_CONFIG_IDS = getSourceDbConfigIds();

	protected static List<String> getSourceDbConfigIds() {
		List<String> ids = new ArrayList<String>();
		ids.add(KualiSqlConfig.DEFAULT.getConfigId());
		ids.add(KualiJdbcConfig.DEFAULT.getConfigId());
		ids.add(KualiImpexExportConfig.DUMP_BUILD.getConfigId());
		ids.add(KSDeploymentResourcesConfig.SOURCE_DB_DUMP.getConfigId());
		return Collections.unmodifiableList(ids);
	}

}
