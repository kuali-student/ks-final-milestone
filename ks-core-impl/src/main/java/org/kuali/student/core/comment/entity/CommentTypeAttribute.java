package org.kuali.student.core.comment.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.core.entity.Attribute;
@Entity
@Table(name = "KSCO_COMMENT_TYPE_ATTR")
public class CommentTypeAttribute extends Attribute<CommentType> {
    @ManyToOne
    @JoinColumn(name = "OWNER")
    private CommentType owner;

    @Override
    public CommentType getOwner() {
        return owner;
    }

    @Override
    public void setOwner(CommentType owner) {
        this.owner = owner;
    }
}
