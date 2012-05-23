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
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingRolloverManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.service.CourseOfferingViewHelperService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
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

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new CourseOfferingRolloverManagementForm();
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        CourseOfferingRolloverManagementForm theForm = (CourseOfferingRolloverManagementForm)form;
        Date date = Calendar.getInstance().getTime();
        System.err.println(date.toString() + " ");
        System.err.println(theForm);
        return getUIFModelAndView(theForm);
        // return super.start(theForm, result, request, response);
    }

    @RequestMapping(params = "methodToCall=goTargetTerm")
    public ModelAndView goTargetTerm(@ModelAttribute("KualiForm") CourseOfferingRolloverManagementForm form, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        CourseOfferingViewHelperService helper = getViewHelperService(form);
        List<TermInfo> termList = helper.findTermByTermCode(form.getTargetTermCode());
        if (termList != null && termList.size() == 1) {
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
        } else {
            form.setTargetTerm(null);
        }
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=goSourceTerm")
    public ModelAndView goSourceTerm(@ModelAttribute("KualiForm") CourseOfferingRolloverManagementForm form, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        CourseOfferingViewHelperService helper = getViewHelperService(form);
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
        } else {
            form.setTargetTerm(null);
        }
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=performRollover")
    public ModelAndView performRollover(@ModelAttribute("KualiForm") CourseOfferingRolloverManagementForm form, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        CourseOfferingViewHelperService helper =  getViewHelperService(form);

        if (form.getSourceTerm() == null || form.getTargetTerm() == null) {
            form.setStatusField("(setUp) Source/target term objects appear to be missing");
            return getUIFModelAndView(form);
        }
        form.setStatusField("");
        String sourceTermId = form.getSourceTerm().getId();
        String targetTermId = form.getTargetTerm().getId();

        helper.performRollover(sourceTermId, targetTermId, form);
        return getUIFModelAndView(form);
    }

    @RequestMapping(params = "methodToCall=setUpSourceTerm")
    public ModelAndView setUpSourceTerm(@ModelAttribute("KualiForm") CourseOfferingRolloverManagementForm form, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        CourseOfferingViewHelperService helper =  getViewHelperService(form);
        
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
        CourseOfferingViewHelperService helper =  getViewHelperService(form);
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

    @RequestMapping(params = "methodToCall=cleanTargetTerm")
    public ModelAndView cleanTargetTerm(@ModelAttribute("KualiForm") CourseOfferingRolloverManagementForm form, BindingResult result,
                                               HttpServletRequest request, HttpServletResponse response) throws Exception {
        CourseOfferingViewHelperService helper =  getViewHelperService(form);
        if (form.getSourceTerm() == null || form.getTargetTerm() == null) {
            form.setStatusField("(cleanUp) Source/target term objects appear to be missing");
            return getUIFModelAndView(form);
        }
        form.setStatusField("");

        helper.cleanTargetTerm(form.getTargetTerm().getId(), form);
        return getUIFModelAndView(form);
    }

    public CourseOfferingViewHelperService getViewHelperService(CourseOfferingRolloverManagementForm rolloverForm){
        if (viewHelperService == null) {
            if (rolloverForm.getView().getViewHelperServiceClass() != null){
                viewHelperService = (CourseOfferingViewHelperService) rolloverForm.getView().getViewHelperService();
            }else{
                viewHelperService= (CourseOfferingViewHelperService) rolloverForm.getPostedView().getViewHelperService();
            }
        }
        return viewHelperService;
    }

    //This method looks for rollover resultInfos for specific target term.
    @RequestMapping(params = "methodToCall=goRolloverTerm")
    public ModelAndView goRolloverTerm(@ModelAttribute("KualiForm") CourseOfferingRolloverManagementForm form, BindingResult result,
                                     HttpServletRequest request, HttpServletResponse response) throws Exception {

        CourseOfferingViewHelperService helper = getViewHelperService(form);
        //helper class to do lookup for courseOfferingSetService
        List<SocRolloverResultInfo> socRolloverResultInfos = helper.findRolloverByTerm(form.getRolloverTargetTerm());
        if(socRolloverResultInfos!=null & socRolloverResultInfos.size()!=0){
            SocRolloverResultInfo socRolloverResultInfo = socRolloverResultInfos.get(0);
            if(socRolloverResultInfo.getStateKey().equals(CourseOfferingSetServiceConstants.FINISHED_RESULT_STATE_KEY))
               form.setStatusField("Finished");
            else if(socRolloverResultInfo.getStateKey().equals(CourseOfferingSetServiceConstants.RUNNING_RESULT_STATE_KEY))
                    form.setStatusField("In Progress");
            //SocInfo service to get Source Term Id
            SocInfo socInfo = _getSocService().getSoc(socRolloverResultInfo.getSourceSocId(),new ContextInfo());
            if(socInfo!=null)
               form.setRolloverSourceTerm(socInfo.getTermId());

            Date dateInitiated = socRolloverResultInfo.getMeta().getCreateTime();
            SimpleDateFormat format = new SimpleDateFormat("MMMM d yyyy, hh:mm");
            String startDate = format.format(dateInitiated);
            form.setDateInitiated(startDate);
            //if items skipped is null, then below condition passess and items skipped is calculated
            if(socRolloverResultInfo.getItemsSkipped()==null || socRolloverResultInfo.getItemsSkipped().toString().length()<1) {
               Integer temp = socRolloverResultInfo.getItemsExpected()-socRolloverResultInfo.getItemsProcessed();
               form.setCourseOfferingsAllowed(socRolloverResultInfo.getItemsProcessed() + " transitioned with "+ temp + " exceptions");
            }
            else {
                  form.setCourseOfferingsAllowed(socRolloverResultInfo.getItemsProcessed() + "transitioned with "+ socRolloverResultInfo.getItemsSkipped() + " exceptions");
            }
            Date dateCompleted = socRolloverResultInfo.getMeta().getUpdateTime();
            format = new SimpleDateFormat("MMMM d yyyy, hh:mm");
            String updatedDate = format.format(dateCompleted);
            form.setDateCompleted(updatedDate);
            //CourseOfferingSet service to get Soc Rollover ResultItems by socResultItemInfo id
             try{
                 List<SocRolloverResultItemInfo> socRolloverResultItemInfos =
                           _getSocService().getSocRolloverResultItemsByResultId(socRolloverResultInfo.getId(),new ContextInfo());
                 form.setSocRolloverResultItemInfos(socRolloverResultItemInfos);
             }catch(UnhandledException ue){

             }
             catch(DoesNotExistException dne){

             }
        }
        return getUIFModelAndView(form);
    }

    private CourseOfferingSetService _getSocService() {
        if (socService == null) {
            socService = (CourseOfferingSetService) GlobalResourceLoader.getService(new QName(CourseOfferingSetServiceConstants.NAMESPACE,
                    CourseOfferingSetServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return socService;
    }
}
