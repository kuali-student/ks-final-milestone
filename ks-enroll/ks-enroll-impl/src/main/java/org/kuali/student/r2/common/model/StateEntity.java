package org.kuali.student.r2.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.kuali.student.r2.common.entity.MetaEntity;

@Entity
@Table(name = "KSEN_COMM_STATE")
public class StateEntity extends MetaEntity {
	@Column(name="NAME")
    private String name;

    @Column(name="DESCR")
    private String description;
    
    @Column(name="PROCESS_KEY")
    private String processKey;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public String getProcessKey() {
		return processKey;
	}

	public void setProcessKey(String processKey) {
		this.processKey = processKey;
	}    
}
