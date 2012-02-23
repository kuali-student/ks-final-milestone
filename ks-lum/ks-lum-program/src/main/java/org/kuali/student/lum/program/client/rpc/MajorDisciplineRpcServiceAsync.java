package org.kuali.student.lum.program.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

import org.kuali.student.common.assembly.data.Data;
import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.common.ui.client.service.BaseDataOrchestrationRpcServiceAsync;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.lum.program.client.requirements.ProgramRequirementsDataModel;
import org.kuali.student.lum.program.dto.ProgramRequirementInfo;

import java.util.List;
import java.util.Map;

public interface MajorDisciplineRpcServiceAsync extends BaseDataOrchestrationRpcServiceAsync {
    public void getProgramRequirements(List<String> programRequirementIds, ContextInfo contextInfo, AsyncCallback<List<ProgramRequirementInfo>> callback);
    public void storeProgramRequirements(Map<Integer, ProgramRequirementsDataModel.requirementState> states, Map<Integer, ProgramRequirementInfo> progReqs, ContextInfo contextInfo, AsyncCallback<Map<Integer, ProgramRequirementInfo>> async);    
    public void createProgramRequirement(ProgramRequirementInfo programRequirementInfo, ContextInfo contextInfo, AsyncCallback<ProgramRequirementInfo> callback);
    public void deleteProgramRequirement(String programRequirementId, ContextInfo contextInfo, AsyncCallback<StatusInfo> callback);
    public void updateProgramRequirement(ProgramRequirementInfo programRequirementInfo, ContextInfo contextInfo, AsyncCallback<ProgramRequirementInfo> callback);
    public void isLatestVersion(String versionIndId, Long versionSequenceNumber, ContextInfo contextInfo, AsyncCallback<Boolean> callback);
	public void updateState(Data data, String state, ContextInfo contextInfo, AsyncCallback<DataSaveResult> callback);
	
	// Determine if this is a proposal
	public void isProposal(String referenceTypeKey, String referenceId, ContextInfo contextInfo, AsyncCallback<Boolean> callback);
}
