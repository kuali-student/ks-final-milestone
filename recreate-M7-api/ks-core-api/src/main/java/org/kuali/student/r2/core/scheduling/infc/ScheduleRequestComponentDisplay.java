package org.kuali.student.r2.core.scheduling.infc;

import org.kuali.student.r2.common.infc.DisplayObject;
import org.kuali.student.r2.common.infc.HasId;
import org.kuali.student.r2.core.class1.type.infc.Type;
import org.kuali.student.r2.core.organization.infc.Org;
import org.kuali.student.r2.core.room.infc.Building;
import org.kuali.student.r2.core.room.infc.Room;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mezba Mahtab
 * Date: 8/28/12
 * Time: 1:27 PM
 *
 * This interface represents a reusable display object in the Scheduling Service for ScheduleRequestComponents.
 */
public interface ScheduleRequestComponentDisplay extends HasId, DisplayObject {

    /**
     * The Building.
     */
    public List<? extends Building> getBuildings();

    /**
     * The Rooms.
     */
    public List<? extends Room> getRooms();

    /**
     * The Orgs to specify a Room or Building "owned" by an
     * Organization.
     */
    public List<? extends Org> getOrgs();

    /**
     * The resource types.
     */
    public List<? extends Type> getResourceTypes();

    /**
     * The Time Slots
     */
    public List<? extends TimeSlot> getTimeSlots();

    /**
     * The flag that holds whether this is a TBA schedule request.
     * A ScheduleRequestComponent is TBA if the Room is TBA, or timeslot is completely TBA (not there), or is of type TBA (weekday or start/end time TBA).
     */
    public Boolean getIsTBA();
}
