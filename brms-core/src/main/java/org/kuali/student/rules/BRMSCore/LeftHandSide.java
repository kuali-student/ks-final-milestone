package org.kuali.student.rules.BRMSCore;

import java.util.ArrayList;

import javax.persistence.Embeddable;

@Embeddable
public class LeftHandSide {

	String businessEntityLeft;
	String factContainer;
	String factContainerMethod;
	ArrayList<String> methodParameters;

	/**
	 * 
	 */
	public LeftHandSide() {
		this.businessEntityLeft = null;
		this.factContainer = null;
		this.factContainerMethod = null;		
		this.methodParameters = null;	
	}
		
	/**
	 * @param businessEntityLeft
	 * @param factContainer
	 * @param factContainerMethod
	 * @param methodParameters
	 */
	public LeftHandSide(String businessEntityLeft, String factContainer,
			String factContainerMethod, ArrayList<String> methodParameters) {
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
	 * @param businessEntity the businessEntity to set
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
	 * @param factContainer the factContainer to set
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
	 * @param factContainerMethod the factContainerMethod to set
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
	 * @param methodParameters the methodParameters to set
	 */
	public final void setMethodParameters(ArrayList<String> methodParameters) {
		this.methodParameters = methodParameters;
	}	
}
