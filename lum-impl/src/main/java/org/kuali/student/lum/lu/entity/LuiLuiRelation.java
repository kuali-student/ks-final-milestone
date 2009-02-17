package org.kuali.student.lum.lu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "KS_LUI_REL_T")
public class LuiLuiRelation {
	@Id
	@Column(name = "ID")
	private String id;

	@ManyToOne
	@JoinColumn(name = "LUI_ID")
	private Lui lui;

	@ManyToOne
	@JoinColumn(name = "RELATED_LUI_ID")
	private Lui relatedLui;

	@ManyToOne
	@JoinColumn(name = "LU_REL_TYPE_ID")
	private LuLuRelationType luLuRelationType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Lui getLui() {
		return lui;
	}

	public void setLui(Lui lui) {
		this.lui = lui;
	}

	public Lui getRelatedLui() {
		return relatedLui;
	}

	public void setRelatedLui(Lui relatedLui) {
		this.relatedLui = relatedLui;
	}

	public LuLuRelationType getLuLuRelationType() {
		return luLuRelationType;
	}

	public void setLuLuRelationType(LuLuRelationType luLuRelationType) {
		this.luLuRelationType = luLuRelationType;
	}
}
