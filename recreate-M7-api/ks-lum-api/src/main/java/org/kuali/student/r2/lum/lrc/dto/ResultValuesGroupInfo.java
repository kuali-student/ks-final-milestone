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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.KeyEntityInfo;
import org.kuali.student.r2.lum.lrc.infc.ResultValuesGroup;

/**
 * Detailed information about a group of result values.
 * 
 * This grouping can be expressed two ways:
 * (1) as an explicit list of values, i.e. A, B, C, etc
 * (2) as a range of numeric values 1-100 with .01 increments
 * 
 * It may also combine the two approaches.  (3) A numeric range for a
 * grade 1-100 but also allow for grades like I for incomplete.
 * 
 * Note: This object has been renamed from R1, previously it was
 * called ResultComponent.
 * 
 * @Author sambit
 * @Since Tue Apr 21 13:47:47 PDT 2009
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GradeValuesGroupInfo", propOrder = { 
        "key", "typeKey", "stateKey", "name", "descr", "resultScaleKey", 
        "resultValueKeys", "resultValueRange", "effectiveDate",
        "expirationDate", "meta", "attributes" , "_futureElements" }) 

public class ResultValuesGroupInfo 
        extends KeyEntityInfo 
        implements ResultValuesGroup {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String resultScaleKey;
    @XmlElement
    private List<String> resultValueKeys;
    @XmlElement
    private ResultValueRangeInfo resultValueRange;
    @XmlElement
    private Date effectiveDate;
    @XmlElement
    private Date expirationDate;
    @XmlAnyElement
    private List<Object> _futureElements;  

    public ResultValuesGroupInfo() {
        resultScaleKey = null;
        resultValueKeys = new ArrayList<String>();
        resultValueRange = null;
        effectiveDate = null;
        expirationDate = null;
    }

    public ResultValuesGroupInfo(ResultValuesGroup orig) {
        super(orig);
        if (null != orig) {
            this.resultScaleKey = orig.getResultScaleKey();
            this.resultValueKeys = new ArrayList<String>(orig.getResultValueKeys());
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
    public String getResultScaleKey() {
        return resultScaleKey;
    }

    public void setResultScaleKey(String resultScaleKey) {
        this.resultScaleKey = resultScaleKey;
    }

    @Override
    public List<String> getResultValueKeys() {
        if (this.resultValueKeys == null) {
            this.resultValueKeys = new ArrayList<String>();
        }
        return resultValueKeys;
    }

    public void setResultValueKeys(List<String> resultValueKeys) {
        this.resultValueKeys = resultValueKeys;
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
