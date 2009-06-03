package org.kuali.student.lum.lu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.entity.MetaEntity;

@Entity
@Table(name = "KSLU_LO_JN_CLU", uniqueConstraints = @UniqueConstraint(columnNames = {
		"LO_ID", "CLU_ID" }))
public class LearningObjective extends MetaEntity {
	@Id
	@Column(name = "ID")
	private String id;
	
	@Column(name = "LO_ID")
	private String learningObjectiveId;
	
	@ManyToOne
	@JoinColumn(name = "CLU_ID")
	private Clu clu;

    @Override
    public void onPrePersist() {
        this.id = UUIDHelper.genStringUUID(this.id);
    }
    
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
