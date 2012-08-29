package org.kuali.student.r2.core.scheduling.infc;

import org.kuali.student.r2.common.infc.DisplayObject;
import org.kuali.student.r2.common.infc.HasId;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mezba Mahtab
 * Date: 8/28/12
 * Time: 1:27 PM
 *
 * This interface represents a reusable display object in the Scheduling Service for ScheduleRequests.
 */
public interface ScheduleRequestComponentDisplay extends HasId, DisplayObject {

    /**
     * The Campus Id to specify a Campus.
     *
     * @name Campus Ids
     */
    public List<String> getCampusIds();

    /**
     * The Building Id to specify a Building.
     *
     * @name Building Ids
     */
    public List<String> getBuildingIds();

    /**
     * The Room Id to specify a Room.
     *
     * @name Room Ids
     */
    public List<String> getRoomIds();

    /**
     * The Org Id to specify a Room or Building "owned" by an
     * Organization.
     *
     * @name Org Ids
     */
    public List<String> getOrgIds();

    /**
     * The Resource Types to specify a Room with types of fixed
     * Resources.
     *
     * @name Resource Type Keys
     */
    public List<String> getResourceTypeKeys();

    /**
     * The Time Slot Ids to specify a time slot.
     *
     * @name Time Slot Ids
     */
    public List<String> getTimeSlotIds();
}
