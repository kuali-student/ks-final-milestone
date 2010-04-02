/**
 * 
 */
package org.kuali.student.lum.kim.role.type;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.support.impl.KimRoleTypeServiceBase;
import org.kuali.rice.student.bo.KualiStudentKimAttributes;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.service.OrganizationService;

/**
 * A Role Type Service that will enable an organization qualifier and parsing of the Organization Hierarchy
 *
 */
public class OrganizationHierarchyRoleTypeService extends KimRoleTypeServiceBase {
	private static final Logger LOG = Logger.getLogger(OrganizationHierarchyRoleTypeService.class);

	public static final String DESCEND_HIERARCHY_TRUE_VALUE = "Y";
    public static final String DESCEND_HIERARCHY_FALSE_VALUE = "N";

    {
    	requiredAttributes.add(KualiStudentKimAttributes.QUALIFICATION_ORG_ID);
    }

	protected OrganizationService orgService;

	@Override
    protected boolean performMatch(AttributeSet inputQualification, AttributeSet roleMemberQualifier) {
        // if no qualification is passed, then we have no basis to reject this
        // (if a null is let through, then we get an NPE below) 
        if ( inputQualification == null || inputQualification.isEmpty() || roleMemberQualifier == null || roleMemberQualifier.isEmpty() ) {
            return true;
        }
        String roleMemberOrganizationShortName = roleMemberQualifier.get(KualiStudentKimAttributes.QUALIFICATION_ORG);
        // if role member qualifiers has a blank or null org short name then assume auto match
        if (StringUtils.isBlank(roleMemberOrganizationShortName)) {
        	return true;
        }
        String inputOrgId = inputQualification.get(KualiStudentKimAttributes.QUALIFICATION_ORG_ID);
		List<AttributeSet> inputSets = new ArrayList<AttributeSet>();
        try {
	        // if role member qualifier says to descend the hierarchy then add in all other org short names from hierarchy
	        if (StringUtils.equals(DESCEND_HIERARCHY_TRUE_VALUE, roleMemberQualifier.get(KualiStudentKimAttributes.DESCEND_HIERARCHY))) {
	        	inputSets.addAll(getHierarchyOrgShortNames(inputOrgId));
	        }
	        // add in the original org short name
        	OrgInfo org = getOrganizationService().getOrganization(inputOrgId);
        	inputSets.add(new AttributeSet(KualiStudentKimAttributes.QUALIFICATION_ORG,org.getShortName()));
    		// check for a match where roleMemberOrganizationShortName exists in one of the attribute sets in the list inputSets
    		return hasMatch(inputSets, roleMemberOrganizationShortName);
        } catch (Exception e) {
        	LOG.error(e);
        	throw new RuntimeException(e);
        }
    }

	protected boolean hasMatch(List<AttributeSet> inputAttributeSets, String roleMemberOrganizationShortName) {
		for (AttributeSet inputSet : inputAttributeSets) {
	        if (StringUtils.equals(roleMemberOrganizationShortName, inputSet.get(KualiStudentKimAttributes.QUALIFICATION_ORG))) {
	        	return true;
	        }
        }
		return false;
	}

	protected List<AttributeSet> getHierarchyOrgShortNames(String inputOrgId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		List<AttributeSet> returnSets = new ArrayList<AttributeSet>();
		returnSets.addAll(getOrgShortNamesForHierarchy(inputOrgId, "kuali.org.hierarchy.Main"));
		returnSets.addAll(getOrgShortNamesForHierarchy(inputOrgId, "kuali.org.hierarchy.Curriculum"));
		return returnSets;
	}

	protected List<AttributeSet> getOrgShortNamesForHierarchy(String inputOrgId, String orgHierarchy) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<AttributeSet> returnSets = new ArrayList<AttributeSet>();
    	List<String> ids = getOrganizationService().getAllAncestors(inputOrgId, orgHierarchy);
    	List<OrgInfo> orgs = getOrganizationService().getOrganizationsByIdList(ids);
    	for (OrgInfo orgInfo : orgs) {
            returnSets.add(new AttributeSet(KualiStudentKimAttributes.QUALIFICATION_ORG,orgInfo.getShortName()));
        }
    	return returnSets;
	}

	protected OrganizationService getOrganizationService() {
		if (null == orgService) {
		   	orgService = (OrganizationService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/organization","OrganizationService"));
		}
		return orgService;
	}

}
