/*
 * Copyright 2012 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.r2.core.appointment.service;

import java.util.List;

import javax.jws.WebParam;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.core.appointment.dto.AppointmentSlotInfo;


/**
 * Appointment Service Logic that is common regardless of how the implementation is realized. 
 * <p/>
 * i.e. this functionality can be used in both the mock and real implementations.
 * 
 * @author Kuali Student Team 
 *
 */
public interface AppointmentServiceBusinessLogic {

    /**
     * Generate Appointments for an AppointmentWindow.
     *
     * @param appointmentWindowId appointment window to which these appointments belong
     * @param appointmentTypeKey  appointment type
     * @param contextInfo         Context information containing the principalId
     *                            and locale information about the caller of
     *                            service operation
     * @return status of the operation (success, failed)
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        appointmentWindowId or appointmentTypeKey
     *                                      does not exist
     * @throws InvalidParameterException    invalid appointmentInfo or
     *                                      contextInfo
     * @throws MissingParameterException    personId, appointmentWindowId,
     *                                      appointmentTypeKey or contextInfo is
     *                                      missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read only
     * @impl AppointmentSlots are assumed to be already generated for the
     * AppointmentWindow
     * @impl Return the number of appointments created as part of StatusInfo
     * message field
     */
    public StatusInfo generateAppointmentsByWindow(@WebParam(name = "appointmentWindowId") String appointmentWindowId, @WebParam(name = "appointmentTypeKey") String appointmentTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

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
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        appointmentWindowId does not exist
     * @throws InvalidParameterException    invalid contextInfo
     * @throws MissingParameterException    appointmentWindowId or contextInfo
     *                                      is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at supplying information
     *                                      designated as read only
     * @impl Check out blackout milestone type and corresponding milestones
     * @impl throw OperationFailedException if unable to create needed slots either because  both EndDate and MaxAppointmentsPerSlot were specified and cannot be satisfied or due to some other reason
     */
    public List<AppointmentSlotInfo> generateAppointmentSlotsByWindow(@WebParam(name = "appointmentWindowId") String appointmentWindowId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException;

}
