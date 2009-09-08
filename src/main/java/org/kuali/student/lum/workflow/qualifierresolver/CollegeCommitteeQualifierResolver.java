package org.kuali.student.lum.workflow.qualifierresolver;

import java.util.List;

import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;

public class CollegeCommitteeQualifierResolver extends
		AbstractOrgQualifierResolver {
	@Override
	public List<AttributeSet> resolve(RouteContext routeContext) {
		List<AttributeSet> attributeSets = super.resolve(routeContext);
		String orgId = null;
		if (attributeSets.size() > 0 && attributeSets.get(0).size() > 0) {
			orgId = getAttribute(attributeSets, ORG_ID);
		}
		return cocAttributeSetsFromAncestors(orgId,KUALI_ORG_COLLEGE,COLLEGE,COLLEGE_ID);
	}
}
