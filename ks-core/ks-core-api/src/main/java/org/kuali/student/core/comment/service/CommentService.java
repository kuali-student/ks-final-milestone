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

package org.kuali.student.core.comment.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.core.comment.dto.CommentInfo;
import org.kuali.student.core.comment.dto.CommentTypeInfo;
import org.kuali.student.core.comment.dto.TagInfo;
import org.kuali.student.core.comment.dto.TagTypeInfo;
import org.kuali.student.core.dictionary.service.DictionaryService;
import org.kuali.student.core.dto.ReferenceTypeInfo;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

/**
 *
 * @Author KSContractMojo
 * @Author Neerav Agrawal
 * @Since Fri Jun 05 14:27:10 EDT 2009
 * @See <a href="https://test.kuali.org/confluence/display/KULSTR/Comment+Service+v1.0-rc1">CommentService</>
 *
 */
@WebService(name = "CommentService", targetNamespace = "http://student.kuali.org/wsdl/comment")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
//@XmlSeeAlso({org.kuali.student.core.dto.ReferenceTypeInfo.class})
public interface CommentService extends DictionaryService {
    /**
     * Retrieves the list of types which can be tagged or commented.
     * @return the list of types which can be tagged or commented
     * @throws OperationFailedException unable to complete request
	 */
    public List<ReferenceTypeInfo> getReferenceTypes() throws OperationFailedException;

    /**
     * Retrieves the list of comment types which can be linked to a referenced object.
     * @return the list of comment types which can be linked to a referenced object
     * @throws OperationFailedException unable to complete request
	 */
    public List<CommentTypeInfo> getCommentTypes() throws OperationFailedException;

    /**
     * Retrieves the list of tag types which can be linked to a referenced object.
     * @return the list of tag types which can be linked to a referenced object
     * @throws OperationFailedException unable to complete request
	 */
    public List<TagTypeInfo> getTagTypes() throws OperationFailedException;

    /**
     * Retrieves the list of comment types which can be linked to a particular type of referenced object.
     * @param referenceTypeKey reference type
     * @return the list of comment types which can be linked to a referenced object type
     * @throws DoesNotExistException specified referenceTypeKey not found
     * @throws InvalidParameterException invalid referenceTypeKey
     * @throws MissingParameterException referenceTypeKey not specified
     * @throws OperationFailedException unable to complete request
	 */
    public List<CommentTypeInfo> getCommentTypesForReferenceType(@WebParam(name="referenceTypeKey")String referenceTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Validates a comment. Depending on the value of validationType, this validation could be limited to tests on just the current object and its directly contained sub-objects or expanded to perform all tests related to this object. If an identifier is present for the comment (and/or one of its contained sub-objects) and a record is found for that identifier, the validation checks if the comment can be shifted to the new values. If an identifier is not present or a record cannot be found for the identifier, it is assumed that the record does not exist and as such, the checks performed will be much shallower, typically mimicking those performed by setting the validationType to the current object.
     * @param validationType identifier of the extent of validation
     * @param commentInfo comment information to be tested.
     * @return results from performing the validation
     * @throws DoesNotExistException validationTypeKey not found
     * @throws InvalidParameterException invalid validationTypeKey, commentInfo
     * @throws MissingParameterException missing validationTypeKey, commentInfo
     * @throws OperationFailedException unable to complete request
	 */
    public List<ValidationResultInfo> validateComment(@WebParam(name="validationType")String validationType, @WebParam(name="commentInfo")CommentInfo commentInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    /**
     * Retrieves comment information for a reference. The expected behavior is that if the caller is not authorized to invoke the getComments operation, a PERMISSION_DENIED error is returned. Assuming that the caller is authorized to invoke getComments, only comments that the caller is authorized to view are included in the returned commentInfoList; comments that the caller is unauthorized to view are filtered out of the return parameter.
     * @param referenceId reference identifier
     * @param referenceTypeKey reference type
     * @return list of comment information
     * @throws DoesNotExistException specified referenceId, referenceTypeKey not found
     * @throws InvalidParameterException invalid referenceId, referenceTypeKey
     * @throws MissingParameterException referenceId, referenceTypeKey not specified
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public List<CommentInfo> getComments(@WebParam(name="referenceId")String referenceId, @WebParam(name="referenceTypeKey")String referenceTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves tag information for a reference. The expected behavior is that if the caller is not authorized to invoke the getTags operation, a PERMISSION_DENIED error is returned. Assuming that the caller is authorized to invoke getTags, only tags that the caller is authorized to view are included in the returned tagInfoList; tags that the caller is unauthorized to view are filtered out of the return parameter.
     * @param referenceId reference identifier
     * @param referenceTypeKey reference type
     * @return list of tag information
     * @throws DoesNotExistException specified referenceId, referenceTypeKey not found
     * @throws InvalidParameterException invalid referenceId, referenceTypeKey
     * @throws MissingParameterException referenceId, referenceTypeKey not specified
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public List<TagInfo> getTags(@WebParam(name="referenceId")String referenceId, @WebParam(name="referenceTypeKey")String referenceTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves comment information for a reference of a particular type. The expected behavior is that if the caller is not authorized to invoke the getCommentsByType operation, a PERMISSION_DENIED error is returned. Assuming that the caller is authorized to invoke getCommentsByType, only comments that the caller is authorized to view are included in the returned commentInfoList; comments that the caller is unauthorized to view are filtered out of the return parameter.
     * @param referenceId reference identifier
     * @param referenceTypeKey reference type
     * @param commentTypeKey comment type
     * @return list of comment information
     * @throws DoesNotExistException specified referenceId, referenceTypeKey, commentTypeKey not found
     * @throws InvalidParameterException invalid referenceId, referenceTypeKey,commentTypeKey
     * @throws MissingParameterException referenceId, referenceTypeKey, commentTypeKey not specified
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public List<CommentInfo> getCommentsByType(@WebParam(name="referenceId")String referenceId, @WebParam(name="referenceTypeKey")String referenceTypeKey, @WebParam(name="commentTypeKey")String commentTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves tag information for a reference of a particular type. The expected behavior is that if the caller is not authorized to invoke the getTagsByType operation, a PERMISSION_DENIED error is returned. Assuming that the caller is authorized to invoke getTagsByType, only tags that the caller is authorized to view are included in the returned tagInfoList; tags that the caller is unauthorized to view are filtered out of the return parameter.
     * @param referenceId reference identifier
     * @param referenceTypeKey reference type
     * @param tagTypeKey tag type
     * @return list of tag information
     * @throws DoesNotExistException specified referenceId, referenceTypeKey, tagTypeKey not found
     * @throws InvalidParameterException invalid referenceId, referenceTypeKey,tagTypeKey
     * @throws MissingParameterException referenceId, referenceTypeKey, tagTypeKey not specified
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public List<TagInfo> getTagsByType(@WebParam(name="referenceId")String referenceId, @WebParam(name="referenceTypeKey")String referenceTypeKey, @WebParam(name="tagTypeKey")String tagTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves information about a comment.
     * @param commentId comment identifier
     * @return information about a comment
     * @throws DoesNotExistException specified commentId not found
     * @throws InvalidParameterException invalid commentId
     * @throws MissingParameterException commentId not specified
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public CommentInfo getComment(@WebParam(name="commentId")String commentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Retrieves information about a tag.
     * @param tagId tag identifier
     * @return information about a tag
     * @throws DoesNotExistException specified tagId not found
     * @throws InvalidParameterException invalid tagId
     * @throws MissingParameterException tagId not specified
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public TagInfo getTag(@WebParam(name="tagId")String tagId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Adds a comment to a reference.
     * @param referenceId identifier of reference
     * @param referenceTypeKey reference type
     * @param commentInfo detailed information about the comment
     * @return detailed information about the comment
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public CommentInfo addComment(@WebParam(name="referenceId")String referenceId, @WebParam(name="referenceTypeKey")String referenceTypeKey, @WebParam(name="commentInfo")CommentInfo commentInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Adds a tag to a reference.
     * @param referenceId identifier of reference
     * @param referenceTypeKey reference type
     * @param tagInfo detailed information about the tag
     * @return detailed information about the tag
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws AlreadyExistsException tag of that namespace, predicate and value already exists for that reference id
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public TagInfo addTag(@WebParam(name="referenceId")String referenceId, @WebParam(name="referenceTypeKey")String referenceTypeKey, @WebParam(name="tagInfo")TagInfo tagInfo) throws DataValidationErrorException, AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Updates a comment for a reference.
     * @param referenceId identifier of reference
     * @param referenceTypeKey reference type
     * @param commentInfo detailed information about the comment
     * @return detailed information about the comment
     * @throws DataValidationErrorException One or more values invalid for this operation
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
     * @throws DoesNotExistException comment does not exist
     * @throws VersionMismatchException The action was attempted on an out of date version.     * 
	 */
    public CommentInfo updateComment(@WebParam(name="referenceId")String referenceId, @WebParam(name="referenceTypeKey")String referenceTypeKey, @WebParam(name="commentInfo")CommentInfo commentInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, VersionMismatchException;

    /**
     * Removes a comment from a reference.
     * @param commentId identifier of the comment
     * @param referenceId identifier of the reference
     * @param referenceTypeKey reference type
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException commentId, referenceId does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo removeComment(@WebParam(name="commentId")String commentId, @WebParam(name="referenceId")String referenceId, @WebParam(name="referenceTypeKey")String referenceTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Removes a tag from a reference.
     * @param tagId identifier of the tag
     * @param referenceId identifier of the reference
     * @param referenceTypeKey reference type
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException tagId, referenceId does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo removeTag(@WebParam(name="tagId")String tagId, @WebParam(name="referenceId")String referenceId, @WebParam(name="referenceTypeKey")String referenceTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Removes all comments associated with a single reference
     * @param referenceId identifier of the reference
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException referenceId does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo removeComments(@WebParam(name="referenceId")String referenceId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    /**
     * Removes a tag from all references to which it is linked.
     * @param tagId identifier of the tag
     * @return status of the operation (success, failed)
     * @throws DoesNotExistException tagId does not exist
     * @throws InvalidParameterException One or more parameters invalid
     * @throws MissingParameterException One or more parameters missing
     * @throws OperationFailedException unable to complete request
     * @throws PermissionDeniedException authorization failure
	 */
    public StatusInfo removeTags(@WebParam(name="tagId")String tagId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

}
