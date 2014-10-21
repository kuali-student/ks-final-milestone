/**
 *
 */
package org.kuali.student.lum.workflow.qualifierresolver;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.student.bo.KualiStudentKimAttributes;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.lum.workflow.node.OrganizationDynamicNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A qualifier resolver class that is used by the hierarchy routing node {@link OrganizationDynamicNode}.
 *
 * This qualifier resolver will get the organization id value from inside the current route node instance and use the
 * {@link OrganizationService#getOrgOrgRelationsByOrg(String)} method to find all relations to it. From those relations
 * this class will select the ones that are both active and of the relation type matching
 * {@link AbstractOrganizationServiceQualifierResolver.KUALI_ORG_TYPE_CURRICULUM_PARENT}. Once the list of those relations has been
 * determined this qualifier resolver will select any of the organizations that match the above relation details but
 * also only organizations that are of the type {@link AbstractOrganizationServiceQualifierResolver.KUALI_ORG_COC}. Those
 * organizations will be returned as qualifications with the details being the organization id and the organization
 * short name fields.
 *
 * If no relation is found that is both active and of the relation type matching
 * {@link AbstractOrganizationServiceQualifierResolver.KUALI_ORG_TYPE_CURRICULUM_PARENT} then this class will use the organization
 * found on the current route node instance as the qualification returned.
 *
 * @deprecated - this was written as a test case that was never implemented (see also {@link OrganizationDynamicNode})
 */
@Deprecated
public class OrganizationCurriculumCommitteeQualifierResolver extends AbstractOrganizationServiceQualifierResolver {
    protected static final Logger LOG = LoggerFactory.getLogger(OrganizationCurriculumCommitteeQualifierResolver.class);

    @Override
    public List<Map<String,String>> resolve(RouteContext routeContext) {
        // get the organization id from the current route node instance and error out if not found
        String orgIdValue = routeContext.getNodeInstance().getNodeState(OrganizationDynamicNode.NODE_STATE_ORG_ID_KEY).getValue();
        if (StringUtils.isBlank(orgIdValue)) {
            throw new RuntimeException("Cannot find valid organization ID in Route Node Instance Node States");
        }
        LOG.debug("orgIdValue = '{}'", orgIdValue);

        try {
            List<Map<String,String>> attributeSets = new ArrayList<Map<String,String>>();
            // find the OrgOrgRelationInfo objects associated with the org from the route node instance
            List<OrgOrgRelationInfo> orgRelationInfos = getOrganizationService().getOrgOrgRelationsByOrg(orgIdValue, ContextUtils.getContextInfo());
            for (OrgOrgRelationInfo orgOrgRelationInfo : orgRelationInfos) {
                // check that the relationship is active
                if (StringUtils.equals("Active", orgOrgRelationInfo.getStateKey())) {
                    // check for the proper relationship type
                    if (StringUtils.equals(AbstractOrganizationServiceQualifierResolver.KUALI_ORG_TYPE_CURRICULUM_PARENT, orgOrgRelationInfo.getTypeKey())) {
                        OrgInfo nextNodeOrgInfo = getOrganization(orgOrgRelationInfo.getRelatedOrgId());
                        // check the org type of the related org is the proper org type
                        if (StringUtils.equals(AbstractOrganizationServiceQualifierResolver.KUALI_ORG_COC, nextNodeOrgInfo.getTypeKey())) {
                            LOG.debug("---- Related Org Relation: {} - {} ({})", nextNodeOrgInfo.getId(), nextNodeOrgInfo.getShortName(), nextNodeOrgInfo.getLongName());
                            Map<String,String> attributeSet = new LinkedHashMap<String,String>();
                            attributeSet.put(KualiStudentKimAttributes.QUALIFICATION_ORG_ID, nextNodeOrgInfo.getId());
                            attributeSets.add(attributeSet);
                        }
                    }
                }
            }
            // if no org is found then use the org on the route node instance
            if (attributeSets.isEmpty()) {
                OrgInfo currentNodeOrg = getOrganization(orgIdValue);
                Map<String,String> attributeSet = new LinkedHashMap<String,String>();
                attributeSet.put(KualiStudentKimAttributes.QUALIFICATION_ORG_ID, currentNodeOrg.getId());
                attributeSets.add(attributeSet);
            }
            return attributeSets;
        } catch (Exception e) {
            LOG.error("Error getting organization(s) or organization relations", e);
            throw new RuntimeException(e);
        }
    }

    protected OrgInfo getOrganization(String orgId) throws Exception {
//        try {
        	OrgInfo orgInfo = getOrganizationService().getOrg(orgId, null);
            return orgInfo;
//        } catch (DoesNotExistException e) {
//            LOG.error("No valid organization found for id '" + orgId + "'", e);
//            throw e;
//        }
    }

}
