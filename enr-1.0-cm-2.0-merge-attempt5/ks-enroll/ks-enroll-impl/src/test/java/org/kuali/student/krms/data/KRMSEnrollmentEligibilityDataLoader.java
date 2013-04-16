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
package org.kuali.student.krms.data;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.kuali.student.common.test.mock.data.AbstractMockServicesAwareDataLoader;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.class2.academicrecord.service.impl.AcademicRecordServiceClass2MockImpl;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.grading.service.GradingService;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.lum.lrc.service.LRCService;
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

    

    @Resource
    private AcademicRecordServiceClass2MockImpl recordService;

    @Resource
    private AtpService atpService;
    
    public static final Date START_SPRING_TERM_DATE = new DateTime().withDate(2012, 5, 1).toDate();
    public static final Date END_SPRING_TERM_DATE = new DateTime().withDate(2012, 8, 29).toDate();
    
    public static final Date START_FALL_TERM_DATE = new DateTime().withDate(2012, 9, 1).toDate();
    public static final Date END_FALL_TERM_DATE = new DateTime().withDate(2012, 12, 29).toDate();
    
    public static final String STUDENT_ONE_ID = "student1";
    
    public static final String FAKE_COURSE_ID = "course1";


    private AtpInfo springAtpInfo;



    private AtpInfo fallAtpInfo;
    
    
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
        
        springAtpInfo = atpService.createAtp(AtpServiceConstants.ATP_SPRING_TYPE_KEY, createTerm (AtpServiceConstants.ATP_SPRING_TYPE_KEY, START_SPRING_TERM_DATE, END_SPRING_TERM_DATE, "Spring 2012"), context);
        
        fallAtpInfo = atpService.createAtp(AtpServiceConstants.ATP_FALL_TYPE_KEY, createTerm (AtpServiceConstants.ATP_FALL_TYPE_KEY, START_FALL_TERM_DATE, END_FALL_TERM_DATE, "Fall 2012"), context);
     
        
    }
    
    public String getSpringTermId() {
        return springAtpInfo.getId();
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
    public StudentCourseRecordInfo createStudentCourseRecord(String studentId, String termId, String courseCode, String courseTitle) throws DoesNotExistException, OperationFailedException {
        
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
        
        courseRecord.setAssignedGradeValue("3.0");
        courseRecord.setAssignedGradeScaleKey("1");
        courseRecord.setAdministrativeGradeValue("3.0");
        courseRecord.setAdministrativeGradeScaleKey("1");
        courseRecord.setCalculatedGradeValue("3.0");
        courseRecord.setCalculatedGradeScaleKey("1");
        
        return courseRecord;
        
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

    private AtpInfo createTerm(String atpSpringTypeKey, Date startDate, Date endDate, String name) {
        
        AtpInfo atp = new AtpInfo();
        
        atp.setTypeKey(atpSpringTypeKey);
        
        atp.setName(name);
        
        atp.setStartDate(startDate);
        atp.setEndDate(endDate);
        
        return atp;
    }
    
    
}
