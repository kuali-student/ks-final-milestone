package org.kuali.student.embedded.workflow;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.engine.RouteHelper;
import org.kuali.rice.kew.engine.node.SplitNode;
import org.kuali.rice.kew.engine.node.SplitResult;

/**
 * Splits the Route Path based on the Campus Codes submitted with the document.
 */
public class DeptSplit implements SplitNode {

	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
			.getLogger(DeptSplit.class);
	
	public SplitResult process(RouteContext routeContext, RouteHelper helper) throws Exception {
		List branchNames = new ArrayList();
		
		
		XPath xPath = XPathFactory.newInstance().newXPath();
		NodeList cluShortNameNodes = (NodeList)xPath.evaluate("//cluShortName", routeContext.getDocumentContent().getDocument(), XPathConstants.NODESET);
		
		for (int index = 0; index < cluShortNameNodes.getLength(); index++) {
			Element element = (Element)cluShortNameNodes.item(index);
			String course = element.getTextContent();
			if (!StringUtils.isBlank(course)){
				if (course.startsWith(("CMSC"))) {
					branchNames.add("CMSC_BRANCH");
					LOG.info("Branching to CMSC");
				} else if (course.startsWith("MATH")){
					branchNames.add("MATH_BRANCH");
					LOG.info("Branching to MATH");
				} else if (course.startsWith("BIOL")){
					branchNames.add("BIOL_BRANCH");
					LOG.info("Branching to BIOL");					
				} else if (course.startsWith("STAT")){
					branchNames.add("MATH_BRANCH");
					branchNames.add("CMSC_BRANCH");
				}
				else {
					branchNames.add("NO_DEPT_BRANCH");
				}			
			}
		}
		
		LOG.info("Branches: " + branchNames);	
		return new SplitResult(branchNames);
	}

}
