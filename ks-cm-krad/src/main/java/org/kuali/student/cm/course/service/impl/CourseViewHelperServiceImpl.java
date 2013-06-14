/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
import org.kuali.student.cm.course.form.CluInstructorInfoDisplay;
import org.kuali.student.cm.course.form.CourseJointInfoDisplay;
import org.kuali.student.cm.course.form.SubjectCodeDisplay;
import org.kuali.student.r1.core.subjectcode.service.SubjectCodeService;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.lum.clu.service.CluService;
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

	public List<CluInstructorInfoDisplay> getInstructorsForSuggest(
			String instructorName) {
		List<CluInstructorInfoDisplay> cluInstructorInfoDisplays = new ArrayList<CluInstructorInfoDisplay>();
		
		List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();
        
        SearchParamInfo displayNameParam = new SearchParamInfo();
        displayNameParam.setKey("person.queryParam.personGivenName");
        displayNameParam.getValues().add(instructorName);
        queryParamValueList.add(displayNameParam);
        
    	SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey("person.search.personQuickViewByGivenName");
        searchRequest.setParams(queryParamValueList);
        searchRequest.setStartAt(0);
        searchRequest.setMaxResults(10);
        searchRequest.setNeededTotalResults(false);
        searchRequest.setSortColumn("person.resultColumn.DisplayName");
        
        SearchResultInfo searchResult = null;
        try {
        	searchResult = getSearchService().search(searchRequest, ContextUtils.getContextInfo());
        	for (SearchResultRowInfo result : searchResult.getRows()) {
                List<SearchResultCellInfo> cells = result.getCells();
                CluInstructorInfoDisplay cluInstructorInfoDisplay = new CluInstructorInfoDisplay();
                for (SearchResultCellInfo cell : cells) {
                    if ("person.resultColumn.GivenName".equals(cell.getKey())) {
                    	cluInstructorInfoDisplay.setGivenName(cell.getValue());
                    } else if ("person.resultColumn.PersonId".equals(cell.getKey())) {
                    	cluInstructorInfoDisplay.setPersonId(cell.getValue());
                    } else if ("person.resultColumn.EntityId".equals(cell.getKey())) {
                    	cluInstructorInfoDisplay.setId(cell.getValue());
                    } else if ("person.resultColumn.PrincipalName".equals(cell.getKey())) {
                    	cluInstructorInfoDisplay.setPrincipalName(cell.getValue());
                    } else if ("person.resultColumn.DisplayName".equals(cell.getKey())) {
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
	
	public List<SubjectCodeDisplay> getSubjectCodesForSuggest(String subjectCode) {
        List<SubjectCodeDisplay> retrievedCodes = new ArrayList<SubjectCodeDisplay>();

		List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();
        
        SearchParamInfo codeParam = new SearchParamInfo();
        codeParam.setKey("subjectCode.queryParam.code");
        List<String> codeValues = new ArrayList<String>();
        codeValues.add(subjectCode);
        codeParam.setValues(codeValues);
        
        queryParamValueList.add(codeParam);

        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey("subjectCode.search.subjectCodeGeneric");
        searchRequest.setParams(queryParamValueList);
        
        SearchResultInfo searchResult = null;
        try {
        	searchResult = getSubjectCodeService().search(searchRequest, ContextUtils.getContextInfo());
            for (SearchResultRowInfo result : searchResult.getRows()) {
                List<SearchResultCellInfo> cells = result.getCells();
                String id = "";
                String code = "";
                for (SearchResultCellInfo cell : cells) {
                	if ("subjectCode.resultColumn.id".equals(cell.getKey())) {
                		id = cell.getValue();
                	}
                	if ("subjectCode.resultColumn.code".equals(cell.getKey())) {
                		code = cell.getValue();
                	}
                }
                retrievedCodes.add(new SubjectCodeDisplay(id, code));
            }
        } catch (Exception e) {
            //do nothing
        }

        return retrievedCodes;
    }
	
	public List<CourseJointInfoDisplay> getJointOfferingCourseNumbersForSuggest(String courseNumber) {
		List<CourseJointInfoDisplay> courseJoints = new ArrayList<CourseJointInfoDisplay>();
		
		List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();
		
		SearchParamInfo codeParam = new SearchParamInfo();
        codeParam.setKey("lu.queryParam.luOptionalCode");
        List<String> codeValues = new ArrayList<String>();
        codeValues.add(courseNumber);
        codeParam.setValues(codeValues);
        
        SearchParamInfo typeParam = new SearchParamInfo();
        typeParam.setKey("lu.queryParam.luOptionalType");
        List<String> typeValues = new ArrayList<String>();
        typeValues.add("kuali.lu.type.CreditCourse");
        typeParam.setValues(typeValues);
        
        queryParamValueList.add(codeParam);
        queryParamValueList.add(typeParam);
        
        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey("lu.search.current.quick");
        searchRequest.setParams(queryParamValueList);
        
        SearchResultInfo searchResult = null;
        try {
        	searchResult = getCluService().search(searchRequest, ContextUtils.getContextInfo());
            for (SearchResultRowInfo result : searchResult.getRows()) {
                List<SearchResultCellInfo> cells = result.getCells();
                String id = "";
                String code = "";
                for (SearchResultCellInfo cell : cells) {
                	if ("lu.resultColumn.cluId".equals(cell.getKey())) {
                		id = cell.getValue();
                	}
                	if ("lu.resultColumn.luOptionalCode".equals(cell.getKey())) {
                		code = cell.getValue();
                	}
                }
                CourseJointInfoDisplay courseJointDisplay = new CourseJointInfoDisplay();
                courseJointDisplay.setCourseId(id);
                courseJointDisplay.setCourseCode(code);
                String subjectArea = code.replaceAll("\\d", "");
                String numberSuffix = code.replaceAll("\\D", "");
                courseJointDisplay.setSubjectArea(subjectArea);
                courseJointDisplay.setCourseNumberSuffix(numberSuffix);
                courseJoints.add(courseJointDisplay);
            }
        } catch (Exception e) {
            //do nothing
        }
		
		return courseJoints;
	}
	
	private SearchService getSearchService() {
		if (searchService == null) {
			searchService = GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/personsearch", "PersonSearchService"));
		}
		return searchService;
	}
	
	private SubjectCodeService getSubjectCodeService() {
		if (subjectCodeService == null) {
			subjectCodeService = GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/subjectCode", SubjectCodeService.class.getSimpleName()));
		}
		return subjectCodeService;
	}	
	
	private CluService getCluService() {
		if (cluService == null) {
			cluService = GlobalResourceLoader.getService(new QName(CluServiceConstants.CLU_NAMESPACE, CluService.class.getSimpleName()));
		}
		return cluService;
	}	

}
