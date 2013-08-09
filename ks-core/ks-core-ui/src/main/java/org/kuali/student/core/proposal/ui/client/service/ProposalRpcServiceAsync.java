/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.core.proposal.ui.client.service;

import org.kuali.student.common.ui.client.service.BaseRpcServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.kuali.student.r2.core.proposal.dto.ProposalInfo;

/**
 * For now this is just a RPC Async interface for proposal service to expose
 * search and dictionary operations. 
 * 
 * @author Kuali Student Team
 *
 */
public interface ProposalRpcServiceAsync extends BaseRpcServiceAsync {

    public void getProposalByWorkflowId(String workflowId, AsyncCallback<ProposalInfo> callback);

}
