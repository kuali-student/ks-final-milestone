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
package org.kuali.student.enrollment.lpr.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.lpr.infc.LprTransactionItemResult;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.w3c.dom.Element;


/**
 * Updated by Red Team
 * @author Kuali Student Team (sambitpatnaik)
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LprTransactionItemResultInfo", propOrder = {"resultingLprId", "validationResults", "_futureElements"})
public class LprTransactionItemResultInfo implements LprTransactionItemResult, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String resultingLprId;

    @XmlElement
    private List<ValidationResultInfo> validationResults;

    @XmlAnyElement
    private List<Element> _futureElements;


    public LprTransactionItemResultInfo() {
    }


    public LprTransactionItemResultInfo(LprTransactionItemResult result) {
        this.resultingLprId = result.getResultingLprId();
        this.validationResults = result.getValidationResults();
    }

    public void setResultingLprId(String resultingLprId) {
        this.resultingLprId = resultingLprId;
    }

    @Override
    public String getResultingLprId() {
        return resultingLprId;
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("LprTransactionItemResultInfo [resultingLprId=");
        builder.append(resultingLprId);
        builder.append(", validationResults=");
        builder.append(validationResults.toString()); // May improve later on
        builder.append("]");
        return builder.toString();
    }


}
