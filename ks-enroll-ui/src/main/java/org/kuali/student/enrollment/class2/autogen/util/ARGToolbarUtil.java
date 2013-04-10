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

        if(!isInProgress(socStateKey, socSchedulingState)){
            permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT,"addCO");
            if(permissionService.isAuthorizedByTemplate(principalId,"KS-ENR",KimConstants.PermissionTemplateNames.PERFORM_ACTION,permissionDetails,roleQualifications)){
                form.setEnableAddButton(true);
            }

            if(coListWrapperList != null && !coListWrapperList.isEmpty()){

                for(CourseOfferingListSectionWrapper coListWrapper : coListWrapperList){
                    String coStateKey = coListWrapper.getCourseOfferingStateKey();
                    String coState = coStateKey.substring(coStateKey.lastIndexOf('.')+1);

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
                    if(permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails,roleQualifications)){
                        coListWrapper.setEnableCopyCOActionLink(true);
                    }

                    permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "editCO");
                    if(permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails,roleQualifications)){
                        coListWrapper.setEnableEditCOActionLink(true);
                    }

                    permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "approveCO");
                    if(permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails,roleQualifications)){
                        if(checkBzLogicForApproveCO(socStateKey, socSchedulingState, coStateKey)){
                            coListWrapper.setEnableApproveButton(true);
                        }
                    }

                    permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "reinstateCO");
                    if(permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails,roleQualifications)){
                        coListWrapper.setEnableReinstateButton(true);
                    }

                    permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "suspendCO");
                    if(permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails,roleQualifications)){
                        coListWrapper.setEnableSuspendButton(true);
                    }

                    permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "cancelCO");
                    if(permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails,roleQualifications)){
                        coListWrapper.setEnableCancelButton(true);
                    }

                    permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "deleteCO");
                    if(permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails,roleQualifications)){
                        coListWrapper.setEnableDeleteButton(true);
                    }
                }
            }
        }
    }

    public static void processAoToolbarForUser(List<ActivityOfferingWrapper> activityWrapperList, ARGCourseOfferingManagementForm form){
        form.setEnableAddButton(false);
        form.setEnableMoveAOButton(false);
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

        if(!isInProgress(socStateKey, socSchedulingState)){
            permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT,"addAO");
            if(permissionService.isAuthorizedByTemplate(principalId,"KS-ENR",KimConstants.PermissionTemplateNames.PERFORM_ACTION,permissionDetails,roleQualifications)){
                form.setEnableAddButton(true);
            }

            permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT,"moveAO");
            if(permissionService.isAuthorizedByTemplate(principalId,"KS-ENR",KimConstants.PermissionTemplateNames.PERFORM_ACTION,permissionDetails,roleQualifications)){
                form.setEnableMoveAOButton(true);
            }

            if(activityWrapperList != null && !activityWrapperList.isEmpty()){
                for(ActivityOfferingWrapper activityWrapper : activityWrapperList){

                    //Only use the last part of the state key to make it clearer in the config
                    String aoStateKey = activityWrapper.getAoInfo().getStateKey();
                    String aoState = aoStateKey.substring(aoStateKey.lastIndexOf('.')+1);

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

                    //for copy and edit action links on each CO row.
                    permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "copyAOonManageAOsPage");
                    if(permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails,roleQualifications)){
                        activityWrapper.setEnableCopyAOActionLink(true);
                    }

                    permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "editAOonManageAOsPage");
                    if(permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails,roleQualifications)){
                        activityWrapper.setEnableEditAOActionLink(true);
                    }

                    permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "cancelAO");
                    if(permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails,roleQualifications)){
                        activityWrapper.setEnableCancelButton(true);
                    }

                    permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "approveAO");
                    if(permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails,roleQualifications)){
                        if(checkBzLogicForApproveAO(socStateKey, socSchedulingState, aoStateKey)){
                            activityWrapper.setEnableApproveButton(true);
                        }
                    }

                    permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "reinstateAO");
                    if(permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails,roleQualifications)){
                        activityWrapper.setEnableReinstateButton(true);
                    }

                    permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "deleteAO");
                    if(permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails,roleQualifications)){
                        activityWrapper.setEnableDeleteButton(true);
                    }
                    permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "suspendAO");
                    if(permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails,roleQualifications)){
                        activityWrapper.setEnableSuspendButton(true);
                    }

                    permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "setDraftAO");
                    if(permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails,roleQualifications)){
                        if(checkBzLogicForDraftAO(aoStateKey)){
                            activityWrapper.setEnableDraftButton(true);
                        }
                    }
                }
            }
        }
    }

    private static boolean checkBzLogicForApproveCO(String socState, String socSchedulingState, String coState){
        boolean bzApproveAllowed = false;

        if(StringUtils.equals(coState, LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY) &&
                (StringUtils.equals(socState, CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY) ||
                        StringUtils.equals(socState, CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY) ||
                        (StringUtils.equals(socState, CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY) &&
                                !StringUtils.equals(socSchedulingState, CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_IN_PROGRESS)))){
            bzApproveAllowed = true;
        }

        return bzApproveAllowed;
    }

    private static boolean checkBzLogicForApproveAO(String socState, String socSchedulingState, String aoState){
        boolean bzApproveAllowed = false;

        if(StringUtils.equals(aoState, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY) &&
                (StringUtils.equals(socState, CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY) ||
                        StringUtils.equals(socState, CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY) ||
                        (StringUtils.equals(socState, CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY) &&
                                !StringUtils.equals(socSchedulingState, CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_IN_PROGRESS)))){
            bzApproveAllowed = true;
        }
        return bzApproveAllowed;

    }

    private static boolean checkBzLogicForDraftAO(String aoState){
        boolean bzDraftAllowed = false;
        if(StringUtils.equals(aoState, LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY)){
            bzDraftAllowed = true;
        }

        return bzDraftAllowed;

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

    private static PermissionService getPermissionService() {
        if(permissionService==null){
            permissionService = KimApiServiceLocator.getPermissionService();
        }
        return permissionService;
    }
}
