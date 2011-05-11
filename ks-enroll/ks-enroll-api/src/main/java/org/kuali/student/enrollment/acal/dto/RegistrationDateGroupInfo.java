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
@XmlType(name = "RegistrationDateGroupInfo", propOrder = {"registrationDateDerivationGroup", "termKey", "registrationDateRange", "classDateRange", "addDate", "dropDate", "finalExamDateRange", "gradingDateRange", "_futureElements"})

public class RegistrationDateGroupInfo implements RegistrationDateGroup, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private final RegistrationDateDerivationGroupInfo registrationDateDerivationGroup;

    @XmlElement
    private final String termKey;

    @XmlElement
    private final DateRangeInfo registrationDateRange;

    @XmlElement
    private final DateRangeInfo classDateRange;

    @XmlElement
    private final Date addDate;

    @XmlElement
    private final Date dropDate;

    @XmlElement
    private final DateRangeInfo finalExamDateRange;

    @XmlElement
    private final DateRangeInfo gradingDateRange;

    @XmlAnyElement
    private final List<Element> _futureElements;  

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
    public RegistrationDateGroupInfo(RegistrationDateGroup dateGroup) {
        this.registrationDateDerivationGroup = new RegistrationDateDerivationGroupInfo(dateGroup.getRegistrationDateDerivationGroup());
        this.termKey = dateGroup.getTermKey();
        this.registrationDateRange = new DateRangeInfo(dateGroup.getRegistrationDateRange());
        this.classDateRange = new DateRangeInfo(dateGroup.getClassDateRange());
        this.addDate = dateGroup.getAddDate();
        this.dropDate = dateGroup.getDropDate();
        this.finalExamDateRange = new DateRangeInfo(dateGroup.getFinalExamDateRange());
        this.gradingDateRange = new DateRangeInfo(dateGroup.getGradingDateRange());
        _futureElements = null;
    }

    @Override
    public RegistrationDateDerivationGroupInfo getRegistrationDateDerivationGroup() {
        return registrationDateDerivationGroup;
    }

    @Override
    public String getTermKey() {
        return termKey;
    }

    @Override
    public DateRangeInfo getRegistrationDateRange() {
        return registrationDateRange;
    }

    @Override
    public DateRangeInfo getClassDateRange() {
        return classDateRange;
    }

    @Override
    public Date getAddDate() {
        return addDate;
    }

    @Override
    public Date getDropDate() {
        return dropDate;
    }

    @Override
    public DateRangeInfo getFinalExamDateRange() {
        return finalExamDateRange;
    }

    @Override
    public DateRangeInfo getGradingDateRange() {
        return gradingDateRange;
    }
}
