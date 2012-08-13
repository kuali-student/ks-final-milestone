package org.kuali.student.core.subjectcode.document.validation.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krad.service.KRADServiceLocator;
import org.kuali.student.core.subjectcode.bo.SubjectCode;

public class SubjectCodeRule extends MaintenanceDocumentRuleBase{
	private BusinessObjectService businessObjectService;
	
	protected BusinessObjectService getBusinessObjectService() {
        if (businessObjectService == null) {
            businessObjectService = KRADServiceLocator.getBusinessObjectService();
        }
        return  businessObjectService;
    }
	@Override
	protected boolean isDocumentValidForSave(
			MaintenanceDocument maintenanceDocument) {
		SubjectCode newSubjectCode = (SubjectCode) super.getNewBo();
		SubjectCode oldSubjectCode = (SubjectCode) super.getOldBo();
		if(newSubjectCode!=null && (oldSubjectCode==null||!newSubjectCode.getCode().equals(oldSubjectCode.getCode()))){
			Map fieldValues = new HashMap();
			fieldValues.put("code", newSubjectCode.getCode());
			Collection results = getBusinessObjectService().findMatching(SubjectCode.class, fieldValues);
			if(results!=null&&results.size()>0){
				putFieldError("code", "error.duplicate.entry", "Subject Code");
				return false;
			}
		}		
		return super.isDocumentValidForSave(maintenanceDocument);
	}
}
