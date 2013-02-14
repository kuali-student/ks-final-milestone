package org.kuali.student.r2.core.organization.service.impl.model;

import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.core.organization.service.impl.lib.BaseAttributeEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "")
public class OrgPositionRestrictionAttributeEntity extends BaseAttributeEntity<OrgPositionRestrictionEntity> {
    public OrgPositionRestrictionAttributeEntity() {
        super();
    }

    public OrgPositionRestrictionAttributeEntity(Attribute att, OrgPositionRestrictionEntity owner) {
        super(att, owner);
    }
}
