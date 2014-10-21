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

import org.kuali.rice.core.api.criteria.GenericQueryResults;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.criteria.CriteriaLookupService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
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
import org.kuali.student.r2.core.population.service.PopulationService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.jws.WebService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class provides the default implementation of the AppointmentService
 *
 * @author Kuali Student Team
 */
@WebService(name = "AppointmentWindowService", serviceName = "AppointmentWindowService", portName = "AppointmentWindowService", targetNamespace = "http://student.kuali.org/wsdl/appointmentwindow")
public class AppointmentServiceImpl implements AppointmentService {
    @Resource
    private AppointmentWindowDao appointmentWindowDao;
    @Resource
    private AppointmentSlotDao appointmentSlotDao;
    @Resource
    private AppointmentDao appointmentDao;

    private PopulationService populationService;

    private CriteriaLookupService criteriaLookupService;

    private static AppointmentServiceImplHelper helper = null;
//    @Resource
//    private AtpService atpService;

    private static int MILLIS_IN_SECOND = 1000;
    private static int MILLIS_IN_MINUTE = MILLIS_IN_SECOND * 60;
    private static int MINUTES_IN_HOUR = 60;

    private AppointmentServiceImplHelper _getHelper() {
        if (helper == null) {
            helper = new AppointmentServiceImplHelper();
        }
        return helper;
    }

    public PopulationService getPopulationService() {
        return populationService;
    }

    public void setPopulationService(PopulationService populationService) {
        this.populationService = populationService;
        _getHelper().setPopulationService(populationService);
    }

    public void setCriteriaLookupService(CriteriaLookupService criteriaLookupService) {
        this.criteriaLookupService = criteriaLookupService;
    }

    public AppointmentWindowDao getAppointmentWindowDao() {
        return appointmentWindowDao;
    }

    public void setAppointmentWindowDao(AppointmentWindowDao appointmentWindowDao) {
        this.appointmentWindowDao = appointmentWindowDao;
        _getHelper().setAppointmentWindowDao(appointmentWindowDao);
    }

    public AppointmentSlotDao getAppointmentSlotDao() {
        return appointmentSlotDao;
    }

    public void setAppointmentSlotDao(AppointmentSlotDao appointmentSlotDao) {
        this.appointmentSlotDao = appointmentSlotDao;
        _getHelper().setAppointmentSlotDao(appointmentSlotDao);
    }

    public AppointmentDao getAppointmentDao() {
        return appointmentDao;
    }

    public void setAppointmentDao(AppointmentDao appointmentDao) {
        this.appointmentDao = appointmentDao;
        _getHelper().setAppointmentDao(appointmentDao);
    }

    @Override
    @Transactional(readOnly = true)
    public AppointmentInfo getAppointment(String appointmentId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentInfo> getAppointmentsByIds(List<String> appointmentIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAppointmentIdsByType(String appointmentTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentInfo> getAppointmentsBySlot(String appointmentSlotId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return helper.getAppointmentsBySlotNoTransact(appointmentSlotId, contextInfo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAppointmentIdsByPerson(String personId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentInfo> getAppointmentsByPersonAndSlot(String personId, String appointmentSlotId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
    public List<ValidationResultInfo> validateAppointment(String validationTypeKey, String personId, String appointmentSlotId, String appointmentTypeKey, AppointmentInfo appointmentInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public AppointmentInfo createAppointment(String personId, String appointmentSlotId, String appointmentTypeKey, AppointmentInfo appointmentInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return helper.createAppointmentNoTransact(personId, appointmentSlotId, appointmentTypeKey, appointmentInfo, contextInfo);
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
        statusInfo = new StatusInfo();
        // Get the population
        List<String> studentIds = populationService.getMembersAsOfDate(populationId, new Date(), contextInfo);
        // Set the status to true here--gives the _generateAppointments method a chance to set it to
        // false, which should only happen in the max allocation
        statusInfo.setSuccess(true);
        // Generate appointments based on appointmentTypeKey (may set success to false)

        helper.generateAppointments(appointmentWindowId, appointmentTypeKey, entity.getMaxAppointmentsPerSlot(), studentIds, slotInfoList, contextInfo, statusInfo);

        // Change state from draft to assigned
        try {
            helper.changeApptWinState(entity, AppointmentServiceConstants.APPOINTMENT_WINDOW_STATE_ASSIGNED_KEY);
        } catch (VersionMismatchException e) {
            throw new OperationFailedException("failed to change AppointmentWindowState for id=" + entity.getId() , e);
        }

        return statusInfo;  //To change body of implemented methods use File | Settings | File Templates.
    }


    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public AppointmentInfo updateAppointment(String appointmentId, AppointmentInfo appointmentInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        AppointmentEntity appointmentEntity = appointmentDao.find(appointmentId);
        if (null != appointmentEntity) {
            appointmentEntity.fromDto(appointmentInfo);

            appointmentEntity.setEntityUpdated(contextInfo);

            appointmentEntity = appointmentDao.merge(appointmentEntity);
            appointmentDao.getEm().flush();
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
            helper.deleteAppointmentsBySlotCascading(appointmentSlotId);
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
            helper.deleteAppointmentsByWindow(apptWin, false); // don't delete the slots
        } else {
            throw new DoesNotExistException(appointmentWindowId);
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
    public List<AppointmentWindowInfo> getAppointmentWindowsByIds(List<String> appointmentWindowIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAppointmentWindowIdsByType(String appointmentWindowTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAppointmentWindowIdsByPopulation(String populationId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentWindowInfo> getAppointmentWindowsByPeriod(String periodMilestoneId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<AppointmentWindowEntity> winEntList = appointmentWindowDao.getAppointmentWindowsByMilestoneId(periodMilestoneId);
        List<AppointmentWindowInfo> winInfoList = new ArrayList<AppointmentWindowInfo>();
        for (AppointmentWindowEntity entity : winEntList) {
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
    public List<AppointmentWindowInfo> searchForAppointmentWindows(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        GenericQueryResults<AppointmentWindowEntity> results = criteriaLookupService.lookup(AppointmentWindowEntity.class, criteria);
        List<AppointmentWindowInfo> appointmentWindows = new ArrayList<AppointmentWindowInfo>(results.getResults().size());
        for (AppointmentWindowEntity awe : results.getResults()) {
            AppointmentWindowInfo awi = awe.toDto();
            appointmentWindows.add(awi);

        }
        return appointmentWindows;
    }

    @Override
    public List<ValidationResultInfo> validateAppointmentWindow(String validationTypeKey, String appointmentWindowTypeKey, AppointmentWindowInfo appointmentWindowInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public AppointmentWindowInfo createAppointmentWindow(String appointmentWindowTypeKey, AppointmentWindowInfo appointmentWindowInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        // TODO: Check what to do in inconsistency between appointmentWindowTypeKey and type in appointmentWindowInfo
        AppointmentWindowEntity apptWin = new AppointmentWindowEntity(appointmentWindowTypeKey, appointmentWindowInfo);

        apptWin.setEntityCreated(contextInfo);

        appointmentWindowDao.persist(apptWin);
        appointmentWindowDao.getEm().flush();
        
        return apptWin.toDto();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public AppointmentWindowInfo updateAppointmentWindow(String appointmentWindowId, AppointmentWindowInfo appointmentWindowInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        AppointmentWindowEntity appointmentWindowEntity = appointmentWindowDao.find(appointmentWindowId);
        if (null != appointmentWindowEntity) {
            appointmentWindowEntity.fromDto(appointmentWindowInfo);

            appointmentWindowEntity.setEntityUpdated(contextInfo);

            appointmentWindowEntity = appointmentWindowDao.merge(appointmentWindowEntity);
            
            appointmentWindowDao.getEm().flush();
            
            return appointmentWindowEntity.toDto();
        } else {
            throw new DoesNotExistException(appointmentWindowId);
        }
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteAppointmentWindowCascading(String appointmentWindowId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);

        AppointmentWindowEntity apptWin = appointmentWindowDao.find(appointmentWindowId);
        if (null != apptWin) {
            helper.deleteAppointmentWindowCascading(apptWin);
        } else {
            throw new DoesNotExistException(appointmentWindowId);
        }
        // TODO Handle removal of orphan RichTextEntities
        return status;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentSlotInfo> getAppointmentSlotsByPersonAndPeriod(String personId, String periodMilestoneId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<AppointmentSlotEntity> entities = appointmentSlotDao.getAppointmentSlotsByPersonAndPeriod(personId, periodMilestoneId);
        List<AppointmentSlotInfo> infoList = new ArrayList<AppointmentSlotInfo>();
        for (AppointmentSlotEntity entity : entities) {
            AppointmentSlotInfo slotInfo = entity.toDto();
            infoList.add(slotInfo);
        }
        return infoList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentSlotInfo> getAppointmentSlotsByWindow(String appointmentWindowId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<AppointmentSlotEntity> entities = appointmentSlotDao.getSlotsByWindowIdSorted(appointmentWindowId);
        List<AppointmentSlotInfo> infoList = new ArrayList<AppointmentSlotInfo>();
        for (AppointmentSlotEntity entity : entities) {
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
    public List<ValidationResultInfo> validateAppointmentSlot(String validationTypeKey, String appointmentWindowId, String appointmentSlotTypeKey, AppointmentSlotInfo appointmentSlotInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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

        appointmentSlotEntity.setEntityCreated(contextInfo);

        // Need to manually set the entity since appointmentSlotInfo only has an id for its corresponding AppointmentWindow
        AppointmentWindowEntity windowEntity = appointmentWindowDao.find(appointmentWindowId);
        if (null == windowEntity) {
            throw new DoesNotExistException(appointmentWindowId);
        }
        appointmentSlotEntity.setApptWinEntity(windowEntity); // This completes the initialization of appointmentSlotEntity
        appointmentSlotDao.persist(appointmentSlotEntity);
        appointmentSlotDao.getEm().flush();
        return appointmentSlotEntity.toDto();
    }

    private boolean _isSupportedAppointmentWindowState(String windowType) {
        return AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_ONE_SLOT_KEY.equals(windowType) ||
                AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_SLOTTED_UNIFORM_KEY.equals(windowType) ||
                AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_SLOTTED_MAX_KEY.equals(windowType);
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
        if (_isSupportedAppointmentWindowState(apptWinInfo.getTypeKey())) {
            helper.deleteAppointmentSlotsByWindowCascading(apptWin);
        }
        // if statement between the three supported cases
        String apptWinType = apptWin.getApptWindowType();
        if (AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_ONE_SLOT_KEY.equals(apptWinType)) {
            slotList = helper.createOneSlotPerWindow(apptWin, contextInfo);
        } else if (AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_SLOTTED_MAX_KEY.equals(apptWinType) ||
                AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_SLOTTED_UNIFORM_KEY.equals(apptWinType)) {
            AppointmentSlotRuleInfo slotRule = apptWinInfo.getSlotRule();
            if (slotRule == null) {
                throw new MissingParameterException("Missing slot rule");
            } else if (slotRule.getStartTimeOfDay() == null) {
                throw new MissingParameterException("Missing start time of day in slot rule");
            } else if (slotRule.getEndTimeOfDay() == null) {
                throw new MissingParameterException("Missing end time of day in slot rule");
            } else if (!slotRule.getStartTimeOfDay().isBefore(slotRule.getEndTimeOfDay())) {
                throw new InvalidParameterException("End time of day should be AFTER start time of day");
            } else if (slotRule.getStartTimeOfDay().getHour() < 1) {
                throw new InvalidParameterException("Start time should be 1 AM or after");
            } else if (slotRule.getEndTimeOfDay().getHour() < 1) {
                throw new InvalidParameterException("End time should be 1 AM or after");
            }

            if (AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_SLOTTED_MAX_KEY.equals(apptWinType)) {
                if (apptWin.getMaxAppointmentsPerSlot() == null) {
                    // Handle null case
                    throw new InvalidParameterException("Null max. Max appointment slot allocation require positive max value");
                } else if (apptWin.getMaxAppointmentsPerSlot() <= 0) {
                    // Handle 0 or negative error case
                    int maxAppts = apptWin.getMaxAppointmentsPerSlot();
                    throw new InvalidParameterException("Invalid max: " + maxAppts + ". Max appointment slot allocation require positive max value");
                }
            }

            Object[] result = helper.createMultiSlots(apptWinInfo, contextInfo);
            slotList = (List<AppointmentSlotInfo>) result[0];
        } else {
            throw new UnsupportedOperationException("Only support one slot, max, and uniform slotting");
        }
        // This returns created slots with appropriate IDs
        slotList = helper.createAppointmentSlots(slotList, apptWin.getApptWindowType(), appointmentWindowId, contextInfo);
        return slotList;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public AppointmentSlotInfo updateAppointmentSlot(String appointmentSlotId, AppointmentSlotInfo appointmentSlotInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        AppointmentSlotEntity appointmentSlotEntity = appointmentSlotDao.find(appointmentSlotId);
        if (null != appointmentSlotEntity) {
            appointmentSlotEntity.fromDto(appointmentSlotInfo);

            appointmentSlotEntity.setEntityUpdated(contextInfo);

            appointmentSlotEntity = appointmentSlotDao.merge(appointmentSlotEntity);
            
            appointmentSlotDao.getEm().flush();
            
            return appointmentSlotEntity.toDto();
        } else {
            throw new DoesNotExistException(appointmentSlotId);
        }
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteAppointmentSlotCascading(String appointmentSlotId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);

        AppointmentSlotEntity apptSlot = appointmentSlotDao.find(appointmentSlotId);
        if (null != apptSlot) {
            helper.deleteAppointmentsBySlotCascading(apptSlot.getId());
        } else {
            throw new DoesNotExistException(appointmentSlotId);
        }
        return status;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteAppointmentSlotsByWindowCascading(String appointmentWindowId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // This will also delete associated appointments since they would otherwise refer to non-existent slots
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);

        AppointmentWindowEntity apptWin = appointmentWindowDao.find(appointmentWindowId);
        if (null != apptWin) {
            helper.deleteAppointmentSlotsByWindowCascading(apptWin);
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

	@Override
	public StatusInfo changeAppointmentState(String appointmentId,
			String nextStateKey, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO KSENROLL-8703
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public StatusInfo changeAppointmentWindowState(String appointmentWindowId,
			String nextStateKey, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO KSENROLL-8703
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public StatusInfo changeAppointmentSlotState(String appointmentSlotId,
			String nextStateKey, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		// TODO KSENROLL-8703
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public StatusInfo deleteAppointmentWindow(String appointmentWindowId,
			ContextInfo contextInfo) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		throw new UnsupportedOperationException("not implemented");
	}
    
    
    
    
}
