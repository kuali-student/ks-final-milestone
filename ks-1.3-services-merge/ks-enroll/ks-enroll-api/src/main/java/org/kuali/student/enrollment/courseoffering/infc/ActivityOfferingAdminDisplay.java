/*
 * Copyright 2012 The Kuali Foundation 
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

package org.kuali.student.enrollment.courseoffering.infc;

import org.kuali.student.r2.common.infc.IdEntity;

/**
 * An adminsitrative view of the ActivityOffering.
 *
 * @author tom
 */
public interface ActivityOfferingAdminDisplay
    extends IdEntity {

    /**
     * A display name for the Activity Offering Type.
     *
     * @name Activity Offering Type Display Name
     * @readOnly
     * @impl ActivityOffering.Type.Name
     */
    public String getTypeName();

    /**
     * A display name for the activity Offering State.
     *
     * @name Activity Offering State Display Name
     * @readOnly
     * @impl ActivityOffering.State.Name
     */
    public String getStateName();

    /**
     * Name of the course used in the college catalog. Initially
     * copied from the course catalog but then, depending on the
     * configuration it may be updatable. For regular courses this is
     * not generally allowed to be updated on the offering, but for
     * special topics courses this is often overridden to capture the
     * particular topic being taught offered this term. Note: the
     * configuration of the validation for titles is typically
     * restricted to exclude line breaks. This may have to be loosened
     * as some schools may want the particular topic to appear on a
     * 2nd line. For example: SPECIAL TOPICS: AN EXPLORATION OF DEEP
     * SPACE ARTIFACTS
     * 
     * @name Course Offering Title
     * @readOnly
     * @impl ActivityOffering.FormatOffering.CourseOffering.Title
     */
    public String getCourseOfferingTitle();

    /**
     * Identifies the number of a course as reflected in the course
     * catalog.  This typically must be unique across all courses
     * offered during that term. If the user wants to create two
     * separate offerings for the same course they must modify this
     * code to make it unique. For example: An on-line offering of the
     * course might have an "O" appended to it to distinguish it from
     * the face to face offering, i.e. ENG101 and ENG101O Initially
     * copied from the course catalog but then, depending on the
     * configuration it may be updatable. Often this field is
     * configured so that it is not not directly updatable but rather
     * is calculated from it's two constituent parts, the subject area
     * and the course number suffix. For example: Subject Area = "ENG"
     * and Suffix = "101" then code = "ENG101"
     * 
     * @name Course Offering Code
     * @readOnly
     * @impl ActivityOffering.FormatOffering.CourseOffering.CourseOfferingCode
     */
    public String getCourseOfferingCode();

    /**
     * The Format Offering Id used to create this ActivityOffering.
     *
     * @name Format Offering Id
     * @readOnly
     * @impl ActivityOffering.FormatOffering.Id
     */
    public String getFormatOfferingId();

    /**
     * A display name for the Format Offering.
     *
     * @name Format Offering Display Name
     * @readOnly
     * @impl ActivityOffering.FormatOffering.Name
     */
    public String getFormatOfferingName();

    /**
     * Alphanumeric character that identifies the section of the
     * course offering.
     *
     * @name Activity Offering Code
     * @readOnly
     * @impl ActivityOffering.ActivityOfferingCode
     */
    public String getActivityOfferingCode();

    /**
     * Gets the instructor of record for this ActivityOffering.
     *
     * @name Instructor Id
     * @readOnly
     * @impl ActivityOffering.OfferingInstructor.PersonId
     */
    public String getInstructorId();

    /**
     * Gets the iname of the nstructor of record for this
     * ActivityOffering.
     *
     * @name Instructor Display Name
     * @readOnly
     * @impl ActivityOffering.OfferingInstructor.Person.name
     */
    public String getInstructorName();

    /**
     * Gets a display string represnting the weekday pattern using
     * characters.
     *
     * @name Schedule Display Weekdays
     * @readOnly
     * @impl if unscheduled, this string is built from all of related
     *       ScheduleRequest.ScheduleComponents.TimeSlots.Weekdays If
     *       there is more than one related ScheduleRequest, then use
     *       the most recent active one (the one that will be sent to
     *       the scheduler).
     *
     *       if scheduled, then use
     *       ActivityOffering.Schedule.ScheduleComponents.TimeSlots.Weekdays
     */
    public String getScheduleWeekdays();

    /**
     * Gets a display string represnting the time and duration.
     *
     * @name Schedule Display Time
     * @readOnly
     * @impl if unscheduled, this string is built from all of related
     *       ScheduleRequest.ScheduleComponents.TimeSlots.StartTime
     *       and ScheduleRequest.ScheduleComponents.TimeSlots.Duration
     *       and If there is more than one related ScheduleRequest,
     *       then use the most recent active one (the one that will be
     *       sent to the scheduler).
     *
     *       if scheduled, then use
     *       ActivityOffering.Schedule.ScheduleComponents.TimeSlots.StartTime
     *       and
     *       ActivityOffering.Schedule.ScheduleComponents.TimeSlots.Duration
     */
    public String getScheduleTime();
}
