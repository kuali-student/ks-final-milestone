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

package org.kuali.student.core.workflow.ui.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.kuali.student.core.rice.StudentWorkflowConstants.ActionRequestType;
import org.kuali.student.core.workflow.ui.client.widgets.ActionDocumentStatusDTO;

import java.util.List;

/**
 * Aysnc methods to make workflow rpc service calls.
 */
public interface WorkflowRpcServiceAsync {

    void getWorkflowIdFromDataId(String workflowDocType, String dataId, AsyncCallback<String> callback);

    void getDataIdFromWorkflowId(String workflowId, AsyncCallback<String> callback);

    void getDocumentStatus(String workflowId, AsyncCallback<String> callback);

    void getWorkflowNodes(String workflowId, AsyncCallback<List<String>> callback);

    void getActionsRequested(String workflowId, AsyncCallback<String> callback);

    void submitDocumentWithId(String workflowId, AsyncCallback<Boolean> callback);

    void cancelDocumentWithId(String workflowId, AsyncCallback<Boolean> callback);

    void approveDocumentWithId(String workflowId, AsyncCallback<Boolean> callback);

    void disapproveDocumentWithId(String workflowId, AsyncCallback<Boolean> callback);

    void acknowledgeDocumentWithId(String workflowId, AsyncCallback<Boolean> callback);

    void fyiDocumentWithId(String workflowId, AsyncCallback<Boolean> callback);

    void withdrawDocumentWithId(String workflowId, AsyncCallback<Boolean> callback);

    void blanketApproveDocumentWithId(String workflowId, AsyncCallback<Boolean> callback);

    void returnDocumentWithId(String workflowId, String nodeName, AsyncCallback<Boolean> callback);

    void getPreviousRouteNodeNames(String workflowId, AsyncCallback<List<String>> callback);

    void adhocRequest(String workflowId, String recipientPrincipalId, ActionRequestType requestType, String annotation, AsyncCallback<Boolean> callback);

    void isAuthorizedAddReviewer(String docId, AsyncCallback<Boolean> callback);

    void isAuthorizedRemoveReviewers(String docId, AsyncCallback<Boolean> callback);

    void getActionsAndDocumentStatus(String workflowId, AsyncCallback<ActionDocumentStatusDTO> callback);
}
