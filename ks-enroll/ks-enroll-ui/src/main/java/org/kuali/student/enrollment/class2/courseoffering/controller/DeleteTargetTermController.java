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
 * Created by Charles on 6/18/12
 */
package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.apache.log4j.Logger;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingRolloverManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.form.DeleteTargetTermForm;
import org.kuali.student.enrollment.class2.courseoffering.service.CourseOfferingViewHelperService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
@Controller
@RequestMapping(value = "/deleteTargetTerm")
public class DeleteTargetTermController extends UifControllerBase {
    final static Logger LOGGER = Logger.getLogger(DeleteTargetTermForm.class);
    private CourseOfferingViewHelperService viewHelperService;

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest httpServletRequest) {
        return new DeleteTargetTermForm();
    }

    public CourseOfferingViewHelperService getViewHelperService(UifFormBase form) {
        if (viewHelperService == null) {
            if (form.getView().getViewHelperServiceClass() != null) {
                viewHelperService = (CourseOfferingViewHelperService) form.getView().getViewHelperService();
            } else {
                viewHelperService = (CourseOfferingViewHelperService) form.getPostedView().getViewHelperService();
            }
        }
        return viewHelperService;
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        DeleteTargetTermForm theForm = (DeleteTargetTermForm) form;
        Date date = Calendar.getInstance().getTime();
        System.err.println(date.toString() + " ");
        System.err.println(theForm);
        return getUIFModelAndView(theForm);
        // return super.start(theForm, result, request, response);
    }

    @RequestMapping(params = "methodToCall=goTargetTerm")
    public ModelAndView goTargetTerm(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                     HttpServletRequest request, HttpServletResponse response) throws Exception {
        LOGGER.info("In goTargetTerm");
        CourseOfferingViewHelperService helperService = getViewHelperService(form);
        DeleteTargetTermForm dttForm = (DeleteTargetTermForm) form;
        List<TermInfo> termInfos = helperService.findTermByTermCode(dttForm.getTargetTermCode());
        if (termInfos == null || termInfos.isEmpty()) {
            // Must have a valid term
            GlobalVariables.getMessageMap().putError("targetTermCode", "error.submit.sourceTerm"); // TODO: Change error
            return getUIFModelAndView(form);
        }

        TermInfo termInfo = termInfos.get(0);
        // Fill in form data for target term
        dttForm.setDisplayedTargetTermId(termInfo.getId());
        String startDateStr = helperService.formatDate(termInfo.getStartDate());
        String endDateStr = helperService.formatDate(termInfo.getEndDate());
        dttForm.setTargetTermStartDate(startDateStr);
        dttForm.setTargetTermEndDate(endDateStr);
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=deleteTargetTerm")
    public ModelAndView deleteTargetTerm(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                         HttpServletRequest request, HttpServletResponse response) throws Exception {
        DeleteTargetTermForm dttForm = (DeleteTargetTermForm) form;
        if (dttForm.getTargetTermCode() == null || dttForm.getTargetTermCode().length() == 0) {
            GlobalVariables.getMessageMap().putError("targetTermCode", "error.submit.sourceTerm");
            return getUIFModelAndView(form);
        }
        // Check for target SOC
        CourseOfferingViewHelperService helperService = getViewHelperService(form);
        SocInfo mainSoc = helperService.getMainSoc(dttForm.getDisplayedTargetTermId());
        if (mainSoc == null) {
            GlobalVariables.getMessageMap().putError("targetTermCode", "error.delete.targetTerm.noSoc");
            return getUIFModelAndView(form);
        }
        if (!mainSoc.getStateKey().equals(CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY)) {
            GlobalVariables.getMessageMap().putError("targetTermCode", "error.delete.targetTerm.notDraftSoc");
            return getUIFModelAndView(form);
        }
        helperService.deleteTargetTerm(dttForm.getDisplayedTargetTermId(), dttForm);
        return getUIFModelAndView(form);
    }
}
