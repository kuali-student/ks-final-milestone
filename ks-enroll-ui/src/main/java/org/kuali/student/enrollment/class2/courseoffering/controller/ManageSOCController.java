/**
 * Copyright 2012 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 */

package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.enrollment.class2.courseoffering.form.ManageSOCForm;
import org.kuali.student.enrollment.class2.courseoffering.service.ManageSOCViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.util.ManageSocConstants;
import org.kuali.student.enrollment.uif.util.KSControllerHelper;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class handles all the request for Managing SOC. This handles requests from ManageSOCView for different SOC state
 * changes and scheduling/publishing events. This controller is mapped to the view defined in <code>ManageSOCView.xml</code>
 *
 * @author Kuali Student Team
 *
 */
@Controller
@RequestMapping(value = "/manageSOC")
public class ManageSOCController extends UifControllerBase {

    private final static Logger LOG = Logger.getLogger(ManageSOCController.class);

    @Override
    protected UifFormBase createInitialForm(@SuppressWarnings("unused") HttpServletRequest request) {
        return new ManageSOCForm();
    }

    @RequestMapping(params = "methodToCall=lockSOC")
    public ModelAndView lockSOC(@ModelAttribute("KualiForm") ManageSOCForm socForm, @SuppressWarnings("unused") BindingResult result,
                                @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        LOG.debug("Locking SOC");

        String dialogName = ManageSocConstants.ConfirmDialogs.LOCK;

        if (!hasDialogBeenAnswered(dialogName, socForm)) {
            return showDialog(dialogName, socForm, request, response);
        }

        boolean dialogAnswer = getBooleanDialogResponse(dialogName, socForm, request, response);
        socForm.getDialogManager().resetDialogStatus(dialogName);

        if (dialogAnswer) {

            if (socForm.getSocInfo() == null) {
                throw new RuntimeException("SocInfo not exists in the form. Please enter the term code and click on GO button");
            }

            if (!StringUtils.equals(CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY, socForm.getSocInfo().getStateKey())) {
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, ManageSocConstants.MessageKeys.ERROR_INVALID_STATUS_FOR_LOCK);
                return getUIFModelAndView(socForm);
            }

            ManageSOCViewHelperService viewHelper = (ManageSOCViewHelperService) KSControllerHelper.getViewHelperService(socForm);
            viewHelper.lockSOC(socForm);

            return buildModel(socForm, result, request, response);

        } else {
            return getUIFModelAndView(socForm);
        }

    }

    @RequestMapping(params = "methodToCall=sendApprovedActivitiesToScheduler")
    public ModelAndView sendApprovedActivitiesToScheduler(@ModelAttribute("KualiForm") ManageSOCForm socForm, @SuppressWarnings("unused") BindingResult result,
                                                          @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        if (!StringUtils.equals(CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY, socForm.getSocInfo().getStateKey())) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, ManageSocConstants.MessageKeys.ERROR_INVALID_STATUS_FOR_SCHEDULE);
            return getUIFModelAndView(socForm);
        }

        String dialogName = ManageSocConstants.ConfirmDialogs.MASS_SCHEDULING;

        if (!hasDialogBeenAnswered(dialogName, socForm)) {
            return showDialog(dialogName, socForm, request, response);
        }

        boolean dialogAnswer = getBooleanDialogResponse(dialogName, socForm, request, response);
        socForm.getDialogManager().resetDialogStatus(dialogName);

        if (dialogAnswer) {
            // start send approved activities to scheduler
            ManageSOCViewHelperService viewHelper = (ManageSOCViewHelperService) KSControllerHelper.getViewHelperService(socForm);
            viewHelper.startMassScheduling(socForm);
            return buildModel(socForm, result, request, response);
        } else {
            return getUIFModelAndView(socForm);
        }
    }

    /**
     * This is called when the user enters the term code and hit the Go button.
     *
     * @param socForm
     * @param result
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "methodToCall=buildModel")
    public ModelAndView buildModel(@ModelAttribute("KualiForm") ManageSOCForm socForm, @SuppressWarnings("unused") BindingResult result,
                                   @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

        ManageSOCViewHelperService viewHelper = (ManageSOCViewHelperService) KSControllerHelper.getViewHelperService(socForm);


        TermInfo term = viewHelper.getTermByCode(socForm.getTermCode());
        if(term!=null){
            socForm.clear();
            socForm.setTermInfo(term);
            viewHelper.buildModel(socForm);
        }
        return getUIFModelAndView(socForm);


    }

    @RequestMapping(params = "methodToCall=allowFinalEdits")
    public ModelAndView allowFinalEdits(@ModelAttribute("KualiForm") ManageSOCForm socForm, @SuppressWarnings("unused") BindingResult result,
                                        @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        if (socForm.getSocInfo() == null) {
            throw new RuntimeException("SocInfo not exists in the form. Please enter the term code and click on GO button");
        }

        if (!StringUtils.equals(CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_COMPLETED, socForm.getSocInfo().getSchedulingStateKey())) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, ManageSocConstants.MessageKeys.ERROR_INVALID_STATUS_FOR_FINALEDIT);
            return getUIFModelAndView(socForm);
        }

        String dialogName = ManageSocConstants.ConfirmDialogs.FINAL_EDITS;

        if (!hasDialogBeenAnswered(dialogName, socForm)) {
            return showDialog(dialogName, socForm, request, response);
        }

        boolean dialogAnswer = getBooleanDialogResponse(dialogName, socForm, request, response);
        socForm.getDialogManager().resetDialogStatus(dialogName);

        if (dialogAnswer) {

            ManageSOCViewHelperService viewHelper = (ManageSOCViewHelperService) KSControllerHelper.getViewHelperService(socForm);
            viewHelper.allowSOCFinalEdit(socForm);

            return buildModel(socForm, result, request, response);

        } else {
            return getUIFModelAndView(socForm);
        }
    }

    @RequestMapping(params = "methodToCall=publishSOC")
    public ModelAndView publishSOC(@ModelAttribute("KualiForm") ManageSOCForm socForm, @SuppressWarnings("unused") BindingResult result,
                                   @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        if (socForm.getSocInfo() == null) {
            throw new RuntimeException("SocInfo not exists in the form. Please enter the term code and click on GO button");
        }

        if (!StringUtils.equals(CourseOfferingSetServiceConstants.FINALEDITS_SOC_STATE_KEY, socForm.getSocInfo().getStateKey())) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, ManageSocConstants.MessageKeys.ERROR_INVALID_STATUS_FOR_PUBLISH);
            return getUIFModelAndView(socForm);
        }

        String dialogName = ManageSocConstants.ConfirmDialogs.MASS_PUBLISHLING;

        if (!hasDialogBeenAnswered(dialogName, socForm)) {
            return showDialog(dialogName, socForm, request, response);
        }

        boolean dialogAnswer = getBooleanDialogResponse(dialogName, socForm, request, response);
        socForm.getDialogManager().resetDialogStatus(dialogName);

        if (dialogAnswer) {
            ManageSOCViewHelperService viewHelper = (ManageSOCViewHelperService) KSControllerHelper.getViewHelperService(socForm);
            viewHelper.publishSOC(socForm);
            return buildModel(socForm, result, request, response);
        } else {
            return getUIFModelAndView(socForm);
        }
    }

    @RequestMapping(params = "methodToCall=closeSOC")
    public ModelAndView closeSOC(@ModelAttribute("KualiForm") ManageSOCForm socForm, @SuppressWarnings("unused") BindingResult result,
                                 @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        if (socForm.getSocInfo() == null) {
            throw new RuntimeException("SocInfo not exists in the form. Please enter the term code and click on GO button");
        }

        if (!StringUtils.equals(CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY, socForm.getSocInfo().getStateKey())) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, ManageSocConstants.MessageKeys.ERROR_INVALID_STATUS_FOR_CLOSE);
            return getUIFModelAndView(socForm);
        }

        String dialogName = ManageSocConstants.ConfirmDialogs.CLOSE_SET;

        if (!hasDialogBeenAnswered(dialogName, socForm)) {
            return showDialog(dialogName, socForm, request, response);
        }

        boolean dialogAnswer = getBooleanDialogResponse(dialogName, socForm, request, response);
        socForm.getDialogManager().resetDialogStatus(dialogName);

        if (dialogAnswer) {
            ManageSOCViewHelperService viewHelper = (ManageSOCViewHelperService) KSControllerHelper.getViewHelperService(socForm);
            viewHelper.closeSOC(socForm);

            return buildModel(socForm, result, request, response);
        } else {
            return getUIFModelAndView(socForm);
        }
    }
}
