package org.kuali.student.poc.learningunit.luipersonrelation.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;

import org.kuali.student.poc.common.util.UUIDHelper;

@Entity
public class LuTypeShadow {
    @Id
    private String id;
    private String luTypeId;
    @ManyToMany(mappedBy="luTypes")
    private Set<LuiPersonRelationType> luiPersonRelationTypes;
    @ManyToMany(mappedBy="luTypes")
    private Set<RelationState> relationStates;

    /**
     * AutoGenerate the Id
     */
    @PrePersist
    public void prePersist() {
        this.id = UUIDHelper.genStringUUID();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLuTypeId() {
        return luTypeId;
    }

    public void setLuTypeId(String luTypeId) {
        this.luTypeId = luTypeId;
    }

    public Set<LuiPersonRelationType> getLuiPersonRelationTypes() {
        return luiPersonRelationTypes;
    }

    public void setLuiPersonRelationTypes(Set<LuiPersonRelationType> luiPersonRelationTypes) {
        this.luiPersonRelationTypes = luiPersonRelationTypes;
    }

    public Set<RelationState> getRelationStates() {
        return relationStates;
    }

    public void setRelationStates(Set<RelationState> relationStates) {
        this.relationStates = relationStates;
    }
    
}
