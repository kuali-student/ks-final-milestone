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
package org.kuali.student.enrollment.class1.krms.view;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.Group;
import org.kuali.rice.krad.uif.element.Action;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.rice.krms.dto.RuleManagementWrapper;
import org.kuali.rice.krms.dto.RuleTypeInfo;
import org.kuali.student.enrollment.class1.krms.dto.CORuleManagementWrapper;
import org.kuali.student.enrollment.main.view.KsViewAuthorizerBase;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kuali Student Team
 */
public class KSKRMSViewAuthorizer extends KsViewAuthorizerBase {

    private static final String KRMS_COMPARE_ACTION_EVENT = "compare";
    private static final String KRMS_ADD_EDIT_ACTION_EVENT = "addEditRequisite";
    private static final String KRMS_DELETE_ACTION_EVENT = "deleteRequisite";
    private static final String KRMS_SUBMIT_ACTION_EVENT = "submitRequisite";

    public boolean canPerformAction(View view, ViewModel model, Action action, String actionEvent,
                                    String actionId, Person user) {
        // check action authz flag is set
        if (!action.getActionSecurity().isPerformActionAuthz()) {
            return true;
        }

        MaintenanceDocumentForm maintenanceDocumentForm = (MaintenanceDocumentForm) model;
        CORuleManagementWrapper wrapper = (CORuleManagementWrapper) maintenanceDocumentForm.getDocument().getNewMaintainableObject().getDataObject();

        String ruleType = null;
        if(wrapper.getRuleEditor() != null) {
            ruleType = wrapper.getRuleEditor().getRuleTypeInfo().getType();
        } else {
            ruleType = action.getActionParameters().get("ruleType");
        }

        Map<String,String> permissionDetails = new HashMap<String,String>();
        Map<String,String> roleQualifications = new HashMap<String,String>();

        String socState = StringUtils.lowerCase(wrapper.getContextBar().getTermSocState());

        if(!actionEvent.equals(KRMS_COMPARE_ACTION_EVENT)) {
            roleQualifications.put("offeringAdminOrgId", wrapper.getAdminOrg());
        }

        permissionDetails.put("socState", socState);
        if(ruleType != null) {
            permissionDetails.put("ruleType", ruleType);
        }

        if (StringUtils.isNotBlank(actionEvent)) {
            permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, actionEvent);
        }

        if(KRMS_SUBMIT_ACTION_EVENT.equals(actionEvent)){
            return isAuthorizedToUpdate(wrapper, view, action, model, user, permissionDetails, roleQualifications);
        } else {
            return isAuthorizedByTemplate(view, action, model, KimConstants.PermissionTemplateNames.PERFORM_ACTION,
                user, permissionDetails, roleQualifications, false);
        }

    }

    private boolean isAuthorizedToUpdate(RuleManagementWrapper ruleWrapper, View view, Component action, ViewModel model, Person user,
                                         Map<String, String> permissionDetails, Map<String, String> roleQualifications) {

        for(AgendaEditor agenda : ruleWrapper.getAgendas()){
            for(RuleTypeInfo ruleType : agenda.getAgendaTypeInfo().getRuleTypes()){

                permissionDetails.put("ruleType", ruleType.getType());

                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, KRMS_ADD_EDIT_ACTION_EVENT);
                if(isAuthorizedByTemplate(view, action, model, KimConstants.PermissionTemplateNames.PERFORM_ACTION,
                        user, permissionDetails, roleQualifications, false)){
                    return true;
                }

                permissionDetails.put(KimConstants.AttributeConstants.ACTION_EVENT, KRMS_DELETE_ACTION_EVENT);
                if(isAuthorizedByTemplate(view, action, model, KimConstants.PermissionTemplateNames.PERFORM_ACTION,
                        user, permissionDetails, roleQualifications, false)){
                    return true;
                }
            }
        }

        return false;
    }

    public boolean canEditGroup(View view, ViewModel model, Group group, String groupId, Person user) {
        // check edit group authz flag is set
        if (!group.getComponentSecurity().isEditAuthz()) {
            return true;
        }

        MaintenanceDocumentForm maintenanceDocumentForm = (MaintenanceDocumentForm) model;
        CORuleManagementWrapper wrapper = (CORuleManagementWrapper) maintenanceDocumentForm.getDocument().getNewMaintainableObject().getDataObject();

        String ruleType = null;
        if(wrapper.getRuleEditor() != null) {
            ruleType = wrapper.getRuleEditor().getRuleTypeInfo().getType();
        }

        Map<String, String> additionalPermissionDetails = new HashMap<String, String>();
        Map<String,String> roleQualifications = new HashMap<String,String>();

        String socState = StringUtils.lowerCase(wrapper.getContextBar().getTermSocState());

        roleQualifications.put("offeringAdminOrgId", wrapper.getAdminOrg());

        additionalPermissionDetails.put(KimConstants.AttributeConstants.NAMESPACE_CODE, "KS-ENR");
        additionalPermissionDetails.put(KimConstants.AttributeConstants.VIEW_ID, model.getViewId());
        additionalPermissionDetails.put(KimConstants.AttributeConstants.GROUP_ID, groupId);
        additionalPermissionDetails.put("socState", socState);

        if(ruleType != null) {
            additionalPermissionDetails.put("ruleType", ruleType);
        }

        return isAuthorizedByTemplate(view, group, model, KimConstants.PermissionTemplateNames.EDIT_GROUP, user, additionalPermissionDetails,
                roleQualifications, false);
    }

}
