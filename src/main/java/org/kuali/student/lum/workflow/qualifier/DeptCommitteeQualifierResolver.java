/* * Copyright 2009 The Kuali Foundation
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
import java.util.Set;
import java.util.TreeSet;

import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.ResultCell;



/**
 * @author Kuali Student Team
 *
 */
public class DeptCommitteeQualifierResolver extends CollegeQualifierResolver {
	
	@Override
	/* (non-Javadoc)
	 * @see org.kuali.rice.kew.role.XPathQualifierResolver#resolve(org.kuali.rice.kew.engine.RouteContext)
	 */
	public List<AttributeSet> resolve(RouteContext context) {
		List<AttributeSet> returnAttrSetList = new ArrayList<AttributeSet>();
		// where we'll put info about our COCs
		Set<OrgInfo> cocs = new TreeSet<OrgInfo>(); // use a set to avoid dup's
		
		// find the department(s) for the orgId in context's documentContent's document
		List<MinimalOrgInfo> depts = findOrgsOfType(context, KUALI_ORG_DEPARTMENT);
		
		// now find the COC(s) for those department(s)
		if (null != depts) {
			for (MinimalOrgInfo dept : depts) {
				orgHierarchyQPV.setValue(dept.getId());
				List<Result> searchResults;
				try {
					searchResults = getOrganizationService().searchForResults("org.search.hierarchiesOrgIsIn", orgHierarchyQPVs);
	
					if (null != searchResults) {
						// find descendants in each hierarchy, looking for COC's
						String hierarchyId;
						for (Result result : searchResults) {
							for (ResultCell cell : result.getResultCells()) {
								// get the ancestors of the org in this hierarchy
								hierarchyId = cell.getValue();
								List<String> descendantIds = getOrganizationService().getAllDescendants(dept.getId(), hierarchyId);
								if (descendantIds.size() > 0) { // if not leaf node; is it possible to have department with no descendants?
									// look for COC's; first get the OrgInfo's for these descendants
									List<OrgInfo> descendants = getOrganizationService().getOrganizationsByIdList(descendantIds);
									
									// get all COC's
									// TODO - do this in SQL?
									for (OrgInfo org : descendants) {
										if (isOrgType(org, KUALI_ORG_COC)) {
											cocs.add(org);
										} 
									}
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}
		
		for (OrgInfo coc : cocs) {
			AttributeSet attributeSet = new AttributeSet();
			attributeSet.put(DEPARTMENT, coc.getShortName());
			attributeSet.put(DEPARTMENT_ID, coc.getId());
			returnAttrSetList.add(attributeSet);
		}
		return returnAttrSetList;
	}
}
