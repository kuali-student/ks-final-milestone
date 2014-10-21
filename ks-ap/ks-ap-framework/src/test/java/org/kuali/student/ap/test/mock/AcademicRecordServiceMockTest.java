package org.kuali.student.ap.test.mock;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.enrollment.academicrecord.dto.GPAInfo;
import org.kuali.student.enrollment.academicrecord.dto.LoadInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentCredentialRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentProgramRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentTestScoreRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
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
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

import javax.jws.WebParam;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: johglove
 * Date: 11/19/13
 * Time: 9:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class AcademicRecordServiceMockTest implements AcademicRecordService{
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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          personId or termId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          personId, termId or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<StudentCourseRecordInfo> getAttemptedCourseRecordsForTerm(String personId, String termId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StudentCourseRecordInfo getStudentCourseRecord(String studentCourseRecordId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<StudentCourseRecordInfo> getStudentCourseRecordsByIds(List<String> studentCourseRecordIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<String> getStudentCourseRecordIdsByType(String studentCourseRecordTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    /**
     * This method returns a list of StudentCourseRecord for a student where
     * each returned course is a course the student completed for any term.
     *
     * @param personId    an Id of a student
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return a list of StudentCourseRecords
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          personId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          personId or contextInfo is missing or
     *          null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<StudentCourseRecordInfo> getCompletedCourseRecords(String personId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<StudentCourseRecordInfo> getStudentCourseRecordsForCourse(String personId, String courseId, ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException {
        return null;
    }

    @Override
    public List<StudentCourseRecordInfo> getStudentCourseRecordsForCourses(String personId, List<String> courseIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<String> searchForStudentCourseRecordIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<StudentCourseRecordInfo> searchForStudentCourseRecords(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

    @Override
    public List<ValidationResultInfo> validateStudentCourseRecord(String validationTypeKey, String studentCourseRecordTypeKey, StudentCourseRecordInfo studentCourseRecordInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          personId or courseId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          personId, courseId or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<StudentCourseRecordInfo> getCompletedCourseRecordsForCourse(String personId, @WebParam(name = "courseId") String courseId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          personId or termId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          personId, termId or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<StudentCourseRecordInfo> getCompletedCourseRecordsForTerm(String personId, String termId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StudentCourseRecordInfo createStudentCourseRecord(String personId, String studentCourseRecordTypeKey, @WebParam(name = "studentCourseRecord") StudentCourseRecordInfo studentCourseRecord, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;
    }

    @Override
    public StudentCourseRecordInfo updateStudentCourseRecord(String studentCourseRecordId, @WebParam(name = "studentCourseRecord") StudentCourseRecordInfo studentCourseRecord, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;
    }

    @Override
    public StatusInfo deleteStudentCourseRecord(String studentCourseRecordId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          personId, termId or calculationTypeKey
     *          not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          personId, termId, calculationTypeKey or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public GPAInfo getGPAForTerm(String personId, String termId, @WebParam(name = "calculationTypeKey") String calculationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          personId or calculationTypeKey not
     *          found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          personId, calculationTypeKey or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public GPAInfo getCumulativeGPA(String personId, @WebParam(name = "calculationTypeKey") String calculationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * This method returns the cumulative GPA of a student.
     *
     * @param studentCourseRecordInfoList The list of student course records that should
     *                                    be used to calculate the GPA.
     * @param calculationTypeKey          Unique key identifying the calculation. For
     *                                    example, it may point to a description of rules
     *                                    such as A+ count more than A and do honors
     *                                    classes count more
     * @param contextInfo                 Context information containing the principalId
     *                                    and locale information about the caller of
     *                                    service operation
     * @return calculated GPA
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          calculationTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          studentCourseRecordInfoList, calculationTypeKey
     *          or contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public GPAInfo calculateGPA(@WebParam(name = "studentCourseRecordInfoList") List<StudentCourseRecordInfo> studentCourseRecordInfoList, @WebParam(name = "calculationTypeKey") String calculationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          personId, programId or calculationTypeKey
     *          not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          personId, programId, calculationTypeKey
     *          or contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public GPAInfo getCumulativeGPAForProgram(String personId, @WebParam(name = "programId") String programId, @WebParam(name = "calculationTypeKey") String calculationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          personId, programId, termKey or
     *          calculationTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          personId, programId, termKey,
     *          calculationTypeKey or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public GPAInfo getCumulativeGPAForTermAndProgram(String personId, @WebParam(name = "programId") String programId, @WebParam(name = "termKey") String termKey, @WebParam(name = "calculationTypeKey") String calculationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          personId, termId or calculationTypeKey
     *          not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          personId, termId, calculationTypeKey or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public LoadInfo getLoadForTerm(String personId, String termId, @WebParam(name = "calculationTypeKey") String calculationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * This method returns the summary of student programs (includes currently
     * enrolled, completed and not completed)
     *
     * @param personId    an Id of a student
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return a list of programs
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          personId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          personId or contextInfo is missing or
     *          null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<StudentProgramRecordInfo> getProgramRecords(String personId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Get credentials that have been awarded by this institution to the
     * student
     *
     * @param personId    an Id of a student
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return a list of credentials
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          personId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          personId or contextInfo is missing or
     *          null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<StudentCredentialRecordInfo> getAwardedCredentials(String personId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Get a student's test scores
     *
     * @param personId    an Id of a student
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return a list of test scores
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          personId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          personId or contextInfo is missing or
     *          null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<StudentTestScoreRecordInfo> getTestScoreRecords(String personId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Get a student's test scores by test type
     *
     * @param personId    an Id of a student
     * @param testTypeKey a key for the test type
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return a list of test scores
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          personId or testTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          personId, testTypeKey or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public List<StudentTestScoreRecordInfo> getTestScoreRecordsByType(String personId, @WebParam(name = "testTypeKey") String testTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          personId, termId or calculationTypeKey
     *          not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          personId, termId, calculationTypeKey or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public String getEarnedCreditsForTerm(String personId, String termId, @WebParam(name = "calculationTypeKey") String calculationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          personId or calculationTypeKey not
     *          found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          personId, calculationTypeKey or
     *          contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public String getEarnedCredits(String personId, @WebParam(name = "calculationTypeKey") String calculationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

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
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          personId, programId, termId or
     *          calculationTypeKey not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          personId, programId, termId,
     *          calculationTypeKey or contextInfo is
     *          missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          an authorization failure occurred
     */
    @Override
    public String getEarnedCumulativeCreditsForProgramAndTerm(String personId, @WebParam(name = "programId") String programId, String termId, @WebParam(name = "calculationTypeKey") String calculationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
