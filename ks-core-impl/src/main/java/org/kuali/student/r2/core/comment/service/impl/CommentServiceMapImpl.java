/*
 * Copyright 2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Lic+ense is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.core.comment.service.impl;


import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
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
import org.kuali.student.r2.core.comment.service.CommentService;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class CommentServiceMapImpl implements MockService, CommentService
{
	// cache variable 
	// The LinkedHashMap is just so the values come back in a predictable order
	private Map<String, CommentInfo> commentMap = new LinkedHashMap<String, CommentInfo>();

	@Override
	public void clear()
	{
		this.commentMap.clear ();
	}

	
	@Override
	public CommentInfo getComment(String commentId, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_BY_ID
		if (!this.commentMap.containsKey(commentId)) {
		   throw new DoesNotExistException(commentId);
		}
		return new CommentInfo(this.commentMap.get (commentId));
	}
	
	@Override
	public List<CommentInfo> getCommentsByIds(List<String> commentIds, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_BY_IDS
		List<CommentInfo> list = new ArrayList<CommentInfo> ();
		for (String id: commentIds) {
		    list.add (this.getComment(id, contextInfo));
		}
		return list;
	}
	
	@Override
	public List<String> getCommentIdsByType(String commentTypeKey, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_IDS_BY_TYPE
		List<String> list = new ArrayList<String> ();
		for (CommentInfo info: commentMap.values ()) {
			if (commentTypeKey.equals(info.getTypeKey())) {
			    list.add (info.getId ());
			}
		}
		return list;
	}
	
	@Override
	public List<CommentInfo> getCommentsByReferenceAndType(String referenceId, String referenceTypeKey, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// GET_INFOS_BY_OTHER
		List<CommentInfo> list = new ArrayList<CommentInfo> ();
		for (CommentInfo info: commentMap.values ()) {
			if (referenceId.equals(info.getReferenceId())) {
				if (referenceTypeKey.equals(info.getReferenceTypeKey())) {
				    list.add (new CommentInfo(info));
				}
			}
		}
		return list;
	}
	
	@Override
	public List<String> searchForCommentIds(QueryByCriteria criteria, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// UNKNOWN
		throw new OperationFailedException ("searchForCommentIds has not been implemented");
	}
	
	@Override
	public List<CommentInfo> searchForComments(QueryByCriteria criteria, ContextInfo contextInfo)
		throws InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// UNKNOWN
		throw new OperationFailedException ("searchForComments has not been implemented");
	}
	
	@Override
    public CommentInfo createComment_KRAD(String referenceId, String referenceTypeKey, String commentTypeKey, CommentInfo commentInfo, ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException {
        /*
        commentInfo.setReferenceTypeKey(referenceTypeKey);
        commentInfo.setReferenceId(referenceId);

        // Validate Comment
        List<ValidationResultInfo> validationResults = null;
        try {
            validationResults = validateComment_KRAD("OBJECT", commentInfo, contextInfo);
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
        */
        // TODO: Re-implement create comment for krad
        return null;
    }

	@Override
	public CommentInfo createComment(String referenceId, String referenceTypeKey, String commentTypeKey, CommentInfo commentInfo, ContextInfo contextInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,ReadOnlyException
	{
		// CREATE
        if (!referenceId.equals (commentInfo.getReferenceId())) {
            throw new InvalidParameterException ("The reference id parameter does not match the reference id on the info object");
        }
		if (!referenceTypeKey.equals (commentInfo.getReferenceTypeKey())) {
		    throw new InvalidParameterException ("The reference type parameter does not match the reference type on the info object");
		}
        if (!commentTypeKey.equals (commentInfo.getTypeKey())) {
            throw new InvalidParameterException ("The type parameter does not match the type on the info object");
        }

		CommentInfo copy = new CommentInfo(commentInfo);
		if (copy.getId() == null) {
		   copy.setId(UUIDHelper.genStringUUID());
		}
		copy.setMeta(newMeta(contextInfo));
		commentMap.put(copy.getId(), copy);
		return new CommentInfo(copy);
	}
	
	@Override
	public CommentInfo updateComment(String commentId, CommentInfo commentInfo, ContextInfo contextInfo)
		throws DataValidationErrorException
		      ,DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
		      ,ReadOnlyException
		      ,VersionMismatchException
	{
		// UPDATE
		if (!commentId.equals (commentInfo.getId())) {
		    throw new InvalidParameterException ("The id parameter does not match the id on the info object");
		}
		CommentInfo copy = new CommentInfo(commentInfo);
		CommentInfo old = this.getComment(commentInfo.getId(), contextInfo);
		if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
		    throw new VersionMismatchException(old.getMeta().getVersionInd());
		}
        if (!old.getReferenceId().equals(commentInfo.getReferenceId())) {
            throw new ReadOnlyException("The new reference id does not match the existing value");
        }
        if (!old.getReferenceTypeKey().equals(commentInfo.getReferenceTypeKey())) {
            throw new ReadOnlyException("The new reference type parameter does not match the existing value");
        }
        if (!old.getTypeKey().equals(commentInfo.getTypeKey())) {
            throw new ReadOnlyException("The new type parameter does not match the existing value");
        }

		copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
		this.commentMap .put(commentInfo.getId(), copy);
		return new CommentInfo(copy);
	}
	
	@Override
	public StatusInfo deleteComment(String commentId, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
		// DELETE
		if (this.commentMap.remove(commentId) == null) {
		   throw new DoesNotExistException(commentId);
		}
		return newStatus();
	}
	
	@Override
	public StatusInfo deleteCommentsByReference(String referenceId, String referenceTypeKey, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
		      ,PermissionDeniedException
	{
        List<String> idsToRemove = new ArrayList<String>();
        for(CommentInfo info : commentMap.values()) {
            if (referenceId.equals(info.getReferenceId()) && referenceTypeKey.equals(info.getReferenceTypeKey())) {
                idsToRemove.add(info.getId());
            }
        }
        for(String id : idsToRemove) {
            commentMap.remove(id);
        }
        return newStatus();
	}
	
	@Override
	public List<ValidationResultInfo> validateComment(String validationTypeKey, String referenceId,
                                                      String referenceTypeKey, String commentTypeKey, CommentInfo commentInfo, ContextInfo contextInfo)
		throws DoesNotExistException
		      ,InvalidParameterException
		      ,MissingParameterException
		      ,OperationFailedException
	{
		// VALIDATE
		return new ArrayList<ValidationResultInfo> ();
	}
	
	private StatusInfo newStatus() {
	     StatusInfo status = new StatusInfo();
	     status.setSuccess(Boolean.TRUE);
	     return status;
	}
	
	private MetaInfo newMeta(ContextInfo context) {
	     MetaInfo meta = new MetaInfo();
	     meta.setCreateId(context.getPrincipalId());
	     meta.setCreateTime(new Date());
	     meta.setUpdateId(context.getPrincipalId());
	     meta.setUpdateTime(meta.getCreateTime());
	     meta.setVersionInd("0");
	     return meta;
	}
	
	private MetaInfo updateMeta(MetaInfo old, ContextInfo context) {
	     MetaInfo meta = new MetaInfo(old);
	     meta.setUpdateId(context.getPrincipalId());
	     meta.setUpdateTime(new Date());
	     meta.setVersionInd((Integer.parseInt(meta.getVersionInd()) + 1) + "");
	     return meta;
	}
	
	@Override
    public List<ValidationResultInfo> validateComment_KRAD(String validationType,
                                                      CommentInfo commentInfo,
                                                      ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException {
		// checkForMissingParameter(validationType, "validationType");
		// checkForMissingParameter(commentInfo, "commentInfo");

        //ObjectStructureDefinition objStructure = this.getObjectStructure(CommentInfo.class.getName());
        //Validator defaultValidator = validatorFactory.getValidator();
        //List<ValidationResultInfo> validationResults = defaultValidator.validateObject(commentInfo, objStructure, null);
        //return validationResults;      
		return null;
    }
}

