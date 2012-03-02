package org.kuali.student.lum.lo.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.kuali.student.common.entity.BaseEntity;

@Entity
@Table(name = "KSLO_LO_JN_LOCATEGORY",
		uniqueConstraints={@UniqueConstraint(columnNames={"LO_ID", "LOCATEGORY_ID"})}
)
public class LoLoCategoryJoin extends BaseEntity{
    
    @ManyToOne
    @JoinColumn(name = "LO_ID")
	private Lo lo;
    
    @ManyToOne
    @JoinColumn(name = "LOCATEGORY_ID")
	private LoCategory loCategory;
    
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
