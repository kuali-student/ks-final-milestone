package org.kuali.student.deploy.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.kuali.common.impex.config.KualiImpexExportConfig;
import org.kuali.common.jdbc.config.JdbcConfigConstants;
import org.kuali.common.util.config.KualiUtilConfig;

/**
 * @deprecated
 */
@Deprecated
public class SourceDbConstants {

	// The complete list of configId's needed to dump KS_SOURCE_DB
	public static final List<String> DUMP_SOURCE_DB_CONFIG_IDS = getDumpSourceDbConfigIds();

	// The complete list of configId's needed to initialize KS_SOURCE_DB
	public static final List<String> INIT_SOURCE_DB_CONFIG_IDS = getInitSourceDbConfigIds();

	// The complete list of configId's needed to update SCM
	public static final List<String> SCM_SOURCE_DB_CONFIG_IDS = getScmSourceDbConfigIds();

	protected static List<String> getScmSourceDbConfigIds() {
		List<String> list = new ArrayList<String>();
		list.add(KualiUtilConfig.SCM.getConfigId());
		list.add(KualiImpexExportConfig.STAGING_BUILD.getConfigId());
		list.add(KSDeploymentResourcesConfig.DB_SOURCE_SCM.getConfigId());
		return Collections.unmodifiableList(list);
	}

	protected static List<String> getInitSourceDbConfigIds() {
		List<String> ids = new ArrayList<String>();
		ids.addAll(JdbcConfigConstants.DEFAULT_CONFIG_IDS);
		ids.add(KualiUtilConfig.METAINF_SQL.getConfigId());
		ids.add(KSDeploymentResourcesConfig.DB_SOURCE_INIT.getConfigId());
		return Collections.unmodifiableList(ids);
	}

	protected static List<String> getDumpSourceDbConfigIds() {
		List<String> ids = new ArrayList<String>();
		ids.addAll(JdbcConfigConstants.DEFAULT_CONFIG_IDS);
		ids.add(KualiImpexExportConfig.DUMP_BUILD.getConfigId());
		ids.add(KSDeploymentResourcesConfig.DB_SOURCE_DUMP.getConfigId());
		return Collections.unmodifiableList(ids);
	}

}
