package org.kuali.student.rules.BRMSCore;

import java.util.ArrayList;

import javax.persistence.Embeddable;

@Embeddable
public class RightHandSide {

	String businessEntityRight;
	String criterionType;
	ArrayList<String> criterionValues;
	
	/**
	 * 
	 */
	public RightHandSide() {
		this.businessEntityRight = null;
		this.criterionType = null;
		this.criterionValues = null;		
	}
	
	/**
	 * @param businessEntity
	 * @param facts
	 * @param className
	 */
	public RightHandSide(String businessEntity, String criterionType, ArrayList<String> criterionValues) {
		this.businessEntityRight = businessEntity;
		this.criterionType = criterionType;
		this.criterionValues = criterionValues;
	}
	
	/**
	 * @return the businessEntity
	 */
	public final String getBusinessEntity() {
		return businessEntityRight;
	}
	
	/**
	 * @param businessEntity the businessEntity to set
	 */
	public final void setBusinessEntity(String businessEntity) {
		this.businessEntityRight = businessEntity;
	}

	/**
	 * @return the criterionType
	 */
	public final String getCriterionType() {
		return criterionType;
	}

	/**
	 * @param criterionType the criterionType to set
	 */
	public final void setCriterionType(String criterionType) {
		this.criterionType = criterionType;
	}

	/**
	 * @return the criterionValues
	 */
	public final ArrayList<String> getCriterionValues() {
		return criterionValues;
	}

	/**
	 * @param criterionValues the criterionValues to set
	 */
	public final void setCriterionValues(ArrayList<String> criterionValues) {
		this.criterionValues = criterionValues;
	}
}
