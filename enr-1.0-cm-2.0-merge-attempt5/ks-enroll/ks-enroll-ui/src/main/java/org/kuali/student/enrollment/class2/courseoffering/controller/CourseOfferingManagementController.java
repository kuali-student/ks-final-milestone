package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCopyWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingListSectionWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ExistingCourseOffering;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.service.CourseOfferingManagementViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingManagementViewHelperServiceImpl;
import org.kuali.student.enrollment.class2.courseoffering.util.*;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

@Controller
@RequestMapping(value = "/courseOfferingManagement")
public class CourseOfferingManagementController extends UifControllerBase  {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(CourseOfferingManagementController.class);
    private LRCService lrcService;
    private AcademicCalendarService academicCalendarService;
    private TypeService typeService;
    private StateService stateService;
    private CourseOfferingManagementViewHelperService viewHelperService;
    private OrganizationService organizationService;

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new CourseOfferingManagementForm();
    }

    @Override
    @RequestMapping(params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, @SuppressWarnings("unused") BindingResult result,
            @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

        if (!(form instanceof CourseOfferingManagementForm)){
            throw new RuntimeException("Form object passed into start method was not of expected type CourseOfferingManagementForm. Got "+form.getClass().getSimpleName());
        }

        CourseOfferingManagementForm theForm = (CourseOfferingManagementForm) form;
        // set adminOrg to the form to temporarily overcome that we actually need page level authorization but not view 
        // level authorization.
        String inputValue = request.getParameter("adminOrg");
        if ((inputValue != null) && !inputValue.isEmpty()){
            theForm.setAdminOrg(inputValue);
        }
        //clean up termCode, inputCode, and theCourseOffering value in the form to prevent the
        //side effect of the authorization.
        theForm.setTermCode(null);
        theForm.setInputCode(null);
        theForm.setTheCourseOffering(null);

        // check view authorization
        // TODO: this needs to be invoked for each request
        if (form.getView() != null) {
            String methodToCall = request.getParameter(KRADConstants.DISPATCH_REQUEST_PARAMETER);
            checkViewAuthorization(theForm, methodToCall);
            theForm.setEditAuthz(checkEditViewAuthz(theForm));
        }


        // check if the view is invoked within portal or not
        inputValue = request.getParameter("withinPortal");
        if ((inputValue != null) && !inputValue.isEmpty()){
            boolean withinPortal = Boolean.valueOf(request.getParameter("withinPortal"));
            theForm.setWithinPortal(withinPortal);
        }

        /**
         * When user cancels edit AO/CO, this will be called. Based on the radio button selected, we need to set the page id
         * sothat that page will be displayed. Otherwise, it displays the default (search page) will be displayed.
         */
        String[] methodToCalls = request.getParameterValues(KRADConstants.DISPATCH_REQUEST_PARAMETER);
        for (String methodToCall : methodToCalls) {
            if (StringUtils.equals(methodToCall,KRADConstants.RETURN_METHOD_TO_CALL)){
                if (StringUtils.equals(theForm.getRadioSelection(),CourseOfferingConstants.COURSEOFFERING_COURSE_OFFERING_CODE)){
                    form.setPageId(CourseOfferingConstants.MANAGE_AO_PAGE);
                } else if (StringUtils.equals(theForm.getRadioSelection(),CourseOfferingConstants.COURSEOFFERING_SUBJECT_CODE)){
                    form.setPageId(CourseOfferingConstants.MANAGE_CO_PAGE);
                }
                break;
            }
        }
        return getUIFModelAndView(theForm);
    }

    /*
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
    public ModelAndView show(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                             @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        //First, find TermInfo based on termCode
        String termCode = theForm.getTermCode();
        List<TermInfo> termList = getViewHelperService(theForm).findTermByTermCode(termCode);

        if (termList != null && !termList.isEmpty()){
            if( termList.size() == 1) {
                // Get THE term
                theForm.setTermInfo(termList.get(0));
            } else {
                LOG.error("Error: Found more than one Term for term code: " + termCode);
                GlobalVariables.getMessageMap().putError("termCode", CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_FOUND_MORE_THAN_ONE_TERM, termCode);
                theForm.clearCourseOfferingResultList();
                return getUIFModelAndView(theForm);
             }
        } else {
            LOG.error("Error: Can't find any Term for term code: " + termCode);
            GlobalVariables.getMessageMap().putError("termCode", CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_NO_TERM_IS_FOUND, termCode);
            theForm.clearCourseOfferingResultList();
            return getUIFModelAndView(theForm);
        }

        //load all courseofferings based on subject Code
        String inputCode = theForm.getInputCode();
        if (inputCode != null && !inputCode.isEmpty()) {
            getViewHelperService(theForm).loadCourseOfferingsByTermAndCourseCode(theForm.getTermInfo().getId(), inputCode, theForm);
            if(!theForm.getCourseOfferingResultList().isEmpty()) {
                if (theForm.getCourseOfferingResultList().size() > 1) {
                    theForm.setSubjectCode(theForm.getCourseOfferingResultList().get(0).getSubjectArea());
                    String longNameDescr = getOrgNameDescription(theForm.getSubjectCode());
                    theForm.setSubjectCodeDescription(longNameDescr);
                    // Pull out the first CO from the result list and then pull out the org ids from this CO
                    // and pass in the first one as the adminOrg
                    CourseOfferingInfo firstCO = getCourseOfferingService().getCourseOffering(theForm.getCourseOfferingResultList().get(0).getCourseOfferingId(), ContextUtils.createDefaultContextInfo());
                    List<String> orgIds = firstCO.getUnitsDeploymentOrgIds();
                    if(orgIds !=null && !orgIds.isEmpty()){
                        theForm.setAdminOrg(orgIds.get(0));
                    }
                    CourseOfferingInfo coToShow = getCourseOfferingService().getCourseOffering(theForm.getCourseOfferingResultList().get(0).getCourseOfferingId(), ContextUtils.createDefaultContextInfo());
                    theForm.setCourseOfferingCode(coToShow.getCourseOfferingCode());
                } else { // just one course offering is returned
                    CourseOfferingInfo coToShow = getCourseOfferingService().getCourseOffering(theForm.getCourseOfferingResultList().get(0).getCourseOfferingId(), ContextUtils.createDefaultContextInfo());
                    theForm.setCourseOfferingCode(coToShow.getCourseOfferingCode());
                    return _prepareManageAOsModelAndView(theForm, coToShow);
                }
            }
            //
            theForm.setEditAuthz(checkEditViewAuthz(theForm));
            if (GlobalVariables.getMessageMap().getErrorMessages().isEmpty()){
                return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_CO_PAGE);
            }else{
                return getUIFModelAndView(theForm, CourseOfferingConstants.SEARCH_PAGE);
            }
        } else {
            LOG.error("Error: Course Code search field can't be empty");
            GlobalVariables.getMessageMap().putError("inputCode", CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "Course Offering", inputCode, termCode);
            theForm.clearCourseOfferingResultList();
            theForm.setActivityWrapperList(null);

            return getUIFModelAndView(theForm);
        }
    }

    private ModelAndView _prepareManageAOsModelAndView(CourseOfferingManagementForm theForm, CourseOfferingInfo coToShow) throws Exception {
        CourseOfferingEditWrapper wrapper = new CourseOfferingEditWrapper(coToShow);

        theForm.setCourseOfferingEditWrapper(wrapper);
        theForm.setTheCourseOffering(coToShow);
        //Pull out the org ids and pass in the first one as the adminOrg
        List<String> orgIds = coToShow.getUnitsDeploymentOrgIds();
        if(orgIds !=null && !orgIds.isEmpty()){
            theForm.setAdminOrg(orgIds.get(0));
        }
        theForm.setFormatIdForNewAO(null);
        theForm.setActivityIdForNewAO(null);
        theForm.setNoOfActivityOfferings(null);
        getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(coToShow, theForm);
        getViewHelperService(theForm).loadPreviousAndNextCourseOffering(theForm, coToShow);

        theForm.setEditAuthz(checkEditViewAuthz(theForm));

        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);
    }

    @RequestMapping(params = "methodToCall=manageRegGroups")
    public ModelAndView manageRegGroups(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                        @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        CourseOfferingInfo theCourseOfferingInfo = theForm.getTheCourseOffering();
        Properties urlParameters = new Properties();
        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.START_METHOD);
        urlParameters.put("coInfo.id", theCourseOfferingInfo.getId());
        urlParameters.put(UifParameters.VIEW_ID, RegistrationGroupConstants.RG_VIEW);
        urlParameters.put(UifConstants.UrlParams.SHOW_HOME, BooleanUtils.toStringTrueFalse(false));
//        urlParameters.put(UifConstants.UrlParams.SHOW_HISTORY, BooleanUtils.toStringTrueFalse(false));
        urlParameters.put("withinPortal", BooleanUtils.toStringTrueFalse(theForm.isWithinPortal()));
        String controllerPath = RegistrationGroupConstants.RG_CONTROLLER_PATH;
        return super.performRedirect(theForm,controllerPath, urlParameters);

    }

    @RequestMapping(params = "methodToCall=loadPreviousCO")
    public ModelAndView loadPreviousCO(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                             @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        CourseOfferingEditWrapper wrapper = new CourseOfferingEditWrapper(theForm.getPreviousCourseOffering());

        theForm.clearCourseOfferingResultList();
        theForm.setCourseOfferingEditWrapper(wrapper);
        theForm.setTheCourseOffering(theForm.getPreviousCourseOffering());
        theForm.setInputCode(wrapper.getCoInfo().getCourseOfferingCode());

        getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(theForm.getPreviousCourseOffering(), theForm);
        getViewHelperService(theForm).loadPreviousAndNextCourseOffering(theForm,theForm.getPreviousCourseOffering());

        return getUIFModelAndView(theForm);
    }

    @RequestMapping(params = "methodToCall=loadNextCO")
    public ModelAndView loadNextCO(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                             @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        CourseOfferingEditWrapper wrapper = new CourseOfferingEditWrapper(theForm.getNextCourseOffering());

        theForm.clearCourseOfferingResultList();
        theForm.setCourseOfferingEditWrapper(wrapper);
        theForm.setTheCourseOffering(theForm.getNextCourseOffering());
        theForm.setInputCode(wrapper.getCoInfo().getCourseOfferingCode());

        getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(theForm.getNextCourseOffering(), theForm);
        getViewHelperService(theForm).loadPreviousAndNextCourseOffering(theForm,theForm.getNextCourseOffering());

        return getUIFModelAndView(theForm);
    }

    @RequestMapping(params = "methodToCall=loadAOs")
    public ModelAndView loadAOs(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                             @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        Object selectedObject = _getSelectedObject(theForm, "Manage");

        if(selectedObject instanceof CourseOfferingListSectionWrapper){
            CourseOfferingListSectionWrapper coWrapper =  (CourseOfferingListSectionWrapper)selectedObject;
            CourseOfferingInfo theCourseOffering = getCourseOfferingService().getCourseOffering(coWrapper.getCourseOfferingId(), ContextUtils.createDefaultContextInfo());

            theForm.setCourseOfferingCode(theCourseOffering.getCourseOfferingCode());
            theForm.setInputCode(theCourseOffering.getCourseOfferingCode());
            return _prepareManageAOsModelAndView(theForm, theCourseOffering);
        }
        else{
            //TODO log error
            return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_CO_PAGE);
        }

    }

    @RequestMapping(params = "methodToCall=copyCourseOfferingCreateCopy")
    public ModelAndView copyCourseOfferingCreateCopy(
            @ModelAttribute("KualiForm") CourseOfferingManagementForm theForm,
            @SuppressWarnings("unused") BindingResult result,
            @SuppressWarnings("unused") HttpServletRequest request,
            @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        CourseOfferingCopyWrapper copyWrapper = theForm.getCourseOfferingCopyWrapper();
        CourseOfferingInfo courseOfferingInfo = copyWrapper.getCoInfo();
        List<String> optionKeys = new ArrayList<String>();

        if (copyWrapper.isExcludeSchedulingInformation()) {
            optionKeys.add(CourseOfferingSetServiceConstants.NO_SCHEDULE_OPTION_KEY);
        }
        if (copyWrapper.isExcludeInstructorInformation()) {
            optionKeys.add(CourseOfferingSetServiceConstants.NO_INSTRUCTORS_OPTION_KEY);
        }
        if (copyWrapper.isExcludeCancelledActivityOfferings()) {
            optionKeys.add(CourseOfferingSetServiceConstants.IGNORE_CANCELLED_AO_OPTION_KEY);
        }
        //Generate Ids
        optionKeys.add(CourseOfferingServiceConstants.APPEND_COURSE_OFFERING_IN_SUFFIX_OPTION_KEY);

        SocRolloverResultItemInfo item = getCourseOfferingService().rolloverCourseOffering(
                courseOfferingInfo.getId(),
                copyWrapper.getTermId(),
                optionKeys,
                ContextUtils.createDefaultContextInfo());
        CourseOfferingInfo courseOffering = getCourseOfferingService().getCourseOffering(item.getTargetCourseOfferingId(), ContextUtils.createDefaultContextInfo());
        ExistingCourseOffering newWrapper = new ExistingCourseOffering(courseOffering);
        newWrapper.setCredits(courseOffering.getCreditCnt());
        newWrapper.setGrading(getGradingOption(courseOffering.getGradingOptionId()));
        copyWrapper.getExistingOfferingsInCurrentTerm().add(newWrapper);
        // reload the COs
        getViewHelperService(theForm).loadCourseOfferingsByTermAndSubjectCode(copyWrapper.getTermId(), theForm.getSubjectCode(), theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_CO_PAGE);
    }

    private String getGradingOption(String gradingOptionId)throws Exception{
        String gradingOption = "";
        if(StringUtils.isNotBlank(gradingOptionId)){
            ResultValuesGroupInfo rvg = getLrcService().getResultValuesGroup(gradingOptionId, ContextUtils.createDefaultContextInfo());
            if(rvg!= null && StringUtils.isNotBlank(rvg.getName())){
                gradingOption = rvg.getName();
            }
        }

        return gradingOption;
    }

    protected AcademicCalendarService getAcademicCalendarService() {
        if(academicCalendarService == null) {
            academicCalendarService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.academicCalendarService;
    }

    protected LRCService getLrcService() {
        if(lrcService == null) {
            lrcService = CourseOfferingResourceLoader.loadLrcService();
        }
        return this.lrcService;
    }

    @RequestMapping(params = "methodToCall=copyCourseOfferingCancel")
    public ModelAndView copyCourseOfferingCancel(
            @ModelAttribute("KualiForm") CourseOfferingManagementForm theForm,
            @SuppressWarnings("unused") BindingResult result,
            @SuppressWarnings("unused") HttpServletRequest request,
            @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_CO_PAGE);
    }

    @RequestMapping(params = "methodToCall=copyCourseOffering")
    public ModelAndView copyCourseOffering(
            @ModelAttribute("KualiForm") CourseOfferingManagementForm theForm,
            @SuppressWarnings("unused") BindingResult result,
            @SuppressWarnings("unused") HttpServletRequest request,
            @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        Object selectedObject = _getSelectedObject(theForm, "Copy"); // Receives edit wrapper, "Copy" for error message.
        if(selectedObject instanceof CourseOfferingListSectionWrapper){

            // Get the selected CourseOfferingEditWrapper.
            CourseOfferingListSectionWrapper coWrapper =  (CourseOfferingListSectionWrapper)selectedObject;
            CourseOfferingInfo courseOfferingInfo = getCourseOfferingService().getCourseOffering(coWrapper.getCourseOfferingId(), ContextUtils.createDefaultContextInfo());

            // Load activity offerings.
            getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(courseOfferingInfo, theForm);

            // Create a new CourseOfferingCopyWrapper from the Course Offering information.
            CourseOfferingCopyWrapper coCopyWrapper = new CourseOfferingCopyWrapper();

            // Add items that the page wrapper intends to displaying.
            coCopyWrapper.setCoInfo(courseOfferingInfo);
            coCopyWrapper.setCourseOfferingCode(courseOfferingInfo.getCourseOfferingCode());
            coCopyWrapper.setCourseTitle(courseOfferingInfo.getCourseOfferingTitle());
            coCopyWrapper.setTermId(courseOfferingInfo.getTermId());
            coCopyWrapper.setCreditCount(courseOfferingInfo.getCreditCnt());
            coCopyWrapper.setGradingOption(courseOfferingInfo.getGradingOptionName());
            coCopyWrapper.setStudentRegistrationGradingOptionsList(courseOfferingInfo.getStudentRegistrationGradingOptions());
            coCopyWrapper.setFinalExamType(courseOfferingInfo.getFinalExamType());
            coCopyWrapper.setWaitlistLevelTypeKey(courseOfferingInfo.getWaitlistLevelTypeKey());
            coCopyWrapper.setWaitlistTypeKey(courseOfferingInfo.getWaitlistTypeKey());
            if(courseOfferingInfo.getIsHonorsOffering() != null) {
                coCopyWrapper.setIsHonors(courseOfferingInfo.getIsHonorsOffering());
            } else {
                coCopyWrapper.setIsHonors(false);
            }
            coCopyWrapper.setActivityOfferingWrapperList(theForm.getActivityWrapperList());

            // Add it to the Copy Wrapper List.
            theForm.setCourseOfferingCopyWrapper(coCopyWrapper);
        } else { //TODO log error
            theForm.setCourseOfferingCopyWrapper(null);
        }
        return getUIFModelAndView(theForm, CourseOfferingConstants.COPY_CO_PAGE);
    }


    @RequestMapping(params = "methodToCall=selectAllActivityOfferings")
    public ModelAndView selectAllActivityOfferings(
            @ModelAttribute("KualiForm")
            CourseOfferingManagementForm theForm,
            @SuppressWarnings("unused") BindingResult result,
            @SuppressWarnings("unused") HttpServletRequest request,
            @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        List<ActivityOfferingWrapper> list = theForm.getActivityWrapperList();
        for (ActivityOfferingWrapper listElement : list) {
            listElement.setIsChecked(true);
        }
        return getUIFModelAndView(theForm);
    }


    @RequestMapping(params = "methodToCall=loadCOs")
    public ModelAndView loadCOs(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        CourseOfferingInfo theCourseOffering = theForm.getTheCourseOffering();
        String subjectCode = theCourseOffering.getSubjectArea();
        String termId = theForm.getTermInfo().getId();
        theForm.setInputCode(subjectCode);
        getViewHelperService(theForm).loadCourseOfferingsByTermAndSubjectCode(termId, subjectCode, theForm);
        theForm.setSubjectCode(subjectCode);
        String longNameDescr = getOrgNameDescription(theForm.getSubjectCode());
        theForm.setSubjectCodeDescription(longNameDescr);
        //clean up theCourseOffering value in the form to prevent the
        //side effect of the authorization.
        theForm.setTheCourseOffering(null);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_CO_PAGE);
    }

    /*
     * Method used to copy activityOffering
     */
    @RequestMapping(params = "methodToCall=copyAO")
    public ModelAndView copyAO( @ModelAttribute("KualiForm") CourseOfferingManagementForm form, @SuppressWarnings("unused") BindingResult result,
                              @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {
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

    /*
     * Method used to delete a list of selected Draft activity Offerings
     */
    @RequestMapping(params = "methodToCall=cancelDeleteAOs")
    public ModelAndView cancelDeleteAOs(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                        @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(theForm.getTheCourseOffering(), theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);
    }

    /*
     * Method used to delete a list of selected Draft activity Offerings
     */
    @RequestMapping(params = "methodToCall=deleteSelectedAoList")
    public ModelAndView deleteSelectedAoList(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                             @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        List<ActivityOfferingWrapper> selectedAolist = theForm.getSelectedToDeleteList();

        try{
            for(ActivityOfferingWrapper ao : selectedAolist)  {
                getCourseOfferingService().deleteActivityOfferingCascaded(ao.getAoInfo().getId(), ContextBuilder.loadContextInfo());
            }


            // check for changes to states in CO and related FOs
            ViewHelperUtil.updateCourseOfferingStateFromActivityOfferingStateChange(theForm.getTheCourseOffering(), ContextBuilder.loadContextInfo());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(theForm.getTheCourseOffering(), theForm);
        if(selectedAolist.size() > 0 && theForm.isSelectedIllegalAOInDeletion())  {
            GlobalVariables.getMessageMap().putWarningForSectionId("manageActivityOfferingsPage",
                    CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_SELECTED_AO_TO_DELETE);
        }

        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);

    }

    /*
     * Method used to confirm delete AOs
     */
    @RequestMapping(params = "methodToCall=deleteCoConfirmation")
    public ModelAndView deleteCoConfirmation(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {

        CourseOfferingInfo theCourseOffering = null;

        String selectedCollectionPath = theForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if ( ! StringUtils.isBlank(selectedCollectionPath)) {
            Object selectedObject = _getSelectedObject(theForm, "deleteCo");
            CourseOfferingListSectionWrapper coWrapper =  (CourseOfferingListSectionWrapper)selectedObject;
            theCourseOffering = getCourseOfferingService().getCourseOffering(coWrapper.getCourseOfferingId(), ContextUtils.createDefaultContextInfo());
            theForm.setTheCourseOffering(theCourseOffering);
        }

        if (theCourseOffering == null) {
            theCourseOffering = theForm.getTheCourseOffering();
        }
        if (theCourseOffering == null) {
            throw new RuntimeException("No Course Offering selected!");
        }

        //  Verify the state of the CourseOffering is appropriate for deleting.
        //  FIXME: This logic is duplicated in CoreOfferingEditWrapper.isLegalToDelete().
        String coState = theCourseOffering.getStateKey();
        if (StringUtils.equals(coState, LuiServiceConstants.LUI_CO_STATE_OFFERED_KEY)
                || StringUtils.equals(coState, LuiServiceConstants.LUI_CO_STATE_CANCELED_KEY)) {
            LOG.error(String.format("Course offering [%s] cannot not be deleted because the state was [%s].",
                theCourseOffering.getCourseOfferingCode(), coState));
            GlobalVariables.getMessageMap().putErrorForSectionId(CourseOfferingConstants.MANAGE_CO_LIST_SECTION,
                CourseOfferingConstants.COURSEOFFERING_INVALID_STATE_FOR_DELETE);
            return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_CO_PAGE);
        }

        theForm.setCourseOfferingCode(theCourseOffering.getCourseOfferingCode());

        // Load activity offerings
        try {
            getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(theCourseOffering, theForm);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for (ActivityOfferingWrapper ao : theForm.getActivityWrapperList()) {
            //  Verify AO state.
            if ( ! ao.isLegalToDelete()) {
                LOG.error(String.format("Course Offering [%s] cannot be deleted because Activity Offering [%s] is in an invalid state for deleting.",
                    theCourseOffering.getCourseOfferingCode(), ao.getActivityCode()));
                GlobalVariables.getMessageMap().putErrorForSectionId(CourseOfferingConstants.MANAGE_CO_LIST_SECTION,
                        CourseOfferingConstants.COURSEOFFERING_INVALID_AO_STATE_FOR_DELETE);
                 return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_CO_PAGE);
            }
        }

        return getUIFModelAndView(theForm, CourseOfferingConstants.CO_DELETE_CONFIRM_PAGE);
    }

    /*
     * Method used to delete a Course Offering with all Draft activity Offerings
     */
    @RequestMapping(params = "methodToCall=deleteCo")
    public ModelAndView deleteCo(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) {
        CourseOfferingInfo  theCourseOffering = theForm.getTheCourseOffering();
        if (theCourseOffering == null) {
            throw new RuntimeException("No Course Offering selected!");
        }

        String termId = theCourseOffering.getTermId();
        String subjectCode = theCourseOffering.getSubjectArea();
        //  Load AOs
        if (theForm.getActivityWrapperList().isEmpty()) {
            try {
                getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(theCourseOffering, theForm);
            } catch (Exception e) {
                LOG.error(String.format("Could not get AOs for Course offering [%s].", theCourseOffering.getCourseOfferingCode()));
                throw new RuntimeException(e);
            }
        }

        try {
            for (ActivityOfferingWrapper ao : theForm.getActivityWrapperList()) {
                // Verify AO status
                if ( ! ao.isLegalToDelete()) {
                    LOG.error(String.format("Course Offering [%s] cannot be deleted because Activity Offering [%s] is in an invalid state for deleting.",
                        theCourseOffering.getCourseOfferingCode(), ao.getActivityCode()));
                    GlobalVariables.getMessageMap().putErrorForSectionId(CourseOfferingConstants.MANAGE_CO_LIST_SECTION,
                            CourseOfferingConstants.COURSEOFFERING_INVALID_AO_STATE_FOR_DELETE);
                    return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_CO_PAGE);
                }
            }
            CourseOfferingResourceLoader.loadCourseOfferingService().deleteCourseOfferingCascaded(theCourseOffering.getId(), ContextBuilder.loadContextInfo());
            // Reload existing COs
            getViewHelperService(theForm).loadCourseOfferingsByTermAndSubjectCode(termId, subjectCode, theForm);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_CO_PAGE);
    }

    /*
     * Method used to delete a Course Offering with all Draft activity Offerings
     */
    @RequestMapping(params = "methodToCall=cancelDeleteCo")
    public ModelAndView cancelDeleteCo(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) {
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
                throw new RuntimeException(e);
            }

        }

        try {
            getViewHelperService(theForm).loadCourseOfferingsByTermAndSubjectCode(termId, subjectCode, theForm);
        } catch (Exception e) {
            LOG.error("Could not load course offerings.", e);
        }

        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_CO_PAGE);
    }

    /*
    * Method used to invoke the Edit CO screen from Manage Course Offering screen while search input is Course Offering
    * Code (04a screen)
    */
    @RequestMapping(params = "methodToCall=editTheCO")
    public ModelAndView editTheCO(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                  @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        CourseOfferingInfo theCourseOfferingInfo = theForm.getTheCourseOffering();
        Properties urlParameters = _buildCOURLParameters(theCourseOfferingInfo,KRADConstants.Maintenance.METHOD_TO_CALL_EDIT);
        String controllerPath = KRADConstants.Maintenance.REQUEST_MAPPING_MAINTENANCE;
        return super.performRedirect(theForm,controllerPath, urlParameters);
    }

    /*
     * Method used to edit a selected CO or AO
     */
    @RequestMapping(params = "methodToCall=edit")
    public ModelAndView edit(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {

        Properties urlParameters = new Properties();
        Object selectedObject = _getSelectedObject(theForm, "edit");

        if (selectedObject instanceof CourseOfferingListSectionWrapper) {
            CourseOfferingListSectionWrapper coWrapper =  (CourseOfferingListSectionWrapper)selectedObject;
            CourseOfferingInfo courseOfferingInfo = getCourseOfferingService().getCourseOffering(coWrapper.getCourseOfferingId(), ContextUtils.createDefaultContextInfo());
            urlParameters = _buildCOURLParameters(courseOfferingInfo,KRADConstants.Maintenance.METHOD_TO_CALL_EDIT);
            String controllerPath = KRADConstants.Maintenance.REQUEST_MAPPING_MAINTENANCE;
            return super.performRedirect(theForm,controllerPath, urlParameters);
        } else if(selectedObject instanceof ActivityOfferingWrapper) {

            ActivityOfferingWrapper aoWrapper = (ActivityOfferingWrapper)selectedObject;

            urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.Maintenance.METHOD_TO_CALL_EDIT);
            urlParameters.put(ActivityOfferingConstants.ACTIVITY_OFFERING_WRAPPER_ID, aoWrapper.getAoInfo().getId());
            urlParameters.put(ActivityOfferingConstants.ACTIVITYOFFERING_COURSE_OFFERING_ID, theForm.getTheCourseOffering().getId());
            urlParameters.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, ActivityOfferingWrapper.class.getName());
            urlParameters.put(UifConstants.UrlParams.SHOW_HOME, BooleanUtils.toStringTrueFalse(false));
            return super.performRedirect(theForm, "activityOffering", urlParameters);
        } else {
            throw new RuntimeException("Invalid type. Does not support for now");
        }
    }

    /*
     *  Determine if any AOs were check-boxed.
     *  @return True if any AOs where selected. Otherwise, false.
     */
    private boolean hasSelectedActivityOfferings(CourseOfferingManagementForm theForm) {
        boolean bIsSelected = false;
        List<ActivityOfferingWrapper> list = theForm.getActivityWrapperList();
        for (ActivityOfferingWrapper activityOfferingWrapper : list) {
            if (activityOfferingWrapper.getIsChecked()) {
                bIsSelected = true;
                break;
            }
        }
        return bIsSelected;
    }

    /*
     * Method used to pick the selected AO actions
     */
    @RequestMapping(params = "methodToCall=selectedAoActions")
    public ModelAndView selectedAoActions(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                      @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        //  Stop here if no AOs are selected.
        if ( ! hasSelectedActivityOfferings(theForm)) {
            GlobalVariables.getMessageMap().putError("manageActivityOfferingsPage", CourseOfferingConstants.NO_AOS_SELECTED);
            return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);
        }

        if (StringUtils.equals(theForm.getSelectedOfferingAction(), CourseOfferingConstants.ACTIVITY_OFFERING_DELETE_ACTION)){
            return confirmDelete(theForm, result, request, response);
        }

        if (StringUtils.equals(theForm.getSelectedOfferingAction(), CourseOfferingConstants.ACTIVITY_OFFERING_DRAFT_ACTION) ||
            StringUtils.equals(theForm.getSelectedOfferingAction(), CourseOfferingConstants.ACTIVITY_OFFERING_SCHEDULING_ACTION)) {
            getViewHelperService(theForm).changeActivityOfferingsState(theForm.getActivityWrapperList(), theForm.getTheCourseOffering(), theForm.getSelectedOfferingAction());
        }

        // Reload the AOs
        CourseOfferingInfo theCourseOffering = theForm.getTheCourseOffering();
        getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(theCourseOffering, theForm);
        getViewHelperService(theForm).loadPreviousAndNextCourseOffering(theForm, theCourseOffering);

        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);
    }

    /*
     *  Determine if any COs were check-boxed.
     *  @return True if any COs where selected. Otherwise, false.
     */
    private boolean hasSelectedCourseOfferings(CourseOfferingManagementForm theForm) {
        boolean isSelected = false;
        List<CourseOfferingListSectionWrapper> list = theForm.getCourseOfferingResultList();
        for (CourseOfferingListSectionWrapper coWrapper : list) {
            if (coWrapper.getIsChecked()) {
                isSelected = true;
                break;
            }
        }
        return isSelected;
    }

    @RequestMapping(params = "methodToCall=selectedCOActions")
    public ModelAndView selectedCOActions(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                          @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        //  Stop here if no COs are selected.
        if ( ! hasSelectedCourseOfferings(theForm)) {
            GlobalVariables.getMessageMap().putError("manageActivityOfferingsPage", CourseOfferingConstants.COURSEOFFERING_NONE_SELECTED);
            return getUIFModelAndView(theForm);
        }

        if (StringUtils.equals(theForm.getSelectedOfferingAction(),CourseOfferingConstants.ACTIVITY_OFFERING_SCHEDULING_ACTION)) {
            getViewHelperService(theForm).markCourseOfferingsForScheduling(theForm.getCourseOfferingResultList());
            getViewHelperService(theForm).loadCourseOfferingsByTermAndSubjectCode(theForm.getTermInfo().getId(), theForm.getInputCode(),theForm);
        }

        return getUIFModelAndView(theForm);

    }

    /*
     * Method used to confirm delete AOs
     */
    @RequestMapping(params = "methodToCall=confirmDelete")
    public ModelAndView confirmDelete(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                             @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        Collection<Object> collection;
        Object selectedObject;
        List<ActivityOfferingWrapper> aoList = theForm.getActivityWrapperList();
        List<ActivityOfferingWrapper> selectedIndexList = theForm.getSelectedToDeleteList();
        boolean bEncounteredNonDraftAOInDeletion = false;

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
            selectedIndexList.add((ActivityOfferingWrapper) selectedObject);
        }
        else {
            // check if there is Draft AO selected
            selectedIndexList.clear();
            for(ActivityOfferingWrapper ao : aoList) {
                if(ao.isLegalToDelete() && ao.getIsChecked()) {
                    selectedIndexList.add(ao);
                } else if (ao.getIsChecked()){
                    if (!bEncounteredNonDraftAOInDeletion) {
                        bEncounteredNonDraftAOInDeletion = true;
                    }
                }
            }
        }

        if (selectedIndexList.isEmpty()) {
            if (bEncounteredNonDraftAOInDeletion) {
                GlobalVariables.getMessageMap().putError("manageActivityOfferingsPage",
                        CourseOfferingConstants.AO_NOT_DRAFT_FOR_DELETION_ERROR);
            } else {
                GlobalVariables.getMessageMap().putError("manageActivityOfferingsPage",
                        CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_FOUND_NO_DRAFT_AO_SELECTED);
            }
            return getUIFModelAndView(theForm);
        }  else {
            theForm.setSelectedIllegalAOInDeletion(false);
            if (bEncounteredNonDraftAOInDeletion) {
                theForm.setSelectedIllegalAOInDeletion(true);
            }
        }

        return getUIFModelAndView(theForm, CourseOfferingConstants.AO_DELETE_CONFIRM_PAGE);
    }

    /*
     * Method used to view a CO or an AO
     */
    @RequestMapping(params = "methodToCall=view")
    public ModelAndView view(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                      @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        Properties urlParameters = new Properties();
        String controllerPath = "inquiry";
        Object selectedObject = _getSelectedObject(theForm, "view");

        if(selectedObject instanceof CourseOfferingInfo){
            urlParameters = _buildCOURLParameters((CourseOfferingInfo)selectedObject,KRADConstants.START_METHOD);
        } else if(selectedObject instanceof ActivityOfferingWrapper) {
            ActivityOfferingWrapper aoWrapper = (ActivityOfferingWrapper)selectedObject;
            urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.START_METHOD);
            urlParameters.put(ActivityOfferingConstants.ACTIVITYOFFERING_ID, aoWrapper.getAoInfo().getId());
            urlParameters.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, ActivityOfferingInfo.class.getName());
        } else {
            throw new RuntimeException("Invalid type. Does not support for now");
        }

        return super.performRedirect(theForm,controllerPath, urlParameters);
    }

    @RequestMapping(params = "methodToCall=addActivityOfferings")
    public ModelAndView addActivityOfferings(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                             @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

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
    public ModelAndView createCourseOffering(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                             @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        String termCode = theForm.getTermCode();

        Properties props = new Properties();
        props.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.START_METHOD);
        props.put("targetTermCode", termCode);
        props.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, "org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCreateWrapper");

         return super.performRedirect(theForm, "courseOffering", props);
    }

    @RequestMapping(params = "methodToCall=markSubjectCodeReadyForScheduling")
    public ModelAndView markSubjectCodeReadyForScheduling(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {
        CourseOfferingManagementViewHelperServiceImpl helperService = (CourseOfferingManagementViewHelperServiceImpl) theForm.getView().getViewHelperService();
        //  State change all of the AOs associated with all CourseOfferings related to the course code. Passing false so that the isChecked() flag is ignored.
        helperService.markCourseOfferingsForScheduling(theForm.getCourseOfferingResultList(), false);
        getViewHelperService(theForm).loadCourseOfferingsByTermAndSubjectCode(theForm.getTermInfo().getId(), theForm.getInputCode(),theForm);
        return getUIFModelAndView(theForm);
    }

    /*
     * Method used to invoke the CO inquiry view from Manage Course Offering screen while search input is Course Offering
     * Code (04a screen)
     */
    @RequestMapping(params = "methodToCall=viewTheCO")
    public ModelAndView viewTheCO(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                  @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        CourseOfferingInfo theCourseOfferingInfo = theForm.getTheCourseOffering();
        Properties urlParameters = _buildCOURLParameters(theCourseOfferingInfo,KRADConstants.START_METHOD);
        String controllerPath = KRADConstants.PARAM_MAINTENANCE_VIEW_MODE_INQUIRY;
        return super.performRedirect(theForm,controllerPath, urlParameters);
    }

    private Properties _buildCOURLParameters(CourseOfferingInfo courseOfferingInfo, String methodToCall){
        Properties props = new Properties();
        props.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, methodToCall);
        props.put("coInfo.id", courseOfferingInfo.getId());
        props.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, CourseOfferingEditWrapper.class.getName());
        props.put(UifConstants.UrlParams.SHOW_HOME, BooleanUtils.toStringTrueFalse(false));
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
        Object selectedObject;
        selectedObject = ((List<Object>) collection).get(selectedLineIndex);

        return selectedObject;
    }


    public String getOrgNameDescription(String orgShortName) {
        String shortName = "shortName";
        String longName = "";

        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        if (StringUtils.isNotBlank(orgShortName) && !orgShortName.isEmpty()) {
            qBuilder.setPredicates(PredicateFactory.or(
                    PredicateFactory.equal(shortName, orgShortName)));
        } else {
            throw new RuntimeException("Org short name is null!");
        }
        try {
            QueryByCriteria query = qBuilder.build();

            OrganizationService  organizationService = getOrganizationService();

            java.util.List<OrgInfo> orgInfos = organizationService.searchForOrgs(query, ContextUtils.createDefaultContextInfo());
            if (!orgInfos.isEmpty()){
                longName = orgInfos.get(0).getLongName();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error! No long name description found.", e);
        }
        return longName;
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

    public TypeService getTypeService() {
        if(typeService == null) {
            typeService = CourseOfferingResourceLoader.loadTypeService();
        }
        return this.typeService;
    }

    public StateService getStateService() {
        if(stateService == null) {
            stateService = CourseOfferingResourceLoader.loadStateService();
        }
        return this.stateService;
    }

    public CourseOfferingService getCourseOfferingService() {
        return CourseOfferingResourceLoader.loadCourseOfferingService();
    }

    private OrganizationService getOrganizationService(){
        if(organizationService == null) {
            organizationService = (OrganizationService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "organization", "OrganizationService"));

        }
        return organizationService;
    }

    private boolean checkEditViewAuthz(CourseOfferingManagementForm theForm){
        Person user = GlobalVariables.getUserSession().getPerson();
        return theForm.getView().getAuthorizer().canEditView(theForm.getView(),theForm,user);
    }

}