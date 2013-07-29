package org.kuali.student.enrollment.class2.autogen.controller;

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
import org.kuali.student.common.uif.form.KSUifForm;
import org.kuali.student.enrollment.class2.autogen.form.ARGCourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.autogen.service.ARGCourseOfferingManagementViewHelperService;
import org.kuali.student.enrollment.class2.autogen.util.ARGToolbarUtil;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingClusterWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingListSectionWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.RegistrationGroupWrapper;
import org.kuali.student.enrollment.class2.courseoffering.service.adapter.AutogenRegGroupServiceAdapter;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingSetInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: aliabad4
 * Date: 3/4/13
 * Time: 3:27 PM
 * Utility Class for common auto generated reg group functions
 */
public class ARGUtil {
    private static ARGCourseOfferingManagementViewHelperService viewHelperService;
    private static OrganizationService organizationService;
    private static StateService stateService;
    private static LRCService lrcService;
    private static TypeService typeService;
    private static AcademicCalendarService academicCalendarService;
    private static AutogenRegGroupServiceAdapter argServiceAdapter;

    public static CourseOfferingService getCourseOfferingService() {
        return CourseOfferingResourceLoader.loadCourseOfferingService();
    }

    public static ARGCourseOfferingManagementViewHelperService getViewHelperService(ARGCourseOfferingManagementForm theForm) {

        if (viewHelperService == null) {
            if (theForm.getView().getViewHelperServiceClass() != null) {
                viewHelperService = (ARGCourseOfferingManagementViewHelperService) theForm.getView().getViewHelperService();
            } else {
                viewHelperService = (ARGCourseOfferingManagementViewHelperService) theForm.getPostedView().getViewHelperService();
            }
        }

        return viewHelperService;
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

    public static AutogenRegGroupServiceAdapter getArgServiceAdapter() {
        if (argServiceAdapter == null) {
            argServiceAdapter = (AutogenRegGroupServiceAdapter) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/autogenRegistrationGroupAppLayer", "AutogenRegGroupServiceAdapter"));
        }
        return argServiceAdapter;
    }

    public static boolean checkEditViewAuthz(ARGCourseOfferingManagementForm theForm) {
        Person user = GlobalVariables.getUserSession().getPerson();
        return theForm.getView().getAuthorizer().canEditView(theForm.getView(), theForm, user);
    }

    public static void prepareManageAOsModelAndView(ARGCourseOfferingManagementForm form, CourseOfferingListSectionWrapper selectedCO) throws Exception {

        CourseOfferingWrapper currentCOWrapper = new CourseOfferingWrapper(selectedCO.isCrossListed(),selectedCO.getCourseOfferingCode(),selectedCO.getCourseOfferingDesc(),selectedCO.getAlternateCOCodes(),selectedCO.getCourseOfferingId());
        form.setSubjectCode(selectedCO.getSubjectArea());
        prepare_AOs_RGs_AOCs_Lists(form, currentCOWrapper);
    }

    public static void prepare_AOs_RGs_AOCs_Lists (ARGCourseOfferingManagementForm form, CourseOfferingWrapper currentCOWrapper) throws Exception {
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
            String orgIDs = "";
            for (String orgId : orgIds) {
                orgIDs = orgIDs + orgId + ",";
            }
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

        getViewHelperService(form).loadPreviousAndNextCourseOffering(form);

        getViewHelperService(form).build_AOs_RGs_AOCs_Lists_For_TheCourseOffering(form, currentCOWrapper);

        ARGToolbarUtil.processAoToolbarForUser(form.getActivityWrapperList(), form);
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

    public static void reloadCourseOfferings(ARGCourseOfferingManagementForm theForm) throws Exception {
        getViewHelperService(theForm).loadCourseOfferingsByTermAndCourseCode(theForm.getTermInfo().getId(), theForm.getInputCode(), theForm);
        ARGToolbarUtil.processCoToolbarForUser(theForm.getCourseOfferingResultList(), theForm);
    }

    public static void reloadTheCourseOfferingWithAOs_RGs_Clusters(ARGCourseOfferingManagementForm theForm) throws Exception {
        // Reload the AOs
        CourseOfferingInfo theCourseOffering = theForm.getCurrentCourseOfferingWrapper().getCourseOfferingInfo();

        CourseOfferingWrapper coWrapper = new CourseOfferingWrapper(theCourseOffering);
        getViewHelperService(theForm).build_AOs_RGs_AOCs_Lists_For_TheCourseOffering(theForm, coWrapper);

        getViewHelperService(theForm).loadPreviousAndNextCourseOffering(theForm);

        ARGToolbarUtil.processAoToolbarForUser(theForm.getActivityWrapperList(), theForm);
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
        props.put(UifConstants.UrlParams.SHOW_HOME, BooleanUtils.toStringTrueFalse(false));
        return props;
    }

    public static boolean _isClusterUniqueWithinCO(ARGCourseOfferingManagementForm form, String courseOfferingId, String privateName) throws Exception{
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

    public static void clearForm (ARGCourseOfferingManagementForm form) throws Exception {
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


}
