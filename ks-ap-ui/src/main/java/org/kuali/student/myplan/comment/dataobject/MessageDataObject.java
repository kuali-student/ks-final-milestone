package org.kuali.student.myplan.comment.dataobject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  Data object for messages from advisers.
 */
public class MessageDataObject extends CommentDataObject {

    private String messageId;
    
    private String subject;

    private Date lastCommentDate;
    
    private String lastCommentBy;

    private List<CommentDataObject> comments;

    public String getLastCommentBy() {
        return lastCommentBy;
    }

    public void setLastCommentBy(String lastCommentBy) {
        this.lastCommentBy = lastCommentBy;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public MessageDataObject() {
        comments = new ArrayList<CommentDataObject>();
    }

    public Date getLastCommentDate() {
        return lastCommentDate;
    }

    public void setLastCommentDate(Date lastCommentDate) {
        this.lastCommentDate = lastCommentDate;
    }

    public List<CommentDataObject> getComments() {
        return comments;
    }

    public void addComment(CommentDataObject comment) {
        comments.add(comment);
    }

    public void setComments(List<CommentDataObject> comments) {
        this.comments = comments;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Sort messages by the last update date.
     */
    public int compareTo(MessageDataObject other) {
        if (other == null) {
            return 1;
        }
        return this.getLastCommentDate().compareTo(other.getLastCommentDate());
    }
}