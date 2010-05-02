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
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.core.dto.Idable;

/**
 *Descriptive information about an enumeration, including field constraints and supported contexts.
 */ 
@XmlAccessorType(XmlAccessType.FIELD)
public class EnumerationMetaInfo implements Serializable, Idable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String name;

    @XmlElement
    private String desc;

    @XmlElement
    private List<EnumeratedValueFieldInfo> enumeratedValueFields;

    @XmlElement
    private List<EnumContextInfo> contextDescriptors;

    @XmlAttribute(name="key")
    private String id;

    /**
     * Name of the enumeration.
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Description of the enumeration. This may describe potential source for the enumeration (ex. standards body, etc.)
     */
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * Description of the constraints on the fields of this enumeration. Ex. the code field may only take a alphanumeric two character string.
     */
    public List<EnumeratedValueFieldInfo> getEnumeratedValueFields() {
        if (enumeratedValueFields == null) {
            enumeratedValueFields = new ArrayList<EnumeratedValueFieldInfo>();
        }
        return enumeratedValueFields;
    }

    public void setEnumeratedValueFields(List<EnumeratedValueFieldInfo> enumeratedValueFields) {
        this.enumeratedValueFields = enumeratedValueFields;
    }

    /**
     * List of contexts supported by this enumeration
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
