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
package org.kuali.student.enrollment.class2.academicrecord.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.jws.WebParam;

import org.apache.bcel.generic.ARRAYLENGTH;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.enrollment.academicrecord.dto.GPAInfo;
import org.kuali.student.enrollment.academicrecord.dto.LoadInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentCredentialRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentProgramRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentTestScoreRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Kuali Student Team 
 *
 */
public class AcademicRecordServiceClass2MockImpl implements
        AcademicRecordService, MockService {
    
    private static final Logger log = LoggerFactory
            .getLogger(AcademicRecordServiceClass2MockImpl.class);

    @Resource
    private AtpService atpService;
    
    /**
     * 
     */
    public AcademicRecordServiceClass2MockImpl() {
        // TODO Auto-generated constructor stub
    }
    
    
    /* (non-Javadoc)
     * @see org.kuali.student.common.mock.MockService#clear()
     */
    @Override
    public void clear() {
        studentToCourseRecordsMap.clear();
        termToCourseRecordsMap.clear();
        courseIdToCourseCodeMap.clear();
        studentCourseRecordsSet.clear();
        
    }


    private Map<String, List<StudentCourseRecordInfo>>studentToCourseRecordsMap = new HashMap<String, List<StudentCourseRecordInfo>>();
    
    private Map<String, List<StudentCourseRecordInfo>>termToCourseRecordsMap = new HashMap<String, List<StudentCourseRecordInfo>>();
    
    private Set<StudentCourseRecordInfo>studentCourseRecordsSet = new HashSet<StudentCourseRecordInfo>();
    
    // this is a bit of a hack until the record can contain the course id directly
    private Map<String, String>courseIdToCourseCodeMap = new HashMap<String, String>();
    
    /**
     * Store a course record for the term specified.  The caller is responsible for filling in the object correctly.
     * 
     * @param studentId the student who completed the course
     * @param termId the term the course is from
     * @param courseRecord the course record itself.
     */
    public void storeStudentCourseRecord(String studentId, String termId, String courseId, StudentCourseRecordInfo courseRecord) {

        studentCourseRecordsSet.add(courseRecord);
        
        courseIdToCourseCodeMap.put(courseId, courseRecord.getCourseCode());
        
        // link to student
        List<StudentCourseRecordInfo> studentCourseList = studentToCourseRecordsMap.get(studentId);
        
        if (studentCourseList == null) {
            studentCourseList = new ArrayList<StudentCourseRecordInfo>();
            studentToCourseRecordsMap.put(studentId, studentCourseList);
        }
        
        studentCourseList.add(courseRecord);
        
        // link to term
        
        List<StudentCourseRecordInfo> termCourseList = termToCourseRecordsMap.get(termId);
        
        if (termCourseList == null) {
            termCourseList = new ArrayList<StudentCourseRecordInfo>();
            termToCourseRecordsMap.put(termId, termCourseList);
        }
        
        termCourseList.add(courseRecord);
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getAttemptedCourseRecordsForTerm(java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<StudentCourseRecordInfo> getAttemptedCourseRecordsForTerm(
             String personId,
            String termId,
             ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
       throw new UnsupportedOperationException("not implemented");
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getCompletedCourseRecords(java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<StudentCourseRecordInfo> getCompletedCourseRecords(
             String personId,
             ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
       throw new UnsupportedOperationException("not implemented");
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getCompletedCourseRecordsForCourse(java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<StudentCourseRecordInfo> getCompletedCourseRecordsForCourse(
             String personId,
            String courseId,
             ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        
        List<StudentCourseRecordInfo>resultsList  = new ArrayList<StudentCourseRecordInfo>();
        
        if (!studentToCourseRecordsMap.keySet().contains(personId))
            throw new DoesNotExistException("No course records for student Id = " + personId);

        if (!courseIdToCourseCodeMap.keySet().contains(courseId))
            throw new DoesNotExistException("No course records for course id = " + courseId);
        
        List<StudentCourseRecordInfo> records = studentToCourseRecordsMap.get(personId);
    
        String courseCode = courseIdToCourseCodeMap.get(courseId);
        
        for (StudentCourseRecordInfo studentCourseRecordInfo : records) {
            
            if (studentCourseRecordInfo.getCourseCode().equals(courseCode))
                resultsList.add(studentCourseRecordInfo);
                
        }
        
        return resultsList;
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getCompletedCourseRecordsForTerm(java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<StudentCourseRecordInfo> getCompletedCourseRecordsForTerm(
             String personId,
            String termId,
             ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
       throw new UnsupportedOperationException("not implemented");
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getGPAForTerm(java.lang.String, java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public GPAInfo getGPAForTerm( String personId,
            String termId,
            String calculationTypeKey,
             ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
       throw new UnsupportedOperationException("not implemented");
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getCumulativeGPA(java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public GPAInfo getCumulativeGPA(
             String personId,
            String calculationTypeKey,
             ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
       throw new UnsupportedOperationException("not implemented");
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getCumulativeGPAForProgram(java.lang.String, java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public GPAInfo getCumulativeGPAForProgram(
             String personId,
            String programId,
            String calculationTypeKey,
             ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
       throw new UnsupportedOperationException("not implemented");
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getCumulativeGPAForTermAndProgram(java.lang.String, java.lang.String, java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public GPAInfo getCumulativeGPAForTermAndProgram(
             String personId,
            String programId,
            String termKey,
            String calculationTypeKey,
             ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
       throw new UnsupportedOperationException("not implemented");
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getLoadForTerm(java.lang.String, java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public LoadInfo getLoadForTerm(
             String personId,
            String termId,
            String calculationTypeKey,
             ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
       throw new UnsupportedOperationException("not implemented");
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getProgramRecords(java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<StudentProgramRecordInfo> getProgramRecords(
             String personId,
             ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
       throw new UnsupportedOperationException("not implemented");
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getAwardedCredentials(java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<StudentCredentialRecordInfo> getAwardedCredentials(
             String personId,
             ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
       throw new UnsupportedOperationException("not implemented");
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getTestScoreRecords(java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<StudentTestScoreRecordInfo> getTestScoreRecords(
             String personId,
             ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
       throw new UnsupportedOperationException("not implemented");
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getTestScoreRecordsByType(java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<StudentTestScoreRecordInfo> getTestScoreRecordsByType(
             String personId,
            String testTypeKey,
             ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
       throw new UnsupportedOperationException("not implemented");
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getEarnedCreditsForTerm(java.lang.String, java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public String getEarnedCreditsForTerm(
             String personId,
            String termId,
            String calculationTypeKey,
             ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
       throw new UnsupportedOperationException("not implemented");
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getEarnedCredits(java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public String getEarnedCredits(
             String personId,
            String calculationTypeKey,
             ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
       throw new UnsupportedOperationException("not implemented");
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getEarnedCumulativeCreditsForProgramAndTerm(java.lang.String, java.lang.String, java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public String getEarnedCumulativeCreditsForProgramAndTerm(
             String personId,
            String programId,
            String termId,
            String calculationTypeKey,
             ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO Auto-generated method stub
       throw new UnsupportedOperationException("not implemented");
    }

}
