package org.kuali.student.ap.coursesearch;

public interface Credit {

	String getId();

	String getDisplay();

	float getMin();

	float getMax();

	CourseSearchItem.CreditType getType();
	
}
