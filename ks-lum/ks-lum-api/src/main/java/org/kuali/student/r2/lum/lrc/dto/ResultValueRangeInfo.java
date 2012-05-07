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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.lum.lrc.infc.ResultValueRange;
//import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResultValueRangeInfo", propOrder = {
        "minValue", "maxValue", "increment" })//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code

public class ResultValueRangeInfo implements ResultValueRange, Serializable {
    private static final long serialVersionUID = 1L;

    @XmlElement
    private String minValue;

    @XmlElement
    private String maxValue;

    @XmlElement
    private String increment;

//    TODO KSCM-372: Non-GWT translatable code
//    @XmlAnyElement
//    private List<Element> _futureElements;

    public ResultValueRangeInfo() {
        minValue = null;
        maxValue = null;
        increment = null;
    }

    public ResultValueRangeInfo(ResultValueRange resultValueRangeInfo) {
        if (null != resultValueRangeInfo) {
            this.minValue = resultValueRangeInfo.getMinValue();
            this.maxValue = resultValueRangeInfo.getMaxValue();
            this.increment = resultValueRangeInfo.getIncrement();
        }
    }

    @Override
    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    @Override
    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public String getIncrement() {
        return increment;
    }

    public void setIncrement(String increment) {
        this.increment = increment;
    }
}
