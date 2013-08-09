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

import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.infc.HasMeta;
import org.kuali.student.r2.common.infc.Meta;
import org.kuali.student.r2.core.enumerationmanagement.infc.EnumContextValue;
import org.kuali.student.r2.core.enumerationmanagement.infc.EnumeratedValue;

import javax.xml.bind.Element;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Value associated with a particular enumeration.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EnumeratedValueInfo", propOrder = {"code", "abbrevValue", "value", "sortKey", "contexts", "enumerationKey", 
        "effectiveDate", "expirationDate", "meta", "_futureElements" }) 
public class EnumeratedValueInfo implements EnumeratedValue, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String code;
    @XmlElement
    private String abbrevValue;
    @XmlElement
    private String value;
    @XmlElement
    private String sortKey;
    @XmlElement
    private List<EnumContextValueInfo> contexts;
    @XmlAttribute
    private String enumerationKey;
    @XmlElement
    private Date effectiveDate;
    @XmlElement
    private Date expirationDate;
    @XmlElement
    private MetaInfo meta;
    
    @XmlAnyElement
    private List<Object> _futureElements;  

    public EnumeratedValueInfo() {
    }

    public EnumeratedValueInfo(EnumeratedValue enumeratedValue) {
        if (null != enumeratedValue) {
            this.meta = new MetaInfo(enumeratedValue.getMeta());
            this.code = enumeratedValue.getCode();
            this.abbrevValue = enumeratedValue.getAbbrevValue();
            this.value = enumeratedValue.getValue();
            this.sortKey = enumeratedValue.getSortKey();
            this.contexts = new ArrayList<EnumContextValueInfo>();
            for (EnumContextValue enumContextValue : enumeratedValue.getContexts()) {
                this.contexts.add(new EnumContextValueInfo(enumContextValue));
            }
            this.effectiveDate = (null != enumeratedValue.getEffectiveDate()) ? new Date(enumeratedValue.getEffectiveDate().getTime()) : null;
            this.expirationDate = (null != enumeratedValue.getExpirationDate()) ? new Date(enumeratedValue.getExpirationDate().getTime()) : null;
        }
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getAbbrevValue() {
        return abbrevValue;
    }

    public void setAbbrevValue(String abbrevValue) {
        this.abbrevValue = abbrevValue;
    }

    @Override
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    @Override
    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    @Override
    public List<EnumContextValueInfo> getContexts() {
        if (contexts == null) {
            contexts = new ArrayList<EnumContextValueInfo>();
        }
        return contexts;
    }

    public void setContexts(List<EnumContextValueInfo> contexts) {
        this.contexts = contexts;
    }

    @Override
    public String getEnumerationKey() {
        return enumerationKey;
    }

    public void setEnumerationKey(String enumerationKey) {
        this.enumerationKey = enumerationKey;
    }

    @Override
    public Meta getMeta() {
        return meta;
    } 
    
    public void setMeta(MetaInfo meta) {
        this.meta = meta;
    }

}
