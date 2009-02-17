package org.kuali.student.lum.lu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="KS_LU_TYPE_T")
public class LuType {

	@Id
	@Column(name="ID")
	private String luTypeKey;

	public String getLuTypeKey() {
		return luTypeKey;
	}

	public void setLuTypeKey(String luTypeKey) {
		this.luTypeKey = luTypeKey;
	}
	
	
}
