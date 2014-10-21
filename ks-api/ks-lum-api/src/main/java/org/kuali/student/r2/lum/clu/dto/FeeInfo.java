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
package org.kuali.student.r2.lum.clu.dto;

import org.kuali.student.r2.common.dto.CurrencyAmountInfo;
import org.kuali.student.r2.common.dto.HasAttributesAndMetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.infc.CurrencyAmount;
import org.kuali.student.r2.lum.clu.infc.Fee;
//import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FeeInfo", propOrder = {"feeType", "rateType", "feeAmounts",
        "descr", "key", "meta", "attributes" , "_futureElements" }) 
public class FeeInfo extends HasAttributesAndMetaInfo implements Fee, Serializable {

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
    private String key;
    @XmlAnyElement
    private List<Object> _futureElements;  

    public FeeInfo() {
        this.feeAmounts = new ArrayList<CurrencyAmountInfo>();
    }

    public FeeInfo(Fee fee) {
        super(fee);
        if (null != fee) {
            this.feeType = fee.getFeeType();
            this.rateType = fee.getRateType();
            this.feeAmounts = new ArrayList<CurrencyAmountInfo>();
            for (CurrencyAmount currencyAmount : fee.getFeeAmounts()) {
                this.feeAmounts.add(new CurrencyAmountInfo(currencyAmount));
            }
            this.key = fee.getKey();
            this.descr = (null != fee.getDescr()) ? new RichTextInfo(fee.getDescr()) : null;
        }
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
    public String getKey() {
        return key;
    }

    public void setId(String key) {
        this.key = key;
    }
}