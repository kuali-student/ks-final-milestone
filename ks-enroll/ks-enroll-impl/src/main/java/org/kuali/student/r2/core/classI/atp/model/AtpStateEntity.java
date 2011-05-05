package org.kuali.student.r2.core.classI.atp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.MetaEntity;

@Entity
@Table(name = "KSEN_ATP_STATE")
public class AtpStateEntity extends MetaEntity{
    private String name;

    @Column(name="DESCR")
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
