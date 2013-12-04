package org.kuali.student.enrollment.class2.courseoffering.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

public interface ActivityOfferingControllerTransactionHelper {

	public abstract ModelAndView routeSuper(DocumentFormBase form,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response,
			ActivityOfferingController activityOfferingController);

}