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
package org.kuali.student.rules.common.entity;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToOne;


/**
 * Contains meta data about the left hand side of a Rule Proposition. For example, in "completed any 2 of (MATH101, MATH102,
 * MATH103)" the left hand side is "completed set of (MATH101, MATH102, MATH103).
 * 
 * @author Kuali Student Team (zdenek.kuali@gmail.com)
 */
@Embeddable
public class LeftHandSide {

    @OneToOne(optional = true, cascade = CascadeType.ALL, targetEntity = ComputationAssistant.class)
    private ComputationAssistant compAssistant;

    String criteria;
    String academicRecordID;
    ValueType valueType; // String, Number, Boolean

    /**
     * Sets up an empty instance.
     */
    public LeftHandSide() {
        criteria = null;
        academicRecordID = null;
        compAssistant = null;
        valueType = null;
    }

    /**
     * Sets up a LeftHandSide instance.
     * 
     * @param businessEntityLeft
     * @param factContainer
     * @param factContainerMethod
     * @param methodParameters
     */
    public LeftHandSide(String criteria, String academicRecordID, ComputationAssistant compAssistant,
            ValueType valueType) {
        this.criteria = criteria;
        this.academicRecordID = academicRecordID;
        this.compAssistant = compAssistant;
        this.valueType = valueType;
    }

    /**
     * @return the valueType
     */
    public final ValueType getValueType() {
        return valueType;
    }

    /**
     * @param valueType
     *            the valueType to set
     */
    public final void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    /**
     * @return the criteria
     */
    public final String getCriteria() {
        return criteria;
    }

    /**
     * @param criteria
     *            the criteria to set
     */
    public final void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    /**
     * @return the academicRecordID
     */
    public final String getAcademicRecordID() {
        return academicRecordID;
    }

    /**
     * @param academicRecordID
     *            the academicRecordID to set
     */
    public final void setAcademicRecordID(String academicRecordID) {
        this.academicRecordID = academicRecordID;
    }

    /**
     * @return the compAssistant
     */
    public final ComputationAssistant getCompAssistant() {
        return compAssistant;
    }

    /**
     * @param compAssistant
     *            the compAssistant to set
     */
    public final void setCompAssistant(ComputationAssistant compAssistant) {
        this.compAssistant = compAssistant;
    }
}
