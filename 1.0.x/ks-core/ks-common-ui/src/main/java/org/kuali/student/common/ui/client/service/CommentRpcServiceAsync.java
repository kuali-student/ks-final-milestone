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

package org.kuali.student.common.ui.client.service;

import java.util.List;

import org.kuali.student.core.comment.dto.CommentInfo;
import org.kuali.student.core.comment.dto.CommentTypeInfo;
import org.kuali.student.core.dto.StatusInfo;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CommentRpcServiceAsync extends BaseRpcServiceAsync {
    /**
     * Adds a comment to a reference.
     * @param referenceId identifier of reference
     * @param referenceTypeKey reference type
     * @param commentInfo detailed information about the comment
     * @param detailed information about the comment
     * @throws Exception 
     */
    public void addComment(String referenceId, String referenceTypeKey, CommentInfo commentInfo, AsyncCallback<CommentInfo> callback) throws Exception;
    /**
     * Retrieves comment information for a reference. The expected behavior is that if the caller is not authorized to invoke the getComments operation, a PERMISSION_DENIED error is returned. Assuming that the caller is authorized to invoke getComments, only comments that the caller is authorized to view are included in the returned commentInfoList; comments that the caller is unauthorized to view are filtered out of the return parameter.
     * @param referenceId reference identifier
     * @param referenceTypeKey reference type
     * @param list of comment information
     * @throws Exception
     */
    public void getComments(String referenceId, String referenceTypeKey, AsyncCallback<List<CommentInfo>> callback) throws Exception;
    /**
     * Retrieves comment information for a reference of a particular type. The expected behavior is that if the caller is not authorized to invoke the getCommentsByType operation, a PERMISSION_DENIED error is returned. Assuming that the caller is authorized to invoke getCommentsByType, only comments that the caller is authorized to view are included in the returned commentInfoList; comments that the caller is unauthorized to view are filtered out of the return parameter.
     * @param referenceId reference identifier
     * @param referenceTypeKey reference type
     * @param commentTypeKey comment type
     * @param list of comment information
     * @throws Exception
     */
    public void getCommentsByType(String referenceId, String referenceTypeKey, String commentTypeKey, AsyncCallback<List<CommentInfo>> callback) throws Exception;

    /**
     * Updates a comment for a reference.
     * @param referenceId identifier of reference
     * @param referenceTypeKey reference type
     * @param commentInfo detailed information about the comment
     * @param detailed information about the comment
     * @throws Exception 
     */
    public void updateComment(String referenceId, String referenceTypeKey, CommentInfo commentInfo, AsyncCallback<CommentInfo> callback) throws Exception;
    
    /**
     * Removes a comment.
     * @param commentId id of comment to be removed
     * @param referenceId identifier of reference
     * @param referenceTypeKey reference type
     */
    public void removeComment(String commentId, String referenceId, String referenceTypeKey, AsyncCallback<StatusInfo> callback) throws Exception;
    
    /**
     * Gets the comment types for a particular reference type.
     * @param referenceTypeKey reference type
     */
    public void getCommentTypesForReferenceType(String referenceTypeKey, AsyncCallback<List<CommentTypeInfo>> callback) throws Exception;

    /**
     * Check for authorization to add a comment
     * @param referenceId identifier of reference
     * @param referenceTypeKey reference type
     */
    public void isAuthorizedAddComment(String referenceId, String referenceTypeKey, AsyncCallback<Boolean> callback);
}
