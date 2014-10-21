/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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

package org.kuali.student.r2.core.search.dto;

import org.kuali.student.r1.common.dictionary.old.dto.FieldDescriptor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
public class QueryParamInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement(namespace="http://student.kuali.org/wsdl/dictionary")
	private FieldDescriptor fieldDescriptor; 
	@XmlAttribute
	private String key; 
	@XmlAttribute
	private boolean optional;
	
	public FieldDescriptor getFieldDescriptor(){
		return fieldDescriptor;
	}
	public void setFieldDescriptor(FieldDescriptor fieldDescriptor){
		this.fieldDescriptor = fieldDescriptor;
	}
	public String getKey(){
		return key;
	}
	public void setKey(String key){
		this.key = key;
	}
	public boolean isOptional() {
		return optional;
	}
	public void setOptional(boolean optional) {
		this.optional = optional;
	}
}
