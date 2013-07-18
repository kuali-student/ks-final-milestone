package org.kuali.student.deploy.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.kuali.common.util.config.KualiUtilConfig;

public class MetaInfConstants {

	// The unmodifiable list of config ids needed to properly package KS SQL
	public static final List<String> SQL_METAINF_CONFIG_IDS = getSqlMetaInfConfigIds();

	protected static List<String> getSqlMetaInfConfigIds() {
		List<String> list = new ArrayList<String>();
		list.add(KualiUtilConfig.METAINF_SQL_BUILD.getConfigId());
		list.add(KSDeploymentResourcesConfig.METAINF_SQL.getConfigId());
		return Collections.unmodifiableList(list);
	}

}
