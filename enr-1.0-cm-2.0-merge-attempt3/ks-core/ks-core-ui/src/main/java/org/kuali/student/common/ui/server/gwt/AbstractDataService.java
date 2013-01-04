package org.kuali.student.common.ui.server.gwt;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import java.net.URLDecoder;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.kew.api.document.DocumentDetail;
import org.kuali.rice.kew.api.document.WorkflowDocumentService;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.common.ui.server.gwt.DataService;
import org.kuali.student.common.ui.shared.IdAttributes;
import org.kuali.student.common.util.security.SecurityUtils;
import org.kuali.student.core.assembly.transform.ProposalWorkflowFilter;
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.transform.AuthorizationFilter;
import org.kuali.student.r1.common.assembly.transform.MetadataFilter;
import org.kuali.student.r1.common.assembly.transform.TransformFilter;
import org.kuali.student.r1.common.assembly.transform.TransformFilter.TransformFilterAction;
import org.kuali.student.r1.common.assembly.transform.TransformationManager;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r1.common.rice.StudentIdentityConstants;
import org.kuali.student.r1.common.rice.authorization.PermissionType;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r1.core.proposal.dto.ProposalInfo;
import org.kuali.student.r1.core.proposal.service.ProposalService;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly=true,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
public abstract class AbstractDataService implements DataService{

	private static final long serialVersionUID = 1L;

	final Logger LOG = Logger.getLogger(AbstractDataService.class);

	private TransformationManager transformationManager;
	
	private PermissionService permissionService;
	
	//TODO: why do we have this reference in the base class????
	private ProposalService proposalService;
	
	private WorkflowDocumentService workflowDocumentService;

	@Override
	public Data getData(String id, ContextInfo contextInfo) throws OperationFailedException {
		Map<String, Object> filterProperties = getDefaultFilterProperties(contextInfo);
		filterProperties.put(TransformFilter.FILTER_ACTION, TransformFilterAction.GET);
		filterProperties.put(MetadataFilter.METADATA_ID_VALUE, id);
		
		String dtoId = URLDecoder.decode(id);
		//First check if this is a proposal id
        //TODO: Igor : Why do we check for this when getting the data for programs?
		try{
			if (proposalService != null){
			    ProposalInfo proposalInfo = proposalService.getProposal(dtoId);
				filterProperties.put(ProposalWorkflowFilter.PROPOSAL_INFO, proposalInfo);
				dtoId = proposalInfo.getProposalReference().get(0);
			}			

			Object dto = get(dtoId, contextInfo);
			if (dto != null){
				return transformationManager.transform(dto, getDtoClass().getName(), filterProperties);
			}
		} catch(DoesNotExistException e){
			return null;
		} catch (Exception e) {
			LOG.error("Error getting data",e);
			throw new OperationFailedException("Error getting data",e);
		}
		return null;
	}

	@Override
	public Metadata getMetadata(String id, Map<String, String> attributes,ContextInfo contextInfo) {
		Map<String, Object> filterProperties = getDefaultFilterProperties(contextInfo);
		filterProperties.put(MetadataFilter.METADATA_ID_VALUE, id);
		
		//Place id attributes into filter properties
		String idType = (attributes != null? attributes.get(IdAttributes.ID_TYPE):null);
		String docType = (attributes != null ? attributes.get(StudentIdentityConstants.DOCUMENT_TYPE_NAME):null);
		String dtoState = (attributes != null ? attributes.get(DtoConstants.DTO_STATE):null);
		String dtoNextState = (attributes != null ? attributes.get(DtoConstants.DTO_NEXT_STATE):null);
		String workflowNode = (attributes != null ? attributes.get(DtoConstants.DTO_WORKFLOW_NODE):null);
				
		if (idType == null){
			filterProperties.remove(MetadataFilter.METADATA_ID_TYPE);
		} else {
			filterProperties.put(MetadataFilter.METADATA_ID_TYPE, idType);
		}
	
		if (docType == null){
			filterProperties.put(ProposalWorkflowFilter.WORKFLOW_DOC_TYPE, getDefaultWorkflowDocumentType());
		} else {
			filterProperties.put(ProposalWorkflowFilter.WORKFLOW_DOC_TYPE, docType);
		}

		if (dtoState != null){
			filterProperties.put(DtoConstants.DTO_STATE, dtoState);			
		}
		
		if (dtoNextState != null){
			filterProperties.put(DtoConstants.DTO_NEXT_STATE, dtoNextState);			
		}

		if (workflowNode != null){
			filterProperties.put(DtoConstants.DTO_WORKFLOW_NODE, workflowNode);			
		}

		if (checkDocumentLevelPermissions()){
			filterProperties.put(AuthorizationFilter.DOC_LEVEL_PERM_CHECK, Boolean.TRUE.toString());
		}
		
		Metadata metadata = transformationManager.getMetadata(getDtoClass().getName(), filterProperties); 
		return metadata;
	}

	@Override
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public DataSaveResult saveData(Data data, ContextInfo contextInfo) throws OperationFailedException, DataValidationErrorException, VersionMismatchException{
		Map<String, Object> filterProperties = getDefaultFilterProperties(contextInfo);
		filterProperties.put(TransformFilter.FILTER_ACTION, TransformFilterAction.SAVE);
		
		DataSaveResult saveResult = new DataSaveResult();
		try {
			//Convert data object to dto object
			Object dto = transformationManager.transform(data, getDtoClass(), filterProperties);
			
			//This calls save method for DataService impl, which makes the needed service calls to persist dto
			//The service call should do it's own validation, any errors will cause DataValidationErrorException
			//and is handled in the catch below.
			dto = save(dto, filterProperties, contextInfo);
			
			//Validate saved data again to get validation warnings that may exist on the data
			List<ValidationResultInfo> validationResults = validate(dto, contextInfo);
			
			//Convert saved data object back to data object to send to UI
			Data persistedData = transformationManager.transform(dto, getDtoClass().getName(), filterProperties);			
			
			saveResult.setValue(persistedData);
			saveResult.setValidationResults(validationResults);			
		}catch (DataValidationErrorException dvee){
			//Throw the error, we need the the transaction to be rolled back when service throws an error.
			throw dvee;
		}catch (OperationFailedException ofe){
		    throw ofe;
		}catch (VersionMismatchException vme){
		    throw vme;
		}catch (Exception e) {
			LOG.error("Failed to save data",e);
			throw new OperationFailedException("Failed to save data",e);
		}
		
		return saveResult;
	}
	
	

	@Override
	public List<ValidationResultInfo> validateData(Data data, ContextInfo contextInfo) throws OperationFailedException {
		List<ValidationResultInfo> validationResults;
		
		try {
			Metadata metadata = transformationManager.getUnfilteredMetadata(getDtoClass().getName());
			Object dto = null;
			dto = transformationManager.getMapper().convertFromData(data, getDtoClass(), metadata);
			validationResults = validate(dto, contextInfo);
		} catch (Exception e) {
			throw new OperationFailedException("Unable to validate data", e);
		}

		return validationResults;
	}

	@Override
    public Boolean isAuthorized(PermissionType type, Map<String,String> attributes, ContextInfo contextInfo) {
        String user = SecurityUtils.getCurrentUserId();
        boolean result = false;
        if (checkDocumentLevelPermissions()) {
            if (type == null) {
                return null;
            }
            String namespaceCode = type.getPermissionNamespace();
            String permissionTemplateName = type.getPermissionTemplateName();
            
            Map<String,String> roleQuals = new LinkedHashMap<String,String>();
            if (attributes != null) {
                //Determine permission details and role qualifiers to pass into permission service.
                //We will use same attributes for permission details and role qualifiers (never hurts to use more than needed)
                
                if (proposalService != null){
                    ProposalInfo proposalInfo = null;
                    try {
                        //Retrieve the proposal info provided the proposal id (passed in as KS_JEW_OBJECT_ID) or the workflow id
                        if (attributes.containsKey(IdAttributes.IdType.KS_KEW_OBJECT_ID.toString())){
                            proposalInfo = proposalService.getProposal(attributes.get(IdAttributes.IdType.KS_KEW_OBJECT_ID.toString()));
                        } else if (attributes.containsKey(IdAttributes.IdType.DOCUMENT_ID.toString())){
                            proposalInfo = proposalService.getProposalByWorkflowId(attributes.get(IdAttributes.IdType.DOCUMENT_ID.toString()));
                        }
                        
                        //Check if the route status is in the list of allowed statuses
                        DocumentDetail docDetail = getWorkflowDocumentService().getDocumentDetail(proposalInfo.getWorkflowId());
                        String docStatusCode = getWorkflowDocumentService().getDocumentStatus(proposalInfo.getWorkflowId()).getCode();

                        //Populate attributes with additional attributes required for permission check
                        if (proposalInfo != null){
                            attributes.put(IdAttributes.IdType.KS_KEW_OBJECT_ID.toString(), proposalInfo.getId());
                            attributes.put(StudentIdentityConstants.QUALIFICATION_DATA_ID, proposalInfo.getId()); // this is what most of the permissions/roles check
                            attributes.put(IdAttributes.IdType.DOCUMENT_ID.toString(), proposalInfo.getWorkflowId());
                            attributes.put(StudentIdentityConstants.DOCUMENT_TYPE_NAME, proposalInfo.getType());
                            attributes.put(StudentIdentityConstants.ROUTE_STATUS_CODE, docStatusCode);
                            
                            //Call t his to add any additional attributes that child classes need
                            addAdditionalAttributes(attributes,proposalInfo,docDetail);
                        }
                    } catch (Exception e){
                        LOG.error("Could not retrieve proposal to determine permission qualifiers:" + e.toString());
                    }
                }
                
                //Put in additional random number for role qualifiers. This is to avoid this request from being cached. 
                //Might want to do this only for specific templates to take advantage of caching
                attributes.put("RAND_NO_CACHE", UUID.randomUUID().toString());
                roleQuals.putAll(attributes);
            }
            if (StringUtils.isNotBlank(namespaceCode) && StringUtils.isNotBlank(permissionTemplateName)) {
                LOG.info("Checking Permission '" + namespaceCode + "/" + permissionTemplateName + "' for user '" + user + "'");
                result = getPermissionService().isAuthorizedByTemplate(user, namespaceCode, permissionTemplateName, new LinkedHashMap<String,String>(), roleQuals);
            }
            else {
                LOG.info("Can not check Permission with namespace '" + namespaceCode + "' and template name '" + permissionTemplateName + "' for user '" + user + "'");
                return Boolean.TRUE;
            }
        }
        else {
            LOG.info("Will not check for document level permissions. Defaulting authorization to true.");
            result = true;
        }
        LOG.info("Result of authorization check for user '" + user + "': " + result);
        return Boolean.valueOf(result);
    }
	
	protected void addAdditionalAttributes(Map<String, String> attributes,
            ProposalInfo proposalInfo, DocumentDetail docDetail) {
        return;
    }
	
	public Map<String, Object> getDefaultFilterProperties(ContextInfo contextInfo){
		Map<String, Object> filterProperties = new HashMap<String,Object>();
		filterProperties.put(MetadataFilter.METADATA_ID_TYPE, StudentIdentityConstants.QUALIFICATION_KEW_OBJECT_ID);
		filterProperties.put(ProposalWorkflowFilter.WORKFLOW_USER, SecurityUtils.getCurrentUserId());
		
		return filterProperties;
	}
	
	protected DataSaveResult _saveData(Data data, Map<String, Object> filterProperties, ContextInfo contextInfo) throws OperationFailedException{
		try {
			filterProperties.put(MetadataFilter.METADATA_ID_VALUE, (String)data.query("id"));	

			Object dto = transformationManager.transform(data, getDtoClass(),filterProperties);
			dto = save(dto, filterProperties, contextInfo);
				
			Data persistedData = transformationManager.transform(dto,getDtoClass().getName(), filterProperties);
			return new DataSaveResult(null, persistedData);
		} catch (DataValidationErrorException dvee){
			return new DataSaveResult(dvee.getValidationResults(), null);
		} catch (Exception e) {
			LOG.error("Unable to save", e);
			throw new OperationFailedException("Unable to save");
		}		
	}
	
	protected boolean checkDocumentLevelPermissions() {
		return false;
	}



	public TransformationManager getTransformationManager() {
		return transformationManager;
	}

	public void setTransformationManager(TransformationManager transformationManager) {
		this.transformationManager = transformationManager;
	}

	public PermissionService getPermissionService() {
		return permissionService;
	}

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}
	
	public ProposalService getProposalService() {
		return proposalService;
	}

	public void setProposalService(ProposalService proposalService) {
		this.proposalService = proposalService;
	}
	
	public WorkflowDocumentService getWorkflowDocumentService() {
        return workflowDocumentService;
    }

    public void setWorkflowDocumentService(WorkflowDocumentService workflowDocumentService) {
        this.workflowDocumentService = workflowDocumentService;
    }
	
	protected abstract String getDefaultWorkflowDocumentType();
	
	protected abstract String getDefaultMetaDataState();
	
	/**
	 * Implement this method to make to make service call to get DTO object. The method is called
	 * by the get(Data) method before it invokes transformationManager to convert DTO to a Data map 
	 * 
	 * @param id DTO id
	 * @param contextInfo
	 * @return the dto retrieved by calling the appropriate service method
	 * @throws Exception
	 */
	protected abstract Object get(String id, ContextInfo contextInfo) throws Exception;
	
	/**
	 * Implement this method to make a service call to get DTO object. The method is called	 
	 * by the save(Data) method after it invokes transformationManager to convert Data map to DTO 
	 * 
	 * @param dto
	 * @param properties
	 * @return the persisted dto object
	 * @throws Exception
	 */
	protected abstract Object save(Object dto, Map<String, Object> properties, ContextInfo contextInfo) throws Exception;
	
	/**
	 * Implement this method to make a service call to get DTO object. The method is called	 
	 * in the save(data) method before calling the save(dto,properties) method to validate the data
 
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	protected abstract List<ValidationResultInfo> validate(Object dto, ContextInfo contextInfo) throws Exception;

	/**
	 * Implement this method to return the type of the dto object.
	 * 
	 * @return The object type returned and expected by the get & save dto methods
	 */
	protected abstract Class<?> getDtoClass();
    
}
