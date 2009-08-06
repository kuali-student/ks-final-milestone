package org.kuali.student.common.ui.client.service;

import java.util.List;

import org.kuali.student.core.comment.dto.CommentInfo;
import org.kuali.student.core.comment.dto.CommentTypeInfo;
import org.kuali.student.core.dto.StatusInfo;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpcservices/CommentRpcService")
public interface CommentRpcService extends BaseRpcService {
    /**
     * Adds a comment to a reference.
     * @param referenceId identifier of reference
     * @param referenceTypeKey reference type
     * @param commentInfo detailed information about the comment
     * @return detailed information about the comment
     * @throws Exception 
     */
    public CommentInfo addComment(String referenceId, String referenceTypeKey, CommentInfo commentInfo) throws Exception;
    /**
     * Retrieves comment information for a reference. The expected behavior is that if the caller is not authorized to invoke the getComments operation, a PERMISSION_DENIED error is returned. Assuming that the caller is authorized to invoke getComments, only comments that the caller is authorized to view are included in the returned commentInfoList; comments that the caller is unauthorized to view are filtered out of the return parameter.
     * @param referenceId reference identifier
     * @param referenceTypeKey reference type
     * @return list of comment information
     * @throws Exception
     */
    public List<CommentInfo> getComments(String referenceId, String referenceTypeKey) throws Exception;
    /**
     * Retrieves comment information for a reference of a particular type. The expected behavior is that if the caller is not authorized to invoke the getCommentsByType operation, a PERMISSION_DENIED error is returned. Assuming that the caller is authorized to invoke getCommentsByType, only comments that the caller is authorized to view are included in the returned commentInfoList; comments that the caller is unauthorized to view are filtered out of the return parameter.
     * @param referenceId reference identifier
     * @param referenceTypeKey reference type
     * @param commentTypeKey comment type
     * @return list of comment information
     * @throws Exception
     */
    public List<CommentInfo> getCommentsByType(String referenceId, String referenceTypeKey, String commentTypeKey) throws Exception;

    /**
     * Updates a comment for a reference.
     * @param referenceId identifier of reference
     * @param referenceTypeKey reference type
     * @param commentInfo detailed information about the comment
     * @return detailed information about the comment
     * @throws Exception 
     */
    public CommentInfo updateComment(String referenceId, String referenceTypeKey, CommentInfo commentInfo) throws Exception;
    
    /**
     * Removes a comment.
     * @param commentId id of comment to be removed
     * @param referenceId identifier of reference
     * @param referenceTypeKey reference type
     */
    public StatusInfo removeComment(String commentId, String referenceId, String referenceTypeKey) throws Exception;

    /**
     * Gets the comment types for a particular reference type.
     * @param referenceTypeKey reference type
     */
    public List<CommentTypeInfo> getCommentTypesForReferenceType(String referenceTypeKey) throws Exception; 
}
