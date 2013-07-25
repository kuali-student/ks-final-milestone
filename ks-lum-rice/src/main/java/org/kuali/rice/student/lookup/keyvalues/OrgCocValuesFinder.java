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

import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.student.r2.core.search.dto.*;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.common.util.ContextUtils;

public class OrgCocValuesFinder extends StudentKeyValuesBase {
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(AllOrgsValuesFinder.class);

	public static List<KeyValue> findCocOrgs() {
        List<KeyValue> departments = new ArrayList<KeyValue>();

        List<String> types = new ArrayList<String>();
        types.add("kuali.org.College");
        types.add("kuali.org.Department");
        types.add("kuali.org.Division");
        
        List<SearchParamInfo> queryParamValues = new ArrayList<SearchParamInfo>(2);
        SearchParamInfo qpOrgType = new SearchParamInfo();
        qpOrgType.setKey("org.queryParam.relationType");
        qpOrgType.getValues().add("kuali.org.CurriculumChild");
        queryParamValues.add(qpOrgType);
        
        qpOrgType = new SearchParamInfo();
        qpOrgType.setKey("org.queryParam.orgTypeList");
        qpOrgType.setValues(types);
        queryParamValues.add(qpOrgType);
        
        qpOrgType = new SearchParamInfo();
        qpOrgType.setKey("org.queryParam.relatedOrgType");
        qpOrgType.getValues().add("kuali.org.COC");
        queryParamValues.add(qpOrgType);

        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey("org.search.orgQuickViewByRelationTypeOrgTypeRelatedOrgTypeAltr");
        searchRequest.setParams(queryParamValues);
        
        try {
            SearchResultInfo results = getOrganizationService().search(searchRequest, ContextUtils.getContextInfo());
            for (SearchResultRowInfo result : results.getRows()) {
                String orgId = "";
                String orgShortName = "";
                for (SearchResultCellInfo resultCell : result.getCells()) {
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
        	LOG.error("Error building KeyValues List", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<KeyValue> getKeyValues() {
        return findCocOrgs();
    }

}
