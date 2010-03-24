/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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

import java.util.List;

import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.student.common.ui.client.service.CommentRpcService;
import org.kuali.student.core.comment.dto.CommentInfo;
import org.kuali.student.core.comment.dto.CommentTypeInfo;
import org.kuali.student.core.comment.service.CommentService;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.rice.StudentIdentityConstants;

public class CommentRpcGwtServlet extends BaseRpcGwtServletAbstract<CommentService> implements CommentRpcService {

	private static final long serialVersionUID = 1L;

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
    public Boolean isAuthorizedAddComment(String referenceId, String referenceTypeKey) {
		if (referenceId != null && (!"".equals(referenceId.trim()))) {
			String namespaceCode = "KS-SYS";
			String permissionTemplateName = "Comment on Document";
			String user = getCurrentUser();
			AttributeSet permissionDetails = new AttributeSet();
			AttributeSet roleQuals = new AttributeSet();
			roleQuals.put(StudentIdentityConstants.QUALIFICATION_KEW_OBJECT_TYPE,referenceTypeKey);
			roleQuals.put(StudentIdentityConstants.QUALIFICATION_KEW_OBJECT_ID, referenceId);
			return Boolean.valueOf(getPermissionService().isAuthorizedByTemplateName(user, namespaceCode, permissionTemplateName, permissionDetails, roleQuals));
		}
		return Boolean.TRUE;
    }

}
