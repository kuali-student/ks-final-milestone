package org.kuali.student.poc.learningunit.luipersonrelation.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import org.kuali.student.poc.common.util.UUIDHelper;

@Entity
public class RelationState {
    @Id
    private String id;
    
    @OneToMany(mappedBy="luiPersonRelationType")
    private Set<LuiPersonRelation> luiPersonRelations;
    @ManyToMany
    private Set<LuiPersonRelationType> luiPersonRelationTypes;
    @ManyToMany
    private Set<LuTypeShadow> luTypes;

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

    public Set<LuiPersonRelation> getLuiPersonRelations() {
        return luiPersonRelations;
    }

    public void setLuiPersonRelations(Set<LuiPersonRelation> luiPersonRelations) {
        this.luiPersonRelations = luiPersonRelations;
    }

    public Set<LuiPersonRelationType> getLuiPersonRelationTypes() {
        return luiPersonRelationTypes;
    }

    public void setLuiPersonRelationTypes(Set<LuiPersonRelationType> luiPersonRelationTypes) {
        this.luiPersonRelationTypes = luiPersonRelationTypes;
    }

    public Set<LuTypeShadow> getLuTypes() {
        return luTypes;
    }

    public void setLuTypes(Set<LuTypeShadow> luTypes) {
        this.luTypes = luTypes;
    }

}
