/*
 * Copyright 2011 The Kuali Foundation Licensed under the
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

package org.kuali.student.r2.core.comment.service;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;

import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.constants.CommentServiceConstants;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * The Comment Service allows for the creation and management of user
 * comments associated with other objects across the system.
 *
 * @version 2.0
 * @Author Sri komandur@uw.edu
 * @Author Mezba (Reviewed for CM2.0 on 20/11/2012)
 */

@WebService(name = "CommentService", targetNamespace = CommentServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface CommentService {

    /**
     * Retrieves information about a comment.
     *
     * @param commentId   comment identifier
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return information about a comment
     * @throws DoesNotExistException     specified commentId not found
     * @throws InvalidParameterException invalid commentId
     * @throws MissingParameterException commentId, contextInfo not specified
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public CommentInfo getComment(@WebParam(name = "commentId") String commentId,
                                  @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Comments corresponding to the
     * given list of Comment Ids
     *
     * @param commentIds  list of Comments to be retrieved
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return list of Comment information
     * @throws DoesNotExistException     an commentKey in list not found
     * @throws InvalidParameterException invalid commentKey
     * @throws MissingParameterException commentIds, contextInfo not specified
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CommentInfo> getCommentsByIds(@WebParam(name = "commentIds") List<String> commentIds,
                                              @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Comments of the specified type.
     *
     * @param commentTypeKey type to be retrieved
     * @param contextInfo    Context information containing the principalId and locale
     *                       information about the caller of service operation
     * @return a list of Comment keys
     * @throws InvalidParameterException invalid commentTypeKey
     * @throws MissingParameterException commentTypeKey, contextInfo not specified
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getCommentIdsByType(@WebParam(name = "commentTypeKey") String commentTypeKey,
                                            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves comment information for a reference. The expected behavior is that if the caller is not authorized
     * to invoke the getCommentsByRefObject operation, a PERMISSION_DENIED error is returned.
     * Assuming that the caller is authorized to invoke getCommentsByRefObject, only comments that the caller
     * is authorized to view are included in the returned commentInfoList; comments that the caller is unauthorized
     * to view are filtered out of the return parameter.
     *
     * @param refObjectId      reference object identifier
     * @param refObjectUri     reference object Uri
     * @param contextInfo      Context information containing the principalId and locale
     *                         information about the caller of service operation
     * @return Comment information
     * @throws DoesNotExistException     specified refObjectId, refObjectUri not found
     * @throws InvalidParameterException invalid refObjectId, refObjectUri
     * @throws MissingParameterException refObjectId, refObjectUri, contextInfo not specified
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CommentInfo> getCommentsByRefObject(@WebParam(name = "refObjectId") String refObjectId,
                                                           @WebParam(name = "refObjectUri") String refObjectUri,
                                                           @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for Comments based on the criteria and returns a list
     * of Comment identifiers which match the search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return list of Comment Ids
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException criteria, contextInfo not specified
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> searchForCommentIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for Comments based on the criteria and returns a list of
     * Comments which match the search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return list of Comment information
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException criteria, contextInfo not specified
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CommentInfo> searchForComments(@WebParam(name = "criteria") QueryByCriteria criteria,
                                               @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Adds a comment to a reference.
     *
     * @param refObjectId      reference object id
     * @param refObjectUri     reference object Uri
     * @param commentTypeKey   comment type
     * @param commentInfo      detailed information about the comment
     * @param contextInfo      Context information containing the principalId and locale
     *                         information about the caller of service operation
     * @return detailed information about the comment
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException        Id or Key does not exist
     * @throws InvalidParameterException    One or more parameters invalid
     * @throws MissingParameterException    One or more parameters missing
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            attempted update of readonly data
     */
    public CommentInfo createComment(@WebParam(name = "refObjectId") String refObjectId,
                                     @WebParam(name = "refObjectUri") String refObjectUri,
                                     @WebParam(name = "commentTypeKey") String commentTypeKey,
                                     @WebParam(name = "commentInfo") CommentInfo commentInfo,
                                     @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates a comment for a reference.
     *
     * @param commentId   comment identifier
     * @param commentInfo detailed information about the comment
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return detailed information about the comment
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws DoesNotExistException        comment does not exist
     * @throws InvalidParameterException    One or more parameters invalid
     * @throws MissingParameterException    commentId, commentInfo not specified
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    authorization failure
     * @throws ReadOnlyException            attempted update of readonly data
     * @throws VersionMismatchException     The action was attempted on an out of date version.
     */
    public CommentInfo updateComment(@WebParam(name = "commentId") String commentId,
                                     @WebParam(name = "commentInfo") CommentInfo commentInfo,
                                     @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException;

    /**
     * Removes a comment from a reference.
     *
     * @param commentId   identifier of the comment
     * @param contextInfo Context information containing the principalId and locale
     *                    information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     commentId, refObjectId does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException commentId, contextInfo not specified
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteComment(@WebParam(name = "commentId") String commentId,
                                    @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Removes all comments associated with a single reference
     *
     * @param refObjectId      reference object id
     * @param refObjectUri     reference object Uri
     * @param contextInfo      Context information containing the principalId and locale
     *                         information about the caller of service operation
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException     refObjectId does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException refObjectId, refObjectUri not specified
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public StatusInfo deleteCommentsByReference(@WebParam(name = "refObjectId") String refObjectId,
                                                @WebParam(name = "refObjectUri") String refObjectUri,
                                                @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Validates a comment. Depending on the value of validationType, this validation could be limited to tests on
     * just the current object and its directly contained sub-objects or expanded to perform all tests related
     * to this object. If an identifier is present for the comment (and/or one of its contained sub-objects)
     * and a record is found for that identifier, the validation checks if the comment can be shifted to the new
     * values. If an identifier is not present or a record cannot be found for the identifier, it is assumed that the
     * record does not exist and as such, the checks performed will be much shallower, typically mimicking those
     * performed by setting the validationType to the current object.
     *
     * @param validationTypeKey identifier of the extent of validation
     * @param refObjectId       reference object id
     * @param refObjectUri      reference object Uri
     * @param commentTypeKey    the identifier for the Comment
     *                          Type to be validated
     * @param commentInfo       comment information to be tested
     * @param contextInfo       Context information containing the principalId and locale
     *                          information about the caller of service operation
     * @return results from performing the validation
     * @throws DoesNotExistException     validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, commentInfo
     * @throws MissingParameterException validationTypeKey, commentInfo, contextInfo not specified
     * @throws OperationFailedException  unable to complete request
     */
    public List<ValidationResultInfo> validateComment(@WebParam(name = "validationTypeKey") String validationTypeKey,
                                                      @WebParam(name = "refObjectId") String refObjectId,
                                                      @WebParam(name = "refObjectUri") String refObjectUri,
                                                      @WebParam(name = "commentTypeKey") String commentTypeKey,
                                                      @WebParam(name = "commentInfo") CommentInfo commentInfo,
                                                      @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException;
    

}
