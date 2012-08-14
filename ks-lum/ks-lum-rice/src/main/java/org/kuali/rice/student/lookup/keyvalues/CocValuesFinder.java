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
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.student.r1.common.search.dto.SearchRequest;
import org.kuali.student.r1.common.search.dto.SearchResult;
import org.kuali.student.r1.common.search.dto.SearchResultCell;
import org.kuali.student.r1.common.search.dto.SearchResultRow;

public abstract class CocValuesFinder extends StudentKeyValuesBase {

	/**
	 * Find the curriculum committee Orgs with specified orgType.
	 * Each org should have a kuali.org.CurriculumParent relationship with
	 * an org with kuali.org.COC type.
	 * 
	 * @param orgType
	 * @return
	 */
	public static List<KeyValue> findCocOrgs(String orgType) {
		List<KeyValue> orgEntities = new ArrayList<KeyValue>();

		SearchRequest searchRequest = new SearchRequest("org.search.orgQuickViewByRelationTypeOrgTypeRelatedOrgType");
		searchRequest.addParam("org.queryParam.relationType","kuali.org.CurriculumParent");
		searchRequest.addParam("org.queryParam.orgType",orgType);
		searchRequest.addParam("org.queryParam.relatedOrgType","kuali.org.COC");

		try {
			SearchResult results = null;
			results = getOrganizationService().search(searchRequest);
			for (SearchResultRow result : results.getRows()) {
				String orgId = "";
				String orgShortName = "";
				String orgLongName = "";
				for (SearchResultCell resultCell : result.getCells()) {
					if ("org.resultColumn.orgId".equals(resultCell.getKey())) {
						orgId = resultCell.getValue();
					} else if ("org.resultColumn.orgShortName".equals(resultCell.getKey())) {
						orgShortName = resultCell.getValue();
					} else if("org.resultColumn.orgLongName".equals(resultCell.getKey())){
						orgLongName = resultCell.getValue();
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
		            orgEntities.add(new ConcreteKeyValue(orgId, orgLongName));
		        }
			}

			return orgEntities;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
