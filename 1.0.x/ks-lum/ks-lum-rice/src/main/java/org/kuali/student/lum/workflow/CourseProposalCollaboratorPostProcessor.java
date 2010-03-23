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

import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.kuali.rice.kew.dto.DocumentContentDTO;
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
import org.kuali.student.lum.lu.dto.workflow.CluProposalCollabRequestDocInfo;

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

	public List<Long> getDocumentIdsToLock(DocumentLockingEvent arg0) throws Exception {
		return null;
	}

    private void addAdhocFYI(DocumentContentDTO document) {
       	try {
			JAXBContext context = JAXBContext.newInstance(CluProposalCollabRequestDocInfo.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
	        CluProposalCollabRequestDocInfo collabRequest = (CluProposalCollabRequestDocInfo) unmarshaller.unmarshal(new StringReader(document.getApplicationContent()));

			String docId = collabRequest.getDocId();
			String recipientPrincipalId = collabRequest.getPrincipalIdRoleAttribute().getRecipientPrincipalId();
			String principalId = collabRequest.getPrincipalId();
			String collaboratorType = collabRequest.getCollaboratorType();

			WorkflowDocument workflowDocument = new WorkflowDocument(principalId, Long.decode(docId));
			if (workflowDocument.getNodeNames().length == 0) {
				throw new RuntimeException("No active nodes found on document id" + docId);
			}
			String routeNodeName = collabRequest.getRouteNodeName();
			if (!Arrays.asList(workflowDocument.getNodeNames()).contains(routeNodeName)) {
				throw new RuntimeException("Route node '" + routeNodeName + "' not found as active node on document id " + docId);
			}
			String annotation = "Collaborator Approved";
			workflowDocument.adHocRouteDocumentToPrincipal(KEWConstants.ACTION_REQUEST_FYI_REQ, routeNodeName, annotation, recipientPrincipalId, "Request to Collaborate", true, collaboratorType);
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
}
