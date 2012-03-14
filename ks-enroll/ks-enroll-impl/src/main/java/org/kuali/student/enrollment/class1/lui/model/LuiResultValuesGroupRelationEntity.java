package org.kuali.student.enrollment.class1.lui.model;

import javax.persistence.*;

@Entity
@Table(name = "KSEN_LUI_RV_GRP_RELTN")
public class LuiResultValuesGroupRelationEntity{

    @Id
	@Column(name = "ID")
	private String id;

    @ManyToOne(optional=false)
	@JoinColumn(name="LUI_ID")
	private LuiEntity lui;

    @Column(name = "RV_GRP_ID", nullable = false)
    private String resultValuesGroupKey;

    public LuiResultValuesGroupRelationEntity(){

    }

    public LuiResultValuesGroupRelationEntity(LuiEntity lui, String resultValuesGroupKey){
        setLui(lui);
        setResultValuesGroupKey(resultValuesGroupKey);
    }

    public String getResultValuesGroupKey() {
        return resultValuesGroupKey;
    }

    public void setResultValuesGroupKey(String resultValuesGroupKey) {
        this.resultValuesGroupKey = resultValuesGroupKey;
    }

    public LuiEntity getLui() {
        return lui;
    }

    public void setLui(LuiEntity lui) {
        this.lui = lui;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
