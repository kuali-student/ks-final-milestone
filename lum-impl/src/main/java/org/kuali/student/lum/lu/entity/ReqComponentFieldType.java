package org.kuali.student.lum.lu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.kuali.student.common.util.UUIDHelper;

@Entity
@Table(name="KS_REQ_COMP_FIELD_TYPE_T")
public class ReqComponentFieldType {

    @Id
    @Column(name = "ID")
    private String id;
	    
    @Column(name="REQ_COMP_FIELD_TYPE_KEY", unique=true, nullable=false)
    private String key;

    /**
     * AutoGenerate the Id
     */
    public void prePersist() {
        this.id = UUIDHelper.genStringUUID(this.id);
    }
          
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }	
}
