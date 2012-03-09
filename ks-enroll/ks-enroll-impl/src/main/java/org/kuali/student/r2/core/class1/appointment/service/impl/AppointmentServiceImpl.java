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
 * Created by Charles on 2/28/12
 */
package org.kuali.student.r2.core.class1.appointment.service.impl;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.appointment.dto.AppointmentInfo;
import org.kuali.student.r2.core.appointment.dto.AppointmentSlotInfo;
import org.kuali.student.r2.core.appointment.dto.AppointmentWindowInfo;
import org.kuali.student.r2.core.appointment.service.AppointmentService;
import org.kuali.student.r2.core.class1.appointment.dao.AppointmentSlotDao;
import org.kuali.student.r2.core.class1.appointment.dao.AppointmentWindowDao;
import org.kuali.student.r2.core.class1.appointment.model.AppointmentSlotEntity;
import org.kuali.student.r2.core.class1.appointment.model.AppointmentWindowEntity;

import java.util.List;
import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.kuali.student.r2.core.class1.atp.model.AtpEntity;
import org.kuali.student.r2.core.class1.type.dao.TypeDao;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
@WebService(name = "AppointmentWindowService", serviceName = "AppointmentWindowService", portName = "AppointmentWindowService", targetNamespace = "http://student.kuali.org/wsdl/appointmentwindow")
@Transactional(readOnly = true, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
public class AppointmentServiceImpl implements AppointmentService {
    @Resource
    private AppointmentWindowDao appointmentWindowDao;
    @Resource
    private AppointmentSlotDao appointmentSlotDao;

    public AppointmentWindowDao getAppointmentWindowDao() {
        return appointmentWindowDao;
    }

    public void setAppointmentWindowDao(AppointmentWindowDao appointmentWindowDao) {
        this.appointmentWindowDao = appointmentWindowDao;
    }

    public AppointmentSlotDao getAppointmentSlotDao() {
        return appointmentSlotDao;
    }

    public void setAppointmentSlotDao(AppointmentSlotDao appointmentSlotDao) {
        this.appointmentSlotDao = appointmentSlotDao;
    }

    @Override
    public AppointmentInfo getAppointment(@WebParam(name = "appointmentId") String appointmentId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<AppointmentInfo> getAppointmentsByIds(@WebParam(name = "appointmentIds") List<String> appointmentIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getAppointmentIdsByType(@WebParam(name = "appointmentTypeKey") String appointmentTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<AppointmentInfo> getAppointmentsBySlot(@WebParam(name = "appointmentSlotId") String appointmentSlotId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getAppointmentIdsByPerson(@WebParam(name = "personId") String personId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<AppointmentInfo> getAppointmentsByPersonAndSlot(@WebParam(name = "personId") String personId, @WebParam(name = "appointmentSlotId") String appointmentSlotId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> searchForAppointmentIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<AppointmentInfo> searchForAppointments(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ValidationResultInfo> validateAppointment(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "personId") String personId, @WebParam(name = "appointmentSlotId") String appointmentSlotId, @WebParam(name = "appointmentTypeKey") String appointmentTypeKey, @WebParam(name = "appointmentInfo") AppointmentInfo appointmentInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public AppointmentInfo createAppointment(@WebParam(name = "personId") String personId, @WebParam(name = "appointmentSlotId") String appointmentSlotId, @WebParam(name = "appointmentTypeKey") String appointmentTypeKey, @WebParam(name = "appointmentInfo") AppointmentInfo appointmentInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo generateAppointmentsByWindow(@WebParam(name = "appointmentWindowId") String appointmentWindowId, @WebParam(name = "appointmentTypeKey") String appointmentTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public AppointmentInfo updateAppointment(@WebParam(name = "appointmentId") String appointmentId, @WebParam(name = "appointmentInfo") AppointmentInfo appointmentInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override

    public StatusInfo deleteAppointment(String appointmentId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo deleteAppointmentsBySlot(@WebParam(name = "appointmentSlotId") String appointmentSlotId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public StatusInfo deleteAppointmentsByWindow(@WebParam(name = "appointmentWindowId") String appointmentWindowId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public AppointmentWindowInfo getAppointmentWindow(String appointmentWindowId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        AppointmentWindowEntity apptWin = appointmentWindowDao.find(appointmentWindowId);
        if (null == apptWin) {
            throw new DoesNotExistException(appointmentWindowId);
        }
        return apptWin.toDto();
    }

    @Override
    public List<AppointmentWindowInfo> getAppointmentWindowsByIds(@WebParam(name = "appointmentWindowIds") List<String> appointmentWindowIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getAppointmentWindowIdsByType(@WebParam(name = "appointmentWindowTypeKey") String appointmentWindowTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getAppointmentWindowIdsByPopulation(@WebParam(name = "populationId") String populationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<AppointmentWindowInfo> getAppointmentWindowsByPeriod(@WebParam(name = "periodMilestoneId") String periodMilestoneId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> searchForAppointmentWindowIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<AppointmentWindowInfo> searchForAppointmentWindows(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ValidationResultInfo> validateAppointmentWindow(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "appointmentWindowTypeKey") String appointmentWindowTypeKey, @WebParam(name = "appointmentWindowInfo") AppointmentWindowInfo appointmentWindowInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional
    public AppointmentWindowInfo createAppointmentWindow(String appointmentWindowTypeKey, AppointmentWindowInfo appointmentWindowInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        AppointmentWindowEntity apptWin = new AppointmentWindowEntity(appointmentWindowInfo);

        appointmentWindowDao.persist(apptWin);

        AppointmentWindowEntity retrieved = appointmentWindowDao.find(apptWin.getId());
        AppointmentWindowInfo info = null;
        if (retrieved != null) {
            info = retrieved.toDto();
        } else {
            throw new OperationFailedException("Appointment Window not found after persisted. apptWinId: " + apptWin.getId());
        }

        return info;
    }

    @Override
    @Transactional
    public AppointmentWindowInfo updateAppointmentWindow(String appointmentWindowId, AppointmentWindowInfo appointmentWindowInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        AppointmentWindowEntity apptWin = appointmentWindowDao.find(appointmentWindowId);

        if (null != apptWin) {
            AppointmentWindowEntity modifiedApptWin = new AppointmentWindowEntity(appointmentWindowInfo);
            appointmentWindowDao.merge(modifiedApptWin);
            return appointmentWindowDao.find(modifiedApptWin.getId()).toDto();
        } else {
            throw new DoesNotExistException(appointmentWindowId);
        }
    }

    @Override
    @Transactional
    public StatusInfo deleteAppointmentWindow(String appointmentWindowId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);

        AppointmentWindowEntity apptWin = appointmentWindowDao.find(appointmentWindowId);
        if (null != apptWin) {
            appointmentWindowDao.remove(apptWin);
        } else {
            status.setSuccess(Boolean.FALSE);
        }
        // TODO Handle removal of orphan RichTextEntities
        return status;
    }

    @Override
    public List<AppointmentSlotInfo> getAppointmentSlotsByPersonAndPeriod(@WebParam(name = "personId") String personId, @WebParam(name = "periodMilestoneId") String periodMilestoneId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<AppointmentSlotInfo> getAppointmentSlotsByWindow(@WebParam(name = "appointmentWindowId") String appointmentWindowId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> searchForAppointmentSlotIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<AppointmentSlotInfo> searchForAppointmentSlots(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ValidationResultInfo> validateAppointmentSlot(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "appointmentWindowId") String appointmentWindowId, @WebParam(name = "appointmentSlotTypeKey") String appointmentSlotTypeKey, @WebParam(name = "appointmentSlotInfo") AppointmentSlotInfo appointmentSlotInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public AppointmentSlotInfo getAppointmentSlot(String appointmentSlotId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        AppointmentSlotEntity apptSlot = appointmentSlotDao.find(appointmentSlotId);
        if (null == apptSlot) {
            throw new DoesNotExistException(appointmentSlotId);
        }
        return apptSlot.toDto();
    }

    @Override
    @Transactional
    public AppointmentSlotInfo createAppointmentSlot(String appointmentWindowId, String appointmentSlotTypeKey, AppointmentSlotInfo appointmentSlotInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        AppointmentSlotEntity apptSlot = new AppointmentSlotEntity(appointmentSlotInfo);
        // Need to manually set the entity since appointmentSlotInfo only has an id for its corresponding AppointmentWindow
        AppointmentWindowEntity windowEntity = appointmentWindowDao.find(appointmentWindowId);
        apptSlot.setApptWinEntity(windowEntity); // This completes the initialization of apptSlot
        appointmentSlotDao.persist(apptSlot);

        AppointmentSlotEntity retrieved = appointmentSlotDao.find(apptSlot.getId());
        AppointmentSlotInfo info = null;
        if (retrieved != null) {
            info = retrieved.toDto();
        } else {
            throw new OperationFailedException("Appointment Window not found after persisted. apptSlotId: " + apptSlot.getId());
        }

        return info;
    }

    @Override
    public List<AppointmentSlotInfo> generateAppointmentSlotsByWindow(@WebParam(name = "appointmentWindowId") String appointmentWindowId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public AppointmentSlotInfo updateAppointmentSlot(String appointmentSlotId, AppointmentSlotInfo appointmentSlotInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        AppointmentSlotEntity apptSlot = appointmentSlotDao.find(appointmentSlotId);

        if (null != apptSlot) {
            String apptWinId = apptSlot.getApptWinEntity().getId();
            AppointmentWindowEntity apptWin = appointmentWindowDao.find(apptWinId);
            if (null != apptWin) {
                AppointmentSlotEntity modifiedApptSlot = new AppointmentSlotEntity(appointmentSlotInfo);
                // Need to manually set appointment window entity (without it, an exception is thrown)
                modifiedApptSlot.setApptWinEntity(apptWin);
                appointmentSlotDao.merge(modifiedApptSlot);
                return appointmentSlotDao.find(modifiedApptSlot.getId()).toDto();
            } else {
                // Prefixing this since this refers to an appointment window
                throw new DoesNotExistException("apptWinId:" + apptWinId);
            }
        } else {
            throw new DoesNotExistException(appointmentSlotId);
        }
    }

    @Override
    @Transactional
    public StatusInfo deleteAppointmentSlot(String appointmentSlotId, ContextInfo contextInfo) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);

        AppointmentSlotEntity apptSlot = appointmentSlotDao.find(appointmentSlotId);
        if (null != apptSlot) {
            appointmentSlotDao.remove(apptSlot);
        } else {
            status.setSuccess(Boolean.FALSE);
        }

        return status;
    }

    @Override
    public StatusInfo deleteAppointmentSlotsByWindow(@WebParam(name = "appointmentWindowId") String appointmentWindowId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
