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

/**
 * 
 */
package org.kuali.student.brms.devgui.client.model;

import java.io.Serializable;

/**
 * @author zzraly
 */
public class BusinessRuleProposition implements Serializable {
    private static final long serialVersionUID = 123123142351352L;

    private String name;
    private String description;
    private String leftHandSide;
    private String comparisonOperatorType;
    private String rightHandSide;
    private String comparisonDataType;

    /**
     * @return the comparisonDataType
     */
    public final String getComparisonDataType() {
        return comparisonDataType;
    }

    /**
     * @param comparisonDataType
     *            the comparisonDataType to set
     */
    public final void setComparisonDataType(String comparisonDataType) {
        this.comparisonDataType = comparisonDataType;
    }

    /**
     * @return the name
     */
    public final String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public final void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public final String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public final void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the leftHandSide
     */
    public final String getLeftHandSide() {
        return leftHandSide;
    }

    /**
     * @param leftHandSide
     *            the leftHandSide to set
     */
    public final void setLeftHandSide(String leftHandSide) {
        this.leftHandSide = leftHandSide;
    }

    /**
     * @return the comparisonOperatorType
     */
    public final String getComparisonOperatorType() {
        return comparisonOperatorType;
    }

    /**
     * @param comparisonOperatorType
     *            the comparisonOperatorType to set
     */
    public final void setComparisonOperatorType(String comparisonOperatorType) {
        this.comparisonOperatorType = comparisonOperatorType;
    }

    /**
     * @return the rightHandSide
     */
    public final String getRightHandSide() {
        return rightHandSide;
    }

    /**
     * @param rightHandSide
     *            the rightHandSide to set
     */
    public final void setRightHandSide(String rightHandSide) {
        this.rightHandSide = rightHandSide;
    }

}
