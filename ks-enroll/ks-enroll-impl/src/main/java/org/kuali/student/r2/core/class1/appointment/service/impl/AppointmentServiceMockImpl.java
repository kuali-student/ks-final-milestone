/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.core.class1.appointment.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.common.util.UUIDHelper;
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
import org.kuali.student.r2.core.class1.atp.service.impl.MockHelper;

/**
 *
 * @author nwright
 */
public class AppointmentServiceMockImpl implements AppointmentService {

    private Map<String, AppointmentWindowInfo> windows = new LinkedHashMap<String, AppointmentWindowInfo>();
    private Map<String, AppointmentSlotInfo> slots = new LinkedHashMap<String, AppointmentSlotInfo>();
    private Map<String, AppointmentInfo> appointments = new LinkedHashMap<String, AppointmentInfo>();

    @Override
    public AppointmentInfo createAppointment(String personId,
            String appointmentSlotId,
            String appointmentTypeKey,
            AppointmentInfo appointmentInfo,
            ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException {

        if (!personId.equals(appointmentInfo.getPersonId())) {
            throw new InvalidParameterException("personId: " + personId + " <> " +
                    appointmentInfo.getPersonId());
        }
        if (!appointmentSlotId.equals(appointmentInfo.getSlotId())) {
            throw new InvalidParameterException("slotId: " + appointmentSlotId + " <> " +
                    appointmentInfo.getSlotId());
        }
        if (!appointmentTypeKey.equals(appointmentInfo.getTypeKey())) {
            throw new InvalidParameterException("TypeKey: " + appointmentTypeKey + " <> " +
                    appointmentInfo.getTypeKey());
        }
        AppointmentInfo copy = new AppointmentInfo(appointmentInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(new MockHelper().createMeta(contextInfo));
        new MockHelper().setIdOnAttributesThatDoNotHaveOne(copy.getAttributes());
        this.appointments.put(copy.getId(), copy);
        return new AppointmentInfo(copy);
    }

    @Override
    public AppointmentSlotInfo createAppointmentSlot(String appointmentWindowId,
            String appointmentSlotTypeKey,
            AppointmentSlotInfo appointmentSlotInfo,
            ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException,
            MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            ReadOnlyException {

        if (!appointmentWindowId.equals(appointmentSlotInfo.getAppointmentWindowId())) {
            throw new InvalidParameterException("appointmentWindowId: " +
                    appointmentWindowId + " <> " + appointmentSlotInfo.
                    getAppointmentWindowId());
        }
        if (!appointmentSlotTypeKey.equals(appointmentSlotInfo.getTypeKey())) {
            throw new InvalidParameterException("TypeKey: " + appointmentSlotTypeKey +
                    " <> " + appointmentSlotInfo.getTypeKey());
        }
        AppointmentSlotInfo copy = new AppointmentSlotInfo(appointmentSlotInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(new MockHelper().createMeta(contextInfo));
        new MockHelper().setIdOnAttributesThatDoNotHaveOne(copy.getAttributes());
        this.slots.put(copy.getId(), copy);
        return new AppointmentSlotInfo(copy);
    }

    @Override
    public AppointmentWindowInfo createAppointmentWindow(
            String appointmentWindowTypeKey,
            AppointmentWindowInfo appointmentWindowInfo,
            ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException,
            PermissionDeniedException, ReadOnlyException {
        if (!appointmentWindowTypeKey.equals(appointmentWindowInfo.getTypeKey())) {
            throw new InvalidParameterException("TypeKey: " + appointmentWindowTypeKey +
                    " <> " + appointmentWindowInfo.getTypeKey());
        }
        AppointmentWindowInfo copy = new AppointmentWindowInfo(appointmentWindowInfo);
        if (copy.getId() == null) {
            copy.setId(UUIDHelper.genStringUUID());
        }
        copy.setMeta(new MockHelper().createMeta(contextInfo));
        new MockHelper().setIdOnAttributesThatDoNotHaveOne(copy.getAttributes());
        this.windows.put(copy.getId(), copy);
        return new AppointmentWindowInfo(copy);
    }

    @Override
    public StatusInfo deleteAppointment(String appointmentId,
            ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        if (!this.appointments.containsKey(appointmentId)) {
            throw new DoesNotExistException(appointmentId);
        }
        this.appointments.remove(appointmentId);
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public StatusInfo deleteAppointmentSlotCascading(String appointmentSlotId,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        if (!this.slots.containsKey(appointmentSlotId)) {
            throw new DoesNotExistException(appointmentSlotId);
        }
//        List<AppointmentInfo> appts = this.getAppointmentsBySlot(appointmentSlotId, contextInfo);
//        if (!appts.isEmpty()) {
//            throw new DependentObjectsExistException(appointmentSlotId + " has " + appts.
//                    size() + " appointments");
//        }
        // Cascade the deletes by removing appointments
        deleteAppointmentsBySlot(appointmentSlotId, contextInfo);
        // Now remove the slot
        this.slots.remove(appointmentSlotId);
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public StatusInfo deleteAppointmentSlotsByWindowCascading(String appointmentWindowId,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        for (AppointmentSlotInfo info : this.slots.values()) {
            if (info.getAppointmentWindowId().equals(appointmentWindowId)) {
                this.deleteAppointmentSlotCascading(info.getId(), contextInfo);
            }
        }
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public StatusInfo deleteAppointmentWindowCascading(String appointmentWindowId,
            ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        if (!this.windows.containsKey(appointmentWindowId)) {
            throw new DoesNotExistException(appointmentWindowId);
        }

//        List<AppointmentSlotInfo> appts = this.getAppointmentSlotsByWindow(appointmentWindowId, contextInfo);
//        if (!appts.isEmpty()) {
//            throw new DependentObjectsExistException(appointmentWindowId + " has " +
//                    appts.size() + " appointment slots");
//        }
        // Cascade the deletes
        List<AppointmentSlotInfo> slotList = getAppointmentSlotsByWindow(appointmentWindowId, contextInfo);
        for (AppointmentSlotInfo slot: slotList) {
            deleteAppointmentSlotCascading(slot.getId(), contextInfo);
        }
        this.windows.remove(appointmentWindowId);
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public StatusInfo deleteAppointmentsBySlot(String appointmentSlotId,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // check slot id exists
        this.getAppointmentSlot(appointmentSlotId, contextInfo);
        List<AppointmentInfo> appts = new ArrayList<AppointmentInfo> (this.appointments.values());
        for (AppointmentInfo info : appts) {
            if (info.getSlotId().equals(appointmentSlotId)) {
                this.deleteAppointment(info.getId(), contextInfo);
            }
        }
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public StatusInfo deleteAppointmentsByWindow(String appointmentWindowId,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // check appointment window exists
        this.getAppointmentWindow(appointmentWindowId, contextInfo);
        for (AppointmentSlotInfo info : this.getAppointmentSlotsByWindow(appointmentWindowId, contextInfo)) {
            this.deleteAppointmentsBySlot(info.getId(), contextInfo);
        }
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    @Override
    public List<AppointmentSlotInfo> generateAppointmentSlotsByWindow(
            String appointmentWindowId, ContextInfo contextInfo) throws
            DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            ReadOnlyException {
        // Note: this should be implemented in a calculation layer
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public StatusInfo generateAppointmentsByWindow(String appointmentWindowId,
            String appointmentTypeKey, ContextInfo contextInfo) throws
            DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            ReadOnlyException {
        // Note: this should be implemented in a calculation layer
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public AppointmentInfo getAppointment(String appointmentId,
            ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        if (!this.appointments.containsKey(appointmentId)) {
            throw new DoesNotExistException(appointmentId);
        }
        return new AppointmentInfo(this.appointments.get(appointmentId));
    }

    @Override
    public List<String> getAppointmentIdsByPerson(String personId,
            ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (AppointmentInfo info : this.appointments.values()) {
            if (info.getPersonId().equals(personId)) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public List<String> getAppointmentIdsByType(String appointmentTypeKey,
            ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (AppointmentInfo info : this.appointments.values()) {
            if (info.getTypeKey().equals(appointmentTypeKey)) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public AppointmentSlotInfo getAppointmentSlot(String appointmentSlotId,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        if (!this.slots.containsKey(appointmentSlotId)) {
            throw new DoesNotExistException(appointmentSlotId);
        }
        return new AppointmentSlotInfo(this.slots.get(appointmentSlotId));
    }

    @Override
    public List<AppointmentSlotInfo> getAppointmentSlotsByIds(
            List<String> appointmentSlotIds, ContextInfo contextInfo) throws
            DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<AppointmentSlotInfo> list = new ArrayList<AppointmentSlotInfo>();
        for (String id : appointmentSlotIds) {
            list.add(this.getAppointmentSlot(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<AppointmentSlotInfo> getAppointmentSlotsByPersonAndPeriod(
            String personId, String periodMilestoneId, ContextInfo contextInfo) throws
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<AppointmentSlotInfo> list = new ArrayList<AppointmentSlotInfo>();
        for (String apptId : this.getAppointmentIdsByPerson(personId, contextInfo)) {
            AppointmentInfo appt;
            try {
                appt = this.getAppointment(apptId, contextInfo);
            } catch (DoesNotExistException ex) {
                throw new OperationFailedException("Unexpected", ex);
            }
            AppointmentSlotInfo slot;
            try {
                slot = this.getAppointmentSlot(appt.getSlotId(), contextInfo);
            } catch (DoesNotExistException ex) {
                throw new OperationFailedException("Unexpected", ex);
            }
            AppointmentWindowInfo wind;
            try {
                wind = this.getAppointmentWindow(slot.getAppointmentWindowId(), contextInfo);
            } catch (DoesNotExistException ex) {
                throw new OperationFailedException("Unexpected", ex);
            }
            if (wind.getPeriodMilestoneId().equals(periodMilestoneId)) {
                list.add(slot);
            }
        }
        return list;
    }

    @Override
    public List<AppointmentSlotInfo> getAppointmentSlotsByWindow(
            String appointmentWindowId, ContextInfo contextInfo) throws
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<AppointmentSlotInfo> list = new ArrayList<AppointmentSlotInfo>();
        for (AppointmentSlotInfo info : this.slots.values()) {
            if (info.getAppointmentWindowId().equals(appointmentWindowId)) {
                list.add(new AppointmentSlotInfo(info));
            }
        }
        return list;
    }

    @Override
    public AppointmentWindowInfo getAppointmentWindow(String appointmentWindowId,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        if (!this.windows.containsKey(appointmentWindowId)) {
            throw new DoesNotExistException(appointmentWindowId);
        }
        return new AppointmentWindowInfo(this.windows.get(appointmentWindowId));
    }

    @Override
    public List<String> getAppointmentWindowIdsByPopulation(String populationId,
            ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (AppointmentWindowInfo info : this.windows.values()) {
            if (info.getAssignedPopulationId().equals(populationId)) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public List<String> getAppointmentWindowIdsByType(
            String appointmentWindowTypeKey, ContextInfo contextInfo) throws
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<String> list = new ArrayList<String>();
        for (AppointmentWindowInfo info : this.windows.values()) {
            if (info.getTypeKey().equals(appointmentWindowTypeKey)) {
                list.add(info.getId());
            }
        }
        return list;
    }

    @Override
    public List<AppointmentWindowInfo> getAppointmentWindowsByIds(
            List<String> appointmentWindowIds, ContextInfo contextInfo) throws
            DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<AppointmentWindowInfo> list = new ArrayList<AppointmentWindowInfo>();
        for (String id : appointmentWindowIds) {
            list.add(this.getAppointmentWindow(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<AppointmentWindowInfo> getAppointmentWindowsByPeriod(
            String periodMilestoneId, ContextInfo contextInfo) throws
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<AppointmentWindowInfo> list = new ArrayList<AppointmentWindowInfo>();
        for (AppointmentWindowInfo info : this.windows.values()) {
            list.add(new AppointmentWindowInfo(info));
        }
        return list;
    }

    @Override
    public List<AppointmentInfo> getAppointmentsByIds(
            List<String> appointmentIds, ContextInfo contextInfo) throws
            DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<AppointmentInfo> list = new ArrayList<AppointmentInfo>();
        for (String id : appointmentIds) {
            list.add(this.getAppointment(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<AppointmentInfo> getAppointmentsByPersonAndSlot(String personId,
            String appointmentSlotId, ContextInfo contextInfo) throws
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<AppointmentInfo> list = new ArrayList<AppointmentInfo>();
        for (AppointmentInfo info : this.getAppointmentsBySlot(appointmentSlotId, contextInfo)) {
            if (info.getPersonId().equals(personId)) {
                list.add(info);
            }
        }
        return list;
    }

    @Override
    public List<AppointmentInfo> getAppointmentsBySlot(String appointmentSlotId,
            ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<AppointmentInfo> list = new ArrayList<AppointmentInfo>();
        for (AppointmentInfo info : this.appointments.values()) {
            if (info.getSlotId().equals(appointmentSlotId)) {
                list.add(new AppointmentInfo(info));
            }
        }
        return list;
    }

    @Override
    public List<String> searchForAppointmentIds(QueryByCriteria criteria,
            ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> searchForAppointmentSlotIds(QueryByCriteria criteria,
            ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<AppointmentSlotInfo> searchForAppointmentSlots(
            QueryByCriteria criteria, ContextInfo contextInfo) throws
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> searchForAppointmentWindowIds(QueryByCriteria criteria,
            ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<AppointmentWindowInfo> searchForAppointmentWindows(
            QueryByCriteria criteria, ContextInfo contextInfo) throws
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<AppointmentInfo> searchForAppointments(QueryByCriteria criteria,
            ContextInfo contextInfo) throws InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public AppointmentInfo updateAppointment(String appointmentId,
            AppointmentInfo appointmentInfo, ContextInfo contextInfo) throws
            DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            ReadOnlyException, VersionMismatchException {
        if (!appointmentId.equals(appointmentInfo.getId())) {
            throw new InvalidParameterException("appointmentSlotId: " + appointmentId +
                    " " + appointmentInfo.getId());
        }
        AppointmentInfo existing = this.appointments.get(appointmentId);
        if (existing == null) {
            throw new DoesNotExistException(appointmentId);
        }
        if (!appointmentInfo.getMeta().getVersionInd().equals(existing.getMeta().
                getVersionInd())) {
            throw new VersionMismatchException("Updated by " + existing.getMeta().
                    getUpdateId() + " on " + existing.getMeta().getUpdateId() +
                    " with version of " + existing.getMeta().getVersionInd());
        }
        AppointmentInfo info = new AppointmentInfo(appointmentInfo);
        info.setMeta(new MockHelper().updateMeta(existing.getMeta(), contextInfo));
        new MockHelper().setIdOnAttributesThatDoNotHaveOne(info.getAttributes());
        this.appointments.put(info.getId(), info);
        return new AppointmentInfo(info);
    }

    @Override
    public AppointmentSlotInfo updateAppointmentSlot(String appointmentSlotId,
            AppointmentSlotInfo appointmentSlotInfo, ContextInfo contextInfo) throws
            DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            ReadOnlyException, VersionMismatchException {
        if (!appointmentSlotId.equals(appointmentSlotInfo.getId())) {
            throw new InvalidParameterException("appointmentSlotId: " + appointmentSlotId +
                    " " + appointmentSlotInfo.getId());
        }
        AppointmentSlotInfo existing = this.slots.get(appointmentSlotId);
        if (existing == null) {
            throw new DoesNotExistException(appointmentSlotId);
        }
        if (!appointmentSlotInfo.getMeta().getVersionInd().equals(existing.getMeta().
                getVersionInd())) {
            throw new VersionMismatchException("Updated by " + existing.getMeta().
                    getUpdateId() + " on " + existing.getMeta().getUpdateId() +
                    " with version of " + existing.getMeta().getVersionInd());
        }

        AppointmentSlotInfo info = new AppointmentSlotInfo(appointmentSlotInfo);
        info.setMeta(new MockHelper().updateMeta(existing.getMeta(), contextInfo));
        new MockHelper().setIdOnAttributesThatDoNotHaveOne(info.getAttributes());
        this.slots.put(info.getId(), info);
        return new AppointmentSlotInfo(info);
    }

    @Override
    public AppointmentWindowInfo updateAppointmentWindow(
            String appointmentWindowId,
            AppointmentWindowInfo appointmentWindowInfo, ContextInfo contextInfo) throws
            DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException,
            ReadOnlyException, VersionMismatchException {
        if (!appointmentWindowId.equals(appointmentWindowInfo.getId())) {
            throw new InvalidParameterException("appointmentWindowId: " + appointmentWindowId +
                    " " + appointmentWindowInfo.getId());
        }
        AppointmentWindowInfo existing = this.windows.get(appointmentWindowId);
        if (existing == null) {
            throw new DoesNotExistException(appointmentWindowId);
        }
        if (!appointmentWindowInfo.getMeta().getVersionInd().equals(existing.getMeta().
                getVersionInd())) {
            throw new VersionMismatchException("Updated by " + existing.getMeta().
                    getUpdateId() + " on " + existing.getMeta().getUpdateId() +
                    " with version of " + existing.getMeta().getVersionInd());
        }

        AppointmentWindowInfo info = new AppointmentWindowInfo(appointmentWindowInfo);
        info.setMeta(new MockHelper().updateMeta(existing.getMeta(), contextInfo));
        new MockHelper().setIdOnAttributesThatDoNotHaveOne(info.getAttributes());
        this.windows.put(info.getId(), info);
        return new AppointmentWindowInfo(info);
    }

    @Override
    public List<ValidationResultInfo> validateAppointment(
            String validationTypeKey, String personId, String appointmentSlotId,
            String appointmentTypeKey, AppointmentInfo appointmentInfo,
            ContextInfo contextInfo) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ValidationResultInfo> validateAppointmentSlot(
            String validationTypeKey, String appointmentWindowId,
            String appointmentSlotTypeKey,
            AppointmentSlotInfo appointmentSlotInfo, ContextInfo contextInfo) throws
            DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ValidationResultInfo> validateAppointmentWindow(
            String validationTypeKey, String appointmentWindowTypeKey,
            AppointmentWindowInfo appointmentWindowInfo, ContextInfo contextInfo) throws
            DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
