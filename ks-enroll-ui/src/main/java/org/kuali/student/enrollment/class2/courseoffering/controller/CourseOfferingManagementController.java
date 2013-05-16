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
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCopyWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingListSectionWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ExistingCourseOffering;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.service.CourseOfferingManagementViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingManagementViewHelperServiceImpl;
import org.kuali.student.enrollment.class2.courseoffering.util.ActivityOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingViewHelperUtil;
import org.kuali.student.enrollment.class2.courseoffering.util.RegistrationGroupConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.ToolbarUtil;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.dto.SocRolloverResultItemInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.enrollment.uif.util.GrowlIcon;
import org.kuali.student.enrollment.uif.util.KSUifUtils;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * This is the controller class what handles all the requests (actions) from the <i>'Manage Course Offering'</i> ui.
 *
 * @see CourseOfferingManagementForm
 * @see CourseOfferingManagementViewHelperService
 */
//@Controller
//@RequestMapping(value = "/courseOfferingManagement")
@Deprecated
/**
 * @deprecated
 * @see org.kuali.student.enrollment.class2.autogen.controller.ARGCourseOfferingManagementController
 */
public class CourseOfferingManagementController extends UifControllerBase  {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(CourseOfferingManagementController.class);
    private LRCService lrcService;
    private AcademicCalendarService academicCalendarService;
    private TypeService typeService;
    private StateService stateService;
    private CourseOfferingManagementViewHelperService viewHelperService;
    private OrganizationService organizationService;
    private CourseOfferingSetService socService;

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new CourseOfferingManagementForm();
    }

    @Override
    @RequestMapping(params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase uifForm, @SuppressWarnings("unused") BindingResult result,
            @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

        if (!(uifForm instanceof CourseOfferingManagementForm)){
            throw new RuntimeException("Form object passed into start method was not of expected type CourseOfferingManagementForm. Got "+uifForm.getClass().getSimpleName());
        }

        CourseOfferingManagementForm form = (CourseOfferingManagementForm)uifForm;

        // set adminOrg to the form to temporarily overcome that we actually need page level authorization but not view
        // level authorization.
        String inputValue = request.getParameter("adminOrg");
        if ((inputValue != null) && !inputValue.isEmpty()){
            form.setAdminOrg(inputValue);
        }

        //clean up termCode, inputCode, and theCourseOffering value in the form to prevent the
        //side effect of the authorization.
        form.setTermCode(null);
        form.setInputCode(null);
        form.setCurrentCourseOfferingWrapper(null);

        // check view authorization
        // TODO: this needs to be invoked for each request
        if (form.getView() != null) {
            String methodToCall = request.getParameter(KRADConstants.DISPATCH_REQUEST_PARAMETER);
            checkViewAuthorization(form, methodToCall);
            form.setEditAuthz(checkEditViewAuthz(form));
        }

        // check if the view is invoked within portal or not
        inputValue = request.getParameter("withinPortal");
        if ((inputValue != null) && !inputValue.isEmpty()){
            boolean withinPortal = Boolean.valueOf(request.getParameter("withinPortal"));
            form.setWithinPortal(withinPortal);
        }

        return getUIFModelAndView(form);
    }

    /**
     * <p>
     *     
     * Method used to
     *  1) search to get TermInfo based on termCode. Only accept one valid TermInfo. If find more than one TermInfo or
     *  don't find any termInfo, log and report an error message.
     *  2) If the input is subject code, load all course offerings based on termId and subjectCode
     *  3) If the input is course offering code,
     *      a)find THE course offering based on termId and courseOfferingCode. If find more than one CO or don't find
     *        any CO, log and report an error message.
     *      b)load all activity offerings based on the courseOfferingId
     *      
     * </p>     
     * @param form
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=show")
    public ModelAndView show(@ModelAttribute("KualiForm") CourseOfferingManagementForm form) throws Exception {

        form.setInputCode(form.getInputCode().toUpperCase());
        getViewHelperService(form).populateTerm(form);

        if (GlobalVariables.getMessageMap().getErrorCount() > 1){
            return getUIFModelAndView(form);
        }

        String inputCode = form.getInputCode();

        if (StringUtils.isBlank(inputCode)){
            GlobalVariables.getMessageMap().putError("inputCode", CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "Course Offering", inputCode, form.getTermCode());
            form.getCourseOfferingResultList().clear();
            form.setActivityWrapperList(null);
            return getUIFModelAndView(form);
        }

        getViewHelperService(form).loadCourseOfferingsByTermAndCourseCode(form.getTermInfo().getId(), inputCode, form);

        if(!form.getCourseOfferingResultList().isEmpty()) {
            if (form.getCourseOfferingResultList().size() > 1) {
                form.setSubjectCode(form.getCourseOfferingResultList().get(0).getSubjectArea());
                String longNameDescr = getOrgNameDescription(form.getSubjectCode());
                form.setSubjectCodeDescription(longNameDescr);

                // Pull out the org ids from COs and pass in as the adminOrg
                String orgIDs = "";
                for (CourseOfferingListSectionWrapper coWrapper : form.getCourseOfferingResultList()) {
                    CourseOfferingInfo coInfo = getCourseOfferingService().getCourseOffering(coWrapper.getCourseOfferingId(), ContextUtils.createDefaultContextInfo());
                    List<String> orgIds = coInfo.getUnitsDeploymentOrgIds();
                    if(orgIds != null && !orgIds.isEmpty()){
                        for (String orgId : orgIds) {
                            if (orgIDs.indexOf(orgId + ",") == -1) {
                                orgIDs = orgIDs + orgId + ",";
                            }
                        }
                    }
                }
                if (orgIDs.length() > 0) {
                    form.setAdminOrg(orgIDs.substring(0, orgIDs.length()-1));
                }

                ToolbarUtil.processCoToolbarForUser(form.getCourseOfferingResultList(), form);
            } else { // just one course offering is returned
                CourseOfferingListSectionWrapper coListWrapper = form.getCourseOfferingResultList().get(0);
                return prepareManageAOsModelAndView(form, coListWrapper);
            }
        }

        form.setEditAuthz(checkEditViewAuthz(form));

        if (GlobalVariables.getMessageMap().getErrorMessages().isEmpty()){
            return getUIFModelAndView(form, CourseOfferingConstants.MANAGE_CO_PAGE);
        }else{
            return getUIFModelAndView(form, CourseOfferingConstants.SEARCH_PAGE);
        }

    }

    @RequestMapping(value = "/ajaxShow", method = RequestMethod.GET)
    public ModelAndView ajaxShow(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                             @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        theForm.setInputCode(theForm.getInputCode().toUpperCase());
        getViewHelperService(theForm).populateTerm(theForm);

        if (GlobalVariables.getMessageMap().getErrorCount() > 1){
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
//                    CourseOfferingInfo firstCO = getCourseOfferingService().getCourseOffering(theForm.getCourseOfferingResultList().get(0).getCourseOfferingId(), ContextUtils.createDefaultContextInfo());
//                    List<String> orgIds = firstCO.getUnitsDeploymentOrgIds();
//                    if(orgIds !=null && !orgIds.isEmpty()){
//                        theForm.setAdminOrg(orgIds.get(0));
//                    }
                    //venkat
//                    CourseOfferingInfo coToShow = getCourseOfferingService().getCourseOffering(theForm.getCourseOfferingResultList().get(0).getCourseOfferingId(), ContextUtils.createDefaultContextInfo());
//                    theForm.setCourseOfferingCode(coToShow.getCourseOfferingCode());

                    ToolbarUtil.processCoToolbarForUser(theForm.getCourseOfferingResultList(), theForm);
                } else { // just one course offering is returned
                    CourseOfferingListSectionWrapper coListWrapper = theForm.getCourseOfferingResultList().get(0);
                    CourseOfferingInfo coToShow = getCourseOfferingService().getCourseOffering(coListWrapper.getCourseOfferingId(), ContextUtils.createDefaultContextInfo());
                    return prepareManageAOsModelAndView(theForm, coListWrapper);
                }
                ModelAndView mav = new ModelAndView("pages/courseOfferingResult");
                List<CourseOfferingListSectionWrapper> wrapper = theForm.getCourseOfferingResultList();
                String pathInfo = request.getPathInfo();
                String url = request.getRequestURL().substring(0, request.getRequestURL().indexOf(pathInfo));

                mav.addObject("tableData", wrapper);
                mav.addObject("url", url);
                return mav;
            }
            //
            theForm.setEditAuthz(checkEditViewAuthz(theForm));
            if (GlobalVariables.getMessageMap().getErrorMessages().isEmpty()){
                return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_CO_PAGE);
            }else{
                return getUIFModelAndView(theForm, CourseOfferingConstants.SEARCH_PAGE_POC);
            }
        } else {
            LOG.error("Error: Course Code search field can't be empty");
            GlobalVariables.getMessageMap().putError("inputCode", CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "Course Offering", inputCode, theForm.getTermCode());
            theForm.getCourseOfferingResultList().clear();
            theForm.setActivityWrapperList(null);

            return getUIFModelAndView(theForm);
        }

//        response.setContentType("application/json");
//        response.getWriter().write("[{\"Hello\" : \"There\" }]");
//        return null;
    }

    @RequestMapping(value = "/jsonShow", method = RequestMethod.GET)
    public void jsonShow(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                 @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        theForm.setInputCode(theForm.getInputCode().toUpperCase());
        //First, find TermInfo based on termCode
        StringBuilder jsonString = new StringBuilder();
        jsonString = jsonString.append("{ \"tableData\":[");

        getViewHelperService(theForm).populateTerm(theForm);

        if (GlobalVariables.getMessageMap().getErrorCount() > 1){
//            return getUIFModelAndView(theForm);
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
//                    CourseOfferingInfo firstCO = getCourseOfferingService().getCourseOffering(theForm.getCourseOfferingResultList().get(0).getCourseOfferingId(), ContextUtils.createDefaultContextInfo());
//                    List<String> orgIds = firstCO.getUnitsDeploymentOrgIds();
//                    if(orgIds !=null && !orgIds.isEmpty()){
//                        theForm.setAdminOrg(orgIds.get(0));
//                    }
                    //venkat
//                    CourseOfferingInfo coToShow = getCourseOfferingService().getCourseOffering(theForm.getCourseOfferingResultList().get(0).getCourseOfferingId(), ContextUtils.createDefaultContextInfo());
//                    theForm.setCourseOfferingCode(coToShow.getCourseOfferingCode());
                    ToolbarUtil.processCoToolbarForUser(theForm.getCourseOfferingResultList(), theForm);
                } else { // just one course offering is returned
                    CourseOfferingInfo coToShow = getCourseOfferingService().getCourseOffering(theForm.getCourseOfferingResultList().get(0).getCourseOfferingId(), ContextUtils.createDefaultContextInfo());
//                    venkat
//                    theForm.setCourseOfferingCode(coToShow.getCourseOfferingCode());
//                    return prepareManageAOsModelAndView(theForm, theForm.getCourseOfferingResultList().get(0));
//                    response.getWriter().write("[{\"Hello\" : \"There\" }]");
                   // return "[{\"Hello\" : \"There\" }]";
                }
                for(CourseOfferingListSectionWrapper wrapper : theForm.getCourseOfferingResultList()){
                    jsonString = jsonString.append("{\"courseOfferingId\":").append("\"").append(wrapper.getCourseOfferingId()).append("\",").
                        append("\"courseOfferingCode\":").append("\"").append(wrapper.getCourseOfferingCode()).append("\",").
                        append("\"courseOfferingDesc\":").append("\"").append(wrapper.getCourseOfferingDesc()).append("\",").
                        append("\"courseOfferingStateKey\":").append("\"").append(wrapper.getCourseOfferingStateKey()).append("\",").
                        append("\"courseOfferingStateDisplay\":").append("\"").append(wrapper.getCourseOfferingStateDisplay()).append("\",").
                        append("\"courseOfferingCreditOptionKey\":").append("\"").append(wrapper.getCourseOfferingCreditOptionKey()).append("\",").
                        append("\"courseOfferingGradingOptionKey\":").append("\"").append(wrapper.getCourseOfferingGradingOptionKey()).append("\",").
                        append("\"courseOfferingCreditOptionDisplay\":").append("\"").append(wrapper.getCourseOfferingCreditOptionDisplay()).append("\",").
                        append("\"courseOfferingGradingOptionDisplay\":").append("\"").append(wrapper.getCourseOfferingGradingOptionDisplay()).append("\",").
                        append("\"subjectArea\":").append("\"").append(wrapper.getSubjectArea()).append("\",").
                        append("\"isLegalToDelete\":").append("\"").append(wrapper.isLegalToDelete()).append("\",").
                        append("\"isChecked\":").append("\"").append(wrapper.getIsChecked()).append("\"},");
                }
                String jsonStr = jsonString.substring(0, jsonString.lastIndexOf(","));
                jsonStr = jsonStr + "]}";
                response.setContentType("application/json");
                response.getWriter().write(jsonStr);
            }
            //
            theForm.setEditAuthz(checkEditViewAuthz(theForm));
            if (GlobalVariables.getMessageMap().getErrorMessages().isEmpty()){
//                return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_CO_PAGE);
               // return "";
            }else{
//                return getUIFModelAndView(theForm, CourseOfferingConstants.SEARCH_PAGE_POC);
              //  return "";
            }
        } else {
            LOG.error("Error: Course Code search field can't be empty");
            GlobalVariables.getMessageMap().putError("inputCode", CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_NO_COURSE_OFFERING_IS_FOUND, "Course Offering", inputCode, theForm.getTermCode());
            theForm.getCourseOfferingResultList().clear();
            theForm.setActivityWrapperList(null);

//            return getUIFModelAndView(theForm);
            //return "";
        }

//        response.setContentType("application/json");
//        response.getWriter().write("[{\"Hello\" : \"There\" }]");
//        return null;
    }

    @RequestMapping(value = "/dataTableShow", method = RequestMethod.GET)
    public void dataTableShow(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                              @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        StringBuilder aaDataJSONString = new StringBuilder();
        aaDataJSONString = aaDataJSONString.append("{ \"aaData\":[");

        //First, find TermInfo based on termCode
        String termCode = theForm.getTermCode();
        if (StringUtils.isEmpty(termCode)) {
            aaDataJSONString.append("\"]}");
        } else {
            getViewHelperService(theForm).populateTerm(theForm);

            if (theForm.getTermInfo() != null) {
                // Get THE term
                //load all courseofferings based on subject Code
                String inputCode = theForm.getInputCode();
                if (!StringUtils.isEmpty(inputCode)) {
                    getViewHelperService(theForm).loadCourseOfferingsByTermAndCourseCode(theForm.getTermInfo().getId(), inputCode, theForm);
                    if (!theForm.getCourseOfferingResultList().isEmpty()) {
                        if (theForm.getCourseOfferingResultList().size() > 1) {
                            theForm.setSubjectCode(theForm.getCourseOfferingResultList().get(0).getSubjectArea());
                            String longNameDescr = getOrgNameDescription(theForm.getSubjectCode());
                            theForm.setSubjectCodeDescription(longNameDescr);
                            // Pull out the first CO from the result list and then pull out the org ids from this CO
                            // and pass in the first one as the adminOrg
//                            CourseOfferingInfo firstCO = getCourseOfferingService().getCourseOffering(theForm.getCourseOfferingResultList().get(0).getCourseOfferingId(), ContextUtils.createDefaultContextInfo());
//                            List<String> orgIds = firstCO.getUnitsDeploymentOrgIds();
//                            if (orgIds != null && !orgIds.isEmpty()) {
//                                theForm.setAdminOrg(orgIds.get(0));
//                            }
                            CourseOfferingInfo coToShow = getCourseOfferingService().getCourseOffering(theForm.getCourseOfferingResultList().get(0).getCourseOfferingId(), ContextUtils.createDefaultContextInfo());
                            //venkat
//                                theForm.setCourseOfferingCode(coToShow.getCourseOfferingCode());
                            ToolbarUtil.processCoToolbarForUser(theForm.getCourseOfferingResultList(), theForm);
                        } else { // just one course offering is returned
                            CourseOfferingInfo coToShow = getCourseOfferingService().getCourseOffering(theForm.getCourseOfferingResultList().get(0).getCourseOfferingId(), ContextUtils.createDefaultContextInfo());
                            //venkat
//                                theForm.setCourseOfferingCode(coToShow.getCourseOfferingCode());
                        }
                        int rowNumber = 0;
                        for (CourseOfferingListSectionWrapper wrapper : theForm.getCourseOfferingResultList()) {
                            aaDataJSONString = aaDataJSONString.append("[").
                                    append("\"").append("<input type='checkbox' />").append("\",").
                                    append("\"").append(createLink(wrapper.getCourseOfferingId(), wrapper.getCourseOfferingCode(), rowNumber, request )).append("\",").
                                    append("\"").append(wrapper.getCourseOfferingStateDisplay()).append("\",").
                                    append("\"").append(wrapper.getCourseOfferingDesc()).append("\",").
                                    append("\"").append(wrapper.getCourseOfferingCreditOptionDisplay()).append("\",").
                                    append("\"").append(wrapper.getCourseOfferingGradingOptionDisplay()).append("\"],");
                            rowNumber++;
                        }
                    }
                    theForm.setEditAuthz(checkEditViewAuthz(theForm));
                }
            }
        }
        String aaDataJSONStr = aaDataJSONString.toString();
        int index = aaDataJSONString.lastIndexOf(",");
        if (index > -1) {
            aaDataJSONStr = aaDataJSONString.substring(0, index);
        }
        aaDataJSONStr = aaDataJSONStr + "]}";
        response.setContentType("application/json");
        response.getWriter().write(aaDataJSONStr);
    }

    private String createLink(String courseOfferingId, String courseOfferingCode, int rowNumber, HttpServletRequest request){
        if(!StringUtils.isEmpty(courseOfferingId)){
            String pathInfo = request.getPathInfo();
            String url = request.getRequestURL().substring(0, request.getRequestURL().indexOf(pathInfo));
            String anchor = "<a id='code_line" + rowNumber + "'"
                    + "href='" + url + "/inquiry?courseOfferingId=" + courseOfferingId +"&amp;methodToCall=start&amp;dataObjectClassName=org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper&amp;renderedInLightBox=true&amp;showHome=false&amp;showHistory=false&amp;history=" + request.getParameter("history")
                    + "' target='_self' class='uif-link' title='Course Offering =courseOfferingId'>" + courseOfferingCode + "</a>";

            return anchor;
        }
        return "";
    }

    protected ModelAndView prepareManageAOsModelAndView(CourseOfferingManagementForm form, CourseOfferingListSectionWrapper selectedCO) throws  Exception {

        CourseOfferingWrapper currentCO = new CourseOfferingWrapper(selectedCO.isCrossListed(),selectedCO.getCourseOfferingCode(),selectedCO.getCourseOfferingDesc(),selectedCO.getAlternateCOCodes(),selectedCO.getCourseOfferingId(), selectedCO.getOwnerCode(), selectedCO.getOwnerAliases());
        currentCO.setTerm( form.getTermInfo() );
        CourseOfferingInfo coInfo = getCourseOfferingService().getCourseOffering(currentCO.getCourseOfferingId(),ContextUtils.createDefaultContextInfo());

        currentCO.setCourseOfferingInfo(coInfo);
        form.setCurrentCourseOfferingWrapper(currentCO);
        form.setSubjectCode(selectedCO.getSubjectArea());

        //Pull out the org ids and pass in the first one as the adminOrg
        List<String> orgIds = coInfo.getUnitsDeploymentOrgIds();
        if(orgIds !=null && !orgIds.isEmpty()){
            OrgInfo org = getOrganizationService().getOrg(orgIds.get(0),ContextUtils.createDefaultContextInfo());
            currentCO.setCoOwningDeptName(org.getShortName());
            // managing multiple orgs
            String orgIDs = "";
            for (String orgId : orgIds) {
                orgIDs = orgIDs + orgId + ",";
            }
            if (orgIDs.length() > 0) {
                form.setAdminOrg(orgIDs.substring(0, orgIDs.length()-1));
            }
        }

        form.setFormatIdForNewAO(null);
        form.setActivityIdForNewAO(null);
        form.setNoOfActivityOfferings(null);

        getViewHelperService(form).loadActivityOfferingsByCourseOffering(coInfo, form);
        getViewHelperService(form).loadPreviousAndNextCourseOffering(form);

        form.setEditAuthz(checkEditViewAuthz(form));

        //TODO: Set SOC State - temporary display, to be removed after testing is finished
        String socState = getSocState(form.getTermInfo().getId());
        if(StringUtils.isNotBlank(socState))   {
            socState = (socState.substring(0,1)).toUpperCase() + socState.substring(1, socState.length());
        }
        form.setSocState(socState);

        ToolbarUtil.processAoToolbarForUser(form.getActivityWrapperList(), form);
        return getUIFModelAndView(form, CourseOfferingConstants.MANAGE_AO_PAGE);
    }

    @RequestMapping(params = "methodToCall=manageRegGroups")
    public ModelAndView manageRegGroups(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {

        CourseOfferingInfo theCourseOfferingInfo = theForm.getCurrentCourseOfferingWrapper().getCourseOfferingInfo();
        Properties urlParameters = new Properties();
        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.START_METHOD);
        urlParameters.put("coInfo.id", theCourseOfferingInfo.getId());
        urlParameters.put(UifParameters.VIEW_ID, RegistrationGroupConstants.RG_VIEW);
        urlParameters.put(UifParameters.PAGE_ID, RegistrationGroupConstants.RG_PAGE);
        urlParameters.put(UifConstants.UrlParams.SHOW_HOME, BooleanUtils.toStringTrueFalse(false));
//        urlParameters.put(UifConstants.UrlParams.SHOW_HISTORY, BooleanUtils.toStringTrueFalse(false));
        urlParameters.put("withinPortal", BooleanUtils.toStringTrueFalse(theForm.isWithinPortal()));
        String controllerPath = RegistrationGroupConstants.RG_CONTROLLER_PATH;

        //set the redirection param to carry over view information to RG view for customized breadcrumb generation
//        urlParameters.put(CourseOfferingConstants.BREADCRUMB_PREVIOUS_CONTROLLER_PATH_KEY, CourseOfferingConstants.MANAGE_CO_CONTROLLER_PATH);
//        urlParameters.put(CourseOfferingConstants.BREADCRUMB_PREVIOUS_VIEW_ID_KEY, CourseOfferingConstants.MANAGE_CO_VIEW_ID);
//        urlParameters.put(CourseOfferingConstants.BREADCRUMB_PREVIOUS_HOME_URL_KEY, theForm.getFormHistory().getHomewardPath().get(0).getUrl());
//        urlParameters.put(CourseOfferingConstants.BREADCRUMB_PREVIOUS_FORM_HISTORY_KEY, theForm.getFormHistory().getHistoryParameterString());
//        urlParameters.put(CourseOfferingConstants.BREADCRUMB_PREVIOUS_FORMKEY_KEY, theForm.getFormKey());

        return super.performRedirect(theForm,controllerPath, urlParameters);

    }

    /**
     * Loads the previous course offering
     *
     * @param form
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=loadPreviousCO")
    public ModelAndView loadPreviousCO(@ModelAttribute("KualiForm") CourseOfferingManagementForm form) throws Exception {

        CourseOfferingWrapper wrapper = form.getPreviousCourseOfferingWrapper();

        CourseOfferingInfo info = getCourseOfferingService().getCourseOffering(wrapper.getCourseOfferingId(),ContextUtils.createDefaultContextInfo());
        wrapper.setCourseOfferingInfo(info);

        form.getCourseOfferingResultList().clear();
        form.setCurrentCourseOfferingWrapper(wrapper);

        form.setInputCode(info.getCourseOfferingCode());

        getViewHelperService(form).loadActivityOfferingsByCourseOffering(info, form);
        getViewHelperService(form).loadPreviousAndNextCourseOffering(form);
        ToolbarUtil.processAoToolbarForUser(form.getActivityWrapperList(), form);
        return getUIFModelAndView(form);
    }

    /**
     * Loads the next course offering.
     *
     * @param form
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=loadNextCO")
    public ModelAndView loadNextCO(@ModelAttribute("KualiForm") CourseOfferingManagementForm form) throws Exception {

        CourseOfferingWrapper wrapper = form.getNextCourseOfferingWrapper();

        CourseOfferingInfo info = getCourseOfferingService().getCourseOffering(wrapper.getCourseOfferingId(),ContextUtils.createDefaultContextInfo());
        wrapper.setCourseOfferingInfo(info);

        form.getCourseOfferingResultList().clear();
        form.setCurrentCourseOfferingWrapper(wrapper);

        form.setInputCode(info.getCourseOfferingCode());

        getViewHelperService(form).loadActivityOfferingsByCourseOffering(info, form);
        getViewHelperService(form).loadPreviousAndNextCourseOffering(form);
        ToolbarUtil.processAoToolbarForUser(form.getActivityWrapperList(), form);
        return getUIFModelAndView(form);
    }

    /**
     * This is called when the user click on the <i>Manage</i> link at the CO search result.
     * 
     * <b>Usage at</b>: <i>CourseOfferingManagement-ManageCourseOfferingsPage.xml</i>
     * 
     * @param form
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=loadAOs")
    public ModelAndView loadAOs(@ModelAttribute("KualiForm") CourseOfferingManagementForm form) throws Exception {

        Object selectedObject = getSelectedObject(form, "Manage");

        if(selectedObject instanceof CourseOfferingListSectionWrapper){
            CourseOfferingListSectionWrapper coWrapper =  (CourseOfferingListSectionWrapper)selectedObject;
            CourseOfferingInfo courseOffering = getCourseOfferingService().getCourseOffering(coWrapper.getCourseOfferingId(), ContextUtils.createDefaultContextInfo());
            CourseOfferingWrapper currentCOWrapper = new CourseOfferingWrapper(courseOffering);
            form.setInputCode(coWrapper.getCourseOfferingCode());
            form.setCurrentCourseOfferingWrapper(currentCOWrapper);
            return prepareManageAOsModelAndView(form, coWrapper);
        }
        else{
            return getUIFModelAndView(form, CourseOfferingConstants.MANAGE_CO_PAGE);
        }

    }

    @RequestMapping(params = "methodToCall=copyCourseOfferingCreateCopy")
    public ModelAndView copyCourseOfferingCreateCopy(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {

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
        reloadCourseOfferings(theForm);
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

    @RequestMapping(params = "methodToCall=copyCourseOfferingCancel")
    public ModelAndView copyCourseOfferingCancel(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_CO_PAGE);
    }

    @RequestMapping(params = "methodToCall=copyCourseOffering")
    public ModelAndView copyCourseOffering(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {
        Object selectedObject = getSelectedObject(theForm, "Copy"); // Receives edit wrapper, "Copy" for error message.
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
    public ModelAndView selectAllActivityOfferings(@ModelAttribute("KualiForm")CourseOfferingManagementForm theForm) throws Exception {
        List<ActivityOfferingWrapper> list = theForm.getActivityWrapperList();
        for (ActivityOfferingWrapper listElement : list) {
            listElement.setIsChecked(true);
        }
        return getUIFModelAndView(theForm);
    }


    /**
     * This is mapped to the <i>List All</i> link.
     *
     * <b>Usage at:</b> CourseOfferingManagement-ManageActivityOfferingsPage.xml
     *
     * @param form
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "methodToCall=loadCOs")
    public ModelAndView loadCOs(@ModelAttribute("KualiForm") CourseOfferingManagementForm form) throws Exception {
//        CourseOfferingInfo theCourseOffering = theForm.getTheCourseOffering();
//        String subjectCode = theCourseOffering.getSubjectArea();
        //venkat
        String termId = form.getTermInfo().getId();
        form.setInputCode(form.getSubjectCode());
        getViewHelperService(form).loadCourseOfferingsByTermAndSubjectCode(termId, form.getSubjectCode(), form);

//        form.setSubjectCode(subjectCode);
        String longNameDescr = getOrgNameDescription(form.getSubjectCode());
        form.setSubjectCodeDescription(longNameDescr);
        //clean up theCourseOffering value in the form to prevent the
        //side effect of the authorization.
//        form.setTheCourseOffering(null);
        form.setCurrentCourseOfferingWrapper(null);
        ToolbarUtil.processCoToolbarForUser(form.getCourseOfferingResultList(), form);
        return getUIFModelAndView(form, CourseOfferingConstants.MANAGE_CO_PAGE);
    }

    /*
     * Method used to copy activityOffering
     */
    @RequestMapping(params = "methodToCall=copyAO")
    public ModelAndView copyAO( @ModelAttribute("KualiForm") CourseOfferingManagementForm form) {
        ActivityOfferingWrapper selectedAO = (ActivityOfferingWrapper)getSelectedObject(form, "copy");
        try{
            CourseOfferingResourceLoader.loadCourseOfferingService().copyActivityOffering(selectedAO.getAoInfo().getId(), ContextBuilder.loadContextInfo());

            //reload AOs including the new one just created
            reloadActivityOffering(form);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        return getUIFModelAndView(form);
    }

    /*
     * Method used to delete a list of selected Draft activity Offerings
     */
    @RequestMapping(params = "methodToCall=cancelDeleteAOs")
    public ModelAndView cancelDeleteAOs(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {
        reloadActivityOffering(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);
    }

    /*
     * Method used to delete a list of selected Draft activity Offerings
     */
    @RequestMapping(params = "methodToCall=deleteSelectedAoList")
    public ModelAndView deleteSelectedAoList(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {

        List<ActivityOfferingWrapper> selectedAolist = theForm.getSelectedToDeleteList();

        try{
            for(ActivityOfferingWrapper ao : selectedAolist)  {
                getCourseOfferingService().deleteActivityOfferingCascaded(ao.getAoInfo().getId(), ContextBuilder.loadContextInfo());
            }


            // check for changes to states in CO and related FOs
            CourseOfferingViewHelperUtil.updateCourseOfferingStateFromActivityOfferingStateChange(theForm.getCurrentCourseOfferingWrapper().getCourseOfferingInfo(), ContextBuilder.loadContextInfo());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        reloadActivityOffering(theForm);

        if(selectedAolist.size() > 0 && theForm.isSelectedIllegalAOInDeletion())  {
            GlobalVariables.getMessageMap().putWarningForSectionId("manageActivityOfferingsPage",
                    CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_SELECTED_AO_TO_DELETE);
        }

        if(selectedAolist.size() > 1){
            KSUifUtils.addGrowlMessageIcon(GrowlIcon.INFORMATION, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_DELETE_N_SUCCESS );
        }else{
            KSUifUtils.addGrowlMessageIcon(GrowlIcon.INFORMATION, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_DELETE_1_SUCCESS);
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
            Object selectedObject = getSelectedObject(theForm, "deleteCo");
            CourseOfferingListSectionWrapper coWrapper =  (CourseOfferingListSectionWrapper)selectedObject;
            theCourseOffering = getCourseOfferingService().getCourseOffering(coWrapper.getCourseOfferingId(), ContextUtils.createDefaultContextInfo());
            theForm.getCurrentCourseOfferingWrapper().setCourseOfferingInfo(theCourseOffering);
        }

        //venkat - dont think this happen
        /*if (theCourseOffering == null) {
            theCourseOffering = theForm.getTheCourseOffering();
        }*/
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

//        theForm.setCourseOfferingCode(theCourseOffering.getCourseOfferingCode());

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
    @RequestMapping(params = "methodToCall=deleteBulkCos")
    public ModelAndView deleteBulkCos(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {
        List<CourseOfferingListSectionWrapper> coList = theForm.getSelectedCoToDeleteList();
        int checked = 0;
        int enabled = 0;

        for (CourseOfferingListSectionWrapper co : coList) {
            boolean hasDeletion = true;
            if (co.getIsChecked()) {
                checked++;
                if(!co.isColocated()) {
                    // dol not delete colocated COs
                    CourseOfferingResourceLoader.loadCourseOfferingService().deleteCourseOfferingCascaded(co.getCourseOfferingId(), ContextBuilder.loadContextInfo());
                }
            }
        }

        if (checked > enabled) {
            KSUifUtils.addGrowlMessageIcon(GrowlIcon.WARNING, CourseOfferingConstants.COURSEOFFERING_TOOLBAR_DELETE);
        } else {
            if (enabled == 1) {
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.INFORMATION, CourseOfferingConstants.COURSEOFFERING_TOOLBAR_DELETE_1_SUCCESS);
            } else {
                KSUifUtils.addGrowlMessageIcon(GrowlIcon.INFORMATION, CourseOfferingConstants.COURSEOFFERING_TOOLBAR_DELETE_N_SUCCESS);
            }
        }

        reloadCourseOfferings(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_CO_PAGE);
    }

    /*
     * Method used to delete a Course Offering with all Draft activity Offerings
     */
    @RequestMapping(params = "methodToCall=cancelDeleteBulkCos")
    public ModelAndView cancelDeleteBulkCos(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {
        reloadCourseOfferings(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_CO_PAGE);
    }

    /*
    * Method used to invoke the Edit CO screen from Manage Course Offering screen while search input is Course Offering
    * Code (04a screen)
    */
    @RequestMapping(params = "methodToCall=editTheCO")
    public ModelAndView editTheCO(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {

        CourseOfferingInfo theCourseOfferingInfo = theForm.getCurrentCourseOfferingWrapper().getCourseOfferingInfo();
        Properties urlParameters = _buildCOURLParameters(theCourseOfferingInfo,KRADConstants.Maintenance.METHOD_TO_CALL_EDIT);
        String controllerPath = CourseOfferingConstants.CONTROLLER_PATH_COURSEOFFERING_BASE_MAINTENANCE;
        return super.performRedirect(theForm,controllerPath, urlParameters);
    }

    /*
     * Method used to edit a selected CO or AO
     */
    @RequestMapping(params = "methodToCall=edit")
    public ModelAndView edit(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {

        Properties urlParameters = new Properties();
        Object selectedObject = getSelectedObject(theForm, "edit");

        if (selectedObject instanceof CourseOfferingListSectionWrapper) {
            CourseOfferingListSectionWrapper coWrapper =  (CourseOfferingListSectionWrapper)selectedObject;
            CourseOfferingInfo courseOfferingInfo = getCourseOfferingService().getCourseOffering(coWrapper.getCourseOfferingId(), ContextUtils.createDefaultContextInfo());
            urlParameters = _buildCOURLParameters(courseOfferingInfo,KRADConstants.Maintenance.METHOD_TO_CALL_EDIT);
            String controllerPath = CourseOfferingConstants.CONTROLLER_PATH_COURSEOFFERING_BASE_MAINTENANCE;
            return super.performRedirect(theForm,controllerPath, urlParameters);
        } else if(selectedObject instanceof ActivityOfferingWrapper) {

            ActivityOfferingWrapper aoWrapper = (ActivityOfferingWrapper)selectedObject;

            urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.Maintenance.METHOD_TO_CALL_EDIT);
            urlParameters.put(ActivityOfferingConstants.ACTIVITY_OFFERING_WRAPPER_ID, aoWrapper.getAoInfo().getId());
            urlParameters.put(ActivityOfferingConstants.ACTIVITYOFFERING_COURSE_OFFERING_ID, theForm.getCurrentCourseOfferingWrapper().getCourseOfferingInfo().getId());
            urlParameters.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, ActivityOfferingWrapper.class.getName());
            urlParameters.put(UifConstants.UrlParams.SHOW_HOME, BooleanUtils.toStringTrueFalse(false));

//            KSUifUtils.setBreadcrumbRedirectUrlParams(urlParameters, theForm);

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
     *  Determine if any COs were check-boxed.
     *  @return True if any COs where selected. Otherwise, false.
     */
   /* private boolean hasSelectedCourseOfferings(CourseOfferingManagementForm theForm) {
        boolean isSelected = false;
        List<CourseOfferingListSectionWrapper> list = theForm.getCourseOfferingResultList();
        for (CourseOfferingListSectionWrapper coWrapper : list) {
            if (coWrapper.getIsChecked()) {
                isSelected = true;
                break;
            }
        }
        return isSelected;
    }*/

    /*
     * Method used to confirm delete AOs
     */
    @RequestMapping(params = "methodToCall=confirmDelete")
    public ModelAndView confirmDelete(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {
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
    public ModelAndView view(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {

        Properties urlParameters = new Properties();
        String controllerPath = "inquiry";
        Object selectedObject = getSelectedObject(theForm, "view");

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
    public ModelAndView addActivityOfferings(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {

        String activityId = theForm.getActivityIdForNewAO();
        String formatId = theForm.getFormatIdForNewAO();
        int aoCount = Integer.parseInt(theForm.getNoOfActivityOfferings());

        getViewHelperService(theForm).createActivityOfferings(formatId, activityId, aoCount, theForm);

        theForm.setFormatIdForNewAO(null);
        theForm.setActivityIdForNewAO(null);
        theForm.setNoOfActivityOfferings(null);
        //KSUifUtils.addGrowlMessageIcon(GrowlIcon.INFORMATION, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_ADD_SUCCESS);
        return getUIFModelAndView(theForm);

    }

    @RequestMapping(params = "methodToCall=createCourseOffering")
    public ModelAndView createCourseOffering(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {

        String termCode = theForm.getTermCode();

        Properties props = new Properties();
        props.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.START_METHOD);
        props.put("targetTermCode", termCode);
        props.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, "org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCreateWrapper");
        String controllerPath = CourseOfferingConstants.CONTROLLER_PATH_COURSEOFFERING_CREATE_MAINTENANCE;
        return super.performRedirect(theForm, controllerPath, props);
    }

    @RequestMapping(params = "methodToCall=markSubjectCodeReadyForScheduling")
    public ModelAndView markSubjectCodeReadyForScheduling(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {
        CourseOfferingManagementViewHelperServiceImpl helperService = (CourseOfferingManagementViewHelperServiceImpl) theForm.getView().getViewHelperService();
        //  State change all of the AOs associated with all CourseOfferings related to the course code. Passing false so that the isChecked() flag is ignored.
        helperService.markCourseOfferingsForScheduling(theForm.getCourseOfferingResultList(), theForm.getViewId(), theForm.getSocStateKey(), false);
        getViewHelperService(theForm).loadCourseOfferingsByTermAndSubjectCode(theForm.getTermInfo().getId(), theForm.getInputCode(), theForm);
        return getUIFModelAndView(theForm);
    }

    /*
     * Method used to invoke the CO inquiry view from Manage Course Offering screen while search input is Course Offering
     * Code (04a screen)
     */
    @RequestMapping(params = "methodToCall=viewTheCO")
    public ModelAndView viewTheCO(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {

        CourseOfferingInfo theCourseOfferingInfo = theForm.getCurrentCourseOfferingWrapper().getCourseOfferingInfo();
        Properties urlParameters = _buildCOURLParameters(theCourseOfferingInfo,KRADConstants.START_METHOD);
        String controllerPath = KRADConstants.PARAM_MAINTENANCE_VIEW_MODE_INQUIRY;
        return super.performRedirect(theForm,controllerPath, urlParameters);
    }

    private Properties _buildCOURLParameters(CourseOfferingInfo courseOfferingInfo, String methodToCall){
        Properties props = new Properties();
        props.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, methodToCall);
        props.put("courseOfferingInfo.id", courseOfferingInfo.getId());
        props.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, CourseOfferingEditWrapper.class.getName());
        props.put(UifConstants.UrlParams.SHOW_HOME, BooleanUtils.toStringTrueFalse(false));
        return props;
    }

    /*private void assignCrossListedInForm(CourseOfferingManagementForm form, CourseOfferingInfo coToShow){
        form.setAlternateCourseOfferingCodesUI(StringUtils.EMPTY);
        form.setCrossListedCO(false);


        String crossListedCodes = CourseOfferingViewHelperUtil.createTheCrossListedCos(coToShow);
        if(crossListedCodes != null && crossListedCodes.length() > 1) {
            form.setAlternateCourseOfferingCodesUI(crossListedCodes.toString());
            form.setCrossListedCO(true);
        }
    }*/

    private Object getSelectedObject(CourseOfferingManagementForm theForm, String actionLink){
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

    public String getSocState (String termCode) {
        ContextInfo context = new ContextInfo();
        try {
            List<String> socIds = getSocService().getSocIdsByTerm(termCode, context);
            if (socIds != null) {
                if (socIds.isEmpty()) {
                    return null;
                }
                List<SocInfo> targetSocs = this.getSocService().getSocsByIds(socIds, context);
                for (SocInfo soc: targetSocs) {
                    if (soc.getTypeKey().equals(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY)) {
                        return soc.getStateKey().substring(soc.getStateKey().lastIndexOf('.')+1);
                    }
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }

    }

    @RequestMapping(params = "methodToCall=approveCOs")
    public ModelAndView approveCOs(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                        @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        String dialogName = CourseOfferingConstants.ConfirmDialogs.APRROVE_CO;

        if (!hasDialogBeenAnswered(dialogName, theForm)) {
            return showDialog(dialogName, theForm, request, response);
        }

        boolean dialogAnswer = getBooleanDialogResponse(dialogName, theForm, request, response);
        theForm.getDialogManager().resetDialogStatus(dialogName);

        if (dialogAnswer) {
            getViewHelperService(theForm).approveCourseOfferings(theForm);
            reloadCourseOfferings(theForm);
        }

        return getUIFModelAndView(theForm);
    }

    @RequestMapping(params = "methodToCall=suspendCOs")
    public ModelAndView suspendCOs(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                        @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        //TODO:  suspendCOs

        return getUIFModelAndView(theForm);
    }

    @RequestMapping(params = "methodToCall=cancelCOs")
    public ModelAndView cancelCOs(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                        @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        //TODO:  cancelCOs
        return getUIFModelAndView(theForm);
    }

    @RequestMapping(params = "methodToCall=reinstateCOs")
    public ModelAndView reinstateCOs(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                        @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {


        //TODO: reinstateCOs
        return getUIFModelAndView(theForm);
    }

    @RequestMapping(params = "methodToCall=deleteOneCoWithLink")
    public ModelAndView deleteOneCoWithLink(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                  @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        // get the co and set in the form as selected
        CourseOfferingWrapper currentCourseOfferingWrapper  = theForm.getCurrentCourseOfferingWrapper();

        List<CourseOfferingListSectionWrapper> courseOfferingList = theForm.getCourseOfferingResultList();
        for (CourseOfferingListSectionWrapper co : courseOfferingList) {
             if(StringUtils.equals(co.getCourseOfferingId(), currentCourseOfferingWrapper.getCourseOfferingId())) {
                 co.setIsChecked(true);
                 break;
             }
        }

        getViewHelperService(theForm).deleteCourseOfferings(theForm);
        //reloadCourseOfferings(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.CO_DELETE_CONFIRM_PAGE);
    }

    @RequestMapping(params = "methodToCall=deleteCOs")
    public ModelAndView deleteCOs(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                        @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        getViewHelperService(theForm).deleteCourseOfferings(theForm);
        if(theForm.isColocatedCoOnly()) {
            // display not support dialogs
            if(theForm.getNumOfColocatedCosToDelete() > 1) {
                if (!hasDialogBeenAnswered(CourseOfferingConstants.ConfirmDialogs.DELETE_COLO_COS, theForm)) {
                    return showDialog(CourseOfferingConstants.ConfirmDialogs.DELETE_COLO_COS, theForm, request, response);
                }
                theForm.getDialogManager().resetDialogStatus(CourseOfferingConstants.ConfirmDialogs.DELETE_COLO_COS);

            }  else {
                if (!hasDialogBeenAnswered(CourseOfferingConstants.ConfirmDialogs.DELETE_ONE_COLO_CO, theForm)) {
                    return showDialog(CourseOfferingConstants.ConfirmDialogs.DELETE_ONE_COLO_CO, theForm, request, response);
                }
                theForm.getDialogManager().resetDialogStatus(CourseOfferingConstants.ConfirmDialogs.DELETE_ONE_COLO_CO);

            }
        } else {
            return getUIFModelAndView(theForm, CourseOfferingConstants.CO_DELETE_CONFIRM_PAGE);
        }
        return getUIFModelAndView(theForm);
    }

    @RequestMapping(params = "methodToCall=approveAOs")
    public ModelAndView approveAOs(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                        @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        getViewHelperService(theForm).approveActivityOfferings(theForm);
        reloadActivityOffering(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);
    }

    @RequestMapping(params = "methodToCall=suspendAOs")
    public ModelAndView suspendAOs(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                        @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        //TODO suspendAOs
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);
    }

    @RequestMapping(params = "methodToCall=cancelAOs")
    public ModelAndView cancelAOs(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                        @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        //TODO: cancelAOs
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);
    }

    @RequestMapping(params = "methodToCall=reinstateAOs")
    public ModelAndView reinstateAOs(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                        @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        //TODO: reinstateAOs
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);
    }

    @RequestMapping(params = "methodToCall=deleteAOs")
    public ModelAndView deleteAOs(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                        @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        List<ActivityOfferingWrapper> aoList = theForm.getActivityWrapperList();
        List<ActivityOfferingWrapper> selectedIndexList = theForm.getSelectedToDeleteList();
        CourseOfferingWrapper currentCoWrapper = theForm.getCurrentCourseOfferingWrapper();
        currentCoWrapper.setColocatedAoToDelete(false);
        ContextInfo context = ContextUtils.createDefaultContextInfo();

        boolean bNoDeletion = false;
        int checked = 0;
        int enabled = 0;
        int totalAosToDelete = 0;
        int numColocatedAosToDelete = 0;

        selectedIndexList.clear();
        for(ActivityOfferingWrapper ao : aoList) {

            if(ao.isEnableDeleteButton() && ao.getIsChecked()) {
                totalAosToDelete++;
                ao.setActivityCode(ao.getAoInfo().getActivityCode());
                selectedIndexList.add(ao);
                if(ao.getAoInfo().getIsColocated())  {
                    currentCoWrapper.setColocatedAoToDelete(true);
                    String colocateInfo = CourseOfferingViewHelperUtil.createColocatedDisplayData(ao.getAoInfo(), context);
                    ao.setColocatedAoInfo(colocateInfo);
                    numColocatedAosToDelete++;
                }
                enabled++;
            } else if (ao.getIsChecked()){
                checked++;
                if (!bNoDeletion) {
                    bNoDeletion = true;
                }
            }
        }

        if (selectedIndexList.isEmpty()) {
            theForm.setSelectedIllegalAOInDeletion(false);
            if (bNoDeletion) {
                theForm.setSelectedIllegalAOInDeletion(true);
            }
        }

        if(checked > enabled){
            KSUifUtils.addGrowlMessageIcon(GrowlIcon.WARNING, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_DELETE);
        }

        if(totalAosToDelete == numColocatedAosToDelete) {
            if(numColocatedAosToDelete > 1) {
                if (!hasDialogBeenAnswered(CourseOfferingConstants.ConfirmDialogs.DELETE_COLO_AOS, theForm)) {
                    return showDialog(CourseOfferingConstants.ConfirmDialogs.DELETE_COLO_AOS, theForm, request, response);
                }
                theForm.getDialogManager().resetDialogStatus(CourseOfferingConstants.ConfirmDialogs.DELETE_COLO_AOS);

            }  else {
                if (!hasDialogBeenAnswered(CourseOfferingConstants.ConfirmDialogs.DELETE_ONE_COLO_AO, theForm)) {
                    return showDialog(CourseOfferingConstants.ConfirmDialogs.DELETE_ONE_COLO_AO, theForm, request, response);
                }
                theForm.getDialogManager().resetDialogStatus(CourseOfferingConstants.ConfirmDialogs.DELETE_ONE_COLO_AO);
            }
        }  else {
            return getUIFModelAndView(theForm, CourseOfferingConstants.AO_DELETE_CONFIRM_PAGE);
        }
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);
    }

    @RequestMapping(params = "methodToCall=draftAOs")
    public ModelAndView draftAOs(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                        @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        getViewHelperService(theForm).draftActivityOfferings(theForm);
        reloadActivityOffering(theForm);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);
    }

    private void reloadActivityOffering(CourseOfferingManagementForm theForm) throws Exception {
          // Reload the AOs
        CourseOfferingInfo theCourseOffering = theForm.getCurrentCourseOfferingWrapper().getCourseOfferingInfo();
        loadActivityOfferings(theCourseOffering, theForm);
        getViewHelperService(theForm).loadPreviousAndNextCourseOffering(theForm);
    }

    private void loadActivityOfferings(CourseOfferingInfo theCourseOffering, CourseOfferingManagementForm theForm) throws Exception{
        getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(theCourseOffering, theForm);
        ToolbarUtil.processAoToolbarForUser(theForm.getActivityWrapperList(), theForm);
    }

    private void reloadCourseOfferings(CourseOfferingManagementForm theForm)  throws Exception{
        getViewHelperService(theForm).loadCourseOfferingsByTermAndCourseCode(theForm.getTermInfo().getId(), theForm.getInputCode(),theForm);
        //getViewHelperService(theForm).loadCourseOfferingsByTermAndSubjectCode(theForm.getTermInfo().getId(), theForm.getInputCode(),theForm);
        ToolbarUtil.processCoToolbarForUser(theForm.getCourseOfferingResultList(), theForm);
    }
    public CourseOfferingSetService getSocService() {
        // If it hasn't been set by Spring, then look it up by GlobalResourceLoader
        if (socService == null) {
            socService = (CourseOfferingSetService) GlobalResourceLoader.getService(new QName(CourseOfferingSetServiceConstants.NAMESPACE,
                                                                                    CourseOfferingSetServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return socService;
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

    private boolean checkEditViewAuthz(CourseOfferingManagementForm theForm){
        Person user = GlobalVariables.getUserSession().getPerson();
        return theForm.getView().getAuthorizer().canEditView(theForm.getView(),theForm,user);
    }

}