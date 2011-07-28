package org.kuali.student.enrollment.class1.lui.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.MetaEntity;

@Entity
@Table(name = "KSEN_LUICLU_RELTN")
public class LuiCluRelationEntity extends MetaEntity{
	@Column(name = "CLU_ID")
	private String cluId;
	
	@ManyToOne
	@JoinColumn(name="LUI_ID")
	private LuiEntity lui;

	public String getCluId() {
		return cluId;
	}

	public void setCluId(String cluId) {
		this.cluId = cluId;
	}

	public LuiEntity getLui() {
		return lui;
	}

	public void setLui(LuiEntity lui) {
		this.lui = lui;
	}
	
	public LuiCluRelationEntity(LuiCluRelationEntity luiCluRelationEntity){
		this.setId(luiCluRelationEntity.getId());
		this.setCluId(luiCluRelationEntity.getCluId());
		this.setLui(luiCluRelationEntity.getLui());
	}
}
