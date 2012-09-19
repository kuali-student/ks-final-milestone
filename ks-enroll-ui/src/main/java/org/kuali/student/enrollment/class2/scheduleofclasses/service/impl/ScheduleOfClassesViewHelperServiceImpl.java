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
import org.kuali.student.enrollment.class2.scheduleofclasses.dto.ActivityOfferingDisplayWrapper;
import org.kuali.student.enrollment.class2.scheduleofclasses.dto.CourseOfferingDisplayWrapper;
import org.kuali.student.enrollment.class2.scheduleofclasses.form.ScheduleOfClassesSearchForm;
import org.kuali.student.enrollment.class2.scheduleofclasses.service.ScheduleOfClassesViewHelperService;
import org.kuali.student.enrollment.class2.scheduleofclasses.util.ScheduleOfClassesConstants;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingDisplayInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingDisplayInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

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
            form.getCoDisplayWrapperList().clear();
            List<CourseOfferingDisplayInfo> coDisplayInfoList = getCourseOfferingService().getCourseOfferingDisplaysByIds(courseOfferingIds, contextInfo);
            List<CourseOfferingDisplayWrapper> coDisplayWrapperList = new ArrayList<CourseOfferingDisplayWrapper>();
            for (CourseOfferingDisplayInfo coDisplayInfo : coDisplayInfoList) {
                CourseOfferingDisplayWrapper coDisplayWrapper = new CourseOfferingDisplayWrapper();
                coDisplayWrapper.setCoDisplayInfo(coDisplayInfo);

                // Have to use CourseOfferingInfo
                CourseOfferingInfo coInfo = getCourseOfferingService().getCourseOffering(coDisplayInfo.getId(), contextInfo);
                String information = "";
                if (coInfo.getIsHonorsOffering() != null && coInfo.getIsHonorsOffering()) {
                    information = "<img src=\"../ks-enroll/images/h.png\" title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_HONORS_COURSE + "\"> ";
                }
                if (coInfo.getGradingOptionId() != null && coInfo.getGradingOptionId().equals(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL)) {
                    information = information + "<img src=\"../ks-enroll/images/p.png\" title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_GRADING_PASSFAIL + "\">";
                } else if (coInfo.getGradingOptionId() != null && coInfo.getGradingOptionId().equals(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT)) {
                    information = information + "<img src=\"../ks-enroll/images/a.png\" title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_GRADING_AUDIT + "\">";
                }
                coDisplayWrapper.setInformation(information);

                coDisplayWrapperList.add(coDisplayWrapper);
            }
            form.setCoDisplayWrapperList(coDisplayWrapperList);
        } else {
            LOG.error("Error: Can't find any Course Offering for a Course Code: " + courseCode + " in term: " + termId);
            GlobalVariables.getMessageMap().putError("Term & courseCode", ScheduleOfClassesConstants.SOC_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "courseCode", courseCode, termId);
            form.getCoDisplayWrapperList().clear();
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
                form.getCoDisplayWrapperList().clear();
                List<CourseOfferingDisplayInfo> coDisplayInfoList = getCourseOfferingService().getCourseOfferingDisplaysByIds(courseOfferingIds, contextInfo);
                List<CourseOfferingDisplayWrapper> coDisplayWrapperList = new ArrayList<CourseOfferingDisplayWrapper>();
                for (CourseOfferingDisplayInfo coDisplayInfo : coDisplayInfoList) {
                    CourseOfferingDisplayWrapper coDisplayWrapper = new CourseOfferingDisplayWrapper();
                    coDisplayWrapper.setCoDisplayInfo(coDisplayInfo);

                    // Have to use CourseOfferingInfo
                    CourseOfferingInfo coInfo = getCourseOfferingService().getCourseOffering(coDisplayInfo.getId(), contextInfo);
                    String information = "";
                    if (coInfo.getIsHonorsOffering() != null && coInfo.getIsHonorsOffering()) {
                        information = "<img src=\"../ks-enroll/images/h.png\" title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_HONORS_COURSE + "\"> ";
                    }
                    if (coInfo.getGradingOptionId() != null && coInfo.getGradingOptionId().equals(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL)) {
                        information = information + "<img src=\"../ks-enroll/images/p.png\" title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_GRADING_PASSFAIL + "\">";
                    } else if (coInfo.getGradingOptionId() != null && coInfo.getGradingOptionId().equals(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT)) {
                        information = information + "<img src=\"../ks-enroll/images/a.png\" title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_GRADING_AUDIT + "\">";
                    }
                    coDisplayWrapper.setInformation(information);

                    coDisplayWrapperList.add(coDisplayWrapper);
                }
                form.setCoDisplayWrapperList(coDisplayWrapperList);
            }
        }

        //If nothing was found then error
        if(courseOfferingIds == null || courseOfferingIds.isEmpty()) {
            LOG.error("Error: Can't find any Course Offering for selected Instructor in term: " + termId);
            GlobalVariables.getMessageMap().putError("Term & Instructor", ScheduleOfClassesConstants.SOC_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "instructor", instructorId, termId);
            form.getCoDisplayWrapperList().clear();
        }
    }

    public void loadActivityOfferingsByCourseOfferingId(String courseOfferingId, ScheduleOfClassesSearchForm form) throws Exception {

        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        List<ActivityOfferingDisplayWrapper> aoDisplayWrapperList = new ArrayList<ActivityOfferingDisplayWrapper>();
        List<ActivityOfferingDisplayInfo> aoDisplayInfoList = getCourseOfferingService().getActivityOfferingDisplaysForCourseOffering(courseOfferingId, contextInfo);

        for (ActivityOfferingDisplayInfo aoDisplayInfo : aoDisplayInfoList) {
            ActivityOfferingDisplayWrapper aoDisplayWrapper = new ActivityOfferingDisplayWrapper();
            aoDisplayWrapper.setAoDisplayInfo(aoDisplayInfo);
            // ToDo: aoDisplayWrapper.setInformation(information);
            aoDisplayWrapperList.add(aoDisplayWrapper);
        }

        if(!aoDisplayWrapperList.isEmpty()) {
            List<CourseOfferingDisplayWrapper> coDisplayWrapperList = form.getCoDisplayWrapperList();
            for (int i=0; i<coDisplayWrapperList.size(); i++) {
                CourseOfferingDisplayWrapper coDisplayWrapper = coDisplayWrapperList.get(i);
                if(coDisplayWrapper.getCoDisplayInfo().getId().equals(courseOfferingId)) {
                    coDisplayWrapper.setAoDisplayWrapperList(aoDisplayWrapperList);
                    coDisplayWrapperList.set(i, coDisplayWrapper);
                    break;
                }
            }
            form.setCoDisplayWrapperList(coDisplayWrapperList);
        }
    }

    public void loadCourseOfferingsByOrganizationId(String termId, String organizationId, ScheduleOfClassesSearchForm form) throws Exception{
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();


        qbcBuilder.setPredicates(PredicateFactory.and(
                PredicateFactory.equal("luiContentOwner", organizationId),
                PredicateFactory.equal("atpId", termId),
                PredicateFactory.equal("luiType", LuiServiceConstants.COURSE_OFFERING_TYPE_KEY)));
        QueryByCriteria criteria = qbcBuilder.build();
        List<String> courseOfferingIds = getCourseOfferingService().searchForCourseOfferingIds(criteria, contextInfo);

        if(courseOfferingIds.size() > 0){
            form.getCoDisplayWrapperList().clear();
            List<CourseOfferingDisplayInfo> coDisplayInfoList = getCourseOfferingService().getCourseOfferingDisplaysByIds(courseOfferingIds, contextInfo);
            List<CourseOfferingDisplayWrapper> coDisplayWrapperList = new ArrayList<CourseOfferingDisplayWrapper>();
            for (CourseOfferingDisplayInfo coDisplayInfo : coDisplayInfoList) {
                CourseOfferingDisplayWrapper coDisplayWrapper = new CourseOfferingDisplayWrapper();
                coDisplayWrapper.setCoDisplayInfo(coDisplayInfo);

                // Have to use CourseOfferingInfo
                CourseOfferingInfo coInfo = getCourseOfferingService().getCourseOffering(coDisplayInfo.getId(), contextInfo);
                String information = "";
                if (coInfo.getIsHonorsOffering() != null && coInfo.getIsHonorsOffering()) {
                    information = "<img src=\"../ks-enroll/images/h.png\" title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_HONORS_COURSE + "\"> ";
                }
                if (coInfo.getGradingOptionId() != null && coInfo.getGradingOptionId().equals(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL)) {
                    information = information + "<img src=\"../ks-enroll/images/p.png\" title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_GRADING_PASSFAIL + "\">";
                } else if (coInfo.getGradingOptionId() != null && coInfo.getGradingOptionId().equals(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT)) {
                    information = information + "<img src=\"../ks-enroll/images/a.png\" title=\"" + ScheduleOfClassesConstants.SOC_RESULT_PAGE_HELP_GRADING_AUDIT + "\">";
                }
                coDisplayWrapper.setInformation(information);

                coDisplayWrapperList.add(coDisplayWrapper);
            }
            form.setCoDisplayWrapperList(coDisplayWrapperList);
        }


    //If nothing was found then error
    if(courseOfferingIds == null || courseOfferingIds.isEmpty()) {
        LOG.error("Error: Can't find any Course Offering for selected Department in term: " + termId);
        GlobalVariables.getMessageMap().putError("Term & Department", ScheduleOfClassesConstants.SOC_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "department", organizationId, termId);
        form.getCoDisplayWrapperList().clear();
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