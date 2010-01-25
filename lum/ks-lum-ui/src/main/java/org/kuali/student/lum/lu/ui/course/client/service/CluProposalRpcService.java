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
package org.kuali.student.lum.lu.ui.course.client.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.common.ui.client.service.BaseRpcService;
import org.kuali.student.common.ui.client.service.exceptions.OperationFailedException;
import org.kuali.student.lum.lu.ui.course.client.configuration.mvc.CluProposalModelDTO;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in.
 *
 * @author Kuali Student Team
 *
 */
@RemoteServiceRelativePath("rpcservices/CluProposalRpcService")
public interface CluProposalRpcService extends BaseRpcService{
	
	public Boolean submitProposal(Data cluProposalDTO);
    public String getActionsRequested(String cluProposalId) throws OperationFailedException;
    public Boolean adhocRequest(String docId, String recipientPrincipalId,String requestType, String annotation) throws OperationFailedException;
    public Boolean approveProposal(Data cluProposal) throws OperationFailedException;
    public Boolean disapproveProposal(Data cluProposal) throws OperationFailedException;
    public Boolean acknowledgeProposal(Data cluProposal) throws OperationFailedException;
    public Boolean addCollaborator(String docId, String recipientPrincipalId, String collabType, boolean participationRequired, String respondBy) throws OperationFailedException;
    public HashMap<String, ArrayList<String>> getCollaborators(String docId) throws OperationFailedException;
    public Boolean loginBackdoor(String backdoorId);
    public Data getCluProposalFromWorkflowId(String docId) throws OperationFailedException;
	public String getWorkflowIdFromProposalId(String proposalId) throws OperationFailedException;
    
    public CluProposalModelDTO createProposal(CluProposalModelDTO cluProposalDTO) throws OperationFailedException;
    public CluProposalModelDTO saveProposal(CluProposalModelDTO cluProposalDTO) throws OperationFailedException;
    public Boolean deleteProposal(String id);
    public CluProposalModelDTO getProposal(String id) throws OperationFailedException;
    
    public CluProposalModelDTO getClu(String id) throws OperationFailedException;

    public Boolean approveDocument(String requestDocId);
	public Boolean disapproveDocument(String requestDocId);
	
//	public DataModel getCluProposalModelDefinition(String modelId);
	public Data saveData(Data data);
    
	public Data getCreditCourseProposal(String id) throws OperationFailedException;
	public DataSaveResult saveCreditCourseProposal(Data proposal) throws OperationFailedException;
	public Metadata getCreditCourseProposalMetadata() throws OperationFailedException;
}
