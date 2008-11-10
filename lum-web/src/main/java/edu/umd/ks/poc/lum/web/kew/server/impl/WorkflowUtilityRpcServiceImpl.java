package edu.umd.ks.poc.lum.web.kew.server.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.rice.kew.dto.ActionItemDTO;
import org.kuali.rice.kew.dto.DocumentContentDTO;
import org.kuali.rice.kew.exception.WorkflowException;
import org.kuali.rice.kew.service.WorkflowUtility;
import org.kuali.rice.kew.webservice.SimpleWorkflowUtilityService;

import edu.umd.ks.poc.lum.lu.dto.CluInfo;
import edu.umd.ks.poc.lum.lu.service.LuService;
import edu.umd.ks.poc.lum.web.kew.client.service.WorkflowUtilityRpcService;

public class WorkflowUtilityRpcServiceImpl implements WorkflowUtilityRpcService{



	private SimpleWorkflowUtilityService wfService;
	private LuService luService;


	public WorkflowUtilityRpcServiceImpl() {
		super();
//		try{
//		Class.forName("org.kuali.rice.kew.dto.ActionItemDTO");
//		ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
//		factory.setServiceClass(WorkflowUtility.class);
//		factory.setAddress("http://localhost:8181/kr-dev/remoting/{TRAVEL}WorkflowUtilityService");
//		//factory.setWsdlLocation("src/main/resources/wsdl/WorkflowUtilityService.wsdl");
//		factory.setWsdlURL("http://localhost:8181/kr-dev/remoting/%7BTRAVEL%7DWorkflowUtilityService?wsdl");
//		factory.setServiceName(new QName("TRAVEL","WorkflowUtilityService"));
//		//factory.getServiceFactory().setDataBinding(new AegisDatabinding());
//		wfService = (WorkflowUtility) factory.create();
//		}catch(Exception e){
//			e.printStackTrace();
//			//throw new RuntimeException(e);
//		}
	}

	public String  getCluIdForDocument(String docId){
	    String sRet = null;

	    try {
            DocumentContentDTO docContent = wfService.getDocumentContent(Long.parseLong(docId));
            String cluIdXml = docContent.getApplicationContent();
            //Remove xml tags
            sRet = cluIdXml.replaceAll("<[^>]+>", "");

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
	    return sRet;
	}

	@Override
	public List<CluInfo> getClusForUser(String user) {


		List<CluInfo> clus = new ArrayList<CluInfo>();

		try {
			/* This was the call required for the WorkflowUtilitService
		    UuIdDTO uuIdDTO = new UuIdDTO();
			uuIdDTO.setUuId(user);
			ActionItemDTO[] actionItems = wfService.getActionItemsForUser(uuIdDTO);
			*/

		    ActionItemDTO[] actionItems = wfService.getActionItemsForUser(user);

			for(ActionItemDTO actionItem:actionItems){
				System.out.println("## ActionItem:"+actionItem.getActionRequestCd()+","+actionItem.getUser().getUuId()+","+actionItem.getRouteHeaderId());
				DocumentContentDTO docContent = wfService.getDocumentContent(actionItem.getRouteHeaderId());
				System.out.println("## DocContent:"+docContent.getApplicationContent());

				String cluIdXml = docContent.getApplicationContent();
				//Remove xml tags
				String cluId = cluIdXml.replaceAll("<[^>]+>", "");
				try{
					CluInfo clu = luService.fetchClu(cluId);
					clu.setStatus(actionItem.getActionRequestCd()+actionItem.getRouteHeaderId());
					clus.add(clu);
				}catch(Exception e){
					System.out.println("## CluNotFound:"+cluId);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return clus;
	}

	public SimpleWorkflowUtilityService getWfService() {
        return wfService;
    }

    public void setWfService(SimpleWorkflowUtilityService wfService) {
        this.wfService = wfService;
    }

    /**
	 * @return the luService
	 */
	public LuService getLuService() {
		return luService;
	}

	/**
	 * @param luService the luService to set
	 */
	public void setLuService(LuService luService) {
		this.luService = luService;
	}


	/**
	 *
	 * This metod isn't really used.  I should put the sessions stuff into a different Interface.
	 *
	 * @see edu.umd.ks.poc.lum.web.kew.client.service.WorkflowUtilityService#getSession()
	 */
    /*@Override
    public HttpSession getSession() {
        return null;
    }*/

    @Override
    public String getUser() {
        return null;
    }

	@Override
	public String switchUser(String userId) {
		return null;
	}



}
