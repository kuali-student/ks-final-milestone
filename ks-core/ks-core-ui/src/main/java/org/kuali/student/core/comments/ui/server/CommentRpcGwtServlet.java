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

import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.entity.Entity;
import org.kuali.rice.kim.api.identity.name.EntityNameContract;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.student.common.ui.server.gwt.BaseRpcGwtServletAbstract;
import org.kuali.student.core.comments.ui.client.service.CommentRpcService;
import org.kuali.student.r1.common.rice.authorization.PermissionTypeGwt;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r1.common.rice.StudentIdentityConstants;
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.core.comment.service.CommentService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CommentRpcGwtServlet extends BaseRpcGwtServletAbstract<CommentService> implements CommentRpcService {

	private static final long serialVersionUID = 1L;
	private IdentityService identityService;
	
	public IdentityService getIdentityService() {
        return identityService;
    }

    public void setIdentityService(IdentityService identityService) {
        this.identityService = identityService;
    }

    @Override
	public CommentInfo addComment(String refObjectId, String refObjectUri,
			CommentInfo commentInfo) throws Exception {
    	return service.createComment(refObjectId, refObjectUri, commentInfo.getType(), commentInfo, ContextUtils.getContextInfo());
	}

	@Override
	public List<CommentInfo> getComments(String refObjectId,
			String refObjectUri) throws Exception {
		return service.getCommentsByRefObject(refObjectId, refObjectUri, ContextUtils.getContextInfo());
	}

	@Override
	public List<CommentInfo> getCommentsByType(String refObjectId,
			String refObjectUri, String commentTypeKey) throws Exception {
		return service.getCommentsByRefObject(refObjectId, refObjectUri, ContextUtils.getContextInfo());
	}

	@Override
	public CommentInfo updateComment(String refObjectId,
			String refObjectUri, CommentInfo commentInfo) throws Exception {
		return service.updateComment(commentInfo.getId(), commentInfo, ContextUtils.getContextInfo());
	}

	@Override
	public StatusInfo removeComment(String commentId, String refObjectId,
			String refObjectUri) throws Exception {
		return service.deleteComment(commentId, ContextUtils.getContextInfo());
	}

//	@Override
//	public List<TypeInfo> getCommentTypesForReferenceType(String refObjectUri) throws Exception {
//		// return service.getCommentTypesForReferenceType(refObjectUri);
//        throw new OperationNotSupportedException("This method has been dropped in R2");
//	}

	@Override    public Boolean isAuthorizedAddComment(String id, String refObjectUri) {
		if (id != null && (!"".equals(id.trim()))) {
			Map<String,String> permissionDetails = new LinkedHashMap<String,String>();
                        permissionDetails.put (StudentIdentityConstants.KS_REFERENCE_TYPE_KEY, refObjectUri);
			if (getPermissionService().isPermissionDefinedByTemplate(PermissionTypeGwt.ADD_COMMENT.getPermissionNamespace(), PermissionTypeGwt.ADD_COMMENT.getPermissionTemplateName(), permissionDetails)) {
	            Map<String,String> roleQuals = new LinkedHashMap<String,String>();
	            roleQuals.put(refObjectUri, id);
	            return Boolean.valueOf(getPermissionService().isAuthorizedByTemplate(getCurrentUser(), PermissionTypeGwt.ADD_COMMENT.getPermissionNamespace(), PermissionTypeGwt.ADD_COMMENT.getPermissionTemplateName(), permissionDetails, roleQuals));
			}
		}
		return Boolean.TRUE;
    }
	
	private String nvl(String inString) {
	    return (inString == null)? "" : inString;
	}

    // @Override

    public String getUserRealName(String userId) {
        Entity kimEntityInfo = identityService.getEntityByPrincipalId(userId);
        return getUserRealNameByEntityInfo(kimEntityInfo);
    }
    
    @Override
    public String getUserRealNameByPrincipalId(String principalId) {
        Entity kimEntityInfo = identityService.getEntityByPrincipalId(principalId);
        return getUserRealNameByEntityInfo(kimEntityInfo);
        
    }
    
    @Override
    public String getPrincipalNameByPrincipalId(String principalId) {
        Principal kimPrincipalInfo = identityService.getPrincipal(principalId);
        return kimPrincipalInfo.getPrincipalName();
        
    }
    
    protected String getUserRealNameByEntityInfo(Entity kimEntityInfo){
        EntityNameContract kimEntityNameInfo = (kimEntityInfo == null)? null : kimEntityInfo.getDefaultName();
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