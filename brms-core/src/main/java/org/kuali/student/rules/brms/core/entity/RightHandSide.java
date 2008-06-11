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
package org.kuali.student.rules.brms.core.entity;

import javax.persistence.Embeddable;

/**
 * Contains meta data about the right hand side of a Rule Proposition. For example, in "completed any 2 of (MATH101, MATH102,
 * MATH103)" the right hand side is a criterion '2'.
 * 
 * @author Kuali Student Team (zdenek.kuali@gmail.com)
 */
@Embeddable
public class RightHandSide {

    String businessEntityRight;
    String criterionType;
    String criterionValue;

    /**
     * Sets up an empty instance.
     */
    public RightHandSide() {
        businessEntityRight = null;
        criterionType = null;
        criterionValue = null;
    }

    /**
     * Sets up a RightHandSide instance.
     * 
     * @param businessEntity
     * @param facts
     * @param className
     */
    public RightHandSide(String businessEntity, String criterionType, String criterionValue) {
        businessEntityRight = businessEntity;
        this.criterionType = criterionType;
        this.criterionValue = criterionValue;
    }

    /**
     * @return the businessEntity
     */
    public final String getBusinessEntity() {
        return businessEntityRight;
    }

    /**
     * @param businessEntity
     *            the businessEntity to set
     */
    public final void setBusinessEntity(String businessEntity) {
        businessEntityRight = businessEntity;
    }

    /**
     * @return the criterionType
     */
    public final String getCriterionType() {
        return criterionType;
    }

    /**
     * @param criterionType
     *            the criterionType to set
     */
    public final void setCriterionType(String criterionType) {
        this.criterionType = criterionType;
    }

    /**
     * @return the criterionValues
     */
    public final String getCriterionValue() {
        return criterionValue;
    }

    /**
     * @param criterionValues
     *            the criterionValues to set
     */
    public final void setCriterionValues(String criterionValues) {
        this.criterionValue = criterionValues;
    }
}
