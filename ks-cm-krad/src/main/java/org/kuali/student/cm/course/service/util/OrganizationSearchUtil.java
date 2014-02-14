package org.kuali.student.cm.course.service.util;

import org.kuali.student.cm.course.form.OrganizationInfoWrapper;
import org.kuali.student.cm.course.service.CourseInfoMaintainable;
import org.kuali.student.lum.lu.util.CurriculumManagementConstants;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.constants.OrganizationServiceConstants;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;

import java.util.ArrayList;
import java.util.List;

import static org.kuali.student.logging.FormattedLogger.info;

public class OrganizationSearchUtil {
    
    private OrganizationSearchUtil() {
    }

    /**
     * Method called when queryMethodToCall is executed for Administering Organizations in order to suggest back to the user an Administering Organization
     *
     * @param organizationName  
     * @return {@link List} of wrapper instances which get added to the {@link CourseForm}
     * @see CourseInfoMaintainable#getOrganizationsForSuggest(String)
     */
    public static List<OrganizationInfoWrapper> searchForOrganizations(final String organizationName, OrganizationService organizationService) {
        final List<OrganizationInfoWrapper> cluOrgInfoDisplays = new ArrayList<OrganizationInfoWrapper>();
        final List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();
        
        final SearchParamInfo displayNameParam = new SearchParamInfo();
        displayNameParam.setKey(CurriculumManagementConstants.QUERY_PARAM_OPTIONAL_LONG_NAME);
        displayNameParam.getValues().add(organizationName);
        queryParamValueList.add(displayNameParam);

        final SearchParamInfo orgOptionalTypeParam = new SearchParamInfo();
        orgOptionalTypeParam.setKey(CurriculumManagementConstants.QUERY_PARAM_OPTIONAL_TYPE);
        orgOptionalTypeParam.getValues().add(OrganizationServiceConstants.ORGANIZATION_COC_TYPE_KEY);
        orgOptionalTypeParam.getValues().add(OrganizationServiceConstants.ORGANIZATION_DEPARTMENT_TYPE_KEY);
        orgOptionalTypeParam.getValues().add(OrganizationServiceConstants.ORGANIZATION_COLLEGE_TYPE_KEY);
        queryParamValueList.add(orgOptionalTypeParam);
        
        final SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey(CurriculumManagementConstants.SEARCH_GENERIC);
        searchRequest.setParams(queryParamValueList);
        searchRequest.setStartAt(0);
        searchRequest.setNeededTotalResults(false);
        searchRequest.setSortColumn(CurriculumManagementConstants.RESULT_COLUMN_OPTIONAL_LONG_NAME);
        
        SearchResultInfo searchResult = null;
        try {
            searchResult = organizationService.search(searchRequest, ContextUtils.getContextInfo());
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (final SearchResultRowInfo result : searchResult.getRows()) {
            final List<SearchResultCellInfo> cells = result.getCells();
            final OrganizationInfoWrapper cluOrgInfoDisplay = new OrganizationInfoWrapper();
            for (final SearchResultCellInfo cell : cells) {
                info("Got key %s", cell.getKey());
                info("Got key %s", cell.getValue());
                
                if ((CurriculumManagementConstants.RESULT_COLUMN_ID).equals(cell.getKey())) {
                    cluOrgInfoDisplay.setId(cell.getValue());
                } 
                else if ((CurriculumManagementConstants.RESULT_COLUMN_OPTIONAL_LONG_NAME).equals(cell.getKey())) {
                    cluOrgInfoDisplay.setOrganizationName(cell.getValue());
                } 
            }
            cluOrgInfoDisplays.add(cluOrgInfoDisplay);
        }
        return cluOrgInfoDisplays;
    }
}
