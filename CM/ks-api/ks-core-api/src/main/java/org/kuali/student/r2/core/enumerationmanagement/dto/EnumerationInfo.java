/*
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
package org.kuali.student.r2.core.enumerationmanagement.dto;

import org.kuali.student.r2.common.dto.KeyEntityInfo;
import org.kuali.student.r2.core.enumerationmanagement.infc.Enumeration;

import javax.xml.bind.Element;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EnumerationInfo", propOrder = {"key", "typeKey", "stateKey",
    "name", "descr", 
    "contextDescriptors", 
    "effectiveDate", 
    "expirationDate", 
    "meta", "attributes", "_futureElements" }) 
public class EnumerationInfo extends KeyEntityInfo implements Enumeration, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private List<String> contextDescriptors;
    @XmlElement
    private Date effectiveDate;
    @XmlElement
    private Date expirationDate;
    @XmlAnyElement
    private List<Object> _futureElements;  

    public EnumerationInfo() {
    }

    public EnumerationInfo(Enumeration enumeration) {
        super(enumeration);
        if (null != enumeration) {
            this.contextDescriptors = new ArrayList<String>(enumeration.getContextDescriptors());
        }
        if (enumeration.getEffectiveDate() != null) {
            this.effectiveDate = new Date(enumeration.getEffectiveDate().getTime());
        }
        if (enumeration.getExpirationDate() != null) {
            this.expirationDate = new Date(enumeration.getExpirationDate().getTime());
        }
    }

    @Override
    public List<String> getContextDescriptors() {
        if (contextDescriptors == null) {
            contextDescriptors = new ArrayList<String>();
        }
        return contextDescriptors;
    }

    public void setContextDescriptors(List<String> contextDescriptors) {
        this.contextDescriptors = contextDescriptors;
    }

    @Override
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
