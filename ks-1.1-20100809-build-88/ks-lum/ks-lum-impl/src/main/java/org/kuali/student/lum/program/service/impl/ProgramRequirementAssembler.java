/**
 *
 */
package org.kuali.student.lum.program.service.impl;

import org.kuali.student.core.assembly.BOAssembler;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.program.dto.ProgramRequirementInfo;


/**
 * @author glindholm
 *
 */
public class ProgramRequirementAssembler implements BOAssembler<ProgramRequirementInfo, CluInfo> {

	@Override
	public ProgramRequirementInfo assemble(CluInfo baseDTO,
			ProgramRequirementInfo businessDTO, boolean shallowBuild)
			throws AssemblyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseDTOAssemblyNode<ProgramRequirementInfo, CluInfo> disassemble(
			ProgramRequirementInfo businessDTO, NodeOperation operation)
			throws AssemblyException {
		// TODO Auto-generated method stub
		return null;
	}
}
