package org.kuali.student.lum.kim.role.type;

import java.util.List;
import java.util.Map;

import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kns.kim.role.DerivedRoleTypeServiceBase;

/**
 * This determines if user should get the affiliation role.
 * 
 */
public class AffiliationDerivedRoleTypeServiceImpl extends
        DerivedRoleTypeServiceBase {

	private static final String EMPLOYEE_STATUS_ACTIVE = "A";
	private List<String> includedAffiliationTypes = null;
	
	@Override
	public boolean hasDerivedRole(String principalId,
			List<String> groupIds, String namespaceCode, String roleName,
			Map<String,String> qualification) {
		Person signedOnPerson = KimApiServiceLocator.getPersonService().getPerson(principalId);
		
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
