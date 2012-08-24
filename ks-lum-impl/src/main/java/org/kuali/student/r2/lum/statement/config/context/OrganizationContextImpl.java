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

package org.kuali.student.r2.lum.statement.config.context;

import java.util.Map;

import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r1.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r1.lum.statement.typekey.ReqComponentFieldTypes;
import org.kuali.student.r2.common.exceptions.OperationFailedException;


/**
 * This class creates the template context for an organization.
 */
public class OrganizationContextImpl extends BasicContextImpl {
 
	private OrganizationService organizationService;
	
	public final static String ORG_TOKEN = "org";

	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	private OrgInfo getOrganization(String orgId, ContextInfo context) throws OperationFailedException {
		if (orgId == null) {
			return null;
		}
		try {

			return organizationService.getOrg(orgId, context);
		} catch (Exception e) {
			throw new OperationFailedException(e.getMessage(), e);
		}
	}

    /**
     * Creates the context map (template data) for the requirement component.
     * 
     *
     * @param reqComponent Requirement component
     * @throws OperationFailedException Creating context map fails
     */
    public Map<String, Object> createContextMap(ReqComponentInfo reqComponent, ContextInfo contextInfo) throws OperationFailedException {
        String orgId = getReqComponentFieldValue(reqComponent, ReqComponentFieldTypes.ORGANIZATION_KEY.getId());
        OrgInfo org = getOrganization(orgId, contextInfo);
        
        Map<String, Object> contextMap = super.createContextMap(reqComponent, contextInfo);
        contextMap.put(ORG_TOKEN, org);
        return contextMap;
    }
}
