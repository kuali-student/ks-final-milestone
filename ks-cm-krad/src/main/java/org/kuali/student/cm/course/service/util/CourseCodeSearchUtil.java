package org.kuali.student.cm.course.service.util;

import static org.kuali.student.logging.FormattedLogger.error;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.kuali.student.cm.course.form.CourseJointInfoWrapper;
import org.kuali.student.cm.course.service.impl.LookupableConstants;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.lum.clu.service.CluService;

public class CourseCodeSearchUtil {
    
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
        codeParam.setKey(LookupableConstants.OPTIONAL_CODE_PARAM);
        List<String> codeValues = new ArrayList<String>();
        codeValues.add(courseNumber);
        codeParam.setValues(codeValues);
        
        SearchParamInfo typeParam = new SearchParamInfo();
        typeParam.setKey(LookupableConstants.OPTIONAL_TYPE_PARAM);
        List<String> typeValues = new ArrayList<String>();
        typeValues.add(LookupableConstants.CREDITCOURSE_LU);
        typeParam.setValues(typeValues);
        
        queryParamValueList.add(codeParam);
        queryParamValueList.add(typeParam);
        
        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey(LookupableConstants.CURRENT_QUICK_SEARCH);
        searchRequest.setParams(queryParamValueList);
        
        SearchResultInfo searchResult = null;
        try {
            searchResult = cluService.search(searchRequest, ContextUtils.getContextInfo());
            
            for (SearchResultRowInfo result : searchResult.getRows()) {
                List<SearchResultCellInfo> cells = result.getCells();
                String id = "";
                String code = "";
                for (SearchResultCellInfo cell : cells) {
                    if (LookupableConstants.ID_RESULT.equals(cell.getKey())) {
                        id = cell.getValue();
                    } else if (LookupableConstants.OPTIONALCODE_RESULT.equals(cell.getKey())) {
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
            error("An error occurred while searching for Course Codes: ", e);
        }
        
        return searchWrappers;
    }
    
    /**
     * This method might be temporary. (See Jira KSCM-912) Retrieves the
     * {@link CourseCodeSearchWrapper} that matches the specific courseNumber and converts
     * it to a {@link CourseJointInfoWrapper}. Only 1 instance will be returned.
     * 
     * @param courseNumber
     * @param cluService
     * @return The exact match for the 'courseNumber' parameter.
     */
    public static CourseJointInfoWrapper getCourseJointInfoWrapper(String courseNumber, CluService cluService) {
        List<CourseCodeSearchWrapper> searchWrappers = searchForCourseNumbers(courseNumber, cluService);
        CourseJointInfoWrapper courseJointInfoWrapper = null;
        if (searchWrappers != null) {
            courseJointInfoWrapper = convertToCourseJointInfoWrapper(searchWrappers.get(0));
        }
        return courseJointInfoWrapper;
    }
    
    /**
     * 
     * The same search as 'searchForCourseNumbers', but converted to {@link CourseJointInfoWrapper}
     * @see #searchForCourseNumbers
     */
    public static List<CourseJointInfoWrapper> searchForCourseJointInfos(String courseNumber, CluService cluService) {
        return convertListToCourseJointInfoWrappers(searchForCourseNumbers(courseNumber, cluService));
    }
    
    private static CourseJointInfoWrapper convertToCourseJointInfoWrapper(CourseCodeSearchWrapper searchWrapper) {
        CourseJointInfoWrapper instance = new CourseJointInfoWrapper();
        try {
            BeanUtils.copyProperties(instance, searchWrapper);
        } catch (Exception e) {
            error("An error occurred while converting from the CouresCodeSearchWrapper to a CourseJointInfoWrapper: ", e);
        }
        return instance;
    }
    
    private static List<CourseJointInfoWrapper> convertListToCourseJointInfoWrappers(List<CourseCodeSearchWrapper> searchWrappers) {
        List<CourseJointInfoWrapper> courseJointWrappers = new ArrayList<CourseJointInfoWrapper>();
        if (searchWrappers != null) {
            for (CourseCodeSearchWrapper searchWrapper : searchWrappers) {
                courseJointWrappers.add(convertToCourseJointInfoWrapper(searchWrapper));
            }
        }
        return courseJointWrappers;
    }
    
}
