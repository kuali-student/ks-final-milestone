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

package org.kuali.student.krms.naturallanguage.config.context;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.core.krms.naturallanguage.TermParameterTypes;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;

import javax.xml.namespace.QName;
import java.util.Map;
import org.kuali.rice.core.api.exception.RiceIllegalStateException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.ContextUtils;


/**
 * This class creates the template context for an organization.
 */
public class OrganizationContextImpl extends BasicContextImpl {

	private OrganizationService organizationService;

	public final static String ORG_TOKEN = "org";

	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}
    private OrganizationService getOrganizationService() {
        if (organizationService == null) {
            organizationService = (OrganizationService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "organization", "OrganizationService"));

        }
        return organizationService;

    }


	private OrgInfo getOrganization(String orgId, ContextInfo context)  {
		if (orgId == null) {
			return null;
		}
		try {

			return this.getOrganizationService().getOrg(orgId, context);
		} catch (Exception e) {
                    throw new RiceIllegalStateException (e);
		}
	}

    /**
     * Creates the context map (template data) for the requirement component.
     *
     * @param parameters
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException Creating context map fails
     */
    @Override
    public Map<String, Object> createContextMap(Map<String, Object> parameters) {
        ContextInfo contextInfo = ContextUtils.getContextInfo();
        Map<String, Object> contextMap = super.createContextMap(parameters);

        String orgId = (String) parameters.get(TermParameterTypes.ORGANIZATION_KEY.getId());
        OrgInfo org = getOrganization(orgId, contextInfo);
        if( org != null){
            contextMap.put(ORG_TOKEN, org);
        }

        return contextMap;
    }
}
