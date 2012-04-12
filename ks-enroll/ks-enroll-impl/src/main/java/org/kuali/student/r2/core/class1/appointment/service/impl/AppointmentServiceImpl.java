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
import org.kuali.student.r2.common.dto.*;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.appointment.constants.AppointmentServiceConstants;
import org.kuali.student.r2.core.appointment.dto.AppointmentInfo;
import org.kuali.student.r2.core.appointment.dto.AppointmentSlotInfo;
import org.kuali.student.r2.core.appointment.dto.AppointmentSlotRuleInfo;
import org.kuali.student.r2.core.appointment.dto.AppointmentWindowInfo;
import org.kuali.student.r2.core.appointment.service.AppointmentService;
import org.kuali.student.r2.core.class1.appointment.dao.AppointmentDao;
import org.kuali.student.r2.core.class1.appointment.dao.AppointmentSlotDao;
import org.kuali.student.r2.core.class1.appointment.dao.AppointmentWindowDao;
import org.kuali.student.r2.core.class1.appointment.model.AppointmentEntity;
import org.kuali.student.r2.core.class1.appointment.model.AppointmentSlotEntity;
import org.kuali.student.r2.core.class1.appointment.model.AppointmentWindowEntity;

import java.util.*;
import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.kuali.student.r2.core.population.service.PopulationService;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
@WebService(name = "AppointmentWindowService", serviceName = "AppointmentWindowService", portName = "AppointmentWindowService", targetNamespace = "http://student.kuali.org/wsdl/appointmentwindow")
public class AppointmentServiceImpl implements AppointmentService {
    // Note: add getters/setters to instance variables otherwise, can't dependency inject!!!!
    @Resource
    private AppointmentWindowDao appointmentWindowDao;
    @Resource
    private AppointmentSlotDao appointmentSlotDao;
    @Resource
    private AppointmentDao appointmentDao;

    private PopulationService populationService;

//    @Resource
//    private AtpService atpService;
    
    private static int MILLIS_IN_SECOND = 1000;
    private static int MILLIS_IN_MINUTE = MILLIS_IN_SECOND * 60;
    private static int MINUTES_IN_HOUR = 60;

    public PopulationService getPopulationService() {
        return populationService;
    }

    public void setPopulationService(PopulationService populationService) {
        this.populationService = populationService;
    }

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

    public AppointmentDao getAppointmentDao() {
        return appointmentDao;
    }

    public void setAppointmentDao(AppointmentDao appointmentDao) {
        this.appointmentDao = appointmentDao;
    }
    @Override
    @Transactional(readOnly = true)
    public AppointmentInfo getAppointment(@WebParam(name = "appointmentId") String appointmentId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentInfo> getAppointmentsByIds(@WebParam(name = "appointmentIds") List<String> appointmentIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAppointmentIdsByType(@WebParam(name = "appointmentTypeKey") String appointmentTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentInfo> getAppointmentsBySlot(String appointmentSlotId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<AppointmentEntity> apptEntityList = appointmentDao.getAppointmentsBySlotId(appointmentSlotId);
        List<AppointmentInfo> apptInfoList = new ArrayList<AppointmentInfo>();
        for (AppointmentEntity entity: apptEntityList) {
            AppointmentInfo info = entity.toDto();
            apptInfoList.add(info);
        }
        return apptInfoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAppointmentIdsByPerson(@WebParam(name = "personId") String personId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentInfo> getAppointmentsByPersonAndSlot(@WebParam(name = "personId") String personId, @WebParam(name = "appointmentSlotId") String appointmentSlotId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> searchForAppointmentIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.return new ArrayList<String>();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentInfo> searchForAppointments(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ValidationResultInfo> validateAppointment(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "personId") String personId, @WebParam(name = "appointmentSlotId") String appointmentSlotId, @WebParam(name = "appointmentTypeKey") String appointmentTypeKey, @WebParam(name = "appointmentInfo") AppointmentInfo appointmentInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /*
     * This is pulled out so other methods can call this without the transactional behavior.
     */
    private AppointmentInfo _createAppointmentNoTransact(String personId, String appointmentSlotId, String appointmentTypeKey, AppointmentInfo appointmentInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        AppointmentEntity  appointmentEntity = new AppointmentEntity();
        appointmentEntity.fromDto(appointmentInfo);
        // TODO: Determine if there should be a check between apptType/slotId and apptInfo counterparts
        // Need to manually set the entity since appointmentInfo only has an id for its corresponding AppointmentSlot
        AppointmentSlotEntity slotEntity = appointmentSlotDao.find(appointmentSlotId);
        if (null == slotEntity) {
            throw new DoesNotExistException(appointmentSlotId);
        }
        appointmentEntity.setSlotEntity(slotEntity); // This completes the initialization of appointmentSlotEntity

        appointmentDao.persist(appointmentEntity);
        return appointmentEntity.toDto();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public AppointmentInfo createAppointment(String personId, String appointmentSlotId, String appointmentTypeKey, AppointmentInfo appointmentInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return _createAppointmentNoTransact(personId, appointmentSlotId, appointmentTypeKey, appointmentInfo, contextInfo);
    }

    private AppointmentInfo _createAppointmentInfo(String studentId, String slotId) {
        AppointmentInfo apptInfo = new AppointmentInfo();
        apptInfo.setPersonId(studentId);
        apptInfo.setSlotId(slotId);
        apptInfo.setTypeKey(AppointmentServiceConstants.APPOINTMENT_TYPE_REGISTRATION);
        apptInfo.setStateKey(AppointmentServiceConstants.APPOINTMENT_STATE_ACTIVE_KEY);
        return apptInfo;
    }

    private void _generateAppointmentsOneSlotCase(List<String> studentIds, List<AppointmentSlotInfo> slotInfoList, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException, DataValidationErrorException, ReadOnlyException {
        String slotId = slotInfoList.get(0).getId();  // Only one slot in the one slot case
        for (String studentId: studentIds) {
            AppointmentInfo apptInfo = _createAppointmentInfo(studentId, slotId);
            _createAppointmentNoTransact(studentId, slotId, AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_ONE_SLOT_KEY, apptInfo, contextInfo);
        }
    }

    private void _generateAppointmentsUniformCase(List<String> studentIds, List<AppointmentSlotInfo> slotInfoList, ContextInfo contextInfo) {
        int numSlots = slotInfoList.size();
        int numStudents = studentIds.size();
        int studentsPerSlot = numStudents / numSlots + 1; // Need to add 1, or we'll not have enough
    }

    private void _generateAppointmentsMaxCase() {
        // TODO: Stubbed out case
    }

    private void _generateAppointments(String appointmentTypeKey, List<String> students, 
                                       List<AppointmentSlotInfo> slotInfoList, ContextInfo contextInfo,
                                       StatusInfo statusInfo) throws OperationFailedException, InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException, ReadOnlyException, PermissionDeniedException {
        // May want to adjust message in the status info
        if (appointmentTypeKey.equals(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_ONE_SLOT_KEY)) {
            _generateAppointmentsOneSlotCase(students, slotInfoList, contextInfo);
        } else if (appointmentTypeKey.equals(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_SLOTTED_UNIFORM_KEY)) {
            // TODO: Finish
        } else if (appointmentTypeKey.equals(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_SLOTTED_MAX_KEY)) {
            _generateAppointmentsMaxCase(); // TODO: Finish
        } else {
            throw new OperationFailedException("Manual window allocation not supported");
        }
    }

    private List<AppointmentSlotInfo> _sortSlotInfoListByStartDate(List<AppointmentSlotInfo> slotInfoList) {
        // Check if slots are already in chronological order--no need to sort if they are
        boolean isSorted = true;
        AppointmentSlotInfo prev = null;
        for (AppointmentSlotInfo slotInfo: slotInfoList) {
            if (prev != null) {
                if (prev.getStartDate().after(slotInfo.getStartDate())) {
                    isSorted = false;
                    break;
                }
                prev = slotInfo;
            }
        }
        if (isSorted) {
            return slotInfoList;
        }
        // Not sorted, so use Collections.sort to sort
        Collections.sort(slotInfoList, new Comparator<AppointmentSlotInfo>() {
            @Override
            public int compare(AppointmentSlotInfo first, AppointmentSlotInfo second) {
                if (first.getStartDate().before(second.getStartDate())) {
                    return -1;
                } else if (first.getStartDate().after(second.getStartDate())) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        return slotInfoList; // Technically, don't need to return (since original list is mutated,
                             // but I like it better with a return
    }
    
    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo generateAppointmentsByWindow(String appointmentWindowId, String appointmentTypeKey, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        StatusInfo statusInfo = null;
        AppointmentWindowEntity entity = appointmentWindowDao.find(appointmentWindowId);
        if (entity == null) {
            throw new DoesNotExistException(appointmentWindowId);
        }
        String populationId = entity.getAssignedPopulationId();
        List<AppointmentSlotInfo> slotInfoList = getAppointmentSlotsByWindow(appointmentWindowId, contextInfo);
        slotInfoList = _sortSlotInfoListByStartDate(slotInfoList); // Make sure it's sorted
        statusInfo = new StatusInfo();
        // Get the population
        List<String> studentIds = populationService.getMembers(populationId, contextInfo);
        // Generate appointments based on appointmentTypeKey
        _generateAppointments(appointmentTypeKey, studentIds, slotInfoList, contextInfo, statusInfo);
        // Change state from draft to assigned
        _changeApptWinState(entity, AppointmentServiceConstants.APPOINTMENT_WINDOW_STATE_ASSIGNED_KEY);
        statusInfo.setSuccess(true);
        return statusInfo;  //To change body of implemented methods use File | Settings | File Templates.
    }


    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public AppointmentInfo updateAppointment(@WebParam(name = "appointmentId") String appointmentId, @WebParam(name = "appointmentInfo") AppointmentInfo appointmentInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        AppointmentEntity appointmentEntity = appointmentDao.find(appointmentId);
        if (null != appointmentEntity) {
            appointmentEntity.fromDto(appointmentInfo);
            appointmentEntity.setUpdateId(contextInfo.getPrincipalId());
            appointmentEntity.setUpdateTime(contextInfo.getCurrentDate());
            appointmentDao.merge(appointmentEntity);
            return appointmentEntity.toDto();
        } else {
            throw new DoesNotExistException(appointmentId);
        }
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteAppointment(String appointmentId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);

        AppointmentEntity appt = appointmentDao.find(appointmentId);
        if (null != appt) {
            appointmentDao.remove(appt);
        } else {
            throw new DoesNotExistException(appointmentId);
        }
        // TODO: Figure out what else to put in status
        return status;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteAppointmentsBySlot(String appointmentSlotId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);

        AppointmentSlotEntity apptSlot = appointmentSlotDao.find(appointmentSlotId);
        if (null != apptSlot) {
            _deleteAppointmentsBySlot(appointmentSlotId);
        } else {
            throw new DoesNotExistException(appointmentSlotId);
        }
        // TODO: Figure out what else to put in status
        return status;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteAppointmentsByWindow(String appointmentWindowId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);

        AppointmentWindowEntity apptWin = appointmentWindowDao.find(appointmentWindowId);
        if (null != apptWin) {
            _deleteAppointmentsByWindow(apptWin, false); // don't delete the slots
        } else {
            throw new DoesNotExistException(apptWin.getId());
        }
        return status;
    }

    @Override
    @Transactional(readOnly = true)
    public AppointmentWindowInfo getAppointmentWindow(String appointmentWindowId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        AppointmentWindowEntity apptWin = appointmentWindowDao.find(appointmentWindowId);
        if (null == apptWin) {
            throw new DoesNotExistException(appointmentWindowId);
        }
        return apptWin.toDto();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentWindowInfo> getAppointmentWindowsByIds(@WebParam(name = "appointmentWindowIds") List<String> appointmentWindowIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAppointmentWindowIdsByType(@WebParam(name = "appointmentWindowTypeKey") String appointmentWindowTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAppointmentWindowIdsByPopulation(@WebParam(name = "populationId") String populationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentWindowInfo> getAppointmentWindowsByPeriod(String periodMilestoneId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<AppointmentWindowEntity> winEntList = appointmentWindowDao.getAppointmentWindowsByMilestoneId(periodMilestoneId);
        List<AppointmentWindowInfo> winInfoList = new ArrayList<AppointmentWindowInfo>();
        for (AppointmentWindowEntity entity: winEntList) {
            AppointmentWindowInfo winInfo = entity.toDto();
            winInfoList.add(winInfo);
        }
        return winInfoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> searchForAppointmentWindowIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentWindowInfo> searchForAppointmentWindows(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ValidationResultInfo> validateAppointmentWindow(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "appointmentWindowTypeKey") String appointmentWindowTypeKey, @WebParam(name = "appointmentWindowInfo") AppointmentWindowInfo appointmentWindowInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public AppointmentWindowInfo createAppointmentWindow(String appointmentWindowTypeKey, AppointmentWindowInfo appointmentWindowInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // TODO: Check what to do in inconsistency between appointmentWindowTypeKey and type in appointmentWindowInfo
        AppointmentWindowEntity apptWin = new AppointmentWindowEntity(appointmentWindowTypeKey, appointmentWindowInfo);
        apptWin.setCreateId(contextInfo.getPrincipalId());
        apptWin.setCreateTime(contextInfo.getCurrentDate());
        apptWin.setUpdateId(contextInfo.getPrincipalId());
        apptWin.setUpdateTime(contextInfo.getCurrentDate());
        appointmentWindowDao.persist(apptWin);
        return apptWin.toDto();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public AppointmentWindowInfo updateAppointmentWindow(String appointmentWindowId, AppointmentWindowInfo appointmentWindowInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        AppointmentWindowEntity appointmentWindowEntity = appointmentWindowDao.find(appointmentWindowId);
        if (null != appointmentWindowEntity) {
            appointmentWindowEntity.fromDto(appointmentWindowInfo);
            appointmentWindowEntity.setUpdateId(contextInfo.getPrincipalId());
            appointmentWindowEntity.setUpdateTime(contextInfo.getCurrentDate());
            appointmentWindowDao.merge(appointmentWindowEntity);
            return appointmentWindowEntity.toDto();
        } else {
            throw new DoesNotExistException(appointmentWindowId);
        }
    }

    private List<AppointmentSlotEntity> _fetchSlotEntitiesByWindows(String apptWinId) {
        List<AppointmentSlotEntity> list = appointmentSlotDao.getSlotsByWindowIdSorted(apptWinId);
        return list;
    }

    private void _deleteAppointmentSlots(List<AppointmentSlotEntity> slotList) {
        if (slotList != null) {
            for (AppointmentSlotEntity entity: slotList) {
                // Use dao since deleteAppointmentSlot is transactional per slot deletion (we want the entire
                // delete operation to be one big transaction (cclin asks: correct?)
                appointmentSlotDao.remove(entity);
            }
        }
    }

    private int _deleteAppointmentsBySlot(String slotId) {
        List<AppointmentEntity> apptList = appointmentDao.getAppointmentsBySlotId(slotId);
        if (apptList != null) {
            for (AppointmentEntity appt: apptList) {
                appointmentDao.remove(appt);
            }
            return apptList.size();
        }
        return -1; // didn't find any appointments (does this really happen?)
    }
    /**
     * Deletes all appointments associated with this slot list.
     * Precondition: Don't call directly by public methods--doesn't change state of window
     * @param slotList Assume not-null
     */
    private void _deleteAppointmentsBySlotList_(List<AppointmentSlotEntity> slotList) {
        for (AppointmentSlotEntity slotEntity: slotList) {
            _deleteAppointmentsBySlot(slotEntity.getId());
        }
    }

    /**
     * Helper that is used both by deleteAppointmentWindow and deleteAppointmentSlotsByWindow
     * @param windowEntity An appointment window entity
     * @param shouldDeleteSlots true, if you want slots to also be deleted
     */
    private void _deleteAppointmentsByWindow(AppointmentWindowEntity windowEntity, boolean shouldDeleteSlots) {
        List<AppointmentSlotEntity> slotList = _fetchSlotEntitiesByWindows(windowEntity.getId());
        if (slotList != null) {
            // Delete appointments, if any
            _deleteAppointmentsBySlotList_(slotList);
            // Delete slots, if any
            _deleteAppointmentSlots(slotList);
        }
        _changeApptWinState(windowEntity, AppointmentServiceConstants.APPOINTMENT_WINDOW_STATE_DRAFT_KEY);
    }

    /**
     * Changes the state of the appointment window (to draft or assigned)
     * Precondition: apptWinId and stateKey are valid
     * @param windowEntity The appointment window ID in the DB
     * @param stateKey Either AppointmentServiceConstants.APPOINTMENT_WINDOW_STATE_DRAFT_KEY or
     *                 AppointmentServiceConstants.APPOINTMENT_WINDOW_STATE_ASSIGNED_KEY
     */
    private void _changeApptWinState(AppointmentWindowEntity windowEntity, String stateKey) {
        windowEntity.setApptWindowState(stateKey);
        appointmentWindowDao.update(windowEntity);
    }


    private void _deleteAppointmentSlotsByWindow(AppointmentWindowEntity windowEntity) {
        _deleteAppointmentsByWindow(windowEntity, true); // also, delete slots
    }

    /**
     * Deletes appointment windows, slots, and appointments.
     * @param windowEntity The window (and its dependent parts) to be deleted.
     */
    private void _deleteAppointmentWindow(AppointmentWindowEntity windowEntity) {
        _deleteAppointmentSlotsByWindow(windowEntity);
        appointmentWindowDao.remove(windowEntity);
        // No need to update the state since the window is gone
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteAppointmentWindow(String appointmentWindowId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);

        AppointmentWindowEntity apptWin = appointmentWindowDao.find(appointmentWindowId);
        if (null != apptWin) {
            _deleteAppointmentWindow(apptWin);
        } else {
            throw new DoesNotExistException(appointmentWindowId);
        }
        // TODO Handle removal of orphan RichTextEntities
        return status;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentSlotInfo> getAppointmentSlotsByPersonAndPeriod(@WebParam(name = "personId") String personId, @WebParam(name = "periodMilestoneId") String periodMilestoneId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentSlotInfo> getAppointmentSlotsByWindow(String appointmentWindowId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<AppointmentSlotEntity> entities = appointmentSlotDao.getSlotsByWindowIdSorted(appointmentWindowId);
        List<AppointmentSlotInfo> infoList = new ArrayList<AppointmentSlotInfo>();
        for (AppointmentSlotEntity entity: entities) {
            AppointmentSlotInfo slotInfo = entity.toDto();
            infoList.add(slotInfo);
        }
        return infoList;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> searchForAppointmentSlotIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentSlotInfo> searchForAppointmentSlots(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<ValidationResultInfo> validateAppointmentSlot(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "appointmentWindowId") String appointmentWindowId, @WebParam(name = "appointmentSlotTypeKey") String appointmentSlotTypeKey, @WebParam(name = "appointmentSlotInfo") AppointmentSlotInfo appointmentSlotInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(readOnly = true)
    public AppointmentSlotInfo getAppointmentSlot(String appointmentSlotId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        AppointmentSlotEntity apptSlot = appointmentSlotDao.find(appointmentSlotId);
        if (null == apptSlot) {
            throw new DoesNotExistException(appointmentSlotId);
        }
        return apptSlot.toDto();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public AppointmentSlotInfo createAppointmentSlot(String appointmentWindowId, String appointmentSlotTypeKey, AppointmentSlotInfo appointmentSlotInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        AppointmentSlotEntity appointmentSlotEntity = new AppointmentSlotEntity(appointmentSlotTypeKey, appointmentSlotInfo);
        appointmentSlotEntity.fromDto(appointmentSlotInfo);
        // Need to manually set the entity since appointmentSlotInfo only has an id for its corresponding AppointmentWindow
        AppointmentWindowEntity windowEntity = appointmentWindowDao.find(appointmentWindowId);
        if(null == windowEntity) {
            throw new DoesNotExistException(appointmentWindowId);
        }
        appointmentSlotEntity.setApptWinEntity(windowEntity); // This completes the initialization of appointmentSlotEntity
        appointmentSlotDao.persist(appointmentSlotEntity);
        return appointmentSlotEntity.toDto();
    }

    private List<AppointmentSlotInfo> _createOneSlotPerWindow(AppointmentWindowEntity apptWin, ContextInfo contextInfo)
            throws InvalidParameterException, OperationFailedException, MissingParameterException, PermissionDeniedException, ReadOnlyException, DataValidationErrorException, DoesNotExistException {
        AppointmentSlotInfo slotInfo = new AppointmentSlotInfo();
        slotInfo.setStartDate(apptWin.getStartDate());
        // Find end date
        _setEndDateToDropDate(slotInfo);

        // One slot per appointment window has no set end date and is defined by the end of the period
        slotInfo.setTypeKey(AppointmentServiceConstants.APPOINTMENT_SLOT_TYPE_OPEN_KEY);
        slotInfo.setStateKey(AppointmentServiceConstants.APPOINTMENT_SLOTS_STATE_ACTIVE_KEY);
        List<AppointmentSlotInfo> slots = new ArrayList<AppointmentSlotInfo>();
        slots.add(slotInfo);
        return slots;
    }

    /**
     * Computes minutes since start of day (midnight).  We don't need millisecond granularity.  
     * Assumption: We ignore daylight saving.  Thus, 9 AM is always 9 * 60 = 540 minutes from midnight.
     * @param milliseconds Milliseconds since start of day
     * @return Minutes since start of day
     */
    private int _computeMinuteOffsetSinceMidnight(Long milliseconds) {
        int minutes = (int) (milliseconds / MILLIS_IN_MINUTE);
        return minutes;
    }

    private static int DAYS_OF_WEEK[] = {Calendar.SUNDAY, Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY,
                                          Calendar.FRIDAY, Calendar.SATURDAY};

    // weekdays contains values 1 -7 (1: Sunday, ..., 7: Saturday)
    private boolean _isDayInWeekdays(Calendar date, List<Integer> weekdays) {
        int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
        for (int i = 0; i < weekdays.size(); i++) {
            int adjustedDayOfWeek = weekdays.get(i) - 1; // Shift to 0..6
            if (dayOfWeek == DAYS_OF_WEEK[adjustedDayOfWeek]) {
                return true;
            }
        }
        return false;
    }

    private boolean _isSameDay(Calendar first, Calendar second) {
        return first.get(Calendar.YEAR) == second.get(Calendar.YEAR) &&
                first.get(Calendar.MONTH) == second.get(Calendar.MONTH) &&
                first.get(Calendar.DAY_OF_MONTH) == second.get(Calendar.DAY_OF_MONTH);
    }
    
    private void _zeroOutHoursMinutesEtc(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }

    private void _zeroOutSecondsMillis(Calendar cal) {
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }

    private Calendar _generateNextDay(Calendar date) {
        Calendar newDate = Calendar.getInstance();
        newDate.setTimeInMillis(date.getTimeInMillis()); // Copy the date
        newDate.add(Calendar.DAY_OF_MONTH, 1); // Add a day
        return newDate;
    }
    
    private List<Calendar> _generateDaysForSlots(Date start, Date end, List<Integer> weekdays) {
        List<Calendar> calList = new ArrayList<Calendar>();
        
        if (start.after(end)) {
            return calList;
        }
        
        // For the purpose of deciding which days appointments occur, we'll zero out the hours/minutes, etc.
        Calendar candidateDay = Calendar.getInstance();
        candidateDay.setTime(start);
        _zeroOutHoursMinutesEtc(candidateDay);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(end);
        _zeroOutHoursMinutesEtc(endCal);
        
        // Keep looping until you reach last day
        Date endDate = endCal.getTime();
        for (;!_isSameDay(candidateDay, endCal); candidateDay = _generateNextDay(candidateDay)) {
            if (_isDayInWeekdays(candidateDay, weekdays)) {
                calList.add(candidateDay);
            }
        }
        // Have to check the last day as well to see if it's a valid day to have appointments
        if (_isDayInWeekdays(endCal, weekdays)) {
            calList.add(endCal);
        }
        return calList;
    }

    private int computeMinuteOffset(Calendar cal) {
        int hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        return (hourOfDay * MINUTES_IN_HOUR) + minute;
    }
    
    /**
     * Determines if appointment can be scheduled for current day (mostly checks if "end" of this appointment
     * Dan Symonds has said (3/29/2012) a valid appointment time starts between [startTimeOfDay, endTimeOfDay]
     * which means an appointment slot can be scheduled exactly at endTimeOfDay.
     * Precondition: apptTime occurs on or after the start time of the day.  Assumes current time is valid.
     *               Assumes start time of day is never at 2 AM (when daylight saving could take effect).  Presumably
     *               weekdays excludes Sundays for this reason.
     * @param apptTime Start of appointment.  Accurate to month/day/year plus hour/minute (assumes seconds/millis zero)
     * @param endTimeInMinutes Starting from midnight, the offset in minutes to end of day (assumes no daylight saving changes,
     *                         thus, 9 AM is always 9 * 60 = 540 minutes after midnight)
     * @return true, if the apptTime is valid (i.e., it can be scheduled
     */
    private boolean _isValidAppointmentTime(Calendar apptTime, int endTimeInMinutes) {
        int minuteOffset = computeMinuteOffset(apptTime);
        if (minuteOffset <= endTimeInMinutes) {
            return true;
        }
        return false;
    }

    private void _setEndDateToDropDate(AppointmentSlotInfo slotInfo) {
//        // TODO: Figure out how to do this
//        List<AtpInfo> infoList = atpService.getAtpsByMilestone(apptWin.getPeriodMilestoneId(), contextInfo);
//        if (infoList.size() != 1) {
//            throw new InvalidParameterException("Should only return one ATP");
//        }
//        AtpInfo info = infoList.get(0);
//        List<MilestoneInfo> milestoneList = atpService.getMilestonesForAtp(info.getId(), contextInfo);
//        boolean found = false;
//        for (MilestoneInfo milestone: milestoneList) {
//            if (milestone.getTypeKey().equals(AtpServiceConstants.MILESTONE_DROP_DATE_TYPE_KEY)) {
//                slotInfo.setEndDate(milestone.getEndDate());
//                found = true;
//                break;
//            }
//        }
//        if (!found) {
//            throw new OperationFailedException("Drop date milestone not found");
//        }
    }

    /**
     * Adds appointment slot to slotList for given day.  Does not set the appointment window ID.
     * @param slotList Where to put the result (mutated)
     * @param day The month/day/year/hour/minute of the appointment
     */
    private void _addAppointmentSlot(List<AppointmentSlotInfo> slotList, Calendar day) {
        AppointmentSlotInfo slotInfo = new AppointmentSlotInfo();
        slotInfo.setStateKey(AppointmentServiceConstants.APPOINTMENT_SLOTS_STATE_ACTIVE_KEY);
        slotInfo.setTypeKey(AppointmentServiceConstants.APPOINTMENT_SLOT_TYPE_OPEN_KEY); // TODO: verify this is valid
        Date date = day.getTime();
        slotInfo.setStartDate(date);
        _setEndDateToDropDate(slotInfo);
        slotList.add(slotInfo);
    }

    private boolean _withinWindowTime(Calendar day, Calendar startDate, Calendar endDate) {
        return !day.before(startDate) && !day.after(endDate);
    }
    /**
     * @param slotList Mutated with slots added for current day
     * @param day Assumption is only month/day/year matters.
     * @param intervalInMinutes How long between the start of each appointment (in minutes)
     * @param startOfDayInMinutes When appointments can start (minutes offset since midnight)
     * @param endOfDayInMinutes When appointments "end" (minutes offset since midnight)
     */
    private void _createAppointmentSlotsForDay(List<AppointmentSlotInfo> slotList, final Calendar day, Calendar startDate,
                                               Calendar endDate, int intervalInMinutes, int startOfDayInMinutes, int endOfDayInMinutes) {
        // Should probably throw exception is hourInDay is 3 AM or earlier (to avoid daylight saving issue)
        int hourOfDay = startOfDayInMinutes / 60;
        int minutes = startOfDayInMinutes % 60;
        Calendar apptTime = Calendar.getInstance();
        apptTime.setTimeInMillis(day.getTimeInMillis());
        apptTime.set(Calendar.HOUR_OF_DAY, hourOfDay); // avoids the daylight saving issue
        apptTime.set(Calendar.MINUTE, minutes);
        for ( /* no init */ ;
                _isValidAppointmentTime(apptTime, endOfDayInMinutes);
                apptTime.add(Calendar.MINUTE, intervalInMinutes)) {
            if (_withinWindowTime(apptTime, startDate, endDate)) {
                _addAppointmentSlot(slotList, apptTime);
            }
        }
    }
    /**
     *
     * @param dayList Days which appointments can be scheduled
     * @param startDate When this window starts
     * @param endDate When this window ends
     * @param startOfDay Start time of day that appointments can be scheduled
     * @param endOfDay End time of day that appointments can be scheduled
     * @param intervalInMinutes Difference between start time in minutes
     * Assumption: Appointments are scheduled during business hours so that students can meet with an advisor either to
     *             get an advising block removed (so they can register) or get advice.  The assumption is the "appointment"
     *             requires intervalInMinutes and that should be scheduled so it lands inside the start/end of day.
     *             Appointments start times should be scheduled [startOfDay, endOfDay).  That is, you can't schedule an
     *             appointment at endOfDay, and each appointment lasts intervalInMinutes which must lie within this period too.
     * @return List of appointment slots
     */
    private List<AppointmentSlotInfo> _createAppointmentSlots(List<Calendar> dayList, Calendar startDate, Calendar endDate, 
                                                              TimeOfDayInfo startOfDay, TimeOfDayInfo endOfDay, int intervalInMinutes) throws InvalidParameterException {
        int startOfDayInMinutes = _computeMinuteOffsetSinceMidnight(startOfDay.getMilliSeconds());
        int endOfDayInMinutes = _computeMinuteOffsetSinceMidnight(endOfDay.getMilliSeconds());
        if (startOfDayInMinutes >= endOfDayInMinutes) {
            throw new InvalidParameterException("Start time should precede end time");
        }
        List<AppointmentSlotInfo> slotList = new ArrayList<AppointmentSlotInfo>();
        for (Calendar day: dayList) {
            _createAppointmentSlotsForDay(slotList, day, startDate, endDate, intervalInMinutes, startOfDayInMinutes, endOfDayInMinutes);
        }
        return slotList;
    }

    private List<AppointmentSlotInfo> _createMultiSlots(AppointmentWindowInfo apptWinInfo, ContextInfo contextInfo)
            throws InvalidParameterException, OperationFailedException, MissingParameterException, PermissionDeniedException, ReadOnlyException, DataValidationErrorException, DoesNotExistException {
        Date start = apptWinInfo.getStartDate();
        Date end = apptWinInfo.getEndDate();
        AppointmentSlotRuleInfo ruleInfo = apptWinInfo.getSlotRule();

        TimeAmountInfo durationQuantityInfo = ruleInfo.getSlotDuration(); // Assume NULL for now
        // Check for start interval (assume minutes)
        // TODO: check if slot interval is required or optional and how to handle if optional
        int startIntervalDiffMinutes =  ruleInfo.getSlotStartInterval().getTimeQuantity();

        List<Integer> weekdays = ruleInfo.getWeekdays();
        // Assume weekdays 1 = Sunday, ..., 7 = Saturday
        TimeOfDayInfo startOfDay = ruleInfo.getStartTimeOfDay();
        TimeOfDayInfo endOfDay = ruleInfo.getEndTimeOfDay();

        // First, get the days
        List<Calendar> days = _generateDaysForSlots(start, end, ruleInfo.getWeekdays());
        // Convert start/end to Calendar
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(start);
        _zeroOutSecondsMillis(startDate);
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(end);
        _zeroOutSecondsMillis(endDate);
        // Then, generate the slots
        List<AppointmentSlotInfo> result = _createAppointmentSlots(days, startDate, endDate, startOfDay, endOfDay, startIntervalDiffMinutes);
        return result;
    }

    private void _assignApptWinIdToSlots(List<AppointmentSlotInfo> slotList, String appointmentWindowId) {
        for (AppointmentSlotInfo slot: slotList) {
            slot.setAppointmentWindowId(appointmentWindowId);
        }
    }
    
    private List<AppointmentSlotInfo> _createAppointmentSlots(List<AppointmentSlotInfo> slotInfoList, String apptSlotTypeKey, String apptWinId, ContextInfo contextInfo) throws InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException, ReadOnlyException, PermissionDeniedException, OperationFailedException {
        List<AppointmentSlotInfo> newSlotInfoList = new ArrayList<AppointmentSlotInfo>();
        for (AppointmentSlotInfo slotInfo: slotInfoList) {
            AppointmentSlotInfo newSlotInfo = createAppointmentSlot(apptWinId, apptSlotTypeKey, slotInfo, contextInfo);
            newSlotInfoList.add(newSlotInfo);
        }
        return newSlotInfoList;
    }
    private boolean isSupportedAppointmentWindowState(String windowType) {
        return windowType == AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_ONE_SLOT_KEY ||
                windowType == AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_SLOTTED_UNIFORM_KEY ||
                windowType == AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_SLOTTED_MAX_KEY;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public List<AppointmentSlotInfo> generateAppointmentSlotsByWindow(String appointmentWindowId, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
                    MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        AppointmentWindowEntity apptWin = appointmentWindowDao.find(appointmentWindowId);
        if (apptWin == null) {
            throw new DoesNotExistException(appointmentWindowId);
        }
        AppointmentWindowInfo apptWinInfo = apptWin.toDto();
        List<AppointmentSlotInfo> slotList = null;
        // Delete previous slots/assignments to get us into a clean state before generating new slots
        if (isSupportedAppointmentWindowState(apptWinInfo.getTypeKey())) {
            _deleteAppointmentSlotsByWindow(apptWin);
        }
        // if statement between the three supported cases
        if (apptWin.getApptWindowType().equals(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_ONE_SLOT_KEY)) {
            slotList = _createOneSlotPerWindow(apptWin, contextInfo);
        } else if (apptWin.getApptWindowType().equals(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_SLOTTED_MAX_KEY) ||
                   apptWin.getApptWindowType().equals(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_SLOTTED_UNIFORM_KEY)) {
            slotList = _createMultiSlots(apptWinInfo, contextInfo);
        } else {
            throw new UnsupportedOperationException("Only support one slot, max, and uniform slotting");
        }
        _assignApptWinIdToSlots(slotList, appointmentWindowId);
        // This returns created slots with appropriate IDs
        slotList = _createAppointmentSlots(slotList, apptWin.getApptWindowType(), appointmentWindowId, contextInfo);
        return slotList;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public AppointmentSlotInfo updateAppointmentSlot(String appointmentSlotId, AppointmentSlotInfo appointmentSlotInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        AppointmentSlotEntity appointmentSlotEntity = appointmentSlotDao.find(appointmentSlotId);
        if (null != appointmentSlotEntity) {
            String apptWinId = appointmentSlotEntity.getApptWinEntity().getId();
            AppointmentWindowEntity appointmentWindowEntity = appointmentWindowDao.find(apptWinId);
            if (null != appointmentWindowEntity) {
                appointmentSlotEntity.fromDto(appointmentSlotInfo);
                appointmentSlotEntity.setUpdateId(contextInfo.getPrincipalId());
                appointmentSlotEntity.setUpdateTime(contextInfo.getCurrentDate());
                appointmentSlotDao.merge(appointmentSlotEntity);
                return appointmentSlotEntity.toDto();
            } else {
                // Prefixing this since this refers to an appointment window
                throw new DoesNotExistException("appointmentWindowEntity:" + apptWinId);
            }
        } else {
            throw new DoesNotExistException(appointmentSlotId);
        }
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteAppointmentSlot(String appointmentSlotId, ContextInfo contextInfo) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);

        AppointmentSlotEntity apptSlot = appointmentSlotDao.find(appointmentSlotId);
        if (null != apptSlot) {
            _deleteAppointmentsBySlot(apptSlot.getId());
        } else {
            throw new DoesNotExistException(appointmentSlotId);
        }
        return status;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteAppointmentSlotsByWindow(@WebParam(name = "appointmentWindowId") String appointmentWindowId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // This will also delete associated appointments since they would otherwise refer to non-existent slots
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);

        AppointmentWindowEntity apptWin = appointmentWindowDao.find(appointmentWindowId);
        if (null != apptWin) {
            _deleteAppointmentSlotsByWindow(apptWin);
        } else {
            throw new DoesNotExistException(appointmentWindowId);
        }
        return status;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentSlotInfo> getAppointmentSlotsByIds(List<String> appointmentSlotIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        throw new UnsupportedOperationException();
    }
}
