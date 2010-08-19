package ca.ubc.student.kuali.lum.workflow.qualifierresolver;

import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.engine.node.BranchState;
import org.kuali.rice.kew.exception.InvalidXmlException;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kew.util.XmlHelper;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.student.bo.KualiStudentKimAttributes;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import ca.ubc.student.kuali.lum.cdm.CdmConstants;
import ca.ubc.student.kuali.lum.workflow.actions.CourseTypeLogicSplitNode;

public class SenateCurriculumServicesQualifierResolver extends AbstractOrgQualifierResolver{
	protected static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
	.getLogger(SenateCurriculumServicesQualifierResolver.class);	
	@Override
	public List<AttributeSet> resolve(RouteContext routeContext) {
		List<AttributeSet> attributeSets = super.resolve(routeContext);
		Document document = routeContext.getDocumentContent().getDocument();

		XmlHelper.printDocumentStructure(document);
		String name = "nextOrgName";
		LOG.info("var prefix " + name + " :" + KEWServiceLocator.getBranchService().getScopedVariableValue(routeContext.getNodeInstance().getBranch(), BranchState.VARIABLE_PREFIX + name));
		LOG.info("no prefix " + name + " :" + KEWServiceLocator.getBranchService().getScopedVariableValue(routeContext.getNodeInstance().getBranch(), name));
		
		
        String contentFragment = routeContext.getNodeInstance().getRouteNode().getContentFragment();
        String variable = null;
        try{
	        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();        
	        Document d = db.parse(new InputSource(new StringReader(contentFragment)));
	        Element e = d.getDocumentElement();
	        variable = e.getElementsByTagName("orgId").item(0).getTextContent();
	        LOG.info("get orgId from workflow: " + variable);
        }catch(Exception e){
        	LOG.error(e.getMessage(), e);
        }
		
		String orgId = null;
		String org = null;
		if (attributeSets.size() > 0 && attributeSets.get(0).size() > 0) {
			orgId = getAttribute(attributeSets, KualiStudentKimAttributes.QUALIFICATION_ORG_ID);
			LOG.info("Retrieve Org Attribute:" + org);
		}
		
		
		return orgAttributeSetsFromAncestors(orgId,"kuali.org.Office","publication","publicationId");
	}
}
