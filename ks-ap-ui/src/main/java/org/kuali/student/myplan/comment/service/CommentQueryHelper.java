package org.kuali.student.myplan.comment.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.myplan.comment.CommentConstants;
import org.kuali.student.myplan.comment.dataobject.CommentDataObject;
import org.kuali.student.myplan.comment.dataobject.MessageDataObject;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.comment.service.CommentService;

public class CommentQueryHelper {

    private final Logger logger = Logger.getLogger(CommentQueryHelper.class);

    private transient CommentService commentService;

    private CommentService getCommentService() {
        if (commentService == null) {
            commentService = (CommentService)
                GlobalResourceLoader.getService(new QName(CommentConstants.NAMESPACE, CommentConstants.SERVICE_NAME));
        }
        return commentService;
    }

    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * Retrieve a single message.
     * @param messageId
     * @return
     */
    public synchronized MessageDataObject getMessage(String messageId, ContextInfo context) {
        CommentInfo commentInfo;
        try {
            commentInfo = getCommentService().getComment(messageId, context);
        } catch (Exception e) {
            logger.error(String.format("Query for comment [%s] failed.", messageId), e);
            return null;
        }
        return makeMessageDataObject(commentInfo, context);
    }

    private MessageDataObject makeMessageDataObject(CommentInfo commentInfo, ContextInfo context) {
        MessageDataObject messageDataObject = new MessageDataObject();
        messageDataObject.setCreateDate(commentInfo.getMeta().getCreateTime());
        messageDataObject.setSubject(commentInfo.getAttributeValue(CommentConstants.SUBJECT_ATTRIBUTE_NAME));
        messageDataObject.setBody(commentInfo.getCommentText().getPlain());
        messageDataObject.setFrom(KsapFrameworkServiceLocator.getUserSessionHelper().getName(commentInfo.getAttributeValue(CommentConstants.CREATED_BY_USER_ATTRIBUTE_NAME)));
        messageDataObject.setMessageId(commentInfo.getId());

        //  Pass the id of the message to get the comments associated with this message.
        List<CommentDataObject> comments = getComments(commentInfo.getId(), context);

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
     * @param studentId
     * @return
     */
    public synchronized List<MessageDataObject> getMessages(String studentId, ContextInfo context) {
        List<MessageDataObject> messages = new ArrayList<MessageDataObject>();
        List<CommentInfo> commentInfos = new ArrayList<CommentInfo>();
        try {
            commentInfos = getCommentService().getCommentsByReferenceAndType(studentId, CommentConstants.MESSAGE_REF_TYPE, context);
        } catch (Exception e) {
            logger.error(String.format("Failed to retrieve messages for student [%s].", studentId), e);
        }

        for (CommentInfo ci : commentInfos) {
            MessageDataObject messageDataObject = makeMessageDataObject(ci, context);
            messages.add(messageDataObject);
        }

        Collections.sort(messages);
        return messages;
    }

    private List<CommentDataObject> getComments(String messageId, ContextInfo context) {
        List<CommentDataObject> comments = new ArrayList<CommentDataObject>();
        List<CommentInfo> commentInfos = new ArrayList<CommentInfo>();
        try {
            commentInfos = getCommentService().getCommentsByReferenceAndType(messageId, CommentConstants.COMMENT_REF_TYPE, context);
        } catch (Exception e) {
            logger.error(String.format("Failed to retrieve messages for messages id [%s].", messageId), e);
        }

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