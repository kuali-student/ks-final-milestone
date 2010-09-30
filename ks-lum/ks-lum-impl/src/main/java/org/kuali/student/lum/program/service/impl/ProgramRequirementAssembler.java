/**
 *
 */
package org.kuali.student.lum.program.service.impl;

import static org.apache.commons.collections.CollectionUtils.isEmpty;

import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.assembly.BOAssembler;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.statement.dto.RefStatementRelationInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.service.StatementService;
import org.kuali.student.core.statement.service.assembler.StatementTreeViewAssembler;
import org.kuali.student.lum.course.service.assembler.LoAssembler;
import org.kuali.student.lum.lo.service.LearningObjectiveService;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.program.dto.ProgramRequirementInfo;
import org.kuali.student.lum.program.service.assembler.ProgramAssemblerUtils;
import org.kuali.student.lum.service.assembler.CluAssemblerUtils;


/**
 * @author glindholm
 *
 */
public class ProgramRequirementAssembler implements BOAssembler<ProgramRequirementInfo, CluInfo> {
    final static Logger LOG = Logger.getLogger(ProgramRequirementAssembler.class);

	private StatementService statementService;
	private StatementTreeViewAssembler statementTreeViewAssembler;
	private LearningObjectiveService loService;
	private LuService luService;
	private LoAssembler loAssembler;
	private CluAssemblerUtils cluAssemblerUtils;
    private ProgramAssemblerUtils programAssemblerUtils;


	@Override
	public ProgramRequirementInfo assemble(CluInfo clu,
			ProgramRequirementInfo progReqInfo, boolean shallowBuild)
			throws AssemblyException {
		ProgramRequirementInfo progReq = (progReqInfo != null ? progReqInfo : new ProgramRequirementInfo());

		if (clu.getOfficialIdentifier() != null) {
			progReq.setShortTitle(clu.getOfficialIdentifier().getShortName());
			progReq.setLongTitle(clu.getOfficialIdentifier().getLongName());
		}
		progReq.setDescr(clu.getDescr());

		if (progReq.getStatement() == null) {
			try {
				List<RefStatementRelationInfo> relations = statementService.getRefStatementRelationsByRef("clu", clu.getId());


				StatementTreeViewInfo statementTree = new StatementTreeViewInfo();
				if (relations != null) {
					statementTreeViewAssembler.assemble(statementService.getStatementTreeView(relations.get(0).getStatementId()), statementTree, shallowBuild);
				}
				progReq.setStatement(statementTree);
			} catch (AssemblyException e) {
				throw e;
			} catch (Exception e) {
				throw new AssemblyException(e);
			}
		}

		if (isEmpty(progReq.getLearningObjectives())) {
			progReq.setLearningObjectives(cluAssemblerUtils.assembleLos(clu.getId(), shallowBuild));
		}

		progReq.setMetaInfo(clu.getMetaInfo());
		progReq.setType(clu.getType());
		progReq.setAttributes(clu.getAttributes());
		progReq.setId(clu.getId());

		return progReq;
	}

	@Override
	public BaseDTOAssemblyNode<ProgramRequirementInfo, CluInfo> disassemble(
			ProgramRequirementInfo progReq, NodeOperation operation)
			throws AssemblyException {

		if (progReq == null) {
			// FIXME Unsure now if this is an exception or just return null or
			// empty assemblyNode
		    LOG.error("ProgramRequirementInfo to disassemble is null!");
			throw new AssemblyException("ProgramRequirementInfo can not be null");
		}

		BaseDTOAssemblyNode<ProgramRequirementInfo, CluInfo> result = new BaseDTOAssemblyNode<ProgramRequirementInfo, CluInfo>(null);

		// Create the Statement Tree
        StatementTreeViewInfo statement = progReq.getStatement();
        statement.setId(UUIDHelper.genStringUUID(statement.getId()));
        BaseDTOAssemblyNode<StatementTreeViewInfo, StatementTreeViewInfo> statementTree;
		try {
			statementTree = statementTreeViewAssembler.disassemble(statement, operation);
		} catch (AssemblyException e) {
			throw e;
		} catch (Exception e) {
			throw new AssemblyException(e);
		}
        result.getChildNodes().add(statementTree);

		CluInfo clu;
		try {
			clu = (NodeOperation.UPDATE == operation) ?  luService.getClu(progReq.getId()) : new CluInfo();
        } catch (Exception e) {
			throw new AssemblyException("Error getting existing learning unit during program requirement update", e);
        }

        BaseDTOAssemblyNode<ProgramRequirementInfo, CluInfo> cluResult = new BaseDTOAssemblyNode<ProgramRequirementInfo, CluInfo>(this);

        cluResult.setNodeData(clu);
        cluResult.setBusinessDTORef(progReq);
        cluResult.setOperation(operation);
        result.getChildNodes().add(cluResult);

        programAssemblerUtils.disassembleBasics(clu, progReq, operation);
        progReq.setId(clu.getId());
        if (clu.getOfficialIdentifier() == null) {
        	clu.setOfficialIdentifier(new CluIdentifierInfo());
        }
        clu.getOfficialIdentifier().setLongName(progReq.getLongTitle());
        clu.getOfficialIdentifier().setShortName(progReq.getShortTitle());
        clu.setDescr(progReq.getDescr());

        if (progReq.getLearningObjectives() != null) {
            disassembleLearningObjectives(progReq, operation, result);
        }

        RefStatementRelationInfo relation;
        if (operation == NodeOperation.CREATE) {
            relation = new RefStatementRelationInfo();
            relation.setId(UUIDHelper.genStringUUID(null));
        } else {
        	try {
        		relation = statementService.getRefStatementRelationsByRef("clu", clu.getId()).get(0);
			} catch (Exception e) {
				throw new AssemblyException("Unable to find RefStatementRelation", e);
			}
        }
        relation.setType("clu.programrequirements");
        relation.setRefObjectId(clu.getId());
        relation.setRefObjectTypeKey("clu");
        relation.setStatementId(statement.getId());
        relation.setState("active");

        BaseDTOAssemblyNode<ProgramRequirementInfo, RefStatementRelationInfo> relationNode = new BaseDTOAssemblyNode<ProgramRequirementInfo, RefStatementRelationInfo>(null);
        relationNode.setNodeData(relation);
        relationNode.setOperation(operation);

        result.getChildNodes().add(relationNode);
        result.setBusinessDTORef(progReq);
        result.setOperation(operation);
		return result;
	}

	private void disassembleLearningObjectives(ProgramRequirementInfo progReq,
			NodeOperation operation,
			BaseDTOAssemblyNode<ProgramRequirementInfo, CluInfo> result) throws AssemblyException {
        try {
            List<BaseDTOAssemblyNode<?, ?>> loResults = cluAssemblerUtils.disassembleLos(progReq.getId(), progReq.getState(),  progReq.getLearningObjectives(), operation);
            if (loResults != null) {
                result.getChildNodes().addAll(loResults);
            }
        } catch (DoesNotExistException e) {
        } catch (Exception e) {
            throw new AssemblyException("Error while disassembling los", e);
        }
	}

	public StatementTreeViewAssembler getStatementTreeViewAssembler() {
		return statementTreeViewAssembler;
	}

	public void setStatementTreeViewAssembler(
			StatementTreeViewAssembler statementTreeViewAssembler) {
		this.statementTreeViewAssembler = statementTreeViewAssembler;
	}

	public StatementService getStatementService() {
		return statementService;
	}

	public void setStatementService(StatementService statementService) {
		this.statementService = statementService;
	}

	public LearningObjectiveService getLoService() {
		return loService;
	}

	public void setLoService(LearningObjectiveService loService) {
		this.loService = loService;
	}

	public LuService getLuService() {
		return luService;
	}

	public void setLuService(LuService luService) {
		this.luService = luService;
	}

	public LoAssembler getLoAssembler() {
		return loAssembler;
	}

	public void setLoAssembler(LoAssembler loAssembler) {
		this.loAssembler = loAssembler;
	}

	public CluAssemblerUtils getCluAssemblerUtils() {
		return cluAssemblerUtils;
	}

	public void setCluAssemblerUtils(CluAssemblerUtils cluAssemblerUtils) {
		this.cluAssemblerUtils = cluAssemblerUtils;
	}

	public ProgramAssemblerUtils getProgramAssemblerUtils() {
		return programAssemblerUtils;
	}

	public void setProgramAssemblerUtils(ProgramAssemblerUtils programAssemblerUtils) {
		this.programAssemblerUtils = programAssemblerUtils;
	}
}
