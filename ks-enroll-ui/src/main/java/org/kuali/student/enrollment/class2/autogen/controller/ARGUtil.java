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
import org.kuali.student.enrollment.class2.autogen.form.ARGCourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingClusterWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingListSectionWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.RegistrationGroupWrapper;
import org.kuali.student.enrollment.class2.courseoffering.service.adapter.AutogenRegGroupServiceAdapter;
import org.kuali.student.enrollment.class2.courseoffering.service.util.RegistrationGroupUtil;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.class2.autogen.service.ARGCourseOfferingManagementViewHelperService;
import org.kuali.student.enrollment.class2.autogen.util.ARGToolbarUtil;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingSetInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.enrollment.courseofferingset.service.CourseOfferingSetService;
import org.kuali.student.enrollment.uif.form.KSUifForm;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.constants.AcademicCalendarServiceConstants;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: aliabad4
 * Date: 3/4/13
 * Time: 3:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class ARGUtil {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ARGUtil.class);

    private static ARGCourseOfferingManagementViewHelperService viewHelperService;
    //don't think we can have two viewHelperService here
//    private static RegistrationGroupManagementViewHelperService regViewHelperService;
    private static OrganizationService organizationService;
    private static CourseOfferingSetService socService;
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

    public static CourseOfferingSetService getSocService() {
        // If it hasn't been set by Spring, then look it up by GlobalResourceLoader
        if (socService == null) {
            socService = (CourseOfferingSetService) GlobalResourceLoader.getService(new QName(CourseOfferingSetServiceConstants.NAMESPACE,
                    CourseOfferingSetServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return socService;
    }

    public static AutogenRegGroupServiceAdapter getArgServiceAdapter() {
        if (argServiceAdapter == null) {
            argServiceAdapter = (AutogenRegGroupServiceAdapter) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/autogenRegistrationGroupAppLayer", "AutogenRegGroupServiceAdapter"));
        }
        return argServiceAdapter;
    }

    public static String getSocState(String termCode) {
        ContextInfo context = new ContextInfo();
        try {
            List<String> socIds = getSocService().getSocIdsByTerm(termCode, context);
            if (socIds != null) {
                if (socIds.isEmpty()) {
                    return null;
                }
                List<SocInfo> targetSocs = getSocService().getSocsByIds(socIds, context);
                for (SocInfo soc : targetSocs) {
                    if (soc.getTypeKey().equals(CourseOfferingSetServiceConstants.MAIN_SOC_TYPE_KEY)) {
                        return soc.getStateKey().substring(soc.getStateKey().lastIndexOf('.') + 1);
                    }
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }

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

        /* commented out by bonnie, replace it with a new method

//        getViewHelperService(form).loadActivityOfferingsByCourseOffering(coInfo, form);
        //get cluster info and add to the form
        List<String> foIds = new ArrayList<String>();
        List<ActivityOfferingClusterInfo> clusterInfos = new ArrayList<ActivityOfferingClusterInfo>();
        for(int i=0; i < form.getActivityWrapperList().size(); i++){  //parse through AOs
            String foId = form.getActivityWrapperList().get(i).getFormatOffering().getId();
            String aoId = form.getActivityWrapperList().get(i).getId();
            if (!foIds.contains(foId)) {   //get AOC if not done yet
                foIds.add(foId);
                clusterInfos = getCourseOfferingService().getActivityOfferingClustersByFormatOffering(foId, contextInfo);
            }
            for (int j=0; j < clusterInfos.size(); j++){  //parse through AOs and assign aocs to ao wrapper
                for(ActivityOfferingSetInfo aosInfo : clusterInfos.get(j).getActivityOfferingSets()){
                    if (aosInfo.getActivityOfferingIds().contains(aoId)) {   //add aocWrapper to aoWrapper
                        form.getActivityWrapperList().get(i).setAoClusterName(clusterInfos.get(j).getName());
                        form.getActivityWrapperList().get(i).setAoClusterID(clusterInfos.get(j).getId());
                        //Should you need to load AOCs - uncomment
                        //form.getActivityWrapperList().get(i).setAoCluster(clusterInfos.get(j));
                    }
                }
            }
            form .setFoId2aoTypeMap(foIds);
            form.setAoCount(i+1);
        }
        */

        getViewHelperService(form).build_AOs_RGs_AOCs_Lists_For_TheCourseOffering(form, currentCOWrapper);

        //Performance fix KSENROLL-6102 the foids are now loaded in ARGCourseOfferingManagementViewHelperServiceImpl.build_AOs_RGs_AOCs_Lists_For_TheCourseOffering
        //delete this code when performance work is complete
//        List<FormatOfferingInfo> formatOfferingList = getCourseOfferingService().getFormatOfferingsByCourseOffering(coInfo.getId(),ContextUtils.createDefaultContextInfo());
//        List<String> foIds = new ArrayList<String>();
//        for(FormatOfferingInfo foInfo:formatOfferingList){
//            foIds.add(foInfo.getId());
//        }
//        form.setFoId2aoTypeMap(foIds);
//        form.setAoCount(form.getActivityWrapperList().size());
//        ARGUtil.loadRegistrationGroupsByCourseOffering(foIds, form);
        

        //turn off authz for now -- TODO: do we still need this authz? seems not (by Bonnie)
//        form.setEditAuthz(checkEditViewAuthz(form));

        ARGToolbarUtil.processAoToolbarForUser(form.getActivityWrapperList(), form);
    }

    /**
     * Grab all the registration groups for the list of FO ids passed in and addes wrapped RGs back to the form
     * @param foIds list of Format offering ids
     * @param form The form
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws DoesNotExistException
     * @throws PermissionDeniedException
     * @throws OperationFailedException
     */
    private static void loadRegistrationGroupsByCourseOffering(List<String> foIds, ARGCourseOfferingManagementForm form) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        List<RegistrationGroupWrapper> wrappedRegGroups = new ArrayList<RegistrationGroupWrapper>();

        HashMap<String, ActivityOfferingWrapper> filteredAOsHM = new HashMap<String, ActivityOfferingWrapper>();
        for (ActivityOfferingWrapper wrapper : form.getActivityWrapperList()) {
            filteredAOsHM.put(wrapper.getAoInfo().getId(), wrapper);
        }

        int index = 0;
        //Grab the registration groups from the course
        for(String foId:foIds){
            List<RegistrationGroupInfo> regGroups = getCourseOfferingService().getRegistrationGroupsByFormatOffering(foId, ContextUtils.createDefaultContextInfo());
            //Sort the AOs in the reg group
            _fixAoIdOrderingInRegGroups(regGroups);

            //Wrap the reg groups and put in the form
            for(RegistrationGroupInfo rgInfo:regGroups){
                RegistrationGroupWrapper rgWrapper = new RegistrationGroupWrapper();
                rgWrapper.setRgInfo(rgInfo);
                String aoActivityCodeText = "", aoStateNameText = "", aoTypeNameText = "", aoInstructorText = "", aoMaxEnrText = "", aoEditLinkText = "";
                Integer minEnrollment=null;
                for (String aoID : rgInfo.getActivityOfferingIds()) {
                    ActivityOfferingWrapper aoWrapper = filteredAOsHM.get(aoID);

                    String cssClass = (aoWrapper.getAoInfo().getScheduleId() == null ? "uif-scheduled-dl" : "uif-actual-dl");
                    if (aoWrapper.getAoInfo().getActivityCode() != null && !aoWrapper.getAoInfo().getActivityCode().equalsIgnoreCase("")) {
                        aoActivityCodeText = aoActivityCodeText + aoWrapper.getAoInfo().getActivityCode() + "<br/>";
                    }
                    if (aoWrapper.getStateName() != null && !aoWrapper.getStateName().equalsIgnoreCase("")) {
                        aoStateNameText = aoStateNameText + aoWrapper.getStateName() + "<br/>";
                    }
                    if (aoWrapper.getTypeName() != null && !aoWrapper.getTypeName().equalsIgnoreCase("")) {
                        aoTypeNameText = aoTypeNameText + aoWrapper.getTypeName() + "<br/>";
                    }
                    if (aoWrapper.getFirstInstructorDisplayName() != null && !aoWrapper.getFirstInstructorDisplayName().equalsIgnoreCase("")) {
                        aoInstructorText = aoInstructorText + aoWrapper.getFirstInstructorDisplayName() + "<br/>";
                    }
                    if (aoWrapper.getAoInfo().getMaximumEnrollment() != null) {
                        Integer maximumEnrollment = aoWrapper.getAoInfo().getMaximumEnrollment();
                        aoMaxEnrText = aoMaxEnrText + Integer.toString(maximumEnrollment) + "<br/>";
                        //Set the minimum enrollment as the smalled max enr of each AO
                        if(minEnrollment==null || (maximumEnrollment!=null && maximumEnrollment<minEnrollment)){
                            minEnrollment = maximumEnrollment;
                        }
                    }

                    if(aoWrapper.getStartTimeDisplay() != null){
                        rgWrapper.setStartTimeDisplay(aoWrapper.getStartTimeDisplay(), true, cssClass);
                    }

                    if(aoWrapper.getEndTimeDisplay() != null){
                        rgWrapper.setEndTimeDisplay(aoWrapper.getEndTimeDisplay(), true, cssClass);
                    }

                    if(aoWrapper.getBuildingName() != null){
                        rgWrapper.setBuildingName(aoWrapper.getBuildingName(), true, cssClass);
                    }

                    if(aoWrapper.getRoomName() != null){
                        rgWrapper.setRoomName(aoWrapper.getRoomName(), true, cssClass);
                    }

                    if(aoWrapper.getDaysDisplayName() != null){
                        rgWrapper.setDaysDisplayName(aoWrapper.getDaysDisplayName(), true, cssClass);
                    }

                    //Manually add links that mirror the functionality of KRAD action links. Pass in the aoID as an action param for the controller to use
                    aoEditLinkText += "<a onclick=\"actionInvokeHandler(this);\" class=\"uif-action uif-actionLink uif-navigationActionLink uif-boxLayoutVerticalItem\"" +
                            " tabindex=\"0\"" +
                            " style=\"margin-bottom:0px;\"" +
                            " data-ajaxreturntype=\"update-component\"" +
                            " data-loadingmessage=\"Loading...\"" +
                            " data-disableblocking=\"false\"" +
                            " data-ajaxsubmit=\"false\"" +
                            " data-refreshid=\"KS-CourseOfferingManagement-AllRegistrationGroupsForACourseOffering\"" +
                            " data-validate=\"false\"" +
                            " data-submit-data='{\"methodToCall\":\"edit\"," +
                                               "\"actionParameters\\[selectedCollectionPath\\]\":\"rgResultList\"," +
                                               "\"actionParameters\\[selectedLineIndex\\]\":\""+index+"\"," +
                                               "\"actionParameters\\[aoId\\]\":\""+aoID+"\"," +
                                               "\"showHistory\":\"false\"," +
                                               "\"showHome\":\"false\"," +
                                               "\"jumpToId\":\"KS-CourseOfferingManagement-AllRegistrationGroupsForACourseOffering\"}'" +
                            ">Edit</a>";
                    rgWrapper.setAoClusterName(aoWrapper.getAoClusterName());
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

                rgWrapper.setStateKey(rgInfo.getStateKey(), getStateService().getState(rgInfo.getStateKey(), ContextUtils.createDefaultContextInfo()).getName());
                rgWrapper.setAoEditLink(aoEditLinkText);
                if (minEnrollment!=null) {
                    rgWrapper.setRgMaxEnrText(Integer.toString(minEnrollment));
                }
                rgWrapper.setAoActivityCodeText(aoActivityCodeText);
                rgWrapper.setAoStateNameText(aoStateNameText);
                rgWrapper.setAoTypeNameText(aoTypeNameText);
                rgWrapper.setAoInstructorText(aoInstructorText);
                rgWrapper.setAoMaxEnrText(aoMaxEnrText);
                wrappedRegGroups.add(rgWrapper);
                index++;
            }
        }
        form.setRgResultList(wrappedRegGroups);
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
        //getViewHelperService(theForm).loadCourseOfferingsByTermAndSubjectCode(theForm.getTermInfo().getId(), theForm.getInputCode(),theForm);
        ARGToolbarUtil.processCoToolbarForUser(theForm.getCourseOfferingResultList(), theForm);
    }

    public static void reloadTheCourseOfferingWithAOs_RGs_Clusters(ARGCourseOfferingManagementForm theForm) throws Exception {
        // Reload the AOs
        CourseOfferingInfo theCourseOffering = theForm.getCurrentCourseOfferingWrapper().getCourseOfferingInfo();
//        loadActivityOfferings(theCourseOffering, theForm);

        CourseOfferingWrapper coWrapper = new CourseOfferingWrapper(theCourseOffering);
        getViewHelperService(theForm).build_AOs_RGs_AOCs_Lists_For_TheCourseOffering(theForm, coWrapper);

//        List<FormatOfferingInfo> foList = getCourseOfferingService().getFormatOfferingsByCourseOffering(theCourseOffering.getId(), ContextUtils.createDefaultContextInfo());
//        List<String> foIDs = new ArrayList<String>();
//        for (FormatOfferingInfo fo : foList) {
//            foIDs.add(fo.getId());
//        }
//        loadRegistrationGroupsByCourseOffering(foIDs, theForm);

        getViewHelperService(theForm).loadPreviousAndNextCourseOffering(theForm);

        ARGToolbarUtil.processAoToolbarForUser(theForm.getActivityWrapperList(), theForm);
    }

    public static void loadActivityOfferings(CourseOfferingInfo theCourseOffering, ARGCourseOfferingManagementForm theForm) throws Exception {
        ARGUtil.getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(theCourseOffering, theForm);
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


    public static void _fixAoIdOrderingInRegGroups(List<RegistrationGroupInfo> rgInfos)
            throws InvalidParameterException, MissingParameterException, DoesNotExistException,
            OperationFailedException, PermissionDeniedException {

        for (RegistrationGroupInfo regGroup : rgInfos) {
            Map<String, String> aoIdsToAoTypes =
                    RegistrationGroupUtil.createAoIdsToAoTypesMap(regGroup.getActivityOfferingIds(), getCourseOfferingService(), ContextUtils.createDefaultContextInfo());
            RegistrationGroupUtil.orderActivityOfferingIdsInRegistrationGroup(regGroup, aoIdsToAoTypes);
        }
    }

    public static boolean _isClusterUnique(String formatOfferingId, String privateName) throws Exception{
        //Build up a term search criteria
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.and(
                PredicateFactory.equalIgnoreCase("privateName", privateName),
                PredicateFactory.equal("formatOfferingId", formatOfferingId)));
        QueryByCriteria criteria = qbcBuilder.build();

        List<ActivityOfferingClusterInfo> aoClusterList = getCourseOfferingService().searchForActivityOfferingClusters(criteria, ContextUtils.createDefaultContextInfo());
        return aoClusterList.size() <= 0;
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
        if (foIds!=null && !foIds.isEmpty()) {
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

    //From Bonnie: Do we still need this method?
//    public static ActivityOfferingClusterInfo _buildDefaultAOCluster (String formatOfferingId,
//                                                                      ARGCourseOfferingManagementForm theForm) throws Exception{
//        ActivityOfferingClusterInfo defaultCluster = _buildEmptyAOCluster(formatOfferingId,"Default Cluster", "Default Cluster");
//        defaultCluster = getCourseOfferingService().createActivityOfferingCluster(formatOfferingId,
//                defaultCluster.getTypeKey(), defaultCluster, ContextUtils.createDefaultContextInfo());
//        List<ActivityOfferingWrapper> filteredAOs = theForm.getFilteredUnassignedAOsForSelectedFO();
//        defaultCluster = _updateAOSets(filteredAOs,defaultCluster,formatOfferingId);
//        return defaultCluster;
//    }

    public static ActivityOfferingClusterInfo _updateAOSets(List<ActivityOfferingWrapper> aoWrapperList,
                                                       ActivityOfferingClusterInfo clusterInfo, String formatOfferingId) throws Exception {
        List<ActivityOfferingSetInfo> aoSetInfoList = clusterInfo.getActivityOfferingSets();
        for (ActivityOfferingWrapper aoWrapper:aoWrapperList){
            try {
                TypeInfo typeInfo = getTypeService().getType(aoWrapper.getAoInfo().getTypeKey(), ContextUtils.createDefaultContextInfo());
                aoWrapper.setTypeKey(typeInfo.getKey());
                aoWrapper.setTypeName(typeInfo.getName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            String aoTypeKey = aoWrapper.getTypeKey();
            for(ActivityOfferingSetInfo aoSetInfo: aoSetInfoList){
                if(aoSetInfo.getActivityOfferingType().equals(aoTypeKey)){
                    List<String> aoIds = aoSetInfo.getActivityOfferingIds();
                    aoIds.add(aoWrapper.getAoInfo().getId());
                    aoSetInfo.setActivityOfferingIds(aoIds);
                    break;
                }
            }
            clusterInfo.setActivityOfferingSets(aoSetInfoList);
        }
        clusterInfo = getCourseOfferingService().updateActivityOfferingCluster(formatOfferingId,
                clusterInfo.getId(), clusterInfo, ContextUtils.createDefaultContextInfo());
        return clusterInfo;
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
