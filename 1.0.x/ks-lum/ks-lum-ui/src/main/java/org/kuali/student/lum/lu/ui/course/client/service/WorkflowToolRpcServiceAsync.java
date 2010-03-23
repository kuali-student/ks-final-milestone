package org.kuali.student.lum.lu.ui.course.client.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.kuali.student.common.ui.client.service.BaseDataOrchestrationRpcServiceAsync;
import org.kuali.student.common.ui.client.service.BaseRpcServiceAsync;
import org.kuali.student.common.ui.client.service.exceptions.OperationFailedException;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.lum.lu.dto.workflow.WorkflowPersonInfo;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface WorkflowToolRpcServiceAsync extends BaseDataOrchestrationRpcServiceAsync{
	public void addCollaborator(String docId, String dataId, String dataTitle, String recipientPrincipalId, String collabType, boolean participationRequired, String respondBy, AsyncCallback<Boolean> callback);
    public void getCollaborators(String docId, AsyncCallback<List<WorkflowPersonInfo>> callback);
    public void getMetadata(String idType, String id, AsyncCallback<Metadata> callback);
}
