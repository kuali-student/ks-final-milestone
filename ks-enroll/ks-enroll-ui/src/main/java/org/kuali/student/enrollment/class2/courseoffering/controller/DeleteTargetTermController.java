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

import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.enrollment.class2.courseoffering.form.DeleteTargetTermForm;
import org.kuali.student.enrollment.class2.courseoffering.service.CourseOfferingViewHelperService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * This class provides controller methods for Delete Target Term ui
 *
 * @author Kuali Student Team
 */
@Controller
@RequestMapping(value = "/deleteTargetTerm")
public class DeleteTargetTermController extends UifControllerBase {
    private final static Logger LOGGER = LoggerFactory.getLogger(DeleteTargetTermForm.class);

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest httpServletRequest) {
        return new DeleteTargetTermForm();
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form,
                              @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
        if (!(form instanceof DeleteTargetTermForm)){
            throw new RuntimeException("Form object passed into start method was not of expected type DeleteTargetTermForm. Got " + form.getClass().getSimpleName());
        }
        DeleteTargetTermForm theForm = (DeleteTargetTermForm) form;
        Date date = Calendar.getInstance().getTime();
        LOGGER.error("{} {}", date, theForm);
        return getUIFModelAndView(theForm);
    }

    @RequestMapping(params = "methodToCall=goTargetTerm")
    public ModelAndView goTargetTerm(@ModelAttribute("KualiForm") DeleteTargetTermForm form) throws Exception {
        LOGGER.info("In goTargetTerm");
        CourseOfferingViewHelperService helperService = CourseOfferingManagementUtil.getCoViewHelperService(form);

        List<TermInfo> termInfos = helperService.findTermByTermCode(form.getTargetTermCode());
        if (termInfos == null || termInfos.isEmpty()) {
            // Must have a valid term
            GlobalVariables.getMessageMap().putError("targetTermCode", "error.submit.sourceTerm"); // TODO: Change error
            return getUIFModelAndView(form);
        }
        int firstTerm = 0;
        TermInfo termInfo = termInfos.get(firstTerm);
        // Fill in form data for target term
        form.setDisplayedTargetTermId(termInfo.getId());
        String startDateStr = helperService.formatDate(termInfo.getStartDate());
        String endDateStr = helperService.formatDate(termInfo.getEndDate());
        form.setTargetTermStartDate(startDateStr);
        form.setTargetTermEndDate(endDateStr);
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=deleteTargetTerm")
    public ModelAndView deleteTargetTerm(@ModelAttribute("KualiForm") DeleteTargetTermForm form, @SuppressWarnings("unused") BindingResult result,
                                         @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        if (form.getTargetTermCode() == null || form.getTargetTermCode().length() == 0) {
            GlobalVariables.getMessageMap().putError("targetTermCode", "error.submit.sourceTerm");
            return getUIFModelAndView(form);
        }
        // Check for target SOC
        CourseOfferingViewHelperService helperService = CourseOfferingManagementUtil.getCoViewHelperService(form);
        SocInfo mainSoc = helperService.getMainSoc(form.getDisplayedTargetTermId());
        if (mainSoc == null) {
            GlobalVariables.getMessageMap().putError("targetTermCode", "error.delete.targetTerm.noSoc");
            return getUIFModelAndView(form);
        }
        if (!mainSoc.getStateKey().equals(CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY)) {
            GlobalVariables.getMessageMap().putError("targetTermCode", "error.delete.targetTerm.notDraftSoc");
            return getUIFModelAndView(form);
        }
        helperService.deleteTargetTerm(form.getDisplayedTargetTermId(), form);
        return getUIFModelAndView(form);
    }
}
