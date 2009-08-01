package org.kuali.student.lum.workflow;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.kuali.rice.kew.dto.DocumentContentDTO;
import org.kuali.rice.kew.exception.WorkflowException;
import org.kuali.rice.kew.postprocessor.ActionTakenEvent;
import org.kuali.rice.kew.postprocessor.AfterProcessEvent;
import org.kuali.rice.kew.postprocessor.BeforeProcessEvent;
import org.kuali.rice.kew.postprocessor.DeleteEvent;
import org.kuali.rice.kew.postprocessor.DocumentLockingEvent;
import org.kuali.rice.kew.postprocessor.DocumentRouteLevelChange;
import org.kuali.rice.kew.postprocessor.DocumentRouteStatusChange;
import org.kuali.rice.kew.postprocessor.PostProcessor;
import org.kuali.rice.kew.postprocessor.ProcessDocReport;
import org.kuali.rice.kew.service.WorkflowDocument;
import org.kuali.rice.kew.service.WorkflowInfo;
import org.kuali.rice.kew.util.KEWConstants;

public class CourseProposalCollaboratorPostProcessor implements PostProcessor{
	private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
	.getLogger(CourseProposalCollaboratorPostProcessor.class);
	
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

	public ProcessDocReport doRouteLevelChange(DocumentRouteLevelChange arg0)
			throws Exception {
        return new ProcessDocReport(true, "");
	}

	public ProcessDocReport doRouteStatusChange(DocumentRouteStatusChange statusChangeEvent)
			throws Exception {
		if (statusChangeEvent.getNewRouteStatus().equals(KEWConstants.ROUTE_HEADER_PROCESSED_CD)) {			
			LOG.info("CourseProposalCollaboratorPostProcessor: Status change to PROCESSED");
			WorkflowInfo workflowInfo = new WorkflowInfo();
			DocumentContentDTO document = workflowInfo.getDocumentContent(statusChangeEvent.getRouteHeaderId());
			addAdhocFYI(document);						
		}
        return new ProcessDocReport(true, "");
	}

	public List<Long> getDocumentIdsToLock(DocumentLockingEvent arg0)
			throws Exception {
		return null;
	}
	
    private void addAdhocFYI(DocumentContentDTO document) {
    	Pattern pattern = Pattern.compile("<docId>\\s*([^<\\s]+)");
		Matcher matcher = pattern.matcher(document.getApplicationContent());
		matcher.find();
		String docId = matcher.group(1);

    	pattern = Pattern.compile("<recipientPrincipalId>\\s*([^<\\s]+)");
		matcher = pattern.matcher(document.getApplicationContent());
		matcher.find();
		String recipientPrincipalId = matcher.group(1);

	   	pattern = Pattern.compile("<principalId>\\s*([^<\\s]+)");
		matcher = pattern.matcher(document.getApplicationContent());
		matcher.find();
		String principalId = matcher.group(1);
		
	   	pattern = Pattern.compile("<collaboratorType>\\s*([^<\\s]+)");
		matcher = pattern.matcher(document.getApplicationContent());
		matcher.find();
		String collaboratorType = matcher.group(1);
		
		String annotation = "Collaborator Approved";
	    
       	try {
			WorkflowDocument workflowDocument = new WorkflowDocument(principalId, new Long(docId));
			if (workflowDocument.getNodeNames().length == 0) {
				throw new RuntimeException("No active nodes found on document");
			}
			String collaborateAtNodeName = workflowDocument.getNodeNames()[0];
			workflowDocument.adHocRouteDocumentToPrincipal(KEWConstants.ACTION_REQUEST_FYI_REQ, collaborateAtNodeName, annotation, recipientPrincipalId, "Request to Collaborate", true, collaboratorType);
		} catch (WorkflowException e) {
			e.printStackTrace();
		}
		
    }
}
