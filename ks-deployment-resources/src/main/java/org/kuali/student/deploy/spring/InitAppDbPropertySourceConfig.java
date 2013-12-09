/**
 * Copyright 2011 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.deploy.spring;

import java.util.ArrayList;
import java.util.List;

import org.kuali.common.impex.KualiImpexProducerConfig;
import org.kuali.common.jdbc.config.JdbcConfigConstants;
import org.kuali.common.util.Assert;
import org.kuali.common.util.Str;
import org.kuali.common.util.config.KualiUtilConfig;
import org.kuali.common.util.config.spring.ProjectPropertySourceConfig;
import org.kuali.common.util.maven.MavenConstants;
import org.kuali.common.util.project.model.Project;
import org.kuali.common.util.spring.SpringUtils;
import org.kuali.student.deploy.config.KSDeploymentResourcesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class InitAppDbPropertySourceConfig extends ProjectPropertySourceConfig {

	protected static final String BASE_CONFIG_ID = KSDeploymentResourcesConfig.DB_APP_INIT.getConfigId();
	protected final static String ARTIFACT_ID_KEY = MavenConstants.ARTIFACT_ID_KEY;
	protected final static String CONFIG_ID_KEY = "impex.init.application.config";

	@Autowired
	Environment env;

	@Autowired
	Project project;

	@Override
	protected List<String> getConfigIds() {
		List<String> baseConfigIds = getBaseConfigIds();
		String appConfigId = getAppConfigId(BASE_CONFIG_ID);
		List<String> configIds = new ArrayList<String>();
		configIds.add(KualiUtilConfig.METAINF_MPX.getConfigId());
		configIds.addAll(baseConfigIds);
		configIds.add(appConfigId);
		return configIds;
	}

	protected List<String> getBaseConfigIds() {
		List<String> results = new ArrayList<String>(JdbcConfigConstants.DEFAULT_CONFIG_IDS);
		results.add(KualiImpexProducerConfig.MPX_SQL.getConfigId());
		results.add(KualiImpexProducerConfig.SCHEMA_SQL.getConfigId());
		return results;
	}

	protected String getAppConfigId(String baseConfigId) {
		String artifactId = project.getArtifactId();
		String contextId = SpringUtils.getProperty(env, CONFIG_ID_KEY, artifactId);
		// in case we are running in a configuration that does not have an artifactId,
		// ensure that a context id is found
		Assert.notNull(contextId);
		return Str.getId(baseConfigId, contextId);
	}
}
