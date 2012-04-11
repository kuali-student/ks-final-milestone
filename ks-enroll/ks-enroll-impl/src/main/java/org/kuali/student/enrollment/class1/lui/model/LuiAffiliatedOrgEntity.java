package org.kuali.student.enrollment.class1.lui.model;

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

import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.lum.clu.dto.AffiliatedOrgInfo;
import org.kuali.student.r2.lum.clu.infc.AffiliatedOrg;

@Entity
@Table(name = "KSEN_LUI_AFFILIATED_ORG")
public class LuiAffiliatedOrgEntity extends MetaEntity implements AttributeOwner<LuiAffiliatedOrgAttributeEntity>{

    @Column(name = "AFFILIATED_ORG_TYPE", nullable = false)
    private String type;

    @Column(name = "AFFILIATED_ORG_STATE", nullable = false)
    private String state;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EFF_DT")
    private Date effectiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIR_DT")
    private Date expirationDate;
    
    @Column(name = "PERCENTAGE")
    private Long percentage;
    
    @Column(name = "ORG_ID", nullable = false)
    private String orgId;
    
    @Column(name = "EXPENDITURE_ID")
    private String expenditureId;
    
    @Column(name = "REVENUE_ID")
    private String revenueId;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<LuiAffiliatedOrgAttributeEntity> attributes;
    
    public LuiAffiliatedOrgEntity() {}

    public LuiAffiliatedOrgEntity(AffiliatedOrg affiliatedOrg) {
        
        super(affiliatedOrg);
        this.setId(affiliatedOrg.getId());
        this.setType(affiliatedOrg.getTypeKey());
        this.setState(affiliatedOrg.getStateKey());
        this.setEffectiveDate(affiliatedOrg.getEffectiveDate());
        this.setExpirationDate(affiliatedOrg.getExpirationDate());
        
        this.setOrgId(affiliatedOrg.getOrgId());
        this.setPercentage(affiliatedOrg.getPercentage());
        //this.setExpenditureId();
        //this.setRevenueId();
        
        this.setAttributes(new ArrayList<LuiAffiliatedOrgAttributeEntity>());
        if (null != affiliatedOrg.getAttributes()) {
            for (Attribute att : affiliatedOrg.getAttributes()) {
                LuiAffiliatedOrgAttributeEntity attEntity = new LuiAffiliatedOrgAttributeEntity(att);
                this.getAttributes().add(attEntity);
            }
        }
    }
    
    public AffiliatedOrgInfo toDto() {
        
        AffiliatedOrgInfo obj = new AffiliatedOrgInfo();
        obj.setId(this.getId());
        obj.setTypeKey(this.getType());
        obj.setStateKey(this.getState());
        obj.setEffectiveDate(this.getEffectiveDate());
        obj.setExpirationDate(this.getExpirationDate());
        
        obj.setOrgId(this.getOrgId());
        obj.setPercentage(this.getPercentage());
        //obj.setExpenditureId
        //obj.setRevenueId

        // Attributes
        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (LuiAffiliatedOrgAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            atts.add(attInfo);
        }
        obj.setAttributes(atts);
        
        obj.setMeta(super.toDTO());

        return obj;

    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Long getPercentage() {
        return percentage;
    }

    public void setPercentage(Long percentage) {
        this.percentage = percentage;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getExpenditureId() {
        return expenditureId;
    }

    public void setExpenditureId(String expenditureId) {
        this.expenditureId = expenditureId;
    }

    public String getRevenueId() {
        return revenueId;
    }

    public void setRevenueId(String revenueId) {
        this.revenueId = revenueId;
    }

    @Override
    public void setAttributes(List<LuiAffiliatedOrgAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    @Override
    public List<LuiAffiliatedOrgAttributeEntity> getAttributes() {
        return attributes;
    }

}
