package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.web.controller.MaintenanceDocumentController;
import org.kuali.rice.krad.web.form.MaintenanceForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ScheduleWrapper;
import org.kuali.student.enrollment.class2.courseoffering.form.ActivityOfferingForm;
import org.kuali.student.enrollment.class2.courseoffering.service.ActivityOfferingMaintainable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping(value = "/activityOffering")
public class ActivityOfferingController extends MaintenanceDocumentController {

    @Override
    protected MaintenanceForm createInitialForm(HttpServletRequest request) {
        return new ActivityOfferingForm();
    }

    @RequestMapping(params = "methodToCall=reviseSchedule")
    public ModelAndView reviseSchedule(@ModelAttribute("KualiForm") ActivityOfferingForm form) throws Exception {

        return getUIFModelAndView(form,ActivityOfferingForm.SCHEDULE_PAGE);
    }

    @RequestMapping(params = "methodToCall=editScheduleComponent")
    public ModelAndView editScheduleComponent(@ModelAttribute("KualiForm") ActivityOfferingForm form) throws Exception {

        ScheduleWrapper scheduleWrapper = (ScheduleWrapper)getSelectedObject(form);
        scheduleWrapper.setEdited(true);
        ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
        activityOfferingWrapper.setNewScheduleRequest(scheduleWrapper);

//        activityOfferingWrapper.getBackUpRequestedComponents().add(scheduleWrapper);
        activityOfferingWrapper.getRequestedScheduleComponents().remove(scheduleWrapper);

        ScheduleWrapper actual = getViewHelperService(form).getMatchingActualForRequestedSchedule(activityOfferingWrapper,scheduleWrapper);
//        activityOfferingWrapper.getBackUpActualComponents().add(actual);
        activityOfferingWrapper.getActualScheduleComponents().remove(actual);

        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=addScheduleComponent")
    public ModelAndView addScheduleComponent(@ModelAttribute("KualiForm") ActivityOfferingForm form) throws Exception {

        ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();

        if (form.isSchedulePage()){
            activityOfferingWrapper.getNewScheduleRequest().setNewlyAdded(true);
        }
        getViewHelperService(form).addOrUpdateScheduleRequestComponent(activityOfferingWrapper);
        
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=saveScheduleRequest")
    public ModelAndView saveScheduleRequest(@ModelAttribute("KualiForm") ActivityOfferingForm form) throws Exception {

        ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();

        activityOfferingWrapper.setNewScheduleRequest(new ScheduleWrapper());

        return getUIFModelAndView(form,ActivityOfferingForm.MAIN_PAGE);
    }

    @RequestMapping(params = "methodToCall=saveAndProcessScheduleRequest")
    public ModelAndView saveAndProcessScheduleRequest(@ModelAttribute("KualiForm") ActivityOfferingForm form) throws Exception {

        ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();

        activityOfferingWrapper.setNewScheduleRequest(new ScheduleWrapper());

        getViewHelperService(form).saveAndProcessScheduleRequest(activityOfferingWrapper,form);

        return getUIFModelAndView(form,ActivityOfferingForm.MAIN_PAGE);
    }

    @RequestMapping(params = "methodToCall=cancelEditingSchedule")
    public ModelAndView cancelEditingSchedule(@ModelAttribute("KualiForm") ActivityOfferingForm form) throws Exception {

        ActivityOfferingWrapper activityOfferingWrapper = (ActivityOfferingWrapper)form.getDocument().getNewMaintainableObject().getDataObject();

        activityOfferingWrapper.setNewScheduleRequest(new ScheduleWrapper());
        List<ScheduleWrapper> cancelScheduleRequestComponents = new ArrayList<ScheduleWrapper>();
        for (ScheduleWrapper scheduleWrapper : activityOfferingWrapper.getRequestedScheduleComponents()){
            if (scheduleWrapper.isNewlyAdded() || scheduleWrapper.isEdited()){
                cancelScheduleRequestComponents.add(scheduleWrapper);
            }
        }

        activityOfferingWrapper.getRequestedScheduleComponents().removeAll(cancelScheduleRequestComponents);

//        if (!activityOfferingWrapper.getBackUpRequestedComponents().isEmpty()){
//            activityOfferingWrapper.getRequestedScheduleComponents().addAll(activityOfferingWrapper.getBackUpRequestedComponents());
//        }
//
//        if (!activityOfferingWrapper.getBackUpActualComponents().isEmpty()){
//            activityOfferingWrapper.getActualScheduleComponents().addAll(activityOfferingWrapper.getBackUpActualComponents());
//        }

        return getUIFModelAndView(form,ActivityOfferingForm.MAIN_PAGE);
    }

    public ActivityOfferingMaintainable getViewHelperService(ActivityOfferingForm form){
        if (form.getView().getViewHelperServiceClass() != null){
            return (ActivityOfferingMaintainable) form.getView().getViewHelperService();
        }else{
            return (ActivityOfferingMaintainable) form.getPostedView().getViewHelperService();
        }
    }
    
    private Object getSelectedObject(ActivityOfferingForm form){
        String selectedCollectionPath = form.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("Selected collection was not set");
        }

        int selectedLineIndex = -1;
        String selectedLine = form.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        if (StringUtils.isNotBlank(selectedLine)) {
            selectedLineIndex = Integer.parseInt(selectedLine);
        }

        if (selectedLineIndex == -1) {
            throw new RuntimeException("Selected line index was not set");
        }

        Collection<Object> collection = ObjectPropertyUtils.getPropertyValue(form, selectedCollectionPath);
        Object selectedObject = ((List<Object>) collection).get(selectedLineIndex);

        return selectedObject;
    }
}
