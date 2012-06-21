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

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new CourseOfferingRolloverManagementForm();
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        CourseOfferingRolloverManagementForm theForm = (CourseOfferingRolloverManagementForm) form;
        Date date = Calendar.getInstance().getTime();
        System.err.println(date.toString() + " ");
        System.err.println(theForm);
        String rolloverTerm = theForm.getRolloverTargetTermCode();

        try{
            if(rolloverTerm!= null && !"".equals(rolloverTerm)){
                return goRolloverTerm(theForm, result, request, response);
            }
        }catch (Exception ex){
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

    //This method looks for rollover resultInfos for specific target term.
    @RequestMapping(params = "methodToCall=goRolloverTerm")
    public ModelAndView goRolloverTerm(@ModelAttribute("KualiForm") CourseOfferingRolloverManagementForm form, BindingResult result,
                                       HttpServletRequest request, HttpServletResponse response) throws Exception {
        //helper class for courseOfferingSetService
        CourseOfferingViewHelperService helper = getViewHelperService(form);
        //To fetch Term by code which is desirable.
        List<TermInfo> termList = helper.findTermByTermCode(form.getRolloverTargetTermCode());
        if (termList.size() != 0) {
            String targetTermId = termList.get(0).getId();
            List<SocRolloverResultInfo> socRolloverResultInfos = helper.findRolloverByTerm(targetTermId);
            if (socRolloverResultInfos != null & socRolloverResultInfos.size() != 0) {
                if (socRolloverResultInfos.size() > 1) {
                    logger.warn("Multiple Soc Rollover Results Found");
                }
                SocRolloverResultInfo socRolloverResultInfo = socRolloverResultInfos.get(0);
                if (socRolloverResultInfo.getStateKey().equals(CourseOfferingSetServiceConstants.FINISHED_RESULT_STATE_KEY)) {
                    form.setStatusField("Finished");
                } else if (socRolloverResultInfo.getStateKey().equals(CourseOfferingSetServiceConstants.RUNNING_RESULT_STATE_KEY)) {
                    form.setStatusField("In Progress");
                }
                //SocInfo service to get Source Term Id
                SocInfo socInfo = _getSocService().getSoc(socRolloverResultInfo.getSourceSocId(), new ContextInfo());

                if (socInfo != null) {//TODO delete this code block, there is weird semantic id logic happening
                    String friendlySourceTermDesc = helper.getTermDesc(socInfo.getTermId());
                    form.setRolloverSourceTermDesc(friendlySourceTermDesc);
                    String friendlyTargetTermDesc = helper.getTermDesc(targetTermId);
                    form.setRolloverTargetTermDesc(friendlyTargetTermDesc);
                }
                Date dateInitiated = socRolloverResultInfo.getMeta().getCreateTime();
                String startDateStr = helper.formatDateAndTime(dateInitiated);
                form.setDateInitiated(startDateStr);
                //if items skipped is null, then below condition passess and items skipped is calculated
                if (socRolloverResultInfo.getCourseOfferingsCreated() == null || socRolloverResultInfo.getCourseOfferingsCreated().toString().length() < 1) {
                    Integer temp = socRolloverResultInfo.getItemsExpected() - socRolloverResultInfo.getItemsProcessed();
                    form.setCourseOfferingsAllowed(socRolloverResultInfo.getItemsProcessed() + " transitioned with " + temp + " exceptions");
                } else {
                    form.setCourseOfferingsAllowed(socRolloverResultInfo.getItemsProcessed() + "transitioned with " + socRolloverResultInfo.getCourseOfferingsSkipped() + " exceptions");
                }
                Date dateCompleted = socRolloverResultInfo.getMeta().getUpdateTime();
                String updatedDateStr = helper.formatDateAndTime(dateCompleted);
                form.setDateCompleted(updatedDateStr);
                //CourseOfferingSet service to get Soc Rollover ResultItems by socResultItemInfo id
                try {
                    List<SocRolloverResultItemInfo> socRolloverResultItemInfos =
                            _getSocService().getSocRolloverResultItemsByResultId(socRolloverResultInfo.getId(), new ContextInfo());
                    List<SocRolloverResultItemInfo> socRolloverResultItemInfos1 = new CopyOnWriteArrayList<SocRolloverResultItemInfo>(socRolloverResultItemInfos);
                    for (SocRolloverResultItemInfo socRolloverResultItemInfo : socRolloverResultItemInfos1) {
                        if (CourseOfferingSetServiceConstants.SUCCESS_RESULT_ITEM_STATE_KEY.equalsIgnoreCase(socRolloverResultItemInfo.getStateKey())) {
                            socRolloverResultItemInfos.remove(socRolloverResultItemInfo);
                        } else {
                            String courseOfferingId = socRolloverResultItemInfo.getTargetCourseOfferingId();
                            if(courseOfferingId == null || "".equals(courseOfferingId)){
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
            } else {
                form.resetForm();
            }
        } else {
            form.resetForm();

        }
        return getUIFModelAndView(form, ROLLOVER_DETAILS_PAGEID);
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
