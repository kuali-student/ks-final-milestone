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
    private RelationState relationState;
    @XmlElement
    private LuiPersonRelationType luiPersonRelationType;
    @XmlElement
    private Date effectiveStartDate;
    @XmlElement
    private Date effectiveEndDate;
    public RelationState getRelationState() {
        return relationState;
    }
    public void setRelationState(RelationState relationState) {
        this.relationState = relationState;
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
