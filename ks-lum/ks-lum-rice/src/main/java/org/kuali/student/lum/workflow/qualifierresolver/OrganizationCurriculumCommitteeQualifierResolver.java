/**
 * 
 */
package org.kuali.student.lum.workflow.qualifierresolver;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.role.QualifierResolver;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.student.bo.KualiStudentKimAttributes;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.core.organization.service.OrganizationService;
import org.kuali.student.lum.workflow.node.OrganizationDynamicNode;

/**
 * A qualifier resolver class that is used by the hierarchy routing node {@link OrganizationDynamicNode}.
 * 
 * This qualifier resolver will get the organization id value from inside the current route node instance and use the
 * {@link OrganizationService#getOrgOrgRelationsByOrg(String)} method to find all relations to it. From those relations
 * this class will select the ones that are both active and of the relation type matching
 * {@link AbstractCocOrgQualifierResolver.KUALI_ORG_TYPE_CURRICULUM_PARENT}. Once the list of those relations has been
 * determined this qualifier resolver will select any of the organizations that match the above relation details but
 * also only organizations that are of the type {@link AbstractCocOrgQualifierResolver.KUALI_ORG_COC}. Those
 * organizations will be returned as qualifications with the details being the organization id and the organization
 * short name fields.
 * 
 * If no relation is found that is both active and of the relation type matching
 * {@link AbstractCocOrgQualifierResolver.KUALI_ORG_TYPE_CURRICULUM_PARENT} then this class will use the organization
 * found on the current route node instance as the qualification returned.
 * 
 */
public class OrganizationCurriculumCommitteeQualifierResolver extends AbstractOrganizationServiceQualifierResolver {
    protected static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(OrganizationCurriculumCommitteeQualifierResolver.class);

    @Override
    public List<AttributeSet> resolve(RouteContext routeContext) {
        // get the organization id from the current route node instance and error out if not found
        String orgIdValue = routeContext.getNodeInstance().getNodeState(OrganizationDynamicNode.NODE_STATE_ORG_ID_KEY).getValue();
        if (StringUtils.isBlank(orgIdValue)) {
            throw new RuntimeException("Cannot find valid organization ID in Route Node Instance Node States");
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("orgIdValue = '" + orgIdValue + "'");
        }

        try {
            List<AttributeSet> attributeSets = new ArrayList<AttributeSet>();
            // find the OrgOrgRelationInfo objects associated with the org from the route node instance
            List<OrgOrgRelationInfo> orgRelationInfos = getOrganizationService().getOrgOrgRelationsByOrg(orgIdValue);
            for (OrgOrgRelationInfo orgOrgRelationInfo : orgRelationInfos) {
                // check that the relationship is active
                if (StringUtils.equals("Active", orgOrgRelationInfo.getState())) {
                    // check for the proper relationship type
                    if (StringUtils.equals(AbstractCocOrgQualifierResolver.KUALI_ORG_TYPE_CURRICULUM_PARENT, orgOrgRelationInfo.getType())) {
                        OrgInfo nextNodeOrgInfo = getOrganization(orgOrgRelationInfo.getRelatedOrgId());
                        // check the org type of the related org is the proper org type
                        if (StringUtils.equals(AbstractCocOrgQualifierResolver.KUALI_ORG_COC, nextNodeOrgInfo.getType())) {
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("---- Related Org Relation: " + nextNodeOrgInfo.getId() + " - " + nextNodeOrgInfo.getShortName() + " (" + nextNodeOrgInfo.getLongName() + ")");
                            }
                            AttributeSet attributeSet = new AttributeSet();
                            attributeSet.put(KualiStudentKimAttributes.QUALIFICATION_ORG, nextNodeOrgInfo.getShortName());
                            attributeSet.put(KualiStudentKimAttributes.QUALIFICATION_ORG_ID, nextNodeOrgInfo.getId());
                            attributeSets.add(attributeSet);
                        }
                    }
                }
            }
            // if no org is found then use the org on the route node instance
            if (attributeSets.isEmpty()) {
                OrgInfo currentNodeOrg = getOrganization(orgIdValue);
                AttributeSet attributeSet = new AttributeSet();
                attributeSet.put(KualiStudentKimAttributes.QUALIFICATION_ORG, currentNodeOrg.getShortName());
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
        try {
            return getOrganizationService().getOrganization(orgId);
        } catch (DoesNotExistException e) {
            LOG.error("No valid organization found for id '" + orgId + "'", e);
            throw e;
        }
    }

}
