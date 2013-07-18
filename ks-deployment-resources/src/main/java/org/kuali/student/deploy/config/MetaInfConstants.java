package org.kuali.student.deploy.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.kuali.common.util.config.KualiUtilConfig;

public class MetaInfConstants {

	/**
	 * The unmodifiable list of configuration ids needed by the process that generates META-INF listings of KS Oracle SQL
	 */
	public static final List<String> SQL_METAINF_CONFIG_IDS = getSqlMetaInfConfigIds();

	protected static List<String> getSqlMetaInfConfigIds() {
		List<String> configIds = new ArrayList<String>();
		configIds.add(KualiUtilConfig.METAINF_SQL_BUILD.getConfigId());
		configIds.add(KSDeploymentResourcesConfig.METAINF_SQL.getConfigId());
		return Collections.unmodifiableList(configIds);
	}

}
