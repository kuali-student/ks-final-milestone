package org.kuali.student.enrollment.class1.roster.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_LPR_ROSTER_ENTRY_ATTR")
public class LprRosterEntryAttributeEntity extends BaseAttributeEntity<LprRosterEntryEntity> {

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
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
