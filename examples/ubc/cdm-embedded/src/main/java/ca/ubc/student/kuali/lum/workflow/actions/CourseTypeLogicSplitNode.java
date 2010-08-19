package ca.ubc.student.kuali.lum.workflow.actions;

import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.engine.RouteHelper;
import org.kuali.rice.kew.engine.node.SplitNode;
import org.kuali.rice.kew.engine.node.SplitResult;
import org.kuali.rice.kew.util.XmlHelper;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.workflow.qualifierresolver.AbstractCocOrgQualifierResolver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ca.ubc.student.kuali.lum.cdm.CdmConstants;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.util.ArrayList;
import java.util.List;


public class CourseTypeLogicSplitNode implements SplitNode {
	protected static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
	.getLogger(CourseTypeLogicSplitNode.class);
	@Override
	public SplitResult process(RouteContext context, RouteHelper helper)
			throws Exception {
	    Document document = context.getDocumentContent().getDocument();
	    Element root = document.getDocumentElement();
	    XPath xpath = XPathFactory.newInstance().newXPath();
	    List<String> branchNames = new ArrayList<String>();
	    String cluId = null;
	    try {
	    	// get document content
	    	//String cluId = xpath.evaluate("cluId", root);
	    	XmlHelper.printDocumentStructure(document);
	    	String query = "//cluId";
	    	cluId = xpath.evaluate(query, root);

	    	// get proposal #id	    	
	    	LuService luService = (LuService) GlobalResourceLoader.getService(new QName(CdmConstants.LU_SERVICE_NAMESPACE,CdmConstants.LU_SERVICE));	    	

	    	// get proposal
	    	CluInfo clu = luService.getClu(cluId);

	    	// get course number	    	
	    	String courseNumber = clu.getOfficialIdentifier().getSuffixCode();

	    	// determine if course is undergrad or graduate and select appropriate branch
	    	if (courseNumber != null && courseNumber.matches("^[0-9]*$")){
	    		if (Integer.parseInt(courseNumber) >= 500){
	    			branchNames.add("Graduate");
	    		}else{
	    			branchNames.add("UnderGraduate");
	    		}
	    	}
	    	
	    } catch (Exception e) {
	      LOG.info("There was a problem checking the course type (course number)");
	    }

	    // default to undergraduate course
	    if (branchNames.size() == 0){
	    	branchNames.add("UnderGraduate");
	    }
	    return new SplitResult(branchNames);
	}
}
