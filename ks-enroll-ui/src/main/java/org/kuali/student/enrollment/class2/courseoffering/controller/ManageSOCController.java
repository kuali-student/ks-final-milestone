package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.class2.courseoffering.form.ManageSOCForm;
import org.kuali.student.enrollment.class2.courseoffering.service.ManageSOCViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: venkat
 * Date: 9/14/12
 * Time: 3:09 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "/manageSOC")
public class ManageSOCController extends UifControllerBase {

    final static Logger LOG = Logger.getLogger(ManageSOCController.class);

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        ManageSOCForm form = new ManageSOCForm();

        try {
            List<StateInfo>  allSOCStates = CourseOfferingResourceLoader.loadStateService().getStatesByLifecycle(CourseOfferingSetServiceConstants.SOC_LIFECYCLE_KEY, ContextUtils.createDefaultContextInfo());
            for (StateInfo stateInfo : allSOCStates) {
                form.getSocStateKeys2Names().put(stateInfo.getKey(), stateInfo.getName());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return form;
    }

    @RequestMapping(params = "methodToCall=lockSOC")
    public ModelAndView lockSOC(@ModelAttribute("KualiForm") ManageSOCForm socForm, BindingResult result,
                                            HttpServletRequest request, HttpServletResponse response){

        if (socForm.getSocInfo() == null){
            throw new RuntimeException("SocInfo not exists in the form. Please enter the term code and click on GO button");
        }

        if (!StringUtils.equals(CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY,socForm.getSocInfo().getStateKey())){
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM,"SOC should be in open state to lock");
            return getUIFModelAndView(socForm);
        }

        ManageSOCViewHelperService viewHelper = (ManageSOCViewHelperService)socForm.getView().getViewHelperService();
        viewHelper.lockSOC(socForm);

        return buildModel(socForm, result, request, response);
    }

    @RequestMapping(params = "methodToCall=sendApprovedActivitiesToScheduler")
    public ModelAndView sendApprovedActivitiesToScheduler (@ModelAttribute("KualiForm") ManageSOCForm socForm, BindingResult result,
                                                            HttpServletRequest request, HttpServletResponse response){
        if ( ! StringUtils.equals(CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY,socForm.getSocInfo().getStateKey())){
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "SOC should be in LOCKED state!");
            return getUIFModelAndView(socForm);
        }

        // start send approved activities to scheduler
        ManageSOCViewHelperService viewHelper = (ManageSOCViewHelperService) socForm.getView().getViewHelperService();
        viewHelper.startMassScheduling(socForm);
        return buildModel(socForm, result, request, response);
    }

    @RequestMapping(params = "methodToCall=buildModel")
    public ModelAndView buildModel(@ModelAttribute("KualiForm") ManageSOCForm socForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response){

        ManageSOCViewHelperService viewHelper = (ManageSOCViewHelperService)socForm.getView().getViewHelperService();
        socForm.clear();

        try {
            List<TermInfo> terms = viewHelper.getTermByCode(socForm.getTermCode());
            if (terms.size() > 1) {
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "Multiple entries found for the term code");
                return getUIFModelAndView(socForm);
            }
            if (terms.isEmpty()){
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "Term not found");
                return getUIFModelAndView(socForm);
            }
            socForm.setTermInfo(terms.get(0));
        } catch (Exception e) {
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM,e.getMessage());
            LOG.error("Error building model.", e);
        }

        viewHelper.buildModel(socForm);

        return getUIFModelAndView(socForm);
    }

    @RequestMapping(params = "methodToCall=allowFinalEdits")
    public ModelAndView allowFinalEdits(@ModelAttribute("KualiForm") ManageSOCForm socForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response){

        if (socForm.getSocInfo() == null){
            throw new RuntimeException("SocInfo not exists in the form. Please enter the term code and click on GO button");
        }

        if ( ! StringUtils.equals(CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_COMPLETED,socForm.getSocInfo().getSchedulingStateKey())){
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "SOC scheduling should be completed for final edits");
            return getUIFModelAndView(socForm);
        }

        ManageSOCViewHelperService viewHelper = (ManageSOCViewHelperService)socForm.getView().getViewHelperService();
        viewHelper.allowSOCFinalEdit(socForm);

        return buildModel(socForm, result, request, response);
    }

    @RequestMapping(params = "methodToCall=publishSOC")
    public ModelAndView publishSOC(@ModelAttribute("KualiForm") ManageSOCForm socForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response){

        if (socForm.getSocInfo() == null){
            throw new RuntimeException("SocInfo not exists in the form. Please enter the term code and click on GO button");
        }

        if ( ! StringUtils.equals(CourseOfferingSetServiceConstants.FINALEDITS_SOC_STATE_KEY, socForm.getSocInfo().getStateKey())){
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "SOC should be at Final Edit for publish");
            return getUIFModelAndView(socForm);
        }

        ManageSOCViewHelperService viewHelper = (ManageSOCViewHelperService)socForm.getView().getViewHelperService();
        try {
            viewHelper.publishSOC(socForm);
        } catch(Exception e) {
            LOG.error("Could not start mass publishing event.", e);
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "Unable to initiate publishing.");
            return getUIFModelAndView(socForm);
        }

        return buildModel(socForm, result, request, response);
    }

    @RequestMapping(params = "methodToCall=closeSOC")
    public ModelAndView closeSOC(@ModelAttribute("KualiForm") ManageSOCForm socForm, BindingResult result,
                                              HttpServletRequest request, HttpServletResponse response){
        if (socForm.getSocInfo() == null){
            throw new RuntimeException("SocInfo not exists in the form. Please enter the term code and click on GO button");
        }

        if (!StringUtils.equals(CourseOfferingSetServiceConstants.PUBLISHED_SOC_STATE_KEY,socForm.getSocInfo().getStateKey())){
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM," SOC should be at Publish state to close");
            return getUIFModelAndView(socForm);
        }

        ManageSOCViewHelperService viewHelper = (ManageSOCViewHelperService)socForm.getView().getViewHelperService();
        viewHelper.closeSOC(socForm);

        return buildModel(socForm, result, request, response);
    }
}
