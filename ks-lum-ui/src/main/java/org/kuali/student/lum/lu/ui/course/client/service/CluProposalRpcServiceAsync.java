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
import org.kuali.student.common.assembly.client.DataModel;
import org.kuali.student.common.ui.client.service.BaseRpcServiceAsync;
import org.kuali.student.lum.lu.assembly.data.client.creditcourse.CreditCourseProposal;
import org.kuali.student.lum.lu.ui.course.client.configuration.mvc.CluProposalModelDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in.
 *
 * @author Kuali Student Team
 *
 */
public interface CluProposalRpcServiceAsync extends BaseRpcServiceAsync{
	public void submitProposal(CluProposalModelDTO cluProposalDTO, AsyncCallback<Boolean> callback);
    public void getActionsRequested(String cluProposalId, AsyncCallback<String> callback);
    public void approveProposal(CluProposalModelDTO cluProposal, AsyncCallback<Boolean> callback);
    public void disapproveProposal(CluProposalModelDTO cluProposal, AsyncCallback<Boolean> callback);
    public void acknowledgeProposal(CluProposalModelDTO cluProposal, AsyncCallback<Boolean> callback);
    public void addCollaborator(String docId, String recipientPrincipalId, String collabType, boolean participationRequired, String respondBy, AsyncCallback<Boolean> callback);
    public void getCollaborators(String docId, AsyncCallback<HashMap<String, ArrayList<String>>> callback);
    public void adhocRequest(String docId, String recipientPrincipalId, String requestType, String annotation, AsyncCallback<Boolean> callback);
    public void loginBackdoor(String backdoorId, AsyncCallback<Boolean> callback);
    public void getCluProposalFromWorkflowId(String docId, AsyncCallback<CluProposalModelDTO> callback);
    public void getWorkflowIdFromProposalId(String proposalId, AsyncCallback<String> callback);
    
    public void createProposal(CluProposalModelDTO cluProposalDTO, AsyncCallback<CluProposalModelDTO> callback);
    public void saveProposal(CluProposalModelDTO cluProposalDTO, AsyncCallback<CluProposalModelDTO> callback);
    public void deleteProposal(String id, AsyncCallback<Boolean> callback);
    public void getProposal(String id, AsyncCallback<CluProposalModelDTO> callback);

    public void getClu(String id, AsyncCallback<CluProposalModelDTO> callback);

	public void approveDocument(String requestDocId, AsyncCallback<Boolean> callback);
	public void disapproveDocument(String requestDocId, AsyncCallback<Boolean> callback);
 
	public void getCluProposalModelDefinition(String modelId, AsyncCallback<DataModel> callback);	
	public void saveData(Data data, AsyncCallback<Data> callback);
	
	public void getCreditCourseProposal(String id, AsyncCallback<Data> callback);
	public void saveCreditCourseProposal(Data proposal, AsyncCallback<DataSaveResult> callback);
    
}
