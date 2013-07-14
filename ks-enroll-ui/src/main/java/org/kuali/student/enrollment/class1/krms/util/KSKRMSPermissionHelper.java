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
import org.kuali.student.enrollment.class1.krms.dto.CORuleManagementWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Kuali Student Team
 */
public class KSKRMSPermissionHelper {

    private static PermissionService permissionService = getPermissionService();

    public static void processActionPermissionForUser(MaintenanceDocumentForm document) {
        CORuleManagementWrapper wrapper = (CORuleManagementWrapper) document.getDocument().getNewMaintainableObject().getDataObject();

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

                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "addRequisite");
                if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                    rule.getPermission().setAddRequisiteAction(true);
                }

                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "deleteRequisite");
                if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                    rule.getPermission().setDeleteRequisiteAction(true);
                }

                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "compare");
                if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                    rule.getPermission().setCompareAction(true);
                }

                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "addRule");
                if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                    rule.getPermission().setAddRuleAction(true);
                }

                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "editRule");
                if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                    rule.getPermission().setEditRuleAction(true);
                }

                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "createGroup");
                if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                    rule.getPermission().setGroupRuleAction(true);
                }

                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "moveUp");
                if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                    rule.getPermission().setMoveUpRuleAction(true);
                }

                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "moveDown");
                if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                    rule.getPermission().setMoveDownRuleAction(true);
                }

                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "moveIn");
                if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                    rule.getPermission().setMoveInRuleAction(true);
                }

                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "moveOut");
                if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                    rule.getPermission().setMoveOutRuleAction(true);
                }

                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "copyRule");
                if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                    rule.getPermission().setCopyRuleAction(true);
                }

                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "cutRule");
                if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                    rule.getPermission().setCutRuleAction(true);
                }

                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "pasteRule");
                if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                    rule.getPermission().setPasteRuleAction(true);
                }

                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "deleteRule");
                if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                    rule.getPermission().setDeleteRuleAction(true);
                }

                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "editRuleLogic");
                if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                    rule.getPermission().setEditRuleLogicTab(true);
                }

                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "updateRule");
                if (permissionService.isAuthorizedByTemplate(principalId, "KS-ENR", KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
                    rule.getPermission().setUpdateRuleAction(true);
                }
            }
        }
    }

    private static void resetActions(RuleEditor ruleEditor) {
        ruleEditor.getPermission().setAddRequisiteAction(false);
        ruleEditor.getPermission().setDeleteRequisiteAction(false);
        ruleEditor.getPermission().setCompareAction(false);
        ruleEditor.getPermission().setAddRuleAction(false);
        ruleEditor.getPermission().setEditRuleAction(false);
        ruleEditor.getPermission().setGroupRuleAction(false);
        ruleEditor.getPermission().setMoveUpRuleAction(false);
        ruleEditor.getPermission().setMoveDownRuleAction(false);
        ruleEditor.getPermission().setMoveOutRuleAction(false);
        ruleEditor.getPermission().setMoveInRuleAction(false);
        ruleEditor.getPermission().setCopyRuleAction(false);
        ruleEditor.getPermission().setCutRuleAction(false);
        ruleEditor.getPermission().setPasteRuleAction(false);
        ruleEditor.getPermission().setDeleteRuleAction(false);
        ruleEditor.getPermission().setEditRuleLogicTab(false);
        ruleEditor.getPermission().setUpdateRuleAction(false);
    }

    private static PermissionService getPermissionService() {
        if(permissionService==null){
            permissionService = KimApiServiceLocator.getPermissionService();
        }
        return permissionService;
    }
}
