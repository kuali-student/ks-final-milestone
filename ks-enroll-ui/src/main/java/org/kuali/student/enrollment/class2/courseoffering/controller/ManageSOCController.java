package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class2.courseoffering.form.ManageSOCForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: venkat
 * Date: 9/14/12
 * Time: 3:09 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "/manageSOC")
public class ManageSOCController extends UifControllerBase {


    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new ManageSOCForm();
    }

    @RequestMapping(params = "methodToCall=lockSOC")
    public ModelAndView lockSOC(@ModelAttribute("KualiForm") ManageSOCForm socForm, BindingResult result,
                                            HttpServletRequest request, HttpServletResponse response){

        return getUIFModelAndView(socForm);
    }
}
