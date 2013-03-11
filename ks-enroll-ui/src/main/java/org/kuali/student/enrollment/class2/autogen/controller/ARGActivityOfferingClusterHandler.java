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
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingClusterWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingListSectionWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.RegistrationGroupWrapper;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.form.RegistrationGroupManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.service.RegistrationGroupManagementViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.util.ActivityOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.class2.courseoffering.util.RegistrationGroupConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.ViewHelperUtil;
import org.kuali.student.enrollment.common.util.ContextBuilder;
import org.kuali.student.enrollment.courseoffering.dto.AOClusterVerifyResultsInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingSetInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.uif.util.GrowlIcon;
import org.kuali.student.enrollment.uif.util.KSUifUtils;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class ARGActivityOfferingClusterHandler extends UifControllerBase {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ARGActivityOfferingClusterHandler.class);
    private RegistrationGroupManagementViewHelperService viewHelperService;

    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new RegistrationGroupManagementForm();
    }

    public static boolean loadAOs(CourseOfferingManagementForm form) throws Exception {

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

    public static void selectAllActivityOfferings(CourseOfferingManagementForm theForm) throws Exception {
        List<ActivityOfferingWrapper> list = theForm.getActivityWrapperList();
        for (ActivityOfferingWrapper listElement : list) {
            listElement.setIsChecked(true);
        }
    }

    public static void copyAO(CourseOfferingManagementForm form) {
        ActivityOfferingWrapper selectedAO = (ActivityOfferingWrapper) ARGUtil.getSelectedObject(form, "copy");
        try {
            CourseOfferingResourceLoader.loadCourseOfferingService().copyActivityOffering(selectedAO.getAoInfo().getId(), ContextBuilder.loadContextInfo());

            //reload AOs including the new one just created
            ARGUtil.reloadActivityOffering(form);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteSelectedAoList(CourseOfferingManagementForm theForm) throws Exception {

        List<ActivityOfferingWrapper> selectedAolist = theForm.getSelectedToDeleteList();

        try {
            for (ActivityOfferingWrapper ao : selectedAolist) {
                ARGUtil.getCourseOfferingService().deleteActivityOfferingCascaded(ao.getAoInfo().getId(), ContextBuilder.loadContextInfo());
            }

            // check for changes to states in CO and related FOs
            ViewHelperUtil.updateCourseOfferingStateFromActivityOfferingStateChange(theForm.getCurrentCourseOfferingWrapper().getCourseOfferingInfo(), ContextBuilder.loadContextInfo());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ARGUtil.reloadActivityOffering(theForm);

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

    public static Properties edit(CourseOfferingManagementForm theForm, ActivityOfferingWrapper aoWrapper) throws Exception {
        Properties urlParameters = new Properties();
        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.Maintenance.METHOD_TO_CALL_EDIT);
        urlParameters.put(ActivityOfferingConstants.ACTIVITY_OFFERING_WRAPPER_ID, aoWrapper.getAoInfo().getId());
        urlParameters.put(ActivityOfferingConstants.ACTIVITYOFFERING_COURSE_OFFERING_ID, theForm.getCurrentCourseOfferingWrapper().getCourseOfferingInfo().getId());
        urlParameters.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, ActivityOfferingWrapper.class.getName());
        urlParameters.put(UifConstants.UrlParams.SHOW_HOME, BooleanUtils.toStringTrueFalse(false));

        KSUifUtils.setBreadcrumbRedirectUrlParams(urlParameters, theForm);
        return urlParameters;
    }

    public static boolean confirmDelete(CourseOfferingManagementForm theForm) throws Exception {
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
                if (ao.isLegalToDelete() && ao.getIsChecked()) {
                    selectedIndexList.add(ao);
                } else if (ao.getIsChecked()) {
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

    public static Properties view(CourseOfferingManagementForm theForm, ActivityOfferingWrapper aoWrapper) throws Exception {
        Properties urlParameters = new Properties();
        urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.START_METHOD);
        urlParameters.put(ActivityOfferingConstants.ACTIVITYOFFERING_ID, aoWrapper.getAoInfo().getId());
        urlParameters.put(KRADConstants.DATA_OBJECT_CLASS_ATTRIBUTE, ActivityOfferingInfo.class.getName());
        return urlParameters;
    }

    public static void addActivityOfferings(CourseOfferingManagementForm theForm) throws Exception {

        String activityId = theForm.getActivityIdForNewAO();
        String formatId = theForm.getFormatIdForNewAO();
        int aoCount = Integer.parseInt(theForm.getNoOfActivityOfferings());

        ARGUtil.getViewHelperService(theForm).createActivityOfferings(formatId, activityId, aoCount, theForm);

        theForm.setFormatIdForNewAO(null);
        theForm.setActivityIdForNewAO(null);
        theForm.setNoOfActivityOfferings(null);
    }

    public static void deleteAOs(CourseOfferingManagementForm theForm) throws Exception {

        List<ActivityOfferingWrapper> aoList = theForm.getActivityWrapperList();
        List<ActivityOfferingWrapper> selectedIndexList = theForm.getSelectedToDeleteList();
        boolean bNoDeletion = false;
        int checked = 0;
        int enabled = 0;

        selectedIndexList.clear();
        for (ActivityOfferingWrapper ao : aoList) {

            if (ao.isEnableDeleteButton() && ao.getIsChecked()) {
                selectedIndexList.add(ao);
                enabled++;
            } else if (ao.getIsChecked()) {
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


    //  RegistrationGroupManagementController related methods:

    public static void filterAOsAndRGsPerFO (RegistrationGroupManagementForm theForm) throws Exception {
        //First cleanup and reset AOCluster list and filteredUnassignedAOsForSelectedFO
        List<ActivityOfferingClusterWrapper> filteredAOClusterWrapperList = new ArrayList<ActivityOfferingClusterWrapper>();
        theForm.setFilteredAOClusterWrapperList(filteredAOClusterWrapperList);
        List<ActivityOfferingWrapper> filteredAOs = new ArrayList<ActivityOfferingWrapper>();
        theForm.setFilteredUnassignedAOsForSelectedFO(filteredAOs);

        // Then update the select Format Offering Name in the form
        String selectedFOId = theForm.getFormatOfferingIdForViewRG();
        if (!selectedFOId.isEmpty()){
            FormatOfferingInfo selectedFO = ARGUtil.getCourseOfferingService().getFormatOffering(selectedFOId, ContextUtils.createDefaultContextInfo());
            theForm.setFormatOfferingName(selectedFO.getName());
        }

        //get unassgined AOs (didn't belong to any cluster)
        filteredAOs = ARGUtil.getAOsWithoutClusterForSelectedFO(theForm.getFormatOfferingIdForViewRG(), theForm);
        for (ActivityOfferingWrapper aoWrapper : filteredAOs) {
            String cssClass = (aoWrapper.getAoInfo().getScheduleId() == null ? "uif-scheduled-dl" : "uif-actual-dl");
            aoWrapper.setDaysDisplayName(aoWrapper.getDaysDisplayName(), false, cssClass);
            aoWrapper.setStartTimeDisplay(aoWrapper.getStartTimeDisplay(), false, cssClass);
            aoWrapper.setEndTimeDisplay(aoWrapper.getEndTimeDisplay(), false, cssClass);
            aoWrapper.setBuildingName(aoWrapper.getBuildingName(), false, cssClass);
            aoWrapper.setRoomName(aoWrapper.getRoomName(), false, cssClass);
        }
        theForm.setFilteredUnassignedAOsForSelectedFO(filteredAOs);

        //get clusters if any for the selected FO
        List<ActivityOfferingClusterInfo> aoClusters = ARGUtil.getCourseOfferingService().getActivityOfferingClustersByFormatOffering(selectedFOId, ContextUtils.createDefaultContextInfo());
        if (aoClusters == null || aoClusters.size()==0){
            theForm.setHasAOCluster(false);
        }
        else {
            theForm.setHasAOCluster(true);
            List <ActivityOfferingClusterWrapper> aoClusterWrappers = ARGUtil._convertToAOClusterWrappers(aoClusters, theForm);
            theForm.setFilteredAOClusterWrapperList(aoClusterWrappers);
        }
    }

    public static void createNewCluster(RegistrationGroupManagementForm theForm) throws Exception {
//TODO: Fix forms and references as they get developed
        String formatOfferingId = theForm.getFormatOfferingIdForViewRG();
        if (ARGUtil._isClusterUnique(formatOfferingId, theForm.getPrivateClusterName())){
            //build a new empty cluster
            ActivityOfferingClusterInfo emptyCluster = ARGUtil._buildEmptyAOCluster(formatOfferingId,
                    theForm.getPrivateClusterName(), theForm.getPrivateClusterName());

            //persist it in DB , comment out for now since it does not work for now
            emptyCluster = ARGUtil.getCourseOfferingService().createActivityOfferingCluster(formatOfferingId,
                    emptyCluster.getTypeKey(), emptyCluster, ContextUtils.createDefaultContextInfo());

            List<ActivityOfferingClusterWrapper> aoClusterWrapperList = theForm.getFilteredAOClusterWrapperList();
            ActivityOfferingClusterWrapper aoClusterWrapper = new ActivityOfferingClusterWrapper();
            aoClusterWrapper.setActivityOfferingClusterId(emptyCluster.getId());
            aoClusterWrapper.setAoCluster(emptyCluster);
            aoClusterWrapper.setClusterNameForDisplay("Forget to set cluster?");

            aoClusterWrapperList.add(aoClusterWrapper);
            theForm.setFilteredAOClusterWrapperList(aoClusterWrapperList);
            theForm.setHasAOCluster(true);
            theForm.setPrivateClusterName("");
            theForm.setPublishedClusterName("");
            theForm.setClusterIdIdForNewFO(emptyCluster.getId());
        }else{
            GlobalVariables.getMessageMap().putError("privateClusterName", RegistrationGroupConstants.MSG_ERROR_INVALID_CLUSTER_NAME);
        }
    }

    public ModelAndView moveAOToACluster (RegistrationGroupManagementForm theForm) throws Exception {
/*TODO: Fix forms and references as they get developed
            //get selected AOC info
            if (theForm.getClusterIdIdForNewFO().equals("")){
                GlobalVariables.getMessageMap().putError("AOCselectionError", RegistrationGroupConstants.MSG_ERROR_INVALID_CLUSTER_SELECTION);
                return getUIFModelAndView(theForm);
            }
            ActivityOfferingClusterInfo selectedAOCInfo = getCourseOfferingService().getActivityOfferingCluster(theForm.getClusterIdIdForNewFO(),ContextUtils.createDefaultContextInfo());

            //get FOs and add them to the selected AOC
            List<ActivityOfferingWrapper> aoWrapperList = theForm.getFilteredUnassignedAOsForSelectedFO();
            boolean aoChecked = false; //at least one AO must be checked
            for (ActivityOfferingWrapper aoWrapper : aoWrapperList) {
                if (aoWrapper.getIsChecked()) {
                    aoChecked = true;
                    //add to appropriate aosIds
                    for ( int i=0; i < selectedAOCInfo.getActivityOfferingSets().size(); i++) {
                        if ( selectedAOCInfo.getActivityOfferingSets().get(i).getActivityOfferingType().equals(aoWrapper.getAoInfo().getTypeKey()) ) {  //add aoId
                            selectedAOCInfo.getActivityOfferingSets().get(i).getActivityOfferingIds().add(aoWrapper.getAoInfo().getId());
                            break;
                        }
                    }
                }
            }
            if (!aoChecked) {
                GlobalVariables.getMessageMap().putError("AOCselectionError", RegistrationGroupConstants.MSG_ERROR_INVALID_AO_SELECTION);
                return getUIFModelAndView(theForm);
            }

            //persist selected AOC
            ActivityOfferingClusterInfo updatedSelectedAOCInfo = getCourseOfferingService().updateActivityOfferingCluster(theForm.getFormatOfferingIdForViewRG(),
                    theForm.getClusterIdIdForNewFO(),
                    selectedAOCInfo, ContextUtils.createDefaultContextInfo());
            //update AO list without cluster
            List<ActivityOfferingWrapper> filteredAOs = getAOsWithoutClusterForSelectedFO(updatedSelectedAOCInfo.getFormatOfferingId(), theForm);

            for (ActivityOfferingWrapper aoWrapper : filteredAOs) {
                String cssClass = (aoWrapper.getAoInfo().getScheduleId() == null ? "uif-scheduled-dl" : "uif-actual-dl");
                aoWrapper.setDaysDisplayName(aoWrapper.getDaysDisplayName(), false, cssClass);
                aoWrapper.setStartTimeDisplay(aoWrapper.getStartTimeDisplay(), false, cssClass);
                aoWrapper.setEndTimeDisplay(aoWrapper.getEndTimeDisplay(), false, cssClass);
                aoWrapper.setBuildingName(aoWrapper.getBuildingName(), false, cssClass);
                aoWrapper.setRoomName(aoWrapper.getRoomName(), false, cssClass);
            }

            theForm.setFilteredUnassignedAOsForSelectedFO(filteredAOs);
            if(!filteredAOs.isEmpty()){
                theForm.setFormatOfferingName(filteredAOs.get(0).getAoInfo().getFormatOfferingName());
            }

            //update AO list in selected cluster and sort out RGs
            List<ActivityOfferingWrapper> filteredClusteredAOs = new ArrayList <ActivityOfferingWrapper>();
            List<ActivityOfferingInfo> aosInCluster = getCourseOfferingService().getActivityOfferingsByCluster(updatedSelectedAOCInfo.getId(), ContextUtils.createDefaultContextInfo());
            for (ActivityOfferingInfo aoInfo : aosInCluster) {
                ActivityOfferingWrapper aoWrapper = getViewHelperService(theForm).convertAOInfoToWrapper(aoInfo);
                String cssClass = (aoInfo.getScheduleId() == null ? "uif-scheduled-dl" : "uif-actual-dl");
                aoWrapper.setDaysDisplayName(aoWrapper.getDaysDisplayName(), false, cssClass);
                aoWrapper.setStartTimeDisplay(aoWrapper.getStartTimeDisplay(), false, cssClass);
                aoWrapper.setEndTimeDisplay(aoWrapper.getEndTimeDisplay(), false, cssClass);
                aoWrapper.setBuildingName(aoWrapper.getBuildingName(), false, cssClass);
                aoWrapper.setRoomName(aoWrapper.getRoomName(), false, cssClass);

                filteredClusteredAOs.add(aoWrapper);


            }
            for (int i = 0; i < theForm.getFilteredAOClusterWrapperList().size(); i++) {
                //collect RGs
                List<RegistrationGroupInfo> rgInfos = getCourseOfferingService().getRegistrationGroupsByActivityOfferingCluster(
                        theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster().getId(), ContextUtils.createDefaultContextInfo());
                //Update selected AOC
                if (theForm.getFilteredAOClusterWrapperList().get(i).getActivityOfferingClusterId().equals(updatedSelectedAOCInfo.getId())) {
                    //need to update AOCluster in the AOClusterWrapper because it will be used for other operation
                    theForm.getFilteredAOClusterWrapperList().get(i).setAoCluster(updatedSelectedAOCInfo);
                    theForm.getFilteredAOClusterWrapperList().get(i).setAoWrapperList(filteredClusteredAOs);
                    //update RG status
                    rgInfos = getCourseOfferingService().getRegistrationGroupsByActivityOfferingCluster(updatedSelectedAOCInfo.getId(), ContextUtils.createDefaultContextInfo());
                    if (rgInfos.size() > 0) {
                        theForm.getFilteredAOClusterWrapperList().get(i).setHasAllRegGroups(false);
                        theForm.getFilteredAOClusterWrapperList().get(i).setRgStatus(RegistrationGroupConstants.RGSTATUS_SOME_RG_GENERATED);
                        theForm.getFilteredAOClusterWrapperList().get(i).setRgMessageStyle(ActivityOfferingClusterWrapper.RG_MESSAGE_PARTIAL);
                    }
                }
                //validate RGs for each cluster and set error msg
                if (rgInfos.size() > 0 && theForm.getFilteredAOClusterWrapperList().get(i).isHasAllRegGroups() ) {
                    // perform max enrollment validation
                    _performMaxEnrollmentValidation(theForm.getFormatOfferingIdForViewRG(), theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster(), i);
                    //validate AO time conflict in RG
                    _performRGTimeConflictValidation(theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster(), rgInfos, i);
                }
            }

            theForm.setClusterIdIdForNewFO("");
            //return updated form
*/            return getUIFModelAndView(theForm);
        }

    public static void removeAOFromCluster(RegistrationGroupManagementForm theForm) throws Exception {
        ActivityOfferingWrapper selectedAOWrapper =(ActivityOfferingWrapper)ARGUtil.getSelectedObject(theForm, "Remove");
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
    }

    public static void deleteACluster(RegistrationGroupManagementForm theForm) throws Exception {

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
    }

    public static void generateRegGroupsPerCluster (RegistrationGroupManagementForm theForm) throws Exception {
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
        }
    }

    public static void generateAllRegGroups (RegistrationGroupManagementForm theForm) throws Exception {
        //Generate RGs for all AOCs
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
        }
    }

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

    public CourseOfferingService getCourseOfferingService() {
        return CourseOfferingResourceLoader.loadCourseOfferingService();
    }

    public RegistrationGroupManagementViewHelperService getViewHelperService(RegistrationGroupManagementForm theForm){

        if (viewHelperService == null) {
            if (theForm.getView().getViewHelperServiceClass() != null){
                viewHelperService = (RegistrationGroupManagementViewHelperService) theForm.getView().getViewHelperService();
            }else{
                viewHelperService= (RegistrationGroupManagementViewHelperService) theForm.getPostedView().getViewHelperService();
            }
        }

        return viewHelperService;
    }

}