package org.kuali.student.lum.program.server;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.server.gwt.DataGwtServlet;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.lum.program.client.rpc.ProgramRpcService;
import org.kuali.student.lum.program.dto.MajorDisciplineInfo;
import org.kuali.student.lum.program.dto.ProgramRequirementInfo;
import org.kuali.student.lum.program.service.ProgramService;

public class ProgramRpcServlet extends DataGwtServlet implements ProgramRpcService {

    private static final long serialVersionUID = 1L;
    
    private ProgramService programService;	
	
    public List<ProgramRequirementInfo> getProgramRequirements(List<String> programRequirementIds) throws Exception {

        List<ProgramRequirementInfo> programReqInfos = new ArrayList<ProgramRequirementInfo>();

        for (String programReqId : programRequirementIds) {
            programReqInfos.add(((ProgramService) getDataService()).getProgramRequirement(programReqId, null, null));
        }

        return programReqInfos;
    }

    public ProgramRequirementInfo addProgramRequirement(ProgramRequirementInfo programRequirement, String programId) throws Exception {
        ProgramRequirementInfo progReq = this.createProgramRequirement(programRequirement);
        MajorDisciplineInfo major = ((ProgramService) getDataService()).getMajorDiscipline(programId);
        major.getProgramRequirements().add(programRequirement.getId());
        updateMajorDiscipline(major);
        return progReq;
    }

    public ProgramRequirementInfo createProgramRequirement(ProgramRequirementInfo programRequirementInfo) throws Exception {
        return programService.createProgramRequirement(programRequirementInfo);
    }

    public MajorDisciplineInfo updateMajorDiscipline(MajorDisciplineInfo majorDisciplineInfo) throws Exception {
        return programService.updateMajorDiscipline(majorDisciplineInfo);
    }

    public StatusInfo deleteProgramRequirement(String programRequirementId) throws Exception {
        return programService.deleteProgramRequirement(programRequirementId);
    }

    public ProgramRequirementInfo updateProgramRequirement(ProgramRequirementInfo programRequirementInfo) throws Exception {
        return programService.updateProgramRequirement(programRequirementInfo);
    }

    public void setProgramService(ProgramService programService) {
        this.programService = programService;
    }
}
