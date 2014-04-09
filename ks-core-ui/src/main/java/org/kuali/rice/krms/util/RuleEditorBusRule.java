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
package org.kuali.rice.krms.util;

import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.bo.GlobalBusinessObject;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.krms.dto.RuleManagementWrapper;
import org.kuali.student.common.uif.rule.KsMaintenanceDocumentRuleBase;

/**
 * This class contains the rules for the AgendaEditor.
 *
 * @author Kuali Student Team
 */
public class RuleEditorBusRule extends KsMaintenanceDocumentRuleBase {

    @Override
    protected boolean primaryKeyCheck(MaintenanceDocument document) {
        // default to success if no failures
        boolean success = true;
        Class<?> dataObjectClass = document.getNewMaintainableObject().getDataObjectClass();

        // Since the dataObject is a wrapper class we need to return the agendaBo instead.
        Object oldBo = ((RuleManagementWrapper) document.getOldMaintainableObject().getDataObject());
        Object newDataObject = ((RuleManagementWrapper) document.getNewMaintainableObject().getDataObject());

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
            if (!KRADServiceLocatorWeb.getLegacyDataAdapter().equalsByPrimaryKeys(oldBo, newDataObject)) {
                // add a complaint to the errors
                putDocumentError(KRADConstants.DOCUMENT_ERRORS,
                        RiceKeyConstants.ERROR_DOCUMENT_MAINTENANCE_PRIMARY_KEYS_CHANGED_ON_EDIT,
                        getHumanReadablePrimaryKeyFieldNames(dataObjectClass));
                success &= false;
            }
        }

        return success;
    }

    @Override
    protected boolean processCustomSaveDocumentBusinessRules(MaintenanceDocument document) {
        return true;
    }

}

