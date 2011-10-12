package org.kuali.student.enrollment.class1.lrr.model;

import org.kuali.student.r2.common.entity.TypeEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "KSEN_LRR_TYPE")
public class ResultSourceTypeEntity extends TypeEntity<ResultSourceAttributeEntity> {

    @Column(name = "REF_OBJECT_URI")
    private String refObjectURI;

    public void setRefObjectURI(String refObjectURI) {
        this.refObjectURI = refObjectURI;
    }

    public String getRefObjectURI() {
        return refObjectURI;
    }

}
