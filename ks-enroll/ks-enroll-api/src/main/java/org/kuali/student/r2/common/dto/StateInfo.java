/*
 * Copyright 2010 The Kuali Foundation Licensed under the Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the License.
 */

package org.kuali.student.r2.common.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.infc.State;
import org.w3c.dom.Element;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StateInfo", propOrder = {"key", "name", "descr", "effectiveDate", "expirationDate", "attributes", "_futureElements"})
public class StateInfo extends HasAttributesInfo implements State, Serializable {

    @XmlAttribute
    private String key;

    @XmlElement
    private String name;

    @XmlElement
    private String descr;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

    @XmlAnyElement
    private List<Element> _futureElements;

    public static StateInfo getInstance(State state) {
        return new StateInfo(state);
    }

    private StateInfo() {
        key = null;
        name = null;
        descr = null;
        effectiveDate = null;
        expirationDate = null;
        _futureElements = null;
    }

    private StateInfo(State sInfo) {
        super(sInfo);
        this.key = sInfo.getKey();
        this.name = sInfo.getName();
        this.descr = sInfo.getDescr();
        this.effectiveDate = null != sInfo.getEffectiveDate() ? new Date(sInfo.getEffectiveDate().getTime()) : null;
        this.expirationDate = null != sInfo.getExpirationDate() ? new Date(sInfo.getExpirationDate().getTime()) : null;
        this._futureElements = null;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescr() {
        return descr;
    }

    @Override
    public void setDescr(String descr) {
        this.descr = descr;
    }

    @Override
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = new Date(effectiveDate.getTime());
    }

    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }

    
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = new Date(expirationDate.getTime());
    }
}
