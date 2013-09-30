package org.kuali.student.enrollment.class1.timeslot.controller;

import edu.emory.mathcs.backport.java.util.Collections;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class1.timeslot.form.TimeSlotForm;
import org.kuali.student.enrollment.class1.timeslot.service.TimeSlotViewHelperService;
import org.kuali.student.enrollment.class1.timeslot.util.TimeSlotConstants;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Controller for Manage Time Slots.
 */
@Controller
@RequestMapping(value = "/timeslot")
public class TimeSlotController extends UifControllerBase {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(TimeSlotController.class);

    private static final String MODEL_ATTRIBUTE_FORM = "KualiForm";

    private TimeSlotViewHelperService viewHelperService;

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new TimeSlotForm();
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=start")
    public ModelAndView start( @ModelAttribute( MODEL_ATTRIBUTE_FORM ) UifFormBase form,
                               BindingResult result, HttpServletRequest request, HttpServletResponse response) {

        TimeSlotForm timeSlotForm = (TimeSlotForm) form;

        TimeSlotViewHelperService viewHelperService = getViewHelperService(form);

        return super.start(form, result, request, response);
    }

    /**
     * Search for TimeSlots by type.
     */
    @RequestMapping(params = "methodToCall=show")
    public ModelAndView show(@ModelAttribute( MODEL_ATTRIBUTE_FORM ) TimeSlotForm form)
            throws Exception, PermissionDeniedException, OperationFailedException {

        List<String> timeSlotTypes = Collections.emptyList();
        form.setTimeSlotResults(viewHelperService.findTimeSlots(timeSlotTypes));

        return getUIFModelAndView(form, TimeSlotConstants.TIME_SLOT_PAGE);
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
}
