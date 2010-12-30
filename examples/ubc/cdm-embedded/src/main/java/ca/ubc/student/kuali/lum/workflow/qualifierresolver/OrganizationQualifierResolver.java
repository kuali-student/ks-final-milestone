package ca.ubc.student.kuali.lum.workflow.qualifierresolver;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.engine.node.RouteNodeUtils;
import org.kuali.rice.kew.role.XPathQualifierResolver;
import org.kuali.rice.kew.rule.bo.RuleAttribute;
import org.kuali.rice.kew.util.XmlHelper;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.student.bo.KualiStudentKimAttributes;
import org.kuali.student.core.organization.service.OrganizationService;
import org.kuali.student.core.search.dto.SearchResultRow;
import org.kuali.student.lum.workflow.qualifierresolver.AbstractCocOrgQualifierResolver;
import org.w3c.dom.Document;

import ca.ubc.student.kuali.lum.cdm.CdmConstants;

public class OrganizationQualifierResolver extends
		AbstractOrgQualifierResolver {
	protected static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(OrganizationQualifierResolver.class);
	
	@Override
	public List<AttributeSet> resolve(RouteContext routeContext) {
		String orgId = RouteNodeUtils.getValueOfCustomProperty(routeContext.getNodeInstance().getRouteNode(), CdmConstants.ORGID_KEY);
		String orgName = RouteNodeUtils.getValueOfCustomProperty(routeContext.getNodeInstance().getRouteNode(), CdmConstants.ORGNAME_KEY);
		
		if (null == orgName){
			orgName = "organization";	
		}
		
		String orgNameId = orgName + "Id";
				
		ArrayList<AttributeSet> returnSets = new ArrayList<AttributeSet>();
		
		returnSets.add(attributeSetFromOrgId(orgId, orgName, orgNameId));
		return returnSets;		
	}	
}
