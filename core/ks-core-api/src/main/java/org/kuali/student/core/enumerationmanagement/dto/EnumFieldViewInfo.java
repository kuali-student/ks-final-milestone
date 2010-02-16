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
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.core.dto.Idable;

/**
 * Description of an enumeration as it relates to a particular field. This is a lighter weight version of the enumerationMetaInfo Structure. Most of the information regarding the enumeration itself (ex. name, desc) is removed in this case, as the information about the field containing it is expected to be more relevant. Information about what the intent of a context and how to prepare it was retained since a caller needs to be aware of those aspects to be able to appropriately retrieve the list of values.

 */ 
@XmlAccessorType(XmlAccessType.FIELD)
public class EnumFieldViewInfo implements Serializable, Idable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private List<EnumContextInfo> contextDescriptors;

    @XmlAttribute(name="key")
    private String id;

    /**
     * List of enumeration context descriptors. It is expected that at least one context should be listed, so this field is required.
     */
    public List<EnumContextInfo> getContextDescriptors() {
        if (contextDescriptors == null) {
            contextDescriptors = new ArrayList<EnumContextInfo>();
        }
        return contextDescriptors;
    }

    public void setContextDescriptors(List<EnumContextInfo> contextDescriptors) {
        this.contextDescriptors = contextDescriptors;
    }

    /**
     * Identifier for an enumeration.
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
