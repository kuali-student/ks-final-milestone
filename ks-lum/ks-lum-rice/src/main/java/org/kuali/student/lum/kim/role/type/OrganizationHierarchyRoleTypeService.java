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

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.web.format.BooleanFormatter;
import org.kuali.rice.kns.kim.role.RoleTypeServiceBase;
import org.kuali.rice.student.bo.KualiStudentKimAttributes;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A Role Type Service that will enable an organization qualifier and parsing of the Organization Hierarchy
 *
 */
public class OrganizationHierarchyRoleTypeService extends RoleTypeServiceBase {
    private static final Logger LOG = LoggerFactory.getLogger(OrganizationHierarchyRoleTypeService.class);

    public static final String DESCEND_HIERARCHY_TRUE_VALUE = "Y";
    public static final String DESCEND_HIERARCHY_FALSE_VALUE = "N";

    protected OrganizationService orgService;

    @Override
    protected boolean performMatch(Map<String, String> inputQualification, Map<String, String> roleMemberQualifier) {
        // if no qualification is passed, then we have no basis to reject this
        // (if a null is let through, then we get an NPE below)
        if (inputQualification == null || inputQualification.isEmpty() || roleMemberQualifier == null || roleMemberQualifier.isEmpty()) {
            return true;
        }
        // String roleMemberOrganizationShortName = roleMemberQualifier.get(KualiStudentKimAttributes.QUALIFICATION_ORG);
        String roleMemberOrganizationId = roleMemberQualifier.get(KualiStudentKimAttributes.QUALIFICATION_ORG_ID);
        // if role member qualifiers has a blank or null orgId then assume auto match
        if (StringUtils.isBlank(roleMemberOrganizationId)) {
            return true;
        }
        String inputOrgId = inputQualification.get(KualiStudentKimAttributes.QUALIFICATION_ORG_ID);
        if(inputOrgId == null){
            //if no qualifier for org is passed then mark this as true
            return true;
        }
        List<Map<String, String>> inputSets = new ArrayList<Map<String, String>>();
        List<String> inputOrgIds = new ArrayList<String>(Arrays.asList(inputOrgId.split(",")));
        try {
            // if role member qualifier says to descend the hierarchy then add in all other org short names from hierarchy
            BooleanFormatter format = new BooleanFormatter();
            Boolean b = (Boolean) format.convertFromPresentationFormat(roleMemberQualifier.get(KualiStudentKimAttributes.DESCEND_HIERARCHY));
            if (b != null && b.booleanValue()) {
                // inputSets.addAll(getHierarchyOrgShortNames(inputOrgId));
                for (String orgId : inputOrgIds) {
                    inputSets.addAll(getHierarchyOrgIds(orgId, ContextUtils.getContextInfo()));
                // check for a match where roleMemberOrganizationId exists in one of the attribute sets in the list inputSets
                    if (hasMatch(inputSets, roleMemberOrganizationId)) {
                        return true;
                    }
                }
            } else {
                for (String orgId : inputOrgIds) {
                    if (orgId.equals(roleMemberOrganizationId)) {
                        return true;
                    }
                }
            }
            /*
	        // add in the original org short name
	        if(inputOrgId!=null){
	            OrgInfo org = getOrganizationService().getOrganization(inputOrgId);
	            inputSets.add(new AttributeSet(KualiStudentKimAttributes.QUALIFICATION_ORG,org.getShortName()));
	        }
             */
            return false;
        } catch (Exception e) {
            LOG.error("Exception occurred", e);
            throw new RuntimeException(e);
        }
    }

    protected boolean hasMatch(List<Map<String, String>> inputAttributeSets, String roleMemberOrganizationId) {
        for (Map<String, String> inputSet : inputAttributeSets) {
            if (StringUtils.equals(roleMemberOrganizationId, inputSet.get(KualiStudentKimAttributes.QUALIFICATION_ORG_ID))) {
                return true;
            }
        }
        return false;
    }

    protected List<Map<String, String>> getHierarchyOrgIds(String inputOrgId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<Map<String, String>> returnSets = new ArrayList<Map<String, String>>();
        returnSets.addAll(getOrgIdsForHierarchy(inputOrgId, "kuali.org.hierarchy.Main", contextInfo));
        returnSets.addAll(getOrgIdsForHierarchy(inputOrgId, "kuali.org.hierarchy.Curriculum", contextInfo));
        return returnSets;
    }

    protected List<Map<String, String>> getOrgIdsForHierarchy(String inputOrgId, String orgHierarchy, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        List<Map<String, String>> returnSets = new ArrayList<Map<String, String>>();
        List<String> ids = getOrganizationService().getAllAncestors(inputOrgId, orgHierarchy, contextInfo);
        if (ids.size() > 0) {
            List<OrgInfo> orgs = getOrganizationService().getOrgsByIds(ids, contextInfo);
            for (OrgInfo orgInfo : orgs) {
                Map<String, String> attrs = new LinkedHashMap<String, String>();
                attrs.put(KualiStudentKimAttributes.QUALIFICATION_ORG_ID, orgInfo.getId());
                returnSets.add(attrs);
            }
        }
        return returnSets;
    }

 /*   protected List<AttributeSet> getHierarchyOrgShortNames(String inputOrgId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<AttributeSet> returnSets = new ArrayList<AttributeSet>();
        returnSets.addAll(getOrgShortNamesForHierarchy(inputOrgId, "kuali.org.hierarchy.Main"));
        returnSets.addAll(getOrgShortNamesForHierarchy(inputOrgId, "kuali.org.hierarchy.Curriculum"));
        return returnSets;
    }

    protected List<AttributeSet> getOrgShortNamesForHierarchy(String inputOrgId, String orgHierarchy) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        List<AttributeSet> returnSets = new ArrayList<AttributeSet>();
        List<String> ids = getOrganizationService().getAllAncestors(inputOrgId, orgHierarchy);
        if (ids.size() > 0) {
            List<OrgInfo> orgs = getOrganizationService().getOrganizationsByIdList(ids);
            for (OrgInfo orgInfo : orgs) {
                returnSets.add(new AttributeSet(KualiStudentKimAttributes.QUALIFICATION_ORG, orgInfo.getShortName()));
            }
        }
        return returnSets;
    }*/

    protected OrganizationService getOrganizationService() {
        if (null == orgService) {
            orgService = (OrganizationService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/organization", "OrganizationService"));
        }
        return orgService;
    }

}
