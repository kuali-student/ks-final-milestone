package org.kuali.student.lum.lu.ui.dependency.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DependencyAnalysisRpcServiceAsync {

	public void getRequirementComponentNL(String reqComponentId, AsyncCallback<String> callback);
	
	public void getRequirmentComponentNL(List<String> reqComponentIds, AsyncCallback<List<String>> callback);

}
