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

package org.kuali.student.core.classII.academiccalendar.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.common.infc.ModelBuilder;
import org.kuali.student.r2.common.dto.DateRangeInfo;
import org.kuali.student.core.classII.academiccalendar.infc.RegistrationDateGroup;


/**
 * A cluster of hardened dates pertinent to an academic term.
 *
 * @Author tom
 * @Since Tue Apr 05 14:22:34 EDT 2011
 */ 

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

    /**
     * The builder class for this DateInfo.
     */
    public static class Builder implements ModelBuilder<RegistrationDateGroupInfo>, RegistrationDateGroup {
        private RegistrationDateDerivationGroupInfo registrationDateDerivationGroup;
        private String termKey;
        private DateRangeInfo registrationDateRange;
        private DateRangeInfo classDateRange;
        private Date addDate;
        private Date dropDate;
        private DateRangeInfo finalExamDateRange;
        private DateRangeInfo gradingDateRange;

        /**
         * Constructs a new builder.
         */
        public Builder() {
        }

        /**
         *  Constructs a new builder initialized from another
         *  RegistrationDateGroup.
         */
        public Builder(RegistrationDateGroup dateGroup) {
            registrationDateDerivationGroup = new RegistrationDateDerivationGroupInfo(dateGroup.getRegistrationDateDerivationGroup());
            termKey = dateGroup.getTermKey();
            registrationDateRange = new DateRangeInfo(dateGroup.getRegistrationDateRange());
            classDateRange =  new DateRangeInfo(dateGroup.getClassDateRange());
            addDate =  dateGroup.getAddDate();
            dropDate =  dateGroup.getDropDate();
            finalExamDateRange =  new DateRangeInfo(dateGroup.getFinalExamDateRange());
            gradingDateRange =  new DateRangeInfo(dateGroup.getGradingDateRange());
        }

        /**
         * Builds the RegistrationDateGroup.
         *
         * @return a new RegistrationDateGroup
         */
        public RegistrationDateGroupInfo build() {
            return new RegistrationDateGroupInfo(this);
        }

        @Override
        public RegistrationDateDerivationGroupInfo getRegistrationDateDerivationGroup() {
            return registrationDateDerivationGroup;
        }

        public void setRegistrationDateDerivationGroup(RegistrationDateDerivationGroupInfo derivationGroup) {
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

        public void setRegistrationDateRange(DateRangeInfo dateRange) {
            this.registrationDateRange = dateRange;
        }

        @Override
        public DateRangeInfo getClassDateRange() {
            return classDateRange;
        }

        public void setClassDateRange(DateRangeInfo dateRange) {
            this.classDateRange = dateRange;
        }

        @Override
        public Date getAddDate() {
            return addDate;
        }

        public void setAddDate(Date date) {
            this.addDate = date;
        }

        @Override
        public Date getDropDate() {
            return dropDate;
        }

        public void setDropDate(Date date) {
            this.dropDate = date;
        }

        @Override
        public DateRangeInfo getFinalExamDateRange() {
            return finalExamDateRange;
        }

        public void setFinalExamDateRange(DateRangeInfo dateRange) {
            this.finalExamDateRange = dateRange;
        }

        @Override
        public DateRangeInfo getGradingDateRange() {
            return gradingDateRange;
        }

        public void setGradingDateRange(DateRangeInfo dateRange) {
            this.gradingDateRange = dateRange;
        }
    }
}
