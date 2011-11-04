package org.kuali.student.enrollment.class1.lpr.model;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "KSEN_LPR_ATTR")
public class LprRosterEntryAttributeEntity extends BaseAttributeEntity<LprRosterEntryEntity> {

    @ManyToOne
    @JoinColumn(name = "OWNER")
    private LprRosterEntryEntity owner;

    public LprRosterEntryAttributeEntity() {}

    public LprRosterEntryAttributeEntity(String key, String value) {
        super(key, value);
    }

    public LprRosterEntryAttributeEntity(Attribute att) {
        super(att);
    }

    @Override
    public void setOwner(LprRosterEntryEntity owner) {
        this.owner = owner;
    }

    @Override
    public LprRosterEntryEntity getOwner() {
        return owner;
    }
}
