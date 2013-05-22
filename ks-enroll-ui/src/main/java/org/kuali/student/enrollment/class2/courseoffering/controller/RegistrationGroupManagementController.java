package org.kuali.student.enrollment.class2.courseoffering.controller;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.UifParameters;
import org.kuali.rice.krad.uif.util.ObjectPropertyUtils;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingClusterWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.RegistrationGroupWrapper;
import org.kuali.student.enrollment.class2.courseoffering.form.RegistrationGroupManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.service.RegistrationGroupManagementViewHelperService;
import org.kuali.student.enrollment.class2.courseoffering.service.util.RegistrationGroupUtil;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.class2.courseoffering.util.RegistrationGroupConstants;
import org.kuali.student.enrollment.courseoffering.dto.*;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.uif.util.KSUifUtils;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.permutation.PermutationCounter;
import org.kuali.student.r2.common.permutation.PermutationUtils;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.class1.state.service.StateService;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping(value = "/registrationGroupManagement")
public class RegistrationGroupManagementController extends UifControllerBase {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(RegistrationGroupManagementController.class);
    private TypeService typeService;
    private StateService stateService;
    private RegistrationGroupManagementViewHelperService viewHelperService;

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
            CourseOfferingInfo theCourseOffering = getCourseOfferingService().getCourseOffering(courseOfferingId, ContextUtils.createDefaultContextInfo());
            theForm.setTheCourseOffering(theCourseOffering);
            List<FormatOfferingInfo> formatOfferingList =
                getCourseOfferingService().getFormatOfferingsByCourseOffering(courseOfferingId, ContextUtils.createDefaultContextInfo());
            theForm.setFormatOfferingName(formatOfferingList.get(0).getName());
            theForm.setFormatOfferingIdForViewRG(formatOfferingList.get(0).getId());
            //get unassgined AOs (didn't belong to any cluster)
            List<ActivityOfferingWrapper> filteredAOs = getAOsWithoutClusterForSelectedFO(formatOfferingList.get(0).getId(), theForm);
    
            for (ActivityOfferingWrapper aoWrapper : filteredAOs) {
//  TODOSSR              String cssClass = (aoWrapper.getAoInfo().getScheduleId() == null ? "uif-scheduled-dl" : "uif-actual-dl");
//                aoWrapper.setDaysDisplayName(aoWrapper.getDaysDisplayName(), false, cssClass);
//                aoWrapper.setStartTimeDisplay(aoWrapper.getStartTimeDisplay(), false, cssClass);
//                aoWrapper.setEndTimeDisplay(aoWrapper.getEndTimeDisplay(), false, cssClass);
//                aoWrapper.setBuildingName(aoWrapper.getBuildingName(), false, cssClass);
//                aoWrapper.setRoomName(aoWrapper.getRoomName(), false, cssClass);
            }
            theForm.setFilteredUnassignedAOsForSelectedFO(filteredAOs);
    
            //get clusters if any for the 1st FO
            List<ActivityOfferingClusterInfo> aoClusters = getCourseOfferingService().getActivityOfferingClustersByFormatOffering(formatOfferingList.get(0).getId(), ContextUtils.createDefaultContextInfo());
            if (aoClusters == null || aoClusters.size()==0){
                theForm.setHasAOCluster(false);
            }
            else {
                theForm.setHasAOCluster(true);
                List <ActivityOfferingClusterWrapper> aoClusterWrappers = _convertToAOClusterWrappers(aoClusters, theForm);
                theForm.setFilteredAOClusterWrapperList(aoClusterWrappers);
            }
        }catch (Exception e){
            LOG.error("Got exception when loading 'Manage Registration Groups'", e);
        }

        //populate the previousFormsMap of the form. The map contains info about the previous view to generate customized breadcrumb
        KSUifUtils.populationPreviousFormsMap(request, theForm);

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
            FormatOfferingInfo selectedFO = getCourseOfferingService().getFormatOffering(selectedFOId, ContextUtils.createDefaultContextInfo());
            theForm.setFormatOfferingName(selectedFO.getName());
        }

        //get unassgined AOs (didn't belong to any cluster)
        filteredAOs = getAOsWithoutClusterForSelectedFO(theForm.getFormatOfferingIdForViewRG(), theForm);
        for (ActivityOfferingWrapper aoWrapper : filteredAOs) {
//TODOSSR            String cssClass = (aoWrapper.getAoInfo().getScheduleId() == null ? "uif-scheduled-dl" : "uif-actual-dl");
//            aoWrapper.setDaysDisplayName(aoWrapper.getDaysDisplayName(), false, cssClass);
//            aoWrapper.setStartTimeDisplay(aoWrapper.getStartTimeDisplay(), false, cssClass);
//            aoWrapper.setEndTimeDisplay(aoWrapper.getEndTimeDisplay(), false, cssClass);
//            aoWrapper.setBuildingName(aoWrapper.getBuildingName(), false, cssClass);
//            aoWrapper.setRoomName(aoWrapper.getRoomName(), false, cssClass);
        }
        theForm.setFilteredUnassignedAOsForSelectedFO(filteredAOs);

        //get clusters if any for the selected FO
        List<ActivityOfferingClusterInfo> aoClusters = getCourseOfferingService().getActivityOfferingClustersByFormatOffering(selectedFOId, ContextUtils.createDefaultContextInfo());
        if (aoClusters == null || aoClusters.size()==0){
            theForm.setHasAOCluster(false);
        }
        else {
            theForm.setHasAOCluster(true);
            List <ActivityOfferingClusterWrapper> aoClusterWrappers = _convertToAOClusterWrappers(aoClusters, theForm);
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
                if (_isClusterUnique(formatOfferingId, theForm.getPrivateClusterNameForLightBox())){
                    //build a new empty cluster
                    ActivityOfferingClusterInfo emptyCluster = _buildEmptyAOCluster(formatOfferingId,
                            theForm.getPrivateClusterNameForLightBox(), theForm.getPublishedClusterNameForLightBox());

                    //persist it in DB , comment out for now since it does not work for now
                    emptyCluster = getCourseOfferingService().createActivityOfferingCluster(formatOfferingId,
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
        if (_isClusterUnique(formatOfferingId, theForm.getPrivateClusterName())){
            //build a new empty cluster
            ActivityOfferingClusterInfo emptyCluster = _buildEmptyAOCluster(formatOfferingId,
                    theForm.getPrivateClusterName(), theForm.getPrivateClusterName());

            //persist it in DB , comment out for now since it does not work for now
            emptyCluster = getCourseOfferingService().createActivityOfferingCluster(formatOfferingId,
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
            selectedClusterWrapper = (ActivityOfferingClusterWrapper)_getSelectedObject(theForm, "Rename Cluster");
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
                if (theForm.getSelectedCluster().getAoCluster().getPrivateName().equalsIgnoreCase(theForm.getPrivateClusterNameForRename()) || _isClusterUnique(theForm.getFormatOfferingIdForViewRG(), theForm.getPrivateClusterNameForRename())){
                    ActivityOfferingClusterInfo  aoCluster = selectedClusterWrapper.getAoCluster();
                    aoCluster.setName(theForm.getPublishedClusterNameForRename());
                    aoCluster.setPrivateName(theForm.getPrivateClusterNameForRename());
                    aoCluster = getCourseOfferingService().updateActivityOfferingCluster(theForm.getFormatOfferingIdForViewRG(),
                            aoCluster.getId(), aoCluster, ContextUtils.createDefaultContextInfo());
                    selectedClusterWrapper.setAoCluster(aoCluster);
                    selectedClusterWrapper.setClusterNameForDisplay("Forget to set cluster?");

                    //After rename an AOC, reload the main RG screen to correctly display the warning messages
                    Properties urlParameters = new Properties();
                    urlParameters.put(KRADConstants.DISPATCH_REQUEST_PARAMETER, KRADConstants.START_METHOD);
                    urlParameters.put("coInfo.id", theForm.getTheCourseOffering().getId());
                    urlParameters.put(UifParameters.VIEW_ID, RegistrationGroupConstants.RG_VIEW);
                    urlParameters.put(UifConstants.UrlParams.SHOW_HOME, BooleanUtils.toStringTrueFalse(false));
                    urlParameters.put("withinPortal", BooleanUtils.toStringTrueFalse(theForm.isWithinPortal()));
                    String controllerPath = RegistrationGroupConstants.RG_CONTROLLER_PATH;
                    return super.performRedirect(theForm,controllerPath, urlParameters);
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
        ActivityOfferingWrapper selectedAOWrapper =(ActivityOfferingWrapper)_getSelectedObject(theForm, "Remove");
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
                TypeInfo typeInfo = getTypeService().getType(selectedAOWrapper.getAoInfo().getTypeKey(), ContextUtils.createDefaultContextInfo());
                selectedAOWrapper.setTypeKey(typeInfo.getKey());
                selectedAOWrapper.setTypeName(typeInfo.getName());
                aoTypeKey = typeInfo.getKey();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        //try to retrieve the accurate AOCluster from DB always
        ActivityOfferingClusterInfo aoCluster = getCourseOfferingService().getActivityOfferingCluster(
                theClusterWrapper.getAoCluster().getId(), ContextUtils.createDefaultContextInfo());
        List <ActivityOfferingSetInfo> aoSetList = aoCluster.getActivityOfferingSets();
        for (ActivityOfferingSetInfo aoSet:aoSetList) {
            if (aoTypeKey.equalsIgnoreCase(aoSet.getActivityOfferingType())) {
                aoSet.getActivityOfferingIds().remove(selectedAOWrapper.getAoInfo().getId());
                break;
            }
        }
        aoCluster = getCourseOfferingService().updateActivityOfferingCluster(theForm.getFormatOfferingIdForViewRG(),
                aoCluster.getId(), aoCluster, ContextUtils.createDefaultContextInfo());
        theClusterWrapper.setAoCluster(aoCluster);

        List<RegistrationGroupInfo> rgInfos =getCourseOfferingService().getRegistrationGroupsByActivityOfferingCluster(aoCluster.getId(), ContextUtils.createDefaultContextInfo());
        List<RegistrationGroupWrapper> filteredRGs = _getRGsForSelectedFO(rgInfos, theClusterWrapper.getAoWrapperList());
        theClusterWrapper.setRgWrapperList(filteredRGs);
        if (rgInfos.size() > 0) {
            theClusterWrapper.setRgStatus(RegistrationGroupConstants.RGSTATUS_ALL_RG_GENERATED);
            theClusterWrapper.setRgMessageStyle(ActivityOfferingClusterWrapper.RG_MESSAGE_ALL);
            theClusterWrapper.setHasAllRegGroups(true);
        }
        for (int i = 0; i < theForm.getFilteredAOClusterWrapperList().size(); i++) {
            rgInfos = getCourseOfferingService().getRegistrationGroupsByActivityOfferingCluster(theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster().getId(),
                    ContextUtils.createDefaultContextInfo());
            //validate RGs for each cluster and set error msg
            if (rgInfos.size() > 0 && theForm.getFilteredAOClusterWrapperList().get(i).isHasAllRegGroups() ) {
                // perform max enrollment validation
                _performMaxEnrollmentValidation(theForm.getFormatOfferingIdForViewRG(), theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster(), i);
                //validate AO time conflict in RG
                _performRGTimeConflictValidation(theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster(), rgInfos, i);
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
            selectedCluster = (ActivityOfferingClusterWrapper)_getSelectedObject(theForm, "Remove Cluster through Dialog");
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
                getCourseOfferingService().deleteActivityOfferingClusterCascaded(selectedCluster.getActivityOfferingClusterId(), ContextUtils.createDefaultContextInfo());
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

        ActivityOfferingClusterWrapper selectedCluster = (ActivityOfferingClusterWrapper)_getSelectedObject(theForm, "Remove Cluster");
        //first need to move all AOs under the selected Cluster back to filteredUnassignedAOsForSelectedFO
        List<ActivityOfferingWrapper> unassignedAOs = theForm.getFilteredUnassignedAOsForSelectedFO();
        List<ActivityOfferingWrapper> toBeRemovedAOs = selectedCluster.getAoWrapperList();
        for(ActivityOfferingWrapper aoWrapper:toBeRemovedAOs){
            unassignedAOs.add(aoWrapper);
        }
        theForm.setFilteredUnassignedAOsForSelectedFO(unassignedAOs);
        //then delete the selected cluster
        getCourseOfferingService().deleteActivityOfferingClusterCascaded(selectedCluster.getActivityOfferingClusterId(), ContextUtils.createDefaultContextInfo());
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
        ActivityOfferingClusterWrapper selectedClusterWrapper = (ActivityOfferingClusterWrapper)_getSelectedObject(theForm, "Generate Registration Groups");
        String selectedClusterId = selectedClusterWrapper.getAoCluster().getId();
        AOClusterVerifyResultsInfo aoClusterVerifyResultsInfo = getCourseOfferingService().verifyActivityOfferingClusterForGeneration(selectedClusterId,ContextUtils.createDefaultContextInfo());
        if (!aoClusterVerifyResultsInfo.getValidationResults().get(0).isWarn())  {
            getCourseOfferingService().generateRegistrationGroupsForCluster(selectedClusterId, ContextUtils.createDefaultContextInfo());
            List<RegistrationGroupInfo> rgInfos = getCourseOfferingService().getRegistrationGroupsByActivityOfferingCluster(selectedClusterId, ContextUtils.createDefaultContextInfo());
            if (rgInfos.size() > 0) {
                //build RGWrapperList and set it to selectedClusterWrapper

                String clusterIndex = theForm.getActionParamaterValue(UifParameters.SELECTED_LINE_INDEX);
                // perform max enrollment validation
                _performMaxEnrollmentValidation(theForm.getFormatOfferingIdForViewRG(), selectedClusterWrapper.getAoCluster(), Integer.parseInt(clusterIndex));
                //validate AO time conflict in RG
                _performRGTimeConflictValidation(selectedClusterWrapper.getAoCluster(), rgInfos, Integer.parseInt(clusterIndex));

                List<RegistrationGroupWrapper> rgWrapperListPerCluster = _getRGsForSelectedFO(rgInfos, selectedClusterWrapper.getAoWrapperList());
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
            List<RegistrationGroupInfo> rgInfos = getCourseOfferingService().getRegistrationGroupsByActivityOfferingCluster(
                    theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster().getId(), ContextUtils.createDefaultContextInfo());
            // validate RGs for each cluster and set error msg
            if (rgInfos.size() > 0 && theForm.getFilteredAOClusterWrapperList().get(i).isHasAllRegGroups() ) {
                // perform max enrollment validation
                _performMaxEnrollmentValidation(theForm.getFormatOfferingIdForViewRG(), theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster(), i);
                //validate AO time conflict in RG
                _performRGTimeConflictValidation(theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster(), rgInfos, i);
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
            AOClusterVerifyResultsInfo aoClusterVerifyResultsInfo = getCourseOfferingService().verifyActivityOfferingClusterForGeneration(selectedClusterId,ContextUtils.createDefaultContextInfo());
            List<RegistrationGroupInfo> rgInfos = getCourseOfferingService().getRegistrationGroupsByActivityOfferingCluster(selectedClusterId, ContextUtils.createDefaultContextInfo());
            //only generate for valid AOCs without RGs or partial RGs
            if (!aoClusterVerifyResultsInfo.getValidationResults().get(0).isWarn() && !theForm.getFilteredAOClusterWrapperList().get(i).isHasAllRegGroups())  {
                getCourseOfferingService().generateRegistrationGroupsForCluster(selectedClusterId, ContextUtils.createDefaultContextInfo());
                rgInfos = getCourseOfferingService().getRegistrationGroupsByActivityOfferingCluster(selectedClusterId, ContextUtils.createDefaultContextInfo());
                if (rgInfos.size() > 0 ) {
                    //build RGWrapperList and set it to selectedClusterWrapper
                    List<RegistrationGroupWrapper> rgWrapperListPerCluster = _getRGsForSelectedFO(rgInfos, theForm.getFilteredAOClusterWrapperList().get(i).getAoWrapperList());
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
                _performMaxEnrollmentValidation(theForm.getFormatOfferingIdForViewRG(), theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster(), i);
                //validate AO time conflict in RG
                _performRGTimeConflictValidation(theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster(), rgInfos, i);
            }
        }

        return getUIFModelAndView(theForm);
    }

    private void _fixAoIdOrderingInRegGroups(List<RegistrationGroupInfo> rgInfos)
            throws InvalidParameterException, MissingParameterException, DoesNotExistException,
            OperationFailedException, PermissionDeniedException {

        for (RegistrationGroupInfo regGroup: rgInfos) {
            Map<String, String> aoIdsToAoTypes =
                    RegistrationGroupUtil.createAoIdsToAoTypesMap(regGroup.getActivityOfferingIds(), getCourseOfferingService(), ContextUtils.createDefaultContextInfo());
            RegistrationGroupUtil.orderActivityOfferingIdsInRegistrationGroup(regGroup, aoIdsToAoTypes);
        }
    }

    @RequestMapping(params = "methodToCall=generateUnconstrainedRegGroups")
    public ModelAndView generateUnconstrainedRegGroups (@ModelAttribute("KualiForm") RegistrationGroupManagementForm theForm, @SuppressWarnings("unused") BindingResult result,
                                                        @SuppressWarnings("unused") HttpServletRequest request, @SuppressWarnings("unused") HttpServletResponse response) throws Exception {
        String formatOfferingId = theForm.getFormatOfferingIdForViewRG();

        //new implementation for M5
        //first, build a new default cluster
        ActivityOfferingClusterInfo defaultCluster = _buildDefaultAOCluster(formatOfferingId, theForm);


        AOClusterVerifyResultsInfo aoClusterVerifyResultsInfo = getCourseOfferingService().
                verifyActivityOfferingClusterForGeneration(defaultCluster.getId(),ContextUtils.createDefaultContextInfo());
        if (!aoClusterVerifyResultsInfo.getValidationResults().get(0).isWarn())  {
            //now create RGs for the default cluster
            getCourseOfferingService().generateRegistrationGroupsForCluster(defaultCluster.getId(), ContextUtils.createDefaultContextInfo());

            //build and set ActivityOfferingClusterWrapper
            ActivityOfferingClusterWrapper aoClusterWrapper = _buildAOClusterWrapper (defaultCluster, theForm,0);
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
            getCourseOfferingService().deleteActivityOfferingCluster(defaultCluster.getId(), ContextUtils.createDefaultContextInfo());
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
//  TODOSSR          String cssClass = (aoWrapper.getAoInfo().getScheduleId() == null ? "uif-scheduled-dl" : "uif-actual-dl");
//            aoWrapper.setDaysDisplayName(aoWrapper.getDaysDisplayName(), false, cssClass);
//            aoWrapper.setStartTimeDisplay(aoWrapper.getStartTimeDisplay(), false, cssClass);
//            aoWrapper.setEndTimeDisplay(aoWrapper.getEndTimeDisplay(), false, cssClass);
//            aoWrapper.setBuildingName(aoWrapper.getBuildingName(), false, cssClass);
//            aoWrapper.setRoomName(aoWrapper.getRoomName(), false, cssClass);
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
//TODOSSR            String cssClass = (aoInfo.getScheduleId() == null ? "uif-scheduled-dl" : "uif-actual-dl");
//            aoWrapper.setDaysDisplayName(aoWrapper.getDaysDisplayName(), false, cssClass);
//            aoWrapper.setStartTimeDisplay(aoWrapper.getStartTimeDisplay(), false, cssClass);
//            aoWrapper.setEndTimeDisplay(aoWrapper.getEndTimeDisplay(), false, cssClass);
//            aoWrapper.setBuildingName(aoWrapper.getBuildingName(), false, cssClass);
//            aoWrapper.setRoomName(aoWrapper.getRoomName(), false, cssClass);

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
            selectedAOCInfoTo = getCourseOfferingService().getActivityOfferingCluster(aocId, ContextUtils.createDefaultContextInfo());
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
                        getCourseOfferingService().getRegistrationGroupsByActivityOfferingCluster(selectedAOCInfoFrom.getId(),ContextUtils.createDefaultContextInfo());
                if (rgInfoList.size() > 0) {
                    for (RegistrationGroupInfo rgInfo :rgInfoList) {
                        for (String aoId : rgInfo.getActivityOfferingIds()) {
                            if (aoWrapper.getAoInfo().getId().equals(aoId)) {
                                getCourseOfferingService().deleteRegistrationGroup(rgInfo.getId(),ContextUtils.createDefaultContextInfo());
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
        ActivityOfferingClusterInfo updatedSelectedAOCInfoTo = getCourseOfferingService().updateActivityOfferingCluster(theForm.getFormatOfferingIdForViewRG(),
                selectedAOCInfoTo.getId(),
                selectedAOCInfoTo, ContextUtils.createDefaultContextInfo());

        ActivityOfferingClusterInfo updatedSelectedAOCInfoFrom = getCourseOfferingService().updateActivityOfferingCluster(theForm.getFormatOfferingIdForViewRG(),
                selectedAOCInfoFrom.getId(),
                selectedAOCInfoFrom, ContextUtils.createDefaultContextInfo());

        //UPDATE theForm
        List<ActivityOfferingWrapper> filteredClusteredAOsTo = new ArrayList <ActivityOfferingWrapper>();
        List<ActivityOfferingWrapper> filteredClusteredAOsFrom = new ArrayList <ActivityOfferingWrapper>();

        //collect aoInfo(s) belonging to updatedSelectedAOCInfoFrom, convert them to AOWrapper(s) and store them in filteredClusteredAOsFrom list for updating theForm below
        List<ActivityOfferingInfo> aosInClusterFrom = getCourseOfferingService().getActivityOfferingsByCluster(updatedSelectedAOCInfoFrom.getId(), ContextUtils.createDefaultContextInfo());
        for ( ActivityOfferingInfo aoInfo : aosInClusterFrom) {
            ActivityOfferingWrapper aoWrapper = getViewHelperService(theForm).convertAOInfoToWrapper(aoInfo);
// TODOSSR           String cssClass = (aoInfo.getScheduleId() == null ? "uif-scheduled-dl" : "uif-actual-dl");
//            aoWrapper.setDaysDisplayName(aoWrapper.getDaysDisplayName(), false, cssClass);
//            aoWrapper.setStartTimeDisplay(aoWrapper.getStartTimeDisplay(), false, cssClass);
//            aoWrapper.setEndTimeDisplay(aoWrapper.getEndTimeDisplay(), false, cssClass);
//            aoWrapper.setBuildingName(aoWrapper.getBuildingName(), false, cssClass);
//            aoWrapper.setRoomName(aoWrapper.getRoomName(), false, cssClass);

            filteredClusteredAOsFrom.add(aoWrapper);
        }

        //collect aoInfo(s) belonging to updatedSelectedAOCInfoTo, convert them to AOWrapper(s) and store them in filteredClusteredAOsTo list for updating theForm below
        List<ActivityOfferingInfo> aosInClusterTo = getCourseOfferingService().getActivityOfferingsByCluster(updatedSelectedAOCInfoTo.getId(), ContextUtils.createDefaultContextInfo());
        for (ActivityOfferingInfo aoInfo : aosInClusterTo) {
            ActivityOfferingWrapper aoWrapper = getViewHelperService(theForm).convertAOInfoToWrapper(aoInfo);
// TODOSSR           String cssClass = (aoInfo.getScheduleId() == null ? "uif-scheduled-dl" : "uif-actual-dl");
//            aoWrapper.setDaysDisplayName(aoWrapper.getDaysDisplayName(), false, cssClass);
//            aoWrapper.setStartTimeDisplay(aoWrapper.getStartTimeDisplay(), false, cssClass);
//            aoWrapper.setEndTimeDisplay(aoWrapper.getEndTimeDisplay(), false, cssClass);
//            aoWrapper.setBuildingName(aoWrapper.getBuildingName(), false, cssClass);
//            aoWrapper.setRoomName(aoWrapper.getRoomName(), false, cssClass);

            filteredClusteredAOsTo.add(aoWrapper);

        }

        for (int i = 0; i < theForm.getFilteredAOClusterWrapperList().size(); i++) {
            //Collect RGs for each cluster
            List<RegistrationGroupInfo> rgInfos = getCourseOfferingService().getRegistrationGroupsByActivityOfferingCluster(
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
                    List<RegistrationGroupWrapper> filteredRGs = _getRGsForSelectedFO(rgInfos, theForm.getFilteredAOClusterWrapperList().get(i).getAoWrapperList());
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
                _performMaxEnrollmentValidation(theForm.getFormatOfferingIdForViewRG(), theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster(), i);
                //validate AO time conflict in RG
                _performRGTimeConflictValidation(theForm.getFilteredAOClusterWrapperList().get(i).getAoCluster(), rgInfos, i);
            }
        }
        //return updated form
        return getUIFModelAndView(theForm);
    }


    private ActivityOfferingClusterInfo _buildDefaultAOCluster (String formatOfferingId,
                                                                RegistrationGroupManagementForm theForm) throws Exception{
        ActivityOfferingClusterInfo defaultCluster = _buildEmptyAOCluster(formatOfferingId,"Default Cluster", "Default Cluster");
        defaultCluster = getCourseOfferingService().createActivityOfferingCluster(formatOfferingId,
                defaultCluster.getTypeKey(), defaultCluster, ContextUtils.createDefaultContextInfo());
        List<ActivityOfferingWrapper> filteredAOs = theForm.getFilteredUnassignedAOsForSelectedFO();
        defaultCluster = _updateAOSets(filteredAOs,defaultCluster,formatOfferingId);
        return defaultCluster;
    }

    private ActivityOfferingClusterInfo _buildEmptyAOCluster (String formatOfferingId, String privateName, String publishedName){
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

    private  ActivityOfferingClusterInfo _updateAOSets(List<ActivityOfferingWrapper> aoWrapperList,
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

    /*
    * convert List<ActivityOfferingClusterInfo> to List<ActivityOfferingClusterWrapper> and set it to the Form
    */
    private List<ActivityOfferingClusterWrapper> _convertToAOClusterWrappers(List<ActivityOfferingClusterInfo> aoClusterList,
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

    private  ActivityOfferingClusterWrapper _buildAOClusterWrapper (ActivityOfferingClusterInfo aoCluster,
                                                                    RegistrationGroupManagementForm theForm, int clusterIndex) throws Exception{

        ActivityOfferingClusterWrapper aoClusterWrapper = new ActivityOfferingClusterWrapper();
        aoClusterWrapper.setActivityOfferingClusterId(aoCluster.getId());
        aoClusterWrapper.setAoCluster(aoCluster);
        aoClusterWrapper.setClusterNameForDisplay("Forget to set cluster?");

        List<ActivityOfferingInfo> aoInfoList = getCourseOfferingService().getActivityOfferingsByCluster(aoCluster.getId(), ContextUtils.createDefaultContextInfo());
        List<ActivityOfferingWrapper> aoWrapperListPerCluster = new ArrayList<ActivityOfferingWrapper>();
        for(ActivityOfferingInfo aoInfo: aoInfoList){
            ActivityOfferingWrapper aoWrapper = getViewHelperService(theForm).convertAOInfoToWrapper(aoInfo);

// TODOSSR           String cssClass = (aoInfo.getScheduleId() == null ? "uif-scheduled-dl" : "uif-actual-dl");
//            aoWrapper.setDaysDisplayName(aoWrapper.getDaysDisplayName(), false, cssClass);
//            aoWrapper.setStartTimeDisplay(aoWrapper.getStartTimeDisplay(), false, cssClass);
//            aoWrapper.setEndTimeDisplay(aoWrapper.getEndTimeDisplay(), false, cssClass);
//            aoWrapper.setBuildingName(aoWrapper.getBuildingName(), false, cssClass);
//            aoWrapper.setRoomName(aoWrapper.getRoomName(), false, cssClass);

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
    private void _validateRegistrationGroupsPerCluster(List<RegistrationGroupInfo> rgInfos, List<ActivityOfferingInfo> aoList,
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

    private void _performMaxEnrollmentValidation(String formateOfferingId, ActivityOfferingClusterInfo aoCluster, int clusterIndex) throws Exception{
        List<ValidationResultInfo> validationResultInfoList = getCourseOfferingService().validateActivityOfferingCluster(
                DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), formateOfferingId, aoCluster, ContextUtils.createDefaultContextInfo());

        if (validationResultInfoList.get(0).isWarn())  {
            GlobalVariables.getMessageMap().putWarningForSectionId("registrationGroupsPerCluster_line"+clusterIndex, RegistrationGroupConstants.MSG_WARNING_MAX_ENROLLMENT, aoCluster.getPrivateName());
            GlobalVariables.getMessageMap().putWarningForSectionId("activityOfferingsPerCluster_line"+clusterIndex, RegistrationGroupConstants.MSG_WARNING_MAX_ENROLLMENT, aoCluster.getPrivateName());
        }
    }

    private List<Integer> _performRGTimeConflictValidation(ActivityOfferingClusterInfo aoCluster, List<RegistrationGroupInfo> registrationGroupInfos, int clusterIndex) throws Exception{
        List<Integer> rgIndexList = new ArrayList<Integer>();
        rgIndexList.clear();

        if (aoCluster != null && registrationGroupInfos != null && !registrationGroupInfos.isEmpty()) {
            int rgIndex = 0;
            for (RegistrationGroupInfo registrationGroupInfo : registrationGroupInfos) {
                List<ValidationResultInfo> validationResultInfoList = getCourseOfferingService().verifyRegistrationGroup(registrationGroupInfo.getId(), ContextUtils.createDefaultContextInfo());
                if (validationResultInfoList.get(0).isWarn())  {
                    getCourseOfferingService().changeRegistrationGroupState(registrationGroupInfo.getId(), LuiServiceConstants.REGISTRATION_GROUP_INVALID_STATE_KEY, ContextUtils.createDefaultContextInfo());
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

    private Map<String, List<String>> _constructActivityOfferingTypeToAvailableActivityOfferingMap(List<ActivityOfferingInfo> aoList) {
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

    private boolean _hasGeneratedRegGroup(List<String>activityOfferingPermutation, RegistrationGroupInfo rgInfo){
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

    private List<ActivityOfferingWrapper> getAOsWithoutClusterForSelectedFO(String theFOId, RegistrationGroupManagementForm theForm) throws Exception {
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

    private List<RegistrationGroupWrapper> _getRGsForSelectedFO(List<RegistrationGroupInfo> rgInfos, List<ActivityOfferingWrapper> filteredAOs) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
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
// TODOSSR               String cssClass = (filteredAOsHM.get(aoID).getAoInfo().getScheduleId() == null ? "uif-scheduled-dl" : "uif-actual-dl");
//                if (filteredAOsHM.get(aoID).getAoInfo().getActivityCode() != null && !filteredAOsHM.get(aoID).getAoInfo().getActivityCode().equalsIgnoreCase("")) {
//                    aoActivityCodeText = aoActivityCodeText + filteredAOsHM.get(aoID).getAoInfo().getActivityCode() + "<br/>";
//                }
//                if (filteredAOsHM.get(aoID).getStateName() != null && !filteredAOsHM.get(aoID).getStateName().equalsIgnoreCase("")) {
//                    aoStateNameText = aoStateNameText + filteredAOsHM.get(aoID).getStateName() + "<br/>";
//                }
//                if (filteredAOsHM.get(aoID).getTypeName() != null && !filteredAOsHM.get(aoID).getTypeName().equalsIgnoreCase("")) {
//                    aoTypeNameText = aoTypeNameText + filteredAOsHM.get(aoID).getTypeName() + "<br/>";
//                }
//                if (filteredAOsHM.get(aoID).getFirstInstructorDisplayName() != null && !filteredAOsHM.get(aoID).getFirstInstructorDisplayName().equalsIgnoreCase("")) {
//                    aoInstructorText = aoInstructorText + filteredAOsHM.get(aoID).getFirstInstructorDisplayName() + "<br/>";
//                }
//                if (filteredAOsHM.get(aoID).getAoInfo().getMaximumEnrollment() != null) {
//                    aoMaxEnrText = aoMaxEnrText + Integer.toString(filteredAOsHM.get(aoID).getAoInfo().getMaximumEnrollment()) + "<br/>";
//                }
//
//                if(filteredAOsHM.get(aoID).getStartTimeDisplay() != null){
//                    rgWrapper.setStartTimeDisplay(filteredAOsHM.get(aoID).getStartTimeDisplay(), true, cssClass);
//                }
//
//                if(filteredAOsHM.get(aoID).getEndTimeDisplay() != null){
//                    rgWrapper.setEndTimeDisplay(filteredAOsHM.get(aoID).getEndTimeDisplay(), true, cssClass);
//                }
//
//                if(filteredAOsHM.get(aoID).getBuildingName() != null){
//                    rgWrapper.setBuildingName(filteredAOsHM.get(aoID).getBuildingName(), true, cssClass);
//                }
//
//                if(filteredAOsHM.get(aoID).getRoomName() != null){
//                    rgWrapper.setRoomName(filteredAOsHM.get(aoID).getRoomName(), true, cssClass);
//                }
//
//                if(filteredAOsHM.get(aoID).getDaysDisplayName() != null){
//                    rgWrapper.setDaysDisplayName(filteredAOsHM.get(aoID).getDaysDisplayName(), true, cssClass);
//                }
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

    private boolean _isClusterUnique(String formatOfferingId, String privateName) throws Exception{
        //Build up a term search criteria
        QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
        qbcBuilder.setPredicates(PredicateFactory.and(
                PredicateFactory.equalIgnoreCase("privateName", privateName),
                PredicateFactory.equal("formatOfferingId", formatOfferingId)));
        QueryByCriteria criteria = qbcBuilder.build();

        List<ActivityOfferingClusterInfo> aoClusterList = getCourseOfferingService().searchForActivityOfferingClusters(criteria, ContextUtils.createDefaultContextInfo());
        return aoClusterList.size() <= 0;
    }

    private Object _getSelectedObject(RegistrationGroupManagementForm theForm, String actionLink){
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

    public TypeService getTypeService() {
        if(typeService == null) {
            typeService = CourseOfferingResourceLoader.loadTypeService();
        }
        return this.typeService;
    }

    public StateService getStateService() {
        if(stateService == null) {
            stateService = CourseOfferingResourceLoader.loadStateService();
        }
        return this.stateService;
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
