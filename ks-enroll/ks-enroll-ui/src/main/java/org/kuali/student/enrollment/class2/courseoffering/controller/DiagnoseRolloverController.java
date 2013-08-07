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
 * Created by Charles on 9/21/12
 */
package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.apache.log4j.Logger;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.enrollment.class2.courseoffering.form.DiagnoseRolloverForm;
import org.kuali.student.enrollment.class2.courseoffering.service.DiagnoseRolloverViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.DiagnoseRolloverViewHelperServiceImpl;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
@Controller
@RequestMapping(value = "/diagnoseRollover")
public class DiagnoseRolloverController extends UifControllerBase {
    private DiagnoseRolloverViewHelperService viewHelperService;

    private static final Logger LOGGER = Logger.getLogger(DiagnoseRolloverController.class);

    @Override
    protected UifFormBase createInitialForm(@SuppressWarnings("unused") HttpServletRequest request) {
        return new DiagnoseRolloverForm();
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, @SuppressWarnings("unused") BindingResult result,
                              @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
        if (!(form instanceof DiagnoseRolloverForm)){
            throw new RuntimeException("Form object passed into start method was not of expected type DiagnoseRolloverForm. Got " + form.getClass().getSimpleName());
        }
        DiagnoseRolloverForm theForm = (DiagnoseRolloverForm) form;
        Map paramMap = request.getParameterMap();
        if (paramMap.containsKey("pageId")) {
            String pageId = ((String []) paramMap.get("pageId"))[0];
            if (pageId.equals("selectTermForDiagnoseRollover")) {
                return _startSelectTermForDiagnoseRollover(form, result, request, response);
            }
        }
        return getUIFModelAndView(theForm);
        // return super.start(theForm, result, request, response);
    }

    private ModelAndView _startSelectTermForDiagnoseRollover(@ModelAttribute("KualiForm") UifFormBase form, @SuppressWarnings("unused") BindingResult result,
                                                            @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
        // Doesn't do anything really, but is there for customization
        DiagnoseRolloverForm theForm = (DiagnoseRolloverForm) form;
        LOGGER.info("selectTermForDiagnoseRollover");
        return getUIFModelAndView(theForm);
    }

    @RequestMapping(params = "methodToCall=goTargetTerm")
    public ModelAndView goTargetTerm(@ModelAttribute("KualiForm") DiagnoseRolloverForm form, @SuppressWarnings("unused") BindingResult result,
                                     @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        DiagnoseRolloverViewHelperService helper = getViewHelperService(form);
        TermInfo targetTerm = helper.searchTermByTermCode(form.getTargetTermCode());
        if (targetTerm == null) {
            form.alertTargetTermValid(false);
            GlobalVariables.getMessageMap().putError("targetTermCode", "error.diagnose.rolloverco.targetTermInvalid", form.getTargetTermCode());
            return getUIFModelAndView(form);
        }
        form.setTargetTerm(targetTerm);
        if (helper.termHasACourseOffering(targetTerm.getId(), form.getCourseOfferingCode())) {
            // TODO: fix error message
            form.alertTargetTermHasCO();
            GlobalVariables.getMessageMap().putError("targetTermCode", "error.diagnose.rolloverco.coInTargetTerm", form.getCourseOfferingCode(), form.getTargetTermCode());
            return getUIFModelAndView(form);
        }
        form.setDisplayedTargetTermCode(targetTerm.getCode());
        form.alertTargetTermValid(true);
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=goSourceCO")
    public ModelAndView goSourceCO(@ModelAttribute("KualiForm") DiagnoseRolloverForm form, @SuppressWarnings("unused") BindingResult result,
                                     @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        DiagnoseRolloverViewHelperService helper = getViewHelperService(form);
        String sourceTermCode = form.getSourceTermCode();
        TermInfo termInfo = helper.searchTermByTermCode(sourceTermCode);
        if (termInfo != null) {
            //validation to check if source term has a soc
            if (!helper.termHasSoc(termInfo)) {
                // Print error message if there are isn't a SOC in the source term (not critical--could change)
                GlobalVariables.getMessageMap().putError("sourceTermCode", "error.rollover.sourceTerm.noSoc");
                form.resetForm();
                return getUIFModelAndView(form);
            }
            // Get first term
            form.setDisplayedSourceTermCode(sourceTermCode);
            // Set the start date
            Date startDate = termInfo.getStartDate();
            String startDateStr = DateFormatters.COURSE_OFFERING_VIEW_HELPER_DATE_FORMATTER.format(startDate);
            form.setSourceTermStartDate(startDateStr);
            // Set the end date
            Date endDate = termInfo.getEndDate();
            String endDateStr = DateFormatters.COURSE_OFFERING_VIEW_HELPER_DATE_FORMATTER.format(endDate);
            form.setSourceTermEndDate(endDateStr);
            // TODO: Put in last rollover date (Kirk says this may be unnecessary in new wireframes 5/18/2012)
            form.setSourceTerm(termInfo);
            // Now handle course
            CourseOfferingInfo coInfo = helper.getCourseOfferingInfo(form.getSourceTerm().getId(), form.getCourseOfferingCode());
            if (coInfo == null) {
                // TODO: Fix error message
                form.alertSourceCoValid(false);
                GlobalVariables.getMessageMap().putError("courseOfferingCode", "error.diagnose.rolloverco.coNotInSourceTerm", form.getCourseOfferingCode(), form.getSourceTerm().getId());
                return getUIFModelAndView(form);
            }
            form.setCoCodeId(coInfo.getId());
            form.setDisplayedCourseOfferingCode(coInfo.getCourseOfferingCode());
            form.setCourseOfferingTitle(coInfo.getCourseOfferingTitle());
            form.alertSourceCoValid(true);
        } else {
            form.setTargetTerm(null);
            form.resetForm();
            GlobalVariables.getMessageMap().putError("sourceTermCode", "error.diagnose.rolloverco.sourceTermInvalid", form.getSourceTermCode());
        }
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=deleteCoInTargetTerm")
    public ModelAndView deleteCoInTargetTerm(@ModelAttribute("KualiForm") DiagnoseRolloverForm form, @SuppressWarnings("unused") BindingResult result,
                                             @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        DiagnoseRolloverViewHelperService helper = getViewHelperService(form);
        TermInfo targetTerm = helper.searchTermByTermCode(form.getTargetTermCode());
        boolean success = helper.deleteCourseOfferingInTerm(form.getCourseOfferingCode(), targetTerm.getId());
        if (success) {
            form.alertTargetTermValid(true);
        }
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=performCoRollover")
    public ModelAndView performCoRollover(@ModelAttribute("KualiForm") DiagnoseRolloverForm form, @SuppressWarnings("unused") BindingResult result,
                                       @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        DiagnoseRolloverViewHelperService helper = getViewHelperService(form);
        helper.searchTermByTermCode(form.getTargetTermCode());
        Map<String, Object> keyValues = helper.rolloverCourseOfferingFromSourceTermToTargetTerm(form.getCourseOfferingCode(), form.getSourceTerm().getId(), form.getTargetTerm().getId());
        double diffInSeconds = (Double) keyValues.get(DiagnoseRolloverViewHelperServiceImpl.DURATION_IN_SECONDS);
        form.setRolloverDuration(diffInSeconds  + "s");
        return getUIFModelAndView(form);
    }

    public DiagnoseRolloverViewHelperService getViewHelperService(DiagnoseRolloverForm rolloverForm) {
        if (viewHelperService == null) {
            if (rolloverForm.getView().getViewHelperServiceClass() != null) {
                viewHelperService = (DiagnoseRolloverViewHelperService) rolloverForm.getView().getViewHelperService();
            } else {
                viewHelperService = (DiagnoseRolloverViewHelperService) rolloverForm.getPostedView().getViewHelperService();
            }
        }
        return viewHelperService;
    }
}
