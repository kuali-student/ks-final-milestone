package org.kuali.student.enrollment.class1.lui.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.common.entity.KSEntityConstants;
import org.kuali.student.enrollment.lui.infc.Lui;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.lum.clu.dto.FeeInfo;

@Entity
@Table(name = "KSEN_LUI_FEE")
public class LuiFeeEntity extends MetaEntity {

    @Column(name = "FEE_TYPE")
    private String feeType;

    @Column(name = "RATE_TYPE")
    private String rateType;

    @Column(name = "FEE_KEY")
    private String feeKey;

    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String formatted;

    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH, nullable = false)
    private String plain;

    @ManyToOne
    @JoinColumn(name = "LUI_ID")
    private LuiEntity lui;

    public LuiFeeEntity() {}

    public LuiFeeEntity(Lui lui) {

    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getRateType() {
        return rateType;
    }

    public void setRateType(String rateType) {
        this.rateType = rateType;
    }

    public String getFeeKey() {
        return feeKey;
    }

    public void setFeeKey(String feeKey) {
        this.feeKey = feeKey;
    }

    public String getFormatted() {
        return formatted;
    }

    public void setFormatted(String formatted) {
        this.formatted = formatted;
    }

    public String getPlain() {
        return plain;
    }

    public void setPlain(String plain) {
        this.plain = plain;
    }

    public LuiEntity getLui() {
        return lui;
    }

    public void setLui(LuiEntity lui) {
        this.lui = lui;
    }

    public FeeInfo toDto() {
        return null;

    }
}
