/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.lu.ui.krms.service.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.common.uif.service.impl.KSLookupableImpl;
import org.kuali.student.lum.lu.ui.krms.util.CluSearchUtil;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang.StringUtils.isEmpty;

/**
 * Lookupable Implementation for Courses
 *
 * @author Kuali Student Team
 */
public class CourseInfoLookupableImpl extends KSLookupableImpl {
	private static final long serialVersionUID = 1L;	
	
    private transient CluService cluService;

    public enum QueryParamEnum {
        ID("lu.queryParam.luOptionalId","id"),
        TITLE("lu.queryParam.luOptionalLongName", "title"),
        CODE("lu.queryParam.luOptionalCode", "code"),
        DESCRIPTION("lu.queryParam.luOptionalDescr", "description");

        private final String fieldValue;
        private final String queryKey;

        QueryParamEnum(String qKey, String fValue) {
            this.queryKey = qKey;
            this.fieldValue = fValue;
        }

        public String getFieldValue() {
            return fieldValue;
        }

        public String getQueryKey() {
            return queryKey;
        }
    }

    @Override
    protected List<?> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues, boolean unbounded) {

        List<SearchParamInfo> searchParams = new ArrayList<SearchParamInfo>();
        searchParams.add(CluSearchUtil.getTypeSearchParamForCourse());
        searchParams.add(CluSearchUtil.getApprovedStateSearchParam());
        for (QueryParamEnum qpEnum : QueryParamEnum.values()) {
            String fieldValue = fieldValues.get(qpEnum.getFieldValue());
            if ( ! isEmpty(fieldValue) ) {
                SearchParamInfo qpv = new SearchParamInfo();
                qpv.setKey(qpEnum.getQueryKey());
                qpv.getValues().add(fieldValue);
                searchParams.add(qpv);
            }
        }

        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setParams(searchParams);
        searchRequest.setSearchKey("lu.search.mostCurrent.union");
        searchRequest.setSortColumn("lu.resultColumn.luOptionalCode");

        try {
            SearchResultInfo searchResult = getCluService().search(searchRequest, ContextUtils.getContextInfo());
            return CluSearchUtil.resolveCluSearchResultSet(searchResult);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected CluService getCluService() {
        if (cluService == null) {
            cluService = GlobalResourceLoader.getService(new QName(CluServiceConstants.CLU_NAMESPACE, CluServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return cluService;
    }
}
