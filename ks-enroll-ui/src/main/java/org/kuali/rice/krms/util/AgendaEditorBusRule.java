/**
 * Copyright 2005-2012 The Kuali Foundation
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
package org.kuali.rice.krms.util;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.bo.GlobalBusinessObject;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.framework.type.ActionTypeService;
import org.kuali.rice.krms.impl.authorization.AgendaAuthorizationService;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.rice.krms.impl.util.KRMSPropertyConstants;
import org.kuali.student.enrollment.class1.krms.dto.EnrolAgendaEditor;
import org.kuali.student.enrollment.class1.krms.dto.EnrolRuleEditor;

/**
 * This class contains the rules for the AgendaEditor.
 */
public class AgendaEditorBusRule extends MaintenanceDocumentRuleBase {

    @Override
    protected boolean primaryKeyCheck(MaintenanceDocument document) {
        // default to success if no failures
        boolean success = true;
        Class<?> dataObjectClass = document.getNewMaintainableObject().getDataObjectClass();

        // Since the dataObject is a wrapper class we need to return the agendaBo instead.
        Object oldBo = ((EnrolAgendaEditor) document.getOldMaintainableObject().getDataObject());
        Object newDataObject = ((EnrolAgendaEditor) document.getNewMaintainableObject().getDataObject());

        // We dont do primaryKeyChecks on Global Business Object maintenance documents. This is
        // because it doesnt really make any sense to do so, given the behavior of Globals. When a
        // Global Document completes, it will update or create a new record for each BO in the list.
        // As a result, there's no problem with having existing BO records in the system, they will
        // simply get updated.
        if (newDataObject instanceof GlobalBusinessObject) {
            return success;
        }

        // fail and complain if the person has changed the primary keys on
        // an EDIT maintenance document.
        if (document.isEdit()) {
            if (!getDataObjectMetaDataService().equalsByPrimaryKeys(oldBo, newDataObject)) {
                // add a complaint to the errors
                putDocumentError(KRADConstants.DOCUMENT_ERRORS,
                        RiceKeyConstants.ERROR_DOCUMENT_MAINTENANCE_PRIMARY_KEYS_CHANGED_ON_EDIT,
                        getHumanReadablePrimaryKeyFieldNames(dataObjectClass));
                success &= false;
            }
        }

        // fail and complain if the person has selected a new object with keys that already exist
        // in the DB.
        else if (document.isNew()) {

            // TODO: check for valid primary keys.
        }

        return success;
    }

    @Override
    protected boolean processCustomSaveDocumentBusinessRules(MaintenanceDocument document) {
        return true;
    }

    /**
     * Check that the rule type is valid when specified.
     * @param ruleTypeId, the type id
     * @param contextId, the contextId the action needs to belong to.
     * @return true if valid, false otherwise.
     */
    private boolean validRuleType(String ruleTypeId, String contextId) {
        if (StringUtils.isBlank(ruleTypeId)) {
            return true;
        }

        if (getKrmsTypeRepositoryService().getRuleTypeByRuleTypeIdAndContextId(ruleTypeId, contextId) != null) {
            return true;
        } else {
            this.putFieldError(KRMSPropertyConstants.Rule.TYPE, "error.rule.invalidType");
            return false;
        }
    }

    /**
     * Check that the rule action type is valid when specified.
     * @param typeId, the action type id
     * @parm contextId, the contextId the action needs to belong to.
     * @return true if valid, false otherwise.
     */
    private boolean validRuleActionType(String typeId, String contextId) {
        if (StringUtils.isBlank(typeId)) {
            return true;
        }

        if (getKrmsTypeRepositoryService().getActionTypeByActionTypeIdAndContextId(typeId, contextId) != null) {
            return true;
        } else {
            this.putFieldError(KRMSPropertyConstants.Action.TYPE, "error.action.invalidType");
            return false;
        }
    }

    /**
     * Check that a action name is specified.
     */
    private boolean validRuleActionName(String name) {
        if (StringUtils.isNotBlank(name)) {
            return true;
        } else {
            this.putFieldError(KRMSPropertyConstants.Action.NAME, "error.action.missingName");
            return false;
        }
    }

    public KrmsTypeRepositoryService getKrmsTypeRepositoryService() {
        return KrmsRepositoryServiceLocator.getKrmsTypeRepositoryService();
    }

    public ActionTypeService getActionTypeService(String serviceName) {
        return (ActionTypeService)KrmsRepositoryServiceLocator.getService(serviceName);
    }

    public AgendaAuthorizationService getAgendaAuthorizationService() {
        return KrmsRepositoryServiceLocator.getAgendaAuthorizationService();
    }

}

