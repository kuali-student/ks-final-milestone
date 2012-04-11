package org.kuali.student.enrollment.class1.lui.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.kuali.student.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.lum.clu.dto.LuCodeInfo;
import org.kuali.student.r2.lum.clu.infc.LuCode;

@Entity
@Table(name = "KSEN_LUI_LU_CD")
public class LuCodeEntity extends MetaEntity implements AttributeOwner<LuCodeAttributeEntity> {

    @Column(name = "DESCR_FORMATTED", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String formatted;
    @Column(name = "DESCR_PLAIN", length = KSEntityConstants.EXTRA_LONG_TEXT_LENGTH)
    private String plain;
    @Column(name = "VALUE")
    private String value;
    @Column(name = "LUI_LUCD_TYPE")
    private String type;
    @ManyToOne
    @JoinColumn(name = "LUI_ID")
    private LuiEntity lui;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private List<LuCodeAttributeEntity> attributes;

    public LuCodeEntity() {
    }

    public LuCodeEntity(LuCode luCode) {
        super(luCode);
        this.setId(luCode.getId());
        this.setType(luCode.getTypeKey());
        this.setValue(luCode.getValue());
        if (luCode.getDescr() != null) {
            RichText rt = luCode.getDescr();
            this.setDescrFormatted(rt.getFormatted());
            this.setDescrPlain(rt.getPlain());
        }
        this.setAttributes(new ArrayList<LuCodeAttributeEntity>());
        if (null != luCode.getAttributes()) {
            for (Attribute att : luCode.getAttributes()) {
                LuCodeAttributeEntity attEntity = new LuCodeAttributeEntity(att);
                this.getAttributes().add(attEntity);
            }
        }
    }

    public LuCodeInfo toDto() {
        LuCodeInfo obj = new LuCodeInfo();
        obj.setId(getId());
        obj.setTypeKey(type);
        obj.setValue(value);
        if (getDescrPlain() != null) {
            RichTextInfo rti = new RichTextInfo();
            rti.setPlain(getDescrPlain());
            rti.setFormatted(getDescrFormatted());
            obj.setDescr(rti);
        }
        obj.setMeta(super.toDTO());
        List<AttributeInfo> atts = new ArrayList<AttributeInfo>();
        for (LuCodeAttributeEntity att : getAttributes()) {
            AttributeInfo attInfo = att.toDto();
            atts.add(attInfo);
        }
        obj.setAttributes(atts);

        return obj;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LuiEntity getLui() {
        return lui;
    }

    public void setLui(LuiEntity lui) {
        this.lui = lui;
    }

    @Override
    public void setAttributes(List<LuCodeAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    @Override
    public List<LuCodeAttributeEntity> getAttributes() {
        return attributes;
    }
}
