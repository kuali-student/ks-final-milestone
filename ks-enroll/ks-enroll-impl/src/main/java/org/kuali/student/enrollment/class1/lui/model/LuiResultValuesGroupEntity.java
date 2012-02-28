package org.kuali.student.enrollment.class1.lui.model;

import javax.persistence.*;

import org.kuali.student.common.util.UUIDHelper;

@Entity
@Table(name = "KSEN_LUI_RESULT_VAL_GRP")
public class LuiResultValuesGroupEntity{

    @Id
	@Column(name = "ID")
	private String id;

    @ManyToOne(optional=false)
	@JoinColumn(name="LUI_ID")
	private LuiEntity lui;

    @Column(name = "RESULT_VAL_GRP_ID", nullable = false)
    private String resultValuesGroupKey;

    public LuiResultValuesGroupEntity(){

    }

    public LuiResultValuesGroupEntity(LuiEntity lui, String resultValuesGroupKey){
        this.setId(UUIDHelper.genStringUUID());
        this.setLui(lui);
        this.setResultValuesGroupKey(resultValuesGroupKey);
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
