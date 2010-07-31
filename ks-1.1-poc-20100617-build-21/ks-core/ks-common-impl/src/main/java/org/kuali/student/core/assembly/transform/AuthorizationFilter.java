package org.kuali.student.core.assembly.transform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.kim.bo.role.dto.KimPermissionInfo;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.PermissionService;
import org.kuali.student.common.util.security.SecurityUtils;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.assembly.data.Metadata.Permission;
import org.kuali.student.core.assembly.util.AssemblerUtils;
import org.kuali.student.core.rice.authorization.PermissionType;

/**
 * The AuthorizationFilter is used to filter both metadata and data structures based
 * on permissions available.
 * 
 * @author Will
 *
 */
public class AuthorizationFilter extends AbstractDataFilter implements MetadataFilter{
    protected PermissionService permissionService;
        
    public static final String DOC_LEVEL_PERM_CHECK = "AuthorizationFilter.DocLevelPermCheck";
    	
	final Logger LOG = Logger.getLogger(AuthorizationFilter.class);
    
    @Override
	public void applyInboundDataFilter(Data data, Metadata metadata, Map<String,String> filterProperties) throws Exception {       
        if(metadata != null && !metadata.isCanEdit()) {
            throw new Exception("Document is read only");
        }
        
        List<QueryPath> dirtyPaths = AssemblerUtils.findDirtyElements(data);
        
        for(QueryPath path : dirtyPaths) {
            
            Metadata fieldMetadata = AssemblerUtils.get(metadata, path);
            
            if(null != fieldMetadata && !fieldMetadata.isCanEdit()) {
                throw new Exception("User does not have edit permission for field");
            }
            
        }
    }

	@Override
	public void applyOutboundDataFilter(Data data, Metadata metadata, Map<String,String> filterProperties)
			throws Exception {
		//TODO: Need to apply authz to the output (mostly full data should not be sent to UI if masked or hidden)
	}

	@Override
	public void applyMetadataFilter(String dtoName, Metadata metadata, Map<String, String> metadataProperties) {       
		applyPermissionsToMetadata(dtoName, metadata, metadataProperties);
	}
	
    protected void applyPermissionsToMetadata(String dtoName, Metadata metadata, Map<String, String> metadataProperties){
        Boolean authorized = null;
    
        String idType = metadataProperties.get(METADATA_ID_TYPE);
        String id = metadataProperties.get(METADATA_ID_VALUE);
        String docLevelPerm = metadataProperties.get(DOC_LEVEL_PERM_CHECK);
        String docType = metadataProperties.get(WorkflowFilter.WORKFLOW_DOC_TYPE);
        
        if (StringUtils.isNotBlank(id) && checkDocumentLevelPermissions(docLevelPerm)) {
            AttributeSet qualification = getQualification(idType, id, docType);
        	String currentUser = SecurityUtils.getCurrentUserId();
	        authorized = Boolean.valueOf(permissionService.isAuthorizedByTemplateName(currentUser, PermissionType.EDIT.getPermissionNamespace(),
	        		PermissionType.EDIT.getPermissionTemplateName(), null, qualification));
			LOG.info("Permission '" + PermissionType.EDIT.getPermissionNamespace() + "/" + PermissionType.EDIT.getPermissionTemplateName() 
					+ "' for user '" + currentUser + "': " + authorized);
	        metadata.setCanEdit(authorized.booleanValue());
        }  
        if(metadata != null && metadata.getProperties() != null) {
            for(Metadata child : metadata.getProperties().values()) {
                if(!child.isCanEdit()) {
                    setReadOnly(child, true);
                }
            }
        }
        // if we're checking doc level perms and user does not have "Edit Document" perm set metadata as readonly
        if (checkDocumentLevelPermissions(docLevelPerm) && Boolean.FALSE.equals(authorized)) {
        	setReadOnly(metadata, true);
        }
        // if not checking doc level perms or user does have "Edit Document" perm check field level authZ
        else {
	        Map<String, String> permissions = getFieldAccessPermissions(dtoName,idType,id, docType);
	        if (permissions != null) {
	            for (Map.Entry<String, String> permission : permissions.entrySet()) {
	                String dtoFieldPath = permission.getKey();
	                String fieldAccessLevel = permission.getValue();
	                String[] fieldPathTokens = getPathTokens(dtoFieldPath);
	                Metadata fieldMetadata = metadata.getProperties().get(fieldPathTokens[0]);
	                for(int i = 1; i < fieldPathTokens.length; i++) {
	                    if(fieldMetadata == null) {
	                        break;
	                    }
	                    fieldMetadata = fieldMetadata.getProperties().get(fieldPathTokens[i]);
	                }
	                if (fieldMetadata != null) {
	                    Permission perm = Metadata.Permission.kimValueOf(fieldAccessLevel);
	                    if (Permission.EDIT.equals(perm)) {
	                        setReadOnly(fieldMetadata, false);
	                        //fieldMetadata.setCanEdit(true);
	                    }
	                }
	            }
	        }
        }
    	
    }
    
    protected Map<String, String> getFieldAccessPermissions(String dtoName, String idType, String id, String docType) {
        try {
            //get permissions and turn into a map of fieldName=>access
            String principalId = SecurityUtils.getCurrentUserId();
            AttributeSet qualification = getQualification(idType, id, docType);
            AttributeSet permissionDetails = new AttributeSet("dtoName", dtoName);
            List<KimPermissionInfo> permissions = permissionService.getAuthorizedPermissionsByTemplateName(principalId,
            		PermissionType.FIELD_ACCESS.getPermissionNamespace(), PermissionType.FIELD_ACCESS.getPermissionTemplateName(), permissionDetails, qualification);
            Map<String, String> permMap = new HashMap<String, String>();
            if (permissions != null) {
                for (KimPermissionInfo permission : permissions) {
                    String dtoFieldKey = permission.getDetails().get("dtoFieldKey");
                    String fieldAccessLevel = permission.getDetails().get("fieldAccessLevel");
                    permMap.put(dtoFieldKey, fieldAccessLevel);
                }
            }
            return permMap;
        } catch (Exception e) {
            LOG.warn("Error calling permission service.", e);
        }
        return null;
    }
   

	private void setReadOnly(Metadata metadata, boolean readOnly) {
		metadata.setCanEdit(!readOnly);
		Map<String, Metadata> childProperties = metadata.getProperties();
		if (childProperties != null && childProperties.size() > 0) {
			for (Metadata child : childProperties.values()) {
				setReadOnly(child, readOnly);
			}
		}
	}
    
    protected boolean checkDocumentLevelPermissions(String docLevelPerm) {
    	return (docLevelPerm != null);
    }

	
    private static String[] getPathTokens(String fieldPath) {
        return (fieldPath != null && fieldPath.contains(".") ? fieldPath.split("\\.") : new String[]{fieldPath});
    }

    protected AttributeSet getQualification(String idType, String id, String doc_type) {
        AttributeSet qualification = new AttributeSet();
        qualification.put(doc_type, "CluCreditCourseProposal");
        qualification.put(idType, id);
        return qualification;
    }

	public PermissionService getPermissionService() {
		return permissionService;
	}

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}
}
