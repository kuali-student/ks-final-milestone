/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.lum.workflow.qualifierresolver;

import java.util.List;

import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.student.bo.KualiStudentKimAttributes;

public class DivisionCommitteeQualifierResolver extends
		AbstractCocOrgQualifierResolver {
	@Override
	public List<AttributeSet> resolve(RouteContext routeContext) {
		List<AttributeSet> attributeSets = super.resolve(routeContext);
		String orgId = null;
		if (attributeSets.size() > 0 && attributeSets.get(0).size() > 0) {
			orgId = getAttribute(attributeSets, KualiStudentKimAttributes.QUALIFICATION_ORG_ID);
		}
		return cocAttributeSetsFromAncestors(orgId,KUALI_ORG_DIVISION,KualiStudentKimAttributes.QUALIFICATION_DIVISION,KualiStudentKimAttributes.QUALIFICATION_DIVISION_ID);
	}
	
}
