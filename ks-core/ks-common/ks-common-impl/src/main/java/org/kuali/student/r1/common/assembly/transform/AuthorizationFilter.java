package org.kuali.student.r1.common.assembly.transform;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kim.api.permission.Permission;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.data.QueryPath;
import org.kuali.student.r1.common.assembly.util.AssemblerUtils;
import org.kuali.student.r1.common.rice.StudentIdentityConstants;
import org.kuali.student.r1.common.rice.StudentWorkflowConstants;
import org.kuali.student.r1.common.rice.authorization.PermissionTypeGwt;
import org.kuali.student.common.util.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The AuthorizationFilter is used to filter both metadata and data structures based
 * on permissions available.
 * 
 * @author Will
 *
 */
@Deprecated
public class AuthorizationFilter extends AbstractDataFilter implements MetadataFilter{
    protected PermissionService permissionService;
        
    public static final String DOC_LEVEL_PERM_CHECK = "AuthorizationFilter.DocLevelPermCheck";
    	
	private static final Logger LOG = LoggerFactory.getLogger(AuthorizationFilter.class);
    
    public enum PermissionEnum {
        EDIT("edit"), VIEW("view"), UNMASK("unmask"), PARTIAL_UNMASK("partialunmask");
        final String kimName;
        private PermissionEnum(String kimName) {
            this.kimName = kimName;
        }
        @Override
        public String toString() {
            return kimName;
        }
        public static PermissionEnum kimValueOf(String kimName) {
            for(PermissionEnum p : values()) {
                if(p.kimName.equals(kimName)) {
                    return p;
                }
            }
            //fall through
            throw new IllegalArgumentException("The value " + kimName + " is not enumerated in Permission"); 
        }
    }
	
    /**
     * This makes sure that for any dirty element in the inbound data that the user has authorization to edit 
     * that field.
     *  
     */
    @Override
	public void applyInboundDataFilter(Data data, Metadata metadata, Map<String,Object> filterProperties) throws Exception {       
        if(metadata != null && !metadata.isCanEdit()) {
            throw new Exception("Document is read only");
        }
        
        List<QueryPath> dirtyPaths = AssemblerUtils.findDirtyElements(data);
        
        for(QueryPath path : dirtyPaths) {
            if (path.contains("_runtimeData")) {
				Metadata fieldMetadata = AssemblerUtils.get(metadata, path);

				if (null != fieldMetadata && !fieldMetadata.isCanEdit()) {
					throw new Exception(
							"User does not have edit permission for field");
				}
			}
        }
        
        //TODO: If fields were masked, need to prevent masked values from being persisted.
    }

	@Override
	public void applyOutboundDataFilter(Data data, Metadata metadata, Map<String,Object> filterProperties)
			throws Exception {
		applyPermissionsToData(data, metadata, filterProperties);
	}

	/**
	 * Modify the metadata tree to apply data restrictions, currently this only does masking. 
	 */
	@Override
	public void applyMetadataFilter(String dtoName, Metadata metadata, Map<String, Object> metadataProperties) {       
		applyPermissionsToMetadata(dtoName, metadata, metadataProperties);
	}
	
	/**
	 * Apply masking rules to outbound data.
	 *  
	 * @param data
	 * @param metadata
	 * @param filterProperties
	 */
	protected void applyPermissionsToData(Data data, Metadata metadata, Map<String, Object> filterProperties){
		 if (data == null) {
		     return;
		 }

		 Map<String, Metadata> properties = metadata.getProperties();
		 for (Entry<String, Metadata> entry:properties.entrySet()){
			 String propName = entry.getKey();
			 Metadata propMetadata = entry.getValue();
			 Object dataValue = data.get(propName);
			 
			 if (dataValue != null){
				 //Apply the appropriate mask when user does not have edit access to this field
				 if (dataValue instanceof String && !propMetadata.isCanEdit()){
					 if (StringUtils.isNotBlank(propMetadata.getMaskFormatter())){
						 data.set(propName, propMetadata.getMaskFormatter());
					 } else if (StringUtils.isNotBlank(propMetadata.getPartialMaskFormatter())){
						 String value = (String)dataValue;
						 String mask = propMetadata.getPartialMaskFormatter();
						 dataValue = mask + value.substring(mask.length());
						 data.set(propName, (String)dataValue);
					 }
				 } else if (dataValue instanceof Data){
					 applyPermissionsToData((Data)data.get(propName), propMetadata, filterProperties);
				 }
			 }
		 }		
	}
	
	/**
	 * 
	 * @param dtoName
	 * @param metadata
	 * @param metadataProperties
	 */
    protected void applyPermissionsToMetadata(String dtoName, Metadata metadata, Map<String, Object> metadataProperties){
        
        boolean editDocumentAllowed;
    
        String idType = (String)metadataProperties.get(METADATA_ID_TYPE);
        String id = (String)metadataProperties.get(METADATA_ID_VALUE);
        String docLevelPerm = (String)metadataProperties.get(DOC_LEVEL_PERM_CHECK);
        String docType = (String)metadataProperties.get(StudentWorkflowConstants.WORKFLOW_DOCUMENT_TYPE);
        
        //See if user is allowed to edit the document.  
        if (checkDocumentLevelPermissions(docLevelPerm) && StringUtils.isNotBlank(id)) {
        	//If doc level permissions are enabled, lookup "Edit Document" permission for this object for this user. 
            Map<String, String> qualification = getQualification(idType, id, docType);
        	String currentUser = SecurityUtils.getCurrentUserId();
        	editDocumentAllowed = permissionService.isAuthorizedByTemplate(currentUser, PermissionTypeGwt.EDIT.getPermissionNamespace(),
                    PermissionTypeGwt.EDIT.getPermissionTemplateName(), null, qualification);
			LOG.info("Permission '{}/{}' for user '{}': {}",
                    PermissionTypeGwt.EDIT.getPermissionNamespace(), PermissionTypeGwt.EDIT.getPermissionTemplateName(), currentUser, editDocumentAllowed);
        }  else {
        	//Doc level permissions not enabled, by default allow user to edit
        	editDocumentAllowed = true;
        }
        
        
        if (!editDocumentAllowed){
        	//User not allowed to edit document, set every metadata element to read only
        	setReadOnly(metadata, true);
        } else {
        	///User allowed to edit document, need to check permissions for individual fields.
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
	                    PermissionEnum perm = PermissionEnum.kimValueOf(fieldAccessLevel);
	                    if (PermissionEnum.EDIT.equals(perm)) {
	                        setReadOnly(fieldMetadata, false);
	                    } else if (PermissionEnum.PARTIAL_UNMASK.equals(perm)){
	                    	fieldMetadata.setCanEdit(false);
	                    	fieldMetadata.setMaskFormatter("");
	                    } else if (PermissionEnum.UNMASK.equals(perm)){
	                    	fieldMetadata.setMaskFormatter("");
	                    	fieldMetadata.setPartialMaskFormatter("");	                    	
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
            Map<String, String> qualification = getQualification(idType, id, docType);
            Map<String, String> permissionDetails = new LinkedHashMap<String, String> ();
            permissionDetails.put ("dtoName", dtoName);
            List<Permission> permissions = permissionService.getAuthorizedPermissionsByTemplate(principalId,
            		PermissionTypeGwt.FIELD_ACCESS.getPermissionNamespace(),
                        PermissionTypeGwt.FIELD_ACCESS.getPermissionTemplateName(),
                        permissionDetails, 
                        qualification);
            Map<String, String> permMap = new HashMap<String, String>();
            if (permissions != null) {
                for (Permission permission : permissions) {
                    String dtoFieldKey = permission.getAttributes().get("dtoFieldKey");
                    String fieldAccessLevel = permission.getAttributes().get("fieldAccessLevel");
                    permMap.put(dtoFieldKey, fieldAccessLevel);
                }
            }
            return permMap;
        } catch (Exception e) {
            LOG.warn("Error calling permission service.", e);
        }
        return null;
    }
   
    /**
     * Sets the metadata node and all it's children to readOnly (i.e. canEdit=false).
     * 
     * @param metadata
     * @param readOnly
     */
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

    protected Map<String, String> getQualification(String idType, String id, String docType) {
        Map<String, String> qualification = new LinkedHashMap<String, String>();
        qualification.put(StudentIdentityConstants.DOCUMENT_TYPE_NAME, docType);
        qualification.put(idType, id);
        qualification.put(StudentIdentityConstants.QUALIFICATION_DATA_ID, id);
        //Put in a random number to avoid this request from being cached. Might want to do this only for specific templates to take advantage of caching
        qualification.put("RAND_NO_CACHE", UUID.randomUUID().toString());
        return qualification;
    }

	public PermissionService getPermissionService() {
		return permissionService;
	}

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}
}
