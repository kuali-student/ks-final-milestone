package org.kuali.student.enrollment.class2.population.controller;

import org.kuali.rice.krad.web.controller.MaintenanceDocumentController;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.enrollment.class2.population.dto.PopulationWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class is a basic controller for managing Populations.
 * The two custom methods set values on the wrapper based on if the population is rule based or not.
 *
 * @author Kuali Student Team
 */
@Controller
@RequestMapping(value = "/population")
public class PopulationController extends MaintenanceDocumentController {

    @RequestMapping(params = "methodToCall=createByRule")
    public ModelAndView createByRule(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, @SuppressWarnings("unused") BindingResult result,
                                          @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        PopulationWrapper wrapper = (PopulationWrapper)form.getDocument().getNewMaintainableObject().getDataObject();

        wrapper.setCreateByRule(true);
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=createByCombiningPopulations")
    public ModelAndView createByCombiningPopulations (@ModelAttribute("KualiForm") MaintenanceDocumentForm form, @SuppressWarnings("unused") BindingResult result,
                                                      @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        PopulationWrapper wrapper = (PopulationWrapper)form.getDocument().getNewMaintainableObject().getDataObject();
        wrapper.setCreateByRule(false);
        wrapper.setEnableCreateButton(false);
        return getUIFModelAndView(form);

    }
}
