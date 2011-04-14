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

package org.kuali.student.core.academiccalendar.dto;

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
import org.kuali.student.core.academiccalendar.infc.EnrollmentDateGroup;



/**
 * A cluster of hardened dates pertinent to an academic term.
 *
 * @Author tom
 * @Since Tue Apr 05 14:22:34 EDT 2011
 */ 

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EnrollmentDateGroupInfo", propOrder = {"termKey", "registrationStartDate", "registrationEndDate", "classStartDate", "classEndDate", "addDate", "dropDate", "finalExamStartDate", "finalExamEndDate", "gradingStartDate", "gradingEndDate", "_futureElements"})

public class EnrollmentDateGroupInfo implements EnrollmentDateGroup, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private final String termKey;

    @XmlElement
    private final Date registrationStartDate;

    @XmlElement
    private final Date registrationEndDate;

    @XmlElement
    private final Date classStartDate;

    @XmlElement
    private final Date classEndDate;

    @XmlElement
    private final Date addDate;

    @XmlElement
    private final Date dropDate;

    @XmlElement
    private final Date finalExamStartDate;

    @XmlElement
    private final Date finalExamEndDate;

    @XmlElement
    private final Date gradingStartDate;

    @XmlElement
    private final Date gradingEndDate;

    @XmlAnyElement
    private final List<Element> _futureElements;  

    private EnrollmentDateGroupInfo() {
        termKey = null;
        registrationStartDate = null;
        registrationEndDate = null;
        classStartDate = null;
        classEndDate = null;
        addDate = null;
        dropDate = null;
        finalExamStartDate = null;
        finalExamEndDate = null;
        gradingStartDate = null;
        gradingEndDate = null;
        _futureElements = null;
    }

    /**
     * Constructs a new EnrollmentDateGroupInfo from another
     * EnrollmentDateGroupInfo.
     *
     * @param dateGroup the EnrollmentDateGroup to copy
     */
    public EnrollmentDateGroupInfo(EnrollmentDateGroup dateGroup) {
        this.termKey = dateGroup.getTermKey();
        this.registrationStartDate = dateGroup.getRegistrationStartDate();
        this.registrationEndDate = dateGroup.getRegistrationEndDate();
        this.classStartDate = dateGroup.getClassStartDate();
        this.classEndDate = dateGroup.getClassEndDate();
        this.addDate = dateGroup.getAddDate();
        this.dropDate = dateGroup.getDropDate();
        this.finalExamStartDate = dateGroup.getFinalExamStartDate();
        this.finalExamEndDate = dateGroup.getFinalExamEndDate();
        this.gradingStartDate = dateGroup.getGradingStartDate();
        this.gradingEndDate = dateGroup.getGradingEndDate();
        _futureElements = null;
    }


    @Override
    public String getTermKey() {
        return termKey;
    }


    @Override
    public Date getRegistrationStartDate() {
        return registrationStartDate;
    }

    public Date getRegistrationEndDate() {
        return registrationEndDate;
    }


    @Override
    public Date getClassStartDate() {
        return classStartDate;
    }

    @Override
    public Date getClassEndDate() {
        return classEndDate;
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
    public Date getFinalExamStartDate() {
        return finalExamStartDate;
    }


    @Override
    public Date getFinalExamEndDate() {
        return finalExamEndDate;
    }


    @Override
    public Date getGradingStartDate() {
        return gradingStartDate;
    }


    @Override
    public Date getGradingEndDate() {
        return gradingEndDate;
    }

    /**
     * The builder class for this DateInfo.
     */
    public static class Builder implements ModelBuilder<EnrollmentDateGroupInfo>, EnrollmentDateGroup {
        private String termKey;
        private Date registrationStartDate;
        private Date registrationEndDate;
        private Date classStartDate;
        private Date classEndDate;
        private Date addDate;
        private Date dropDate;
        private Date finalsStartDate;
        private Date finalsEndDate;
        private Date gradingStartDate;
        private Date gradingEndDate;

        /**
         * Constructs a new builder.
         */
        public Builder() {
        }

        /**
         *  Constructs a new builder initialized from another
         *  EnrollmentDateGroup.
         */
        public Builder(EnrollmentDateGroup dateGroup) {
            termKey = dateGroup.getTermKey();
            registrationStartDate = dateGroup.getRegistrationStartDate();
            registrationEndDate = dateGroup.getRegistrationStartDate();
            classStartDate =  dateGroup.getClassStartDate();
            classEndDate =  dateGroup.getClassStartDate();
            addDate =  dateGroup.getAddDate();
            dropDate =  dateGroup.getDropDate();
            finalsStartDate =  dateGroup.getFinalExamStartDate();
            finalsEndDate =  dateGroup.getFinalExamStartDate();
            gradingStartDate =  dateGroup.getGradingStartDate();
            gradingEndDate =  dateGroup.getGradingStartDate();
        }

        /**
         * Builds the EnrollmentDateGroup.
         *
         * @return a new EnrollmentDateGroup
         */
        public EnrollmentDateGroupInfo build() {
            return new EnrollmentDateGroupInfo(this);
        }

        @Override
        public String getTermKey() {
            return termKey;
        }

        public void setTermKey(String termKey) {
            this.termKey = termKey;
        }

        @Override
        public Date getRegistrationStartDate() {
            return registrationStartDate;
        }

        public void setRegistrationStartDate(Date date) {
            this.registrationStartDate = date;
        }


        @Override
        public Date getRegistrationEndDate() {
            return registrationEndDate;
        }

        public void setRegistrationEndDate(Date date) {
            this.registrationEndDate = date;
        }


        @Override
        public Date getClassStartDate() {
            return classStartDate;
        }

        public void setClassStartDate(Date date) {
            this.classStartDate = date;
        }


        @Override
        public Date getClassEndDate() {
            return classEndDate;
        }

        public void setClassEndDate(Date date) {
            this.classEndDate = date;
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
        public Date getFinalExamStartDate() {
            return finalsStartDate;
        }

        public void setFinalExamStartDate(Date date) {
            this.finalsStartDate = date;
        }


        @Override
        public Date getFinalExamEndDate() {
            return finalsEndDate;
        }

        public void setFinalExamEndDate(Date date) {
            this.finalsEndDate = date;
        }

        @Override
        public Date getGradingStartDate() {
            return gradingStartDate;
        }

        public void setGradingStartDate(Date date) {
            this.gradingStartDate = date;
        }


        @Override
        public Date getGradingEndDate() {
            return gradingEndDate;
        }

        public void setGradingEndDate(Date date) {
            this.gradingEndDate = date;
        }
    }
}
