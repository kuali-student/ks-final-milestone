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
 * Created by David Yin on 6/27/12
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.inquiry.InquirableImpl;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingCrossListingInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.OfferingInstructorWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.OrganizationInfoWrapper;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.course.service.assembler.CourseAssemblerConstants;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class CourseOfferingEditInquirableImpl extends InquirableImpl {
    private CourseOfferingService courseOfferingService;
    private CourseService courseService;
    private LRCService lrcService;
    private OrganizationService organizationService;
    private transient AcademicCalendarService acalService;

    @Override
    public Object retrieveDataObject(Map<String, String> parameters) {
        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();

        String coInfoId = parameters.get("coInfo.id");
        if(coInfoId == null || "".equals(coInfoId)){
            coInfoId = parameters.get("id");
        }
        if(coInfoId == null || "".equals(coInfoId)){
            coInfoId = parameters.get("currentCourseOfferingWrapper.courseOfferingId");
        }
        if(coInfoId == null || "".equals(coInfoId)){
            coInfoId = parameters.get("courseOfferingId");
        }
        //ResultValuesGroup rvGroup = null;

        try {
            CourseOfferingInfo coInfo = getCourseOfferingService().getCourseOffering(coInfoId, contextInfo);

            //Display credit count
            CourseInfo courseInfo = getCourseService().getCourse(coInfo.getCourseId(), contextInfo);

            CourseOfferingEditWrapper formObject = new CourseOfferingEditWrapper(coInfo);

            formObject.setCourse(courseInfo);

            //Display format offering
            List<FormatOfferingInfo> formatOfferingInfos = getCourseOfferingService().getFormatOfferingsByCourseOffering(coInfoId, contextInfo);
            //List<FormatOfferingInfoWrapper> foList = new ArrayList<FormatOfferingInfoWrapper>();
            formObject.setFormatOfferingList(formatOfferingInfos);
            TermInfo term = getAcalService().getTerm(coInfo.getTermId(), contextInfo);
            formObject.setTermName(term.getName());
            /*
            for (FormatOfferingInfo formatOfferingInfo : formatOfferingInfos) {
                FormatOfferingInfoWrapper foWrapper = new FormatOfferingInfoWrapper();
                foWrapper.setFormatOfferingInfo(formatOfferingInfo);

                TypeInfo type = getTypeService().getType(formatOfferingInfo.getGradeRosterLevelTypeKey(), getContextInfo());
                //formatOfferingInfo.setGradeRosterLevelTypeKey(type.getName());
                foWrapper.setGradeRosterLevel(type.getName());

                type = getTypeService().getType(formatOfferingInfo.getFinalExamLevelTypeKey(), getContextInfo());
                foWrapper.setFinalExamLevel(type.getName());
                //formatOfferingInfo.setFinalExamLevelTypeKey(type.getName());

                //foWrapper.setName(formatOfferingInfo.getName());
                foList.add(foWrapper);
            }
            formObject.setFormatOfferings(foList);
            */

            //Display instructors
            List<OfferingInstructorInfo> offeringInstructorInfos = coInfo.getInstructors();
            List<OfferingInstructorWrapper> instructorList = new ArrayList<OfferingInstructorWrapper>();

            for (OfferingInstructorInfo offeringInstructorInfo : offeringInstructorInfos) {
                OfferingInstructorWrapper instructor = new OfferingInstructorWrapper();
                instructor.setOfferingInstructorInfo(offeringInstructorInfo);
                if (offeringInstructorInfo.getTypeKey().equals(LprServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY)) {
                    //offeringInstructorInfo.setTypeKey("Instructor");
                    instructor.setTypeName("Instructor");
                } else if (offeringInstructorInfo.getTypeKey().equals(LprServiceConstants.INSTRUCTOR_ASSISTANT_TYPE_KEY)) {
                    //offeringInstructorInfo.setTypeKey("Teaching Assistant");
                    instructor.setTypeName("Teaching Assistant");
//                } else if (offeringInstructorInfo.getTypeKey().equals(LprServiceConstants.INSTRUCTOR_SUPPORT_TYPE_KEY)) {
                    //TODO: set support here
                }
                instructorList.add(instructor);
            }
            formObject.setInstructors(instructorList);

            //Display organization name
            List<OrganizationInfoWrapper> orgList = new ArrayList<OrganizationInfoWrapper>();
            if(coInfo.getUnitsDeploymentOrgIds() != null){
                for(String orgId: coInfo.getUnitsDeploymentOrgIds()){
                    OrgInfo orgInfo = getOrganizationService().getOrg(orgId, contextInfo);
                    orgList.add(new OrganizationInfoWrapper(orgInfo));
                }
            }
            formObject.setOrganizationNames(orgList);

            //formObject.setCoInfo(coInfo);

            //Display grading options
            /*
            String gradingOptId = coInfo.getGradingOptionId();
            if (gradingOptId != null && !gradingOptId.isEmpty()) {
                rvGroup = getLRCService().getResultValuesGroup(coInfo.getGradingOptionId(), getContextInfo());
                formObject.setSelectedGradingOptionName(rvGroup.getName());
            }*/

            //Display student registration options
            /*List<String> studentRegOptIds = coInfo.getStudentRegistrationOptionIds();
            String selectedStudentRegOpts = new String();
            ResultValuesGroup rvGroup = null;
            if (studentRegOptIds != null && !studentRegOptIds.isEmpty()) {
                for (String studentRegOptId: coInfo.getStudentRegistrationOptionIds()) {
                    rvGroup = getLRCService().getResultValuesGroup(studentRegOptId, getContextInfo());
                    selectedStudentRegOpts = selectedStudentRegOpts + rvGroup.getName() + "; ";
                }
                selectedStudentRegOpts = selectedStudentRegOpts.substring(0, selectedStudentRegOpts.length() - 2);
                formObject.setSelectedStudentRegOpts(selectedStudentRegOpts);
            } */

            List<String> studentRegOptions = new ArrayList<String>();
            List<String> crsGradingOptions = new ArrayList<String>();
            if (coInfo.getCourseId() != null && courseInfo != null) {
                List<String> gradingOptions = courseInfo.getGradingOptions();
                Set<String> regOpts = new HashSet<String>(Arrays.asList(CourseOfferingServiceConstants.ALL_STUDENT_REGISTRATION_OPTION_TYPE_KEYS));
                for (String gradingOption : gradingOptions) {
                    if (regOpts.contains(gradingOption)) {
                        studentRegOptions.add(gradingOption);
                    } else {
                        crsGradingOptions.add(gradingOption);
                    }
                }
                //Audit is pulled out into a dynamic attribute on course so map it back
                if("true".equals(courseInfo.getAttributeValue(CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_AUDIT))){
                    studentRegOptions.add(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT);
                }
            }

            formObject.setStudentRegOptions(studentRegOptions);
            formObject.setCrsGradingOptions(crsGradingOptions);

            //TODO - please comment what the following section of code is supposed to do!
            String selectedStudentRegOpts = "";
            if (studentRegOptions != null) {
                ResultValuesGroupInfo rvg;
                StringBuilder sbStudentRegOpts = new StringBuilder();
                for(String studentGradingOption : studentRegOptions) {
                    rvg = getLRCService().getResultValuesGroup(studentGradingOption, contextInfo);
                    if (null != rvg) {
                        sbStudentRegOpts.append(rvg.getName());
                    } else {
                        sbStudentRegOpts.append(studentGradingOption);
                    }
                    sbStudentRegOpts.append("; ");
                }
                selectedStudentRegOpts = sbStudentRegOpts.toString();
            }
            if (selectedStudentRegOpts.isEmpty()) {
                selectedStudentRegOpts = CourseOfferingConstants.COURSEOFFERING_TEXT_STD_REG_OPTS_EMPTY;
            }
            else {
                selectedStudentRegOpts = selectedStudentRegOpts.substring(0, selectedStudentRegOpts.length() - 2);
            }
            formObject.setSelectedStudentRegOpts(selectedStudentRegOpts);

            //Display the long version final exam type, comment out for now because we will use the short version for the performance concern
            /*
            List<EnumeratedValueInfo> enumerationInfos = (List<EnumeratedValueInfo> ) getEnumerationManagementService().getEnumeratedValues("kuali.lu.finalExam.status", null, null, null);
            String tempFinalExamType = null;
            if (coInfo.getFinalExamType().equals("STANDARD")) {
                tempFinalExamType = "STD";
            } else if (coInfo.getFinalExamType().equals("ALTERNATE")) {
                tempFinalExamType = "ALT";
            } else if (coInfo.getFinalExamType().equals("NONE")) {
                tempFinalExamType = "None";
            }
            for(EnumeratedValueInfo enumerationInfo : enumerationInfos) {
                if (tempFinalExamType.equals(enumerationInfo.getCode())) {
                    formObject.setFinalExam(enumerationInfo.getValue());
                    break;
                }
            }
            */

            /**
             * Sets the cross listed infos
             */
            for (CourseOfferingCrossListingInfo crossListingInfo : coInfo.getCrossListings()){
                formObject.getAlternateCOCodes().add(crossListingInfo.getCode());
            }
            return formObject;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public CourseOfferingService getCourseOfferingService() {
        if(courseOfferingService == null)
            courseOfferingService= CourseOfferingResourceLoader.loadCourseOfferingService();
        return courseOfferingService;
    }

    protected CourseService getCourseService() {
        if(courseService == null) {
            courseService = (CourseService) GlobalResourceLoader.getService(new QName(CourseServiceConstants.COURSE_NAMESPACE, "CourseService"));
        }
        return this.courseService;
    }

    protected LRCService getLRCService() {
        if(lrcService == null) {
            lrcService = (LRCService) GlobalResourceLoader.getService(new QName(LrcServiceConstants.NAMESPACE, LrcServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.lrcService;
    }

//    private static TypeService getTypeService() {
//        if(typeService == null) {
//            typeService = CourseOfferingResourceLoader.loadTypeService();
//        }
//
//        return typeService;
//    }

    private OrganizationService getOrganizationService(){
        if(organizationService == null) {
            organizationService = (OrganizationService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "organization", "OrganizationService"));
        }
        return organizationService;
    }
    public AcademicCalendarService getAcalService() {
        if(acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.acalService;
    }

}
