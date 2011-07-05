/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.enrollment.courseoffering.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.dto.SeatPoolDefinitionInfo;
import org.kuali.student.r2.common.datadictionary.service.DataDictionaryService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.CircularReferenceException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;

/**
 * Version: DRAFT - NOT READY FOR RELEASE. 
 * 
 * Course Offering is a Class II service supporting the process of offering courses
 * for student registration. Courses are offered for a specific term which is associated with a specific Academic Calendar.
 * At the canonical level a course is defined by formats for which the course will be offered. Each format describes the
 * activity types that comprise that format, e.g., lecture and lab. The purpose of multiple formats is to support different
 * formats based on a term type, e.g., Fall versus Spring offering, or to offer multiple formats in the same term, e.g., in
 * person (traditional) versus online. 
 * 
 * Offering a course is the process of creating specific instances of the course, and for
 * each format to be offered in the selected term, creating a specified number of each activity type that comprises the
 * format, e.g. five (5) lectures and ten (10) labs of Biology 101. Individual activity offerings correspond to events in a
 * scheduling system, each with a meeting pattern. 
 * 
 * The term 'section' varies by institution, but refers to either the
 * individual activity offering, or it refers to the combination of activity offerings, when the course has more than one
 * activity type, that the student registers in as part of that course. To avoid confusion, this service introduces a new
 * entity to capture the second definition of section. A registration group represents a valid combination of activity
 * offerings, even if the number is one, in which a student registers. The design supports unrestricted matching, e.g., any
 * lecture with any lab, as well as specific matching, e.g., lecture 1 with lab A or B, and lecture 2 with lab C or D.
 * Version: 1.0 (Dev)
 * 
 * @author Kuali Student Team (Kamal)
 */
@WebService(name = "CourseOfferingService", targetNamespace = CourseOfferingServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface CourseOfferingService extends DataDictionaryService {

        
    /**
     * Retrieve information about a CourseOffering
     * 
     * @param courseOfferingId Unique Id of the CourseOffering
     * @param context Context information containing the principalId and locale information about the caller of service
     *            operation
     * @return CourseOffering associated with the passed in Id
     * @throws DoesNotExistException seatPoolDefinitionId not found
     * @throws InvalidParameterException invalid courseOfferingId
     * @throws MissingParameterException missing courseOfferingId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public CourseOfferingInfo getCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    

    /**
     * Retrieve CourseOfferings by canonical course id and term. This could return multiple offerings in cases of multiple offerings for formats and cross listed 
     * 
     * @param courseId Unique Id of the Course (canonical)
     * @param termKey  Unique key of the term in which the course is being offered
     * @param context Context information containing the principalId and locale information about the caller of service
     *            operation
     * @return List of CourseOfferings
     * @throws DoesNotExistException courseId or termKey not found
     * @throws InvalidParameterException invalid courseId or termKey 
     * @throws MissingParameterException missing courseId or termKey 
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CourseOfferingInfo> getCourseOfferingsForCourseAndTerm(@WebParam(name = "courseId") String courseId,  @WebParam(name = "termKey") String termKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    
    /**
     * Retrieve CourseOffering ids for a given term and if useIncludedTerms is set to 'true' then use included terms also
     * 
     * @param termKey  Unique key of the term in which the course is being offered
     * @param useIncludedTerm Indicates if the offerings from included term are also to be returned
     * @param context Context information containing the principalId and locale information about the caller of service
     *            operation
     * @return List of CourseOffering Ids
     * @throws DoesNotExistException courseId or termKey not found
     * @throws InvalidParameterException invalid courseId or termKey 
     * @throws MissingParameterException missing courseId or termKey 
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getCourseOfferingIdsForTerm(@WebParam(name = "termKey") String termKey, @WebParam(name = "useIncludedTerm") Boolean useIncludedTerm, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /**
     * Retrieve CourseOffering ids for a given term and subject area
     * 
     * @param termKey  Unique key of the term in which the course is being offered
     * @param subjectArea subject area 
     * @param context Context information containing the principalId and locale information about the caller of service
     *            operation
     * @return List of CourseOffering Ids
     * @throws DoesNotExistException courseId or termKey not found
     * @throws InvalidParameterException invalid courseId or termKey 
     * @throws MissingParameterException missing courseId or termKey 
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getCourseOfferingIdsBySubjectArea(@WebParam(name = "termKey") String termKey, @WebParam(name = "subjectArea") String subjectArea, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    

    /**
     * Retrieve CourseOffering ids for a given term and unit content owner
     * 
     * @param termKey  Unique key of the term in which the course is being offered
     * @param unitOnwerId Unit content owner Id 
     * @param context Context information containing the principalId and locale information about the caller of service
     *            operation
     * @return List of CourseOffering Ids
     * @throws DoesNotExistException courseId or termKey not found
     * @throws InvalidParameterException invalid courseId or termKey 
     * @throws MissingParameterException missing courseId or termKey 
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getCourseOfferingIdsByUnitContentOwner(@WebParam(name = "termKey") String termKey, @WebParam(name = "unitOwnerId") String unitOwnerId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new CourseOffering from a canonical course. 
     * 
     * @param courseId Canonical course IdList of courseOffering Ids that the ActivityOffering will belong to
     * @param termKey  Unique key of the term in which the course is being offered
     * @param formatIdList Canonical formats from the canonical course to be used for the course offering
     * @param context Context information containing the principalId and locale information about the caller of service
     *            operation
     * @return newly created CourseOfferingInfo
     * @throws AlreadyExistsException the CourseOffering being created already exists
     * @throws DoesNotExistException courseId not found
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public CourseOfferingInfo createCourseOfferingFromCanonical(@WebParam(name = "courseId") String courseid, @WebParam(name = "termKey") String termKey, @WebParam(name = "formatIdList") List<String> formatIdList, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    
    
    /**
     * Updates an existing CourseOffering.
     * 
     * @param courseOfferingId Id of CourseOffering to be updated
     * @param courseOfferingInfo Details of updates to the CourseOffering
     * @param context Context information containing the principalId and locale information about the caller of service
     *            operation
     * @return updated CourseOffering
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException the CourseOffering does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date version.
     */
    public CourseOfferingInfo updateCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "courseOfferingInfo") CourseOfferingInfo courseOfferingInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    
    /**
     * Updates an existing CourseOffering from its canonical. This should reinitialize and overrwrite any changes to the course 
     * offering that were made since its creation with the defaults from the canonical course
     * 
     * @param courseOfferingId Id of CourseOffering to be updated
     * @param context Context information containing the principalId and locale information about the caller of service
     *            operation
     * @return updated CourseOffering
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException the CourseOffering does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date version.
     */
    public CourseOfferingInfo updateCourseOfferingFromCanonical(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;
    
    /** 
     * Deletes an existing CourseOffering. Deleting a course offering should also delete all the activity offerings and registrations groups within it.
     * Cross listed course offerings should also be deleted along with passed in courseOfferingId.
     *
     * @param courseOfferingId the Id of the ActivityOffering to be deleted
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException the SeatPoolDefinition does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    
    /**
     * Retrieves the course offering restrictions.
     *
     * @param courseOfferingId Unique Id of the Course Offering
     * @param nlUsageTypeKey Natural language usage type key (context)
     * @param language Translation language e.g en, es, gr
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of StatemenTree structures defining the restrictions
     * @throws DoesNotExistException  CourseOffering does not exist
     * @throws InvalidParameterException invalid courseOfferingId
     * @throws MissingParameterException invalid courseOfferingId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<StatementTreeViewInfo> getCourseOfferingRestrictions(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name="nlUsageTypeKey")String nlUsageTypeKey, @WebParam(name="language")String language,  @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    
    
    /**
     * Creates the Course offering restriction (Statement)
     *
     * @param courseOfferingId Unique Id of the Course Offering
     * @param restrictionInfo Offering restriction as a statementree structure
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return created restriction as a statementree Structures
     * @throws DoesNotExistException  Course Offering does not exist
     * @throws InvalidParameterException invalid courseOfferingId, statementTreeViewInfoList
     * @throws MissingParameterException invalid courseOfferingId, statementTreeViewInfoList
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws DataValidationErrorException One or more values invalid for this operation
     */
    public StatementTreeViewInfo createCourseOfferingRestriction(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "restrictionInfo") StatementTreeViewInfo statementTreeViewInfo,  @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException;

    /**
     * Updates the course offering restriction
     *
     * @param courseOfferingId  Unique Id of the Course Offering
     * @param restrictionInfo  Offering restriction as a statementree structure
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return updated restriction
     * @throws DoesNotExistException Course Offering does not exist
     * @throws InvalidParameterException  invalid courseOfferingId
     * @throws MissingParameterException invalid courseOfferingId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date version.
     * @throws CircularReferenceException circular reference in statements
     * @throws DataValidationErrorException One or more values invalid for this operation
     */
    public StatementTreeViewInfo updateCourseOfferingRestriction(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "statementTreeViewInfo") StatementTreeViewInfo statementTreeViewInfo,  @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, CircularReferenceException, VersionMismatchException;

    /**
     * Delete the course offering restriction
     *
     * @param courseOfferingId     Unique Id of the Course Offering
     * @param restrictionId restriction Id to be deleted
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException Course does not exist
     * @throws InvalidParameterException   invalid courseOfferingId
     * @throws MissingParameterException  invalid courseOfferingId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteCourseOfferingRestriction(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "restrictionId") String restrictionId,  @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    

    /**
     * This method returns the TypeInfo for a given activity offering
     * type key.
     *
     * @param activityOfferingTypeKey Key of the type
     * @param context Context information containing the principalId
     *        and locale information about the caller of service
     *        operation
     * @return Information about the Type
     * @throws DoesNotExistException activityOfferingTypeKey not found
     * @throws InvalidParameterException invalid activityOfferingTypeKey
     * @throws MissingParameterException missing activityOfferingTypeKey
     * @throws OperationFailedException unable to complete request
     */    
    public TypeInfo getActivityOfferingType(@WebParam(name = "activityOfferingTypeKey") String activityOfferingTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * This method returns the valid activity offering types.
     *
     * @param context Context information containing the principalId and 
     *        locale information about the caller of service operation
     * @return a list of valid activity offering Types
     * @throws InvalidParameterException invalid context
     * @throws MissingParameterException missing context
     * @throws OperationFailedException unable to complete request
     */
    public List<TypeInfo> getAllActivityOfferingTypes(@WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException;

    
    /**
     * This method returns the valid activity offerring types for a given canonical activity type
     *
     * @param activityTypeKey Key of the canonical activity type
     * @param context Context information containing the principalId and 
     *        locale information about the caller of service operation
     * @return a list of valid activity offering Types
     * @throws DoesNotExistException activityOfferingTypeKey not found
     * @throws InvalidParameterException invalid context
     * @throws MissingParameterException missing context
     * @throws OperationFailedException unable to complete request
     */
    public List<TypeInfo> getActivityOfferingTypesForActivityType(@WebParam(name = "activityTypeKey") String activityTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
    
    
    /**
     * Retrieve information about an ActivityOffering
     * 
     * @param activityOfferingId Unique Id of the ActivityOffering
     * @param context Context information containing the principalId and locale information about the caller of service
     *            operation
     * @return ActivityOffering associated with the passed in Id
     * @throws DoesNotExistException seatPoolDefinitionId not found
     * @throws InvalidParameterException invalid activityOfferingId
     * @throws MissingParameterException missing activityOfferingId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ActivityOfferingInfo getActivityOffering(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    

    /**
     * Retrieves a list of ActivityOffering records that belongs to a CourseOffering. 
     * 
     * @param courseOfferingId Unique Id of the CourseOffering
     * @param context Context information containing the principalId and locale information about the caller of service
     *            operation
     * @return List of ActivityOffering
     * @throws DoesNotExistException courseOfferingId not found
     * @throws InvalidParameterException invalid courseOfferingId
     * @throws MissingParameterException missing courseOfferingId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ActivityOfferingInfo> getActivitiesForCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /**
     * Retrieves a list of ActivityOffering records that belongs to a RegistrationGroup. 
     * 
     * @param registrationGroupId Unique Id of the RegistrationGroup
     * @param context Context information containing the principalId and locale information about the caller of service
     *            operation
     * @return List of SeatPoolDefinitions
     * @throws DoesNotExistException registrationGroupId not found
     * @throws InvalidParameterException invalid registrationGroupId
     * @throws MissingParameterException missing registrationGroupId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<ActivityOfferingInfo> getActivitiesForRegGroup(@WebParam(name = "registrationGroupId") String registrationGroupId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new Activity Offering
     * 
     * @param courseOfferingIdList List of courseOffering Ids that the ActivityOffering will belong to
     * @param ActivityOfferingInfo Details of the ActivityOffering to be created
     * @param context Context information containing the principalId and locale information about the caller of service
     *            operation
     * @return newly created ActivityOffering
     * @throws AlreadyExistsException the ActivityOffering being created already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public ActivityOfferingInfo createActivityOffering(@WebParam(name = "courseOfferingIdList") List<String> courseOfferingIdList, @WebParam(name = "activityOfferingInfo") ActivityOfferingInfo activityOfferingInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    
    /**
     * Updates an existing ActivityOffering.
     * 
     * @param activityOfferingId Id of ActivitOffering to be updated
     * @param activityOfferingInfo Details of updates to the ActivityOffering
     * @param context Context information containing the principalId and locale information about the caller of service
     *            operation
     * @return updated ActivityOffering
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException the ActivityOffering does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date version.
     */
    public ActivityOfferingInfo updateActivityOffering(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "activityOfferingInfo") ActivityOfferingInfo activityOfferingInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Deletes an existing ActivityOffering. Activity offering cannot be deleted if a courseOffering or registrationGroup is still pointing to the activity offering.
     *
     * @param activityOfferingId the Id of the ActivityOffering to be deleted
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException the SeatPoolDefinition does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteActivityOffering(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
     
    /**
     * Retrieves the activity offering restrictions.
     *
     * @param activityOfferingId Unique Id of the Activity Offering
     * @param nlUsageTypeKey Natural language usage type key (context)
     * @param language Translation language e.g en, es, gr
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of StatemenTree structures defining the restrictions
     * @throws DoesNotExistException  ActivityOffering does not exist
     * @throws InvalidParameterException invalid activityOfferingId
     * @throws MissingParameterException invalid activityOfferingId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<StatementTreeViewInfo> getActivityOfferingRestrictions(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name="nlUsageTypeKey")String nlUsageTypeKey, @WebParam(name="language")String language,  @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    
    
    /**
     * Creates the Activity offering restriction (Statement)
     *
     * @param activityOfferingId Unique Id of the Activity Offering
     * @param restrictionInfo Offering restriction as a statementree structure
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return created restriction as a statementree Structures
     * @throws DoesNotExistException  Activity Offering does not exist
     * @throws InvalidParameterException invalid activityOfferingId, statementTreeViewInfoList
     * @throws MissingParameterException invalid activityOfferingId, statementTreeViewInfoList
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws DataValidationErrorException One or more values invalid for this operation
     */
    public StatementTreeViewInfo createActivityOfferingRestriction(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "restrictionInfo") StatementTreeViewInfo statementTreeViewInfo,  @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException;

    /**
     * Updates the activity offering restriction
     *
     * @param activityOfferingId  Unique Id of the Activity Offering
     * @param restrictionInfo  Offering restriction as a statementree structure
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return updated restriction
     * @throws DoesNotExistException Activity Offering does not exist
     * @throws InvalidParameterException  invalid activityOfferingId
     * @throws MissingParameterException invalid activityOfferingId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date version.
     * @throws CircularReferenceException circular reference in statements
     * @throws DataValidationErrorException One or more values invalid for this operation
     */
    public StatementTreeViewInfo updateActivityOfferingRestriction(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "statementTreeViewInfo") StatementTreeViewInfo statementTreeViewInfo,  @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, CircularReferenceException, VersionMismatchException;

    /**
     * Delete the activity offering restriction
     *
     * @param activityOfferingId     Unique Id of the Activity Offering
     * @param restrictionId restriction Id to be deleted
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status of the operation (success or failure)
     * @throws DoesNotExistException Course does not exist
     * @throws InvalidParameterException   invalid activityOfferingId
     * @throws MissingParameterException  invalid activityOfferingId
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteActivityOfferingRestriction(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "restrictionId") String restrictionId,  @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    
    
    /**
     * When/for how long does the offering meet in class during the term. 
     * Calculated by system based on meeting times and term length; may be validated against CLU. 
     * 
     * @param activityOfferingId the Id of the ActivityOffering to be used for contact hour calculation
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return in class contact hours for the term
     * @throws DoesNotExistException the SeatPoolDefinition does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public Float calculateInClassContactHoursForTerm(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    
    /**
     * When/for how long does the offering meet out of class during the term. 
     * Calculated by system based on meeting times and term length; may be validated against CLU. 
     * 
     * @param activityOfferingId the Id of the ActivityOffering to be used for contact hour calculation
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return out of  class contact hours for the term
     * @throws DoesNotExistException the SeatPoolDefinition does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public Float calculateOutofClassContactHoursForTerm(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    
    
    /**
     * When/for how long does the offering meet in total during the term. 
     * Calculated by system based on meeting times and term length; may be validated against CLU. 
     * 
     * @param activityOfferingId the Id of the ActivityOffering to be used for contact hour calculation
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return total class contact hours for the term
     * @throws DoesNotExistException the SeatPoolDefinition does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public Float calculateTotalContactHoursForTerm(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    
    /**
     * Creates specified number of copies from a given activity offering. The amount of data copied over is specified using the copyContextType.
     * 
     * @param activityOfferingId ActivityOffering to be copied from
     * @param numberOfCopies Number of copies to be made
     * @param copyContextTypeKey copy context specifies the amount of information to be copied over 
     * @param context Context information containing the principalId and locale information about the caller of service
     *            operation
     * @return newly created ActivityOfferings
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */    
    public List<ActivityOfferingInfo> copyActivityOffering(@WebParam(name = "activityOfferingId") String activityOfferingId, @WebParam(name = "numberOfCopies") Integer numberOfCopies,  @WebParam(name = "copyContextTypeKey") String copyContextTypeKey, @WebParam(name = "context") ContextInfo context)  throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    
    
    /**
     * Retrieve information about a RegistrationGroup
     * 
     * @param registrationGroupId Unique Id of the RegistrationGroup
     * @param context Context information containing the principalId and locale information about the caller of service
     *            operation
     * @return RegistrationGroup associated with the passed in Id
     * @throws DoesNotExistException registrationGroupId not found
     * @throws InvalidParameterException invalid registrationGroupId
     * @throws MissingParameterException missing registrationGroupId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public RegistrationGroupInfo getRegistrationGroup(@WebParam(name = "registrationGroupId") String registrationGroupId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of RegistrationGroup records that belongs to a CourseOffering
     * 
     * @param courseOfferingId Unique Id of the CourseOffering
     * @param context Context information containing the principalId and locale information about the caller of service
     *            operation
     * @return List of RegistrationGroups
     * @throws DoesNotExistException courseOfferingId not found
     * @throws InvalidParameterException invalid courseOfferingId
     * @throws MissingParameterException missing courseOfferingId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<RegistrationGroupInfo> getRegGroupsForCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of RegistrationGroup records that belongs to a CourseOffering for a given canonical format type
     * 
     * @param courseOfferingId Unique Id of the CourseOffering
     * @param formatTypeKey Type of the canonical format
     * @param context Context information containing the principalId and locale information about the caller of service
     *            operation
     * @return List of RegistrationGroups
     * @throws DoesNotExistException courseOfferingId or formatTypeKey not found
     * @throws InvalidParameterException invalid courseOfferingId or formatTypeKey
     * @throws MissingParameterException missing courseOfferingId or formatTypeKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<RegistrationGroupInfo> getRegGroupsByFormatForCourse(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "formatTypeKey") String formatTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new Registration Group
     * 
     * @param courseOfferingId courseOffering Id that the RegistrationGroup will belong to
     * @param registrationGroupInfo Details of the RegistrationGroup to be created
     * @param context Context information containing the principalId and locale information about the caller of service
     *            operation
     * @return newly created registrationGroup
     * @throws AlreadyExistsException the RegistrationGroup being created already exists
     * @throws DoesNotExistException courseOfferingId not found    
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public RegistrationGroupInfo createRegistrationGroup(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "registrationGroupInfo") RegistrationGroupInfo registrationGroupInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Updates an existing RegistrationGroup.
     * 
     * @param registrationGroupId Id of RegistrationGroup to be updated
     * @param registrationGroupInfo Details of updates to the RegistrationGroup
     * @param context Context information containing the principalId and locale information about the caller of service
     *            operation
     * @return updated RegistrationGroup
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException the RegistrationGroup does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date version.
     */
    public RegistrationGroupInfo updateRegistrationGroup(@WebParam(name = "registrationGroupId") String registrationGroupId, @WebParam(name = "registrationGroupInfo") RegistrationGroupInfo registrationGroupInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Deletes an existing Registration Group.
     *
     * @param registrationGroupId the Id of the RegistrationGroup to be deleted
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException the RegistrationGroup does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteRegistrationGroup(@WebParam(name = "registrationGroupId") String registrationGroupId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    

    /**
     * Retrieve information about a SeatPoolDefinition
     * 
     * @param seatPoolDefinitionId Unique Id of the SeatPoolDefinition
     * @param context Context information containing the principalId and locale information about the caller of service
     *            operation
     * @return SeatPoolDefinition associated with the passed in Id
     * @throws DoesNotExistException seatPoolDefinitionId not found
     * @throws InvalidParameterException invalid seatPoolDefinitionId
     * @throws MissingParameterException missing seatPoolDefinitionId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public SeatPoolDefinitionInfo getSeatPoolDefinition(@WebParam(name = "seatPoolDefinitionId") String seatPoolDefinitionId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
    

    /**
     * Retrieves a list of SeatPoolDefinitions records that belongs to a CourseOffering. This should return SeatPoolDefinitions that apply globally across all 
     * RegistrationGroup in the CourseOffering
     * 
     * @param courseOfferingId Unique Id of the CourseOffering
     * @param context Context information containing the principalId and locale information about the caller of service
     *            operation
     * @return List of SeatPoolDefinitions
     * @throws DoesNotExistException courseOfferingId not found
     * @throws InvalidParameterException invalid courseOfferingId
     * @throws MissingParameterException missing courseOfferingId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<SeatPoolDefinitionInfo> getSeatPoolsForCourseOffering(@WebParam(name = "courseOfferingId") String courseOfferingId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /**
     * Retrieves a list of SeatPoolDefinitions records that belongs to a RegistrationGroup. 
     * 
     * @param registrationGroupId Unique Id of the RegistrationGroup
     * @param context Context information containing the principalId and locale information about the caller of service
     *            operation
     * @return List of SeatPoolDefinitions
     * @throws DoesNotExistException registrationGroupId not found
     * @throws InvalidParameterException invalid registrationGroupId
     * @throws MissingParameterException missing registrationGroupId
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<SeatPoolDefinitionInfo> getSeatPoolsForRegGroup(@WebParam(name = "registrationGroupId") String registrationGroupId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new Seat Pool
     * 
     * @param SeatPoolDefinitionInfo Details of the SeatPoolDefinition to be created
     * @param context Context information containing the principalId and locale information about the caller of service
     *            operation
     * @return newly created SeatPoolDefinition
     * @throws AlreadyExistsException the SeatPoolDefinition being created already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public SeatPoolDefinitionInfo createSeatPoolDefinition(@WebParam(name = "seatPoolDefinitionInfo") SeatPoolDefinitionInfo seatPoolDefinitionInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    
    /**
     * Updates an existing SeatPoolDefinition.
     * 
     * @param seatPoolDefinitionId Id of SeatPoolDefinition to be updated
     * @param seatPoolDefinitionInfo Details of updates to the SeatPoolDefinition
     * @param context Context information containing the principalId and locale information about the caller of service
     *            operation
     * @return updated SeatPoolDefinition
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException the SeatPoolDefinition does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date version.
     */
    public SeatPoolDefinitionInfo updateSeatPoolDefinition(@WebParam(name = "seatPoolDefinitionId") String seatPoolDefinitionId, @WebParam(name = "seatPoolDefinitionInfo") SeatPoolDefinitionInfo seatPoolDefinitionInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Deletes an existing SeatPoolDefinition.
     *
     * @param seatPoolDefinitionId the Id of the SeatPoolDefinition to be deleted
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException the SeatPoolDefinition does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteSeatPoolDefinition(@WebParam(name = "seatPoolDefinitionId") String seatPoolDefinitionId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
        
}
