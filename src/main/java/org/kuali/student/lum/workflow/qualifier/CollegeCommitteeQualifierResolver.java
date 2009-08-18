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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.ResultCell;



/**
 * @author Kuali Student Team
 *
 */
public class CollegeCommitteeQualifierResolver extends CollegeQualifierResolver {
	
	@Override
	/* (non-Javadoc)
	 * @see org.kuali.rice.kew.role.XPathQualifierResolver#resolve(org.kuali.rice.kew.engine.RouteContext)
	 */
	public List<AttributeSet> resolve(RouteContext context) {
		List<AttributeSet> returnAttrSetList = new ArrayList<AttributeSet>();
		// where we'll put info about our COCs
		List<OrgInfo> cocs = new ArrayList<OrgInfo>();
		
		// find the college(s) for the orgId in context's documentContent's document
		List<MinimalOrgInfo> colleges = findOrgsOfType(context, KUALI_ORG_COLLEGE);
		
		// now find the committee(s) for those college(s)
		if (null != colleges) {
			for (MinimalOrgInfo college : colleges) {
				orgHierarchyQPV.setValue(college.getId());
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
								List<String> descendantIds = getOrganizationService().getAllDescendants(college.getId(), hierarchyId);
								if (descendantIds.size() > 0) { // if not leaf node; is it possible to have college with no descendants?
									// look for COC's; first get the OrgInfo's for these descendants
									List<OrgInfo> descendants = getOrganizationService().getOrganizationsByIdList(descendantIds);
									
									// get all COC's and Departments to screen out COC's of Departments.
									// TODO - do this in SQL; this way has got to be bloody expensive 
									Map<String, OrgInfo> depts = new HashMap<String, OrgInfo>();
									for (OrgInfo org : descendants) {
										if (isOrgType(org, KUALI_ORG_COC)) {
											cocs.add(org);
										} else if (isOrgType(org, KUALI_ORG_DEPARTMENT)) {
											depts.put(org.getId(), org);
										}
									}
									// this could be the _really_ expensive part
									// remove any COC that is descendant of a Department
									for (String deptId : depts.keySet()) {
										for (Iterator<OrgInfo> cocIter = cocs.iterator(); cocIter.hasNext(); ) {
											OrgInfo coc = cocIter.next();
											if (getOrganizationService().isDescendant(coc.getId(), deptId, hierarchyId)) {
												cocIter.remove();
											}
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
		
		// remove possible dup COC's
		// TODO - yet another inefficiency. This whole method needs rethinking,
		// refactoring, and sql-izing
		Map<String, OrgInfo> orgInfoMap = new HashMap<String, OrgInfo>();
		for (OrgInfo coc : cocs) {
			orgInfoMap.put(coc.getId(), coc);
		}
		for (OrgInfo coc : orgInfoMap.values()) {
			AttributeSet attributeSet = new AttributeSet();
			attributeSet.put(COLLEGE, coc.getShortName());
			attributeSet.put(COLLEGE_ID, coc.getId());
			returnAttrSetList.add(attributeSet);
		}
		return returnAttrSetList;
	}
}
