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
		System.out.println("1");
		List<String> configIds = new ArrayList<String>();
		System.out.println("2");
		configIds.add(KualiUtilConfig.METAINF_SQL_BUILD.getConfigId());
		System.out.println("3");
		configIds.add(KSDeploymentResourcesConfig.METAINF_SQL.getConfigId());
		System.out.println("configIds.size()=" + configIds.size());
		for (String configId : configIds) {
			System.out.println("configId=" + configId);
		}
		return Collections.unmodifiableList(configIds);
	}

}
