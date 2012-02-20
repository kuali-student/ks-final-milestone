package org.kuali.student.enrollment.class1.lui.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.enrollment.lui.infc.Lui;
import org.kuali.student.r2.common.entity.MetaEntity;

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

}
