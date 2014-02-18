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

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.academicrecord.dto.GPAInfo;
import org.kuali.student.enrollment.academicrecord.dto.LoadInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentCredentialRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentProgramRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentTestScoreRecordInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.util.constants.AcademicRecordServiceConstants;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * Academic Record Service Description and Assumptions.
 * <p/>
 * This service provides the Academic Record.
 * <p/>
 *
 * @author tom
 * @version 0.0.7
 * @Author Sri komandur@uw.edu
 * @since Mon Sep 12 12:22:34 EDT 2011
 */

@WebService(name = "AcademicRecordService", serviceName = "AcademicRecordService", portName = "AcademicRecordService", targetNamespace = AcademicRecordServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
// TODO KSENROLL-1630
public interface AcademicRecordService {

    /**
     * This method returns a list of StudentCourseRecords for a student and a
     * term where each record is a course the student attempted. The Term
     * includes nested or sub-Terms.
     *
     * @param personId    an Id of a student
     * @param termId      a key of the term
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return a list of StudentCourseRecords
     * @throws DoesNotExistException     personId or termId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException personId, termId or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<StudentCourseRecordInfo> getAttemptedCourseRecordsForTerm(@WebParam(name = "personId") String personId,
                                                                          @WebParam(name = "termId") String termId,
                                                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * This method returns a list of StudentCourseRecord for a student where
     * each returned course is a course the student completed for any term.
     *
     * @param personId    an Id of a student
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return a list of StudentCourseRecords
     * @throws DoesNotExistException     personId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException personId or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<StudentCourseRecordInfo> getCompletedCourseRecords(@WebParam(name = "personId") String personId,
                                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * This method returns a list of StudentCourseRecord for a student for a
     * given course
     *
     * @param personId    an Id of a student
     * @param courseId    Unique Id of the Course (canonical)
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return a list of StudentCourseRecords for the specified course or empty
     *         list if none exist
     * @throws DoesNotExistException     personId or courseId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException personId, courseId or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<StudentCourseRecordInfo> getCompletedCourseRecordsForCourse(@WebParam(name = "personId") String personId,
                                                                            @WebParam(name = "courseId") String courseId,
                                                                            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;


    /**
     * This method returns a list of StudentCourseRecord for a student and a
     * term where each returned course is a course the student completed The
     * Term includes nested or sub-Terms.
     *
     * @param personId    an Id of a student
     * @param termId      a key of the term
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return a list of StudentCourseRecords
     * @throws DoesNotExistException     personId or termId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException personId, termId or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<StudentCourseRecordInfo> getCompletedCourseRecordsForTerm(@WebParam(name = "personId") String personId,
                                                                          @WebParam(name = "termId") String termId,
                                                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * This method returns the GPA of a student for all courses taken within a
     * given a Term including its sub-Terms.
     *
     * @param personId           an Id of a student
     * @param termId             a key of the term
     * @param calculationTypeKey Unique key identifying the calculation. For
     *                           example, it may point to a description of rules
     *                           such as A+ count more than A and do honors
     *                           classes count more
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return a GPA
     * @throws DoesNotExistException     personId, termId or calculationTypeKey
     *                                   not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException personId, termId, calculationTypeKey or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public GPAInfo getGPAForTerm(@WebParam(name = "personId") String personId,
                                 @WebParam(name = "termId") String termId,
                                 @WebParam(name = "calculationTypeKey") String calculationTypeKey,
                                 @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * This method returns the cumulative GPA of a student.
     *
     * @param personId           an Id of a student
     * @param calculationTypeKey Unique key identifying the calculation. For
     *                           example, it may point to a description of rules
     *                           such as A+ count more than A and do honors
     *                           classes count more
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return cumulative GPA
     * @throws DoesNotExistException     personId or calculationTypeKey not
     *                                   found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException personId, calculationTypeKey or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public GPAInfo getCumulativeGPA(@WebParam(name = "personId") String personId,
                                    @WebParam(name = "calculationTypeKey") String calculationTypeKey,
                                    @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * This method returns the cumulative GPA of a student.
     *
     * @param studentCourseRecordInfoList  The list of student course records that should
     *                                     be used to calculate the GPA.
     * @param calculationTypeKey           Unique key identifying the calculation. For
     *                                     example, it may point to a description of rules
     *                                     such as A+ count more than A and do honors
     *                                     classes count more
     * @param contextInfo                  Context information containing the principalId
     *                                     and locale information about the caller of
     *                                     service operation
     * @return calculated GPA
     * @throws DoesNotExistException       calculationTypeKey not found
     * @throws InvalidParameterException   invalid contextInfo
     * @throws MissingParameterException   studentCourseRecordInfoList, calculationTypeKey
     *                                     or contextInfo is missing or null
     * @throws OperationFailedException    unable to complete request
     * @throws PermissionDeniedException   an authorization failure occurred
     */
    public GPAInfo calculateGPA(@WebParam(name = "studentCourseRecordInfoList") List<StudentCourseRecordInfo> studentCourseRecordInfoList,
                                    @WebParam(name = "calculationTypeKey") String calculationTypeKey,
                                    @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * This method returns student's cumulative GPA for the program to date
     *
     * @param personId           an Id of a student
     * @param programId          Id of the program
     * @param calculationTypeKey Unique key identifying the calculation. For
     *                           example, it may point to a description of rules
     *                           such as A+ count more than A and do honors
     *                           classes count more
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return cumulative GPA
     * @throws DoesNotExistException     personId, programId or calculationTypeKey
     *                                   not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException personId, programId, calculationTypeKey
     *                                   or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public GPAInfo getCumulativeGPAForProgram(@WebParam(name = "personId") String personId,
                                              @WebParam(name = "programId") String programId,
                                              @WebParam(name = "calculationTypeKey") String calculationTypeKey,
                                              @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * This method returns student's cumulative GPA for the specified program
     * and term
     *
     * @param personId           an Id of a student
     * @param programId          Id of the program
     * @param termKey            a key for the term
     * @param calculationTypeKey Unique key identifying the calculation. For
     *                           example, it may point to a description of rules
     *                           such as A+ count more than A and do honors
     *                           classes count more
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return cumulative GPA
     * @throws DoesNotExistException     personId, programId, termKey or
     *                                   calculationTypeKey not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException personId, programId, termKey,
     *                                   calculationTypeKey or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public GPAInfo getCumulativeGPAForTermAndProgram(@WebParam(name = "personId") String personId,
                                                     @WebParam(name = "programId") String programId,
                                                     @WebParam(name = "termKey") String termKey,
                                                     @WebParam(name = "calculationTypeKey") String calculationTypeKey,
                                                     @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * This method returns the load information for the given student, term and
     * calculation type
     *
     * @param personId           an Id of a student
     * @param termId             a key for the term
     * @param calculationTypeKey Unique key identifying the calculation. For
     *                           example, it may point to a description of rules
     *                           such as A+ count more than A and do honors
     *                           classes count more
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return load information
     * @throws DoesNotExistException     personId, termId or calculationTypeKey
     *                                   not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException personId, termId, calculationTypeKey or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public LoadInfo getLoadForTerm(@WebParam(name = "personId") String personId,
                                   @WebParam(name = "termId") String termId,
                                   @WebParam(name = "calculationTypeKey") String calculationTypeKey,
                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * This method returns the summary of student programs (includes currently
     * enrolled, completed and not completed)
     *
     * @param personId    an Id of a student
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return a list of programs
     * @throws DoesNotExistException     personId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException personId or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<StudentProgramRecordInfo> getProgramRecords(@WebParam(name = "personId") String personId,
                                                            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Get credentials that have been awarded by this institution to the
     * student
     *
     * @param personId    an Id of a student
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return a list of credentials
     * @throws DoesNotExistException     personId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException personId or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<StudentCredentialRecordInfo> getAwardedCredentials(@WebParam(name = "personId") String personId,
                                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;


    /**
     * Get a student's test scores
     *
     * @param personId    an Id of a student
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return a list of test scores
     * @throws DoesNotExistException     personId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException personId or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<StudentTestScoreRecordInfo> getTestScoreRecords(@WebParam(name = "personId") String personId,
                                                                @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Get a student's test scores by test type
     *
     * @param personId    an Id of a student
     * @param testTypeKey a key for the test type
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return a list of test scores
     * @throws DoesNotExistException     personId or testTypeKey not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException personId, testTypeKey or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<StudentTestScoreRecordInfo> getTestScoreRecordsByType(@WebParam(name = "personId") String personId,
                                                                      @WebParam(name = "testTypeKey") String testTypeKey,
                                                                      @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;


    /**
     * This method returns the number of credits a student earned by course
     * within in a given Term including its sub-Terms.
     *
     * @param personId           an Id of a student
     * @param termId             a key for the term
     * @param calculationTypeKey Unique key identifying the calculation. For
     *                           example, it may point to a description of rules
     *                           such as A+ count more than A and do honors
     *                           classes count more
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return a number of credits represented by a string
     * @throws DoesNotExistException     personId, termId or calculationTypeKey
     *                                   not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException personId, termId, calculationTypeKey or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public String getEarnedCreditsForTerm(@WebParam(name = "personId") String personId,
                                          @WebParam(name = "termId") String termId,
                                          @WebParam(name = "calculationTypeKey") String calculationTypeKey,
                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;


    /**
     * This method returns the number of credits a student earned across all
     * terms.
     *
     * @param personId           an Id of a student
     * @param calculationTypeKey Unique key identifying the calculation. For
     *                           example, it may point to a description of rules
     *                           such as A+ count more than A and do honors
     *                           classes count more
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return a number of credits represented by a string
     * @throws DoesNotExistException     personId or calculationTypeKey not
     *                                   found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException personId, calculationTypeKey or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public String getEarnedCredits(@WebParam(name = "personId") String personId,
                                   @WebParam(name = "calculationTypeKey") String calculationTypeKey,
                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;


    /**
     * This method returns the given student's earned cumulative credits in the
     * program
     *
     * @param personId           an Id of a student
     * @param programId          Id of the program
     * @param termId             a key of the term
     * @param calculationTypeKey Unique key identifying the calculation. For
     *                           example, it may point to a description of rules
     *                           such as A+ count more than A and do honors
     *                           classes count more
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return a number of credits represented by a string
     * @throws DoesNotExistException     personId, programId, termId or
     *                                   calculationTypeKey not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException personId, programId, termId,
     *                                   calculationTypeKey or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public String getEarnedCumulativeCreditsForProgramAndTerm(@WebParam(name = "personId") String personId,
                                                              @WebParam(name = "programId") String programId,
                                                              @WebParam(name = "termId") String termId,
                                                              @WebParam(name = "calculationTypeKey") String calculationTypeKey,
                                                              @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;


    /**
     * Get credentials of student awarded elsewhere
     *
     * @param personId    an Id of a student
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return a list of external credentials
     * @throws DoesNotExistException     personId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException personId or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     *
    public List<ExternalCredentialRecordInfo> getExternalCredentials(@WebParam(name = "personId") String personId,
     @WebParam(name = "contextInfo") ContextInfo contextInfo)
     throws DoesNotExistException,
     InvalidParameterException,
     MissingParameterException,
     OperationFailedException,
     PermissionDeniedException;
     */


    /**
     * Get a student's transfer credits
     *
     * @param personId    an Id of a student
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return a list of transfer credit records
     * @throws DoesNotExistException     personId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException personId or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     *
    public List<StudentTransferCreditRecordInfo> getTransferCredits(@WebParam(name = "personId") String personId,
     @WebParam(name = "contextInfo") ContextInfo contextInfo)
     throws DoesNotExistException,
     InvalidParameterException,
     MissingParameterException,
     OperationFailedException,
     PermissionDeniedException;
     */


    /**
     * Get a student's transfer credits for the awarded course TODO: Why is the
     * return a list?
     *
     * @param personId    an Id of a student
     * @param courseId    Id of the course
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return a list of transfer credit records
     * @throws DoesNotExistException     personId or courseId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException personId, courseId or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     *
    public List<StudentTransferCreditRecordInfo> getTransferCreditForAwardedCourse(@WebParam(name = "personId") String personId,
     @WebParam(name = "courseId") String courseId,
     @WebParam(name = "contextInfo") ContextInfo contextInfo)
     throws DoesNotExistException,
     InvalidParameterException,
     MissingParameterException,
     OperationFailedException,
     PermissionDeniedException;

     */


    /**
     * Get a student's cumulative rank
     *
     * @param personId      an Id of a student
     * @param populationKey a key for the population
     * @param contextInfo   Context information containing the principalId and
     *                      locale information about the caller of service
     *                      operation
     * @return cumulative rank
     * @throws DoesNotExistException     personId or populationKey not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException personId, populationKey or contextInfo
     *                                   is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     *
    public StudentRankInfo getCumulativeRank(@WebParam(name = "personId") String personId,
     @WebParam(name = "populationKey") String populationKey,
     @WebParam(name = "contextInfo") ContextInfo contextInfo)
     throws DoesNotExistException,
     InvalidParameterException,
     MissingParameterException,
     OperationFailedException,
     PermissionDeniedException;
     */

    /**
     * Get a student's cumulative rank in the program
     *
     * @param personId      an Id of a student
     * @param programId     Id of the program
     * @param populationKey a key for the test type
     * @param contextInfo   Context information containing the principalId and
     *                      locale information about the caller of service
     *                      operation
     * @return cumulative rank
     * @throws DoesNotExistException     personId, programId or populationKey
     *                                   not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException personId, programId, populationKey or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     *
    public StudentRankInfo getCumulativeRankForProgram(@WebParam(name = "personId") String personId,
     @WebParam(name = "programId") String programId,
     @WebParam(name = "populationKey") String populationKey,
     @WebParam(name = "contextInfo") ContextInfo contextInfo)
     throws DoesNotExistException,
     InvalidParameterException,
     MissingParameterException,
     OperationFailedException,
     PermissionDeniedException;
     */


    /**
     * Get a student's honors information
     *
     * @param personId    an Id of a student
     * @param programId   Id of the program
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return honors information
     * @throws DoesNotExistException     personId or programId not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException personId, programId or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     *//*

    public HonorsInfo getHonorsByProgram(@WebParam(name = "personId") String personId,
                                         @WebParam(name = "programId") String programId,
                                         @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;
    */

    /**
     * This method returns the number of credits a student earned in a given
     * Academic Calendar.
     *
     * @param personId            an Id of a student
     * @param academicCalendarKey a key for an AcademicCalendar
     * @param calculationTypeKey  Unique key identifying the calculation. For
     *                            example, it may point to a description of
     *                            rules such as A+ count more than A and do
     *                            honors classes count more
     * @param contextInfo         Context information containing the principalId
     *                            and locale information about the caller of
     *                            service operation
     * @return a number of credits represented by a string
     * @throws DoesNotExistException     personId, academicCalendarKey or
     *                                   calculationTypeKey not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException personId, academicCalendarKey or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     *
    public String getEarnedCreditsForAcademicCalendar(@WebParam(name = "personId") String personId,
     @WebParam(name = "academicCalendarKey") String academicCalendarKey,
     @WebParam(name = "calculationTypeKey") String calculationTypeKey,
     @WebParam(name = "contextInfo") ContextInfo contextInfo)
     throws DoesNotExistException,
     InvalidParameterException,
     MissingParameterException,
     OperationFailedException,
     PermissionDeniedException;
     */

    /**
     * This method returns the given student's earned cumulative credits in the
     * program
     *
     * @param personId           an Id of a student
     * @param programId          Id of the program
     * @param calculationTypeKey Unique key identifying the calculation. For
     *                           example, it may point to a description of rules
     *                           such as A+ count more than A and do honors
     *                           classes count more
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return a number of credits represented by a string
     * @throws DoesNotExistException     personId, programId or calculationTypeKey
     *                                   not found
     * @throws InvalidParameterException invalid contextInfo
     * @throws MissingParameterException personId, programId or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     *
    public String getEarnedCumulativeCreditsForProgram(@WebParam(name = "personId") String personId,
     @WebParam(name = "programId") String programId,
     @WebParam(name = "calculationTypeKey") String calculationTypeKey,
     @WebParam(name = "contextInfo") ContextInfo contextInfo)
     throws DoesNotExistException,
     InvalidParameterException,
     MissingParameterException,
     OperationFailedException,
     PermissionDeniedException;
     */

    /**
     * Retrieves a single StudentProgramRecord by a StudentProgramRecord Id.
     * @param studentProgramRecordId the id of the studentProgramRecord
     * @param contextInfo Context information containing the principalId and locale information about the caller of service operation
     * @return the StudentProgramRecordInfo
     * @throws DoesNotExistException studentProgramRecordId is not found
     * @throws InvalidParameterException studentProgramRecordId or contextInfo is not valid
     * @throws MissingParameterException studentProgramRecordId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StudentProgramRecordInfo getStudentProgramRecord(@WebParam(name = "studentProgramRecordId") String studentProgramRecordId,
                                                            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of StudentProgramRecords from a list of StudentProgramRecord Ids. The returned
     * list may be in any order and if duplicate Ids are supplied, a unique set may or may not be returned.
     * @param studentProgramRecordIds a list of StudentProgramRecord identifiers
     * @param contextInfo
     * @return a List of StudentProgramRecordInfo
     * @throws DoesNotExistException studentProgramRecordIds are not found
     * @throws InvalidParameterException studentProgramRecordIds or contextInfo is not valid
     * @throws MissingParameterException studentProgramRecordIds or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<StudentProgramRecordInfo> getStudentProgramRecordsByIds(@WebParam(name = "studentProgramRecordIds") List<String> studentProgramRecordIds,
                                                                        @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of StudentProgramRecord Ids by StudentProgramRecord Type.
     * @param studentProgramRecordTypeKey
     * @param contextInfo
     * @return a list of Object identifiers matching objectTypeKey or an empty list if none found
     * @throws InvalidParameterException studentProgramRecordTypeKey or contextInfo is not valid
     * @throws MissingParameterException studentProgramRecordTypeKey or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getStudentProgramRecordIdsByType(@WebParam(name = "studentProgramRecordTypeKey") String studentProgramRecordTypeKey,
                                                         @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for StudentProgramRecord Ids that meet the given search criteria.
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return a list of StudentProgramRecordIds
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForStudentProgramRecordIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                         @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for StudentProgramRecords that meet the given search criteria.
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return a list of StudentProgramRecords
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<StudentProgramRecordInfo> searchForStudentProgramRecords(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                                         @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Validates a StudentProgramRecord. If an identifier is present for the StudentProgramRecord and a
     * record is found for that identifier, the validation checks if the
     * StudentProgramRecord can be updated to the new values. If an identifier is not
     * present or a record does not exist, the validation checks if
     * the StudentProgramRecord with the given data can be created.
     * @param validationTypeKey the identifier for the validation Type
     * @param studentProgramTypeKey the identifier for the Object Type to be validated
     * @param studentProgramRecordInfo the studentProgramRecord to be validated
     * @param contextInfo Context information containing the principalId and locale information about the caller of service operation
     * @return a list of validation results or an empty list if validation succeeded
     * @throws DoesNotExistException validationTypeKey or objectTypeKey is not found
     * @throws InvalidParameterException studentProgramRecordInfo or contextInfo is not valid
     * @throws MissingParameterException validationTypeKey, objectTypeKey, studentProgramRecordInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateStudentProgramRecord(@WebParam(name = "validationTypeKey") String validationTypeKey,
                                                                   @WebParam(name = "studentProgramTypeKey") String studentProgramTypeKey,
                                                                   @WebParam(name = "studentProgramRecordInfo") StudentProgramRecordInfo studentProgramRecordInfo,
                                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new StudentProgramRecord. The StudentProgramRecord Id, Type, and Meta information may not
     * be set in the supplied data object.
     *
     * @param studentProgramRecordTypeKey
     * @param personId
     * @param studentProgramRecord
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the newly created StudentProgramRecordInfo
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException objectTypeKey does not exist or is not supported
     * @throws InvalidParameterException studentProgramRecord or contextInfo is not valid
     * @throws MissingParameterException objectTypeKey, studentProgramRecord, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information designated as read only
     */
    public StudentProgramRecordInfo createStudentProgramRecord(@WebParam(name = "studentProgramRecordTypeKey") String studentProgramRecordTypeKey,
                                                               @WebParam(name = "personId") String personId,
                                                               @WebParam(name = "studentProgramRecord") StudentProgramRecordInfo studentProgramRecord,
                                                               @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates an existing StudentProgramRecord. The StudentProgramRecord Id, Type, and Meta
     * information may not be changed.
     * @param studentProgramRecordId
     * @param studentProgramRecord
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return
     * @throws DataValidationErrorException
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws ReadOnlyException
     */
    public StudentProgramRecordInfo updateStudentProgramRecord(@WebParam(name = "studentProgramRecordId") String studentProgramRecordId,
                                                               @WebParam(name = "studentProgramRecord") StudentProgramRecordInfo studentProgramRecord,
                                                               @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Deletes an existing StudentProgramRecord
     * @param studentProgramRecordId  the identifier for the Object to be deleted
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the status of the delete operation. This must always be true.
     * @throws DataValidationErrorException
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public StatusInfo deleteStudentProgramRecord(@WebParam(name = "studentProgramRecordId") String studentProgramRecordId,
                                                 @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a single StudentCourseRecord by a StudentCourseRecord Id.
     * @param studentCourseRecordId the id of the studentCourseRecord
     * @param contextInfo Context information containing the principalId and locale information about the caller of service operation
     * @return the StudentCourseRecordInfo
     * @throws DoesNotExistException studentCourseRecordId is not found
     * @throws InvalidParameterException studentCourseRecordId or contextInfo is not valid
     * @throws MissingParameterException studentCourseRecordId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StudentCourseRecordInfo getStudentCourseRecord(@WebParam(name = "studentCourseRecordId") String studentCourseRecordId,
                                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of StudentCourseRecords from a list of StudentCourseRecords Ids. The returned
     * list may be in any order and if duplicate Ids are supplied, a unique set may or may not be returned.
     * @param studentCourseRecordIds a list of StudentCourseRecord identifiers
     * @param contextInfo
     * @return a List of StudentCourseRecordInfo
     * @throws DoesNotExistException studentCourseRecordIds are not found
     * @throws InvalidParameterException studentCourseRecordIds or contextInfo is not valid
     * @throws MissingParameterException studentCourseRecordIds or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<StudentCourseRecordInfo> getStudentCourseRecordsByIds(@WebParam(name = "studentCourseRecordIds") List<String> studentCourseRecordIds,
                                                                      @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of StudentCourseRecord Ids by StudentCourseRecord Type.
     * @param studentCourseRecordTypeKey
     * @param contextInfo
     * @return a list of Object identifiers matching objectTypeKey or an empty list if none found
     * @throws InvalidParameterException studentCourseRecordTypeKey or contextInfo is not valid
     * @throws MissingParameterException studentCourseRecordTypeKey or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getStudentCourseRecordIdsByType(@WebParam(name = "studentCourseRecordTypeKey") String studentCourseRecordTypeKey,
                                                        @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for StudentCourseRecord Ids that meet the given search criteria.
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return a list of StudentCredentialRecordIds
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForStudentCourseRecordIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                        @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for StudentCourseRecords that meet the given search criteria.
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return a list of StudentCourseRecords
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<StudentCourseRecordInfo> searchForStudentCourseRecords(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                                       @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Validates a StudentCourseRecord. If an identifier is present for the
     * StudentCourseRecord and a record is found for that identifier, the validation
     * checks if the StudentCourseRecord can be updated to the new values.
     * If an identifier is not present or a record does not exist, the validation
     * checks if the StudentCourseRecord with the given data can be created.
     * @param validationTypeKey the identifier for the validation Type
     * @param studentCourseRecordTypeKey the identifier for the Object Type to be validated
     * @param studentCourseRecordInfo the studentCourseRecord to be validated
     * @param contextInfo Context information containing the principalId and locale information about the caller of service operation
     * @return a list of validation results or an empty list if validation succeeded
     * @throws DoesNotExistException validationTypeKey or objectTypeKey is not found
     * @throws InvalidParameterException studentCourseRecordInfo or contextInfo is not valid
     * @throws MissingParameterException validationTypeKey, objectTypeKey, studentCourseRecordInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateStudentCourseRecord(@WebParam(name = "validationTypeKey") String validationTypeKey,
                                                                  @WebParam(name = "studentCourseRecordTypeKey") String studentCourseRecordTypeKey,
                                                                  @WebParam(name = "studentCourseRecordInfo") StudentCourseRecordInfo studentCourseRecordInfo,
                                                                  @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new StudentCourseRecord. The StudentCourseRecord Id, Type, and Meta information may not
     * be set in the supplied data object.
     *
     * @param studentCourseRecordTypeKey
     * @param personId
     * @param studentCourseRecord
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the newly created StudentCourseRecordInfo
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException objectTypeKey does not exist or is not supported
     * @throws InvalidParameterException StudentCourseRecord or contextInfo is not valid
     * @throws MissingParameterException objectTypeKey, studentCourseRecord, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information designated as read only
     */
    public StudentCourseRecordInfo createStudentCourseRecord(@WebParam(name = "studentCourseRecordTypeKey") String studentCourseRecordTypeKey,
                                                             @WebParam(name = "personId") String personId,
                                                             @WebParam(name = "studentCourseRecord") StudentCourseRecordInfo studentCourseRecord,
                                                             @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates an existing StudentCourseRecord. The StudentCourseRecord Id, Type, and Meta information may not be changed.
     * @param studentCourseRecordId
     * @param studentCourseRecord
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the updated StudentCourseRecord
     * @throws DataValidationErrorException
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws ReadOnlyException
     */
    public StudentCourseRecordInfo updateStudentCourseRecord(@WebParam(name = "studentCourseRecordId") String studentCourseRecordId,
                                                             @WebParam(name = "studentCourseRecord") StudentCourseRecordInfo studentCourseRecord,
                                                             @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Deletes an existing StudentCourseRecord
     * @param studentCourseRecordId  the identifier for the Object to be deleted
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the status of the delete operation. This must always be true.
     * @throws DataValidationErrorException
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public StatusInfo deleteStudentCourseRecord(@WebParam(name = "studentCourseRecordId") String studentCourseRecordId,
                                                @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a single StudentCredentialRecord by a StudentCredentialRecord Id.
     * @param studentCredentialRecordId the id of the StudentCredentialRecord
     * @param contextInfo Context information containing the principalId and locale information about the caller of service operation
     * @return the StudentCredentialRecordInfo
     * @throws DoesNotExistException studentCredentialRecordId is not found
     * @throws InvalidParameterException studentCredentialRecordId or contextInfo is not valid
     * @throws MissingParameterException studentCredentialRecordId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StudentCourseRecordInfo getStudentCredentialRecord(@WebParam(name = "studentCredentialRecordId") String studentCredentialRecordId,
                                                              @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of StudentCredentialRecord Ids by StudentCredentialRecord Type.
     * @param studentCredentialRecordTypeKey
     * @param contextInfo
     * @return a list of Object identifiers matching objectTypeKey or an empty list if none found
     * @throws InvalidParameterException studentCredentialRecordTypeKey or contextInfo is not valid
     * @throws MissingParameterException studentCredentialRecordTypeKey or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getStudentCredentialRecordIdsByType(@WebParam(name = "studentCredentialRecordTypeKey") String studentCredentialRecordTypeKey,
                                                            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of StudentCredentialRecords from a list of StudentCredentialRecord Ids. The returned
     * list may be in any order and if duplicate Ids are supplied, a unique set may or may not be returned.
     * @param studentCredentialRecordIds a list of StudentCredentialRecord identifiers
     * @param contextInfo
     * @return a List of studentCredentialRecordInfo
     * @throws DoesNotExistException studentCredentialRecordIds are not found
     * @throws InvalidParameterException studentCredentialRecordIds or contextInfo is not valid
     * @throws MissingParameterException studentCredentialRecordIds or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<StudentCredentialRecordInfo> getStudentCredentialRecordsByIds(@WebParam(name = "studentCredentialRecordIds") List<String> studentCredentialRecordIds,
                                                                              @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for StudentCredentialRecord Ids that meet the given search criteria.
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return a list of StudentCourseRecordIds
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForStudentCredentialRecordIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for StudentCredentialRecords that meet the given search criteria.
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return a list of StudentCredentialRecords
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<StudentCredentialRecordInfo> searchForStudentCredentialRecords(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                                               @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Validates a StudentCredentialRecord. If an identifier is present for the
     * StudentCredentialRecord and a record is found for that identifier, the validation
     * checks if the StudentCredentialRecord can be updated to the new values.
     * If an identifier is not present or a record does not exist, the validation
     * checks if the StudentCredentialRecord with the given data can be created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param studentCredentialTypeKey the identifier for the Object Type to be validated
     * @param studentCredentialRecord the studentCredentialRecord to be validated
     * @param contextInfo Context information containing the principalId and locale information about the caller of service operation
     * @return a list of validation results or an empty list if validation succeeded
     * @throws DoesNotExistException validationTypeKey or objectTypeKey is not found
     * @throws InvalidParameterException studentCredentialRecordInfo or contextInfo is not valid
     * @throws MissingParameterException validationTypeKey, objectTypeKey, studentCredentialRecordInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateStudentCredentialRecord(@WebParam(name = "validationTypeKey") String validationTypeKey,
                                                                      @WebParam(name = "studentCredentialTypeKey") String studentCredentialTypeKey,
                                                                      @WebParam(name = "studentCredentialRecord") StudentCredentialRecordInfo studentCredentialRecord,
                                                                      @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new StudentCredentialRecord. The StudentCredentialRecord Id, Type, and Meta information may not
     * be set in the supplied data object.
     *
     * @param studentCredentialRecordTypeKey
     * @param personId
     * @param studentCredentialRecord
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the newly created StudentCredentialRecordInfo
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException objectTypeKey does not exist or is not supported
     * @throws InvalidParameterException StudentCredentialRecord or contextInfo is not valid
     * @throws MissingParameterException objectTypeKey, studentCredentialRecord, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information designated as read only
     */
    public StudentCredentialRecordInfo createStudentCredentialRecord(@WebParam(name = "studentCredentialRecordTypeKey") String studentCredentialRecordTypeKey,
                                                                     @WebParam(name = "personId") String personId,
                                                                     @WebParam(name = "studentCredentialRecord") StudentCredentialRecordInfo studentCredentialRecord,
                                                                     @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates an existing StudentCredentialRecord. The StudentCredentialRecord Id, Type, and Meta
     * information may not be changed.
     * @param studentCredentialRecordId
     * @param studentCredentialRecord
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the updated StudentCredentialRecordInfo
     * @throws DataValidationErrorException
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws ReadOnlyException
     */
    public StudentCredentialRecordInfo updateStudentCredentialRecord(@WebParam(name = "studentCredentialRecordId") String studentCredentialRecordId,
                                                                     @WebParam(name = "studentCredentialRecord") StudentCredentialRecordInfo studentCredentialRecord,
                                                                     @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Deletes an existing StudentCredentialRecord
     * @param studentCredentialRecordId  the identifier for the Object to be deleted
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the status of the delete operation. This must always be true.
     * @throws DataValidationErrorException
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public StatusInfo deleteStudentCredentialRecord(@WebParam(name = "studentCredentialRecordId") String studentCredentialRecordId,
                                                    @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a single StudentTestScoreRecord by a StudentTestScoreRecord Id.
     * @param studentTestScoreRecordId the id of the StudentCredentialRecord
     * @param contextInfo Context information containing the principalId and locale information about the caller of service operation
     * @return the StudentCredentialRecordInfo
     * @throws DoesNotExistException studentTestScoreRecordId is not found
     * @throws InvalidParameterException studentTestScoreRecordId or contextInfo is not valid
     * @throws MissingParameterException studentTestScoreRecordId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StudentTestScoreRecordInfo getStudentTestScoreRecord(@WebParam(name = "studentTestScoreRecordId") String studentTestScoreRecordId,
                                                              @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of StudentTestScoreRecords from a list of StudentTestScoreRecord Ids. The returned
     * list may be in any order and if duplicate Ids are supplied, a unique set may or may not be returned.
     * @param studentTestScoreRecordIds a list of StudentTestScoreRecord identifiers
     * @param contextInfo
     * @return a List of StudentTestScoreRecordInfo
     * @throws DoesNotExistException studentTestScoreRecordIds are not found
     * @throws InvalidParameterException studentTestScoreRecordIds or contextInfo is not valid
     * @throws MissingParameterException studentTestScoreRecordIds or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<StudentTestScoreRecordInfo> getStudentTestScoreRecordsByIds(@WebParam(name = "studentTestScoreRecordIds") List<String> studentTestScoreRecordIds,
                                                                            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of StudentTestScoreRecord Ids by StudentTestScoreRecord Type.
     * @param studentTestScoreTypeKey
     * @param contextInfo
     * @return a list of Object identifiers matching objectTypeKey or an empty list if none found
     * @throws InvalidParameterException studentTestScoreTypeKey or contextInfo is not valid
     * @throws MissingParameterException studentTestScoreTypeKey or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getStudentTestScoreIdsByType(@WebParam(name = "studentTestScoreTypeKey") String studentTestScoreTypeKey,
                                                     @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for StudentTestScoreRecord Ids that meet the given search criteria.
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return a list of StudentTestScoreRecordIds
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForStudentTestScoreRecordIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                           @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for StudentTestScoreRecords that meet the given search criteria.
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return a list of StudentTestScoreRecords
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<StudentTestScoreRecordInfo> searchForStudentTestScoreRecords(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                                             @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     *
     * Validates a StudentTestScoreRecord. If an identifier is present for the
     * StudentTestScoreRecord and a record is found for that identifier, the validation
     * checks if the StudentTestScoreRecord can be updated to the new values.
     * If an identifier is not present or a record does not exist, the validation
     * checks if the StudentTestScoreRecord with the given data can be created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param studentTestScoreRecordTypeKey the identifier for the Object Type to be validated
     * @param studentTestScoreRecord the studentTestScoreRecord to be validated
     * @param contextInfo Context information containing the principalId and locale information about the caller of service operation
     * @return a list of validation results or an empty list if validation succeeded
     * @throws DoesNotExistException validationTypeKey or objectTypeKey is not found
     * @throws InvalidParameterException studentTestScoreRecordInfo or contextInfo is not valid
     * @throws MissingParameterException validationTypeKey, objectTypeKey, studentTestScoreRecordInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateStudentTestScoreRecord(@WebParam(name = "validationTypeKey") String validationTypeKey,
                                                                     @WebParam(name = "studentTestScoreRecordTypeKey") String studentTestScoreRecordTypeKey,
                                                                     @WebParam(name = "studentTestScoreRecord") StudentTestScoreRecordInfo studentTestScoreRecord,
                                                                     @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new StudentTestScoreRecord. The StudentTestScoreRecord Id, Type, and Meta information may not
     * be set in the supplied data object.
     *
     * @param studentTestScoreRecordTypeKey
     * @param personId
     * @param studentTestScoreRecord
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the newly created StudentTestScoreRecordInfo
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException objectTypeKey does not exist or is not supported
     * @throws InvalidParameterException StudentTestScoreRecord or contextInfo is not valid
     * @throws MissingParameterException objectTypeKey, studentTestScoreRecord, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information designated as read only
     */
    public StudentTestScoreRecordInfo createStudentTestScoreRecord(@WebParam(name = "studentTestScoreRecordTypeKey") String studentTestScoreRecordTypeKey,
                                                                   @WebParam(name = "personId") String personId,
                                                                   @WebParam(name = "studentTestScoreRecord") StudentTestScoreRecordInfo studentTestScoreRecord,
                                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates an existing StudentTestScoreRecord. The StudentTestScoreRecord Id, Type, and Meta
     * information may not be changed.
     * @param studentTestScoreRecordId
     * @param studentTestScoreRecord
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the updated StudentTestScoreRecordInfo
     * @throws DataValidationErrorException
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws ReadOnlyException
     */
    public StudentTestScoreRecordInfo updateStudentTestScoreRecord(@WebParam(name = "studentTestScoreRecordId") String studentTestScoreRecordId,
                                                                   @WebParam(name = "studentTestScoreRecord") StudentTestScoreRecordInfo studentTestScoreRecord,
                                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Deletes an existing StudentTestScoreRecord
     * @param studentTestScoreRecordId  the identifier for the Object to be deleted
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the status of the delete operation. This must always be true.
     * @throws DataValidationErrorException
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public StatusInfo deleteStudentTestScoreRecord(@WebParam(name = "studentTestScoreRecordId") String studentTestScoreRecordId,
                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a single GPA by a GPA Id.
     * @param gpaId the id of the GPA
     * @param contextInfo Context information containing the principalId and locale information about the caller of service operation
     * @return the GPAInfo
     * @throws DoesNotExistException gpaId is not found
     * @throws InvalidParameterException gpaId or contextInfo is not valid
     * @throws MissingParameterException gpaId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public GPAInfo getGpa(@WebParam(name = "gpaId") String gpaId,
                                           @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of GPAs from a list of GPA Ids. The returned
     * list may be in any order and if duplicate Ids are supplied, a unique set may or may not be returned.
     * @param gpaIds a list of GPA identifiers
     * @param contextInfo
     * @return a List of GPAInfo
     * @throws DoesNotExistException gpaIds are not found
     * @throws InvalidParameterException gpaIds or contextInfo is not valid
     * @throws MissingParameterException gpaIds or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<GPAInfo> getGpasByIds(@WebParam(name = "gpaId") List<String> gpaIds,
                                      @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of GPA Ids by GPA Type.
     * @param gpaTypeKey
     * @param contextInfo
     * @return a list of Object identifiers matching objectTypeKey or an empty list if none found
     * @throws InvalidParameterException gpaTypeKey or contextInfo is not valid
     * @throws MissingParameterException gpaTypeKey or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getGpaIdsByType(@WebParam(name = "gpaTypeKey") String gpaTypeKey,
                                        @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for GPAs that meet the given search criteria.
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return a list of GPAs
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<GPAInfo> searchForGpas(@WebParam(name = "criteria") QueryByCriteria criteria,
                                       @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for GPA Ids that meet the given search criteria.
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return a list of GpaIds
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForGpaIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                        @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Validates a GPA. If an identifier is present for the
     * GPA and a record is found for that identifier, the validation
     * checks if the GPA can be updated to the new values.
     * If an identifier is not present or a record does not exist, the validation
     * checks if the GPA with the given data can be created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param gpaTypeKey the identifier for the Object Type to be validated
     * @param gpa the gpa to be validated
     * @param contextInfo Context information containing the principalId and locale information about the caller of service operation
     * @return a list of validation results or an empty list if validation succeeded
     * @throws DoesNotExistException validationTypeKey or objectTypeKey is not found
     * @throws InvalidParameterException gpaInfo or contextInfo is not valid
     * @throws MissingParameterException validationTypeKey, objectTypeKey, gpaInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateGPA(@WebParam(name = "validationTypeKey") String validationTypeKey,
                                                  @WebParam(name = "gpaTypeKey") String gpaTypeKey,
                                                  @WebParam(name = "gpa") GPAInfo gpa,
                                                  @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new GPA. The GPA Id, Type, and Meta information may not be set in the supplied data object.
     * @param gpaTypeKey
     * @param gpa
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the newly created GPAInfo
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException objectTypeKey does not exist or is not supported
     * @throws InvalidParameterException gpa or contextInfo is not valid
     * @throws MissingParameterException objectTypeKey, gpa, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information designated as read only
     */
    public GPAInfo createGPA(@WebParam(name = "gpaTypeKey") String gpaTypeKey,
                             @WebParam(name = "gpa") GPAInfo gpa,
                             @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates an existing GPA. The GPA Id, Type, and Meta information may not be changed.
     * @param gpaId
     * @param gpa
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the updated GPAInfo
     * @throws DataValidationErrorException
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws ReadOnlyException
     */
    public GPAInfo updateGPA(@WebParam(name = "gpaId") String gpaId,
                             @WebParam(name = "gpa") GPAInfo gpa,
                             @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Deletes an existing GPA
     * @param gpaId  the identifier for the Object to be deleted
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the status of the delete operation. This must always be true.
     * @throws DataValidationErrorException
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public StatusInfo deleteGPA(@WebParam(name = "gpaId") String gpaId,
                                @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a single Load by a Load Id.
     * @param loadId the id of the Load
     * @param contextInfo Context information containing the principalId and locale information about the caller of service operation
     * @return the LoadInfo
     * @throws DoesNotExistException loadId is not found
     * @throws InvalidParameterException loadId or contextInfo is not valid
     * @throws MissingParameterException loadId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public LoadInfo getLoad(@WebParam(name = "loadId") String loadId,
                                           @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Loads from a list of Load Ids. The returned
     * list may be in any order and if duplicate Ids are supplied, a unique set may or may not be returned.
     * @param loadIds a list of Load identifiers
     * @param contextInfo
     * @return a List of LoadInfo
     * @throws DoesNotExistException loadIds are not found
     * @throws InvalidParameterException loadIds or contextInfo is not valid
     * @throws MissingParameterException loadIds or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<LoadInfo> getLoadsByIds(@WebParam(name = "loadIds") List<String> loadIds,
                                      @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Load Ids by Load Type.
     * @param loadTypeKey
     * @param contextInfo
     * @return a list of Object identifiers matching objectTypeKey or an empty list if none found
     * @throws InvalidParameterException loadTypeKey or contextInfo is not valid
     * @throws MissingParameterException loadTypeKey or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getLoadIdsByType(@WebParam(name = "loadTypeKey") String loadTypeKey,
                                         @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for Load Ids that meet the given search criteria.
     * @param criteria the search criteria
     * @param contextInfo information containing the principalId and locale information about the caller of the service operation
     * @return a list of LoadIds
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForLoadIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                        @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     *
     * Validates a Load. If an identifier is present for the
     * Load and a record is found for that identifier, the validation
     * checks if the Load can be updated to the new values.
     * If an identifier is not present or a record does not exist, the validation
     * checks if the Load with the given data can be created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param loadTypeKey the identifier for the Object Type to be validated
     * @param load the load to be validated
     * @param contextInfo Context information containing the principalId and locale information about the caller of service operation
     * @return a list of validation results or an empty list if validation succeeded
     * @throws DoesNotExistException validationTypeKey or objectTypeKey is not found
     * @throws InvalidParameterException loadInfo or contextInfo is not valid
     * @throws MissingParameterException validationTypeKey, objectTypeKey, loadInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateLoad(@WebParam(name = "validationTypeKey") String validationTypeKey,
                                                   @WebParam(name = "loadTypeKey") String loadTypeKey,
                                                   @WebParam(name = "load") LoadInfo load,
                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new Load. The Load Id, Type, and Meta information may not be set in the supplied data object.
     *
     * @param loadTypeKey
     * @param load
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the newly created LoadInfo
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException objectTypeKey does not exist or is not supported
     * @throws InvalidParameterException load or contextInfo is not valid
     * @throws MissingParameterException objectTypeKey, load, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException an attempt at supplying information designated as read only
     */
    public LoadInfo createLoad(@WebParam(name = "loadTypeKey") String loadTypeKey,
                               @WebParam(name = "load") LoadInfo load,
                               @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates an existing Load. The Load Id, Type, and Meta information may not be changed.
     * @param loadId
     * @param load
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the updated LoadInfo
     * @throws DataValidationErrorException
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     * @throws ReadOnlyException
     */
    public LoadInfo updateLoad(@WebParam(name = "loadId") String loadId,
                               @WebParam(name = "load") LoadInfo load,
                               @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Deletes an existing Load
     * @param loadId  the identifier for the Object to be deleted
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return the status of the delete operation. This must always be true.
     * @throws DataValidationErrorException
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public StatusInfo deleteLoad(@WebParam(name = "loadId") String loadId,
                                 @WebParam(name = "contextInfo") ContextInfo contextInfo) throws
            DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

}



