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

import org.apache.commons.lang.UnhandledException;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.dto.TermInfo;
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
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
@Controller
@RequestMapping(value = "/courseOfferingRollover")
public class CourseOfferingRolloverController extends UifControllerBase {
    private CourseOfferingViewHelperService viewHelperService;
    private CourseOfferingSetService socService;
    private CourseOfferingService coService;

    final Logger logger = Logger.getLogger(CourseOfferingRolloverController.class);
    public static final String ROLLOVER_DETAILS_PAGEID = "selectTermForRolloverDetails";
    public static final String ROLLOVER_CONFIRM_RELEASE = "releaseToDepts";

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new CourseOfferingRolloverManagementForm();
    }

    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=startPerformRollover")
    public ModelAndView startPerformRollover(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        CourseOfferingRolloverManagementForm theForm = (CourseOfferingRolloverManagementForm) form;
        System.err.println("startPerformRollover");
        return getUIFModelAndView(theForm);
        // return super.start(theForm, result, request, response);
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        CourseOfferingRolloverManagementForm theForm = (CourseOfferingRolloverManagementForm) form;
        System.err.println("start=====");
        return getUIFModelAndView(theForm);
        // return super.start(theForm, result, request, response);
    }

    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=startRolloverDetails")
    public ModelAndView startRolloverDetails(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                                             HttpServletRequest request, HttpServletResponse response) {
        CourseOfferingRolloverManagementForm theForm = (CourseOfferingRolloverManagementForm) form;
        System.err.println("startRolloverDetails");
        String rolloverTerm = theForm.getRolloverTargetTermCode();

        try {
            if (rolloverTerm != null && !"".equals(rolloverTerm)) {
                return showRolloverResults(theForm, result, request, response);
            }
        } catch (Exception ex){
            return getUIFModelAndView(theForm);
        }

        return getUIFModelAndView(theForm);
        // return super.start(theForm, result, request, response);
    }

    @RequestMapping(params = "methodToCall=goTargetTerm")
    public ModelAndView goTargetTerm(@ModelAttribute("KualiForm") CourseOfferingRolloverManagementForm form, BindingResult result,
                                     HttpServletRequest request, HttpServletResponse response) throws Exception {
        CourseOfferingViewHelperService helper = getViewHelperService(form);
        List<TermInfo> termList = helper.findTermByTermCode(form.getTargetTermCode());
        if (termList != null && termList.size() == 1) {
            //validation to check if already rollover target term exists..
            List<String> coIds = this._getCourseOfferingService().getCourseOfferingIdsByTerm(termList.get(0).getId(), true, new ContextInfo());
            if (!coIds.isEmpty()) {
                // Print error message if there are course offerings in the target term
                GlobalVariables.getMessageMap().putError("targetTermCode", "error.courseoffering.rollover.targetTermExists");
                form.resetForm();
                return getUIFModelAndView(form);
            }
            // Get first term
            TermInfo matchingTerm = termList.get(0);
            String targetTermCode = matchingTerm.getCode();
            form.setDisplayedTargetTermCode(targetTermCode);
            // Set the start date
            Date startDate = matchingTerm.getStartDate();
            SimpleDateFormat format = new SimpleDateFormat("EEE, MMMMM d, yyyy");
            String startDateStr = format.format(startDate);
            form.setTargetTermStartDate(startDateStr);
            // Set the end date
            Date endDate = matchingTerm.getEndDate();
            String endDateStr = format.format(endDate);
            form.setTargetTermEndDate(endDateStr);
            // TODO: Put in last rollover date (Kirk says this may be unnecessary in new wireframes 5/18/2012)
            form.setTargetTerm(matchingTerm);
            form.setIsGoSourceButtonDisabled(false); // Make go button for source enabled
        } else {
            form.setTargetTerm(null);
            form.resetForm();
            GlobalVariables.getMessageMap().putError("targetTermCode", "error.courseoffering.targetTerm.inValid");
        }
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=goSourceTerm")
    public ModelAndView goSourceTerm(@ModelAttribute("KualiForm") CourseOfferingRolloverManagementForm form, BindingResult result,
                                     HttpServletRequest request, HttpServletResponse response) throws Exception {
        // validation to check for valid term.
        if (form.getDisplayedTargetTermCode() == null || form.getDisplayedTargetTermCode().length() == 0) {
            GlobalVariables.getMessageMap().putError("targetTermCode", "error.submit.sourceTerm");
            return getUIFModelAndView(form);
        }
        CourseOfferingViewHelperService helper = getViewHelperService(form);
        
        // validation to check for like terms and target term year comes before source term year.
        String targetTermCd = form.getTargetTermCode();
        String sourceTermCd = form.getSourceTermCode();
        TermInfo targetTerm = helper.findTermByTermCode(targetTermCd).get(0);
        TermInfo sourceTerm = helper.findTermByTermCode(sourceTermCd).get(0);
        boolean likeTerms = sourceTerm.getTypeKey().equals(targetTerm.getTypeKey());
        boolean sourcePrecedesTarget = sourceTerm.getStartDate().before(targetTerm.getStartDate());
        boolean sourceTermHasSoc = helper.termHasSoc(sourceTerm.getId(), form);
        if (!likeTerms) {
            GlobalVariables.getMessageMap().putError("sourceTermCode", "error.likeTerms.validation");
            form.setIsRolloverButtonDisabled(true);
            return getUIFModelAndView(form);
        } else if (!sourcePrecedesTarget) {
            GlobalVariables.getMessageMap().putError("sourceTermCode", "error.years.validation");
            form.setIsRolloverButtonDisabled(true);
            return getUIFModelAndView(form);
        } else if (!sourceTermHasSoc) {
            GlobalVariables.getMessageMap().putError("sourceTermCode", "error.rollover.sourceTerm.noSoc");
            form.setIsRolloverButtonDisabled(true);
            return getUIFModelAndView(form);
        }

        List<TermInfo> termList = helper.findTermByTermCode(form.getSourceTermCode());
        if (termList != null && termList.size() == 1) {
            // Get first term
            TermInfo matchingTerm = termList.get(0);
            String sourceTermCode = matchingTerm.getCode();
            form.setDisplayedSourceTermCode(sourceTermCode);
            // Set the start date
            Date startDate = matchingTerm.getStartDate();
            SimpleDateFormat format = new SimpleDateFormat("EEE, MMMMM d, yyyy");
            String startDateStr = format.format(startDate);
            form.setSourceTermStartDate(startDateStr);
            // Set the end date
            Date endDate = matchingTerm.getEndDate();
            String endDateStr = format.format(endDate);
            form.setSourceTermEndDate(endDateStr);
            form.setSourceTerm(matchingTerm);
            form.setIsRolloverButtonDisabled(false); // Enable the button
        } else {
            form.setTargetTerm(null);
            form.resetForm();
            GlobalVariables.getMessageMap().putError("soucrceTermCode", "error.courseoffering.sourceTerm.inValid");
        }
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=performRollover")
    public ModelAndView performRollover(@ModelAttribute("KualiForm") CourseOfferingRolloverManagementForm form, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
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
            return start(form,result,request,response);
            //return getUIFModelAndView(form, ROLLOVER_DETAILS_PAGEID);
        } else{
            // Had problems, stay in the same screen
            return getUIFModelAndView(form);
        }
    }

    @RequestMapping(params = "methodToCall=setUpSourceTerm")
    public ModelAndView setUpSourceTerm(@ModelAttribute("KualiForm") CourseOfferingRolloverManagementForm form, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        CourseOfferingViewHelperService helper = getViewHelperService(form);

        if (form.getSourceTerm() == null || form.getTargetTerm() == null) {
            form.setStatusField("(setUp) Source/target term objects appear to be missing");
            return getUIFModelAndView(form);
        }
        form.setStatusField("");
        // Delete previous SOC and course offerings (allows for us to redefine SOC/course offering later on

        boolean success = helper.cleanSourceTerm(form.getSourceTerm().getId(), form);
        if (success) {
            helper.createSocCoFoAoForTerm(form.getSourceTerm().getId(), form);
        }
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=performReverseRollover")
    public ModelAndView performReverseRollover(@ModelAttribute("KualiForm") CourseOfferingRolloverManagementForm form, BindingResult result,
                                               HttpServletRequest request, HttpServletResponse response) throws Exception {
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
            form.setReleaseToDeptsDisabled(true);
            form.setReleaseToDeptsInvalidTerm(true);
        } else if (!socInfo.getStateKey().equals(CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY)) {
            // Disable if not in draft state
            form.setReleaseToDeptsDisabled(true);
            form.setReleaseToDeptsAlreadyReleased(true);
            form.setIsReleasedToDepts(true); // Assume it's been released if no longer in draft state
        }
    }

    private String _computeRolloverDuration(Date dateInitiated, Date dateCompleted) {
        long diffInMillis = dateCompleted.getTime() - dateInitiated.getTime();
        long diffInSeconds = diffInMillis / 1000;
        int minutes = (int)(diffInSeconds / 60);
        int seconds = (int)(diffInSeconds % 60);
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

    // This method displays rollover result Infos for specific target term.
    @RequestMapping(params = "methodToCall=showRolloverResults")
    public ModelAndView showRolloverResults(@ModelAttribute("KualiForm") CourseOfferingRolloverManagementForm form, BindingResult result,
                                            HttpServletRequest request, HttpServletResponse response) throws Exception {
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
            String targetTermId = targetTerm.getId();
            // Get rollover result info for target term
            List<SocRolloverResultInfo> socRolloverResultInfos = helper.findRolloverByTerm(targetTermId);
            if (socRolloverResultInfos == null || socRolloverResultInfos.isEmpty()) {
                GlobalVariables.getMessageMap().putError("rolloverTargetTermCode", "error.rollover.targetTerm.noResults", targetTermCode);
                form.resetForm(); // TODO: Does this make sense?  I don't think so. cclin
                return getUIFModelAndView(form);
            } else {
                if (socRolloverResultInfos.size() > 1) {
                    logger.warn("Multiple Soc Rollover Results Found");
                }
                _disableReleaseToDeptsIfNeeded(helper, targetTermId, form);
                SocRolloverResultInfo socRolloverResultInfo = socRolloverResultInfos.get(0);
                if (socRolloverResultInfo.getStateKey().equals(CourseOfferingSetServiceConstants.FINISHED_RESULT_STATE_KEY)) {
                    form.setStatusField("Finished");
                } else if (socRolloverResultInfo.getStateKey().equals(CourseOfferingSetServiceConstants.RUNNING_RESULT_STATE_KEY)) {
                    form.setStatusField("In Progress");
                }
                // SocInfo service to get Source Term Id
                SocInfo socInfo = _getSocService().getSoc(socRolloverResultInfo.getSourceSocId(), new ContextInfo());

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
                    form.setCourseOfferingsAllowed(socRolloverResultInfo.getItemsProcessed() + " transitioned with " + temp + " exceptions");
                } else {
                    // This is the official way to compute this
                    form.setCourseOfferingsAllowed(socRolloverResultInfo.getCourseOfferingsCreated() + " transitioned with " +
                            socRolloverResultInfo.getCourseOfferingsSkipped() + " exceptions");
                }
                form.setActivityOfferingsAllowed(socRolloverResultInfo.getActivityOfferingsCreated() + " transitioned with " +
                        socRolloverResultInfo.getActivityOfferingsSkipped() + " exceptions");
                Date dateCompleted = socRolloverResultInfo.getDateCompleted();
                String updatedDateStr = helper.formatDateAndTime(dateCompleted);
                form.setDateCompleted(updatedDateStr);
                String rolloverDuration = _computeRolloverDuration(dateInitiated, dateCompleted);
                form.setRolloverDuration(rolloverDuration);
                // CourseOfferingSet service to get Soc Rollover ResultItems by socResultItemInfo id
                try {
                    List<SocRolloverResultItemInfo> socRolloverResultItemInfos =
                            _getSocService().getSocRolloverResultItemsByResultId(socRolloverResultInfo.getId(), new ContextInfo());
                    List<SocRolloverResultItemInfo> socRolloverResultItemInfos1 = new CopyOnWriteArrayList<SocRolloverResultItemInfo>(socRolloverResultItemInfos);
                    for (SocRolloverResultItemInfo socRolloverResultItemInfo : socRolloverResultItemInfos1) {
                        if (CourseOfferingSetServiceConstants.SUCCESS_RESULT_ITEM_STATE_KEY.equalsIgnoreCase(socRolloverResultItemInfo.getStateKey())) {
                            socRolloverResultItemInfos.remove(socRolloverResultItemInfo);
                        } else {
                            String courseOfferingId = socRolloverResultItemInfo.getTargetCourseOfferingId();
                            if (courseOfferingId == null || "".equals(courseOfferingId)){
                                courseOfferingId = socRolloverResultItemInfo.getSourceCourseOfferingId();
                            }

                            CourseOfferingInfo courseOfferingInfo = _getCourseOfferingService().getCourseOffering(courseOfferingId, new ContextInfo());
                            SocRolloverResultItemWrapper socRolloverResultItemWrapper = new SocRolloverResultItemWrapper();
                            socRolloverResultItemWrapper.setCourse(courseOfferingInfo.getCourseOfferingCode());
                            if (socRolloverResultItemInfo.getMessage() != null) {
                                socRolloverResultItemWrapper.setMessage(socRolloverResultItemInfo.getMessage().getPlain());
                            }
                            socRolloverResultItemWrapper.setState(socRolloverResultItemInfo.getStateKey());
                            form.getSocRolloverResultItems().add(socRolloverResultItemWrapper);
                        }
                    }
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
     * This is used in the rollover results page to
     */
    @RequestMapping(params = "methodToCall=releaseToDepts")
    public ModelAndView releaseToDepts(@ModelAttribute("KualiForm") CourseOfferingRolloverManagementForm form, BindingResult result,
                                            HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.err.println("releaseToDepts");
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
            System.err.println("Ready to release to depts");
            SocInfo socInfo = helper.getMainSoc(targetTerm.getId());
            if (!socInfo.getStateKey().equals(CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY)) {
                // If it's not draft, then set variable to disable release to depts in the UI
                form.setReleaseToDeptsDisabled(true);
                form.setReleaseToDeptsAlreadyReleased(true);
            } else {
                // It's draft, so change to state to open
                socInfo.setStateKey(CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY);
                // Persist the state change
                _getSocService().updateSoc(socInfo.getId(), socInfo, new ContextInfo());
                form.setReleaseToDeptsDisabled(true);
                form.setReleaseToDeptsAlreadyReleased(true);
            }
        }
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=startReleaseToDepts")
    public ModelAndView startReleaseToDepts(@ModelAttribute("KualiForm") CourseOfferingRolloverManagementForm form, BindingResult result,
                                       HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.err.println("startReleaseToDepts");
        String targetTermCode = form.getRolloverTargetTermCode();
        if (targetTermCode == null || targetTermCode.trim().isEmpty()) {
            form.setReleaseToDeptsDisabled(true);
        }
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=checkApproval")
    public ModelAndView checkApproval(@ModelAttribute("KualiForm") CourseOfferingRolloverManagementForm form, BindingResult result,
                                            HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.err.println("checkApproval " + form.getAcceptIndicator());
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=redoRollover")
    public ModelAndView redoRollover(@ModelAttribute("KualiForm") CourseOfferingRolloverManagementForm form, BindingResult result,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.err.println("redoRollover ");
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=confirmReleaseToDepts")
    public ModelAndView confirmReleaseToDepts(@ModelAttribute("KualiForm") CourseOfferingRolloverManagementForm form, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.err.println("confirmReleaseToDepts ");
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
}
