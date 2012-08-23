package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCopyWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ExistingCourseOffering;
import org.kuali.student.enrollment.class2.courseoffering.dto.RegistrationGroupWrapper;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.service.CourseOfferingManagementViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseOfferingManagementViewHelperServiceImpl;
import org.kuali.student.enrollment.class2.courseoffering.util.ActivityOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.class2.courseoffering.util.ViewHelperUtil;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r1.common.search.dto.*;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
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
import java.util.*;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

@Controller
@RequestMapping(value = "/courseOfferingManagement")
public class CourseOfferingManagementController extends UifControllerBase  {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(CourseOfferingManagementController.class);
    private CluService luService;
    private transient LRCService lrcService;
    private CourseService courseService;
    private AcademicCalendarService academicCalendarService;

    private CourseOfferingManagementViewHelperService viewHelperService;
    private OrganizationService organizationService;

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new CourseOfferingManagementForm();
    }

    @Override
    @RequestMapping(params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, BindingResult result,
            HttpServletRequest request, HttpServletResponse response) {

        // check view authorization
        // TODO: this needs to be invoked for each request
        if (form.getView() != null) {
            String methodToCall = request.getParameter(KRADConstants.DISPATCH_REQUEST_PARAMETER);
            checkViewAuthorization(form, methodToCall);
        }

        /**
         * When user cancels edit AO/CO, this will be called. Based on the radio button selected, we need to set the page id
         * sothat that page will be displayed. Otherwise, it displays the default (search page) will be displayed.
         */
        String[] methodToCalls = request.getParameterValues(KRADConstants.DISPATCH_REQUEST_PARAMETER);
        for (String methodToCall : methodToCalls) {
            if (StringUtils.equals(methodToCall,KRADConstants.RETURN_METHOD_TO_CALL)){
                if (StringUtils.equals(((CourseOfferingManagementForm)form).getRadioSelection(),CourseOfferingConstants.COURSEOFFERING_COURSE_OFFERING_CODE)){
                    form.setPageId(CourseOfferingConstants.MANAGE_AO_PAGE);
                } else if (StringUtils.equals(((CourseOfferingManagementForm)form).getRadioSelection(),CourseOfferingConstants.COURSEOFFERING_SUBJECT_CODE)){
                    form.setPageId(CourseOfferingConstants.MANAGE_CO_PAGE);
                }
                break;
            }
        }
        return getUIFModelAndView(form);
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
            theForm.setSubjectCodeDescription("");
            getViewHelperService(theForm).loadCourseOfferingsByTermAndSubjectCode(theForm.getTermInfo().getId(), theForm.getInputCode(),theForm);
            if(!theForm.getCourseOfferingEditWrapperList().isEmpty()) {
                theForm.setSubjectCode(theForm.getCourseOfferingEditWrapperList().get(0).getCoInfo().getSubjectArea());
                String longNameDescr = getOrgNameDescription(theForm.getSubjectCode());
                theForm.setSubjectCodeDescription(longNameDescr);
            }

            return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_CO_PAGE);

        } else {
            //load courseOffering based on courseOfferingCode and load all associated activity offerings
            String courseOfferingCode = theForm.getInputCode();
            theForm.setCourseOfferingCode(courseOfferingCode);

            List<CourseOfferingInfo> courseOfferingList = getViewHelperService(theForm).findCourseOfferingsByTermAndCourseOfferingCode(termCode, courseOfferingCode, theForm);

            if (!courseOfferingList.isEmpty() && courseOfferingList.size() == 1 )  {

                CourseOfferingInfo coToShow = courseOfferingList.get(0);

                return prepareManageAOsModelAndView(theForm, coToShow);

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

    private ModelAndView prepareManageAOsModelAndView(CourseOfferingManagementForm theForm, CourseOfferingInfo coToShow) throws Exception {
        CourseOfferingEditWrapper wrapper = new CourseOfferingEditWrapper(coToShow);

        theForm.getCourseOfferingEditWrapperList().clear();
        theForm.getCourseOfferingEditWrapperList().add(wrapper);
        theForm.setTheCourseOffering(coToShow);
        theForm.setFormatIdForNewAO(null);
        theForm.setActivityIdForNewAO(null);
        theForm.setNoOfActivityOfferings(null);
        getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(coToShow, theForm);
        getViewHelperService(theForm).loadPreviousAndNextCourseOffering(theForm, coToShow);

        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);
    }

    @RequestMapping(params = "methodToCall=manageRegGroups")
    public ModelAndView manageRegGroups(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {
        String courseOfferingId = theForm.getTheCourseOffering().getId();
        List<FormatOfferingInfo> formatOfferingList =
                getCourseOfferingService().getFormatOfferingsByCourseOffering(courseOfferingId, getContextInfo());
        theForm.setFormatOfferingName(formatOfferingList.get(0).getName());
        theForm.setFormatOfferingIdForViewRG(formatOfferingList.get(0).getId());
        List<ActivityOfferingWrapper> filteredAOs = getAOsForSelectedFO(formatOfferingList.get(0).getId(), theForm);
        theForm.setFilteredAOsForSelectedFO(filteredAOs);

        // Getting Reg Groups (if any) for the 1st FO
        List<RegistrationGroupInfo> rgInfos = getCourseOfferingService().getRegistrationGroupsByFormatOffering(formatOfferingList.get(0).getId(), getContextInfo());
        List<RegistrationGroupWrapper> filteredRGs = getRGsForSelectedFO(rgInfos, filteredAOs);
        theForm.setFilteredRGsForSelectedFO(filteredRGs);
        if(rgInfos != null && rgInfos.size()>0) {
//            if(theForm.getFormatOfferingIdForViewRG()==null){
                //Default the selected Format Offering if it was not set already

//            }
            getViewHelperService(theForm).validateRegistrationGroupsForFormatOffering(rgInfos, theForm.getFormatOfferingIdForViewRG(), theForm);
        }
        return getUIFModelAndView(theForm, CourseOfferingConstants.REG_GROUP_PAGE);

    }

    @RequestMapping(params = "methodToCall=filterAOsAndRGsPerFO")
    public ModelAndView filterAOsAndRGsPerFO (@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        //first update AOs
        List<ActivityOfferingWrapper> filteredAOs = getAOsForSelectedFO(theForm.getFormatOfferingIdForViewRG(), theForm);
        theForm.setFilteredAOsForSelectedFO(filteredAOs);
        if(!filteredAOs.isEmpty()){
            theForm.setFormatOfferingName(filteredAOs.get(0).getFormatOffering().getName());
        }

        //then update RGs
        List<RegistrationGroupInfo> rgInfos = getCourseOfferingService().getRegistrationGroupsByFormatOffering(theForm.getFormatOfferingIdForViewRG(), getContextInfo());
        List<RegistrationGroupWrapper> filteredRGs = getRGsForSelectedFO(rgInfos, filteredAOs);
        theForm.setFilteredRGsForSelectedFO(filteredRGs);
        if(rgInfos != null && rgInfos.size()>0) {
            getViewHelperService(theForm).validateRegistrationGroupsForFormatOffering(rgInfos, theForm.getFormatOfferingIdForViewRG(), theForm);
        }

        return getUIFModelAndView(theForm, CourseOfferingConstants.REG_GROUP_PAGE);

    }

//    @RequestMapping(params = "methodToCall=filterAOsPerFO")
//    public ModelAndView filterAOsPerFO (@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
//                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
//        List<ActivityOfferingWrapper> filteredAOs = getAOsForSelectedFO(theForm.getFormatOfferingIdForViewRG(), theForm);
//        theForm.setFilteredAOsForSelectedFO(filteredAOs);
//        if(!filteredAOs.isEmpty()){
//            theForm.setFormatOfferingName(filteredAOs.get(0).getFormatOffering().getName());
//        }
//        return getUIFModelAndView(theForm, CourseOfferingConstants.REG_GROUP_PAGE);
//
//    }

//    @RequestMapping(params = "methodToCall=filterRGsPerFO")
//    public ModelAndView filterRGsPerFO (@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
//                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
//        List<ActivityOfferingWrapper> filteredAOs = getAOsForSelectedFO(theForm.getFormatOfferingIdForViewRG(), theForm);
//        List<RegistrationGroupInfo> rgInfos = getCourseOfferingService().getRegistrationGroupsByFormatOffering(theForm.getFormatOfferingIdForViewRG(), getContextInfo());
//        List<RegistrationGroupWrapper> filteredRGs = getRGsForSelectedFO(rgInfos, filteredAOs);
//        theForm.setFilteredRGsForSelectedFO(filteredRGs);
//        if(rgInfos != null && rgInfos.size()>0) {
//            getViewHelperService(theForm).validateRegistrationGroupsForFormatOffering(rgInfos, theForm.getFormatOfferingIdForViewRG(), theForm);
//        }
//
//        return getUIFModelAndView(theForm, CourseOfferingConstants.REG_GROUP_PAGE);
//    }

    @RequestMapping(params = "methodToCall=generateUnconstrainedRegGroups")
    public ModelAndView generateUnconstrainedRegGroups (@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<RegistrationGroupInfo> rgInfos = getCourseOfferingService().generateRegistrationGroupsForFormatOffering(theForm.getFormatOfferingIdForViewRG(), getContextInfo());
        List<ActivityOfferingWrapper> filteredAOs = theForm.getFilteredAOsForSelectedFO();
        List<RegistrationGroupWrapper> filteredRGs = getRGsForSelectedFO(rgInfos, filteredAOs);
        theForm.setFilteredRGsForSelectedFO(filteredRGs);

        return getUIFModelAndView(theForm, CourseOfferingConstants.REG_GROUP_PAGE);
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

            theForm.setCourseOfferingCode(theCourseOffering.getCourseOfferingCode());
            theForm.setInputCode(theCourseOffering.getCourseOfferingCode());
            theForm.setRadioSelection(CourseOfferingConstants.COURSEOFFERING_COURSE_OFFERING_CODE);

            return prepareManageAOsModelAndView(theForm, theCourseOffering);
        }
        else{
            //TODO log error
            return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_CO_PAGE);
        }
    }

    @RequestMapping(params = "methodToCall=copyCourseOfferingCreateCopy")
    public ModelAndView copyCourseOfferingCreateCopy(
            @ModelAttribute("KualiForm") CourseOfferingManagementForm theForm,
            BindingResult result,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

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

        CourseOfferingInfo courseOffering =
            getCourseOfferingService().rolloverCourseOffering(
                courseOfferingInfo.getId(), copyWrapper.getTermId(), optionKeys, getContextInfo());
        ExistingCourseOffering newWrapper = new ExistingCourseOffering(courseOffering);
        CourseInfo course = getCourseInfo(copyWrapper.getCourseOfferingCode());
        newWrapper.setCredits(ViewHelperUtil.getCreditCount(courseOffering, course));
        newWrapper.setGrading(getGradingOption(courseOffering.getGradingOptionId()));
        copyWrapper.getExistingOfferingsInCurrentTerm().add(newWrapper);
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_CO_PAGE);
    }

    private TermInfo getTerm(String termCode){
        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        List<Predicate> pList = new ArrayList<Predicate>();
        Predicate p = null;

        qBuilder.setPredicates();
        if (StringUtils.isNotBlank(termCode)){
            p = equal("atpCode", termCode);
            pList.add(p);
        }

        qBuilder.setPredicates(p);

        try {
            List<TermInfo> terms = getAcademicCalendarService().searchForTerms(qBuilder.build(),getContextInfo());
            if (terms.size() > 1){
                //GlobalVariables.getMessageMap().putError("asf","asdf");//FIXME
                return null;
            }else if (terms.isEmpty()){
                return null;
            }
            return terms.get(0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private CourseInfo getCourseInfo(String courseName) {

        CourseInfo        returnCourseInfo = null;
        String            courseId         = null;
        List<SearchParam> searchParams     = new ArrayList<SearchParam>();
        List <CourseInfo> courseInfoList = new ArrayList<CourseInfo>();

        SearchParam qpv1 = new SearchParam();
        qpv1.setKey("lu.criteria.code");
        qpv1.setValue(courseName);
        searchParams.add(qpv1);

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setParams(searchParams);
        searchRequest.setSearchKey("lu.search.cluByCode");

        try {
            SearchResult searchResult = getCluService().search(searchRequest);
            if (searchResult.getRows().size() > 0) {
                for(SearchResultRow row : searchResult.getRows()){
                    List<SearchResultCell> srCells = row.getCells();
                    if(srCells != null && srCells.size() > 0){
                        for(SearchResultCell cell : srCells){
                            if ("lu.resultColumn.cluId".equals(cell.getKey())) {
                                courseId = cell.getValue();
                                returnCourseInfo = getCourseService().getCourse(courseId, ContextUtils.getContextInfo());
                                courseInfoList.add(returnCourseInfo);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (courseInfoList.size() > 1){
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "Multiple matches found for the course code");
            return null;
        }

        if (courseInfoList.isEmpty()){
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, "No match found for the course code");
            return null;
        }

        return courseInfoList.get(0);

    }

    private String getGradingOption(String gradingOptionId)throws Exception{
        String gradingOption = "";
        if(StringUtils.isNotBlank(gradingOptionId)){
            ResultValuesGroupInfo rvg = getLrcService().getResultValuesGroup(gradingOptionId, getContextInfo());
            if(rvg!= null && StringUtils.isNotBlank(rvg.getName())){
                gradingOption = rvg.getName();
            }
        }

        return gradingOption;
    }

    private List<ActivityOfferingWrapper> getAOsForSelectedFO(String theFOId, CourseOfferingManagementForm theForm) {
        List<ActivityOfferingWrapper> fullAOs = theForm.getActivityWrapperList();
        List<ActivityOfferingWrapper> filterdAOList = theForm.getFilteredAOsForSelectedFO();
        filterdAOList.clear();

        for (ActivityOfferingWrapper ao: fullAOs)  {
            String formatOfferingId =ao.getAoInfo().getFormatOfferingId();
            if (formatOfferingId.equals(theFOId) ){
                filterdAOList.add(ao);
            }
        }
        return filterdAOList;
    }

    private List<RegistrationGroupWrapper> getRGsForSelectedFO(List<RegistrationGroupInfo> rgInfos, List<ActivityOfferingWrapper> filteredAOs) {

        List<RegistrationGroupWrapper> filterdRGList = new ArrayList<RegistrationGroupWrapper>();

        HashMap<String, ActivityOfferingWrapper> filteredAOsHM = new HashMap<String, ActivityOfferingWrapper>();
        for (ActivityOfferingWrapper wrapper : filteredAOs) {
            filteredAOsHM.put(wrapper.getAoInfo().getId(), wrapper);
        }

        for (RegistrationGroupInfo rgInfo : rgInfos) {
           RegistrationGroupWrapper rgWrapper = new RegistrationGroupWrapper();
            rgWrapper.setRgInfo(rgInfo);
            String aoActivityCodeText = "", aoStateNameText = "", aoTypeNameText = "", aoInstructorText = "", aoMaxEnrText = "";
            for (String aoID : rgInfo.getActivityOfferingIds()) {
                if (filteredAOsHM.get(aoID).getAoInfo().getActivityCode() != null && !filteredAOsHM.get(aoID).getAoInfo().getActivityCode().equalsIgnoreCase("")) {
                    aoActivityCodeText = aoActivityCodeText + filteredAOsHM.get(aoID).getAoInfo().getActivityCode() + "<br/>";
                }
                if (filteredAOsHM.get(aoID).getStateName() != null && !filteredAOsHM.get(aoID).getStateName().equalsIgnoreCase("")) {
                    aoStateNameText = aoStateNameText + filteredAOsHM.get(aoID).getStateName() + "<br/>";
                }
                if (filteredAOsHM.get(aoID).getTypeName() != null && !filteredAOsHM.get(aoID).getTypeName().equalsIgnoreCase("")) {
                    aoTypeNameText = aoTypeNameText + filteredAOsHM.get(aoID).getTypeName() + "<br/>";
                }
                if (filteredAOsHM.get(aoID).getFirstInstructorDisplayName() != null && !filteredAOsHM.get(aoID).getFirstInstructorDisplayName().equalsIgnoreCase("")) {
                    aoInstructorText = aoInstructorText + filteredAOsHM.get(aoID).getFirstInstructorDisplayName() + "<br/>";
                }
                if (filteredAOsHM.get(aoID).getAoInfo().getMaximumEnrollment() != null) {
                    aoMaxEnrText = aoMaxEnrText + Integer.toString(filteredAOsHM.get(aoID).getAoInfo().getMaximumEnrollment()) + "<br/>";
                }
            }
            if (aoActivityCodeText.length() > 0) {
                aoActivityCodeText = aoActivityCodeText.substring(0, aoActivityCodeText.lastIndexOf("<br/>"));
            }
            if (aoStateNameText.length() > 0) {
                aoStateNameText = aoStateNameText.substring(0, aoStateNameText.lastIndexOf("<br/>"));
            }
            if (aoTypeNameText.length() > 0) {
                aoTypeNameText = aoTypeNameText.substring(0, aoTypeNameText.lastIndexOf("<br/>"));
            }
            if (aoInstructorText.length() > 0) {
                aoInstructorText = aoInstructorText.substring(0, aoInstructorText.lastIndexOf("<br/>"));
            }
            if (aoMaxEnrText.length() > 0) {
                aoMaxEnrText = aoMaxEnrText.substring(0, aoMaxEnrText.lastIndexOf("<br/>"));
            }

            rgWrapper.setAoActivityCodeText(aoActivityCodeText);
            rgWrapper.setAoStateNameText(aoStateNameText);
            rgWrapper.setAoTypeNameText(aoTypeNameText);
            rgWrapper.setAoInstructorText(aoInstructorText);
            rgWrapper.setAoMaxEnrText(aoMaxEnrText);
            filterdRGList.add(rgWrapper);
        }

        return filterdRGList;
    }

    protected AcademicCalendarService getAcademicCalendarService() {
        if(academicCalendarService == null) {
            academicCalendarService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.academicCalendarService;
    }

    private CluService getCluService() {
        if(luService == null) {
            luService = CourseOfferingResourceLoader.loadCluService();
        }
        return luService;
    }

    protected LRCService getLrcService() {
        if(lrcService == null) {
            lrcService = CourseOfferingResourceLoader.loadLrcService();
        }
        return this.lrcService;
    }

    private CourseService getCourseService() {
        if(courseService == null) {
            courseService = CourseOfferingResourceLoader.loadCourseService();
        }
        return courseService;
    }

    @RequestMapping(params = "methodToCall=copyCourseOfferingCancel")
    public ModelAndView copyCourseOfferingCancel(
            @ModelAttribute("KualiForm") CourseOfferingManagementForm theForm,
            BindingResult result,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_CO_PAGE);
    }

    @RequestMapping(params = "methodToCall=copyCourseOffering")
    public ModelAndView copyCourseOffering(
            @ModelAttribute("KualiForm") CourseOfferingManagementForm theForm,
            BindingResult result,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        Object selectedObject = _getSelectedObject(theForm, "Copy"); // Receives edit wrapper, "Copy" for error message.
        if(selectedObject instanceof CourseOfferingEditWrapper){

            // Get the selected CourseOfferingEditWrapper.
            CourseOfferingEditWrapper courseOfferingEditWrapper = (CourseOfferingEditWrapper)selectedObject;
            CourseOfferingInfo courseOfferingInfo = courseOfferingEditWrapper.getCoInfo();

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
            coCopyWrapper.setGradingOption(courseOfferingInfo.getGradingOption());
            coCopyWrapper.setStudentRegistrationGradingOptionsList(courseOfferingInfo.getStudentRegistrationGradingOptions());
            coCopyWrapper.setFinalExamType(courseOfferingInfo.getFinalExamType());
            coCopyWrapper.setWaitlistLevelTypeKey(courseOfferingInfo.getWaitlistLevelTypeKey());
            coCopyWrapper.setWaitlistTypeKey(courseOfferingInfo.getWaitlistTypeKey());
            coCopyWrapper.setIsHonors(courseOfferingInfo.getIsHonorsOffering());
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
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_CO_PAGE);
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
        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);
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
                CourseOfferingResourceLoader.loadCourseOfferingService().deleteActivityOfferingCascaded(ao.getAoInfo().getId(), ContextBuilder.loadContextInfo());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(theForm.getTheCourseOffering(), theForm);
        if(selectedAolist.size() > 0)  {
            GlobalVariables.getMessageMap().putWarningForSectionId("manageActivityOfferingsPage",
                    CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_SELECTED_AO_TO_DELETE);
        }

        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);

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
                return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_CO_PAGE);
            }
        }

        return getUIFModelAndView(theForm, CourseOfferingConstants.CO_DELETE_CONFIRM_PAGE);
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
                    return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_CO_PAGE);
                }
            }
            CourseOfferingResourceLoader.loadCourseOfferingService().deleteCourseOfferingCascaded(theCourseOffering.getId(), ContextBuilder.loadContextInfo());
            //reload existing COs
            getViewHelperService(theForm).loadCourseOfferingsByTermAndSubjectCode(termId, subjectCode, theForm);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_CO_PAGE);
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

        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_CO_PAGE);
    }

    /**
     * Method used to edit a selected CO or AO
     */
    @RequestMapping(params = "methodToCall=edit")
    public ModelAndView edit(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                             HttpServletRequest request, HttpServletResponse response) throws Exception {

        Properties urlParameters = new Properties();
        Object selectedObject = _getSelectedObject(theForm, "edit");

        if(selectedObject instanceof CourseOfferingEditWrapper){

            CourseOfferingInfo courseOfferingInfo = ((CourseOfferingEditWrapper) selectedObject).getCoInfo();
            urlParameters = _buildCOURLParameters(courseOfferingInfo,KRADConstants.Maintenance.METHOD_TO_CALL_EDIT,false,getContextInfo());

        }else if(selectedObject instanceof ActivityOfferingWrapper) {

            ActivityOfferingWrapper aoWrapper = (ActivityOfferingWrapper)selectedObject;

            urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.Maintenance.METHOD_TO_CALL_EDIT);
            urlParameters.put(ActivityOfferingConstants.ACTIVITY_OFFERING_WRAPPER_ID, aoWrapper.getAoInfo().getId());
            urlParameters.put(ActivityOfferingConstants.ACTIVITYOFFERING_COURSE_OFFERING_ID, theForm.getTheCourseOffering().getId());
            urlParameters.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, ActivityOfferingWrapper.class.getName());
            urlParameters.put(UifConstants.UrlParams.SHOW_HOME, BooleanUtils.toStringTrueFalse(false));

        } else {
            throw new RuntimeException("Invalid type. Does not support for now");
        }

        return super.performRedirect(theForm, KRADConstants.Maintenance.REQUEST_MAPPING_MAINTENANCE, urlParameters);

    }

    /**
     * Method used to pick the selected AO actions
     */
    @RequestMapping(params = "methodToCall=selectedAoActions")
    public ModelAndView selectedAoActions(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                      HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (StringUtils.equals(theForm.getSelectedOfferingAction(), CourseOfferingConstants.ACTIVITY_OFFERING_DELETE_ACTION)){
            return confirmDelete(theForm, result, request, response);
        }

        if (StringUtils.equals(theForm.getSelectedOfferingAction(), CourseOfferingConstants.ACTIVITY_OFFERING_DRAFT_ACTION) ||
            StringUtils.equals(theForm.getSelectedOfferingAction(), CourseOfferingConstants.ACTIVITY_OFFERING_SCHEDULING_ACTION)) {
            getViewHelperService(theForm).changeActivityOfferingsState(theForm.getActivityWrapperList(), theForm.getSelectedOfferingAction());
        }

        // reload the AOs
        CourseOfferingInfo theCourseOffering = theForm.getTheCourseOffering();
        getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(theCourseOffering, theForm);
        getViewHelperService(theForm).loadPreviousAndNextCourseOffering(theForm, theCourseOffering);

        return getUIFModelAndView(theForm, CourseOfferingConstants.MANAGE_AO_PAGE);
    }

    @RequestMapping(params = "methodToCall=selectedCOActions")
    public ModelAndView selectedCOActions(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm, BindingResult result,
                                          HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (StringUtils.equals(theForm.getSelectedOfferingAction(),CourseOfferingConstants.ACTIVITY_OFFERING_SCHEDULING_ACTION)) {
            /*
            if (!hasDialogBeenAnswered("schedulingConfirmDialog", theForm)){
                loadSelectedCOsForScheduling(theForm);
                return showDialog("schedulingConfirmDialog", theForm, request, response);
            }

            String res = getStringDialogResponse("schedulingConfirmDialog", theForm, request, response);
            */
            getViewHelperService(theForm).markCourseOfferingsForScheduling(theForm.getCourseOfferingEditWrapperList());
        }

        return getUIFModelAndView(theForm);

    }

    private void loadSelectedCOsForScheduling(CourseOfferingManagementForm theForm){
        String textToDisplay = StringUtils.EMPTY;
        int count = 1;
        for (CourseOfferingEditWrapper co : theForm.getCourseOfferingEditWrapperList()) {
             if (co.getIsChecked()){
                  textToDisplay = textToDisplay + co.getCoInfo().getCourseOfferingCode() + ",";
                 count++;
             }
        }
        theForm.setToBeScheduledCourseOfferingsUI(StringUtils.stripEnd(textToDisplay,","));
        theForm.setToBeScheduledCourseOfferingsCount(count-1);
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
        boolean bEncounteredNonDraftAOInDeletion = false;
        boolean bLegalAOInDeletion = false;

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
                    bLegalAOInDeletion = true;
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
        }
        /*
                    else {
                        GlobalVariables.getMessageMap().putWarningForSectionId("selectedAoDeleteConfirmationPage",
                                CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_SELECTED_AO_TO_DELETE);
                    }
        */

        return getUIFModelAndView(theForm, CourseOfferingConstants.AO_DELETE_CONFIRM_PAGE);
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
            urlParameters = _buildCOURLParameters((CourseOfferingInfo)selectedObject,KRADConstants.START_METHOD,true,getContextInfo());
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
        props.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.START_METHOD);
        props.put("targetTermCode",termCode);
        props.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, "org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCreateWrapper");

         return super.performRedirect(theForm,"courseOffering", props);
    }

    @RequestMapping(params = "methodToCall=markSubjectCodeReadyForScheduling")
    public ModelAndView markSubjectCodeReadyForScheduling(@ModelAttribute("KualiForm") CourseOfferingManagementForm theForm) throws Exception {
        CourseOfferingManagementViewHelperServiceImpl helperService = (CourseOfferingManagementViewHelperServiceImpl) theForm.getView().getViewHelperService();
        //  State change all of the AOs associated with all CourseOfferings related to the course code. Passing false so that the isChecked() flag is ignored.
        helperService.markCourseOfferingsForScheduling(theForm.getCourseOfferingEditWrapperList(), false);
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
        Properties urlParameters = _buildCOURLParameters(theCourseOfferingInfo,KRADConstants.START_METHOD,false,getContextInfo());
        String controllerPath = KRADConstants.PARAM_MAINTENANCE_VIEW_MODE_INQUIRY;
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
        Properties urlParameters = _buildCOURLParameters(theCourseOfferingInfo,KRADConstants.Maintenance.METHOD_TO_CALL_EDIT,false,getContextInfo());
        String controllerPath = KRADConstants.Maintenance.REQUEST_MAPPING_MAINTENANCE;
        return super.performRedirect(theForm,controllerPath, urlParameters);
    }

    private Properties _buildCOURLParameters(CourseOfferingInfo courseOfferingInfo, String methodToCall, boolean readOnlyView, ContextInfo context){
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
        Object selectedObject = ((List<Object>) collection).get(selectedLineIndex);

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

            java.util.List<OrgInfo> orgInfos = organizationService.searchForOrgs(query, getContextInfo());
            if (!orgInfos.isEmpty()){
                longName = orgInfos.get(0).getLongName();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error! No long name description found.",e); //To change body of catch statement use File | Settings | File Templates.
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

    public CourseOfferingService getCourseOfferingService() {
        return CourseOfferingResourceLoader.loadCourseOfferingService();
    }

    private OrganizationService getOrganizationService(){
        if(organizationService == null) {
            organizationService = (OrganizationService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "organization", "OrganizationService"));

        }
        return organizationService;
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
