package org.kuali.student.ap.schedulebuilder.infc;

import org.kuali.student.ap.common.infc.HasUniqueId;

import java.io.Serializable;

/**
 * Represents an option for building a class schedule.
 * 
 * @author Mark Fyffe <mwfyffe@indiana.edu>
 * @version 0.7
 */
public interface ScheduleBuildOption extends HasUniqueId, Serializable {

	/**
	 * Determine if the option has been selected for inclusion in building a
	 * schedule.
	 * 
	 * @return True if the option has been selected, false if not.
	 */
	boolean isSelected();

	/**
	 * Determine if the option has been locked in, and must appear in all
	 * possible schedules.
	 * 
	 * @return True if the option has been locked in, false if not.
	 */
	boolean isLockedIn();

	/**
	 * Get the new position for this option in the overall list.
	 * 
	 * @return The new position for this option in the overall list.
	 */
	int getShuffle();

	/**
	 * Determine if the option has been discarded in, and should no longer appear.
	 * 
	 * @return True if the option has been discarded, false if not.
	 */
	boolean isDiscarded();

}
