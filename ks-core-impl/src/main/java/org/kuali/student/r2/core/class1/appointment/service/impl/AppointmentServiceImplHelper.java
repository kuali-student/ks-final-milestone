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
 * Created by Charles on 4/14/12
 */
package org.kuali.student.r2.core.class1.appointment.service.impl;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.appointment.constants.AppointmentServiceConstants;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.core.appointment.dto.AppointmentInfo;
import org.kuali.student.r2.core.appointment.dto.AppointmentSlotInfo;
import org.kuali.student.r2.core.appointment.dto.AppointmentWindowInfo;
import org.kuali.student.r2.core.class1.appointment.dao.AppointmentDao;
import org.kuali.student.r2.core.class1.appointment.dao.AppointmentSlotDao;
import org.kuali.student.r2.core.class1.appointment.dao.AppointmentWindowDao;
import org.kuali.student.r2.core.class1.appointment.model.AppointmentEntity;
import org.kuali.student.r2.core.class1.appointment.model.AppointmentSlotEntity;
import org.kuali.student.r2.core.class1.appointment.model.AppointmentWindowEntity;
import org.kuali.student.r2.core.population.service.PopulationService;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class AppointmentServiceImplHelper {
    private static int MILLIS_IN_SECOND = 1000;
    private static int MILLIS_IN_MINUTE = MILLIS_IN_SECOND * 60;
    private static int MINUTES_IN_HOUR = 60;
    
    private AppointmentWindowDao appointmentWindowDao;
    private AppointmentSlotDao appointmentSlotDao;
    private AppointmentDao appointmentDao;
    private PopulationService populationService;

    public AppointmentServiceImplHelper() {
    }


    /*
    * This is pulled out so other methods can call this without the transactional behavior.
    */
    public AppointmentInfo createAppointmentNoTransact(String personId, String appointmentSlotId, String appointmentTypeKey, AppointmentInfo appointmentInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        AppointmentEntity  appointmentEntity = new AppointmentEntity(appointmentInfo);
      
        appointmentEntity.setEntityCreated(contextInfo);
        
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

    // ------------------------------ Slot creation ------------------------
    public List<AppointmentSlotInfo> createOneSlotPerWindow(AppointmentWindowEntity apptWin, ContextInfo contextInfo)
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
    // ------------------------------- delete public methods ----------------------------
    /**
     * Helper that is used both by deleteAppointmentWindow and deleteAppointmentSlotsByWindow
     * @param windowEntity An appointment window entity
     * @param shouldDeleteSlots true, if you want slots to also be deleted
     */
    public void deleteAppointmentsByWindow(AppointmentWindowEntity windowEntity, boolean shouldDeleteSlots) {
        List<AppointmentSlotEntity> slotList = _fetchSlotEntitiesByWindows(windowEntity.getId());
        if (slotList != null) {
            // Delete appointments, if any
            _deleteAppointmentsBySlotList_(slotList);
            // Delete slots, if any
            _deleteAppointmentSlots(slotList);
        }
        changeApptWinState(windowEntity, AppointmentServiceConstants.APPOINTMENT_WINDOW_STATE_DRAFT_KEY);
    }

    public void deleteAppointmentSlotsByWindowCascading(AppointmentWindowEntity windowEntity) {
        deleteAppointmentsByWindow(windowEntity, true); // also, delete slots
    }

    /**
     * Deletes appointment windows, slots, and appointments.
     * @param windowEntity The window (and its dependent parts) to be deleted.
     */
    public void deleteAppointmentWindowCascading(AppointmentWindowEntity windowEntity) {
        deleteAppointmentSlotsByWindowCascading(windowEntity);
        appointmentWindowDao.remove(windowEntity);
        // No need to update the state since the window is gone
    }

    public int deleteAppointmentsBySlotCascading(String slotId) {
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
     * Changes the state of the appointment window (to draft or assigned)
     * Precondition: apptWinId and stateKey are valid
     * @param windowEntity The appointment window ID in the DB
     * @param stateKey Either AppointmentServiceConstants.APPOINTMENT_WINDOW_STATE_DRAFT_KEY or
     *                 AppointmentServiceConstants.APPOINTMENT_WINDOW_STATE_ASSIGNED_KEY
     */
    public void changeApptWinState(AppointmentWindowEntity windowEntity, String stateKey) {
        windowEntity.setApptWindowState(stateKey);
        appointmentWindowDao.update(windowEntity);
    }

    /**
     * Creates a list of AppointmentSlotInfo (but does not persist in DB).
     * @param apptWinInfo Appointment Window
     * @param contextInfo Context info
     * @return Object with 3 elements.  Object[0] is a list of AppointmentSlotInfo objects, Object[1] is number allocated
     * (only for max allocation, otherwise 0 for uniform), and Object[2] is number unallocated (again, only for max
     * allocation, otherwise 0 for uniform)
     */
    public Object[] createMultiSlots(AppointmentWindowInfo apptWinInfo, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, DoesNotExistException,
                   OperationFailedException, PermissionDeniedException {
        String atpDurationTypeKey = apptWinInfo.getSlotRule().getSlotStartInterval().getAtpDurationTypeKey();
        if (!AtpServiceConstants.DURATION_MINUTES_TYPE_KEY.equals(atpDurationTypeKey)) {
            // Currently, only support minutes
            throw new InvalidParameterException("Only kuali.atp.duration.Minutes is implemented");
        }
        String apptWinTypeKey = apptWinInfo.getTypeKey();
        if (! (AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_SLOTTED_MAX_KEY.equals(apptWinTypeKey) ||
              (AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_SLOTTED_UNIFORM_KEY.equals(apptWinTypeKey)))) {
            throw new InvalidParameterException("Supported types: MAX and UNIFORM");
        }
        // Get parameters ready
        Calendar startDate = _convertDateToCalendar(apptWinInfo.getStartDate());
        Calendar endDate = _convertDateToCalendar(apptWinInfo.getEndDate());
        int maxPerSlot = apptWinInfo.getMaxAppointmentsPerSlot() == null ? 0 : apptWinInfo.getMaxAppointmentsPerSlot();
        int totalStudents = -1;
        if (AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_SLOTTED_MAX_KEY.equals(apptWinInfo.getTypeKey())) {
            totalStudents = _computeTotalStudents(apptWinInfo, contextInfo);
        }

        int startTimeInMinutes = _computeMinuteOffsetSinceMidnight(apptWinInfo.getSlotRule().getStartTimeOfDay().getMilliSeconds());
        int endTimeInMinutes =  _computeMinuteOffsetSinceMidnight(apptWinInfo.getSlotRule().getEndTimeOfDay().getMilliSeconds());
        // TODO: Current implementation only supports minutes.  Except thrown at start of this method for any other
        int startIntervalInMinutes = apptWinInfo.getSlotRule().getSlotStartInterval().getTimeQuantity();
        int durationInMinutes = -1; // TODO: Currently, unsupported
        List<Integer> weekdays = apptWinInfo.getSlotRule().getWeekdays();
        
        // The big call!  Compute the slot times
        Object[] result = null;
        if (AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_SLOTTED_MAX_KEY.equals(apptWinTypeKey)) {
            result = _computeSlotTimesMaxAllocation(startDate, endDate, totalStudents, maxPerSlot, startTimeInMinutes,
                                                    endTimeInMinutes, startIntervalInMinutes, 
                                                    durationInMinutes, weekdays);
        } else if (AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_SLOTTED_UNIFORM_KEY.equals(apptWinTypeKey)) {
            result = _computeSlotTimesUniformAllocation(startDate, endDate, totalStudents, startTimeInMinutes,
                                                        endTimeInMinutes, startIntervalInMinutes, durationInMinutes, 
                                                        weekdays);
        }
        List<AppointmentSlotInfo> slotList = new ArrayList<AppointmentSlotInfo>();
        _computeApptSlotList(slotList, (List<Calendar>) result[0]);
        Object[] result2 = {slotList, result[1], result[2]};
        return result2;
    }

    public List<AppointmentSlotInfo> createAppointmentSlots(List<AppointmentSlotInfo> slotInfoList, String apptSlotTypeKey,
                                                              String apptWinId, ContextInfo contextInfo)
            throws InvalidParameterException, DataValidationErrorException, MissingParameterException,
                   DoesNotExistException, ReadOnlyException, PermissionDeniedException, OperationFailedException {
        List<AppointmentSlotInfo> newSlotInfoList = new ArrayList<AppointmentSlotInfo>();
        for (AppointmentSlotInfo slotInfo: slotInfoList) {
            slotInfo.setAppointmentWindowId(apptWinId);
            AppointmentSlotInfo newSlotInfo = createAppointmentSlot(apptWinId, apptSlotTypeKey, slotInfo, contextInfo);
            newSlotInfoList.add(newSlotInfo);
        }
        return newSlotInfoList;
    }

    public AppointmentSlotInfo createAppointmentSlot(String appointmentWindowId, String appointmentSlotTypeKey,
                                                     AppointmentSlotInfo appointmentSlotInfo, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        AppointmentSlotEntity appointmentSlotEntity = new AppointmentSlotEntity(appointmentSlotTypeKey, appointmentSlotInfo);
        
        appointmentSlotEntity.setEntityCreated(contextInfo);
        
        // Need to manually set the entity since appointmentSlotInfo only has an id for its corresponding AppointmentWindow
        AppointmentWindowEntity windowEntity = appointmentWindowDao.find(appointmentWindowId);
        if (null == windowEntity) {
            throw new DoesNotExistException(appointmentWindowId);
        }
        appointmentSlotEntity.setApptWinEntity(windowEntity); // This completes the initialization of appointmentSlotEntity
        appointmentSlotDao.persist(appointmentSlotEntity);
        return appointmentSlotEntity.toDto();
    }

    public long countAppointmentsByApptWinId(String apptWinId) {
        long result = appointmentDao.countAppointmentsByWindowId(apptWinId);
        return result;
    }

    public List<AppointmentInfo> getAppointmentsBySlotNoTransact(String appointmentSlotId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<AppointmentEntity> apptEntityList = appointmentDao.getAppointmentsBySlotId(appointmentSlotId);
        List<AppointmentInfo> apptInfoList = new ArrayList<AppointmentInfo>();
        for (AppointmentEntity entity: apptEntityList) {
            AppointmentInfo info = entity.toDto();
            apptInfoList.add(info);
        }

        return apptInfoList;
    }

    // maxSizePerSlot is likely to be null except in the max case, so leave it as an object here
    public void generateAppointments(String apptWinId,
                                     String appointmentTypeKey, Integer maxSizePerSlot,
                                     List<String> students, List<AppointmentSlotInfo> slotInfoList,
                                     ContextInfo contextInfo,
                                     StatusInfo statusInfo) throws OperationFailedException, InvalidParameterException, DataValidationErrorException, MissingParameterException, DoesNotExistException, ReadOnlyException, PermissionDeniedException {
        if (slotInfoList.isEmpty()) {
            throw new OperationFailedException("No slots");
        }
        // May want to adjust message in the status info
        if (appointmentTypeKey.equals(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_ONE_SLOT_KEY)) {
            _generateAppointmentsOneSlotCase(students, slotInfoList, statusInfo, contextInfo);
        } else if (appointmentTypeKey.equals(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_SLOTTED_UNIFORM_KEY)) {
            _generateAppointmentsUniformCase(students, slotInfoList, statusInfo, contextInfo);
        } else if (appointmentTypeKey.equals(AppointmentServiceConstants.APPOINTMENT_WINDOW_TYPE_SLOTTED_MAX_KEY)) {
            _generateAppointmentsMaxCase(apptWinId, students, maxSizePerSlot, slotInfoList, statusInfo, contextInfo);
        } else {
            throw new OperationFailedException("Manual window allocation not supported");
        }
    }
    // =====================================================================================
    // ------------------------------- PRIVATE METHODS -------------------------------------
    // =====================================================================================
    
    // ---------------------------- Slot generation private
    /**
     *
     * @param startDate When appointments can start (including hour/minute)
     * @param endDate When appointments end (an appointment can be scheduled exactly at that time)
     * @param totalStudents How many students we need slots for.  If -1, we won't care
     * @param maxPerSlot How many students can be allocated per slot
     * @param startTimeInMinutes Offset from midnight in minutes for start of business day
     *                           (doesn't account for daylight saving transitions)
     * @param endTimeInMinutes Offset from midnight in minutes for start of business day
     *                         (same caveat)
     * @param startIntervalInMinutes Difference in minutes
     * @param durationInMinutes How long an appointment lasts in minutes (currently ignored)
     * @param weekdays List of integers representing which days of the week business hours occur
     *                 (1 - Sunday, 2 - Monday, ..., 7 - Saturday).  Must be non-empty, non-null
     * @return Object[0] contains List<Calendar> for slot times in chronological order.  Object[1]
     *         contains Integer for number of students allocated, Object[2] contains an Integer for
     *         number of students unallocated.  Object[1] + Object[2] adds to totalStudents.
     */
    private Object [] _computeSlotTimesMaxAllocation(Calendar startDate, Calendar endDate, int totalStudents,
                                                     int maxPerSlot, int startTimeInMinutes, int endTimeInMinutes,
                                                     int startIntervalInMinutes, int durationInMinutes,
                                                     List<Integer> weekdays) throws InvalidParameterException {
        if (totalStudents < 0) {
            throw new InvalidParameterException("totalStudents should be positive");
        }
        List<Calendar> slotTimes = new ArrayList<Calendar>();
        if (weekdays.isEmpty()) {
            Object[] result = {slotTimes, new Integer(0), new Integer(totalStudents)};
            return result;
        }
        Calendar date = _makeCopy(startDate);
        _setTime(date, startTimeInMinutes); // Set to start of day
        int numAllocated = 0; // Number of students accounted for by slots
        while (true) {
            // Exit if we're past the end date
            if (endDate != null && date.after(endDate)) {
                break;
            }
            // Exit loop if we're allocated enough (but make sure we're checking for that)
            if (numAllocated >= totalStudents) {
                break;
            }

            // -------------------- Now allocate slots for current day --------------------
            Calendar endOfToday = _makeCopy(date);
            _setTime(endOfToday, endTimeInMinutes);
            while (true) { // Iterate over slots for today
                // Make sure first slot is >= startDate
                _setTimePastStartDate(date, startDate, startIntervalInMinutes);
                if (date.after(endOfToday) || date.after(endDate)) {
                    break; // quit out of if we've reached the end of the day or final end date
                }
                slotTimes.add(date); // Add next slot time
                if (maxPerSlot > 0) {
                    numAllocated += maxPerSlot;
                }
                if (numAllocated >= totalStudents) {
                    break; // We have enough slots to account for all students, so quit
                }
                // Create next date
                date = _makeCopy(date, startIntervalInMinutes);
            }
            // Searches for the start of the next available business day
            date = _getNextValidDate(date, startTimeInMinutes, weekdays, endDate);
            if (date == null) {
                break;
            }
        }
        // We haven't really allocated, just computed how many we *would* allocate.
        if (numAllocated > totalStudents) {
            numAllocated = totalStudents;
        }
        int unallocated = totalStudents - numAllocated;
        Object[] result = {slotTimes, new Integer(numAllocated), new Integer(unallocated)};
        return result;
    }

    private Object [] _computeSlotTimesUniformAllocation(Calendar startDate, Calendar endDate, int totalStudents,
                                                         int startTimeInMinutes, int endTimeInMinutes,
                                                         int startIntervalInMinutes, int durationInMinutes,
                                                         List<Integer> weekdays) {
        List<Calendar> slotTimes = new ArrayList<Calendar>();
        if (weekdays.isEmpty()) {
            Object[] result = {slotTimes, new Integer(0), new Integer(totalStudents)};
            return result;
        }
        Calendar date = _makeCopy(startDate);
        _setTime(date, startTimeInMinutes); // Set to start of day
        while (true) {
            // Exit if we're past the end date
            if (endDate != null && date.after(endDate)) {
                break;
            }
            // -------------------- Now allocate slots for current day --------------------
            Calendar endOfToday = _makeCopy(date);
            _setTime(endOfToday, endTimeInMinutes);
            while (true) { // Iterate over slots for today
                // Make sure first slot is >= startDate
                _setTimePastStartDate(date, startDate, startIntervalInMinutes);
                if (date.after(endOfToday) || date.after(endDate)) {
                    break; // quit out of if we've reached the end of the day or final end date
                }
                slotTimes.add(date); // Add next slot time
                // Create next date
                date = _makeCopy(date, startIntervalInMinutes);
            }
            // Searches for the start of the next available business day
            date = _getNextValidDate(date, startTimeInMinutes, weekdays, endDate);
            if (date == null) {
                break;
            }
        }
        // We haven't really allocated, just computed how many we *would* allocate.
        Object[] result = {slotTimes, new Integer(totalStudents), new Integer(0)};
        return result;
    }

    private void _computeApptSlotList(List<AppointmentSlotInfo> slotList, List<Calendar> slotTimes) {
        for (Calendar date: slotTimes) {    
            AppointmentSlotInfo slotInfo = _createApptSlotFromDate(date);
            slotList.add(slotInfo);
        }
    }
    
    private AppointmentSlotInfo _createApptSlotFromDate(Calendar day) {
        AppointmentSlotInfo slotInfo = new AppointmentSlotInfo();
        slotInfo.setStateKey(AppointmentServiceConstants.APPOINTMENT_SLOTS_STATE_ACTIVE_KEY);
        slotInfo.setTypeKey(AppointmentServiceConstants.APPOINTMENT_SLOT_TYPE_OPEN_KEY); // TODO: verify this is valid
        Date date = day.getTime();
        slotInfo.setStartDate(date);
        _setEndDateToDropDate(slotInfo);
        return slotInfo;
    }
    
    private int _computeTotalStudents(AppointmentWindowInfo apptWinInfo, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        List<String> ids = populationService.getMembersAsOfDate(apptWinInfo.getAssignedPopulationId(), new Date(), contextInfo);
        if (ids != null) {
            return ids.size();
        } else {
            return 0;  // TODO: Perhaps do something else?  Throw an exception?
        }
    }


    // ---------------------------------- Calendar private methods ----------------------------------
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

    private Calendar _convertDateToCalendar(Date date) {
        if (date == null) {
            return null;
        }
        Calendar result = Calendar.getInstance();
        result.setTime(date);
        return result;
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
    
    private Calendar _makeCopy(Calendar date) {
        Calendar copy = Calendar.getInstance();
        copy.setTimeInMillis(date.getTimeInMillis());
        return copy;
    }

    private Calendar _makeCopy(Calendar date, int offsetInMinutes) {
        Calendar copy = Calendar.getInstance();
        copy.setTimeInMillis(date.getTimeInMillis());
        copy.add(Calendar.MINUTE, offsetInMinutes);
        return copy;
    }
    
    private void _setTime(Calendar date, int minutesSinceMidnight) {
        int hours = minutesSinceMidnight / MINUTES_IN_HOUR;
        int minutes = minutesSinceMidnight % MINUTES_IN_HOUR;
        date.set(Calendar.HOUR_OF_DAY, hours);
        date.set(Calendar.MINUTE, minutes);
    }
    
    private Calendar _getNextValidDate(Calendar date, int startOfDayInMinutes, List<Integer> weekdays,
                                       Calendar endDate) {
        Calendar copy = _makeCopy(date);
        _setTime(copy, startOfDayInMinutes);
        copy.add(Calendar.DAY_OF_MONTH, 1); // Add a day
        int loopCount = 0;
        while (true) {
            if (endDate != null && copy.after(endDate)) {
                return null;
            }
            if (_isDayInWeekdays(copy, weekdays)) {
                return copy;
            }
            copy.add(Calendar.DAY_OF_MONTH, 1); // Add a day
            loopCount++;
//            if (loopCount > 10) {
                // TODO: Figure this out (should it be taken care of)
//            }
        }
    }
    
    private boolean _sameDayMonthYear(Calendar date, Calendar startDate) {
        return date.get(Calendar.DAY_OF_MONTH) == startDate.get(Calendar.DAY_OF_MONTH) &&
                date.get(Calendar.MONTH) == startDate.get(Calendar.MONTH) &&
                date.get(Calendar.YEAR) == startDate.get(Calendar.YEAR);
    }
    
    private void _setTimePastStartDate(Calendar date, Calendar startDate, int startIntervalInMinutes) {
        if (_sameDayMonthYear(date, startDate)) {
            while (date.before(startDate)) {
                date.add(Calendar.MINUTE, startIntervalInMinutes);
            }
        }
    }
    // ---------------------- DELETE Private Methods --------------------------------
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

    /**
     * Deletes all appointments associated with this slot list.
     * Precondition: Don't call directly by public methods--doesn't change state of window
     * @param slotList Assume not-null
     */
    private void _deleteAppointmentsBySlotList_(List<AppointmentSlotEntity> slotList) {
        for (AppointmentSlotEntity slotEntity: slotList) {
            deleteAppointmentsBySlotCascading(slotEntity.getId());
        }
    }
    // ----------------------- Generate appts private methods ---------------------

    private void _generateAppointmentsOneSlotCase(List<String> studentIds, List<AppointmentSlotInfo> slotInfoList,
                                                  StatusInfo statusInfo, ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException, DoesNotExistException,
                   PermissionDeniedException, OperationFailedException, DataValidationErrorException, ReadOnlyException {
        String slotId = slotInfoList.get(0).getId();  // Only one slot in the one slot case
        for (String studentId: studentIds) {
            AppointmentInfo apptInfo = _createAppointmentInfo(studentId, slotId);
            createAppointmentNoTransact(studentId, slotId, apptInfo.getTypeKey(), apptInfo, contextInfo);
        }
        // Set number of students allocated
        statusInfo.setMessage("" + studentIds.size());
    }

    private AppointmentInfo _createAppointmentInfo(String studentId, String slotId) {
        AppointmentInfo apptInfo = new AppointmentInfo();
        apptInfo.setPersonId(studentId);
        apptInfo.setSlotId(slotId);
        apptInfo.setTypeKey(AppointmentServiceConstants.APPOINTMENT_TYPE_REGISTRATION);
        apptInfo.setStateKey(AppointmentServiceConstants.APPOINTMENT_STATE_ACTIVE_KEY);
        return apptInfo;
    }

    // Used by both max/uniform allocation
    private void _allocateStudentsToSlotsCommon(int studentsPerSlot, List<AppointmentSlotInfo> slotInfoList,
                                                List<String> studentIds, ContextInfo contextInfo)
            throws InvalidParameterException, DataValidationErrorException, MissingParameterException,
            DoesNotExistException, ReadOnlyException, PermissionDeniedException, OperationFailedException {
        int count = 0;
        int numStudents = studentIds.size();

        boolean done = false;

        for (AppointmentSlotInfo slotInfo: slotInfoList) {
            List<String> sublist = null;

            // Grab sublist of students
            int endIndex = (count + 1) * studentsPerSlot;
            if (endIndex > numStudents) { // adjust end if we go past number of students
                endIndex = numStudents;
                done = true;
            }
            sublist = studentIds.subList(count * studentsPerSlot, endIndex);

            count++;
            // Make the assignments
            for (String studentId: sublist) {
                String slotId = slotInfo.getId();
                AppointmentInfo apptInfo = _createAppointmentInfo(studentId, slotId);
                createAppointmentNoTransact(studentId, slotId, apptInfo.getTypeKey(), apptInfo, contextInfo);
            }
            if (done) {
                break; // Some slots may be unassigned in the max allocation
            }
        }
    }
    /*
     * Precondition: At least one slot in the slotInfoList
     */
    private void _generateAppointmentsUniformCase(List<String> studentIds, List<AppointmentSlotInfo> slotInfoList,
                                                  StatusInfo statusInfo, ContextInfo contextInfo) throws InvalidParameterException,
            DataValidationErrorException, MissingParameterException, DoesNotExistException, ReadOnlyException,
            PermissionDeniedException, OperationFailedException {
        int numSlots = slotInfoList.size();
        int numStudents = studentIds.size();
        int studentsPerSlot = numStudents / numSlots;
        if (numStudents % numSlots != 0) {
            studentsPerSlot++; // Add 1 if numSlots does not evenly divide numStudents
        }

        _allocateStudentsToSlotsCommon(studentsPerSlot, slotInfoList, studentIds, contextInfo);
        statusInfo.setMessage("" + studentIds.size()); // Set to number of appointments
    }

    // The slot generation will take care of the end dates
    private void _generateAppointmentsMaxCase(String apptWinId,
                                              List<String> studentIds, int maxSizePerSlot,
                                              List<AppointmentSlotInfo> slotInfoList,
                                              StatusInfo statusInfo, ContextInfo contextInfo) throws InvalidParameterException,
            DataValidationErrorException, MissingParameterException, DoesNotExistException, ReadOnlyException,
            PermissionDeniedException, OperationFailedException {
        int numSlots = slotInfoList.size();
        int numStudents = studentIds.size();
        // Do we have enough slots for all the students?
        if (numStudents > numSlots * maxSizePerSlot) {
            // No, so quit without doing any more
            statusInfo.setSuccess(false);
            statusInfo.setMessage("Not enough room for ["+ numStudents +"] appointments. numSlots[" + numSlots+ "] * maxPerSlot[" +maxSizePerSlot + "] = "
                    + "["+ (numSlots * maxSizePerSlot)+ "] available appointments. Please increase available slots or max per slot.");
            return; // And we're outta here
        }
        // Enough slots, so start assigning
        _allocateStudentsToSlotsCommon(maxSizePerSlot, slotInfoList, studentIds, contextInfo);
        // Count how many we assigned
        long numAppts = countAppointmentsByApptWinId(apptWinId);
        statusInfo.setMessage("" + numAppts); // Set to number of appointments
    }


    // ----------------------- MISC private methods -------------------------------
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


    public void setAppointmentWindowDao(AppointmentWindowDao appointmentWindowDao) {
        this.appointmentWindowDao = appointmentWindowDao;
    }

    public void setAppointmentSlotDao(AppointmentSlotDao appointmentSlotDao) {
        this.appointmentSlotDao = appointmentSlotDao;
    }

    public void setAppointmentDao(AppointmentDao appointmentDao) {
        this.appointmentDao = appointmentDao;
    }

    public void setPopulationService(PopulationService populationService) {
        this.populationService = populationService;
    }

}
