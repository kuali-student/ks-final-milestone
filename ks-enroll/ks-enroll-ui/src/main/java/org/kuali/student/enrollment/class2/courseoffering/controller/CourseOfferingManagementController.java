package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.service.CourseOfferingManagementViewHelperService;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/courseOfferingManagement")
public class CourseOfferingManagementController extends UifControllerBase {
    private CourseOfferingManagementViewHelperService viewHelperService;

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new CourseOfferingManagementForm();
    }

    /**
     * Method used to search term
     */
    @RequestMapping(params = "methodToCall=searchForTerm")
    public ModelAndView searchForTerm(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        String termCode = theForm.getTermCode();
        CourseOfferingManagementViewHelperService helper = getViewHelperService(theForm);
        List<TermInfo> termList = helper.findTermByTermCode(termCode);
        if (termList != null && termList.size() == 1) {
            // Get first term
            TermInfo matchingTerm = termList.get(0);
            theForm.setTermInfo(matchingTerm);
            if(!theForm.isHaveValidTerm()){
                theForm.setHaveValidTerm(true);
            }
        } else {
            theForm.setHaveValidTerm(false);
            //TODO: if termList is null or termList.size()>1, log error??
        }
        return getUIFModelAndView(theForm);
    }

    @RequestMapping(params = "methodToCall=show")
    public ModelAndView show(@ModelAttribute("KualiForm") CourseOfferingManagementForm form, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        String termId=form.getTermInfo().getId();
        String radioSelection = form.getRadioSelection();
        if (radioSelection.equals("subjectCode")){
            //load all courseofferings based on subject Code
            String subjectCode = form.getInputCode();
            form.setSubjectCode(subjectCode);
            getViewHelperService(form).loadCourseOfferingsByTermAndSubjectCode(termId, subjectCode,form);
            return getUIFModelAndView(form, "manageCourseOfferingsPage");
        }
        else {
            //load courseOffering based on courseOfferingCode and load all associated activity offerings 
            String courseOfferingCode = form.getInputCode();
            List<CourseOfferingInfo> courseOfferingList = getViewHelperService(form).
                                            findCourseOfferingsByCourseOfferingCode(termId, courseOfferingCode, form);
            if (!courseOfferingList.isEmpty() && courseOfferingList.size() == 1 )  {
                CourseOfferingInfo theCourseOffering= form.getCourseOfferingList().get(0);
                getViewHelperService(form).loadActivityOfferingsByCourseOffering(theCourseOffering, form);
                return getUIFModelAndView(form, "");
            }
            else{
                //TODO: how to handle when size > 1
                return getUIFModelAndView(form);
            }
        }        
    }

    public CourseOfferingManagementViewHelperService getViewHelperService(CourseOfferingManagementForm theForm){
        if (viewHelperService == null) {
            if (theForm.getView().getViewHelperServiceClass() != null){
                viewHelperService = (CourseOfferingManagementViewHelperService) theForm.getView().getViewHelperService();
            }else{
                viewHelperService= (CourseOfferingManagementViewHelperService) theForm.getPostedView().getViewHelperService();
            }
        }
        return viewHelperService;
    }
}
