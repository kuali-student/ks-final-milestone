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

package org.kuali.student.core.academiccalendar.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.common.exceptions.AlreadyExistsException;
import org.kuali.student.common.exceptions.DataValidationErrorException;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.exceptions.PermissionDeniedException;
import org.kuali.student.common.exceptions.VersionMismatchException;

import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.common.dto.ValidationResultInfo;
import org.kuali.student.common.service.TypeService;
import org.kuali.student.common.service.StateService;

import org.kuali.student.core.academiccalendar.dto.AcademicCalendarInfo;
import org.kuali.student.core.academiccalendar.dto.CampusCalendarInfo;
import org.kuali.student.core.academiccalendar.dto.TermInfo;
import org.kuali.student.core.academiccalendar.dto.KeyDateInfo;
import org.kuali.student.core.academiccalendar.dto.HolidayInfo;
import org.kuali.student.core.academiccalendar.dto.EnrollmentDateGroupInfo;

import org.kuali.student.datadictionary.service.DataDictionaryService;

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

@WebService(name = "AcademicCalendarService", targetNamespace = "http://student.kuali.org/wsdl/academiccalendar")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface AcademicCalendarService extends DataDictionaryService, TypeService, StateService {

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
     * @throws DoesNotExistExceptionan  academicCalendarKey in list not found
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
     * Copy an Academic Calendar. The associated Terms and key datess
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
     * @throws DoesNotExistExceptionan  campusCalendarKey in list not found
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
     * @throws DoesNotExistExceptionan  termKey in list not found
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
     * @throws DoesNotExistExceptionan  keyDateKey in list not found
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
     * dates include all key dates mapped to any terms, sub terms, or
     * campus calendars.
     *
     * @param academicCalendarKey
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of key dates
     * @throws DoesNotExistExceptionan academicCalendarKey not found
     * @throws InvalidParameterException invalid academicCalendarKey
     * @throws MissingParameterException missing academicCalendarKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<KeyDateInfo> getKeyDatesForAcademicCalendar(@WebParam(name = "academicCalendarKey") String academicCalendarKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of key dates immediately mapped to a Term.
     *
     * @param termKey
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of key dates
     * @throws DoesNotExistExceptionan termKey not found
     * @throws InvalidParameterException invalid termKey
     * @throws MissingParameterException missing termKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<KeyDateInfo> getKeyDatesForTerm(@WebParam(name = "termKey") String termKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of key dates for a Term. The dates
     * include all key dates mapped to any nested terms.
     *
     * @param termKey
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of key dates
     * @throws DoesNotExistExceptionan termKey not found
     * @throws InvalidParameterException invalid termKey
     * @throws MissingParameterException missing termKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<KeyDateInfo> getAllKeyDatesForTerm(@WebParam(name = "termKey") String termKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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
     * Creates a new Key Date for a Campus Calendar.
     *
     * @param campusCalendarKey a key for a Term to which this key date is mapped
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
    public KeyDateInfo createKeyDateForCampusCalendar(@WebParam(name = "campusCalendarKey") String campusCalendarKey, @WebParam(name = "keyDateKey") String keyDateKey, @WebParam(name = "keyDateInfo") KeyDateInfo keyDateInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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
     * Retrieves a list of holidays for an academic calendar.
     *
     * @param academicCalendarKey
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of holidays
     * @throws DoesNotExistExceptionan academicCalendarKey not found
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
     * Gets the enrollment key date group for a term.
     *
     * @param termKey unique key of a term
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the enrollment date group
     * @throws DoesNotExistException termKey not found
     * @throws InvalidParameterException invalid termKey
     * @throws MissingParameterException missing termKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public EnrollmentDateGroupInfo getEnrollmentDateGroup(@WebParam(name = "termKey") String termKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates a the enrollment key date group for a term. The
     * date group is a set of hardened well-known dates. Updating
     * a key date group is a short cut to creating the corresponding
     * key dates and relating them to the given term.
     *
     * @param termKey key of Term
     * @param enrollmentDateGroup the enrollment date group
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
    public EnrollmentDateGroupInfo updateEnrollmentDateGroup(@WebParam(name = "termKey") String termKey, @WebParam(name = "enrollmentDateGroup") EnrollmentDateGroupInfo enrollmentDateGroupInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

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
