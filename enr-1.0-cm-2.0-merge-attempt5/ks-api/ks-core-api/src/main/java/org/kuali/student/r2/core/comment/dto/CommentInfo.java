/*
 * Copyright 2011 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.core.comment.dto;

import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.core.comment.infc.Comment;
//import org.w3c.dom.Element;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Refer to interface javadoc
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CommentInfo", propOrder = {"id", "typeKey", "stateKey",
                "commentText", "commenterId", "referenceTypeKey", 
                "referenceId", "effectiveDate",
                "expirationDate", "meta", "attributes", "_futureElements" }) 

public class CommentInfo 
    extends IdNamelessEntityInfo 
    implements Comment, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private RichTextInfo commentText;

    @XmlElement
    private String commenterId;

    @XmlElement
    private String referenceTypeKey;

    @XmlElement
    private String referenceId;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

    @XmlAnyElement
    private List<Object> _futureElements;  

    public CommentInfo() {
    }

    public CommentInfo(Comment comment) {
        super(comment);

        if (null != comment) {
            this.commentText = (null != comment.getCommentText()) ? new RichTextInfo(comment.getCommentText()) : null;
            this.commenterId = comment.getCommenterId();
            this.referenceTypeKey = comment.getReferenceTypeKey();
            this.referenceId = comment.getReferenceId();
            this.effectiveDate = (null != comment.getEffectiveDate()) ? new Date(comment.getEffectiveDate().getTime()) : null;
            this.expirationDate = (null != comment.getExpirationDate()) ? new Date(comment.getExpirationDate().getTime()) : null;
        }
    }

    @Override
    public RichTextInfo getCommentText() {
        return commentText;
    }

    public void setCommentText(RichTextInfo commentText) {
        this.commentText = commentText;
    }

    @Override
    public String getCommenterId() {
        return this.commenterId;
    }

    public void setCommenterId(String commenterId) {
        this.commenterId = commenterId;
    }

    @Override
    public String getReferenceTypeKey() {
        return referenceTypeKey;
    }

    public void setReferenceTypeKey(String referenceTypeKey) {
        this.referenceTypeKey = referenceTypeKey;
    }

    @Override
    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    @Override
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
