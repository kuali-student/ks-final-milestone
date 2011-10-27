/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.enrollment.academicrecord.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.r2.common.dto.ContextInfo;

import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.GPAInfo;

import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

/**
 * Academic Record Service Description and Assumptions.
 *
 * This service provides the Academic Record.
 *
 * Version: 1.0 (Dev)
 *
 * @author tom
 * @since Mon Sep 12 12:22:34 EDT 2011
 */

@WebService(name = "AcademicRecordService", serviceName = "AcademicRecordService", portName = "AcademicRecordService", targetNamespace = "http://student.kuali.org/wsdl/academicrecord")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)

public interface AcademicRecordService {

    /**
     * This method returns a list of StudentCourseRecords for a
     * student and a term where each record is a course the student
     * attempted. The Term includes nested or sub-Terms.
     *
     * @param personId an Id of a student
     * @param termKey a key of a Term
     * @param context Context information containing the principalId
     *        and locale information about the caller of service
     *        operation
     * @return a list of StudentCourseRecords
     * @throws DoesNotExistException personId or termKey not found
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     */    
    public List<StudentCourseRecordInfo> getAttemptedCourseRecordsForTerm(@WebParam(name = "personId") String personId, @WebParam(name = "termKey") String termKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * This method returns a list of StudentCourseRecord for a student
     * where each returned course is a course the student completed
     * for any term.
     *
     * @param personId an Id of a student
     * @param context Context information containing the principalId
     *        and locale information about the caller of service
     *        operation
     * @return a list of StudentCourseRecords
     * @throws DoesNotExistException personId not found
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     */    
    public List<StudentCourseRecordInfo> getCompletedCourseRecords(@WebParam(name = "personId") String personId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * This method returns a list of StudentCourseRecord for a student
     * and a term where each returned course is a course the student
     * completed The Term includes nested or sub-Terms.
     *
     * @param personId an Id of a student
     * @param termKey a key of a Term
     * @param context Context information containing the principalId
     *        and locale information about the caller of service
     *        operation
     * @return a list of StudentCourseRecords
     * @throws DoesNotExistException personId or termKey not found
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     */    
    public List<StudentCourseRecordInfo> getCompletedCourseRecordsForTerm(@WebParam(name = "personId") String personId, @WebParam(name = "termKey") String termKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * This method returns the GPA of a student for all courses taken
     * within a given a Term including its sub-Terms.
     *
     * @param personId an Id of a student
     * @param termKey a key of a Term
     * @param context Context information containing the principalId
     *        and locale information about the caller of service
     *        operation
     * @return a GPA
     * @throws DoesNotExistException personId, termKey or
     *         calculationTypeKey not found
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     */    
    public GPAInfo getGPAForTerm(@WebParam(name = "personId") String personId, @WebParam(name = "termKey") String termKey, @WebParam(name = "calculationTypeKey") String calculationTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * This method returns the GPA of a student for the period of time
     * indicated by the given Academic Calendar.
     *
     * @param personId an Id of a student
     * @param academiccalendarKey a key of an Academic Calendar
     * @param context Context information containing the principalId
     *        and locale information about the caller of service
     *        operation
     * @return a GPA
     * @throws DoesNotExistException personId or academicCalendarKey not found
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     */    
    public GPAInfo getGPAForAcademicCalendar(@WebParam(name = "personId") String personId, @WebParam(name = "academicCalendarKey") String academicCalendarKey, @WebParam(name = "calculationTypeKey") String calculationTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * This method returns the cumulative GPA of a student.
     *
     * @param personId an Id of a student
     * @param context Context information containing the principalId
     *        and locale information about the caller of service
     *        operation
     * @return a GPA
     * @throws DoesNotExistException personId or calculationTypeKey 
     *         not found
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     */    
    public GPAInfo getCumulativeGPA(@WebParam(name = "personId") String personId, @WebParam(name = "calculationTypeKey") String calculationTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * This method returns the number of credits a student earned by
     * courss within in a given Term including its sub-Terms.
     *
     * @param personId an Id of a student
     * @param termKey a key for a Term
     * @param context Context information containing the principalId
     *        and locale information about the caller of service
     *        operation
     * @return a number of credits represented by a string
     * @throws DoesNotExistException personId, termKey or 
     *         calculationTypeKey not found
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     */    
    public String getEarnedCreditsForTerm(@WebParam(name = "personId") String personId, @WebParam(name = "termKey") String termKey, @WebParam(name = "calculationTypeKey") String calculationTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * This method returns the number of credits a student earned in a
     * given Academic Calendar.
     *
     * @param personId an Id of a student
     * @param academicCalendarKey a key for an AcademicCalendar
     * @param context Context information containing the principalId
     *        and locale information about the caller of service
     *        operation
     * @return a number of credits represented by a string
     * @throws DoesNotExistException personId, academicCalendarKey or 
     *         calculationTypeKey not found
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     */    
    public String getEarnedCreditsForAcademicCalendar(@WebParam(name = "personId") String personId, @WebParam(name = "academicCalendarKey") String academicCalendarKey, @WebParam(name = "calculationTypeKey") String calculationTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * This method returns the number of credits a student earned
     * across all terms.
     *
     * @param personId an Id of a student
     * @param context Context information containing the principalId
     *        and locale information about the caller of service
     *        operation
     * @return a number of credits represented by a string
     * @throws DoesNotExistException personId or calculationTypeKey 
     *         not found
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     */    
    public String getEarnedCredits(@WebParam(name = "personId") String personId, @WebParam(name = "calculationTypeKey") String calculationTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
}
