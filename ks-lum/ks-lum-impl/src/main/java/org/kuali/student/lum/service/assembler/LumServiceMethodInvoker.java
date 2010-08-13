package org.kuali.student.lum.service.assembler;

import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode;
import org.kuali.student.core.assembly.BusinessServiceMethodInvoker;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.atp.service.AtpService;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.CircularRelationshipException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.UnsupportedActionException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.organization.service.OrganizationService;
import org.kuali.student.core.statement.service.StatementService;
import org.kuali.student.lum.course.service.assembler.LoCategoryRelationInfo;
import org.kuali.student.lum.lo.dto.LoInfo;
import org.kuali.student.lum.lo.dto.LoLoRelationInfo;
import org.kuali.student.lum.lo.service.LearningObjectiveService;
import org.kuali.student.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.CluLoRelationInfo;
import org.kuali.student.lum.lu.dto.CluResultInfo;
import org.kuali.student.lum.lu.service.LuService;

public abstract class LumServiceMethodInvoker implements BusinessServiceMethodInvoker {
	final Logger LOG = Logger.getLogger(LumServiceMethodInvoker.class);
	private LuService luService;
	private StatementService statementService;
	private LearningObjectiveService loService;
	private OrganizationService orgService;
	private AtpService atpService;
	
	@SuppressWarnings("unchecked")
	@Override
	public void invokeServiceCalls(BaseDTOAssemblyNode results)
			throws AlreadyExistsException, DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, VersionMismatchException,
			DependentObjectsExistException, CircularRelationshipException,
			AssemblyException, UnsupportedActionException {

	    // For Delete operation process the tree from bottom up 
	    if(NodeOperation.DELETE == results.getOperation()) {
            for(BaseDTOAssemblyNode childNode: (List<BaseDTOAssemblyNode>) results.getChildNodes()){
                invokeServiceCalls(childNode);
            }
	    }
	    
	    Object nodeData = results.getNodeData();
		if(nodeData instanceof CluInfo){
			CluInfo clu = (CluInfo) nodeData;
			switch(results.getOperation()){
			case CREATE:
				CluInfo newClu = luService.createClu(clu.getType(), clu);
				results.getAssembler().assemble(newClu, results.getBusinessDTORef(), true);
				break;
			case UPDATE:
				CluInfo updatedClu = luService.updateClu(clu.getId(), clu);
				results.getAssembler().assemble(updatedClu, results.getBusinessDTORef(), true);
				break;
			case DELETE:
				luService.deleteClu(clu.getId());
				break;
			}						
		}else if(nodeData instanceof CluCluRelationInfo){
			CluCluRelationInfo  relation = (CluCluRelationInfo) nodeData;
			switch(results.getOperation()){
			case CREATE:
				CluCluRelationInfo newCluRel = luService.createCluCluRelation(relation.getCluId(), relation.getRelatedCluId(), relation.getType(), relation);
				// Update the businessDTO if one exists for the cluclurelation (for e.g. CourseJointInfo)
				if(null != results.getBusinessDTORef()) {
					results.getAssembler().assemble(newCluRel, results.getBusinessDTORef(), true);
				}				
				break;
			case UPDATE:
				CluCluRelationInfo updatedCluRel = luService.updateCluCluRelation(relation.getId(), relation);				
				// Update the businessDTO if one exists for the cluclurelation (for e.g. CourseJointInfo)
				if(null != results.getBusinessDTORef()) {
					results.getAssembler().assemble(updatedCluRel, results.getBusinessDTORef(), true);
				}
				break;
			case DELETE:
				luService.deleteCluCluRelation(relation.getId());
				break;
			}			
		}else if(nodeData instanceof CluResultInfo){
			CluResultInfo cluResult = (CluResultInfo) nodeData;
			switch(results.getOperation()){
			case CREATE:
				luService.createCluResult(cluResult.getCluId(), cluResult.getType(), cluResult);
				break;
			case UPDATE:
				luService.updateCluResult(cluResult.getId(), cluResult);
				break;
			case DELETE:
				luService.deleteCluResult(cluResult.getId());
				break;
			}
		}else if(nodeData instanceof LoCategoryRelationInfo){
			LoCategoryRelationInfo loCategoryRelation = (LoCategoryRelationInfo) nodeData;
			switch(results.getOperation()){
			case CREATE:
				loService.addLoCategoryToLo(loCategoryRelation.getCategoryId(), loCategoryRelation.getLoId());
				LOG.debug("added category " + loCategoryRelation.getCategoryId() + " to lo " + loCategoryRelation.getLoId());
				break;
			case UPDATE:
				throw new UnsupportedOperationException("Can't call update on lo category relations, just add and remove");
			case DELETE:
				LOG.debug("removing category " + loCategoryRelation.getCategoryId() + " to lo " + loCategoryRelation.getLoId());
				loService.removeLoCategoryFromLo(loCategoryRelation.getCategoryId(), loCategoryRelation.getLoId());
				break;
			}
		}else if(nodeData instanceof LoInfo){
			LoInfo lo = (LoInfo) nodeData;
			switch(results.getOperation()){
			case CREATE:
				LoInfo createdLo = loService.createLo(lo.getLoRepositoryKey(), lo.getType(), lo);
				if(null != results.getBusinessDTORef()) {
					results.getAssembler().assemble(createdLo, results.getBusinessDTORef(), true);
				}
				LOG.debug("created Lo "+lo.getId());
				break;
			case UPDATE:
				LoInfo updatedLo = loService.updateLo(lo.getId(), lo);
				if(null != results.getBusinessDTORef()) {
					results.getAssembler().assemble(updatedLo, results.getBusinessDTORef(), true);
				}
				break;
			case DELETE:
				LOG.debug("deleting Lo "+lo.getId());
				loService.deleteLo(lo.getId());
				break;
			}
		}else if(nodeData instanceof LoLoRelationInfo){
			LoLoRelationInfo loRelation = (LoLoRelationInfo) nodeData;
			switch(results.getOperation()){
			case CREATE:
				loService.createLoLoRelation(loRelation.getLoId(), loRelation.getRelatedLoId(), loRelation.getType(), loRelation);
				LOG.debug("created lo relation "+loRelation.getLoId()+ " => " + loRelation.getRelatedLoId());
				break;
			case UPDATE:
				loService.updateLoLoRelation(loRelation.getId(), loRelation);
				LOG.debug("updated lo relation "+loRelation.getLoId()+ " => " + loRelation.getRelatedLoId());
				break;
			case DELETE:
				LOG.debug("deleting lo relation "+loRelation.getLoId()+ " => " + loRelation.getRelatedLoId() +" with id " +loRelation.getId());
				loService.deleteLoLoRelation(loRelation.getId());
				break;
			}
		}else if(nodeData instanceof CluLoRelationInfo){
			CluLoRelationInfo cluLoRelation = (CluLoRelationInfo) nodeData;
			switch(results.getOperation()){
			case CREATE:
				luService.createCluLoRelation(cluLoRelation.getCluId(), cluLoRelation.getLoId(), cluLoRelation.getType(), cluLoRelation);
				break;
			case UPDATE:
				luService.updateCluLoRelation(cluLoRelation.getLoId(), cluLoRelation);
				break;
			case DELETE:
				luService.deleteCluLoRelation(cluLoRelation.getId());
				break;
			}
		}
		
		// For create/update process the child nodes from top to bottom
		if(NodeOperation.DELETE != results.getOperation()) {
		    for(BaseDTOAssemblyNode childNode: (List<BaseDTOAssemblyNode>) results.getChildNodes()){
		        invokeServiceCalls(childNode);
		    }
		}
	}
	
	public LuService getLuService() {
		return luService;
	}

	public void setLuService(LuService luService) {
		this.luService = luService;
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

	public OrganizationService getOrgService() {
		return orgService;
	}

	public void setOrgService(OrganizationService orgService) {
		this.orgService = orgService;
	}

	public AtpService getAtpService() {
		return atpService;
	}

	public void setAtpService(AtpService atpService) {
		this.atpService = atpService;
	}

}
