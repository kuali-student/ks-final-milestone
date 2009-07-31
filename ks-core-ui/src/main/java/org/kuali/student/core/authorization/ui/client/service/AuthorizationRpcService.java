package org.kuali.student.core.authorization.ui.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
@RemoteServiceRelativePath("rpcservices/AuthorizationRpcService")
public interface AuthorizationRpcService extends RemoteService{
	public Boolean hasPermission(String namespace, String permissionTemplateName);
}
