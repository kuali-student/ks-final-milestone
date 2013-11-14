/*
 * Copyright 2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.r2.core.comment.service.impl;

import org.kuali.rice.core.api.criteria.GenericQueryResults;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.criteria.CriteriaLookupService;
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
import org.kuali.student.r2.core.comment.dao.CommentDao;
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.comment.model.CommentEntity;
import org.kuali.student.r2.core.comment.service.CommentService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
public class CommentServiceImpl implements CommentService {

    private CommentDao commentDao;
    private CriteriaLookupService commentCriteriaLookupService;

    public CommentDao getCommentDao() {
        return commentDao;
    }

    public void setCommentDao(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    public CriteriaLookupService getCommentCriteriaLookupService() {
        return commentCriteriaLookupService;
    }

    public void setCommentCriteriaLookupService(CriteriaLookupService commentCriteriaLookupService) {
        this.commentCriteriaLookupService = commentCriteriaLookupService;
    }

    @Override
    public CommentInfo getComment(String commentId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        CommentEntity entity = commentDao.find(commentId);
        if(entity == null) {
            throw new DoesNotExistException(commentId);
        }
        return entity.toDto();
    }

    @Override
    public List<CommentInfo> getCommentsByIds(List<String> commentIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<CommentEntity> entities = commentDao.findByIds(commentIds);
        List<CommentInfo> result = new ArrayList<CommentInfo>(entities.size());
        for (CommentEntity entity : entities) {
            result.add(entity.toDto());
        }
        return result;
    }

    @Override
    public List<String> getCommentIdsByType(String commentTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return commentDao.getIdsByType(commentTypeKey);
    }

    @Override
    public List<CommentInfo> getCommentsByReferenceAndType(String referenceId, String referenceTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<CommentEntity> entities = commentDao.getCommentsByRefObjectIdAndRefObjectType(referenceId, referenceTypeKey);
        List<CommentInfo> infoList = new ArrayList<CommentInfo>();
        for(CommentEntity entity : entities) {
            infoList.add(entity.toDto());
        }
        return infoList;
    }

    @Override
    public List<String> searchForCommentIds(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<String> results = new ArrayList<String>();
        GenericQueryResults<CommentEntity> comments = commentCriteriaLookupService.lookup(CommentEntity.class, criteria);
        if (null != comments && comments.getResults().size() > 0) {
            for (CommentEntity comment : comments.getResults()) {
                results.add(comment.getId());
            }
        }
        return results;
    }

    @Override
    public List<CommentInfo> searchForComments(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<CommentInfo> results = new ArrayList<CommentInfo>();
        GenericQueryResults<CommentEntity> comments = commentCriteriaLookupService.lookup(CommentEntity.class, criteria);
        if (null != comments && comments.getResults().size() > 0) {
            for (CommentEntity comment : comments.getResults()) {
                results.add(comment.toDto());
            }
        }
        return results;
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public CommentInfo createComment(String referenceId, String referenceTypeKey, String commentTypeKey, CommentInfo commentInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        if(!referenceId.equals(commentInfo.getReferenceId())) {
            throw new InvalidParameterException(referenceId + " does not match the corresponding value in the object " + commentInfo.getReferenceId());
        }
        if(!referenceTypeKey.equals(commentInfo.getReferenceTypeKey())) {
            throw new InvalidParameterException(referenceTypeKey + " does not match the corresponding value in the object " + commentInfo.getReferenceTypeKey());
        }
        if(!commentTypeKey.equals(commentInfo.getTypeKey())) {
            throw new InvalidParameterException(commentTypeKey + " does not match the corresponding value in the object " + commentInfo.getTypeKey());
        }

        CommentEntity entity = new CommentEntity(commentInfo);
        entity.setEntityCreated(contextInfo);

        commentDao.persist(entity);
        commentDao.getEm().flush();
        return entity.toDto();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public CommentInfo updateComment(String commentId, CommentInfo commentInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        if(!commentId.equals(commentInfo.getId())) {
            throw new ReadOnlyException(commentId + " does not match the id in the object " + commentInfo.getId());
        }
        CommentEntity entity = commentDao.find(commentId);
        if(null == entity) {
            throw new DoesNotExistException(commentId);
        }

        if(!commentInfo.getTypeKey().equals(entity.getTypeKey())) {
            throw new ReadOnlyException(commentInfo.getTypeKey() + " does not match the type key in the entity " + entity.getTypeKey());
        }

        if(!commentInfo.getReferenceTypeKey().equals(entity.getRefObjectTypeKey())) {
            throw new ReadOnlyException(commentInfo.getReferenceTypeKey() + " does not match the reference type key in the entity " + entity.getRefObjectTypeKey());
        }

        if(!commentInfo.getReferenceId().equals(entity.getRefObjectId())) {
            throw new ReadOnlyException(commentInfo.getReferenceId() + " does not match the ref object id in the entity " + entity.getRefObjectId());
        }

        entity.fromDto(commentInfo);
        entity.setEntityUpdated(contextInfo);

        entity = commentDao.merge(entity);
        
        commentDao.getEm().flush();
        
        return entity.toDto();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteComment(String commentId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        CommentEntity entity = commentDao.find(commentId);
        if(null == entity) {
            throw new DoesNotExistException((commentId));
        }

        commentDao.remove(entity);
        return new StatusInfo();
    }

    @Override
    @Transactional(readOnly = false, noRollbackFor = {DoesNotExistException.class}, rollbackFor = {Throwable.class})
    public StatusInfo deleteCommentsByReference(String referenceId, String referenceTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<CommentEntity> entities = commentDao.getCommentsByRefObjectIdAndRefObjectType(referenceId, referenceTypeKey);
        for(CommentEntity entity : entities) {
            commentDao.remove(entity);
        }
        return new StatusInfo();
    }

    @Override
    public List<ValidationResultInfo> validateComment(String validationTypeKey, String referenceId, String referenceTypeKey, String commentTypeKey, CommentInfo commentInfo, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        return new ArrayList<ValidationResultInfo>();
    }
}
