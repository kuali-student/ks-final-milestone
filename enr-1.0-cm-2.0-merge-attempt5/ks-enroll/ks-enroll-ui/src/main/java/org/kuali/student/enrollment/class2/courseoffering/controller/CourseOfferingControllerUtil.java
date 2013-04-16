package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingClusterWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.RegistrationGroupWrapper;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.form.RegistrationGroupManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.service.CourseOfferingManagementViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.service.RegistrationGroupManagementViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.service.util.RegistrationGroupUtil;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.class2.courseoffering.util.RegistrationGroupConstants;
import org.kuali.student.enrollment.courseoffering.dto.*;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.permutation.PermutationUtils;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;

import javax.xml.namespace.QName;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: aliabad4
 * Date: 10/29/12
 * Time: 10:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class CourseOfferingControllerUtil {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(CourseOfferingControllerUtil.class);

    private static CourseOfferingManagementViewHelperService comViewHelperService;
    private static RegistrationGroupManagementViewHelperService rgmViewHelperService;
    private static LRCService lrcService;
    private static TypeService typeService;
    private static StateService stateService;
    private static OrganizationService organizationService;

    public static CourseOfferingManagementViewHelperService getViewHelperService(CourseOfferingManagementForm theForm){

        if (comViewHelperService == null) {
            if (theForm.getView().getViewHelperServiceClass() != null){
                comViewHelperService = (CourseOfferingManagementViewHelperService) theForm.getView().getViewHelperService();
            }else{
                comViewHelperService= (CourseOfferingManagementViewHelperService) theForm.getPostedView().getViewHelperService();
            }
        }

        return comViewHelperService;
    }

    public static RegistrationGroupManagementViewHelperService getViewHelperService(RegistrationGroupManagementForm theForm){

        if (rgmViewHelperService == null) {
            if (theForm.getView().getViewHelperServiceClass() != null){
                rgmViewHelperService = (RegistrationGroupManagementViewHelperService) theForm.getView().getViewHelperService();
            }else{
                rgmViewHelperService= (RegistrationGroupManagementViewHelperService) theForm.getPostedView().getViewHelperService();
            }
        }

        return rgmViewHelperService;
    }

    private static OrganizationService getOrganizationService(){
        if(organizationService == null) {
            organizationService = (OrganizationService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "organization", "OrganizationService"));

        }
        return organizationService;
    }

    private static LRCService getLrcService() {
        if(lrcService == null) {
            lrcService = CourseOfferingResourceLoader.loadLrcService();
        }
        return lrcService;
    }

    public static TypeService getTypeService() {
        if(typeService == null) {
            typeService = CourseOfferingResourceLoader.loadTypeService();
        }
        return typeService;
    }

    public static StateService getStateService() {
        if(stateService == null) {
            stateService = CourseOfferingResourceLoader.loadStateService();
        }
        return stateService;
    }

    public static CourseOfferingService getCourseOfferingService() {
        return CourseOfferingResourceLoader.loadCourseOfferingService();
    }

    public static Object _getSelectedObject(CourseOfferingManagementForm theForm, String actionLink){
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

    public static void _prepareManageAOsModelAndView(CourseOfferingManagementForm theForm, CourseOfferingInfo coToShow) throws Exception {
        CourseOfferingEditWrapper wrapper = new CourseOfferingEditWrapper(coToShow);

        theForm.setCourseOfferingEditWrapper(wrapper);
        theForm.setTheCourseOffering(coToShow);
        theForm.setFormatIdForNewAO(null);
        theForm.setActivityIdForNewAO(null);
        theForm.setNoOfActivityOfferings(null);
        getViewHelperService(theForm).loadActivityOfferingsByCourseOffering(coToShow, theForm);
        getViewHelperService(theForm).loadPreviousAndNextCourseOffering(theForm, coToShow);
    }

    public static Object _getSelectedObject(RegistrationGroupManagementForm theForm, String actionLink){
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

    private static void _fixAoIdOrderingInRegGroups(List<RegistrationGroupInfo> rgInfos)
            throws InvalidParameterException, MissingParameterException, DoesNotExistException,
            OperationFailedException, PermissionDeniedException {

        for (RegistrationGroupInfo regGroup: rgInfos) {
            Map<String, String> aoIdsToAoTypes =
                    RegistrationGroupUtil.createAoIdsToAoTypesMap(regGroup.getActivityOfferingIds(), getCourseOfferingService(), ContextUtils.createDefaultContextInfo());
            RegistrationGroupUtil.orderActivityOfferingIdsInRegistrationGroup(regGroup, aoIdsToAoTypes);
        }
    }

    public static List<RegistrationGroupWrapper> _getRGsForSelectedFO(List<RegistrationGroupInfo> rgInfos, List<ActivityOfferingWrapper> filteredAOs) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        _fixAoIdOrderingInRegGroups(rgInfos);
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
                String cssClass = (filteredAOsHM.get(aoID).getAoInfo().getScheduleId() == null ? "uif-scheduled-dl" : "uif-actual-dl");
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

                if(filteredAOsHM.get(aoID).getStartTimeDisplay() != null){
                    rgWrapper.setStartTimeDisplay(filteredAOsHM.get(aoID).getStartTimeDisplay(), true, cssClass);
                }

                if(filteredAOsHM.get(aoID).getEndTimeDisplay() != null){
                    rgWrapper.setEndTimeDisplay(filteredAOsHM.get(aoID).getEndTimeDisplay(), true, cssClass);
                }

                if(filteredAOsHM.get(aoID).getBuildingName() != null){
                    rgWrapper.setBuildingName(filteredAOsHM.get(aoID).getBuildingName(), true, cssClass);
                }

                if(filteredAOsHM.get(aoID).getRoomName() != null){
                    rgWrapper.setRoomName(filteredAOsHM.get(aoID).getRoomName(), true, cssClass);
                }

                if(filteredAOsHM.get(aoID).getDaysDisplayName() != null){
                    rgWrapper.setDaysDisplayName(filteredAOsHM.get(aoID).getDaysDisplayName(), true, cssClass);
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

            try{
                rgWrapper.setStateKey(rgInfo.getStateKey(), getStateService().getState(rgInfo.getStateKey(), ContextUtils.createDefaultContextInfo()).getName());
            }catch (Exception e){
                LOG.info("Error occured to get the StateService" + e.getMessage());
            }
        }

        return filterdRGList;
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

    private static ActivityOfferingClusterInfo _updateAOSets(List<ActivityOfferingWrapper> aoWrapperList,
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

    public static ActivityOfferingClusterInfo _buildDefaultAOCluster (String formatOfferingId,
                                                                RegistrationGroupManagementForm theForm) throws Exception{
        ActivityOfferingClusterInfo defaultCluster = _buildEmptyAOCluster(formatOfferingId,"Default Cluster", "Default Cluster");
        defaultCluster = getCourseOfferingService().createActivityOfferingCluster(formatOfferingId,
                defaultCluster.getTypeKey(), defaultCluster, ContextUtils.createDefaultContextInfo());
        List<ActivityOfferingWrapper> filteredAOs = theForm.getFilteredUnassignedAOsForSelectedFO();
        defaultCluster = _updateAOSets(filteredAOs,defaultCluster,formatOfferingId);
        return defaultCluster;
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

    /*
    * convert List<ActivityOfferingClusterInfo> to List<ActivityOfferingClusterWrapper> and set it to the Form
    */
    public static List<ActivityOfferingClusterWrapper> _convertToAOClusterWrappers(List<ActivityOfferingClusterInfo> aoClusterList,
                                                                             RegistrationGroupManagementForm theForm) throws Exception{
        List<ActivityOfferingClusterWrapper> aoClusterWrapperList = new ArrayList<ActivityOfferingClusterWrapper>();
        int clusterIndex =0;
        for (ActivityOfferingClusterInfo aoCluster: aoClusterList) {
            ActivityOfferingClusterWrapper aoClusterWrapper = _buildAOClusterWrapper (aoCluster, theForm, clusterIndex);
            aoClusterWrapperList.add(aoClusterWrapper);
            clusterIndex++;
        }
        return aoClusterWrapperList;
    }

    public static ActivityOfferingClusterWrapper _buildAOClusterWrapper (ActivityOfferingClusterInfo aoCluster,
                                                                    RegistrationGroupManagementForm theForm, int clusterIndex) throws Exception{

        ActivityOfferingClusterWrapper aoClusterWrapper = new ActivityOfferingClusterWrapper();
        aoClusterWrapper.setActivityOfferingClusterId(aoCluster.getId());
        aoClusterWrapper.setAoCluster(aoCluster);
        aoClusterWrapper.setClusterNameForDisplay("Forget to set cluster?");

        List<ActivityOfferingInfo> aoInfoList = getCourseOfferingService().getActivityOfferingsByCluster(aoCluster.getId(), ContextUtils.createDefaultContextInfo());
        List<ActivityOfferingWrapper> aoWrapperListPerCluster = new ArrayList<ActivityOfferingWrapper>();
        for(ActivityOfferingInfo aoInfo: aoInfoList){
            ActivityOfferingWrapper aoWrapper = getViewHelperService(theForm).convertAOInfoToWrapper(aoInfo);

            String cssClass = (aoInfo.getScheduleId() == null ? "uif-scheduled-dl" : "uif-actual-dl");
            aoWrapper.setDaysDisplayName(aoWrapper.getDaysDisplayName(), false, cssClass);
            aoWrapper.setStartTimeDisplay(aoWrapper.getStartTimeDisplay(), false, cssClass);
            aoWrapper.setEndTimeDisplay(aoWrapper.getEndTimeDisplay(), false, cssClass);
            aoWrapper.setBuildingName(aoWrapper.getBuildingName(), false, cssClass);
            aoWrapper.setRoomName(aoWrapper.getRoomName(), false, cssClass);

            aoWrapperListPerCluster.add(aoWrapper);
        }
        aoClusterWrapper.setAoWrapperList(aoWrapperListPerCluster);

        List<RegistrationGroupInfo> rgInfos =getCourseOfferingService().getRegistrationGroupsByActivityOfferingCluster(aoCluster.getId(), ContextUtils.createDefaultContextInfo());
        List<RegistrationGroupWrapper> rgListPerCluster = new ArrayList<RegistrationGroupWrapper>();
        if (rgInfos.size() > 0 ){
            _validateRegistrationGroupsPerCluster(rgInfos, aoInfoList, aoClusterWrapper, theForm, clusterIndex);
            rgListPerCluster= _getRGsForSelectedFO(rgInfos, aoWrapperListPerCluster);
        } else {
            aoClusterWrapper.setHasAllRegGroups(false);
            aoClusterWrapper.setRgStatus(RegistrationGroupConstants.RGSTATUS_NO_RG_GENERATED);
            aoClusterWrapper.setRgMessageStyle(ActivityOfferingClusterWrapper.RG_MESSAGE_NONE);
        }
        aoClusterWrapper.setRgWrapperList(rgListPerCluster);
        return aoClusterWrapper;
    }

    /*
    * Perform several validations:
    * 1. check if All RGs have been generated given AOs in a cluster
    *    if not, set rgStatus="Only Some Registration Groups Generated" and
    *            set hasAllRegGroups=false, therefore "Generate Registration Group" action link will show up
    *    if yes, set rgStatus="All Registration Groups Generated" and
    *            set hasAllRegGroups=true, therefore "View Registration Group" action link will show up
    * 2. when #1 validation result is yes, need to perform max enrollment validation.
    * 3. when #1 validation result is yes, need to perform time conflict validation
    *
    */
    private static void _validateRegistrationGroupsPerCluster(List<RegistrationGroupInfo> rgInfos, List<ActivityOfferingInfo> aoList,
                                                       ActivityOfferingClusterWrapper aoClusterWrapper, RegistrationGroupManagementForm theForm, int clusterIndex) throws Exception{

        Map<String, List<String>> activityOfferingTypeToAvailableActivityOfferingMap =
                _constructActivityOfferingTypeToAvailableActivityOfferingMap(aoList);

        List<List<String>> generatedPermutations = new ArrayList<List<String>>();
        List<List<String>> foundList = new ArrayList<List<String>>();

        PermutationUtils.generatePermutations(new ArrayList<String>(
                activityOfferingTypeToAvailableActivityOfferingMap.keySet()),
                new ArrayList<String>(),
                activityOfferingTypeToAvailableActivityOfferingMap,
                generatedPermutations);

        List<RegistrationGroupInfo> rgInfosCopy = new ArrayList<RegistrationGroupInfo>(rgInfos.size());
        for(RegistrationGroupInfo rgInfo:rgInfos) {
            rgInfosCopy.add(rgInfo);
        }

        for (List<String> activityOfferingPermutation : generatedPermutations) {
            for (RegistrationGroupInfo rgInfo : rgInfosCopy){
                if (_hasGeneratedRegGroup(activityOfferingPermutation,rgInfo)){
                    rgInfosCopy.remove(rgInfo);
                    foundList.add(activityOfferingPermutation);
                    break;
                }
            }
        }
        if (generatedPermutations.size() != foundList.size() )  {
            aoClusterWrapper.setRgStatus(RegistrationGroupConstants.RGSTATUS_SOME_RG_GENERATED);
            aoClusterWrapper.setRgMessageStyle(ActivityOfferingClusterWrapper.RG_MESSAGE_PARTIAL);
            aoClusterWrapper.setHasAllRegGroups(false);
        }
        else {
            aoClusterWrapper.setRgStatus(RegistrationGroupConstants.RGSTATUS_ALL_RG_GENERATED);
            aoClusterWrapper.setRgMessageStyle(ActivityOfferingClusterWrapper.RG_MESSAGE_ALL);
            aoClusterWrapper.setHasAllRegGroups(true);
            // perform max enrollment validation
            _performMaxEnrollmentValidation(theForm.getFormatOfferingIdForViewRG(), aoClusterWrapper.getAoCluster(), clusterIndex);
            //validate AO time conflict in RG
            _performRGTimeConflictValidation(aoClusterWrapper.getAoCluster(), rgInfos, clusterIndex);

        }
        if (!rgInfosCopy.isEmpty()){
            GlobalVariables.getMessageMap().putWarningForSectionId("registrationGroupsPerFormatSection", CourseOfferingConstants.REGISTRATIONGROUP_INVALID_REGGROUPS);
        }
    }

    public static List<Integer> _performRGTimeConflictValidation(ActivityOfferingClusterInfo aoCluster, List<RegistrationGroupInfo> registrationGroupInfos, int clusterIndex) throws Exception{
        List<Integer> rgIndexList = new ArrayList<Integer>();
        rgIndexList.clear();

        if (aoCluster != null && registrationGroupInfos != null && !registrationGroupInfos.isEmpty()) {
            int rgIndex = 0;
            for (RegistrationGroupInfo registrationGroupInfo : registrationGroupInfos) {
                List<ValidationResultInfo> validationResultInfoList = getCourseOfferingService().verifyRegistrationGroup(registrationGroupInfo.getId(),ContextUtils.createDefaultContextInfo());
                if (validationResultInfoList.get(0).isWarn())  {
                    getCourseOfferingService().updateRegistrationGroupState(registrationGroupInfo.getId(), LuiServiceConstants.REGISTRATION_GROUP_INVALID_STATE_KEY,ContextUtils.createDefaultContextInfo());
                    rgIndexList.add(rgIndex);
                }

                rgIndex++;
            }

            if (!rgIndexList.isEmpty()) {
                GlobalVariables.getMessageMap().putWarningForSectionId("activityOfferingsPerCluster_line"+clusterIndex, RegistrationGroupConstants.MSG_WARNING_AO_TIMECONFLICT, aoCluster.getPrivateName());
                GlobalVariables.getMessageMap().putWarningForSectionId("registrationGroupsPerCluster_line"+clusterIndex, RegistrationGroupConstants.MSG_WARNING_AO_TIMECONFLICT, aoCluster.getPrivateName());
            }
        }

        return rgIndexList;
    }

    private static boolean _hasGeneratedRegGroup(List<String>activityOfferingPermutation, RegistrationGroupInfo rgInfo){
        boolean isMatched = true;
        List<String> aoIds = rgInfo.getActivityOfferingIds();
        List<String> aoIdsCopy = new ArrayList<String>(aoIds.size());
        for (String aoId: aoIds){
            aoIdsCopy.add(aoId);
        }
        List<String> foundList = new ArrayList<String>();
        for (String activityOfferingPermutationItem : activityOfferingPermutation){
            for (String aoId: aoIdsCopy){
                if (activityOfferingPermutationItem.equals(aoId)){
                    aoIdsCopy.remove(aoId);
                    foundList.add(activityOfferingPermutationItem);
                    break;
                }
            }
        }
        if (activityOfferingPermutation.size() != foundList.size() ||!aoIdsCopy.isEmpty()  )  {
            isMatched = false;
        }
        return isMatched;
    }

    public static void _performMaxEnrollmentValidation(String formateOfferingId, ActivityOfferingClusterInfo aoCluster, int clusterIndex) throws Exception{
        List<ValidationResultInfo> validationResultInfoList = getCourseOfferingService().validateActivityOfferingCluster(
                "validation on max enroll totals", formateOfferingId, aoCluster, ContextUtils.createDefaultContextInfo());

        if (validationResultInfoList.get(0).isWarn())  {
            GlobalVariables.getMessageMap().putWarningForSectionId("registrationGroupsPerCluster_line"+clusterIndex, RegistrationGroupConstants.MSG_WARNING_MAX_ENROLLMENT, aoCluster.getPrivateName());
            GlobalVariables.getMessageMap().putWarningForSectionId("activityOfferingsPerCluster_line"+clusterIndex, RegistrationGroupConstants.MSG_WARNING_MAX_ENROLLMENT, aoCluster.getPrivateName());
        }
    }

    private static Map<String, List<String>> _constructActivityOfferingTypeToAvailableActivityOfferingMap(List<ActivityOfferingInfo> aoList) {
        Map<String, List<String>> activityOfferingTypeToAvailableActivityOfferingMap = new HashMap<String, List<String>>();

        for (ActivityOfferingInfo info : aoList) {
            String activityType = info.getTypeKey();
            List<String> activityList = activityOfferingTypeToAvailableActivityOfferingMap
                    .get(activityType);

            if (activityList == null) {
                activityList = new ArrayList<String>();
                activityOfferingTypeToAvailableActivityOfferingMap.put(
                        activityType, activityList);
            }

            activityList.add(info.getId());

        }
        return activityOfferingTypeToAvailableActivityOfferingMap;
    }

    public static List<ActivityOfferingWrapper> getAOsWithoutClusterForSelectedFO(String theFOId, RegistrationGroupManagementForm theForm) throws Exception {
        List<ActivityOfferingWrapper> filterdAOList = theForm.getFilteredUnassignedAOsForSelectedFO();
        filterdAOList.clear();

        //Turn the following code on once the COServiceImpl supports it
        List<ActivityOfferingInfo> aoList = getCourseOfferingService().getActivityOfferingsWithoutClusterByFormatOffering(theFOId,ContextUtils.createDefaultContextInfo());
        for (ActivityOfferingInfo ao: aoList){
            ActivityOfferingWrapper aoWrapper = getViewHelperService(theForm).convertAOInfoToWrapper(ao);
            filterdAOList.add(aoWrapper);

        }
        return filterdAOList;
    }

    public static String getGradingOption(String gradingOptionId)throws Exception{
        String gradingOption = "";
        if(StringUtils.isNotBlank(gradingOptionId)){
            ResultValuesGroupInfo rvg = getLrcService().getResultValuesGroup(gradingOptionId, ContextUtils.createDefaultContextInfo());
            if(rvg!= null && StringUtils.isNotBlank(rvg.getName())){
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
            if (!orgInfos.isEmpty()){
                longName = orgInfos.get(0).getLongName();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error! No long name description found.", e);
        }
        return longName;
    }
}
