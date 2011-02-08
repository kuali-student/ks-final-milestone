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

package org.kuali.rice.student.lookup.keyvalues;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.student.core.search.dto.SearchParam;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultCell;
import org.kuali.student.core.search.dto.SearchResultRow;

public abstract class CocValuesFinder extends StudentKeyValuesBase {

	/**
	 * Find the curriculum committee Orgs with specified orgType.
	 * Each org should have a kuali.org.CurriculumParent relationship with
	 * an org with kuali.org.COC type.
	 * 
	 * @param orgType
	 * @return
	 */
	public static List<KeyLabelPair> findCocOrgs(String orgType) {
		List<KeyLabelPair> orgEntities = new ArrayList<KeyLabelPair>();

		List<SearchParam> queryParamValues = new ArrayList<SearchParam>(2);
		SearchParam qpOrgType = new SearchParam();
		qpOrgType.setKey("org.queryParam.relationType");
		qpOrgType.setValue("kuali.org.CurriculumParent");
		queryParamValues.add(qpOrgType);
		
		qpOrgType = new SearchParam();
        qpOrgType.setKey("org.queryParam.orgType");
        qpOrgType.setValue(orgType);
		queryParamValues.add(qpOrgType);
		
		qpOrgType = new SearchParam();
		qpOrgType.setKey("org.queryParam.relatedOrgType");
		qpOrgType.setValue("kuali.org.COC");
		queryParamValues.add(qpOrgType);
		
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.setParams(queryParamValues);
        searchRequest.setSearchKey("org.search.orgQuickViewByRelationTypeOrgTypeRelatedOrgType");

		try {
			SearchResult results = getOrganizationService().search(searchRequest);

			for (SearchResultRow result : results.getRows()) {
				String orgId = "";
				String orgShortName = "";
				String orgLongName = "";
				for (SearchResultCell resultCell : result.getCells()) {
					if ("org.resultColumn.orgId".equals(resultCell
							.getKey())) {
						orgId = resultCell.getValue();
						orgLongName = getOrganizationService().getOrganization(orgId).getLongName();
					} else if ("org.resultColumn.orgShortName"
							.equals(resultCell.getKey())) {
						orgShortName = resultCell.getValue();
					}					
				}
		        if (StringUtils.isBlank(orgLongName)) {
		           //use shortName when longName is blank
		            orgEntities.add(buildKeyLabelPair(orgId, orgShortName, null, null));
		        }
		        else {
		            /*
		             * The requirement is that in the RICE portal, when we add a principal to a role
		             * the drop-down list for DepartmentCoC or DivisionCoC should display the full/long 
		             * names instead of short names.
		             */
		            orgEntities.add(new KeyLabelPair(orgId, orgLongName));
		        }
//		        orgEntities.add(buildKeyLabelPair(orgId, null, orgLongName, null));
			}

			return orgEntities;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
