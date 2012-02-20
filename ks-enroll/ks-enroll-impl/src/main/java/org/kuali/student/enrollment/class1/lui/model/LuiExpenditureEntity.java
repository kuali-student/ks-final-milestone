package org.kuali.student.enrollment.class1.lui.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.enrollment.lui.infc.Lui;
import org.kuali.student.r2.common.entity.MetaEntity;
import org.kuali.student.r2.lum.clu.dto.ExpenditureInfo;

@Entity
@Table(name = "KSEN_LUI_EXPENDITURE")
public class LuiExpenditureEntity extends MetaEntity {

    @ManyToOne
    @JoinColumn(name = "LUI_ID")
    private LuiEntity lui;

    public LuiExpenditureEntity() {}

    public LuiExpenditureEntity(Lui lui) {

    }

    public LuiEntity getLui() {
        return lui;
    }

    public void setLui(LuiEntity lui) {
        this.lui = lui;
    }

    public ExpenditureInfo toDto() {
        ExpenditureInfo obj = new ExpenditureInfo();
        obj.setId(this.getId());
                
        //TODO: attributes
        
        return obj;

    }
}
