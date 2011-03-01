package org.kuali.student.lum.lu.bo;

import javax.persistence.Column;

import org.kuali.student.core.bo.KsTypeBusinessObjectBase;

public class LuType extends KsTypeBusinessObjectBase {

	private static final long serialVersionUID = -6091857629199582219L;

	@Column(name = "INSTR_FRMT")
	private String instructionalFormat;

	@Column(name = "DLVR_MTHD")
    private String deliveryMethod;

	public String getInstructionalFormat() {
		return instructionalFormat;
	}

	public void setInstructionalFormat(String instructionalFormat) {
		this.instructionalFormat = instructionalFormat;
	}

	public String getDeliveryMethod() {
		return deliveryMethod;
	}

	public void setDeliveryMethod(String deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}

	
}
