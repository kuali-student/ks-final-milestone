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

import java.util.List;

import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.workflow.dto.WorkflowPersonInfo;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface WorkflowToolRpcServiceAsync{
	public void addCollaborator(String docId, String dataId, String dataTitle, String recipientPrincipalId, String selectedPermission, String actionRequestTypeCode, boolean participationRequired, String respondBy, AsyncCallback<Boolean> callback);
    public void getCollaborators(String docId, AsyncCallback<List<WorkflowPersonInfo>> callback);
    public void getMetadata(String idType, String id, AsyncCallback<Metadata> callback);
    public void isAuthorizedAddReviewer(String docId, AsyncCallback<Boolean> callback);
}
