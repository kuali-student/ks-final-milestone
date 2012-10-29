package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingClusterWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.RegistrationGroupWrapper;
import org.kuali.student.enrollment.class2.courseoffering.form.RegistrationGroupManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.RegistrationGroupConstants;
import org.kuali.student.enrollment.courseoffering.dto.AOClusterVerifyResultsInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingClusterInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingSetInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.r2.common.class1.type.dto.TypeInfo;
import org.kuali.student.r2.common.permutation.PermutationCounter;
import org.kuali.student.r2.common.util.ContextUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/registrationGroupManagement")
public class RegistrationGroupManagementController extends UifControllerBase {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(CourseOfferingManagementController.class);
//    private TypeService typeService;
//    private StateService stateService;
//    private RegistrationGroupManagementViewHelperService viewHelperService;

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new RegistrationGroupManagementForm();
    }

    @Override
    @RequestMapping(params = "methodToCall=start")
    public ModelAndView start(@ModelAttribute("KualiForm") UifFormBase form, @SuppressWarnings("unused") BindingResult result,
                              @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) {

        if (!(form instanceof RegistrationGroupManagementForm)){
            throw new RuntimeException("Form object passed into start method was not of expected type RegistrationGroupManagementForm. Got "+form.getClass().getSimpleName());
        }

        RegistrationGroupManagementForm theForm = (RegistrationGroupManagementForm) form;

        // check view authorization
        // TODO: this needs to be invoked for each request
        if (form.getView() != null) {
            String methodToCall = request.getParameter(KRADConstants.DISPATCH_REQUEST_PARAMETER);
            checkViewAuthorization(theForm, methodToCall);
        }

        // check if the view is invoked within portal or not
        String inputValue = request.getParameter("withinPortal");
        if ((inputValue != null) && !inputValue.isEmpty()){
            boolean withinPortal = Boolean.valueOf(request.getParameter("withinPortal"));
            theForm.setWithinPortal(withinPortal);
        }
        
        // get the course offering id        
        try {
            String courseOfferingId = request.getParameter("coInfo.id");
            CourseOfferingInfo theCourseOffering = CourseOfferingControllerUtil.getCourseOfferingService().getCourseOffering(courseOfferingId, ContextUtils.createDefaultContextInfo());
            theForm.setTheCourseOffering(theCourseOffering);
            List<FormatOfferingInfo> formatOfferingList =
                    CourseOfferingControllerUtil.getCourseOfferingService().getFormatOfferingsByCourseOffering(courseOfferingId, ContextUtils.createDefaultContextInfo());
            theForm.setFormatOfferingName(formatOfferingList.get(0).getName());
            theForm.setFormatOfferingIdForViewRG(formatOfferingList.get(0).getId());
            //get unassgined AOs (didn't belong to any cluster)
            List<ActivityOfferingWrapper> filteredAOs = CourseOfferingControllerUtil.getAOsWithoutClusterForSelectedFO(formatOfferingList.get(0).getId(), theForm);
    
            for (ActivityOfferingWrapper aoWrapper : filteredAOs) {
                String cssClass = (aoWrapper.getAoInfo().getScheduleId() == null ? "uif-scheduled-dl" : "uif-actual-dl");
                aoWrapper.setDaysDisplayName(aoWrapper.getDaysDisplayName(), false, cssClass);
                aoWrapper.setStartTimeDisplay(aoWrapper.getStartTimeDisplay(), false, cssClass);
                aoWrapper.setEndTimeDisplay(aoWrapper.getEndTimeDisplay(), false, cssClass);
                aoWrapper.setBuildingName(aoWrapper.getBuildingName(), false, cssClass);
                aoWrapper.setRoomName(aoWrapper.getRoomName(), false, cssClass);
            }
            theForm.setFilteredUnassignedAOsForSelectedFO(filteredAOs);
    
            //get clusters if any for the 1st FO
            List<ActivityOfferingClusterInfo> aoClusters = CourseOfferingControllerUtil.getCourseOfferingService().getActivityOfferingClustersByFormatOffering(formatOfferingList.get(0).getId(), ContextUtils.createDefaultContextInfo());
            if (aoClusters == null || aoClusters.size()==0){
                theForm.setHasAOCluster(false);
            }
            else {
                theForm.setHasAOCluster(true);
                List <ActivityOfferingClusterWrapper> aoClusterWrappers = CourseOfferingControllerUtil._convertToAOClusterWrappers(aoClusters, theForm);
                theForm.setFilteredAOClusterWrapperList(aoClusterWrappers);
            }
        }catch (Exception e){
            LOG.error("Got exception when loading 'Manage Registration Groups'", e);
        }
        return super.start(theForm, result, request, response);
    }

    @RequestMapping(params = "methodToCall=filterAOsAndRGsPerFO")
    public ModelAndView filterAOsAndRGsPerFO (@ModelAttribute("KualiForm") RegistrationGroupManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                              @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        //First cleanup and reset AOCluster list and filteredUnassignedAOsForSelectedFO
        List<ActivityOfferingClusterWrapper> filteredAOClusterWrapperList = new ArrayList<ActivityOfferingClusterWrapper>();
        theForm.setFilteredAOClusterWrapperList(filteredAOClusterWrapperList);
        List<ActivityOfferingWrapper> filteredAOs = new ArrayList<ActivityOfferingWrapper>();
        theForm.setFilteredUnassignedAOsForSelectedFO(filteredAOs);

        // Then update the select Format Offering Name in the form
        String selectedFOId = theForm.getFormatOfferingIdForViewRG();
        if (!selectedFOId.isEmpty()){
            FormatOfferingInfo selectedFO = CourseOfferingControllerUtil.getCourseOfferingService().getFormatOffering(selectedFOId, ContextUtils.createDefaultContextInfo());
            theForm.setFormatOfferingName(selectedFO.getName());
        }

        //get unassgined AOs (didn't belong to any cluster)
        filteredAOs = CourseOfferingControllerUtil.getAOsWithoutClusterForSelectedFO(theForm.getFormatOfferingIdForViewRG(), theForm);
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
        List<ActivityOfferingClusterInfo> aoClusters = CourseOfferingControllerUtil.getCourseOfferingService().getActivityOfferingClustersByFormatOffering(selectedFOId, ContextUtils.createDefaultContextInfo());
        if (aoClusters == null || aoClusters.size()==0){
            theForm.setHasAOCluster(false);
        }
        else {
            theForm.setHasAOCluster(true);
            List <ActivityOfferingClusterWrapper> aoClusterWrappers = CourseOfferingControllerUtil._convertToAOClusterWrappers(aoClusters, theForm);
            theForm.setFilteredAOClusterWrapperList(aoClusterWrappers);
        }

        return getUIFModelAndView(theForm);
    }

    @RequestMapping(params = "methodToCall=createNewClusterFromLightBox")
    public ModelAndView createNewClusterFromLightBox(@ModelAttribute("KualiForm") RegistrationGroupManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                                     @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        if (!hasDialogBeenDisplayed("createNewClusterDialog", theForm)){
            //Set the focus in the lightbox
            theForm.setFocusId("privateClusterNameForLightBox_control");
            // redirect back to client to display lightbox
            return showDialog("createNewClusterDialog", theForm, request, response);
        }
        if(hasDialogBeenAnswered("createNewClusterDialog", theForm)) {
            boolean createNewCluster = getBooleanDialogResponse("createNewClusterDialog", theForm, request, response);
            if(createNewCluster){
                String formatOfferingId = theForm.getFormatOfferingIdForViewRG();
                if (theForm.getPrivateClusterNameForLightBox() == null || theForm.getPrivateClusterNameForLightBox().isEmpty()) {
                    GlobalVariables.getMessageMap().putError("privateClusterNameForLightBox", RegistrationGroupConstants.MSG_ERROR_CLUSTER_PRIVATE_NAME_IS_NULL);
                    GlobalVariables.getMessageMap().putErrorForSectionId("hasClusterCondition",
                            RegistrationGroupConstants.MSG_ERROR_CLUSTER_PRIVATE_NAME_IS_NULL);
                    theForm.getDialogManager().removeDialog("createNewClusterDialog");
                    return getUIFModelAndView(theForm, CourseOfferingConstants.REG_GROUP_PAGE);
                }
                if (CourseOfferingControllerUtil._isClusterUnique(formatOfferingId, theForm.getPrivateClusterNameForLightBox())){
                    //build a new empty cluster
                    ActivityOfferingClusterInfo emptyCluster = CourseOfferingControllerUtil._buildEmptyAOCluster(formatOfferingId,
                            theForm.getPrivateClusterNameForLightBox(), theForm.getPublishedClusterNameForLightBox());

                    //persist it in DB , comment out for now since it does not work for now
                    emptyCluster = CourseOfferingControllerUtil.getCourseOfferingService().createActivityOfferingCluster(formatOfferingId,
                            emptyCluster.getTypeKey(), emptyCluster, ContextUtils.createDefaultContextInfo());

                    List<ActivityOfferingClusterWrapper> aoClusterWrapperList = theForm.getFilteredAOClusterWrapperList();
                    ActivityOfferingClusterWrapper aoClusterWrapper = new ActivityOfferingClusterWrapper();
                    aoClusterWrapper.setActivityOfferingClusterId(emptyCluster.getId());
                    aoClusterWrapper.setAoCluster(emptyCluster);
                    aoClusterWrapper.setClusterNameForDisplay("Forget to set cluster?");

                    aoClusterWrapperList.add(aoClusterWrapper);
                    theForm.setFilteredAOClusterWrapperList(aoClusterWrapperList);
                    theForm.setHasAOCluster(true);
                    theForm.setPrivateClusterNameForLightBox("");
                    theForm.setPublishedClusterNameForLightBox("");
                    if(theForm.isSelectCreateNewFromDropDown()){
                        theForm.setClusterIdIdForNewFO(emptyCluster.getId());
                    }
                }else {
                    GlobalVariables.getMessageMap().putError("privateClusterNameForLightBox", RegistrationGroupConstants.MSG_ERROR_INVALID_CLUSTER_NAME);
                }
            }else {
                theForm.setPrivateClusterNameForLightBox("");
                theForm.setPublishedClusterNameForLightBox("");
            }

        }

        // clear dialog history so they can press the button again
        theForm.getDialogManager().removeDialog("createNewClusterDialog");
        return getUIFModelAndView(theForm);
    }

    @RequestMapping(params = "methodToCall=createNewCluster")
    public ModelAndView createNewCluster(@ModelAttribute("KualiForm") RegistrationGroupManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                         @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        String formatOfferingId = theForm.getFormatOfferingIdForViewRG();
        if (CourseOfferingControllerUtil._isClusterUnique(formatOfferingId, theForm.getPrivateClusterName())){
            //build a new empty cluster
            ActivityOfferingClusterInfo emptyCluster = CourseOfferingControllerUtil._buildEmptyAOCluster(formatOfferingId,
                    theForm.getPrivateClusterName(), theForm.getPrivateClusterName());

            //persist it in DB , comment out for now since it does not work for now
            emptyCluster = CourseOfferingControllerUtil.getCourseOfferingService().createActivityOfferingCluster(formatOfferingId,
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

        return getUIFModelAndView(theForm);
    }

    @RequestMapping(params = "methodToCall=renameAClusterThroughDialog")
    public ModelAndView renameAClusterThroughDialog(@ModelAttribute("KualiForm") RegistrationGroupManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                                    @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        ActivityOfferingClusterWrapper selectedClusterWrapper;
        if (!hasDialogBeenDisplayed("renameClusterDialog", theForm)){
            selectedClusterWrapper = (ActivityOfferingClusterWrapper)CourseOfferingControllerUtil._getSelectedObject(theForm, "Rename Cluster");
            theForm.setSelectedCluster(selectedClusterWrapper);
            theForm.setPrivateClusterNameForRename(selectedClusterWrapper.getAoCluster().getPrivateName());
            theForm.setPublishedClusterNameForRename(selectedClusterWrapper.getAoCluster().getName());

            //set clusterIndex for selected/from cluster
            String selectedClusterIndex = theForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
            theForm.setSelectedClusterIndex(Integer.parseInt(selectedClusterIndex));
            // redirect back to client to display lightbox
            return showDialog("renameClusterDialog", theForm, request, response);
        }
        if (hasDialogBeenAnswered("renameClusterDialog", theForm)) {
            boolean wantToRename = getBooleanDialogResponse("renameClusterDialog", theForm, request, response);
            if(wantToRename){
                selectedClusterWrapper = theForm.getSelectedCluster();
                if (theForm.getPrivateClusterNameForRename() == null || theForm.getPrivateClusterNameForRename().isEmpty()) {
                    GlobalVariables.getMessageMap().putError("privateClusterNameForRename", RegistrationGroupConstants.MSG_ERROR_CLUSTER_PRIVATE_NAME_IS_NULL);
                    GlobalVariables.getMessageMap().putErrorForSectionId("activityOfferingsPerCluster_line"+theForm.getSelectedClusterIndex(),
                            RegistrationGroupConstants.MSG_ERROR_CLUSTER_PRIVATE_NAME_IS_NULL);
                    theForm.getDialogManager().removeDialog("renameClusterDialog");
                    return getUIFModelAndView(theForm, CourseOfferingConstants.REG_GROUP_PAGE);
                }
                if (theForm.getSelectedCluster().getAoCluster().getPrivateName().equalsIgnoreCase(theForm.getPrivateClusterNameForRename()) || CourseOfferingControllerUtil._isClusterUnique(theForm.getFormatOfferingIdForViewRG(), theForm.getPrivateClusterNameForRename())){
                    ActivityOfferingClusterInfo  aoCluster = selectedClusterWrapper.getAoCluster();
                    aoCluster.setName(theForm.getPublishedClusterNameForRename());
                    aoCluster.setPrivateName(theForm.getPrivateClusterNameForRename());
                    aoCluster = CourseOfferingControllerUtil.getCourseOfferingService().updateActivityOfferingCluster(theForm.getFormatOfferingIdForViewRG(),
                            aoCluster.getId(), aoCluster, ContextUtils.createDefaultContextInfo());
                    selectedClusterWrapper.setAoCluster(aoCluster);
                    selectedClusterWrapper.setClusterNameForDisplay("Forget to set cluster?");
                } else {
                    GlobalVariables.getMessageMap().putError("privateClusterNameForRename", RegistrationGroupConstants.MSG_ERROR_INVALID_CLUSTER_NAME);
                }
            }
            theForm.setSelectedCluster(null);
        }
        theForm.setPrivateClusterNameForRename("");
        theForm.setPublishedClusterNameForRename("");
        // clear dialog history so they can press the button again
        theForm.getDialogManager().resetDialogStatus("renameClusterDialog");
        return getUIFModelAndView(theForm);
    }


    @RequestMapping(params = "methodToCall=removeAOFromCluster")
    public ModelAndView removeAOFromCluster(@ModelAttribute("KualiForm") RegistrationGroupManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                            @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        ActivityOfferingWrapper selectedAOWrapper =(ActivityOfferingWrapper)CourseOfferingControllerUtil._getSelectedObject(theForm, "Remove");
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
                TypeInfo typeInfo = CourseOfferingControllerUtil.getTypeService().getType(selectedAOWrapper.getAoInfo().getTypeKey(), ContextUtils.createDefaultContextInfo());
                selectedAOWrapper.setTypeKey(typeInfo.getKey());
                selectedAOWrapper.setTypeName(typeInfo.getName());
                aoTypeKey = typeInfo.getKey();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        //try to retrieve the accurate AOCluster from DB always
        ActivityOfferingClusterInfo aoCluster = CourseOfferingControllerUtil.getCourseOfferingService().getActivityOfferingCluster(
                theClusterWrapper.getAoCluster().getId(), ContextUtils.createDefaultContextInfo());
        List <ActivityOfferingSetInfo> aoSetList = aoCluster.getActivityOfferingSets();
        for (ActivityOfferingSetInfo aoSet:aoSetList) {
            if (aoTypeKey.equalsIgnoreCase(aoSet.getActivityOfferingType())) {
                aoSet.getActivityOfferingIds().remove(selectedAOWrapper.getAoInfo().getId());
                break;
            }
        }
        aoCluster = CourseOfferingControllerUtil.getCourseOfferingService().updateActivityOfferingCluster(theForm.getFormatOfferingIdForViewRG(),
                aoCluster.getId(), aoCluster, ContextUtils.createDefaultContextInfo());
        theClusterWrapper.setAoCluster(aoCluster);

        List<RegistrationGroupInfo> rgInfos =CourseOfferingControllerUtil.getCourseOfferingService().getRegistrationGroupsByActivityOfferingCluster(aoCluster.getId(), ContextUtils.createDefaultContextInfo());
        List<RegistrationGroupWrapper> filteredRGs = CourseOfferingControllerUtil._getRGsForSelectedFO(rgInfos, theClusterWrapper.getAoWrapperList());
        theClusterWrapper.setRgWrapperList(filteredRGs);
        if (rgInfos.size() > 0) {
            theClusterWrapper.setRgStatus(RegistrationGroupConstants.RGSTATUS_ALL_RG_GENERATED);
            theClusterWrapper.setRgMessageStyle(ActivityOfferingClusterWrapper.RG_MESSAGE_ALL);
            theClusterWrapper.setHasAllRegGroups(true);
        }
        for (int i = 0; i < theForm.getFilteredAOClusterWrapperList().size(); i++) {
            rgInfos = CourseOfferingControllerUtil.getCourseOfferingService().getRegistrationGroupsByActivityOfferingCluster(theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster().getId(),
                    ContextUtils.createDefaultContextInfo());
            //validate RGs for each cluster and set error msg
            if (rgInfos.size() > 0 && theForm.getFilteredAOClusterWrapperList().get(i).isHasAllRegGroups() ) {
                // perform max enrollment validation
                CourseOfferingControllerUtil._performMaxEnrollmentValidation(theForm.getFormatOfferingIdForViewRG(), theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster(), i);
                //validate AO time conflict in RG
                CourseOfferingControllerUtil._performRGTimeConflictValidation(theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster(), rgInfos, i);
            }
        }
        //finally, move selected AO from AO table under selected Cluster to the unassigned table
        theClusterWrapper.getAoWrapperList().remove(selectedAOWrapper);
        theForm.getFilteredUnassignedAOsForSelectedFO().add(selectedAOWrapper);

        return getUIFModelAndView(theForm);
    }

    @RequestMapping(params = "methodToCall=deleteAClusterThroughDialog")
    public ModelAndView deleteAClusterThroughDialog(@ModelAttribute("KualiForm") RegistrationGroupManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                                    @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        ActivityOfferingClusterWrapper selectedCluster;
        if (!hasDialogBeenDisplayed("confirmToDeleteClusterDialog", theForm)) {
            selectedCluster = (ActivityOfferingClusterWrapper)CourseOfferingControllerUtil._getSelectedObject(theForm, "Remove Cluster through Dialog");
            theForm.setSelectedCluster(selectedCluster);
            // redirect back to client to display lightbox
            return showDialog("confirmToDeleteClusterDialog", theForm, request, response);
        }
        if (hasDialogBeenAnswered("confirmToDeleteClusterDialog", theForm)) {
            boolean wantToDelete = getBooleanDialogResponse("confirmToDeleteClusterDialog", theForm, request, response);
            if (wantToDelete) {
                //first need to move all AOs under the selected Cluster back to filteredUnassignedAOsForSelectedFO
                selectedCluster = theForm.getSelectedCluster();
                List<ActivityOfferingWrapper> unassignedAOs = theForm.getFilteredUnassignedAOsForSelectedFO();
                List<ActivityOfferingWrapper> toBeRemovedAOs = selectedCluster.getAoWrapperList();
                for(ActivityOfferingWrapper aoWrapper:toBeRemovedAOs){
                    unassignedAOs.add(aoWrapper);
                }
                theForm.setFilteredUnassignedAOsForSelectedFO(unassignedAOs);
                //then delete the selected cluster
                CourseOfferingControllerUtil.getCourseOfferingService().deleteActivityOfferingClusterCascaded(selectedCluster.getActivityOfferingClusterId(), ContextUtils.createDefaultContextInfo());
                List<ActivityOfferingClusterWrapper> aoClusterWrapperList = theForm.getFilteredAOClusterWrapperList();
                aoClusterWrapperList.remove(selectedCluster);
                if(aoClusterWrapperList.size() ==0){
                    theForm.setHasAOCluster(false);
                }
            }
            theForm.setSelectedCluster(null);
        }
        // clear dialog history so they can press the button again
        theForm.getDialogManager().resetDialogStatus("confirmToDeleteClusterDialog");
        return getUIFModelAndView(theForm);
    }

    @RequestMapping(params = "methodToCall=deleteACluster")
    public ModelAndView deleteACluster(@ModelAttribute("KualiForm") RegistrationGroupManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                       @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        ActivityOfferingClusterWrapper selectedCluster = (ActivityOfferingClusterWrapper)CourseOfferingControllerUtil._getSelectedObject(theForm, "Remove Cluster");
        //first need to move all AOs under the selected Cluster back to filteredUnassignedAOsForSelectedFO
        List<ActivityOfferingWrapper> unassignedAOs = theForm.getFilteredUnassignedAOsForSelectedFO();
        List<ActivityOfferingWrapper> toBeRemovedAOs = selectedCluster.getAoWrapperList();
        for(ActivityOfferingWrapper aoWrapper:toBeRemovedAOs){
            unassignedAOs.add(aoWrapper);
        }
        theForm.setFilteredUnassignedAOsForSelectedFO(unassignedAOs);
        //then delete the selected cluster
        CourseOfferingControllerUtil.getCourseOfferingService().deleteActivityOfferingClusterCascaded(selectedCluster.getActivityOfferingClusterId(), ContextUtils.createDefaultContextInfo());
        List<ActivityOfferingClusterWrapper> aoClusterWrapperList = theForm.getFilteredAOClusterWrapperList();
        aoClusterWrapperList.remove(selectedCluster);
        if (aoClusterWrapperList.size() == 0){
            theForm.setHasAOCluster(false);
        }

        return getUIFModelAndView(theForm);
    }

    @RequestMapping(params = "methodToCall=generateRegGroupsPerCluster")
    public ModelAndView generateRegGroupsPerCluster (@ModelAttribute("KualiForm") RegistrationGroupManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                                     @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        ActivityOfferingClusterWrapper selectedClusterWrapper = (ActivityOfferingClusterWrapper)CourseOfferingControllerUtil._getSelectedObject(theForm, "Generate Registration Groups");
        String selectedClusterId = selectedClusterWrapper.getAoCluster().getId();
        AOClusterVerifyResultsInfo aoClusterVerifyResultsInfo = CourseOfferingControllerUtil.getCourseOfferingService().verifyActivityOfferingClusterForGeneration(selectedClusterId,ContextUtils.createDefaultContextInfo());
        if (!aoClusterVerifyResultsInfo.getValidationResults().get(0).isError())  {
            CourseOfferingControllerUtil.getCourseOfferingService().generateRegistrationGroupsForCluster(selectedClusterId, ContextUtils.createDefaultContextInfo());
            List<RegistrationGroupInfo> rgInfos = CourseOfferingControllerUtil.getCourseOfferingService().getRegistrationGroupsByActivityOfferingCluster(selectedClusterId, ContextUtils.createDefaultContextInfo());
            if (rgInfos.size() > 0) {
                //build RGWrapperList and set it to selectedClusterWrapper

                String clusterIndex = theForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
                // perform max enrollment validation
                CourseOfferingControllerUtil._performMaxEnrollmentValidation(theForm.getFormatOfferingIdForViewRG(), selectedClusterWrapper.getAoCluster(), Integer.parseInt(clusterIndex));
                //validate AO time conflict in RG
                CourseOfferingControllerUtil._performRGTimeConflictValidation(selectedClusterWrapper.getAoCluster(), rgInfos, Integer.parseInt(clusterIndex));

                List<RegistrationGroupWrapper> rgWrapperListPerCluster = CourseOfferingControllerUtil._getRGsForSelectedFO(rgInfos, selectedClusterWrapper.getAoWrapperList());
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
            List<RegistrationGroupInfo> rgInfos = CourseOfferingControllerUtil.getCourseOfferingService().getRegistrationGroupsByActivityOfferingCluster(
                    theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster().getId(), ContextUtils.createDefaultContextInfo());
            // validate RGs for each cluster and set error msg
            if (rgInfos.size() > 0 && theForm.getFilteredAOClusterWrapperList().get(i).isHasAllRegGroups() ) {
                // perform max enrollment validation
                CourseOfferingControllerUtil._performMaxEnrollmentValidation(theForm.getFormatOfferingIdForViewRG(), theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster(), i);
                //validate AO time conflict in RG
                CourseOfferingControllerUtil._performRGTimeConflictValidation(theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster(), rgInfos, i);
            }
        }

        return getUIFModelAndView(theForm);
    }

    @RequestMapping(params = "methodToCall=generateAllRegGroups")
    public ModelAndView generateAllRegGroups (@ModelAttribute("KualiForm") RegistrationGroupManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                              @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        //Generate RGs for all AOCs
        for (int i = 0; i < theForm.getFilteredAOClusterWrapperList().size(); i++) {
            String selectedClusterId = theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster().getId();
            AOClusterVerifyResultsInfo aoClusterVerifyResultsInfo = CourseOfferingControllerUtil.getCourseOfferingService().verifyActivityOfferingClusterForGeneration(selectedClusterId,ContextUtils.createDefaultContextInfo());
            List<RegistrationGroupInfo> rgInfos = CourseOfferingControllerUtil.getCourseOfferingService().getRegistrationGroupsByActivityOfferingCluster(selectedClusterId, ContextUtils.createDefaultContextInfo());
            //only generate for valid AOCs without RGs or partial RGs
            if (!aoClusterVerifyResultsInfo.getValidationResults().get(0).isError() && !theForm.getFilteredAOClusterWrapperList().get(i).isHasAllRegGroups())  {
                CourseOfferingControllerUtil.getCourseOfferingService().generateRegistrationGroupsForCluster(selectedClusterId, ContextUtils.createDefaultContextInfo());
                rgInfos = CourseOfferingControllerUtil.getCourseOfferingService().getRegistrationGroupsByActivityOfferingCluster(selectedClusterId, ContextUtils.createDefaultContextInfo());
                if (rgInfos.size() > 0 ) {
                    //build RGWrapperList and set it to selectedClusterWrapper
                    List<RegistrationGroupWrapper> rgWrapperListPerCluster = CourseOfferingControllerUtil._getRGsForSelectedFO(rgInfos, theForm.getFilteredAOClusterWrapperList().get(i).getAoWrapperList());
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
                CourseOfferingControllerUtil._performMaxEnrollmentValidation(theForm.getFormatOfferingIdForViewRG(), theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster(), i);
                //validate AO time conflict in RG
                CourseOfferingControllerUtil._performRGTimeConflictValidation(theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster(), rgInfos, i);
            }
        }

        return getUIFModelAndView(theForm);
    }

    @RequestMapping(params = "methodToCall=generateUnconstrainedRegGroups")
    public ModelAndView generateUnconstrainedRegGroups (@ModelAttribute("KualiForm") RegistrationGroupManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                                        @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        String formatOfferingId = theForm.getFormatOfferingIdForViewRG();

        //new implementation for M5
        //first, build a new default cluster
        ActivityOfferingClusterInfo defaultCluster = CourseOfferingControllerUtil._buildDefaultAOCluster(formatOfferingId, theForm);


        AOClusterVerifyResultsInfo aoClusterVerifyResultsInfo = CourseOfferingControllerUtil.getCourseOfferingService().
                verifyActivityOfferingClusterForGeneration(defaultCluster.getId(),ContextUtils.createDefaultContextInfo());
        if (!aoClusterVerifyResultsInfo.getValidationResults().get(0).isError())  {
            //now create RGs for the default cluster
            CourseOfferingControllerUtil.getCourseOfferingService().generateRegistrationGroupsForCluster(defaultCluster.getId(), ContextUtils.createDefaultContextInfo());

            //build and set ActivityOfferingClusterWrapper
            ActivityOfferingClusterWrapper aoClusterWrapper = CourseOfferingControllerUtil._buildAOClusterWrapper (defaultCluster, theForm,0);
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
            CourseOfferingControllerUtil.getCourseOfferingService().deleteActivityOfferingCluster(defaultCluster.getId(), ContextUtils.createDefaultContextInfo());
            GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_MESSAGES, RegistrationGroupConstants.MSG_ERROR_INVALID_AOLIST);
        }
        return getUIFModelAndView(theForm);
    }

    /*
    *  Move unassigned FO(s) to one of the existing clusters
    */
    @RequestMapping(params = "methodToCall=moveAOToACluster")
    public ModelAndView moveAOToACluster (@ModelAttribute("KualiForm") RegistrationGroupManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                          @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {

        if(theForm.getClusterIdIdForNewFO().equals("create-new")){
            theForm.setSelectCreateNewFromDropDown(true);
            return createNewClusterFromLightBox(theForm, result, request, response);
        }
//        theForm.getDialogManager().resetDialogStatus("createNewClusterDialog");
//        theForm.getDialogManager().removeDialog("createNewClusterDialog");
        if (theForm.isSelectCreateNewFromDropDown()){
            //In the previous step, if a user has selected "Create New... " from the drop-down list,
            // the Dialog lightbox has been popped up, the user has input something and then
            // clicked either "Create" or "Cancel" button in the Dialog. The Dialog will be closed,
            // the process will come back to hit this method. Now we need to reset SelectCreateNewFromDropDown
            // boolean to false before invoking createNewClusterFromLightBox method to handle the true work
            // -- validate if the cluster's name is unique and then create one in DB
            theForm.setPrivateClusterName(theForm.getPrivateClusterNameForLightBox());
            theForm.setPublishedClusterName(theForm.getPublishedClusterNameForLightBox());
            createNewClusterFromLightBox(theForm, result, request, response);
            theForm.setSelectCreateNewFromDropDown(false);
        }

        //get selected AOC info
        if (theForm.getClusterIdIdForNewFO().equals("")){
            GlobalVariables.getMessageMap().putError("AOCselectionError", RegistrationGroupConstants.MSG_ERROR_INVALID_CLUSTER_SELECTION);
            return getUIFModelAndView(theForm);
        }
        ActivityOfferingClusterInfo selectedAOCInfo = CourseOfferingControllerUtil.getCourseOfferingService().getActivityOfferingCluster(theForm.getClusterIdIdForNewFO(),ContextUtils.createDefaultContextInfo());

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
        ActivityOfferingClusterInfo updatedSelectedAOCInfo = CourseOfferingControllerUtil.getCourseOfferingService().updateActivityOfferingCluster(theForm.getFormatOfferingIdForViewRG(),
                theForm.getClusterIdIdForNewFO(),
                selectedAOCInfo, ContextUtils.createDefaultContextInfo());
        //update AO list without cluster
        List<ActivityOfferingWrapper> filteredAOs = CourseOfferingControllerUtil.getAOsWithoutClusterForSelectedFO(updatedSelectedAOCInfo.getFormatOfferingId(), theForm);

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
        List<ActivityOfferingInfo> aosInCluster = CourseOfferingControllerUtil.getCourseOfferingService().getActivityOfferingsByCluster(updatedSelectedAOCInfo.getId(), ContextUtils.createDefaultContextInfo());
        for (ActivityOfferingInfo aoInfo : aosInCluster) {
            ActivityOfferingWrapper aoWrapper = CourseOfferingControllerUtil.getViewHelperService(theForm).convertAOInfoToWrapper(aoInfo);
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
            List<RegistrationGroupInfo> rgInfos = CourseOfferingControllerUtil.getCourseOfferingService().getRegistrationGroupsByActivityOfferingCluster(
                    theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster().getId(), ContextUtils.createDefaultContextInfo());
            //Update selected AOC
            if (theForm.getFilteredAOClusterWrapperList().get(i).getActivityOfferingClusterId().equals(updatedSelectedAOCInfo.getId())) {
                //need to update AOCluster in the AOClusterWrapper because it will be used for other operation
                theForm.getFilteredAOClusterWrapperList().get(i).setAoCluster(updatedSelectedAOCInfo);
                theForm.getFilteredAOClusterWrapperList().get(i).setAoWrapperList(filteredClusteredAOs);
                //update RG status
                rgInfos = CourseOfferingControllerUtil.getCourseOfferingService().getRegistrationGroupsByActivityOfferingCluster(updatedSelectedAOCInfo.getId(), ContextUtils.createDefaultContextInfo());
                if (rgInfos.size() > 0) {
                    theForm.getFilteredAOClusterWrapperList().get(i).setHasAllRegGroups(false);
                    theForm.getFilteredAOClusterWrapperList().get(i).setRgStatus(RegistrationGroupConstants.RGSTATUS_SOME_RG_GENERATED);
                    theForm.getFilteredAOClusterWrapperList().get(i).setRgMessageStyle(ActivityOfferingClusterWrapper.RG_MESSAGE_PARTIAL);
                }
            }
            //validate RGs for each cluster and set error msg
            if (rgInfos.size() > 0 && theForm.getFilteredAOClusterWrapperList().get(i).isHasAllRegGroups() ) {
                // perform max enrollment validation
                CourseOfferingControllerUtil._performMaxEnrollmentValidation(theForm.getFormatOfferingIdForViewRG(), theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster(), i);
                //validate AO time conflict in RG
                CourseOfferingControllerUtil._performRGTimeConflictValidation(theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster(), rgInfos, i);
            }
        }

        theForm.setClusterIdIdForNewFO("");
        //return updated form
        return getUIFModelAndView(theForm);
    }

    /*
    *  Move assigned FO(s) between clusters
    */
    @RequestMapping(params = "methodToCall=moveAOBetweenClusters")
    public ModelAndView moveAOBetweenClusters (@ModelAttribute("KualiForm") RegistrationGroupManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                               @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        //set clusterIndex for selected/from cluster
        int clusterIndex;
        String selectedCollectionPath = theForm.getActionParamaterValue(UifParameters.SELLECTED_COLLECTION_PATH);
        clusterIndex = Integer.parseInt(StringUtils.substringBetween(selectedCollectionPath,"filteredAOClusterWrapperList[","]"));

        List<ActivityOfferingClusterWrapper> aocWrapperList = theForm.getFilteredAOClusterWrapperList();
        ActivityOfferingClusterInfo selectedAOCInfoTo = new ActivityOfferingClusterInfo();
        ActivityOfferingClusterInfo selectedAOCInfoFrom = aocWrapperList.get(clusterIndex).getAoCluster();

        //get selected TO AOC info
        if (theForm.getClusterIdForAOMove() != null && !theForm.getClusterIdForAOMove().equals("") &&
                theForm.getClusterIdForAOMove().length() > 0  ) {
            String aocId;
            if (theForm.getClusterIdForAOMove().contains(",") ) {
                //strip off "," - not sure why commas are between IDs in the keyValue finder in the footer?
                aocId = theForm.getClusterIdForAOMove().replace(",", "");
            } else {
                aocId = theForm.getClusterIdForAOMove();
            }
            if (aocId.equals("")) {
                GlobalVariables.getMessageMap().putErrorForSectionId("activityOfferingsPerCluster_line"+clusterIndex, RegistrationGroupConstants.MSG_ERROR_INVALID_CLUSTER_SELECTION);
                return getUIFModelAndView(theForm);
            }
            selectedAOCInfoTo = CourseOfferingControllerUtil.getCourseOfferingService().getActivityOfferingCluster(aocId, ContextUtils.createDefaultContextInfo());
        }

        //check if valid selectedAOCInfoTo is selected
        if (selectedAOCInfoTo.getId() == null || selectedAOCInfoTo.getId().equals("") ) {
            GlobalVariables.getMessageMap().putErrorForSectionId("activityOfferingsPerCluster_line"+clusterIndex, RegistrationGroupConstants.MSG_ERROR_INVALID_CLUSTER_SELECTION);
            return getUIFModelAndView(theForm);
        }

        //get FOs and add them to the selected AOC
        boolean aoChecked = false; //at least one AO must be checked
        ActivityOfferingClusterWrapper aocWreapperFrom = aocWrapperList.get(clusterIndex);
        for (ActivityOfferingWrapper aoWrapper : aocWreapperFrom.getAoWrapperList()) {
            if (aoWrapper.getIsChecked()) {
                aoChecked = true;
                //selectedAOCInfoFrom and selectedAOCInfoTo clusters have to be different and only one selectedAOCInfoFrom cluster is allowed at this point
                if (aocWreapperFrom.getActivityOfferingClusterId().equals(selectedAOCInfoTo.getId())) {
                    GlobalVariables.getMessageMap().putErrorForSectionId("activityOfferingsPerCluster_line"+clusterIndex, RegistrationGroupConstants.MSG_ERROR_INVALID_CLUSTER_SELECTION);
                    return getUIFModelAndView(theForm);
                }

                //delete all RGs for AO being moved
                List<RegistrationGroupInfo> rgInfoList =
                        CourseOfferingControllerUtil.getCourseOfferingService().getRegistrationGroupsByActivityOfferingCluster(selectedAOCInfoFrom.getId(),ContextUtils.createDefaultContextInfo());
                if (rgInfoList.size() > 0) {
                    for (RegistrationGroupInfo rgInfo :rgInfoList) {
                        for (String aoId : rgInfo.getActivityOfferingIds()) {
                            if (aoWrapper.getAoInfo().getId().equals(aoId)) {
                                CourseOfferingControllerUtil.getCourseOfferingService().deleteRegistrationGroup(rgInfo.getId(),ContextUtils.createDefaultContextInfo());
                            }
                        }
                    }
                }

                //add AO to selectedAOCInfoTo
                for (int i=0; i < selectedAOCInfoTo.getActivityOfferingSets().size(); i++) {
                    if (selectedAOCInfoTo.getActivityOfferingSets().get(i).getActivityOfferingType().equals(aoWrapper.getAoInfo().getTypeKey()) ) {
                        selectedAOCInfoTo.getActivityOfferingSets().get(i).getActivityOfferingIds().add(aoWrapper.getAoInfo().getId());
                        break;
                    }
                }
                //remove AO from selectedAOCInfoFrom
                for (int i=0; i < selectedAOCInfoFrom.getActivityOfferingSets().size(); i++) {
                    if (selectedAOCInfoFrom.getActivityOfferingSets().get(i).getActivityOfferingType().equals(aoWrapper.getAoInfo().getTypeKey()) ) {
                        selectedAOCInfoFrom.getActivityOfferingSets().get(i).getActivityOfferingIds().remove(aoWrapper.getAoInfo().getId());
                        break;
                    }
                }
            }
        }
        //throw error if nothing is checked
        if (!aoChecked) {
            GlobalVariables.getMessageMap().putErrorForSectionId("activityOfferingsPerCluster_line"+clusterIndex, RegistrationGroupConstants.MSG_ERROR_INVALID_AO_SELECTION);
            return getUIFModelAndView(theForm);
        }

        //persist selected AOCs for update
        ActivityOfferingClusterInfo updatedSelectedAOCInfoTo = CourseOfferingControllerUtil.getCourseOfferingService().updateActivityOfferingCluster(theForm.getFormatOfferingIdForViewRG(),
                selectedAOCInfoTo.getId(),
                selectedAOCInfoTo, ContextUtils.createDefaultContextInfo());

        ActivityOfferingClusterInfo updatedSelectedAOCInfoFrom = CourseOfferingControllerUtil.getCourseOfferingService().updateActivityOfferingCluster(theForm.getFormatOfferingIdForViewRG(),
                selectedAOCInfoFrom.getId(),
                selectedAOCInfoFrom, ContextUtils.createDefaultContextInfo());

        //UPDATE theForm
        List<ActivityOfferingWrapper> filteredClusteredAOsTo = new ArrayList <ActivityOfferingWrapper>();
        List<ActivityOfferingWrapper> filteredClusteredAOsFrom = new ArrayList <ActivityOfferingWrapper>();

        //collect aoInfo(s) belonging to updatedSelectedAOCInfoFrom, convert them to AOWrapper(s) and store them in filteredClusteredAOsFrom list for updating theForm below
        List<ActivityOfferingInfo> aosInClusterFrom = CourseOfferingControllerUtil.getCourseOfferingService().getActivityOfferingsByCluster(updatedSelectedAOCInfoFrom.getId(), ContextUtils.createDefaultContextInfo());
        for ( ActivityOfferingInfo aoInfo : aosInClusterFrom) {
            ActivityOfferingWrapper aoWrapper = CourseOfferingControllerUtil.getViewHelperService(theForm).convertAOInfoToWrapper(aoInfo);
            String cssClass = (aoInfo.getScheduleId() == null ? "uif-scheduled-dl" : "uif-actual-dl");
            aoWrapper.setDaysDisplayName(aoWrapper.getDaysDisplayName(), false, cssClass);
            aoWrapper.setStartTimeDisplay(aoWrapper.getStartTimeDisplay(), false, cssClass);
            aoWrapper.setEndTimeDisplay(aoWrapper.getEndTimeDisplay(), false, cssClass);
            aoWrapper.setBuildingName(aoWrapper.getBuildingName(), false, cssClass);
            aoWrapper.setRoomName(aoWrapper.getRoomName(), false, cssClass);

            filteredClusteredAOsFrom.add(aoWrapper);
        }

        //collect aoInfo(s) belonging to updatedSelectedAOCInfoTo, convert them to AOWrapper(s) and store them in filteredClusteredAOsTo list for updating theForm below
        List<ActivityOfferingInfo> aosInClusterTo = CourseOfferingControllerUtil.getCourseOfferingService().getActivityOfferingsByCluster(updatedSelectedAOCInfoTo.getId(), ContextUtils.createDefaultContextInfo());
        for (ActivityOfferingInfo aoInfo : aosInClusterTo) {
            ActivityOfferingWrapper aoWrapper = CourseOfferingControllerUtil.getViewHelperService(theForm).convertAOInfoToWrapper(aoInfo);
            String cssClass = (aoInfo.getScheduleId() == null ? "uif-scheduled-dl" : "uif-actual-dl");
            aoWrapper.setDaysDisplayName(aoWrapper.getDaysDisplayName(), false, cssClass);
            aoWrapper.setStartTimeDisplay(aoWrapper.getStartTimeDisplay(), false, cssClass);
            aoWrapper.setEndTimeDisplay(aoWrapper.getEndTimeDisplay(), false, cssClass);
            aoWrapper.setBuildingName(aoWrapper.getBuildingName(), false, cssClass);
            aoWrapper.setRoomName(aoWrapper.getRoomName(), false, cssClass);

            filteredClusteredAOsTo.add(aoWrapper);

        }

        for (int i = 0; i < theForm.getFilteredAOClusterWrapperList().size(); i++) {
            //Collect RGs for each cluster
            List<RegistrationGroupInfo> rgInfos = CourseOfferingControllerUtil.getCourseOfferingService().getRegistrationGroupsByActivityOfferingCluster(
                    theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster().getId(), ContextUtils.createDefaultContextInfo());

            //find the relevant AOCWrapperTo in theForm and update it
            if (theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster().getId().equals(updatedSelectedAOCInfoTo.getId())) {
                theForm.getFilteredAOClusterWrapperList().get(i).setAoCluster(updatedSelectedAOCInfoTo);
                theForm.getFilteredAOClusterWrapperList().get(i).setAoWrapperList(filteredClusteredAOsTo);
                //if there are RGS belonging to updatedSelectedAOCInfoTo - set RG status (logic in RegistrationGroupInfo)
                if (rgInfos.size() > 0) {
                    theForm.getFilteredAOClusterWrapperList().get(i).setHasAllRegGroups(false);
                    theForm.getFilteredAOClusterWrapperList().get(i).setRgStatus(RegistrationGroupConstants.RGSTATUS_SOME_RG_GENERATED);
                    theForm.getFilteredAOClusterWrapperList().get(i).setRgMessageStyle(ActivityOfferingClusterWrapper.RG_MESSAGE_PARTIAL);
                }
            }

            //find the relevant AOCWrapperFrom in theForm and update it
            if (theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster().getId().equals(updatedSelectedAOCInfoFrom.getId())) {
                theForm.getFilteredAOClusterWrapperList().get(i).setAoCluster(updatedSelectedAOCInfoFrom);
                theForm.getFilteredAOClusterWrapperList().get(i).setAoWrapperList(filteredClusteredAOsFrom);


                //if there are RGS belonging to updatedSelectedAOCInfoFrom - set RG status (logic in RegistrationGroupInfo)
                if (rgInfos.size() > 0) {
                    //check if all the RGs have been generated for an AOC
                    Set<List<String>> missingRegGroupAoSets = PermutationCounter.computeMissingRegGroupAoIdsInCluster(theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster(), rgInfos);
                    if (missingRegGroupAoSets != null && !missingRegGroupAoSets.isEmpty()) {
                        theForm.getFilteredAOClusterWrapperList().get(i).setHasAllRegGroups(false);
                        theForm.getFilteredAOClusterWrapperList().get(i).setRgStatus(RegistrationGroupConstants.RGSTATUS_SOME_RG_GENERATED);
                        theForm.getFilteredAOClusterWrapperList().get(i).setRgMessageStyle(ActivityOfferingClusterWrapper.RG_MESSAGE_PARTIAL);
                    } else {
                        theForm.getFilteredAOClusterWrapperList().get(i).setHasAllRegGroups(true);
                        theForm.getFilteredAOClusterWrapperList().get(i).setRgStatus(RegistrationGroupConstants.RGSTATUS_ALL_RG_GENERATED);
                        theForm.getFilteredAOClusterWrapperList().get(i).setRgMessageStyle(ActivityOfferingClusterWrapper.RG_MESSAGE_ALL);
                    }

                    //update theForm with RGs belonging to updatedSelectedAOCInfoFrom
                    List<RegistrationGroupWrapper> filteredRGs = CourseOfferingControllerUtil._getRGsForSelectedFO(rgInfos, theForm.getFilteredAOClusterWrapperList().get(i).getAoWrapperList());
                    theForm.getFilteredAOClusterWrapperList().get(i).setRgWrapperList(filteredRGs);
                } else {
                    theForm.getFilteredAOClusterWrapperList().get(i).setHasAllRegGroups(false);
                    theForm.getFilteredAOClusterWrapperList().get(i).setRgStatus(RegistrationGroupConstants.RGSTATUS_NO_RG_GENERATED);
                    theForm.getFilteredAOClusterWrapperList().get(i).setRgMessageStyle(ActivityOfferingClusterWrapper.RG_MESSAGE_NONE);
                }
            }
            //validate RGs for each cluster and set error msg
            if (rgInfos.size() > 0 && theForm.getFilteredAOClusterWrapperList().get(i).isHasAllRegGroups() ) {
                // perform max enrollment validation
                CourseOfferingControllerUtil._performMaxEnrollmentValidation(theForm.getFormatOfferingIdForViewRG(), theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster(), i);
                //validate AO time conflict in RG
                CourseOfferingControllerUtil._performRGTimeConflictValidation(theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster(), rgInfos, i);
            }
        }
        //return updated form
        return getUIFModelAndView(theForm);
    }

}
