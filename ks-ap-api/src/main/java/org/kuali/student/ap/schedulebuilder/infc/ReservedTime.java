package org.kuali.student.ap.schedulebuilder.infc;

import org.kuali.student.ap.common.infc.HasUniqueId;
import org.kuali.student.r2.common.infc.HasId;

/**
 * Represents a reserved time as designated by the student.
 * 
 * @author Mark Fyffe <mwfyffe@indiana.edu>
 * @version 0.7.1
 */
public interface ReservedTime extends ScheduleBuildEvent, HasId, HasUniqueId {

	/**
	 * Determine if this reserved time should be included when in scheduling.
	 * 
	 * @return True if the reserved time should be included when scheduling,
	 *         false to ignore.
	 */
	boolean isSelected();

}
