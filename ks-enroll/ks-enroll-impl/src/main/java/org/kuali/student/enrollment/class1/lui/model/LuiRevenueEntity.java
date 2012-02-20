package org.kuali.student.enrollment.class1.lui.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.enrollment.lui.infc.Lui;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.lum.clu.dto.RevenueInfo;

@Entity
@Table(name = "KSEN_LUI_REVENUE")
public class LuiRevenueEntity extends MetaEntity {
    
    @Column(name = "FEE_TYPE")
    private String feeType;
    
    @ManyToOne
    @JoinColumn(name = "LUI_ID")
    private LuiEntity lui;
    
    public LuiRevenueEntity() {}

    public LuiRevenueEntity(Lui lui) {

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
    
    public RevenueInfo toDto() {
        RevenueInfo obj = new RevenueInfo();
        obj.setId(this.getId());
        obj.setFeeType(this.getFeeType());
        
        //TODO: attributes
        
        return obj;

    }

}
