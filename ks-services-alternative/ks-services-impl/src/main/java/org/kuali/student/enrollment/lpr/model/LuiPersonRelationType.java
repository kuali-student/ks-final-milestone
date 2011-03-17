package org.kuali.student.enrollment.lpr.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Igor
 */
@Entity
public class LuiPersonRelationType implements Serializable {
    private static final long serialVersionUID = 5014644991006774962L;

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
    @JoinColumn(name = "person_relation_type_id")
    private List<DynamicAttribute> dynamicAttributes;

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

    public List<DynamicAttribute> getDynamicAttributes() {
        return dynamicAttributes;
    }

    public void setDynamicAttributes(List<DynamicAttribute> dynamicAttributes) {
        this.dynamicAttributes = dynamicAttributes;
    }
}
