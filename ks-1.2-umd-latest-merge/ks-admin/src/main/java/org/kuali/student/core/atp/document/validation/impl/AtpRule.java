/*
 * Copyright 2006 The Kuali Foundation
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
package org.kuali.student.core.atp.document.validation.impl;

import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.student.core.atp.bo.Atp;

/**
 * Implements the business rules for {@link Atp} maintenance document
 */
public class AtpRule extends MaintenanceDocumentRuleBase {
	protected Atp oldAtp;
	protected Atp newAtp;

	/**
	 * Sets the convenience objects like newDefinition and oldDefinition, so you have short and easy handles
	 * to the new and old objects contained in the maintenance document. It also calls the
	 * BusinessObjectBase.refresh(), which will attempt to load all sub-objects from the DB by their primary
	 * keys, if available.
	 * 
	 * @see org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase#setupConvenienceObjects()
	 */
	@Override
	public void setupConvenienceObjects() {

		// setup oldAccount convenience objects, make sure all possible sub-objects are populated
		oldAtp = (Atp) super.getOldBo();

		// setup newAccount convenience objects, make sure all possible sub-objects are populated
		newAtp = (Atp) super.getNewBo();
	}

	/**
	 * Performs rules checks on document route This rule fails on business rule failures
	 * 
	 * @see org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase#processCustomRouteDocumentBusinessRules(org.kuali.rice.kns.document.MaintenanceDocument)
	 */
	@Override
	protected boolean processCustomRouteDocumentBusinessRules(MaintenanceDocument document) {
		//        if ((ObjectUtils.isNotNull(newDefinition.getFinancialObject()) && !newDefinition.getFinancialObject().isFinancialObjectActiveCode()) || ObjectUtils.isNull(newDefinition.getFinancialObject())) {
		//            putFieldError("financialObjectCode", KFSKeyConstants.ERROR_DOCUMENT_OFFSETDEFMAINT_INACTIVE_OBJ_CODE_FOR_DOCTYPE, new String[] { newDefinition.getFinancialObjectCode(), evaluator.getParameterValuesForMessage() });
		//            success &= false;
		//        }

		return true;
	}

}
