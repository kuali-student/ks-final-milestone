package org.kuali.student.core.statement.service.assembler;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.kuali.student.common.assembly.BOAssembler;
import org.kuali.student.common.assembly.BaseDTOAssemblyNode;
import org.kuali.student.common.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.common.assembly.data.AssemblyException;
import org.kuali.student.common.service.impl.BaseAssembler;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;


/**
 * CRUD operations for StatementTreeViewInfo
 * <p/>
 * NOTE: the Natural Language field is NOT filled in. This has be done further up the calling stack.
 *
 * @author glindholm
 *
 */
public class StatementTreeViewAssembler extends BaseAssembler implements BOAssembler<StatementTreeViewInfo, StatementTreeViewInfo>{
    final static Logger LOG = Logger.getLogger(StatementTreeViewAssembler.class);


	@Override
	public StatementTreeViewInfo assemble(StatementTreeViewInfo baseDTO,
			StatementTreeViewInfo businessDTO, boolean shallowBuild)
			throws AssemblyException {
		StatementTreeViewInfo stmtTree = (null != businessDTO) ? businessDTO
				: new StatementTreeViewInfo();
		try {
			BeanUtils.copyProperties(stmtTree, baseDTO);
		} catch (Exception e) {
			throw new AssemblyException("Error assembling StatementTree",e);
		}
		
		return stmtTree;
	}


	@Override
	public BaseDTOAssemblyNode<StatementTreeViewInfo, StatementTreeViewInfo> disassemble(
			StatementTreeViewInfo newTree, NodeOperation operation)
			throws AssemblyException {

        if (newTree == null) {
			// FIXME Unsure now if this is an exception or just return null or
			// empty assemblyNode
		    LOG.error("StatementTreeView to disassemble is null!");
			throw new AssemblyException("StatementTreeView can not be null");
		}

		BaseDTOAssemblyNode<StatementTreeViewInfo, StatementTreeViewInfo> result = new BaseDTOAssemblyNode<StatementTreeViewInfo, StatementTreeViewInfo>(this);
		if(newTree.getId()==null|newTree.getId().isEmpty()){
			result.setOperation(NodeOperation.CREATE);
		}else{
			result.setOperation(operation);
		}
		result.setBusinessDTORef(newTree);
		result.setNodeData(newTree);
		return result;
	}




}
