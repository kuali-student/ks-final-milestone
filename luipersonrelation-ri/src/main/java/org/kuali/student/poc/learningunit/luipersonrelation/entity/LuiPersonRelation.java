package org.kuali.student.poc.learningunit.luipersonrelation.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.poc.common.util.UUIDHelper;

@Entity
public class LuiPersonRelation {

    @Id
    private String id;
    
    private String luiId;
    private String personId;
    @ManyToOne
    private LuiPersonRelationType luiPersonRelationType;
    @ManyToOne
    private RelationState relationState;
    @Temporal(TemporalType.TIMESTAMP)
    private Date effectiveStartDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date effectiveEndDate;
    
    /**
     * AutoGenerate the Id
     */
    @PrePersist
    public void prePersist() {
        this.id = UUIDHelper.genStringUUID();
    }

    public RelationState getRelationState() {
        return relationState;
    }

    public void setRelationState(RelationState relationState) {
        this.relationState = relationState;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLuiId() {
        return luiId;
    }

    public void setLuiId(String luiId) {
        this.luiId = luiId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public LuiPersonRelationType getLuiPersonRelationType() {
        return luiPersonRelationType;
    }

    public void setLuiPersonRelationType(LuiPersonRelationType luiPersonRelationType) {
        this.luiPersonRelationType = luiPersonRelationType;
    }

    public Date getEffectiveStartDate() {
        return effectiveStartDate;
    }

    public void setEffectiveStartDate(Date effectiveStartDate) {
        this.effectiveStartDate = effectiveStartDate;
    }

    public Date getEffectiveEndDate() {
        return effectiveEndDate;
    }

    public void setEffectiveEndDate(Date effectiveEndDate) {
        this.effectiveEndDate = effectiveEndDate;
    }

}
