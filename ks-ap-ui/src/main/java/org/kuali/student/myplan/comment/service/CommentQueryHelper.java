package org.kuali.student.myplan.comment.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.myplan.comment.CommentConstants;
import org.kuali.student.myplan.comment.dataobject.CommentDataObject;
import org.kuali.student.myplan.comment.dataobject.MessageDataObject;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.comment.dto.CommentInfo;

public class CommentQueryHelper {

	public static MessageDataObject getMessage(String messageId) {
        CommentInfo commentInfo;
        try {
			commentInfo = KsapFrameworkServiceLocator.getCommentService().getComment(messageId,
						KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (DoesNotExistException e) {
			throw new IllegalArgumentException("Comment lookup failure", e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("Comment lookup failure", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("Comment lookup failure", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("Comment lookup failure", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("Comment lookup failure", e);
        }

		return makeMessageDataObject(commentInfo);
    }

	private static MessageDataObject makeMessageDataObject(CommentInfo commentInfo) {
        MessageDataObject messageDataObject = new MessageDataObject();
        messageDataObject.setCreateDate(commentInfo.getMeta().getCreateTime());
        messageDataObject.setSubject(commentInfo.getAttributeValue(CommentConstants.SUBJECT_ATTRIBUTE_NAME));
        messageDataObject.setBody(commentInfo.getCommentText().getPlain());
        messageDataObject.setFrom(KsapFrameworkServiceLocator.getUserSessionHelper().getName(commentInfo.getAttributeValue(CommentConstants.CREATED_BY_USER_ATTRIBUTE_NAME)));
        messageDataObject.setMessageId(commentInfo.getId());

        //  Pass the id of the message to get the comments associated with this message.
		List<CommentDataObject> comments = getComments(commentInfo.getId());

        //  Determine the last update date for the message. If comments exist then use the most recent comment create date.
        //  Otherwise, use the message create date.
        Date lastCommentDate = null;
        String lastCommentBy = null;
        for (CommentDataObject comment : comments) {
            Date d = comment.getCreateDate();
            if (lastCommentDate == null || d.after(lastCommentDate)) {
                lastCommentDate = d;
                lastCommentBy = comment.getFrom();
            }
        }
        messageDataObject.setLastCommentBy(lastCommentBy);
        messageDataObject.setLastCommentDate(lastCommentDate);
        messageDataObject.setComments(comments);
        return messageDataObject;
    }

    /**
     * Get all messages for a particular student.
	 *
     * @param studentId
     * @return
     */
	public static List<MessageDataObject> getMessages(String studentId) {
		List<CommentInfo> commentInfos;
        try {
			commentInfos = KsapFrameworkServiceLocator.getCommentService().getCommentsByReferenceAndType(studentId,
						CommentConstants.MESSAGE_REF_TYPE, KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (DoesNotExistException e) {
			throw new IllegalArgumentException("Comment lookup failure", e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("Comment lookup failure", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("Comment lookup failure", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("Comment lookup failure", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("Comment lookup failure", e);
        }

		List<MessageDataObject> messages = new ArrayList<MessageDataObject>(commentInfos.size());
		for (CommentInfo ci : commentInfos)
			messages.add(makeMessageDataObject(ci));

        Collections.sort(messages);
        return messages;
    }

	private static List<CommentDataObject> getComments(String messageId) {
		List<CommentInfo> commentInfos;
        try {
			commentInfos = KsapFrameworkServiceLocator.getCommentService().getCommentsByReferenceAndType(messageId,
					CommentConstants.COMMENT_REF_TYPE, KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (DoesNotExistException e) {
			throw new IllegalArgumentException("Comment lookup failure", e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("Comment lookup failure", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("Comment lookup failure", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("Comment lookup failure", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("Comment lookup failure", e);
        }

		List<CommentDataObject> comments = new ArrayList<CommentDataObject>(commentInfos.size());
        for (CommentInfo ci : commentInfos) {
            CommentDataObject commentDataObject = new CommentDataObject();
            commentDataObject.setCreateDate(ci.getMeta().getCreateTime());
            commentDataObject.setBody(ci.getCommentText().getPlain());
            commentDataObject.setFrom(KsapFrameworkServiceLocator.getUserSessionHelper().getName(ci.getAttributeValue(CommentConstants.CREATED_BY_USER_ATTRIBUTE_NAME)));
            comments.add(commentDataObject);
        }

        Collections.sort(comments);
        return comments;
    }
}