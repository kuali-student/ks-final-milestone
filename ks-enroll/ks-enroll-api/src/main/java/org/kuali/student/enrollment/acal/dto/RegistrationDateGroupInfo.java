/*
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

package org.kuali.student.enrollment.acal.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.acal.infc.RegistrationDateGroup;
import org.kuali.student.r2.common.dto.DateRangeInfo;

import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegistrationDateGroupInfo", propOrder = {
		"termKey", "registrationDateRange",
		"classDateRange", "addDate", "dropDate", "finalExamDateRange",
		"gradingDateRange", "registrationDateDerivationGroup", 
                "_futureElements" })
public class RegistrationDateGroupInfo 
    implements RegistrationDateGroup, Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @XmlElement
    private RegistrationDateDerivationGroupInfo registrationDateDerivationGroup;
    
    @XmlElement
    private String termKey;
    
    @XmlElement
    private DateRangeInfo registrationDateRange;
    
    @XmlElement
    private DateRangeInfo classDateRange;
    
    @XmlElement
    private Date addDate;
    
    @XmlElement
    private Date dropDate;
    
    @XmlElement
    private DateRangeInfo finalExamDateRange;
    
    @XmlElement
    private DateRangeInfo gradingDateRange;
    
    @XmlAnyElement
    private List<Element> _futureElements;
    
    
    /**
     * Constructs a new RegistrationDateGroupInfo.
     */
    public RegistrationDateGroupInfo() {
    }
    
    /**
     * Constructs a new RegistrationDateGroupInfo from another
     * RegistrationDateGroupInfo.
     * 
     * @param dateGroup the RegistrationDateGroup to copy
     */
    public RegistrationDateGroupInfo(RegistrationDateGroup dateGroup) {
        
        if (dateGroup != null) {
            this.registrationDateDerivationGroup = new RegistrationDateDerivationGroupInfo(dateGroup.getRegistrationDateDerivationGroup());
            this.termKey = dateGroup.getTermKey();

            if (dateGroup.getRegistrationDateRange() != null) {
                this.registrationDateRange = new DateRangeInfo(dateGroup.getRegistrationDateRange());
            }

            if (dateGroup.getClassDateRange() != null) {
		this.classDateRange = new DateRangeInfo(dateGroup.getClassDateRange());
            }

            if (dateGroup.getAddDate() != null) {
		this.addDate = new Date(dateGroup.getAddDate().getTime());
            }

            if (dateGroup.getDropDate() != null) {
		this.dropDate = new Date(dateGroup.getDropDate().getTime());
            }
            
            if (dateGroup.getFinalExamDateRange() != null) {
		this.finalExamDateRange = new DateRangeInfo(dateGroup.getFinalExamDateRange());
            }

            if (dateGroup.getGradingDateRange() != null) {
                this.gradingDateRange = new DateRangeInfo(dateGroup.getGradingDateRange());
            }
	}
    }

    @Override
    public RegistrationDateDerivationGroupInfo getRegistrationDateDerivationGroup() {
        return registrationDateDerivationGroup;
    }
    
    public void setRegistrationDateDerivationGroup(RegistrationDateDerivationGroupInfo registrationDateDerivationGroup) {
        this.registrationDateDerivationGroup = registrationDateDerivationGroup;
    }
    
    @Override
    public String getTermKey() {
        return termKey;
    }
    
    public void setTermKey(String termKey) {
        this.termKey = termKey;
    }
    
    @Override
    public DateRangeInfo getRegistrationDateRange() {
        return registrationDateRange;
    }
    
    public void setRegistrationDateRange(DateRangeInfo registrationDateRange) {
        this.registrationDateRange = registrationDateRange;
    }
    
    @Override
    public DateRangeInfo getClassDateRange() {
        return classDateRange;
    }
    
    public void setClassDateRange(DateRangeInfo classDateRange) {
        this.classDateRange = classDateRange;
    }
    
    @Override
    public Date getAddDate() {
        return addDate;
    }
    
    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }
    
    @Override
    public Date getDropDate() {
        return dropDate;
    }
    
    public void setDropDate(Date dropDate) {
        this.dropDate = dropDate;
    }
    
    @Override
    public DateRangeInfo getFinalExamDateRange() {
        return finalExamDateRange;
    }
    
    public void setFinalExamDateRange(DateRangeInfo finalExamDateRange) {
        this.finalExamDateRange = finalExamDateRange;
    }
    
    @Override
    public DateRangeInfo getGradingDateRange() {
        return gradingDateRange;
    }
    
    public void setGradingDateRange(DateRangeInfo gradingDateRange) {
        this.gradingDateRange = gradingDateRange;
    }
}
