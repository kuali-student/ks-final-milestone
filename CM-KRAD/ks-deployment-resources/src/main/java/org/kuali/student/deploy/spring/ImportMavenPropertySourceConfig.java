/**
 * Copyright 2010-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.deploy.spring;

import java.util.Arrays;
import java.util.List;

import org.kuali.common.impex.spring.GeneratorPropertiesConfig;
import org.kuali.common.jdbc.spring.JdbcPropertiesConfig;
import org.kuali.common.util.property.ProjectProperties;
import org.kuali.common.util.spring.MavenPropertySourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ JdbcPropertiesConfig.class, GeneratorPropertiesConfig.class })
public class ImportMavenPropertySourceConfig extends MavenPropertySourceConfig {

	@Autowired
	JdbcPropertiesConfig jdbcProperties;

	@Autowired
	GeneratorPropertiesConfig generatorProperties;

	@Override
	protected List<ProjectProperties> getProjectPropertiesList() {
		return Arrays.asList(jdbcProperties.jdbcProjectProperties(), generatorProperties.generatorProjectProperties());
	}

}
