/*
 * Copyright 2012 The Kuali Foundation
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
package org.kuali.student.r2.common.krms.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.kuali.student.common.test.mock.data.AbstractMockServicesAwareDataLoader;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentProgramRecordInfo;
import org.kuali.student.enrollment.class2.academicrecord.service.impl.AcademicRecordServiceClass2MockImpl;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingServiceTestDataUtils;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.infc.CourseOffering;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Kuali Student Team 
 *
 */
public class KRMSEnrollmentEligibilityDataLoader extends AbstractMockServicesAwareDataLoader {
    
    private static final Logger log = LoggerFactory
            .getLogger(KRMSEnrollmentEligibilityDataLoader.class);

    @Resource(name = "cluService")
    private CluService cluService;

    @Resource(name = "courseService")
    private CourseService courseService;

    @Resource(name = "courseOfferingService")
    private CourseOfferingService courseOfferingService;

    @Resource
    private AcademicRecordServiceClass2MockImpl recordService;

    @Resource
    private CourseRegistrationService registrationService;

    @Resource
    private AtpService atpService;

    public static final Date START_WINTER_TERM_DATE = new DateTime().withDate(2012, 1, 1).toDate();
    public static final Date END_WINTER_TERM_DATE = new DateTime().withDate(2012, 3, 31).toDate();
    
    public static final Date START_SPRING_TERM_DATE = new DateTime().withDate(2012, 4, 1).toDate();
    public static final Date END_SPRING_TERM_DATE = new DateTime().withDate(2012, 6, 30).toDate();

    public static final Date START_SUMMER_TERM_DATE = new DateTime().withDate(2012, 7, 1).toDate();
    public static final Date END_SUMMER_TERM_DATE = new DateTime().withDate(2012, 9, 30).toDate();
    
    public static final Date START_FALL_TERM_DATE = new DateTime().withDate(2012, 10, 1).toDate();
    public static final Date END_FALL_TERM_DATE = new DateTime().withDate(2012, 12, 31).toDate();
    
    public static final String STUDENT_ONE_ID = "student1";
    public static final String STUDENT_TWO_ID = "student2";
    public static final String STUDENT_THREE_ID = "student3";
    
    public static final String FAKE_COURSE_ID = "course1";
    public static final String FAKE_COURSE2_ID = "course2";
    public static final String FAKE_COURSE3_ID = "course3";

    private AtpInfo winterAtpInfo;
    private AtpInfo springAtpInfo;
    private AtpInfo summerAtpInfo;
    private AtpInfo fallAtpInfo;

    public ContextInfo contextInfo = null;
    
    
    /**
     * 
     */
    public KRMSEnrollmentEligibilityDataLoader() {
        super();
    }

    /* (non-Javadoc)
     * @see org.kuali.student.common.test.mock.data.AbstractMockServicesAwareDataLoader#initializeData()
     */
    @Override
    protected void initializeData() throws Exception {

        winterAtpInfo = atpService.createAtp(AtpServiceConstants.ATP_WINTER_TYPE_KEY, createTerm (AtpServiceConstants.ATP_WINTER_TYPE_KEY,
                START_WINTER_TERM_DATE, END_WINTER_TERM_DATE, "Winter 2012"), context);
        springAtpInfo = atpService.createAtp(AtpServiceConstants.ATP_SPRING_TYPE_KEY, createTerm (AtpServiceConstants.ATP_SPRING_TYPE_KEY,
                START_SPRING_TERM_DATE, END_SPRING_TERM_DATE, "Spring 2012"), context);
        summerAtpInfo = atpService.createAtp(AtpServiceConstants.ATP_SUMMER_TYPE_KEY, createTerm (AtpServiceConstants.ATP_SUMMER_TYPE_KEY,
                START_SUMMER_TERM_DATE, END_SUMMER_TERM_DATE, "Summer 2012"), context);
        fallAtpInfo = atpService.createAtp(AtpServiceConstants.ATP_FALL_TYPE_KEY, createTerm (AtpServiceConstants.ATP_FALL_TYPE_KEY,
                START_FALL_TERM_DATE, END_FALL_TERM_DATE, "Fall 2012"), context);
    }
    
    public String getWinterTermId() {
        return winterAtpInfo.getId();
    }

    public String getSpringTermId() {
        return springAtpInfo.getId();
    }

    public String getSummerTermId() {
        return summerAtpInfo.getId();
    }
    
    public String getFallTermId() {
        return fallAtpInfo.getId();
    }
    
    /**
     * Helper to create a new StudentCourseRecordInfo object built using the provided details.
     * 
     * @param studentId the student that took the course
     * @param termId the term when the course was taken
     * @param courseCode the course code of the course that was taken
     * @param courseTitle the title of the course
     * @return the non-saved new course record built using the provided details
     * @throws DoesNotExistException the term does not exist
     * @throws InvalidParameterException the
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public StudentCourseRecordInfo createStudentCourseRecord(String studentId, String termId, String courseCode, String courseTitle,
                                                             String gradeValue, String gradeScaleKey) throws DoesNotExistException, OperationFailedException {
        
        StudentCourseRecordInfo courseRecord = new StudentCourseRecordInfo();
        
        courseRecord.setCourseRegistrationId("courseRegistrationId");
        courseRecord.setPersonId(studentId);
        courseRecord.setCourseTitle(courseTitle);
        courseRecord.setCourseCode(courseCode);
        courseRecord.setActivityCode("dummyActivityCode");
        
        AtpInfo term;
        try {
            term = atpService.getAtp(termId, context);
        } catch (Exception e) {
           throw new OperationFailedException("failed to get atp with id = " + termId, e);
        } 
        
        // note this is not the term id
        courseRecord.setTermName(term.getName());
        courseRecord.setCourseBeginDate(term.getStartDate());
        courseRecord.setCourseEndDate(term.getEndDate());
        
        courseRecord.setAssignedGradeValue(gradeValue);
        courseRecord.setAssignedGradeScaleKey(gradeScaleKey);
        courseRecord.setAdministrativeGradeValue(gradeValue);
        courseRecord.setAdministrativeGradeScaleKey(gradeScaleKey);
        courseRecord.setCalculatedGradeValue("3.0");
        courseRecord.setCalculatedGradeScaleKey("1");
        courseRecord.setCreditsForGPA("3");
        courseRecord.setCreditsEarned("3");
        
        return courseRecord;
        
    }

    /**
     * Helper to create a new StudentProgramRecordInfo object built using the provided details.
     *
     * @param studentId the student that completed the program
     * @param program the program
     * @return the non-saved new course record built using the provided details
     * @throws DoesNotExistException the term does not exist
     * @throws InvalidParameterException the
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public StudentProgramRecordInfo createStudentProgramRecord(String studentId, CluInfo program) throws DoesNotExistException, OperationFailedException {

        StudentProgramRecordInfo programRecord = new StudentProgramRecordInfo();

        programRecord.setProgramId(program.getId());
        programRecord.setProgramCode(program.getOfficialIdentifier().getCode());
        programRecord.setProgramTitle(program.getOfficialIdentifier().getLongName());

        return programRecord;

    }

    /**
     * Store a new CourseRecord for the student in the term given.
     * @param studentId
     * @param termId
     * @param courseRecord
     * @throws DoesNotExistException if the term does not exist.
     * @throws OperationFailedException  if an exception occurs that prevents the execution of the method.
     */
    public void storeStudentCourseRecord (String studentId, String termId, String courseId, StudentCourseRecordInfo courseRecord) throws DoesNotExistException, OperationFailedException {
        
        try {
            
            atpService.getAtp(termId, context);
            
        } catch (Exception e) {
            
            if (e instanceof DoesNotExistException)
                throw (DoesNotExistException)e;
            else
                throw new OperationFailedException("", e);
        }
        
        recordService.storeStudentCourseRecord(studentId, termId, courseId, courseRecord);
    }

    /**
     * Store a new ProgramRecord for the student in the term given.
     * @param studentId
     * @param programId
     * @param programRecord
     * @throws DoesNotExistException if the term does not exist.
     * @throws OperationFailedException  if an exception occurs that prevents the execution of the method.
     */
    public void storeStudentProgramRecord (String studentId, String programId, StudentProgramRecordInfo programRecord) throws DoesNotExistException, OperationFailedException {
        recordService.storeStudentProgramRecord(studentId, programId, programRecord);
    }

    private AtpInfo createTerm(String atpSpringTypeKey, Date startDate, Date endDate, String name) {
        
        AtpInfo atp = new AtpInfo();
        
        atp.setTypeKey(atpSpringTypeKey);
        
        atp.setName(name);
        
        atp.setStartDate(startDate);
        atp.setEndDate(endDate);
        
        return atp;
    }

    /**
     * Helper to create a new StudentCourseRecordInfo object built using the provided details.
     *
     * @param studentId the student that took the course
     * @param termId the term when the course was taken
     * @return the non-saved new course record built using the provided details
     */
    public RegistrationRequestInfo createRegistrationRequest(String studentId, String termId) {

        RegistrationRequestInfo request = new RegistrationRequestInfo();
        request.setRequestorId(studentId);
        request.setTermId(termId);
        request.setTypeKey(LprServiceConstants.LPRTRANS_REGISTER_TYPE_KEY);
        return request;
    }

    /**
     * Helper to create a new StudentCourseRecordInfo object built using the provided details.
     *
     * @param regGroupId the student that took the course
     * @return the non-saved new course record built using the provided details
     */
    public RegistrationRequestItemInfo createRegistrationItem(String studentId, String regGroupId) {

        RegistrationRequestItemInfo requestItem = new RegistrationRequestItemInfo();
        requestItem.setStudentId(studentId);
        requestItem.setNewRegistrationGroupId(regGroupId);
        requestItem.setCredits("3.0");
        requestItem.setGradingOptionId(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_LETTER);
        requestItem.setTypeKey(LprServiceConstants.LPRTRANS_ITEM_ADD_TYPE_KEY);
        return requestItem;
    }

    /**
     * Store a new CourseRecord for the student in the term given.
     * @param request
     * @throws DoesNotExistException if the term does not exist.
     * @throws OperationFailedException  if an exception occurs that prevents the execution of the method.
     */
    public RegistrationRequestInfo persistRegistrationRequest(RegistrationRequestInfo request) throws InvalidParameterException, PermissionDeniedException, OperationFailedException, AlreadyExistsException, MissingParameterException, DoesNotExistException, ReadOnlyException, DataValidationErrorException {
        return registrationService.createRegistrationRequest(request.getTypeKey(), request, context);
    }

    public void submitRegistrationRequest(String requestId) throws InvalidParameterException, PermissionDeniedException, OperationFailedException, AlreadyExistsException, MissingParameterException, DoesNotExistException, ReadOnlyException, DataValidationErrorException {
        registrationService.submitRegistrationRequest(requestId, context);
    }

    public CourseOffering getCourseOffering(String courseId, String termId) throws Exception {
        CourseInfo course = this.getCourse(courseId);
        String coId = "CO:" + course.getId() + ":" + termId;
        CourseOfferingInfo courseOffering = null;
        try {
            courseOffering = courseOfferingService.getCourseOffering(coId, contextInfo);
        } catch (DoesNotExistException dne) {
            //Create a course offering from the course if it does not exist.
            courseOffering = CourseOfferingServiceTestDataUtils.createCourseOffering(course, termId);
            courseOffering.setId(coId);
            courseOffering = courseOfferingService.createCourseOffering(course.getId(), termId, LuiServiceConstants.COURSE_OFFERING_TYPE_KEY,
                    courseOffering, new ArrayList<String>(), contextInfo);
            courseOffering.getUnitsContentOwnerOrgIds().add("ORG1");
            RegistrationGroupInfo regGroup = new RegistrationGroupInfo();
            regGroup.setCourseOfferingId(courseOffering.getId());
            regGroup.setTypeKey("atype");
            courseOfferingService.createRegistrationGroup(regGroup.getFormatOfferingId(), regGroup.getActivityOfferingClusterId(), regGroup.getTypeKey(), regGroup, contextInfo);
        }
        return courseOffering;
    }

    public CourseInfo getCourse(String courseId) throws Exception {
        try {
            return courseService.getCourse(courseId, contextInfo);
        } catch (DoesNotExistException dne) {
            //Create a course from the cluService if it does not exist.
            CluInfo clu = cluService.getClu(courseId, contextInfo);
            CourseInfo course = new CourseInfo();
            course.setId(clu.getId());
            course.setCode(clu.getOfficialIdentifier().getCode());
            course.setCourseTitle(clu.getOfficialIdentifier().getLongName());
            course.setCourseNumberSuffix(clu.getOfficialIdentifier().getSuffixCode());
            ResultValuesGroupInfo rvg = new ResultValuesGroupInfo();
            rvg.setKey(LrcServiceConstants.RESULT_GROUP_KEY_KUALI_CREDITTYPE_CREDIT_1_0);
            course.getCreditOptions().add(rvg);

            return courseService.createCourse(course, contextInfo);
        }
    }

    public CluInfo getProgram(String programId) throws Exception {
        return cluService.getClu(programId, contextInfo);
    }

    public RegistrationGroupInfo getRegistrationGroup(String courseId, String termId) throws Exception {
        CourseOffering courseOffering = this.getCourseOffering(courseId, termId);
        List<RegistrationGroupInfo> regGroups = courseOfferingService.getRegistrationGroupsForCourseOffering(courseOffering.getId(), contextInfo);
        for (RegistrationGroupInfo regGroup : regGroups) {
            return regGroup;
        }
        return null;
    }

    public void setContextInfo(ContextInfo contextInfo) {
        this.contextInfo = contextInfo;
    }
}
