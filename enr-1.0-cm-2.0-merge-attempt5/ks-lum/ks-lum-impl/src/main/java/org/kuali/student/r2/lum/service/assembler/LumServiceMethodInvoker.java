package org.kuali.student.r2.lum.service.assembler;

import org.apache.log4j.Logger;
import org.kuali.student.r1.common.assembly.BaseDTOAssemblyNode;
import org.kuali.student.r1.common.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.r1.common.assembly.BusinessServiceMethodInvoker;
import org.kuali.student.r1.core.statement.dto.RefStatementRelationInfo;
import org.kuali.student.r1.core.statement.dto.ReqComponentInfo;
import org.kuali.student.r1.core.statement.dto.StatementInfo;
import org.kuali.student.r1.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.r1.core.statement.service.StatementService;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.CircularReferenceException;
import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.UnsupportedActionException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.lum.clu.dto.CluCluRelationInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.dto.CluLoRelationInfo;
import org.kuali.student.r2.lum.clu.dto.CluPublicationInfo;
import org.kuali.student.r2.lum.clu.dto.CluResultInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.service.assembler.LoCategoryRelationInfo;
import org.kuali.student.r2.lum.lo.dto.LoInfo;
import org.kuali.student.r2.lum.lo.dto.LoLoRelationInfo;
import org.kuali.student.r2.lum.lo.service.LearningObjectiveService;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;

import java.util.List;

public class LumServiceMethodInvoker implements BusinessServiceMethodInvoker {
	final Logger LOG = Logger.getLogger(LumServiceMethodInvoker.class);
	private CluService cluService;
	private StatementService statementService;
	private LearningObjectiveService loService;
	private OrganizationService orgService;
	private AtpService atpService;
	private LRCService lrcService;

	@SuppressWarnings("unchecked")
    @Override
	public final void invokeServiceCalls(BaseDTOAssemblyNode results, ContextInfo contextInfo)
			throws DependentObjectsExistException, CircularRelationshipException,
			AssemblyException, UnsupportedActionException, UnsupportedOperationException, CircularReferenceException, ReadOnlyException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, AlreadyExistsException {

	    // For Delete operation process the tree from bottom up
	    if(NodeOperation.DELETE == results.getOperation()) {
            for(BaseDTOAssemblyNode childNode: (List<BaseDTOAssemblyNode>) results.getChildNodes()){
                invokeServiceCalls(childNode,contextInfo);
            }
	    }

	    invokeServiceCallOnResult(results, contextInfo);

		// For create/update process the child nodes from top to bottom
		if(NodeOperation.DELETE != results.getOperation()) {
		    for(BaseDTOAssemblyNode childNode: (List<BaseDTOAssemblyNode>) results.getChildNodes()){
		        invokeServiceCalls(childNode,contextInfo);
		    }
		}
	}

	/**
	 * @param results
	 * @throws AlreadyExistsException
	 * @throws DataValidationErrorException
	 * @throws DoesNotExistException
	 * @throws InvalidParameterException
	 * @throws MissingParameterException
	 * @throws OperationFailedException
	 * @throws PermissionDeniedException
	 * @throws AssemblyException
	 * @throws VersionMismatchException
	 * @throws DependentObjectsExistException
	 * @throws CircularRelationshipException
	 * @throws UnsupportedActionException
	 * @throws UnsupportedOperationException
	 * @throws CircularReferenceException
	 * @throws ReadOnlyException
	 * @throws org.kuali.student.r2.common.exceptions.DataValidationErrorException
	 * @throws org.kuali.student.r2.common.exceptions.DoesNotExistException
	 * @throws org.kuali.student.r2.common.exceptions.InvalidParameterException
	 * @throws org.kuali.student.r2.common.exceptions.MissingParameterException
	 * @throws org.kuali.student.r2.common.exceptions.OperationFailedException
	 * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
	 * @throws org.kuali.student.r2.common.exceptions.VersionMismatchException
	 * @throws org.kuali.student.r2.common.exceptions.AlreadyExistsException
	 */
	protected void invokeServiceCallOnResult(BaseDTOAssemblyNode results, ContextInfo contextInfo)
			throws AlreadyExistsException, DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, AssemblyException,
			VersionMismatchException, DependentObjectsExistException,
			CircularRelationshipException, UnsupportedActionException,
			UnsupportedOperationException, CircularReferenceException, ReadOnlyException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, AlreadyExistsException {
		Object nodeData = results.getNodeData();
		if (nodeData == null) {
			return;
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug(results.getOperation() + ": " + nodeData);
		}

		if(nodeData instanceof CluInfo){
			CluInfo clu = (CluInfo) nodeData;
			switch(results.getOperation()){
			case CREATE:
				CluInfo newClu = cluService.createClu(clu.getTypeKey(), clu, contextInfo);
				if(results.getAssembler() != null) {
					results.getAssembler().assemble(newClu, results.getBusinessDTORef(), true, contextInfo);
				}
				break;
			case UPDATE:
				CluInfo updatedClu = cluService.updateClu(clu.getId(), clu, contextInfo);
				if(results.getAssembler() != null) {
					results.getAssembler().assemble(updatedClu, results.getBusinessDTORef(), true, contextInfo);
				}
				break;
			case DELETE:
				cluService.deleteClu(clu.getId(), contextInfo);
				break;
			}
		}else if(nodeData instanceof CluCluRelationInfo){
			CluCluRelationInfo  relation = (CluCluRelationInfo) nodeData;
			switch(results.getOperation()){
			case CREATE:
				CluCluRelationInfo newCluRel = cluService.createCluCluRelation(relation.getCluId(), relation.getRelatedCluId(), relation.getTypeKey(), relation, contextInfo);
				// Update the businessDTO if one exists for the cluclurelation (for e.g. CourseJointInfo)
				if(null != results.getBusinessDTORef()) {
					results.getAssembler().assemble(newCluRel, results.getBusinessDTORef(), true, contextInfo);
				}
				break;
			case UPDATE:
				CluCluRelationInfo updatedCluRel = cluService.updateCluCluRelation(relation.getId(), relation, contextInfo);
				// Update the businessDTO if one exists for the cluclurelation (for e.g. CourseJointInfo)
				if(null != results.getBusinessDTORef()) {
					results.getAssembler().assemble(updatedCluRel, results.getBusinessDTORef(), true, contextInfo);
				}
				break;
			case DELETE:
				cluService.deleteCluCluRelation(relation.getId(), contextInfo);
				break;
			}
		}else if(nodeData instanceof CluResultInfo){
			CluResultInfo cluResult = (CluResultInfo) nodeData;
			switch(results.getOperation()){
			case CREATE:
				cluService.createCluResult(cluResult.getCluId(), cluResult.getTypeKey(), cluResult, contextInfo);
				break;
			case UPDATE:
				cluService.updateCluResult(cluResult.getId(), cluResult, contextInfo);
				break;
			case DELETE:
				cluService.deleteCluResult(cluResult.getId(), contextInfo);
				break;
			}
		}else if(nodeData instanceof LoCategoryRelationInfo){
			LoCategoryRelationInfo loCategoryRelation = (LoCategoryRelationInfo) nodeData;
			switch(results.getOperation()){
			case CREATE:
				loService.addLoCategoryToLo(loCategoryRelation.getCategoryId(), loCategoryRelation.getLoId(), contextInfo);
				break;
			case UPDATE:
				throw new UnsupportedOperationException("Can't call update on lo category relations, just add and remove");
			case DELETE:
				loService.removeLoCategoryFromLo(loCategoryRelation.getCategoryId(), loCategoryRelation.getLoId(), contextInfo);
				break;
			}
		}else if(nodeData instanceof LoInfo){
			LoInfo lo = (LoInfo) nodeData;
			switch(results.getOperation()){
			case CREATE:
				LoInfo createdLo = loService.createLo(lo.getLoRepositoryKey(), lo.getTypeKey(), lo, contextInfo);
				if(null != results.getBusinessDTORef()) {
					results.getAssembler().assemble(createdLo, results.getBusinessDTORef(), true, contextInfo);
				}
				break;
			case UPDATE:
				LoInfo updatedLo = loService.updateLo(lo.getId(), lo, contextInfo);
				if(null != results.getBusinessDTORef()) {
					results.getAssembler().assemble(updatedLo, results.getBusinessDTORef(), true, contextInfo);
				}
				break;
			case DELETE:
				loService.deleteLo(lo.getId(), contextInfo);
				break;
			}
		}else if(nodeData instanceof LoLoRelationInfo){
			LoLoRelationInfo loRelation = (LoLoRelationInfo) nodeData;
			switch(results.getOperation()){
			case CREATE:
				loService.createLoLoRelation(loRelation.getTypeKey(), loRelation, contextInfo);
				break;
			case UPDATE:
				loService.updateLoLoRelation(loRelation.getId(), loRelation, contextInfo);
 				break;
			case DELETE:
				loService.deleteLoLoRelation(loRelation.getId(), contextInfo);
				break;
			}
		}else if(nodeData instanceof CluLoRelationInfo){
			CluLoRelationInfo cluLoRelation = (CluLoRelationInfo) nodeData;
			switch(results.getOperation()){
			case CREATE:
				cluService.createCluLoRelation(cluLoRelation.getCluId(), cluLoRelation.getLoId(), cluLoRelation.getTypeKey(), cluLoRelation, contextInfo);
				break;
			case UPDATE:
				cluService.updateCluLoRelation(cluLoRelation.getLoId(), cluLoRelation, contextInfo);
				break;
			case DELETE:
				cluService.deleteCluLoRelation(cluLoRelation.getId(), contextInfo);
				break;
			}
		}else if(nodeData instanceof ResultValuesGroupInfo){
		    ResultValuesGroupInfo resultComponent = (ResultValuesGroupInfo) nodeData;
			switch(results.getOperation()){
			case CREATE:
                //Do a get-create on each of the result values to ensure they exist.
                for(String resultValue : resultComponent.getResultValueKeys()){
                    lrcService.getCreateResultValueForScale(resultValue, resultComponent.getResultScaleKey(), contextInfo).getKey();
                }
                ResultValuesGroupInfo createdResultComponent = lrcService.createResultValuesGroup(resultComponent.getResultScaleKey(), resultComponent.getTypeKey(), resultComponent, contextInfo);
				//Copy the created back to the reference Should there be an assembler for this?
				if(results.getBusinessDTORef()!=null&& results.getBusinessDTORef() instanceof ResultValuesGroupInfo){
				    ResultValuesGroupInfo resultComponentToUpdate = (ResultValuesGroupInfo) results.getBusinessDTORef();
					resultComponentToUpdate.setKey(createdResultComponent.getKey());
					resultComponentToUpdate.setTypeKey(createdResultComponent.getTypeKey());
					resultComponentToUpdate.setDescr(createdResultComponent.getDescr());
					resultComponentToUpdate.setEffectiveDate(createdResultComponent.getEffectiveDate());
					resultComponentToUpdate.setExpirationDate(createdResultComponent.getExpirationDate());
					resultComponentToUpdate.setMeta(createdResultComponent.getMeta());
					resultComponentToUpdate.setName(createdResultComponent.getName());
					resultComponentToUpdate.setResultValueKeys(createdResultComponent.getResultValueKeys());
					resultComponentToUpdate.setStateKey(createdResultComponent.getStateKey());
				}
				break;
			case UPDATE:
				lrcService.updateResultValuesGroup(resultComponent.getKey(), resultComponent, contextInfo);
				break;
			case DELETE:
				lrcService.deleteResultValuesGroup(resultComponent.getKey(), contextInfo);
				break;
			}
		} else if(nodeData instanceof RefStatementRelationInfo){
			RefStatementRelationInfo relation = (RefStatementRelationInfo) nodeData;
			switch(results.getOperation()){
			case CREATE:
				RefStatementRelationInfo created = statementService.createRefStatementRelation(relation);
				relation.setMetaInfo(created.getMetaInfo());
				break;
			case UPDATE:
				RefStatementRelationInfo updated = statementService.updateRefStatementRelation(relation.getId(), relation);
				relation.setMetaInfo(updated.getMetaInfo());
				break;
			case DELETE:
				statementService.deleteRefStatementRelation(relation.getId());
				break;
			}
		} else if(nodeData instanceof StatementInfo){
			StatementInfo statement = (StatementInfo) nodeData;
			switch(results.getOperation()){
			case CREATE:
				StatementInfo created = statementService.createStatement(statement.getType(), statement);
				if(results.getAssembler() != null && results.getBusinessDTORef() != null) {
					results.getAssembler().assemble(created, results.getBusinessDTORef(), true, contextInfo);
				}
				break;
			case UPDATE:
				StatementInfo updated = statementService.updateStatement(statement.getId(), statement);
				if(results.getAssembler() != null && results.getBusinessDTORef() != null) {
					results.getAssembler().assemble(updated, results.getBusinessDTORef(), true, contextInfo);
				}
				break;
			case DELETE:
				statementService.deleteStatement(statement.getId());
				break;
			}
		} else if(nodeData instanceof ReqComponentInfo){
			ReqComponentInfo reqComp = (ReqComponentInfo) nodeData;
			switch(results.getOperation()){
			case CREATE:
				ReqComponentInfo created = statementService.createReqComponent(reqComp.getType(), reqComp);
				reqComp.setMetaInfo(created.getMetaInfo());
				break;
			case UPDATE:
				ReqComponentInfo updated = statementService.updateReqComponent(reqComp.getId(), reqComp);
				reqComp.setMetaInfo(updated.getMetaInfo());
				break;
			case DELETE:
				statementService.deleteReqComponent(reqComp.getId());
				break;
			}
		}else if(nodeData instanceof StatementTreeViewInfo){
			StatementTreeViewInfo treeView = (StatementTreeViewInfo) nodeData;
			switch(results.getOperation()){
			case CREATE:
				StatementTreeViewInfo created = statementService.createStatementTreeView(treeView);
				if(results.getAssembler() != null && results.getBusinessDTORef() != null) {
					results.getAssembler().assemble(created, results.getBusinessDTORef(), true, contextInfo);
				}
				break;
			case UPDATE:
				StatementTreeViewInfo updated = statementService.updateStatementTreeView(treeView.getId(), treeView);
				if(results.getAssembler() != null && results.getBusinessDTORef() != null) {
					results.getAssembler().assemble(updated, results.getBusinessDTORef(), true, contextInfo);
				}
				break;
			case DELETE:
				statementService.deleteStatementTreeView(treeView.getId());
				break;
			}
   		}else if(nodeData instanceof CluPublicationInfo){
			CluPublicationInfo cluPublication = (CluPublicationInfo) nodeData;
			switch(results.getOperation()){
			case CREATE:
				cluService.createCluPublication(cluPublication.getCluId(), cluPublication.getTypeKey(), cluPublication, contextInfo);
				break;
			case UPDATE:
				cluService.updateCluPublication(cluPublication.getId(), cluPublication, contextInfo);
				break;
			case DELETE:
				cluService.deleteCluPublication(cluPublication.getId(), contextInfo);
				break;
			}
		}else{
			throw new UnsupportedActionException("This service invoker does not know how to handle nodeData for "+nodeData.getClass().getName());
		}

	}

	public CluService getCluService() {
		return cluService;
	}

	public void setCluService(CluService cluService) {
		this.cluService = cluService;
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

	public void setLrcService(LRCService lrcService) {
		this.lrcService = lrcService;
	}

}
