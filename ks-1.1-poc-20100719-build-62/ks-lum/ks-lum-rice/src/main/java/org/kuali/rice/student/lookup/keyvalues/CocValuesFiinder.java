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

import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.student.core.search.dto.SearchParam;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultCell;
import org.kuali.student.core.search.dto.SearchResultRow;

/**
 * @author lindholm
 *
 */
public abstract class CocValuesFiinder extends StudentKeyValuesBase {

	/**
	 * Find the curriculum committee Orgs of this orgType
	 *
	 * @param orgType
	 * @return
	 */
	public static List<KeyLabelPair> findCocOrgs(String orgType) {
		List<KeyLabelPair> departments = new ArrayList<KeyLabelPair>();

		List<SearchParam> queryParamValues = new ArrayList<SearchParam>(2);
		SearchParam qpOrgType = new SearchParam();
		qpOrgType.setKey("org.queryParam.relationType");
		qpOrgType.setValue("kuali.org.CurriculumChild");
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
				for (SearchResultCell resultCell : result.getCells()) {
					if ("org.resultColumn.orgId".equals(resultCell
							.getKey())) {
						orgId = resultCell.getValue();
					} else if ("org.resultColumn.orgShortName"
							.equals(resultCell.getKey())) {
						orgShortName = resultCell.getValue();
					}
				}
				departments.add(buildKeyLabelPair(orgId, orgShortName, null, null));
			}

			return departments;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
