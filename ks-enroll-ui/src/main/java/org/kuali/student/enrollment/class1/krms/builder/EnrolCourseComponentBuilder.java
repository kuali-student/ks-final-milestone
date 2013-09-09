/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class1.krms.builder;

import org.apache.commons.lang.BooleanUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.lum.lu.ui.krms.builder.CourseComponentBuilder;
import org.kuali.student.lum.lu.ui.krms.dto.LUPropositionEditor;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.class1.search.CourseOfferingManagementSearchImpl;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kuali Student Team
 */
public class EnrolCourseComponentBuilder extends CourseComponentBuilder {

    private final static Logger LOG = Logger.getLogger(EnrolCourseComponentBuilder.class);

    @Override
    public Map<String, String> buildTermParameters(LUPropositionEditor propositionEditor) {
        Map<String, String> termParameters = new HashMap<String, String>();
        if (propositionEditor.getCourseInfo() != null) {
            termParameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_CLU_KEY, propositionEditor.getCourseInfo().getVersion().getVersionIndId());
        }

        if (propositionEditor.getTermInfo() != null) {
            termParameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERM_KEY, propositionEditor.getTermInfo().getId());
            termParameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERMCODE_KEY, propositionEditor.getTermCode());
            String propName = PropositionTreeUtil.getBindingPath(propositionEditor, "termCode");
            loadCourseOfferingsByTermAndCourseCode(propositionEditor.getTermInfo().getId(), propositionEditor.getCourseInfo().getCode(), propName);
        }

        if (propositionEditor.getTermInfo2() != null) {
            termParameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERM2_KEY, propositionEditor.getTermInfo2().getId());
            termParameters.put(KSKRMSServiceConstants.TERM_PARAMETER_TYPE_TERMCODE2_KEY, propositionEditor.getTermCode2());
            String propName = PropositionTreeUtil.getBindingPath(propositionEditor, "termCode2");
            loadCourseOfferingsByTermAndCourseCode(propositionEditor.getTermInfo2().getId(), propositionEditor.getCourseInfo().getCode(),propName );
        }

        return termParameters;
    }

    /**
     * Method used to
     * find THE course based on termId and courseCode. If find more than one CO or don't find
     * any CO, log and report an error message.
     *
     * @param termId
     * @param courseCode
     */
    public void loadCourseOfferingsByTermAndCourseCode(String termId, String courseCode, String propName ) {

        SearchRequestInfo searchRequest = new SearchRequestInfo(CourseOfferingManagementSearchImpl.CO_MANAGEMENT_SEARCH.getKey());
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.COURSE_CODE, courseCode);
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.ATP_ID, termId);
        searchRequest.addParam(CourseOfferingManagementSearchImpl.SearchParameters.CROSS_LIST_SEARCH_ENABLED, BooleanUtils.toStringTrueFalse(true));

        SearchResultInfo searchResult = null;
        try {
            searchResult = getSearchService().search(searchRequest, ContextUtils.getContextInfo());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (searchResult.getRows().isEmpty()) {
            LOG.error("Error: Can't find any Course Offering for a Course Code: " + courseCode + " in term: " + termId);
            GlobalVariables.getMessageMap().putError(propName, CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND,  "Course Code",courseCode, termId);
        }
    }

}
