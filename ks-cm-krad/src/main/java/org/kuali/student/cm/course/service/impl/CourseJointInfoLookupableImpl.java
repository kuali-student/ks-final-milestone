/**
 * Copyright 2005-2013 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.cm.course.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.lookup.LookupableImpl;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.cm.course.form.CourseJointInfoWrapper;
import org.kuali.student.logging.FormattedLogger;
import org.kuali.student.lum.lu.ui.course.keyvalues.CourseJointKeyValuesFinder.SearchByKeys;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;

public class CourseJointInfoLookupableImpl extends LookupableImpl {

	private static final long serialVersionUID = 730938705006420306L;
	
	private CluService cluService;
	
	@Override
	protected List<?> getSearchResults(LookupForm form,
			Map<String, String> searchCriteria, boolean unbounded) {
		List<CourseJointInfoWrapper> courseJointInfoDisplays = new ArrayList<CourseJointInfoWrapper>();
		
        List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();
        SearchByKeys searchByKey = SearchByKeys.valueOf(searchCriteria.get(CourseServiceConstants.SEARCHBY_SEARCH));
        String courseTitle = searchCriteria.get(CourseServiceConstants.COURSETITLE_SEARCH);
        String subjectArea = searchCriteria.get(CourseServiceConstants.COURSECODE_SEARCH);
        String description = searchCriteria.get(CourseServiceConstants.DESCR_PLAIN_SEARCH);
        
        if (StringUtils.isNotBlank(courseTitle)) {
            SearchParamInfo courseTitleParam = new SearchParamInfo();
            courseTitleParam.setKey(CourseServiceConstants.OPTIONAL_LONGNAME_PARAM);
            courseTitleParam.getValues().add(courseTitle);
            queryParamValueList.add(courseTitleParam);
        }
        if (StringUtils.isNotBlank(subjectArea)) {
            SearchParamInfo courseCodeParam = new SearchParamInfo();
            courseCodeParam.setKey(CourseServiceConstants.OPTIONAL_CODE_PARAM);
            courseCodeParam.getValues().add(subjectArea);
            queryParamValueList.add(courseCodeParam);
        }
        if (StringUtils.isNotBlank(description)) {
            SearchParamInfo descriptionParam = new SearchParamInfo();
            descriptionParam.setKey(CourseServiceConstants.OPTIONAL_DESCR_PARAM);
            descriptionParam.getValues().add(description);
            queryParamValueList.add(descriptionParam);
        }
        
        SearchParamInfo typeParam = new SearchParamInfo();
        typeParam.setKey(CourseServiceConstants.OPTIONAL_TYPE_PARAM);
        typeParam.getValues().add(CluServiceConstants.CREDIT_COURSE_LU_TYPE_KEY);
        
        SearchParamInfo stateParam = new SearchParamInfo();
        stateParam.setKey(CourseServiceConstants.OPTIONAL_STATE_PARAM);
        List<String> states = new ArrayList<String>();
        switch (searchByKey) {
	        case COURSES_AND_PROPOSALS:
	        	states.add("Draft");
	            states.add("Submitted");
	            states.add("Withdrawn");
	            states.add("Approved");
	            states.add("Active");
	            break;
	        case COURSES_ONLY:
	        	states.add("Active");
	        	states.add("Approved");
	        	states.add("Retired");
	        	states.add("Suspended");
	        	break;
	        case PROPOSALS_ONLY:
	        	states.add("Draft");
    			states.add("Submitted");
				states.add("Withdrawn");
				states.add("Approved");
        }
        
        stateParam.setValues(states);
        queryParamValueList.add(stateParam);

        String searchKey = "";
        SearchRequestInfo searchRequest = new SearchRequestInfo();
        switch (searchByKey) {
        	case COURSES_AND_PROPOSALS:
        		searchKey = CourseServiceConstants.GENERIC_LU_SEARCH;
        		break;
        	case COURSES_ONLY:
        	case PROPOSALS_ONLY:
        		searchKey = CourseServiceConstants.MOSTCURRENT_UNION_SEARCH;
        }
        searchRequest.setSearchKey(searchKey);
        searchRequest.setParams(queryParamValueList);
        SearchResultInfo clus = null;
        try {
            clus = getCluService().search(searchRequest, ContextUtils.getContextInfo());
            for (SearchResultRowInfo result : clus.getRows()) {
                List<SearchResultCellInfo> cells = result.getCells();
                CourseJointInfoWrapper courseJointInfoDisplay = new CourseJointInfoWrapper();
                for (SearchResultCellInfo cell : cells) {
                    if (CourseServiceConstants.ID_RESULT.equals(cell.getKey())) {
                    	courseJointInfoDisplay.setCourseId(cell.getValue());
                    } else if (CourseServiceConstants.LONGNAME_OPTIONAL_RESULT.equals(cell.getKey())) {
                    	courseJointInfoDisplay.setName(cell.getValue());
                    } else if (CourseServiceConstants.OPTIONALCODE_RESULT.equals(cell.getKey())) {
                    	courseJointInfoDisplay.setCourseCode(cell.getValue());
                    }
                    else if (CourseServiceConstants.DESCR_RESULT.equals(cell.getKey())) {
                    	RichTextInfo descr = new RichTextInfo();
                    	descr.setPlain(cell.getValue());
                    	courseJointInfoDisplay.setDescr(descr);
                    }
                }
                courseJointInfoDisplays.add(courseJointInfoDisplay);
            }
        } catch (Exception e) {
            FormattedLogger.error("An error occurred retrieving the courseJointInfoDisplay: " + e);
        }
        return courseJointInfoDisplays;
	}	
	
	private CluService getCluService() {
		if (cluService == null) {
			cluService = GlobalResourceLoader.getService(new QName(CluServiceConstants.CLU_NAMESPACE, CluService.class.getSimpleName()));
		}
		return cluService;
	}

}
