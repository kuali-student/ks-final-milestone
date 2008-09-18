package org.kuali.student.embedded.workflow;

import java.io.ByteArrayInputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

import org.kuali.student.poc.common.ws.beans.JaxWsClientFactory;
import org.kuali.student.poc.common.ws.beans.JaxWsClientFactoryBean;
import org.kuali.student.poc.wsdl.learningunit.lu.LuService;
import org.kuali.student.poc.wsdl.learningunit.lu.jaxws.CreateClu;
import org.kuali.student.poc.xsd.learningunit.lu.dto.CluInfo;

import org.kuali.rice.kew.actions.ActionTakenEvent;
import org.kuali.rice.kew.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.rice.kew.service.WorkflowInfo;
import org.kuali.rice.kew.dto.DocumentContentDTO;
import org.kuali.rice.kew.postprocessor.DefaultPostProcessor;
import org.kuali.rice.kew.postprocessor.ProcessDocReport;

public class CoursePostProcessor extends DefaultPostProcessor {
	
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
	.getLogger(CoursePostProcessor.class);
	
	public ProcessDocReport doRouteStatusChange(DocumentRouteStatusChange statusChange)
			throws Exception {

		LOG.info("STATUS CHANGED");
		
		//String cluId = "11223344-1122-1122-1111-000000000006";
		//callWebService(cluId);
				
		WorkflowInfo workflowInfo = new WorkflowInfo();
		
		//System.out.println("Current Route Names: " + workflowInfo.getNodeInstance(nodeInstanceId)(statusChange.getRouteHeaderId()));

		if (statusChange.getNewRouteStatus().equals(KEWConstants.ROUTE_HEADER_APPROVED_CD)) {			
			LOG.info("CoursePostProcessor: Status change to APPROVED");
			DocumentContentDTO document = workflowInfo.getDocumentContent(statusChange.getRouteHeaderId());
			
			//createNewClu(document);						
		}
		
		return new ProcessDocReport(true);
	}	

    public ProcessDocReport doActionTaken(ActionTakenEvent event) throws Exception {
    	LOG.info("ACTION TAKEN");
    	return new ProcessDocReport(true, "");
    }

	private void callWebService(String cluId){
        try{
            LuService client = KSServiceLocator.getLuService();
            CluInfo cluInfo = client.fetchClu(cluId);
            
            System.out.println(
            		"Clu Long Name: " + cluInfo.getCluLongName() + "\n" +
            		"Clu Short Name: " + cluInfo.getCluShortName() + "\n" +
            		"Clu Description: " + cluInfo.getDescription());
            
        } catch (Exception e){
            LOG.info("EXCEPTION OCCURED!!");
        	e.printStackTrace(System.out);
        }
    }
    
    private void createNewClu(DocumentContentDTO doc) throws Exception{
   	
    	ByteArrayInputStream input = new ByteArrayInputStream (doc.getApplicationContent().getBytes());
    	JAXBContext jc = JAXBContext.newInstance(CreateClu.class);
    	Unmarshaller u = jc.createUnmarshaller();
    	CreateClu cluMsg = (CreateClu)u.unmarshal( input); 
    	
		String luTypeId = "11223344-1122-1122-1111-000000000003";
		LuService client = KSServiceLocator.getLuService();
		try {
			String cluId = client.createClu(luTypeId, cluMsg.getCluCreateInfo());
			LOG.info("Clu created with id: " + cluId);
		} catch (Exception e){
			e.printStackTrace(System.out);
		}		
    }
    
}
