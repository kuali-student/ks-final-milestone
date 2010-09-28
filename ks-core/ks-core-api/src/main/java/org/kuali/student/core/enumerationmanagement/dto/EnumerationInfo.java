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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.core.dto.TypeInfo;


/**
 *Descriptive information about an enumeration, including field constraints and supported contexts.
 */ 
@XmlAccessorType(XmlAccessType.FIELD)
public class EnumerationInfo extends TypeInfo {

    private static final long serialVersionUID = 1L;

    @XmlAttribute(name="key")
    private String id;
    
    @XmlElement
    private String name;
    
    @XmlElement(name ="desc")
    private String descr;

    @XmlElement
    private Date effectiveDate;
    
    @XmlElement
    private Date expirationDate;
    
    @XmlElement
    private List<String> contextDescriptors;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * List of contexts supported by this enumeration
     */
    public List<String> getContextDescriptors() {
        if (contextDescriptors == null) {
            contextDescriptors = new ArrayList<String>();
        }
        return contextDescriptors;
    }

    public void setContextDescriptors(List<String> contextDescriptors) {
        this.contextDescriptors = contextDescriptors;
    }
}
