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

package org.kuali.student.rice.config.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class NestedSimpleConfig extends NestedBaseConfig {


	private Properties baseProperties;
	private Map<String, Object> baseObjects;

	public NestedSimpleConfig() {
		super(new ArrayList<String>());
	}

	public NestedSimpleConfig(Properties properties) {
		super(new ArrayList<String>());
		this.baseProperties = properties;
	}

	public NestedSimpleConfig(List<String> fileLocs, Properties baseProperties) {
		super(fileLocs);
		this.baseProperties = baseProperties;
	}

	public NestedSimpleConfig(List<String> fileLocs) {
		super(fileLocs);
	}

	public NestedSimpleConfig(String fileLoc) {
		this(fileLoc, null);
	}

	public NestedSimpleConfig(String fileLoc, Properties baseProperties) {
		super(fileLoc);
		this.baseProperties = baseProperties;
	}

	@Override
	public Map<String, Object> getBaseObjects() {
		if (this.baseObjects == null) {
		    this.baseObjects = new HashMap<String, Object>();
		}
		return this.baseObjects;
	}

	@Override
	public Properties getBaseProperties() {
		if (this.baseProperties == null) {
			return new Properties();
		}
		return this.baseProperties;
	}


}
