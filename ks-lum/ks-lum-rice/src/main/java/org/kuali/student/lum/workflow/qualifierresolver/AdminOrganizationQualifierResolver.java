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

/**
 * 
 */
package org.kuali.student.lum.workflow.qualifierresolver;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.student.bo.KualiStudentKimAttributes;
import org.kuali.student.core.organization.dto.OrgInfo;

/**
 * This class finds the admin organization on the KS document and returns the id
 * and short name of that organization for routing directly to the org on the
 * document
 * 
 */
public class AdminOrganizationQualifierResolver extends AbstractCocOrgQualifierResolver {
	protected static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(AdminOrganizationQualifierResolver.class);

	@Override
	public List<AttributeSet> resolve(RouteContext routeContext) {
		List<AttributeSet> attributeSets = super.resolve(routeContext);
		String orgId = null;
		if (attributeSets.size() > 0 && attributeSets.get(0).size() > 0) {
			orgId = getAttribute(attributeSets, KualiStudentKimAttributes.QUALIFICATION_ORG_ID);
		}
		List<AttributeSet> returnList = new ArrayList<AttributeSet>();
		if ( (orgId != null) && (!"".equals(orgId.trim())) ) {
			try {
				OrgInfo orgInfo = getOrganizationService().getOrganization(orgId);
				if (orgInfo == null) {
					LOG.error("Cannot find valid Org with id: " + orgId);
				}
				else {
					AttributeSet attributeSet = new AttributeSet();
					attributeSet.put(KualiStudentKimAttributes.QUALIFICATION_ORG, orgInfo.getShortName());
					attributeSet.put(KualiStudentKimAttributes.QUALIFICATION_ORG_ID, orgInfo.getId());
					returnList.add(attributeSet);
				}
			} catch (Exception e) {
				LOG.error("Error calling org service to retrieve org id: " + orgId);
				throw new RuntimeException(e);
			}
		}
		return returnList;
	}

}
