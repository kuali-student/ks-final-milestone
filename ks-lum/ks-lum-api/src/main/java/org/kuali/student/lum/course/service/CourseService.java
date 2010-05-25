/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.course.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.core.dictionary.poc.dto.ObjectStructureDefinition;
import org.kuali.student.core.dictionary.service.poc.DictionaryService;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.lum.course.dto.ActivityInfo;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.dto.FormatInfo;
import org.kuali.student.lum.course.dto.LoDisplayInfo;

/**
 *
 * @Author KSContractMojo
 * @Author Kamal
 * @Since Tue May 18 11:30:50 PDT 2010
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/Course+Service">CourseService</>
 *
 */
@WebService(name = "CourseService", targetNamespace = "http://student.kuali.org/wsdl/course") // TODO CHECK THESE VALUES
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface CourseService extends DictionaryService{ 
    /** 
     * Retrieves a Course
     * @param courseId Unique Id of the Course. Maps to cluId
     * @return the created course
     * @throws DoesNotExistException Course does not exist
     * @throws InvalidParameterException invalid course
     * @throws MissingParameterException missing Course
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public CourseInfo getCourse(@WebParam(name="courseId")String courseId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the formats for a Course.
     * @param courseId Unique Id of the Course. Maps to cluId
     * @return a list of format info Structures
     * @throws DoesNotExistException Course does not exist
     * @throws InvalidParameterException invalid courseId
     * @throws MissingParameterException invalid courseId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public List<FormatInfo> getCourseFormats(@WebParam(name="courseId")String courseId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the Activities for a Course.
     * @param formatId Unique Id of the Format. Maps to cluId
     * @return a list of activity info Structures
     * @throws DoesNotExistException Format does not exist
     * @throws InvalidParameterException invalid format
     * @throws MissingParameterException missing Format
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public List<ActivityInfo> getCourseActivities(@WebParam(name="formatId")String formatId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the Los for a Course.
     * @param courseId Unique Id of the Course. Maps to cluId
     * @return a list of LoDisplay info Structures
     * @throws DoesNotExistException Course does not exist
     * @throws InvalidParameterException invalid courseId
     * @throws MissingParameterException invalid courseId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public List<LoDisplayInfo> getCourseLos(@WebParam(name="courseId")String courseId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves the Statements of a Course.
     * @param courseId Unique Id of the Course. Maps to cluId
     * @return a list of Statementree Structures
     * @throws DoesNotExistException Course does not exist
     * @throws InvalidParameterException invalid courseId
     * @throws MissingParameterException invalid courseId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public List<StatementTreeViewInfo> getCourseStatements(@WebParam(name="courseId")String courseId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Creates a Course
     * @param courseInfo courseInfo
     * @return the created course
     * @throws AlreadyExistsException The Course already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException invalid course
     * @throws MissingParameterException missing Course
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public CourseInfo createCourse(@WebParam(name="courseInfo")CourseInfo courseInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates a Course.
     * @param courseInfo courseInfo
     * @return updated Course
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException course not found
     * @throws InvalidParameterException invalid course
     * @throws MissingParameterException missing course
     * @throws VersionMismatchException The action was attempted on an out of date version.
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public CourseInfo updateCourse(@WebParam(name="courseInfo")CourseInfo courseInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, VersionMismatchException, OperationFailedException, PermissionDeniedException;

    /** 
     * Deletes a Course.
     * @param courseId identifier for Course.Maps to cluId
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException Course does not exist
     * @throws InvalidParameterException invalid courseId
     * @throws MissingParameterException invalid courseId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo deleteCourse(@WebParam(name="courseId")String courseId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates the Los for a Course.
     * @param courseId Unique Id of the Course.Maps to cluId
     * @param loDisplayInfoList list of LoDisplay info Structures
     * @return a list of LoDisplay info Structures
     * @throws DoesNotExistException Course does not exist
     * @throws InvalidParameterException invalid courseId
     * @throws MissingParameterException invalid courseId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public List<LoDisplayInfo> updateCourseLos(@WebParam(name="courseId")String courseId, @WebParam(name="loDisplayInfoList")List<LoDisplayInfo> loDisplayInfoList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates the Statements for a Course.
     * @param courseId Unique Id of the Course. Maps to cluId
     * @param statementTreeViewInfoList a list of Statementree Structures
     * @return a list of Statementree Structures
     * @throws DoesNotExistException Course does not exist
     * @throws InvalidParameterException invalid courseId
     * @throws MissingParameterException invalid courseId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public List<StatementTreeViewInfo> updateCourseStatements(@WebParam(name="courseId")String courseId, @WebParam(name="statementTreeViewInfoList")List<StatementTreeViewInfo> statementTreeViewInfoList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


}