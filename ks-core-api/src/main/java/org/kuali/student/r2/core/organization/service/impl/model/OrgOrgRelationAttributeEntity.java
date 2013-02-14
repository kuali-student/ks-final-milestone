package org.kuali.student.r2.core.organization.service.impl.model;


import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.core.organization.service.impl.lib.BaseAttributeEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "")
public class OrgOrgRelationAttributeEntity extends BaseAttributeEntity<OrgOrgRelationEntity> {
    public OrgOrgRelationAttributeEntity() {
        super();
    }

    public OrgOrgRelationAttributeEntity(Attribute att, OrgOrgRelationEntity owner) {
        super(att, owner);
    }
}
