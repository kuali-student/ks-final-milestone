package org.kuali.student.lum.lu.ui.course.client.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.kuali.student.common.ui.client.service.BaseDataOrchestrationRpcService;
import org.kuali.student.common.ui.client.service.exceptions.OperationFailedException;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
@RemoteServiceRelativePath("rpcservices/CreditCourseProposalRpcService")
public interface CreditCourseProposalRpcService extends BaseDataOrchestrationRpcService{

	public Boolean addCollaborator(String docId, String recipientPrincipalId, String collabType, boolean participationRequired, String respondBy) throws OperationFailedException;
    public HashMap<String, ArrayList<String>> getCollaborators(String docId) throws OperationFailedException;

    public Boolean hasPermission(String permName);
}
