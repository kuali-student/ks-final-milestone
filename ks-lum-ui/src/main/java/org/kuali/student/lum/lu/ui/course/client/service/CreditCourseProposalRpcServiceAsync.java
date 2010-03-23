package org.kuali.student.lum.lu.ui.course.client.service;

import java.util.Map;

import org.kuali.student.common.ui.client.service.BaseDataOrchestrationRpcServiceAsync;
import org.kuali.student.common.ui.client.service.AuthorizationRpcService.PermissionType;
import org.kuali.student.core.assembly.data.Data;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CreditCourseProposalRpcServiceAsync extends BaseDataOrchestrationRpcServiceAsync{

    public void getNewProposalWithCopyOfClu(String cluId, AsyncCallback<Data> callback);

    public void isAuthorized(PermissionType type, Map<String,String> attributes, AsyncCallback<Boolean> callback);
}
