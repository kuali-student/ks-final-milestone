package org.kuali.student.lum.lu.ui.dependency.client.service;

import java.util.List;

import org.kuali.student.lum.program.dto.ProgramRequirementInfo;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DependencyAnalysisRpcServiceAsync {

	public void getRequirementComponentNL(String reqComponentId, AsyncCallback<String> callback);
	
	public void getRequirementComponentNL(List<String> reqComponentIds, AsyncCallback<List<String>> callback);
	
	public void getProgramRequirement(String reqId, AsyncCallback<ProgramRequirementInfo> callback);
}
