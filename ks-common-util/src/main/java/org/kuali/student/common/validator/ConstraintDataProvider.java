package org.kuali.student.common.validator;

public interface ConstraintDataProvider {

	public String getObjectId();
	
	public Object getValue(String fieldKey);	
	
	public Boolean hasField(String fieldKey);	
	
	public void initialize(Object o);
}

