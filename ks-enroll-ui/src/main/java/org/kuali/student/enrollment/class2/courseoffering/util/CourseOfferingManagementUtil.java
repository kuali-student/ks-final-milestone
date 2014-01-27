package org.kuali.student.enrollment.class2.courseoffering.util;

import net.sf.ehcache.CacheManager;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.PersonService;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.rice.krms.api.KrmsConstants;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.student.common.uif.form.KSUifForm;
import org.kuali.student.enrollment.class2.courseoffering.controller.ActivityOfferingControllerTransactionHelper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingClusterWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingListSectionWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.RegistrationGroupWrapper;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.form.CreateSocForm;
import org.kuali.student.enrollment.class2.courseoffering.form.DiagnoseRolloverForm;
import org.kuali.student.enrollment.class2.courseoffering.form.TestServiceCallForm;
import org.kuali.student.enrollment.class2.courseoffering.helper.impl.ActivityOfferingScheduleHelperImpl;
import org.kuali.student.enrollment.class2.courseoffering.refdata.CluFixer;
import org.kuali.student.enrollment.class2.courseoffering.service.CourseOfferingManagementViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.service.CourseOfferingViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.service.CreateSocViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.service.DiagnoseRolloverViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.service.SeatPoolUtilityService;
import org.kuali.student.enrollment.class2.courseoffering.service.TestServiceCallViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.service.facade.CSRServiceFacade;
import org.kuali.student.enrollment.class2.courseoffering.service.facade.CourseOfferingServiceFacade;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.CourseInfoByTermLookupableImpl;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.DefaultOptionKeysService;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.DefaultOptionKeysServiceImpl;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.SeatPoolUtilityServiceImpl;
import org.kuali.student.enrollment.class2.coursewaitlist.service.facade.CourseWaitListServiceFacade;
import org.kuali.student.enrollment.class2.coursewaitlist.service.facade.CourseWaitListServiceFacadeConstants;
import org.kuali.student.enrollment.class2.examoffering.service.facade.ExamOfferingServiceFacade;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingSetInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.enrollment.coursewaitlist.service.CourseWaitListService;
import org.kuali.student.enrollment.examoffering.service.ExamOfferingService;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseRegistrationServiceConstants;
import org.kuali.student.r2.common.util.constants.ExamOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.acal.dto.TermInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.constants.FeeServiceConstants;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.enumerationmanagement.service.EnumerationManagementService;
import org.kuali.student.r2.core.fee.service.FeeService;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.kuali.student.r2.core.room.service.RoomService;
import org.kuali.student.r2.core.scheduling.service.SchedulingService;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.service.SearchService;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;

/**
 * Created with IntelliJ IDEA.
 * User: aliabad4
 * Date: 3/4/13
 * Time: 3:27 PM
 * Utility Class for common auto generated reg group functions
 */
public class CourseOfferingManagementUtil {
    private static CourseOfferingManagementViewHelperService viewHelperService;
    private static CourseOfferingViewHelperService coViewHelperService;
    private static CreateSocViewHelperService socViewHelperService;
    private static DiagnoseRolloverViewHelperService drViewHelperService;
    private static  TestServiceCallViewHelperService testViewHelperService;
    private static OrganizationService organizationService;
    private static StateService stateService;
    private static LRCService lrcService;
    private static TypeService typeService;
    private static AcademicCalendarService academicCalendarService;
    private static CourseOfferingServiceFacade courseOfferingServiceFacade;
    private static CSRServiceFacade csrServiceFacade;
    private static CourseService courseService;
    private static CourseOfferingSetService socService;
    private static AtpService atpService;
    private static CluService cluService;
    private static CourseOfferingSetService courseOfferingSetService;
    private static CourseOfferingService courseOfferingService;
    private static CourseRegistrationService courseRegistrationService;
    private static SearchService searchService;
    private static CourseWaitListServiceFacade courseWaitListServiceFacade;
    private static ExamOfferingServiceFacade examOfferingServiceFacade;
    private static ExamOfferingService examOfferingService;
    private static SchedulingService schedulingService;
    private static PopulationService populationService;
    private static CourseWaitListService courseWaitListService;
    private static DefaultOptionKeysService defaultOptionKeysService;
    private static RoomService roomService;
    private static PermissionService permissionService;
    private static CacheManager cacheManager;
    private static IdentityService identityService;
    private static RuleManagementService ruleManagementService;
    private static LprService lprService;
    private static FeeService feeService;
    private static CluFixer cluFixer;
    private static LuiService luiService = null;
    private static ActivityOfferingControllerTransactionHelper activityOfferingControllerTransactionHelper;
    private static EnumerationManagementService enumerationManagementService;
    private static PersonService personService;

    private static HashMap<String, String> scheduleStateHm = null;

    public static PersonService getPersonService() {
        return KimApiServiceLocator.getPersonService();
    }

    public static EnumerationManagementService getEnumerationManagementService() {
        if(enumerationManagementService == null) {
            enumerationManagementService = (EnumerationManagementService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/enumerationmanagement", "EnumerationManagementService"));
        }
        return enumerationManagementService;
    }

    public static CourseRegistrationService getCourseRegistrationService() {
        if (courseRegistrationService == null) {
            courseRegistrationService = (CourseRegistrationService) GlobalResourceLoader.getService(CourseRegistrationServiceConstants.Q_NAME);
        }
        return courseRegistrationService;
    }

    public static ActivityOfferingControllerTransactionHelper getActivityOfferingControllerTransactionHelper() {
        if(activityOfferingControllerTransactionHelper == null){
            activityOfferingControllerTransactionHelper = GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "activityOfferingControllerTransactionHelper", ActivityOfferingControllerTransactionHelper.class.getSimpleName()));
        }

        return activityOfferingControllerTransactionHelper;
    }

    public static LuiService getLuiService() {
        if (luiService == null) {
            luiService = (LuiService) GlobalResourceLoader.getService(new QName(LuiServiceConstants.NAMESPACE,
                    LuiServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return luiService;
    }

    public static CluFixer getCluFixer() {
        if(cluFixer == null){
            cluFixer = (CluFixer) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/cluFixer","CluFixer"));
        }
        return cluFixer;
    }

    public static FeeService getFeeService() {
        if (feeService == null) {
            feeService = (FeeService) GlobalResourceLoader.getService(new QName(FeeServiceConstants.NAMESPACE,
                    FeeServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return feeService;
    }

    public static LprService getLprService() {
        if (lprService == null) {
            lprService = (LprService) GlobalResourceLoader.getService(new QName(LprServiceConstants.NAMESPACE,
                    LprServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return lprService;
    }

    public static RuleManagementService getRuleManagementService() {
        if (ruleManagementService == null) {
            ruleManagementService = (RuleManagementService) GlobalResourceLoader.getService(new QName(KrmsConstants.Namespaces.KRMS_NAMESPACE_2_0, "ruleManagementService"));
        }
        return ruleManagementService;
    }

    public static IdentityService getIdentityService() {
        if (identityService == null) {
            identityService = KimApiServiceLocator.getIdentityService();
        }
        return identityService;
    }

    public static CacheManager getCacheManager() {
        if(cacheManager == null){
            // "ks-ehcache" is the parent bean in ks-ehcache.xml file. This should probably be a constant.
            cacheManager = CacheManager.getCacheManager("ks-ehcache");
        }
        return cacheManager;
    }

    public static PermissionService getPermissionService() {
        if(permissionService==null){
            permissionService = KimApiServiceLocator.getPermissionService();
        }
        return permissionService;
    }

    public static RoomService getRoomService(){
        if (roomService == null){
            roomService = CourseOfferingResourceLoader.loadRoomService();
        }
        return roomService;
    }

    public static ActivityOfferingScheduleHelperImpl getScheduleHelper(){
        return new ActivityOfferingScheduleHelperImpl();
    }

    public static SeatPoolUtilityService getSeatPoolUtilityService(){
        return new SeatPoolUtilityServiceImpl();
    }

    public static CourseOfferingManagementViewHelperService getViewHelperService(CourseOfferingManagementForm theForm) {
        if (viewHelperService == null) {
            if (theForm.getView().getViewHelperServiceClass() != null) {
                viewHelperService = (CourseOfferingManagementViewHelperService) theForm.getView().getViewHelperService();
            } else {
                viewHelperService = (CourseOfferingManagementViewHelperService) theForm.getPostedView().getViewHelperService();
            }
        }
        return viewHelperService;
    }

    public static CourseOfferingViewHelperService getCoViewHelperService(UifFormBase form) {
        if (coViewHelperService == null) {
            if (form.getView().getViewHelperServiceClass() != null) {
                coViewHelperService = (CourseOfferingViewHelperService) form.getView().getViewHelperService();
            } else {
                coViewHelperService = (CourseOfferingViewHelperService) form.getPostedView().getViewHelperService();
            }
        }
        return coViewHelperService;
    }

    public static CreateSocViewHelperService getSocViewHelperService(CreateSocForm createSocForm) {
        if (socViewHelperService == null) {
            if (createSocForm.getView().getViewHelperServiceClass() != null) {
                socViewHelperService = (CreateSocViewHelperService) createSocForm.getView().getViewHelperService();
            } else {
                socViewHelperService = (CreateSocViewHelperService) createSocForm.getPostedView().getViewHelperService();
            }
        }
        return socViewHelperService;
    }

    public static DiagnoseRolloverViewHelperService getDrViewHelperService(DiagnoseRolloverForm rolloverForm) {
        if (drViewHelperService == null) {
            if (rolloverForm.getView().getViewHelperServiceClass() != null) {
                drViewHelperService = (DiagnoseRolloverViewHelperService) rolloverForm.getView().getViewHelperService();
            } else {
                drViewHelperService = (DiagnoseRolloverViewHelperService) rolloverForm.getPostedView().getViewHelperService();
            }
        }
        return drViewHelperService;
    }

    public static TestServiceCallViewHelperService getTestViewHelperService(TestServiceCallForm serviceCallForm) {
        if (testViewHelperService == null) {
            if (serviceCallForm.getView().getViewHelperServiceClass() != null) {
                testViewHelperService = (TestServiceCallViewHelperService) serviceCallForm.getView().getViewHelperService();
            } else {
                testViewHelperService = (TestServiceCallViewHelperService) serviceCallForm.getPostedView().getViewHelperService();
            }
        }
        return testViewHelperService;
    }

    public static OrganizationService getOrganizationService() {
        if (organizationService == null) {
            organizationService = (OrganizationService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "organization", "OrganizationService"));
        }
        return organizationService;
    }

    public static StateService getStateService() {
        if (stateService == null) {
            stateService = CourseOfferingResourceLoader.loadStateService();
        }
        return stateService;
    }

    public static LRCService getLrcService() {
        if (lrcService == null) {
            lrcService = CourseOfferingResourceLoader.loadLrcService();
        }
        return lrcService;
    }

    public static TypeService getTypeService() {
        if (typeService == null) {
            typeService = CourseOfferingResourceLoader.loadTypeService();
        }
        return typeService;
    }

    public static AcademicCalendarService getAcademicCalendarService() {
        if (academicCalendarService == null) {
            academicCalendarService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return academicCalendarService;
    }

    public static CourseOfferingServiceFacade getCourseOfferingServiceFacade() {
        if (courseOfferingServiceFacade == null) {
            courseOfferingServiceFacade = (CourseOfferingServiceFacade) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/courseOfferingServiceFacade", "CourseOfferingServiceFacade"));
        }
        return courseOfferingServiceFacade;
    }

    public static CSRServiceFacade getCsrServiceFacade() {
        if (csrServiceFacade == null) {
            csrServiceFacade = (CSRServiceFacade) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/csrServiceFacade", "CSRServiceFacade"));
        }
        return csrServiceFacade;
    }

    public static CourseWaitListServiceFacade getCourseWaitListServiceFacade() {
        if (courseWaitListServiceFacade == null) {
            courseWaitListServiceFacade = (CourseWaitListServiceFacade) GlobalResourceLoader.getService(CourseWaitListServiceFacadeConstants.getQName());
        }
        return courseWaitListServiceFacade;
    }

    public static ExamOfferingServiceFacade getExamOfferingServiceFacade() {
        if (examOfferingServiceFacade == null) {
            examOfferingServiceFacade = (ExamOfferingServiceFacade) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/examOfferingServiceFacade", "examOfferingServiceFacade"));
        }
        return examOfferingServiceFacade;
    }

    public static ExamOfferingService getExamOfferingService() {
        if (examOfferingService == null) {
            examOfferingService = (ExamOfferingService) GlobalResourceLoader.getService(new QName(ExamOfferingServiceConstants.NAMESPACE, ExamOfferingServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return examOfferingService;
    }


    public static CourseService getCourseService() {
        if(courseService == null) {
            courseService = CourseOfferingResourceLoader.loadCourseService();
        }
        return courseService;
    }

    public static CourseOfferingSetService getSocService() {
        if (socService == null) {
            socService = CourseOfferingResourceLoader.loadSocService();
        }
        return socService;
    }

    public static AtpService getAtpService() {
        if(atpService == null){
            atpService = CourseOfferingResourceLoader.loadAtpService();
        }
        return atpService;
    }

    public static CluService getCluService() {
        if (cluService == null) {
            cluService = CourseOfferingResourceLoader.loadCluService();
        }
        return cluService;
    }

    public static CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null) {
            courseOfferingService = CourseOfferingResourceLoader.loadCourseOfferingService();
        }
        return courseOfferingService;
    }

    public static CourseOfferingSetService getCourseOfferingSetService(){
        if (courseOfferingSetService == null){
            courseOfferingSetService = (CourseOfferingSetService) GlobalResourceLoader.getService(new QName(CourseOfferingSetServiceConstants.NAMESPACE, CourseOfferingSetServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return courseOfferingSetService;
    }

    public static SearchService getSearchService() {
        if (searchService == null) {
            searchService = (SearchService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "search", SearchService.class.getSimpleName()));
        }
        return searchService;
    }

    public static SchedulingService getSchedulingService() {
        if(schedulingService == null) {
            schedulingService = CourseOfferingResourceLoader.loadSchedulingService();
        }
        return schedulingService;
    }

    public static PopulationService getPopulationService() {
        if(populationService == null) {
            populationService = (PopulationService) GlobalResourceLoader.getService(new QName(PopulationServiceConstants.NAMESPACE, PopulationServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return populationService;
    }

    public static CourseWaitListService getCourseWaitListService() {
        if(courseWaitListService == null) {
            courseWaitListService = CourseOfferingResourceLoader.loadCourseWaitlistService();
        }
        return courseWaitListService;
    }

    public static DefaultOptionKeysService getDefaultOptionKeysService() {
        if (defaultOptionKeysService == null) {
            defaultOptionKeysService = new DefaultOptionKeysServiceImpl();
        }
        return defaultOptionKeysService;
    }

    public static boolean checkEditViewAuthz(CourseOfferingManagementForm theForm) {
        Person user = GlobalVariables.getUserSession().getPerson();
        return theForm.getView().getAuthorizer().canEditView(theForm.getView(), theForm, user);
    }

    public static void prepareManageAOsModelAndView(CourseOfferingManagementForm form, CourseOfferingListSectionWrapper selectedCO) throws Exception {

        CourseOfferingWrapper currentCOWrapper = new CourseOfferingWrapper(selectedCO.isCrossListed(),selectedCO.getCourseOfferingCode(),selectedCO.getCourseOfferingDesc(),selectedCO.getAlternateCOCodes(),selectedCO.getCourseOfferingId());
        form.setSubjectCode(selectedCO.getSubjectArea());
        prepare_AOs_RGs_AOCs_Lists(form, currentCOWrapper);
    }

    public static void prepare_AOs_RGs_AOCs_Lists (CourseOfferingManagementForm form, CourseOfferingWrapper currentCOWrapper) throws Exception {

        //Set exam period id
        try{
            currentCOWrapper.setExamPeriodId(getExamOfferingServiceFacade().getExamPeriodId(form.getTermInfo().getId(), ContextUtils.createDefaultContextInfo()));
        }catch (DoesNotExistException e){
        }

        currentCOWrapper.setTerm( form.getTermInfo() );

        CourseOfferingInfo coInfo = getCourseOfferingService().getCourseOffering(currentCOWrapper.getCourseOfferingId(),ContextUtils.createDefaultContextInfo());
        currentCOWrapper.setCourseOfferingInfo(coInfo);

        //set the ownerCode if not set
        if (currentCOWrapper.getOwnerCode()==null ||currentCOWrapper.getOwnerCode().equals("") ) {
            for (CourseOfferingListSectionWrapper courseOfferingListSectionWrapper : form.getCourseOfferingResultList()) {
               if (courseOfferingListSectionWrapper.getCourseOfferingCode().equals(currentCOWrapper.getCourseOfferingCode())) {
                   currentCOWrapper.setOwnerCode(courseOfferingListSectionWrapper.getOwnerCode());
                   break;
               }
            }
        }

        ContextInfo contextInfo =  ContextUtils.createDefaultContextInfo();
        List<String> orgIds = coInfo.getUnitsDeploymentOrgIds();
        if(orgIds !=null && !orgIds.isEmpty()){
            OrgInfo org = getOrganizationService().getOrg(orgIds.get(0), contextInfo);
            currentCOWrapper.setCoOwningDeptName(org.getShortName());
            // managing multiple orgs
            StringBuilder sb = new StringBuilder("");
            for (String orgId : orgIds) {
                sb.append(orgId).append(",");
            }
            String orgIDs = sb.toString();
            if (orgIDs.length() > 0) {
                form.setAdminOrg(orgIDs.substring(0, orgIDs.length()- 1));
            }
        }

        form.setCurrentCourseOfferingWrapper(currentCOWrapper);
        form.setInputCode(currentCOWrapper.getCourseOfferingCode());

        form.setFormatIdForNewAO(null);
        form.setFormatOfferingIdForNewAO(null);
        form.setActivityIdForNewAO(null);
        form.setNoOfActivityOfferings(null);
        form.setPrivateClusterNamePopover("");
        form.setPublishedClusterNamePopover("");

        getViewHelperService(form).loadExamOfferingRelations(form);

        getViewHelperService(form).loadPreviousAndNextCourseOffering(form);

        getViewHelperService(form).build_AOs_RGs_AOCs_Lists_For_TheCourseOffering(form);

        CourseOfferingManagementToolbarUtil.processAoToolbarForUser(form.getActivityWrapperList(), form);
    }

    /*
     *  Determine if any COs were check-boxed.
     *  @return True if any COs where selected. Otherwise, false.
     */
    public static Object getSelectedObject(KSUifForm theForm, String actionLink) {
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

    public static void reloadCourseOfferings(CourseOfferingManagementForm theForm) throws Exception {
        getViewHelperService(theForm).loadCourseOfferingsByTermAndCourseCode(theForm.getTermInfo(), theForm.getInputCode(), theForm);
        CourseOfferingManagementToolbarUtil.processCoToolbarForUser(theForm.getCourseOfferingResultList(), theForm);
    }

    public static void reloadTheCourseOfferingWithAOs_RGs_Clusters(CourseOfferingManagementForm theForm) throws Exception {
        // Reload the AOs
        CourseOfferingInfo theCourseOffering = theForm.getCurrentCourseOfferingWrapper().getCourseOfferingInfo();

        CourseOfferingWrapper coWrapper = new CourseOfferingWrapper(theCourseOffering);
        getViewHelperService(theForm).build_AOs_RGs_AOCs_Lists_For_TheCourseOffering(theForm);

        getViewHelperService(theForm).loadPreviousAndNextCourseOffering(theForm);

        getViewHelperService(theForm).loadExamOfferingRelations(theForm);

        CourseOfferingManagementToolbarUtil.processAoToolbarForUser(theForm.getActivityWrapperList(), theForm);
    }

    public static String getGradingOption(String gradingOptionId) throws Exception {
        String gradingOption = "";
        if (StringUtils.isNotBlank(gradingOptionId)) {
            ResultValuesGroupInfo rvg = getLrcService().getResultValuesGroup(gradingOptionId, ContextUtils.createDefaultContextInfo());
            if (rvg != null && StringUtils.isNotBlank(rvg.getName())) {
                gradingOption = rvg.getName();
            }
        }

        return gradingOption;
    }

    public static String getOrgNameDescription(String orgShortName) {
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

            OrganizationService organizationService = getOrganizationService();

            java.util.List<OrgInfo> orgInfos = organizationService.searchForOrgs(query, ContextUtils.createDefaultContextInfo());
            if (!orgInfos.isEmpty()) {
                longName = orgInfos.get(0).getLongName();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error! No long name description found.", e);
        }
        return longName;
    }

    public static Properties _buildCOURLParameters(CourseOfferingInfo courseOfferingInfo, String methodToCall) {
        Properties props = new Properties();
        props.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, methodToCall);
        props.put("courseOfferingInfo.id", courseOfferingInfo.getId());
        props.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, CourseOfferingEditWrapper.class.getName());

        // UrlParams.SHOW_HISTORY and SHOW_HOME no longer exist
        // https://fisheye.kuali.org/changelog/rice?cs=39034
        // TODO KSENROLL-8469
        //props.put(UifConstants.UrlParams.SHOW_HOME, BooleanUtils.toStringTrueFalse(false));
        return props;
    }

    public static boolean _isClusterUniqueWithinCO(CourseOfferingManagementForm form, String courseOfferingId, String privateName) throws Exception{
        List<String> foIds = new ArrayList<String>();
        //fetch all the formatOfferingIds associated with the given courseOfferingId
        //For performance, if FOIds are already in the form, use it (most likely it is). Otherwise, fetch FOs by COId
        if (form.getFoId2aoTypeMap()==null || form.getFoId2aoTypeMap().isEmpty()) {
            List<FormatOfferingInfo> formatOfferingList = getCourseOfferingService().getFormatOfferingsByCourseOffering(courseOfferingId,ContextUtils.createDefaultContextInfo());
            for(FormatOfferingInfo foInfo:formatOfferingList){
                foIds.add(foInfo.getId());
            }
        } else {
            foIds = new ArrayList<String>(form.getFoId2aoTypeMap().keySet());
        }

        //Build up a term search criteria
        if (!foIds.isEmpty()) {
            QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
            qbcBuilder.setPredicates(PredicateFactory.and(
                    PredicateFactory.in("formatOfferingId", foIds.toArray()),
                    PredicateFactory.equalIgnoreCase("privateName", privateName)));
            QueryByCriteria criteria = qbcBuilder.build();

            List<ActivityOfferingClusterInfo> aoClusterList = getCourseOfferingService().searchForActivityOfferingClusters(criteria, ContextUtils.createDefaultContextInfo());
            return aoClusterList.size() <= 0;
        }

        return true;
    }


    public static ActivityOfferingClusterInfo _buildEmptyAOCluster (String formatOfferingId, String privateName, String publishedName){
        ActivityOfferingClusterInfo emptyCluster = new ActivityOfferingClusterInfo();
        emptyCluster.setTypeKey(CourseOfferingServiceConstants.AOC_ROOT_TYPE_KEY);
        emptyCluster.setStateKey(CourseOfferingServiceConstants.AOC_ACTIVE_STATE_KEY);
        emptyCluster.setPrivateName(privateName);
        emptyCluster.setName(publishedName);
        emptyCluster.setFormatOfferingId(formatOfferingId);
        List<ActivityOfferingSetInfo> activityOfferingSets = new ArrayList<ActivityOfferingSetInfo>();
        emptyCluster.setActivityOfferingSets(activityOfferingSets);
        return emptyCluster;
    }

    public static boolean _clusterForFormatOfferingValidation (FormatOfferingInfo formatOfferingInfo, ContextInfo context) {
        try {
            if (formatOfferingInfo.getActivityOfferingTypeKeys()!=null && formatOfferingInfo.getActivityOfferingTypeKeys().size()>1) {
                return true;
            } else if (formatOfferingInfo.getActivityOfferingTypeKeys()!=null && formatOfferingInfo.getActivityOfferingTypeKeys().size()==1) {
                QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
                qbcBuilder.setPredicates(PredicateFactory.equal("formatOfferingId", formatOfferingInfo.getId()));
                QueryByCriteria criteria = qbcBuilder.build();

                List<String> aoClusterIds = getCourseOfferingService().searchForActivityOfferingClusterIds(criteria, context);
                if (aoClusterIds!=null && aoClusterIds.size()>=1) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void clearForm (CourseOfferingManagementForm form) throws Exception {
        form.setAdminOrg(null);
        form.setCourseOfferingResultList(new ArrayList<CourseOfferingListSectionWrapper>());
        form.setActivityWrapperList(new ArrayList<ActivityOfferingWrapper>());
        form.setClusterResultList(new ArrayList<ActivityOfferingClusterWrapper>());
        form.setRgResultList(new ArrayList<RegistrationGroupWrapper>());

        form.setSelectedToDeleteList(new ArrayList<ActivityOfferingWrapper>());
        form.setSelectedCoToDeleteList(new ArrayList<CourseOfferingListSectionWrapper>());

        form.setCourseOfferingCopyWrapper(null);
        form.setFormatOfferingIdForNewAO(null);

        form.setHasMoreThanOneFormat(false);
        form.setHasMoreThanOneCluster(false);
        form.setTermInfo(null);
        form.setSubjectCode(null);
        form.setSubjectCodeDescription(null);

        form.setFormatIdForNewAO(null);
        form.setFormatOfferingIdForNewAO(null);
        form.setActivityIdForNewAO(null);
        form.setClusterIdForNewAO(null);
        form.setNoOfActivityOfferings(null);

        form.setCurrentCourseOfferingWrapper(null);
        form.setPreviousCourseOfferingWrapper(null);
        form.setNextCourseOfferingWrapper(null);

        form.setSocState(null);
        form.setSocStateKey(null);

        form.setSelectedIllegalAOInDeletion(false);

        form.setEnableAddButton(false);
        form.setEnableMoveAOButton(false);
        form.setEnableAddClusterButton(false);

    }

    public static String getTermStartEndDate(String termId, TermInfo term) {
        // Return Term as String display like 'FALL 2020 (9/26/2020-12/26/2020)'
        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder, Locale.US);
        String displayString = termId; // use termId as a default.
        if (term != null) {
            String startDate = DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.format(term.getStartDate());
            String endDate = DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.format(term.getEndDate());
            formatter.format("%s - %s", startDate, endDate);
            displayString = stringBuilder.toString();
        }
        return displayString;
    }

    public static String getTermDisplayString(String termId, TermInfo term) {
        // Return Term as String display like 'FALL 2020 (9/26/2020-12/26/2020)'
        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder, Locale.US);
        String displayString = termId; // use termId as a default.
        if (term != null) {
            String startDate = DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.format(term.getStartDate());
            String endDate = DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER.format(term.getEndDate());
            String termType = term.getName();
            formatter.format("%s (%s to %s)", termType, startDate, endDate);
            displayString = stringBuilder.toString();
        }
        return displayString;
    }

    public static CourseOfferingInfo createCourseOfferingInfo(String termId, CourseInfo courseInfo) throws Exception {

        int firstGradingOption = 0;
        CourseOfferingInfo courseOffering = new CourseOfferingInfo();

        courseOffering.setTermId(termId);
        courseOffering.setCourseOfferingTitle(courseInfo.getCourseTitle());
        courseOffering.setCourseId(courseInfo.getId());
        courseOffering.setCourseCode(courseInfo.getCode());
        courseOffering.setTypeKey(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY);
        courseOffering.setStateKey(LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY);
        //need to setup the default value though ks-enroll-config.xml
//        courseOffering.setHasWaitlist(true);
        courseOffering.setWaitlistTypeKey(null);
        courseOffering.setWaitlistLevelTypeKey(null);
        courseOffering.setCourseOfferingCode(courseInfo.getCode());

        //Copy grading and credit options
        if(!courseInfo.getCreditOptions().isEmpty()){
            courseOffering.setCreditOptionId(courseInfo.getCreditOptions().get(firstGradingOption).getKey());
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
            courseOffering.setGradingOptionId(courseGradingOptions.get(firstGradingOption));
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

    private static String convertCourseFinalExamTypeToCourseOfferingFinalExamType(String courseFinalExamType){
        String sRet = null;
        if("STD".equals(courseFinalExamType))   {
            sRet = CourseOfferingConstants.COURSEOFFERING_FINAL_EXAM_TYPE_STANDARD;
        } else if("ALT".equals(courseFinalExamType)) {
            sRet = CourseOfferingConstants.COURSEOFFERING_FINAL_EXAM_TYPE_ALTERNATE;
        } else {
            sRet = CourseOfferingConstants.COURSEOFFERING_FINAL_EXAM_TYPE_NONE;
        }
        return sRet;
    }

    public static TermInfo getTerm(String termCode) {

        int firstTerm = 0;

        QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
        List<Predicate> pList = new ArrayList<Predicate>();
        Predicate p = null;

        qBuilder.setPredicates();
        if (StringUtils.isNotBlank(termCode)) {
            p = equal("atpCode", termCode);
            pList.add(p);
        }
        qBuilder.setPredicates(p);

        try {
            List<TermInfo> terms = getAcademicCalendarService().searchForTerms(qBuilder.build(), ContextUtils.createDefaultContextInfo());
            if (terms.size() > 1) {
                return null;
            } else if (terms.isEmpty()) {
                return null;
            }
            return terms.get(firstTerm);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<CourseInfo> retrieveMatchingCourses(String courseCode, TermInfo term) {

        int firstTerm = 0;
        ContextInfo context = ContextUtils.createDefaultContextInfo();
        CourseInfo returnCourseInfo;
        String courseId;
        List<SearchParamInfo> searchParams = new ArrayList<SearchParamInfo>();
        List<CourseInfo> courseInfoList = new ArrayList<CourseInfo>();

        SearchParamInfo qpv = new SearchParamInfo();
        qpv.setKey("lu.queryParam.luOptionalType");
        qpv.getValues().add("kuali.lu.type.CreditCourse");
        searchParams.add(qpv);

        //Include course states of: Active, Superseded and Retired
        qpv = new SearchParamInfo();
        qpv.setKey("lu.queryParam.luOptionalState");
        qpv.setValues(Arrays.asList(
                DtoConstants.STATE_ACTIVE,
                DtoConstants.STATE_SUPERSEDED,
                DtoConstants.STATE_RETIRED));
        searchParams.add(qpv);

        /**Note:  TermCode lookup criteria field takes the special bus route:  it is used to retrieve
         * term begin/end dates to in-turn constrain courses by their effective and
         * retire dates, respectively.  AND NOTE that termId is not itself used
         * directly in the course lookup query.
         */
        List<AtpInfo> atps;
        try {
            atps = getAtpService().getAtpsByCode(term.getCode(), context);
        } catch (Exception e) {
            return courseInfoList;
        }
        if (atps == null || atps.size() != 1) {
            return courseInfoList;
        }

        qpv = new SearchParamInfo();
        qpv.setKey(CourseInfoByTermLookupableImpl.QueryParamEnum.TERM_START.getQueryKey());
        qpv.getValues().add(DateFormatters.QUERY_SERVICE_TIMESTAMP_FORMATTER.format(atps.get(firstTerm).getStartDate()));
        searchParams.add(qpv);

        qpv = new SearchParamInfo();
        qpv.setKey(CourseInfoByTermLookupableImpl.QueryParamEnum.TERM_END.getQueryKey());
        qpv.getValues().add(DateFormatters.QUERY_SERVICE_TIMESTAMP_FORMATTER.format(atps.get(firstTerm).getEndDate()));
        searchParams.add(qpv);

        //set courseCode to query by
        qpv = new SearchParamInfo();
        qpv.setKey(CourseInfoByTermLookupableImpl.QueryParamEnum.CODE.getQueryKey());
        qpv.getValues().add(courseCode);
        searchParams.add(qpv);

        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setParams(searchParams);
        searchRequest.setSearchKey("lu.search.courseCodes");

        try {
            SearchResultInfo searchResult = getCluService().search(searchRequest, ContextUtils.getContextInfo());
            if (searchResult.getRows().size() > 0) {
                for (SearchResultRowInfo row : searchResult.getRows()) {
                    List<SearchResultCellInfo> srCells = row.getCells();
                    if (srCells != null && srCells.size() > 0) {
                        for (SearchResultCellInfo cell : srCells) {
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

        return courseInfoList;
    }

    public static Properties _buildCOURLParameters(String courseId, String termId, String socId, String methodToCall) {
        Properties props = new Properties();
        props.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, methodToCall);
        props.put(CourseOfferingConstants.COURSE_ID, courseId);
        props.put(CourseOfferingConstants.TARGET_TERM_ID, termId);
        props.put(CourseOfferingConstants.SOC_ID, socId);
        props.put(CourseOfferingConstants.CREATE_COURSEOFFERING, "true");
        props.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, CourseOfferingEditWrapper.class.getName());

        return props;
    }

    public static HashMap getSchedulingStateAndNameHash() throws Exception {
        if (scheduleStateHm == null) {
            scheduleStateHm = new HashMap<String, String>();
            ContextInfo contextInfo = ContextUtils.getContextInfo();

            List<StateInfo> stateInfoList;
            try {
                stateInfoList = getStateService().getStatesByLifecycle(LuiServiceConstants.LUI_AO_SCHEDULING_STATE_LIFECYCLE_KEY, contextInfo);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            for (StateInfo stateInfo : stateInfoList) {
                if (stateInfo != null) {
                    scheduleStateHm.put(stateInfo.getKey(), stateInfo.getName());
                }
            }
        }
        return scheduleStateHm;
    }
}
