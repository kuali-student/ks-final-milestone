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

package org.kuali.student.r1.core.comment.service.impl;

import org.apache.log4j.Logger;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r1.common.dictionary.service.DictionaryService;
import org.kuali.student.r1.common.dto.ReferenceTypeInfo;
import org.kuali.student.r1.common.dto.StatusInfo;
import org.kuali.student.r1.core.comment.dao.CommentDao;
import org.kuali.student.r1.core.comment.dto.CommentInfo;
import org.kuali.student.r1.core.comment.dto.CommentTypeInfo;
import org.kuali.student.r1.core.comment.dto.TagInfo;
import org.kuali.student.r1.core.comment.dto.TagTypeInfo;
import org.kuali.student.r1.core.comment.entity.Comment;
import org.kuali.student.r1.core.comment.entity.CommentType;
import org.kuali.student.r1.core.comment.entity.Reference;
import org.kuali.student.r1.core.comment.entity.ReferenceType;
import org.kuali.student.r1.core.comment.entity.Tag;
import org.kuali.student.r1.core.comment.entity.TagType;
import org.kuali.student.r1.core.comment.service.CommentService;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.validator.Validator;
import org.kuali.student.r2.common.validator.ValidatorFactory;
import org.kuali.student.r2.core.search.service.SearchManager;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebService;
import javax.persistence.NoResultException;
import java.util.List;

/**
 * Implementation of the Comment Search Service
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
@Deprecated
@WebService(endpointInterface = "org.kuali.student.r1.core.comment.service.CommentService", serviceName = "CommentService", portName = "CommentService", targetNamespace = "http://student.kuali.org/wsdl/commentService")
public class CommentServiceImpl implements CommentService {
    
    final Logger logger = Logger.getLogger(CommentServiceImpl.class);
    
    private CommentDao commentDao;
    private DictionaryService dictionaryServiceDelegate;
    private SearchManager searchManager;
    private ValidatorFactory validatorFactory;

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.comment.service.CommentService#addComment(java.lang.String, java.lang.String, org.kuali.student.core.comment.dto.CommentInfo)
     */
    @Override
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public CommentInfo addComment(String referenceId, String referenceTypeKey, CommentInfo commentInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        commentInfo.setReferenceTypeKey(referenceTypeKey);
        commentInfo.setReferenceId(referenceId);
    	
        // Validate Comment
        List<ValidationResultInfo> validationResults = null;
        try {
            validationResults = validateComment("OBJECT", commentInfo);
        } catch (DoesNotExistException e1) {
            throw new OperationFailedException("Validation call failed." + e1.getMessage());
        }
        if (null != validationResults && validationResults.size() > 0) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }
        
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
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public TagInfo addTag(String referenceId, String referenceTypeKey, TagInfo tagInfo) throws DataValidationErrorException, AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        // Validate Tag
        List<ValidationResultInfo> validationResults = null;
        try {
            validationResults = validateTag("OBJECT", tagInfo);
        } catch (DoesNotExistException e1) {
            throw new OperationFailedException("Validation call failed." + e1.getMessage());
        }
        if (null != validationResults && validationResults.size() > 0) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }
        
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
    @Transactional(readOnly=true)
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
    @Transactional(readOnly=true)
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
    @Transactional(readOnly=true)
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
    @Transactional(readOnly=true)
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
    @Transactional(readOnly=true)
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
    @Transactional(readOnly=true)
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
    @Transactional(readOnly=true)
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
    @Transactional(readOnly=true)
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
    @Transactional(readOnly=true)
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
    @Transactional(readOnly=true)
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
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
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
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
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
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
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
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
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
    @Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
	public CommentInfo updateComment(String referenceId, String referenceTypeKey, CommentInfo commentInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, VersionMismatchException {

        // Validate Comment
        List<ValidationResultInfo> validationResults = validateComment("OBJECT", commentInfo);
        if (null != validationResults && validationResults.size() > 0) {
            throw new DataValidationErrorException("Validation error!", validationResults);
        }
        
		Comment entity = commentDao.fetch(Comment.class, commentInfo.getId());
		if (!String.valueOf(entity.getVersionNumber()).equals(commentInfo.getMetaInfo().getVersionInd())){
			throw new VersionMismatchException("ResultComponent to be updated is not the current version");
		}

		CommentServiceAssembler.toComment(entity, referenceId, referenceTypeKey, commentInfo, commentDao);
	    commentDao.update(entity);

	    return CommentServiceAssembler.toCommentInfo(entity);
    }

    /**
     * This overridden method ...
     *
     * @see org.kuali.student.core.comment.service.CommentService#validateComment(java.lang.String, org.kuali.student.core.comment.dto.CommentInfo)
     */
    @Override
    public List<ValidationResultInfo> validateComment(String validationType, CommentInfo commentInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		checkForMissingParameter(validationType, "validationType");
		checkForMissingParameter(commentInfo, "commentInfo");

        ObjectStructureDefinition objStructure = this.getObjectStructure(CommentInfo.class.getName());
        Validator defaultValidator = validatorFactory.getValidator();
        List<ValidationResultInfo> validationResults = defaultValidator.validateObject(commentInfo, objStructure, null);
        return validationResults;         
    }

    /**
     * 
     * This method ...
     * 
     * @param validationType
     * @param tagInfo
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @
     */
    @Deprecated
    private List<ValidationResultInfo> validateTag(String validationType, TagInfo tagInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        checkForMissingParameter(validationType, "validationType");
        checkForMissingParameter(tagInfo, "tagInfo");

        ObjectStructureDefinition objStructure = this.getObjectStructure(TagInfo.class.getName());
        Validator defaultValidator = validatorFactory.getValidator();
        List<ValidationResultInfo> validationResults = defaultValidator.validateObject(tagInfo, objStructure, null);
        return validationResults;         
    }
    
    /**
     * @return the commentDao
     */
    @Deprecated
    public CommentDao getCommentDao()  {
        return commentDao;
    }

    /**
     * @param commentDao the commentDao to set
     */
    @Deprecated
    public void setCommentDao(CommentDao commentDao)  {
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
    @Deprecated
	public DictionaryService getDictionaryServiceDelegate()  {
		return dictionaryServiceDelegate;
	}

	/**
	 * @param dictionaryServiceDelegate the dictionaryServiceDelegate to set
	 */
    @Deprecated
	public void setDictionaryServiceDelegate(
			DictionaryService dictionaryServiceDelegate)  {
		this.dictionaryServiceDelegate = dictionaryServiceDelegate;
	}
    /**
	 * @return the searchManager
	 */
    @Deprecated
	public SearchManager getSearchManager()  {
		return searchManager;
	}

	/**
	 * @param searchManager the searchManager to set
	 */
    @Deprecated
	public void setSearchManager(SearchManager searchManager)  {
		this.searchManager = searchManager;
	}

	@Override
	public ObjectStructureDefinition getObjectStructure(String objectTypeKey) {
		return dictionaryServiceDelegate.getObjectStructure(objectTypeKey);
	}
	@Override
	public List<String> getObjectTypes() {
		return dictionaryServiceDelegate.getObjectTypes();
	}

    /**
     * @return the validatorFactory
     */
	@Deprecated
    public ValidatorFactory getValidatorFactory()  {
        return validatorFactory;
    }

    /**
     * @param validatorFactory the validatorFactory to set
     */
	@Deprecated
    public void setValidatorFactory(ValidatorFactory validatorFactory)  {
        this.validatorFactory = validatorFactory;
    }
}
