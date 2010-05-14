/*
 * Copyright 2009 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 * 
 * http://www.osedu.org/licenses/ECL-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
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

	@Override
	public String toString() {
		return "ReqComponentFieldType[id=" + id + "]";
	}        
}
