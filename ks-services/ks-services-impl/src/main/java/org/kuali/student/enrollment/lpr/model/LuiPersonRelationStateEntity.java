package org.kuali.student.enrollment.lpr.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;

/**
 * @author Igor
 */
@Entity
public class LuiPersonRelationStateEntity extends MetaEntity implements AttributeOwner<LuiPersonRelationAttributeEntity> {
	
    private String name;

    private String description;

    @Temporal(TemporalType.DATE)
    private Date effectiveDate;

    @Temporal(TemporalType.DATE)
    private Date expirationDate;

    @OneToMany(cascade = CascadeType.ALL)
    // @JoinColumn(name = "person_relation_state_id")
//    @JoinTable(name="LPR_ATTR_JOIN",
//    			joinColumns=@JoinColumn(name="OWNER_ID", referencedColumnName="ID"),
//    			inverseJoinColumns=@JoinColumn(name="ATTRIB_ID", referencedColumnName="ID"))
    private List<LuiPersonRelationAttributeEntity> attributes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public List<LuiPersonRelationAttributeEntity> getAttributes() {
        return attributes;
    }

    @Override
    public void setAttributes(List<LuiPersonRelationAttributeEntity> attributes) {
        this.attributes = attributes;
    }

}
