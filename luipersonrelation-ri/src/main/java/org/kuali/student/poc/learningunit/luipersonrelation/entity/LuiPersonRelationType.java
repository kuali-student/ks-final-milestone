package org.kuali.student.poc.learningunit.luipersonrelation.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import org.kuali.student.poc.common.util.UUIDHelper;

@Entity
public class LuiPersonRelationType {
    @Id
    private String id;
    
    String name;
    @OneToMany(mappedBy="luiPersonRelationType")
    private Set<LuiPersonRelation> luiPersonRelations;
    @ManyToMany(mappedBy="luiPersonRelationTypes")
    private Set<RelationState> relationStates;
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

    public Set<RelationState> getRelationStates() {
        return relationStates;
    }

    public void setRelationStates(Set<RelationState> relationStates) {
        this.relationStates = relationStates;
    }

    public Set<LuTypeShadow> getLuTypes() {
        return luTypes;
    }

    public void setLuTypes(Set<LuTypeShadow> luTypes) {
        this.luTypes = luTypes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
