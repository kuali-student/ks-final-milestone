package org.kuali.student.r2.core.class1.atp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.TypeEntity;

@Entity
@Table(name = "KSEN_ATP_TYPE")
public class AtpTypeEntity extends TypeEntity<AtpAttributeEntity>{

    @Column(name="REF_OBJECT_URI")
    private String refObjectURI;

    public void setRefObjectURI(String refObjectURI) {
        this.refObjectURI = refObjectURI;
    }

    public String getRefObjectURI() {
        return refObjectURI;
    }
}
