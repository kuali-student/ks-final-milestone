package org.kuali.student.r2.core.organization.service.impl.model;


import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.infc.Org;
import org.kuali.student.r2.core.organization.infc.OrgCode;
import org.kuali.student.r2.core.organization.service.impl.lib.AttributeOwner;
import org.kuali.student.r2.core.organization.service.impl.lib.MetaEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="KSEN_ORG_CD")
@NamedQueries({
        @NamedQuery(name = "OrgCodeEntity.getIdsByType",
                query = "select id from OrgCodeEntity where type = :type")
})
public class OrgCodeEntity extends MetaEntity implements AttributeOwner<OrgCodeAttributeEntity> {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER, orphanRemoval = true)
    private final Set<OrgCodeAttributeEntity> attributes = new HashSet<OrgCodeAttributeEntity>();

    public  OrgCodeEntity() {
    }

    public OrgCodeEntity(OrgCode orgCode) {

    }

    @Override
    public Set<OrgCodeAttributeEntity> getAttributes() {
        return attributes;
    }

    @Override
    public void setAttributes(Set<OrgCodeAttributeEntity> attributes) {
        this.attributes.clear();
        if (attributes != null) {
            this.attributes.addAll(attributes);
        }
    }

    public void fromDto(Org org) {

    }

    public OrgInfo toDto() {
        OrgInfo info = new OrgInfo();

        return info;
    }
}
