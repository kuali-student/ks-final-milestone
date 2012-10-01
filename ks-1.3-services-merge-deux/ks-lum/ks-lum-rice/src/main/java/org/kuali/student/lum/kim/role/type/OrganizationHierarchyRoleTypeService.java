/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

/**
 * 
 */
package org.kuali.student.lum.kim.role.type;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.web.format.BooleanFormatter;
import org.kuali.rice.kns.kim.role.RoleTypeServiceBase;
import org.kuali.rice.student.bo.KualiStudentKimAttributes;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.exceptions.PermissionDeniedException;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.service.OrganizationService;

/**
 * A Role Type Service that will enable an organization qualifier and parsing of the Organization Hierarchy
 *
 */
public class OrganizationHierarchyRoleTypeService extends RoleTypeServiceBase {
	private static final Logger LOG = Logger.getLogger(OrganizationHierarchyRoleTypeService.class);

	public static final String DESCEND_HIERARCHY_TRUE_VALUE = "Y";
    public static final String DESCEND_HIERARCHY_FALSE_VALUE = "N";

	protected OrganizationService orgService;

	@Override
    protected boolean performMatch(Map<String,String> inputQualification, Map<String,String> roleMemberQualifier) {
        // if no qualification is passed, then we have no basis to reject this
        // (if a null is let through, then we get an NPE below) 
        if ( inputQualification == null || inputQualification.isEmpty() || roleMemberQualifier == null || roleMemberQualifier.isEmpty() ) {
            return true;
        }
//      String roleMemberOrganizationShortName = roleMemberQualifier.get(KualiStudentKimAttributes.QUALIFICATION_ORG);
        String roleMemberOrganizationId = roleMemberQualifier.get(KualiStudentKimAttributes.QUALIFICATION_ORG_ID); 
        // if role member qualifiers has a blank or null orgId then assume auto match
        if (StringUtils.isBlank(roleMemberOrganizationId)) {
        	return true;
        }
        String inputOrgId = inputQualification.get(KualiStudentKimAttributes.QUALIFICATION_ORG_ID);
		List<Map<String,String>> inputSets = new ArrayList<Map<String,String>>();
        try {
	        // if role member qualifier says to descend the hierarchy then add in all other org short names from hierarchy
            BooleanFormatter format = new BooleanFormatter();
            Boolean b = (Boolean)format.convertFromPresentationFormat(roleMemberQualifier.get(KualiStudentKimAttributes.DESCEND_HIERARCHY));
	        if (b.booleanValue()) {
//	        	inputSets.addAll(getHierarchyOrgShortNames(inputOrgId));
	            inputSets.addAll(getHierarchyOrgIds(inputOrgId));
	        }
/*
	        // add in the original org short name
	        if(inputOrgId!=null){
	            OrgInfo org = getOrganizationService().getOrganization(inputOrgId);
	            inputSets.add(new Map<String,String>(KualiStudentKimAttributes.QUALIFICATION_ORG,org.getShortName()));
	        }
*/	        
    		// check for a match where roleMemberOrganizationId exists in one of the attribute sets in the list inputSets
    		return hasMatch(inputSets, roleMemberOrganizationId);
        } catch (Exception e) {
        	LOG.error(e);
        	throw new RuntimeException(e);
        }
    }

	protected boolean hasMatch(List<Map<String,String>> inputAttributeSets, String roleMemberOrganizationId) {
		for (Map<String,String> inputSet : inputAttributeSets) {
	        if (StringUtils.equals(roleMemberOrganizationId, inputSet.get(KualiStudentKimAttributes.QUALIFICATION_ORG_ID))) {
	        	return true;
	        }
        }
		return false;
	}

    protected List<Map<String,String>> getHierarchyOrgIds(String inputOrgId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<Map<String,String>> returnSets = new ArrayList<Map<String,String>>();
        returnSets.addAll(getOrgIdsForHierarchy(inputOrgId, "kuali.org.hierarchy.Main"));
        returnSets.addAll(getOrgIdsForHierarchy(inputOrgId, "kuali.org.hierarchy.Curriculum"));
        return returnSets;
    }
    
    protected List<Map<String,String>> getOrgIdsForHierarchy(String inputOrgId, String orgHierarchy) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        List<Map<String,String>> returnSets = new ArrayList<Map<String,String>>();
        List<String> ids = getOrganizationService().getAllAncestors(inputOrgId, orgHierarchy);
        if (ids.size() > 0) {
            List<OrgInfo> orgs = getOrganizationService().getOrganizationsByIdList(ids);
            for (OrgInfo orgInfo : orgs) {
                Map<String, String> attrs = new LinkedHashMap<String,String>();
                attrs.put(KualiStudentKimAttributes.QUALIFICATION_ORG_ID, orgInfo.getId());
                returnSets.add(attrs);                        
            }
        }
        return returnSets;
    }

/*    
    
	protected List<Map<String,String>> getHierarchyOrgShortNames(String inputOrgId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		List<Map<String,String>> returnSets = new ArrayList<Map<String,String>>();
		returnSets.addAll(getOrgShortNamesForHierarchy(inputOrgId, "kuali.org.hierarchy.Main"));
		returnSets.addAll(getOrgShortNamesForHierarchy(inputOrgId, "kuali.org.hierarchy.Curriculum"));
		return returnSets;
	}

	protected List<Map<String,String>> getOrgShortNamesForHierarchy(String inputOrgId, String orgHierarchy) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
		List<Map<String,String>> returnSets = new ArrayList<Map<String,String>>();
    	List<String> ids = getOrganizationService().getAllAncestors(inputOrgId, orgHierarchy);
    	if (ids.size() > 0) {
            List<OrgInfo> orgs = getOrganizationService().getOrganizationsByIdList(ids);
            for (OrgInfo orgInfo : orgs) {
                returnSets.add(new Map<String,String>(KualiStudentKimAttributes.QUALIFICATION_ORG, orgInfo.getShortName()));
            }
        }
    	return returnSets;
	}
*/
	protected OrganizationService getOrganizationService() {
		if (null == orgService) {
		   	orgService = (OrganizationService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/organization","OrganizationService"));
		}
		return orgService;
	}

}
