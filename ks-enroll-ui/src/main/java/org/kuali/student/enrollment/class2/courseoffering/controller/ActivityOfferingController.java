package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.MaintenanceDocumentController;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ScheduleWrapper;
import org.kuali.student.enrollment.class2.courseoffering.form.ActivityOfferingForm;
import org.kuali.student.enrollment.class2.courseoffering.service.ActivityOfferingMaintainable;
import org.kuali.student.enrollment.uif.util.KSControllerHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping(value = "/activityOffering")
public class ActivityOfferingController extends MaintenanceDocumentController {

    @Override
    protected MaintenanceDocumentForm createInitialForm(HttpServletRequest request) {
        return new ActivityOfferingForm();
    }

    @RequestMapping(params = "methodToCall=reviseSchedule")
    public ModelAndView reviseSchedule(@ModelAttribute("KualiForm") ActivityOfferingForm form) throws Exception {

        ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
        ActivityOfferingMaintainable viewHelper = (ActivityOfferingMaintainable) KSControllerHelper.getViewHelperService(form);
        viewHelper.prepareForScheduleRevise(activityOfferingWrapper);
        form.setDeliveryLogisiticsAddButtonText("Add");

        return getUIFModelAndView(form,ActivityOfferingForm.SCHEDULE_PAGE);
    }

    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=editScheduleComponent")
    public ModelAndView editScheduleComponent(@ModelAttribute("KualiForm") ActivityOfferingForm form) throws Exception {

        ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
        ScheduleWrapper scheduleWrapper = (ScheduleWrapper)getSelectedObject(form);

        ScheduleWrapper newSchedule = new ScheduleWrapper();
        newSchedule.copyForEditing(scheduleWrapper);
        activityOfferingWrapper.setNewScheduleRequest(newSchedule);
        activityOfferingWrapper.getRevisedScheduleRequestComponents().remove(scheduleWrapper);
        form.setDeliveryLogisiticsAddButtonText("Update Request");
        form.setScheduleEditInProgress(true);

        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=addScheduleComponent")
    public ModelAndView addScheduleComponent(@ModelAttribute("KualiForm") ActivityOfferingForm form) throws Exception {

        ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
        ActivityOfferingMaintainable viewHelper = (ActivityOfferingMaintainable) KSControllerHelper.getViewHelperService(form);
        boolean success = viewHelper.addScheduleRequestComponent(form);

        if (form.isSchedulePage() && success){
            form.setDeliveryLogisiticsAddButtonText("Add");
            form.setScheduleEditInProgress(false);
            activityOfferingWrapper.setSchedulesRevised(true);
        }
        
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=saveRevisedSchedules")
    public ModelAndView saveRevisedSchedules(@ModelAttribute("KualiForm") ActivityOfferingForm form) throws Exception {

        ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();

        if (form.isScheduleEditInProgress()){
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "Editing a schedule request in progress. Please update it first before processing");
            return getUIFModelAndView(form,ActivityOfferingForm.SCHEDULE_PAGE);
        }

        activityOfferingWrapper.getRequestedScheduleComponents().clear();

        for (ScheduleWrapper scheduleWrapper : activityOfferingWrapper.getRevisedScheduleRequestComponents()){
            activityOfferingWrapper.getRequestedScheduleComponents().add(scheduleWrapper);
        }

        activityOfferingWrapper.setNewScheduleRequest(new ScheduleWrapper());
        activityOfferingWrapper.getRevisedScheduleRequestComponents().clear();

        return getUIFModelAndView(form,ActivityOfferingForm.MAIN_PAGE);
    }

    @RequestMapping(params = "methodToCall=processRevisedSchedules")
    public ModelAndView processRevisedSchedules(@ModelAttribute("KualiForm") ActivityOfferingForm form) throws Exception {

        ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();

        if (form.isScheduleEditInProgress()){
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "Editing a schedule request in progress. Please update it first before processing");
            return getUIFModelAndView(form,ActivityOfferingForm.SCHEDULE_PAGE);
        }

        activityOfferingWrapper.getRequestedScheduleComponents().clear();

        for (ScheduleWrapper scheduleWrapper : activityOfferingWrapper.getRevisedScheduleRequestComponents()){
            activityOfferingWrapper.getRequestedScheduleComponents().add(scheduleWrapper);
        }

        ActivityOfferingMaintainable viewHelper = (ActivityOfferingMaintainable) KSControllerHelper.getViewHelperService(form);
        viewHelper.processRevisedSchedules(activityOfferingWrapper);

        activityOfferingWrapper.setNewScheduleRequest(new ScheduleWrapper());
        activityOfferingWrapper.getRevisedScheduleRequestComponents().clear();
        activityOfferingWrapper.setSchedulesRevised(false);

        GlobalVariables.getMessageMap().putInfo(KRADConstants.GLOBAL_INFO, RiceKeyConstants.ERROR_CUSTOM, "Schedule has been successfully processed");

        return getUIFModelAndView(form,ActivityOfferingForm.MAIN_PAGE);
    }

    @RequestMapping(params = "methodToCall=cancelScheduleRevise")
    public ModelAndView cancelScheduleRevise(@ModelAttribute("KualiForm") ActivityOfferingForm form) throws Exception {

        ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();

        activityOfferingWrapper.getRevisedScheduleRequestComponents().clear();
        activityOfferingWrapper.setSchedulesRevised(false);

        return getUIFModelAndView(form,ActivityOfferingForm.MAIN_PAGE);
    }

    /*@RequestMapping(method = RequestMethod.POST, params = "methodToCall=deleteLine")
    public ModelAndView deleteLine(@ModelAttribute("KualiForm") ActivityOfferingForm form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
        String selectedCollectionPath = form.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);

        if (StringUtils.endsWith(selectedCollectionPath,"revisedScheduleRequestComponents")){
            if (activityOfferingWrapper.getRequestedScheduleComponents().size() == 1){
                String dialogName = "deleteScheduleConfirmDialog";

                if (!hasDialogBeenAnswered(dialogName, form)) {
                    return showDialog(dialogName, form, request, response);
                }

                boolean dialogAnswer = getBooleanDialogResponse(dialogName, form, request, response);
                form.getDialogManager().resetDialogStatus(dialogName);

                if (!dialogAnswer) {
                    return getUIFModelAndView(form);
                }
            }
        }

        return super.deleteLine(form,result,request,response);

    }*/

    private Object getSelectedObject(ActivityOfferingForm form){
        String selectedCollectionPath = form.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("Selected collection was not set");
        }

        int selectedLineIndex = KSControllerHelper.getSelectedCollectionLineIndex(form);

        Collection<Object> collection = ObjectPropertyUtils.getPropertyValue(form, selectedCollectionPath);
        return ((List<Object>) collection).get(selectedLineIndex);
    }
}
