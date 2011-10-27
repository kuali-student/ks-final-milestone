package org.kuali.student.enrollment.class1.lpr.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.enrollment.class1.lpr.model.LprRosterEntity;
import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_LPR_ATTR")
public class LprRosterAttributeEntity extends BaseAttributeEntity<LprRosterEntity> {

    @ManyToOne
    @JoinColumn(name = "OWNER")
    private LprRosterEntity owner;

    public LprRosterAttributeEntity () {
    }

    public LprRosterAttributeEntity(String key, String value) {
        super(key, value);
    }

    public LprRosterAttributeEntity(Attribute att) {
        super(att);
    }

    @Override
    public void setOwner(LprRosterEntity owner) {
        this.owner = owner;
    }

    @Override
    public LprRosterEntity getOwner() {
        return owner;
    }
}
