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
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.ResultCell;


/**
 * @author Kuali Student Team
 *
 */
public class CollegeCommitteeQualifierResolver extends AbstractOrgQualifierResolver {
	
   	private QueryParamValue orgHierarchyQPV = new QueryParamValue();
   	private List<QueryParamValue> orgHierarchyQPVs = new ArrayList<QueryParamValue>();
   	
	public CollegeCommitteeQualifierResolver() {
	   	orgHierarchyQPV.setKey("org_queryParam_orgId");
	   	orgHierarchyQPVs.add(orgHierarchyQPV);
	}
	
	@Override
	/* (non-Javadoc)
	 * @see org.kuali.rice.kew.role.XPathQualifierResolver#resolve(org.kuali.rice.kew.engine.RouteContext)
	 */
	public List<AttributeSet> resolve(RouteContext context) {
		List<AttributeSet> returnAttrSetList = new ArrayList<AttributeSet>();
		AttributeSet returnSet = new AttributeSet();
		List<Result> searchResults = null;
		
		List<AttributeSet> attributeSets;
		try {
			attributeSets = super.resolve(context);
			if (attributeSets.size() > 0 && attributeSets.get(0).size() > 0) {
				String orgId = getAttribute(attributeSets, ORG_ID);
				if (null != orgId) {
					OrgInfo orgInfo;
					orgInfo = getOrganizationService().getOrganization(orgId);
					if (null != orgInfo) {
						// found a college right away
						if (isCollege(orgInfo)) {
							returnSet.put(COLLEGE, orgInfo.getShortName());
						}
						else {
							// get the hierarchy(s) this org is in
							orgHierarchyQPV.setValue(orgId);
							searchResults = getOrganizationService().searchForResults("org.search.hierarchiesOrgIsIn", orgHierarchyQPVs);
							
							if (null != searchResults) {
								// find ancestors in each hierarchy, looking for colleges
								String hierarchyId;
								for (Result result : searchResults) {
									for (ResultCell cell : result.getResultCells()) {
										// get the ancestors of the org in this hierarchy
										hierarchyId = cell.getValue();
										List<String> ancestorIds = getOrganizationService().getAllAncestors(orgId, hierarchyId);
										if (ancestorIds.size() > 0) { // hey, it could conceivably be the root
											// look for colleges
											List<OrgInfo> ancestors = getOrganizationService().getOrganizationsByIdList(ancestorIds);
											for (OrgInfo org : ancestors) {
												if (isCollege(org)) {
													// found one
													returnSet.put(COLLEGE, org.getShortName());
												}
											}
										}
									}
								}
							}
						}
					}
				}
				if (returnSet.size() > 0) { // found at least one college
					returnAttrSetList.add(returnSet);
				}
			}
		} catch (Exception e) {
				e.printStackTrace();
		}
		return returnAttrSetList;
	}

	/**
	 * @param attributeSets 
	 * @param string
	 * @return
	 */
	private String getAttribute(List<AttributeSet> attributeSets, String searchStr) {
		String attrStr;
		
		for (AttributeSet set : attributeSets) {
			attrStr = set.get(searchStr);
			if (null != attrStr) {
				return attrStr;
			}
		}
		return null;
	}
}
