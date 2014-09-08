package org.kuali.student.cm.course.service.util;

import org.apache.commons.beanutils.BeanUtils;
import org.kuali.student.cm.course.form.wrapper.CourseJointInfoWrapper;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public class CourseCodeSearchUtil {

    private static final Logger LOG = LoggerFactory.getLogger(CourseCodeSearchUtil.class);

    private CourseCodeSearchUtil() {
    }

    /**
     * Search for all course codes matching the 'courseNumber'
     * 
     * @param courseNumber - The search string
     * @param cluService - The service used to execute the search
     * @return A list of {@link CourseCodeSearchWrapper} that matches the search criteria.
     */
    public static List<CourseCodeSearchWrapper> searchForCourseNumbers(String courseNumber, CluService cluService) {
        List<CourseCodeSearchWrapper> searchWrappers = new ArrayList<CourseCodeSearchWrapper>();
        
        List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();
        
        SearchParamInfo codeParam = new SearchParamInfo();
        codeParam.setKey(CourseServiceConstants.OPTIONAL_CODE_PARAM);
        List<String> codeValues = new ArrayList<String>();
        codeValues.add(courseNumber);
        codeParam.setValues(codeValues);
        
        SearchParamInfo typeParam = new SearchParamInfo();
        typeParam.setKey(CourseServiceConstants.OPTIONAL_TYPE_PARAM);
        List<String> typeValues = new ArrayList<String>();
        typeValues.add(CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY);
        typeParam.setValues(typeValues);
        
        queryParamValueList.add(codeParam);
        queryParamValueList.add(typeParam);
        
        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey(CourseServiceConstants.CURRENT_QUICK_SEARCH);
        searchRequest.setParams(queryParamValueList);
        
        SearchResultInfo searchResult = null;
        try {
            searchResult = cluService.search(searchRequest, ContextUtils.createDefaultContextInfo());
            
            for (SearchResultRowInfo result : searchResult.getRows()) {
                List<SearchResultCellInfo> cells = result.getCells();
                String id = "";
                String code = "";
                for (SearchResultCellInfo cell : cells) {
                    if (CourseServiceConstants.ID_RESULT.equals(cell.getKey())) {
                        id = cell.getValue();
                    } else if (CourseServiceConstants.OPTIONALCODE_RESULT.equals(cell.getKey())) {
                        code = cell.getValue();
                    }
                }
                CourseCodeSearchWrapper searchWrapper = new CourseCodeSearchWrapper();
                searchWrapper.setCourseId(id);
                searchWrapper.setCourseCode(code);
                String subjectArea = code.replaceAll("\\d", "");
                String numberSuffix = code.replaceAll("\\D", "");
                searchWrapper.setSubjectArea(subjectArea);
                searchWrapper.setCourseNumberSuffix(numberSuffix);
                searchWrappers.add(searchWrapper);
            }
        } catch (Exception e) {
            LOG.error("An error occurred while searching for Course Codes: ", e);
        }
        
        return searchWrappers;
    }
    
    /**
     * 
     * The same search as 'searchForCourseNumbers', but converted to {@link CourseJointInfoWrapper}
     * @see #searchForCourseNumbers
     */
    public static List<CourseJointInfoWrapper> searchForCourseJointInfos(String courseNumber, CluService cluService) {

        List<CourseJointInfoWrapper> courseJointWrappers = new ArrayList<CourseJointInfoWrapper>();

        List<CourseCodeSearchWrapper> matchFound = searchForCourseNumbers(courseNumber, cluService);

        for (CourseCodeSearchWrapper searchWrapper : matchFound) {
            CourseJointInfoWrapper jointInfoWrapper = new CourseJointInfoWrapper();
            try {
                BeanUtils.copyProperties(jointInfoWrapper, searchWrapper);
            } catch (Exception e) {
                LOG.error("An error occurred while converting from the CouresCodeSearchWrapper to a CourseJointInfoWrapper: ", e);
            }
            courseJointWrappers.add(jointInfoWrapper);
        }

        return courseJointWrappers;

    }
    
}
