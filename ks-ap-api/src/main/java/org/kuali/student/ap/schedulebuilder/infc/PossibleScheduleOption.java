package org.kuali.student.ap.schedulebuilder.infc;

import org.kuali.student.r2.common.infc.HasId;
import org.kuali.student.r2.common.infc.RichText;

import java.util.List;

/**
 * Represents a possible schedule option.
 * 
 * @author Mark Fyffe <mwfyffe@iu.edu>
 * @version 1.1
 */
public interface PossibleScheduleOption extends HasId, ScheduleBuildOption {
	
	/**
	 * Get the ID of the term this is a possible schedule option for.
	 * @return The id of the term 
	 */
	String getTermId();
	
	/**
	 * Describe this possible schedule option as HTML.
	 * 
	 * <p>
	 * The method is used to dynamically refresh the possible schedule list on
	 * the front end using JSON rather than KRAD rendering.
	 * </p>
	 * 
	 * @return An HTML description of this possible schedule option.
	 */
	RichText getDescription();

	/**
	 * Get the activity options associated with this possible schedule.
	 * 
	 * @return The activity options associated with this possible schedule.
	 */
	List<ActivityOption> getActivityOptions();

}
