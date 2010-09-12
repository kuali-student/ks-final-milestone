/**
 * 
 */
package org.kuali.student.lum.workflow.qualifierresolver;

import java.util.List;

import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.student.bo.KualiStudentKimAttributes;
import org.kuali.student.lum.workflow.node.OrganizationDynamicNode;

/**
 * A qualifier resolver class that uses the hierarchy routing node to get details about how to qualify the organization values
 *
 */
public class OrganizationCommitteeQualifierResolver extends AbstractCocOrgQualifierResolver {
	protected static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(OrganizationCommitteeQualifierResolver.class);

	@Override
	public List<AttributeSet> resolve(RouteContext routeContext) {
		List<AttributeSet> attributeSets = super.resolve(routeContext);
		String orgId = null;
		if (attributeSets.size() > 0 && attributeSets.get(0).size() > 0) {
			orgId = getAttribute(attributeSets, KualiStudentKimAttributes.QUALIFICATION_ORG_ID);
		}
		String orgType = routeContext.getNodeInstance().getNodeState(OrganizationDynamicNode.NODE_STATE_ORG_TYPE_CODE).getValue();
		String orgShortNameKey = routeContext.getNodeInstance().getNodeState(OrganizationDynamicNode.NODE_STATE_ORG_QUAL_SHORT_NAME_KEY).getValue();
		String orgIdKey = routeContext.getNodeInstance().getNodeState(OrganizationDynamicNode.NODE_STATE_ORG_QUAL_ID_KEY).getValue();
        LOG.info("orgTypeCode = '" + orgType + "'");
        LOG.info("orgShortNameKey = '" + orgShortNameKey + "'");
        LOG.info("orgIdKey = '" + orgIdKey + "'");
		return cocAttributeSetsFromAncestors(orgId,orgType,orgShortNameKey,orgIdKey);
	}

}
