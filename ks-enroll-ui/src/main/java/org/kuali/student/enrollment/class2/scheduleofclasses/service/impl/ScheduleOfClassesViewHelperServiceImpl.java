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
 * Created by vgadiyak on 9/10/12
 */
package org.kuali.student.enrollment.class2.scheduleofclasses.service.impl;

import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.enrollment.class2.scheduleofclasses.form.ScheduleOfClassesSearchForm;
import org.kuali.student.enrollment.class2.scheduleofclasses.service.ScheduleOfClassesViewHelperService;
import org.kuali.student.enrollment.class2.scheduleofclasses.util.ScheduleOfClassesConstants;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingDisplayInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class ScheduleOfClassesViewHelperServiceImpl extends ViewHelperServiceImpl implements ScheduleOfClassesViewHelperService {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ScheduleOfClassesViewHelperServiceImpl.class);

    private CourseOfferingService coService;
    private LprService lprService;

    public void loadCourseOfferingsByTermAndCourseCode (String termId, String courseCode, ScheduleOfClassesSearchForm form) throws Exception{

        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        // Building a query
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.and(
                PredicateFactory.like("courseOfferingCode", courseCode + "%"),
                PredicateFactory.equalIgnoreCase("atpId", termId)));
        QueryByCriteria criteria = qbcBuilder.build();
        List<String> courseOfferingIds = getCourseOfferingService().searchForCourseOfferingIds(criteria, contextInfo);

        if(courseOfferingIds.size() > 0){
            form.getCoDisplayInfoList().clear();
            List<CourseOfferingDisplayInfo> coDisplayInfoList = getCourseOfferingService().getCourseOfferingDisplaysByIds(courseOfferingIds, contextInfo);
            form.setCoDisplayInfoList(coDisplayInfoList);
        } else {
            LOG.error("Error: Can't find any Course Offering for a Course Code: " + courseCode + " in term: " + termId);
            GlobalVariables.getMessageMap().putError("Term & courseCode", ScheduleOfClassesConstants.SOC_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "courseCode", courseCode, termId);
            form.getCoDisplayInfoList().clear();
        }
    }

    @Override
    public void loadCourseOfferingsByTermAndInstructor(String termId, String instructorId, ScheduleOfClassesSearchForm form) throws Exception {

        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        //this is a cross service search between LPR and LUI, so it is inefficient (no join)
        //First get all the luiIds that the instructor is teaching
        //TODO TENTATIVE_STATE_KEY should be active in the code below, but it is hardcoded as such
        List<String> luiIds = getLprService().getLuiIdsByPersonAndTypeAndState(instructorId, LprServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY, LprServiceConstants.TENTATIVE_STATE_KEY, contextInfo);

        List<String> courseOfferingIds = null;

        if(luiIds != null && !luiIds.isEmpty()){
            //Now find all the COs with Aos that are attached to that instructor.

            // Build a query
            QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
            qbcBuilder.setPredicates(PredicateFactory.and(
                    PredicateFactory.in("aoid", luiIds.toArray()),
                    PredicateFactory.equalIgnoreCase("atpId", termId)));
            QueryByCriteria criteria = qbcBuilder.build();
            courseOfferingIds = getCourseOfferingService().searchForCourseOfferingIds(criteria, contextInfo);

            if(courseOfferingIds.size() > 0){
                form.getCoDisplayInfoList().clear();
                List<CourseOfferingDisplayInfo> coDisplayInfoList = getCourseOfferingService().getCourseOfferingDisplaysByIds(courseOfferingIds, contextInfo);
                form.setCoDisplayInfoList(coDisplayInfoList);
            }
        }

        //If nothing was found then error
        if(courseOfferingIds == null || courseOfferingIds.isEmpty()) {
            LOG.error("Error: Can't find any Course Offering for selected Instructor in term: " + termId);
            GlobalVariables.getMessageMap().putError("Term & Instructor", ScheduleOfClassesConstants.SOC_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "instructor", instructorId, termId);
            form.getCoDisplayInfoList().clear();
        }
    }

    private CourseOfferingService getCourseOfferingService() {
        if (coService == null) {
            coService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE,
                    CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return coService;
    }

    private LprService getLprService() {
        if (lprService == null) {
            lprService = (LprService) GlobalResourceLoader.getService(new QName(LprServiceConstants.NAMESPACE,
                    LprServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return lprService;
    }

}