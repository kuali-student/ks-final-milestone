package org.kuali.student.r2.core.scheduling.infc;

import org.kuali.student.r2.common.infc.DisplayObject;
import org.kuali.student.r2.common.infc.IdEntity;

/**
 * Created with IntelliJ IDEA.
 * User: Mezba Mahtab
 * Date: 8/28/12
 * Time: 1:38 PM
 *
 * This interface represents a reusable display object in the Scheduling Service for Schedules.
 */
public interface ScheduleDisplay extends IdEntity, DisplayObject {

    /**
     * Display string representing the time of the Activity Offering.
     * Eg. 9:00am - 11:00am
     */
    public String getScheduleTime();

    /**
     * Display string representing the weekday pattern on which the Activity Offering meets.
     * Eg. MWF
     */
    public String getScheduleWeekdays();

    /**
     * The building and room location.
     */
    public String getLocation ();
}
