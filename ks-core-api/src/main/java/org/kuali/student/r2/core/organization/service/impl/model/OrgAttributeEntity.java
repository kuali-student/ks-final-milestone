package org.kuali.student.r2.core.organization.service.impl.model;


import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.core.organization.service.impl.lib.BaseAttributeEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "KSEN_ORG_ATTR")
public class OrgAttributeEntity extends BaseAttributeEntity<OrgEntity> {

    public OrgAttributeEntity() {
        super();
    }

    public OrgAttributeEntity(Attribute att, OrgEntity owner) {
        super(att, owner);
    }
}
