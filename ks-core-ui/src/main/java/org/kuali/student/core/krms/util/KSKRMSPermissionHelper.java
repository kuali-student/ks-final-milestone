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
package org.kuali.student.core.krms.util;

import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.rice.krms.dto.PermissionWrapper;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.dto.RuleManagementWrapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Kuali Student Team
 */
public class KSKRMSPermissionHelper {

    private static PermissionService permissionService = getPermissionService();

    public static void processActionPermissionForUser(MaintenanceDocumentForm document) {
        RuleManagementWrapper wrapper = (RuleManagementWrapper) document.getDocument().getNewMaintainableObject().getDataObject();

        String principalId = GlobalVariables.getUserSession().getPerson().getPrincipalId();

        Map<String,String> permissionDetails = new HashMap<String,String>();
        Map<String,String> roleQualifications = new HashMap<String,String>();

        permissionDetails.put(KimConstants.AttributeConstants.VIEW_ID, document.getViewId());

        processPermissionsForAgendaList(principalId, permissionDetails, roleQualifications, wrapper.getAgendas(), "KS-SYS");
    }

    public static void processPermissionsForAgendaList(String principalId, Map<String, String> permissionDetails, Map<String, String> roleQualifications,
                                                       List<AgendaEditor> agendas, String namespace) {
        for(AgendaEditor agenda : agendas) {
            for(RuleEditor rule : agenda.getRuleEditors().values()) {

                if(rule.getPermission() == null) {
                    rule.setPermission(new PermissionWrapper());
                }

                resetActions(rule);

                permissionDetails.put("ruleType", rule.getRuleTypeInfo().getType());

                processPermissionsForRule(principalId, permissionDetails, roleQualifications, rule, namespace);
            }
        }
    }

    public static void processPermissionsForRule(String principalId, Map<String, String> permissionDetails, Map<String, String> roleQualifications,
                                                 RuleEditor rule, String namespace) {

        permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "addEditGroupRule");
        if (permissionService.isAuthorizedByTemplate(principalId, namespace, KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
            rule.getPermission().setAddEditGroupRule(true);
        }

        permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "moveRule");
        if (permissionService.isAuthorizedByTemplate(principalId, namespace, KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
            rule.getPermission().setMoveRule(true);
        }

        permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "copyCutRule");
        if (permissionService.isAuthorizedByTemplate(principalId, namespace, KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
            rule.getPermission().setCopyCutRule(true);
        }

        permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "editLogic");
        if (permissionService.isAuthorizedByTemplate(principalId, namespace, KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
            rule.getPermission().setEditLogic(true);
        }

        permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, "deleteRule");
        if (permissionService.isAuthorizedByTemplate(principalId, namespace, KimConstants.PermissionTemplateNames.PERFORM_ACTION, permissionDetails, roleQualifications)) {
            rule.getPermission().setDeleteRule(true);
        }
    }

    public static void resetActions(RuleEditor ruleEditor) {
        ruleEditor.getPermission().setAddEditGroupRule(false);
        ruleEditor.getPermission().setMoveRule(false);
        ruleEditor.getPermission().setCopyCutRule(false);
        ruleEditor.getPermission().setEditLogic(false);
        ruleEditor.getPermission().setDeleteRule(false);
    }

    protected static PermissionService getPermissionService() {
        if(permissionService==null){
            permissionService = KimApiServiceLocator.getPermissionService();
        }
        return permissionService;
    }
}
