package org.kuali.student.core.authorization.ui.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AuthorizationRpcServiceAsync {
	public void hasPermission(String namespace, String permissionTemplateName, AsyncCallback<Boolean> callback);
}
