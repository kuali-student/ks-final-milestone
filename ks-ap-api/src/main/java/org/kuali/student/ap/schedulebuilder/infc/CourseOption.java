package org.kuali.student.ap.schedulebuilder.infc;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Interface represents an option for scheduling a course.
 * 
 * @author Mark Fyffe <mwfyffe@indiana.edu>
 * @version 0.7
 */
public interface CourseOption extends ScheduleBuildOption, Comparable<CourseOption>, Serializable {

	/**
	 * Get the course ID.
	 * 
	 * @return The course ID.
	 */
	String getCourseId();

	/**
	 * Get the course code.
	 * 
	 * @return The course code.
	 */
	String getCourseCode();

	/**
	 * Get the course title.
	 * 
	 * @return The course title.
	 */
	String getCourseTitle();

	/**
	 * Get the credits for this course, if variable.
	 * 
	 * @return The credits for this course, if variable.
	 */
	BigDecimal getCredits();

	/**
	 * Get the activity options for this course.
	 * 
	 * @return The activity options for this course.
	 */
	List<ActivityOption> getActivityOptions();

	/**
	 * Get the total number of activity options available for this course.
	 * 
	 * @param includeClosed
	 *            True to include closed sections in the count, false to exclude
	 *            closed sections.
	 * @return The total number of activity options available for this course.
	 */
	int getActivityCount(boolean includeClosed);

	/**
	 * Get the number of activity options selected for this course.
	 * 
	 * @param includeClosed
	 *            True to include closed sections in the count, false to exclude
	 *            closed sections.
	 * @return The number of activity options selected for this course.
	 */
	int getSelectedActivityCount(boolean includeClosed);

	/**
	 * Of all selected activity options, return the highest possible minimum
	 * credits.
	 * 
	 * @return The highest possible minimum credits for this course.
	 */
	BigDecimal getMaxSelectedMinCredits();

}
