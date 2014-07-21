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

package org.kuali.student.core.comments.ui.client.service;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import org.kuali.student.common.ui.client.service.BaseRpcService;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.core.comment.dto.CommentInfo;

import java.util.List;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;

@RemoteServiceRelativePath("rpcservices/CommentRpcService")
public interface CommentRpcService extends BaseRpcService {
    /**
     * Adds a comment to a reference.
     * @param refObjectId  reference object id
     * @param refObjectUri reference object Uri
     * @param commentInfo detailed information about the comment
     * @return detailed information about the comment
     * @throws Exception 
     */
	
	
    public CommentInfo addComment(String refObjectId, String refObjectUri, CommentInfo commentInfo) throws Exception;
    /**
     * Retrieves comment information for a reference. The expected behavior is that if the caller is not authorized to invoke the getComments operation, a PERMISSION_DENIED error is returned. Assuming that the caller is authorized to invoke getComments, only comments that the caller is authorized to view are included in the returned commentInfoList; comments that the caller is unauthorized to view are filtered out of the return parameter.
     * @param refObjectId reference object id
     * @param refObjectUri reference object Uri
     * @return list of comment information
     * @throws Exception
     */
    public List<CommentInfo> getComments(String refObjectId, String refObjectUri) throws Exception;
    /**
     * Retrieves comment information for a reference of a particular type. The expected behavior is that if the caller is not authorized to invoke the getCommentsByType operation, a PERMISSION_DENIED error is returned. Assuming that the caller is authorized to invoke getCommentsByType, only comments that the caller is authorized to view are included in the returned commentInfoList; comments that the caller is unauthorized to view are filtered out of the return parameter.
     * @param refObjectId reference object id
     * @param refObjectUri reference object Uri
     * @param commentTypeKey comment type
     * @return list of comment information
     * @throws Exception
     */
    public List<CommentInfo> getCommentsByType(String refObjectId, String refObjectUri, String commentTypeKey) throws Exception;

    /**
     * Updates a comment for a reference.
     * @param refObjectId reference object id
     * @param refObjectUri reference object Uri
     * @param commentInfo detailed information about the comment
     * @return detailed information about the comment
     * @throws Exception 
     */
    public CommentInfo updateComment(String refObjectId, String refObjectUri, CommentInfo commentInfo) throws Exception;
    
    /**
     * Removes a comment.
     * @param commentId id of comment to be removed
     * @param refObjectId reference object id
     * @param refObjectUri reference object Uri
     */
    public StatusInfo removeComment(String commentId, String refObjectId, String refObjectUri) throws Exception;

//    /**
//     * Gets the comment types for a particular reference type.
//     * @param refObjectUri reference object Uri
//     */
//    public List<TypeInfo> getCommentTypesForReferenceType(String refObjectUri) throws Exception;

    /**
     * Check for authorization to add a comment
     * @param id identifier of the object
     * @param refObjectUri reference object Uri
     */
    public Boolean isAuthorizedAddComment(String id, String refObjectUri);
    
    /**
     * user IdentityService to get user name
     * @param userId
     */
    public String getUserRealName(String userId);
    
    /**
     * user IdentityService to get user name by principalId
     * @param principalId the principal id
     */
    public String getUserRealNameByPrincipalId(String principalId);
    
    /**
     * user IdentityService to get PrincipalName by principalId
     * @param principalId the principal id
     */
    public String getPrincipalNameByPrincipalId(String principalId);
    
}
