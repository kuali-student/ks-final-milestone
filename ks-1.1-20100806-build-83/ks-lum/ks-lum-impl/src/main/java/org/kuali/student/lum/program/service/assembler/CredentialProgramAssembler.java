package org.kuali.student.lum.program.service.assembler;

import org.apache.log4j.Logger;
import org.kuali.student.core.assembly.BOAssembler;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.program.dto.CredentialProgramInfo;

public class CredentialProgramAssembler implements BOAssembler<CredentialProgramInfo, CluInfo>{
	final static Logger LOG = Logger.getLogger(CredentialProgramAssembler.class);

	@Override
	public CredentialProgramInfo assemble(CluInfo baseDTO,
			CredentialProgramInfo businessDTO, boolean shallowBuild)
			throws AssemblyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseDTOAssemblyNode<CredentialProgramInfo, CluInfo> disassemble(
			CredentialProgramInfo businessDTO, NodeOperation operation)
			throws AssemblyException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
