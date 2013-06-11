package org.kuali.student.cm.course.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.cm.course.form.CluInstructorInfoDisplay;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.service.SearchService;

public class CluInstructorInfoLookupableImpl extends LookupableImpl {

	private static final long serialVersionUID = -3027283578926320100L;
	
	private SearchService searchService;

	@Override
	protected List<?> getSearchResults(LookupForm form,
			Map<String, String> searchCriteria, boolean unbounded) {
		List<CluInstructorInfoDisplay> cluInstructorInfoDisplays = new ArrayList<CluInstructorInfoDisplay>();
		
		List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();
        String displayName = searchCriteria.get("displayName");
        String personId = searchCriteria.get("personId");
        
        if (StringUtils.isNotBlank(displayName)) {
            SearchParamInfo displayNameParam = new SearchParamInfo();
            displayNameParam.setKey("person.queryParam.personGivenName");
            displayNameParam.getValues().add(displayName);
            queryParamValueList.add(displayNameParam);
        }
        if (StringUtils.isNotBlank(personId)) {
        	SearchParamInfo personIdParam = new SearchParamInfo();
        	personIdParam.setKey("person.queryParam.personId");
        	personIdParam.getValues().add(personId);
        	queryParamValueList.add(personIdParam);
        }
        
        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey("person.search.personQuickViewByGivenName");
        searchRequest.setParams(queryParamValueList);
        searchRequest.setStartAt(0);
        searchRequest.setMaxResults(10);
        searchRequest.setNeededTotalResults(false);
        searchRequest.setSortColumn("person.resultColumn.DisplayName");
        
        SearchResultInfo searchResult = null;
        try {
        	searchResult = getSearchService().search(searchRequest, ContextUtils.getContextInfo());
        	for (SearchResultRowInfo result : searchResult.getRows()) {
                List<SearchResultCellInfo> cells = result.getCells();
                CluInstructorInfoDisplay cluInstructorInfoDisplay = new CluInstructorInfoDisplay();
                for (SearchResultCellInfo cell : cells) {
                    if ("person.resultColumn.GivenName".equals(cell.getKey())) {
                    	cluInstructorInfoDisplay.setGivenName(cell.getValue());
                    } else if ("person.resultColumn.PersonId".equals(cell.getKey())) {
                    	cluInstructorInfoDisplay.setPersonId(cell.getValue());
                    } else if ("person.resultColumn.EntityId".equals(cell.getKey())) {
                    	cluInstructorInfoDisplay.setId(cell.getValue());
                    } else if ("person.resultColumn.PrincipalName".equals(cell.getKey())) {
                    	cluInstructorInfoDisplay.setPrincipalName(cell.getValue());
                    } else if ("person.resultColumn.DisplayName".equals(cell.getKey())) {
                    	cluInstructorInfoDisplay.setDisplayName(cell.getValue());
                    }
                }
                cluInstructorInfoDisplays.add(cluInstructorInfoDisplay);
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
        
		return cluInstructorInfoDisplays;
	}
	
	private SearchService getSearchService() {
		if (searchService == null) {
			searchService = GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/personsearch", "PersonSearchService"));
		}
		return searchService;
	}

}
