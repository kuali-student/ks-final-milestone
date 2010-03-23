package org.kuali.student.lum.lu.ui.course.client.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.kuali.student.common.ui.client.service.BaseDataOrchestrationRpcService;
import org.kuali.student.common.ui.client.service.BaseDataOrchestrationRpcServiceAsync;
import org.kuali.student.common.ui.client.service.BaseRpcService;
import org.kuali.student.common.ui.client.service.exceptions.OperationFailedException;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.lum.lu.dto.workflow.WorkflowPersonInfo;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
@RemoteServiceRelativePath("rpcservices/WorkflowToolRpcService")
public interface WorkflowToolRpcService extends BaseDataOrchestrationRpcService{
	public Boolean addCollaborator(String docId, String dataId, String dataTitle, String recipientPrincipalId, String collabType, boolean participationRequired, String respondBy) throws OperationFailedException;
    public List<WorkflowPersonInfo> getCollaborators(String docId) throws OperationFailedException;
    public Metadata getMetadata(String idType, String id) throws OperationFailedException;
}
