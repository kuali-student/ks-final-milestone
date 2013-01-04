package org.kuali.student.enrollment.class1.lui.model;

import org.kuali.student.r1.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.lum.clu.dto.FeeInfo;
import org.kuali.student.r2.lum.clu.infc.Fee;

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
@Table(name = "KSEN_LUI_FEE")
public class LuiFeeEntity extends MetaEntity implements AttributeOwner<LuiFeeAttributeEntity> {

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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<LuiFeeAttributeEntity> attributes;

    public LuiFeeEntity() {}

    public LuiFeeEntity(Fee fee) {
        super(fee);
        this.setFeeKey(fee.getKey());
        this.setFeeType(fee.getFeeType());
        this.setRateType(fee.getRateType());

        // Description
        if (fee.getDescr() != null) {
            RichText rt = fee.getDescr();
            this.setFormatted(rt.getFormatted());
            this.setPlain(rt.getPlain());
        }

        // Attributes
        this.setAttributes(new HashSet<LuiFeeAttributeEntity>());
        if (null != fee.getAttributes()) {
            for (Attribute att : fee.getAttributes()) {
                LuiFeeAttributeEntity attEntity = new LuiFeeAttributeEntity(att, this);
                this.getAttributes().add(attEntity);
            }
        }
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
        FeeInfo obj = new FeeInfo();
        obj.setId(this.getId());
        //if(lui!=null)
        //    obj.setLuiId(lui.getId());
        obj.setFeeType(this.getFeeType());
        obj.setRateType(this.getRateType());
        // TODO: obj.setFeeAmounts(feeAmounts)

        RichTextInfo rtInfo = new RichTextInfo();
        rtInfo.setFormatted(this.getFormatted());
        rtInfo.setPlain(this.getPlain());
        obj.setDescr(rtInfo);

        // Attributes
        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (LuiFeeAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            atts.add(attInfo);
        }
        obj.setAttributes(atts);
        
        obj.setMeta(super.toDTO());

        return obj;

    }

    @Override
    public void setAttributes(Set<LuiFeeAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Set<LuiFeeAttributeEntity> getAttributes() {
        return attributes;
    }
}
