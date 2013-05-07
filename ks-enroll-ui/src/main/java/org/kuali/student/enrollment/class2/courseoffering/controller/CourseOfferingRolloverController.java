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
 * Created by Charles on 5/7/12
 */
package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.UnhandledException;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.view.History;
import org.kuali.rice.krad.uif.view.HistoryEntry;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.util.UrlFactory;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class2.appointment.util.AppointmentConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.uif.util.GrowlIcon;
import org.kuali.student.enrollment.uif.util.KSUifUtils;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.enrollment.class2.courseoffering.dto.SocRolloverResultItemWrapper;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingRolloverManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.service.CourseOfferingViewHelperService;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.constants.StateServiceConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The controller for Perform Rollover, Rollover Details, and Release to Depts page (all within the same view).
 *
 * @author Kuali Student Team
 */
@Controller
@RequestMapping(value = "/courseOfferingRollover")
public class CourseOfferingRolloverController extends UifControllerBase {
    private CourseOfferingViewHelperService viewHelperService;
    private CourseOfferingSetService socService;
    private CourseOfferingService coService;
    private StateService stateService;

    private static final Logger LOGGER = Logger.getLogger(CourseOfferingRolloverController.class);
    public static final String ROLLOVER_DETAILS_PAGEID = "selectTermForRolloverDetails";
    public static final String ROLLOVER_MANAGEMENT_VIEWID = "courseOfferingRolloverManagementView";
    public static final String ROLLOVER_CONFIRM_RELEASE = "releaseToDepts";

    @Override
    protected UifFormBase createInitialForm(@SuppressWarnings("unused") HttpServletRequest request) {
        return new CourseOfferingRolloverManagementForm();
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, @SuppressWarnings("unused") BindingResult result,
                              @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
        if (!(form instanceof CourseOfferingRolloverManagementForm)) {
            throw new RuntimeException("Form object passed into start method was not of expected type CourseOfferingRolloverManagementForm. Got " + form.getClass().getSimpleName());
        }
        CourseOfferingRolloverManagementForm theForm = (CourseOfferingRolloverManagementForm) form;

        // check view authorization
        // TODO: this needs to be invoked for each request
        if (form.getView() != null) {
            String methodToCall = request.getParameter(KRADConstants.DISPATCH_REQUEST_PARAMETER);
            checkViewAuthorization(theForm, methodToCall);
        }

        Map paramMap = request.getParameterMap();
        if (paramMap.containsKey("pageId")) {
            String pageId = ((String[]) paramMap.get("pageId"))[0];
            if (pageId.equals("selectTermsForRollover")) {
                return _startPerformRollover(form, result, request, response);
            } else if (pageId.equals("releaseToDepts")) {
                return _startReleaseToDepts(theForm, result, request, response);
            } else if (pageId.equals("selectTermForRolloverDetails")) {
                return _startRolloverDetails(form, result, request, response);
            }
        }
        return getUIFModelAndView(theForm);
        // return super.start(theForm, result, request, response);
    }

    private ModelAndView _startPerformRollover(@ModelAttribute("KualiForm") UifFormBase form, @SuppressWarnings("unused") BindingResult result,
                                               @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
        CourseOfferingRolloverManagementForm theForm = (CourseOfferingRolloverManagementForm) form;
        LOGGER.info("startPerformRollover");
        return getUIFModelAndView(theForm);
        // return super.start(theForm, result, request, response);
    }

    private ModelAndView _startRolloverDetails(@ModelAttribute("KualiForm") UifFormBase form, @SuppressWarnings("unused") BindingResult result,
                                               @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
        CourseOfferingRolloverManagementForm theForm = (CourseOfferingRolloverManagementForm) form;
        LOGGER.info("startRolloverDetails");
        String rolloverTerm = theForm.getRolloverTargetTermCode();

        try {
            if (rolloverTerm != null && !"".equals(rolloverTerm)) {
                return showRolloverResults(theForm, result, request, response);
            }
        } catch (Exception ex) {
            return getUIFModelAndView(theForm);
        }

        return getUIFModelAndView(theForm);
    }

    private ModelAndView _startReleaseToDepts(@ModelAttribute("KualiForm") CourseOfferingRolloverManagementForm form, @SuppressWarnings("unused") BindingResult result,
                                              @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
        LOGGER.info("startReleaseToDepts");
        form.computeReleaseToDeptsDisabled();
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=goTargetTerm")
    public ModelAndView goTargetTerm(@ModelAttribute("KualiForm") CourseOfferingRolloverManagementForm form, @SuppressWarnings("unused") BindingResult result,
                                     @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        CourseOfferingViewHelperService helper = getViewHelperService(form);
        // validation to check for like terms and target term year comes before source term year.
        String targetTermCd = form.getTargetTermCode();
        String sourceTermCd = form.getSourceTermCode();
        List<TermInfo> targetTermsByCode = helper.findTermByTermCode(targetTermCd);
        List<TermInfo> sourceTermsByCode = helper.findTermByTermCode(sourceTermCd);

        //Check that the source and target terms exist in the db
        if (sourceTermsByCode.isEmpty()) {
            GlobalVariables.getMessageMap().putError("sourceTermCode", "error.courseoffering.sourceTerm.inValid");
            form.setIsRolloverButtonDisabled(true);
            return getUIFModelAndView(form);
        }
        if (targetTermsByCode.isEmpty()) {
            GlobalVariables.getMessageMap().putError("targetTermCode", "error.courseoffering.targetTerm.inValid");
            form.setIsRolloverButtonDisabled(true);
            return getUIFModelAndView(form);
        }

        TermInfo targetTerm = helper.findTermByTermCode(targetTermCd).get(0);
        TermInfo sourceTerm = helper.findTermByTermCode(sourceTermCd).get(0);
        boolean likeTerms = sourceTerm.getTypeKey().equals(targetTerm.getTypeKey());
        boolean sourcePrecedesTarget = sourceTerm.getStartDate().before(targetTerm.getStartDate());
        if (!likeTerms) {
            GlobalVariables.getMessageMap().putError("targetTermCode", "error.likeTerms.validation");
            form.setIsRolloverButtonDisabled(true);
            return getUIFModelAndView(form);
        } else if (!sourcePrecedesTarget) {
            GlobalVariables.getMessageMap().putError("targetTermCode", "error.years.validation");
            form.setIsRolloverButtonDisabled(true);
            return getUIFModelAndView(form);
        } 
        
        List<TermInfo> termList = helper.findTermByTermCode(form.getTargetTermCode());
        if (termList != null && termList.size() == 1) {
            //validation to check if already rollover target term exists..
            List<String> coIds = this._getCourseOfferingService().getCourseOfferingIdsByTerm(termList.get(0).getId(), true, new ContextInfo());
            if (!coIds.isEmpty()) {
                // Print error message if there are course offerings in the target term
                GlobalVariables.getMessageMap().putError("targetTermCode", "error.courseoffering.rollover.targetTermExists");
                //form.resetForm();
                return getUIFModelAndView(form);
            }
            // Get first term
            TermInfo matchingTerm = termList.get(0);
            String targetTermCode = matchingTerm.getCode();
            form.setDisplayedTargetTermCode(targetTermCode);
            // Set the start date
            Date startDate = matchingTerm.getStartDate();
            String startDateStr = DateFormatters.COURSE_OFFERING_VIEW_HELPER_DATE_FORMATTER.format(startDate);
            form.setTargetTermStartDate(startDateStr);
            // Set the end date
            Date endDate = matchingTerm.getEndDate();
            String endDateStr = DateFormatters.COURSE_OFFERING_VIEW_HELPER_DATE_FORMATTER.format(endDate);
            form.setTargetTermEndDate(endDateStr);
            form.setTargetTerm(matchingTerm);
            form.setIsRolloverButtonDisabled(false); // Enable the button
        } else {
            form.setTargetTerm(null);
            form.resetForm();
            GlobalVariables.getMessageMap().putError("targetTermCode", "error.courseoffering.targetTerm.inValid");
        }
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=goSourceTerm")
    public ModelAndView goSourceTerm(@ModelAttribute("KualiForm") CourseOfferingRolloverManagementForm form, @SuppressWarnings("unused") BindingResult result,
                                     @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        CourseOfferingViewHelperService helper = getViewHelperService(form);
        List<TermInfo> termList = helper.findTermByTermCode(form.getSourceTermCode());
        if (termList != null && termList.size() == 1) {
            // Get first term
            TermInfo matchingTerm = termList.get(0);
            String sourceTermCode = matchingTerm.getCode();
            //Check SOC
            boolean sourceTermHasSoc = helper.termHasSoc(matchingTerm.getId(), form);
            if (!sourceTermHasSoc) {
                GlobalVariables.getMessageMap().putError("sourceTermCode", "error.rollover.sourceTerm.noSoc");
                form.setIsRolloverButtonDisabled(true);
                return getUIFModelAndView(form);
            }  
            form.setDisplayedSourceTermCode(sourceTermCode);
            // Set the start date
            Date startDate = matchingTerm.getStartDate();
            String startDateStr = DateFormatters.COURSE_OFFERING_VIEW_HELPER_DATE_FORMATTER.format(startDate);
            form.setSourceTermStartDate(startDateStr);
            // Set the end date
            Date endDate = matchingTerm.getEndDate();
            String endDateStr = DateFormatters.COURSE_OFFERING_VIEW_HELPER_DATE_FORMATTER.format(endDate);
            form.setSourceTermEndDate(endDateStr);
            form.setSourceTerm(matchingTerm);
            form.setIsGoSourceButtonDisabled(false); // Make go button for target enabled
        } else {
            form.setTargetTerm(null);
            form.resetForm();
            GlobalVariables.getMessageMap().putError("soucrceTermCode", "error.courseoffering.sourceTerm.inValid");
        }
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=performRollover")
    public ModelAndView performRollover(@ModelAttribute("KualiForm") CourseOfferingRolloverManagementForm form, @SuppressWarnings("unused") BindingResult result,
                                        @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        CourseOfferingViewHelperService helper = getViewHelperService(form);

        if (form.getSourceTerm() == null || form.getTargetTerm() == null) {
            form.setStatusField("(setUp) Source/target term objects appear to be missing");
            return getUIFModelAndView(form);
        }
        form.setStatusField("");
        String sourceTermId = form.getSourceTerm().getId();
        String targetTermId = form.getTargetTerm().getId();

        boolean success = helper.performRollover(sourceTermId, targetTermId, form);
        if (success) {
            form.setRolloverTargetTermCode(form.getTargetTermCode());
            showRolloverResults(form, result, request, response); // TODO: Factor out a common method?
            // Switch to rollover details page
            return start(form, result, request, response);
            //return getUIFModelAndView(form, ROLLOVER_DETAILS_PAGEID);
        } else {
            // Had problems, stay in the same screen
            return getUIFModelAndView(form);
        }
    }

    @RequestMapping(params = "methodToCall=performReverseRollover")
    public ModelAndView performReverseRollover(@ModelAttribute("KualiForm") CourseOfferingRolloverManagementForm form, @SuppressWarnings("unused") BindingResult result,
                                               @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        CourseOfferingViewHelperService helper = getViewHelperService(form);
        if (form.getSourceTerm() == null || form.getTargetTerm() == null) {
            form.setStatusField("(cleanUp) Source/target term objects appear to be missing");
            return getUIFModelAndView(form);
        }
        form.setStatusField("");

        String sourceTermId = form.getSourceTerm().getId();
        String targetTermId = form.getTargetTerm().getId();
        SocRolloverResultInfo info = helper.performReverseRollover(sourceTermId, targetTermId, form);
        if (info != null) {
            form.setStatusField("Num items processed: " + info.getItemsProcessed());
        }
        return getUIFModelAndView(form);
    }

    public CourseOfferingViewHelperService getViewHelperService(CourseOfferingRolloverManagementForm rolloverForm) {
        if (viewHelperService == null) {
            if (rolloverForm.getView().getViewHelperServiceClass() != null) {
                viewHelperService = (CourseOfferingViewHelperService) rolloverForm.getView().getViewHelperService();
            } else {
                viewHelperService = (CourseOfferingViewHelperService) rolloverForm.getPostedView().getViewHelperService();
            }
        }
        return viewHelperService;
    }

    private void _disableReleaseToDeptsIfNeeded(CourseOfferingViewHelperService helper, String targetTermId,
                                                CourseOfferingRolloverManagementForm form) {
        SocInfo socInfo = helper.getMainSoc(targetTermId);
        if (socInfo == null) {
            // Disable if no term found
            form.setReleaseToDeptsInvalidTerm(true);
        } else {
            String stateKey = socInfo.getStateKey();
            if (!CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY.equals(stateKey)) {
                // Assume it's been released if no longer in draft state (TODO: may not be true--revisit)
                form.setSocReleasedToDepts(true);
            } else { // In draft state
                form.setSocReleasedToDepts(false);
            }
        }
    }

    private String _computeRolloverDuration(Date dateInitiated, Date dateCompleted) {
        long diffInMillis = dateCompleted.getTime() - dateInitiated.getTime();
        long diffInSeconds = diffInMillis / 1000;
        int minutes = (int) (diffInSeconds / 60);
        int seconds = (int) (diffInSeconds % 60);
        int hours = minutes / 60;
        minutes = minutes % 60;
        String result = seconds + "s";
        if (minutes > 0 || hours > 0) {
            result = minutes + "m " + result;
        }
        if (hours > 0) {
            result = hours + "h " + result;
        }
        return result;
    }

    private String _createPlural(int count) {
        return count == 1 ? "" : "s";
    }

    private String _createStatusString(SocRolloverResultInfo socRolloverResultInfo) {
        String status = "";
        String stateKey = socRolloverResultInfo.getStateKey();
        if (CourseOfferingSetServiceConstants.SUBMITTED_RESULT_STATE_KEY.equals(stateKey) ||
                CourseOfferingSetServiceConstants.RUNNING_RESULT_STATE_KEY.equals(stateKey)) {
            status = " (in progress)";
        } else if (CourseOfferingSetServiceConstants.ABORTED_RESULT_STATE_KEY.equals(stateKey)) {
            status = " (aborted)";
        }
        return status;
    }

    private void _setStatus(String stateKey, CourseOfferingRolloverManagementForm form) {
        if (CourseOfferingSetServiceConstants.FINISHED_RESULT_STATE_KEY.equals(stateKey)) {
            form.setStatusField("Finished");
            form.setRolloverCompleted(true);
        } else if (CourseOfferingSetServiceConstants.RUNNING_RESULT_STATE_KEY.equals(stateKey) ||
                CourseOfferingSetServiceConstants.SUBMITTED_RESULT_STATE_KEY.equals(stateKey)) {
            form.setStatusField("In Progress");
            form.setRolloverCompleted(false);
        } else if (CourseOfferingSetServiceConstants.ABORTED_RESULT_STATE_KEY.equals(stateKey)) {
            form.setRolloverCompleted(true);
        }
    }

    private void _displayRolloverInfo(SocInfo socInfo, SocRolloverResultInfo socRolloverResultInfo,
                                      CourseOfferingRolloverManagementForm form, CourseOfferingViewHelperService helper,
                                      String stateKey, String targetTermId) {
        if (socInfo != null) {
            // Set some display fields that show friendly, human-readable term data
            String friendlySourceTermDesc = helper.getTermDesc(socInfo.getTermId());
            form.setRolloverSourceTermDesc(friendlySourceTermDesc);
            String friendlyTargetTermDesc = helper.getTermDesc(targetTermId);
            form.setRolloverTargetTermDesc(friendlyTargetTermDesc);
        }
        Date dateInitiated = socRolloverResultInfo.getDateInitiated();
        String startDateStr = helper.formatDateAndTime(dateInitiated);
        form.setDateInitiated(startDateStr);
        // if items skipped is null, then below condition passes and items skipped is calculated
        if (socRolloverResultInfo.getCourseOfferingsCreated() == null || socRolloverResultInfo.getCourseOfferingsCreated().toString().length() < 1) {
            Integer temp = socRolloverResultInfo.getItemsExpected() - socRolloverResultInfo.getItemsProcessed();
            String plural = _createPlural(temp);
            form.setCourseOfferingsAllowed(socRolloverResultInfo.getItemsProcessed() + " transitioned with " + temp + " exception" + plural);
        } else {
            // This is the official way to compute this
            String plural = _createPlural(socRolloverResultInfo.getCourseOfferingsSkipped());
            form.setCourseOfferingsAllowed(socRolloverResultInfo.getCourseOfferingsCreated() + " transitioned with " +
                    socRolloverResultInfo.getCourseOfferingsSkipped() + " exception" + plural);
        }
        String plural = _createPlural(socRolloverResultInfo.getActivityOfferingsSkipped());
        form.setActivityOfferingsAllowed(socRolloverResultInfo.getActivityOfferingsCreated() + " transitioned with " +
                socRolloverResultInfo.getActivityOfferingsSkipped() + " exception" + plural);
        Date dateCompleted = socRolloverResultInfo.getDateCompleted();
        String updatedDateStr = helper.formatDateAndTime(dateCompleted);
        // The status displays whether the time is in progress or aborted or nothing if it's completed.
        String status = _createStatusString(socRolloverResultInfo);
        if ((CourseOfferingSetServiceConstants.SUBMITTED_RESULT_STATE_KEY.equals(stateKey) ||
                CourseOfferingSetServiceConstants.RUNNING_RESULT_STATE_KEY.equals(stateKey))) {
            form.setDateCompleted("Rollover in progress");  // DanS doesn't want a date completed if still in progress
        } else {
            form.setDateCompleted(updatedDateStr + status);
        }
        // Set value on how long rollover has been running
        String rolloverDuration = _computeRolloverDuration(dateInitiated, dateCompleted);
        form.setRolloverDuration(rolloverDuration + status);
    }

    private void _displayRolloverItems(CourseOfferingRolloverManagementForm form,
                                       List<SocRolloverResultItemInfo> socRolloverResultItemInfos,
                                       List<SocRolloverResultItemInfo> socRolloverResultItemInfosCopy)
            throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException,
            OperationFailedException {
        // Clear out the existing list of result items
        form.getSocRolloverResultItems().clear();

        for (SocRolloverResultItemInfo socRolloverResultItemInfo : socRolloverResultItemInfosCopy) {
            if (CourseOfferingSetServiceConstants.SUCCESSFUL_RESULT_ITEM_STATES.contains(socRolloverResultItemInfo.getStateKey())) {
                socRolloverResultItemInfos.remove(socRolloverResultItemInfo);
            } else {
                String courseOfferingId = socRolloverResultItemInfo.getTargetCourseOfferingId();
                if (courseOfferingId == null || courseOfferingId.isEmpty()) {
                    courseOfferingId = socRolloverResultItemInfo.getSourceCourseOfferingId();
                }

                CourseOfferingInfo courseOfferingInfo = _getCourseOfferingService().getCourseOffering(courseOfferingId, new ContextInfo());
                SocRolloverResultItemWrapper socRolloverResultItemWrapper = new SocRolloverResultItemWrapper();
                socRolloverResultItemWrapper.setCourse(courseOfferingInfo.getCourseOfferingCode());
                if (socRolloverResultItemInfo.getMessage() != null) {
                    socRolloverResultItemWrapper.setMessage(socRolloverResultItemInfo.getMessage().getPlain());
                }
                socRolloverResultItemWrapper.setState(socRolloverResultItemInfo.getStateKey());

                try {
                    StateInfo stateInfo = this._getStateService().getState(socRolloverResultItemInfo.getStateKey(), ContextUtils.getContextInfo());
                    if (stateInfo != null) {
                        socRolloverResultItemWrapper.setStateName((stateInfo.getName() != null) ? stateInfo.getName() : socRolloverResultItemInfo.getStateKey());
                    }
                } catch (DoesNotExistException ex) {
                    socRolloverResultItemWrapper.setStateName(socRolloverResultItemInfo.getStateKey());
                }


                form.getSocRolloverResultItems().add(socRolloverResultItemWrapper);
            }
        }
    }

    // This method displays rollover result Infos for specific target term.
    @RequestMapping(params = "methodToCall=showRolloverResults")
    public ModelAndView showRolloverResults(@ModelAttribute("KualiForm") CourseOfferingRolloverManagementForm form, @SuppressWarnings("unused") BindingResult result,
                                            @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        //helper class for courseOfferingSetService
        CourseOfferingViewHelperService helper = getViewHelperService(form);
        //To fetch Term by code which is desirable.
        String targetTermCode = form.getRolloverTargetTermCode();
        List<TermInfo> termList = helper.findTermByTermCode(targetTermCode);
        if (termList.isEmpty()) {
            GlobalVariables.getMessageMap().putError("rolloverTargetTermCode", "error.rollover.targetTerm.noResults", targetTermCode);
            form.resetForm(); // TODO: Does this make sense?  I don't think so. cclin
            return getUIFModelAndView(form);
        } else {
            TermInfo targetTerm = termList.get(0);
            form.setTargetTerm(targetTerm);
            form.setTargetTermCode(targetTermCode);
            String targetTermId = targetTerm.getId();
            // Get rollover result info for target term
            List<SocRolloverResultInfo> socRolloverResultInfos = helper.findRolloverByTerm(targetTermId);
            if (socRolloverResultInfos == null || socRolloverResultInfos.isEmpty()) {
                GlobalVariables.getMessageMap().putError("rolloverTargetTermCode", "error.rollover.targetTerm.noResults", targetTermCode);
                form.resetForm(); // TODO: Does this make sense?  I don't think so. cclin
                return getUIFModelAndView(form);
            } else {
                if (socRolloverResultInfos.size() > 1) {
                    LOGGER.warn("Multiple Soc Rollover Results Found");
                }
                _disableReleaseToDeptsIfNeeded(helper, targetTermId, form);
                SocRolloverResultInfo socRolloverResultInfo = socRolloverResultInfos.get(0);
                String stateKey = socRolloverResultInfo.getStateKey();
                _setStatus(stateKey, form);
                // SocInfo service to get Source Term Id
                SocInfo socInfo = _getSocService().getSoc(socRolloverResultInfo.getSourceSocId(), new ContextInfo());
                // Put info in the display fields on the left hand side
                _displayRolloverInfo(socInfo, socRolloverResultInfo, form, helper, stateKey, targetTermId);

                // CourseOfferingSet service to get Soc Rollover ResultItems by socResultItemInfo id
                try {
                    List<SocRolloverResultItemInfo> socRolloverResultItemInfos =
                            _getSocService().getSocRolloverResultItemsByResultId(socRolloverResultInfo.getId(), new ContextInfo());
                    List<SocRolloverResultItemInfo> socRolloverResultItemInfosCopy =
                            new CopyOnWriteArrayList<SocRolloverResultItemInfo>(socRolloverResultItemInfos);

                    _displayRolloverItems(form, socRolloverResultItemInfos, socRolloverResultItemInfosCopy);
                } catch (UnhandledException ue) {
                    throw new RuntimeException(ue);
                } catch (DoesNotExistException dne) {
                    throw new RuntimeException(dne);
                }
            }
        }
        return getUIFModelAndView(form, ROLLOVER_DETAILS_PAGEID);
    }

    /**
     * This is used in the release to depts page from
     * CourseOfferingRolloverManagement-ReleaseToDeptsPage.xml
     */
    @RequestMapping(params = "methodToCall=releaseToDepts")
    public ModelAndView releaseToDepts(@ModelAttribute("KualiForm") CourseOfferingRolloverManagementForm form, @SuppressWarnings("unused") BindingResult result,
                                       @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        LOGGER.info("releaseToDepts");
        CourseOfferingViewHelperService helper = getViewHelperService(form);
        boolean accept = form.getAcceptIndicator();
        TermInfo targetTerm = form.getTargetTerm();
        if (!accept) {
            // Didn't click approval
            GlobalVariables.getMessageMap().putError("approveCheckbox", "error.rollover.release.notApproved");
        } else if (targetTerm == null) {
            // Didn't get term info from Rollover Results page
            GlobalVariables.getMessageMap().putError("approveCheckbox", "error.rollover.invalidTerm");
        } else {
            // We're good!
            LOGGER.info("Ready to release to depts");
            SocInfo socInfo = helper.getMainSoc(targetTerm.getId());
            if (!socInfo.getStateKey().equals(CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY)) {
                // If it's not draft, then set variable to disable release to depts in the UI
                form.setSocReleasedToDepts(true);
            } else {
                // It's draft, so change to state to open
                _getSocService().changeSocState(socInfo.getId(), CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY, new ContextInfo());
                form.setSocReleasedToDepts(true);
            }
            // Do a refresh of the data on rollover details
            showRolloverResults(form, result, request, response);
            KSUifUtils.addGrowlMessageIcon(GrowlIcon.SUCCESS, CourseOfferingConstants.COURSEOFFERING_ROLLOVER_RELEASE_TO_DEPTS_SUCCESSFULLY);
        }
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=checkApproval")
    public ModelAndView checkApproval(@ModelAttribute("KualiForm") CourseOfferingRolloverManagementForm form, @SuppressWarnings("unused") BindingResult result,
                                      @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        LOGGER.info("checkApproval " + form.getAcceptIndicator());
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=redoRollover")
    public ModelAndView redoRollover(@ModelAttribute("KualiForm") CourseOfferingRolloverManagementForm form, @SuppressWarnings("unused") BindingResult result,
                                     @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        LOGGER.info("redoRollover ");
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=confirmReleaseToDepts")
    public ModelAndView confirmReleaseToDepts(@ModelAttribute("KualiForm") CourseOfferingRolloverManagementForm form, @SuppressWarnings("unused") BindingResult result,
                                              @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        LOGGER.info("confirmReleaseToDepts ");
        return getUIFModelAndView(form, ROLLOVER_CONFIRM_RELEASE);
    }

    private CourseOfferingSetService _getSocService() {
        if (socService == null) {
            socService = (CourseOfferingSetService) GlobalResourceLoader.getService(new QName(CourseOfferingSetServiceConstants.NAMESPACE,
                    CourseOfferingSetServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return socService;
    }

    private CourseOfferingService _getCourseOfferingService() {
        if (coService == null) {
            coService = (CourseOfferingService) GlobalResourceLoader.getService(new QName(CourseOfferingServiceConstants.NAMESPACE,
                    CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return coService;
    }

    private StateService _getStateService() {
        if (stateService == null) {
            stateService = (StateService) GlobalResourceLoader.getService(new QName(StateServiceConstants.NAMESPACE,
                    StateServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return stateService;
    }
}
