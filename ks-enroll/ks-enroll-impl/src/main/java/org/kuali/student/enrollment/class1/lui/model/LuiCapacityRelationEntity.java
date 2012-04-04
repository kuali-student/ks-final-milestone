package org.kuali.student.enrollment.class1.lui.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.enrollment.lui.infc.Lui;
import org.kuali.student.r2.common.entity.MetaEntity;

@Entity
@Table(name = "KSEN_LUICAPACITY_RELTN")
public class LuiCapacityRelationEntity extends MetaEntity {
    
    @ManyToOne
    @JoinColumn(name = "LUI_ID")
    private LuiEntity lui;
    
    @ManyToOne
    @JoinColumn(name = "LUI_CAPACITY_ID")
    private LuiCapacityEntity luiCapacity;

    public LuiCapacityRelationEntity() {}

    public LuiCapacityRelationEntity(Lui lui) {

    }
    
    public LuiEntity getLui() {
        return lui;
    }

    public void setLui(LuiEntity lui) {
        this.lui = lui;
    }

    public LuiCapacityEntity getLuiCapacity() {
        return luiCapacity;
    }

    public void setLuiCapacity(LuiCapacityEntity luiCapacity) {
        this.luiCapacity = luiCapacity;
    }
}
