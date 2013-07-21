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
import org.kuali.common.util.config.spring.SmartProjectPropertySourceConfig;
import org.kuali.common.util.maven.MavenConstants;
import org.kuali.common.util.spring.SpringUtils;
import org.kuali.student.deploy.config.KSDeploymentResourcesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class InitializeApplicationDbPropertySourceConfig extends SmartProjectPropertySourceConfig {

	protected static final String BASE_CONFIG_ID = KSDeploymentResourcesConfig.INIT_APP_DB.getConfigId();

	protected final static String ARTIFACT_ID_KEY = MavenConstants.ARTIFACT_ID_KEY;

	protected final static String CONFIG_ID_KEY = "impex.init.application.config";

	@Autowired
	Environment env;

	@Override
	protected List<String> getConfigIds() {
		String artifactId = null;// PropertyUtils.getProperty(getProjectProperties(), ARTIFACT_ID_KEY, null);

		String contextId = SpringUtils.getProperty(env, CONFIG_ID_KEY, artifactId);

		// in case we are running in a configuration that does not have an artifactId,
		// ensure that a context id is found
		Assert.notNull(contextId);

		String configId = Str.getId(BASE_CONFIG_ID, contextId);

		List<String> results = new ArrayList<String>(JdbcConfigConstants.DEFAULT_CONFIG_IDS);

		results.add(KualiImpexProducerConfig.MPX_SQL.getConfigId());
		results.add(KualiImpexProducerConfig.SCHEMA_SQL.getConfigId());
		results.add(configId);

		return results;
	}
}
