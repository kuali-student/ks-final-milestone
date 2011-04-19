package org.kuali.student.enrollment.lpr.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Igor
 */
@Entity
public class LuiPersonRelation implements Serializable {
    private static final long serialVersionUID = -8711908979901134064L;

    @Id
    @GeneratedValue
    private Long id;

    private String personId;

    private String luiId;

    @Temporal(TemporalType.DATE)
    private Date effectiveDate;

    @Temporal(TemporalType.DATE)
    private Date expirationDate;

    @ManyToOne(cascade = CascadeType.ALL)
    private LuiPersonRelationType personRelationType;

    @ManyToOne(cascade = CascadeType.ALL)
    private LuiPersonRelationState personRelationState;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "lui_person_relation_id")
    private List<DynamicAttribute> dynamicAttributes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getLuiId() {
        return luiId;
    }

    public void setLuiId(String luiId) {
        this.luiId = luiId;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LuiPersonRelationType getPersonRelationType() {
        return personRelationType;
    }

    public void setPersonRelationType(LuiPersonRelationType personRelationType) {
        this.personRelationType = personRelationType;
    }

    public LuiPersonRelationState getPersonRelationState() {
        return personRelationState;
    }

    public void setPersonRelationState(LuiPersonRelationState personRelationState) {
        this.personRelationState = personRelationState;
    }

    public List<DynamicAttribute> getDynamicAttributes() {
        return dynamicAttributes;
    }

    public void setDynamicAttributes(List<DynamicAttribute> dynamicAttributes) {
        this.dynamicAttributes = dynamicAttributes;
    }
}
