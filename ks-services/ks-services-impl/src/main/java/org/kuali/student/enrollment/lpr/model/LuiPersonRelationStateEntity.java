package org.kuali.student.enrollment.lpr.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Igor
 */
@Entity
public class LuiPersonRelationStateEntity {
	
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    @Temporal(TemporalType.DATE)
    private Date effectiveDate;

    @Temporal(TemporalType.DATE)
    private Date expirationDate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_relation_state_id")
    private List<LuiPersonRelationAttributeEntity> attributes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<LuiPersonRelationAttributeEntity> getAttributes() {
        return attributes;
    }

    public void setDynamicAttributes(List<LuiPersonRelationAttributeEntity> attributes) {
        this.attributes = attributes;
    }
}
