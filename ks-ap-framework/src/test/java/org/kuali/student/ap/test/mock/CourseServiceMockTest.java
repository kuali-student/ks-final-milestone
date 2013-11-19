package org.kuali.student.ap.test.mock;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.CircularReferenceException;
import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.IllegalVersionSequencingException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.UnsupportedActionException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;
import org.kuali.student.r2.lum.course.service.CourseService;

import javax.jws.WebParam;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: johglove
 * Date: 11/19/13
 * Time: 10:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class CourseServiceMockTest implements CourseService {
    /**
     * Retrieves a Course
     *
     * @param courseId Unique Id of the Course. Maps to cluId
     * @return the created course
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          Course does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid course
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing Course
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public CourseInfo getCourse(@WebParam(name = "courseId") String courseId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves a list of courses by Ids
     *
     * @param courseIds
     * @param contextInfo
     * @return
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *
     */
    @Override
    public List<CourseInfo> getCoursesByIds(@WebParam(name = "courseIds") List<String> courseIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Searches for courses based on the criteria and returns a list of Comment
     * identifiers which match the search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service operation
     * @return list of Comment Ids
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid parameter
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          criteria, contextInfo not specified
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<String> searchForCourseIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Searches for courses based on the criteria and returns a list of Comments
     * which match the search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service operation
     * @return list of Comment information
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid parameter
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          criteria, contextInfo not specified
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<CourseInfo> searchForCourses(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Creates a Course
     *
     * @param courseInfo courseInfo
     * @return the created course
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this
     *          operation
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid course
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing Course
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *
     * @throws org.kuali.student.r2.common.exceptions.DependentObjectsExistException
     *
     */
    @Override
    public CourseInfo createCourse(@WebParam(name = "courseInfo") CourseInfo courseInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Updates a Course.
     *
     * @param courseInfo courseInfo
     * @param courseId
     * @return updated Course
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this
     *          operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          course not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid course
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing course
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          The action was attempted on an out of
     *          date version.
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.CircularReferenceException
     *
     * @throws org.kuali.student.r2.common.exceptions.CircularRelationshipException
     *
     * @throws org.kuali.student.r2.common.exceptions.AlreadyExistsException
     *
     * @throws org.kuali.student.r2.common.exceptions.DependentObjectsExistException
     *
     * @throws org.kuali.student.r2.common.exceptions.UnsupportedActionException
     *
     */
    @Override
    public CourseInfo updateCourse(@WebParam(name = "courseId") String courseId, @WebParam(name = "courseInfo") CourseInfo courseInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException, UnsupportedActionException, DependentObjectsExistException, AlreadyExistsException, CircularRelationshipException, CircularReferenceException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Deletes a Course.
     *
     * @param courseId identifier for Course.Maps to cluId
     * @return status of the operation (success or failure)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          Course does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid courseId
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          invalid courseId
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.CircularReferenceException
     *
     * @throws org.kuali.student.r2.common.exceptions.CircularRelationshipException
     *
     * @throws org.kuali.student.r2.common.exceptions.DependentObjectsExistException
     *
     * @throws org.kuali.student.r2.common.exceptions.UnsupportedActionException
     *
     */
    @Override
    public StatusInfo deleteCourse(@WebParam(name = "courseId") String courseId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DataValidationErrorException, AlreadyExistsException, UnsupportedActionException, DependentObjectsExistException, CircularRelationshipException, CircularReferenceException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Validates a course based on its dictionary
     *
     * @param validationType identifier of the extent of validation
     * @param courseInfo     Course to be validated
     * @return results from performing the validation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          Course does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid courseId
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          invalid courseId
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     */
    @Override
    public List<ValidationResultInfo> validateCourse(String validationType, CourseInfo courseInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the formats for a Course.
     *
     * @param courseId Unique Id of the Course. Maps to cluId
     * @return a list of format info Structures
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          Course does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid courseId
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          invalid courseId
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<FormatInfo> getCourseFormatsByCourse(@WebParam(name = "courseId") String courseId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the Activities for a Course.
     *
     * @param formatId Unique Id of the Format. Maps to cluId
     * @return a list of activity info Structures
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          Format does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid format
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing Format
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<ActivityInfo> getCourseActivitiesByCourseFormat(@WebParam(name = "formatId") String formatId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the Learning Objectives for a Course.
     *
     * @param courseId Unique Id of the Course. Maps to cluId
     * @return a list of LoDisplay info Structures
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          Course does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid courseId
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          invalid courseId
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<LoDisplayInfo> getCourseLearningObjectivesByCourse(@WebParam(name = "courseId") String courseId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves the Statements of a Course.
     *
     * @param courseId       Unique Id of the Course. Maps to cluId
     * @param nlUsageTypeKey Natural language usage type key (context)
     * @param language       Translation language e.g en, es, gr
     * @return a list of Statement tree Structures
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          Course does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid courseId
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          invalid courseId
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<StatementTreeViewInfo> getCourseStatements(@WebParam(name = "courseId") String courseId, @WebParam(name = "nlUsageTypeKey") String nlUsageTypeKey, @WebParam(name = "language") String language, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Creates the Statement for a Course.
     *
     * @param courseId                  Unique Id of the Course. Maps to cluId
     * @param statementTreeViewInfoList a Statementree Structures
     * @return created Statementree Structures
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          Course does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid courseId
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          invalid courseId
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *
     */
    @Override
    public StatementTreeViewInfo createCourseStatement(@WebParam(name = "courseId") String courseId, @WebParam(name = "statementTreeViewInfo") StatementTreeViewInfo statementTreeViewInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Updates the Statement for a Course.
     *
     * @param courseId                  Unique Id of the Course. Maps to cluId
     * @param statementId
     * @param statementTreeViewInfoList a Statementree Structures
     * @return updated Statementree Structures
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          Course does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid courseId
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          invalid courseId
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *
     */
    @Override
    public StatementTreeViewInfo updateCourseStatement(@WebParam(name = "courseId") String courseId, @WebParam(name = "statementTreeId") String statementId, @WebParam(name = "statementTreeViewInfo") StatementTreeViewInfo statementTreeViewInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Delete the Statement for a Course.
     *
     * @param courseId                  Unique Id of the Course. Maps to cluId
     * @param statementTreeViewInfoList a Statementree Structures
     * @return status of the operation (success or failure)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          Course does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid courseId
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          invalid courseId
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo deleteCourseStatement(@WebParam(name = "courseId") String courseId, @WebParam(name = "statementTreeViewInfo") StatementTreeViewInfo statementTreeViewInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Validates the Statement for a Course.
     *
     * @param courseId                  Unique Id of the Course. Maps to cluId
     * @param statementTreeViewInfoList a Statementree Structures
     * @return results from performing the validation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          Course or StementTreeView does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid courseId or stratement tree
     *          view Id
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          invalid courseId or statement tree view
     *          Id
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *
     */
    @Override
    public List<ValidationResultInfo> validateCourseStatement(@WebParam(name = "courseId") String courseId, @WebParam(name = "statementTreeViewInfo") StatementTreeViewInfo statementTreeViewInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Creates a new Course version based on the current course
     *
     * @param courseId       identifier for the Course to be versioned
     * @param versionComment comment for the current version
     * @return the new versioned Course information
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this
     *          operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          courseId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid courseId
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing courseId
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
     *          The action was attempted on an out of
     *          date version
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException
     *
     */
    @Override
    public CourseInfo createNewCourseVersion(@WebParam(name = "courseId") String courseId, @WebParam(name = "versionComment") String versionComment, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Sets a specific version of the Course as current. The sequence number
     * must be greater than the existing current Course version. This will
     * truncate the current version's end date to the currentVersionStart param.
     * If a Course exists which is set to become current in the future, that
     * course's currentVersionStart and CurrentVersionEnd will be nullified. The
     * currentVersionStart must be in the future to prevent changing historic
     * data.
     *
     * @param courseVersionId     Version Specific Id of the Course
     * @param currentVersionStart Date when this course becomes current. Must be
     *                            in the future and be after the most current course's start
     *                            date.
     * @return status of the operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          courseVersionId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid courseVersionId, previousState,
     *          newState
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          missing courseVersionId, previousState,
     *          newState
     * @throws org.kuali.student.r2.common.exceptions.IllegalVersionSequencingException
     *          a Course with higher sequence
     *          number from the one provided is marked current
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.r2.common.exceptions.IllegalVersionSequencingException
     *
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
     *
     */
    @Override
    public StatusInfo setCurrentCourseVersion(@WebParam(name = "courseVersionId") String courseVersionId, @WebParam(name = "currentVersionStart") Date currentVersionStart, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, IllegalVersionSequencingException, DataValidationErrorException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Retrieves current version associated with the objectId.
     *
     * @param refObjectTypeURI reference object type URI
     * @param refObjectId      reference object Id
     * @return current version
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
     *          specified refObjectId, refObjectTypeURI not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
     *          invalid refObjectId, refObjectTypeURI
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
     *          refObjectId, refObjectTypeURI not specified
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public VersionDisplayInfo getCurrentVersion(@WebParam(name = "refObjectTypeURI") String refObjectTypeURI, @WebParam(name = "refObjectId") String refObjectId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<VersionDisplayInfo> getVersions(String refObjectTypeURI, String refObjectId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getObjectTypes() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ObjectStructureDefinition getObjectStructure(@WebParam(name = "objectTypeKey") String objectTypeKey) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
