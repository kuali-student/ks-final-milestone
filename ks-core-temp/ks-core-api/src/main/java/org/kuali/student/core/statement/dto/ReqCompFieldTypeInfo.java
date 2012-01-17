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

package org.kuali.student.core.statement.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.core.dictionary.old.dto.FieldDescriptor;
import org.kuali.student.core.dto.Idable;

/**
 *Detailed information about a requirement component field type.
 */ 
@XmlAccessorType(XmlAccessType.FIELD)
public class ReqCompFieldTypeInfo implements Serializable, Idable {

    private static final long serialVersionUID = 1L;

    @XmlElement(namespace="http://student.kuali.org/wsdl/dictionary")
    private FieldDescriptor fieldDescriptor;

    @XmlAttribute(name="key")
    private String id;

    /**
     * Describes a "field" or simple type within a larger object.
     */
    public FieldDescriptor getFieldDescriptor() {
        return fieldDescriptor;
    }

    public void setFieldDescriptor(FieldDescriptor fieldDescriptor) {
        this.fieldDescriptor = fieldDescriptor;
    }

    /**
     * Unique identifier for a requirement component field type.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	@Override
	public String toString() {
		return "ReqCompFieldTypeInfo[id=" + id + "]";
	}
}
