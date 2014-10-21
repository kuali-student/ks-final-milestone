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

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.kuali.student.common.ui.client.service.BaseRpcServiceAsync;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.core.comment.dto.CommentInfo;

import java.util.List;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;

public interface CommentRpcServiceAsync extends BaseRpcServiceAsync {
    /**
     * Adds a comment to a reference.
     * @param refObjectId  reference object id
     * @param refObjectUri reference object Uri
     * @param commentInfo detailed information about the comment
     * @param callback detailed information about the comment
     * @throws Exception 
     */
    public void addComment(String refObjectId, String refObjectUri, CommentInfo commentInfo, AsyncCallback<CommentInfo> callback) throws Exception;
    /**
     * Retrieves comment information for a reference. The expected behavior is that if the caller is not authorized to invoke the getComments operation, a PERMISSION_DENIED error is returned. Assuming that the caller is authorized to invoke getComments, only comments that the caller is authorized to view are included in the returned commentInfoList; comments that the caller is unauthorized to view are filtered out of the return parameter.
     * @param refObjectId reference object id
     * @param refObjectUri reference object Uri
     * @param callback list of comment information
     * @throws Exception
     */
    public void getComments(String refObjectId, String refObjectUri, AsyncCallback<List<CommentInfo>> callback) throws Exception;
    /**
     * Retrieves comment information for a reference of a particular type. The expected behavior is that if the caller is not authorized to invoke the getCommentsByType operation, a PERMISSION_DENIED error is returned. Assuming that the caller is authorized to invoke getCommentsByType, only comments that the caller is authorized to view are included in the returned commentInfoList; comments that the caller is unauthorized to view are filtered out of the return parameter.
     * @param refObjectId reference object id
     * @param refObjectUri reference object Uri
     * @param commentTypeKey comment type
     * @param callback list of comment information
     * @throws Exception
     */
    public void getCommentsByType(String refObjectId, String refObjectUri, String commentTypeKey, AsyncCallback<List<CommentInfo>> callback) throws Exception;

    /**
     * Updates a comment for a reference.
     * @param refObjectId reference object id
     * @param refObjectUri reference object Uri
     * @param commentInfo detailed information about the comment
     * @param callback detailed information about the comment
     * @throws Exception 
     */
    public void updateComment(String refObjectId, String refObjectUri, CommentInfo commentInfo, AsyncCallback<CommentInfo> callback) throws Exception;
    
    /**
     * Removes a comment.
     * @param commentId id of comment to be removed
     * @param refObjectId reference object id
     * @param refObjectUri reference object Uri
     */
    public void removeComment(String commentId, String refObjectId, String refObjectUri, AsyncCallback<StatusInfo> callback) throws Exception;
    
//    /**
//     * Gets the comment types for a particular reference type.
//     * @param refObjectUri reference object Uri
//     */
//    public void getCommentTypesForReferenceType(String refObjectUri, AsyncCallback<List<TypeInfo>> callback) throws Exception;

    /**
     * Check for authorization to add a comment
     * @param id identifier of the object related to the reference type key
     * @param refObjectUri reference object Uri
     */
    public void isAuthorizedAddComment(String id, String refObjectUri, AsyncCallback<Boolean> callback);
    
    /**
     * user IdentityService to get user name
     * @param userId
     * @param callback
     */
    public void getUserRealName(String userId, AsyncCallback<String> callback);
    
    /**
     * user IdentityService to get user name by principalId
     * @param principalId
     * @param callback
     */
    public void getUserRealNameByPrincipalId(String principalId, AsyncCallback<String> callback);
    
    /**
     * user IdentityService to get principalName by principalId
     * @param principalId
     * @param callback
     */
    public void getPrincipalNameByPrincipalId(String principalId, AsyncCallback<String> callback);
}
