package org.kuali.student.myplan.comment.dataobject;

import java.util.Date;

/**
 *  Data object for comments.
 */
public class CommentDataObject implements Comparable<CommentDataObject> {
    private Date createDate;
    private String body;
    private String from;

    public String getFrom() {
       return from;
    }

    public void setFrom(String from) {
       this.from = from;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int compareTo(CommentDataObject other) {
        if (other == null) {
            return 1;
        }
        return this.getCreateDate().compareTo(other.getCreateDate());
    }
}
