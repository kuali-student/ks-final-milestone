package org.kuali.student.r2.core.scheduling.infc;

import org.kuali.student.r2.common.infc.DisplayObject;
import org.kuali.student.r2.common.infc.HasId;
import org.kuali.student.r2.core.room.dto.BuildingInfo;
import org.kuali.student.r2.core.room.dto.RoomInfo;
import org.kuali.student.r2.core.scheduling.dto.TimeSlotInfo;

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
    public List<TimeSlotInfo> getTimeSlots();

    /**
     * The Room.
     */
    public RoomInfo getRoom();

    /**
     * The Building.
     */
    public BuildingInfo getBuilding();

}
