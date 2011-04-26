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

package org.kuali.student.enrollment.classII.academiccalendar.dto;

import java.io.Serializable;
import java.util.List;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.classII.academiccalendar.infc.RegistrationDateDerivationGroup;
import org.kuali.student.r2.common.infc.ModelBuilder;


/**
 * This structure specifies how the dates in the RegistrationDateGroup
 * are derived.
 *
 * @Author tom
 * @Since Tue Apr 23 14:22:34 EDT 2011
 */ 

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegistrationDateDerivationGroupInfo", propOrder = {"registrationStartDateTermKey", "registrationEndDateTermKey", "classStartDateTermKey", "classEndDateTermKey", "addDateTermKey", "dropDateTermKey", "finalExamStartDateTermKey", "finalExamEndDateTermKey", "gradingStartDateTermKey", "gradingEndDateTerKey", "_futureElements"})

public class RegistrationDateDerivationGroupInfo implements RegistrationDateDerivationGroup, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private final String registrationStartDateTermKey;

    @XmlElement
    private final String registrationEndDateTermKey;

    @XmlElement
    private final String classStartDateTermKey;

    @XmlElement
    private final String classEndDateTermKey;

    @XmlElement
    private final String addDateTermKey;

    @XmlElement
    private final String dropDateTermKey;

    @XmlElement
    private final String finalExamStartDateTermKey;

    @XmlElement
    private final String finalExamEndDateTermKey;

    @XmlElement
    private final String gradingStartDateTermKey;

    @XmlElement
    private final String gradingEndDateTermKey;

    @XmlAnyElement
    private final List<Element> _futureElements;  

    private RegistrationDateDerivationGroupInfo() {
        registrationStartDateTermKey = null;
        registrationEndDateTermKey = null;
        classStartDateTermKey = null;
        classEndDateTermKey = null;
        addDateTermKey = null;
        dropDateTermKey = null;
        finalExamStartDateTermKey = null;
        finalExamEndDateTermKey = null;
        gradingStartDateTermKey = null;
        gradingEndDateTermKey = null;
        _futureElements = null;
    }

    /**
     * Constructs a new RegistrationDateDerivationGroupInfo from another
     * RegistrationDateDerivationGroup.
     *
     * @param dateDerivationGroup the RegistrationDateDerivationGroup to copy
     */
    public RegistrationDateDerivationGroupInfo(RegistrationDateDerivationGroup dateDerivationGroup) {
        registrationStartDateTermKey = dateDerivationGroup.getRegistrationStartDateTermKey();
        registrationEndDateTermKey = dateDerivationGroup.getRegistrationEndDateTermKey();
        classStartDateTermKey = dateDerivationGroup.getClassStartDateTermKey();
        classEndDateTermKey = dateDerivationGroup.getRegistrationEndDateTermKey();
        addDateTermKey = dateDerivationGroup.getAddDateTermKey();
        dropDateTermKey = dateDerivationGroup.getDropDateTermKey();
        finalExamStartDateTermKey = dateDerivationGroup.getFinalExamStartDateTermKey();
        finalExamEndDateTermKey = dateDerivationGroup.getFinalExamEndDateTermKey();
        gradingStartDateTermKey = dateDerivationGroup.getGradingStartDateTermKey();
        gradingEndDateTermKey = dateDerivationGroup.getGradingEndDateTermKey();
        _futureElements = null;
    }

    /**
     * Gets the key of the Term from which the start of the
     * registration period is derived. If null, then the registration
     * start date needs to be explicitly set.
     *
     * @return a term key
     */
    @Override
    public String getRegistrationStartDateTermKey() {
        return registrationStartDateTermKey;
    }

    /**
     * Gets the key of the Term from which the end of the registration
     * period is derived. If null, then the registration end date
     * needs to be explicitly set.
     *
     * @return a term key
     */
    @Override
    public String getRegistrationEndDateTermKey() {
        return registrationEndDateTermKey;
    }

    /**
     * Gets the key of the Term from which the start of the class
     * period is derived. If null, then the class start date needs to
     * be explicitly set.
     *
     * @return a term key
     */
    @Override
    public String getClassStartDateTermKey() {
        return classStartDateTermKey;
    }

    /**
     * Gets the key of the Term from which the end of the class period
     * is derived. If null, then the class end date needs to be
     * explicitly set.
     *
     * @return a term key
     */
    @Override
    public String getClassEndDateTermKey() {
        return classEndDateTermKey;
    }

    /**
     * Gets the key of the Term from which the add date is derived. If
     * null, then the add date needs to be explicitly set.
     *
     * @return a term key
     */
    @Override
    public String getAddDateTermKey() {
        return addDateTermKey;
    }

    /**
     * Gets the key of the Term from which the drop date is derived. If
     * null, then the drop date needs to be explicitly set.
     *
     * @return a term key
     */
    @Override
    public String getDropDateTermKey() {
        return dropDateTermKey;
    }

    /**
     * Gets the key of the Term from which the start of the final exam
     * period is derived. If null, then the class start date needs to
     * be explicitly set.
     *
     * @return a term key
     */
    @Override
    public String getFinalExamStartDateTermKey() {
        return finalExamStartDateTermKey;
    }

    /**
     * Gets the key of the Term from which the end of the final exam
     * period is derived. If null, then the class end date needs to be
     * explicitly set.
     *
     * @return a term key
     */
    @Override
    public String getFinalExamEndDateTermKey() {
        return finalExamEndDateTermKey;
    }

    /**
     * Gets the key of the Term from which the start of the grading
     * period is derived. If null, then the class start date needs to
     * be explicitly set.
     *
     * @return a term key
     */
    @Override
    public String getGradingStartDateTermKey() {
        return gradingStartDateTermKey;
    }

    /**
     * Gets the key of the Term from which the end of the grading
     * period is derived. If null, then the class end date needs to be
     * explicitly set.
     *
     * @return a term key
     */
    @Override
    public String getGradingEndDateTermKey() {
        return gradingEndDateTermKey;
    }


    /**
     * The builder class for this DateInfo.
     */
    public static class Builder implements ModelBuilder<RegistrationDateDerivationGroupInfo>, RegistrationDateDerivationGroup {
        private String registrationStartDateTermKey;
        private String registrationEndDateTermKey;
        private String classStartDateTermKey;
        private String classEndDateTermKey;
        private String addDateTermKey;
        private String dropDateTermKey;
        private String finalExamStartDateTermKey;
        private String finalExamEndDateTermKey;
        private String gradingStartDateTermKey;
        private String gradingEndDateTermKey;

        /**
         * Constructs a new builder.
         */
        public Builder() {
        }

        /**
         *  Constructs a new builder initialized from another
         *  RegistrationDateDerivationGroup.
         */
        public Builder(RegistrationDateDerivationGroup dateDerivationGroup) {
            registrationStartDateTermKey = dateDerivationGroup.getRegistrationStartDateTermKey();
            registrationEndDateTermKey = dateDerivationGroup.getRegistrationEndDateTermKey();
            classStartDateTermKey = dateDerivationGroup.getClassStartDateTermKey();
            classEndDateTermKey = dateDerivationGroup.getRegistrationEndDateTermKey();
            addDateTermKey = dateDerivationGroup.getAddDateTermKey();
            dropDateTermKey = dateDerivationGroup.getDropDateTermKey();
            finalExamStartDateTermKey = dateDerivationGroup.getFinalExamStartDateTermKey();
            finalExamEndDateTermKey = dateDerivationGroup.getFinalExamEndDateTermKey();
            gradingStartDateTermKey = dateDerivationGroup.getGradingStartDateTermKey();
            gradingEndDateTermKey = dateDerivationGroup.getGradingEndDateTermKey();
        }

        /**
         * Builds the RegistrationDateDerivationGroup.
         *
         * @return a new RegistrationDateDerivationGroup
         */
        @Override
        public RegistrationDateDerivationGroupInfo build() {
            return new RegistrationDateDerivationGroupInfo(this);
        }

        /**
         * Gets the key of the Term from which the start of the
         * registration period is derived. If null, then the registration
         * start date needs to be explicitly set.
         *
         * @return a term key
         */
        @Override
        public String getRegistrationStartDateTermKey() {
            return registrationStartDateTermKey;
        }

        public void setRegistrationStartDateTermKey(String termKey) {
            this.registrationStartDateTermKey = termKey;
        }

        /**
         * Gets the key of the Term from which the end of the registration
         * period is derived. If null, then the registration end date
         * needs to be explicitly set.
         *
         * @return a term key
         */
        @Override
        public String getRegistrationEndDateTermKey() {
            return registrationEndDateTermKey;
        }

        public void setRegistrationEndDateTermKey(String termKey) {
            this.registrationEndDateTermKey = termKey;
        }

        /**
         * Gets the key of the Term from which the start of the class
         * period is derived. If null, then the class start date needs to
         * be explicitly set.
         *
         * @return a term key
         */
        @Override
        public String getClassStartDateTermKey() {
            return classStartDateTermKey;
        }

        public void setClassStartDateTermKey(String termKey) {
            this.classStartDateTermKey = termKey;
        }

        /**
         * Gets the key of the Term from which the end of the class period
         * is derived. If null, then the class end date needs to be
         * explicitly set.
         *
         * @return a term key
         */
        @Override
        public String getClassEndDateTermKey() {
            return classEndDateTermKey;
        }

        public void setClassEndDateTermKey(String termKey) {
            this.classEndDateTermKey = termKey;
        }

        /**
         * Gets the key of the Term from which the add date is derived. If
         * null, then the add date needs to be explicitly set.
         *
         * @return a term key
         */
        @Override
        public String getAddDateTermKey() {
            return addDateTermKey;
        }

        public void setAddDateTermKey(String termKey) {
            this.addDateTermKey = termKey;
        }

        /**
         * Gets the key of the Term from which the drop date is derived. If
         * null, then the drop date needs to be explicitly set.
         *
         * @return a term key
         */
        @Override
        public String getDropDateTermKey() {
            return dropDateTermKey;
        }

        public void setDropDateTermKey(String termKey) {
            this.dropDateTermKey = termKey;
        }

        /**
         * Gets the key of the Term from which the start of the final exam
         * period is derived. If null, then the class start date needs to
         * be explicitly set.
         *
         * @return a term key
         */
        @Override
        public String getFinalExamStartDateTermKey() {
            return finalExamStartDateTermKey;
        }

        public void setFinalExamStartDateTermKey(String termKey) {
            this.finalExamStartDateTermKey = termKey;
        }

        /**
         * Gets the key of the Term from which the end of the final exam
         * period is derived. If null, then the class end date needs to be
         * explicitly set.
         *
         * @return a term key
         */
        @Override
        public String getFinalExamEndDateTermKey() {
            return finalExamEndDateTermKey;
        }

        public void setFinalExamEndDateTermKey(String termKey) {
            this.finalExamEndDateTermKey = termKey;
        }

        /**
         * Gets the key of the Term from which the start of the grading
         * period is derived. If null, then the class start date needs to
         * be explicitly set.
         *
         * @return a term key
         */
        @Override
        public String getGradingStartDateTermKey() {
            return gradingStartDateTermKey;
        }

        public void setGradingStartDateTermKey(String termKey) {
            this.gradingStartDateTermKey = termKey;
        }

        /**
         * Gets the key of the Term from which the end of the grading
         * period is derived. If null, then the class end date needs to be
         * explicitly set.
         *
         * @return a term key
         */
        @Override
        public String getGradingEndDateTermKey() {
            return gradingEndDateTermKey;
        }

        public void setGradingEndDateTermKey(String termKey) {
            this.gradingEndDateTermKey = termKey;
        }
    }
}
