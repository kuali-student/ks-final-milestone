package ca.ubc.student.kuali.lum.workflow.qualifierresolver;
import java.util.List;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.engine.node.BranchState;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kew.util.XmlHelper;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.student.bo.KualiStudentKimAttributes;
import org.kuali.student.lum.workflow.qualifierresolver.AbstractCocOrgQualifierResolver;
import org.w3c.dom.Document;

public class SenateCurriculumCommitteeQualifierResolver extends AbstractCocOrgQualifierResolver {
  
	@Override
	public List<AttributeSet> resolve(RouteContext routeContext) {
		List<AttributeSet> attributeSets = super.resolve(routeContext);
		String orgId = null;
		
		Document document = routeContext.getDocumentContent().getDocument();

		XmlHelper.printDocumentStructure(document);
		String name = "nextOrgName";
		LOG.info("var prefix " + name + " :" + KEWServiceLocator.getBranchService().getScopedVariableValue(routeContext.getNodeInstance().getBranch(), BranchState.VARIABLE_PREFIX + name));
		LOG.info("no prefix " + name + " :" + KEWServiceLocator.getBranchService().getScopedVariableValue(routeContext.getNodeInstance().getBranch(), name));		
		
		if (attributeSets.size() > 0 && attributeSets.get(0).size() > 0) {
			orgId = getAttribute(attributeSets, KualiStudentKimAttributes.QUALIFICATION_ORG_ID);
		}
		return cocAttributeSetsFromAncestors(orgId,"kuali.org.Senate","senateCOC","senateCOCId");
	}
}
