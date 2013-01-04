package org.kuali.student.lum.program.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.common.ui.client.service.BaseDataOrchestrationRpcServiceAsync;
import org.kuali.student.lum.program.client.requirements.ProgramRequirementsDataModel;
import org.kuali.student.lum.program.dto.ProgramRequirementInfo;

import java.util.List;
import java.util.Map;

public interface MajorDisciplineRpcServiceAsync extends BaseDataOrchestrationRpcServiceAsync {
    public void getProgramRequirements(List<String> programRequirementIds, AsyncCallback<List<ProgramRequirementInfo>> callback);
    public void storeProgramRequirements(Map<Integer, ProgramRequirementsDataModel.requirementState> states, Map<Integer, ProgramRequirementInfo> progReqs, AsyncCallback<Map<Integer, ProgramRequirementInfo>> async);    
    public void createProgramRequirement(ProgramRequirementInfo programRequirementInfo, AsyncCallback<ProgramRequirementInfo> callback);
    public void deleteProgramRequirement(String programRequirementId, AsyncCallback<StatusInfo> callback);
    public void updateProgramRequirement(ProgramRequirementInfo programRequirementInfo, AsyncCallback<ProgramRequirementInfo> callback);
    public void isLatestVersion(String versionIndId, Long versionSequenceNumber, AsyncCallback<Boolean> callback);
}
