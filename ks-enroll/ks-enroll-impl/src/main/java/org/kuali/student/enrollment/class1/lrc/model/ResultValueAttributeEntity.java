package org.kuali.student.enrollment.class1.lrc.model;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "KSEN_LRC_RES_VALUE_ATTR")
public class ResultValueAttributeEntity  extends BaseAttributeEntity<ResultValueEntity> {

    @ManyToOne
    @JoinColumn(name = "OWNER")
    private ResultValueEntity owner;

    public ResultValueAttributeEntity() {}

    public ResultValueAttributeEntity(String key, String value) {
        super(key, value);
    }

    public ResultValueAttributeEntity(Attribute att) {
        super(att);
    }

    @Override
    public void setOwner(ResultValueEntity owner) {
        this.owner = owner;
    }

    @Override
    public ResultValueEntity getOwner() {
        return owner;
    }
}
