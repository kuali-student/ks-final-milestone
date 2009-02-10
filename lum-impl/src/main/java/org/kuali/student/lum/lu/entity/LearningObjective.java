package org.kuali.student.lum.lu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "KS_LRN_OBJ_CLU_T", uniqueConstraints = @UniqueConstraint(columnNames = {
		"LRN_OBJ_ID", "CLU_ID" }))
public class LearningObjective {
	@Id
	@Column(name = "ID")
	private String id;
	
	@Column(name = "LRN_OBJ_ID")
	private String learningObjectiveId;
	
	@ManyToOne
	@JoinColumn(name = "CLU_ID")
	private Clu clu;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLearningObjectiveId() {
		return learningObjectiveId;
	}

	public void setLearningObjectiveId(String learningObjectiveId) {
		this.learningObjectiveId = learningObjectiveId;
	}

	public Clu getClu() {
		return clu;
	}

	public void setClu(Clu clu) {
		this.clu = clu;
	}

}
