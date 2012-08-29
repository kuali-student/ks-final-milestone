package org.kuali.student.r2.core.scheduling.infc;

import org.kuali.student.r2.common.infc.DisplayObject;
import org.kuali.student.r2.common.infc.HasId;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mezba Mahtab
 * Date: 8/28/12
 * Time: 1:40 PM
 *
 * This interface represents a reusable display object in the Scheduling Service for ScheduleComponents.
 */
public interface ScheduleComponentDisplay extends HasId, DisplayObject {

    /**
     * The Time Slots Ids.
     *
     * @name Time Slot Ids
     * @required
     */
    public List<String> getTimeSlotIds();

    /**
     * The Room Id.
     *
     * @name Room Id
     */
    public String getRoomId();
}
