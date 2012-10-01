package org.kuali.student.core.enumerationmanagement.document;

import java.util.Map;

import org.kuali.rice.kns.document.MaintenanceDocument;
import org.kuali.rice.kns.maintenance.KualiMaintainableImpl;
import org.kuali.student.core.enumerationmanagement.bo.EnumeratedValue;
import org.kuali.student.core.enumerationmanagement.bo.Enumeration;

public class EnumerationMaintainableImpl extends KualiMaintainableImpl {

	private static final long serialVersionUID = 7426377658194232269L;

	@Override
	public void processAfterCopy(MaintenanceDocument document, Map<String, String[]> parameters) {
		super.processAfterCopy(document, parameters);
		
		Enumeration enumeration = (Enumeration) document.getNewMaintainableObject().getBusinessObject();
		
		if(enumeration.getEnumeratedValueList() != null) {
			for(EnumeratedValue value : enumeration.getEnumeratedValueList()) {
				value.setId(null);
			}
		}
	}

}
