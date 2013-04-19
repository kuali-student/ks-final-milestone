/**
 * Copyright 2013 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by David Yin on 3/4/13
 */
package org.kuali.student.enrollment.class2.autogen.controller;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.enrollment.class2.autogen.form.ARGCourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingClusterWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingListSectionWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.service.adapter.ActivityOfferingResult;
import org.kuali.student.enrollment.class2.courseoffering.util.ActivityOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingViewHelperUtil;
import org.kuali.student.enrollment.class2.courseoffering.util.FormatOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.RegistrationGroupConstants;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.uif.util.GrowlIcon;
import org.kuali.student.enrollment.uif.util.KSUifUtils;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class ARGActivityOfferingClusterHandler {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ARGActivityOfferingClusterHandler.class);
    private static boolean createAOCFromMove = false;

    public static boolean loadAOs_RGs_AOCs(ARGCourseOfferingManagementForm form) throws Exception {

        Object selectedObject = ARGUtil.getSelectedObject(form, "Manage");

        if (selectedObject instanceof CourseOfferingListSectionWrapper) {
            CourseOfferingListSectionWrapper coWrapper = (CourseOfferingListSectionWrapper) selectedObject;
            CourseOfferingInfo courseOffering = ARGUtil.getCourseOfferingService().getCourseOffering(coWrapper.getCourseOfferingId(), ContextUtils.createDefaultContextInfo());
            CourseOfferingWrapper currentCOWrapper = new CourseOfferingWrapper(courseOffering);
            form.setInputCode(coWrapper.getCourseOfferingCode());
            form.setCurrentCourseOfferingWrapper(currentCOWrapper);

            ARGUtil.prepareManageAOsModelAndView(form, coWrapper);
            return true;
        } else {
            return false;
        }
    }

    public static void selectAllActivityOfferings(ARGCourseOfferingManagementForm theForm) throws Exception {
        List<ActivityOfferingWrapper> list = theForm.getActivityWrapperList();
        for (ActivityOfferingWrapper listElement : list) {
            listElement.setIsChecked(true);
        }
    }

    public static void copyAO(ARGCourseOfferingManagementForm form) {
        ActivityOfferingWrapper selectedAO = (ActivityOfferingWrapper) ARGUtil.getSelectedObject(form, "copy");
        try {
            String aoIdToCopy = selectedAO.getAoInfo().getId(); // Create a copy of this AO
            String clusterId = selectedAO.getAoCluster().getId(); // Use this AO cluster
            ActivityOfferingResult aoResult =
                    ARGUtil.getArgServiceAdapter().copyActivityOfferingToCluster(aoIdToCopy, clusterId, ContextBuilder.loadContextInfo());

            // reload AOs including the new one just created
            ARGUtil.reloadTheCourseOfferingWithAOs_RGs_Clusters(form);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteSelectedAoList(ARGCourseOfferingManagementForm theForm) throws Exception {

        List<ActivityOfferingWrapper> selectedAolist = theForm.getSelectedToDeleteList();

        try {
            for (ActivityOfferingWrapper ao : selectedAolist) {
                // The adapter does not technically need an AOC ID, so I'm setting it to null
                ARGUtil.getArgServiceAdapter().deleteActivityOfferingCascaded(ao.getAoInfo().getId(), null, ContextBuilder.loadContextInfo());
            }

            // check for changes to states in CO and related FOs
            CourseOfferingViewHelperUtil.updateCourseOfferingStateFromActivityOfferingStateChange(theForm.getCurrentCourseOfferingWrapper().getCourseOfferingInfo(), ContextBuilder.loadContextInfo());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ARGUtil.reloadTheCourseOfferingWithAOs_RGs_Clusters(theForm);

        if (selectedAolist.size() > 0 && theForm.isSelectedIllegalAOInDeletion()) {
            GlobalVariables.getMessageMap().putWarningForSectionId("manageActivityOfferingsPage",
                    CourseOfferingConstants.COURSEOFFERING_MSG_ERROR_SELECTED_AO_TO_DELETE);
        }

        if (selectedAolist.size() > 1) {
            KSUifUtils.addGrowlMessageIcon(GrowlIcon.INFORMATION, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_DELETE_N_SUCCESS);
        } else {
            KSUifUtils.addGrowlMessageIcon(GrowlIcon.INFORMATION, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_DELETE_1_SUCCESS);
        }
    }

    public static Properties editAO(ARGCourseOfferingManagementForm theForm, String aoId) throws Exception {
        Properties urlParameters = new Properties();
        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.Maintenance.METHOD_TO_CALL_EDIT);
        urlParameters.put(ActivityOfferingConstants.ACTIVITY_OFFERING_WRAPPER_ID, aoId);
        urlParameters.put(ActivityOfferingConstants.ACTIVITYOFFERING_COURSE_OFFERING_ID, theForm.getCurrentCourseOfferingWrapper().getCourseOfferingInfo().getId());
        urlParameters.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, ActivityOfferingWrapper.class.getName());
        urlParameters.put(UifConstants.UrlParams.SHOW_HOME, BooleanUtils.toStringTrueFalse(false));

        KSUifUtils.setBreadcrumbRedirectUrlParams(urlParameters, theForm);
        return urlParameters;
    }

    public static boolean confirmDelete(ARGCourseOfferingManagementForm theForm) throws Exception {
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
        } else {
            // check if there is Draft AO selected
            selectedIndexList.clear();
            for (ActivityOfferingWrapper ao : aoList) {
                if (ao.isLegalToDelete() && ((!"0".equals(theForm.getSelectedTabId()))&& ao.getIsChecked()||
                                             ( "0".equals(theForm.getSelectedTabId()))&& ao.getIsCheckedByCluster())) {
                    selectedIndexList.add(ao);
                } else if ((!"0".equals(theForm.getSelectedTabId()))&& ao.getIsChecked()||
                        ( "0".equals(theForm.getSelectedTabId()))&& ao.getIsCheckedByCluster()) {
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
            return false;
        } else {
            theForm.setSelectedIllegalAOInDeletion(false);
            if (bEncounteredNonDraftAOInDeletion) {
                theForm.setSelectedIllegalAOInDeletion(true);
            }
        }

        return true;
    }

    public static Properties view(ARGCourseOfferingManagementForm theForm, ActivityOfferingWrapper aoWrapper) throws Exception {
        Properties urlParameters = new Properties();
        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.START_METHOD);
        urlParameters.put(ActivityOfferingConstants.ACTIVITYOFFERING_ID, aoWrapper.getAoInfo().getId());
        urlParameters.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, ActivityOfferingInfo.class.getName());
        return urlParameters;
    }

    public static void addActivityOfferings(ARGCourseOfferingManagementForm theForm) throws Exception {

        String activityId = theForm.getActivityIdForNewAO();
        String formatOfferingId = theForm.getFormatOfferingIdForNewAO();
        int aoCount = Integer.parseInt(theForm.getNoOfActivityOfferings());

        ARGUtil.getViewHelperService(theForm).createActivityOfferings(formatOfferingId, activityId, aoCount, theForm);
        ARGUtil.reloadTheCourseOfferingWithAOs_RGs_Clusters(theForm);

        theForm.setFormatIdForNewAO(null);
        theForm.setFormatOfferingIdForNewAO(null);
        theForm.setActivityIdForNewAO(null);
        theForm.setNoOfActivityOfferings(null);
    }

    /**
     * This method does not delete, just marks which AOs are to be deleted.
     * @param theForm the form that has a list of activityWrappers that are checked or not
     * @throws Exception
     */
    public static void deleteAOs(ARGCourseOfferingManagementForm theForm) throws Exception {

        List<ActivityOfferingWrapper> aoList = theForm.getActivityWrapperList();
        List<ActivityOfferingWrapper> selectedIndexList = theForm.getSelectedToDeleteList();
        boolean bNoDeletion = false;
        int checked = 0;
        int enabled = 0;

        selectedIndexList.clear();
        for (ActivityOfferingWrapper ao : aoList) {

            if (ao.isEnableDeleteButton() && ((!"0".equals(theForm.getSelectedTabId()))&& ao.getIsChecked())||
                    ( "0".equals(theForm.getSelectedTabId()))&& ao.getIsCheckedByCluster()) {
                selectedIndexList.add(ao);
                enabled++;
            } else if ((!"0".equals(theForm.getSelectedTabId()))&& ao.getIsChecked() ||
                    ( "0".equals(theForm.getSelectedTabId()))&& ao.getIsCheckedByCluster()) {
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

        if (checked > enabled) {
            KSUifUtils.addGrowlMessageIcon(GrowlIcon.WARNING, CourseOfferingConstants.ACTIVITYOFFERING_TOOLBAR_DELETE);
        }

    }

    public static ARGCourseOfferingManagementForm createNewCluster(ARGCourseOfferingManagementForm theForm) throws Exception {

        String growlPrivateName="";
        String growlPublicName="";

        if (createAOCFromMove) {  //where is the call coming from
            if (theForm.getPrivateClusterNameForMovePopover().isEmpty()) {
                GlobalVariables.getMessageMap().putError("privateClusterNameForMovePopover", RegistrationGroupConstants.MSG_ERROR_CLUSTER_PRIVATE_NAME_IS_NULL);
                createAOCFromMove = false;
                return theForm;
            }

            growlPrivateName = theForm.getPrivateClusterNameForMovePopover();
            growlPublicName = theForm.getPublishedClusterNameForMovePopover();
        } else {
            if (theForm.getPrivateClusterNamePopover().isEmpty()) {
                GlobalVariables.getMessageMap().putError("privateClusterNamePopover", RegistrationGroupConstants.MSG_ERROR_CLUSTER_PRIVATE_NAME_IS_NULL);
                return theForm;
            }

            growlPrivateName = theForm.getPrivateClusterNamePopover();
            growlPublicName = theForm.getPublishedClusterNamePopover();
        }

        //fix names
        /*if(theForm.getPrivateClusterNamePopover().contains(",") ) {
            theForm.setPrivateClusterNamePopover(theForm.getPrivateClusterNamePopover().replace(",",""));
        }
        if(theForm.getPublishedClusterNamePopover().contains(",") ) {
            theForm.setPublishedClusterNamePopover(theForm.getPublishedClusterNamePopover().replace(",",""));
        }*/


        String formatOfferingId = theForm.getFormatOfferingIdForViewRG();
        ContextInfo context = ContextUtils.createDefaultContextInfo();
        FormatOfferingInfo formatOfferingInfo = getCourseOfferingService().getFormatOffering(formatOfferingId, context);

        //validate if the given FO is allowed to create multiple clusters
        //FO has multiple AO types: it can have multiple clusters
        //FO has single AO type: it can only have one cluster
        if (!ARGUtil._clusterForFormatOfferingValidation(formatOfferingInfo, context)) {
            GlobalVariables.getMessageMap().putError("privateClusterName", FormatOfferingConstants.MSG_ERROR_FORMAT_OFFERING_CLUSTER_OVERLIMIT, formatOfferingInfo.getName());
            return theForm;
        }

        if (ARGUtil._isClusterUniqueWithinCO(theForm, theForm.getCurrentCourseOfferingWrapper().getCourseOfferingId(), growlPrivateName)){

            //build a new empty cluster
            ActivityOfferingClusterInfo emptyCluster = ARGUtil._buildEmptyAOCluster(formatOfferingId,
                    growlPrivateName, growlPublicName);

            //persist it in DB , comment out for now since it does not work for now
            emptyCluster = ARGUtil.getCourseOfferingService().createActivityOfferingCluster(formatOfferingId,
                    emptyCluster.getTypeKey(), emptyCluster, context);



            List<ActivityOfferingClusterWrapper> aoClusterWrapperList = theForm.getClusterResultList();
            ActivityOfferingClusterWrapper aoClusterWrapper = new ActivityOfferingClusterWrapper();
            aoClusterWrapper.setActivityOfferingClusterId(emptyCluster.getId());
            aoClusterWrapper.setAoCluster(emptyCluster);
            aoClusterWrapper.setClusterNameForDisplay("Forget to set cluster?");

            aoClusterWrapperList.add(aoClusterWrapper);
            theForm.setClusterResultList(aoClusterWrapperList);
            theForm.setPrivateClusterNamePopover("");
            theForm.setPublishedClusterNamePopover("");
            theForm.setPrivateClusterNameForMovePopover("");
            theForm.setPublishedClusterNameForMovePopover("");
            createAOCFromMove = false;
        }else{
            GlobalVariables.getMessageMap().putError("privateClusterName", RegistrationGroupConstants.MSG_ERROR_INVALID_CLUSTER_NAME);
            return theForm;
        }

        GlobalVariables.getMessageMap().addGrowlMessage("", "cluster.created", growlPrivateName, growlPublicName );

        return theForm;
    }

    public static ARGCourseOfferingManagementForm moveAOToACluster (ARGCourseOfferingManagementForm theForm) throws Exception {

        ContextInfo context = ContextUtils.createDefaultContextInfo();
        boolean aoChecked = false;
        String aocId;

        //get selected AOC info
        if (theForm.getClusterIdForAOMove().equals("")){
            GlobalVariables.getMessageMap().putError("AOCselectionError", RegistrationGroupConstants.MSG_ERROR_INVALID_CLUSTER_SELECTION);
            return theForm;
        }

        aocId = theForm.getClusterIdForAOMove();

        //move AO
        for (ActivityOfferingWrapper aoWrapper : theForm.getActivityWrapperList()) {
            if ((!"0".equals(theForm.getSelectedTabId())&& aoWrapper.getIsChecked())||
                ( "0".equals(theForm.getSelectedTabId())&& aoWrapper.getIsCheckedByCluster())) {
                if("createNewCluster".equals(aocId)){
                    // create cluster and set aocId to the id of the cluster
                    theForm.setPublishedClusterNameForRenamePopover(theForm.getPublishedClusterNamePopover());
                    if(!StringUtils.isEmpty(theForm.getFormatOfferingIdForViewRG())) {
                        theForm.setFormatOfferingIdForViewRG(theForm.getSelectedFOIDForAOMove());
                    }
                    createAOCFromMove = true;
                    theForm = createNewCluster(theForm);
                    if(GlobalVariables.getMessageMap().hasErrors()){
                        return theForm;
                    }

                    aocId = theForm.getClusterResultList().get(theForm.getClusterResultList().size() - 1).getActivityOfferingClusterId();

                }
                ARGUtil.getArgServiceAdapter().moveActivityOffering(aoWrapper.getAoInfo().getId(), aoWrapper.getAoClusterID(), aocId, context);
                aoChecked = true;
            }
        }

        //at least one AO must be checked
        if (!aoChecked) {
            GlobalVariables.getMessageMap().putError("AOCselectionError", RegistrationGroupConstants.MSG_ERROR_INVALID_AO_SELECTION);
            return theForm;
        }

        GlobalVariables.getMessageMap().addGrowlMessage("", "activityOffering.moved" );

        return theForm;

    }

    public static void removeAOFromCluster(ARGCourseOfferingManagementForm theForm) throws Exception {
/*        ActivityOfferingWrapper selectedAOWrapper =(ActivityOfferingWrapper)ARGUtil.getSelectedObject(theForm, "Remove");
        String selectedCollectionPath = theForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        if (StringUtils.isBlank(selectedCollectionPath)) {
            throw new RuntimeException("Selected collection was not set for Remove");
        }
        String selectedClusterIndex = StringUtils.substringBetween(selectedCollectionPath,"filteredAOClusterWrapperList[","]");
        ActivityOfferingClusterWrapper theClusterWrapper = theForm.getFilteredAOClusterWrapperList().get(Integer.parseInt(selectedClusterIndex));

        //First, update the DB for cluster and RGs if any
        String aoTypeKey = selectedAOWrapper.getTypeKey();
        if (aoTypeKey== null || aoTypeKey.isEmpty()) {
            try {
                TypeInfo typeInfo = ARGUtil.getTypeService().getType(selectedAOWrapper.getAoInfo().getTypeKey(), ContextUtils.createDefaultContextInfo());
                selectedAOWrapper.setTypeKey(typeInfo.getKey());
                selectedAOWrapper.setTypeName(typeInfo.getName());
                aoTypeKey = typeInfo.getKey();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        //try to retrieve the accurate AOCluster from DB always
        ActivityOfferingClusterInfo aoCluster = ARGUtil.getCourseOfferingService().getActivityOfferingCluster(
                theClusterWrapper.getAoCluster().getId(), ContextUtils.createDefaultContextInfo());
        List <ActivityOfferingSetInfo> aoSetList = aoCluster.getActivityOfferingSets();
        for (ActivityOfferingSetInfo aoSet:aoSetList) {
            if (aoTypeKey.equalsIgnoreCase(aoSet.getActivityOfferingType())) {
                aoSet.getActivityOfferingIds().remove(selectedAOWrapper.getAoInfo().getId());
                break;
            }
        }
        aoCluster = ARGUtil.getCourseOfferingService().updateActivityOfferingCluster(theForm.getFormatOfferingIdForViewRG(),
                aoCluster.getId(), aoCluster, ContextUtils.createDefaultContextInfo());
        theClusterWrapper.setAoCluster(aoCluster);

        List<RegistrationGroupInfo> rgInfos = ARGUtil.getCourseOfferingService().getRegistrationGroupsByActivityOfferingCluster(aoCluster.getId(), ContextUtils.createDefaultContextInfo());
        List<RegistrationGroupWrapper> filteredRGs = ARGUtil._getRGsForSelectedFO(rgInfos, theClusterWrapper.getAoWrapperList());
        theClusterWrapper.setRgWrapperList(filteredRGs);
        if (rgInfos.size() > 0) {
            theClusterWrapper.setRgStatus(RegistrationGroupConstants.RGSTATUS_ALL_RG_GENERATED);
            theClusterWrapper.setRgMessageStyle(ActivityOfferingClusterWrapper.RG_MESSAGE_ALL);
            theClusterWrapper.setHasAllRegGroups(true);
        }
        for (int i = 0; i < theForm.getFilteredAOClusterWrapperList().size(); i++) {
            rgInfos = ARGUtil.getCourseOfferingService().getRegistrationGroupsByActivityOfferingCluster(theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster().getId(),
                    ContextUtils.createDefaultContextInfo());
            //validate RGs for each cluster and set error msg
            if (rgInfos.size() > 0 && theForm.getFilteredAOClusterWrapperList().get(i).isHasAllRegGroups() ) {
                // perform max enrollment validation
                ARGUtil._performMaxEnrollmentValidation(theForm.getFormatOfferingIdForViewRG(), theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster(), i);
                //validate AO time conflict in RG
                ARGUtil._performRGTimeConflictValidation(theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster(), rgInfos, i);
            }
        }
        //finally, move selected AO from AO table under selected Cluster to the unassigned table
        theClusterWrapper.getAoWrapperList().remove(selectedAOWrapper);
        theForm.getFilteredUnassignedAOsForSelectedFO().add(selectedAOWrapper);
 */   }

    public static void deleteACluster(ARGCourseOfferingManagementForm theForm) throws Exception {
       /*
        ActivityOfferingClusterWrapper selectedCluster = (ActivityOfferingClusterWrapper)ARGUtil.getSelectedObject(theForm, "Remove Cluster");
        //first need to move all AOs under the selected Cluster back to filteredUnassignedAOsForSelectedFO
        List<ActivityOfferingWrapper> unassignedAOs = theForm.getFilteredUnassignedAOsForSelectedFO();
        List<ActivityOfferingWrapper> toBeRemovedAOs = selectedCluster.getAoWrapperList();
        for(ActivityOfferingWrapper aoWrapper:toBeRemovedAOs){
            unassignedAOs.add(aoWrapper);
        }
        theForm.setFilteredUnassignedAOsForSelectedFO(unassignedAOs);
        //then delete the selected cluster
        ARGUtil.getCourseOfferingService().deleteActivityOfferingClusterCascaded(selectedCluster.getActivityOfferingClusterId(), ContextUtils.createDefaultContextInfo());
        List<ActivityOfferingClusterWrapper> aoClusterWrapperList = theForm.getFilteredAOClusterWrapperList();
        aoClusterWrapperList.remove(selectedCluster);
        if (aoClusterWrapperList.size() == 0){
            theForm.setHasAOCluster(false);
        }
        */
    }

    public static void generateRegGroupsPerCluster (ARGCourseOfferingManagementForm theForm) throws Exception {
        /*
        ActivityOfferingClusterWrapper selectedClusterWrapper = (ActivityOfferingClusterWrapper)ARGUtil.getSelectedObject(theForm, "Generate Registration Groups");
        String selectedClusterId = selectedClusterWrapper.getAoCluster().getId();
        AOClusterVerifyResultsInfo aoClusterVerifyResultsInfo = ARGUtil.getCourseOfferingService().verifyActivityOfferingClusterForGeneration(selectedClusterId,ContextUtils.createDefaultContextInfo());
        if (!aoClusterVerifyResultsInfo.getValidationResults().get(0).isWarn())  {
            ARGUtil.getCourseOfferingService().generateRegistrationGroupsForCluster(selectedClusterId, ContextUtils.createDefaultContextInfo());
            List<RegistrationGroupInfo> rgInfos = ARGUtil.getCourseOfferingService().getRegistrationGroupsByActivityOfferingCluster(selectedClusterId, ContextUtils.createDefaultContextInfo());
            if (rgInfos.size() > 0) {
                //build RGWrapperList and set it to selectedClusterWrapper

                String clusterIndex = theForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
                // perform max enrollment validation
                ARGUtil._performMaxEnrollmentValidation(theForm.getFormatOfferingIdForViewRG(), selectedClusterWrapper.getAoCluster(), Integer.parseInt(clusterIndex));
                //validate AO time conflict in RG
                ARGUtil._performRGTimeConflictValidation(selectedClusterWrapper.getAoCluster(), rgInfos, Integer.parseInt(clusterIndex));

                List<RegistrationGroupWrapper> rgWrapperListPerCluster = ARGUtil._getRGsForSelectedFO(rgInfos, selectedClusterWrapper.getAoWrapperList());
                selectedClusterWrapper.setRgWrapperList(rgWrapperListPerCluster);
                selectedClusterWrapper.setHasAllRegGroups(true);
                selectedClusterWrapper.setRgStatus(RegistrationGroupConstants.RGSTATUS_ALL_RG_GENERATED);
                selectedClusterWrapper.setRgMessageStyle(ActivityOfferingClusterWrapper.RG_MESSAGE_ALL);
            }
        } else {
            aoClusterVerifyResultsInfo.getValidationResults().get(0).getMessage();
            int selectedLineIndex = -1;
            String selectedLine = theForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
            if (StringUtils.isNotBlank(selectedLine)) {
                selectedLineIndex = Integer.parseInt(selectedLine);
            }

            if (selectedLineIndex == -1) {
                throw new RuntimeException("Selected line index was not set");
            }
            GlobalVariables.getMessageMap().putErrorForSectionId("activityOfferingsPerCluster_line"+selectedLine, RegistrationGroupConstants.MSG_ERROR_INVALID_CLUSTER);
        }
        // Update all form AOCs with validation messages
        for (int i = 0; i < theForm.getFilteredAOClusterWrapperList().size(); i++) {
            // Collect RGs for each cluster
            List<RegistrationGroupInfo> rgInfos = ARGUtil.getCourseOfferingService().getRegistrationGroupsByActivityOfferingCluster(
                    theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster().getId(), ContextUtils.createDefaultContextInfo());
            // validate RGs for each cluster and set error msg
            if (rgInfos.size() > 0 && theForm.getFilteredAOClusterWrapperList().get(i).isHasAllRegGroups() ) {
                // perform max enrollment validation
                ARGUtil._performMaxEnrollmentValidation(theForm.getFormatOfferingIdForViewRG(), theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster(), i);
                //validate AO time conflict in RG
                ARGUtil._performRGTimeConflictValidation(theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster(), rgInfos, i);
            }
        }*/
    }

    public static void generateAllRegGroups (ARGCourseOfferingManagementForm theForm) throws Exception {
 /*       //Generate RGs for all AOCs
        for (int i = 0; i < theForm.getFilteredAOClusterWrapperList().size(); i++) {
            String selectedClusterId = theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster().getId();
            AOClusterVerifyResultsInfo aoClusterVerifyResultsInfo = ARGUtil.getCourseOfferingService().verifyActivityOfferingClusterForGeneration(selectedClusterId,ContextUtils.createDefaultContextInfo());
            List<RegistrationGroupInfo> rgInfos = ARGUtil.getCourseOfferingService().getRegistrationGroupsByActivityOfferingCluster(selectedClusterId, ContextUtils.createDefaultContextInfo());
            //only generate for valid AOCs without RGs or partial RGs
            if (!aoClusterVerifyResultsInfo.getValidationResults().get(0).isWarn() && !theForm.getFilteredAOClusterWrapperList().get(i).isHasAllRegGroups())  {
                ARGUtil.getCourseOfferingService().generateRegistrationGroupsForCluster(selectedClusterId, ContextUtils.createDefaultContextInfo());
                rgInfos = ARGUtil.getCourseOfferingService().getRegistrationGroupsByActivityOfferingCluster(selectedClusterId, ContextUtils.createDefaultContextInfo());
                if (rgInfos.size() > 0 ) {
                    //build RGWrapperList and set it to selectedClusterWrapper
                    List<RegistrationGroupWrapper> rgWrapperListPerCluster = ARGUtil._getRGsForSelectedFO(rgInfos, theForm.getFilteredAOClusterWrapperList().get(i).getAoWrapperList());
                    theForm.getFilteredAOClusterWrapperList().get(i).setRgWrapperList(rgWrapperListPerCluster);
                    theForm.getFilteredAOClusterWrapperList().get(i).setHasAllRegGroups(true);
                    theForm.getFilteredAOClusterWrapperList().get(i).setRgStatus(RegistrationGroupConstants.RGSTATUS_ALL_RG_GENERATED);
                    theForm.getFilteredAOClusterWrapperList().get(i).setRgMessageStyle(ActivityOfferingClusterWrapper.RG_MESSAGE_ALL);
                }
            } else if (rgInfos.size() < 1 ) {
                GlobalVariables.getMessageMap().putErrorForSectionId("activityOfferingsPerCluster_line"+i, RegistrationGroupConstants.MSG_ERROR_INVALID_CLUSTER);
            }
            //validate RGs for each cluster and set error msg
            if (rgInfos.size() > 0 && theForm.getFilteredAOClusterWrapperList().get(i).isHasAllRegGroups() ) {
                // perform max enrollment validation
                ARGUtil._performMaxEnrollmentValidation(theForm.getFormatOfferingIdForViewRG(), theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster(), i);
                //validate AO time conflict in RG
                ARGUtil._performRGTimeConflictValidation(theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster(), rgInfos, i);
            }
        } */
    }

    /*
    public static void generateUnconstrainedRegGroups (RegistrationGroupManagementForm theForm) throws Exception {
        String formatOfferingId = theForm.getFormatOfferingIdForViewRG();

        //new implementation for M5
        //first, build a new default cluster
        ActivityOfferingClusterInfo defaultCluster = ARGUtil._buildDefaultAOCluster(formatOfferingId, theForm);


        AOClusterVerifyResultsInfo aoClusterVerifyResultsInfo = ARGUtil.getCourseOfferingService().
                verifyActivityOfferingClusterForGeneration(defaultCluster.getId(),ContextUtils.createDefaultContextInfo());
        if (!aoClusterVerifyResultsInfo.getValidationResults().get(0).isWarn())  {
            //now create RGs for the default cluster
            ARGUtil.getCourseOfferingService().generateRegistrationGroupsForCluster(defaultCluster.getId(), ContextUtils.createDefaultContextInfo());

            //build and set ActivityOfferingClusterWrapper
            ActivityOfferingClusterWrapper aoClusterWrapper = ARGUtil._buildAOClusterWrapper (defaultCluster, theForm,0);
            List<ActivityOfferingWrapper> defaultAOList = new ArrayList<ActivityOfferingWrapper>();
            List<ActivityOfferingWrapper> filteredAOs = theForm.getFilteredUnassignedAOsForSelectedFO();
            for(ActivityOfferingWrapper aoWrapper:filteredAOs){
                defaultAOList.add(aoWrapper);
            }
            aoClusterWrapper.setAoWrapperList(defaultAOList);
            List<ActivityOfferingClusterWrapper> aoClusterWrapperList = new ArrayList<ActivityOfferingClusterWrapper>();
            aoClusterWrapperList.add(aoClusterWrapper);
            theForm.setFilteredAOClusterWrapperList(aoClusterWrapperList);
            theForm.setHasAOCluster(true);
            //no AO in unassigned list any more for now
            filteredAOs.clear();
            theForm.setFilteredUnassignedAOsForSelectedFO(filteredAOs);
        }else {
            ARGUtil.getCourseOfferingService().deleteActivityOfferingCluster(defaultCluster.getId(), ContextUtils.createDefaultContextInfo());
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES, RegistrationGroupConstants.MSG_ERROR_INVALID_AOLIST);
        }
    }
    */
    public static CourseOfferingService getCourseOfferingService() {
        return CourseOfferingResourceLoader.loadCourseOfferingService();
    }

//    public RegistrationGroupManagementViewHelperService getViewHelperService(RegistrationGroupManagementForm theForm){
//
//        if (viewHelperService == null) {
//            if (theForm.getView().getViewHelperServiceClass() != null){
//                viewHelperService = (RegistrationGroupManagementViewHelperService) theForm.getView().getViewHelperService();
//            }else{
//                viewHelperService= (RegistrationGroupManagementViewHelperService) theForm.getPostedView().getViewHelperService();
//            }
//        }
//
//        return viewHelperService;
//    }

    public static void showDeleteClusterConfirmPage(ARGCourseOfferingManagementForm theForm) throws Exception {

        Object selectedObject = ARGUtil.getSelectedObject(theForm, "Delete");
        if (selectedObject instanceof ActivityOfferingClusterWrapper) {
            ActivityOfferingClusterWrapper clusterWrapper = (ActivityOfferingClusterWrapper)selectedObject;
            theForm.setSelectedCluster(clusterWrapper);
            theForm.setSelectedToDeleteList(clusterWrapper.getAoWrapperList());
            theForm.setTotalAOsToBeDeleted(clusterWrapper.getAoWrapperList().size());



        }
    }

    public static void renameAClusterThroughDialog(ARGCourseOfferingManagementForm theForm) throws Exception {
        Object selectedObject = ARGUtil.getSelectedObject(theForm, "Rename");
        if (selectedObject instanceof ActivityOfferingClusterWrapper) {
            ActivityOfferingClusterWrapper clusterWrapper = (ActivityOfferingClusterWrapper)selectedObject;
            theForm.setSelectedCluster(clusterWrapper);
        }
    }

    public static void deleteClusterCascaded(ARGCourseOfferingManagementForm theForm) throws Exception {
        ActivityOfferingClusterWrapper aoWrapper = theForm.getSelectedCluster();
        ARGUtil.getArgServiceAdapter().deleteActivityOfferingCluster(aoWrapper.getActivityOfferingClusterId(), ContextBuilder.loadContextInfo());
        ARGUtil.reloadTheCourseOfferingWithAOs_RGs_Clusters(theForm);
    }


}