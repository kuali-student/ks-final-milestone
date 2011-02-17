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

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import org.kuali.student.common.ui.client.service.exceptions.OperationFailedException;
import org.kuali.student.core.rice.StudentWorkflowConstants.ActionRequestType;
import org.kuali.student.core.workflow.ui.client.widgets.ActionDocumentStatusDTO;

import java.util.List;

@RemoteServiceRelativePath("rpcservices/WorkflowRpcService")
public interface WorkflowRpcService extends RemoteService {

    /**
     * @param dataId
     * @return The workflow document id associated with the object data id
     * @throws OperationFailedException
     */
    String getWorkflowIdFromDataId(String workflowDocType, String dataId) throws OperationFailedException;


    /**
     * @param workflowId
     * @return The object data id associated with the workflow document id
     * @throws OperationFailedException
     */
    String getDataIdFromWorkflowId(String workflowId) throws OperationFailedException;

    /**
     * @param workflowId The workflow document id
     * @return Returns the current document status code for the workflow document
     * @throws OperationFailedException
     */
    String getDocumentStatus(String workflowId) throws OperationFailedException;

    /**
     * @param workflowId The workflow document id
     * @return Returns the workflow nodes document is currently in
     * @throws OperationFailedException
     */
    List<String> getWorkflowNodes(String workflowId) throws OperationFailedException;

    /**
     * @param workflowId The workflow document id
     * @return The action(s) available to the user in the current workflow node
     * @throws OperationFailedException
     */
    String getActionsRequested(String workflowId) throws OperationFailedException;

    //These methods just call the actions

    Boolean submitDocumentWithId(String workflowId) throws OperationFailedException;

    Boolean cancelDocumentWithId(String workflowId) throws OperationFailedException;

    Boolean approveDocumentWithId(String workflowId) throws OperationFailedException;

    Boolean disapproveDocumentWithId(String workflowId) throws OperationFailedException;

    Boolean acknowledgeDocumentWithId(String workflowId) throws OperationFailedException;

    Boolean fyiDocumentWithId(String workflowId) throws OperationFailedException;

    Boolean withdrawDocumentWithId(String workflowId) throws OperationFailedException;

    Boolean blanketApproveDocumentWithId(String workflowId) throws OperationFailedException;

    Boolean returnDocumentWithId(String workflowId, String nodeName) throws OperationFailedException;

    List<String> getPreviousRouteNodeNames(String workflowId) throws OperationFailedException;

    Boolean adhocRequest(String docId, String recipientPrincipalId, ActionRequestType requestType, String annotation) throws OperationFailedException;

    Boolean isAuthorizedAddReviewer(String workflowId) throws OperationFailedException;

    Boolean isAuthorizedRemoveReviewers(String workflowId) throws OperationFailedException;

    ActionDocumentStatusDTO getActionsAndDocumentStatus(String workflowId) throws OperationFailedException;
}
