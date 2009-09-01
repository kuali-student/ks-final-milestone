package org.kuali.student.lum.workflow.qualifierresolver;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.student.lum.workflow.qualifier.AbstractOrgQualifierResolver;

public class SenateCommitteeQualifierResolver extends
		AbstractOrgQualifierResolver {
	@Override
	public List<AttributeSet> resolve(RouteContext routeContext) {
		List<AttributeSet> returnAttrSetList = new ArrayList<AttributeSet>();
		AttributeSet attributeSet = new AttributeSet();
		attributeSet.put(ORG, "COC");
		attributeSet.put(ORG_ID, "141");
		returnAttrSetList.add(attributeSet);
		return returnAttrSetList;
	}
}
