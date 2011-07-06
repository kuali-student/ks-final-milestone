/*
 * Copyright 2009 The Kuali Foundation
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
package org.kuali.student.r2.lum.course.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.CurrencyAmountInfo;
import org.kuali.student.r2.common.dto.HasAttributesAndMetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.lum.course.infc.CourseFee;
import org.w3c.dom.Element;

/**
 * Information about a fee related to a course.
 *
 * @Author KSContractMojo
 * @Author Daniel Epstein
 * @Since Mon Jul 26 14:12:33 EDT 2010
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/courseFeeInfo+Structure">CourseFeeInfo</>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseFeeInfo", propOrder = {"feeType", "rateType", "feeAmounts",
        "descr", "id", "meta", "attributes", "_futureElements"})
public class CourseFeeInfo extends HasAttributesAndMetaInfo implements CourseFee, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String feeType;

    @XmlElement
    private String rateType;

    @XmlElement
    private List<CurrencyAmountInfo> feeAmounts;

    @XmlElement
    private RichTextInfo descr;

    @XmlAttribute
    private String id;

    @XmlAnyElement
    private List<Element> _futureElements;

    public CourseFeeInfo() {
        this.feeType = null;
        this.rateType = null;
        this.feeAmounts = new ArrayList<CurrencyAmountInfo>();
        this.descr = null;
        this.id = null;
        this._futureElements = null;
    }
    
    public CourseFeeInfo(CourseFee fee) {
        super(fee);
        
        if(null == fee) return;
        
        this.feeType = fee.getFeeType();
        this.rateType = fee.getRateType();
        this.feeAmounts = (null != fee.getFeeAmounts()) ? new ArrayList<CurrencyAmountInfo>((List<CurrencyAmountInfo>)fee.getFeeAmounts()) : null;
        this.id = fee.getId();
        this.descr = (null != fee.getDescr()) ? new RichTextInfo(fee.getDescr() ) : null;
    }
    
    @Override
    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    @Override
    public String getRateType() {
        return rateType;
    }

    public void setRateType(String rateType) {
        this.rateType = rateType;
    }

    @Override
    public List<CurrencyAmountInfo> getFeeAmounts() {
        if (feeAmounts == null) {
            feeAmounts = new ArrayList<CurrencyAmountInfo>(0);
        }
        return feeAmounts;
    }

    public void setFeeAmounts(List<CurrencyAmountInfo> feeAmounts) {
        this.feeAmounts = feeAmounts;
    }

    @Override
    public RichTextInfo getDescr() {
        return descr;
    }

    public void setDescr(RichTextInfo descr) {
        this.descr = descr;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}