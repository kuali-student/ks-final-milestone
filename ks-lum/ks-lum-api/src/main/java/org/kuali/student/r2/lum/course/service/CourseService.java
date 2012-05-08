/*
 * Copyright 2009 The Kuali Foundation Licensed under the Educational Community
 * License, Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.kuali.student.r2.lum.course.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r1.common.dictionary.service.DictionaryService;
import org.kuali.student.r2.common.exceptions.IllegalVersionSequencingException;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;

import org.kuali.student.r2.common.exceptions.CircularReferenceException;
import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.UnsupportedActionException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;

import org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;

import org.kuali.student.r2.lum.course.dto.ActivityInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;



/**
 * The Program Service allows for the creation and management of courses.
 * 
 * @author Kuali Student Team (sambitpa@kuali.org)
 */
@WebService(name = "CourseService", targetNamespace = CourseServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface CourseService extends DictionaryService {
    /**
     * Retrieves a Course
     * 
     * @param courseId Unique Id of the Course. Maps to cluId
     * @return the created course
     * @throws DoesNotExistException Course does not exist
     * @throws InvalidParameterException invalid course
     * @throws MissingParameterException missing Course
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public CourseInfo getCourse(@WebParam(name = "courseId") String courseId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of courses by Ids
     * 
     * @param courseIds
     * @param contextInfo
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public List<CourseInfo> getCoursesByIds(@WebParam(name = "courseIds") List<String> courseIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for courses based on the criteria and returns a list of Comment
     * identifiers which match the search criteria.
     * 
     * @param criteria the search criteria
     * @param contextInfo Context information containing the principalId and
     *            locale information about the caller of service operation
     * @return list of Comment Ids
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException criteria, contextInfo not specified
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> searchForCourseIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for courses based on the criteria and returns a list of Comments
     * which match the search criteria.
     * 
     * @param criteria the search criteria
     * @param contextInfo Context information containing the principalId and
     *            locale information about the caller of service operation
     * @return list of Comment information
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException criteria, contextInfo not specified
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CourseInfo> searchForCourses(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a Course
     * 
     * @param courseInfo courseInfo
     * @return the created course
     * @throws DataValidationErrorException One or more values invalid for this
     *             operation
     * @throws InvalidParameterException invalid course
     * @throws MissingParameterException missing Course
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException
     * @throws DependentObjectsExistException
     */
    public CourseInfo createCourse(@WebParam(name = "courseInfo") CourseInfo courseInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /**
     * Updates a Course.
     * 
     * @param courseInfo courseInfo
     * @param courseId
     * @return updated Course
     * @throws DataValidationErrorException One or more values invalid for this
     *             operation
     * @throws DoesNotExistException course not found
     * @throws InvalidParameterException invalid course
     * @throws MissingParameterException missing course
     * @throws VersionMismatchException The action was attempted on an out of
     *             date version.
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws CircularReferenceException 
     * @throws CircularRelationshipException 
     * @throws AlreadyExistsException 
     * @throws DependentObjectsExistException 
     * @throws UnsupportedActionException 
     */
    public CourseInfo updateCourse(@WebParam(name = "courseId") String courseId, @WebParam(name = "courseInfo") CourseInfo courseInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException,
            PermissionDeniedException, UnsupportedActionException, DependentObjectsExistException, AlreadyExistsException, CircularRelationshipException, CircularReferenceException, ReadOnlyException;

    /**
     * Deletes a Course.
     * 
     * @param courseId identifier for Course.Maps to cluId
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException Course does not exist
     * @throws InvalidParameterException invalid courseId
     * @throws MissingParameterException invalid courseId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws CircularReferenceException 
     * @throws CircularRelationshipException 
     * @throws DependentObjectsExistException 
     * @throws UnsupportedActionException 
     */
    public StatusInfo deleteCourse(@WebParam(name = "courseId") String courseId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DataValidationErrorException, AlreadyExistsException, UnsupportedActionException, DependentObjectsExistException, CircularRelationshipException, CircularReferenceException, ReadOnlyException;

    /**
     * Validates a course based on its dictionary
     * 
     * @param validationType identifier of the extent of validation
     * @param courseInfo Course to be validated
     * @return results from performing the validation
     * @throws DoesNotExistException Course does not exist
     * @throws InvalidParameterException invalid courseId
     * @throws MissingParameterException invalid courseId
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateCourse(String validationType, CourseInfo courseInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves the formats for a Course.
     * 
     * @param courseId Unique Id of the Course. Maps to cluId
     * @return a list of format info Structures
     * @throws DoesNotExistException Course does not exist
     * @throws InvalidParameterException invalid courseId
     * @throws MissingParameterException invalid courseId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<FormatInfo> getCourseFormatsByCourse(@WebParam(name = "courseId") String courseId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the Activities for a Course.
     * 
     * @param formatId Unique Id of the Format. Maps to cluId
     * @return a list of activity info Structures
     * @throws DoesNotExistException Format does not exist
     * @throws InvalidParameterException invalid format
     * @throws MissingParameterException missing Format
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ActivityInfo> getCourseActivitiesByCourseFormat(@WebParam(name = "formatId") String formatId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the Learning Objectives for a Course.
     * 
     * @param courseId Unique Id of the Course. Maps to cluId
     * @return a list of LoDisplay info Structures
     * @throws DoesNotExistException Course does not exist
     * @throws InvalidParameterException invalid courseId
     * @throws MissingParameterException invalid courseId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<LoDisplayInfo> getCourseLearningObjectivesByCourse(@WebParam(name = "courseId") String courseId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the Statements of a Course.
     * 
     * @param courseId Unique Id of the Course. Maps to cluId
     * @param nlUsageTypeKey Natural language usage type key (context)
     * @param language Translation language e.g en, es, gr
     * @return a list of Statement tree Structures
     * @throws DoesNotExistException Course does not exist
     * @throws InvalidParameterException invalid courseId
     * @throws MissingParameterException invalid courseId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<StatementTreeViewInfo> getCourseStatements(@WebParam(name = "courseId") String courseId, @WebParam(name = "nlUsageTypeKey") String nlUsageTypeKey,
            @WebParam(name = "language") String language, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

    /**
     * Creates the Statement for a Course.
     * 
     * @param courseId Unique Id of the Course. Maps to cluId
     * @param statementTreeViewInfoList a Statementree Structures
     * @return created Statementree Structures
     * @throws DoesNotExistException Course does not exist
     * @throws InvalidParameterException invalid courseId
     * @throws MissingParameterException invalid courseId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws DataValidationErrorException
     */
    public StatementTreeViewInfo createCourseStatement(@WebParam(name = "courseId") String courseId, @WebParam(name = "statementTreeViewInfo") StatementTreeViewInfo statementTreeViewInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, DataValidationErrorException;

    /**
     * Updates the Statement for a Course.
     * 
     * @param courseId Unique Id of the Course. Maps to cluId
     * @param statementId
     * @param statementTreeViewInfoList a Statementree Structures
     * @return updated Statementree Structures
     * @throws DoesNotExistException Course does not exist
     * @throws InvalidParameterException invalid courseId
     * @throws MissingParameterException invalid courseId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException
     * @throws DataValidationErrorException
     */
    public StatementTreeViewInfo updateCourseStatement(@WebParam(name = "courseId") String courseId, @WebParam(name = "statementTreeId") String statementId,
            @WebParam(name = "statementTreeViewInfo") StatementTreeViewInfo statementTreeViewInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, VersionMismatchException;

    /**
     * Delete the Statement for a Course.
     * 
     * @param courseId Unique Id of the Course. Maps to cluId
     * @param statementTreeViewInfoList a Statementree Structures
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException Course does not exist
     * @throws InvalidParameterException invalid courseId
     * @throws MissingParameterException invalid courseId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteCourseStatement(@WebParam(name = "courseId") String courseId, @WebParam(name = "statementTreeViewInfo") StatementTreeViewInfo statementTreeViewInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException;

    /**
     * Validates the Statement for a Course.
     * 
     * @param courseId Unique Id of the Course. Maps to cluId
     * @param statementTreeViewInfoList a Statementree Structures
     * @return results from performing the validation
     * @throws DoesNotExistException Course or StementTreeView does not exist
     * @throws InvalidParameterException invalid courseId or stratement tree
     *             view Id
     * @throws MissingParameterException invalid courseId or statement tree view
     *             Id
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException 
     */
    public List<ValidationResultInfo> validateCourseStatement(@WebParam(name = "courseId") String courseId, @WebParam(name = "statementTreeViewInfo") StatementTreeViewInfo statementTreeViewInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new Course version based on the current course
     * 
     * @param courseId identifier for the Course to be versioned
     * @param versionComment comment for the current version
     * @return the new versioned Course information
     * @throws DataValidationErrorException One or more values invalid for this
     *             operation
     * @throws DoesNotExistException courseId not found
     * @throws InvalidParameterException invalid courseId
     * @throws MissingParameterException missing courseId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of
     *             date version
     * @throws ReadOnlyException 
     */
    public CourseInfo createNewCourseVersion(@WebParam(name = "courseId") String courseId, @WebParam(name = "versionComment") String versionComment,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, VersionMismatchException, ReadOnlyException;

    /**
     * Sets a specific version of the Course as current. The sequence number
     * must be greater than the existing current Course version. This will
     * truncate the current version's end date to the currentVersionStart param.
     * If a Course exists which is set to become current in the future, that
     * course's currentVersionStart and CurrentVersionEnd will be nullified. The
     * currentVersionStart must be in the future to prevent changing historic
     * data.
     * 
     * @param courseVersionId Version Specific Id of the Course
     * @param currentVersionStart Date when this course becomes current. Must be
     *            in the future and be after the most current course's start
     *            date.
     * @return status of the operation
     * @throws DoesNotExistException courseVersionId not found
     * @throws InvalidParameterException invalid courseVersionId, previousState,
     *             newState
     * @throws MissingParameterException missing courseVersionId, previousState,
     *             newState
     * @throws IllegalVersionSequencingException a Course with higher sequence
     *             number from the one provided is marked current
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws IllegalVersionSequencingException 
     * @throws DataValidationErrorException 
     */
    public StatusInfo setCurrentCourseVersion(@WebParam(name = "courseVersionId") String courseVersionId, @WebParam(name = "currentVersionStart") Date currentVersionStart,
            @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, 
            OperationFailedException, PermissionDeniedException, IllegalVersionSequencingException, DataValidationErrorException;
    
    /**
     * Retrieves current version associated with the objectId.
     * 
     * @param refObjectTypeURI
     *            reference object type URI 
     * @param refObjectId
     *            reference object Id
     * @return current version
     * @throws DoesNotExistException
     *             specified refObjectId, refObjectTypeURI not found
     * @throws InvalidParameterException
     *             invalid refObjectId, refObjectTypeURI
     * @throws MissingParameterException
     *            refObjectId, refObjectTypeURI not specified
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    @Deprecated
    public VersionDisplayInfo getCurrentVersion(@WebParam(name = "refObjectTypeURI") String refObjectTypeURI, @WebParam(name = "refObjectId") String refObjectId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    
    @Deprecated
    public List<VersionDisplayInfo> getVersions(String refObjectTypeURI,
            String refObjectId, ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException;

}