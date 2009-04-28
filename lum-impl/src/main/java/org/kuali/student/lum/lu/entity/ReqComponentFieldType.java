package org.kuali.student.lum.lu.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.kuali.student.core.entity.FieldDescriptorEntity;

@Entity
@Table(name="KSLU_REQ_COM_FIELD_TYPE")
public class ReqComponentFieldType {

    @Id
    @Column(name = "ID")
    private String id;
	    
    @Embedded
    private FieldDescriptorEntity fieldDescriptor;
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

    /**
     * @return the fieldDescriptor
     */
    public FieldDescriptorEntity getFieldDescriptor() {
        return fieldDescriptor;
    }

    /**
     * @param fieldDescriptor the fieldDescriptor to set
     */
    public void setFieldDescriptor(FieldDescriptorEntity fieldDescriptor) {
        this.fieldDescriptor = fieldDescriptor;
    }        
}
