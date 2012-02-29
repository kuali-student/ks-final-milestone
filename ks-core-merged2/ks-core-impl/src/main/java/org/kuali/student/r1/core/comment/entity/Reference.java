/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.r1.core.comment.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.kuali.student.r1.common.entity.BaseEntity;

@Deprecated
@Entity
@Table(name = "KSCO_REFERENCE",
        uniqueConstraints= @UniqueConstraint(columnNames={"REFERENCE_ID", "REFERENCE_TYPE"}))
@NamedQueries( {
        @NamedQuery(name = "Reference.getReference", query = "SELECT reference FROM Reference reference WHERE reference.referenceId =:refId AND reference.referenceType.id=:refTypeId")})
public class Reference extends BaseEntity {
    @Column(name="REFERENCE_ID")
    private String referenceId;

    @ManyToOne
    @JoinColumn(name = "REFERENCE_TYPE")
    private ReferenceType referenceType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="reference")
    private List<Tag> tags;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="reference")
    private List<Comment> comments;

    public String getReferenceId(){
        return referenceId;
    }

    public void setReferenceId(String referenceId){
        this.referenceId=referenceId;
    }

    public ReferenceType getReferenceType(){
        return referenceType;
    }

    public void setReferenceType(ReferenceType referenceType){
        this.referenceType=referenceType;
    }

    public List<Tag> getTags(){
        return tags;
    }

    public void setTags(List<Tag> tags){
        this.tags=tags;
    }

    public List<Comment> getCommentss(){
        return comments;
    }

    public void setComments(List<Comment> comments){
        this.comments=comments;
    }
}
