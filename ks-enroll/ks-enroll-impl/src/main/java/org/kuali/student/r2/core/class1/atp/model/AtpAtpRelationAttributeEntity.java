package org.kuali.student.r2.core.class1.atp.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_ATPATP_RELTN_ATTR")
public class AtpAtpRelationAttributeEntity extends BaseAttributeEntity<AtpAtpRelationEntity> {

    @ManyToOne
    @JoinColumn(name = "OWNER")
    private AtpAtpRelationEntity owner;

    public AtpAtpRelationAttributeEntity() {}

    public AtpAtpRelationAttributeEntity(Attribute att) {
        super(att);
    }

    public AtpAtpRelationAttributeEntity(String key, String value) {
        super(key, value);
    }

    @Override
    public void setOwner(AtpAtpRelationEntity owner) {
        this.owner = owner;

    }

    @Override
    public AtpAtpRelationEntity getOwner() {
        return owner;
    }
}
