package org.kuali.student.enrollment.class1.lui.model;

import org.kuali.student.r2.common.assembler.TransformUtility;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.entity.AttributeOwner;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.lum.clu.dto.ExpenditureInfo;
import org.kuali.student.r2.lum.clu.infc.Expenditure;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "KSEN_LUI_EXPENDITURE")
public class LuiExpenditureEntity extends MetaEntity implements AttributeOwner<LuiExpenditureAttributeEntity> {

    @ManyToOne
    @JoinColumn(name = "LUI_ID")
    private LuiEntity lui;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER)
    private List<LuiExpenditureAttributeEntity> attributes;

    public LuiExpenditureEntity() {}

    public LuiExpenditureEntity(Expenditure expenditure) {
        super(expenditure);
        this.setId(expenditure.getId());

        // Attributes
        TransformUtility.mergeToEntityAttributes(LuiExpenditureAttributeEntity.class, expenditure, this);
    }

    public ExpenditureInfo toDto() {
        ExpenditureInfo obj = new ExpenditureInfo();
        obj.setId(this.getId());

        // Attributes
        obj.setAttributes(TransformUtility.toAttributeInfoList(this));
        
        obj.setMeta(super.toDTO());

        return obj;

    }
    
    public LuiEntity getLui() {
        return lui;
    }

    public void setLui(LuiEntity lui) {
        this.lui = lui;
    }

    @Override
    public void setAttributes(List<LuiExpenditureAttributeEntity> attributes) {
        this.attributes = attributes;
    }

    @Override
    public List<LuiExpenditureAttributeEntity> getAttributes() {
        return attributes;
    }
}
