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

package org.kuali.student.common.ui.server.gwt.old;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import org.kuali.rice.kew.api.action.WorkflowDocumentActionsService;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.student.r1.common.assembly.data.AssemblyException;
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.old.Assembler;
import org.kuali.student.r1.common.assembly.old.data.SaveResult;
import org.kuali.student.r1.common.rice.StudentIdentityConstants;
import org.kuali.student.r1.common.rice.authorization.PermissionType;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.common.ui.client.service.BaseDataOrchestrationRpcService;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.common.ui.client.service.exceptions.OperationFailedException;
import org.kuali.student.common.ui.shared.IdAttributes;
import org.kuali.student.common.util.security.SecurityUtils;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.kuali.student.r2.common.util.ContextUtils;

/**
 * Generic implementation of data orchestration calls and workflow calls
 *
 */
@Deprecated
public abstract class AbstractBaseDataOrchestrationRpcGwtServlet extends RemoteServiceServlet implements BaseDataOrchestrationRpcService {
	//FIXME issues:
	// -The Type/state config is hardcoded here which will cause troubles with different types and states
	// -Workflow filter should be combined with this for save
	// -The exception handling here needs standardization.  Should RPC errors throw operation failed with just the message and log the message and exception?
	// also should calls that return Boolean ever throw exceptions?

	private static final long serialVersionUID = 1L;

	final Logger LOG = Logger.getLogger(AbstractBaseDataOrchestrationRpcGwtServlet.class);

	private Assembler<Data, Void> assembler;

    private WorkflowDocumentActionsService simpleDocService;
	private PermissionService permissionService;
	private IdentityService identityService;

	@Override
	public Data getData(String dataId) {
		try {
			return assembler.get(dataId);
		} catch (AssemblyException e) {
			LOG.error("Error getting Data.",e);
		}
		return null;
	}

	@Override
	public Metadata getMetadata(String id, Map<String,String> idAttributes) {

		try {
		    //FIXME: should not pass empty id. What to do here?
			String idType = "";
			if (idAttributes != null){
				idType = idAttributes.get(IdAttributes.ID_TYPE);
			}
			return assembler.getMetadata(idType, id, getDefaultMetaDataType(), getDefaultMetaDataState());
		} catch (AssemblyException e) {
			LOG.error("Error getting Metadata.",e);
		}
		return null;
	}

	@Override
	public DataSaveResult saveData(Data data) throws OperationFailedException {
		try {
			SaveResult<Data> saveResult = assembler.save(data);
			if (saveResult != null) {
				return new DataSaveResult(saveResult.getValidationResults(), saveResult.getValue());
			}
		} catch (Exception e) {
			LOG.error("Unable to save", e);
			throw new OperationFailedException("Unable to save");
		}
		return null;
	}


	protected String getCurrentUser() {
		String username = SecurityUtils.getCurrentPrincipalId();
		//backdoorId is only for convenience
		if(username==null&&this.getThreadLocalRequest().getSession().getAttribute("backdoorId")!=null){
			username=(String)this.getThreadLocalRequest().getSession().getAttribute("backdoorId");
        }
		return username;
	}

	protected boolean checkDocumentLevelPermissions() {
		return false;
	}

	public Boolean isAuthorized(PermissionType type, Map<String,String> attributes) {
        try
        {
            String user = getCurrentUser();
            boolean result = false;
            if (checkDocumentLevelPermissions()) {
                if (type == null) {
                    return null;
                }
                String namespaceCode = type.getPermissionNamespace();
                String permissionTemplateName = type.getPermissionTemplateName();
                Map<String, String> roleQuals = new LinkedHashMap<String, String>();
                roleQuals.put(StudentIdentityConstants.DOCUMENT_TYPE_NAME, getDefaultWorkflowDocumentType());
                if (attributes != null) {
                    roleQuals.putAll(attributes);
                }
                if (StringUtils.isNotBlank(namespaceCode) && StringUtils.isNotBlank(permissionTemplateName)) {
                    LOG.info("Checking Permission '" + namespaceCode + "/" + permissionTemplateName + "' for user '"
                            + user + "'");
                    result = getPermissionService().isAuthorizedByTemplate(user, namespaceCode, permissionTemplateName,
                            new LinkedHashMap<String, String>(), roleQuals);
                }
                else {
                    LOG.info("Can not check Permission with namespace '" + namespaceCode + "' and template name '"
                            + permissionTemplateName + "' for user '" + user + "'");
                    return Boolean.TRUE;
                }
            }
            else {
                LOG.info("Will not check for document level permissions. Defaulting authorization to true.");
                result = true;
            }
            LOG.info("Result of authorization check for user '" + user + "': " + result);
            return Boolean.valueOf(result);
        } catch (Exception ex) {
            // Log exception 
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

	protected abstract String deriveAppIdFromData(Data data);
	protected abstract String deriveDocContentFromData(Data data);
	protected abstract String getDefaultWorkflowDocumentType();
	protected abstract String getDefaultMetaDataState();
	protected abstract String getDefaultMetaDataType();

	//POJO methods
	public void setAssembler(Assembler<Data, Void> assembler) {
		this.assembler = assembler;
	}

	public PermissionService getPermissionService() {
        return permissionService;
    }

    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

	public IdentityService getIdentityService() {
    	return identityService;
    }

	public void setIdentityService(IdentityService identityService) {
    	this.identityService = identityService;
    }

	public void setSimpleDocService(WorkflowDocumentActionsService simpleDocService) {
		this.simpleDocService = simpleDocService;
	}

	protected Assembler<Data, Void> getAssembler() {
		return assembler;
	}

	protected WorkflowDocumentActionsService getSimpleDocService() {
		return simpleDocService;
	}

}
