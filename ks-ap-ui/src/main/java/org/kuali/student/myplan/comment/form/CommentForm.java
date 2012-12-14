package org.kuali.student.myplan.comment.form;

import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.myplan.comment.dataobject.CommentDataObject;

import java.util.Date;
import java.util.List;

public class CommentForm extends UifFormBase {
    private String from;
    private Date createdDate;
    private String studentName;
    private String personName;
    private String subject;
    private String body;
    private String commentBody;
    private String studentId;
    private String messageId;
    private List<CommentDataObject> comments;
    private boolean feedBackMode;

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public boolean isFeedBackMode() {
        return feedBackMode;
    }

    public void setFeedBackMode(boolean feedBackMode) {
        this.feedBackMode = feedBackMode;
    }

    public List<CommentDataObject> getComments() {
        return comments;
    }

    public void setComments(List<CommentDataObject> comments) {
        this.comments = comments;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }
}
