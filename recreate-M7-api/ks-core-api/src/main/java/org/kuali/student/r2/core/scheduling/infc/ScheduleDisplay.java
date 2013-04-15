package org.kuali.student.r2.core.scheduling.infc;

import org.kuali.student.r2.common.infc.DisplayObject;
import org.kuali.student.r2.common.infc.IdEntity;
import org.kuali.student.r2.core.atp.infc.Atp;

import java.util.List;

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
     * The ATP. Schedule Components are applied to this ATP.
     * @required
     */
    public Atp getAtp();

    /**
     * The Schedule Components. These provide a list of Time Slots
     * coupled with Room locations.
     */
    public List<? extends ScheduleComponentDisplay> getScheduleComponentDisplays();
}
