package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.service.CourseOfferingManagementViewHelperService;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/courseOfferingManagement")
public class CourseOfferingManagementController extends UifControllerBase {
    private CourseOfferingManagementViewHelperService viewHelperService;

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new CourseOfferingManagementForm();
    }

    /**
     * Method used to search and set a valid termInfo based on termCode
     */
    @RequestMapping(params = "methodToCall=searchForTerm")
    public ModelAndView searchForTerm(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        String termCode = theForm.getTermCode();
        CourseOfferingManagementViewHelperService helper = getViewHelperService(theForm);
        List<TermInfo> termList = helper.findTermByTermCode(termCode);
        if (termList != null && termList.size() == 1) {
            // Get THE term
            theForm.setTermInfo(termList.get(0));
            if(!theForm.isHaveValidTerm()){
                theForm.setHaveValidTerm(true);
            }
        } else {
            theForm.setHaveValidTerm(false);
            //TODO: if termList is null or termList.size()>1, log error??
        }
        return getUIFModelAndView(theForm);
    }

    /**
     * Method used to
     *  1)if the input is subject code, load all course offerings based on termId and subjectCode
     *  2)if the input is course offering code,
     *      a)find THE course offering based on termId and courseOfferingCode
     *      b)load all activity offerings based on the courseOfferingId
     */
    @RequestMapping(params = "methodToCall=show")
    public ModelAndView show(@ModelAttribute("KualiForm") CourseOfferingManagementForm form, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        String termId=form.getTermInfo().getId();
        String termCode = form.getTermInfo().getCode();
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
            form.setCourseOfferingCode(courseOfferingCode);
            List<CourseOfferingInfo> courseOfferingList = getViewHelperService(form).
                                       findCourseOfferingsByTermAndCourseOfferingCode(termCode, courseOfferingCode, form);
            if (!courseOfferingList.isEmpty() && courseOfferingList.size() == 1 )  {
                CourseOfferingInfo theCourseOffering = courseOfferingList.get(0);
                form.setTheCourseOffering(theCourseOffering);
                getViewHelperService(form).loadActivityOfferingsByCourseOffering(theCourseOffering, form);
                return getUIFModelAndView(form, "manageActivityOfferingsPage");
            }
            else{
                //TODO: how to handle when size > 1
                return getUIFModelAndView(form,  "manageActivityOfferingsPage");
            }
        }        
    }

    @RequestMapping(params = "methodToCall=loadAOs")
    public ModelAndView loadAOs(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object selectedObject = _getSelectedObject(theForm, "Edit Activity Offerings");
        if(selectedObject instanceof CourseOfferingInfo){
            CourseOfferingInfo theCourseOffering = (CourseOfferingInfo)selectedObject;
            theForm.setTheCourseOffering(theCourseOffering);
            theForm.setCourseOfferingCode(theCourseOffering.getCourseOfferingCode());
            theForm.setInputCode(theCourseOffering.getCourseOfferingCode());
            theForm.setRadioSelection("courseOfferingCode");
            getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(theCourseOffering, theForm);
            return getUIFModelAndView(theForm, "manageActivityOfferingsPage");
        }
        else{
            //TODO log error
            return getUIFModelAndView(theForm, "manageCourseOfferingsPage");
        }

    }

    @RequestMapping(params = "methodToCall=loadCOs")
    public ModelAndView loadCOs(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                HttpServletRequest request, HttpServletResponse response) throws Exception {
        CourseOfferingInfo theCourseOffering = theForm.getTheCourseOffering();
        String subjectCode = theCourseOffering.getSubjectArea();
        String termId = theForm.getTermInfo().getId();
        theForm.setRadioSelection("subjectCode");
        theForm.setInputCode(subjectCode);
        getViewHelperService(theForm).loadCourseOfferingsByTermAndSubjectCode(termId, subjectCode, theForm);
        return getUIFModelAndView(theForm, "manageCourseOfferingsPage");
    }

    /**
     * Method used to view a CO or an AO

    @RequestMapping(params = "methodToCall=view")
    public ModelAndView view(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        Object selectedObject = _getSelectedObject(theForm, "view");
        Properties urlParameters;
        String controllerPath;
        if(selectedObject instanceof CourseOfferingInfo){
            //urlParameters = _buildCOURLParameters((CourseOfferingInfo)selectedObject,"start",true,getContextInfo());
            //controllerPath = CalendarConstants.HCAL_CONTROLLER_PATH;
            urlParameters = new Properties();
            controllerPath ="";
        } else if(selectedObject instanceof ActivityOfferingInfo) {
            urlParameters = new Properties();
//            urlParameters = _buildAOURLParameters((ActivityOfferingInfo)selectedObject,"start",true,getContextInfo());
            controllerPath ="";
        } else {
            throw new RuntimeException("Invalid type. Does not support for now");
        }

        return super.performRedirect(theForm,controllerPath, urlParameters);
    }
    */

    /**
     * Method used to edit the atp
     */
    @RequestMapping(params = "methodToCall=edit")
    public ModelAndView edit(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        Properties urlParameters;
        String controllerPath;
        Object selectedObject = _getSelectedObject(theForm, "edit");

        if(selectedObject instanceof CourseOfferingInfo){
            urlParameters = _buildCOURLParameters((CourseOfferingInfo)selectedObject,"maintenanceEdit",false,getContextInfo());
            controllerPath = "maintenance";
        }
        else if(selectedObject instanceof ActivityOfferingInfo) {
            urlParameters = _buildAOURLParameters((ActivityOfferingInfo)selectedObject,"maintenanceEdit",false,getContextInfo());
            controllerPath ="maintenance";
        } else {
            throw new RuntimeException("Invalid type. Does not support for now");
        }

        return super.performRedirect(theForm,controllerPath, urlParameters);

    }

    private Properties _buildCOURLParameters(CourseOfferingInfo courseOfferingInfo, String methodToCall, boolean readOnlyView, ContextInfo context){

        Properties props = new Properties();
        props.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, methodToCall);
        props.put("coInfo.id", courseOfferingInfo.getId());
        props.put("dataObjectClassName", "org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper");

        return props;

    }

    private Properties _buildAOURLParameters(ActivityOfferingInfo activityOfferingInfo, String methodToCall, boolean readOnlyView, ContextInfo context){

        Properties props = new Properties();
        props.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, methodToCall);
        props.put("aoInfo.id", activityOfferingInfo.getId());
        props.put("dataObjectClassName", "org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingFormObject");

        return props;

    }

    private Object _getSelectedObject(CourseOfferingManagementForm theForm, String actionLink){
        String selectedCollectionPath = theForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("Selected collection was not set for " + actionLink);
        }

        int selectedLineIndex = -1;
        String selectedLine = theForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
        if (StringUtils.isNotBlank(selectedLine)) {
            selectedLineIndex = Integer.parseInt(selectedLine);
        }

        if (selectedLineIndex == -1) {
            throw new RuntimeException("Selected line index was not set");
        }

        Collection<Object> collection = ObjectPropertyUtils.getPropertyValue(theForm, selectedCollectionPath);
        Object selectedObject = ((List<Object>) collection).get(selectedLineIndex);

        return selectedObject;
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

    public ContextInfo getContextInfo() {
        ContextInfo contextInfo = new ContextInfo();
        contextInfo.setAuthenticatedPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
        contextInfo.setPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
        LocaleInfo localeInfo = new LocaleInfo();
        localeInfo.setLocaleLanguage(Locale.getDefault().getLanguage());
        localeInfo.setLocaleRegion(Locale.getDefault().getCountry());
        contextInfo.setLocale(localeInfo);
        return contextInfo;
    }
}
