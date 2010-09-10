/*
 * Copyright 2009 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.lum.course.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.core.dictionary.service.DictionaryService;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.CircularReferenceException;
import org.kuali.student.core.exceptions.CircularRelationshipException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.IllegalVersionSequencingException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.UnsupportedActionException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.core.versionmanagement.service.VersionManagementService;
import org.kuali.student.lum.course.dto.ActivityInfo;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.dto.FormatInfo;
import org.kuali.student.lum.course.dto.LoDisplayInfo;

/**
 * @Author KSContractMojo
 * @Author Kamal
 * @Since Tue May 18 11:30:50 PDT 2010
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/Course+Service">CourseService</>
 */
@WebService(name = "CourseService", targetNamespace = "http://student.kuali.org/wsdl/course")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface CourseService extends DictionaryService, VersionManagementService {
    /**
     * Retrieves a Course
     *
     * @param courseId
     *            Unique Id of the Course. Maps to cluId
     * @return the created course
     * @throws DoesNotExistException
     *             Course does not exist
     * @throws InvalidParameterException
     *             invalid course
     * @throws MissingParameterException
     *             missing Course
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public CourseInfo getCourse(@WebParam(name = "courseId") String courseId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the formats for a Course.
     *
     * @param courseId
     *            Unique Id of the Course. Maps to cluId
     * @return a list of format info Structures
     * @throws DoesNotExistException
     *             Course does not exist
     * @throws InvalidParameterException
     *             invalid courseId
     * @throws MissingParameterException
     *             invalid courseId
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public List<FormatInfo> getCourseFormats(@WebParam(name = "courseId") String courseId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the Activities for a Course.
     *
     * @param formatId
     *            Unique Id of the Format. Maps to cluId
     * @return a list of activity info Structures
     * @throws DoesNotExistException
     *             Format does not exist
     * @throws InvalidParameterException
     *             invalid format
     * @throws MissingParameterException
     *             missing Format
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public List<ActivityInfo> getCourseActivities(@WebParam(name = "formatId") String formatId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the Los for a Course.
     *
     * @param courseId
     *            Unique Id of the Course. Maps to cluId
     * @return a list of LoDisplay info Structures
     * @throws DoesNotExistException
     *             Course does not exist
     * @throws InvalidParameterException
     *             invalid courseId
     * @throws MissingParameterException
     *             invalid courseId
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public List<LoDisplayInfo> getCourseLos(@WebParam(name = "courseId") String courseId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves the Statements of a Course.
     *
     * @param courseId
     *            Unique Id of the Course. Maps to cluId
     * @param nlUsageTypeKey Natural language usage type key (context)
     * @param language Translation language e.g en, es, gr
     * @return a list of Statementree Structures
     * @throws DoesNotExistException
     *             Course does not exist
     * @throws InvalidParameterException
     *             invalid courseId
     * @throws MissingParameterException
     *             invalid courseId
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public List<StatementTreeViewInfo> getCourseStatements(@WebParam(name = "courseId") String courseId, @WebParam(name="nlUsageTypeKey")String nlUsageTypeKey, @WebParam(name="language")String language) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a Course
     *
     * @param courseInfo
     *            courseInfo
     * @return the created course
     * @throws AlreadyExistsException
     *             The Course already exists
     * @throws DataValidationErrorException
     *             One or more values invalid for this operation
     * @throws InvalidParameterException
     *             invalid course
     * @throws MissingParameterException
     *             missing Course
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     * @throws VersionMismatchException
     * @throws DependentObjectsExistException
     * @throws CircularRelationshipException
     * @throws DoesNotExistException
     * @throws UnsupportedActionException
     */
    public CourseInfo createCourse(@WebParam(name = "courseInfo") CourseInfo courseInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DoesNotExistException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException;

    /**
     * Updates a Course.
     *
     * @param courseInfo
     *            courseInfo
     * @return updated Course
     * @throws DataValidationErrorException
     *             One or more values invalid for this operation
     * @throws DoesNotExistException
     *             course not found
     * @throws InvalidParameterException
     *             invalid course
     * @throws MissingParameterException
     *             missing course
     * @throws VersionMismatchException
     *             The action was attempted on an out of date version.
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     * @throws DependentObjectsExistException
     * @throws CircularRelationshipException
     * @throws AlreadyExistsException
     * @throws UnsupportedActionException
     * @throws CircularReferenceException
     * @throws UnsupportedOperationException
     */
    public CourseInfo updateCourse(@WebParam(name = "courseInfo") CourseInfo courseInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException, AlreadyExistsException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException, UnsupportedOperationException, CircularReferenceException;

    /**
     * Deletes a Course.
     *
     * @param courseId
     *            identifier for Course.Maps to cluId
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException
     *             Course does not exist
     * @throws InvalidParameterException
     *             invalid courseId
     * @throws MissingParameterException
     *             invalid courseId
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     * @throws VersionMismatchException
     * @throws DependentObjectsExistException
     * @throws CircularRelationshipException
     * @throws AlreadyExistsException
     * @throws DataValidationErrorException
     * @throws UnsupportedActionException
     * @throws CircularReferenceException
     * @throws UnsupportedOperationException
     */
    public StatusInfo deleteCourse(@WebParam(name = "courseId") String courseId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DataValidationErrorException, AlreadyExistsException, CircularRelationshipException, DependentObjectsExistException, UnsupportedActionException, UnsupportedOperationException, CircularReferenceException;

    /**
     * Creates the Statement for a Course.
     *
     * @param courseId
     *            Unique Id of the Course. Maps to cluId
     * @param statementTreeViewInfoList
     *            a Statementree Structures
     * @return created Statementree Structures
     * @throws DoesNotExistException
     *             Course does not exist
     * @throws InvalidParameterException
     *             invalid courseId
     * @throws MissingParameterException
     *             invalid courseId
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public StatementTreeViewInfo createCourseStatement(@WebParam(name = "courseId") String courseId, @WebParam(name = "statementTreeViewInfo") StatementTreeViewInfo statementTreeViewInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Updates the Statement for a Course.
     *
     * @param courseId
     *            Unique Id of the Course. Maps to cluId
     * @param statementTreeViewInfoList
     *            a Statementree Structures
     * @return updated Statementree Structures
     * @throws DoesNotExistException
     *             Course does not exist
     * @throws InvalidParameterException
     *             invalid courseId
     * @throws MissingParameterException
     *             invalid courseId
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public StatementTreeViewInfo updateCourseStatement(@WebParam(name = "courseId") String courseId, @WebParam(name = "statementTreeViewInfo") StatementTreeViewInfo statementTreeViewInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Delete the Statement for a Course.
     *
     * @param courseId
     *            Unique Id of the Course. Maps to cluId
     * @param statementTreeViewInfoList
     *            a Statementree Structures
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException
     *             Course does not exist
     * @throws InvalidParameterException
     *             invalid courseId
     * @throws MissingParameterException
     *             invalid courseId
     * @throws OperationFailedException
     *             unable to complete request
     * @throws PermissionDeniedException
     *             authorization failure
     */
    public StatusInfo deleteCourseStatement(@WebParam(name = "courseId") String courseId, @WebParam(name = "statementTreeViewInfo") StatementTreeViewInfo statementTreeViewInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /**
     * Validates a course based on its dictionary
     *
     * @param validationType
     *            identifier of the extent of validation
     * @param courseInfo
     *            Course to be validated
     * @return results from performing the validation
     * @throws DoesNotExistException
     *             Course does not exist
     * @throws InvalidParameterException
     *             invalid courseId
     * @throws MissingParameterException
     *             invalid courseId
     * @throws OperationFailedException
     *             unable to complete request
     */
    public List<ValidationResultInfo> validateCourse(String validationType, CourseInfo courseInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException;


    /**
     * Validates the Statement for a Course.
     *
     * @param courseId
     *            Unique Id of the Course. Maps to cluId
     * @param statementTreeViewInfoList
     *            a Statementree Structures
     * @return results from performing the validation
     * @throws DoesNotExistException
     *             Course or StementTreeView does not exist
     * @throws InvalidParameterException
     *             invalid courseId or stratement tree view Id
     * @throws MissingParameterException
     *             invalid courseId or statement tree view Id
     * @throws OperationFailedException
     *             unable to complete request
     */
	public List<ValidationResultInfo> validateCourseStatement(@WebParam(name = "courseId") String courseId, @WebParam(name = "statementTreeViewInfo") StatementTreeViewInfo statementTreeViewInfo)  throws InvalidParameterException, MissingParameterException, OperationFailedException;
    
	
    /** 
     * Creates a new Course version based on the current course
     * @param courseId identifier for the Course to be versioned
     * @param versionComment comment for the current version
     * @return the new versioned Course information
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException courseId not found
     * @throws InvalidParameterException invalid courseId
     * @throws MissingParameterException missing courseId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date version
     */
    public CourseInfo createNewCourseVersion(@WebParam(name="courseId")String courseId, @WebParam(name="versionComment")String versionComment) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    
    /** 
     * Sets a specific version of the Course as current. The sequence number must be greater than the existing current Course version.
     * This will truncate the current version's end date to the currentVersionStart param.
     * If a Course exists which is set to become current in the future, that course's currentVersionStart and CurrentVersionEnd will be nullified.
     * The currentVersionStart must be in the future to prevent changing historic data. 
     * @param courseVersionId Version Specific Id of the Course
     * @param currentVersionStart Date when this course becomes current. Must be in the future and be after the most current course's start date. 
     * @return status of the operation
     * @throws DoesNotExistException courseVersionId not found
     * @throws InvalidParameterException invalid courseVersionId, previousState, newState
     * @throws MissingParameterException missing courseVersionId, previousState, newState
     * @throws IllegalVersionSequencingException a Course with higher sequence number from the one provided is marked current
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo setCurrentCourseVersion(@WebParam(name="courseVersionId")String courseVersionId, @WebParam(name="currentVersionStart")Date currentVersionStart) throws DoesNotExistException, InvalidParameterException, MissingParameterException, IllegalVersionSequencingException, OperationFailedException, PermissionDeniedException;

}