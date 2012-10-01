/*
 * Copyright 2009 The Kuali Foundation Licensed under the Educational Community
 * License, Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.kuali.student.r2.lum.course.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.CurrencyAmountInfo;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.infc.CurrencyAmount;
import org.kuali.student.r2.lum.course.infc.CourseFee;
import org.w3c.dom.Element;

@XmlType(name = "CourseFeeInfo", propOrder = {"id",
    "typeKey", "stateKey", 
    "name", "descr",
    "feeType", "rateType", "feeAmounts", "meta", "attributes", "_futureElements"})
@XmlAccessorType(XmlAccessType.FIELD)
public class CourseFeeInfo extends IdEntityInfo implements CourseFee, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String feeType;

    @XmlElement
    private String rateType;

    @XmlElement
    private List<CurrencyAmountInfo> feeAmounts;
    
    @XmlAnyElement
    private List<Element> _futureElements;

    public CourseFeeInfo() {

    }

    public CourseFeeInfo(CourseFee courseFee) {
        super(courseFee);
        if (courseFee != null) {
            this.feeType = courseFee.getFeeType();
            this.rateType = courseFee.getRateType();
            List<CurrencyAmountInfo> feeAmounts = new ArrayList<CurrencyAmountInfo>();
            for (CurrencyAmount courseAmount : courseFee.getFeeAmounts()) {
                feeAmounts.add(new CurrencyAmountInfo(courseAmount));
            }
            this.feeAmounts = feeAmounts;
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

}