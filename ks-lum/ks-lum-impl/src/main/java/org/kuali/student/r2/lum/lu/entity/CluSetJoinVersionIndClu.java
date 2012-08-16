package org.kuali.student.r2.lum.lu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.kuali.student.r1.common.entity.BaseEntity;

@Entity
@Table(name = "KSLU_CLU_SET_JN_CLU")
public class CluSetJoinVersionIndClu extends BaseEntity {
	@ManyToOne
	@JoinColumn(name = "CLU_SET_ID")
	private CluSet cluSet;

	@Column(name = "CLU_VER_IND_ID")
	private String cluVersionIndId;

	public CluSet getCluSet() {
		return cluSet;
	}

	public void setCluSet(CluSet cluSet) {
		this.cluSet = cluSet;
	}

	public String getCluVersionIndId() {
		return cluVersionIndId;
	}

	public void setCluVersionIndId(String cluVersionIndId) {
		this.cluVersionIndId = cluVersionIndId;
	}
}
