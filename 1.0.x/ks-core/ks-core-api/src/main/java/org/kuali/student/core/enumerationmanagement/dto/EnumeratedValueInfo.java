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
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


/**
 *Value associated with a particular enumeration.
 */ 
@XmlAccessorType(XmlAccessType.FIELD)
public class EnumeratedValueInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String code;

    @XmlElement
    private String abbrevValue;

    @XmlElement
    private String value;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

    @XmlElement
    private String sortKey;

    @XmlElement
    private List<EnumContextValueInfo> contexts;

    /**
     * Typically coincides with a code representation. Likely the key if this is a reference to another object.
     */
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Typically coincides with a shortened name. May be equal to the code or value fields.
     */
    public String getAbbrevValue() {
        return abbrevValue;
    }

    public void setAbbrevValue(String abbrevValue) {
        this.abbrevValue = abbrevValue;
    }

    /**
     * Typically coincides with a name for display.
     */
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Date that this enumerated value became effective. If referring to another object, this may correspond with the effective date, created date, date of a state transition, or some arbitrarily defined date. For code/value pairs with no dates, the current date may be returned.
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * Date that this enumerated value expires. If referring to another object, this may correspond with the expiration date, date of a state transition, or some arbitrarily defined date. If this field is not specified, then no expiration date has been currently defined. For code/value pairs with no dates, this date may not be specified.
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * Default position for the enumerated value. This might or might not exist, particularly in cases where the enumeration consists solely of a view.
     */
    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    /**
     * Indicates which context types and values this particular enumerated value participates in.
     */
    public List<EnumContextValueInfo> getContexts() {
        if (contexts == null) {
            contexts = new ArrayList<EnumContextValueInfo>();
        }
        return contexts;
    }

    public void setContexts(List<EnumContextValueInfo> contexts) {
        this.contexts = contexts;
    }
}
