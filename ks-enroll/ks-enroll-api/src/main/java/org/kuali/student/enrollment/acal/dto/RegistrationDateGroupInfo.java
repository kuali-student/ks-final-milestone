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

import org.kuali.student.enrollment.acal.infc.RegistrationDateDerivationGroup;
import org.kuali.student.enrollment.acal.infc.RegistrationDateGroup;
import org.kuali.student.r2.common.dto.DateRangeInfo;
import org.kuali.student.r2.common.infc.DateRange;
import org.w3c.dom.Element;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegistrationDateGroupInfo", propOrder = {"registrationDateDerivationGroup", "termKey", "registrationDateRange", "classDateRange", "addDate", "dropDate", "finalExamDateRange", "gradingDateRange", "_futureElements"})

public class RegistrationDateGroupInfo implements RegistrationDateGroup, Serializable {

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

    public RegistrationDateGroupInfo newInstance() {
        return new RegistrationDateGroupInfo();
    }
    
    public RegistrationDateGroupInfo getInstance(RegistrationDateGroup dateGroup) {
        return new RegistrationDateGroupInfo(dateGroup);
    }
    
    public RegistrationDateGroupInfo getInstance(RegistrationDateDerivationGroup registrationDateDerivationGroup, String termKey,
                                                    DateRange registrationDateRange, DateRange classDateRange,
                                                    Date addDate, Date dropDate,
										            DateRange finalExamDateRange, DateRange gradingDateRange) {
        return new RegistrationDateGroupInfo(registrationDateDerivationGroup, termKey, registrationDateRange, classDateRange, addDate, dropDate, finalExamDateRange, gradingDateRange);
    }
    
    private RegistrationDateGroupInfo() {
        registrationDateDerivationGroup = null;
        termKey = null;
        registrationDateRange = null;
        classDateRange = null;
        addDate = null;
        dropDate = null;
        finalExamDateRange = null;
        gradingDateRange = null;
        _futureElements = null;
    }

    /**
     * Constructs a new RegistrationDateGroupInfo from another
     * RegistrationDateGroupInfo.
     *
     * @param dateGroup the RegistrationDateGroup to copy
     */
    private RegistrationDateGroupInfo(RegistrationDateGroup dateGroup) {
        this.registrationDateDerivationGroup = RegistrationDateDerivationGroupInfo.getInstance(dateGroup.getRegistrationDateDerivationGroup());
        this.termKey = dateGroup.getTermKey();
        this.registrationDateRange = new DateRangeInfo(dateGroup.getRegistrationDateRange());
        this.classDateRange = new DateRangeInfo(dateGroup.getClassDateRange());
        this.addDate = dateGroup.getAddDate();
        this.dropDate = dateGroup.getDropDate();
        this.finalExamDateRange = new DateRangeInfo(dateGroup.getFinalExamDateRange());
        this.gradingDateRange = new DateRangeInfo(dateGroup.getGradingDateRange());
        _futureElements = null;
    }

    private RegistrationDateGroupInfo(RegistrationDateDerivationGroup registrationDateDerivationGroup, String termKey,
                                        DateRange registrationDateRange, DateRange classDateRange,
                                        Date addDate, Date dropDate,
                                        DateRange finalExamDateRange, DateRange gradingDateRange) {
        this.registrationDateDerivationGroup = RegistrationDateDerivationGroupInfo.getInstance(registrationDateDerivationGroup);
        this.termKey = termKey;
        this.registrationDateRange = new DateRangeInfo(registrationDateRange);
        this.classDateRange = new DateRangeInfo(classDateRange);
        this.addDate = new Date(addDate.getTime());
        this.dropDate = new Date(dropDate.getTime());
        this.finalExamDateRange = new DateRangeInfo(finalExamDateRange);
        this.gradingDateRange = new DateRangeInfo(gradingDateRange);
        _futureElements = null;
    }

    @Override
    public RegistrationDateDerivationGroupInfo getRegistrationDateDerivationGroup() {
        return registrationDateDerivationGroup;
    }

    @Override
    public void setRegistrationDateDerivationGroup(RegistrationDateDerivationGroup registrationDateDerivationGroup) {
        this.registrationDateDerivationGroup = RegistrationDateDerivationGroupInfo.getInstance(registrationDateDerivationGroup);
    }

    @Override
    public String getTermKey() {
        return termKey;
    }

    @Override
    public void setTermKey(String termKey) {
        this.termKey = termKey;
    }

    @Override
    public DateRangeInfo getRegistrationDateRange() {
        return registrationDateRange;
    }

    @Override
    public void setRegistrationDateRange(DateRange registrationDateRange) {
        this.registrationDateRange = DateRangeInfo.getInstance(registrationDateRange);
    }

    @Override
    public DateRangeInfo getClassDateRange() {
        return classDateRange;
    }

    @Override
    public void setClassDateRange(DateRange classDateRange) {
        this.classDateRange = DateRangeInfo.getInstance(classDateRange);
    }

    @Override
    public Date getAddDate() {
        return addDate;
    }

    @Override
    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    @Override
    public Date getDropDate() {
        return dropDate;
    }

    @Override
    public void setDropDate(Date dropDate) {
        this.dropDate = dropDate;
    }

    @Override
    public DateRangeInfo getFinalExamDateRange() {
        return finalExamDateRange;
    }

    @Override
    public void setFinalExamDateRange(DateRange finalExamDateRange) {
        this.finalExamDateRange = DateRangeInfo.getInstance(finalExamDateRange);
    }

    @Override
    public DateRangeInfo getGradingDateRange() {
        return gradingDateRange;
    }

    @Override
    public void setGradingDateRange(DateRange gradingDateRange) {
        this.gradingDateRange = DateRangeInfo.getInstance(gradingDateRange);
    }
}
