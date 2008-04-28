package org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiDisplay;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonDisplay;

@XmlAccessorType(XmlAccessType.FIELD)
public class LuiPersonRelationDisplay implements Serializable {

    private static final long serialVersionUID = 1575015530864361975L;
    @XmlElement
    private LuiDisplay luiDisplay;
    @XmlElement
    private PersonDisplay personDisplay;
    @XmlElement
    private LuiPersonRelationTypeInfo luiPersonRelationType;
    @XmlElement
    private RelationStateInfo relationState;
    @XmlAttribute
    private String luiPersonRelationId;
    public LuiDisplay getLuiDisplay() {
        return luiDisplay;
    }
    public void setLuiDisplay(LuiDisplay luiDisplay) {
        this.luiDisplay = luiDisplay;
    }
    public PersonDisplay getPersonDisplay() {
        return personDisplay;
    }
    public void setPersonDisplay(PersonDisplay personDisplay) {
        this.personDisplay = personDisplay;
    }
    public LuiPersonRelationTypeInfo getLuiPersonRelationType() {
        return luiPersonRelationType;
    }
    public void setLuiPersonRelationType(LuiPersonRelationTypeInfo luiPersonRelationType) {
        this.luiPersonRelationType = luiPersonRelationType;
    }
    public RelationStateInfo getRelationState() {
        return relationState;
    }
    public void setRelationState(RelationStateInfo relationState) {
        this.relationState = relationState;
    }
    public String getLuiPersonRelationId() {
        return luiPersonRelationId;
    }
    public void setLuiPersonRelationId(String luiPersonRelationId) {
        this.luiPersonRelationId = luiPersonRelationId;
    }
    
}
