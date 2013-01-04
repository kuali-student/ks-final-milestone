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
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.comment.dto.TagInfo;

import java.util.List;

/**
 * Refer to service contract javadoc
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */

public class CommentServiceDecorator implements CommentService {

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

    @Override
    public CommentInfo getComment(String commentId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getComment(commentId, contextInfo);
    }

    @Override
    public List<CommentInfo> getCommentsByReferenceAndType(String referenceId, String referenceTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCommentsByReferenceAndType(referenceId, referenceTypeKey, contextInfo);
    }

    @Override
    public List<CommentInfo> getCommentsByIds(List<String> commentIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCommentsByIds(commentIds, contextInfo);
    }

    @Override
    public List<String> getCommentIdsByType(String commentTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCommentIdsByType(commentTypeKey, contextInfo);
    }

    @Override
    public List<String> searchForCommentIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForCommentIds(criteria, contextInfo);
    }

    @Override
    public List<CommentInfo> searchForComments(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForComments(criteria, contextInfo);
    }

    @Override
    public CommentInfo createComment(String referenceId, String referenceTypeKey, String commentTypeKey, CommentInfo commentInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createComment(referenceId, referenceTypeKey, commentTypeKey, commentInfo, contextInfo);
    }

    @Override
    public CommentInfo updateComment(String commentId, CommentInfo commentInfo, ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, VersionMismatchException, ReadOnlyException {
        return getNextDecorator().updateComment(commentId, commentInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteComment(String commentId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteComment(commentId, contextInfo);
    }

    @Override
    public StatusInfo deleteCommentsByReference(String referenceId, String referenceTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteCommentsByReference(referenceId, referenceTypeKey, contextInfo);
    }

    @Override
    public List<ValidationResultInfo> validateComment(String validationTypeKey, CommentInfo commentInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return getNextDecorator().validateComment(validationTypeKey, commentInfo, contextInfo);
    }

    @Override
    public List<TagInfo> getTagsByReferenceAndType(String referenceId, String referenceTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getTagsByReferenceAndType(referenceId, referenceTypeKey, contextInfo);
    }

    @Override
    public TagInfo getTag(String tagId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getTag(tagId, contextInfo);
    }

    @Override
    public List<TagInfo> getTagsByIds(List<String> tagIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getTagsByIds(tagIds, contextInfo);
    }

    @Override
    public List<String> getTagIdsByType(String tagTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getTagIdsByType(tagTypeKey, contextInfo);
    }

    @Override
    public List<String> searchForTagIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForTagIds(criteria, contextInfo);
    }

    @Override
    public List<TagInfo> searchForTags(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().searchForTags(criteria, contextInfo);
    }

    @Override
    public TagInfo createTag(String referenceId, String referenceTypeKey, TagInfo tagInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createTag(referenceId, referenceTypeKey, tagInfo, contextInfo);
    }

    @Override
    public StatusInfo deleteTagsByReference(String referenceId, String referenceTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteTagsByReference(referenceId, referenceTypeKey, contextInfo);
    }

    @Override
    public StatusInfo deleteTag(String tagId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteTag(tagId, contextInfo);
    }

    @Override
    public ObjectStructureDefinition getObjectStructure(String objectTypeKey) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getObjectTypes() {
        // TODO Auto-generated method stub
        return null;
    }

}
