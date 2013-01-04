/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 *
 * Created by Charles on 3/21/12
 */
package org.kuali.student.r2.core.class1.appointment.service.decorators;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.appointment.dto.AppointmentInfo;
import org.kuali.student.r2.core.appointment.dto.AppointmentSlotInfo;
import org.kuali.student.r2.core.appointment.dto.AppointmentWindowInfo;
import org.kuali.student.r2.core.appointment.service.AppointmentService;

import javax.jws.WebParam;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public abstract class AppointmentServiceDecorator implements AppointmentService {
    private AppointmentService nextDecorator;

    public AppointmentService getNextDecorator() throws OperationFailedException {
        if (null == nextDecorator) {
            throw new OperationFailedException("Misconfigured application: nextDecorator is null");
        }
        return nextDecorator;
    }

    public void setNextDecorator(AppointmentService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    @Override
    public AppointmentInfo getAppointment(@WebParam(name = "appointmentId") String appointmentId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAppointment(appointmentId, contextInfo);
    }

    @Override
    public List<AppointmentInfo> getAppointmentsByIds(@WebParam(name = "appointmentIds") List<String> appointmentIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAppointmentsByIds(appointmentIds, contextInfo);
    }

    @Override
    public List<String> getAppointmentIdsByType(@WebParam(name = "appointmentTypeKey") String appointmentTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAppointmentIdsByType(appointmentTypeKey, contextInfo);
    }

    @Override
    public List<AppointmentInfo> getAppointmentsBySlot(@WebParam(name = "appointmentSlotId") String appointmentSlotId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAppointmentsBySlot(appointmentSlotId, contextInfo);
    }

    @Override
    public List<String> getAppointmentIdsByPerson(@WebParam(name = "personId") String personId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAppointmentIdsByPerson(personId, contextInfo);
    }

    @Override
    public List<AppointmentInfo> getAppointmentsByPersonAndSlot(@WebParam(name = "personId") String personId, @WebParam(name = "appointmentSlotId") String appointmentSlotId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAppointmentsByPersonAndSlot(personId,appointmentSlotId, contextInfo);
    }

    @Override
    public List<String> searchForAppointmentIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForAppointmentIds(criteria,contextInfo);
    }

    @Override
    public List<AppointmentInfo> searchForAppointments(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForAppointments(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateAppointment(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "personId") String personId, @WebParam(name = "appointmentSlotId") String appointmentSlotId, @WebParam(name = "appointmentTypeKey") String appointmentTypeKey, @WebParam(name = "appointmentInfo") AppointmentInfo appointmentInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateAppointment(validationTypeKey, personId, appointmentSlotId, appointmentTypeKey, appointmentInfo, contextInfo);
    }

    @Override
    public AppointmentInfo createAppointment(@WebParam(name = "personId") String personId, @WebParam(name = "appointmentSlotId") String appointmentSlotId, @WebParam(name = "appointmentTypeKey") String appointmentTypeKey, @WebParam(name = "appointmentInfo") AppointmentInfo appointmentInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createAppointment(personId, appointmentSlotId, appointmentTypeKey,appointmentInfo, contextInfo);
    }

    @Override
    public StatusInfo generateAppointmentsByWindow(@WebParam(name = "appointmentWindowId") String appointmentWindowId, @WebParam(name = "appointmentTypeKey") String appointmentTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().generateAppointmentsByWindow(appointmentWindowId, appointmentTypeKey, contextInfo);
    }

    @Override
    public AppointmentInfo updateAppointment(@WebParam(name = "appointmentId") String appointmentId, @WebParam(name = "appointmentInfo") AppointmentInfo appointmentInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateAppointment(appointmentId, appointmentInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteAppointment(@WebParam(name = "appointmentId") String appointmentId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteAppointment(appointmentId, contextInfo);
    }

    @Override
    public StatusInfo deleteAppointmentsBySlot(@WebParam(name = "appointmentSlotId") String appointmentSlotId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteAppointmentsBySlot(appointmentSlotId, contextInfo);
    }

    @Override
    public StatusInfo deleteAppointmentsByWindow(@WebParam(name = "appointmentWindowId") String appointmentWindowId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteAppointmentsByWindow(appointmentWindowId, contextInfo);
    }

    @Override
    public AppointmentWindowInfo getAppointmentWindow(@WebParam(name = "appointmentWindowId") String appointmentWindowId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAppointmentWindow(appointmentWindowId, contextInfo);
    }

    @Override
    public List<AppointmentWindowInfo> getAppointmentWindowsByIds(@WebParam(name = "appointmentWindowIds") List<String> appointmentWindowIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAppointmentWindowsByIds(appointmentWindowIds, contextInfo);
    }

    @Override
    public List<String> getAppointmentWindowIdsByType(@WebParam(name = "appointmentWindowTypeKey") String appointmentWindowTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAppointmentWindowIdsByType(appointmentWindowTypeKey, contextInfo);
    }

    @Override
    public List<String> getAppointmentWindowIdsByPopulation(@WebParam(name = "populationId") String populationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAppointmentWindowIdsByPopulation(populationId, contextInfo);
    }

    @Override
    public List<AppointmentWindowInfo> getAppointmentWindowsByPeriod(@WebParam(name = "periodMilestoneId") String periodMilestoneId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAppointmentWindowsByPeriod(periodMilestoneId, contextInfo);
    }

    @Override
    public List<String> searchForAppointmentWindowIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForAppointmentWindowIds(criteria, contextInfo);
    }

    @Override
    public List<AppointmentWindowInfo> searchForAppointmentWindows(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForAppointmentWindows(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateAppointmentWindow(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "appointmentWindowTypeKey") String appointmentWindowTypeKey, @WebParam(name = "appointmentWindowInfo") AppointmentWindowInfo appointmentWindowInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateAppointmentWindow(validationTypeKey, appointmentWindowTypeKey, appointmentWindowInfo, contextInfo);
    }

    @Override
    public AppointmentWindowInfo createAppointmentWindow(@WebParam(name = "appointmentWindowTypeKey") String appointmentWindowTypeKey, @WebParam(name = "appointmentWindowInfo") AppointmentWindowInfo appointmentWindowInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createAppointmentWindow(appointmentWindowTypeKey, appointmentWindowInfo, contextInfo);
    }

    @Override
    public AppointmentWindowInfo updateAppointmentWindow(@WebParam(name = "appointmentWindowId") String appointmentWindowId, @WebParam(name = "appointmentWindowInfo") AppointmentWindowInfo appointmentWindowInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateAppointmentWindow(appointmentWindowId, appointmentWindowInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteAppointmentWindowCascading(String appointmentWindowId, ContextInfo contextInfo) throws
            DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteAppointmentWindowCascading(appointmentWindowId, contextInfo);
    }

    @Override
    public AppointmentSlotInfo getAppointmentSlot(@WebParam(name = "appointmentSlotId") String appointmentSlotId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAppointmentSlot(appointmentSlotId, contextInfo);
    }

    @Override
    public List<AppointmentSlotInfo> getAppointmentSlotsByIds(@WebParam(name = "appointmentSlotIds") List<String> appointmentSlotIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAppointmentSlotsByIds(appointmentSlotIds, contextInfo);
    }

    @Override
    public List<AppointmentSlotInfo> getAppointmentSlotsByPersonAndPeriod(@WebParam(name = "personId") String personId, @WebParam(name = "periodMilestoneId") String periodMilestoneId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAppointmentSlotsByPersonAndPeriod(personId, periodMilestoneId, contextInfo);
    }

    @Override
    public List<AppointmentSlotInfo> getAppointmentSlotsByWindow(@WebParam(name = "appointmentWindowId") String appointmentWindowId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getAppointmentSlotsByWindow(appointmentWindowId, contextInfo);
    }

    @Override
    public List<String> searchForAppointmentSlotIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForAppointmentSlotIds(criteria, contextInfo);
    }

    @Override
    public List<AppointmentSlotInfo> searchForAppointmentSlots(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForAppointmentSlots(criteria, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateAppointmentSlot(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "appointmentWindowId") String appointmentWindowId, @WebParam(name = "appointmentSlotTypeKey") String appointmentSlotTypeKey, @WebParam(name = "appointmentSlotInfo") AppointmentSlotInfo appointmentSlotInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().validateAppointmentSlot(validationTypeKey, appointmentWindowId, appointmentSlotTypeKey, appointmentSlotInfo, contextInfo);
    }

    @Override
    public AppointmentSlotInfo createAppointmentSlot(@WebParam(name = "appointmentWindowId") String appointmentWindowId, @WebParam(name = "appointmentSlotTypeKey") String appointmentSlotTypeKey, @WebParam(name = "appointmentSlotInfo") AppointmentSlotInfo appointmentSlotInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createAppointmentSlot(appointmentWindowId, appointmentSlotTypeKey, appointmentSlotInfo, contextInfo);
    }

    @Override
    public List<AppointmentSlotInfo> generateAppointmentSlotsByWindow(@WebParam(name = "appointmentWindowId") String appointmentWindowId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().generateAppointmentSlotsByWindow(appointmentWindowId, contextInfo);
    }

    @Override
    public AppointmentSlotInfo updateAppointmentSlot(@WebParam(name = "appointmentSlotId") String appointmentSlotId, @WebParam(name = "appointmentSlotInfo") AppointmentSlotInfo appointmentSlotInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return getNextDecorator().updateAppointmentSlot(appointmentSlotId, appointmentSlotInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteAppointmentSlotCascading(@WebParam(name = "appointmentSlotId") String appointmentSlotId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteAppointmentSlotCascading(appointmentSlotId, contextInfo);
    }

    @Override
    public StatusInfo deleteAppointmentSlotsByWindowCascading(@WebParam(name = "appointmentWindowId") String appointmentWindowId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteAppointmentSlotsByWindowCascading(appointmentWindowId, contextInfo);
    }
}
