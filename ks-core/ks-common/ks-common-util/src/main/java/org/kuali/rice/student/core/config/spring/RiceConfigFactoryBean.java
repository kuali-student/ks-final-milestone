/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.rice.student.core.config.spring;

/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.List;

import org.kuali.rice.core.api.config.ConfigurationException;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.impl.config.property.Config;
import org.kuali.rice.core.impl.config.property.JAXBConfigImpl;
import org.springframework.beans.factory.FactoryBean;

public class RiceConfigFactoryBean implements FactoryBean {

	private List<String> configLocations;

	public Object getObject() throws Exception {
		if (getConfigLocations() == null) {
			throw new ConfigurationException(
					"No config locations declared, at least one is required");
		}
		// we don't have to worry about a null config below because the JAXBConfigImpl class handles that scenario
		JAXBConfigImpl config = new JAXBConfigImpl(getConfigLocations(), ConfigContext.getCurrentContextConfig());
		config.setSystemOverride(true);
		config.parseConfig();
		// we always want to call init for this config as it should be overriding any existing config object and any
		// existing config object should have already been merged into our new config object already from above
		ConfigContext.init(config);
		
		return config;
	}

	public Class<?> getObjectType() {
		return Config.class;
	}

	public boolean isSingleton() {
		return true;
	}

	public List<String> getConfigLocations() {
		return this.configLocations;
	}

	public void setConfigLocations(List<String> configLocations) {
		this.configLocations = configLocations;
	}

}
