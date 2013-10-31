package org.kuali.student.ap.plannerreview.infc;

import org.kuali.student.r2.core.acal.service.AcademicCalendarService;

import java.util.List;

public interface LearningPlanReviewTerm {

	/**
	 * Get the term ID.
	 * 
	 * @return The term ID.
	 * @see AcademicCalendarService
	 */
	String getTermId();

	/**
	 * The courses IDs for this term.
	 * 
	 * <p>
	 * When <strong>null</strong>, all courses in the original learning plan for
	 * the indicated term will be sent. An empty list means that no courses will
	 * be included on the indicated term.
	 * </p>
	 * 
	 * @return The course IDs for this term. May be null to indicate all courses
	 *         on the original learning plan for the term should be sent.
	 * @see org.kuali.student.r2.lum.course.service.CourseService
	 */
	List<String> getCourseIds();

}
