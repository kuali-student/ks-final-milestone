package org.kuali.student.enrollment.class1.timeslot.service;

import org.kuali.student.common.uif.service.KSViewHelperService;
import org.kuali.student.enrollment.class1.timeslot.dto.TimeSlotWrapper;
import org.kuali.student.enrollment.class1.timeslot.form.TimeSlotForm;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;

import java.util.List;

/**
 * View helper interface for Manage Time Slots.
 */
public interface TimeSlotViewHelperService extends KSViewHelperService {
    /**
     * Build a list of {@link TimeSlotWrapper} objects for the given time slot types.
     * @param timeSlotTypes A list of TimeSlot type keys.
     * @return A list of TimeSlotWrapper objects.
     */
    public abstract List<TimeSlotWrapper> findTimeSlots(List<String> timeSlotTypes) throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException;

    public void createTimeSlot(TimeSlotForm form) throws Exception;
    public void deleteTimeSlots(TimeSlotForm form) throws Exception;

    public boolean isUniqueTimeSlot(TimeSlotForm form) throws Exception;

    public boolean isUniqueTimeSlot(TimeSlotForm form,TimeSlotInfo skipTS) throws Exception;

    public void updateTimeSlot(TimeSlotForm form,TimeSlotWrapper tsWrapper) throws Exception;

    public boolean isTimeSlotInUse(TimeSlotInfo ts) throws Exception;

}