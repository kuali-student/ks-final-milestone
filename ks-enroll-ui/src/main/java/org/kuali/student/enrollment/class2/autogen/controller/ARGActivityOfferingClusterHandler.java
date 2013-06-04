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
import org.kuali.student.common.uif.util.GrowlIcon;
import org.kuali.student.common.uif.util.KSUifUtils;
import org.kuali.student.enrollment.class2.autogen.form.ARGCourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingClusterWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingListSectionWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.util.ActivityOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingViewHelperUtil;
import org.kuali.student.enrollment.class2.courseoffering.util.FormatOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.RegistrationGroupConstants;
import org.kuali.student.common.util.ContextBuilder;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * This class is used by the ARGCourseOfferingManagementController to handle AO Cluster operations
 *
 * @author Kuali Student Team
 */
public class ARGActivityOfferingClusterHandler {

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
            String clusterId = selectedAO.getAoClusterID();
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

    public static Properties view(@SuppressWarnings("unused") ARGCourseOfferingManagementForm theForm, ActivityOfferingWrapper aoWrapper) throws Exception {
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

    public static ARGCourseOfferingManagementForm createNewCluster(ARGCourseOfferingManagementForm theForm) throws Exception {

        String growlPrivateName;
        String growlPublicName;

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


    public static CourseOfferingService getCourseOfferingService() {
        return CourseOfferingResourceLoader.loadCourseOfferingService();
    }

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
        CourseOfferingViewHelperUtil.updateCourseOfferingStateFromActivityOfferingStateChange(theForm.getCurrentCourseOfferingWrapper().getCourseOfferingInfo(), ContextBuilder.loadContextInfo());
        ARGUtil.reloadTheCourseOfferingWithAOs_RGs_Clusters(theForm);
    }


}