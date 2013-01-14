package org.kuali.student.ap.framework.course;

public interface Credit {

	String getId();

	String getDisplay();

	float getMin();

	float getMax();

	CourseSearchItem.CreditType getType();
	
}
