package org.kuali.student.poc.xsd.learningunit.luipersonrelation.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class LuiPersonRelationUpdateInfo implements Serializable {

    private static final long serialVersionUID = -2767988735753860338L;
    @XmlElement
    private RelationStateInfo relationState;
    @XmlElement
    private LuiPersonRelationTypeInfo luiPersonRelationType;
    @XmlElement
    private Date effectiveStartDate;
    @XmlElement
    private Date effectiveEndDate;
    public RelationStateInfo getRelationState() {
        return relationState;
    }
    public void setRelationState(RelationStateInfo relationState) {
        this.relationState = relationState;
    }
    public LuiPersonRelationTypeInfo getLuiPersonRelationType() {
        return luiPersonRelationType;
    }
    public void setLuiPersonRelationType(LuiPersonRelationTypeInfo luiPersonRelationType) {
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
