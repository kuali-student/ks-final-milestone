package org.kuali.student.enrollment.class2.courseoffering.util;

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
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingListSectionWrapper;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.acal.dto.KeyDateInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class contains methods to handle CO and AO toolbars matrix
 *
 * @author Kuali Student Team
 */
public class CourseOfferingManagementToolbarUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseOfferingManagementToolbarUtil.class);

    public static void processCoToolbarForUser(List<CourseOfferingListSectionWrapper> coListWrapperList, CourseOfferingManagementForm form){

        long startTime = System.currentTimeMillis();

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
            if (CourseOfferingManagementUtil.getPermissionService().isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                form.setEnableAddButton(true);
            }
        }


        if (coListWrapperList != null && !coListWrapperList.isEmpty()) {
            for (CourseOfferingListSectionWrapper coListWrapper : coListWrapperList) {
                String coStateKey = coListWrapper.getCourseOfferingStateKey();
                String coState = coStateKey.substring(coStateKey.lastIndexOf('.') + 1);

                permissionDetails.put("coState", coState);
                roleQualifications.put("offeringAdminOrgId", coListWrapper.getAdminOrg());

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
                if (CourseOfferingManagementUtil.getPermissionService().isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                    coListWrapper.setEnableCopyCOActionLink(true);
                }

                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "editCO");
                if (CourseOfferingManagementUtil.getPermissionService().isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                    coListWrapper.setEnableEditCOActionLink(true);
                }

                //for approve CO button
                //check business logic
                if (checkBzLogicForCOButtons(socStateKey, socSchedulingState, coStateKey, "approveCO")) {
                    //check role permission
                    permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "approveCO");
                    if (CourseOfferingManagementUtil.getPermissionService().isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                        coListWrapper.setEnableApproveButton(true);
                    }
                }

                //for delete CO button
                if (checkBzLogicForCOButtons(socStateKey, socSchedulingState, coStateKey, "deleteCO")) {
                    // Term Registration Start Date (Milestone:First Day to Add < in the AZ matrix)
                    if(coStateKey.equals(LuiServiceConstants.LUI_CO_STATE_PLANNED_KEY)) {    //for performance - skip if not applicable
                        ContextInfo contextInfo = ContextUtils.createDefaultContextInfo();
                        Date termRegStartDate = null;
                        try {
                            //loop through the key dates and registration periods in order to determine the start date for the registration within the subject term
                            List<TypeInfo> regPeriods = CourseOfferingManagementUtil.getTypeService().getTypesForGroupType(AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_GROUP_TYPE_KEY, contextInfo);
                            List<KeyDateInfo> keyDateInfoList = CourseOfferingManagementUtil.getAcademicCalendarService().getKeyDatesForTerm(form.getTermInfo().getId(), contextInfo);
                            if (keyDateInfoList != null && keyDateInfoList.size() > 0) {
                                for (KeyDateInfo keyDateInfo : keyDateInfoList) {
                                    for (TypeInfo regPeriod : regPeriods) {
                                        if (keyDateInfo.getTypeKey().equalsIgnoreCase(regPeriod.getKey()) && keyDateInfo.getStartDate() != null) {
                                            if (termRegStartDate == null || keyDateInfo.getStartDate().before(termRegStartDate)) {
                                                termRegStartDate = keyDateInfo.getStartDate();
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (Exception e){
                            throw new RuntimeException("Exception in processCoToolbarForUser(): "+e);
                        }
                        Date nowReg = new Date();
                        if (termRegStartDate == null || nowReg.before(termRegStartDate)) {
                            permissionDetails.put("termRegStartDateLater", "true");
                        }
                    }
                    //check role permission
                    permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "deleteCO");
                    if (CourseOfferingManagementUtil.getPermissionService().isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                        coListWrapper.setEnableDeleteButton(true);
                    }
                }

            }
        }
        LOGGER.info("******** CO Toolbar AuthZ Check *********{}ms", System.currentTimeMillis()-startTime);
    }

    public static void processAoToolbarForUser(List<ActivityOfferingWrapper> activityWrapperList, CourseOfferingManagementForm form){
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
        roleQualifications.put("offeringAdminOrgId", form.getAdminOrg());
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
        if (checkBzLogicForAOButtons(socStateKey, socSchedulingState, "", ActivityOfferingConstants.ACTIVITYOFFERING_ACTION_ADD)) {
            //check role permission
            permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, ActivityOfferingConstants.ACTIVITYOFFERING_ACTION_ADD);
            if (CourseOfferingManagementUtil.getPermissionService().isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                form.setEnableAddButton(true);
            }

        }

        //for add cluster button
        if (checkBzLogicForAOButtons(socStateKey, socSchedulingState, "", ActivityOfferingConstants.ACTIVITYOFFERING_ACTION_ADD_CLUSTER)) {
            //check role permission
            permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, ActivityOfferingConstants.ACTIVITYOFFERING_ACTION_ADD_CLUSTER);
            if (CourseOfferingManagementUtil.getPermissionService().isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
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
                activityWrapper.setEnableMoveToButton(false);
                activityWrapper.setEnableApproveButton(false);
                activityWrapper.setEnableDraftButton(false);
                activityWrapper.setEnableCancelButton(false);
                activityWrapper.setEnableSuspendButton(false);
                activityWrapper.setEnableReinstateButton(false);
                activityWrapper.setEnableDeleteButton(false);

                //for approve AO button
                if (checkBzLogicForAOButtons(socStateKey, socSchedulingState, aoStateKey, ActivityOfferingConstants.ACTIVITYOFFERING_ACTION_APPROVE)) {
                    //check role permission
                    permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, ActivityOfferingConstants.ACTIVITYOFFERING_ACTION_APPROVE);
                    if (CourseOfferingManagementUtil.getPermissionService().isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                        activityWrapper.setEnableApproveButton(true);
                    }
                }

                //for Delete AO button
                if (checkBzLogicForAOButtons(socStateKey, socSchedulingState, "", ActivityOfferingConstants.ACTIVITYOFFERING_ACTION_DELETE)) {
                    //check role permission
                    permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, ActivityOfferingConstants.ACTIVITYOFFERING_ACTION_DELETE);
                    if (CourseOfferingManagementUtil.getPermissionService().isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                        activityWrapper.setEnableDeleteButton(true);
                    }
                }

                //for Set as Draft button
                if (checkBzLogicForAOButtons(socStateKey, socSchedulingState, aoStateKey, ActivityOfferingConstants.ACTIVITYOFFERING_ACTION_SET_DRAFT)) {
                    //check role permission
                    permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, ActivityOfferingConstants.ACTIVITYOFFERING_ACTION_SET_DRAFT);
                    if (CourseOfferingManagementUtil.getPermissionService().isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                        activityWrapper.setEnableDraftButton(true);
                    }
                }

                //for move to button
                if (checkBzLogicForAOButtons(socStateKey, socSchedulingState, "", ActivityOfferingConstants.ACTIVITYOFFERING_ACTION_MOVE)) {
                    //check role permission
                    permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, ActivityOfferingConstants.ACTIVITYOFFERING_ACTION_MOVE);
                    if (CourseOfferingManagementUtil.getPermissionService().isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                        activityWrapper.setEnableMoveToButton(true);
                    }
                }

                //for copy and edit action links on each CO row.
                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "copyAOonManageAOsPage");
                if (CourseOfferingManagementUtil.getPermissionService().isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                    activityWrapper.setEnableCopyAOActionLink(true);
                }

                String colocatedAO = "";
                if (activityWrapper.isColocatedAO()) {
                    colocatedAO = "true";
                }
                permissionDetails.put("colocatedAO", colocatedAO);
                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "editAOonManageAOsPage");
                if(CourseOfferingManagementUtil.getPermissionService().isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails,roleQualifications)){
                    activityWrapper.setEnableEditAOActionLink(true);
                }

                //for Cancel AO button
                if (!activityWrapper.isColocatedAO() && checkBzLogicForAOButtons(socStateKey, socSchedulingState, aoStateKey, ActivityOfferingConstants.ACTIVITYOFFERING_ACTION_CANCEL)) {
                    permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, ActivityOfferingConstants.ACTIVITYOFFERING_ACTION_CANCEL);
                    if (CourseOfferingManagementUtil.getPermissionService().isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                        activityWrapper.setEnableCancelButton(true);
                    }
                }

                //for Suspend AO button
                if (checkBzLogicForAOButtons(socStateKey, socSchedulingState, aoStateKey, ActivityOfferingConstants.ACTIVITYOFFERING_ACTION_SUSPEND)) {
                    permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, ActivityOfferingConstants.ACTIVITYOFFERING_ACTION_SUSPEND);
                    if (CourseOfferingManagementUtil.getPermissionService().isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                        activityWrapper.setEnableSuspendButton(true);
                    }
                }

                //for Reinstate AO button
                if (checkBzLogicForAOButtons(socStateKey, socSchedulingState, aoStateKey, ActivityOfferingConstants.ACTIVITYOFFERING_ACTION_REINSTATE)) {
                    permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, ActivityOfferingConstants.ACTIVITYOFFERING_ACTION_REINSTATE);
                    if (CourseOfferingManagementUtil.getPermissionService().isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                        activityWrapper.setEnableReinstateButton(true);
                    }
                }
            }
        }

    }

    private static boolean checkBzLogicForCOButtons(String socState, String socSchedulingState, String coStateKey, String actionEvent){
        boolean bzEnableButton = false;
        if(StringUtils.equals(actionEvent, "approveCO")) {
            //Approve CO button is enabled under all SOC states as long as CO is in Draft/Planned state
            if((StringUtils.equals(coStateKey, LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY) || StringUtils.equals(coStateKey, LuiServiceConstants.LUI_CO_STATE_PLANNED_KEY))){
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
        if(StringUtils.equals(actionEvent, ActivityOfferingConstants.ACTIVITYOFFERING_ACTION_APPROVE)) {
            //Approve AO button is enabled under all SOC states as long as AO is in Draft state
            if(StringUtils.equals(aoStateKey, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY)){
                bzEnableButton = true;
            }
        } else if (StringUtils.equals(actionEvent, ActivityOfferingConstants.ACTIVITYOFFERING_ACTION_ADD)) {
            bzEnableButton = true;
        } else if (StringUtils.equals(actionEvent, ActivityOfferingConstants.ACTIVITYOFFERING_ACTION_SET_DRAFT)) {
            if(StringUtils.equals(aoStateKey, LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY) &&
                    (StringUtils.equals(socState, CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY) ||
                            StringUtils.equals(socState, CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY)||
                            isSOCLockedPreMSE(socState, socSchedulingState))){
                bzEnableButton = true;
            }
        } else if (StringUtils.equals(actionEvent, ActivityOfferingConstants.ACTIVITYOFFERING_ACTION_DELETE)) {
            bzEnableButton = true;
        } else if (StringUtils.equals(actionEvent, ActivityOfferingConstants.ACTIVITYOFFERING_ACTION_ADD_CLUSTER)) {
            bzEnableButton = true;
        } else if (StringUtils.equals(actionEvent, ActivityOfferingConstants.ACTIVITYOFFERING_ACTION_MOVE)) {
            bzEnableButton = true;
        } else if(StringUtils.equals(actionEvent, ActivityOfferingConstants.ACTIVITYOFFERING_ACTION_CANCEL)){
            if(StringUtils.equals(aoStateKey, LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY) ||
                    StringUtils.equals(aoStateKey, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY) ||
                    StringUtils.equals(aoStateKey, LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY) ||
                    StringUtils.equals(aoStateKey, LuiServiceConstants.LUI_AO_STATE_SUSPENDED_KEY)){
                bzEnableButton = true;
            }
        } else if(StringUtils.equals(actionEvent, ActivityOfferingConstants.ACTIVITYOFFERING_ACTION_SUSPEND)){
            if((StringUtils.equals(aoStateKey, LuiServiceConstants.LUI_AO_STATE_APPROVED_KEY) ||
                    StringUtils.equals(aoStateKey, LuiServiceConstants.LUI_AO_STATE_DRAFT_KEY) ||
                    StringUtils.equals(aoStateKey, LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY)) &&
                    !StringUtils.equals(socState, CourseOfferingSetServiceConstants.OPEN_SOC_STATE_KEY) &&
                    !StringUtils.equals(socState, CourseOfferingSetServiceConstants.DRAFT_SOC_STATE_KEY)){
                bzEnableButton = true;
            }
        } else if(StringUtils.equals(actionEvent, ActivityOfferingConstants.ACTIVITYOFFERING_ACTION_REINSTATE)){
            if(StringUtils.equals(aoStateKey, LuiServiceConstants.LUI_AO_STATE_CANCELED_KEY) ||
                    StringUtils.equals(aoStateKey, LuiServiceConstants.LUI_AO_STATE_SUSPENDED_KEY)){
                bzEnableButton = true;
            }
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
        boolean socLockedAndMSEInProgress = false;
        if(StringUtils.equals(socState, CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY) &&
                !StringUtils.equals(socSchedulingState, CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_IN_PROGRESS)){
            socLockedAndMSEInProgress = true;
        }
        return socLockedAndMSEInProgress;
    }

    private static boolean isSOCLockedPreMSE(String socState, String socSchedulingState){
        boolean socLockedPreMSE = false;
        if(StringUtils.equals(socState, CourseOfferingSetServiceConstants.LOCKED_SOC_STATE_KEY) &&
                StringUtils.equals(socSchedulingState, CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_NOT_STARTED)){
            socLockedPreMSE = true;
        }
        return socLockedPreMSE;
    }
}
