package org.kuali.student.lum.program.service.assembler;

import org.apache.log4j.Logger;
import org.kuali.student.core.assembly.BOAssembler;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.lum.lu.dto.AdminOrgInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.program.dto.CredentialProgramInfo;

public class CredentialProgramAssembler implements BOAssembler<CredentialProgramInfo, CluInfo>{
	final static Logger LOG = Logger.getLogger(CredentialProgramAssembler.class);
    private ProgramAssemblerUtils programAssemblerUtils;

	@Override
    public CredentialProgramInfo assemble(CluInfo clu,
                                          CredentialProgramInfo credentialProgram,
                                          boolean shallowBuild)
	            throws AssemblyException {

        CredentialProgramInfo cpInfo = (null != credentialProgram) ? credentialProgram : new CredentialProgramInfo();

        // Copy all the data from the clu to the credential program
        if ( ! ProgramAssemblerConstants.CREDENTIAL_PROGRAM_TYPES.contains(clu.getType()) ) {
            throw new AssemblyException("CredentialProgramAssembler.assemble() called for Clu of incorrect type: " + clu.getType());
        }
        cpInfo.setCredentialProgramType(clu.getType());
        cpInfo.setDescr(clu.getDescr());

        programAssemblerUtils.assembleBasics(clu, cpInfo);
        programAssemblerUtils.assembleIdentifiers(clu, cpInfo);
        if (null != clu.getOfficialIdentifier().getLevel()) {
            cpInfo.setProgramLevel(clu.getOfficialIdentifier().getLevel());
        }
        programAssemblerUtils.assembleAdminOrgs(clu, cpInfo);
        for (AdminOrgInfo org : clu.getAdminOrgs()) {
            if (ProgramAssemblerConstants.INSTITUTION.equals(org.getType())) {
                cpInfo.setInstitution(org);
            }
        }
        programAssemblerUtils.assembleLuCodes(clu, cpInfo);
        programAssemblerUtils.assembleRequirements(clu, cpInfo);

        return cpInfo;
	}

    @Override
	public BaseDTOAssemblyNode<CredentialProgramInfo, CluInfo> disassemble(
			CredentialProgramInfo businessDTO, NodeOperation operation)
			throws AssemblyException {
		return null;
	}
	
    public void setProgramAssemblerUtils(ProgramAssemblerUtils programAssemblerUtils) {
        this.programAssemblerUtils = programAssemblerUtils;
    }
}
