package org.kuali.student.rules.BRMSCore.entity;

import java.util.ArrayList;

import javax.persistence.Embeddable;

/**
 * Contains meta data about the left hand side of a Rule Proposition. For example, in "completed any 2 of (MATH101, MATH102,
 * MATH103)" the left hand side is "completed set of (MATH101, MATH102, MATH103).
 * 
 * @author Zdenek Zraly (zdenek.zraly@ubc.ca)
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
