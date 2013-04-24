package org.kuali.student.enrollment.class2.autogen.util;

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
 */

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingListSectionWrapper;
import org.kuali.student.enrollment.class2.autogen.form.ARGCourseOfferingManagementForm;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class contains mehtods to handle CO and AO toolbars matrix
 *
 * @author Kuali Student Team
 */
public class ARGToolbarUtil {
    private static PermissionService permissionService = getPermissionService();

    public static void processCoToolbarForUser(List<CourseOfferingListSectionWrapper> coListWrapperList, ARGCourseOfferingManagementForm form){
        form.setEnableAddButton(false);
        String socStateKey = form.getSocStateKey();
        String socState = socStateKey==null?null:socStateKey.substring(socStateKey.lastIndexOf('.')+1);
        String socSchedulingState = form.getSocSchedulingStateKey();

        String principalId = GlobalVariables.getUserSession().getPerson().getPrincipalId();

        //Check if the user can add based on soc state
        Map<String,String> permissionDetails = new HashMap<String,String>();
        Map<String,String> roleQualifications = new HashMap<String,String>();

        //ToDo, add role qualifiers for org/subject/etc. (Refactor so qualification resolving is done in a single place)
        roleQualifications.put("subjectArea", form.getSubjectCode());

        permissionDetails.put("socState", socState);

        //Check if the user can add based on classes start date
        Date termClassStartDate = form.getTermClassStartDate();
        Date now = new Date();
        if (termClassStartDate == null || now.before(termClassStartDate)) {
            permissionDetails.put("termClassStartDateLater", "true");
        }

        permissionDetails.put(KimConstants.AttributeConstants.VIEW_ID, form.getViewId());

        //for Add CO button
        if (checkBzLogicForCOButtons(socStateKey, socSchedulingState, "", "addCO")) {
            //check role permission
            permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "addCO");
            if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                form.setEnableAddButton(true);
            }
        }


        if (coListWrapperList != null && !coListWrapperList.isEmpty()) {
            for (CourseOfferingListSectionWrapper coListWrapper : coListWrapperList) {
                String coStateKey = coListWrapper.getCourseOfferingStateKey();
                String coState = coStateKey.substring(coStateKey.lastIndexOf('.') + 1);

                permissionDetails.put("coState", coState);
                roleQualifications.put("org", coListWrapper.getAdminOrg());

                //Clear old values
                coListWrapper.setEnableCopyCOActionLink(false);
                coListWrapper.setEnableEditCOActionLink(false);
                coListWrapper.setEnableApproveButton(false);
                coListWrapper.setEnableReinstateButton(false);
                coListWrapper.setEnableSuspendButton(false);
                coListWrapper.setEnableCancelButton(false);
                coListWrapper.setEnableDeleteButton(false);

                //for copy and edit action links on each CO row.
                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "copyCOonManageCOsPage");
                if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                    coListWrapper.setEnableCopyCOActionLink(true);
                }

                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "editCO");
                if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                    coListWrapper.setEnableEditCOActionLink(true);
                }

                //for approve CO button
                //check business logic
                if (checkBzLogicForCOButtons(socStateKey, socSchedulingState, coStateKey, "approveCO")) {
                    //check role permission
                    permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "approveCO");
                    if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                        coListWrapper.setEnableApproveButton(true);
                    }
                }

                //for delete CO button
                if (checkBzLogicForCOButtons(socStateKey, socSchedulingState, coStateKey, "deleteCO")) {
                    //check role permission
                    permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "deleteCO");
                    if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                        coListWrapper.setEnableDeleteButton(true);
                    }
                }


                //Currently, there are no reinstate, suspend and cancel CO buttons. Comment out the following checking
                /*
                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "reinstateCO");
                if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                    coListWrapper.setEnableReinstateButton(true);
                }

                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "suspendCO");
                if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                    coListWrapper.setEnableSuspendButton(true);
                }

                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "cancelCO");
                if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                    coListWrapper.setEnableCancelButton(true);
                }
                */

            }
        }

    }

    public static void processAoToolbarForUser(List<ActivityOfferingWrapper> activityWrapperList, ARGCourseOfferingManagementForm form){
        form.setEnableAddButton(false);
        form.setEnableMoveAOButton(false);
        form.setEnableAddClusterButton(false);
        String principalId = GlobalVariables.getUserSession().getPerson().getPrincipalId();

        String socStateKey = form.getSocStateKey();
        String socState = socStateKey==null?null:socStateKey.substring(socStateKey.lastIndexOf('.')+1);
        String socSchedulingState = form.getSocSchedulingStateKey();

        //Check if the user can add based on soc state
        Map<String,String> permissionDetails = new HashMap<String,String>();
        Map<String,String> roleQualifications = new HashMap<String,String>();

        //ToDo, add role qualifiers for org/subject/etc. (Refactor so qualification resolving is done in a single place)
        roleQualifications.put("org", form.getAdminOrg());
        roleQualifications.put("subjectArea", form.getSubjectCode());

        permissionDetails.put("socState", socState);

        //Check if the user can add based on classes start date
        Date termClassStartDate = form.getTermClassStartDate();
        Date now = new Date();
        if (termClassStartDate == null || now.before(termClassStartDate)) {
            permissionDetails.put("termClassStartDateLater", "true");
        }

        permissionDetails.put(KimConstants.AttributeConstants.VIEW_ID, form.getViewId());

        //for Add Activity button
        if (checkBzLogicForAOButtons(socStateKey, socSchedulingState, "", "addAO")) {
            //check role permission
            permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "addAO");
            if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                form.setEnableAddButton(true);
            }

        }

        //for move to button
        if (checkBzLogicForAOButtons(socStateKey, socSchedulingState, "", "moveAO")) {
            //check role permission
            permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "moveAO");
            if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                form.setEnableMoveAOButton(true);
            }
        }

        //for add cluster button
        if (checkBzLogicForAOButtons(socStateKey, socSchedulingState, "", "addCluster")) {
            //check role permission
            permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "addCluster");
            if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                form.setEnableAddClusterButton(true);
            }
        }


        if (activityWrapperList != null && !activityWrapperList.isEmpty()) {
            for (ActivityOfferingWrapper activityWrapper : activityWrapperList) {

                //Only use the last part of the state key to make it clearer in the config
                String aoStateKey = activityWrapper.getAoInfo().getStateKey();
                String aoState = aoStateKey.substring(aoStateKey.lastIndexOf('.') + 1);

                permissionDetails.put("aoState", aoState);

                //Reset the form
                activityWrapper.setEnableCopyAOActionLink(false);
                activityWrapper.setEnableEditAOActionLink(false);
                activityWrapper.setEnableCancelButton(false);
                activityWrapper.setEnableApproveButton(false);
                activityWrapper.setEnableReinstateButton(false);
                activityWrapper.setEnableDeleteButton(false);
                activityWrapper.setEnableSuspendButton(false);
                activityWrapper.setEnableDraftButton(false);

                //for approve AO button
                if (checkBzLogicForAOButtons(socStateKey, socSchedulingState, aoStateKey, "approveAO")) {
                    //check role permission
                    permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "approveAO");
                    if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                        activityWrapper.setEnableApproveButton(true);
                    }
                }

                //for Delete AO button
                if (checkBzLogicForAOButtons(socStateKey, socSchedulingState, "", "deleteAO")) {
                    //check role permission
                    permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "deleteAO");
                    if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                        activityWrapper.setEnableDeleteButton(true);
                    }
                }

                //for Set as Draft button
                if (checkBzLogicForAOButtons(socStateKey, socSchedulingState, aoStateKey, "setDraftAO")) {
                    //check role permission
                    permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "setDraftAO");
                    if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                        activityWrapper.setEnableDraftButton(true);
                    }
                }


                //for copy and edit action links on each CO row.
                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "copyAOonManageAOsPage");
                if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                    activityWrapper.setEnableCopyAOActionLink(true);
                }

                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "editAOonManageAOsPage");
                if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                    activityWrapper.setEnableEditAOActionLink(true);
                }

                //Currently, there are no reinstate, suspend and cancel AO buttons. Comment out the following checking
                /*
                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "cancelAO");
                if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                    activityWrapper.setEnableCancelButton(true);
                }

                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "reinstateAO");
                if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                    activityWrapper.setEnableReinstateButton(true);
                }

                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "suspendAO");
                if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                    activityWrapper.setEnableSuspendButton(true);
                }
                */

            }
        }

    }

    private static boolean checkBzLogicForCOButtons(String socState, String socSchedulingState, String coStateKey, String actionEvent){
        boolean bzEnableButton = false;
        if(StringUtils.equals(actionEvent, "approveCO")) {
            if((StringUtils.equals(coStateKey, LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY) || StringUtils.equals(coStateKey, LuiServiceConstants.LUI_CO_STATE_PLANNED_KEY)) &&
                    (StringUtils.equals(socState, CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY) ||
                            StringUtils.equals(socState, CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY) ||
                            !isSOCLockedAndMSEInProgress(socState, socSchedulingState))){
                bzEnableButton = true;
            }
        } else if (StringUtils.equals(actionEvent, "addCO")) {
            bzEnableButton = true;
        } else if (StringUtils.equals(actionEvent, "deleteCO")) {
            bzEnableButton = true;
        }

        return bzEnableButton;
    }

    private static boolean checkBzLogicForAOButtons(String socState, String socSchedulingState, String aoStateKey, String actionEvent){
        boolean bzEnableButton = false;
        if(StringUtils.equals(actionEvent, "approveAO")) {
            if(StringUtils.equals(aoStateKey, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY) &&
                    (StringUtils.equals(socState, CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY) ||
                            StringUtils.equals(socState, CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY) ||
                            !isSOCLockedAndMSEInProgress(socState, socSchedulingState))){
                bzEnableButton = true;
            }
        } else if (StringUtils.equals(actionEvent, "addAO")) {
            bzEnableButton = true;
        } else if (StringUtils.equals(actionEvent, "setDraftAO")) {
            if(StringUtils.equals(aoStateKey, LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY)){
                bzEnableButton = true;
            }
        } else if (StringUtils.equals(actionEvent, "deleteAO")) {
            bzEnableButton = true;
        } else if (StringUtils.equals(actionEvent, "addCluster")) {
            bzEnableButton = true;
        } else if (StringUtils.equals(actionEvent, "moveAO")) {
            bzEnableButton = true;
        }

        return bzEnableButton;
    }


    private static boolean isInProgress(String socState, String socSchedulingState){
        boolean inProgress = false;
        if(StringUtils.equals(socState, CourseOfferingSetServiceConstants.PUBLISHING_SOC_STATE_KEY) ||
                (StringUtils.equals(socState, CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY) &&
                        StringUtils.equals(socSchedulingState, CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_IN_PROGRESS))){
            inProgress = true;
        }
        return inProgress;
    }

    private static boolean isSOCLockedAndMSEInProgress(String socState, String socSchedulingState){
        boolean inProgress = false;
        if(StringUtils.equals(socState, CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY) &&
                StringUtils.equals(socSchedulingState, CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_IN_PROGRESS)){
            inProgress = true;
        }
        return inProgress;
    }

    private static PermissionService getPermissionService() {
        if(permissionService==null){
            permissionService = KimApiServiceLocator.getPermissionService();
        }
        return permissionService;
    }
}
