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
package org.kuali.student.core.enumerationmanagement.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.core.dto.Idable;

/**
 *Describes a field on an enumerated value structure.
 */ 
@XmlAccessorType(XmlAccessType.FIELD)
public class EnumeratedValueFieldInfo implements Serializable, Idable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private FieldDescriptorInfo fieldDescriptor;

    @XmlElement(name="key")
    private String id;

    @XmlElement
    private Integer minOccurs;

    @XmlElement
    private String maxOccurs;
    
    /**
     * Describes the value that can be supplied for the field specified in the key, including field lengths, allowed characters, enumerations, etc. These constraints should act as further restrictions beyond those supplied via the general format of an enumeratedValueInfo structure.
     */
    public FieldDescriptorInfo getFieldDescriptor() {
        return fieldDescriptor;
    }

    public void setFieldDescriptor(FieldDescriptorInfo fieldDescriptor) {
        this.fieldDescriptor = fieldDescriptor;
    }

    /**
     * Identifier for the field on an enumerated value structure. Unless there is a decision to support dynamic attributes here, the list of allowed values for this field will be fixed.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * The Minimum Occurrences value establishes a lower bound on the number of times a field can appear in the given context. A minOccurs of 0 indicates that the field is not required and can be left null. Must be less than or equal to the maxOccurs value, if specified.
     */
    public Integer getMinOccurs() {
        return minOccurs;
    }

    public void setMinOccurs(Integer minOccurs) {
        this.minOccurs = minOccurs;
    }

    /**
     * The Maximum Occurrences value establishes an upper bound on the number of times a field can appear in the given context. Allowed values are integers or the string "unbounded". Must be greater than or equal to the minOccurs value, if specified. "Unbounded" is evaluated to be greater than any integer value.
     */
    public String getMaxOccurs() {
        return maxOccurs;
    }

    public void setMaxOccurs(String maxOccurs) {
        this.maxOccurs = maxOccurs;
    }
}
