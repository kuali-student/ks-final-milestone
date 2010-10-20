package ca.ubc.student.kuali.lum.workflow.actions;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.engine.node.SplitNode;
import org.kuali.rice.kew.util.XmlHelper;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ca.ubc.student.kuali.lum.cdm.CdmConstants;

public class LogicSplitNode {

	public LogicSplitNode() {
		super();
	}

	protected CluInfo getProposal(RouteContext context) throws XPathExpressionException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
			    Document document = context.getDocumentContent().getDocument();
			    Element root = document.getDocumentElement();
			    XPath xpath = XPathFactory.newInstance().newXPath();
			    String cluId = null;
			    
				// get document content
				XmlHelper.printDocumentStructure(document);
				String query = "//cluId";
				cluId = xpath.evaluate(query, root);
			
				// get proposal #id	    	
				LuService luService = (LuService) GlobalResourceLoader.getService(new QName(CdmConstants.LU_SERVICE_NAMESPACE,CdmConstants.LU_SERVICE));	    	
			
				// get proposal
				CluInfo clu = luService.getClu(cluId);
				
				return clu;
				
			}

}