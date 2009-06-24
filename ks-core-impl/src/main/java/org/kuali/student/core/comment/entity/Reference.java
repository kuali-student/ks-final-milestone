package org.kuali.student.core.comment.entity;


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
import javax.persistence.UniqueConstraint;

import org.kuali.student.common.util.UUIDHelper;



@Entity
@Table(name = "KSCO_REFERENCE",
        uniqueConstraints= @UniqueConstraint(columnNames={"REFERENCE_ID", "REFERENCE_TYPE"}))
@NamedQueries( {
        @NamedQuery(name = "Reference.getReference", query = "SELECT reference FROM Reference reference WHERE reference.referenceId =:refId AND reference.referenceType.id=:refTypeId")})
public class Reference  {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name="REFERENCE_ID")
    private String referenceId;

    @ManyToOne
    @JoinColumn(name = "REFERENCE_TYPE")
    private ReferenceType referenceType;


    @OneToMany(cascade = CascadeType.ALL, mappedBy="reference")
    private List<Tag> tags;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="reference")
    private List<Comment> comments;

    @PrePersist
    public void prePersist() {
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
