/**
 * Copyright 2005-2012 The Kuali Foundation
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
package org.kuali.student.ap.framework.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.kuali.rice.core.api.config.module.RunMode;
import org.kuali.rice.core.framework.config.module.ModuleConfigurer;

/**
 * Configures KSAP as a Kuali Rice Module.
 * 
 * @author Mark Fyffe <mwfyffe@indiana.edu>
 * @version ksap-0.1.1
 */
public class KSAPConfigurer extends ModuleConfigurer {

	public KSAPConfigurer() {
		super(KSAPConstants.KSAP_MODULE_NAME);
		setValidRunModes(Arrays.asList(RunMode.REMOTE, RunMode.LOCAL));
	}

	@Override
	protected String getDefaultConfigPackagePath() {
		return KSAPConstants.KSAP_PACKAGE_CONFIG_PATH;
	}

	@Override
	public List<String> getPrimarySpringFiles() {
		List<String> springFileLocations = new ArrayList<String>();
		springFileLocations.add(getDefaultConfigPackagePath() + "ks-ap-"
				+ getRunMode().name().toLowerCase() + ".xml");
		return springFileLocations;
	}

}
