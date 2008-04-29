package org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.poc.xsd.learningunit.lu.dto.LuiDisplay;
import org.kuali.student.poc.xsd.personidentity.person.dto.PersonDisplay;

@XmlAccessorType(XmlAccessType.FIELD)
public class LuiPersonRelationCriteria implements Serializable {

    private static final long serialVersionUID = -2541872844557390290L;
    @XmlElement
    private String luiId;
    @XmlElement
    private String personId;
    @XmlElement
    private LuiPersonRelationTypeInfo luiPersonRelationType;
    @XmlElement
    private RelationStateInfo relationState;
    @XmlElement
    private Date effectiveStartDate;
    @XmlElement
    private Date effectiveEndDate;
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
