/**
 * Copyright 2010 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.common.infc;

/**
 * Captures space and time information associated with a meeting or
 * event.
 * 
 * @author Kuali Student Team (Kamal)
 */

// TODO: This interface should eventually move into the scheduling
//       service domain

public interface MeetingSchedule 
    extends HasId {

    /**
     * Space code where the meeting is going to take place.
     * 
     * @name Space Code
     */
    public String getSpaceId();

    /**
     * Date and Time The date and time values for all vCalendar properties are
     * formatted as a string consistent with the ISO 8601 representation for
     * combinations of dates and times. Either the basic or extended format is
     * allowed. The use of UTC, rather than local time, should be used when ever
     * possible in order to avoid time zone ambiguities. The format for the
     * complete, basic representation of a date and time value is written in the
     * following sequence of characters:
     * <year><month><day>T<hour><minute<second><type designator> For example,
     * 8:30 AM on April 15, 1996 local time would be written as: 19960415T083000
     * And the same time in UTC based time would be written as: 19960415T083000Z
     * Where a value needs to specify a sequence of date and time values, then
     * the property value is a string made up of a list of date and time values,
     * separated by the field separator. For example: 19960101T090000Z;
     * 19960201T090000Z; 19960301T090000Z; 19960401T090000Z; ... Time Duration
     * The values for time duration or periods of time for all vCalendar
     * properties are formatted as a string conformant with the ISO 8601 basic
     * representation for duration of time. A given duration of a period of time
     * is represented by a character string consisting of the designator "P",
     * optionally including the number of years followed by the designator "Y",
     * optionally including the number of months followed by the designator "M",
     * optionally including the number of weeks followed by the designator "W",
     * optionally including the number of days followed by the designator "D".
     * The sequence can also contain a time component preceded by the designator
     * "T", optionally including the number of hours followed by the designator
     * "H", optionally including the number of minutes followed by the
     * designator "M", optionally including the number of seconds followed by
     * the designator "S". For example: P6W A period of six weeks; PT15M A
     * period of 15 minutes; PT1H30M A period of 1 hour and thirty minutes; or
     * P2Y10M15DT10H30M20S A period of 2 years, 10 months, 15 days, 10 hours, 30
     * minutes, and 20 seconds.
     * 
     * @name Schedule Id
     */
    public String getScheduleId();
}
