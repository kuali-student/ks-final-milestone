/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.lum.workflow;
import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.namespace.QName;

import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kew.actionitem.ActionItem;
import org.kuali.rice.kew.dto.DocumentContentDTO;
import org.kuali.rice.kew.dto.NetworkIdDTO;
import org.kuali.rice.kew.postprocessor.ActionTakenEvent;
import org.kuali.rice.kew.postprocessor.AfterProcessEvent;
import org.kuali.rice.kew.postprocessor.BeforeProcessEvent;
import org.kuali.rice.kew.postprocessor.DeleteEvent;
import org.kuali.rice.kew.postprocessor.DocumentLockingEvent;
import org.kuali.rice.kew.postprocessor.DocumentRouteLevelChange;
import org.kuali.rice.kew.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.kew.postprocessor.PostProcessor;
import org.kuali.rice.kew.postprocessor.ProcessDocReport;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kew.service.WorkflowDocument;
import org.kuali.rice.kew.service.WorkflowInfo;
import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.service.LuService;
public class CluPostProcessor implements PostProcessor{

	private static final NetworkIdDTO KS_SYS_PRINCIPAL = new NetworkIdDTO("KS");
	private static final String CLU_STATE_ACTIVATED = "activated";
	
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
	.getLogger(CluPostProcessor.class);
	
	public ProcessDocReport afterProcess(AfterProcessEvent arg0)
			throws Exception {
        return new ProcessDocReport(true, "");
	}

	public ProcessDocReport beforeProcess(BeforeProcessEvent arg0)
			throws Exception {
        return new ProcessDocReport(true, "");
	}

	public ProcessDocReport doActionTaken(ActionTakenEvent arg0)
			throws Exception {
        return new ProcessDocReport(true, "");
	}

	public ProcessDocReport doDeleteRouteHeader(DeleteEvent arg0)
			throws Exception {
        return new ProcessDocReport(true, "");
	}

	public ProcessDocReport doRouteLevelChange(DocumentRouteLevelChange documentRouteLevelChange)
			throws Exception {

		Long routeHeaderId = documentRouteLevelChange.getRouteHeaderId();
		
		//Clear all the current Collab FYIs on the current document
		Collection<ActionItem> actionItems = KEWServiceLocator.getActionListService().getActionListForSingleDocument(routeHeaderId);
        Map<Long,Long> fyisToClear = new HashMap<Long,Long>();
		for (Iterator<ActionItem> iterator = actionItems.iterator(); iterator.hasNext();) {
            ActionItem item = iterator.next();
            if(KEWConstants.ACTION_REQUEST_FYI_REQ.equals(item.getActionRequestCd())&&item.getRequestLabel()!=null
            		&&(item.getRequestLabel().startsWith("Co-Author")||
            		   item.getRequestLabel().startsWith("Commentor")||
            		   item.getRequestLabel().startsWith("Viewer")||
            		   item.getRequestLabel().startsWith("Delegate"))){
            	fyisToClear.put(item.getActionRequestId(), item.getRouteHeaderId());
            }
        }
		for(Map.Entry<Long, Long> fyiToClear:fyisToClear.entrySet()){
    		WorkflowDocument workflowDocument = new WorkflowDocument(KS_SYS_PRINCIPAL, fyiToClear.getValue());
    		workflowDocument.superUserActionRequestApprove(fyiToClear.getKey(), "Cleared by KS because CluProposal status has changed");
		}
		
		//Clear all pending Collab request documents for this document
		Collection<Long> pendingCollabIds = KEWServiceLocator.getRouteHeaderService().findByDocTypeAndAppId("CluCollaboratorDocument", routeHeaderId.toString());
		if(pendingCollabIds!=null){
			Set<Long> uniquePendingCollagIds = new HashSet<Long>(pendingCollabIds);
        	for(Long pendingCollabId:uniquePendingCollagIds){
        		WorkflowDocument workflowDocument = new WorkflowDocument(KS_SYS_PRINCIPAL, pendingCollabId);
        		if (workflowDocument.stateIsInitiated() || workflowDocument.stateIsSaved() || 
        			workflowDocument.stateIsEnroute() || workflowDocument.stateIsException()) {
//         		String routeStatus = workflowDocument.getRouteHeader().getDocRouteStatus();
//        		if(!KEWConstants.ROUTE_HEADER_FINAL_CD.equals(routeStatus) &&
//        		   !KEWConstants.ROUTE_HEADER_APPROVED_CD.equals(routeStatus) &&
//        		   !KEWConstants.ROUTE_HEADER_PROCESSED_CD.equals(routeStatus) &&
//        		   !KEWConstants.ROUTE_HEADER_DISAPPROVED_CD.equals(routeStatus)){
        			workflowDocument.superUserDisapprove("Collaboration request has been revoked because CluProposal status has changed");
        		}
        	}
        }

		return new ProcessDocReport(true, "");
	}

	public ProcessDocReport doRouteStatusChange(DocumentRouteStatusChange statusChangeEvent)
			throws Exception {
		if (statusChangeEvent.getNewRouteStatus().equals(KEWConstants.ROUTE_HEADER_APPROVED_CD)) {			
			LOG.info("CluApprovalPostProcessor: Status change to APPROVED");
			WorkflowInfo workflowInfo = new WorkflowInfo();
			DocumentContentDTO document = workflowInfo.getDocumentContent(statusChangeEvent.getRouteHeaderId());
			updateCluStatus(document);						
		}
        return new ProcessDocReport(true, "");
	}

	public List<Long> getDocumentIdsToLock(DocumentLockingEvent arg0)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
    private void updateCluStatus(DocumentContentDTO document) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, org.kuali.student.core.exceptions.InvalidParameterException, VersionMismatchException{
    	LuService luService = (LuService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/lu","LuService"));
    	
    	Pattern pattern = Pattern.compile("<cluId>\\s*([^<\\s]+)");
		Matcher matcher = pattern.matcher(document.getApplicationContent());
		matcher.find();
		String cluId = matcher.group(1);
    	
		CluInfo clu = luService.getClu(cluId);
    	clu.setState(CLU_STATE_ACTIVATED);
		luService.updateClu(cluId, clu);
    }
}
