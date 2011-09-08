package org.kuali.student.lum.kim.role.type;

import java.util.List;

import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kim.bo.Person;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.KIMServiceLocator;
import org.kuali.rice.kim.service.support.impl.KimDerivedRoleTypeServiceBase;

public class AffiliationDerivedRoleTypeServiceImpl extends
	KimDerivedRoleTypeServiceBase {

	private static final String EMPLOYEE_STATUS_ACTIVE = "A";
	private List<String> includedAffiliationTypes = null;
	private List<String> includedRouteStatuses = null;
	
	@Override
	public boolean hasApplicationRole(String principalId,
			List<String> groupIds, String namespaceCode, String roleName,
			AttributeSet qualification) {
		Person signedOnPerson = KIMServiceLocator.getPersonService().getPerson(principalId);
		
		//Check if the route status is in the list of allowed statuses
		String routeStatus = KEWServiceLocator.getRouteHeaderService().getDocumentStatus(Long.parseLong(qualification.get("documentNumber")));
		
		
		// check to see if principalID is a staff member or faculty memeber
		for (String affiliationType : includedAffiliationTypes) {
			if (signedOnPerson.hasAffiliationOfType(affiliationType) && (signedOnPerson.getEmployeeStatusCode().equals(EMPLOYEE_STATUS_ACTIVE))) {
				if(includedRouteStatuses!=null && includedRouteStatuses.contains(routeStatus)){
					return true;
				}
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

	public void setIncludedRouteStatuses(List<String> includedRouteStatuses) {
		this.includedRouteStatuses = includedRouteStatuses;
	}

}