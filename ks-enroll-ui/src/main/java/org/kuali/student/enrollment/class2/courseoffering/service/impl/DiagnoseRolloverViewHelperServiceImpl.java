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
 * Created by Charles on 9/21/12
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.enrollment.class2.courseofferingset.util.CourseOfferingSetUtil;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.enrollment.class2.courseoffering.service.DiagnoseRolloverViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;

import javax.xml.namespace.QName;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides helper logic for the Diagnose Rollover ui
 *
 * @author Kuali Student Team
 */
public class DiagnoseRolloverViewHelperServiceImpl extends ViewHelperServiceImpl implements DiagnoseRolloverViewHelperService {

    public static final String COURSE_OFFERING_KEY = "courseOffering";
    public static final String START_ROLLOVER_DATE = "startRolloverDate";
    public static final String FINISH_ROLLOVER_DATE = "finishRolloverDate";
    public static final String DURATION_IN_SECONDS = "durationInSeconds";

    @Override
    public TermInfo searchTermByTermCode(String termCode) throws Exception {
        // TODO: Find sensible way to rewrap exception that acal service may throw
        // Find the term (alas, I think it does approximate search)
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.equal(CourseOfferingConstants.ATP_CODE, termCode));
        QueryByCriteria criteria = qbcBuilder.build();

        // Do search.  In ideal case, terms returns one element, which is the desired term.
        List<TermInfo> terms = CourseOfferingManagementUtil.getAcademicCalendarService().searchForTerms(criteria, new ContextInfo());
        int firstTerm = 0;
        if (terms == null || terms.isEmpty()) {
            return null;
        } else {
            return terms.get(firstTerm);
        }
    }

    @Override
    public boolean termHasCourseOfferings(TermInfo termInfo) throws Exception {
        List<String> coIds = CourseOfferingManagementUtil.getCourseOfferingService().getCourseOfferingIdsByTerm(termInfo.getId(), true, new ContextInfo());
        return coIds != null && coIds.size() > 0;
    }

    @Override
    public boolean termHasSoc(TermInfo termInfo) throws Exception {
        ContextInfo contextInfo = new ContextInfo();

        SocInfo socInfo = CourseOfferingSetUtil.getMainSocForTermId(termInfo.getId(), contextInfo);
        if (socInfo != null) {
            return true;
        }

        return false;
    }

    @Override
    public CourseOfferingInfo getCourseOfferingInfo(String termId, String courseOfferingCode) throws Exception {
        List<CourseOfferingInfo> coList = _searchCourseOfferingByCOCodeAndTerm(courseOfferingCode, termId);
        int firstCOInfo = 0 ;
        if (coList == null ||  coList.isEmpty()) {
            return null;
        } else {
            CourseOfferingInfo courseOfferingInfo = coList.get(firstCOInfo);
            return courseOfferingInfo; // Pick first one
        }
    }

    @Override
    public boolean termHasACourseOffering(String termId, String courseOfferingCode) throws Exception {
        List<CourseOfferingInfo> coList = _searchCourseOfferingByCOCodeAndTerm(courseOfferingCode, termId);
        boolean hasOffering = coList != null && !coList.isEmpty();
        return hasOffering;
    }

    @Override
    public boolean deleteCourseOfferingInTerm(String courseOfferingCode, String termId) throws Exception {
        List<CourseOfferingInfo> coList = _searchCourseOfferingByCOCodeAndTerm(courseOfferingCode, termId);
        if (coList == null || coList.isEmpty()) {
            return false;
        }
        int firstCOInfo = 0;
        CourseOfferingInfo coInfo = coList.get(firstCOInfo);
        StatusInfo statusInfo;
        try {
            statusInfo = CourseOfferingManagementUtil.getCourseOfferingService().deleteCourseOfferingCascaded(coInfo.getId(), new ContextInfo());
        } catch (Exception e) {
            return false;
        }
        return statusInfo != null && statusInfo.getIsSuccess();
    }

    private double _computeDiffInSeconds(Date start, Date end) {
        long diff = end.getTime() - start.getTime();
        double diffInSeconds = diff / 1000.0;
        return diffInSeconds;
    }
    
    @Override
    public Map<String, Object> rolloverCourseOfferingFromSourceTermToTargetTerm(String courseOfferingCode, String sourceTermId, String targetTermId) throws Exception {
        List<CourseOfferingInfo> coInfos = _searchCourseOfferingByCOCodeAndTerm(courseOfferingCode, sourceTermId);
        if (coInfos == null || coInfos.isEmpty()) {
            return null;
        }
        ContextInfo contextInfo = new ContextInfo();
        int firstCOInfo = 0;
        CourseOfferingInfo coInfo = coInfos.get(firstCOInfo); // Just get the first one
        Date start = new Date();
        List<String> optionKeys = CourseOfferingManagementUtil.getDefaultOptionKeysService().getDefaultOptionKeysForCopySingleCourseOffering();
        SocRolloverResultItemInfo rolloverResultInfo =
                CourseOfferingManagementUtil.getCourseOfferingService().rolloverCourseOffering(coInfo.getId(), targetTermId, optionKeys, contextInfo);
        Date end = new Date();
        String targetId = rolloverResultInfo.getTargetCourseOfferingId();
        CourseOfferingInfo targetCo = CourseOfferingManagementUtil.getCourseOfferingService().getCourseOffering(targetId, contextInfo);
        Map<String, Object> keyValues = new HashMap<String, Object>();
        keyValues.put(COURSE_OFFERING_KEY, targetCo);
        keyValues.put(START_ROLLOVER_DATE, start);
        keyValues.put(FINISH_ROLLOVER_DATE, end);
        double diff = _computeDiffInSeconds(start, end);
        keyValues.put(DURATION_IN_SECONDS, new Double(diff));
        return keyValues;
    }

    private List<CourseOfferingInfo> _searchCourseOfferingByCOCodeAndTerm(String courseOfferingCode, String termId) throws Exception {
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(
                PredicateFactory.and(
                    PredicateFactory.equal(CourseOfferingConstants.COURSEOFFERING_COURSE_OFFERING_CODE, courseOfferingCode),
                    PredicateFactory.equal(CourseOfferingConstants.ATP_ID, termId)
                ));
        QueryByCriteria criteria = qbcBuilder.build();
        List<CourseOfferingInfo> coList = CourseOfferingManagementUtil.getCourseOfferingService().searchForCourseOfferings(criteria, new ContextInfo());
        return coList;
    }
}
