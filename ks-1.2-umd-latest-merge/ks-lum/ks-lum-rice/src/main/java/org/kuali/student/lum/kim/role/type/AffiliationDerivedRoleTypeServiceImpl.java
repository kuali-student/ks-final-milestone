package org.kuali.student.lum.kim.role.type;

import java.util.List;

import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kim.service.support.impl.KimDerivedRoleTypeServiceBase;

/**
 * This determines if user should get the affiliation role.
 * 
 */
public class AffiliationDerivedRoleTypeServiceImpl extends
	KimDerivedRoleTypeServiceBase {

	private static final String EMPLOYEE_STATUS_ACTIVE = "A";
	private List<String> includedAffiliationTypes = null;
	
	@Override
	public boolean hasApplicationRole(String principalId,
			List<String> groupIds, String namespaceCode, String roleName,
			AttributeSet qualification) {
		Person signedOnPerson = KIMServiceLocator.getPersonService().getPerson(principalId);			
		
		// check to see if principalID is a staff member or faculty memeber
		for (String affiliationType : includedAffiliationTypes) {
			if (signedOnPerson.hasAffiliationOfType(affiliationType) && (signedOnPerson.getEmployeeStatusCode().equals(EMPLOYEE_STATUS_ACTIVE))) {
				return true;
			}
		}	
		return false;
	}

	public List<String> getIncludedAffiliationTypes() {
		return includedAffiliationTypes;
	}

	public void setIncludedAffiliationTypes(List<String> includedAffiliationTypes) {
		this.includedAffiliationTypes = includedAffiliationTypes;
	}

}