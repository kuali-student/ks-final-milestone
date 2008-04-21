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

    private static final long serialVersionUID = 1L;
    @XmlElement
    private LuiDisplay luiDisplay;
    @XmlElement
    private PersonDisplay personDisplay;
    @XmlElement
    private LuiPersonRelationType luiPersonRelationType;
    @XmlElement
    private RelationState relationState;
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
    public LuiPersonRelationType getLuiPersonRelationType() {
        return luiPersonRelationType;
    }
    public void setLuiPersonRelationType(LuiPersonRelationType luiPersonRelationType) {
        this.luiPersonRelationType = luiPersonRelationType;
    }
    public RelationState getRelationState() {
        return relationState;
    }
    public void setRelationState(RelationState relationState) {
        this.relationState = relationState;
    }
    public String getLuiPersonRelationId() {
        return luiPersonRelationId;
    }
    public void setLuiPersonRelationId(String luiPersonRelationId) {
        this.luiPersonRelationId = luiPersonRelationId;
    }
    
}
