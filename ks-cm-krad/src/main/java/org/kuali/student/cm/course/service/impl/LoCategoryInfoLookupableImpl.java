package org.kuali.student.cm.course.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.cm.course.form.LoCategoryInfoWrapper;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.LearningObjectiveServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.lum.lo.service.LearningObjectiveService;

/**
 * Lookupable service class for {@link LoCategoryInfoWrapper} "Browse for categories" link
 * 
 * @author OpenCollab/rSmart KRAD CM Conversion Alliance!
 */
public class LoCategoryInfoLookupableImpl extends LookupableImpl {

    private static final long serialVersionUID = 4742457104713770383L;
    
    private LearningObjectiveService learningObjectiveService;
    
    @Override
    protected List<?> getSearchResults(LookupForm form, Map<String, String> searchCriteria, boolean unbounded) {
        List<LoCategoryInfoWrapper> loCategories = new ArrayList<LoCategoryInfoWrapper>();
        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey(LookupableConstants.LOCATEGORY_SEARCH);
        searchRequest.setSortColumn(LookupableConstants.LO_CATEGORY_NAME_AND_TYPE_RESULT);
        try {
            SearchResultInfo searchResult = getLearningObjectiveService().search(searchRequest, ContextUtils.getContextInfo());
            for (SearchResultRowInfo result : searchResult.getRows()) {
                List<SearchResultCellInfo> cells = result.getCells();
                LoCategoryInfoWrapper loWrapper = new LoCategoryInfoWrapper();
                for (SearchResultCellInfo cell : cells) {
                    if (LookupableConstants.LO_CATEGORY_ID_RESULT.equals(cell.getKey())) {
                        loWrapper.setId(cell.getValue());
                    } else if (LookupableConstants.LO_CATEGORY_NAME_RESULT.equals(cell.getKey())) {
                        loWrapper.setName(cell.getValue());
                    } else if(LookupableConstants.LO_CATEGORY_TYPE_RESULT.equals(cell.getKey())){
                        loWrapper.setTypeKey(cell.getValue());
                    } else if(LookupableConstants.LO_CATEGORY_TYPE_NAME_RESULT.equals(cell.getKey())){
                        loWrapper.setTypeName(cell.getValue());
                    } else if (LookupableConstants.LO_CATEGORY_NAME_AND_TYPE_RESULT.equals(cell.getKey())) {
                        loWrapper.setCatNameAndType(cell.getValue());
                    } else if(LookupableConstants.LO_CATEGORY_STATE_RESULT.equals(cell.getKey())){
                        loWrapper.setStateKey(cell.getValue());
                    }
                }
                loCategories.add(loWrapper);
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while searching for the available Learning Categories", e);
        }
        return loCategories;
    }   
    
    protected LearningObjectiveService getLearningObjectiveService() {
        if (learningObjectiveService == null) {
            learningObjectiveService = GlobalResourceLoader.getService(new QName(LearningObjectiveServiceConstants.NAMESPACE, LearningObjectiveService.class.getSimpleName()));
        }
        return learningObjectiveService;
    }
    
}
