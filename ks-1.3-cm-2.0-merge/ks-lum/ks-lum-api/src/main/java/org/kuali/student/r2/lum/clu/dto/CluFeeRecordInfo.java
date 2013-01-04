/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.lum.clu.dto;

import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.CurrencyAmountInfo;
import org.kuali.student.r2.common.dto.HasAttributesAndMetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.infc.CurrencyAmount;
import org.kuali.student.r2.lum.clu.infc.AffiliatedOrg;
import org.kuali.student.r2.lum.clu.infc.CluFeeRecord;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CluFeeRecordInfo", propOrder = {"id", "feeType", "rateType", "feeAmounts", "affiliatedOrgs", "descr", "attributes", "meta" })//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code})
public class CluFeeRecordInfo extends HasAttributesAndMetaInfo implements CluFeeRecord, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlAttribute
    private String id;

    @XmlElement
    private RichTextInfo descr;

    @XmlElement
    private String feeType;

    @XmlElement
    private String rateType;

    @XmlElement
    private List<CurrencyAmountInfo> feeAmounts;

    @XmlElement
    private List<AffiliatedOrgInfo> affiliatedOrgs;

//    TODO KSCM-372: Non-GWT translatable code
//    @XmlAnyElement
//    private List<Element> _futureElements;


    public CluFeeRecordInfo() {

    }

    public CluFeeRecordInfo(CluFeeRecord cluFeeRecord) {
        super(cluFeeRecord);
        if (null != cluFeeRecord) {
            this.id = cluFeeRecord.getId();
            this.descr = cluFeeRecord.getDescr();
            this.feeType = cluFeeRecord.getFeeType();
            this.rateType = cluFeeRecord.getRateType();
            this.feeAmounts = new ArrayList<CurrencyAmountInfo>();
            for (CurrencyAmount currencyAmount : cluFeeRecord.getFeeAmounts()) {
                this.feeAmounts.add(new CurrencyAmountInfo(currencyAmount));
            }
            this.affiliatedOrgs = new ArrayList<AffiliatedOrgInfo>();
            for (AffiliatedOrg affiliatedOrg : cluFeeRecord.getAffiliatedOrgs()) {
                this.affiliatedOrgs.add(new AffiliatedOrgInfo(affiliatedOrg));
            }
        }
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
            feeAmounts = new ArrayList<CurrencyAmountInfo>();
        }
        return feeAmounts;
    }

    public void setFeeAmounts(List<CurrencyAmountInfo> feeAmounts) {
        this.feeAmounts = feeAmounts;
    }

    @Override
    public List<AffiliatedOrgInfo> getAffiliatedOrgs() {
        if (affiliatedOrgs == null) {
            affiliatedOrgs = new ArrayList<AffiliatedOrgInfo>(0);
        }
        return affiliatedOrgs;
    }

    public void setAffiliatedOrgs(List<AffiliatedOrgInfo> affiliatedOrgs) {
        this.affiliatedOrgs = affiliatedOrgs;
    }

    @Override
    public RichTextInfo getDescr() {
        return descr;
    }

    public void setDescr(RichTextInfo descr) {
        this.descr = descr;
    }

}