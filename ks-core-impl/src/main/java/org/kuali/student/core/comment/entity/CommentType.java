package org.kuali.student.core.comment.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.core.entity.Type;
@Entity
@Table(name = "KSCO_COMMENT_TYPE")
@NamedQueries(
	{@NamedQuery(name = "CommentType.getCommentTypesByReferenceTypeId", query="SELECT DISTINCT comment.type FROM Comment comment JOIN comment.reference r1 WHERE r1.referenceType.id = :referenceTypeId")
})

public class CommentType extends Type<CommentTypeAttribute> {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<CommentTypeAttribute> attributes;

    @Override
    public List<CommentTypeAttribute> getAttributes() {
        return attributes;
    }

    @Override
    public void setAttributes(List<CommentTypeAttribute> attributes) {
        this.attributes=attributes;
    }

}
