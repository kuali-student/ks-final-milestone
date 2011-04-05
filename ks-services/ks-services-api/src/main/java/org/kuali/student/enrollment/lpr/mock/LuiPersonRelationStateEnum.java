/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.lpr.mock;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.kuali.student.common.infc.AttributeInfc;
import org.kuali.student.common.infc.StateInfc;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationConstants;

/**
 * States for Learning Person Relations
 * <p/>
 * See https://wiki.kuali.org/display/STUDENT/LuiPeronRelation+Types+and+States#LuiPeronRelationTypesandStates-References
 *
 * @author nwright
 */
public enum LuiPersonRelationStateEnum implements StateInfc, Serializable {

    /**
     * Student states to courses
     */
    PLANNED(LuiPersonRelationConstants.PLANNED_STATE_KEY, "Planned", "The student plans on taking this course or program", asDate("20100101"), null, null),
    REGISTERED(LuiPersonRelationConstants.REGISTERED_STATE_KEY, "Registered", "The student is officially registered for the course or section", asDate("20100101"), null, null),
    WAITLISTED(LuiPersonRelationConstants.WAITLISTED_STATE_KEY, "Waitlisted", "The student attempted to join but has been put on the waitlist", asDate("20100101"), null, null),
    DROPPED(LuiPersonRelationConstants.DROPPED_STATE_KEY, "Dropped Early", "Student dropped the course before the normal deadline", asDate("20010101"), null, null),
    DROPPED_LATE(LuiPersonRelationConstants.DROPPED_LATE_STATE_KEY, "Dropped Late", "The student was registered but subsequently dropped the course or section past the normally allotted time period, typically resulting in a special grade or mark to so indicate", asDate("20010101"), null, null),
    /**
     * Instructor states
     */
    TENATIVE(LuiPersonRelationConstants.TENATIVE_STATE_KEY, "Tentative", "The instructor is proposed to teach this course or section but it has not yet been confirmed", asDate("20010101"), null, null),
    ASSIGNED(LuiPersonRelationConstants.ASSIGNED_STATE_KEY, "Assigned", "The instructor is assigned to teach this course or section.", asDate("20010101"), null, null),
    UNASSIGNED(LuiPersonRelationConstants.UNASSIGNED_STATE_KEY, "Unassigned", "The instructor had been assigned but then that assignment was removed", asDate("20010101"), null, null),
    /**
     * Program states
     */
    INQUIRED(LuiPersonRelationConstants.INQUIRED_STATE_KEY, "Inquired", "The student took an active step in contacting the program indicating their plans", asDate("20100101"), null, null),
    APPLIED(LuiPersonRelationConstants.APPLIED_STATE_KEY, "Applied", "The student has applied for the program", asDate("20100101"), null, null),
    ADMITTED(LuiPersonRelationConstants.ADMITTED_STATE_KEY, "Admitted", "The student has been admitted to the program ", asDate("20100101"), null, null),
    DENIED(LuiPersonRelationConstants.DENIED_STATE_KEY, "Denied", "The student was denied admission to the program", asDate("20100101"), null, null),
    CONFIRMED(LuiPersonRelationConstants.CONFIRMED_STATE_KEY, "Confirmed", "The student has confirmed that she plans to matriculate ", asDate("20100101"), null, null),
    CANCELED(LuiPersonRelationConstants.CANCELED_STATE_KEY, "Canceled", "The student canceled prior to matriculation", asDate("20100101"), null, null),
    DEFERED(LuiPersonRelationConstants.DEFERED_STATE_KEY, "Deferred", "The student defers matriculation to a different term", asDate("20100101"), null, null),
    ENROLLED(LuiPersonRelationConstants.ENROLLED_STATE_KEY, "Enrolled", "The student is fully enrolled in the program ", asDate("20100101"), null, null),
    TEMPORARY_ABSENCE(LuiPersonRelationConstants.TEMPORARY_ABSENCE_STATE_KEY, "Temporary Absence", "The student has temporarily not matriculated but is expected to return", asDate("20100101"), null, null),
    WITHDRAWN(LuiPersonRelationConstants.WITHDRAWN_STATE_KEY, "Withdrawn", "The student was registered but then withdrew from the program", asDate("20100101"), null, null),
    PROBATION(LuiPersonRelationConstants.PROBATION_STATE_KEY, "Probation", "The student must fulfill certain requirements in order to stay in the program", asDate("20100101"), null, null);
    /**
     * States used for isntructors of courses
     */
    public static final LuiPersonRelationStateEnum[] COURSE_INSTRUCTOR_STATES = {TENATIVE, ASSIGNED, UNASSIGNED};
    /**
     * Types used for students in courses
     */
    public static final LuiPersonRelationStateEnum[] COURSE_STUDENT_STATES = {PLANNED, REGISTERED, WAITLISTED, DROPPED, DROPPED_LATE};
    /**
     * States used for isntructors of PROGRAMS
     */
    public static final LuiPersonRelationStateEnum[] PROGRAM_ADVISOR_STATES = {TENATIVE, ASSIGNED, UNASSIGNED};
    /**
     * Types used for students in PROGRAMS
     */
    public static final LuiPersonRelationStateEnum[] PROGRAM_STUDENT_STATES = {PLANNED, INQUIRED, APPLIED, WAITLISTED, DENIED, CONFIRMED, CANCELED, DEFERED, ENROLLED, TEMPORARY_ABSENCE, WITHDRAWN, PROBATION};
    private static final long serialVersionUID = 1L;
    private String name;
    private String descr;
    private Date effectiveDate;
    private Date expirationDate;
    private List<? extends AttributeInfc> attributes;
    private String key;

    LuiPersonRelationStateEnum(String key, String name, String descr, Date effectiveDate, Date expirationDate, List<? extends AttributeInfc> attributes) {
        this.key = key;
        this.name = name;
        this.descr = descr;
        this.effectiveDate = effectiveDate;
        this.expirationDate = expirationDate;
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescr() {
        return this.descr;
    }

    @Override
    public Date getEffectiveDate() {
        return this.effectiveDate;
    }

    @Override
    public Date getExpirationDate() {
        return this.expirationDate;
    }

    private void setAttributes(List<? extends AttributeInfc> attributes) {
        this.attributes = attributes;
    }

    @Override
    public List<? extends AttributeInfc> getAttributes() {
        return this.attributes;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    private static Date asDate(String dateStr) {
        if (dateStr == null) {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        try {
            return df.parse(dateStr);
        } catch (ParseException ex) {
            throw new IllegalArgumentException(ex);
        }
    }
}
