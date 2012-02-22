package org.kuali.student.r2.core.class1.atp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.kuali.student.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.infc.Atp;

@Entity
@Table(name = "KSEN_ATP")
public class AtpEntity extends MetaEntity implements AttributeOwner<AtpAttributeEntity> {
    @Column(name = "NAME")
    private String name;

    @Column(name = "ADMIN_ORG_ID")
    private String adminOrgId;

    @Column(name = "ATP_CD")
    private String atpCode;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_DT", nullable = false)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_DT", nullable = false)
    private Date endDate;

    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String formatted;

    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH, nullable = false)
    private String plain;

    @Column(name = "ATP_TYPE", nullable = false)
    private String atpType;

    @Column(name = "ATP_STATE", nullable = false)
    private String atpState;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<AtpAttributeEntity> attributes = new ArrayList<AtpAttributeEntity>();

    public AtpEntity() {}

    public AtpEntity(Atp atp) {
        super(atp);
        this.setId(atp.getId());
        this.setName(atp.getName());
        this.setAdminOrgId(atp.getAdminOrgId());
        this.setAtpState(atp.getStateKey());
        this.setAtpType(atp.getTypeKey());

        if (atp.getStartDate() != null) {
            this.setStartDate(atp.getStartDate());
        }
        if (atp.getEndDate() != null) {
            this.setEndDate(atp.getEndDate());
        }
        if (atp.getDescr() != null) {
            RichText rt = atp.getDescr();
            this.setDescrFormatted(rt.getFormatted());
            this.setDescrPlain(rt.getPlain());
        }

        this.setAttributes(new ArrayList<AtpAttributeEntity>());
        if (null != atp.getAttributes()) {
            for (Attribute att : atp.getAttributes()) {
                this.getAttributes().add(new AtpAttributeEntity(att, this));
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getAtpType() {
        return atpType;
    }

    public void setAtpType(String atpType) {
        this.atpType = atpType;
    }

    public String getAtpState() {
        return atpState;
    }

    public void setAtpState(String atpState) {
        this.atpState = atpState;
    }

    @Override
    public void setAttributes(List<AtpAttributeEntity> attributes) {
        this.attributes = attributes;

    }

    @Override
    public List<AtpAttributeEntity> getAttributes() {
        return attributes;
    }

    public String getAdminOrgId() {
        return adminOrgId;
    }

    public void setAdminOrgId(String adminOrgId) {
        this.adminOrgId = adminOrgId;
    }

    public AtpInfo toDto() {
        AtpInfo atp = new AtpInfo();
        atp.setId(getId());
        atp.setName(name);
        atp.setStartDate(startDate);
        atp.setEndDate(endDate);
        atp.setAdminOrgId(getAdminOrgId());
        if (atpType != null)
            atp.setTypeKey(atpType);
        if (atpState != null)
            atp.setStateKey(atpState);
        atp.setMeta(super.toDTO());
        if (getDescrPlain() != null) {
            RichTextInfo rti = new RichTextInfo();
            rti.setPlain(getDescrPlain());
            rti.setFormatted(getDescrFormatted());
            atp.setDescr(rti);
        }

        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (AtpAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            atts.add(attInfo);
        }
        atp.setAttributes(atts);

        return atp;
    }

    public String getDescrFormatted() {
        return formatted;
    }

    public void setDescrFormatted(String formatted) {
        this.formatted = formatted;
    }

    public String getDescrPlain() {
        return plain;
    }

    public void setDescrPlain(String plain) {
        this.plain = plain;
    }

    public String getAtpCode() {
        return atpCode;
    }

    public void setAtpCode(String atpCode) {
        this.atpCode = atpCode;
    }

    //Kept this setter for the sake of backwards compatibility
    public void setDescr(AtpRichTextEntity atpRichTextEntity) {
        this.setDescrFormatted(atpRichTextEntity.getFormatted());
        this.setDescrPlain(atpRichTextEntity.getPlain());

    }

    //Kept this getter for the sake of backwards compatibility
    public AtpRichTextEntity getDescr() {
        return new AtpRichTextEntity(this.getDescrPlain(), this.getDescrFormatted());
    }
}
