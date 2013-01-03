/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community
 * License, Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.kuali.student.r2.lum.lrc.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.KeyEntityInfo;
import org.kuali.student.r2.lum.lrc.infc.ResultValue;
//import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResultValueInfo", propOrder = {"key", "typeKey", "stateKey",
        "name", "descr", "resultScaleKey", "numericValue", "value",
        "effectiveDate", "expirationDate", "meta", "attributes" })//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code
        
public class ResultValueInfo extends KeyEntityInfo implements ResultValue,
        Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String resultScaleKey;

    @XmlElement
    private String numericValue;

    @XmlElement
    private String value;

    @XmlElement
    private Date effectiveDate;

    @XmlElement
    private Date expirationDate;

//    TODO KSCM-372: Non-GWT translatable code
//    @XmlAnyElement
//    private List<Element> _futureElements;

    public ResultValueInfo() {
        super();
        resultScaleKey = null;
        numericValue = null;
        value = null;
        effectiveDate = null;
        expirationDate = null;
    }

    public ResultValueInfo(ResultValue resultValueInfo) {
        super(resultValueInfo);
        if (null != resultValueInfo) {
            this.resultScaleKey = resultValueInfo.getResultScaleKey();
            this.numericValue = resultValueInfo.getNumericValue();
            this.value = resultValueInfo.getValue();
            this.effectiveDate = new Date(resultValueInfo.getEffectiveDate()
                    .getTime());
            this.expirationDate = new Date(resultValueInfo.getExpirationDate()
                    .getTime());
        }
    }

    @Override
    public String getResultScaleKey() {
        return resultScaleKey;
    }

    public void setResultScaleKey(String resultScaleKey) {
        this.resultScaleKey = resultScaleKey;
    }

    @Override
    public String getNumericValue() {
        return numericValue;
    }

    public void setNumericValue(String numericValue) {
        this.numericValue = numericValue;
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
}
