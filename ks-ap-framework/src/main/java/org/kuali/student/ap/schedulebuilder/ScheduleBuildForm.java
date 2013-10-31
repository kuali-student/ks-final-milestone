package org.kuali.student.ap.schedulebuilder;

import org.kuali.student.ap.schedulebuilder.infc.CourseOption;
import org.kuali.student.ap.schedulebuilder.infc.PossibleScheduleOption;
import org.kuali.student.ap.schedulebuilder.infc.ReservedTime;
import org.kuali.student.r2.core.acal.infc.Term;

import java.util.List;

/**
 * Common interface for passing request attributes from the UI layer to service
 * layer.
 * 
 * @author Mark Fyffe <mwfyffe@indiana.edu>
 * @version 0.7.1
 */
public interface ScheduleBuildForm {

	/**
	 * Get the term for which to build a schedule.
	 * 
	 * @return The term for which to build a schedule.
	 */
	Term getTerm();

	/**
	 * Get the requested learning plan ID.
	 * 
	 * @return The requested learning plan ID.
	 */
	String getRequestedLearningPlanId();

	/**
	 * Determine if closed activity offerings should be included in building
	 * schedules.
	 * 
	 * @return True if closed activity offerings should be included, false if
	 *         not.
	 */
	boolean isIncludeClosed();

	/**
	 * Get the course options the student is currently working with.
	 * 
	 * @return The course options the student is currently working with.
	 */
	List<CourseOption> getCourseOptions();

	/**
	 * Get the reserved time options.
	 * 
	 * @return The reserved time options.
	 */
	List<ReservedTime> getReservedTimes();

	/**
	 * Get the index of a reserved time to remove from the list.
	 * 
	 * @return The index of a reserved time to remove from the list.
	 */
	Integer getRemoveReserved();

	/**
	 * Get the list of possible schedule options.
	 * 
	 * @return The list of possible schedule options.
	 */
	List<PossibleScheduleOption> getPossibleScheduleOptions();

	/**
	 * Get the saved schedule options.
	 * 
	 * @return The saved schedule options.
	 */
	List<PossibleScheduleOption> getSavedSchedules();

	/**
	 * Reset the form to its initial state.
	 */
	void reset();

	/**
	 * Determine if more schedule options based on the same criteria are
	 * requested.
	 * 
	 * @return True if more options based on the same criteria are requested,
	 *         false if the first set of options are requested.
	 */
	boolean isMore();

	/**
	 * Determine if more schedule options based on the current criteria are
	 * available.
	 * 
	 * @return True if more options are available beyond the first set, false if
	 *         the first set has all of the available options.
	 */
	boolean hasMore();

	/**
	 * Rebuild schedule options based on the most recent form data.
	 */
	void buildSchedules();

	/**
	 * Add the possible schedule option indicated by
	 * {@link #getScheduleIndexToSave()} to the saved schedule list.
	 * 
	 * @return The ID of the saved schedule.
	 */
	PossibleScheduleOption saveSchedule();

	/**
	 * Remove the saved schedule indicated by {@link #getRemoveSavedSchedule()}.
	 */
	void removeSchedule();

}
