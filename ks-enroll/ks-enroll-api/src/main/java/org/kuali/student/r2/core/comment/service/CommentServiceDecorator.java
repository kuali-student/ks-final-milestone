/**
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
import org.kuali.student.common.dto.ReferenceTypeInfo;
import org.kuali.student.common.exceptions.*;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.comment.dto.CommentTypeInfo;
import org.kuali.student.r2.core.comment.dto.TagInfo;
import org.kuali.student.r2.core.comment.dto.TagTypeInfo;

import java.util.List;

/**
 *  Refer to contract javadoc
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */

public abstract class CommentServiceDecorator implements CommentService {

    private CommentService nextDecorator;

    public CommentService getNextDecorator() throws OperationFailedException {
        if (null == nextDecorator) {
            throw new OperationFailedException("Misconfigured application: nextDecorator is null");
        }
        return nextDecorator;
    }

    public void setNextDecorator(CommentService nextDecorator) {
        this.nextDecorator = nextDecorator;
    }

    /**
     * Retrieves the list of types which can be tagged or commented.
     *
     * @return the list of types which can be tagged or commented
     * @throws org.kuali.student.common.exceptions.OperationFailedException
     *          unable to complete request
     */
    @Override
    public List<ReferenceTypeInfo> getReferenceTypes(ContextInfo context) throws OperationFailedException {
        return this.getNextDecorator().getReferenceTypes(context);
    }

    /**
     * Retrieves comment information for a reference. The expected behavior is that if the caller is not authorized to invoke the getCommentByReference operation, a PERMISSION_DENIED error is returned. Assuming that the caller is authorized to invoke getCommentByReference, only comments that the caller is authorized to view are included in the returned commentInfoList; comments that the caller is unauthorized to view are filtered out of the return parameter.
     *
     * @param referenceId      reference identifier
     * @param referenceTypeKey reference type
     * @return list of comment information
     * @throws org.kuali.student.common.exceptions.DoesNotExistException
     *          specified referenceId, referenceTypeKey not found
     * @throws org.kuali.student.common.exceptions.InvalidParameterException
     *          invalid referenceId, referenceTypeKey
     * @throws org.kuali.student.common.exceptions.MissingParameterException
     *          referenceId, referenceTypeKey not specified
     * @throws org.kuali.student.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<CommentInfo> getCommentByReference(String referenceId, String referenceTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().getCommentByReference(referenceId, referenceTypeKey, context);
    }


    /**
     * Retrieves comment information for a reference of a particular type. The expected behavior is that if the caller is not authorized to invoke the getCommentsByType operation, a PERMISSION_DENIED error is returned. Assuming that the caller is authorized to invoke getCommentsByType, only comments that the caller is authorized to view are included in the returned commentInfoList; comments that the caller is unauthorized to view are filtered out of the return parameter.
     *
     * @param referenceId      reference identifier
     * @param referenceTypeKey reference type
     * @param commentTypeKey   comment type
     * @return list of comment information
     * @throws org.kuali.student.common.exceptions.DoesNotExistException
     *          specified referenceId, referenceTypeKey, commentTypeKey not found
     * @throws org.kuali.student.common.exceptions.InvalidParameterException
     *          invalid referenceId, referenceTypeKey,commentTypeKey
     * @throws org.kuali.student.common.exceptions.MissingParameterException
     *          referenceId, referenceTypeKey, commentTypeKey not specified
     * @throws org.kuali.student.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<CommentInfo> getCommentsByType(String referenceId, String referenceTypeKey, String commentTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().getCommentsByType(referenceId, referenceTypeKey, commentTypeKey, context);
    }

    /**
     * Retrieves information about a comment.
     *
     * @param commentId comment identifier
     * @return information about a comment
     * @throws org.kuali.student.common.exceptions.DoesNotExistException
     *          specified commentId not found
     * @throws org.kuali.student.common.exceptions.InvalidParameterException
     *          invalid commentId
     * @throws org.kuali.student.common.exceptions.MissingParameterException
     *          commentId not specified
     * @throws org.kuali.student.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public CommentInfo getComment(String commentId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().getComment(commentId, context);
    }


    /**
     * Retrieves a list of Comments corresponding to the
     * given list of Comment keys.
     *
     * @param commentKeyList list of Comments to be retrieved
     * @param context        Context information containing the principalId
     *                       and locale information about the caller of service
     *                       operation
     * @return List of Comment keys of the given type
     * @throws DoesNotExistException     an commentKey in list not found
     * @throws InvalidParameterException invalid commentKey
     * @throws MissingParameterException missing commentKey
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CommentInfo> getCommentsByKeyList(List<String> commentKeyList, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().getCommentsByKeyList(commentKeyList, context);
    }


    /**
     * Retrieves a list of Comments of the specified type.
     *
     * @param commentTypeKey type to be retrieved
     * @param context        Context information containing the principalId
     *                       and locale information about the caller of service
     *                       operation
     * @return a list of Comment keys
     * @throws InvalidParameterException invalid commentTypeKey
     * @throws MissingParameterException missing commentTypeKey
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getCommentKeysByType(String commentTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().getCommentKeysByType(commentTypeKey, context);
    }


    /**
     * Searches for Comments based on the criteria and returns a list
     * of Comment identifiers which match the search criteria.
     *
     * @param criteria the search criteria
     * @param context  Context information containing the principalId
     *                 and locale information about the caller of service
     *                 operation
     * @return list of Comment Ids
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException parameter is missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> searchForCommentKeys(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().searchForCommentKeys(criteria, context);
    }


    /**
     * Searches for Comments based on the criteria and returns a list of
     * Comments which match the search criteria.
     *
     * @param criteria the search criteria
     * @param context  Context information containing the principalId
     *                 and locale information about the caller of service
     *                 operation
     * @return list of Comments
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException parameter is missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<CommentInfo> searchForComments(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().searchForComments(criteria, context);
    }


    /**
     * Adds a comment to a reference.
     *
     * @param referenceId      identifier of reference
     * @param referenceTypeKey reference type
     * @param commentInfo      detailed information about the comment
     * @return detailed information about the comment
     * @throws org.kuali.student.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this operation
     * @throws org.kuali.student.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public CommentInfo createComment(String referenceId, String referenceTypeKey, CommentInfo commentInfo, ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().createComment(referenceId, referenceTypeKey, commentInfo, context);
    }

    /**
     * Retrieves the list of comment types which can be linked to a referenced object.
     *
     * @return the list of comment types which can be linked to a referenced object
     * @throws org.kuali.student.common.exceptions.OperationFailedException
     *          unable to complete request
     */
    @Override
    public List<CommentTypeInfo> getCommentTypes(ContextInfo context) throws OperationFailedException {
        return this.getNextDecorator().getCommentTypes(context);
    }


    /**
     * Updates a comment for a reference.
     *
     * @param referenceId      identifier of reference
     * @param referenceTypeKey reference type
     * @param commentInfo      detailed information about the comment
     * @return detailed information about the comment
     * @throws org.kuali.student.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this operation
     * @throws org.kuali.student.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.common.exceptions.PermissionDeniedException
     *          authorization failure
     * @throws org.kuali.student.common.exceptions.DoesNotExistException
     *          comment does not exist
     * @throws org.kuali.student.common.exceptions.VersionMismatchException
     *          The action was attempted on an out of date version.     *
     */
    @Override
    public CommentInfo updateComment(String referenceId, String referenceTypeKey, CommentInfo commentInfo, ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, VersionMismatchException {
        return this.getNextDecorator().updateComment(referenceId, referenceTypeKey, commentInfo, context);
    }


    /**
     * Removes a comment from a reference.
     *
     * @param commentId        identifier of the comment
     * @param referenceId      identifier of the reference
     * @param referenceTypeKey reference type
     * @return status of the operation (success, failed)
     * @throws org.kuali.student.common.exceptions.DoesNotExistException
     *          commentId, referenceId does not exist
     * @throws org.kuali.student.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo deleteComment(String commentId, String referenceId, String referenceTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().deleteComment(commentId, referenceId, referenceTypeKey, context);
    }


    /**
     * Removes all comments associated with a single reference
     *
     * @param referenceId identifier of the reference
     * @return status of the operation (success, failed)
     * @throws org.kuali.student.common.exceptions.DoesNotExistException
     *          referenceId does not exist
     * @throws org.kuali.student.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo deleteComments(String referenceId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().deleteComments(referenceId, context);
    }


    /**
     * Validates a comment. Depending on the value of validationType, this validation could be limited to tests on just the current object and its directly contained sub-objects or expanded to perform all tests related to this object. If an identifier is present for the comment (and/or one of its contained sub-objects) and a record is found for that identifier, the validation checks if the comment can be shifted to the new values. If an identifier is not present or a record cannot be found for the identifier, it is assumed that the record does not exist and as such, the checks performed will be much shallower, typically mimicking those performed by setting the validationType to the current object.
     *
     * @param validationType identifier of the extent of validation
     * @param commentInfo    comment information to be tested.
     * @return results from performing the validation
     * @throws org.kuali.student.common.exceptions.DoesNotExistException
     *          validationTypeKey not found
     * @throws org.kuali.student.common.exceptions.InvalidParameterException
     *          invalid validationTypeKey, commentInfo
     * @throws org.kuali.student.common.exceptions.MissingParameterException
     *          missing validationTypeKey, commentInfo
     * @throws org.kuali.student.common.exceptions.OperationFailedException
     *          unable to complete request
     */
    @Override
    public List<ValidationResultInfo> validateComment(String validationType, CommentInfo commentInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return this.getNextDecorator().validateComment(validationType, commentInfo, context);
    }

    /**
     * Retrieves the list of tag types which can be linked to a referenced object.
     *
     * @return the list of tag types which can be linked to a referenced object
     * @throws org.kuali.student.common.exceptions.OperationFailedException
     *          unable to complete request
     */
    @Override
    public List<TagTypeInfo> getTagTypes(ContextInfo context) throws OperationFailedException {
        return this.getNextDecorator().getTagTypes(context);
    }

    /**
     * Retrieves the list of comment types which can be linked to a particular type of referenced object.
     *
     * @param referenceTypeKey reference type
     * @return the list of comment types which can be linked to a referenced object type
     * @throws org.kuali.student.common.exceptions.DoesNotExistException
     *          specified referenceTypeKey not found
     * @throws org.kuali.student.common.exceptions.InvalidParameterException
     *          invalid referenceTypeKey
     * @throws org.kuali.student.common.exceptions.MissingParameterException
     *          referenceTypeKey not specified
     * @throws org.kuali.student.common.exceptions.OperationFailedException
     *          unable to complete request
     */
    @Override
    public List<CommentTypeInfo> getCommentTypesForReferenceType(String referenceTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return this.getNextDecorator().getCommentTypesForReferenceType(referenceTypeKey, context);
    }

    /**
     * Retrieves information about a tag.
     *
     * @param tagId tag identifier
     * @return information about a tag
     * @throws org.kuali.student.common.exceptions.DoesNotExistException
     *          specified tagId not found
     * @throws org.kuali.student.common.exceptions.InvalidParameterException
     *          invalid tagId
     * @throws org.kuali.student.common.exceptions.MissingParameterException
     *          tagId not specified
     * @throws org.kuali.student.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public TagInfo getTag(String tagId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().getTag(tagId, context);
    }

    /**
     * Retrieves tag information for a reference. The expected behavior is that if the caller is not authorized to invoke the getTags operation, a PERMISSION_DENIED error is returned. Assuming that the caller is authorized to invoke getTags, only tags that the caller is authorized to view are included in the returned tagInfoList; tags that the caller is unauthorized to view are filtered out of the return parameter.
     *
     * @param referenceId      reference identifier
     * @param referenceTypeKey reference type
     * @return list of tag information
     * @throws org.kuali.student.common.exceptions.DoesNotExistException
     *          specified referenceId, referenceTypeKey not found
     * @throws org.kuali.student.common.exceptions.InvalidParameterException
     *          invalid referenceId, referenceTypeKey
     * @throws org.kuali.student.common.exceptions.MissingParameterException
     *          referenceId, referenceTypeKey not specified
     * @throws org.kuali.student.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<TagInfo> getTags(String referenceId, String referenceTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().getTags(referenceId, referenceTypeKey, context);
    }

    /**
     * Retrieves a list of Tags of the specified type.
     *
     * @param tagTypeKey type to be retrieved
     * @param context    Context information containing the principalId
     *                   and locale information about the caller of service
     *                   operation
     * @return a list of Tag keys
     * @throws InvalidParameterException invalid tagTypeKey
     * @throws MissingParameterException missing tagTypeKey
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> getTagKeysByType(String tagTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().getTagKeysByType(tagTypeKey, context);
    }

    /**
     * Searches for Tags based on the criteria and returns a list
     * of Tag identifiers which match the search criteria.
     *
     * @param criteria the search criteria
     * @param context  Context information containing the principalId
     *                 and locale information about the caller of service
     *                 operation
     * @return list of Tag Ids
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException parameter is missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<String> searchForTagKeys(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().searchForTagKeys(criteria, context);
    }


    /**
     * Searches for Tags based on the criteria and returns a list of
     * Tags which match the search criteria.
     *
     * @param criteria the search criteria
     * @param context  Context information containing the principalId
     *                 and locale information about the caller of service
     *                 operation
     * @return list of Tags
     * @throws InvalidParameterException invalid parameter
     * @throws MissingParameterException parameter is missing
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TagInfo> searchForTags(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().searchForTags(criteria, context);
    }

    /**
     * Adds a tag to a reference.
     *
     * @param referenceId      identifier of reference
     * @param referenceTypeKey reference type
     * @param tagInfo          detailed information about the tag
     * @return detailed information about the tag
     * @throws org.kuali.student.common.exceptions.DataValidationErrorException
     *          One or more values invalid for this operation
     * @throws org.kuali.student.common.exceptions.AlreadyExistsException
     *          tag of that namespace, predicate and value already exists for that reference id
     * @throws org.kuali.student.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public TagInfo createTag(String referenceId, String referenceTypeKey, TagInfo tagInfo, ContextInfo context) throws DataValidationErrorException, AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().createTag(referenceId, referenceTypeKey, tagInfo, context);
    }


    /**
     * Retrieves a list of Tags corresponding to the
     * given list of Tag keys.
     *
     * @param tagKeyList list of Tags to be retrieved
     * @param context    Context information containing the principalId
     *                   and locale information about the caller of service
     *                   operation
     * @return List of Tag keys of the given type
     * @throws DoesNotExistException     an tagKey in list not found
     * @throws InvalidParameterException invalid tagKey
     * @throws MissingParameterException missing tagKey
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException authorization failure
     */
    public List<TagInfo> getTagsByKeyList(List<String> tagKeyList, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().getTagsByKeyList(tagKeyList, context);
    }

    /**
     * Retrieves tag information for a reference of a particular type. The expected behavior is that if the caller is not authorized to invoke the getTagsByType operation, a PERMISSION_DENIED error is returned. Assuming that the caller is authorized to invoke getTagsByType, only tags that the caller is authorized to view are included in the returned tagInfoList; tags that the caller is unauthorized to view are filtered out of the return parameter.
     *
     * @param referenceId      reference identifier
     * @param referenceTypeKey reference type
     * @param tagTypeKey       tag type
     * @return list of tag information
     * @throws org.kuali.student.common.exceptions.DoesNotExistException
     *          specified referenceId, referenceTypeKey, tagTypeKey not found
     * @throws org.kuali.student.common.exceptions.InvalidParameterException
     *          invalid referenceId, referenceTypeKey,tagTypeKey
     * @throws org.kuali.student.common.exceptions.MissingParameterException
     *          referenceId, referenceTypeKey, tagTypeKey not specified
     * @throws org.kuali.student.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public List<TagInfo> getTagsByType(String referenceId, String referenceTypeKey, String tagTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().getTagsByType(referenceId, referenceTypeKey, tagTypeKey, context);
    }

    /**
     * Removes a tag from a reference.
     *
     * @param tagId            identifier of the tag
     * @param referenceId      identifier of the reference
     * @param referenceTypeKey reference type
     * @return status of the operation (success, failed)
     * @throws org.kuali.student.common.exceptions.DoesNotExistException
     *          tagId, referenceId does not exist
     * @throws org.kuali.student.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo deleteTag(String tagId, String referenceId, String referenceTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().deleteTag(tagId, referenceId, referenceTypeKey, context);
    }


    /**
     * Removes a tag from all references to which it is linked.
     *
     * @param tagId identifier of the tag
     * @return status of the operation (success, failed)
     * @throws org.kuali.student.common.exceptions.DoesNotExistException
     *          tagId does not exist
     * @throws org.kuali.student.common.exceptions.InvalidParameterException
     *          One or more parameters invalid
     * @throws org.kuali.student.common.exceptions.MissingParameterException
     *          One or more parameters missing
     * @throws org.kuali.student.common.exceptions.OperationFailedException
     *          unable to complete request
     * @throws org.kuali.student.common.exceptions.PermissionDeniedException
     *          authorization failure
     */
    @Override
    public StatusInfo deleteTags(String tagId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return this.getNextDecorator().deleteTags(tagId, context);
    }


}
