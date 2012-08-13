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
import org.kuali.student.r1.common.search.dto.*;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.courseoffering.dto.*;
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
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
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
        return getUIFModelAndView(theForm, "manageCourseOfferingsPage");
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
        return getUIFModelAndView(theForm, "manageCourseOfferingsPage");
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
        return getUIFModelAndView(theForm, "copyCourseOfferingPage");
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

        return super.performRedirect(theForm, controllerPath, urlParameters);

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
