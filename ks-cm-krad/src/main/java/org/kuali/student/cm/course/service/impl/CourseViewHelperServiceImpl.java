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

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.student.cm.course.form.CluInstructorInfoWrapper;
import org.kuali.student.cm.course.form.CourseJointInfoWrapper;
import org.kuali.student.cm.course.form.SubjectCodeWrapper;
import org.kuali.student.logging.FormattedLogger;
import org.kuali.student.lum.lu.ui.course.keyvalues.KeyValueConstants;
import org.kuali.student.r1.core.personsearch.service.impl.QuickViewByGivenName;
import org.kuali.student.r1.core.subjectcode.service.SubjectCodeService;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.LearningObjectiveServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.lo.dto.LoCategoryInfo;
import org.kuali.student.r2.lum.lo.service.LearningObjectiveService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

/**
 * This is the helper class for CourseView
 * 
 * @author Kuali Student Team
 */
public class CourseViewHelperServiceImpl extends ViewHelperServiceImpl {

	private static final long serialVersionUID = 1338662637708570500L;

	private SearchService searchService;

	private SubjectCodeService subjectCodeService;

	private CluService cluService;
	
	private LearningObjectiveService learningObjectiveService;

	private static CourseViewHelperServiceImpl instance;
	
	public static final CourseViewHelperServiceImpl getInstance() {
		if (instance == null) {
			instance = new CourseViewHelperServiceImpl();
		}
		return instance;
	}
	
	public List<CluInstructorInfoWrapper> getInstructorsForSuggest(
			String instructorName) {
		List<CluInstructorInfoWrapper> cluInstructorInfoDisplays = new ArrayList<CluInstructorInfoWrapper>();
		
		List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();
        
        SearchParamInfo displayNameParam = new SearchParamInfo();
        displayNameParam.setKey(QuickViewByGivenName.NAME_PARAM);
        displayNameParam.getValues().add(instructorName);
        queryParamValueList.add(displayNameParam);
        
    	SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey(QuickViewByGivenName.SEARCH_TYPE);
        searchRequest.setParams(queryParamValueList);
        searchRequest.setStartAt(0);
        searchRequest.setMaxResults(10);
        searchRequest.setNeededTotalResults(false);
        searchRequest.setSortColumn(QuickViewByGivenName.DISPLAY_NAME_RESULT);
        
        SearchResultInfo searchResult = null;
        try {
        	searchResult = getSearchService().search(searchRequest, ContextUtils.getContextInfo());
        	for (SearchResultRowInfo result : searchResult.getRows()) {
                List<SearchResultCellInfo> cells = result.getCells();
                CluInstructorInfoWrapper cluInstructorInfoDisplay = new CluInstructorInfoWrapper();
                for (SearchResultCellInfo cell : cells) {
                    if (QuickViewByGivenName.GIVEN_NAME_RESULT.equals(cell.getKey())) {
                    	cluInstructorInfoDisplay.setGivenName(cell.getValue());
                    } else if (QuickViewByGivenName.PERSON_ID_RESULT.equals(cell.getKey())) {
                    	cluInstructorInfoDisplay.setPersonId(cell.getValue());
                    } else if (QuickViewByGivenName.ENTITY_ID_RESULT.equals(cell.getKey())) {
                    	cluInstructorInfoDisplay.setId(cell.getValue());
                    } else if (QuickViewByGivenName.PRINCIPAL_NAME_RESULT.equals(cell.getKey())) {
                    	cluInstructorInfoDisplay.setPrincipalName(cell.getValue());
                    } else if (QuickViewByGivenName.DISPLAY_NAME_RESULT.equals(cell.getKey())) {
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
	
	public CluInstructorInfoWrapper getInstructor(String instructorName) {
	    CluInstructorInfoWrapper instructor = null;

		List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();

		SearchParamInfo displayNameParam = new SearchParamInfo();
		displayNameParam.setKey(QuickViewByGivenName.NAME_PARAM);
		displayNameParam.getValues().add(instructorName);
		queryParamValueList.add(displayNameParam);

		SearchRequestInfo searchRequest = new SearchRequestInfo();
		searchRequest.setSearchKey(QuickViewByGivenName.SEARCH_TYPE);
		searchRequest.setParams(queryParamValueList);
		searchRequest.setStartAt(0);
		searchRequest.setMaxResults(10);
		searchRequest.setNeededTotalResults(false);
		searchRequest.setSortColumn(QuickViewByGivenName.DISPLAY_NAME_RESULT);

		SearchResultInfo searchResult = null;
		try {
			searchResult = getSearchService().search(searchRequest,
					ContextUtils.getContextInfo());
			if (searchResult.getRows().size() == 1) {
				SearchResultRowInfo result = searchResult.getRows().get(0);
				List<SearchResultCellInfo> cells = result.getCells();
				instructor = new CluInstructorInfoWrapper();
				for (SearchResultCellInfo cell : cells) {
				    if (QuickViewByGivenName.GIVEN_NAME_RESULT.equals(cell.getKey())) {
				        instructor.setGivenName(cell.getValue());
                    } else if (QuickViewByGivenName.PERSON_ID_RESULT.equals(cell.getKey())) {
                        instructor.setPersonId(cell.getValue());
                    } else if (QuickViewByGivenName.ENTITY_ID_RESULT.equals(cell.getKey())) {
                        instructor.setId(cell.getValue());
                    } else if (QuickViewByGivenName.PRINCIPAL_NAME_RESULT.equals(cell.getKey())) {
                        instructor.setPrincipalName(cell.getValue());
                    } else if (QuickViewByGivenName.DISPLAY_NAME_RESULT.equals(cell.getKey())) {
                        instructor.setDisplayName(cell.getValue());
                    }
				}
			} else {
				FormattedLogger
						.error("The method getInstructor returned more than 1 search result.");
			}
		} catch (Exception e) {
			FormattedLogger.error(
					"An error occurred in the getInstructor method.", e);
		}

		return instructor;
	}
	
    public List<SubjectCodeWrapper> getSubjectCodesForSuggest(String subjectCode) {
        List<SubjectCodeWrapper> retrievedCodes = new ArrayList<SubjectCodeWrapper>();

        List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();

        SearchParamInfo codeParam = new SearchParamInfo();
        codeParam.setKey(LookupableConstants.SUBJECTCODE_CODE_PARAM);
        List<String> codeValues = new ArrayList<String>();
        codeValues.add(subjectCode);
        codeParam.setValues(codeValues);

        queryParamValueList.add(codeParam);

        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey(LookupableConstants.SUBJECTCODE_GENERIC_SEARCH);
        searchRequest.setParams(queryParamValueList);

        SearchResultInfo searchResult = null;
        try {
            searchResult = getSubjectCodeService().search(searchRequest, ContextUtils.getContextInfo());
            for (SearchResultRowInfo result : searchResult.getRows()) {
                List<SearchResultCellInfo> cells = result.getCells();
                String id = "";
                String code = "";
                for (SearchResultCellInfo cell : cells) {
                    if (LookupableConstants.SUBJECTCODE_ID_RESULT.equals(cell.getKey())) {
                        id = cell.getValue();
                    } else if (LookupableConstants.SUBJECTCODE_CODE_RESULT.equals(cell.getKey())) {
                        code = cell.getValue();
                    }
                }
                retrievedCodes.add(new SubjectCodeWrapper(id, code));
            }
        } catch (Exception e) {
            FormattedLogger.error("An error occurred retrieving the SubjectCodeDisplay: " + e);
        }

        return retrievedCodes;
    }
	
	public List<CourseJointInfoWrapper> getJointOfferingCourseNumbersForSuggest(String courseNumber) {
		List<CourseJointInfoWrapper> courseJoints = new ArrayList<CourseJointInfoWrapper>();
		
		List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();
		
		SearchParamInfo codeParam = new SearchParamInfo();
        codeParam.setKey(LookupableConstants.OPTIONAL_CODE_PARAM);
        List<String> codeValues = new ArrayList<String>();
        codeValues.add(courseNumber);
        codeParam.setValues(codeValues);
        
        SearchParamInfo typeParam = new SearchParamInfo();
        typeParam.setKey(LookupableConstants.OPTIONAL_TYPE_PARAM);
        List<String> typeValues = new ArrayList<String>();
        typeValues.add(LookupableConstants.CREDITCOURSE_lU);
        typeParam.setValues(typeValues);
        
        queryParamValueList.add(codeParam);
        queryParamValueList.add(typeParam);
        
        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey(LookupableConstants.CURRENT_QUICK_SEARCH);
        searchRequest.setParams(queryParamValueList);
        
        SearchResultInfo searchResult = null;
        try {
        	searchResult = getCluService().search(searchRequest, ContextUtils.getContextInfo());
        	
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
                CourseJointInfoWrapper courseJointDisplay = new CourseJointInfoWrapper();
                courseJointDisplay.setCourseId(id);
                courseJointDisplay.setCourseCode(code);
                String subjectArea = code.replaceAll("\\d", "");
                String numberSuffix = code.replaceAll("\\D", "");
                courseJointDisplay.setSubjectArea(subjectArea);
                courseJointDisplay.setCourseNumberSuffix(numberSuffix);
                courseJoints.add(courseJointDisplay);
            }
        } catch (Exception e) {
            FormattedLogger.error("An error occurred retrieving the courseJointDisplay: " + e);
        }
		
		return courseJoints;
	}
	
	/**
	 * Returns the CourseJointInfoDisplay object for the specified course code.
	 * @param courseCode The entire course code should be passed.
	 * @return Only 1 CourseJointInfoDisplay result is expected and will to be returned.
	 */
	public CourseJointInfoWrapper getJointOfferingCourse(String courseCode) {
	    CourseJointInfoWrapper courseJointInfo = null;
		
		List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();
		
		SearchParamInfo codeParam = new SearchParamInfo();
        codeParam.setKey(LookupableConstants.OPTIONAL_CODE_PARAM);
        List<String> codeValues = new ArrayList<String>();
        codeValues.add(courseCode);
        codeParam.setValues(codeValues);
        
        SearchParamInfo typeParam = new SearchParamInfo();
        typeParam.setKey(LookupableConstants.OPTIONAL_TYPE_PARAM);
        List<String> typeValues = new ArrayList<String>();
        typeValues.add(LookupableConstants.CREDITCOURSE_lU);
        typeParam.setValues(typeValues);
        
        queryParamValueList.add(codeParam);
        queryParamValueList.add(typeParam);
        
        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey(LookupableConstants.CURRENT_QUICK_SEARCH);
        searchRequest.setParams(queryParamValueList);
        
        SearchResultInfo searchResult = null;
        try {
        	searchResult = getCluService().search(searchRequest, ContextUtils.getContextInfo());
        	//Only 1 item should be retrieved in this search
        	if (searchResult.getRows().size() == 1) {
        		SearchResultRowInfo result = searchResult.getRows().get(0);
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
                courseJointInfo = new CourseJointInfoWrapper();
                courseJointInfo.setCourseId(id);
                courseJointInfo.setCourseCode(code);
                String subjectArea = code.replaceAll("\\d", "");
                String numberSuffix = code.replaceAll("\\D", "");
                courseJointInfo.setSubjectArea(subjectArea);
                courseJointInfo.setCourseNumberSuffix(numberSuffix);
                
        	} else {
        		FormattedLogger.error("The getJointOfferingCourse method has returned more than 1 result.");
        	}
        	
        } catch (Exception e) {
        	FormattedLogger.error("An error occurred in getJointOfferingCourse.", e);
        }
		
		return courseJointInfo;
	}
	
	public List<LoCategoryInfo> getLoCategoriesForSuggest(String categoryName) {
        List<LoCategoryInfo> retrievedCategories = new ArrayList<LoCategoryInfo>();

        List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();

        SearchParamInfo categoryNameParam = new SearchParamInfo();
        categoryNameParam.setKey(LookupableConstants.OPTIONAL_LO_CATEGORY_NAME_PARAM);
        List<String> categoryNameValues = new ArrayList<String>();
        categoryNameValues.add(categoryName);
        categoryNameParam.setValues(categoryNameValues);

        queryParamValueList.add(categoryNameParam);

        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey(LookupableConstants.LOCATEGORY_SEARCH);
        searchRequest.setParams(queryParamValueList);
        searchRequest.setSortColumn(LookupableConstants.LO_CATEGORY_NAME_RESULT);

        try {
            SearchResultInfo searchResult = getLearningObjectiveService().search(searchRequest,
                    ContextUtils.getContextInfo());
            for (SearchResultRowInfo result : searchResult.getRows()) {
                List<SearchResultCellInfo> cells = result.getCells();
                LoCategoryInfo newCat = new LoCategoryInfo();
                for (SearchResultCellInfo cell : cells) {
                    if (LookupableConstants.LO_CATEGORY_ID_RESULT.equals(cell.getKey())) {
                        newCat.setId(cell.getValue());
                    } else if (LookupableConstants.LO_CATEGORY_NAME_RESULT.equals(cell.getKey())) {
                        newCat.setName(cell.getValue());
                    }
                }
                retrievedCategories.add(newCat);
            }
        } catch (Exception e) {
            FormattedLogger.error("An error occurred in getLoCategoriesForSuggest.", e);
        }

        return retrievedCategories;
    }
	
	private SearchService getSearchService() {
		if (searchService == null) {
			searchService = GlobalResourceLoader.getService(new QName(LookupableConstants.NAMESPACE_PERSONSEACH, LookupableConstants.PERSONSEACH_SERVICE_NAME_LOCAL_PART));
		}
		return searchService;
	}
	
	private SubjectCodeService getSubjectCodeService() {
		if (subjectCodeService == null) {
			subjectCodeService = GlobalResourceLoader.getService(new QName(LookupableConstants.NAMESPACE_SUBJECTCODE, SubjectCodeService.class.getSimpleName()));
		}
		return subjectCodeService;
	}	
	
	private CluService getCluService() {
		if (cluService == null) {
			cluService = GlobalResourceLoader.getService(new QName(CluServiceConstants.CLU_NAMESPACE, CluService.class.getSimpleName()));
		}
		return cluService;
	}	
	
	private LearningObjectiveService getLearningObjectiveService() {
        if (learningObjectiveService == null) {
            learningObjectiveService = GlobalResourceLoader.getService(new QName(
                    LearningObjectiveServiceConstants.NAMESPACE, LearningObjectiveService.class.getSimpleName()));
        }
        return learningObjectiveService;
    }
	
}
