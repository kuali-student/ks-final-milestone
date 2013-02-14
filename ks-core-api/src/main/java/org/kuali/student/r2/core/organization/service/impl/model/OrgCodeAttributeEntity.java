package org.kuali.student.r2.core.organization.service.impl.model;

import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.core.organization.service.impl.lib.BaseAttributeEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "KSEN_ORG_CD_ATTR")
public class OrgCodeAttributeEntity extends BaseAttributeEntity<OrgCodeEntity> {

    public OrgCodeAttributeEntity() {
        super();
    }

    public OrgCodeAttributeEntity(Attribute att, OrgCodeEntity owner) {
        super(att, owner);
    }
}