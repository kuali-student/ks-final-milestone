/*
 * Copyright 2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.r2.core.comment.model;


import org.kuali.student.r1.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.comment.infc.Comment;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "KSEN_COMMENT")
@NamedQueries({
        @NamedQuery(name = CommentEntity.COMMENT_QUERY_GET_IDS_BY_TYPE,
                query = "select id from CommentEntity where typeKey = :type"),
        @NamedQuery(name = CommentEntity.COMMENT_QUERY_GET_COMMENTS_BY_REFERENCE_ID_REFERENCE_TYPE,
                query = "select comment from CommentEntity comment where comment.refObjectId = :id AND comment.refObjectTypeKey = :type")
})
public class CommentEntity extends MetaEntity implements AttributeOwner<CommentAttributeEntity> {

    public static final String COMMENT_QUERY_GET_IDS_BY_TYPE = "CommentEntity.getIdsByType";
    public static final String COMMENT_QUERY_GET_COMMENTS_BY_REFERENCE_ID_REFERENCE_TYPE = "CommentEntity.getCommentsByReferenceIdAndReferenceType";

    @Column(name = "COMMENT_TYPE", nullable=false)
    private String typeKey;

    @Column(name = "COMMENT_STATE", nullable=false)
    private String stateKey;

    @Column(name = "COMMENT_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String commentPlain;

    @Column(name = "COMMENT_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String commentFormatted;

    @Column(name="COMMENTER_ID")
    private String commenterId;

    @Column(name = "REF_OBJECT_TYPE", nullable=false)
    private String refObjectTypeKey;

    @Column(name = "REF_OBJECT_ID", nullable=false)
    private String refObjectId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch= FetchType.EAGER, orphanRemoval=true)
    private Set<CommentAttributeEntity> attributes = new HashSet<CommentAttributeEntity>();

    public CommentEntity() {
        super();
    }

    public CommentEntity(Comment comment) {
        super(comment);
        setId(comment.getId());
        setTypeKey(comment.getTypeKey());
        setRefObjectTypeKey(comment.getReferenceTypeKey());
        setRefObjectId(comment.getReferenceId());
        fromDto(comment);
    }

    public String getTypeKey() {
        return typeKey;
    }

    public void setTypeKey(String typeKey) {
        this.typeKey = typeKey;
    }

    public String getStateKey() {
        return stateKey;
    }

    public void setStateKey(String stateKey) {
        this.stateKey = stateKey;
    }

    public String getCommentPlain() {
        return commentPlain;
    }

    public void setCommentPlain(String commentPlain) {
        this.commentPlain = commentPlain;
    }

    public String getCommentFormatted() {
        return commentFormatted;
    }

    public void setCommentFormatted(String commentFormatted) {
        this.commentFormatted = commentFormatted;
    }

    public String getCommenterId() {
        return commenterId;
    }

    public void setCommenterId(String commenterId) {
        this.commenterId = commenterId;
    }

    public String getRefObjectTypeKey() {
        return refObjectTypeKey;
    }

    public void setRefObjectTypeKey(String refObjectTypeKey) {
        this.refObjectTypeKey = refObjectTypeKey;
    }

    public String getRefObjectId() {
        return refObjectId;
    }

    public void setRefObjectId(String refObjectId) {
        this.refObjectId = refObjectId;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public void setAttributes(Set<CommentAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Set<CommentAttributeEntity> getAttributes() {
        return attributes;
    }

    public void fromDto(Comment comment) {
        setStateKey(comment.getStateKey());
        if(null != comment.getCommentText()) {
            setCommentFormatted(comment.getCommentText().getFormatted());
            setCommentPlain(comment.getCommentText().getPlain());
        }
        setCommenterId(comment.getCommenterId());
        setEffectiveDate(comment.getEffectiveDate());
        setExpirationDate(comment.getExpirationDate());

        attributes.clear();
        for (Attribute att : comment.getAttributes()) {
            CommentAttributeEntity attEntity = new CommentAttributeEntity(att, this);
            getAttributes().add(attEntity);
        }
    }

    public CommentInfo toDto() {
        CommentInfo comment = new CommentInfo();

        comment.setId(getId());
        comment.setStateKey(getStateKey());
        comment.setTypeKey(getTypeKey());
        comment.setCommentText(RichTextHelper.buildRichTextInfo(getCommentPlain(), getCommentFormatted()));
        comment.setCommenterId(getCommenterId());
        comment.setReferenceTypeKey(getRefObjectTypeKey());
        comment.setReferenceId(getRefObjectId());
        comment.setEffectiveDate(getEffectiveDate());
        comment.setExpirationDate(getExpirationDate());

        comment.setMeta(super.toDTO());

        for (CommentAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            comment.getAttributes().add(attInfo);
        }

        return comment;
    }
}
