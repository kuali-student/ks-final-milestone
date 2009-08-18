/*
 * Copyright 2009 The Kuali Foundation
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

package org.kuali.student.lum.workflow.qualifier;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.student.core.organization.dto.OrgInfo;

/**
 * @author Kuali Student Team
 *
 */
public class DeptQualifierResolver extends AbstractOrgQualifierResolver {
	
	@Override
	public List<AttributeSet> resolve(RouteContext context) {
		List<AttributeSet> foundSet = super.resolve(context);
		List<AttributeSet> returnSet = new ArrayList<AttributeSet>();
		if (null != foundSet && foundSet.size() > 0) {
			String orgId = foundSet.get(0).get(ORG_ID);
			OrgInfo orgInfo = null;
			try {
				orgInfo = getOrganizationService().getOrganization(orgId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (isOrgType(orgInfo, KUALI_ORG_DEPARTMENT)) {
				AttributeSet aSet = new AttributeSet();
				aSet.put(DEPARTMENT, orgInfo.getShortName());
				aSet.put(DEPARTMENT_ID, orgInfo.getId());
				returnSet.add(aSet);
			}
		}
		return returnSet;
	}	
}
