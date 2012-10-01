package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.common.dto.DtoConstants;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.service.CourseOfferingManagementViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingManagementViewHelperServiceImpl;
import org.kuali.student.enrollment.class2.courseoffering.util.ActivityOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

@Controller
@RequestMapping(value = "/courseOfferingManagement")
public class CourseOfferingManagementController extends UifControllerBase  {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(CourseOfferingManagementController.class);

    private CourseOfferingManagementViewHelperService viewHelperService;

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new CourseOfferingManagementForm();
    }

    /**
     * Method used to
     *  1) search to get TermInfo based on termCode. Only accept one valid TermInfo. If find more than one TermInfo or
     *  don't find any termInfo, log and report an error message.
     *  2) If the input is subject code, load all course offerings based on termId and subjectCode
     *  3) If the input is course offering code,
     *      a)find THE course offering based on termId and courseOfferingCode. If find more than one CO or don't find
     *        any CO, log and report an error message.
     *      b)load all activity offerings based on the courseOfferingId
     */
    @RequestMapping(params = "methodToCall=show")
    public ModelAndView show(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        //First, find TermInfo based on termCode
        String termCode = theForm.getTermCode();
        List<TermInfo> termList = getViewHelperService(theForm).findTermByTermCode(termCode);

        if (termList != null && termList.size() == 1) {
            // Get THE term
            theForm.setTermInfo(termList.get(0));
        } else if (termList.size()>1) {
            LOG.error("Error: Found more than one Term for term code: "+termCode);
            GlobalVariables.getMessageMap().putError("termCode", CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_FOUND_MORE_THAN_ONE_TERM, termCode);
            theForm.getCourseOfferingEditWrapperList().clear();
            return getUIFModelAndView(theForm);
         } else{
            LOG.error("Error: Can't find any Term for term code: "+termCode);
            GlobalVariables.getMessageMap().putError("termCode", CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_NO_TERM_IS_FOUND, termCode);
            theForm.getCourseOfferingEditWrapperList().clear();
            return getUIFModelAndView(theForm);
        }
        
        //Second, handle subjectCode vs courseOFferingCode
        if (theForm.getRadioSelection().equals("subjectCode")){

            //load all courseofferings based on subject Code
            theForm.setSubjectCode(theForm.getInputCode());
            getViewHelperService(theForm).loadCourseOfferingsByTermAndSubjectCode(theForm.getTermInfo().getId(), theForm.getInputCode(),theForm);

            return getUIFModelAndView(theForm, "manageCourseOfferingsPage");

        } else {
            //load courseOffering based on courseOfferingCode and load all associated activity offerings 
            String courseOfferingCode = theForm.getInputCode();
            theForm.setCourseOfferingCode(courseOfferingCode);

            List<CourseOfferingInfo> courseOfferingList = getViewHelperService(theForm).findCourseOfferingsByTermAndCourseOfferingCode(termCode, courseOfferingCode, theForm);

            if (!courseOfferingList.isEmpty() && courseOfferingList.size() == 1 )  {

                CourseOfferingEditWrapper wrapper = new CourseOfferingEditWrapper(courseOfferingList.get(0));

                theForm.getCourseOfferingEditWrapperList().clear();
                theForm.getCourseOfferingEditWrapperList().add(wrapper);
                theForm.setTheCourseOffering(courseOfferingList.get(0));
                getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(courseOfferingList.get(0), theForm);
                getViewHelperService(theForm).loadPreviousAndNextCourseOffering(theForm,courseOfferingList.get(0));

                return getUIFModelAndView(theForm, "manageActivityOfferingsPage");

            } else if (courseOfferingList.size()>1) {

                LOG.error("Error: Found more than one Course Offering for a Course Offering Code: "+courseOfferingCode+" in term: "+termCode);

                GlobalVariables.getMessageMap().putError("inputCode", CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_FOUND_MORE_THAN_ONE_COURSE_OFFERING, courseOfferingCode, termCode);
                theForm.getCourseOfferingEditWrapperList().clear();
                theForm.setActivityWrapperList(null);

                return getUIFModelAndView(theForm);

            } else {

                LOG.error("Error: Can't find any Course Offering for a Course Offering Code: "+courseOfferingCode+" in term: "+termCode);

                GlobalVariables.getMessageMap().putError("inputCode", CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "Course Offering", courseOfferingCode, termCode);
                theForm.getCourseOfferingEditWrapperList().clear();
                theForm.setActivityWrapperList(null);

                return getUIFModelAndView(theForm);
            }
        }
    }

    @RequestMapping(params = "methodToCall=loadPreviousCO")
    public ModelAndView loadPreviousCO(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        CourseOfferingEditWrapper wrapper = new CourseOfferingEditWrapper(theForm.getPreviousCourseOffering());

        theForm.getCourseOfferingEditWrapperList().clear();
        theForm.getCourseOfferingEditWrapperList().add(wrapper);
        theForm.setTheCourseOffering(theForm.getPreviousCourseOffering());
        theForm.setInputCode(wrapper.getCoInfo().getCourseOfferingCode());

        getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(theForm.getPreviousCourseOffering(), theForm);
        getViewHelperService(theForm).loadPreviousAndNextCourseOffering(theForm,theForm.getPreviousCourseOffering());

        return getUIFModelAndView(theForm);
    }

    @RequestMapping(params = "methodToCall=loadNextCO")
    public ModelAndView loadNextCO(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        CourseOfferingEditWrapper wrapper = new CourseOfferingEditWrapper(theForm.getNextCourseOffering());

        theForm.getCourseOfferingEditWrapperList().clear();
        theForm.getCourseOfferingEditWrapperList().add(wrapper);
        theForm.setTheCourseOffering(theForm.getNextCourseOffering());
        theForm.setInputCode(wrapper.getCoInfo().getCourseOfferingCode());

        getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(theForm.getNextCourseOffering(), theForm);
        getViewHelperService(theForm).loadPreviousAndNextCourseOffering(theForm,theForm.getNextCourseOffering());

        return getUIFModelAndView(theForm);
    }

    @RequestMapping(params = "methodToCall=loadAOs")
    public ModelAndView loadAOs(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object selectedObject = _getSelectedObject(theForm, "Manage");
        if(selectedObject instanceof CourseOfferingEditWrapper){
            CourseOfferingEditWrapper coWrapper =  (CourseOfferingEditWrapper)selectedObject;
            CourseOfferingInfo theCourseOffering = coWrapper.getCoInfo();
            theForm.setTheCourseOffering(theCourseOffering);
            theForm.setCourseOfferingCode(theCourseOffering.getCourseOfferingCode());
            theForm.setInputCode(theCourseOffering.getCourseOfferingCode());
            theForm.setRadioSelection("courseOfferingCode");
            getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(theCourseOffering, theForm);
            getViewHelperService(theForm).loadPreviousAndNextCourseOffering(theForm,theCourseOffering);

            return getUIFModelAndView(theForm, "manageActivityOfferingsPage");
        }
        else{
            //TODO log error
            return getUIFModelAndView(theForm, "manageCourseOfferingsPage");
        }

    }


    @RequestMapping(params = "methodToCall=selectAllActivityOfferings")
    public ModelAndView selectAllActivityOfferings(
            @ModelAttribute("KualiForm")
            CourseOfferingManagementForm theForm,
            BindingResult result,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        List<ActivityOfferingWrapper> list = theForm.getActivityWrapperList();
        for (ActivityOfferingWrapper listElement : list) {
            listElement.setIsChecked(true);
        }
        return getUIFModelAndView(theForm);
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
     * Method used to copy activityOffering
     */
    @RequestMapping(params = "methodToCall=copyAO")
    public ModelAndView copyAO( @ModelAttribute("KualiForm") CourseOfferingManagementForm form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {
        ActivityOfferingWrapper selectedAO = (ActivityOfferingWrapper)_getSelectedObject(form, "copy");
        try{
            CourseOfferingResourceLoader.loadCourseOfferingService().copyActivityOffering(selectedAO.getAoInfo().getId(), ContextBuilder.loadContextInfo());

            //reload AOs including the new one just created
            getViewHelperService(form).loadActivityOfferingsByCourseOffering(form.getTheCourseOffering(), form);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        return getUIFModelAndView(form);
    }

    /**
     * Method used to delete a list of selected Draft activity Offerings
     **/
    @RequestMapping(params = "methodToCall=cancelDeleteAOs")
    public ModelAndView cancelDeleteAOs(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(theForm.getTheCourseOffering(), theForm);
        return getUIFModelAndView(theForm, "manageActivityOfferingsPage");
    }

    /**
     * Method used to delete a list of selected Draft activity Offerings
     **/

    @RequestMapping(params = "methodToCall=deleteSelectedAoList")
    public ModelAndView deleteSelectedAoList(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<ActivityOfferingWrapper> selectedAolist = theForm.getSelectedToDeleteList();

        try{
            for(ActivityOfferingWrapper ao : selectedAolist)  {
                CourseOfferingResourceLoader.loadCourseOfferingService().deleteActivityOffering(ao.getAoInfo().getId(), ContextBuilder.loadContextInfo());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(theForm.getTheCourseOffering(), theForm);
        return getUIFModelAndView(theForm, "manageActivityOfferingsPage");

    }

    /**
     * Method used to confirm delete AOs
     */
    @RequestMapping(params = "methodToCall=deleteCoConfirmation")
    public ModelAndView deleteCoConfirmation(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {

        CourseOfferingInfo theCourseOffering = null;

        String selectedCollectionPath = theForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (!StringUtils.isBlank(selectedCollectionPath)) {
            Object selectedObject = _getSelectedObject(theForm, "deleteCo");
            theCourseOffering = ((CourseOfferingEditWrapper) selectedObject).getCoInfo();
            theForm.setTheCourseOffering(theCourseOffering);
            // load the related AOs
            try {
                getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(theCourseOffering, theForm);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        if(theCourseOffering == null) {
            theCourseOffering = theForm.getTheCourseOffering();
        }
        if(theCourseOffering == null) {
            throw new RuntimeException("No Course Offering selected!");
        }

        String termId = theCourseOffering.getTermId();
        String subjectCode = theCourseOffering.getSubjectArea();
        theForm.setCourseOfferingCode(theCourseOffering.getCourseOfferingCode());

        // load all the activity offerings
        if (theForm.getActivityWrapperList().isEmpty()) {
            try {
                getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(theCourseOffering, theForm);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }

        }
        for (ActivityOfferingWrapper ao : theForm.getActivityWrapperList()) {
            // verify if any AO status is not draft throw exception
            if (!ao.isLegalToDelete()) {
                LOG.error("Error: Course Offering cannot be deleted.");
                GlobalVariables.getMessageMap().putErrorForSectionId("KS-CourseOfferingManagement-CourseOfferingListSection",
                        CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_CO_CANNOT_DELETE);
                return getUIFModelAndView(theForm, "manageCourseOfferingsPage");
            }
        }

        return getUIFModelAndView(theForm, "coDeleteConfirmationPage");
    }

    /**
     * Method used to delete a Course Offering with all Draft activity Offerings
     **/
    @RequestMapping(params = "methodToCall=deleteCo")
    public ModelAndView deleteCo(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                             HttpServletRequest request, HttpServletResponse response) {
        CourseOfferingInfo  theCourseOffering = theForm.getTheCourseOffering();
        if(theCourseOffering == null) {
            throw new RuntimeException("No Course Offering selected!");
        }

        String termId = theCourseOffering.getTermId();
        String subjectCode = theCourseOffering.getSubjectArea();
        // load all the activity offerings
        if (theForm.getActivityWrapperList().isEmpty()) {
            try {
                getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(theCourseOffering, theForm);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }

        }
        try {
            for (ActivityOfferingWrapper ao : theForm.getActivityWrapperList()) {
                // verify if any AO status is not draft throw exception
                if (!ao.isLegalToDelete()) {
                    LOG.error("Error: Course Offering cannot be deleted.");
                    GlobalVariables.getMessageMap().putErrorForSectionId("KS-CourseOfferingManagement-CourseOfferingListSection",
                            CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_CO_CANNOT_DELETE);
                    return getUIFModelAndView(theForm, "manageCourseOfferingsPage");
                }
            }
            CourseOfferingResourceLoader.loadCourseOfferingService().deleteCourseOfferingCascaded(theCourseOffering.getId(), ContextBuilder.loadContextInfo());
            //reload existing COs
            getViewHelperService(theForm).loadCourseOfferingsByTermAndSubjectCode(termId, subjectCode, theForm);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return getUIFModelAndView(theForm, "manageCourseOfferingsPage");
    }

    /**
     * Method used to delete a Course Offering with all Draft activity Offerings
     **/
    @RequestMapping(params = "methodToCall=cancelDeleteCo")
    public ModelAndView cancelDeleteCo(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                 HttpServletRequest request, HttpServletResponse response) {
        CourseOfferingInfo  theCourseOffering = theForm.getTheCourseOffering();
        if(theCourseOffering == null) {
            throw new RuntimeException("No Course Offering selected!");
        }

        String termId = theCourseOffering.getTermId();
        String subjectCode = theCourseOffering.getSubjectArea();
        // load all the activity offerings
        if (theForm.getActivityWrapperList().isEmpty()) {
            try {
                getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(theCourseOffering, theForm);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }

        }

        try {
            getViewHelperService(theForm).loadCourseOfferingsByTermAndSubjectCode(termId, subjectCode, theForm);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return getUIFModelAndView(theForm, "manageCourseOfferingsPage");
    }

    /**
     * Method used to edit a selected CO or AO
     */
    @RequestMapping(params = "methodToCall=edit")
    public ModelAndView edit(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        Properties urlParameters = new Properties();
        String controllerPath = "maintenance";
        Object selectedObject = _getSelectedObject(theForm, "edit");
        if(selectedObject instanceof CourseOfferingEditWrapper){
            CourseOfferingInfo courseOfferingInfo = ((CourseOfferingEditWrapper) selectedObject).getCoInfo();
            urlParameters = _buildCOURLParameters(courseOfferingInfo,"maintenanceEdit",false,getContextInfo());
        }
        else if(selectedObject instanceof ActivityOfferingWrapper) {
            ActivityOfferingWrapper aoWrapper = (ActivityOfferingWrapper)selectedObject;
            urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, "maintenanceEdit");
            urlParameters.put(ActivityOfferingConstants.ACTIVITY_OFFERING_WRAPPER_ID, aoWrapper.getAoInfo().getId());
            urlParameters.put(ActivityOfferingConstants.ACTIVITYOFFERING_COURSE_OFFERING_ID, theForm.getTheCourseOffering().getId());
            urlParameters.put("dataObjectClassName", ActivityOfferingWrapper.class.getName());
            urlParameters.put(UifConstants.UrlParams.SHOW_HOME, BooleanUtils.toStringTrueFalse(false));
        } else {
            throw new RuntimeException("Invalid type. Does not support for now");
        }

        return super.performRedirect(theForm,controllerPath, urlParameters);

    }

    /**
     * Method used to pick the selected AO actions
     */
    @RequestMapping(params = "methodToCall=selectedAoActions")
    public ModelAndView selectedAoActions(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (StringUtils.equals(theForm.getSelectedOfferingAction(),CourseOfferingConstants.ACTIVITY_OFFERING_DELETE_ACTION)){
            return confirmDelete(theForm,  result, request,  response);
        }

        if (StringUtils.equals(theForm.getSelectedOfferingAction(),CourseOfferingConstants.ACTIVITY_OFFERING_DRAFT_ACTION) ||
            StringUtils.equals(theForm.getSelectedOfferingAction(),CourseOfferingConstants.ACTIVITY_OFFERING_SCHEDULING_ACTION)){
            getViewHelperService(theForm).changeActivityOfferingsState(theForm.getActivityWrapperList(),theForm.getSelectedOfferingAction());
        }

        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);

    }

    @RequestMapping(params = "methodToCall=selectedCOActions")
    public ModelAndView selectedCOActions(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                          HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (StringUtils.equals(theForm.getSelectedOfferingAction(),CourseOfferingConstants.ACTIVITY_OFFERING_SCHEDULING_ACTION)){
            getViewHelperService(theForm).markCourseOfferingsForScheduling(theForm.getCourseOfferingEditWrapperList());
        }

        return getUIFModelAndView(theForm);

    }


    /**
     * Method used to confirm delete AOs
     */
    @RequestMapping(params = "methodToCall=confirmDelete")
    public ModelAndView confirmDelete(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        Collection<Object> collection;
        Object selectedObject;
        List<ActivityOfferingWrapper> aoList = theForm.getActivityWrapperList();
        List<ActivityOfferingWrapper> selectedIndexList = theForm.getSelectedToDeleteList();

        // clear the list
        selectedIndexList.clear();

        String selectedCollectionPath = theForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isNotBlank(selectedCollectionPath)) {
            // select the single AO
            int selectedLineIndex = -1;
            String selectedLine = theForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
            if (StringUtils.isNotBlank(selectedLine)) {
                selectedLineIndex = Integer.parseInt(selectedLine);
            }

            if (selectedLineIndex == -1) {
                throw new RuntimeException("Selected line index was not set");
            }

            collection = ObjectPropertyUtils.getPropertyValue(theForm, selectedCollectionPath);

            selectedObject = ((List<Object>) collection).get(selectedLineIndex);
            // Record the selected AO IsChecked
            selectedIndexList.add((ActivityOfferingWrapper)selectedObject);
        }
        else {
            // check if there is Draft AO selected
            selectedIndexList.clear();
            for(ActivityOfferingWrapper ao : aoList) {
                if(ao.isLegalToDelete() && ao.getIsChecked()) {
                    selectedIndexList.add(ao);
                }else if (ao.getIsChecked()){
                    GlobalVariables.getMessageMap().putError("selectedOfferingAction",CourseOfferingConstants.AO_NOT_DRAFT_FOR_DELETION_ERROR);
                    return getUIFModelAndView(theForm);
                }
            }
            if(selectedIndexList.isEmpty() ) {
                LOG.error("Error: No selected Draft Activity Offering");
                GlobalVariables.getMessageMap().putErrorForSectionId("confirmationResultSection",
                        CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_FOUND_NO_DRAFT_AO_SELECTED);
                return getUIFModelAndView(theForm);
            }
        }

        // GlobalVariables.getMessageMap().putErrorForSectionId("add_planned_course", PlanConstants.ERROR_KEY_UNKNOWN_COURSE);

        Integer toBeDeleted = selectedIndexList.size();

        if(selectedIndexList.size() >= 1) {
            GlobalVariables.getMessageMap().putWarningForSectionId("confirmationResultSection",
                    CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_SELECTED_AO_TO_DELETE, toBeDeleted.toString());
        }

        return getUIFModelAndView(theForm, "selectedAoDeleteConfirmationPage");
    }

    /**
     * Method used to view a CO or an AO
     */
    @RequestMapping(params = "methodToCall=view")
    public ModelAndView view(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {

        Properties urlParameters = new Properties();
        String controllerPath = "inquiry";
        Object selectedObject = _getSelectedObject(theForm, "view");

        if(selectedObject instanceof CourseOfferingInfo){
            urlParameters = _buildCOURLParameters((CourseOfferingInfo)selectedObject,"start",true,getContextInfo());
        } else if(selectedObject instanceof ActivityOfferingWrapper) {
            ActivityOfferingWrapper aoWrapper = (ActivityOfferingWrapper)selectedObject;
            urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, "start");
            urlParameters.put(ActivityOfferingConstants.ACTIVITYOFFERING_ID, aoWrapper.getAoInfo().getId());
            urlParameters.put("dataObjectClassName", ActivityOfferingInfo.class.getName());
        } else {
            throw new RuntimeException("Invalid type. Does not support for now");
        }

        return super.performRedirect(theForm,controllerPath, urlParameters);
    }

    @RequestMapping(params = "methodToCall=addActivityOfferings")
    public ModelAndView addActivityOfferings(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        String activityId = theForm.getActivityIdForNewAO();
        String formatId = theForm.getFormatIdForNewAO();
        int aoCount = Integer.parseInt(theForm.getNoOfActivityOfferings());

        getViewHelperService(theForm).createActivityOfferings(formatId, activityId, aoCount, theForm);

        theForm.setFormatIdForNewAO(null);
        theForm.setActivityIdForNewAO(null);
        theForm.setNoOfActivityOfferings(null);

        return getUIFModelAndView(theForm);

    }

    @RequestMapping(params = "methodToCall=createCourseOffering")
    public ModelAndView createCourseOffering(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        String termCode = theForm.getTermCode();

        Properties props = new Properties();
        props.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, "start");
        props.put("targetTermCode",termCode);
        props.put("dataObjectClassName", "org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCreateWrapper");

         return super.performRedirect(theForm,"courseOffering", props);
    }


    @RequestMapping(params = "methodToCall=markSubjectCodeReadyForScheduling")
    public ModelAndView markSubjectCodeReadyForScheduling(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                             HttpServletRequest request, HttpServletResponse response) throws Exception {
        CourseOfferingManagementViewHelperServiceImpl helperService = (CourseOfferingManagementViewHelperServiceImpl)theForm.getView().getViewHelperService();
        List<ActivityOfferingWrapper> list = theForm.getActivityWrapperList();
        for (ActivityOfferingWrapper activityOfferingWrapper : list) {
            if (DtoConstants.STATE_DRAFT.equalsIgnoreCase(activityOfferingWrapper.getStateName())) {

                // Update the activityOfferingWrapper with the new state.
                activityOfferingWrapper.setStateName(DtoConstants.STATE_APPROVED);

                // Update the activityOfferingInfo with the new state.
                ActivityOfferingInfo activityOfferingInfo = activityOfferingWrapper.getAoInfo();
                activityOfferingInfo.setStateKey(LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY);

                // Persist changes to the database.
                CourseOfferingService courseOfferingService = helperService.getCourseOfferingService();
                ContextInfo contextInfo = helperService.getContextInfo();
                String activityOfferingId = activityOfferingWrapper.getId();
                courseOfferingService.updateActivityOffering(activityOfferingId, activityOfferingInfo, contextInfo);
            }
        }
        return getUIFModelAndView(theForm);
    }

    /**
     * Method used to invoke the CO inquiry view from Manage Course Offering screen while search input is Course Offering
     * Code (04a screen)
     */
    @RequestMapping(params = "methodToCall=viewTheCO")
    public ModelAndView viewTheCO(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                  HttpServletRequest request, HttpServletResponse response) throws Exception {

        CourseOfferingInfo theCourseOfferingInfo = theForm.getTheCourseOffering();
        Properties urlParameters = _buildCOURLParameters(theCourseOfferingInfo,"start",false,getContextInfo());
        String controllerPath = "inquiry";
        return super.performRedirect(theForm,controllerPath, urlParameters);
    }

    /**
     * Method used to invoke the Edit CO screen from Manage Course Offering screen while search input is Course Offering
     * Code (04a screen)
     */
    @RequestMapping(params = "methodToCall=editTheCO")
    public ModelAndView editTheCO(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                  HttpServletRequest request, HttpServletResponse response) throws Exception {

        CourseOfferingInfo theCourseOfferingInfo = theForm.getTheCourseOffering();
        Properties urlParameters = _buildCOURLParameters(theCourseOfferingInfo,"maintenanceEdit",false,getContextInfo());
        String controllerPath = "maintenance";
        return super.performRedirect(theForm,controllerPath, urlParameters);
    }

    private Properties _buildCOURLParameters(CourseOfferingInfo courseOfferingInfo, String methodToCall, boolean readOnlyView, ContextInfo context){
        Properties props = new Properties();
        props.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, methodToCall);
        props.put("coInfo.id", courseOfferingInfo.getId());
        props.put("dataObjectClassName", CourseOfferingEditWrapper.class.getName());
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

    public CourseOfferingService getCourseOfferingService() {
        return CourseOfferingResourceLoader.loadCourseOfferingService();
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
