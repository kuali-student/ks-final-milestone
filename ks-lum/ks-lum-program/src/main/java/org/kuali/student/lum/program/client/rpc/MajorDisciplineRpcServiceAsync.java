package org.kuali.student.lum.program.client.rpc;

import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.service.BaseDataOrchestrationRpcServiceAsync;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.lum.program.client.requirements.ProgramRequirementsDataModel;
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.lum.program.dto.ProgramRequirementInfo;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MajorDisciplineRpcServiceAsync extends BaseDataOrchestrationRpcServiceAsync {
    public void getProgramRequirements(List<String> programRequirementIds, AsyncCallback<List<ProgramRequirementInfo>> callback);
    public void storeProgramRequirements(Map<Integer, ProgramRequirementsDataModel.requirementState> states, Map<Integer, ProgramRequirementInfo> progReqs, AsyncCallback<Map<Integer, ProgramRequirementInfo>> async);    
    public void createProgramRequirement(ProgramRequirementInfo programRequirementInfo, AsyncCallback<ProgramRequirementInfo> callback);
    public void deleteProgramRequirement(String programRequirementId, AsyncCallback<StatusInfo> callback);
    public void updateProgramRequirement(ProgramRequirementInfo programRequirementInfo, AsyncCallback<ProgramRequirementInfo> callback);
    public void isLatestVersion(String versionIndId, Long versionSequenceNumber, AsyncCallback<Boolean> callback);
	public void updateState(Data data, String state, AsyncCallback<DataSaveResult> callback);
	
	// Determine if this is a proposal
	public void isProposal(String referenceTypeKey, String referenceId, AsyncCallback<Boolean> callback);
}
