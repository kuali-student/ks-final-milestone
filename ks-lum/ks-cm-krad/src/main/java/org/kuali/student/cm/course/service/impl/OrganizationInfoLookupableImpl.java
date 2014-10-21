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
import org.kuali.student.cm.course.form.wrapper.OrganizationInfoWrapper;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;



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
    private static final Logger LOG = LoggerFactory.getLogger(OrganizationInfoLookupableImpl.class);

	private OrganizationService organizationService;

	@Override
	public List<?> performSearch(final LookupForm form,
                                       final Map<String, String> searchCriteria, 
                                       final boolean bounded) {
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

        LOG.info("Searching for {}", queryParamValueList);

        final SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey(CurriculumManagementConstants.OrganizationMessageKeys.ORG_SEARCH_GENERIC);
        searchRequest.setParams(queryParamValueList);
        searchRequest.setStartAt(0);
        searchRequest.setNeededTotalResults(false);
        searchRequest.setSortColumn(CurriculumManagementConstants.OrganizationMessageKeys.ORG_RESULT_COLUMN_OPTIONAL_ID);

        SearchResultInfo searchResult = null;
        try {
        	searchResult = getOrganizationService().search(searchRequest, ContextUtils.createDefaultContextInfo());
		} catch (Exception e) {
            LOG.error("An error occurred in getting search result.", e);
		}

        for (final SearchResultRowInfo result : searchResult.getRows()) {
            final List<SearchResultCellInfo> cells = result.getCells();
            final OrganizationInfoWrapper cluOrgInfoDisplay = new OrganizationInfoWrapper();
            for (final SearchResultCellInfo cell : cells) {
                LOG.debug("Got key: {} value: {}", cell.getKey(), cell.getValue());

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
	
	protected OrganizationService getOrganizationService() {
		if (organizationService == null) {
	        organizationService = (OrganizationService) GlobalResourceLoader
                .getService(new QName(QNAME,ORGANIZATION_SERVICE));
		}
		return organizationService;
	}

}
