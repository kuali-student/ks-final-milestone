/*
 * Copyright 2012 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.r2.core.fee.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.core.fee.infc.EnrollmentFee;
import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.w3c.dom.Element;

/*
 * The name "EnrollmentFee" is a temporary name to distinguish this
 * from Fees in CM.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EnrollmentFeeInfo", propOrder = { 
                "id", "typeKey", "stateKey", "descr", 
                "amount", "orgId", "refObjectURI",
                "refObjectId", 
		"meta", "attributes", "_futureElements" })

public class EnrollmentFeeInfo 
    extends IdNamelessEntityInfo 
    implements EnrollmentFee, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private RichTextInfo descr;

    @XmlElement
    private EnrollmentFeeAmountInfo amount;

    @XmlElement
    private String orgId;

    @XmlElement
    private String refObjectURI;

    @XmlElement
    private String refObjectId;
	
    @XmlAnyElement
    private List<Element> _futureElements;


    /**
     * Constructs a new FeeInfo.
     */
    public EnrollmentFeeInfo() {
    }

    /**
     * Constructs a new FeeInfo from another Fee.
     * 
     * @param fee the Fee to copy
     */
    public EnrollmentFeeInfo(EnrollmentFee fee) {
        super(fee);

        if (fee != null) {
            this.descr = new RichTextInfo(fee.getDescr());
            this.amount = new EnrollmentFeeAmountInfo(fee.getAmount());
            this.orgId = fee.getOrgId();
            this.refObjectURI = fee.getRefObjectURI();
            this.refObjectId = fee.getRefObjectId();
        }
    }

    @Override
    public RichTextInfo getDescr() {
        return descr;
    }

    public void setDescr(RichTextInfo descr) {
        this.descr = descr;
    }

    @Override
    public EnrollmentFeeAmountInfo getAmount() {
        return amount;
    }
    
    public void setAmount(EnrollmentFeeAmountInfo amount) {
        this.amount = amount;;
    }

    @Override
    public String getOrgId() {
        return orgId;
    }
    
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @Override
    public String getRefObjectURI() {
        return refObjectURI;
    }
    
    public void setRefObjectURI(String refObjectURI) {
        this.refObjectURI = refObjectURI;
    }

    @Override
    public String getRefObjectId() {
        return refObjectId;
    }
    
    public void setRefObjectId(String refObjectId) {
        this.refObjectId = refObjectId;
    }
}    
