package org.kuali.student.enrollment.class1.lui.model;

import org.kuali.student.r1.common.entity.KSEntityConstants;
import org.kuali.student.r2.common.assembler.TransformUtility;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.lum.clu.dto.LuCodeInfo;
import org.kuali.student.r2.lum.clu.infc.LuCode;

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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<LuCodeAttributeEntity> attributes;

    public LuCodeEntity() {
    }

    public LuCodeEntity(LuCode luCode) {
        super(luCode);
        this.setId(luCode.getId());
        this.setType(luCode.getTypeKey());
        fromDto(luCode);
    }

    public List<Object> fromDto(LuCode luCode) {
        List<Object> orphansToDelete = new ArrayList<Object>();

        this.setValue(luCode.getValue());
        if (luCode.getDescr() != null) {
            RichText rt = luCode.getDescr();
            this.setDescrFormatted(rt.getFormatted());
            this.setDescrPlain(rt.getPlain());
        }

        //Attributes
        orphansToDelete.addAll(TransformUtility.mergeToEntityAttributes(LuCodeAttributeEntity.class, luCode, this));

        return orphansToDelete;
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

        // Attributes
        obj.setAttributes(TransformUtility.toAttributeInfoList(this));

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
    public void setAttributes(Set<LuCodeAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Set<LuCodeAttributeEntity> getAttributes() {
        return attributes;
    }


}
