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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.KeyEntityInfo;
import org.kuali.student.r2.lum.lrc.infc.ResultScale;
//import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResultScaleInfo", propOrder = {
        "key", "typeKey", "stateKey", "name", "descr", 
        "resultValueRange", "effectiveDate", "expirationDate", 
        "meta", "attributes" })//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code})

public class ResultScaleInfo 
        extends KeyEntityInfo 
        implements ResultScale, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private ResultValueRangeInfo resultValueRange;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

//    TODO KSCM-372: Non-GWT translatable code
//    @XmlAnyElement
//    private List<Element> _futureElements;

    public ResultScaleInfo() {
        super();
    }

    public ResultScaleInfo(ResultScale orig) {
        super(orig);
        if (null != orig) {
            if (orig.getResultValueRange() != null) {
                this.resultValueRange = new ResultValueRangeInfo(orig.getResultValueRange());
            }
            if (orig.getEffectiveDate() != null) {
                this.effectiveDate = new Date(orig.getEffectiveDate().getTime());
            }
            if (orig.getExpirationDate() != null) {
                this.expirationDate = new Date(orig.getExpirationDate().getTime());
            }
        }
    }

    @Override    
    public ResultValueRangeInfo getResultValueRange() {
        return resultValueRange;
    }

    public void setResultValueRange(ResultValueRangeInfo resultValueRange) {
        this.resultValueRange = resultValueRange;
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
