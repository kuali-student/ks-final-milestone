package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ControllerTransactionHelper {

	ModelAndView routeSuper(DocumentFormBase form,
			                BindingResult result, HttpServletRequest request,
			                HttpServletResponse response,
                            ActivityOfferingController activityOfferingController);

    ModelAndView routeSuper(DocumentFormBase form,
                            BindingResult result, HttpServletRequest request,
                            HttpServletResponse response,
                            CourseOfferingEditController courseOfferingEditController);

}
