package org.kuali.student.r2.core.organization.service.impl.model;


import org.kuali.student.r2.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.r2.core.organization.infc.OrgHierarchy;
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
@Table(name = "")
@NamedQueries({
        @NamedQuery(name = "OrgHierarchyEntity.getIdsByType",
                query = "select id from OrgHierarchyEntity where type = :type")
})
public class OrgHierarchyEntity extends MetaEntity implements AttributeOwner<OrgHierarchyAttributeEntity> {

    @Column(name = "TYPE")
    private String type;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER, orphanRemoval = true)
    private final Set<OrgHierarchyAttributeEntity> attributes = new HashSet<OrgHierarchyAttributeEntity>();

    public OrgHierarchyEntity() {

    }

    public OrgHierarchyEntity(OrgHierarchy orgHierarchy) {
    //BOOKMARK TODO
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public Set<OrgHierarchyAttributeEntity> getAttributes() {
        return attributes;
    }

    @Override
    public void setAttributes(Set<OrgHierarchyAttributeEntity> attributes) {
        this.attributes.clear();
        if (attributes != null) {
            this.attributes.addAll(attributes);
        }
    }

    public void fromDto(OrgHierarchy orgHierarchy) {
        //BOOKMARK TODO
    }

    public OrgHierarchyInfo toDto() {
        //BOOKMARK TODO
        return null;
    }

}
