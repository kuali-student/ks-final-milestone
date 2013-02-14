package org.kuali.student.r2.core.organization.service.impl.model;

import org.kuali.student.r2.core.organization.dto.OrgPersonRelationInfo;
import org.kuali.student.r2.core.organization.infc.OrgPersonRelation;
import org.kuali.student.r2.core.organization.service.impl.lib.AttributeOwner;
import org.kuali.student.r2.core.organization.service.impl.lib.MetaEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="KSEN_ORG_PERS_RELTN")
@NamedQueries({
        @NamedQuery(name = "OrgPersonRelationEntity.getIdsByType",
                query = "select id from OrgPersonRelationEntity where type = :type")
})
public class OrgPersonRelationEntity extends MetaEntity implements AttributeOwner<OrgPersonRelationAttributeEntity> {
    @Column(name = "TYPE")
    private String type;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER, orphanRemoval = true)
    private final Set<OrgPersonRelationAttributeEntity> attributes = new HashSet<OrgPersonRelationAttributeEntity>();

    public  OrgPersonRelationEntity() { }

    public OrgPersonRelationEntity(OrgPersonRelation orgPersonRelation) {
        //BOOKMARK TODO
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public Set<OrgPersonRelationAttributeEntity> getAttributes() {
        return attributes;
    }

    @Override
    public void setAttributes(Set<OrgPersonRelationAttributeEntity> attributes) {
        this.attributes.clear();
        if (attributes != null) {
            this.attributes.addAll(attributes);
        }
    }

    public void fromDto(OrgPersonRelation orgPersonRelation) {
        //BOOKMARK TODO
    }

    public OrgPersonRelationInfo toDto() {
        //BOOKMARK TODO
        return null;
    }
}
