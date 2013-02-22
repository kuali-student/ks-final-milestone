package org.kuali.student.r2.core.organization.service.impl.model;



import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.infc.Org;
import org.kuali.student.r2.core.organization.service.impl.lib.AttributeOwner;
import org.kuali.student.r2.core.organization.service.impl.lib.KSEntityConstants;
import org.kuali.student.r2.core.organization.service.impl.lib.MetaEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name="KSEN_ORG")
@NamedQueries({
        @NamedQuery(name = "OrgEntity.getIdsByType",
                query = "select id from OrgEntity where orgType = :type")
})
public class OrgEntity extends MetaEntity implements AttributeOwner<OrgAttributeEntity> {

    @Column(name = "ORG_TYPE", nullable = false)
    private String orgType;
    @Column(name = "ORG_STATE", nullable = false)
    private String orgState;
    @Column(name = "LONG_NAME", nullable = false)
    private String longName;
    @Column(name = "LONG_DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String longDescrPlain;
    @Column(name = "LONG_DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String longDescrFormatted;
    @Column(name = "SHORT_NAME")
    private String shortName;
    @Column(name = "SORT_NAME")
    private String sortName;
    @Column(name = "SHORT_DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String shortDescrPlain;
    @Column(name = "SHORT_DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String shortDescrFormatted;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="EXPIR_DT", nullable = false)
    private Date expirationDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="EFF_DT", nullable = false)
    private Date effectiveDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER, orphanRemoval = true)
    private final Set<OrgAttributeEntity> attributes = new HashSet<OrgAttributeEntity>();
    //BOOKMARK TODO - add orgCode mapping then modify necessary methods fromDto and toDto


    public  OrgEntity() {
    }

    public OrgEntity(Org org) {
        super(org);
        setId(org.getId());
        setOrgType(org.getTypeKey());
        fromDto(org);
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getOrgState() {
        return orgState;
    }

    public void setOrgState(String orgState) {
        this.orgState = orgState;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getLongDescrPlain() {
        return longDescrPlain;
    }

    public void setLongDescrPlain(String longDescrPlain) {
        this.longDescrPlain = longDescrPlain;
    }

    public String getLongDescrFormatted() {
        return longDescrFormatted;
    }

    public void setLongDescrFormatted(String longDescrFormatted) {
        this.longDescrFormatted = longDescrFormatted;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getShortDescrPlain() {
        return shortDescrPlain;
    }

    public void setShortDescrPlain(String shortDescrPlain) {
        this.shortDescrPlain = shortDescrPlain;
    }

    public String getShortDescrFormatted() {
        return shortDescrFormatted;
    }

    public void setShortDescrFormatted(String shortDescrFormatted) {
        this.shortDescrFormatted = shortDescrFormatted;
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

    @Override
    public Set<OrgAttributeEntity> getAttributes() {
        return attributes;
    }

    @Override
    public void setAttributes(Set<OrgAttributeEntity> attributes) {
        this.attributes.clear();
        if (attributes != null) {
            this.attributes.addAll(attributes);
        }
    }

    public void fromDto(Org org) {
        setOrgState(org.getStateKey());
        setLongName(org.getLongName());
        if(null != org.getLongDescr()) {
            setLongDescrPlain(org.getLongDescr().getPlain());
            setLongDescrFormatted(org.getLongDescr().getFormatted());
        }
        setShortName(org.getShortName());
        setSortName(org.getSortName());
        if(org.getShortDescr() != null) {
            setShortDescrPlain(org.getShortDescr().getPlain());
            setShortDescrFormatted(org.getShortDescr().getFormatted());
        }
        setExpirationDate(org.getExpirationDate());
        setEffectiveDate(org.getEffectiveDate());

        this.attributes.clear();
        for (Attribute att : org.getAttributes()) {
            OrgAttributeEntity attEntity = new OrgAttributeEntity(att, this);
            this.getAttributes().add(attEntity);
        }
    }

    public OrgInfo toDto() {
        OrgInfo info = new OrgInfo();

        info.setId(getId());
        info.setTypeKey(orgType);
        info.setStateKey(orgState);
        info.setLongName(longName);
        info.setLongDescr(RichTextHelper.toRichTextInfo(longDescrPlain, longDescrFormatted));
        info.setShortName(shortName);
        info.setSortName(sortName);
        info.setShortDescr(RichTextHelper.toRichTextInfo(shortDescrPlain, shortDescrFormatted));
        info.setExpirationDate(expirationDate);
        info.setEffectiveDate(effectiveDate);
        info.setMeta(super.toDTO());

        for (OrgAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            info.getAttributes().add(attInfo);
        }

        return info;
    }
}
