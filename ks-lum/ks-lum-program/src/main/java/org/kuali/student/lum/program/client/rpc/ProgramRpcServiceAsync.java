package org.kuali.student.lum.program.client.rpc;

import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.service.BaseDataOrchestrationRpcServiceAsync;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.lum.program.client.requirements.ProgramRequirementsDataModel;
import org.kuali.student.lum.program.dto.MajorDisciplineInfo;
import org.kuali.student.lum.program.dto.ProgramRequirementInfo;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ProgramRpcServiceAsync extends BaseDataOrchestrationRpcServiceAsync {
    public void getProgramRequirements(List<String> programRequirementIds, AsyncCallback<List<ProgramRequirementInfo>> callback);
    public void storeProgramRequirements(Map<Integer, ProgramRequirementsDataModel.requirementState> states, Map<Integer, ProgramRequirementInfo> progReqs, AsyncCallback<Map<Integer, ProgramRequirementInfo>> async);    
    public void addProgramRequirement(ProgramRequirementInfo programRequirement, String programId, AsyncCallback<ProgramRequirementInfo> callback);
    public void createProgramRequirement(ProgramRequirementInfo programRequirementInfo, AsyncCallback<ProgramRequirementInfo> callback);
    public void updateMajorDiscipline(MajorDisciplineInfo majorDisciplineInfo, AsyncCallback<MajorDisciplineInfo> callback);
    public void deleteProgramRequirement(String programRequirementId, AsyncCallback<StatusInfo> callback);
    public void updateProgramRequirement(ProgramRequirementInfo programRequirementInfo, AsyncCallback<ProgramRequirementInfo> callback);
}
