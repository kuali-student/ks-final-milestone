package org.kuali.student.lum.lu.ui.course.client.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.kuali.student.common.ui.client.service.BaseDataOrchestrationRpcServiceAsync;
import org.kuali.student.common.ui.client.service.AuthorizationRpcService.PermissionType;
import org.kuali.student.core.assembly.data.Data;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CreditCourseProposalRpcServiceAsync extends BaseDataOrchestrationRpcServiceAsync{

	public void addCollaborator(String docId, String recipientPrincipalId, String collabType, boolean participationRequired, String respondBy, AsyncCallback<Boolean> callback);
    public void getCollaborators(String docId, AsyncCallback<HashMap<String, ArrayList<String>>> callback);
    public void getNewProposalWithCopyOfClu(String cluId, AsyncCallback<Data> callback);

    public void isAuthorized(PermissionType type, Map<String,String> attributes, AsyncCallback<Boolean> callback);
}
