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

package org.kuali.student.core.enumerationmanagement.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;


/**
 *Describes the context for an enumeration.
 */ 
@XmlAccessorType(XmlAccessType.FIELD)
public class EnumContextInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private FieldDescriptorInfo contextValueDescriptor;

    @XmlAttribute
    private String type;

    /**
     * Describes the value that can be supplied for a given context, including field lengths, allowed characters, enumerations, etc.
     */
    public FieldDescriptorInfo getContextValueDescriptor() {
        return contextValueDescriptor;
    }

    public void setContextValueDescriptor(FieldDescriptorInfo contextValueDescriptor) {
        this.contextValueDescriptor = contextValueDescriptor;
    }

    /**
     * Identifier for the context modifier for an enumeration.
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
