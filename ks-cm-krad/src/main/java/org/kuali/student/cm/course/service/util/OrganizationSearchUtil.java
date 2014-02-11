package org.kuali.student.cm.course.service.util;

import static org.kuali.student.logging.FormattedLogger.info;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.cm.course.form.OrganizationInfoWrapper;
import org.kuali.student.cm.course.service.CourseInfoMaintainable;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;

public class OrganizationSearchUtil {
    
    
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
        displayNameParam.setKey("org.queryParam.orgOptionalLongName");
        displayNameParam.getValues().add(organizationName);
        queryParamValueList.add(displayNameParam);

        final SearchParamInfo orgOptionalTypeParam = new SearchParamInfo();
        orgOptionalTypeParam.setKey("org.queryParam.orgOptionalType");
        orgOptionalTypeParam.getValues().add("kuali.org.COC");
        orgOptionalTypeParam.getValues().add("kuali.org.Department");
        orgOptionalTypeParam.getValues().add("kuali.org.College");
        queryParamValueList.add(orgOptionalTypeParam);
        
        final SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey("org.search.generic");
        searchRequest.setParams(queryParamValueList);
        searchRequest.setStartAt(0);
        searchRequest.setNeededTotalResults(false);
        searchRequest.setSortColumn("org.resultColumn.orgOptionalLongName");
        
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
                
                if ("org.resultColumn.orgId".equals(cell.getKey())) {
                    cluOrgInfoDisplay.setId(cell.getValue());
                } 
                else if ("org.resultColumn.orgOptionalLongName".equals(cell.getKey())) {
                    cluOrgInfoDisplay.setOrganizationName(cell.getValue());
                } 
            }
            cluOrgInfoDisplays.add(cluOrgInfoDisplay);
        }
        
        return cluOrgInfoDisplays;
    }
    
    

}
