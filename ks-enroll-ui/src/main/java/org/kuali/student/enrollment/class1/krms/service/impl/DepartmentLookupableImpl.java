/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 *
 * Created by Adi Rajesh on 6/7/12
 */
package org.kuali.student.enrollment.class1.krms.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class DepartmentLookupableImpl extends LookupableImpl {

    private OrganizationService organizationService;
    private ContextInfo contextInfo;

    @Override
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {

        String shortName = fieldValues.get("shortName");
        String longName = fieldValues.get("longName");

        List<OrgInfo> displays = new ArrayList<OrgInfo>();
        List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();

        if (StringUtils.isNotBlank(longName) && !longName.isEmpty()) {
            queryParamValueList.add(this.createSearchParamInfo(longName, "org.queryParam.orgOptionalLongName"));
        } else if (StringUtils.isNotBlank(shortName) && !shortName.isEmpty()) {
            queryParamValueList.add(this.createSearchParamInfo(shortName, "org.queryParam.orgOptionalShortName"));
        }
        queryParamValueList.add(this.createSearchParamInfo("kuali.org.Department", "org.queryParam.orgOptionalType"));

        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey("org.search.generic");
        searchRequest.setParams(queryParamValueList);
        SearchResultInfo orgs = null;
        try {
            orgs = getOrganizationService().search(searchRequest, getContextInfo());
            for (SearchResultRowInfo result : orgs.getRows()) {
                List<SearchResultCellInfo> cells = result.getCells();
                OrgInfo display = new OrgInfo();
                for (SearchResultCellInfo cell : cells) {
                    if ("org.resultColumn.orgId".equals(cell.getKey())) {
                        display.setId(cell.getValue());
                    } else if ("org.resultColumn.orgOptionalLongName".equals(cell.getKey())) {
                        display.setLongName(cell.getValue());
                    } else if ("org.resultColumn.orgShortName".equals(cell.getKey())) {
                        display.setShortName(cell.getValue());
                    }

                }
                displays.add(display);
            }
        } catch (Exception e) {

        }
        return displays;
    }

    private SearchParamInfo createSearchParamInfo(String value, String searchKey){
        SearchParamInfo searchParam = new SearchParamInfo();
        searchParam.setKey(searchKey);
        searchParam.getValues().add(value);
        return searchParam;
    }

    private ContextInfo getContextInfo() {
        if (null == contextInfo) {
            contextInfo = ContextUtils.createDefaultContextInfo();
        }
        return contextInfo;
    }

    private OrganizationService getOrganizationService() {
        if (organizationService == null) {
            organizationService = (OrganizationService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "organization", "OrganizationService"));

        }
        return organizationService;

    }
}
