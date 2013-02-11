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
 * Created by Li Pan on 1/26/13
 */
package org.kuali.student.enrollment.class2.courseoffering.util;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingListSectionWrapper;
import org.kuali.student.enrollment.class2.courseoffering.form.CourseOfferingManagementForm;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class contains mehtods to handle CO and AO toolbars matrix
 *
 * @author Kuali Student Team
 */
public class ToolbarUtil {
    private static PermissionService permissionService = getPermissionService();
    
    public static void processCoToolbarForUser(List<CourseOfferingListSectionWrapper> coListWrapperList, CourseOfferingManagementForm form){
        String socState = form.getSocStateKey();
        socState = socState==null?null:socState.substring(socState.lastIndexOf('.')+1);

        String principalId = GlobalVariables.getUserSession().getPerson().getPrincipalId();

        //Check if the user can add based on soc state
        Map<String,String> permissionDetails = new HashMap<String,String>();
        Map<String,String> roleQualifications = new HashMap<String,String>();

        //ToDo, add role qualifiers for org/subject/etc. (Refactor so qualification resolving is done in a single place)
        roleQualifications.put("org", form.getAdminOrg());

        permissionDetails.put("socState", socState);
        permissionDetails.put("termClassStartDateLater", "true");

        //Check if the user can add based on classes start date
        if (form.getTermInfo().getStartDate() != null) {
            Date now = new Date();
            if (now.before(form.getTermInfo().getStartDate())) {
                permissionDetails.put("termClassStartDateLater", "true");
            }
        }

        permissionDetails.put(KimConstants.AttributeConstants.VIEW_ID, form.getViewId());

        permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT,"addCO");
        if(permissionService.isAuthorizedByTemplate(principalId,"KS-ENR",KimConstants.PermissionTemplateNames.PERFORM_ACTION,permissionDetails,roleQualifications)){
            form.setEnableAddButton(true);
        }

        if(coListWrapperList != null && !coListWrapperList.isEmpty()){

            for(CourseOfferingListSectionWrapper coListWrapper : coListWrapperList){
                String coState = coListWrapper.getCourseOfferingStateKey();
                coState = coState.substring(coState.lastIndexOf('.')+1);

                permissionDetails.put("coState", coState);

                //TODO put in business logic so you can't cancel a canceled state, approve approved state etc...
                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "approveCO");
                if(permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails,roleQualifications)){
                    coListWrapper.setEnableApproveButton(true);
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

    public static void processAoToolbarForUser(List<ActivityOfferingWrapper> activityWrapperList, CourseOfferingManagementForm form){
        String principalId = GlobalVariables.getUserSession().getPerson().getPrincipalId();

        String socState = form.getSocStateKey();
        socState = socState==null?null:socState.substring(socState.lastIndexOf('.')+1);

        //Check if the user can add based on soc state
        Map<String,String> permissionDetails = new HashMap<String,String>();
        Map<String,String> roleQualifications = new HashMap<String,String>();

        //ToDo, add role qualifiers for org/subject/etc. (Refactor so qualification resolving is done in a single place)
        roleQualifications.put("org", form.getAdminOrg());

        permissionDetails.put("socState", socState);

        permissionDetails.put("termClassStartDateLater", "true");

        //Check if the user can add based on classes start date
        if (form.getTermInfo().getStartDate() != null) {
            Date now = new Date();
            if (now.before(form.getTermInfo().getStartDate())) {
                permissionDetails.put("termClassStartDateLater", "true");
            }
        }

        permissionDetails.put(KimConstants.AttributeConstants.VIEW_ID, form.getViewId());

        permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT,"addCO");
        if(permissionService.isAuthorizedByTemplate(principalId,"KS-ENR",KimConstants.PermissionTemplateNames.PERFORM_ACTION,permissionDetails,roleQualifications)){
            form.setEnableAddButton(true);
        }
        if(activityWrapperList != null && !activityWrapperList.isEmpty()){
            for(ActivityOfferingWrapper activityWrapper : activityWrapperList){

                //Only use the last part of the state key to make it clearer in the config
                String aoState = activityWrapper.getAoInfo().getStateKey();
                aoState = aoState.substring(aoState.lastIndexOf('.')+1);

                permissionDetails.put("aoState", aoState);
                //TODO put in business logic so you can't cancel a canceled state, approve approved state etc...
                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "cancelAO");
                if(permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails,roleQualifications)){
                    activityWrapper.setEnableCancelButton(true);
                }
                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "approveAO");
                if(permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails,roleQualifications)){
                    activityWrapper.setEnableApproveButton(true);
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
                    activityWrapper.setEnableDraftButton(true);
                }
            }
        }
    }

    private static PermissionService getPermissionService() {
        if(permissionService==null){
            permissionService = KimApiServiceLocator.getPermissionService();
        }
        return permissionService;
    }
}
