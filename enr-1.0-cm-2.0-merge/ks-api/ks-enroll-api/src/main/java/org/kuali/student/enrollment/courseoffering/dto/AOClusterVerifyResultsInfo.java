/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.enrollment.courseoffering.dto;

import org.kuali.student.enrollment.courseoffering.infc.AOClusterVerifyResults;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.infc.ValidationResult;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sri komandur@uw.edu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AOClusterVerifyResultsInfo", propOrder = {
        "existingRGCount", "validRGCount", "isRegenerationNeeded", "validationResults",
        "_futureElements"})

public class AOClusterVerifyResultsInfo implements AOClusterVerifyResults {


    private static final long serialVersionUID = 1L;

    @XmlElement
    private Integer existingRGCount;

    @XmlElement
    private Integer validRGCount;

    @XmlElement
    private Boolean isRegenerationNeeded;

    @XmlElement
    private List<ValidationResultInfo> validationResults;

    @XmlAnyElement
    private List<Element> _futureElements;

    public AOClusterVerifyResultsInfo() {
    }

    public AOClusterVerifyResultsInfo(AOClusterVerifyResults aoClusterVerifyResults) {

        this.existingRGCount = aoClusterVerifyResults.getExistingRGCount();
        this.validRGCount = aoClusterVerifyResults.getValidRGCount();
        this.isRegenerationNeeded = aoClusterVerifyResults.getIsRegenerationNeeded();

        if (aoClusterVerifyResults.getValidationResults() != null) {
            this.validationResults = new ArrayList<ValidationResultInfo>();
            for (ValidationResult validResult : aoClusterVerifyResults.getValidationResults()) {
                this.validationResults.add(new ValidationResultInfo(validResult));
            }
        }
    }

    @Override
    public Integer getExistingRGCount() {
        return existingRGCount;
    }

    public void setExistingRGCount(Integer existingRGCount) {
        this.existingRGCount = existingRGCount;
    }

    @Override
    public Integer getValidRGCount() {
        return validRGCount;
    }

    public void setValidRGCount(Integer validRGCount) {
        this.validRGCount = validRGCount;
    }

    @Override
    public Boolean getIsRegenerationNeeded() {
        return isRegenerationNeeded;
    }

    public void setIsRegenerationNeeded(Boolean regenerationNeeded) {
        isRegenerationNeeded = regenerationNeeded;
    }

    @Override
    public List<ValidationResultInfo> getValidationResults() {
        return validationResults;
    }

    public void setValidationResults(List<ValidationResultInfo> validationResults) {
        this.validationResults = validationResults;
    }
}
