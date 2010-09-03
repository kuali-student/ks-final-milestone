/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.ui.server.gwt;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.PermissionService;
import org.kuali.student.common.ui.client.service.BaseDataOrchestrationRpcService;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.common.ui.client.service.exceptions.OperationFailedException;
import org.kuali.student.common.util.security.SecurityUtils;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.transform.AuthorizationFilter;
import org.kuali.student.core.assembly.transform.MetadataFilter;
import org.kuali.student.core.assembly.transform.TransformationManager;
import org.kuali.student.core.assembly.transform.WorkflowFilter;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.proposal.dto.ProposalInfo;
import org.kuali.student.core.proposal.service.ProposalService;
import org.kuali.student.core.rice.StudentIdentityConstants;
import org.kuali.student.core.rice.authorization.PermissionType;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Generic implementation of data gwt data operations calls.
 *
 */
public abstract class AbstractBaseDataOrchestrationRpcGwtServlet extends RemoteServiceServlet implements BaseDataOrchestrationRpcService {

	private static final long serialVersionUID = 1L;

	final Logger LOG = Logger.getLogger(AbstractBaseDataOrchestrationRpcGwtServlet.class);

	private TransformationManager transformationManager;
	
	private PermissionService permissionService;

    //TODO: why do we have this reference in the base class????
	private ProposalService proposalService;

	public Map<String,String> getDefaultFilterProperties(){
		Map<String, String> filterProperties = new HashMap<String,String>();
		filterProperties.put(MetadataFilter.METADATA_ID_TYPE, StudentIdentityConstants.QUALIFICATION_KEW_OBJECT_ID);
		filterProperties.put(WorkflowFilter.WORKFLOW_USER, SecurityUtils.getCurrentUserId());
		
		return filterProperties;
	}
	
	@Override
	public Data getData(String id) {
		Map<String, String> filterProperties = getDefaultFilterProperties();
		filterProperties.put(MetadataFilter.METADATA_ID_VALUE, id);
		
		try {
			String dtoId = id;
			//First check if this is a proposal id
            //TODO: Igor : Why do we check for this when getting the data for programs?
			if (proposalService != null){
				ProposalInfo proposalInfo = proposalService.getProposal(dtoId);
				filterProperties.put(WorkflowFilter.WORKFLOW_DOC_ID, proposalInfo.getWorkflowId());
				dtoId = proposalInfo.getProposalReference().get(0);
			}			
			
			Object dto = get(dtoId);
			if (dto != null){
				return transformationManager.transform(dto, filterProperties);
			}
		} catch (Exception e){
			LOG.error("Error getting Data.",e);			
		}
		
		return null;
	}

	@Override
	public Metadata getMetadata(String idType, String id) {
		Map<String, String> filterProperties = getDefaultFilterProperties();
		
		if (idType == null){
			filterProperties.remove(MetadataFilter.METADATA_ID_TYPE);
		} else {
			filterProperties.put(MetadataFilter.METADATA_ID_TYPE, idType);
		}
		filterProperties.put(MetadataFilter.METADATA_ID_VALUE, id);
		filterProperties.put(WorkflowFilter.WORKFLOW_DOC_TYPE, getDefaultWorkflowDocumentType());
		if (checkDocumentLevelPermissions()){
			filterProperties.put(AuthorizationFilter.DOC_LEVEL_PERM_CHECK, Boolean.TRUE.toString());
		}
		
		try {
			Metadata metadata = transformationManager.getMetadata(getDtoClass().getName(), filterProperties); 
			return metadata;
		} catch (Exception e) {
			LOG.error("Could not get metadata ", e);
			throw new RuntimeException("Failed to get metadata");
		}		
	}

	@Override
	public DataSaveResult saveData(Data data) throws OperationFailedException {
		Map<String, String> filterProperties = getDefaultFilterProperties();
		filterProperties.put(MetadataFilter.METADATA_ID_VALUE, (String)data.query("proposalId"));

		try {
			Object dto = transformationManager.transform(data, getDtoClass(), filterProperties);
			dto = save(dto);
				
			Data persistedData = transformationManager.transform(dto, filterProperties);
			return new DataSaveResult(null, persistedData);
		} catch (DataValidationErrorException dvee){
			return new DataSaveResult(dvee.getValidationResults(), null);
		} catch (Exception e) {
			LOG.error("Unable to save", e);
			throw new OperationFailedException("Unable to save");
		}
	}

	protected DataSaveResult _saveData(Data data, Map<String,String> filterProperties) throws OperationFailedException{
		try {
			filterProperties.put(MetadataFilter.METADATA_ID_VALUE, (String)data.query("id"));	

			Object dto = transformationManager.transform(data, getDtoClass(),filterProperties);
			dto = save(dto);
				
			Data persistedData = transformationManager.transform(dto,filterProperties);
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

	public Boolean isAuthorized(PermissionType type, Map<String,String> attributes) {
		String user = SecurityUtils.getCurrentUserId();
		boolean result = false;
		if (checkDocumentLevelPermissions()) {
			if (type == null) {
				return null;
			}
			String namespaceCode = type.getPermissionNamespace();
			String permissionTemplateName = type.getPermissionTemplateName();
			AttributeSet roleQuals = new AttributeSet("documentTypeName", getDefaultWorkflowDocumentType());
			if (attributes != null) {
				roleQuals.putAll(attributes);
			}
			if (StringUtils.isNotBlank(namespaceCode) && StringUtils.isNotBlank(permissionTemplateName)) {
				LOG.info("Checking Permission '" + namespaceCode + "/" + permissionTemplateName + "' for user '" + user + "'");
				result = getPermissionService().isAuthorizedByTemplateName(user, namespaceCode, permissionTemplateName, null, roleQuals);
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

	protected abstract String getDefaultWorkflowDocumentType();
	
	protected abstract String getDefaultMetaDataState();
	
	/**
	 * Implement this method to make to make service call to get DTO object. The method is called
	 * by the get(Data) method before it invokes transformationManager to convert DTO to a Data map 
	 * 
	 * @param id DTO id
	 * @return the dto retrieved by calling the appropriate service method
	 * @throws Exception
	 */
	protected abstract Object get(String id) throws Exception;
	
	/**
	 * Implement this method to make to make service call to get DTO object. The method is called	 
	 * by the get(Data) method before it invokes transformationManager to convert DTO to a Data map
	 * 
	 * @param id DTO id
	 * @return the dto retrieved by calling the appropriate service method
	 * @throws Exception
	 */ 
	protected abstract Object save(Object dto) throws Exception;
	
	/**
	 * Implement this method to return the type of the dto object.
	 * 
	 * @return The object type returned and expected by the get & save dto methods
	 */
	protected abstract Class<?> getDtoClass();
}
