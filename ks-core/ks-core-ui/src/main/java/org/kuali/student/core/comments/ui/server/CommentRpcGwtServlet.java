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

package org.kuali.student.core.comments.ui.server;

import java.util.List;

import org.kuali.rice.kim.bo.entity.dto.KimEntityInfo;
import org.kuali.rice.kim.bo.entity.dto.KimEntityNameInfo;
import org.kuali.rice.kim.bo.entity.dto.KimPrincipalInfo;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.IdentityManagementService;
import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.common.rice.StudentIdentityConstants;
import org.kuali.student.common.rice.authorization.PermissionType;
import org.kuali.student.common.ui.server.gwt.BaseRpcGwtServletAbstract;
import org.kuali.student.core.comment.dto.CommentInfo;
import org.kuali.student.core.comment.dto.CommentTypeInfo;
import org.kuali.student.core.comment.service.CommentService;
import org.kuali.student.core.comments.ui.client.service.CommentRpcService;

public class CommentRpcGwtServlet extends BaseRpcGwtServletAbstract<CommentService> implements CommentRpcService {

	private static final long serialVersionUID = 1L;
	private IdentityManagementService identityService;
	
	public IdentityManagementService getIdentityService() {
        return identityService;
    }

    public void setIdentityService(IdentityManagementService identityService) {
        this.identityService = identityService;
    }

    @Override
	public CommentInfo addComment(String referenceId, String referenceTypeKey,
			CommentInfo commentInfo) throws Exception {
		return service.addComment(referenceId, referenceTypeKey, commentInfo);
	}

	@Override
	public List<CommentInfo> getComments(String referenceId,
			String referenceTypeKey) throws Exception {
		return service.getComments(referenceId, referenceTypeKey);
	}

	@Override
	public List<CommentInfo> getCommentsByType(String referenceId,
			String referenceTypeKey, String commentTypeKey) throws Exception {
		return service.getCommentsByType(referenceId, referenceTypeKey, commentTypeKey);
	}

	@Override
	public CommentInfo updateComment(String referenceId,
			String referenceTypeKey, CommentInfo commentInfo) throws Exception {
		return service.updateComment(referenceId, referenceTypeKey, commentInfo);
	}

	@Override
	public StatusInfo removeComment(String commentId, String referenceId,
			String referenceTypeKey) throws Exception {
		return service.removeComment(commentId, referenceId, referenceTypeKey);
	}

	@Override
	public List<CommentTypeInfo> getCommentTypesForReferenceType(String referenceTypeKey) throws Exception {
		return service.getCommentTypesForReferenceType(referenceTypeKey);
	}

	@Override
    public Boolean isAuthorizedAddComment(String id, String referenceTypeKey) {
		if (id != null && (!"".equals(id.trim()))) {
			AttributeSet permissionDetails = new AttributeSet(StudentIdentityConstants.KS_REFERENCE_TYPE_KEY, referenceTypeKey);
			if (getPermissionService().isPermissionDefinedForTemplateName(PermissionType.ADD_COMMENT.getPermissionNamespace(), PermissionType.ADD_COMMENT.getPermissionTemplateName(), permissionDetails)) {
	            AttributeSet roleQuals = new AttributeSet();
	            roleQuals.put(referenceTypeKey, id);
	            return Boolean.valueOf(getPermissionService().isAuthorizedByTemplateName(getCurrentUser(), PermissionType.ADD_COMMENT.getPermissionNamespace(), PermissionType.ADD_COMMENT.getPermissionTemplateName(), permissionDetails, roleQuals));
			}
		}
		return Boolean.TRUE;
    }
	
	private String nvl(String inString) {
	    return (inString == null)? "" : inString;
	}

    @Override
    public String getUserRealName(String principalName) {
        KimEntityInfo kimEntityInfo = identityService.getEntityInfoByPrincipalName(principalName);
        return getUserRealNameByEntityInfo(kimEntityInfo);
    }
    
    @Override
    public String getUserRealNameByPrincipalId(String principalId) {
        KimEntityInfo kimEntityInfo = identityService.getEntityInfoByPrincipalId(principalId);
        return getUserRealNameByEntityInfo(kimEntityInfo);
        
    }
    
    @Override
    public String getPrincipalNameByPrincipalId(String principalId) {
        KimPrincipalInfo kimPrincipalInfo = identityService.getPrincipal(principalId);
        return kimPrincipalInfo.getPrincipalName();
        
    }
    
    protected String getUserRealNameByEntityInfo(KimEntityInfo kimEntityInfo){
    	KimEntityNameInfo kimEntityNameInfo = (kimEntityInfo == null)? null : kimEntityInfo.getDefaultName();
        StringBuilder name = new StringBuilder(); 
        if (kimEntityNameInfo != null) {
            if (!nvl(kimEntityNameInfo.getFirstName()).trim().isEmpty()) {
                if (!name.toString().isEmpty()) {
                    name.append(" ");
                }
                name.append(nvl(kimEntityNameInfo.getFirstName()));
            }
            
            if (!nvl(kimEntityNameInfo.getMiddleName()).trim().isEmpty()) {
                if (!name.toString().isEmpty()) {
                    name.append(" ");
                }
                name.append(nvl(kimEntityNameInfo.getMiddleName()));
            }
            if (!nvl(kimEntityNameInfo.getLastName()).trim().isEmpty()) {
                if (!name.toString().isEmpty()) {
                    name.append(" ");
                }
                name.append(nvl(kimEntityNameInfo.getLastName()));
            }
        }
        return name.toString();
    }       

}
