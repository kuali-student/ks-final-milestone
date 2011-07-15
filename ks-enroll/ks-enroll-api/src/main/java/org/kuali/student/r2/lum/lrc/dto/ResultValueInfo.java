/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.lum.lrc.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.HasAttributesAndMetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.lum.lrc.infc.ResultValue;
import org.w3c.dom.Element;

@XmlType(name = "ResultValueInfo", propOrder = {"id", "name", "descr", "scaleKey", "rank", "effectiveDate", "expirationDate", "value", "meta", "attributes", "_futureElements"})
public class ResultValueInfo extends HasAttributesAndMetaInfo implements ResultValue, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlAttribute
    private String id;

    @XmlElement
    private String name;
    
    @XmlElement
    private RichTextInfo descr;
    
    @XmlElement
    private String value;

    @XmlElement
    private String scaleKey;

    @XmlElement
    private Float rank;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

    @XmlAnyElement
    private List<Element> _futureElements;

    public void setId(String id) {
        this.id = id;
    }

    public ResultValueInfo() {
        super();
        id = null;
        name = null;
        descr = null;        
        value = null;
        scaleKey = null;
        rank = null;
        effectiveDate = null;
        expirationDate = null;
    }

    public ResultValueInfo(ResultValue resultValueInfo) {
        super(resultValueInfo);
        if (null != resultValueInfo) {
            this.name = resultValueInfo.getName();
            this.descr = new RichTextInfo( resultValueInfo.getDescr());            
            this.value = resultValueInfo.getValue();
            this.scaleKey = resultValueInfo.getScaleKey();
            this.rank = (null != resultValueInfo.getRank()) ? resultValueInfo.getRank().floatValue() : null;
            this.effectiveDate = new Date(resultValueInfo.getEffectiveDate().getTime());
            this.expirationDate = new Date(resultValueInfo.getExpirationDate().getTime());
            this.id = resultValueInfo.getId();
        }
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getScaleKey() {
        return scaleKey;
    }

    @Override
    public Float getRank() {
        return rank;
    }

    @Override
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public RichTextInfo getDescr() {
        return descr;
    }

    public List<Element> get_futureElements() {

        return _futureElements;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescr(RichTextInfo descr) {
        this.descr = descr;
    }
    
    public void setScaleKey(String scaleKey) {
        this.scaleKey = scaleKey;
    }

    public void setRank(Float rank) {
        this.rank = rank;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void set_futureElements(List<Element> _futureElements) {
        this._futureElements = _futureElements;
    }
}
