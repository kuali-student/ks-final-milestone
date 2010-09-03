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

package org.kuali.student.lum.statement.config.context;

import java.util.Map;

import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.service.OrganizationService;
import org.kuali.student.core.statement.entity.ReqComponent;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.lum.statement.typekey.ReqComponentFieldTypes;

/**
 * This class creates the template context for an organization.
 */
public class OrganizationContextImpl extends BasicContextImpl {
 
	private OrganizationService organizationService;
	
	public final static String ORG_TOKEN = "org";

	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	private OrgInfo getOrganization(String orgId) throws OperationFailedException {
		if (orgId == null) {
			return null;
		}
		try {
			return organizationService.getOrganization(orgId);
		} catch (Exception e) {
			throw new OperationFailedException(e.getMessage(), e);
		}
	}

    /**
     * Creates the context map (template data) for the requirement component.
     * 
     * @param reqComponent Requirement component
     * @throws OperationFailedException Creating context map fails
     */
    public Map<String, Object> createContextMap(ReqComponent reqComponent) throws OperationFailedException {
        String orgId = getReqComponentFieldValue(reqComponent, ReqComponentFieldTypes.ORGANIZATION_KEY.getId());
        OrgInfo org = getOrganization(orgId);
        
        Map<String, Object> contextMap = super.createContextMap(reqComponent);
        contextMap.put(ORG_TOKEN, org);
        return contextMap;
    }
}
