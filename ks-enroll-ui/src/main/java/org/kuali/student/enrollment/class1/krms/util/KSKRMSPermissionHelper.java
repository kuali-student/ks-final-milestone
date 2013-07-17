/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class1.krms.util;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.rice.krms.dto.PermissionWrapper;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.student.common.uif.form.KSUifForm;
import org.kuali.student.enrollment.class1.krms.dto.EnrolRuleManagementWrapper;
import org.kuali.student.enrollment.class2.autogen.form.ARGCourseOfferingManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Kuali Student Team
 */
public class KSKRMSPermissionHelper {

    private static PermissionService permissionService = getPermissionService();

    public static void processActionPermissionForUser(MaintenanceDocumentForm document) {
        EnrolRuleManagementWrapper wrapper = (EnrolRuleManagementWrapper) document.getDocument().getNewMaintainableObject().getDataObject();

        String principalId = GlobalVariables.getUserSession().getPerson().getPrincipalId();

        String socState = StringUtils.lowerCase(wrapper.getContextBar().getTermSocState());

        Map<String,String> permissionDetails = new HashMap<String,String>();
        Map<String,String> roleQualifications = new HashMap<String,String>();

        roleQualifications.put("offeringAdminOrgId", wrapper.getAdminOrg());

        permissionDetails.put("socState", socState);
        permissionDetails.put(KimConstants.AttributeConstants.VIEW_ID, document.getViewId());

        for(AgendaEditor agenda : wrapper.getAgendas()) {
            for(RuleEditor rule : agenda.getRuleEditors().values()) {

                if(rule.getPermission() == null) {
                    rule.setPermission(new PermissionWrapper());
                }

                resetActions(rule);

                permissionDetails.put("ruleType", rule.getRuleTypeInfo().getType());

                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "addEditRequisite");
                if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                    rule.getPermission().setAddEditRequisite(true);
                }

                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "deleteRequisite");
                if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                    rule.getPermission().setDeleteRequisite(true);
                }

                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "compare");
                if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                    rule.getPermission().setCompare(true);
                }

                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "addEditGroupRule");
                if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                    rule.getPermission().setAddEditGroupRule(true);
                }

                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "moveRule");
                if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                    rule.getPermission().setMoveRule(true);
                }

                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "copyCutRule");
                if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                    rule.getPermission().setCopyCutRule(true);
                }

                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "editLogic");
                if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                    rule.getPermission().setEditLogic(true);
                }

                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "deleteRule");
                if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                    rule.getPermission().setDeleteRule(true);
                }
            }
        }
    }

    public static void processManageCORequisiteLinkForUser(ARGCourseOfferingManagementForm form) {
        form.setRequisiteLink(false);

        String principalId = GlobalVariables.getUserSession().getPerson().getPrincipalId();

        String socState = StringUtils.lowerCase(form.getContextBar().getTermSocState());

        Map<String,String> permissionDetails = new HashMap<String,String>();
        Map<String,String> roleQualifications = new HashMap<String,String>();

        roleQualifications.put("offeringAdminOrgId", form.getAdminOrg());

        permissionDetails.put("socState", socState);
        permissionDetails.put(KimConstants.AttributeConstants.VIEW_ID, form.getViewId());

        permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "requisiteCOLink");
        if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
            form.setRequisiteLink(true);
        }
    }

    public static void processManageAORequisiteLinkForUser(ARGCourseOfferingManagementForm form, ActivityOfferingWrapper wrapper) {
        wrapper.setRequisiteLink(false);

        String principalId = GlobalVariables.getUserSession().getPerson().getPrincipalId();

        String socState = StringUtils.lowerCase(form.getContextBar().getTermSocState());

        Map<String,String> permissionDetails = new HashMap<String,String>();
        Map<String,String> roleQualifications = new HashMap<String,String>();

        roleQualifications.put("offeringAdminOrgId", form.getAdminOrg());

        permissionDetails.put("socState", socState);
        permissionDetails.put(KimConstants.AttributeConstants.VIEW_ID, form.getViewId());

        permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "requisiteAOLink");
        if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
            wrapper.setRequisiteLink(true);
        }
    }

    private static void resetActions(RuleEditor ruleEditor) {
        ruleEditor.getPermission().setAddEditRequisite(false);
        ruleEditor.getPermission().setDeleteRequisite(false);
        ruleEditor.getPermission().setCompare(false);
        ruleEditor.getPermission().setAddEditGroupRule(false);
        ruleEditor.getPermission().setMoveRule(false);
        ruleEditor.getPermission().setCopyCutRule(false);
        ruleEditor.getPermission().setEditLogic(false);
        ruleEditor.getPermission().setDeleteRule(false);
    }

    private static PermissionService getPermissionService() {
        if(permissionService==null){
            permissionService = KimApiServiceLocator.getPermissionService();
        }
        return permissionService;
    }
}
