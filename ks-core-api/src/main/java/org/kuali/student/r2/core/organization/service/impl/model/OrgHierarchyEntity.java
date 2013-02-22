package org.kuali.student.r2.core.organization.service.impl.model;


import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.r2.core.organization.infc.OrgHierarchy;
import org.kuali.student.r2.core.organization.service.impl.lib.AttributeOwner;
import org.kuali.student.r2.core.organization.service.impl.lib.KSEntityConstants;
import org.kuali.student.r2.core.organization.service.impl.lib.MetaEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "KSEN_ORG_HIRCHY")
@NamedQueries({
        @NamedQuery(name = "OrgHierarchyEntity.getIdsByType",
                query = "select id from OrgHierarchyEntity where orgHierarchyType = :type")
})
public class OrgHierarchyEntity extends MetaEntity implements AttributeOwner<OrgHierarchyAttributeEntity> {

    @Column(name = "ORG_HIRCHY_TYPE", nullable = false)
    private String orgHierarchyType;
    @Column(name = "ORG_HIRCHY_STATE", nullable = false)
    private String orgHierarchyState;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrPlain;
    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String descrFormatted;
    @Column(name = "ROOT_ORG_ID", nullable = false)
    private String rootOrgId;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="EXPIR_DT", nullable = false)
    private Date expirationDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="EFF_DT", nullable = false)
    private Date effectiveDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER, orphanRemoval = true)
    private final Set<OrgHierarchyAttributeEntity> attributes = new HashSet<OrgHierarchyAttributeEntity>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinTable(name = "KSEN_ORG_HIRCHY_OORT",
               joinColumns = @JoinColumn(name = "ORG_HIRCHY_ID"))
    private Set<String> orgOrgRelationTypes = new HashSet<String>();

    public OrgHierarchyEntity() {
    }

    public OrgHierarchyEntity(OrgHierarchy orgHierarchy) {
        super(orgHierarchy);
        setId(orgHierarchy.getId());
        setOrgHierarchyType(orgHierarchy.getTypeKey());
        fromDto(orgHierarchy);
    }

    public String getOrgHierarchyType() {
        return orgHierarchyType;
    }

    public void setOrgHierarchyType(String orgHierarchyType) {
        this.orgHierarchyType = orgHierarchyType;
    }

    public String getOrgHierarchyState() {
        return orgHierarchyState;
    }

    public void setOrgHierarchyState(String orgHierarchyState) {
        this.orgHierarchyState = orgHierarchyState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescrPlain() {
        return descrPlain;
    }

    public void setDescrPlain(String descrPlain) {
        this.descrPlain = descrPlain;
    }

    public String getDescrFormatted() {
        return descrFormatted;
    }

    public void setDescrFormatted(String descrFormatted) {
        this.descrFormatted = descrFormatted;
    }

    public String getRootOrgId() {
        return rootOrgId;
    }

    public void setRootOrgId(String rootOrgId) {
        this.rootOrgId = rootOrgId;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Set<String> getOrgOrgRelationTypes() {
        return orgOrgRelationTypes;
    }

    public void setOrgOrgRelationTypes(Set<String> orgOrgRelationTypes) {
        this.orgOrgRelationTypes = orgOrgRelationTypes;
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
        setOrgHierarchyState(orgHierarchy.getStateKey());
        setName(orgHierarchy.getName());
        if(orgHierarchy.getDescr() != null) {
            setDescrPlain(orgHierarchy.getDescr().getPlain());
            setDescrFormatted(orgHierarchy.getDescr().getFormatted());
        }
        setRootOrgId(orgHierarchy.getRootOrgId());
        setExpirationDate(orgHierarchy.getExpirationDate());
        setEffectiveDate(orgHierarchy.getEffectiveDate());

        attributes.clear();
        for (Attribute att : orgHierarchy.getAttributes()) {
            OrgHierarchyAttributeEntity attEntity = new OrgHierarchyAttributeEntity(att, this);
            this.getAttributes().add(attEntity);
        }

        orgOrgRelationTypes.clear();
        orgOrgRelationTypes.addAll(orgHierarchy.getOrgOrgRelationTypes());
    }

    public OrgHierarchyInfo toDto() {
        OrgHierarchyInfo info = new OrgHierarchyInfo();
        info.setId(getId());
        info.setTypeKey(orgHierarchyType);
        info.setStateKey(orgHierarchyState);
        info.setName(name);
        info.setDescr(RichTextHelper.toRichTextInfo(descrPlain, descrFormatted));
        info.setRootOrgId(rootOrgId);
        info.setExpirationDate(expirationDate);
        info.setEffectiveDate(effectiveDate);
        info.setMeta(super.toDTO());

        for (OrgHierarchyAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            info.getAttributes().add(attInfo);
        }

        info.getOrgOrgRelationTypes().addAll(orgOrgRelationTypes);

        return info;
    }

}
