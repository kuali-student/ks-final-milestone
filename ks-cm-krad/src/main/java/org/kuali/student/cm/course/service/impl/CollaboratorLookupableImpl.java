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
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.cm.course.form.CollaboratorWrapper;
import org.kuali.student.lum.lu.util.CurriculumManagementConstants;
import org.kuali.student.logging.FormattedLogger;
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

/**
*
* @author OpenCollab/rSmart KRAD CM Conversion Alliance!
* 
*/ 
public class CollaboratorLookupableImpl extends LookupableImpl {

	private static final long serialVersionUID = -3027283578926320100L;
    private static final String PERSON_ID = "personId";
    private static final String DISPLAY_NAME ="displayName";

    private SearchService searchService;

	@Override
	protected List<?> getSearchResults(LookupForm form,
			Map<String, String> searchCriteria, boolean unbounded) {
		List<CollaboratorWrapper> collaboratorDisplays = new ArrayList<CollaboratorWrapper>();
		
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
                CollaboratorWrapper collaboratorDisplay = new CollaboratorWrapper();
                //implement a test to remove searched username from list
                for (SearchResultCellInfo cell : cells) {
                    
                    if (QuickViewByGivenName.GIVEN_NAME_RESULT.equals(cell.getKey())) {
                    	collaboratorDisplay.setGivenName(cell.getValue());
                    } else if (QuickViewByGivenName.PERSON_ID_RESULT.equals(cell.getKey())) {
                    	collaboratorDisplay.setPersonID(cell.getValue());
                    } else if (QuickViewByGivenName.ENTITY_ID_RESULT.equals(cell.getKey())) {
                    	collaboratorDisplay.setPrincipalId(cell.getValue());
                    } else if (QuickViewByGivenName.PRINCIPAL_NAME_RESULT.equals(cell.getKey())) {
                    	collaboratorDisplay.setPrincipalName(cell.getValue());
                    } else if (QuickViewByGivenName.DISPLAY_NAME_RESULT.equals(cell.getKey())) {
                    	collaboratorDisplay.setDisplayName(cell.getValue());
                    }
                }
                if(!ContextUtils.getContextInfo().getPrincipalId().equals(collaboratorDisplay.getPersonID())){
                    collaboratorDisplays.add(collaboratorDisplay);    
                }
        	}
		} catch (Exception e) {
            FormattedLogger.error("An error occurred retrieving the Collaborators: " + e);
		}
        
		return collaboratorDisplays;
	}
	
	private SearchService getSearchService() {
		if (searchService == null) {
			searchService = GlobalResourceLoader.getService(new QName(CourseServiceConstants.NAMESPACE_PERSONSEACH, CourseServiceConstants.PERSONSEACH_SERVICE_NAME_LOCAL_PART));
		}
		return searchService;
	}

}
