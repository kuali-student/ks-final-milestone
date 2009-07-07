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

import javax.xml.namespace.QName;

import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.service.OrganizationService;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.ResultCell;


/**
 * @author Kuali Student Team
 *
 */
public class CollegeQualifierResolver extends DeptQualifierResolver {
	
	// package protected so test can set it
   	private OrganizationService orgService;
   	private QueryParamValue orgShortNameQPV = new QueryParamValue();
   	private QueryParamValue orgIdQPV = new QueryParamValue();
   	private QueryParamValue orgHierarchyQPV = new QueryParamValue();
   	private List<QueryParamValue> orgShortNameQPVs = new ArrayList<QueryParamValue>();
   	private List<QueryParamValue> orgHierarchyQPVs = new ArrayList<QueryParamValue>();
   	
	public CollegeQualifierResolver() {
	   	orgShortNameQPV.setKey("org_queryParam_orgShortName");
	   	orgIdQPV.setKey("org_queryParam_orgId");
	   	orgShortNameQPVs.add(orgShortNameQPV);
	   	orgShortNameQPVs.add(orgIdQPV);
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
		
	   	// see if we find a college
	   	// TODO - is it an error if we find college right off the bat?
		List<AttributeSet> attributeSets = super.resolve(context);
		if (attributeSets.size() != 0) {
			String college = getAttribute(attributeSets, COLLEGE);
			if (null == college) {
				String department = getAttribute(attributeSets, DEPARTMENT);
				if (null != department) {
				   	orgShortNameQPV.setValue(department);
					List<Result> searchResults = null;
					try {
						// find the org id based on short name
						searchResults = getOrganizationService().searchForResults("org.search.orgByShortName", orgShortNameQPVs);
						
						if (null == searchResults) { return returnAttrSetList; }
						
						// should be only one 
						assert(searchResults.size() == 1 && searchResults.get(0).getResultCells().size() == 2);
						ResultCell orgIdCell = searchResults.get(0).getResultCells().get(0);
						assert(orgIdCell.getKey().equals("org.resultColumn.orgId"));
						
						// get its orgId
						String orgId = orgIdCell.getValue();
						
						// get the hierarchy(s) this org is in
						orgHierarchyQPV.setValue(orgId);
						searchResults = getOrganizationService().searchForResults("org.search.hierarchiesOrgIsInByShortName", orgHierarchyQPVs);
						
						if (null == searchResults) { return returnAttrSetList; }
						// find ancestors in each hierarchy, looking for colleges
						String hierarchyId;
						for (Result result : searchResults) {
							for (ResultCell cell : result.getResultCells()) {
								// get the ancestors of the org in this hierarchy
								hierarchyId = cell.getValue();
								List<String> ancestorIds = getOrganizationService().getAncestors(orgId, hierarchyId);
								if (ancestorIds.size() > 0) { // hey, it could conceivably be the root
									List<OrgInfo> ancestors = getOrganizationService().getOrganizationsByIdList(ancestorIds);
									for (OrgInfo orgInfo : ancestors) {
										if (orgInfo.getType().equals("kuali.org.College")) {
											// if a college, stash in attributeSets
											returnSet.put(COLLEGE, orgInfo.getId());
										}
									}
								}
							}
						}
						if (returnSet.size() > 0) { // found some college ancestors
							returnAttrSetList.add(returnSet);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
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
	
	private OrganizationService getOrganizationService() {
		if (null == orgService) {
		   	orgService = (OrganizationService) GlobalResourceLoader.getService(new QName("http://org.kuali.student/core/organization","OrganizationService"));
		}
		return orgService;
	}
	
	// package-private so test code can set it
	void setOrganizationService(OrganizationService orgSvc) {
		orgService = orgSvc;
	}	
}
