/**
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
 * agreed to in writing, software distributed under the License is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.acal.service;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.core.acal.dto.AcalEventInfo;
import org.kuali.student.r2.core.acal.dto.HolidayCalendarInfo;
import org.kuali.student.r2.core.acal.dto.HolidayInfo;
import org.kuali.student.r2.core.acal.dto.KeyDateInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.Date;
import java.util.List;

/**
 * This service manages Academic Calendars. There are three kinds of calendars
 * in this service.
 * <p/>
 * 1. HolidayCalendar:  a HolidayCalendar relates to a Campus and is intended to
 * define all the Holiday dates and other non-instructional days on a designated
 * campus.
 * <p/>
 * 2. AcademicCalendar: an AcademicCalendar is a calendar of Terms. The Academic
 * Calendar may have one or more HolidayCalendars. This distinction is to allow
 * multiple AcademicCalendars to exist on a Campus without having to manage
 * multiple sets of Holidays. An AcademicCalendar may be referenced from a
 * ProgramOffering.
 * <p/>
 * An AcademicCalendar may have its own milestones called AcalEvents.
 * <p/>
 * 3. Term:             A Term has KeyDates and may have Terms nested within.
 * <p/>
 * For example, an AcademicCalendar for the undergraduate program in a given
 * year may have a HolidayCalendar for the holidays of that year, Fall, Spring,
 * and Summer Terms, the Fall and Spring mini-mesters, all with their own
 * managed dates.
 * <p/>
 * Not all of the relations among the entities are maintained within the
 * entities. To maximize flexibility and to easily reference calendars and terms
 * externally, these mappings are often implied by the service operations.
 * <p/>
 * Each of the calendaring entities have their own milestone structures.
 * <p/>
 * 1. Holiday:   A milestone used with HolidayCalendars (e.g. Labor Day). 2.
 * AcalEvent: A milestone used with AcademicCalendars (e.g. Commencement). 3.
 * KeyDate:   A milestone used with Terms (e.g. Registration Period).
 * <p/>
 * Version: 1.0 (Dev)
 *
 * @author tom
 * @since Sun Apr 10 14:22:34 EDT 2011
 */

@WebService(name = "AcademicCalendarService", serviceName = "AcademicCalendarService", portName = "AcademicCalendarService", targetNamespace = AcademicCalendarServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)

public interface AcademicCalendarService {

    /**
     * Retrieves an AcademicCalendar Type by Type key.
     *
     * @param academicCalendarTypeKey the key of an AcademicCalendar Type
     * @param contextInfo             information containing the principalId and
     *                                locale information about the caller of
     *                                service operation
     * @return the type requested
     * @throws DoesNotExistException     academicCalendarTypeKey is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException academicCalendarTypeKey or contextInfo
     *                                   is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public TypeInfo getAcademicCalendarType(@WebParam(name = "academicCalendarTypeKey") String academicCalendarTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets the valid AcademicCalendar types.
     *
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of valid AcademicCalendar Types
     * @throws InvalidParameterException contextInfo is not value
     * @throws MissingParameterException contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<TypeInfo> getAcademicCalendarTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets an AcademicCalendar State by key.
     *
     * @param academicCalendarStateKey a key for an AcademicCalendar State
     * @param contextInfo              information containing the principalId
     *                                 and locale information about the caller
     *                                 of service operation
     * @return the AcademicCalendar State requested
     * @throws DoesNotExistException     academicCalendarStateKey is not found
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException academicCalendarStateKey or contextInfo
     *                                   is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StateInfo getAcademicCalendarState(@WebParam(name = "academicCalendarStateKey") String academicCalendarStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets the valid AcademicCalendar States.
     *
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of valid AcademicCalendar States
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<StateInfo> getAcademicCalendarStates(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a single AcademicCalendar by an AcademicCalendar Id.
     *
     * @param academicCalendarId the identifier for the AcademicCalendar to be
     *                           retrieved
     * @param contextInfo        information containing the principalId and
     *                           locale information about the caller of service
     *                           operation
     * @return the AcademicCalendar requested
     * @throws DoesNotExistException     academicCalendarIs is not found
     * @throws InvalidParameterException contextInfo isnot valid
     * @throws MissingParameterException academicCalendarId or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public AcademicCalendarInfo getAcademicCalendar(@WebParam(name = "academicCalendarId") String academicCalendarId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list AcademicCalendars from a list of AcademicCalendar Ids.
     * The returned list may be in any order and if duplicate Ids are supplied,
     * a unique set may or may not be returned.
     *
     * @param academicCalendarIds list of AcademicCalendar Ids
     * @param contextInfo         information containing the principalId and
     *                            locale information about the caller of service
     *                            operation
     * @return a list of AcademicCalendars
     * @throws DoesNotExistException     an academicCalendarId in list was not
     *                                   found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException academicCalendarIds, an Id in
     *                                   academicCalendarIds, or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<AcademicCalendarInfo> getAcademicCalendarsByIds(@WebParam(name = "academicCalendarIds") List<String> academicCalendarIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of AcademicCalendar Ids by Type.
     *
     * @param academicCalendarTypeKey an identifier for an AcademicCalendar
     *                                Type
     * @param contextInfo             information containing the principalId and
     *                                locale information about the caller of
     *                                service operation
     * @return a list of AcademicCalendars matching academicCalendarTypeKey or
     *         an empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException academicCalendarTypeKey or contextInfo
     *                                   is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getAcademicCalendarIdsByType(@WebParam(name = "academicCalendarTypeKey") String academicCalendarTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of AcademicCalendars that start in the given year.
     *
     * @param year        calendar year during which the academic calendar
     *                    starts
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of AcademicCalendars starting in the given year
     * @throws InvalidParameterException contextInfo i snot valid
     * @throws MissingParameterException year or context is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<AcademicCalendarInfo> getAcademicCalendarsByStartYear(@WebParam(name = "year") Integer year, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /**
     * Gets the list of academic calendars that use this term.
     *
     * @param termId  term identifier
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of AcademicCalendars using this term
     * @throws DoesNotExistException termId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException termId or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<AcademicCalendarInfo> getAcademicCalendarsForTerm(@WebParam(name = "termId") String termId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for AcademicCalendars that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of AcademicCalendar identifiers matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForAcademicCalendarIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for AcademicCalendars that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of AcademicCalendars matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<AcademicCalendarInfo> searchForAcademicCalendars(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates an AcademicCalendar. Depending on the value of validationType,
     * this validation could be limited to tests on just the current
     * AcademicCalendar and its directly contained subobjects or expanded to
     * perform all tests related to this AcademicCalendar. If an identifier is
     * present for the AcademicCalendar (and/or one of its contained
     * sub-objects) and a record is found for that identifier, the validation
     * checks if the AcademicCalendar can be updated to the new values. If an
     * identifier is not present or a record does not exist, the validation
     * checks if the AcademicCalendar with the given data can be created.
     *
     * @param validationTypeKey       the identifier for the validation Type
     * @param academicCalendarTypeKey the identifier for the AcademicCalendar
     *                                Type to be validated
     * @param academicCalendarInfo    the AcademicCalendar to be validated
     * @param contextInfo             information containing the principalId and
     *                                locale information about the caller of
     *                                service operation
     * @return a list of validation results or an empty list if validation
     *         succeeded
     * @throws DoesNotExistException     validationTypeKey or academicCalendarTypeKey
     *                                   is not found
     * @throws InvalidParameterException academicCalendarInfo or contextInfo is
     *                                   not valid
     * @throws MissingParameterException validationTypeKey, academicCalendarTypeKey,
     *                                   academicCalendarInfo, or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateAcademicCalendar(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "academicCalendarTypeKey") String academicCalendarTypeKey, @WebParam(name = "academicCalendarInfo") AcademicCalendarInfo academicCalendarInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new AcademicCalendar. The AcademicCalendar Id, Type, and Meta
     * information may not be set in the supplied data object.
     *
     * @param academicCalendarTypeKey the identifier for the Type of
     *                                AcademicCalendar to be created
     * @param academicCalendarInfo    the data with which to create the
     *                                AcademicCalendar
     * @param contextInfo             information containing the principalId and
     *                                locale information about the caller of
     *                                service operation
     * @return the new AcademicCalendar
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        academicCalendarTypeKey does not
     *                                      exist or is not supported
     * @throws InvalidParameterException    academicCalendarInfo or contextInfo
     *                                      is not valid
     * @throws MissingParameterException    academicCalendarTypeKey, academicCalendarInfo,
     *                                      or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read only
     */
    public AcademicCalendarInfo createAcademicCalendar(@WebParam(name = "academicCalendarTypeKey") String academicCalendarTypeKey, @WebParam(name = "academicCalendarInfo") AcademicCalendarInfo academicCalendarInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;


    /**
     * Updates an existing AcademicCalendar. The AcademicCalendar Id, Type, and
     * Meta information may not be changed.
     *
     * @param academicCalendarId   the identifier for the AcademicCalendar to be
     *                             updated
     * @param academicCalendarInfo the new data for the AcademicCalendar
     * @param contextInfo          information containing the principalId and
     *                             locale information about the caller of
     *                             service operation
     * @return the updated AcademicCalendar
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException AcademicCalendarId is not found
     * @throws InvalidParameterException academicCalendarInfo or
     *         contextInfo is not valid
     * @throws MissingParameterException academicCalendarId,
     *         academicCalendarInfo, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure
     *         occurred
     * @throws ReadOnlyException an attempt at changing information
     *         deisgnated as read only
     * @throws VersionMismatchException an optimistic locking failure
     *         or the action was attempted on an out of date version
     */
    public AcademicCalendarInfo updateAcademicCalendar(@WebParam(name = "academicCalendarId") String academicCalendarId, @WebParam(name = "academicCalendarInfo") AcademicCalendarInfo academicCalendarInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Changes the state of an existing AcademicCalendar to another state
     * provided that it is valid to do so.
     *
     * @param academicCalendarId Id of the AcademicCalendar to be updated.
     * @param nextStateKey       The State Key into which the identified
     *                           AcademicCalendar will be placed if the
     *                           operation succeeds.
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     the identified AcademicCalendar does
     *                                   not exist
     * @throws InvalidParameterException the contextInfo object is invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo changeAcademicCalendarState(@WebParam(name = "academicCalendarId") String academicCalendarId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Deletes an existing AcademicCalendar.
     *
     * @param academicCalendarId the identifier for the AcademicCalendar to be
     *                           deleted
     * @param contextInfo        information containing the principalId and
     *                           locale information about the caller of service
     *                           operation
     * @return the status of the operation. This must always be true.
     * @throws DoesNotExistException     AcademicCalendarId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException academicCalendarId or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteAcademicCalendar(@WebParam(name = "academicCalendarId") String academicCalendarId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Copy an AcademicCalendar. The associated Terms and key dates are also
     * copied and related to this new calendar. This copy operation allows for a
     * AcademicCalendar to be created from an existing AcademicCalendar in which
     * the dates of the Terms and Key Dates will be updated.
     * <p/>
     * This method may perform date transformations on the Terms and key dates
     * to make them applicable under the new academic calendar.
     *
     * @param academicCalendarId the identifier for the Academic Calendar to be
     *                           copied
     * @param startDate          the start of the new calendar
     * @param endDate            the end of the new calendar
     * @param contextInfo        information containing the principalId and
     *                           locale information about the caller of service
     *                           operation
     * @return new AcademicCalendarInfo created from the copy
     * @throws DoesNotExistException     academicCalendarId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException academicCalendarId, startYear, endYear,
     *                                   or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public AcademicCalendarInfo copyAcademicCalendar(@WebParam(name = "academicCalendarId") String academicCalendarId, @WebParam(name = "startDate") Date startDate, @WebParam(name = "endDate") Date endDate, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Generates calendaring data for the Terms and key dates in an Academic
     * Calendar. The format of the data is specified by the
     * calendarDataFormatType. An example of such a type is VCALENDAR.
     * <p/>
     * NOTE: this might be better placed in the Calendar (interface) Service.
     *
     * @param academicCalendarId        the key of the AcademicCalendar to be
     *                                  retrieved
     * @param calendarDataFormatTypeKey the type of format
     * @param contextInfo               information containing the principalId
     *                                  and locale information about the caller
     *                                  of service operation
     * @return calendar data
     * @throws DoesNotExistException     the AcademicCalendar or format type
     *                                   does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public String getAcademicCalendarData(@WebParam(name = "academicCalendarId") String academicCalendarId, @WebParam(name = "calendarDataFormatTypeKey") String calendarDataFormatTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Rerieves a HolidayCalendar Type by Type key.
     *
     * @param holidayCalendarTypeKey the key of a HolidayCalendar Type
     * @param contextInfo            information containing the principalId and
     *                               locale information about the caller of
     *                               service operation
     * @return the type requested
     * @throws DoesNotExistException     holidayCalendarTypeKey is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException holidayCalendarTypeKey or contextInfo
     *                                   is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public TypeInfo getHolidayCalendarType(@WebParam(name = "holidayCalendarTypeKey") String holidayCalendarTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets the valid HolidayCalendar types.
     *
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of valid HolidayCalendar Types
     * @throws InvalidParameterException contextInfo is not value
     * @throws MissingParameterException contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<TypeInfo> getHolidayCalendarTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets a HolidayCalendar State by key.
     *
     * @param holidayCalendarStateKey a key for a HolidayCalendar State
     * @param contextInfo             information containing the principalId and
     *                                locale information about the caller of
     *                                service operation
     * @return the HolidayCalendar State requested
     * @throws DoesNotExistException     holidayCalendarStateKey is not found
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException holidayCalendarStateKey or contextInfo
     *                                   is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StateInfo getHolidayCalendarState(@WebParam(name = "holidayCalendarStateKey") String holidayCalendarStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets the valid HolidayCalendar States.
     *
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of valid HolidayCalendar States
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<StateInfo> getHolidayCalendarStates(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a single HolidayCalendar by a HolidayCalendar Id.
     *
     * @param holidayCalendarId the identifier for the HolidayCalendar to be
     *                          retrieved
     * @param contextInfo       information containing the principalId and
     *                          locale information about the caller of service
     *                          operation
     * @return the HolidayCalendar requested
     * @throws DoesNotExistException     holidayCalendarIs is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException holidayCalendarId or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public HolidayCalendarInfo getHolidayCalendar(@WebParam(name = "holidayCalendarId") String holidayCalendarId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list HolidayCalendars from a list of HolidayCalendar Ids. The
     * returned list may be in any order and if duplicate Ids are supplied, a
     * unique set may or may not be returned.
     *
     * @param holidayCalendarIds list of HolidayCalendar Ids
     * @param contextInfo        information containing the principalId and
     *                           locale information about the caller of service
     *                           operation
     * @return a list of HolidayCalendars
     * @throws DoesNotExistException     a holidayCalendarId in list was not
     *                                   found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException holidayCalendarIds, an Id in
     *                                   holidayCalendarIds, or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<HolidayCalendarInfo> getHolidayCalendarsByIds(@WebParam(name = "holidayCalendarIds") List<String> holidayCalendarIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of HolidayCalendar Ids by Type.
     *
     * @param holidayCalendarTypeKey an identifier for an HolidayCalendar Type
     * @param contextInfo            information containing the principalId and
     *                               locale information about the caller of
     *                               service operation
     * @return a list of HolidayCalendars matching holidayCalendarTypeKey or an
     *         empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException holidayCalendarTypeKey or contextInfo
     *                                   is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getHolidayCalendarIdsByType(@WebParam(name = "holidayCalendarTypeKey") String holidayCalendarTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of HolidayCalendars that start in the given year.
     *
     * @param year        calendar year during which the holiday calendar
     *                    starts
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of HolidayCalendars starting in the given year
     * @throws InvalidParameterException contextInfo i snot valid
     * @throws MissingParameterException year or context is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<HolidayCalendarInfo> getHolidayCalendarsByStartYear(@WebParam(name = "year") Integer year, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for HolidayCalendars that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of HolidayCalendar identifiers matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForHolidayCalendarIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for HolidayCalendars that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of HolidayCalendars matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<HolidayCalendarInfo> searchForHolidayCalendars(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates a HolidayCalendar. Depending on the value of validationType,
     * this validation could be limited to tests on just the current
     * HolidayCalendar and its directly contained subobjects or expanded to
     * perform all tests related to this HolidayCalendar. If an identifier is
     * present for the HolidayCalendar (and/or one of its contained sub-objects)
     * and a record is found for that identifier, the validation checks if the
     * HolidayCalendar can be updated to the new values. If an identifier is not
     * present or a record does not exist, the validation checks if the
     * HolidayCalendar with the given data can be created.
     *
     * @param validationTypeKey      the identifier for the validation Type
     * @param holidayCalendarTypeKey the identifier for the HolidayCalendar Type
     *                               to be validated
     * @param holidayCalendarInfo    the HolidayCalendar to be validated
     * @param contextInfo            information containing the principalId and
     *                               locale information about the caller of
     *                               service operation
     * @return a list of validation results or an empty list if validation
     *         succeeded
     * @throws DoesNotExistException     validationTypeKey or holidayCalendarTypeKey
     *                                   is not found
     * @throws InvalidParameterException holidayCalendarInfo or contextInfo is
     *                                   not valid
     * @throws MissingParameterException validationTypeKey, holidayCalendarTypeKey,
     *                                   holidayCalendarInfo, or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateHolidayCalendar(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "holidayCalendarTypeKey") String holidayCalendarTypeKey, @WebParam(name = "holidayCalendarInfo") HolidayCalendarInfo holidayCalendarInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new HolidayCalendar. The HolidayCalendar Id, Type, and Meta
     * information may not be set in the supplied data object.
     *
     * @param holidayCalendarTypeKey the identifier for the Type of
     *                               HolidayCalendar to be created
     * @param holidayCalendarInfo    the data with which to create the
     *                               HolidayCalendar
     * @param contextInfo            information containing the principalId and
     *                               locale information about the caller of
     *                               service operation
     * @return the new HolidayCalendar
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        holidayCalendarTypeKey does not
     *                                      exist or is not supported
     * @throws InvalidParameterException    holidayCalendarInfo or contextInfo
     *                                      is not valid
     * @throws MissingParameterException    holidayCalendarTypeKey, holidayCalendarInfo,
     *                                      or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read only
     */
    public HolidayCalendarInfo createHolidayCalendar(@WebParam(name = "holidayCalendarTypeKey") String holidayCalendarTypeKey, @WebParam(name = "holidayCalendarInfo") HolidayCalendarInfo holidayCalendarInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;



    /**
     * Copy an HolidayCalendar.  This copy
     * operation allows for a HolidayCalendar to be created from an
     * existing HolidayCalendar .
     *
     *
     * @param holidayCalendarId the identifier for the Holiday
     *        Calendar to be copied
     * @param startDate the start of the new calendar
     * @param endDate the end of the new calendar
     * @param contextInfo information containing the principalId and
     *        locale information about the caller of service operation
     * @return new HolidayCalendarInfo created from the copy
     * @throws DoesNotExistException academicCalendarId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException academicCalendarId, startYear,
     *         endYear, or contextInfo is missing or null
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public HolidayCalendarInfo copyHolidayCalendar(@WebParam(name = "holidayCalendarId") String holidayCalendarId, @WebParam(name = "startDate") Date startDate, @WebParam(name = "endDate") Date endDate,  @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException ;

    /**
     * Updates an existing Holiday Calendar. The HolidayCalendar Id,
     * Type, and Meta information may not be changed.
     * 
     * @param holidayCalendarId the identifier for the
     *        HolidayCalendar to be updated
     * @param holidayCalendarInfo the new data for the HolidayCalendar
     * @param contextInfo         information containing the principalId and
     *                            locale information about the caller of service
     *                            operation
     * @return the updated HolidayCalendar
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        HolidayCalendarId is not found
     * @throws InvalidParameterException    holidayCalendarInfo or contextInfo
     *                                      is not valid
     * @throws MissingParameterException    holidayCalendarId, holidayCalendarInfo,
     *                                      or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException           an attempt at changing information
     *                                      designated as read only
     * @throws VersionMismatchException     an optimistic locking failure or the
     *                                      action was attempted on an out of
     *                                      date version
     */
    public HolidayCalendarInfo updateHolidayCalendar(@WebParam(name = "holidayCalendarId") String holidayCalendarId, @WebParam(name = "holidayCalendarInfo") HolidayCalendarInfo holidayCalendarInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Changes the state of an existing HolidayCalendar to another state
     * provided that it is valid to do so.
     *
     * @param holidayCalendarId Id of the HolidayCalendar to be updated.
     * @param nextStateKey       The State Key into which the identified
     *                           HolidayCalendar will be placed if the
     *                           operation succeeds.
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     the identified HolidayCalendar does
     *                                   not exist
     * @throws InvalidParameterException the contextInfo object is invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo changeHolidayCalendarState(@WebParam(name = "holidayCalendarId") String holidayCalendarId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Deletes an existing HolidayCalendar.
     *
     * @param holidayCalendarId the identifier for the HolidayCalendar to be
     *                          deleted
     * @param contextInfo       information containing the principalId and
     *                          locale information about the caller of service
     *                          operation
     * @return the status of the operation. This must always be true.
     * @throws DoesNotExistException     HolidayCalendarId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException holidayCalendarId or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteHolidayCalendar(@WebParam(name = "holidayCalendarId") String holidayCalendarId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a Term Type by Type key.
     *
     * @param termTypeKey the key of a Term Type
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return the type requested
     * @throws DoesNotExistException     termTypeKey is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException termTypeKey or contextInfo is missing
     *                                   or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public TypeInfo getTermType(@WebParam(name = "termTypeKey") String termTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets the valid Term types.
     *
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of valid Term Types
     * @throws InvalidParameterException contextInfo is not value
     * @throws MissingParameterException contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<TypeInfo> getTermTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method returns the valid Term types for an AcademicCalendar Type.
     * Only Terms of allowed Types can be mapped to an AcademicCalendar.
     *
     * @param academicCalendarTypeKey an identifier for an AcademicCalendar
     *                                Type
     * @param contextInfo             information containing the principalId and
     *                                locale information about the caller of
     *                                service operation
     * @return a list of valid Term Types for the AcademicCalendar Type
     * @throws DoesNotExistException     academicCalendarTypeKey is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException academicCalendarTypeKey or contextInfo
     *                                   is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<TypeInfo> getTermTypesForAcademicCalendarType(@WebParam(name = "academicCalendarTypeKey") String academicCalendarTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method returns the valid Term types for a Term Type. Only Terms of
     * allowed Types can be included inside another Term.
     *
     * @param termTypeKey a identifier for a Term Type
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of valid Term Types
     * @throws DoesNotExistException     termTypeKey is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException termTypeKey or contextInfo is missing
     *                                   or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<TypeInfo> getTermTypesForTermType(@WebParam(name = "termTypeKey") String termTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets a Term State by key.
     *
     * @param termStateKey a key for a Term State
     * @param contextInfo  information containing the principalId and locale
     *                     information about the caller of service operation
     * @return the Term State requested
     * @throws DoesNotExistException     termStateKey is not found
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException termStateKey or contextInfo is missing
     *                                   or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StateInfo getTermState(@WebParam(name = "termStateKey") String termStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets the valid Term States.
     *
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of valid Term States
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     */
    public List<StateInfo> getTermStates(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a single Term by a Term Id.
     *
     * @param termId      the identifier for the Term to be retrieved
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return the Term requested
     * @throws DoesNotExistException     termIs is not found
     * @throws InvalidParameterException contextInfo isnot valid
     * @throws MissingParameterException termId or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public TermInfo getTerm(@WebParam(name = "termId") String termId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list Terms from a list of Term Ids. The returned list may be
     * in any order and if duplicate Ids are supplied, a unique set may or may
     * not be returned.
     *
     * @param termIds     list of Term Ids
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of Terms
     * @throws DoesNotExistException     a termId in list was not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException termIds, an Id in termIds, or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<TermInfo> getTermsByIds(@WebParam(name = "termIds") List<String> termIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of Term Ids by Type.
     *
     * @param termTypeKey an identifier for a Term Type
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of Terms matching termTypeKey or an empty list if none
     *         found
     * @throws DoesNotExistException     a termTypeKey in list was not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException termTypeKey or contextInfo is missing
     *                                   or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getTermIdsByType(@WebParam(name = "termTypeKey") String termTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets a list of Terms by Code. Typically, a Term Code is unique.
     *
     * @param code        a Term Code
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of Terms with the given Term Code
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException code or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<TermInfo> getTermsByCode(@WebParam(name = "code") String code, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of the top level Terms mapped to the given
     * AcademicCalendar ordered by Term start date.
     *
     * @param academicCalendarId an identifier for an AcademicCalendar
     * @param contextInfo        information containing the principalId and
     *                           locale information about the caller of service
     *                           operation
     * @return a list of Terms mapped to the given AcademicCalendar
     * @throws DoesNotExistException     academicCalendarId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException academicCalendarId or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TermInfo> getTermsForAcademicCalendar(@WebParam(name = "academicCalendarId") String academicCalendarId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list Terms included immediately inside the given Term ordered
     * by Term start date. This method should be called recursively to get
     * sub-terms of the returned Terms.
     *
     * @param termId      an identifier for a Term
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of Terms or an empty list if there are no included Terms
     * @throws DoesNotExistException     termId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException termId or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TermInfo> getIncludedTermsInTerm(@WebParam(name = "termId") String termId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets the containing terms of a given term. A term may be "included"
     * inside other terms using addTermToTerm(). This method returns the list of
     * Terms that the given Term has been placed inside. Typically, a term is
     * placed inside a single parent term.
     *
     * @param termId      an identifier for a Term
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return the parent terms or an empty list if it is a root
     * @throws DoesNotExistException     termId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException termId or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TermInfo> getContainingTerms(@WebParam(name = "termId") String termId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for Terms that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of Term identifiers matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForTermIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for Terms that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of Terms matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<TermInfo> searchForTerms(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates a Term. Depending on the value of validationType, this
     * validation could be limited to tests on just the current Term and its
     * directly contained subobjects or expanded to perform all tests related to
     * this Term. If an identifier is present for the Term (and/or one of its
     * contained sub-objects) and a record is found for that identifier, the
     * validation checks if the Term can be updated to the new values. If an
     * identifier is not present or a record does not exist, the validation
     * checks if the Term with the given data can be created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param termTypeKey       the identifier for the Term Type to be
     *                          validated
     * @param termInfo          the Term to be validated
     * @param contextInfo       information containing the principalId and
     *                          locale information about the caller of service
     *                          operation
     * @return a list of validation results or an empty list if validation
     *         succeeded
     * @throws DoesNotExistException     validationTypeKey or termTypeKey is not
     *                                   found
     * @throws InvalidParameterException termInfo or contextInfo is not valid
     * @throws MissingParameterException validationTypeKey, termTypeKey,
     *                                   termInfo, or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateTerm(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "termTypeKey") String termTypeKey, @WebParam(name = "termInfo") TermInfo termInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new Term. The Term Type and Meta information may not be set in
     * the supplied data object.
     *
     * @param termTypeKey the identifier for the Type of Term to be created
     * @param termInfo    the data with which to create the Term
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return the new Term
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        termTypeKey does not exist or is not
     *                                      supported
     * @throws InvalidParameterException    termInfo or contextInfo is not
     *                                      valid
     * @throws MissingParameterException    termTypeKey, termInfo, or
     *                                      contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read only
     */
    public TermInfo createTerm(@WebParam(name = "termTypeKey") String termTypeKey, @WebParam(name = "termInfo") TermInfo termInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates an existing Term. The Term Id, Type, and Meta information may not
     * be changed.
     *
     * @param termId      the identifier for the Term to be updated
     * @param termInfo    the new data for the Term
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return the updated Term
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        TermId is not found
     * @throws InvalidParameterException    termInfo or contextInfo is not
     *                                      valid
     * @throws MissingParameterException    termId, termInfo, or contextInfo is
     *                                      missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException           an attempt at changing information
     *                                      deisgnated as read only
     * @throws VersionMismatchException     an optimistic locking failure or the
     *                                      action was attempted on an out of
     *                                      date version
     */
    public TermInfo updateTerm(@WebParam(name = "termId") String termId, @WebParam(name = "termInfo") TermInfo termInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Changes the state of an existing Term to another state
     * provided that it is valid to do so.
     *
     * @param termId Id of the Term to be updated.
     * @param nextStateKey       The State Key into which the identified
     *                           Term will be placed if the
     *                           operation succeeds.
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     the identified Term does
     *                                   not exist
     * @throws InvalidParameterException the contextInfo object is invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo changeTermState(@WebParam(name = "termId") String termId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Deletes an existing Term.
     *
     * @param termId      the identifier for the Term to be deleted
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return the status of the operation. This must always be true.
     * @throws DoesNotExistException     TermId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException termId or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteTerm(@WebParam(name = "termId") String termId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Adds a Term to an AcademicCalendar.
     *
     * @param academicCalendarId an identifier for an AcademicCalendar
     * @param termId             the Id of Term to be added
     * @param contextInfo        information containing the principalId and
     *                           locale information about the caller of service
     *                           operation
     * @return the status of the operation. This must always be true.
     * @throws AlreadyExistsException    The Term is already mapped to the
     *                                   AcademicCalendar
     * @throws DoesNotExistException     academicCalendarId or termId is not
     *                                   found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException academicCalendarId, termId, or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo addTermToAcademicCalendar(@WebParam(name = "academicCalendarId") String academicCalendarId, @WebParam(name = "termId") String termId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Removes a Term from an AcademicCalendar.
     *
     * @param academicCalendarId an identifier for an AcademicCalendar
     * @param termId             the Id of Term to be unmapped
     * @param contextInfo        information containing the principalId and
     *                           locale information about the caller of service
     *                           operation
     * @return the status of the operation. This must always be true.
     * @throws DoesNotExistException     termId or academicCalendarId is not
     *                                   found or termId is not mapped to
     *                                   academicCalendarId
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException academicCalendarId, termId, or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo removeTermFromAcademicCalendar(@WebParam(name = "academicCalendarId") String academicCalendarId, @WebParam(name = "termId") String termId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Adds a Term as an included term within another Term.
     *
     * @param termId         an identifier for a Term
     * @param includedTermId the identifier for the Term to be included
     * @param contextInfo    information containing the principalId and locale
     *                       information about the caller of service operation
     * @return the status of the operation. This must always be true.
     * @throws AlreadyExistsException    includedTermId is already mapped to
     *                                   termId
     * @throws DoesNotExistException     temId or includedTermId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException temId, includedTermId, or contextInfo
     *                                   is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo addTermToTerm(@WebParam(name = "termId") String termId, @WebParam(name = "includedTermId") String includedTermId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Removes an included Term from a Term.
     *
     * @param termId         an identifier for a Term
     * @param includedTermId the identifier for the Term to be removed
     * @param contextInfo    information containing the principalId and locale
     *                       information about the caller of service operation
     * @return the status of the operation. This must always be true.
     * @throws DoesNotExistException     termId or includedTermId is not found
     *                                   or includedTermId is not mapped to
     *                                   termId
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException termId, includedTermId, or contextInfo
     *                                   is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo removeTermFromTerm(@WebParam(name = "termId") String termId, @WebParam(name = "includedTermId") String includedTermId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Rerieves a KeyDate Type by Type key.
     *
     * @param keyDateTypeKey the key of a keyDate Type
     * @param contextInfo    information containing the principalId and locale
     *                       information about the caller of service operation
     * @return the type requested
     * @throws DoesNotExistException     keyDateTypeKey is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException keyDateTypeKey or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public TypeInfo getKeyDateType(@WebParam(name = "keyDateTypeKey") String keyDateTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets the valid KeyDate types.
     *
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of valid KeyDate Types
     * @throws InvalidParameterException contextInfo is not value
     * @throws MissingParameterException contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<TypeInfo> getKeyDateTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method returns the valid KeyDate Types for the given Term Type.
     *
     * @param termTypeKey identifier for a Term Type
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of valid KeyDate Types
     * @throws DoesNotExistException     termTypeKey not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException termTypeKey or contextInfo is missing
     *                                   or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<TypeInfo> getKeyDateTypesForTermType(@WebParam(name = "termTypeKey") String termTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets a KeyDate State by key.
     *
     * @param keyDateStateKey a key for a keyDate State
     * @param contextInfo     information containing the principalId and locale
     *                        information about the caller of service operation
     * @return the KeyDate State requested
     * @throws DoesNotExistException     keyDateStateKey is not found
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException keyDateStateKey or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StateInfo getKeyDateState(@WebParam(name = "keyDateStateKey") String keyDateStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets the valid KeyDate States.
     *
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of valid KeyDate States
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<StateInfo> getKeyDateStates(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a single KeyDate by a KeyDate Id.
     *
     * @param keyDateId   the identifier for the KeyDate to be retrieved
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return the KeyDate requested
     * @throws DoesNotExistException     keyDateIs is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException keyDateId or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public KeyDateInfo getKeyDate(@WebParam(name = "keyDateId") String keyDateId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list KeyDates from a list of KeyDate Ids. The returned list
     * may be in any order and if duplicate Ids are supplied, a unique set may
     * or may not be returned.
     *
     * @param keyDateIds  list of KeyDate Ids
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of KeyDates
     * @throws DoesNotExistException     a keyDateId in list was not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException keyDateIds, an Id in keyDateIds, or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<KeyDateInfo> getKeyDatesByIds(@WebParam(name = "keyDateIds") List<String> keyDateIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of KeyDate Ids by Type.
     *
     * @param keyDateTypeKey an identifier for a KeyDate Type
     * @param contextInfo    information containing the principalId and locale
     *                       information about the caller of service operation
     * @return a list of KeyDates matching keyDateTypeKey or an empty list if
     *         none found
     * @throws DoesNotExistException     a keyDateTypeKey is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException keyDateTypeKey or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getKeyDateIdsByType(@WebParam(name = "keyDateTypeKey") String keyDateTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of KeyDate Ids by Type and for a specific Term
     *
     * @param keyDateTypeKey an identifier for a KeyDate Type
     * @param termId      an identifier for a term
     * @param contextInfo    information containing the principalId and locale
     *                       information about the caller of service operation
     * @return a list of KeyDates matching keyDateTypeKey and for termId or an empty list if none found
     * @throws DoesNotExistException     a keyDateTypeKey is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException keyDateTypeKey or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getKeyDateIdsByTypeForTerm(@WebParam(name = "keyDateTypeKey") String keyDateTypeKey, @WebParam(name = "termId") String termId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of KeyDates immediately mapped to a Term ordered by
     * date.
     *
     * @param termId      an identifier for a term
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of KeyDates mapped to the given Term
     * @throws DoesNotExistException     termId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException termId or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<KeyDateInfo> getKeyDatesForTerm(@WebParam(name = "termId") String termId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of KeyDates immediately mapped to a Term that fall
     * within the given date range inclusive ordered by date. The dates include
     * only those dates immediate mapped to the Term.
     *
     * @param termId      an identifier for a Term
     * @param startDate   the start of date range
     * @param endDate     the end of date range
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of KeyDates for the given Term in the given dates
     * @throws DoesNotExistException     termId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException termId, startDate, endDate, or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<KeyDateInfo> getKeyDatesForTermByDate(@WebParam(name = "termId") String termId, @WebParam(name = "startDate") Date startDate, @WebParam(name = "endDate") Date endDate, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets a list of KeyDates impacted by a change to a given KeyDate. Rules
     * may exist to calculate key dates based on a KeyDate Type. Management of
     * these calculation rules are not at this time exposed in this service.
     *
     * @param keyDateId   an identifier for a KeyDate
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of KeyDates impacted by the given KeyDate
     * @throws DoesNotExistException     keyDateId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException keyDateId or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @deprecated Key Dates are to be calculated when calender is copied.
     */
    public List<KeyDateInfo> getImpactedKeyDates(@WebParam(name = "keyDateId") String keyDateId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for KeyDates that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of KeyDate identifiers matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForKeyDateIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for KeyDates that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of KeyDates matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<KeyDateInfo> searchForKeyDates(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates a KeyDate. Depending on the value of validationType, this
     * validation could be limited to tests on just the current KeyDate and its
     * directly contained subobjects or expanded to perform all tests related to
     * this KeyDate. If an identifier is present for the KeyDate (and/or one of
     * its contained sub-objects) and a record is found for that identifier, the
     * validation checks if the KeyDate can be updated to the new values. If an
     * identifier is not present or a record does not exist, the validation
     * checks if the KeyDate with the given data can be created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param termId            the identifier for the Term
     * @param keyDateTypeKey    the identifier for the KeyDate Type to be
     *                          validated
     * @param keyDateInfo       the KeyDate to be validated
     * @param contextInfo       information containing the principalId and
     *                          locale information about the caller of service
     *                          operation
     * @return a list of validation results or an empty list if validation
     *         succeeded
     * @throws DoesNotExistException     validationTypeKey, termId, or
     *                                   keyDateTypeKey is not found
     * @throws InvalidParameterException keyDateInfo or contextInfo is not
     *                                   valid
     * @throws MissingParameterException validationTypeKey, termId, keyDateTypeKey,
     *                                   keyDateInfo, or contextInfo is missing
     *                                   or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateKeyDate(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "termId") String termId, @WebParam(name = "keyDateTypeKey") String keyDateTypeKey, @WebParam(name = "keyDateInfo") KeyDateInfo keyDateInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new KeyDate. The KeyDate Id, Type, and Meta information may not
     * be set in the supplied data object.
     *
     * @param termId         the identifier for the Term in which to create the
     *                       KeyDate
     * @param keyDateTypeKey the identifier for the Type of KeyDate to be
     *                       created
     * @param keyDateInfo    the data with which to create the KeyDate
     * @param contextInfo    information containing the principalId and locale
     *                       information about the caller of service operation
     * @return the new KeyDate
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        termId is not found or keyDateTypeKey
     *                                      does not exist or is not supported
     * @throws InvalidParameterException    keyDateInfo or contextInfo is not
     *                                      valid
     * @throws MissingParameterException    termId, keyDateTypeKey, keyDateInfo,
     *                                      or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read only
     */
    public KeyDateInfo createKeyDate(@WebParam(name = "termId") String termId, @WebParam(name = "keyDateTypeKey") String keyDateTypeKey, @WebParam(name = "keyDateInfo") KeyDateInfo keyDateInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates an existing KeyDate. The KeyDate Id, Type, and Meta information
     * may not be changed.
     *
     * @param keyDateId   the identifier for the KeyDate to be updated
     * @param keyDateInfo the new data for the Key`Date
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return the updated KeyDate
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        KeyDateId is not found
     * @throws InvalidParameterException    keyDateInfo or contextInfo is not
     *                                      valid
     * @throws MissingParameterException    keyDateId, keyDateInfo, or
     *                                      contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException           an attempt at changing information
     *                                      designated as read only
     * @throws VersionMismatchException     an optimistic locking failure or the
     *                                      action was attempted on an out of
     *                                      date version
     */
    public KeyDateInfo updateKeyDate(@WebParam(name = "keyDateId") String keyDateId, @WebParam(name = "keyDateInfo") KeyDateInfo keyDateInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Changes the state of an existing KeyDate to another state
     * provided that it is valid to do so.
     *
     * @param keyDateId Id of the KeyDate to be updated.
     * @param nextStateKey       The State Key into which the identified
     *                           KeyDate will be placed if the
     *                           operation succeeds.
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     the identified KeyDate does
     *                                   not exist
     * @throws InvalidParameterException the contextInfo object is invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo changeKeyDateState(@WebParam(name = "keyDateId") String keyDateId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Deletes an existing KeyDate.
     *
     * @param keyDateId   the identifier for the KeyDate to be deleted
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return the status of the operation. This must always be true.
     * @throws DoesNotExistException     KeyDateId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException keyDateId or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteKeyDate(@WebParam(name = "keyDateId") String keyDateId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Calculates the dates in the KeyDate based on a rule attached to the
     * KeyDate Type. If there is no rule available for the Type of the given
     * KeyDate, then no changes to the KeyDate occur.
     *
     * @param keyDateId   an identifier for a KeyDate
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return the KeyDate with the calculated dates
     * @throws DoesNotExistException     keyDateId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException keyDateId or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @deprecated Key Dates are to be calculated when calender is copied.
     */
    public KeyDateInfo calculateKeyDate(@WebParam(name = "keyDateId") String keyDateId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Rerieves a AcalEvent Type by Type key.
     *
     * @param acalEventTypeKey the key of a acalEvent Type
     * @param contextInfo      information containing the principalId and locale
     *                         information about the caller of service
     *                         operation
     * @return the type requested
     * @throws DoesNotExistException     acalEventTypeKey is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException acalEventTypeKey or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public TypeInfo getAcalEventType(@WebParam(name = "acalEventTypeKey") String acalEventTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets the valid AcalEvent types.
     *
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of valid AcalEvent Types
     * @throws InvalidParameterException contextInfo is not value
     * @throws MissingParameterException contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<TypeInfo> getAcalEventTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method returns the valid AcalEvent Types for the given
     * AcademicCalendar Type.
     *
     * @param academicCalendarTypeKey identifier for an AcademicCalendar Type
     * @param contextInfo             information containing the principalId and
     *                                locale information about the caller of
     *                                service operation
     * @return a list of valid AcalEvent Types
     * @throws DoesNotExistException     academicCalendarTypeKey not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException academicCalendarTypeKey or contextInfo
     *                                   is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<TypeInfo> getAcalEventTypesForAcademicCalendarType(@WebParam(name = "academicCalendarTypeKey") String academicCalendarTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets a AcalEvent State by key.
     *
     * @param acalEventStateKey a key for a acalEvent State
     * @param contextInfo       information containing the principalId and
     *                          locale information about the caller of service
     *                          operation
     * @return the AcalEvent State requested
     * @throws DoesNotExistException     acalEventStateKey is not found
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException acalEventStateKey or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StateInfo getAcalEventState(@WebParam(name = "acalEventStateKey") String acalEventStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets the valid AcalEvent States.
     *
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of valid AcalEvent States
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<StateInfo> getAcalEventStates(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a single AcalEvent by an AcalEvent Id.
     *
     * @param acalEventId the identifier for the AcalEvent to be retrieved
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return the AcalEvent requested
     * @throws DoesNotExistException     acalEventIs is not found
     * @throws InvalidParameterException contextInfo isnot valid
     * @throws MissingParameterException acalEventKey or contextInfo is missing
     *                                   or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public AcalEventInfo getAcalEvent(@WebParam(name = "acalEventId") String acalEventId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list AcalEvents from a list of AcalEvent Ids. The returned
     * list may be in any order and if duplicate Ids are supplied, a unique set
     * may or may not be returned.
     *
     * @param acalEventIds list of AcalEvent Ids
     * @param contextInfo  information containing the principalId and locale
     *                     information about the caller of service operation
     * @return a list of AcalEvents
     * @throws DoesNotExistException     an acalEventId in list was not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException acalEventIds, an Id in acalEventIds, or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<AcalEventInfo> getAcalEventsByIds(@WebParam(name = "acalEventIds") List<String> acalEventIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of AcalEvent Ids by Type.
     *
     * @param acalEventTypeKey an identifier for an AcalEvent Type
     * @param contextInfo      information containing the principalId and locale
     *                         information about the caller of service
     *                         operation
     * @return a list of AcalEvents matching acalEventTypeKey or an empty list
     *         if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException acalEventTypeKey or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getAcalEventIdsByType(@WebParam(name = "acalEventTypeKey") String acalEventTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of AcalEvents immediately mapped to an AcademicCalendar
     * ordered by date.
     *
     * @param academicCalendarId an identifier for an AcademicCalendar
     * @param contextInfo        information containing the principalId and
     *                           locale information about the caller of service
     *                           operation
     * @return a list of AcalEvents mapped to the given AcademicCalendar
     * @throws DoesNotExistException     academicCalendarId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException academicCalendarId or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<AcalEventInfo> getAcalEventsForAcademicCalendar(@WebParam(name = "academicCalendarId") String academicCalendarId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of AcalEvents immediately mapped to an AcademicCalendar
     * that fall within the given date range inclusive ordered by date.
     *
     * @param academicCalendarId an identifier for an AcademicCalendar
     * @param startDate          the start of date range
     * @param endDate            the end of date range
     * @param contextInfo        information containing the principalId and
     *                           locale information about the caller of service
     *                           operation
     * @return a list of AcalEvents for the given AcademicCalendar in the given
     *         dates
     * @throws DoesNotExistException     academicCalendarId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException academicCalendarId, startDate, endDate,
     *                                   or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<AcalEventInfo> getAcalEventsForAcademicCalendarByDate(@WebParam(name = "academicCalendarId") String academicCalendarId, @WebParam(name = "startDate") Date startDate, @WebParam(name = "endDate") Date endDate, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets a list of AcalEvents impacted by a change to a given AcalEvent.
     * Rules may exist to calculate key dates based on a AcalEvent Type.
     * Management of these calculation rules are not at this time exposed in
     * this service.
     *
     * @param acalEventId an identifier for an AcalEvent
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of AcalEvents impacted by the given AcalEvent
     * @throws DoesNotExistException     keyDateId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException acalEventId or contextInfo is missing
     *                                   or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<AcalEventInfo> getImpactedAcalEvents(@WebParam(name = "acalEventId") String acalEventId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for AcalEvents that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of AcalEvent identifiers matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForAcalEventIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for AcalEvents that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of AcalEvents matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<AcalEventInfo> searchForAcalEvents(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates an AcalEvent. Depending on the value of validationType, this
     * validation could be limited to tests on just the current AcalEvent and
     * its directly contained subobjects or expanded to perform all tests
     * related to this AcalEvent. If an identifier is present for the AcalEvent
     * (and/or one of its contained sub-objects) and a record is found for that
     * identifier, the validation checks if the AcalEvent can be updated to the
     * new values. If an identifier is not present or a record does not exist,
     * the validation checks if the AcalEvent with the given data can be
     * created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param termId            the identifier for the Term
     * @param acalEventTypeKey  the identifier for the AcalEvent Type to be
     *                          validated
     * @param acalEventInfo     the AcalEvent to be validated
     * @param contextInfo       information containing the principalId and
     *                          locale information about the caller of service
     *                          operation
     * @return a list of validation results or an empty list if validation
     *         succeeded
     * @throws DoesNotExistException     validationTypeKey, termId, or
     *                                   acalEventTypeKey is not found
     * @throws InvalidParameterException acalEventInfo or contextInfo is not
     *                                   valid
     * @throws MissingParameterException validationTypeKey, termId, acalEventTypeKey,
     *                                   acalEventInfo, or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateAcalEvent(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "termId") String termId, @WebParam(name = "acalEventTypeKey") String acalEventTypeKey, @WebParam(name = "acalEventInfo") AcalEventInfo acalEventInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new AcalEvent. The AcalEvent Id, Type, and Meta information may
     * not be set in the supplied data object.
     *
     * @param academicCalendarId the identifier for the AcademicCalendar in
     *                           which to create the AcalEvent
     * @param acalEventTypeKey   the identifier for the Type of AcalEvent to be
     *                           created
     * @param acalEventInfo      the data with which to create the AcalEvent
     * @param contextInfo        information containing the principalId and
     *                           locale information about the caller of service
     *                           operation
     * @return the new AcalEvent
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        academicCalendarId is not found or
     *                                      acalEventTypeKey does not exist or
     *                                      is not supported
     * @throws InvalidParameterException    acalEventInfo or contextInfo is not
     *                                      valid
     * @throws MissingParameterException    academicCalendarId, acalEventTypeKey,
     *                                      acalEventInfo, or contextInfo is
     *                                      missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read only
     */
    public AcalEventInfo createAcalEvent(@WebParam(name = "academicCalendarId") String academicCalendarId, @WebParam(name = "acalEventTypeKey") String acalEventTypeKey, @WebParam(name = "acalEventInfo") AcalEventInfo acalEventInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates an existing AcalEvent. The AcalEvent Id, Type, and Meta
     * information may not be changed.
     *
     * @param acalEventId   the identifier for the AcalEvent to be updated
     * @param acalEventInfo the new data for the AcalEvent
     * @param contextInfo   information containing the principalId and locale
     *                      information about the caller of service operation
     * @return the updated AcalEvent
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        AcalEventId is not found
     * @throws InvalidParameterException    acalEventInfo or contextInfo is not
     *                                      valid
     * @throws MissingParameterException    acalEventId, acalEventInfo, or
     *                                      contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException           an attempt at changing information
     *                                      deisgnated as read only
     * @throws VersionMismatchException     an optimistic locking failure or the
     *                                      action was attempted on an out of
     *                                      date version
     */
    public AcalEventInfo updateAcalEvent(@WebParam(name = "acalEventId") String acalEventId, @WebParam(name = "acalEventInfo") AcalEventInfo acalEventInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Changes the state of an existing AcalEvent to another state
     * provided that it is valid to do so.
     *
     * @param acalEventId Id of the AcalEvent to be updated.
     * @param nextStateKey       The State Key into which the identified
     *                           AcalEvent will be placed if the
     *                           operation succeeds.
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     the identified AcalEvent does
     *                                   not exist
     * @throws InvalidParameterException the contextInfo object is invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo changeAcalEventState(@WebParam(name = "acalEventId") String acalEventId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Deletes an existing AcalEvent.
     *
     * @param acalEventId the identifier for the AcalEvent to be deleted
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return the status of the operation. This must always be true.
     * @throws DoesNotExistException     AcalEventId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException acalEventId or contextInfo is missing
     *                                   or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteAcalEvent(@WebParam(name = "acalEventId") String acalEventId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Calculates the dates in the AcalEvent based on a rule attached to the
     * AcalEvent Type. If there is no rule available for the Type of the given
     * AcalEvent, then no changes to the AcalEvent occur.
     *
     * @param acalEventId an identifier for a AcalEvent
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return the AcalEvent with the calculated dates
     * @throws DoesNotExistException     acalEventId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException acalEventId or contextInfo is missing
     *                                   or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public AcalEventInfo calculateAcalEvent(@WebParam(name = "acalEventId") String acalEventId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Rerieves a Holiday Type by Type key.
     *
     * @param holidayTypeKey the key of a holiday Type
     * @param contextInfo    information containing the principalId and locale
     *                       information about the caller of service operation
     * @return the type requested
     * @throws DoesNotExistException     holidayTypeKey is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException holidayTypeKey or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public TypeInfo getHolidayType(@WebParam(name = "holidayTypeKey") String holidayTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets the valid Holiday types.
     *
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of valid Holiday Types
     * @throws InvalidParameterException contextInfo is not value
     * @throws MissingParameterException contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<TypeInfo> getHolidayTypes(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * This method returns the valid Holiday Types for the given HolidayCalendar
     * Type.
     *
     * @param holidayCalendarTypeKey identifier for a HolidayCalendar Type
     * @param contextInfo            information containing the principalId and
     *                               locale information about the caller of
     *                               service operation
     * @return a list of valid Holiday Types
     * @throws DoesNotExistException     holidayCalendarTypeKey not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException holidayCalendarTypeKey or contextInfo
     *                                   is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<TypeInfo> getHolidayTypesForHolidayCalendarType(@WebParam(name = "holidayCalendarTypeKey") String holidayCalendarTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets a Holiday State by key.
     *
     * @param holidayStateKey a key for a holiday State
     * @param contextInfo     information containing the principalId and locale
     *                        information about the caller of service operation
     * @return the Holiday State requested
     * @throws DoesNotExistException     holidayStateKey is not found
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException holidayStateKey or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StateInfo getHolidayState(@WebParam(name = "holidayStateKey") String holidayStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets the valid Holiday States.
     *
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of valid Holiday States
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<StateInfo> getHolidayStates(@WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a single Holiday by a Holiday Id.
     *
     * @param holidayId   the identifier for the Holiday to be retrieved
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return the Holiday requested
     * @throws DoesNotExistException     holidayIs is not found
     * @throws InvalidParameterException contextInfo isnot valid
     * @throws MissingParameterException holidayId or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public HolidayInfo getHoliday(@WebParam(name = "holidayId") String holidayId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list Holidays from a list of Holiday Ids. The returned list
     * may be in any order and if duplicate Ids are supplied, a unique set may
     * or may not be returned.
     *
     * @param holidayIds  list of Holiday Ids
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of Holidays
     * @throws DoesNotExistException     a holidayId in list was not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException holidayIds, an Id in holidayIds, or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<HolidayInfo> getHolidaysByIds(@WebParam(name = "holidayIds") List<String> holidayIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of Holiday Ids by Type.
     *
     * @param holidayTypeKey an identifier for an Holiday Type
     * @param contextInfo    information containing the principalId and locale
     *                       information about the caller of service operation
     * @return a list of Holidays matching holidayTypeKey or an empty list if
     *         none found
     * @throws DoesNotExistException     a holidayTypeKey was not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException holidayTypeKey or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getHolidayIdsByType(@WebParam(name = "holidayTypeKey") String holidayTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of Holidays immediately mapped to a HolidayCalendar
     * ordered by date.
     *
     * @param holidayCalendarId an identifier for a HolidayCalendar
     * @param contextInfo       information containing the principalId and
     *                          locale information about the caller of service
     *                          operation
     * @return a list of Holidays mapped to the given HolidayCalendar
     * @throws DoesNotExistException     holidayCalendarId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException holidayCalendarId or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<HolidayInfo> getHolidaysForHolidayCalendar(@WebParam(name = "holidayCalendarId") String holidayCalendarId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of Holidays immediately mapped to a HolidayCalendar that
     * fall within the given date range inclusive ordered by date.
     *
     * @param holidayCalendarId an identifier for a HolidayCalendar
     * @param startDate         the start of date range
     * @param endDate           the end of date range
     * @param contextInfo       information containing the principalId and
     *                          locale information about the caller of service
     *                          operation
     * @return a list of Holidays for the given HolidayCalendar in the given
     *         dates
     * @throws DoesNotExistException     holidayCalendarId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException holidayCalendarId, startDate, endDate,
     *                                   or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<HolidayInfo> getHolidaysForHolidayCalendarByDate(@WebParam(name = "holidayCalendarId") String holidayCalendarId, @WebParam(name = "startDate") Date startDate, @WebParam(name = "endDate") Date endDate, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets a list of holidays for a particular Academic Calendar further
     * qualified by the dates. Can be used to get holidays for a term or any
     * time period within the specified dates
     *
     * @param academicCalendarId an identifier for an Academic Calendar
     * @param startDate                  the start of date range
     * @param endDate                    the end of date range
     * @param contextInfo                information containing the principalId and
     *                                   locale information about the caller of service
     *                                   operation
     * @return a list of holidays for a particular Academic Calendar in the given dates
     * @throws DoesNotExistException     academicCalendarId is not found
     * @throws InvalidParameterException If the academicCalendarId is invalid or
     *                                   the dates are out of the range
     * @throws MissingParameterException Missing dates or academic Calendar Id
     * @throws OperationFailedException  unable to complete request for any
     *                                   reason
     * @throws PermissionDeniedException authorization failure
     */
    public List<HolidayInfo> getHolidaysByDateForAcademicCalendar(@WebParam(name = "academicCalendarId") String academicCalendarId, @WebParam(name = "startDate") Date startDate, @WebParam(name = "endDate") Date endDate, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Gets a list of Holidays impacted by a change to a given Holiday. Rules
     * may exist to calculate key dates based on a Holiday Type. Management of
     * these calculation rules are not at this time exposed in this service.
     *
     * @param holidayId   an identifier for a Holiday
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of Holidays impacted by the given Holiday
     * @throws DoesNotExistException     keyDateId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException holidayId or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @deprecated Holidays are to be calculated when calender is copied.
     */
    public List<HolidayInfo> getImpactedHolidays(@WebParam(name = "holidayId") String holidayId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for Holidays that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of Holiday identifiers matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForHolidayIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for Holidays that meet the given search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of Holidays matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is not valid
     * @throws MissingParameterException criteria or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<HolidayInfo> searchForHolidays(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates a Holiday. Depending on the value of validationType, this
     * validation could be limited to tests on just the current Holiday and its
     * directly contained subobjects or expanded to perform all tests related to
     * this Holiday. If an identifier is present for the Holiday (and/or one of
     * its contained sub-objects) and a record is found for that identifier, the
     * validation checks if the Holiday can be updated to the new values. If an
     * identifier is not present or a record does not exist, the validation
     * checks if the Holiday with the given data can be created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param holidayCalendarId the identifier for the HolidayCalendar
     * @param holidayTypeKey    the identifier for the Holiday Type to be
     *                          validated
     * @param holidayInfo       the Holiday to be validated
     * @param contextInfo       information containing the principalId and
     *                          locale information about the caller of service
     *                          operation
     * @return a list of validation results or an empty list if validation
     *         succeeded
     * @throws DoesNotExistException     validationTypeKey, holidayCalendarId,
     *                                   or holidayTypeKey is not found
     * @throws InvalidParameterException holidayInfo or contextInfo is not
     *                                   valid
     * @throws MissingParameterException validationTypeKey, holidayCalendarId,
     *                                   holidayTypeKey, holidayInfo, or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateHoliday(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "holidayCalendarId") String holidayCalendarId, @WebParam(name = "holidayTypeKey") String holidayTypeKey, @WebParam(name = "holidayInfo") HolidayInfo holidayInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates a new Holiday. The Holiday Id, Type, and Meta information may not
     * be set in the supplied data object.
     *
     * @param holidayCalendarId the identifier for the HolidayCalendar in which
     *                          to create the Holiday
     * @param holidayTypeKey    the identifier for the Type of Holiday to be
     *                          created
     * @param holidayInfo       the data with which to create the Holiday
     * @param contextInfo       information containing the principalId and
     *                          locale information about the caller of service
     *                          operation
     * @return the new Holiday
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        holidayCalendarId is not found or
     *                                      holidayTypeKey does not exist or is
     *                                      not supported
     * @throws InvalidParameterException    holidayInfo or contextInfo is not
     *                                      valid
     * @throws MissingParameterException    holidayCalendarId, holidayTypeKey,
     *                                      holidayInfo, or contextInfo is
     *                                      missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read only
     */
    public HolidayInfo createHoliday(@WebParam(name = "holidayCalendarId") String holidayCalendarId, @WebParam(name = "holidayTypeKey") String holidayTypeKey, @WebParam(name = "holidayInfo") HolidayInfo holidayInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates an existing Holiday. The Holiday Id, Type, and Meta information
     * may not be changed.
     *
     * @param holidayId   the identifier for the Holiday to be updated
     * @param holidayInfo the new data for the Holiday
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return the updated Holiday
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        HolidayId is not found
     * @throws InvalidParameterException    holidayInfo or contextInfo is not
     *                                      valid
     * @throws MissingParameterException    holidayId, holidayInfo, or
     *                                      contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException           an attempt at changing information
     *                                      deisgnated as read only
     * @throws VersionMismatchException     an optimistic locking failure or the
     *                                      action was attempted on an out of
     *                                      date version
     */
    public HolidayInfo updateHoliday(@WebParam(name = "holidayId") String holidayId, @WebParam(name = "holidayInfo") HolidayInfo holidayInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;


    /**
     * Changes the state of an existing Holiday to another state
     * provided that it is valid to do so.
     *
     * @param holidayId Id of the Holiday to be updated.
     * @param nextStateKey       The State Key into which the identified
     *                           Holiday will be placed if the
     *                           operation succeeds.
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     the identified Holiday does
     *                                   not exist
     * @throws InvalidParameterException the contextInfo object is invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo changeHolidayState(@WebParam(name = "holidayId") String holidayId, @WebParam(name = "nextStateKey") String nextStateKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Deletes an existing Holiday.
     *
     * @param holidayId   the identifier for the Holiday to be deleted
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return the status of the operation. This must always be true.
     * @throws DoesNotExistException     HolidayId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException holidayId or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteHoliday(@WebParam(name = "holidayId") String holidayId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Calculates the dates in the Holiday based on a rule attached to the
     * Holiday Type. If there is no rule available for the Type of the given
     * Holiday, then no changes to the Holiday occur.
     *
     * @param holidayId   an identifier for a Holiday
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return the Holiday with the calculated dates
     * @throws DoesNotExistException     holidayId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException holidayId or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @deprecated Holidays are to be calculated when calender is copied.
     */
    public HolidayInfo calculateHoliday(@WebParam(name = "holidayId") String holidayId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Calculates the number of instructional days for a Term. The number of
     * instructional days is the number of class days in a Term minus the
     * non-instructional holidays on the related holiday calendar.
     *
     * @param termId      an identifier for a Term
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return the number of instructional days in the given Term
     * @throws DoesNotExistException     termId is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException termId or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public Integer getInstructionalDaysForTerm(@WebParam(name = "termId") String termId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Convenience method added for core-slice development to get a current
     * term. The method will be redesigned later to be more generic so that it
     * finds the unique term based on process key and academic calendar -
     * "current" is a misnomer.
     *
     * @param usageKey    we don't know what this means
     * @param contextInfo information containing the principalId and locale
     *                    information about the caller of service operation
     * @return a list of "current" Terms
     * @throws DoesNotExistException     usageKey is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException usageKey or contextInfo is missing or
     *                                   null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @deprecated This was a hack for core-slice but it is being used by
     *             MyPlan.
     */
    public List<TermInfo> getCurrentTerms(@WebParam(name = "usageKey") String usageKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
}