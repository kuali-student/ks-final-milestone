package org.kuali.student.enrollment.class1.lrc.model;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "KSEN_LRC_RES_SCALE_ATTR")
public class ResultScaleAttributeEntity extends BaseAttributeEntity<ResultScaleEntity> {

    @ManyToOne
    @JoinColumn(name = "OWNER")
    private ResultScaleEntity owner;

    public ResultScaleAttributeEntity() {}

    public ResultScaleAttributeEntity(String key, String value) {
        super(key, value);
    }

    public ResultScaleAttributeEntity(Attribute att) {
        super(att);
    }

    @Override
    public void setOwner(ResultScaleEntity owner) {
        this.owner = owner;
    }

    @Override
    public ResultScaleEntity getOwner() {
        return owner;
    }
}
