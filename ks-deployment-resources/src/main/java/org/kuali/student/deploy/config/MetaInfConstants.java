package org.kuali.student.deploy.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.kuali.common.util.config.KualiUtilConfig;

public class MetaInfConstants {

	/**
	 * The unmodifiable list of configuration ids needed by the process that generates META-INF listings of KS Oracle SQL
	 */
	public static final List<String> METAINF_SQL_CONFIG_IDS = getMetaInfSqlConfigIds();

	protected static List<String> getMetaInfSqlConfigIds() {
		List<String> configIds = new ArrayList<String>();
		configIds.add(KualiUtilConfig.METAINF_SQL_BUILD.getConfigId());
		configIds.add(KSDeploymentResourcesConfig.METAINF_SQL.getConfigId());
		return Collections.unmodifiableList(configIds);
	}

}
