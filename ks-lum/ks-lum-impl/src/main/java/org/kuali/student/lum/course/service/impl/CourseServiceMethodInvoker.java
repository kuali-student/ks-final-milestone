package org.kuali.student.lum.course.service.impl;

import java.util.List;

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
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.organization.service.OrganizationService;
import org.kuali.student.core.statement.service.StatementService;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.service.assembler.BaseDTOAssemblyNode;
import org.kuali.student.lum.lo.service.LearningObjectiveService;
import org.kuali.student.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.service.LuService;

public class CourseServiceMethodInvoker {
	
	private LuService luService;
	private StatementService statementService;
	private LearningObjectiveService loService;
	private OrganizationService orgService;
	private AtpService atpService;
	
	@SuppressWarnings("unchecked")
	public CourseInfo invokeServiceCalls(BaseDTOAssemblyNode results) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DependentObjectsExistException, CircularRelationshipException, AssemblyException {
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
				luService.createCluCluRelation(relation.getCluId(), relation.getRelatedCluId(), relation.getType(), relation);
				break;
			case UPDATE:
				luService.updateCluCluRelation(relation.getId(), relation);
				break;
			case DELETE:
				luService.deleteCluCluRelation(relation.getId());
				break;
			}
			
		}
		
		for(BaseDTOAssemblyNode childNode: (List<BaseDTOAssemblyNode>) results.getChildNodes()){
			invokeServiceCalls(childNode);
		}

		//TODO... instead of getting the object again we could build it back up from the return values here!
		return null;
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
