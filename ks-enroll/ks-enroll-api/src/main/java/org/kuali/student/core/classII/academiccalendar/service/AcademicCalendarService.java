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

package org.kuali.student.core.classII.academiccalendar.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;


import org.kuali.student.core.classII.academiccalendar.dto.AcademicCalendarInfo;
import org.kuali.student.core.classII.academiccalendar.dto.CampusCalendarInfo;
import org.kuali.student.core.classII.academiccalendar.dto.HolidayInfo;
import org.kuali.student.core.classII.academiccalendar.dto.KeyDateInfo;
import org.kuali.student.core.classII.academiccalendar.dto.RegistrationDateGroupInfo;
import org.kuali.student.core.classII.academiccalendar.dto.TermInfo;

import org.kuali.student.r2.common.datadictionary.service.DataDictionaryService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;

import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.AcademicCalendarServiceConstants;

/**
 * Academic Calendar Service Description and Assumptions.
 *
 * This service manages Academic Calendars. An Academic Calendar is
 * related to a credential program type and contains Terms. The Terms
 * are managed as nested objects inside the Academic Calendar at this
 * level.
 *
 * Key Dates are mapped to Terms but are managed through the service
 * independent of the Term. This is to allow a reference to a Term
 * that does not retrieve all the key date information that may relate
 * to the Term.
 *
 * Terms may be nested at this level. A Term may contain another Term
 * and each of these sub-Terms may have their own key
 * dates. Convenience service methods exist to query all the key dates
 * for an Academic Calendar or parent Term.
 *
 * An Academic Calendar also has a Campus Calendar. The Campus
 * Calendar has a campus location, and include the key dates and
 * holidays that are specific to a campus. The same Campus Calendar
 * can be used for multiple Academic Calendars.
 *
 * Version: 1.0 (Dev)
 *
 * @Author tom
 * @Since Sun Apr 10 14:22:34 EDT 2011
 */

@WebService(name = "AcademicCalendarService", targetNamespace = AcademicCalendarServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface AcademicCalendarService extends DataDictionaryService {

    /**
     * This method returns the TypeInfo for a given academic calendar
     * type key.
     *
     * @param academicCalendarTypeKey Key of the type
     * @param context Context information containing the principalId
     *        and locale information about the caller of service
     *        operation
     * @return Information about the Type
     * @throws DoesNotExistException academicCalendarTypeKey not found
     * @throws InvalidParameterException invalid academicCalendarTypeKey
     * @throws MissingParameterException missing academicCalendarTypeKey
     * @throws OperationFailedException unable to complete request
     */    
    public TypeInfo getAcademicCalendarType(@WebParam(name = "academicCalendarTypeKey") String academicCalendarTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * This method returns the valid academic calendar types.
     *
     * @param context Context information containing the principalId and 
     *        locale information about the caller of service operation
     * @return a list of valid academic calendar Types
     * @throws InvalidParameterException invalid context
     * @throws MissingParameterException missing context
     * @throws OperationFailedException unable to complete request
     */
    public List<TypeInfo> getAcademicCalendarTypes(@WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * This method returns the details on an academic calendar state.
     *
     * @param academicCalendarStateKey a key for an academic calendar state
     * @param context Context information containing the principalId and 
     *        locale information about the caller of service operation
     * @return details on the academic calendar state
     * @throws DoesNotExistException academicCalendarStateKey not found
     * @throws InvalidParameterException invalid context
     * @throws MissingParameterException missing context
     * @throws OperationFailedException unable to complete request
     */
    public StateInfo getAcademicCalendarState(@WebParam(name = "academicCalendarStateKey") String academicCalendarStateKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * This method returns the possible academic calendar states.
     *
     * @param context Context information containing the principalId and 
     *        locale information about the caller of service operation
     * @return a list of valid academic calendar states
     * @throws InvalidParameterException invalid context
     * @throws MissingParameterException missing context
     * @throws OperationFailedException unable to complete request
     */
    public List<StateInfo> getAcademicCalendarStates(@WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the details of a single Academic Calendar by an
     * academic calendar key.
     *
     * @param academicCalendarKey Unique key of the Academic Calendar to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of the Academic Calendar requested
     * @throws DoesNotExistException academicCalendarKey not found
     * @throws InvalidParameterException invalid academicCalendarKey
     * @throws MissingParameterException missing academicCalendarKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public AcademicCalendarInfo getAcademicCalendar(@WebParam(name = "academicCalendarKey") String academicCalendarKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list Academic Calendars corresponding to a list of
     * academic calendar keys.
     *
     * @param academicCalendarKeyList list of unique keys of the
     *        Academic Calendar to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Academic Calendars
     * @throws DoesNotExistException an  academicCalendarKey in list not found
     * @throws InvalidParameterException invalid academicCalendarKey in list
     * @throws MissingParameterException missing academicCalendarKeyList
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<AcademicCalendarInfo> getAcademicCalendarsByKeyList(@WebParam(name = "academicCalendarKeyList") List<String> academicCalendarKeyList, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Academic Calendar Keys by Type.
     *
     * @param academicCalendarTypeKey a Type of Academic Calendar to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Academic Calendars of the given Type
     * @throws InvalidParameterException invalid academicCalendarTypeKey
     * @throws MissingParameterException missing academicCalendarTypeKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getAcademicCalendarKeysByType(@WebParam(name = "academicCalendarTypeKey") String academicCalendarTypeKey, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Academic Calendars that pertain to the
     * given year.
     *
     * @param year 
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Academic Calendars 
     * @throws InvalidParameterException invalid year
     * @throws MissingParameterException missing year
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<AcademicCalendarInfo> getAcademicCalendarsByYear(@WebParam(name = "year") Integer year, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Academic Calendars mapped to a credential
     * program type.
     *
     * @param credentialProgramTypeKey a Type of a credential program
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Academic Calendars
     * @throws InvalidParameterException invalid credentialProgramTypeKey
     * @throws MissingParameterException missing credentialProgramTypeKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<AcademicCalendarInfo> getAcademicCalendarsByCredentialProgramType(@WebParam(name = "credentialProgramTypeKey") String credentialProgramTypeKey, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Academic Calendars mapped to a credential
     * program type and pertains to the given year.
     *
     * @param credentialProgramTypeKey a Type of a credential program
     * @param year
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Academic Calendars
     * @throws InvalidParameterException invalid credentialProgramType or year
     * @throws MissingParameterException missing credentialProgramTypeKey or year
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<AcademicCalendarInfo> getAcademicCalendarsByCredentialProgramTypeForYear(@WebParam(name = "credentialProgramTypeKey") String credentialProgramTypeKey, @WebParam(name = "year") Integer year, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Validates an academic calendar. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current object and its directly contained subobjects
     * or expanded to perform all tests related to this object. If an
     * identifier is present for the academic calendar and a record
     * is found for that identifier, the validation checks if the
     * academic calendar can be shifted to the new values. If a
     * record cannot be found for the identifier, it is assumed that
     * the record does not exist and as such, the checks performed
     * will be much shallower, typically mimicking those performed by
     * setting the validationType to the current object. This is a
     * slightly different pattern from the standard validation as the
     * caller provides the identifier in the create statement instead
     * of the server assigning an identifier.
     *
     * @param validationType Identifier of the extent of validation
     * @param academicCalendarInfo the academic calendar information to be tested.
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, academicCalendarInfo
     * @throws MissingParameterException missing validationTypeKey, academicCalendarInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateAcademicCalendar(@WebParam(name = "validationType") String validationType, @WebParam(name = "academicCalendarInfo") AcademicCalendarInfo academicCalendarInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Creates a new Academic Calendar.
     *
     * @param academicCalendarKey the key of the Academic Calendar to be created
     * @param academicCalendarInfo Details of the Academic Calendar to be created
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of the Academic Calendar just created
     * @throws AlreadyExistsException the Academic Calendar being created already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public AcademicCalendarInfo createAcademicCalendar(@WebParam(name = "academicCalendarKey") String academicCalendarKey, @WebParam(name = "academicCalendarInfo") AcademicCalendarInfo academicCalendarInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates an existing Academic Calendar.
     *
     * @param academicCalendarKey Key of Academic Calendar to be updated
     * @param academicCalendarInfo Details of updates to the Academic
     *        Calendar being updated
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of Academic Calendar just updated
     * @throws DataValidationErrorException One or more values invalid for this 
     *         operation
     * @throws DoesNotExistException the Academic Calendar does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date 
     *         version.
     */
    public AcademicCalendarInfo updateAcademicCalendar(@WebParam(name = "academicCalendarKey") String academicCalendarKey, @WebParam(name = "academicCalendarInfo") AcademicCalendarInfo academicCalendarInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Deletes an existing Academic Calendar.
     *
     * @param academicCalendarKey the key of the Academic Calendar to
     *        be deleted
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException the Academic Calendar does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteAcademicCalendar(@WebParam(name = "academicCalendarKey") String academicCalendarKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Copy an Academic Calendar. The associated Terms and key dates
     * are also copied and related to this new calendar. This copy
     * operation allows for a calendar template to be created from an
     * existing calendar in which the dates of the Terms and
     * Key Dates will be updated.
     *
     * Currently, this method does not perform any date
     * transformations on the Terms and key dates (Labor Day has a
     * new date). A rollover process needs to make a copy of a
     * calendar and set the new dates for the new year.
     *
     * @param academicCalendarKey the key of the Academic Calendar to
     *        be copied
     * @param newAcademicCalendarKey the key of the new copy of the
     *        Academic Calendar
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status of the operation (success, failed)
     * @throws AlreadyExistsException newAcademicCalendarKey already exists
     * @throws DoesNotExistException the Academic Calendar does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public AcademicCalendarInfo copyAcademicCalendar(@WebParam(name = "academicCalendarKey") String academicCalendarKey, @WebParam(name = "newAcademicCalendarKey") String newAcademicCalendarKey, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Generates calendaring data for the Terms and key dates in an
     * Academic Calendar. The format of the data is specified by the
     * calendarDataFormatType. An example of such a type is VCALENDAR.
     *
     * @param academicCalendarKey the key of the Academic Calendar to
     *        be retrieved
     * @param calendarDataFormatTypeKey the type of format
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException the Academic Calendar or format
     *         type does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public String getAcademicCalendarData(@WebParam(name = "academicCalendarKey") String academicCalendarKey, @WebParam(name = "calendarDataFormatTypeKey") String calendarDataFormatTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method returns the TypeInfo for a campus calendar type
     * key.
     *
     * @param campusCalendarTypeKey Key of the type
     * @param context Context information containing the principalId
     *        and locale information about the caller of service
     *        operation
     * @return Information about the Type
     * @throws DoesNotExistException  campusCalendarTypeKey not found
     * @throws InvalidParameterException invalid campusCalendarTypeKey
     * @throws MissingParameterException missing campusCalendarTypeKey
     * @throws OperationFailedException unable to complete request
     */    
    public TypeInfo getCampusCalendarType(@WebParam(name = "campusCalendarTypeKey") String campusCalendarTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * This method returns the valid campus calendar types.
     *
     * @param context Context information containing the principalId
     *        and locale information about the caller of service
     *        operation
     * @return a list of valid campus calendar Types
     * @throws InvalidParameterException invalid context
     * @throws MissingParameterException missing context
     * @throws OperationFailedException unable to complete request
     */
    public List<TypeInfo> getCampusCalendarTypes(@WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * This method returns the details on a campus calendar state.
     *
     * @param campusCalendarStateKey a key for a campus calendar state
     * @param context Context information containing the principalId and 
     *        locale information about the caller of service operation
     * @return details on the campus calendar state
     * @throws DoesNotExistException campusCalendarStateKey not found
     * @throws InvalidParameterException invalid context
     * @throws MissingParameterException missing context
     * @throws OperationFailedException unable to complete request
     */
    public StateInfo getCampusCalendarState(@WebParam(name = "campusCalendarStateKey") String campusCalendarStateKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * This method returns the possible campus calendar states.
     *
     * @param context Context information containing the principalId and 
     *        locale information about the caller of service operation
     * @return a list of valid campus calendar states
     * @throws InvalidParameterException invalid context
     * @throws MissingParameterException missing context
     * @throws OperationFailedException unable to complete request
     */
    public List<StateInfo> getCampusCalendarStates(@WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the details of a single Campus Calendar by an
     * campus calendar key.
     *
     * @param campusCalendarKey Unique key of the Campus Calendar to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of the Campus Calendar requested
     * @throws DoesNotExistException campusCalendarKey not found
     * @throws InvalidParameterException invalid campusCalendarKey
     * @throws MissingParameterException missing campusCalendarKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public CampusCalendarInfo getCampusCalendar(@WebParam(name = "campusCalendarKey") String campusCalendarKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list Campus Calendars corresponding to a list of
     * campus calendar keys.
     *
     * @param campusCalendarKeyList list of unique keys of the
     *        Campus Calendar to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Campus Calendars
     * @throws DoesNotExistException a campusCalendarKey in list not found
     * @throws InvalidParameterException invalid campusCalendarKey in list
     * @throws MissingParameterException missing campusCalendarKeyList
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CampusCalendarInfo> getCampusCalendarsByKeyList(@WebParam(name = "campusCalendarKeyList") List<String> campusCalendarKeyList, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Campus Calendars by Type.
     *
     * @param campusCalendarTypeKey a Type of Campus Calendar to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Campus Calendars of the given Type
     * @throws InvalidParameterException invalid campusCalendarTypeKey
     * @throws MissingParameterException missing campusCalendarTypeKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getCampusCalendarKeysByType(@WebParam(name = "campusCalendarTypeKey") String campusCalendarTypeKey, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Campus Calendars that pertain to the
     * given year.
     *
     * @param year 
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Campus Calendars 
     * @throws InvalidParameterException invalid year
     * @throws MissingParameterException missing year
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CampusCalendarInfo> getCampusCalendarsByYear(@WebParam(name = "year") Integer year, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Validates a campus calendar. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current object and its directly contained subobjects
     * or expanded to perform all tests related to this object. If an
     * identifier is present for the campus calendar and a record
     * is found for that identifier, the validation checks if the
     * campus calendar can be shifted to the new values. If a
     * record cannot be found for the identifier, it is assumed that
     * the record does not exist and as such, the checks performed
     * will be much shallower, typically mimicking those performed by
     * setting the validationType to the current object. This is a
     * slightly different pattern from the standard validation as the
     * caller provides the identifier in the create statement instead
     * of the server assigning an identifier.
     *
     * @param validationType Identifier of the extent of validation
     * @param campusCalendarInfo the campus calendar information to be tested.
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, campusCalendarInfo
     * @throws MissingParameterException missing validationTypeKey, campusCalendarInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateCampusCalendar(@WebParam(name = "validationType") String validationType, @WebParam(name = "campusCalendarInfo") CampusCalendarInfo campusCalendarInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Creates a new Campus Calendar.
     *
     * @param campusCalendarKey the key of the Campus Calendar to be created
     * @param campusCalendarInfo Details of the Campus Calendar to be created
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of the Campus Calendar just created
     * @throws AlreadyExistsException the Campus Calendar being created already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public CampusCalendarInfo createCampusCalendar(@WebParam(name = "campusCalendarKey") String campusCalendarKey, @WebParam(name = "campusCalendarInfo") CampusCalendarInfo campusCalendarInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates an existing Campus Calendar.
     *
     * @param campusCalendarKey Key of Campus Calendar to be updated
     * @param campusCalendarInfo Details of updates to the Campus
     *        Calendar being updated
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of Campus Calendar just updated
     * @throws DataValidationErrorException One or more values invalid for this 
     *         operation
     * @throws DoesNotExistException the Campus Calendar does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date 
     *         version.
     */
    public CampusCalendarInfo updateCampusCalendar(@WebParam(name = "campusCalendarKey") String campusCalendarKey, @WebParam(name = "campusCalendarInfo") CampusCalendarInfo campusCalendarInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Deletes an existing Campus Calendar.
     *
     * @param campusCalendarKey the key of the Campus Calendar to
     *        be deleted
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException the Campus Calendar does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteCampusCalendar(@WebParam(name = "campusCalendarKey") String campusCalendarKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method returns the TypeInfo for a given Term type key.
     *
     * @param teryTypeKey Key of the type
     * @param context Context information containing the principalId
     *        and locale information about the caller of service
     *        operation
     * @return Information about the Type
     * @throws DoesNotExistException  termTypeKey not found
     * @throws InvalidParameterException invalid termTypeKey
     * @throws MissingParameterException missing termTypeKey
     * @throws OperationFailedException unable to complete request
     */    
    public TypeInfo getTermType(@WebParam(name = "termTypeKey") String termTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * This method returns the valid Term types.
     *
     * @param context Context information containing the principalId
     *        and locale information about the caller of service
     *        operation
     * @return a list of valid term Types
     * @throws InvalidParameterException invalid context
     * @throws MissingParameterException missing context
     * @throws OperationFailedException unable to complete request
     */
    public List<TypeInfo> getTermTypes(@WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * This method returns the details on a term state.
     *
     * @param termStateKey a key for a term state
     * @param context Context information containing the principalId and 
     *        locale information about the caller of service operation
     * @return state info
     * @throws DoesNotExistException  termTypeKey not found
     * @throws InvalidParameterException invalid context
     * @throws MissingParameterException missing context
     * @throws OperationFailedException unable to complete request
     */
    public StateInfo getTermState(@WebParam(name = "termStateKey") String termStateKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * This method returns the possible term states.
     *
     * @param context Context information containing the principalId and 
     *        locale information about the caller of service operation
     * @return a list of valid term states
     * @throws InvalidParameterException invalid context
     * @throws MissingParameterException missing context
     * @throws OperationFailedException unable to complete request
     */
    public List<StateInfo> getTermStates(@WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the details of a single Term by a term key.
     *
     * @param termKey Unique key of the Term to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of the Term requested
     * @throws DoesNotExistException termKey not found
     * @throws InvalidParameterException invalid termKey
     * @throws MissingParameterException missing termKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public TermInfo getTerm(@WebParam(name = "termKey") String termKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list Terms corresponding to a list of
     * terms keys.
     *
     * @param termKeyList list of unique keys of the
     *        Term to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Terms
     * @throws DoesNotExistException a termKey in list not found
     * @throws InvalidParameterException invalid termKey in list
     * @throws MissingParameterException missing termKeyList
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TermInfo> getTermsByKeyList(@WebParam(name = "termKeyList") List<String> termKeyList, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Terms by Type.
     *
     * @param termTypeKey a Type of Term to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Terms of the given Type
     * @throws InvalidParameterException invalid termTypeKey
     * @throws MissingParameterException missing termTypeKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getTermKeysByType(@WebParam(name = "termTypeKey") String termTypeKey, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list Terms mapped to the given Academic Calendar.
     *
     * Mappings are managed through Type configuration and inferred by
     * the dates of the calendar and term so operations to manage the
     * mappings are not currently defined.
     *
     * @param academicCalendarKey a key for an academic calendar
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Terms
     * @throws DoesNotExistException the academic calendar not found
     * @throws InvalidParameterException invalid acadenmicCalendarKey
     * @throws MissingParameterException missing academicCalendarKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TermInfo> getTermsForAcademicCalendar(@WebParam(name = "academicCalendarKey") String academicCalendarKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list Terms nested inside the given Term.
     *
     * Mappings are managed through Type configuration and inferred by
     * the dates of the terms so operations to manage the mappings are
     * not currently defined.
     *
     * @param termKey a key for a Term
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Terms
     * @throws DoesNotExistException the term is not found
     * @throws InvalidParameterException invalid termKey
     * @throws MissingParameterException missing termKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TermInfo> getTermsForTerm(@WebParam(name = "termKey") String termKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Validates a term. Depending on the value of validationType,
     * this validation could be limited to tests on just the current
     * object and its directly contained subobjects or expanded to
     * perform all tests related to this object. If an identifier is
     * present for the term and a record is found for that identifier,
     * the validation checks if the term can be shifted to the new
     * values. If a record cannot be found for the identifier, it is
     * assumed that the record does not exist and as such, the checks
     * performed will be much shallower, typically mimicking those
     * performed by setting the validationType to the current
     * object. This is a slightly different pattern from the standard
     * validation as the caller provides the identifier in the create
     * statement instead of the server assigning an identifier.
     *
     * @param validationType Identifier of the extent of validation
     * @param termInfo the term information to be tested.
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, termInfo
     * @throws MissingParameterException missing validationTypeKey, termInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateTerm(@WebParam(name = "validationType") String validationType, @WebParam(name = "termInfo") TermInfo termInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Creates a new Tern.
     *
     * @param termKey the key of the term to be created
     * @param termInfo Details of the term to be created
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of the Term just created
     * @throws AlreadyExistsException the Term being created already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public TermInfo createTerm(@WebParam(name = "termKey") String termKey, @WebParam(name = "termInfo") TermInfo termInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates an existing Term.
     *
     * @param termKey Key of Term to be updated
     * @param termInfo Details of updates to the Term being updated
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of Term just updated
     * @throws DataValidationErrorException One or more values invalid for this 
     *         operation
     * @throws DoesNotExistException the Term does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date 
     *         version.
     */
    public TermInfo updateTerm(@WebParam(name = "termKey") String termKey, @WebParam(name = "termInfo") TermInfo termInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Deletes an existing Term.
     *
     * @param termKey the key of the Term to be deleted
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException the Term does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteTerm(@WebParam(name = "termKey") String termKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method returns the TypeInfo for a given KeyDate type key.
     *
     * @param typeKey Key of the type
     * @param context Context information containing the principalId
     *        and locale information about the caller of service
     *        operation
     * @return Information about the Type
     * @throws DoesNotExistException  keyDateTypeKey not found
     * @throws InvalidParameterException invalid keyDateTypeKey
     * @throws MissingParameterException missing keyDateTypeKey
     * @throws OperationFailedException unable to complete request
     */    
    public TypeInfo getKeyDateType(@WebParam(name = "keyDateTypeKey") String keyDateTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * This method returns the valid key date types for the given
     * term type.
     *
     * @param context Context information containing the principalId
     *        and locale information about the caller of service
     *        operation
     * @param campusCalendatTypeKey
     * @return a list of valid key date Types
     * @throws DoesNotExistException termTypeKey not found
     * @throws InvalidParameterException invalid key or context
     * @throws MissingParameterException missing key or context
     * @throws OperationFailedException unable to complete request
     */
    public List<TypeInfo> getKeyDateTypesForTermType(@WebParam(name = "termTypeKey") String termTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves the details of a single key date by a key date key.
     *
     * @param keyDateKey Unique key of the key date to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of the key date requested
     * @throws DoesNotExistException keyDateKey not found
     * @throws InvalidParameterException invalid keyDateKey
     * @throws MissingParameterException missing keyDateKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public KeyDateInfo getKeyDate(@WebParam(name = "keyDateKey") String keyDateKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of key dates corresponding to a list of
     * key date keys.
     *
     * @param keyDateKeyList list of unique keys of the
     *        key date to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of key dates
     * @throws DoesNotExistException an  keyDateKey in list not found
     * @throws InvalidParameterException invalid keyDateKey in list
     * @throws MissingParameterException missing keyDateKeyList
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<KeyDateInfo> getKeyDatesByKeyList(@WebParam(name = "keyDateKeyList") List<String> keyDateKeyList, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of key dates by Type.
     *
     * @param keyDateTypeKey a Type of key date to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of key dates of the given Type
     * @throws InvalidParameterException invalid key dateTypeKey
     * @throws MissingParameterException missing keyDateTypeKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getKeyDateKeysByType(@WebParam(name = "keyDateTypeKey") String keyDateTypeKey, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of key dates for an academic calendar. The
     * dates include all key dates mapped to any terms and sub terms.
     *
     * @param academicCalendarKey
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of key dates
     * @throws DoesNotExistException an academicCalendarKey not found
     * @throws InvalidParameterException invalid academicCalendarKey
     * @throws MissingParameterException missing academicCalendarKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<KeyDateInfo> getKeyDatesForAcademicCalendar(@WebParam(name = "academicCalendarKey") String academicCalendarKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of key dates for an academic calendar that 
     * fall within the given date range inclusive. The dates
     * include all key dates mapped to any terms and sub terms.
     *
     * @param academicCalendarKey
     * @param startDate the start of the date range
     * @param endDate the end of the date range
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of key dates
     * @throws DoesNotExistException an academicCalendarKey not found
     * @throws InvalidParameterException invalid academicCalendarKey
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<KeyDateInfo> getKeyDatesForAcademicCalendarByDate(@WebParam(name = "academicCalendarKey") String academicCalendarKey, @WebParam(name = "startDate") Date startDate, @WebParam(name = "endDate") Date endDate, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of key dates immediately mapped to a Term.
     *
     * @param termKey
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of key dates
     * @throws DoesNotExistException a termKey not found
     * @throws InvalidParameterException invalid termKey
     * @throws MissingParameterException missing termKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<KeyDateInfo> getKeyDatesForTerm(@WebParam(name = "termKey") String termKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of key dates for a given Term that 
     * fall within the given date range inclusive. The dates
     * include only those dates immediate mapped to the Term.
     *
     * @param termKey unique key for a Term
     * @param startDate start of date range
     * @param endDate end of date range
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of key dates
     * @throws DoesNotExistException a termKey not found
     * @throws InvalidParameterException invalid termKey
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<KeyDateInfo> getKeyDatesForTermByDate(@WebParam(name = "termKey") String termKey, @WebParam(name = "startDate") Date startDate, @WebParam(name = "endDate") Date endDate, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of key dates for a Term. The dates
     * include all key dates mapped to any nested terms.
     *
     * @param termKey unique key for a Term
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of key dates
     * @throws DoesNotExistException a termKey not found
     * @throws InvalidParameterException invalid termKey
     * @throws MissingParameterException missing termKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<KeyDateInfo> getAllKeyDatesForTerm(@WebParam(name = "termKey") String termKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of key dates for a given Term that 
     * fall within the given date range inclusive. The dates
     * include all key dates mapped to any nested terms.
     *
     * @param termKey unique key for a Term
     * @param startDate start of date range
     * @param endDate end of date range
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of key dates
     * @throws DoesNotExistException a termKey not found
     * @throws InvalidParameterException invalid termKey
     * @throws MissingParameterException missing parameter
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<KeyDateInfo> getKeyDatesForAllTermsByDate(@WebParam(name = "termKey") String termKey, @WebParam(name = "startDate") Date startDate, @WebParam(name = "endDate") Date endDate, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Validates a key date. Depending on the value of validationType,
     * this validation could be limited to tests on just the current
     * object and its directly contained subobjects or expanded to
     * perform all tests related to this object. If an identifier is
     * present for the key date and a record is found for that identifier,
     * the validation checks if the key date can be shifted to the new
     * values. If a record cannot be found for the identifier, it is
     * assumed that the record does not exist and as such, the checks
     * performed will be much shallower, typically mimicking those
     * performed by setting the validationType to the current
     * object. This is a slightly different pattern from the standard
     * validation as the caller provides the identifier in the create
     * statement instead of the server assigning an identifier.
     *
     * @param validationType Identifier of the extent of validation
     * @param keyDateInfo the key date information to be tested.
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, keyDateInfo
     * @throws MissingParameterException missing validationTypeKey, keyDateInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateKeyDate(@WebParam(name = "validationType") String validationType, @WebParam(name = "keyDateInfo") KeyDateInfo keyDateInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Creates a new Key Date for a Term.
     *
     * @param termKey a key for a Term to which this date is mapped
     * @param keyDateKey the key of the Key Date to be created
     * @param keyDateInfo Details of the Key Date to be created
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of the Key Date just created
     * @throws AlreadyExistsException the Key Date being created already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public KeyDateInfo createKeyDateForTerm(@WebParam(name = "termKey") String termKey, @WebParam(name = "keyDateKey") String keyDateKey, @WebParam(name = "keyDateInfo") KeyDateInfo keyDateInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates an existing Key Date.
     *
     * @param keyDateKey Key of Key Date to be updated
     * @param keyDateInfo Details of updates to the key date
     *        being updated
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of key date just updated
     * @throws DataValidationErrorException One or more values invalid for this 
     *         operation
     * @throws DoesNotExistException the key date does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date 
     *         version.
     */
    public KeyDateInfo updateKeyDate(@WebParam(name = "keyDateKey") String keyDateKey, @WebParam(name = "keyDateInfo") KeyDateInfo keyDateInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Deletes an existing KeyDate.
     *
     * @param keyDateKey the key of the Key Date to
     *        be deleted
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException the KeyDate does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteKeyDate(@WebParam(name = "keyDateKey") String keyDateKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method returns the TypeInfo for a given holiday type key.
     *
     * @param holidayTypeKey Key of the type
     * @param context Context information containing the principalId
     *        and locale information about the caller of service
     *        operation
     * @return Information about the Type
     * @throws DoesNotExistException holidayTypeKey not found
     * @throws InvalidParameterException invalid holidayTypeKey
     * @throws MissingParameterException missing holidayTypeKey
     * @throws OperationFailedException unable to complete request
     */    
    public TypeInfo getHolidayType(@WebParam(name = "holidayTypeKey") String holidayTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * This method returns the valid holiday types for the given
     * campus calendar type.
     *
     * @param context Context information containing the principalId
     *        and locale information about the caller of service
     *        operation
     * @param campusCalendatTypeKey
     * @return a list of valid holiday Types
     * @throws DoesNotExistException campusCalendarTypeKey not found
     * @throws InvalidParameterException invalid key or context
     * @throws MissingParameterException missing key or context
     * @throws OperationFailedException unable to complete request
     */
    public List<TypeInfo> getHolidayTypesForCampusCalendarType(@WebParam(name = "campusCalendarTypeKey") String campusCalendarTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Retrieves a list of holidays for an academic calendar.
     *
     * @param academicCalendarKey
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of holidays
     * @throws DoesNotExistException an academicCalendarKey not found
     * @throws InvalidParameterException invalid academicCalendarKey
     * @throws MissingParameterException missing academicCalendarKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<HolidayInfo> getHolidaysForAcademicCalendar(@WebParam(name = "academicCalendarKey") String academicCalendarKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Validates a holiday. Depending on the value of validationType,
     * this validation could be limited to tests on just the current
     * object and its directly contained subobjects or expanded to
     * perform all tests related to this object. If an identifier is
     * present for the holiday and a record is found for that identifier,
     * the validation checks if the holiday can be shifted to the new
     * values. If a record cannot be found for the identifier, it is
     * assumed that the record does not exist and as such, the checks
     * performed will be much shallower, typically mimicking those
     * performed by setting the validationType to the current
     * object. This is a slightly different pattern from the standard
     * validation as the caller provides the identifier in the create
     * statement instead of the server assigning an identifier.
     *
     * @param validationType Identifier of the extent of validation
     * @param holidayInfo the holiday information to be tested.
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, holidayInfo
     * @throws MissingParameterException missing validationTypeKey, holidayInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateHoliday(@WebParam(name = "validationType") String validationType, @WebParam(name = "holidayInfo") HolidayInfo holidayInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Creates a new Holiday for a Campus Calendar.
     *
     * @param campusCalendarKey a key for a Term to which this holiday is mapped
     * @param holidayKey the key of the Holiday to be created
     * @param holidayInfo Details of the Holiday to be created
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of the Holiday just created
     * @throws AlreadyExistsException the Holiday being created already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public HolidayInfo createHolidayForCampusCalendar(@WebParam(name = "campusCalendarKey") String campusCalendarKey, @WebParam(name = "holidayKey") String holidayKey, @WebParam(name = "holidayInfo") HolidayInfo holidayInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates an existing Holiday.
     *
     * @param holidayKey Key of Holiday to be updated
     * @param holidayInfo Details of updates to the holiday
     *        being updated
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of holiday just updated
     * @throws DataValidationErrorException One or more values invalid for this 
     *         operation
     * @throws DoesNotExistException the holiday does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date 
     *         version.
     */
    public HolidayInfo updateHoliday(@WebParam(name = "holidayKey") String holidayKey, @WebParam(name = "holidayInfo") HolidayInfo holidayInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Deletes an existing Holiday.
     *
     * @param holidayKey the key of the Holiday to
     *        be deleted
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException the Holiday does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteHoliday(@WebParam(name = "holidayKey") String holidayKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Gets the registration key date group for a term.
     *
     * @param termKey unique key of a term
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the registration date group
     * @throws DoesNotExistException termKey not found
     * @throws InvalidParameterException invalid termKey
     * @throws MissingParameterException missing termKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public RegistrationDateGroupInfo getRegistrationDateGroup(@WebParam(name = "termKey") String termKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Validates an registration date grpup. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current object and its directly contained subobjects
     * or expanded to perform all tests related to this object. If an
     * identifier is present for the registration date group and a
     * record is found for that identifier, the validation checks if
     * the registration date group can be shifted to the new values. If
     * a record cannot be found for the identifier, it is assumed that
     * the record does not exist and as such, the checks performed
     * will be much shallower, typically mimicking those performed by
     * setting the validationType to the current object. This is a
     * slightly different pattern from the standard validation as the
     * caller provides the identifier in the create statement instead
     * of the server assigning an identifier.
     *
     * @param validationType Identifier of the extent of validation
     * @param registrationDateGroupInfo the registration date group to be tested.
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, academicCalendarInfo
     * @throws MissingParameterException missing validationTypeKey, academicCalendarInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateRegistrationDateGroup(@WebParam(name = "validationType") String validationType, @WebParam(name = "registrationDateGroupInfo") RegistrationDateGroupInfo registrationDateGroupInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Updates a the registration key date group for a term. The
     * date group is a set of hardened well-known dates. 
     *
     * Updating an registration date group is a short cut to creating or
     * updating the corresponding key dates and relating them to the
     * given term. An RegistrationDateGroupInfo is available for all
     * Terms by default and does not explicitly need to be created.
     *
     * If the KeyDates have not been created for the dates in
     * the group, an RegistrationDateGroup is still returned from
     * getRegistrationDateGroup() but with empty or default dates.
     *
     * Setting date values in an RegistrationDateGroup through this
     * update method either creates the KeyDates with the appropriate
     * Types that map to the fields in the date group for the given
     * Term or updates them if they already exist.
     *
     * @param termKey key of Term
     * @param registrationDateGroup the registration date group
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of enrollmemnt date group just updated
     * @throws DataValidationErrorException One or more values invalid for this 
     *         operation
     * @throws DoesNotExistException the term does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date 
     *         version.
     */
    public RegistrationDateGroupInfo updateRegistrationDateGroup(@WebParam(name = "termKey") String termKey, @WebParam(name = "registrationDateGroupInfo") RegistrationDateGroupInfo registrationDateGroupInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Calculates the number of instructional days for a Term. The
     * number of instructional days is the number of class days in a
     * Term minus the non-instructional holidays on the related campus
     * calendar.
     *
     * @param termKey unique key of a term
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the number of instructional days
     * @throws DoesNotExistException termKey not found
     * @throws InvalidParameterException invalid termKey
     * @throws MissingParameterException missing termKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public Integer getInstructionalDaysForTerm(@WebParam(name = "termKey") String termKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
}
