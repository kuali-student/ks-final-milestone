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
package org.kuali.rice.student.lookup.keyvalues;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.util.KeyLabelPair;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;
import org.kuali.student.core.organization.service.OrganizationService;
import org.kuali.student.core.search.dto.SearchParam;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultCell;
import org.kuali.student.core.search.dto.SearchResultRow;

public class OrgCocValuesFinder extends KeyValuesBase{
    public static List<KeyLabelPair> findCocOrgs() {
        List<KeyLabelPair> departments = new ArrayList<KeyLabelPair>();

        OrganizationService orgService = (OrganizationService) GlobalResourceLoader
                .getService(new QName(
                        "http://student.kuali.org/wsdl/organization",
                        "OrganizationService"));

        List<String> types = new ArrayList<String>();
        types.add("kuali.org.College");
        types.add("kuali.org.Department");
        types.add("kuali.org.Division");
        
        List<SearchParam> queryParamValues = new ArrayList<SearchParam>(2);
        SearchParam qpOrgType = new SearchParam();
        qpOrgType.setKey("org.queryParam.relationType");
        qpOrgType.setValue("kuali.org.CurriculumChild");
        queryParamValues.add(qpOrgType);
        
        qpOrgType = new SearchParam();
        qpOrgType.setKey("org.queryParam.orgTypeList");
        qpOrgType.setValue(types);
        queryParamValues.add(qpOrgType);
        
        qpOrgType = new SearchParam();
        qpOrgType.setKey("org.queryParam.relatedOrgType");
        qpOrgType.setValue("kuali.org.COC");
        queryParamValues.add(qpOrgType);

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setSearchKey("org.search.orgQuickViewByRelationTypeOrgTypeRelatedOrgTypeAltr");
        searchRequest.setParams(queryParamValues);
        
        try {
            SearchResult results = orgService.search(searchRequest);

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
                // departments.add(new KeyLabelPair(orgId, orgShortName));
                departments.add(new KeyLabelPair(orgShortName, orgShortName));
            }

            return departments;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List getKeyValues() {

        return findCocOrgs();
    }

}
