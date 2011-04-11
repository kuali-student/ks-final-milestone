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
import org.kuali.student.core.academiccalendar.dto.MilestoneInfo;
import org.kuali.student.datadictionary.service.DataDictionaryService;


/**
 * Academic Calendar Service Description and Assumptions.
 *
 * *** IN PROGRESS ***
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
     * Retrieves a list of Academic Calendars by Type.
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
    public List<AcademicCalendarInfo> getAcademicCalendarsByType(@WebParam(name = "academicCalendarTypeKey") String academicCalendarTypeKey, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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
     * @param academicCalendarTypeKey the type of Academic Calendar to be created
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
    public AcademicCalendarInfo createAcademicCalendar(@WebParam(name = "academicCalendarTypeKey") String academicCalendarTypeKey, @WebParam(name = "academicCalendarKey") String academicCalendarKey, @WebParam(name = "academicCalendarInfo") AcademicCalendarInfo academicCalendarInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates an existing Academic Calendar..
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
     * @throws DoesNotExistException the Academiic Calendar does not exist
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
     * Copy an Academic Calendar. The associated Terms and Milestones
     * are also copied and related to this new calendar. This copy
     * operation allows for a calendar template to be created from an
     * existing calendar in which the dates of the Terms and
     * Milestones will be updated.
     *
     * Currently, this method does not perform any date
     * transformations on the Terms and Milestones (Labor Day has a
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
     * Generates calendaring data for the Terms and Milestones in an
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
    public List<CampusCalendarInfo> getCampusCalendarsByType(@WebParam(name = "campusCalendarTypeKey") String campusCalendarTypeKey, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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
     * @param campusCalendarTypeKey the type of Campus Calendar to be created
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
    public CampusCalendarInfo createCampusCalendar(@WebParam(name = "campusCalendarTypeKey") String campusCalendarTypeKey, @WebParam(name = "campusCalendarKey") String campusCalendarKey, @WebParam(name = "campusCalendarInfo") CampusCalendarInfo campusCalendarInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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
     * @throws DoesNotExistException the Academiic Calendar does not exist
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
    public List<TermInfo> getTermsByType(@WebParam(name = "termTypeKey") String termTypeKey, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Gets the immediate children nested inside a Term.
     *
     * @param termKey a term
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of immediate Terms
     * @throws InvalidParameterException invalid termKey
     * @throws MissingParameterException missing termKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TermInfo> getTermsForTerm(@WebParam(name = "termKey") String termKey, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Gets all the descendant Terms inside a Term.
     *
     * @param termKey a term
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of immediate Terms
     * @throws InvalidParameterException invalid termKey
     * @throws MissingParameterException missing termKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TermInfo> getAllTermsForTerm(@WebParam(name = "termKey") String termKey, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

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
     * Creates a new Term.
     *
     * @param termTypeKey the type of Term to be created
     * @param termKey the key of the Term to be created
     * @param termInfo Details of the Term to be created
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
    public TermInfo createTerm(@WebParam(name = "termTypeKey") String termTypeKey, @WebParam(name = "termKey") String termKey, @WebParam(name = "termInfo") TermInfo termInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates an existing Term.
     *
     * @param termKey Key of Term to be updated
     * @param termInfo Details of updates to the Term
     *        being updated
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of Term just updated
     * @throws DataValidationErrorException One or more values invalid for this 
     *         operation
     * @throws DoesNotExistException the Academiic Calendar does not exist
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
     * @param termKey the key of the Term to
     *        be deleted
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
     * Retrieves the details of a single Milestone by a milestone key.
     *
     * @param milestoneKey Unique key of the Milestone to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of the Milestone requested
     * @throws DoesNotExistException milestoneKey not found
     * @throws InvalidParameterException invalid milestoneKey
     * @throws MissingParameterException missing milestoneKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public MilestoneInfo getMilestone(@WebParam(name = "milestoneKey") String milestoneKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list Milestones corresponding to a list of
     * milestones keys.
     *
     * @param milestoneKeyList list of unique keys of the
     *        Milestone to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Milestones
     * @throws DoesNotExistExceptionan  milestoneKey in list not found
     * @throws InvalidParameterException invalid milestoneKey in list
     * @throws MissingParameterException missing milestoneKeyList
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<MilestoneInfo> getMilestonesByKeyList(@WebParam(name = "milestoneKeyList") List<String> milestoneKeyList, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Retrieves a list of Milestones by Type.
     *
     * @param milestoneTypeKey a Type of Milestone to be retrieved
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return a list of Milestones of the given Type
     * @throws InvalidParameterException invalid milestoneTypeKey
     * @throws MissingParameterException missing milestoneTypeKey
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<MilestoneInfo> getMilestonesByType(@WebParam(name = "milestoneTypeKey") String milestoneTypeKey, @WebParam(name = "context") ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Validates a milestone. Depending on the value of validationType,
     * this validation could be limited to tests on just the current
     * object and its directly contained subobjects or expanded to
     * perform all tests related to this object. If an identifier is
     * present for the milestone and a record is found for that identifier,
     * the validation checks if the milestone can be shifted to the new
     * values. If a record cannot be found for the identifier, it is
     * assumed that the record does not exist and as such, the checks
     * performed will be much shallower, typically mimicking those
     * performed by setting the validationType to the current
     * object. This is a slightly different pattern from the standard
     * validation as the caller provides the identifier in the create
     * statement instead of the server assigning an identifier.
     *
     * @param validationType Identifier of the extent of validation
     * @param milestoneInfo the milestone information to be tested.
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, milestoneInfo
     * @throws MissingParameterException missing validationTypeKey, milestoneInfo
     * @throws OperationFailedException unable to complete request
     */
    public List<ValidationResultInfo> validateMilestone(@WebParam(name = "validationType") String validationType, @WebParam(name = "milestoneInfo") MilestoneInfo milestoneInfo, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /** 
     * Creates a new Milestone.
     *
     * @param milestoneTypeKey the type of Milestone to be created
     * @param milestoneKey the key of the Milestone to be created
     * @param milestoneInfo Details of the Milestone to be created
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of the Milestone just created
     * @throws AlreadyExistsException the Milestone being created already exists
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public MilestoneInfo createMilestone(@WebParam(name = "milestoneTypeKey") String milestoneTypeKey, @WebParam(name = "milestoneKey") String milestoneKey, @WebParam(name = "milestoneInfo") MilestoneInfo milestoneInfo, @WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /** 
     * Updates an existing Milestone.
     *
     * @param milestoneKey Key of Milestone to be updated
     * @param milestoneInfo Details of updates to the Milestone
     *        being updated
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return the details of Milestone just updated
     * @throws DataValidationErrorException One or more values invalid for this 
     *         operation
     * @throws DoesNotExistException the Academiic Calendar does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws VersionMismatchException The action was attempted on an out of date 
     *         version.
     */
    public MilestoneInfo updateMilestone(@WebParam(name = "milestoneKey") String milestoneKey, @WebParam(name = "milestoneInfo") MilestoneInfo milestoneInfo, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException;

    /** 
     * Deletes an existing Milestone.
     *
     * @param milestoneKey the key of the Milestone to
     *        be deleted
     * @param context Context information containing the principalId
     *                and locale information about the caller of service
     *                operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException the Milestone does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteMilestone(@WebParam(name = "milestoneKey") String milestoneKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
}
