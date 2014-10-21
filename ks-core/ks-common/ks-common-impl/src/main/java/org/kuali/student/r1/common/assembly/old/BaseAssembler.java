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

package org.kuali.student.r1.common.assembly.old;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kim.api.permission.PermissionService;
import org.kuali.student.r1.common.assembly.data.AssemblyException;
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.dictionary.old.MetadataServiceImpl;
import org.kuali.student.r1.common.rice.authorization.PermissionTypeGwt;
import org.kuali.student.common.util.security.SecurityUtils;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.infc.ValidationResult.ErrorLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public abstract class BaseAssembler<TargetType, SourceType> implements Assembler<TargetType, SourceType> {
    protected static final Logger LOG = LoggerFactory.getLogger(BaseAssembler.class);

    protected PermissionService permissionService;
    protected MetadataServiceImpl metadataService;
    
    public enum PermissionEnum {
        EDIT("edit"), READ_ONLY("readonly"), UNMASK("unmask");
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
    

    // TODO: Below must be changed to use constants from KualiStudentKimAttributes class (class is currently in LUM)
    protected Map<String, String> getFieldAccessPermissions(String dtoName, String idType, String id) {
        try {
            //get permissions and turn into a map of fieldName=>access
            String principalId = SecurityUtils.getCurrentUserId();
            Map<String,String> qualification = getQualification(idType, id);
            Map<String,String> permissionDetails = new LinkedHashMap <String,String> ();
            permissionDetails.put ("dtoName", dtoName);
//            List<Permission> permissions = permissionService.getAuthorizedPermissionsByTemplateName(principalId,
//            		PermissionTypeGwt.FIELD_ACCESS.getPermissionNamespace(), PermissionTypeGwt.FIELD_ACCESS.getPermissionTemplateName(), permissionDetails, qualification);
            Map<String, String> permMap = new HashMap<String, String>();
//            if (permissions != null) {
//                for (Permission permission : permissions) {
//                    String dtoFieldKey = permission.getAttributes().get
////                            "dtoFieldKey");
//                    String fieldAccessLevel = permission.getDetails().get("fieldAccessLevel");
//                    permMap.put(dtoFieldKey, fieldAccessLevel);
//                }
//            }
            return permMap;
        } catch (Exception e) {
            LOG.warn("Error calling permission service.", e);
        }
        return null;
    }

   /* protected Map<String, String> getScreenComponentAccessPermissions(List<SectionViewInfo> sectionViewInfo) {
        return null;
    }*/
    
	private void setReadOnly(Metadata metadata, boolean readOnly) {
		metadata.setCanEdit(!readOnly);
		Map<String, Metadata> childProperties = metadata.getProperties();
		if (childProperties != null && childProperties.size() > 0) {
			for (Metadata child : childProperties.values()) {
				setReadOnly(child, readOnly);
			}
		}
	}

    @Override
    public Metadata getMetadata(String idType, String id, String type, String state) throws AssemblyException {
        Metadata metadata = metadataService.getMetadata(getDataType(), type, state);
        applyPermissionsToMetadata(metadata,idType, id);
        return metadata;
    }


    protected void applyPermissionsToMetadata(Metadata metadata, String idType, String id){
        Boolean authorized = null;
        if (StringUtils.isNotBlank(id) && checkDocumentLevelPermissions()) {
            Map<String,String> qualification = getQualification(idType, id);
        	String currentUser = SecurityUtils.getCurrentUserId();
	        authorized = permissionService.isAuthorizedByTemplate(currentUser, PermissionTypeGwt.EDIT.getPermissionNamespace(),
                    PermissionTypeGwt.EDIT.getPermissionTemplateName(), null, qualification);
			LOG.info("Permission '{}/{}' for user '{}': {}",
                    PermissionTypeGwt.EDIT.getPermissionNamespace(), PermissionTypeGwt.EDIT.getPermissionTemplateName(), currentUser, authorized);
	        metadata.setCanEdit(authorized);
        }  
        if(metadata != null && metadata.getProperties() != null) {
            for(Metadata child : metadata.getProperties().values()) {
                if(!child.isCanEdit()) {
                    setReadOnly(child, true);
                }
            }
        }
        // if we're checking doc level perms and user does not have "Edit Document" perm set metadata as readonly
        if (checkDocumentLevelPermissions() && Boolean.FALSE.equals(authorized)) {
        	setReadOnly(metadata, true);
        }
        // if not checking doc level perms or user does have "Edit Document" perm check field level authZ
        else {
	        Map<String, String> permissions = getFieldAccessPermissions(getDtoName(),idType,id);
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
	                        //fieldMetadata.setCanEdit(true);
	                    }
	                }
	            }
	        }
        }
    	
    }

    public Metadata getDefaultMetadata() {
        return metadataService.getMetadata(getDataType(), null, null);
    }
    
    protected boolean hasValidationErrors(List<ValidationResultInfo> validationResults) {
        boolean result = false;
        if (validationResults != null) {
            for (ValidationResultInfo validationResult : validationResults) {
                if (validationResult.getLevel() == ErrorLevel.ERROR) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }
        
    public List<ValidationResultInfo> validate(Data data)  throws AssemblyException {
    	List<ValidationResultInfo> validationResults = null; 
    	
        return validationResults;
    }

    private static String[] getPathTokens(String fieldPath) {
        return (fieldPath != null && fieldPath.contains(".") ? fieldPath.split("\\.") : new String[]{fieldPath});
    }

    public boolean checkDocumentLevelPermissions() {
    	return false;
    }

    /**
     * 
     * This method should return the data type of the implementing assembler
     * 
     * @return the data type, i.e. "CreditCourseProposal" from the CreditCourseProposalAssembler
     */
    protected abstract String getDataType();

    /**
     * 
     * This method should return the DTO name of the implementing assembler
     * 
     * @return the DTO name, i.e. "kuali.lu.type.CreditCourse" from the CreditCourseProposalAssembler
     */
    protected abstract String getDtoName();

    /**
     * 
     * This method should return the root document property name
     * 
     * @return the document property name, i.e. "course" from the CreditCourseProposalAssembler 
     */
    protected abstract String getDocumentPropertyName();

    /**
     * 
     * This method should return the qualification name for the document type
     * 
     * @return the qualifications in at AttributeSet
     */

    protected abstract Map<String,String> getQualification(String idType, String id);
    
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }
    
    public void setMetadataService(MetadataServiceImpl metadataService) {
        this.metadataService = metadataService;
    }
    
}
