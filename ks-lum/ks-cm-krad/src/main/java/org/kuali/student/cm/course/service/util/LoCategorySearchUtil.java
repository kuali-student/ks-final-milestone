package org.kuali.student.cm.course.service.util;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.cm.course.form.wrapper.LoCategoryInfoWrapper;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.lum.lo.service.LearningObjectiveService;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class LoCategorySearchUtil {

    private static final Logger LOG = LoggerFactory.getLogger(LoCategorySearchUtil.class);

    private LoCategorySearchUtil() {
    }

    public static List<LoCategoryInfoWrapper> searchForLoCategories(String categoryName, LearningObjectiveService learningObjectiveService) {
        List<LoCategoryInfoWrapper> retrievedCategories = new ArrayList<LoCategoryInfoWrapper>();

        List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();

        if (StringUtils.isNotEmpty(categoryName)) {
            SearchParamInfo categoryNameParam = new SearchParamInfo();
            categoryNameParam.setKey(CourseServiceConstants.OPTIONAL_LO_CATEGORY_NAME_PARAM);
            List<String> categoryNameValues = new ArrayList<String>();
            categoryNameValues.add(categoryName);
            categoryNameParam.setValues(categoryNameValues);
            queryParamValueList.add(categoryNameParam);
        }

        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey(CourseServiceConstants.LOCATEGORY_SEARCH);
        searchRequest.setParams(queryParamValueList);
        searchRequest.setSortColumn(CourseServiceConstants.LO_CATEGORY_NAME_AND_TYPE_RESULT);

        try {
            SearchResultInfo searchResult = learningObjectiveService.search(searchRequest,
                    ContextUtils.createDefaultContextInfo());
            for (SearchResultRowInfo result : searchResult.getRows()) {
                List<SearchResultCellInfo> cells = result.getCells();
                LoCategoryInfoWrapper newCat = new LoCategoryInfoWrapper();
                for (SearchResultCellInfo cell : cells) {
                    if (CourseServiceConstants.LO_CATEGORY_ID_RESULT.equals(cell.getKey())) {
                        newCat.setId(cell.getValue());
                    } else if (CourseServiceConstants.LO_CATEGORY_NAME_RESULT.equals(cell.getKey())) {
                        newCat.setName(cell.getValue());
                    } else if(CourseServiceConstants.LO_CATEGORY_TYPE_RESULT.equals(cell.getKey())){
                        newCat.setTypeKey(cell.getValue());
                    } else if(CourseServiceConstants.LO_CATEGORY_TYPE_NAME_RESULT.equals(cell.getKey())){
                        newCat.setTypeName(cell.getValue());
                    } else if (CourseServiceConstants.LO_CATEGORY_NAME_AND_TYPE_RESULT.equals(cell.getKey())) {
                        newCat.setCatNameAndType(cell.getValue());
                    } else if(CourseServiceConstants.LO_CATEGORY_STATE_RESULT.equals(cell.getKey())){
                        newCat.setStateKey(cell.getValue());
                    }
                }
                retrievedCategories.add(newCat);
            }
        } catch (Exception e) {
            LOG.error("An error occurred in searchForLoCategories.", e);
        }

        return retrievedCategories;
    }

}
