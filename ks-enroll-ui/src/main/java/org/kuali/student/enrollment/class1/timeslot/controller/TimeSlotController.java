package org.kuali.student.enrollment.class1.timeslot.controller;

import org.apache.commons.lang.math.NumberUtils;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.common.uif.util.GrowlIcon;
import org.kuali.student.common.uif.util.KSUifUtils;
import org.kuali.student.enrollment.class1.timeslot.dto.TimeSlotWrapper;
import org.kuali.student.enrollment.class1.timeslot.form.TimeSlotForm;
import org.kuali.student.enrollment.class1.timeslot.service.TimeSlotViewHelperService;
import org.kuali.student.enrollment.class1.timeslot.util.TimeSlotConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.scheduling.util.SchedulingServiceUtil;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil.getTypeService;

/**
 * Controller for Manage Time Slots.
 */
@Controller
@RequestMapping(value = "/timeslot")
public class TimeSlotController extends UifControllerBase {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(TimeSlotController.class);

    private static final String MODEL_ATTRIBUTE_FORM = "KualiForm";

    private TimeSlotViewHelperService viewHelperService;

    private ContextInfo contextInfo;

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new TimeSlotForm();
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=start")
    public ModelAndView start( @ModelAttribute( MODEL_ATTRIBUTE_FORM ) UifFormBase form,
                               HttpServletRequest request, HttpServletResponse response) {

        super.start(form, request, response);
        return  getUIFModelAndView(form);
    }

    /**
   * Search for TimeSlots by type.
   */
    @RequestMapping(params = "methodToCall=show")
    public ModelAndView show(@ModelAttribute(MODEL_ATTRIBUTE_FORM) TimeSlotForm form)
            throws Exception, PermissionDeniedException, OperationFailedException {

        List<String> timeSlotTypes = form.getTermTypeSelections();
        form.getTypeNameSelections().clear();
        String namesUI = "";


        form.setEnableAddButton(!timeSlotTypes.isEmpty());

        if(timeSlotTypes.size() > 0) {
            // convert to type name for display
            int i = 0;
            for(String slotType : timeSlotTypes) {
                if(slotType != null) {
                    TypeInfo typeInfo = getTypeService().getType(slotType, contextInfo);
                    form.getTypeNameSelections().add(typeInfo.getName());
                    StringBuilder sb = new StringBuilder(namesUI);
                    if(i > 0 && i < timeSlotTypes.size()) {
                        sb = sb.append(",  ");
                    }
                    sb = sb.append(typeInfo.getName());
                    namesUI = sb.toString();
                    i++;
                }
            }
            form.setTypeNamesUI(namesUI);
        }
        form.getTimeSlotResults().clear();
        TimeSlotViewHelperService viewHelperService = getViewHelperService(form);

        List<TimeSlotWrapper> timeSlotWrapperList = viewHelperService.findTimeSlots(timeSlotTypes);
        for (TimeSlotWrapper wrapper : timeSlotWrapperList) {
            form.getTimeSlotResults().add(wrapper);
        }
        if (timeSlotWrapperList.size() > 0) {
            form.setTimeSlotsLoaded(true);
        }

        return getUIFModelAndView(form, TimeSlotConstants.TIME_SLOT_PAGE);
    }


    @RequestMapping(params = "methodToCall=createTimeSlot")
    public ModelAndView createTimeSlot(@ModelAttribute(MODEL_ATTRIBUTE_FORM) TimeSlotForm form) throws Exception{

        form.setEditInProcess(false);

        validateTimeSlot(form);
        if (GlobalVariables.getMessageMap().hasErrors()){
            return getUIFModelAndView(form, TimeSlotConstants.TIME_SLOT_PAGE);
        }

        boolean isUnique = getViewHelperService(form).isUniqueTimeSlot(form);

        if (!isUnique){
            String startTime = form.getAddOrEditStartTime() + " " + form.getAddOrEditStartTimeAmPm();
            String endTime = form.getAddOrEditEndTime() + " " + form.getAddOrEditEndTimeAmPm();
            String timeSlotTypeName = getTypeService().getType(form.getAddOrEditTermKey(),contextInfo).getName();
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, TimeSlotConstants.ApplicationResouceKeys.TIMESLOT_DUPLICATE_ERROR,timeSlotTypeName,form.getAddOrEditDays(),startTime,endTime);
            return getUIFModelAndView(form, TimeSlotConstants.TIME_SLOT_PAGE);
        }

        getViewHelperService(form).createTimeSlot(form);

        if (!GlobalVariables.getMessageMap().hasErrors()){
            KSUifUtils.addGrowlMessageIcon(GrowlIcon.SUCCESS,TimeSlotConstants.ApplicationResouceKeys.TIMESLOT_ADD_SUCCESS);
        }

        return getUIFModelAndView(form, TimeSlotConstants.TIME_SLOT_PAGE);
    }

    @RequestMapping(params = "methodToCall=editTimeSlot")
    public ModelAndView editTimeSlot(@ModelAttribute(MODEL_ATTRIBUTE_FORM) TimeSlotForm form) throws Exception{

        form.setEditInProcess(true);

        String selectedPathIndex = form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        TimeSlotWrapper tsWrapper = form.getTimeSlotResults().get(NumberUtils.toInt(selectedPathIndex));

        validateTimeSlot(form);
        if (GlobalVariables.getMessageMap().hasErrors()){
            return getUIFModelAndView(form, TimeSlotConstants.TIME_SLOT_PAGE);
        }

        boolean isUnique = getViewHelperService(form).isUniqueTimeSlot(form,tsWrapper.getTimeSlotInfo());

        if (!isUnique){
            String startTime = form.getAddOrEditStartTime() + " " + form.getAddOrEditStartTimeAmPm();
            String endTime = form.getAddOrEditEndTime() + " " + form.getAddOrEditEndTimeAmPm();
            String timeSlotTypeName = getTypeService().getType(form.getAddOrEditTermKey(),contextInfo).getName();
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, TimeSlotConstants.ApplicationResouceKeys.TIMESLOT_DUPLICATE_ERROR,timeSlotTypeName,form.getAddOrEditDays(),startTime,endTime);
            return getUIFModelAndView(form, TimeSlotConstants.TIME_SLOT_PAGE);
        }

        boolean isInUse = getViewHelperService(form).isTimeSlotInUse(tsWrapper.getTimeSlotInfo());

        if (isInUse){
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, TimeSlotConstants.ApplicationResouceKeys.TIMESLOT_IN_USE, tsWrapper.getTimeSlotInfo().getName());
            return getUIFModelAndView(form, TimeSlotConstants.TIME_SLOT_PAGE);
        }

        getViewHelperService(form).updateTimeSlot(form,tsWrapper);

        if (!GlobalVariables.getMessageMap().hasErrors()){
            KSUifUtils.addGrowlMessageIcon(GrowlIcon.SUCCESS,TimeSlotConstants.ApplicationResouceKeys.TIMESLOT_EDIT_SUCCESS);
        }

        form.setEditInProcess(false);
        return getUIFModelAndView(form, TimeSlotConstants.TIME_SLOT_PAGE);
    }

    private void validateTimeSlot(TimeSlotForm form){

        if (!SchedulingServiceUtil.isValidDays(form.getAddOrEditDays())){
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, "validation.enroll.ao.scheduleValidChars");
        }

        long startTime = 0;
        try{
            startTime = DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.parse(form.getAddOrEditStartTime() + " " + form.getAddOrEditStartTimeAmPm()).getTime();
        } catch (Exception e) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "Invalid Start time");
        }

        long endTime = 0;
        try{
            endTime = DateFormatters.HOUR_MINUTE_AM_PM_TIME_FORMATTER.parse(form.getAddOrEditEndTime() + " " + form.getAddOrEditEndTimeAmPm()).getTime();
        } catch (Exception e) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "Invalid End time");
        }

        if ((startTime != 0 && endTime != 0) && startTime > endTime){
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "Start time should be less than end time");
        }

    }

    private TimeSlotViewHelperService getViewHelperService(UifFormBase form) {
        if (viewHelperService == null) {
            if (form.getView().getViewHelperService() != null) {
                viewHelperService = (TimeSlotViewHelperService) form.getView().getViewHelperService();
            } else {
                viewHelperService = (TimeSlotViewHelperService) form.getPostedView().getViewHelperService();
            }
        }
        return viewHelperService;
    }

    @RequestMapping(params = "methodToCall=deleteTimeSlots")
    public ModelAndView deleteTimeSlots(@ModelAttribute(MODEL_ATTRIBUTE_FORM) TimeSlotForm form) throws Exception {

        getViewHelperService(form).deleteTimeSlots(form);

        return getUIFModelAndView(form, TimeSlotConstants.TIME_SLOT_PAGE);
    }
}
