package org.kuali.student.common.ui.client.service;

import java.util.List;

import org.kuali.student.core.comment.dto.CommentInfo;

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

}
