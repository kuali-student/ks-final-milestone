package org.kuali.student.lum.course.service.assembler;

import org.kuali.student.core.assembly.BOAssembler;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.lum.course.dto.FormatInfo;
import org.kuali.student.lum.course.dto.LoDisplayInfo;
import org.kuali.student.lum.lo.dto.LoInfo;
import org.kuali.student.lum.lu.dto.CluInfo;

public class LoAssembler implements BOAssembler<LoDisplayInfo, LoInfo> {

	@Override
	public LoDisplayInfo assemble(LoInfo baseDTO, LoDisplayInfo businessDTO,
			boolean shallowBuild) throws AssemblyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseDTOAssemblyNode<LoDisplayInfo, LoInfo> disassemble(
			LoDisplayInfo loDisplay, NodeOperation operation)
			throws AssemblyException {
		
		BaseDTOAssemblyNode<LoDisplayInfo, LoInfo> result = new BaseDTOAssemblyNode<LoDisplayInfo, LoInfo>(this);
		if (loDisplay == null) {
			// FIXME Unsure now if this is an exception or just return null or
			// empty assemblyNode
			throw new AssemblyException("Format can not be null");
		}
//		if (NodeOperation.CREATE != operation && null == loDisplay.getId()) {
//			throw new AssemblyException("Course Format Shell's id can not be null");
//		}
		// TODO Auto-generated method stub
		return null;
	}

}
