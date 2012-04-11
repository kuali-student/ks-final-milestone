package org.kuali.student.enrollment.class1.lui.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.BaseAttributeEntity;
import org.kuali.student.r2.common.infc.Attribute;

@Entity
@Table(name = "KSEN_LUI_AFFIL_ORG_ATTR")
public class LuiAffiliatedOrgAttributeEntity extends BaseAttributeEntity<LuiAffiliatedOrgEntity> {
    
    @ManyToOne
    @JoinColumn(name = "OWNER")
    private LuiAffiliatedOrgEntity owner;

    public LuiAffiliatedOrgAttributeEntity () {
    }
    
    public LuiAffiliatedOrgAttributeEntity(String key, String value) {
        super(key, value);
    }

    public LuiAffiliatedOrgAttributeEntity(Attribute att) {
        super(att);
    }

    @Override
    public void setOwner(LuiAffiliatedOrgEntity owner) {
        this.owner = owner;
    }

    @Override
    public LuiAffiliatedOrgEntity getOwner() {
        return owner;
    }
}
