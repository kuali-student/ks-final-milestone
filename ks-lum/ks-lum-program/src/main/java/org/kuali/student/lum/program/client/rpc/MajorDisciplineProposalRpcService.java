package org.kuali.student.lum.program.client.rpc;

import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.lum.program.client.requirements.ProgramRequirementsDataModel;
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.lum.program.dto.ProgramRequirementInfo;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpcservices/majorDisciplineProposalRpcService")
public interface MajorDisciplineProposalRpcService extends MajorDisciplineRpcService {
    public List<ProgramRequirementInfo> getProgramRequirements(List<String> programRequirementIds) throws Exception;
    public Map<Integer, ProgramRequirementInfo> storeProgramRequirements(Map<Integer, ProgramRequirementsDataModel.requirementState> states, Map<Integer, ProgramRequirementInfo> progReqs) throws Exception;    
    public ProgramRequirementInfo createProgramRequirement(ProgramRequirementInfo programRequirementInfo) throws Exception;
    public StatusInfo deleteProgramRequirement(String programRequirementId) throws Exception;
    public ProgramRequirementInfo updateProgramRequirement(ProgramRequirementInfo programRequirementInfo) throws Exception;

    /**
     * Is the current version sequence number the latest version.
     *  
     * @param versionIndId The version independent id of program
     * @param versionSequenceNumber The sequence number to check.
     * @return
     * @throws Exception
     */
    public Boolean isLatestVersion(String versionIndId, Long versionSequenceNumber) throws Exception;
    

    /**
     * 
     * This method is called when the user changes the program state using
     * the drop-down box, clicking certain buttons, or creating
     * a new version of the program.
     * <p>
     * For example, creating a new program or using the drop down box to modify
     * with new version will change the state to DRAFT.  Clicking the approve button
     * will change the state to APPROVED,  clicking the activate button will change
     * the state to ACTIVE.
     * <p>
     * Other states will be added in the future.
     * 
     * 
     * @param data the XML used to pass data between controller to view 
     * @param state the state we should update the program to
     * @return the result of the save
     */
	public DataSaveResult updateState(Data data, String state) throws Exception ;
}
