/*
 * Copyright 2012 The Kuali Foundation Licensed under the
 *  Educational Community License, Version 2.0 (the "License"); you may
 *  not use this file except in compliance with the License. You may
 *  obtain a copy of the License at
 *
 *   http://www.osedu.org/licenses/ECL-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an "AS IS"
 *  BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 *  or implied. See the License for the specific language governing
 *  permissions and limitations under the License.
 */

package org.kuali.student.r2.core.appointment.service;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.appointment.constants.AppointmentServiceConstants;
import org.kuali.student.r2.core.appointment.dto.AppointmentInfo;
import org.kuali.student.r2.core.appointment.dto.AppointmentSlotInfo;
import org.kuali.student.r2.core.appointment.dto.AppointmentWindowInfo;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * @Version 1.0
 * @Author Sri komandur@uw.edu
 */
@WebService(name = "AppointmentService", serviceName = "AppointmentService", portName = "AppointmentService", targetNamespace = AppointmentServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface AppointmentService {

    /**
     * Retrieves an Appointment
     *
     * @param appointmentId a unique Id of an Appointment
     * @param contextInfo   Context information containing the principalId and
     *                      locale information about the caller of service
     *                      operation
     * @return an Appointment
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException     appointmentId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException appointmentId or contextInfo is missing
     *                                   or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException  unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException an authorization failure occurred
     */
    public AppointmentInfo getAppointment(@WebParam(name = "appointmentId") String appointmentId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of Appointments corresponding to the given list of
     * Appointment Ids.
     *
     * @param appointmentIds list of Appointments to be retrieved
     * @param contextInfo    Context information containing the principalId and
     *                       locale information about the caller of service
     *                       operation
     * @return a list of Appointments
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException     a appointmentId in list not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException appointmentId or contextInfo is
     *                                   missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException  unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException an authorization failure occurred
     */
    public List<AppointmentInfo> getAppointmentsByIds(@WebParam(name = "appointmentIds") List<String> appointmentIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of Appointment Ids by Appointment Type.
     *
     * @param appointmentTypeKey an identifier for an Appointment Type
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return a list of Appointment identifiers matching appointmentTypeKey or
     *         an empty list if none found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException appointmentTypeKey or contextInfo is
     *                                   missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException  unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException an authorization failure occurred
     */
    public List<String> getAppointmentIdsByType(@WebParam(name = "appointmentTypeKey") String appointmentTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves all Appointments to the given AppointmentSlot
     *
     * @param appointmentSlotId the identifier for the AppointmentSlot
     * @param contextInfo       Context information containing the principalId
     *                          and locale information about the caller of
     *                          service operation
     * @return a list of Appointments to the given AppointmentSlot or an empty
     *         list if none found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException appointmentSlotId or contextInfo is
     *                                   missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException  unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException an authorization failure occurred
     */
    public List<AppointmentInfo> getAppointmentsBySlot(@WebParam(name = "appointmentSlotId") String appointmentSlotId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves all Appointments to the given Person
     *
     * @param personId    the identifier for the Person
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return a list of Appointments to the given Person or an empty list if
     *         none found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException personId or contextInfo is missing or
     *                                   null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException  unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException an authorization failure occurred
     */
    public List<String> getAppointmentIdsByPerson(@WebParam(name = "personId") String personId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves all Appointments to the given Person and Slot
     *
     * @param personId          the identifier for the Person
     * @param appointmentSlotId the identifier for the AppointmentSlot
     * @param contextInfo       Context information containing the principalId
     *                          and locale information about the caller of
     *                          service operation
     * @return a list of Appointments to the given Person and Slot or an empty
     *         list if none found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException personId, appointmentSlotId or contextInfo is missing or
     *                                   null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException  unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException an authorization failure occurred
     */
    public List<AppointmentInfo> getAppointmentsByPersonAndSlot(@WebParam(name = "personId") String personId, @WebParam(name = "appointmentSlotId") String appointmentSlotId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for Appointments based on the criteria and returns a list of
     * Appointment identifiers which match the search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of Appointment Ids
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException criteria, contextInfo is missing or
     *                                   null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException  unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForAppointmentIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for Appointments based on the criteria and returns a list of
     * Appointments which match the search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of Appointment information
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException criteria, contextInfo is missing or
     *                                   null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException  unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException an authorization failure occurred
     */
    public List<AppointmentInfo> searchForAppointments(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates an Appointment. Depending on the value of validationType, this
     * validation could be limited to tests on just the current Appointment and
     * its directly contained sub-objects or expanded to perform all tests
     * related to this Appointment. If an identifier is present for the
     * Appointment (and/or one of its contained sub-objects) and a record is
     * found for that identifier, the validation checks if the Appointment can
     * be updated to the new values. If an identifier is not present or a record
     * does not exist, the validation checks if the object with the given data
     * can be created.
     *
     * @param validationTypeKey  the identifier for the validation Type
     * @param personId           identifier of the person
     * @param appointmentSlotId  appointment slot of the person
     * @param appointmentTypeKey appointment type
     * @param appointmentInfo    detailed information about the appointment
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return a list of validation results or an empty list if validation
     *         succeeded
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException     validationTypeKey, personId,
     *                                   appointmentSlotId or appointmentTypeKey
     *                                   not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException invalid appointmentInfo or contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException validationTypeKey, personId,
     *                                   appointmentSlotId, appointmentTypeKey
     *                                   or contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException  unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateAppointment(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "personId") String personId, @WebParam(name = "appointmentSlotId") String appointmentSlotId, @WebParam(name = "appointmentTypeKey") String appointmentTypeKey, @WebParam(name = "appointmentInfo") AppointmentInfo appointmentInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Creates an Appointment. Most of the time the personId is from the
     * Population in the AppointmentWindow.
     *
     * @param personId           identifier of the person
     * @param appointmentSlotId  appointment slot of the person
     * @param appointmentTypeKey appointment type
     * @param appointmentInfo    detailed information about the appointment
     * @param contextInfo        Context information containing the principalId
     *                           and locale information about the caller of
     *                           service operation
     * @return detailed information about the appointment
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException supplied data is invalid
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException        appointmentSlotId or appointmentTypeKey
     *                                      does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException    invalid appointmentInfo or
     *                                      contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException    personId, appointmentSlotId,
     *                                      appointmentTypeKey or contextInfo is
     *                                      missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException     unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException    an authorization failure occurred
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException            an attempt at supplying information
     *                                      designated as read only
     */
    public AppointmentInfo createAppointment(@WebParam(name = "personId") String personId, @WebParam(name = "appointmentSlotId") String appointmentSlotId, @WebParam(name = "appointmentTypeKey") String appointmentTypeKey, @WebParam(name = "appointmentInfo") AppointmentInfo appointmentInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Generate Appointments for an AppointmentWindow.
     *
     * @param appointmentWindowId appointment window to which these appointments belong
     * @param appointmentTypeKey  appointment type
     * @param contextInfo         Context information containing the principalId
     *                            and locale information about the caller of
     *                            service operation
     * @return status of the operation (success, failed)
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException supplied data is invalid
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException        appointmentWindowId or appointmentTypeKey
     *                                      does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException    invalid appointmentInfo or
     *                                      contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException    personId, appointmentWindowId,
     *                                      appointmentTypeKey or contextInfo is
     *                                      missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException     unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException    an authorization failure occurred
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException            an attempt at supplying information
     *                                      designated as read only
     * @impl AppointmentSlots are assumed to be already generated for the
     * AppointmentWindow
     * @impl Return the number of appointments created as part of StatusInfo
     * message field
     */
    public StatusInfo generateAppointmentsByWindow(@WebParam(name = "appointmentWindowId") String appointmentWindowId, @WebParam(name = "appointmentTypeKey") String appointmentTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates a relationship between a person and their appointment.
     *
     * @param appointmentId   identifier of the appointment relationship to be
     *                        updated
     * @param appointmentInfo information about the object appointmentInfo to be
     *                        updated
     * @param contextInfo     context information containing the principalId and
     *                        locale information about the caller of service
     *                        operation
     * @return updated appointment relationship information
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException one or more values invalid for this
     *                                      operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException        appointmentId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException    invalid appointmentInfo or
     *                                      contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException    appointmentId, appointmentInfo or
     *                                      contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException     unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException    an authorization failure occurred
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException            an attempt at supplying information
     *                                      designated as read-only
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException     optimistic locking failure or the
     *                                      action was attempted on an out of
     *                                      date version
     */
    public AppointmentInfo updateAppointment(@WebParam(name = "appointmentId") String appointmentId, @WebParam(name = "appointmentInfo") AppointmentInfo appointmentInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Removes appointment relationship between a person and a slot.
     *
     * @param appointmentId object Appointment relationship identifier
     * @param contextInfo   context information containing the principalId and
     *                      locale information about the caller of service
     *                      operation
     * @return status of the operation (success, failed)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException     appointmentId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException appointmentId or contextInfo is missing
     *                                   or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException  unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteAppointment(@WebParam(name = "appointmentId") String appointmentId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Remove relationships referencing an AppointmentSlot
     *
     * @param appointmentSlotId object Appointment relationship identifier
     * @param contextInfo       context information containing the principalId
     *                          and locale information about the caller of
     *                          service operation
     * @return status of the operation (success, failed)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException     appointmentSlotId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException appointmentSlotId or contextInfo is
     *                                   missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException  unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException an authorization failure occurred
     * @impl capture number of appointments deleted in the message field of
     * StatusInfo
     */
    public StatusInfo deleteAppointmentsBySlot(@WebParam(name = "appointmentSlotId") String appointmentSlotId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Remove appointment relationships belonging to an AppointmentWindow
     *
     * @param appointmentWindowId object Appointment relationship identifier
     * @param contextInfo         context information containing the principalId
     *                            and locale information about the caller of
     *                            service operation
     * @return status of the operation (success, failed)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException     appointmentWindowId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException appointmentWindowId or contextInfo is
     *                                   missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException  unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException an authorization failure occurred
     * @impl capture number of appointments deleted in the message field of
     * StatusInfo
     */
    public StatusInfo deleteAppointmentsByWindow(@WebParam(name = "appointmentWindowId") String appointmentWindowId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves an AppointmentWindow
     *
     * @param appointmentWindowId a unique Id of an AppointmentWindow
     * @param contextInfo         Context information containing the principalId
     *                            and locale information about the caller of
     *                            service operation
     * @return an AppointmentWindow
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException     appointmentWindowId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException appointmentWindowId or contextInfo is
     *                                   missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException  unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException an authorization failure occurred
     */
    public AppointmentWindowInfo getAppointmentWindow(@WebParam(name = "appointmentWindowId") String appointmentWindowId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of AppointmentWindows corresponding to the given list of
     * AppointmentWindow Ids.
     *
     * @param appointmentWindowIds list of AppointmentWindows to be retrieved
     * @param contextInfo          Context information containing the
     *                             principalId and locale information about the
     *                             caller of service operation
     * @return a list of AppointmentWindows
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException     a appointmentWindowId in list not
     *                                   found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException appointmentWindowId or contextInfo is
     *                                   missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException  unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException an authorization failure occurred
     */
    public List<AppointmentWindowInfo> getAppointmentWindowsByIds(@WebParam(name = "appointmentWindowIds") List<String> appointmentWindowIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of AppointmentWindow Ids by AppointmentWindow Type.
     *
     * @param appointmentWindowTypeKey an identifier for an AppointmentWindow
     *                                 Type
     * @param contextInfo              Context information containing the
     *                                 principalId and locale information about
     *                                 the caller of service operation
     * @return a list of AppointmentWindow identifiers matching
     *         appointmentWindowTypeKey or an empty list if none found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException appointmentWindowTypeKey or contextInfo
     *                                   is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException  unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException an authorization failure occurred
     */
    public List<String> getAppointmentWindowIdsByType(@WebParam(name = "appointmentWindowTypeKey") String appointmentWindowTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves all AppointmentWindows associated with a Population
     *
     * @param populationId the identifier for the AppointmentSlot
     * @param contextInfo  Context information containing the principalId and
     *                     locale information about the caller of service
     *                     operation
     * @return AppointmentWindows associated with the given Population or an
     *         empty list if none found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException populationId or contextInfo is missing
     *                                   or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException  unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException an authorization failure occurred
     */
    public List<String> getAppointmentWindowIdsByPopulation(@WebParam(name = "populationId") String populationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /**
     * Retrieves all AppointmentWindows associated with a Period milestone
     *
     * @param periodMilestoneId the identifier for the AppointmentSlot
     * @param contextInfo       Context information containing the principalId
     *                          and locale information about the caller of
     *                          service operation
     * @return AppointmentWindows associated with the given Population or an
     *         empty list if none found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException periodMilestoneId or contextInfo is
     *                                   missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException  unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException an authorization failure occurred
     */
    public List<AppointmentWindowInfo> getAppointmentWindowsByPeriod(@WebParam(name = "periodMilestoneId") String periodMilestoneId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for AppointmentWindows based on the criteria and returns a list
     * of AppointmentWindow identifiers which match the search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of AppointmentWindow Ids
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException criteria, contextInfo is missing or
     *                                   null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException  unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForAppointmentWindowIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for AppointmentWindows based on the criteria and returns a list
     * of AppointmentWindows which match the search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of Appointment information
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException criteria, contextInfo is missing or
     *                                   null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException  unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException an authorization failure occurred
     */
    public List<AppointmentWindowInfo> searchForAppointmentWindows(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validates an AppointmentWindow. Depending on the value of validationType,
     * this validation could be limited to tests on just the current
     * AppointmentWindow and its directly contained sub-objects or expanded to
     * perform all tests related to this AppointmentWindow. If an identifier is
     * present for the AppointmentWindow (and/or one of its contained
     * sub-objects) and a record is found for that identifier, the validation
     * checks if the AppointmentWindow can be updated to the new values. If an
     * identifier is not present or a record does not exist, the validation
     * checks if the object with the given data can be created.
     *
     * @param validationTypeKey        the identifier for the validation Type
     * @param appointmentWindowTypeKey appointment type
     * @param appointmentWindowInfo    detailed information about the
     *                                 appointment
     * @param contextInfo              Context information containing the
     *                                 principalId and locale information about
     *                                 the caller of service operation
     * @return a list of validation results or an empty list if validation
     *         succeeded
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException     assignedOrderTypeKey or appointmentWindowTypeKey
     *                                   does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException invalid appointmentWindowInfo or
     *                                   contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException periodMilestoneId, populationId,
     *                                   assignedOrderTypeKey, appointmentWindowTypeKey
     *                                   or contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException  unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateAppointmentWindow(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "appointmentWindowTypeKey") String appointmentWindowTypeKey, @WebParam(name = "appointmentWindowInfo") AppointmentWindowInfo appointmentWindowInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /**
     * Creates an AppointmentWindow
     *
     * @param appointmentWindowTypeKey appointment type
     * @param appointmentWindowInfo    detailed information about the
     *                                 appointment
     * @param contextInfo              Context information containing the
     *                                 principalId and locale information about
     *                                 the caller of service operation
     * @return detailed information about the appointment
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException supplied data is invalid
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException        assignedOrderTypeKey or appointmentWindowTypeKey
     *                                      does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException    invalid appointmentWindowInfo or
     *                                      contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException    periodMilestoneId, populationId,
     *                                      assignedOrderTypeKey, appointmentWindowTypeKey
     *                                      or contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException     unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException    an authorization failure occurred
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException            an attempt at supplying information
     *                                      designated as read only
     */
    public AppointmentWindowInfo createAppointmentWindow(@WebParam(name = "appointmentWindowTypeKey") String appointmentWindowTypeKey, @WebParam(name = "appointmentWindowInfo") AppointmentWindowInfo appointmentWindowInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates an AppointmentWindow
     *
     * @param appointmentWindowId   identifier of the appointment relationship
     *                              to be updated
     * @param appointmentWindowInfo information about the object appointmentWindow
     *                              to be updated
     * @param contextInfo           context information containing the
     *                              principalId and locale information about the
     *                              caller of service operation
     * @return updated appointment relationship information
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException one or more values invalid for this
     *                                      operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException        appointmentWindowId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException    invalid appointmentWindowInfo or
     *                                      contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException    appointmentWindowId, appointmentWindowInfo
     *                                      or contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException     unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException    an authorization failure occurred
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException            an attempt at supplying information
     *                                      designated as read-only
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException     optimistic locking failure or the
     *                                      action was attempted on an out of
     *                                      date version
     */
    public AppointmentWindowInfo updateAppointmentWindow(@WebParam(name = "appointmentWindowId") String appointmentWindowId, @WebParam(name = "appointmentWindowInfo") AppointmentWindowInfo appointmentWindowInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Deletes an AppointmentWindow
     *
     * @param appointmentWindowId AppointmentWindow identifier
     * @param contextInfo         context information containing the principalId
     *                            and locale information about the caller of
     *                            service operation
     * @return status of the operation (success, failed)
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException     appointmentWindowId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException appointmentWindowId or contextInfo is
     *                                   missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException  unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteAppointmentWindow(@WebParam(name = "appointmentWindowId") String appointmentWindowId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves an AppointmentSlot
     *
     * @param appointmentSlotId a unique Id of an AppointmentSlot
     * @param contextInfo         Context information containing the principalId
     *                            and locale information about the caller of
     *                            service operation
     * @return an AppointmentSlot
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException     appointmentSlotId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException appointmentSlotId or contextInfo is
     *                                   missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException  unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException an authorization failure occurred
     */
    public AppointmentSlotInfo getAppointmentSlot(@WebParam(name = "appointmentSlotId") String appointmentSlotId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves a list of AppointmentSlots corresponding to the given list of
     * AppointmentSlot Ids.
     *
     * @param appointmentSlotIds list of Appointments to be retrieved
     * @param contextInfo    Context information containing the principalId and
     *                       locale information about the caller of service
     *                       operation
     * @return a list of Appointments
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException     an appointmentSlotId in list not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException appointmentSlotId or contextInfo is
     *                                   missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException  unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException an authorization failure occurred
     */
    public List<AppointmentSlotInfo> getAppointmentSlotsByIds(@WebParam(name = "appointmentSlotIds") List<String> appointmentSlotIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves all AppointmentWindows belonging to the person and period
     *
     * @param personId          the identifier for the Person
     * @param periodMilestoneId the identifier of the Period milestone
     * @param contextInfo       Context information containing the principalId
     *                          and locale information about the caller of
     *                          service operation
     * @return AppointmentSlots belonging to the Person or an empty list if none
     *         found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException personId or contextInfo is missing or
     *                                   null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException  unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException an authorization failure occurred
     */
    public List<AppointmentSlotInfo> getAppointmentSlotsByPersonAndPeriod(@WebParam(name = "personId") String personId, @WebParam(name = "periodMilestoneId") String periodMilestoneId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves all AppointmentWindows to the given AppointmentWindowSlot
     *
     * @param appointmentWindowId the identifier for the AppointmentWindow
     * @param contextInfo         Context information containing the principalId
     *                            and locale information about the caller of
     *                            service operation
     * @return AppointmentSlots belonging to the AppointmentWindow or an empty
     *         list if none found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException contextInfo is not valid
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException appointmentWindowId or contextInfo is
     *                                   missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException  unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException an authorization failure occurred
     */
    public List<AppointmentSlotInfo> getAppointmentSlotsByWindow(@WebParam(name = "appointmentWindowId") String appointmentWindowId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;


    /**
     * Searches for AppointmentSlots based on the criteria and returns a list of
     * AppointmentSlot identifiers which match the search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of AppointmentSlot Ids
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException criteria, contextInfo is missing or
     *                                   null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException  unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForAppointmentSlotIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Searches for AppointmentSlots based on the criteria and returns a list of
     * AppointmentSlots which match the search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo Context information containing the principalId and
     *                    locale information about the caller of service
     *                    operation
     * @return list of AppointmentSlot information
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException criteria, contextInfo is missing or
     *                                   null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException  unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException an authorization failure occurred
     */
    public List<AppointmentSlotInfo> searchForAppointmentSlots(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Validate an AppointmentSlot
     *
     * @param validationTypeKey      the identifier for the validation Type
     * @param appointmentWindowId    appointment slot of the person
     * @param appointmentSlotTypeKey appointment slot type
     * @param appointmentSlotInfo    detailed information about the
     *                               AppointmentSlot
     * @param contextInfo            context information containing the
     *                               principalId and locale information about
     *                               the caller of service operation
     * @return a list of validation results or an empty list if validation
     *         succeeded
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException        validationTypeKey, appointmentWindowId
     *                                      or appointmentSlotTypeKey does not
     *                                      exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException    invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException    validationTypeKey, appointmentWindowId,
     *                                      appointmentSlotTypeKey or
     *                                      contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException     unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException    an authorization failure occurred
     */
    public List<ValidationResultInfo> validateAppointmentSlot(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "appointmentWindowId") String appointmentWindowId, @WebParam(name = "appointmentSlotTypeKey") String appointmentSlotTypeKey, @WebParam(name = "appointmentSlotInfo") AppointmentSlotInfo appointmentSlotInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Create an AppointmentSlot
     *
     * @param appointmentWindowId    appointment slot of the person
     * @param appointmentSlotTypeKey appointment slot type
     * @param appointmentSlotInfo    detailed information about the
     *                               AppointmentSlot
     * @param contextInfo            context information containing the
     *                               principalId and locale information about
     *                               the caller of service operation
     * @return created AppointmentSlot for the given AppointmentWindow and slot
     *         information
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException supplied data is invalid
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException        appointmentWindowId or appointmentSlotTypeKey
     *                                      does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException    invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException    appointmentWindowId, appointmentSlotTypeKey
     *                                      or contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException     unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException    an authorization failure occurred
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException            an attempt at supplying information
     *                                      designated as read only
     * @impl Check out blackout milestone type and corresponding milestones
     */
    public AppointmentSlotInfo createAppointmentSlot(@WebParam(name = "appointmentWindowId") String appointmentWindowId, @WebParam(name = "appointmentSlotTypeKey") String appointmentSlotTypeKey, @WebParam(name = "appointmentSlotInfo") AppointmentSlotInfo appointmentSlotInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Generate AppointmentSlots for the AppointmentWindow using its
     * AppointmentSlotRule information
     *
     * @param appointmentWindowId appointment window to which these slots belong
     * @param contextInfo         context information containing the principalId
     *                            and locale information about the caller of
     *                            service operation
     * @return created AppointmentSlots for the window using its
     *         AppointmentSlotRule information
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException supplied data is invalid
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException        appointmentWindowId does not exist
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException    invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException    appointmentWindowId or contextInfo
     *                                      is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException     unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException    an authorization failure occurred
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException            an attempt at supplying information
     *                                      designated as read only
     * @impl Check out blackout milestone type and corresponding milestones
     * @impl throw OperationFailedException if unable to create needed slots either because  both EndDate and MaxAppointmentsPerSlot were specified and cannot be satisfied or due to some other reason
     */
    public List<AppointmentSlotInfo> generateAppointmentSlotsByWindow(@WebParam(name = "appointmentWindowId") String appointmentWindowId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

    /**
     * Updates an appointment slot
     *
     * @param appointmentSlotId   identifier of the appointmentSlot to be
     *                            updated
     * @param appointmentSlotInfo information about the object appointmentSlot
     *                            to be updated
     * @param contextInfo         context information containing the principalId
     *                            and locale information about the caller of
     *                            service operation
     * @return updated appointment slot  information
     * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException one or more values invalid for this
     *                                      operation
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException        appointmentSlotId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException    invalid appointmentSlotInfo or
     *                                      contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException    appointmentSlotId, appointmentSlotInfo
     *                                      or contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException     unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException    an authorization failure occurred
     * @throws org.kuali.student.r2.common.exceptions.ReadOnlyException            an attempt at supplying information
     *                                      designated as read-only
     * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException     optimistic locking failure or the
     *                                      action was attempted on an out of
     *                                      date version
     */
    public AppointmentSlotInfo updateAppointmentSlot(@WebParam(name = "appointmentSlotId") String appointmentSlotId, @WebParam(name = "appointmentSlotInfo") AppointmentSlotInfo appointmentSlotInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException;

    /**
     * Delete an AppointmentSlot
     *
     * @param appointmentSlotId the identifier for the AppointmentSlot
     * @param contextInfo       context information containing the principalId
     *                          and locale information about the caller of
     *                          service operation
     * @return status of the operation (success, failed)
     * @throws org.kuali.student.r2.common.exceptions.DependentObjectsExistException delete would leave orphaned
     *                                        objects or violate integrity
     *                                        constraints
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException          appointmentWindowId or appointmentSlotId
     *                                        not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException      invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException      appointmentWindowId, appointmentSlotId
     *                                        or contextInfo is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException       unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException      an authorization failure occurred
     */
    public StatusInfo deleteAppointmentSlot(@WebParam(name = "appointmentSlotId") String appointmentSlotId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Deletes AppointmentSlots belonging to an AppointmentWindow
     *
     * @param appointmentWindowId object Appointment relationship identifier
     * @param contextInfo         context information containing the principalId
     *                            and locale information about the caller of
     *                            service operation
     * @return status of the operation (success, failed)
     * @throws org.kuali.student.r2.common.exceptions.DependentObjectsExistException delete would leave orphaned
     *                                        objects or violate integrity
     *                                        constraints
     * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException          appointmentWindowId not found
     * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException      invalid contextInfo
     * @throws org.kuali.student.r2.common.exceptions.MissingParameterException      appointmentWindowId or contextInfo
     *                                        is missing or null
     * @throws org.kuali.student.r2.common.exceptions.OperationFailedException       unable to complete request
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException      an authorization failure occurred
     * @impl delete the corresponding appointments
     */
    public StatusInfo deleteAppointmentSlotsByWindow(@WebParam(name = "appointmentWindowId") String appointmentWindowId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

}
