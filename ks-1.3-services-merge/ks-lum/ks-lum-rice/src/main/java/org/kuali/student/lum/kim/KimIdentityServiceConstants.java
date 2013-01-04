package org.kuali.student.lum.kim;

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

import org.kuali.rice.core.api.util.RiceConstants;
import org.kuali.rice.kim.api.identity.IdentityService;

/**
 * @author alubbers
 *
 * Created to replace the constants in the rice-2.0.0-m8 version of class org.kuali.rice.kim.util.KIMWebServiceConstants
 */
public class KimIdentityServiceConstants {

    public static final String MODULE_TARGET_NAMESPACE = RiceConstants.RICE_JAXWS_TARGET_NAMESPACE_BASE + "/kim";

    public static final String INTERFACE_CLASS = "org.kuali.rice.kim.api.identity.IdentityService";
	public static final String WEB_SERVICE_PORT = "IdentityServicePort";
}
