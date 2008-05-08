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
package org.kuali.student.rules.BRMSCore.entity;

import java.util.ArrayList;

import javax.persistence.Embeddable;

/**
 * Contains meta data about the left hand side of a Rule Proposition. For example, in "completed any 2 of (MATH101, MATH102,
 * MATH103)" the left hand side is "completed set of (MATH101, MATH102, MATH103).
 * 
 * @author Kuali Student Team (zdenek.kuali@gmail.com)
 */
@Embeddable
public class LeftHandSide {

    String businessEntityLeft;
    String factContainer;
    String factContainerMethod;
    ArrayList<String> methodParameters;

    /**
     * Sets up an empty instance.
     */
    public LeftHandSide() {
        businessEntityLeft = null;
        factContainer = null;
        factContainerMethod = null;
        methodParameters = null;
    }

    /**
     * Sets up a LeftHandSide instance.
     * 
     * @param businessEntityLeft
     * @param factContainer
     * @param factContainerMethod
     * @param methodParameters
     */
    public LeftHandSide(String businessEntityLeft, String factContainer, String factContainerMethod, ArrayList<String> methodParameters) {
        this.businessEntityLeft = businessEntityLeft;
        this.factContainer = factContainer;
        this.factContainerMethod = factContainerMethod;
        this.methodParameters = methodParameters;
    }

    /**
     * @return the businessEntity
     */
    public final String getBusinessEntityLeft() {
        return businessEntityLeft;
    }

    /**
     * @param businessEntity
     *            the businessEntity to set
     */
    public final void setBusinessEntityLeft(String businessEntityLeft) {
        this.businessEntityLeft = businessEntityLeft;
    }

    /**
     * @return the factContainer
     */
    public final String getFactContainer() {
        return factContainer;
    }

    /**
     * @param factContainer
     *            the factContainer to set
     */
    public final void setFactContainer(String factContainer) {
        this.factContainer = factContainer;
    }

    /**
     * @return the factContainerMethod
     */
    public final String getFactContainerMethod() {
        return factContainerMethod;
    }

    /**
     * @param factContainerMethod
     *            the factContainerMethod to set
     */
    public final void setFactContainerMethod(String factContainerMethod) {
        this.factContainerMethod = factContainerMethod;
    }

    /**
     * @return the methodParameters
     */
    public final ArrayList<String> getMethodParameters() {
        return methodParameters;
    }

    /**
     * @param methodParameters
     *            the methodParameters to set
     */
    public final void setMethodParameters(ArrayList<String> methodParameters) {
        this.methodParameters = methodParameters;
    }
}
