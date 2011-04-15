package org.kuali.student.core.subjectcode.document.validation.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.student.core.subjectcode.bo.SubjectCode;

public class SubjectCodeRule extends MaintenanceDocumentRuleBase{
	private BusinessObjectService businessObjectService;
	
	protected BusinessObjectService getBusinessObjectService() {
        if (businessObjectService == null) {
            businessObjectService = KNSServiceLocator.getBusinessObjectService();
        }
        return  businessObjectService;
    }
	@Override
	protected boolean isDocumentValidForSave(
			MaintenanceDocument maintenanceDocument) {
		SubjectCode subjectCode = (SubjectCode) super.getNewBo();
		if(subjectCode!=null){
			Map fieldValues = new HashMap();
			fieldValues.put("code", subjectCode.getCode());
			Collection results = getBusinessObjectService().findMatching(SubjectCode.class, fieldValues);
			if(results!=null&&results.size()>0){
				putFieldError("code", "error.duplicate.entry", "Subject Code");
				return false;
			}
		}		
		return super.isDocumentValidForSave(maintenanceDocument);
	}
}
