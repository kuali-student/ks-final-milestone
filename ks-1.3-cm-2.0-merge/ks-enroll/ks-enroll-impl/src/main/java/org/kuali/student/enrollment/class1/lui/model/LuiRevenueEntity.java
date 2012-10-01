package org.kuali.student.enrollment.class1.lui.model;

import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.lum.clu.dto.RevenueInfo;
import org.kuali.student.r2.lum.clu.infc.Revenue;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "KSEN_LUI_REVENUE")
public class LuiRevenueEntity extends MetaEntity implements AttributeOwner<LuiRevenueAttributeEntity> {

    @Column(name = "FEE_TYPE")
    private String feeType;

    @ManyToOne
    @JoinColumn(name = "LUI_ID")
    private LuiEntity lui;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<LuiRevenueAttributeEntity> attributes;

    public LuiRevenueEntity() {}

    public LuiRevenueEntity(Revenue revenue) {
        super(revenue);
        this.setId(revenue.getId());
        this.setFeeType(revenue.getFeeType());

        // Attributes
        this.setAttributes(new HashSet<LuiRevenueAttributeEntity>());
        if (null != revenue.getAttributes()) {
            for (Attribute att : revenue.getAttributes()) {
                LuiRevenueAttributeEntity attEntity = new LuiRevenueAttributeEntity(att, this);
                this.getAttributes().add(attEntity);
            }
        }
    }

    public RevenueInfo toDto() {
        RevenueInfo obj = new RevenueInfo();
        obj.setId(this.getId());
        obj.setFeeType(this.getFeeType());

        // Attributes
        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (LuiRevenueAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            atts.add(attInfo);
        }
        obj.setAttributes(atts);
        
        obj.setMeta(super.toDTO());

        return obj;

    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public LuiEntity getLui() {
        return lui;
    }

    public void setLui(LuiEntity lui) {
        this.lui = lui;
    }

    @Override
    public void setAttributes(Set<LuiRevenueAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Set<LuiRevenueAttributeEntity> getAttributes() {
        return attributes;
    }

}
