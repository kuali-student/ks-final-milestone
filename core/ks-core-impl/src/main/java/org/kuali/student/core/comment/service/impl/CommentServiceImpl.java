/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.core.comment.service.impl;

import java.util.List;

import javax.jws.WebService;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.kuali.student.common.validator.Validator;
import org.kuali.student.core.comment.dao.CommentDao;
import org.kuali.student.core.comment.dto.CommentInfo;
import org.kuali.student.core.comment.dto.CommentTypeInfo;
import org.kuali.student.core.comment.dto.TagInfo;
import org.kuali.student.core.comment.dto.TagTypeInfo;
import org.kuali.student.core.comment.entity.Comment;
import org.kuali.student.core.comment.entity.CommentType;
import org.kuali.student.core.comment.entity.Reference;
import org.kuali.student.core.comment.entity.ReferenceType;
import org.kuali.student.core.comment.entity.Tag;
import org.kuali.student.core.comment.entity.TagType;
import org.kuali.student.core.comment.service.CommentService;
import org.kuali.student.core.dictionary.dto.ObjectStructure;
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
import org.kuali.student.core.search.service.impl.SearchManager;
import org.kuali.student.core.validation.dto.ValidationResultContainer;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the Comment Search Service
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
@WebService(endpointInterface = "org.kuali.student.core.comment.service.CommentService", serviceName = "CommentService", portName = "CommentService", targetNamespace = "http://student.kuali.org/wsdl/commentService")
@Transactional(rollbackFor={Throwable.class})
public class CommentServiceImpl implements CommentService {
    
    final Logger logger = Logger.getLogger(CommentServiceImpl.class);
    
    private CommentDao commentDao;
    private DictionaryService dictionaryServiceDelegate;// = new DictionaryServiceImpl(); //TODO this should probably be done differently, but I don't want to copy/paste the code in while it might still change
    private SearchManager searchManager;
    private Validator validator;

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.comment.service.CommentService#addComment(java.lang.String, java.lang.String, org.kuali.student.core.comment.dto.CommentInfo)
     */
    @Override
    public CommentInfo addComment(String referenceId, String referenceTypeKey, CommentInfo commentInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        commentInfo.setReferenceTypeKey(referenceTypeKey);
        commentInfo.setReferenceId(referenceId);
        Reference reference=null;
        reference = commentDao.getReference(referenceId, referenceTypeKey);
        if(reference==null){
            reference = new Reference();
            reference.setReferenceId(referenceId);
			try {
				ReferenceType referenceType = commentDao.fetch(ReferenceType.class, referenceTypeKey);
	            reference.setReferenceType(referenceType);
	            commentDao.create(reference);
			} catch (DoesNotExistException e) {
				throw new InvalidParameterException(e.getMessage());
			}
        }

        Comment comment = null;

        try {
            comment = CommentServiceAssembler.toComment(false, commentInfo, commentDao);
        } catch (DoesNotExistException e) {
            throw new InvalidParameterException(e.getMessage());
        }

        Comment createdComment = commentDao.create(comment);

        CommentInfo createdCommentInfo = CommentServiceAssembler.toCommentInfo(createdComment);

        return createdCommentInfo;
    }

    /**
     * This overridden method ...
     * @see org.kuali.student.core.comment.service.CommentService#addTag(java.lang.String, java.lang.String, org.kuali.student.core.comment.dto.TagInfo)
     */
    @Override
    public TagInfo addTag(String referenceId, String referenceTypeKey, TagInfo tagInfo) throws DataValidationErrorException, AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        tagInfo.setReferenceTypeKey(referenceTypeKey);
        tagInfo.setReferenceId(referenceId);
        Reference reference=null;
        reference = commentDao.getReference(referenceId, referenceTypeKey);
        if(reference==null){
            reference = new Reference();
            reference.setReferenceId(referenceId);
			try {
				ReferenceType referenceType = commentDao.fetch(ReferenceType.class, referenceTypeKey);
	            reference.setReferenceType(referenceType);
	            commentDao.create(reference);
			} catch (DoesNotExistException e) {
				throw new InvalidParameterException(e.getMessage());
			}
        }

        Tag tag = null;

        try {
            tag = CommentServiceAssembler.toTag(false, tagInfo, commentDao);
        } catch (DoesNotExistException e) {
            logger.error("Exception occured: ", e);
        }

        Tag createdTag = commentDao.create(tag);

        TagInfo createdTagInfo = CommentServiceAssembler.toTagInfo(createdTag);

        return createdTagInfo;
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
        List<CommentType> commentTypes = commentDao.find(CommentType.class);
        return CommentServiceAssembler.toCommentTypeInfos(commentTypes);
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.comment.service.CommentService#getCommentTypesForReferenceType(java.lang.String)
     */
    @Override
    public List<CommentTypeInfo> getCommentTypesForReferenceType(String referenceTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        List<CommentType> commentTypes = commentDao.getCommentTypesByReferenceTypeId(referenceTypeKey);
        return CommentServiceAssembler.toCommentTypeInfos(commentTypes);
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.comment.service.CommentService#getComments(java.lang.String, java.lang.String)
     */
    @Override
    public List<CommentInfo> getComments(String referenceId, String referenceTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<Comment> comments = commentDao.getComments(referenceId, referenceTypeKey);
        return CommentServiceAssembler.toCommentInfos(comments);
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.comment.service.CommentService#getCommentsByType(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public List<CommentInfo> getCommentsByType(String referenceId, String referenceTypeKey, String commentTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<Comment> comments = commentDao.getCommentsByType(referenceId, referenceTypeKey, commentTypeKey);
        return CommentServiceAssembler.toCommentInfos(comments);
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.comment.service.CommentService#getReferenceTypes()
     */
    @Override
    public List<ReferenceTypeInfo> getReferenceTypes() throws OperationFailedException {
    	List<ReferenceType> referenceTypes = commentDao.find(ReferenceType.class);
        return CommentServiceAssembler.toReferenceTypeInfos(referenceTypes);
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
        List<TagType> tagTypes = commentDao.find(TagType.class);

        return CommentServiceAssembler.toTagTypeInfos(tagTypes);
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.comment.service.CommentService#getTags(java.lang.String, java.lang.String)
     */
    @Override
    public List<TagInfo> getTags(String referenceId, String referenceTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<Tag> tags = commentDao.getTags(referenceId, referenceTypeKey);

        return CommentServiceAssembler.toTagInfos(tags);
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.comment.service.CommentService#getTagsByType(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public List<TagInfo> getTagsByType(String referenceId, String referenceTypeKey, String tagTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<Tag> tags = commentDao.getTagsByType(referenceId, referenceTypeKey, tagTypeKey);
        return CommentServiceAssembler.toTagInfos(tags);
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.comment.service.CommentService#removeComment(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public StatusInfo removeComment(String commentId, String referenceId, String referenceTypeKey) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        try{
            checkForMissingParameter(commentId, "commentId");
            commentDao.delete(Comment.class, commentId);
            return  new StatusInfo();
        }
        catch(MissingParameterException mpe){
            Comment comment = null;
            try{
                comment = commentDao.getComment(referenceId, referenceTypeKey);
                if(comment==null){
                    throw new DoesNotExistException();
                }
            }
            catch(NoResultException nre){
                throw new DoesNotExistException();
            }
            commentDao.delete(comment);
            return  new StatusInfo();
        }

    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.comment.service.CommentService#removeComments(java.lang.String)
     */
    @Override
    public StatusInfo removeComments(String referenceId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<Comment> comments = commentDao.getCommentsByRefId(referenceId);
        for(Comment comment:comments){
            commentDao.delete(comment);
        }
        return new StatusInfo();
    }

    /**
     * This overridden method ...
     * @throws DoesNotExistException
     *
     * @see org.kuali.student.core.comment.service.CommentService#removeTag(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public StatusInfo removeTag(String tagId, String referenceId, String referenceTypeKey) throws  InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException {
        try{
            checkForMissingParameter(tagId, "tagId");
            commentDao.delete(Tag.class, tagId);
            return  new StatusInfo();
        }
        catch(MissingParameterException mpe){
            Tag tag = null;
            try{
                tag = commentDao.getTag(referenceId, referenceTypeKey);
                if(tag==null){
                    throw new DoesNotExistException();
                }
            }
            catch(NoResultException nre){
                throw new DoesNotExistException();
            }
            commentDao.delete(tag);
            return  new StatusInfo();
        }
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.comment.service.CommentService#removeTags(java.lang.String)
     */
    @Override
    public StatusInfo removeTags(String tagId) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //tagId sould be referenceId like in removeComments() method
        List<Tag> tags = commentDao.getTagsByRefId(tagId);
        for(Tag tag:tags){
            commentDao.delete(tag);
        }
        return new StatusInfo();
    }


    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.comment.service.CommentService#updateComment(java.lang.String, java.lang.String, org.kuali.student.core.comment.dto.CommentInfo)
     */
    @Override
    public CommentInfo updateComment(String referenceId, String referenceTypeKey, CommentInfo commentInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        try {
			Comment entity = commentDao.fetch(Comment.class, commentInfo.getId());
			if (!String.valueOf(entity.getVersionInd()).equals(commentInfo.getMetaInfo().getVersionInd())){
				throw new VersionMismatchException("ResultComponent to be updated is not the current version");
			}

			CommentServiceAssembler.toComment(entity, referenceId, referenceTypeKey, commentInfo, commentDao);
	    	commentDao.update(entity);

	        return CommentServiceAssembler.toCommentInfo(entity);
		} catch (DoesNotExistException e) {
			throw new InvalidParameterException(e.getMessage());
		} catch (VersionMismatchException e) {
			throw new DataValidationErrorException(e.getMessage());
		}
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.comment.service.CommentService#validateComment(java.lang.String, org.kuali.student.core.comment.dto.CommentInfo)
     */
    @Override
    public List<ValidationResultContainer> validateComment(String validationType, CommentInfo commentInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		checkForMissingParameter(validationType, "validationType");
		checkForMissingParameter(commentInfo, "commentInfo");

		List<ValidationResultContainer> validationResults = validator.validateTypeStateObject(commentInfo, getObjectStructure("commentInfo"));

		return validationResults;
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
	 * @return the dictionaryServiceDelegate
	 */
	public DictionaryService getDictionaryServiceDelegate() {
		return dictionaryServiceDelegate;
	}

	/**
	 * @param dictionaryServiceDelegate the dictionaryServiceDelegate to set
	 */
	public void setDictionaryServiceDelegate(
			DictionaryService dictionaryServiceDelegate) {
		this.dictionaryServiceDelegate = dictionaryServiceDelegate;
	}
    /**
	 * @return the searchManager
	 */
	public SearchManager getSearchManager() {
		return searchManager;
	}

	/**
	 * @param searchManager the searchManager to set
	 */
	public void setSearchManager(SearchManager searchManager) {
		this.searchManager = searchManager;
	}

	@Override
	public ObjectStructure getObjectStructure(String objectTypeKey) {
		return dictionaryServiceDelegate.getObjectStructure(objectTypeKey);
	}
	@Override
	public List<String> getObjectTypes() {
		return dictionaryServiceDelegate.getObjectTypes();
	}

	@Override
	public boolean validateObject(String objectTypeKey, String stateKey,
			String info) {
		return dictionaryServiceDelegate.validateObject(objectTypeKey, stateKey, info);
	}

	@Override
	public boolean validateStructureData(String objectTypeKey, String stateKey,
			String info) {
		return dictionaryServiceDelegate.validateStructureData(objectTypeKey, stateKey, info);
	}

	public Validator getValidator() {
		return validator;
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
	}

}
