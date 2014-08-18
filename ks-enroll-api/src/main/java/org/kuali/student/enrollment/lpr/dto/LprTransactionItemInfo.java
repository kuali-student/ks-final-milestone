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

import org.kuali.student.enrollment.lpr.infc.LprTransactionItem;
import org.kuali.student.enrollment.lpr.infc.LprTransactionItemRequestOption;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LprTransactionItemInfo", propOrder = {
                "id", "typeKey", "stateKey", "name", "descr",
                "transactionId", "personId", 
                "newLuiId",
                "existingLprId", "resultValuesGroupKeys",
                "requestOptions", 
                "resultingLprId",
                "validationResults",
                "crossList",
                "meta", "attributes", "_futureElements"})

public class LprTransactionItemInfo 
    extends IdEntityInfo 
    implements LprTransactionItem, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String transactionId;

    @XmlElement
    private String personId;

    @XmlElement
    private String newLuiId;

    @XmlElement
    private String existingLprId;

    @XmlElement
    private List<String> resultValuesGroupKeys;

    @XmlElement
    private List<LprTransactionItemRequestOptionInfo> requestOptions;

    @XmlElement
    private String resultingLprId;
    
    @XmlElement
    private List<ValidationResultInfo> validationResults;

    @XmlElement
    private String crossList;

    @XmlAnyElement
    private List<Element> _futureElements;


    public LprTransactionItemInfo() {
        super();
    }

    public LprTransactionItemInfo(LprTransactionItem lprTransactionItem) {

        super(lprTransactionItem);
        if (null != lprTransactionItem) {
            this.transactionId = lprTransactionItem.getTransactionId();
            this.newLuiId = lprTransactionItem.getResultingLprId();

            this.personId = lprTransactionItem.getPersonId();
            this.newLuiId = lprTransactionItem.getNewLuiId();
            this.existingLprId = lprTransactionItem.getExistingLprId();

            this.crossList = lprTransactionItem.getCrossList();
            
            this.requestOptions = new ArrayList<LprTransactionItemRequestOptionInfo>();
            if (null != lprTransactionItem.getRequestOptions()) {
                for (LprTransactionItemRequestOption reqOp : lprTransactionItem.getRequestOptions()) {
                    this.requestOptions.add(new LprTransactionItemRequestOptionInfo(reqOp));
                }
            }

            this.resultValuesGroupKeys = new ArrayList<String>();
            if (null != lprTransactionItem.getResultValuesGroupKeys()) {
                resultValuesGroupKeys.addAll(lprTransactionItem.getResultValuesGroupKeys());
            }

            this.validationResults = new ArrayList<ValidationResultInfo>();
            if (null != lprTransactionItem.getValidationResults()) {
                // Make a deep copy
                for (ValidationResultInfo info: lprTransactionItem.getValidationResults()) {
                    this.validationResults.add(new ValidationResultInfo(info));
                }
            }
        }
    }

    @Override
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public String getResultingLprId() {
        return resultingLprId;
    }

    public void setResultingLprId(String resultingLprId) {
        this.resultingLprId = resultingLprId;
    }

    @Override
    public String getNewLuiId() {
        return newLuiId;
    }

    public void setNewLuiId(String luiId) {
        this.newLuiId = luiId;
    }

    @Override
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    @Override
    public List<LprTransactionItemRequestOptionInfo> getRequestOptions() {
        if (requestOptions == null) {
            requestOptions = new ArrayList<LprTransactionItemRequestOptionInfo> ();
        }
        return requestOptions;
    }

    public void setRequestOptions(List<LprTransactionItemRequestOptionInfo> requestOptions) {
        this.requestOptions = requestOptions;
    }

    public void setExistingLprId(String existingLprId) {
        this.existingLprId = existingLprId;
    }

    @Override
    public String getExistingLprId() {
        return existingLprId;
    }

    @Override
    public List<String> getResultValuesGroupKeys() {
        if (this.resultValuesGroupKeys == null) {
            this.resultValuesGroupKeys = new ArrayList<String> ();
        }
        return resultValuesGroupKeys;
    }

    public void setResultValuesGroupKeys(List<String> resultValuesGroupKeys) {
        this.resultValuesGroupKeys = resultValuesGroupKeys;
    }

    @Override
    public List<ValidationResultInfo> getValidationResults() {
        if(validationResults == null){
            validationResults = new ArrayList<ValidationResultInfo>();
        }
        return validationResults;
    }

    public void setValidationResults(List<ValidationResultInfo> validationResults) {
        this.validationResults = validationResults;
    }

    @Override
    public String getCrossList() { return crossList; }

    public void setCrossList(String crossList) { this.crossList = crossList; }
}
