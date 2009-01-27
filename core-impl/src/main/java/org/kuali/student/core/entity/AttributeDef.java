package org.kuali.student.core.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.kuali.student.common.util.UUIDHelper;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name="KS_ATTR_DEF_T")
public abstract class AttributeDef {
	@Id
	private String id;
	
	private String name;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	/**
	 * AutoGenerate the Id
	 */
	@PrePersist
	public void prePersist() {
		if(this.id==null){
			this.id = UUIDHelper.genStringUUID(this.id);
		}
	}
}
