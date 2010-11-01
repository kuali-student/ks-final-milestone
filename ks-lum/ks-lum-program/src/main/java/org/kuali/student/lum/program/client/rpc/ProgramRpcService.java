package org.kuali.student.lum.program.client.rpc;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import org.kuali.student.common.ui.client.service.BaseDataOrchestrationRpcService;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.lum.program.client.requirements.ProgramRequirementsDataModel;
import org.kuali.student.lum.program.dto.ProgramRequirementInfo;

import java.util.List;
import java.util.Map;

@RemoteServiceRelativePath("rpcservices/programRpcService")
public interface ProgramRpcService extends BaseDataOrchestrationRpcService {
    public List<ProgramRequirementInfo> getProgramRequirements(List<String> programRequirementIds) throws Exception;
    public Map<Integer, ProgramRequirementInfo> storeProgramRequirements(Map<Integer, ProgramRequirementsDataModel.requirementState> states, Map<Integer, ProgramRequirementInfo> progReqs) throws Exception;    
    public ProgramRequirementInfo createProgramRequirement(ProgramRequirementInfo programRequirementInfo) throws Exception;
    public StatusInfo deleteProgramRequirement(String programRequirementId) throws Exception;
    public ProgramRequirementInfo updateProgramRequirement(ProgramRequirementInfo programRequirementInfo) throws Exception;
}
