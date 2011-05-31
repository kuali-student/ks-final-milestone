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
import org.kuali.student.r2.lum.lrc.infc.ResultScale;
import org.w3c.dom.Element;

@XmlType(name = "ResultScaleInfo", propOrder = {"key", "name", "descr", "effectiveDate", "expirationDate", "meta", "attributes", "_futureElements"})
public class ResultScaleInfo extends HasAttributesAndMetaInfo implements ResultScale, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlAttribute
    private String key;

    @XmlElement
    private String name;

    @XmlElement
    private RichTextInfo descr;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

    @XmlAnyElement
    private List<Element> _futureElements;

    public ResultScaleInfo() {
        super();
        key = null;
        name = null;
        descr = null;
        effectiveDate = null;
        expirationDate = null;
    }

    public ResultScaleInfo(ResultScale resultScale) {
        super(resultScale);
        if (null != resultScale) {
            this.key = resultScale.getKey();
            this.name = resultScale.getName();
            this.descr = new RichTextInfo(resultScale.getDescr());
            this.effectiveDate = new Date(resultScale.getEffectiveDate().getTime());
            this.expirationDate = new Date(resultScale.getExpirationDate().getTime());
        }
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override    
    public String getName() {
        return name;
    }

    @Override    
    public RichTextInfo getDescr() {
        return descr;
    }

    @Override    
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    @Override    
    public Date getExpirationDate() {
        return expirationDate;
    }
    
    public List<Element> get_futureElements() {
        return _futureElements;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescr(RichTextInfo descr) {
        this.descr = descr;
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
