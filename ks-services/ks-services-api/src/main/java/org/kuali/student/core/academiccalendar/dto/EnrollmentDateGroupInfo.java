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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.common.infc.ModelBuilder;
import org.kuali.student.common.dto.KeyEntityInfo;
import org.kuali.student.core.academiccalendar.infc.EnrollmentDateGroupInfc;

import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;


/**
 * A cluster of hardened dates pertinent to an academic term.
 *
 * @Author tom
 * @Since Tue Apr 05 14:22:34 EDT 2011
 */ 

@XmlAccessorType(XmlAccessType.FIELD)
public class EnrollmentDateGroupInfo implements EnrollmentDateGroupInfc, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private final Date registrationStart;

    @XmlElement
    private final Date registrationEnd;

    @XmlElement
    private final Date classStart;

    @XmlElement
    private final Date classEnd;

    @XmlElement
    private final Date addDate;

    @XmlElement
    private final Date dropDate;

    @XmlElement
    private final Date finalsStart;

    @XmlElement
    private final Date finalsEnd;

    @XmlElement
    private final Date gradingStart;

    @XmlElement
    private final Date gradingEnd;

    private EnrollmentDateGroupInfo() {
        registrationStart = null;
        registrationEnd = null;
        classStart = null;
        classEnd = null;
        addDate = null;
        dropDate = null;
        finalsStart = null;
        finalsEnd = null;
        gradingStart = null;
        gradingEnd = null;
    }

    /**
     * Constructs a new EnrollmentDateGroupInfo from another
     * EnrollmentDateGroupInfo.
     *
     * @param dateGroup the EnrollmentDateGroup to copy
     */
    public EnrollmentDateGroupInfo(EnrollmentDateGroupInfc dateGroup) {
        this.registrationStart = dateGroup.getRegistrationStartDate();
        this.registrationEnd = dateGroup.getRegistrationEndDate();
        this.classStart = dateGroup.getClassStartDate();
        this.classEnd = dateGroup.getClassEndDate();
        this.addDate = dateGroup.getAddDate();
        this.dropDate = dateGroup.getDropDate();
        this.finalsStart = dateGroup.getFinalExamStartDate();
        this.finalsEnd = dateGroup.getFinalExamEndDate();
        this.gradingStart = dateGroup.getGradingStartDate();
        this.gradingEnd = dateGroup.getGradingEndDate();
    }


    /**
     * Name: RegistrationStart
     * Gets the registration start date.
     *
     * @return the registration start date
     */
    public Date getRegistrationStartDate() {
        return registrationStart;
    }

    /**
     * Name: RegistrationEnd
     * Gets the registration end date.
     *
     * @return the registration end date
     */
    public Date getRegistrationEndDate() {
        return registrationEnd;
    }

    /**
     * Name: ClassStart
     * Gets the class start date.
     *
     * @return the class start date
     */
    @Override
    public Date getClassStartDate() {
        return classStart;
    }

    /**
     * Name: ClassEnd
     * Gets the class end date.
     *
     * @return the class end date
     */
    @Override
    public Date getClassEndDate() {
        return classEnd;
    }

    /**
     * Name: AddDate
     * Gets the add date.
     *
     * @return add date
     */
    @Override
    public Date getAddDate() {
        return addDate;
    }

    /**
     * Name: DropDate
     * Gets the drop date.
     *
     * @return drop date
     */
    @Override
    public Date getDropDate() {
        return dropDate;
    }

    /**
     * Name: FinalExamStart
     * Gets the final exam start date.
     *
     * @return the final exam start date
     */
    @Override
    public Date getFinalExamStartDate() {
        return finalsStart;
    }

    /**
     * Name: FinalExamEnd
     * Gets the final exam end date.
     *
     * @return the final exam end date
     */
    @Override
    public Date getFinalExamEndDate() {
        return finalsEnd;
    }

    /**
     * Name: GradingStart
     * Gets the grading period start date.
     *
     * @return the grading start date
     */
    @Override
    public Date getGradingStartDate() {
        return gradingStart;
    }

    /**
     * Name: GradingEnd
     * Gets the grading period end date.
     *
     * @return the grading end date
     */
    @Override
    public Date getGradingEndDate() {
        return gradingEnd;
    }

    /**
     * The builder class for this DateInfo.
     */
    public static class Builder implements ModelBuilder<EnrollmentDateGroupInfo>, EnrollmentDateGroupInfc {

        private Date registrationStart;
        private Date registrationEnd;
        private Date classStart;
        private Date classEnd;
        private Date addDate;
        private Date dropDate;
        private Date finalsStart;
        private Date finalsEnd;
        private Date gradingStart;
        private Date gradingEnd;

        /**
         * Constructs a new builder.
         */
        public Builder() {
        }

        /**
         *  Constructs a new builder initialized from another
         *  EnrollmentDateGroup.
         */
        public Builder(EnrollmentDateGroupInfc dateGroupInfo) {
            registrationStart = dateGroupInfo.getRegistrationStartDate();
            registrationEnd = dateGroupInfo.getRegistrationStartDate();
            classStart =  dateGroupInfo.getClassStartDate();
            classEnd =  dateGroupInfo.getClassStartDate();
            addDate =  dateGroupInfo.getAddDate();
            dropDate =  dateGroupInfo.getDropDate();
            finalsStart =  dateGroupInfo.getFinalExamStartDate();
            finalsEnd =  dateGroupInfo.getFinalExamStartDate();
            gradingStart =  dateGroupInfo.getGradingStartDate();
            gradingEnd =  dateGroupInfo.getGradingStartDate();
        }

        /**
         * Builds the EnrollmentDateGroup.
         *
         * @return a new EnrollmentDateGroup
         */
        public EnrollmentDateGroupInfo build() {
            return new EnrollmentDateGroupInfo(this);
        }


        /**
         * Name: RegistrationStart
         * Gets the registration start date.
         *
         * @return the registration start date
         */
        @Override
        public Date getRegistrationStartDate() {
            return registrationStart;
        }

        public void setRegistrationStartDate(Date date) {
            this.registrationStart = date;
        }

        /**
         * Name: RegistrationEnd
         * Gets the registration end date.
         *
         * @return the registration end date
         */
        @Override
        public Date getRegistrationEndDate() {
            return registrationEnd;
        }

        public void setRegistrationEndDate(Date date) {
            this.registrationEnd = date;
        }

        /**
         * Name: ClassStart
         * Gets the class start date.
         *
         * @return the class start date
         */
        @Override
        public Date getClassStartDate() {
            return classStart;
        }

        public void setClassStartDate(Date date) {
            this.classStart = date;
        }

        /**
         * Name: ClassEnd
         * Gets the class end date.
         *
         * @return the class end date
         */
        @Override
        public Date getClassEndDate() {
            return classEnd;
        }

        public void setClassEndDate(Date date) {
            this.classEnd = date;
        }

        /**
         * Name: AddDate
         * Gets the add date.
         *
         * @return add date
         */
        @Override
        public Date getAddDate() {
            return addDate;
        }

        public void setAddDate(Date date) {
            this.addDate = date;
        }

        /**
         * Name: DropDate
         * Gets the drop date.
         *
         * @return drop date
         */
        @Override
        public Date getDropDate() {
            return dropDate;
        }

        public void setDropDate(Date date) {
            this.dropDate = date;
        }

        /**
         * Name: FinalExamStart
         * Gets the final exam start date.
         *
         * @return the final exam start date
         */
        @Override
        public Date getFinalExamStartDate() {
            return finalsStart;
        }

        public void setFinalExamStartDate(Date date) {
            this.finalsStart = date;
        }

        /**
         * Name: FinalExamEnd
         * Gets the final exam end date.
         *
         * @return the final exam end date
         */
        @Override
        public Date getFinalExamEndDate() {
            return finalsEnd;
        }

        public void setFinalExamEndDate(Date date) {
            this.finalsEnd = date;
        }

        /**
         * Name: GradingStart
         * Gets grading period start date.
         *
         * @return the grading period start date
         */
        @Override
        public Date getGradingStartDate() {
            return gradingStart;
        }

        public void setGradingStartDate(Date date) {
            this.gradingStart = date;
        }

        /**
         * Name: GradingEnd
         * Gets the grading period end date.
         *
         * @return the grading end date
         */
        @Override
        public Date getGradingEndDate() {
            return gradingEnd;
        }

        public void setGradingEndDate(Date date) {
            this.gradingEnd = date;
        }
    }
}
