/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.core.comment.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.kuali.student.core.comment.dao.CommentDao;
import org.kuali.student.core.comment.dto.CommentCriteriaInfo;
import org.kuali.student.core.comment.dto.CommentInfo;
import org.kuali.student.core.comment.dto.CommentTypeInfo;
import org.kuali.student.core.comment.dto.ReferenceTypeInfo;
import org.kuali.student.core.comment.dto.TagCriteriaInfo;
import org.kuali.student.core.comment.dto.TagInfo;
import org.kuali.student.core.comment.dto.TagTypeInfo;
import org.kuali.student.core.comment.dto.ValidationResultInfo;
import org.kuali.student.core.comment.entity.Comment;
import org.kuali.student.core.comment.entity.Tag;
import org.kuali.student.core.comment.service.CommentService;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.springframework.transaction.annotation.Transactional;

/**
 * This is a description of what this class does - lindholm don't forget to fill this in.
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
@WebService(endpointInterface = "org.kuali.student.core.comment.service.CommentService", serviceName = "CommentService", portName = "CommentService", targetNamespace = "http://student.kuali.org/commentService")
@Transactional(rollbackFor={Throwable.class})
public class CommentServiceImpl implements CommentService {
    private CommentDao commentDao;

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.comment.service.CommentService#addComment(java.lang.String, java.lang.String, org.kuali.student.core.comment.dto.CommentInfo)
     */
    @Override
    public CommentInfo addComment(String referenceId, String referenceTypeKey, CommentInfo commentInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO lindholm - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.comment.service.CommentService#addTag(java.lang.String, java.lang.String, org.kuali.student.core.comment.dto.TagInfo)
     */
    @Override
    public TagInfo addTag(String referenceId, String referenceTypeKey, TagInfo tagInfo) throws DataValidationErrorException, AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO lindholm - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.comment.service.CommentService#getComment(java.lang.String)
     */
    @Override
    public CommentInfo getComment(String commentId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(commentId, "commentId");
        Comment comment = commentDao.fetch(Comment.class, commentId);
        return CommentServiceAssembler.toCommentInfo(comment);
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.comment.service.CommentService#getCommentTypes()
     */
    @Override
    public List<CommentTypeInfo> getCommentTypes() throws OperationFailedException {
        // TODO lindholm - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.comment.service.CommentService#getCommentTypesForReferenceType(java.lang.String)
     */
    @Override
    public List<CommentTypeInfo> getCommentTypesForReferenceType(String referenceTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO lindholm - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.comment.service.CommentService#getComments(java.lang.String, java.lang.String)
     */
    @Override
    public List<CommentInfo> getComments(String referenceId, String referenceTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO lindholm - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.comment.service.CommentService#getCommentsByType(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public List<CommentInfo> getCommentsByType(String referenceId, String referenceTypeKey, String commentTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO lindholm - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.comment.service.CommentService#getReferenceTypes()
     */
    @Override
    public List<ReferenceTypeInfo> getReferenceTypes() throws OperationFailedException {
        // TODO lindholm - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.comment.service.CommentService#getTag(java.lang.String)
     */
    @Override
    public TagInfo getTag(String tagId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        checkForMissingParameter(tagId, "tagId");
        Tag tag = commentDao.fetch(Tag.class, tagId);
        return CommentServiceAssembler.toTagInfo(tag);
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.comment.service.CommentService#getTagTypes()
     */
    @Override
    public List<TagTypeInfo> getTagTypes() throws OperationFailedException {
        // TODO lindholm - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.comment.service.CommentService#getTags(java.lang.String, java.lang.String)
     */
    @Override
    public List<TagInfo> getTags(String referenceId, String referenceTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO lindholm - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.comment.service.CommentService#getTagsByType(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public List<TagInfo> getTagsByType(String referenceId, String referenceTypeKey, String tagTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO lindholm - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.comment.service.CommentService#removeComment(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public StatusInfo removeComment(String commentId, String referenceId, String referenceTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO lindholm - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.comment.service.CommentService#removeComments(java.lang.String)
     */
    @Override
    public StatusInfo removeComments(String referenceId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO lindholm - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.comment.service.CommentService#removeTag(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public StatusInfo removeTag(String tagId, String referenceId, String referenceTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO lindholm - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.comment.service.CommentService#removeTags(java.lang.String)
     */
    @Override
    public StatusInfo removeTags(String tagId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO lindholm - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.comment.service.CommentService#searchForComments(org.kuali.student.core.comment.dto.CommentCriteriaInfo)
     */
    @Override
    public List<String> searchForComments(CommentCriteriaInfo commentCriteriaInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO lindholm - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.comment.service.CommentService#searchForTags(org.kuali.student.core.comment.dto.TagCriteriaInfo)
     */
    @Override
    public List<String> searchForTags(TagCriteriaInfo tagCriteriaInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO lindholm - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.comment.service.CommentService#updateComment(java.lang.String, java.lang.String, org.kuali.student.core.comment.dto.CommentInfo)
     */
    @Override
    public CommentInfo updateComment(String referenceId, String referenceTypeKey, CommentInfo commentInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO lindholm - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.comment.service.CommentService#validateComment(java.lang.String, org.kuali.student.core.comment.dto.CommentInfo)
     */
    @Override
    public List<ValidationResultInfo> validateComment(String validationType, CommentInfo commentInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO lindholm - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * @return the commentDao
     */
    public CommentDao getCommentDao() {
        return commentDao;
    }

    /**
     * @param commentDao the commentDao to set
     */
    public void setCommentDao(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    /**
     * Check for missing parameter and throw localized exception if missing
     *
     * @param param
     * @param parameter name
     * @throws MissingParameterException
     */
    private void checkForMissingParameter(Object param, String paramName)
            throws MissingParameterException {
        if (param == null) {
            throw new MissingParameterException(paramName + " can not be null");
        }
    }

    /**
     * @param param
     * @param paramName
     * @throws MissingParameterException
     */
    private void checkForEmptyList(Object param, String paramName)
            throws MissingParameterException {
        if (param != null && param instanceof List && ((List<?>)param).size() == 0) {
            throw new MissingParameterException(paramName + " can not be an empty list");
        }
    }

}
