package org.kuali.student.enrollment.class1.krms.controller;

import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.rice.krms.util.KRMSConstants;
import org.kuali.student.enrollment.class1.krms.util.EnrolKRMSConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Override of RuleEditorController for Student
 *
 * @author Kuali Student Team
 */
@Controller
@RequestMapping(value = "/courseOfferingRules")
public class CORuleEditorController extends EnrolRuleEditorController {

    /**
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    @RequestMapping(params = "methodToCall=addRule")
    public ModelAndView addRule(@ModelAttribute("KualiForm") UifFormBase form, @SuppressWarnings("unused") BindingResult result,
                                @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        form.getActionParameters().put(UifParameters.NAVIGATE_TO_PAGE_ID, EnrolKRMSConstants.KSKRMS_RULE_CO_MAINTENANCE_PAGE_ID);
        return super.addRule(form, result, request, response);
    }

    /**
     * Method used to invoke the CO inquiry view from Manage Course Offering screen while search input is Course Offering
     * Code (04a screen)
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=goToRuleView")
    public ModelAndView goToRuleView(@ModelAttribute("KualiForm") UifFormBase form, @SuppressWarnings("unused") BindingResult result,
                                     @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        form.getActionParameters().put(UifParameters.NAVIGATE_TO_PAGE_ID, EnrolKRMSConstants.KSKRMS_RULE_CO_MAINTENANCE_PAGE_ID);
        return super.goToRuleView(form, result, request, response);
    }

    /**
     * Reverts rule to previous state and navigates to agenda maintenance page.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=cancelEditRule")
    public ModelAndView cancelEditRule(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                       HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        form.getActionParameters().put(UifParameters.NAVIGATE_TO_PAGE_ID, EnrolKRMSConstants.KSKRMS_AGENDA_CO_MAINTENANCE_PAGE_ID);
        return super.cancelEditRule(form, result, request, response);
    }

    /**
     * Updates rule and redirects to agenda maintenance page.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=updateRule")
    public ModelAndView updateRule(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                   HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        form.getActionParameters().put(UifParameters.NAVIGATE_TO_PAGE_ID, EnrolKRMSConstants.KSKRMS_AGENDA_CO_MAINTENANCE_PAGE_ID);
        return super.updateRule(form, result, request, response);
    }

    /**
     * Retrieves selected proposition key and initializes edit on propostion.
     *
     * @param form
     * @param result
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=getSelectedKey")
    public ModelAndView getSelectedKey(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                       HttpServletRequest request, HttpServletResponse response) throws Exception {

        //Clear the current states of the tabs to open the first tab again with the edit tree.
        Map<String, String> states = (Map<String, String>) form.getClientStateForSyncing().get(EnrolKRMSConstants.KSKRMS_RULE_CO_TABS_ID);
        states.put(KRMSConstants.KRMS_PARM_ACTIVE_TAB, EnrolKRMSConstants.KSKRMS_RULE_CO_EDITWITHOBJECT_ID);

        //Set the selected rule statement key.
        String selectedKey = request.getParameter(KRMSConstants.KRMS_PARM_SELECTED_KEY);
        getRuleEditor(form).setSelectedKey(selectedKey);

        return this.goToEditProposition(form, result, request, response);
    }

    /**
     * Test method for a controller that invokes a dialog lightbox.
     *
     * @param form     - test form
     * @param result   - Spring form binding result
     * @param request  - http request
     * @param response - http response
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=compareRules")
    public ModelAndView compareRules(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                     HttpServletRequest request, HttpServletResponse response) throws Exception {

        doCompareRules(form);

        // redirect back to client to display lightbox
        return showDialog(EnrolKRMSConstants.KSKRMS_DIALOG_COMPARE_CLU_CO, form, request, response);
    }

}
