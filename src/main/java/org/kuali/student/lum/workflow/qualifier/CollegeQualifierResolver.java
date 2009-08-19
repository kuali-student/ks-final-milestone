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
import java.util.Arrays;
import java.util.List;

import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.ResultCell;


/**
 * @author Kuali Student Team
 *
 */
public class CollegeQualifierResolver extends AbstractOrgQualifierResolver {
	
   	protected QueryParamValue orgHierarchyQPV = new QueryParamValue();
   	protected List<QueryParamValue> orgHierarchyQPVs = new ArrayList<QueryParamValue>();
   	
	public CollegeQualifierResolver() {
	   	orgHierarchyQPV.setKey("org_queryParam_orgId");
	   	orgHierarchyQPVs.add(orgHierarchyQPV);
	}
	
	@Override
	/* (non-Javadoc)
	 * @see org.kuali.rice.kew.role.XPathQualifierResolver#resolve(org.kuali.rice.kew.engine.RouteContext)
	 */
	public List<AttributeSet> resolve(RouteContext context) {
		List<AttributeSet> returnAttrSetList = new ArrayList<AttributeSet>();
		
		List<MinimalOrgInfo> colleges = findOrgsOfType(context, KUALI_ORG_COLLEGE);
		
		if (null != colleges) {
			for (MinimalOrgInfo college : colleges) {
				AttributeSet attributeSet = new AttributeSet();
				attributeSet.put(COLLEGE, college.getShortName());
				attributeSet.put(COLLEGE_ID, college.getId());
				returnAttrSetList.add(attributeSet);
			}
		}
		return returnAttrSetList;
	}

	/**
	 * Finds all the orgs of the specified orgType that either are or are ancestors of
	 * the org specified by the orgId contained in rContext's documentContent's document.
	 * Return their id's and shortName's contained in MinimalOrgValue's
	 *
	 * @param rContext 
	 * 
	 * @return List<MinimalOrgValue>
	 */
	protected List<MinimalOrgInfo> findOrgsOfType(RouteContext rContext, String orgType) {
		List<AttributeSet> attributeSets;
		List<MinimalOrgInfo> orgsFound = null;
		
		try {
			attributeSets = super.resolve(rContext);
			OrgInfo orgInfo = getOrgInfo(attributeSets);
			if (null != orgInfo) {
				if (isOrgType(orgInfo, orgType)) {
					// found an org right away
		 			orgsFound = Arrays.asList(new MinimalOrgInfo(orgInfo.getId(), orgInfo.getShortName()));
				}
				else {
					orgsFound = getOrgsOrgIsIn(orgInfo.getId(), orgType);
				}
			}
		} catch (Exception e) {
				e.printStackTrace();
		}
		
		return orgsFound;
	}

	/**
	 * @param id
	 * @param returnSet
	 * @return
	 * @throws PermissionDeniedException 
	 * @throws OperationFailedException 
	 * @throws MissingParameterException 
	 * @throws InvalidParameterException 
	 * @throws DoesNotExistException 
	 */
	private List<MinimalOrgInfo> getOrgsOrgIsIn(String id, String orgType) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		
		List<MinimalOrgInfo> orgs = new ArrayList<MinimalOrgInfo>();
		// get the hierarchy(s) this org is in
		orgHierarchyQPV.setValue(id);
		List<Result> searchResults = getOrganizationService().searchForResults("org.search.hierarchiesOrgIsIn", orgHierarchyQPVs);
		
		if (null != searchResults) {
			// find ancestors in each hierarchy, looking for colleges
			String hierarchyId;
			for (Result result : searchResults) {
				for (ResultCell cell : result.getResultCells()) {
					// get the ancestors of the org in this hierarchy
					hierarchyId = cell.getValue();
					List<String> ancestorIds = getOrganizationService().getAllAncestors(id, hierarchyId);
					if (ancestorIds.size() > 0) { // hey, it could conceivably be the root
						// look for colleges
						List<OrgInfo> ancestors = getOrganizationService().getOrganizationsByIdList(ancestorIds);
						for (OrgInfo org : ancestors) {
							if (isOrgType(org, orgType)) {
								// found one
								orgs.add(new MinimalOrgInfo(org.getId(), org.getShortName()));
							}
						}
					}
				}
			}
		}
		return orgs;
	}

	/**
	 * @param attributeSets
	 * @return
	 * @throws PermissionDeniedException 
	 * @throws OperationFailedException 
	 * @throws MissingParameterException 
	 * @throws InvalidParameterException 
	 * @throws DoesNotExistException 
	 */
	private OrgInfo getOrgInfo(List<AttributeSet> attrSets) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		OrgInfo returnInfo = null;
		if (attrSets.size() > 0 && attrSets.get(0).size() > 0) {
			String orgId = getAttribute(attrSets, ORG_ID);
			if (null != orgId) {
				returnInfo = getOrganizationService().getOrganization(orgId);
			}
		}
		return returnInfo;
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
