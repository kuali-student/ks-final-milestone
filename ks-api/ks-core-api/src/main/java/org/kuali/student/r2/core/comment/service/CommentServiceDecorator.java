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
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.comment.dto.CommentInfo;

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
    public List<CommentInfo> getCommentsByRefObject(String refObjectId, String refObjectUri, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getCommentsByRefObject(refObjectId, refObjectUri, contextInfo);
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
    public CommentInfo createComment(String refObjectId, String refObjectUri, String commentTypeKey, CommentInfo commentInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        return getNextDecorator().createComment(refObjectId, refObjectUri, commentTypeKey, commentInfo, contextInfo);
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
    public StatusInfo deleteCommentsByReference(String refObjectId, String refObjectUri, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        return getNextDecorator().deleteCommentsByReference(refObjectId, refObjectUri, contextInfo);
    }

	@Override
	public List<ValidationResultInfo> validateComment(String validationTypeKey,
			String refObjectId, String refObjectUri, String commentTypeKey,
			CommentInfo commentInfo, ContextInfo contextInfo)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		return getNextDecorator().validateComment(validationTypeKey, refObjectId, refObjectUri, commentTypeKey, commentInfo, contextInfo);
	}


}
