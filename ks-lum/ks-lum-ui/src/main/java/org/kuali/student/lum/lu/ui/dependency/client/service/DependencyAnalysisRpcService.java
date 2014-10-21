package org.kuali.student.lum.lu.ui.dependency.client.service;

import java.util.List;

import org.kuali.student.r2.lum.program.dto.ProgramRequirementInfo;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpcservices/DependencyAnalysisRpcService")
public interface DependencyAnalysisRpcService extends RemoteService{

	public String getRequirementComponentNL(String reqComponentId) throws Exception;
	
	public List<String> getRequirementComponentNL(List<String> reqComponentIds) throws Exception;
	
	public ProgramRequirementInfo getProgramRequirement(String reqId) throws Exception;
	
}
