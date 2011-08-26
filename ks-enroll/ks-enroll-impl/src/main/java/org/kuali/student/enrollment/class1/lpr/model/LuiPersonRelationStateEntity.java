package org.kuali.student.enrollment.class1.lpr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.MetaEntity;

/**
 * @author Igor
 */
@Entity
@Table(name = "KSEN_LPR_STATE")
public class LuiPersonRelationStateEntity extends MetaEntity {

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCR")
    private String description;

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

}
