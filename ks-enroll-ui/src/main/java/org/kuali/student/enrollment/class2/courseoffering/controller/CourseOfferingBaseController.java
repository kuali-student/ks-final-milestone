package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.exception.AuthorizationException;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.MaintenanceDocumentController;
import org.kuali.rice.krad.web.form.DocumentFormBase;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.common.uif.form.KSUifMaintenanceDocumentForm;
import org.kuali.student.common.uif.util.KSControllerHelper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingContextBar;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.FormatOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.OrganizationInfoWrapper;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingEditMaintainableImpl;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.common.util.EnrollConstants;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CreditOptionInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.class1.search.CourseOfferingManagementSearchImpl;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.StateServiceConstants;
import org.kuali.student.r2.core.constants.TypeServiceConstants;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.course.service.assembler.CourseAssemblerConstants;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

@Controller
@RequestMapping(value = "/courseOffering")
public class CourseOfferingBaseController extends MaintenanceDocumentController {

    private transient CourseService courseService;
    private transient TypeService typeService;
    private transient LRCService lrcService;
    private transient CourseOfferingSetService courseOfferingSetService;
    private transient AcademicCalendarService acalService;
    private transient StateService stateService;
    private transient OrganizationService organizationService;

    @Override
    protected MaintenanceDocumentForm createInitialForm(HttpServletRequest request) {
        return new KSUifMaintenanceDocumentForm();
    }

    @Override
    public ModelAndView maintenanceEdit(@ModelAttribute("KualiForm") MaintenanceDocumentForm form, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {

        setupMaintenance(form, request, KRADConstants.MAINTENANCE_EDIT_ACTION);

        // check view authorization
        // TODO: this needs to be invoked for each request
        if (form.getView() != null) {
            String methodToCall = request.getParameter(KRADConstants.DISPATCH_REQUEST_PARAMETER);
            checkViewAuthorization(form, methodToCall);
            String crossListedAlias = request.getParameter("editCrossListedCoAlias");
            if(StringUtils.equals(crossListedAlias, "true")) {
                Object selectedObject =  form.getDocument().getNewMaintainableObject().getDataObject();
                if(selectedObject instanceof CourseOfferingEditWrapper) {
                    ((CourseOfferingEditWrapper) selectedObject).setEditCrossListedCoAlias(true);
                }
            }
//            form.setEditAuthz(checkEditViewAuthz(form));
        }

        //populate the previousFormsMap of the form. The map contains info about the previous view to generate customized breadcrumb
//        KSUifUtils.populationPreviousFormsMap(request, (KSUifMaintenanceDocumentForm) form);

        return getUIFModelAndView(form);
    }

    @Override
    @RequestMapping(params = "methodToCall=route")
    public ModelAndView route(@ModelAttribute("KualiForm") DocumentFormBase form, BindingResult result,
                              HttpServletRequest request, HttpServletResponse response) {

        super.route(form, result, request, response);

        if( GlobalVariables.getMessageMap().hasErrors() ) {
            return handleRouteForErrors( form );
        }

        if( this instanceof CourseOfferingCreateController ) {
            return handleRouteForCoCreate( form );
        }

        return handleRouteForCoEdit(form);
    }

    /* Returns a ModelAndView for the route()-method to return to original view if there were errors.
     * Should only be called if there are indeed errors.
     */
    private ModelAndView handleRouteForErrors( DocumentFormBase form ) {

        assert ( GlobalVariables.getMessageMap().hasErrors() );

        if (((MaintenanceDocumentForm)form).getDocument().getNewMaintainableObject().getDataObject() instanceof CourseOfferingEditWrapper){
            CourseOfferingEditMaintainableImpl viewHelper = (CourseOfferingEditMaintainableImpl)KSControllerHelper.getViewHelperService(form);
            //Make the format type drop down readonly.. otherwise, we run into display issue when the server returns back error
            CourseOfferingEditWrapper dataObject = (CourseOfferingEditWrapper)((MaintenanceDocumentForm)form).getDocument().getNewMaintainableObject().getDataObject();
            viewHelper.populateFormatNames(dataObject);
        }

        return getUIFModelAndView(form);    // because there were errors, return a MAV to re-nav back to
    }

    /* Returns a ModelAndView for the route()-method to return a new view if we are creating a CO */
    private ModelAndView handleRouteForCoCreate( DocumentFormBase form ) {

        assert( this instanceof CourseOfferingCreateController );

        Properties urlParameters = new Properties();

        CourseOfferingEditWrapper dataObject = (CourseOfferingEditWrapper)((MaintenanceDocumentForm)form).getDocument().getNewMaintainableObject().getDataObject();

        urlParameters.put(EnrollConstants.GROWL_MESSAGE, CourseOfferingConstants.COURSE_OFFERING_CREATE_SUCCESS);
        urlParameters.put(EnrollConstants.GROWL_MESSAGE_PARAMS, dataObject.getCourseOfferingCode());

        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, "show");
        urlParameters.put("termCode", dataObject.getTerm().getCode());
        if (dataObject.getCourseOfferingInfo().getCourseNumberSuffix() != null && !StringUtils.isBlank(dataObject.getCourseOfferingInfo().getCourseNumberSuffix())) {
            urlParameters.put("inputCode", dataObject.getCourseOfferingInfo().getCourseOfferingCode().concat(dataObject.getCourseOfferingInfo().getCourseNumberSuffix()));
        } else {
            urlParameters.put("inputCode", dataObject.getCourseOfferingInfo().getCourseOfferingCode());
        }
        urlParameters.put("viewId",CourseOfferingConstants.MANAGE_CO_VIEW_ID);
        urlParameters.put("pageId",CourseOfferingConstants.MANAGE_THE_CO_PAGE);
        urlParameters.put("withinPortal","false");

        return super.performRedirect(form, CourseOfferingConstants.MANAGE_CO_CONTROLLER_PATH, urlParameters);
    }

    /* Returns a ModelAndView for the route()-method to return a new view if we are editing a CO */
    private ModelAndView handleRouteForCoEdit( DocumentFormBase form ) {

        assert ( !(this instanceof CourseOfferingCreateController) );

        String urlToRedirectTo = "";
        Properties urlParameters = new Properties();

        GlobalVariables.getUifFormManager().removeSessionForm(form);    // clear current form from session

        // create a Growl-message
        CourseOfferingEditWrapper dataObject = (CourseOfferingEditWrapper)((MaintenanceDocumentForm)form).getDocument().getNewMaintainableObject().getDataObject();
        urlParameters.put(EnrollConstants.GROWL_MESSAGE, CourseOfferingConstants.COURSE_OFFERING_EDIT_SUCCESS);
        urlParameters.put(EnrollConstants.GROWL_MESSAGE_PARAMS, dataObject.getCourseOfferingCode());

        // determine which url to redirect to
        String returnLocationFromForm = form.getReturnLocation();
        if( StringUtils.contains( returnLocationFromForm,"viewId=courseOfferingManagementView" )
                || StringUtils.contains( returnLocationFromForm,"pageId=manageTheCourseOfferingPage" ) )
        {
            if ( !returnLocationFromForm.contains("methodToCall=") ) {
                // This happens when we display a list of COs and then user click on Manage action
                form.getViewRequestParameters().put(CourseOfferingManagementSearchImpl.SearchParameters.IS_EXACT_MATCH_CO_CODE_SEARCH, Boolean.TRUE.toString());
            }
            else {
                form.getViewRequestParameters().put(CourseOfferingManagementSearchImpl.SearchParameters.IS_EXACT_MATCH_CO_CODE_SEARCH, Boolean.FALSE.toString());
                //urlToRedirectTo = returnLocationFromForm.replaceFirst("methodToCall=[a-zA-Z0-9]+","methodToCall=show");
            }
            urlToRedirectTo = returnLocationFromForm.replaceFirst("methodToCall=[a-zA-Z0-9]+","methodToCall=show");
        }
        else {
            urlToRedirectTo = returnLocationFromForm;
        }

        // special handling if navigating to a specific CO
        String loadNewCO = form.getActionParameters().get( "coId" );
        if( StringUtils.isNotBlank( loadNewCO ) ) {

            urlParameters.put( KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.Maintenance.METHOD_TO_CALL_EDIT );
            urlParameters.put( "courseOfferingInfo.id", loadNewCO );
            urlParameters.put( KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, CourseOfferingEditWrapper.class.getName() );
            urlParameters.put( UifConstants.UrlParams.SHOW_HOME, BooleanUtils.toStringTrueFalse(false) );

            urlParameters.put( "returnLocation", urlToRedirectTo );

            urlToRedirectTo = "courseOffering";
        }

        return performRedirect(form, urlToRedirectTo, urlParameters);
    }

    protected void populateCreateCourseOfferingForm(MaintenanceDocumentForm form, HttpServletRequest request) {

        try {
            ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
            MaintenanceDocument document = form.getDocument();
            String termId = request.getParameter(CourseOfferingConstants.TARGET_TERM_ID);
            CourseInfo courseInfo = getCourseService().getCourse(request.getParameter(CourseOfferingConstants.COURSE_ID), contextInfo);

            //1. set CourseOfferingInfo - no saved Course Offering as of yet (see logic in CourseOfferingEditMaintainableImpl.retrieveObjectForEditOrCopy
            CourseOfferingInfo coInfo = createCourseOfferingInfo(termId, courseInfo);
            CourseOfferingEditWrapper formObject = new CourseOfferingEditWrapper(coInfo);
            formObject.setCreateCO(true);

            //2. set CourseInfo
            formObject.setCourse(courseInfo);

            //3. set formatOfferingList
            formObject.setFormatOfferingList(new ArrayList<FormatOfferingWrapper>());
            FormatOfferingWrapper defaultFO = new FormatOfferingWrapper();
           // defaultFO.setFormatInfo(courseInfo.getFormats().get(0));
            defaultFO.setFormatId(courseInfo.getFormats().get(0).getId());
            defaultFO.getRenderHelper().setNewRow(true);
            formObject.getFormatOfferingList().add(defaultFO);

            //4. Checking if Grading Options should be disabled or not and assign default (if no value)
            //5. Checking if there are any student registration options from CLU for screen display
            List<String> studentRegOptions = new ArrayList<String>();
            List<String> crsGradingOptions = new ArrayList<String>();
            if (courseInfo != null) {
                List<String> gradingOptions = courseInfo.getGradingOptions();
                Set<String> regOpts = new HashSet<String>(Arrays.asList(CourseOfferingServiceConstants.ALL_STUDENT_REGISTRATION_OPTION_TYPE_KEYS));
                for (String gradingOption : gradingOptions) {
                    if (regOpts.contains(gradingOption)) {
                        studentRegOptions.add(gradingOption);
                    } else {
                        crsGradingOptions.add(gradingOption);
                    }
                }
                //Audit is pulled out into a dynamic attribute on course so map it back
                if("true".equals(courseInfo.getAttributeValue(CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_AUDIT))){
                    studentRegOptions.add(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT);
                }
            }

            formObject.setStudentRegOptions(studentRegOptions);
            formObject.setCrsGradingOptions(crsGradingOptions);

            //6. Defining Credit Option and if CLU is fixed (then it's disabled)
            boolean creditOptionFixed = false;
            CreditOptionInfo creditOption = new CreditOptionInfo();

            //Grab the Course's credit constraints
            //FindBugs: getCreditOptions() null check is in CourseInfo
            List<ResultValuesGroupInfo> courseCreditOptions = courseInfo.getCreditOptions();

            //Lookup the related course's credit constraints and set them on the creditOption
            if (!courseCreditOptions.isEmpty()) {
                ResultValuesGroupInfo resultValuesGroupInfo = courseCreditOptions.get(0);
                //Check for fixed
                if (resultValuesGroupInfo.getTypeKey().equalsIgnoreCase(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED)) {
                    if (!resultValuesGroupInfo.getResultValueKeys().isEmpty()) {
                        creditOption.setCourseFixedCredits(getLrcService().getResultValue(resultValuesGroupInfo.getResultValueKeys().get(0), contextInfo).getValue());
                    }
                    //Set the flag
                    creditOptionFixed = true;

                    //Default the value
                    creditOption.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED);
                    creditOption.setFixedCredit(creditOption.getCourseFixedCredits());
                    creditOption.getAllowedCredits().add(creditOption.getCourseFixedCredits());
                } else {
                    //This is either range or multiple

                    //Copy all the allowed credits and sort so that the multiple checkboxes can be properly displayed
                    List<ResultValueInfo> resultValueInfos = getLrcService().getResultValuesForResultValuesGroup(resultValuesGroupInfo.getKey(), contextInfo);
                    for (ResultValueInfo rVI: resultValueInfos) {
                        creditOption.getAllowedCredits().add(rVI.getValue());
                    }
                    Collections.sort(creditOption.getAllowedCredits());

                    if (resultValuesGroupInfo.getTypeKey().equalsIgnoreCase(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE)) {
                        creditOption.setCourseMinCredits(resultValuesGroupInfo.getResultValueRange().getMinValue());
                        creditOption.setCourseMaxCredits(resultValuesGroupInfo.getResultValueRange().getMaxValue());

                        //Default the value
                        creditOption.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE);
                        creditOption.setMinCredits(creditOption.getCourseMinCredits());
                        creditOption.setMaxCredits(creditOption.getCourseMaxCredits());
                    } else if (resultValuesGroupInfo.getTypeKey().equalsIgnoreCase(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE)) {
                        //Default the value
                        creditOption.setTypeKey(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE);
                        creditOption.getCredits().addAll(creditOption.getAllowedCredits());
                    }
                }
            }

            formObject.setCreditOption(creditOption);
            formObject.setCreditOptionFixed(creditOptionFixed);

            formObject.setOrganizationNames(new ArrayList<OrganizationInfoWrapper>());

            ArrayList<OrganizationInfoWrapper> orgList = new ArrayList<OrganizationInfoWrapper>();

            // ??? Do we have to pull them off courseInfo ?
            if(courseInfo.getUnitsDeployment() != null){
                for(String orgId: courseInfo.getUnitsDeployment()){
                    OrgInfo orgInfo = getOrganizationService().getOrg(orgId,contextInfo);
                    orgList.add(new OrganizationInfoWrapper(orgInfo));
                }
            }
            formObject.setOrganizationNames(orgList);

            // adding SOC
            SocInfo socInfo = getCourseOfferingSetService().getSoc(request.getParameter(CourseOfferingConstants.SOC_ID), contextInfo);
            formObject.setSocInfo(socInfo);

            setTermPropertiesOnFormObject(formObject, termId, contextInfo );
            formObject.setContextBar(CourseOfferingContextBar.NEW_INSTANCE(formObject.getTerm(), formObject.getSocInfo(),
                    getStateService(), getAcalService(), contextInfo) );

            document.getNewMaintainableObject().setDataObject(formObject);
            document.getOldMaintainableObject().setDataObject(formObject);
            document.getDocumentHeader().setDocumentDescription("Create CO - " + courseInfo.getCode());

            // Authz check
            Person user = GlobalVariables.getUserSession().getPerson();
            boolean canOpenView = this.getDocumentDictionaryService().getDocumentAuthorizer(document).canOpen(document,user);

            // Work around, should be fixed with KULRICE-8049
            if (!canOpenView) {
                throw new AuthorizationException(user.getPrincipalName(), "open", null,
                        "User '" + user.getPrincipalName() + "' is not authorized to open view", null);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setTermPropertiesOnFormObject(CourseOfferingEditWrapper formObject, String termId, ContextInfo contextInfo ) throws Exception {

        TermInfo termInfo = getAcalService().getTerm(termId, contextInfo);
        formObject.setTerm(termInfo);
        formObject.setTermName(termInfo.getName());

        // Setting term string: Fall 2012 (09/28/2012 to 12/15/2012)
        String termStartDate = new String( DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.format( termInfo.getStartDate() ) );
        String termEndDate = new String( DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.format( termInfo.getEndDate() ) );
        StringBuilder termStartEnd = new StringBuilder();
        termStartEnd.append( termInfo.getName() );
        termStartEnd.append( " (" );
        termStartEnd.append( termStartDate );
        termStartEnd.append( " to " );
        termStartEnd.append( termEndDate );
        termStartEnd.append( ")" );
        formObject.setTermStartEnd( termStartEnd.toString() );
    }

    protected CourseOfferingInfo createCourseOfferingInfo(String termId, CourseInfo courseInfo) throws Exception {

        CourseOfferingInfo courseOffering = new CourseOfferingInfo();

        courseOffering.setTermId(termId);
        courseOffering.setCourseOfferingTitle(courseInfo.getCourseTitle());
        courseOffering.setCourseId(courseInfo.getId());
        courseOffering.setCourseCode(courseInfo.getCode());
        courseOffering.setTypeKey(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY);
        courseOffering.setStateKey(LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY);
        courseOffering.setHasWaitlist(false);
        courseOffering.setCourseOfferingCode(courseInfo.getCode());

        //Copy grading and credit options
        if(!courseInfo.getCreditOptions().isEmpty()){
            courseOffering.setCreditOptionId(courseInfo.getCreditOptions().get(0).getKey());
        }
        //Remove these two special student registration options and set them on the CO
        List<String> courseGradingOptions = new ArrayList<String>(courseInfo.getGradingOptions());
        if(courseGradingOptions.remove(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL) ){
            courseOffering.getStudentRegistrationGradingOptions().add(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_PASSFAIL);
        }
        if(courseGradingOptions.remove(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT) ){
            courseOffering.getStudentRegistrationGradingOptions().add(LrcServiceConstants.RESULT_GROUP_KEY_GRADE_AUDIT);
        }
        //set the first remaining grading option on the CO
        if(!courseGradingOptions.isEmpty()){
            courseOffering.setGradingOptionId(courseGradingOptions.get(0));
        }

        // make sure we set attribute information from the course
        if(!courseInfo.getAttributes().isEmpty()){
            for(AttributeInfo info: courseInfo.getAttributes()){
                // Default the CourseOffering Final Exam Type to the Final Exam type in the Course
                if(info.getKey().equals("finalExamStatus")){
                    courseOffering.setFinalExamType(convertCourseFinalExamTypeToCourseOfferingFinalExamType(info.getValue()));
                }
            }
        }

        return courseOffering;
    }

    protected static String convertCourseFinalExamTypeToCourseOfferingFinalExamType(String courseFinalExamType){
        String sRet = null;
        if("STD".equals(courseFinalExamType))   {
            sRet = CourseOfferingConstants.COURSEOFFERING_FINAL_EXAM_TYPE_STANDARD;
        } else if("ALT".equals(courseFinalExamType)) {
            sRet = CourseOfferingConstants.COURSEOFFERING_FINAL_EXAM_TYPE_ALTERNATE;
        }
        return sRet;
    }

    @RequestMapping(params = "methodToCall=cancel")
    @Override
    public ModelAndView cancel(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {

        DocumentFormBase documentForm = (DocumentFormBase) form;
        performWorkflowAction(documentForm, UifConstants.WorkflowAction.CANCEL, false);

        String urlToRedirectTo = "";
        Properties urlParameters = new Properties();

        // determine which url to redirect to
        String returnLocationFromForm = form.getReturnLocation();
        if( StringUtils.contains( returnLocationFromForm,"viewId=courseOfferingManagementView" )
                || StringUtils.contains( returnLocationFromForm,"pageId=manageTheCourseOfferingPage" ) )
        {
            if ( !returnLocationFromForm.contains("methodToCall=") ) {  // This happens when we display a list of COs and then user click on Manage action
                form.getViewRequestParameters().put(CourseOfferingManagementSearchImpl.SearchParameters.IS_EXACT_MATCH_CO_CODE_SEARCH, Boolean.TRUE.toString());
            }
            else {
                form.getViewRequestParameters().put(CourseOfferingManagementSearchImpl.SearchParameters.IS_EXACT_MATCH_CO_CODE_SEARCH, Boolean.FALSE.toString());
            }
            urlToRedirectTo = returnLocationFromForm.replaceFirst("methodToCall=[a-zA-Z0-9]+","methodToCall=show");
        }
        else {
            urlToRedirectTo = returnLocationFromForm;
        }

        String loadNewCO = form.getActionParameters().get( "coId" );
        if( StringUtils.isNotBlank( loadNewCO ) ) {

            urlParameters.put( KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.Maintenance.METHOD_TO_CALL_EDIT );
            urlParameters.put( "courseOfferingInfo.id", loadNewCO );
            urlParameters.put( KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, CourseOfferingEditWrapper.class.getName() );
            urlParameters.put( UifConstants.UrlParams.SHOW_HOME, BooleanUtils.toStringTrueFalse(false) );

            urlParameters.put( "returnLocation", urlToRedirectTo );

            urlToRedirectTo = "courseOffering";
            GlobalVariables.getUifFormManager().removeSessionForm(form);
            return performRedirect(form, urlToRedirectTo, urlParameters);
        }

        form.setReturnLocation( urlToRedirectTo );
        return back(form,result,request,response);
    }


    /**
     * This override is specifically for making sure users selected the format types before adding new lines (there is no
     * util method available at view helper).
     *
     * @param form
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, params = "methodToCall=addBlankLine")
    public ModelAndView addBlankLine(@ModelAttribute("KualiForm") UifFormBase form) {

        boolean validAction = true;
        if (((MaintenanceDocumentForm)form).getDocument().getNewMaintainableObject().getDataObject() instanceof CourseOfferingEditWrapper){
            CourseOfferingEditWrapper dataObject = (CourseOfferingEditWrapper)((MaintenanceDocumentForm)form).getDocument().getNewMaintainableObject().getDataObject();
            String selectedCollectionPath = form.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
            if (StringUtils.endsWith(selectedCollectionPath, "formatOfferingList")) {
                for (FormatOfferingWrapper foWrapper : dataObject.getFormatOfferingList()){
                    if (StringUtils.isBlank(foWrapper.getFormatOfferingInfo().getFormatId())){
                        GlobalVariables.getMessageMap().putError("KS-CourseOfferingEdit-DeliveryFormats",CourseOfferingConstants.COURSEOFFERING_FORMAT_REQUIRED);
                        validAction = false;
                        break;
                    }
                }
            }
        }

        if (validAction){
            return super.addBlankLine(form);
        } else {
            return getUIFModelAndView(form);
        }

    }


    protected CourseService getCourseService() {
        if(courseService == null) {
            courseService = CourseOfferingResourceLoader.loadCourseService();
        }
        return this.courseService;
    }

    protected LRCService getLrcService() {
        if(lrcService == null) {
            lrcService = (LRCService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/lrc", "LrcService"));
        }
        return this.lrcService;
    }

    protected CourseOfferingSetService getCourseOfferingSetService(){
        if (courseOfferingSetService == null){
            courseOfferingSetService = (CourseOfferingSetService) GlobalResourceLoader.getService(new QName(CourseOfferingSetServiceConstants.NAMESPACE, CourseOfferingSetServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return courseOfferingSetService;
    }

    protected AcademicCalendarService getAcalService() {
        if(acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/acal", "AcademicCalendarService"));
        }
        return this.acalService;
    }

    protected StateService getStateService() {
        if( stateService == null ) {
            stateService = (StateService) GlobalResourceLoader.getService( new QName(StateServiceConstants.NAMESPACE, StateServiceConstants.SERVICE_NAME_LOCAL_PART) );
        }
        return stateService;
    }

    private OrganizationService getOrganizationService(){
        if(organizationService == null) {
            organizationService = (OrganizationService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "organization", "OrganizationService"));
        }
        return organizationService;
    }

    protected TypeService getTypeService() {
        if(typeService == null) {
            typeService = (TypeService) GlobalResourceLoader.getService(new QName(TypeServiceConstants.NAMESPACE, TypeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.typeService;
    }
}
