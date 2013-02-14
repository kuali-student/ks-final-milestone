package org.kuali.student.r2.core.organization.service.impl.model;

import org.kuali.student.r2.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.r2.core.organization.infc.OrgOrgRelation;
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
@Table(name="")
@NamedQueries({
        @NamedQuery(name = "OrgOrgRelationEntity.getIdsByType",
                query = "select id from OrgOrgRelationEntity where type = :type")
})
public class OrgOrgRelationEntity extends MetaEntity implements AttributeOwner<OrgOrgRelationAttributeEntity> {
    @Column(name = "TYPE")
    private String type;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER, orphanRemoval = true)
    private final Set<OrgOrgRelationAttributeEntity> attributes = new HashSet<OrgOrgRelationAttributeEntity>();

    public  OrgOrgRelationEntity() {

    }

    public OrgOrgRelationEntity(OrgOrgRelation orgOrgRelation) {
        //BOOKMARK TODO
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public Set<OrgOrgRelationAttributeEntity> getAttributes() {
        return attributes;
    }

    @Override
    public void setAttributes(Set<OrgOrgRelationAttributeEntity> attributes) {
        this.attributes.clear();
        if (attributes != null) {
            this.attributes.addAll(attributes);
        }
    }

    public void fromDto(OrgOrgRelation orgOrgRelation) {
        //BOOKMARK TODO
    }

    public OrgOrgRelationInfo toDto() {
        //BOOKMARK TODO
        return null;
    }
}
