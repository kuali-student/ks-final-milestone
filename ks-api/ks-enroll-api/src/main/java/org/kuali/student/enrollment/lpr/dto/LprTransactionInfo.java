/*
 * Copyright 2011 The Kuali Foundation 
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

package org.kuali.student.enrollment.lpr.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.lpr.infc.LprTransaction;
import org.kuali.student.enrollment.lpr.infc.LprTransactionItem;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.w3c.dom.Element;

/**
 * @author Kuali Student Team (sambit)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LprTransactionInfo", propOrder = {
                "id", "typeKey", "stateKey", "name", "descr",
                "requestingPersonId", 
                "atpId",
                "lprTransactionItems",
                "validationResults",
                "meta", "attributes", "_futureElements"})

public class LprTransactionInfo 
    extends IdEntityInfo 
    implements LprTransaction, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String requestingPersonId;

    @XmlElement
    private String atpId;

    @XmlElement
    private List<LprTransactionItemInfo> lprTransactionItems;

    @XmlElement
    private List<ValidationResultInfo> validationResults;

    @XmlAnyElement
    private List<Element> _futureElements;


    public LprTransactionInfo() {
    }

    public LprTransactionInfo(LprTransaction input) {
        super(input);
        if (input == null) {
            return;
        }
        this.requestingPersonId = input.getRequestingPersonId();
        this.atpId = input.getAtpId();
        this.lprTransactionItems = new ArrayList<LprTransactionItemInfo>();
        if (input.getLprTransactionItems()!= null) {
            // Make a deep copy
            for (LprTransactionItem info: input.getLprTransactionItems()) {
                this.lprTransactionItems.add(new LprTransactionItemInfo(info));
            }
        }
        this.validationResults = new ArrayList<ValidationResultInfo>();
        if (input.getValidationResults() != null) {
            // Make a deep copy
            for (ValidationResult info: input.getValidationResults()) {
                this.validationResults.add(new ValidationResultInfo(info));
            }
        }
    }

    @Override
    public String getRequestingPersonId() {
        return requestingPersonId;
    }

    public void setRequestingPersonId(String requestingPersonId) {
        this.requestingPersonId = requestingPersonId;
    }

    @Override
    public String getAtpId() {
        return atpId;
    }

    public void setAtpId(String atpId) {
        this.atpId = atpId;
    }
    
    
    @Override
    public List<LprTransactionItemInfo> getLprTransactionItems() {
        if (lprTransactionItems == null) {
            lprTransactionItems = new ArrayList<LprTransactionItemInfo>();
        }
        return lprTransactionItems;
    }

    public void setLprTransactionItems(List<LprTransactionItemInfo> lprTransactionItems) {
        this.lprTransactionItems = lprTransactionItems;
    }

    @Override
    public List<ValidationResultInfo> getValidationResults() {
        if (validationResults == null) {
            validationResults = new ArrayList<ValidationResultInfo>();
        }
        return validationResults;
    }

    public void setValidationResults(List<ValidationResultInfo> validationResults) {
        this.validationResults = validationResults;
    }

}
