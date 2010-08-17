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

package org.kuali.student.core.assembly;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.kim.bo.role.dto.KimPermissionInfo;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.PermissionService;
import org.kuali.student.common.util.security.SecurityUtils;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.Metadata.Permission;
import org.kuali.student.core.assembly.dictionary.old.MetadataServiceImpl;
import org.kuali.student.core.rice.authorization.PermissionType;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel;

@Deprecated
public abstract class BaseAssembler<TargetType, SourceType> implements Assembler<TargetType, SourceType> {
    protected final Logger LOG = Logger.getLogger(getClass());

    protected PermissionService permissionService;
    protected MetadataServiceImpl metadataService;

    // TODO: Below must be changed to use constants from KualiStudentKimAttributes class (class is currently in LUM)
    protected Map<String, String> getFieldAccessPermissions(String dtoName, String idType, String id) {
        try {
            //get permissions and turn into a map of fieldName=>access
            String principalId = SecurityUtils.getCurrentUserId();
            AttributeSet qualification = getQualification(idType, id);
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
            AttributeSet qualification = getQualification(idType, id);
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
    protected abstract AttributeSet getQualification(String idType, String id);
    
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }
    
    public void setMetadataService(MetadataServiceImpl metadataService) {
        this.metadataService = metadataService;
    }
    
}
