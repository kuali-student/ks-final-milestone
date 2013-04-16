package org.kuali.student.r2.core.scheduling.infc;

import org.kuali.student.r2.common.infc.DisplayObject;
import org.kuali.student.r2.common.infc.HasId;
import org.kuali.student.r2.core.room.infc.Building;
import org.kuali.student.r2.core.room.infc.Room;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mezba Mahtab
 * Date: 8/28/12
 * Time: 1:40 PM
 *
 * This interface represents a reusable display object in the Scheduling Service for ScheduleComponents.
 * @author Mezba Mahtab
 */
public interface ScheduleComponentDisplay extends HasId, DisplayObject {

    /**
     * The Time Slots.
     * @required
     */
    public List<? extends TimeSlot> getTimeSlots();

    /**
     * The Room.
     */
    public Room getRoom();

    /**
     * The Building.
     */
    public Building getBuilding();

}
