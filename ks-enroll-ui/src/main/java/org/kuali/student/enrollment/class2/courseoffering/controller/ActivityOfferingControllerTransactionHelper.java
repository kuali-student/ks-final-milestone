package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: Daniel
 * Date: 7/23/13
 * Time: 10:54 AM
 * This method is a round-about way to provide a new transaction when calling MaintenanceDocumentController.route()
 */
public class ActivityOfferingControllerTransactionHelper {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ModelAndView routeSuper(DocumentFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response, ActivityOfferingController activityOfferingController) {
        return activityOfferingController.routeSuper(form, result, request, response);
    }

}
