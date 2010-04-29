/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.core.comment.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.entity.AttributeOwner;
import org.kuali.student.core.entity.MetaEntity;

/**
 * This is a description of what this class does - lindholm don't forget to fill this in.
 *
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
@Entity
@Table(name = "KSCO_COMMENT")
@NamedQueries( {
        @NamedQuery(name = "Comment.getComments", query = "SELECT  comment FROM Comment comment JOIN comment.reference r1 WHERE r1.referenceId =:refId AND r1.referenceType.id =:refTypeId"),
        @NamedQuery(name = "Comment.getComment", query = "SELECT  comment FROM Comment comment JOIN comment.reference r1 WHERE r1.referenceId =:refId AND r1.referenceType.id =:refTypeId"),
        @NamedQuery(name = "Comment.getCommentsByType", query = "SELECT  comment FROM Comment comment JOIN comment.reference r1 WHERE r1.referenceId =:refId AND r1.referenceType.id =:refTypeId AND comment.type.id =:commentTypeId"),
        @NamedQuery(name = "Comment.getCommentsByRefId", query="SELECT comment FROM Comment comment JOIN comment.reference r1 WHERE r1.referenceId =:refId")})
public class Comment extends MetaEntity implements AttributeOwner<CommentAttribute> {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    private String id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RT_DESCR_ID")
    private CommentRichText commentText;

    @ManyToOne
    @JoinColumn(name = "REFERENCE")
    private Reference reference;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<CommentAttribute> attributes;

    @ManyToOne
    @JoinColumn(name = "TYPE")
    private CommentType type;

    @Column(name = "STATE")
    private String state;

    /**
     * AutoGenerate the Id
     */
    @PrePersist
    public void beforePersist() {
        this.id = UUIDHelper.genStringUUID(this.id);
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the commentText
     */
    public CommentRichText getCommentText() {
        return commentText;
    }

    /**
     * @param commentText the commentText to set
     */
    public void setCommentText(CommentRichText commentText) {
        this.commentText = commentText;
    }

    /**
	 * @param reference the reference to set
	 */
	public void setReference(Reference reference) {
		this.reference = reference;
	}

	/**
	 * @return the reference
	 */
	public Reference getReference() {
		return reference;
	}

	/**
     * @return the effectiveDate
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * @param effectiveDate the effectiveDate to set
     */
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * @return the expirationDate
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    /**
     * @param expirationDate the expirationDate to set
     */
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }


    @Override
    public List<CommentAttribute> getAttributes() {
        return attributes;
    }

    @Override
    public void setAttributes(List<CommentAttribute> attributes) {
        this.attributes = attributes;
    }

    /**
     * @return the type
     */
    public CommentType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(CommentType type) {
        this.type = type;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

}
