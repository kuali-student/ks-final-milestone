package org.kuali.student.enrollment.class1.timeslot.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.common.uif.service.impl.KSViewHelperServiceImpl;
import org.kuali.student.common.uif.util.GrowlIcon;
import org.kuali.student.common.uif.util.KSUifUtils;
import org.kuali.student.common.util.KSCollectionUtils;
import org.kuali.student.enrollment.class1.timeslot.dto.TimeSlotWrapper;
import org.kuali.student.enrollment.class1.timeslot.form.TimeSlotForm;
import org.kuali.student.enrollment.class1.timeslot.service.TimeSlotViewHelperService;
import org.kuali.student.enrollment.class1.timeslot.util.TimeSlotConstants;
import org.kuali.student.enrollment.class1.util.WeekDaysDtoAndUIConversions;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.core.scheduling.util.SchedulingServiceUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * View helper for Manage Time Slots.
 */
public class TimeSlotViewHelperServiceImpl
        extends KSViewHelperServiceImpl implements TimeSlotViewHelperService {

    private ContextInfo contextInfo;
    private transient SchedulingService schedulingService;

    @Override
    public List<TimeSlotWrapper> findTimeSlots(List<String> timeSlotTypes)
            throws MissingParameterException, InvalidParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {

        List<TimeSlotWrapper> timeSlotWrappers = new ArrayList<TimeSlotWrapper>();

        for (String tsTypeKey : timeSlotTypes) {
            Date timeForDisplay;

            List<String> timeSlotIds = getSchedulingService().getTimeSlotIdsByType(tsTypeKey, getContextInfo());
            List<TimeSlotInfo> timeSlotInfos = getSchedulingService().getTimeSlotsByIds(timeSlotIds, getContextInfo());

            for (TimeSlotInfo timeSlotInfo : timeSlotInfos) {
                TimeSlotWrapper tsWrapper = new TimeSlotWrapper();
                tsWrapper.setTimeSlotInfo(timeSlotInfo);
                Long startTime = timeSlotInfo.getStartTime().getMilliSeconds();
                if (startTime != null) {
                    tsWrapper.setStartTimeDisplay(SchedulingServiceUtil.makeFormattedTimeFromMillis(startTime));
                }

                Long endTime = timeSlotInfo.getEndTime().getMilliSeconds();
                if (endTime != null) {
                    tsWrapper.setEndTimeDisplay(SchedulingServiceUtil.makeFormattedTimeFromMillis(endTime));
                }

                String daysUI = WeekDaysDtoAndUIConversions.buildDaysForUI(timeSlotInfo.getWeekdays());
                tsWrapper.setDaysDisplayName(daysUI);
                tsWrapper.setTypeKey(timeSlotInfo.getTypeKey());
                if (timeSlotInfo.getTypeKey() != null) {
                    TypeInfo typeInfo = getTypeService().getType(timeSlotInfo.getTypeKey(), contextInfo);
                    tsWrapper.setTypeName(typeInfo.getName());
                }

                // convert typeKey to displayable typeName

                timeSlotWrappers.add(tsWrapper);
            }
        }

        return timeSlotWrappers;
    }

    public void createTimeSlot(TimeSlotForm form) throws Exception {

        TimeSlotWrapper newTSWrapper = new TimeSlotWrapper();

        TimeSlotInfo newTSInfo = new TimeSlotInfo();

        buildTimeSlotInfo(form,newTSInfo);

        try{
            TimeSlotInfo createdTimeSlot = getSchedulingService().createTimeSlot(form.getAddOrEditTermKey(),newTSInfo, createContextInfo());
            newTSWrapper.setTimeSlotInfo(createdTimeSlot);
        } catch (DataValidationErrorException e){
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES, RiceKeyConstants.ERROR_CUSTOM, e.getMessage());
            return;
        }

        form.getTimeSlotResults().add(newTSWrapper);
        initializeTimeSlotWrapper(form,newTSWrapper);

    }

    public void updateTimeSlot(TimeSlotForm form,TimeSlotWrapper tsWrapper) throws Exception {

        buildTimeSlotInfo(form,tsWrapper.getTimeSlotInfo());

        try {

            TimeSlotInfo updatedTimeSlot = getSchedulingService().updateTimeSlot(tsWrapper.getTimeSlotInfo().getId(),tsWrapper.getTimeSlotInfo(),createContextInfo());

            if (form.getTermTypeSelections().contains(updatedTimeSlot.getTypeKey())){
                tsWrapper.setTimeSlotInfo(updatedTimeSlot);
                initializeTimeSlotWrapper(form,tsWrapper);
            } else {
                form.getTimeSlotResults().remove(tsWrapper);
            }

        } catch (DataValidationErrorException e){
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES, RiceKeyConstants.ERROR_CUSTOM, e.getMessage());
            return;
        }

    }

    @Override
    public void deleteTimeSlots(TimeSlotForm form) throws Exception {
        List<TimeSlotWrapper> timeSlotWrappers = form.getTimeSlotResults();
        List<TimeSlotWrapper> selectedTimeSlots = form.getSelectedTimeSlots();

        selectedTimeSlots.clear();
        List<TimeSlotWrapper> timeSlotsToDelete = new ArrayList<TimeSlotWrapper>();

        boolean deleteInfoAdded = false;
        for(TimeSlotWrapper tsWrapper : timeSlotWrappers) {
            if(tsWrapper.isEnableDeleteButton() && tsWrapper.getIsChecked()) {
                boolean isInUse = isTimeSlotInUse(tsWrapper.getTimeSlotInfo());
                if (!isInUse){
                    selectedTimeSlots.add(tsWrapper);
                    StatusInfo statusInfo = getSchedulingService().deleteTimeSlot(tsWrapper.getTimeSlotInfo().getId(), contextInfo);
                    timeSlotsToDelete.add(tsWrapper);
                }
                if (!deleteInfoAdded){
                    KSUifUtils.addGrowlMessageIcon(GrowlIcon.SUCCESS, TimeSlotConstants.ApplicationResouceKeys.TIMESLOT_TOOLBAR_DELETE);
                    deleteInfoAdded = true;
                }
                tsWrapper.setIsChecked(false);
            }
        }
        form.getTimeSlotResults().removeAll(timeSlotsToDelete);
    }

    public boolean isUniqueTimeSlot(TimeSlotForm form) throws Exception {
        return isUniqueTimeSlot(form,null);
    }

    public boolean isUniqueTimeSlot(TimeSlotForm form,TimeSlotInfo skipTS) throws Exception {

        List<Integer> days = WeekDaysDtoAndUIConversions.buildDaysForDTO(form.getAddOrEditDays());

        String startTimeString = form.getAddOrEditStartTime() + " " + form.getAddOrEditStartTimeAmPm();
        TimeOfDayInfo startTimeOfDayInfo = SchedulingServiceUtil.makeTimeOfDayInfoFromTimeString(startTimeString);

        String endTimeString = form.getAddOrEditEndTime() + " " + form.getAddOrEditEndTimeAmPm();
        TimeOfDayInfo endTimeOfDayInfo = SchedulingServiceUtil.makeTimeOfDayInfoFromTimeString(endTimeString);

        List<TimeSlotInfo> exisitingTS = getSchedulingService()
                .getTimeSlotsByDaysAndStartTimeAndEndTime(form.getAddOrEditTermKey(),days,startTimeOfDayInfo,endTimeOfDayInfo,createContextInfo());

        if (exisitingTS.size() == 1 && skipTS != null){
            TimeSlotInfo ts = KSCollectionUtils.getRequiredZeroElement(exisitingTS);
            if (StringUtils.equals(ts.getId(),skipTS.getId())){
                return true;
            } else {
                return false;
            }
        } else {
            return exisitingTS.isEmpty();
        }
    }

    public boolean isTimeSlotInUse(TimeSlotInfo ts) throws Exception {
        return !getSchedulingService().canUpdateTimeSlot(ts.getId(),createContextInfo());
    }

    protected void buildTimeSlotInfo(TimeSlotForm form,TimeSlotInfo tsInfo){

        tsInfo.setStateKey(SchedulingServiceConstants.TIME_SLOT_STATE_ACTIVE);
        List<Integer> days = WeekDaysDtoAndUIConversions.buildDaysForDTO(form.getAddOrEditDays());
        tsInfo.setWeekdays(days);

        String startTimeString = form.getAddOrEditStartTime() + " " + form.getAddOrEditStartTimeAmPm();
        tsInfo.setStartTime(SchedulingServiceUtil.makeTimeOfDayInfoFromTimeString(startTimeString));

        String endTimeString = form.getAddOrEditEndTime() + " " + form.getAddOrEditEndTimeAmPm();
        tsInfo.setEndTime(SchedulingServiceUtil.makeTimeOfDayInfoFromTimeString(endTimeString));

        tsInfo.setTypeKey(form.getAddOrEditTermKey());
    }

    protected void initializeTimeSlotWrapper(TimeSlotForm form,TimeSlotWrapper tsWrapper){
        String daysUI = WeekDaysDtoAndUIConversions.buildDaysForUI(tsWrapper.getTimeSlotInfo().getWeekdays());
        tsWrapper.setDaysDisplayName(daysUI);
        tsWrapper.setEnableDeleteButton(true);
        tsWrapper.setStartTimeDisplay(SchedulingServiceUtil.makeFormattedTimeFromMillis(tsWrapper.getTimeSlotInfo().getStartTime().getMilliSeconds()));
        tsWrapper.setEndTimeDisplay(SchedulingServiceUtil.makeFormattedTimeFromMillis(tsWrapper.getTimeSlotInfo().getEndTime().getMilliSeconds()));
        TypeInfo type = getTypeInfo(form.getAddOrEditTermKey());
        tsWrapper.setTypeName(type.getName());

        form.setAddOrEditDays("");
        form.setAddOrEditEndTime("");
        form.setAddOrEditEndTimeAmPm("");
        form.setAddOrEditStartTime("");
        form.setAddOrEditStartTimeAmPm("");
        form.setAddOrEditTermKey("");
    }

    private ContextInfo getContextInfo() {
        if (contextInfo == null) {
            contextInfo = ContextUtils.createDefaultContextInfo();
        }
        return contextInfo;
    }

    protected SchedulingService getSchedulingService() {
        if(schedulingService == null) {
            schedulingService = CourseOfferingResourceLoader.loadSchedulingService();
        }
        return schedulingService;
    }
}
