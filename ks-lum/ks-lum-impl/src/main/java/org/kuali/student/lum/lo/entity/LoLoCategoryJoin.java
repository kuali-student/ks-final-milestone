package org.kuali.student.lum.lo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.kuali.student.common.util.UUIDHelper;

@Entity
@Table(name = "KSLU_LO_JN_LOCATEGORY",
		uniqueConstraints={@UniqueConstraint(columnNames={"LO_ID", "LOCATEGORY_ID"})}
)
public class LoLoCategoryJoin {
    
	@Id
    @Column(name = "ID")
	private String id;
    
    @ManyToOne
    @JoinColumn(name = "LO_ID")
	private Lo lo;
    
    @ManyToOne
    @JoinColumn(name = "LOCATEGORY_ID")
	private LoCategory loCategory;
    
    @PrePersist
	public void onPrePersist() {
		this.id = UUIDHelper.genStringUUID(this.id);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Lo getLo() {
		return lo;
	}

	public void setLo(Lo lo) {
		this.lo = lo;
	}

	public LoCategory getLoCategory() {
		return loCategory;
	}

	public void setLoCategory(LoCategory loCategory) {
		this.loCategory = loCategory;
	}
}
