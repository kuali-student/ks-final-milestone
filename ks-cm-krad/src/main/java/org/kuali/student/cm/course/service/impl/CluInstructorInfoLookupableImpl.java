/**
 * Copyright 2005-2013 The Kuali Foundation Licensed under the
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
 */
package org.kuali.student.cm.course.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.lookup.LookupForm;
import org.kuali.student.cm.course.form.CluInstructorInfoWrapper;
import org.kuali.student.logging.FormattedLogger;
import org.kuali.student.lum.lu.util.CurriculumManagementConstants;
import org.kuali.student.r1.core.personsearch.service.impl.QuickViewByGivenName;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CluInstructorInfoLookupableImpl extends LookupableImpl {

	private static final long serialVersionUID = -3027283578926320100L;
    private static final String PERSON_ID = "personId";
    private static final String DISPLAY_NAME ="displayName";

	
	private SearchService searchService;

	@Override
	public List<?> performSearch(LookupForm form,
			Map<String, String> searchCriteria, boolean bounded) {
		List<CluInstructorInfoWrapper> cluInstructorInfoDisplays = new ArrayList<CluInstructorInfoWrapper>();
		
		List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();
        String displayName = searchCriteria.get(DISPLAY_NAME);
        String personId = searchCriteria.get(PERSON_ID);
        
        if (StringUtils.isNotBlank(displayName)) {
            SearchParamInfo displayNameParam = new SearchParamInfo();
            displayNameParam.setKey(QuickViewByGivenName.NAME_PARAM);
            displayNameParam.getValues().add(displayName);
            queryParamValueList.add(displayNameParam);
        }
        if (StringUtils.isNotBlank(personId)) {
        	SearchParamInfo personIdParam = new SearchParamInfo();
        	personIdParam.setKey(QuickViewByGivenName.ID_PARAM);
        	personIdParam.getValues().add(personId);
        	queryParamValueList.add(personIdParam);
        }
        
        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey(QuickViewByGivenName.SEARCH_TYPE);
        searchRequest.setParams(queryParamValueList);
        searchRequest.setStartAt(0);
        searchRequest.setNeededTotalResults(false);
        searchRequest.setSortColumn(QuickViewByGivenName.DISPLAY_NAME_RESULT);
        
        SearchResultInfo searchResult = null;
        try {
        	searchResult = getSearchService().search(searchRequest, ContextUtils.getContextInfo());
        	for (SearchResultRowInfo result : searchResult.getRows()) {
                List<SearchResultCellInfo> cells = result.getCells();
                CluInstructorInfoWrapper cluInstructorInfoDisplay = new CluInstructorInfoWrapper();
                for (SearchResultCellInfo cell : cells) {
                    if (QuickViewByGivenName.GIVEN_NAME_RESULT.equals(cell.getKey())) {
                    	cluInstructorInfoDisplay.setGivenName(cell.getValue());
                    } else if (QuickViewByGivenName.PERSON_ID_RESULT.equals(cell.getKey())) {
                    	cluInstructorInfoDisplay.setPersonId(cell.getValue());
                    } else if (QuickViewByGivenName.ENTITY_ID_RESULT.equals(cell.getKey())) {
                    	cluInstructorInfoDisplay.setId(cell.getValue());
                    } else if (QuickViewByGivenName.PRINCIPAL_NAME_RESULT.equals(cell.getKey())) {
                    	cluInstructorInfoDisplay.setPrincipalName(cell.getValue());
                    } else if (QuickViewByGivenName.DISPLAY_NAME_RESULT.equals(cell.getKey())) {
                    	cluInstructorInfoDisplay.setDisplayName(cell.getValue());
                    }
                }
                cluInstructorInfoDisplays.add(cluInstructorInfoDisplay);
        	}
		} catch (Exception e) {
		    FormattedLogger.error(CurriculumManagementConstants.ConfigProperties.ERROR_OCCURRED_RETRIEVING_CLU_INSTRUCTORS + e);
		}
        
		return cluInstructorInfoDisplays;
	}
	
	private SearchService getSearchService() {
		if (searchService == null) {
			searchService = GlobalResourceLoader.getService(new QName(CourseServiceConstants.NAMESPACE_PERSONSEACH, CourseServiceConstants.PERSONSEACH_SERVICE_NAME_LOCAL_PART));
		}
		return searchService;
	}

}
