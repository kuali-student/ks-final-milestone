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
import org.kuali.student.cm.course.form.OrganizationInfoWrapper;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;

import javax.xml.namespace.QName;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.kuali.student.logging.FormattedLogger.debug;
import static org.kuali.student.logging.FormattedLogger.error;
import static org.kuali.student.logging.FormattedLogger.info;


/**
 * Lookupable service class for {@link OrganizationInfoWrapper} advanced search
 * 
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 */
public class OrganizationInfoLookupableImpl extends LookupableImpl {

	private static final long serialVersionUID = -3027283578926320100L;
    public static final String QNAME = "http://student.kuali.org/wsdl/organization";
    public static final String ORGANIZATION_SERVICE = "OrganizationService";
    public static final String ORGANIZATION_NAME = "organizationName";
    public static final String ABBREVIATION = "abbreviation";
    public static final String ID = "id";
	
	private SearchService searchService;
	private OrganizationService organizationService;


	@Override
	protected List<?> getSearchResults(final LookupForm form,
                                       final Map<String, String> searchCriteria, 
                                       final boolean unbounded) {
		final List<OrganizationInfoWrapper> retval = new LinkedList<OrganizationInfoWrapper>();
		
		final List<SearchParamInfo> queryParamValueList = new LinkedList<SearchParamInfo>();
        final String id               = searchCriteria.get(ID);
        final String organizationName = searchCriteria.get(ORGANIZATION_NAME);
        final String shortName        = searchCriteria.get(ABBREVIATION);
        
        if (StringUtils.isNotBlank(id)) {
            final SearchParamInfo idParam = new SearchParamInfo();
            idParam.setKey(CurriculumManagementConstants.OrganizationMessageKeys.ORG_QUERY_PARAM_OPTIONAL_ID);
            idParam.getValues().add(id);
            queryParamValueList.add(idParam);
        }
        if (StringUtils.isNotBlank(organizationName)) {
            final SearchParamInfo displayNameParam = new SearchParamInfo();
            displayNameParam.setKey(CurriculumManagementConstants.OrganizationMessageKeys.ORG_QUERY_PARAM_OPTIONAL_LONG_NAME);
            displayNameParam.getValues().add(organizationName);
            queryParamValueList.add(displayNameParam);
        }

        if (StringUtils.isNotBlank(shortName)) {
            final SearchParamInfo shortNameParam = new SearchParamInfo();
            shortNameParam.setKey(CurriculumManagementConstants.OrganizationMessageKeys.ORG_QUERY_PARAM_OPTIONAL_SHORT_NAME);
            shortNameParam.getValues().add(shortName);
            queryParamValueList.add(shortNameParam);
        }

        info("Searching for %s", queryParamValueList);

        final SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey(CurriculumManagementConstants.OrganizationMessageKeys.ORG_SEARCH_GENERIC);
        searchRequest.setParams(queryParamValueList);
        searchRequest.setStartAt(0);
        searchRequest.setNeededTotalResults(false);
        searchRequest.setSortColumn(CurriculumManagementConstants.OrganizationMessageKeys.ORG_RESULT_COLUMN_OPTIONAL_ID);

        SearchResultInfo searchResult = null;
        try {
        	searchResult = getOrganizationService().search(searchRequest, ContextUtils.getContextInfo());
		} catch (Exception e) {
            error("An error occurred in getting search result. %s", e.getMessage());
		}

        for (final SearchResultRowInfo result : searchResult.getRows()) {
            final List<SearchResultCellInfo> cells = result.getCells();
            final OrganizationInfoWrapper cluOrgInfoDisplay = new OrganizationInfoWrapper();
            for (final SearchResultCellInfo cell : cells) {
                debug("Got key %s", cell.getKey());
                debug("Got value %s", cell.getValue());
                
                if ((CurriculumManagementConstants.OrganizationMessageKeys.ORG_RESULT_COLUMN_ID).equals(cell.getKey())) {
                    cluOrgInfoDisplay.setId(cell.getValue());
                } 
                else if ((CurriculumManagementConstants.OrganizationMessageKeys.ORG_RESULT_COLUMN_OPTIONAL_LONG_NAME).equals(cell.getKey())) {
                    cluOrgInfoDisplay.setOrganizationName(cell.getValue());
                } 
                else if ((CurriculumManagementConstants.OrganizationMessageKeys.ORG_RESULT_COLUMN_SHORT_NAME).equals(cell.getKey())) {
                    cluOrgInfoDisplay.setAbbreviation(cell.getValue());
                }
            }
            retval.add(cluOrgInfoDisplay);
        }
        
		return retval;
	}
	
	protected SearchService getSearchService() {
		if (searchService == null) {
			searchService = GlobalResourceLoader.getService(new QName(CourseServiceConstants.NAMESPACE_PERSONSEACH, CourseServiceConstants.PERSONSEACH_SERVICE_NAME_LOCAL_PART));
		}
		return searchService;
	}

	protected OrganizationService getOrganizationService() {
		if (organizationService == null) {
	        organizationService = (OrganizationService) GlobalResourceLoader
                .getService(new QName(QNAME,ORGANIZATION_SERVICE));
		}
		return organizationService;
	}

}
