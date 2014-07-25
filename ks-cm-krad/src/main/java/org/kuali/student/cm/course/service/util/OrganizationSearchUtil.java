package org.kuali.student.cm.course.service.util;

import org.kuali.student.cm.course.form.wrapper.OrganizationInfoWrapper;
import org.kuali.student.cm.common.util.CurriculumManagementConstants;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.core.constants.OrganizationServiceConstants;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class OrganizationSearchUtil {

    private static final Logger LOG = LoggerFactory.getLogger(OrganizationSearchUtil.class);

    private OrganizationSearchUtil() {
    }

    /**
     * Method called when queryMethodToCall is executed for Administering Organizations in order to suggest back to the user an Administering Organization
     *
     * @param organizationName  
     * @return {@link List} of wrapper instances which get added to the {@link CourseForm}
     * @see org.kuali.student.cm.course.service.CourseMaintainable#getOrganizationsForSuggest(String)
     */
    public static List<OrganizationInfoWrapper> searchForOrganizations(final String organizationName, OrganizationService organizationService) {
        final List<OrganizationInfoWrapper> cluOrgInfoDisplays = new ArrayList<OrganizationInfoWrapper>();
        final List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();
        
        final SearchParamInfo displayNameParam = new SearchParamInfo();
        displayNameParam.setKey(CurriculumManagementConstants.OrganizationMessageKeys.ORG_QUERY_PARAM_OPTIONAL_LONG_NAME);
        displayNameParam.getValues().add("%" + organizationName);
        queryParamValueList.add(displayNameParam);

        final SearchParamInfo orgOptionalTypeParam = new SearchParamInfo();
        orgOptionalTypeParam.setKey(CurriculumManagementConstants.OrganizationMessageKeys.ORG_QUERY_PARAM_OPTIONAL_TYPE);
        orgOptionalTypeParam.getValues().add(OrganizationServiceConstants.ORGANIZATION_COC_TYPE_KEY);
        orgOptionalTypeParam.getValues().add(OrganizationServiceConstants.ORGANIZATION_DEPARTMENT_TYPE_KEY);
        orgOptionalTypeParam.getValues().add(OrganizationServiceConstants.ORGANIZATION_COLLEGE_TYPE_KEY);
        queryParamValueList.add(orgOptionalTypeParam);
        
        final SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey(CurriculumManagementConstants.OrganizationMessageKeys.ORG_SEARCH_GENERIC);
        searchRequest.setParams(queryParamValueList);
        searchRequest.setStartAt(0);
        searchRequest.setNeededTotalResults(false);
        searchRequest.setSortColumn(CurriculumManagementConstants.OrganizationMessageKeys.ORG_RESULT_COLUMN_OPTIONAL_LONG_NAME);
        
        SearchResultInfo searchResult = null;
        try {
            searchResult = organizationService.search(searchRequest, ContextUtils.createDefaultContextInfo());
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (final SearchResultRowInfo result : searchResult.getRows()) {
            final List<SearchResultCellInfo> cells = result.getCells();
            final OrganizationInfoWrapper cluOrgInfoDisplay = new OrganizationInfoWrapper();
            for (final SearchResultCellInfo cell : cells) {
                LOG.info("Got key: {} value: {}", cell.getKey(), cell.getValue());

                if ((CurriculumManagementConstants.OrganizationMessageKeys.ORG_RESULT_COLUMN_ID).equals(cell.getKey())) {
                    cluOrgInfoDisplay.setId(cell.getValue());
                } 
                else if ((CurriculumManagementConstants.OrganizationMessageKeys.ORG_RESULT_COLUMN_OPTIONAL_LONG_NAME).equals(cell.getKey())) {
                    cluOrgInfoDisplay.setOrganizationName(cell.getValue());
                } 
            }
            cluOrgInfoDisplays.add(cluOrgInfoDisplay);
        }
        return cluOrgInfoDisplays;
    }
}
