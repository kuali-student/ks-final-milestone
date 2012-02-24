package org.kuali.student.lum.program.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.r1.common.ui.client.service.BaseDataOrchestrationRpcServiceAsync;
import org.kuali.student.r1.common.ui.client.service.DataSaveResult;
import org.kuali.student.lum.program.client.requirements.ProgramRequirementsDataModel;
import org.kuali.student.r2.lum.program.dto.ProgramRequirementInfo;

import java.util.List;
import java.util.Map;


/**
 * 
 * The async services are used by GWT to communicate with the servlets.  It's a standard GWT pattern.
 * You can use the google to find instructions on how to use it.
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public interface MajorDisciplineProposalRpcServiceAsync extends MajorDisciplineRpcServiceAsync {
    // Note: This is a copy of MajorDisciplineRpcServiceAsync with slight modifications
    
    public void getProgramRequirements(List<String> programRequirementIds, AsyncCallback<List<ProgramRequirementInfo>> callback, ContextInfo contextInfo);
    public void storeProgramRequirements(Map<Integer, ProgramRequirementsDataModel.requirementState> states, Map<Integer, ProgramRequirementInfo> progReqs, AsyncCallback<Map<Integer, ProgramRequirementInfo>> async, ContextInfo contextInfo);    
    public void createProgramRequirement(ProgramRequirementInfo programRequirementInfo, AsyncCallback<ProgramRequirementInfo> callback, ContextInfo contextInfo);
    public void deleteProgramRequirement(String programRequirementId, AsyncCallback<StatusInfo> callback, ContextInfo contextInfo);
    public void updateProgramRequirement(ProgramRequirementInfo programRequirementInfo, AsyncCallback<ProgramRequirementInfo> callback, ContextInfo contextInfo);
    public void isLatestVersion(String versionIndId, Long versionSequenceNumber, AsyncCallback<Boolean> callback, ContextInfo contextInfo);
	public void updateState(Data data, String state, AsyncCallback<DataSaveResult> callback, ContextInfo contextInfo);
}
