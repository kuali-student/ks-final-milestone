package org.kuali.student.enrollment.class1.lui.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.common.util.UUIDHelper;

@Entity
@Table(name = "KSEN_LUI_UNITS_CONT_OWNER")
public class LuiUnitsContentOwnerEntity {

    @Id
    @Column(name = "ID")
    private String id;

    @ManyToOne
    @JoinColumn(name = "LUI_ID")
    private LuiEntity lui;

    @Column(name = "ORG_ID", nullable = false)
    private String orgId;
    
    public LuiUnitsContentOwnerEntity(){
    }

    public LuiUnitsContentOwnerEntity(LuiEntity lui, String orgId){
        this.setId(UUIDHelper.genStringUUID());
        this.setLui(lui);
        this.setOrgId(orgId);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LuiEntity getLui() {
        return lui;
    }

    public void setLui(LuiEntity lui) {
        this.lui = lui;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}
